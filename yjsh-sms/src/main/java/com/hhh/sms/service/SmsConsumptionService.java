package com.hhh.sms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.common.SmsApi;
import com.hhh.sms.dao.SmsApplyRecordDao;
import com.hhh.sms.dao.SmsConsumptionDao;
import com.hhh.sms.dao.SmsCountDao;
import com.hhh.sms.dao.SmsCountDaoImpl;
import com.hhh.sms.dao.SmsUserDao;
import com.hhh.sms.dao.entity.SmsConsumption;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.web.model.SmsApplyRecordBean;
import com.hhh.sms.web.model.SmsBean;
import com.hhh.sms.web.model.SmsLoginBean;
import com.hhh.sms.web.model.SmsPage;
import com.hhh.sms.web.model.SmsConsumptionBean;
import com.hhh.sms.web.model.SmsCountBean;
import com.hhh.sms.web.model.SmsResultBean;
import com.hhh.sms.web.model.SmsUserBean;

@Component
@Transactional
public class SmsConsumptionService {
	@Autowired
	private SmsConsumptionDao consumptionDao;
	@Autowired
	private SmsApplyRecordDao recordDao;
	@Autowired
	private SmsUserDao smsUserDao;
	@Autowired
	private SmsCountDao smsCountDao;
	@Autowired
	private Environment env;
	
	/**
	 * 添加消费记录明细
	 */
	public void saveSmsConsumption(SmsConsumption com){
		consumptionDao.save(com);
	}
	
	/**
	 * 批量保存
	 * @param coms
	 */
	public void saveSmsConsumption(List<SmsConsumption> coms){
		consumptionDao.save(coms);
	}
	
	/**
	 * 发送短信
	 * @param userId 用户id
	 * @param content 发送内容
	 * @param userNumber 手机号码(可以多个，中间逗号隔开)
	 * @return recordList 发送的消费记录
	 */
	public Map<String,Object> sendMsg(String userId,String content,String userNumber){
		SmsApi smsApi = new SmsApi();
		SmsLoginBean loginBean = new SmsLoginBean();
		loginBean.setSpCode(env.getProperty("spCode"));
		loginBean.setPassword(env.getProperty("password"));
		loginBean.setLoginName(env.getProperty("loginName"));
		SmsBean smsBean = new SmsBean();
		smsBean.setMessageContent(content);
		smsBean.setUserNumber(userNumber);
		//生成流水号
		Random random = new Random(); 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String serialNumber = df.format(new Date())+String.valueOf(random.nextInt(100)+100);
		smsBean.setSerialNumber(serialNumber);//流水号
		smsBean.setScheduleTime("");
		smsBean.setExtendAccessNum("");
		smsBean.setF("1");
		SmsResultBean resultBean = smsApi.sendMsg(loginBean, smsBean);
		//发送时间
		Date sendTime = new Date();
		//发送成功
		List<SmsConsumption> recordList = new ArrayList<SmsConsumption>();
		String userNumbers[] = userNumber.split(",");
		if(resultBean.getResult()==0){
			String other = resultBean.getOther();
			//找出发送失败的号码
			Matcher m=Pattern.compile("faillist=(.*?)(&|$)").matcher(other);
			String faillist = null;
			while(m.find())faillist = m.group(1);
			String failNumbers[] = null;
			if(faillist!=null&&!faillist.equals("")){
				failNumbers = faillist.split(",");
			}
			SmsConsumption record = null;
			for(String number : userNumbers){
				if(failNumbers!=null){
					for(String failNumber : failNumbers){
						if(!number.equals(failNumber)){
							record = new SmsConsumption();
							record.setContent(content);
							record.setSendTime(sendTime);
							record.setTelephone(number);
							record.setStatus(ConstantClassField.SMS_CONSUMPTION_STATUS_SENDED);
							record.setSmsUser(smsUserDao.findOne(userId));
							recordList.add(record);
						}else{
							record = new SmsConsumption();
							record.setContent(content);
							record.setSendTime(sendTime);
							record.setTelephone(failNumber);
							record.setStatus(ConstantClassField.SMS_CONSUMPTION_STATUS_NOT_SEND);
							record.setSmsUser(smsUserDao.findOne(userId));
							recordList.add(record);
						}
					}
				}else{
					record = new SmsConsumption();
					record.setContent(content);
					record.setSendTime(sendTime);
					record.setTelephone(number);
					record.setStatus(ConstantClassField.SMS_CONSUMPTION_STATUS_SENDED);
					record.setSmsUser(smsUserDao.findOne(userId));
					recordList.add(record);
				}
			}
		}else{//发送失败
			for(String number : userNumbers){
				SmsConsumption record = new SmsConsumption();
				record.setContent(content);
				record.setSendTime(sendTime);
				record.setTelephone(number);
				record.setStatus(ConstantClassField.SMS_CONSUMPTION_STATUS_NOT_SEND);
				record.setSmsUser(smsUserDao.findOne(userId));
				recordList.add(record);
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", recordList);
		map.put("result", resultBean);
		return map;
	}
	
	/**
	 * 查询消费记录明细
	 */
	public SmsPage<SmsConsumptionBean> listConsumptionByUserId(SmsConsumptionBean consumption,Pageable pageable){
		String userId = consumption.getUserId();
		String customerId = consumption.getCustomerId();
		String customerName = consumption.getCustomerName();
		String sendTime = consumption.getSendTime();
		int status = consumption.getStatus();
		Page<SmsConsumption> cons=null;
		if(smsUserDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_CUSTOMER){
			cons = consumptionDao.findByUserId(userId,pageable);
		}
		if(smsUserDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_MANAGER&&consumption.getUserIdOfDetail()==null){//管理员可以查看所有记录
			cons = consumptionDao.findAll(getConSpec(consumption),pageable);
		}
		if(smsUserDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_MANAGER&&consumption.getUserIdOfDetail()!=null){//当管理员点击短信统计的消费记录明细时查看当前企业的记录
			 cons = consumptionDao.findByUserId(consumption.getUserIdOfDetail(),pageable);
		 }
		if(smsUserDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&consumption.getUserIdOfDetail()==null){//部门管理员可以查看所有记录
			cons = consumptionDao.findAll(getConSpec(consumption),pageable);
		}
		if(smsUserDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&consumption.getUserIdOfDetail()!=null){//当部门管理员点击短信统计的消费记录明细时查看当前企业的记录
			 cons = consumptionDao.findByUserId(consumption.getUserIdOfDetail(),pageable);
		 }
		List<SmsConsumption> recordList = cons.getContent();
		List<SmsConsumptionBean> list = new ArrayList<SmsConsumptionBean>();
		SmsConsumptionBean sprb = null;
		for(SmsConsumption r : recordList){
			sprb = new SmsConsumptionBean();
			sprb.setId(r.getId());
			sprb.setContent(r.getContent());
			sprb.setCustomerId(r.getSmsUser().getCustomerId());
			sprb.setCustomerName(r.getSmsUser().getCustomerName());
			sprb.setSendTime(r.getSendTime());
			sprb.setStatus(r.getStatus());
			sprb.setTelephone(r.getTelephone());
			sprb.setUserId(r.getSmsUser().getId());
			list.add(sprb);
		}
		SmsPage<SmsConsumptionBean> page = new SmsPage<SmsConsumptionBean>(cons.getTotalPages(), cons.getTotalElements(), list);
		return page;
	}
	
	/**
	 * 部门查询消费记录明细
	 */
	public SmsPage<SmsConsumptionBean> listConsumptionByDept(String dept,Pageable pageable){
		Page<SmsConsumption> cons=null;
		List<SmsUser> userList = smsUserDao.findByDept(dept);
		List<SmsConsumption> recordList =new ArrayList<SmsConsumption>();
		for(SmsUser user : userList){
			String userId = user.getId();
			List<SmsConsumption> recordList1 = consumptionDao.findBySmsUserId(userId);
			recordList.addAll(recordList1);
		}
		List<SmsConsumptionBean> list = new ArrayList<SmsConsumptionBean>();
		SmsConsumptionBean sprb = null;
		int pageSize = pageable.getPageSize();
		int size = recordList.size();
		int number = size/pageSize+1;
		for(SmsConsumption r : recordList){
			sprb = new SmsConsumptionBean();
			sprb.setId(r.getId());
			sprb.setContent(r.getContent());
			sprb.setCustomerId(r.getSmsUser().getCustomerId());
			sprb.setCustomerName(r.getSmsUser().getCustomerName());
			sprb.setSendTime(r.getSendTime());
			sprb.setStatus(r.getStatus());
			sprb.setTelephone(r.getTelephone());
			sprb.setUserId(r.getSmsUser().getId());
			list.add(sprb);
		}
		List<SmsConsumptionBean> list1 = new ArrayList<SmsConsumptionBean>();
		int pagenumber = pageable.getPageNumber();
		int pagesize = pageable.getPageSize();
		if(pagenumber != 0){
			pagenumber = pagenumber*pagesize;
			pagesize = pagenumber+pagesize;
		}
		int bd = number-1;
		int pagenumber1 = pageable.getPageNumber();
		if(pagenumber1 == bd){
			pagesize=size;
		}
		for(int i=pagenumber;i<pagesize;i++){
			SmsConsumptionBean ss = (SmsConsumptionBean)list.get(i);
			list1.add(ss);
		}
		SmsPage<SmsConsumptionBean> page = new SmsPage<SmsConsumptionBean>(number, recordList.size(), list1);
		return page;
	}
	
	public Specification<SmsConsumption> getConSpec(final SmsConsumptionBean con){
		return new Specification<SmsConsumption>() {
			@Override
			public Predicate toPredicate(Root<SmsConsumption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				Join<SmsConsumption,SmsUser> join = root.join(root.getModel().getSingularAttribute("smsUser",SmsUser.class) , JoinType.LEFT);
				if(null != con.getCustomerId() && con.getCustomerId().length() != 0){
					list.add(cb.like(join.get("customerId").as(String.class),"%"+con.getCustomerId()+"%"));
				}
				if(null != con.getCustomerName() && con.getCustomerName().length() != 0){
					list.add(cb.like(join.get("customerName").as(String.class), "%"+con.getCustomerName()+"%"));
				}
				if(null != con.getStartTime() && con.getStartTime().length() != 0 && null != con.getEndTime() && con.getEndTime().length() != 0){
					SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date startTime = null;
					Date endTime = null;
					try {
						startTime = sdf.parse(con.getStartTime());
						endTime = sdf.parse(con.getEndTime());
					} catch (Exception e) {
						e.printStackTrace();
					}
					list.add(cb.between(root.get("sendTime").as(Date.class),startTime, endTime));
				}
				if(0 == con.getStatus() ){
				list.add(cb.equal(root.get("status"), con.getStatus()));
				}else{
					list.add(cb.equal(root.get("status"),  con.getStatus()));
				}
				//从管理员中加入部门管理员的hql语句
				if(smsUserDao.findOne(con.getUserId()).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){
					list.add(cb.like(join.get("dept").as(String.class), "%"+smsUserDao.findOne(con.getUserId()).getDept()+"%"));
				}
				if(list.isEmpty()){
					return null;
				}else{
					query.where(cb.and(list.toArray(new Predicate[list.size()])));
					query.orderBy(cb.desc(root.get("sendTime").as(Date.class)));
					Predicate predicate = query.getRestriction();
					return predicate;
				}
			}
			
		};
	}
	
	/**
	 * 短信统计查询
	 */
	public List<SmsCountBean> listSmsCount(SmsCountBean scb,Pageable pageable){
		String customerId = scb.getCustomerId();
		String customerName = scb.getCustomerName();
		String userId = scb.getUserId();
		List<SmsCountBean> list = smsCountDao.findSmsCount(userId,customerId,customerName,pageable);
		List<SmsCountBean> resultList = addSendedAndRemainMsg(list);
		return resultList;
	}
	
	
	/**
	 * 部门短信统计查询
	 */
	public List<SmsCountBean> listSmsCountByDept(Pageable pageable){
		List<SmsCountBean> list = smsCountDao.findSmsCountByDept(pageable);
		//SmsUser smsUser = smsUserDao.findByCustomerId(customerId);
		String dept="";
		List<SmsCountBean> resultList = addSendedAndRemainMsg(list);
		return resultList;
	}
	
	/**
	 * 部门短信统计查询
	 */
	public List<SmsCountBean> listCountByDept(String dept,Pageable pageable){
		List<SmsCountBean> list = smsCountDao.findCountByDept(dept,pageable);
		//SmsUser smsUser = smsUserDao.findByCustomerId(customerId);
		List<SmsCountBean> resultList = addSendedAndRemainMsg(list);
		return resultList;
	}
	/**
	 * 获取短信统计总记录数
	 * @return
	 */
	public int getTjCount(SmsCountBean scb){
		String customerId = scb.getCustomerId();
		String customerName = scb.getCustomerName();
		String userId = scb.getUserId();
		int count = smsCountDao.getCount(userId,customerId,customerName);
		return count;
	}
	
	/**
	 * 往每个SmsCountBean中添加msgRemain(剩余短信)数据
	 * @return
	 */
	public List<SmsCountBean> addSendedAndRemainMsg(List<SmsCountBean> beanList){
		for(SmsCountBean scb : beanList){
			String customerId = scb.getCustomerId();
			int sendedAmount = scb.getSendedAmount();
			int msgRemain = scb.getMsgAmount() - sendedAmount;
			scb.setMsgRemain(msgRemain);
		}
		return beanList;
	}
	
	/**
	 * 获取短信剩余短信量
	 * @return
	 */
//	public Integer getRemindMsg(String userId){
//		Map<String,Object> map = smsCountDao.findSmsCount(userId, null, null, null);
//		List
//		for(String key : map.keySet()){
//			if(key.equals("countList"))
//		}
//	}
	
	/**
	 * 获取消费记录总数
	 * @return
	 */
	public int getCount(){
		return (int)consumptionDao.count();
	}
	
	/**
	 * 获取指定用户的的记录总数
	 * @return
	 */
	public int getCountByUserId(String userId){
		return consumptionDao.findCountByUserId(userId);
	}
	
	/**
	 * 获取页码,从0页开始
	 * @return
	 */
	public int getPage(int start,int pageSize){
		return (int)Math.floor((double)start/pageSize);
	}
	/**
	 * @param 申请的实体
	 * @return 当前实体的用户的部门的数量
	 * */
	public int getCountBySmsUserDept(SmsConsumptionBean record){
		String dept = smsUserDao.findOne(record.getUserId()).getDept();
		List list = smsUserDao.findByDept(dept);
		return list.size();
	}
	/**
	 * @param 申请的实体
	 * @return 当前实体的用户的部门的数量
	 * */
	public int findCountAll(){
		List list = smsCountDao.findAllCount();
		return list.size();
	}
}
