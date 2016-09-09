package com.hhh.sms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.fund.web.model.UserBean;
import com.hhh.security.util.EncryptHelper;
import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.dao.SmsApplyRecordDao;
import com.hhh.sms.dao.SmsConsumptionDao;
import com.hhh.sms.dao.SmsUserDao;
import com.hhh.sms.dao.entity.SmsApplyRecord;
import com.hhh.sms.dao.entity.SmsConsumption;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.web.model.SmsPage;
import com.hhh.sms.web.model.SmsUserBean;

@Component
@Transactional
public class SmsUserService {
	@Autowired
	private SmsUserDao smsUserDao;
	@Autowired
	private SmsConsumptionDao consumptionDao;
	@Autowired
	private SmsApplyRecordDao	recordDao;
	
	/**
	 * 根据条件查询所有用户
	 * @param user
	 * @return
	 */
	public SmsPage<SmsUserBean> listSmsUsers(SmsUserBean user,Pageable pageable){
		Page<SmsUser> users = null;
		if(user.getType()==ConstantClassField.SMS_USER_TYPE_MANAGER){
			users = smsUserDao.findAll(getUserSpec(user), pageable);
		}else if(user.getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){
			users = smsUserDao.findAll(getUserSpec(user), pageable);
		}else{
				users = smsUserDao.findById(user.getId(),pageable);
		}
		List<SmsUser> userList = users.getContent();
		List<SmsUserBean> list = new ArrayList<SmsUserBean>();
		SmsUserBean userBean = null;
		for(SmsUser u : userList){
			userBean = new SmsUserBean();
			userBean.setId(u.getId());
			userBean.setContactor(u.getContactor());
			userBean.setContactorInfo(u.getContactorInfo());
			userBean.setCustomerId(u.getCustomerId());
			userBean.setCustomerName(u.getCustomerName());
			userBean.setMsgAmount(u.getMsgAmount());
			userBean.setUsername(u.getUsername());
			userBean.setType(u.getType());
			userBean.setDept(u.getDept());
			list.add(userBean);
		}
		SmsPage<SmsUserBean> page = new SmsPage<SmsUserBean>(users.getTotalPages(), users.getTotalElements(), list);
		return page;
	}
	
	@Transactional(readOnly=true)
	public Specification<SmsUser> getUserSpec(final SmsUserBean user){
		return new Specification<SmsUser>() {
			@Override
			public Predicate toPredicate(Root<SmsUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(null != user.getCustomerId() && user.getCustomerId().length() != 0){
					list.add(cb.like(root.get("customerId").as(String.class), "%"+user.getCustomerId()+"%"));
				}
				if(null != user.getCustomerName() && user.getCustomerName().length() != 0){
					list.add(cb.like(root.get("customerName").as(String.class), "%"+user.getCustomerName()+"%"));
				}
				if(null != user.getDept() && user.getDept().length() != 0){
					list.add(cb.like(root.get("dept").as(String.class), "%"+user.getDept()+"%"));
				}
				//从管理员中加入部门管理员的hql语句
				if(user.getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){
					list.add(cb.like(root.get("dept").as(String.class), "%"+user.getDept()+"%"));
					list.add(cb.notLike(root.get("type").as(String.class), "%1%"));
					list.add(cb.notLike(root.get("id").as(String.class), "%"+user.getId()+"%"));
				}
				if(list.isEmpty()){
					return null;
				}else{
					query.where(cb.and(list.toArray(new Predicate[list.size()])));
					Predicate predicate = query.getRestriction();
					return predicate;
				}
			}
		};
	}
	/**
	 * 添加或者修改用户
	 */
	public void saveOrUpdateSmsUser(SmsUserBean user){
		String salt = EncryptHelper.randomNumberSalt();
		String pwd = EncryptHelper.entrypt(user.getPassword(), salt);
		
		SmsUser smsUser = new SmsUser();
		smsUser.setId(user.getId());
		smsUser.setUsername(user.getUsername());
		smsUser.setPassword(pwd);
		smsUser.setSalt(salt);
		smsUser.setCustomerId(user.getCustomerId());
		smsUser.setCustomerName(user.getCustomerName());
		smsUser.setContactor(user.getContactor());
		smsUser.setContactorInfo(user.getContactorInfo());
		smsUser.setMsgAmount(user.getMsgAmount());
		smsUser.setType(user.getType());
		smsUser.setDept(user.getDept());
		smsUserDao.save(smsUser);
	}
	
	/**
	 * 根据企业id查询用户
	 */
	public Page<SmsUser> findSmsUser(String customerId,Pageable pageable){
		return smsUserDao.findByCustomerId(customerId,pageable);
	}
	
	/**
	 * 根据用户id查询用户
	 * @param userId
	 * @return
	 */
	public SmsUser findOne(String userId){
		return smsUserDao.findOne(userId);
	}
	
	/**
	 * 根据id删除用户,删除用户的时候同时返回短信剩余量(短信总量-已发送量)给管理员
	 */
	public void deleteSmsUserById(String smsUserId){
		SmsUser smsUser = smsUserDao.findOne(smsUserId);
		int msgAmount = smsUser.getMsgAmount();//短信总量
		int sendedAmount = consumptionDao.findSumMsgByCustomerId(smsUserId);
		List<SmsUser> list = smsUserDao.findByType(ConstantClassField.SMS_USER_TYPE_MANAGER);//查找管理员账号
		for(SmsUser manager:list){
			manager.setMsgAmount(manager.getMsgAmount()+(msgAmount-sendedAmount));//短信剩余量(短信总量-已发送量)
		}
		smsUserDao.delete(smsUserId);
		//删除用户的同时删除相关的申购历史记录和消费记录
		recordDao.deleteByUserId(smsUserId);
		consumptionDao.deleteByUserId(smsUserId);
	}
	
	/**
	 * 根据用户名和密码判断是否匹配成功
	 * @param username
	 * @param password
	 * @return	存放结果和登陆用户
	 */
	public Map<String,Object> checkUserNameAndPassword(String userName, String password){
		SmsUser user = smsUserDao.findByUsername(userName);
		Map<String,Object> map = new HashMap<String,Object>();
		if(user!=null){
			String salt = user.getSalt();
			String loginpwd = EncryptHelper.entrypt(password, salt);
			if(loginpwd.equals(user.getPassword())){
				map.put("loginUser", user);
				map.put("result", true);
			}else{
				map.put("result", false);
				map.put("loginUser", null);
			}
		}else{
			map.put("loginUser", null);
			map.put("result", false);
		}
		return map;
	}
	
	/**
	 * 将登陆用户保存到cookie中
	 * @param loginUser
	 * @return
	 */
	public void addLoginUserCookie(SmsUser loginUser){
		Cookie userNameCookie = new Cookie();
		userNameCookie.setPath("/");
		userNameCookie.setName("userName");
		userNameCookie.setValue(loginUser.getUsername());
		Cookie pwdCookie = new Cookie();
		pwdCookie.setPath("/");
		pwdCookie.setName("password");
		pwdCookie.setValue(loginUser.getPassword());
	}
	
	/**
	 * 获取总页数
	 * @param pageSize
	 * @return
	 */
	public int getPageCount(int pageSize) {
		return (int)Math.ceil( (double)smsUserDao.count() / pageSize);
	}
	
	/**
	 * 获取记录总数
	 * @return
	 */
	public int getCount(){
		return (int)smsUserDao.count();
	}
	
	/**
	 * 根据用户类型获取记录总数
	 * @param type
	 * @return
	 */
	public int getCountByType(int type){
		List<SmsUser> list = smsUserDao.findByType(type);
		return list.size();
	}
	
	/**
	 * 获取页码,从0页开始
	 * @return
	 */
	public int getPage(int start,int pageSize){
		return (int)Math.floor((double)start/pageSize);
	}
	
	/**
	 * 根据用户id获取用户类型
	 * @param userId
	 * @return
	 */
	public int getTypeOfUser(String userId){
		return smsUserDao.findOne(userId).getType();
	}
	
	
	public SmsUserBean userToUserBean(SmsUser user){
		SmsUserBean userBean = null;
		if(user!=null){
			userBean = new SmsUserBean();
			userBean.setContactor(user.getContactor());
			userBean.setContactorInfo(user.getContactorInfo());
			userBean.setCustomerId(user.getCustomerId());
			userBean.setCustomerName(user.getCustomerName());
			userBean.setId(user.getId());
			userBean.setMsgAmount(user.getMsgAmount());
			userBean.setPassword(user.getPassword());
			userBean.setType(user.getType());
			userBean.setUsername(user.getUsername());
			userBean.setSalt(user.getSalt());
			userBean.setDept(user.getDept());
		}
		return userBean;
	}
	
	public SmsUserBean findByUserName(String userName){
		SmsUser user = smsUserDao.findByUsername(userName);
		return userToUserBean(user);
	}
	
	public void updateMsgAmount(String id,Integer msgAmount){
		smsUserDao.updateMsgAmount(id, msgAmount);
	}
	
	public List<SmsUser> findByType(int type){
		return smsUserDao.findByType(type);
	}
	
	public SmsUser findByCustomerId(String customerId){
		return smsUserDao.findByCustomerId(customerId);
	}
	/**
	 * @param 部门
	 * @return 这个部门的记录的数量
	 * */
	public int getCountByDept(String dept){
		List<SmsUser> list = smsUserDao.findByDept(dept);
		return list.size();
	}
}
