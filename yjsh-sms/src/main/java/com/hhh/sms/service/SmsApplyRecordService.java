package com.hhh.sms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.common.PrimaryGenerater;
import com.hhh.sms.dao.SmsApplyRecordDao;
import com.hhh.sms.dao.SmsPackageDao;
import com.hhh.sms.dao.SmsUserDao;
import com.hhh.sms.dao.entity.SmsApplyRecord;
import com.hhh.sms.dao.entity.SmsConsumption;
import com.hhh.sms.dao.entity.SmsPackage;
import com.hhh.sms.dao.entity.SmsUser;
import com.hhh.sms.web.model.SmsApplyRecordBean;
import com.hhh.sms.web.model.SmsConsumptionBean;
import com.hhh.sms.web.model.SmsPage;
import com.hhh.sms.web.model.SmsUserBean;

@Component
@Transactional
public class SmsApplyRecordService {
	@Autowired
	private SmsApplyRecordDao recordDao;
	@Autowired
	private SmsUserDao smsUserDao;
	@Autowired
	private SmsPackageDao smsPackageDao;
	
	/**
	 * 查看申购历史记录
	 * @param userId 登录人id
	 * @param customerId 需要查看的企业id
	 */
	public SmsPage<SmsApplyRecordBean> listRecordsByUserId(SmsApplyRecordBean record,Pageable pageable){
		Page<SmsApplyRecord> records = null;
		 if(smsUserDao.findOne(record.getUserId()).getType()==ConstantClassField.SMS_USER_TYPE_CUSTOMER){//只可以查看企业自己的记录
			 records = recordDao.findByUserId(record.getUserId(),pageable);
		 }
		 if(smsUserDao.findOne(record.getUserId()).getType()==ConstantClassField.SMS_USER_TYPE_MANAGER&&record.getUserIdOfDetail()!=null){//当管理员点击用户管理明细时查看当前企业的记录
			 records = recordDao.findByUserId(record.getUserIdOfDetail(),pageable);
		 }
		 if(smsUserDao.findOne(record.getUserId()).getType()==ConstantClassField.SMS_USER_TYPE_MANAGER&&record.getUserIdOfDetail()==null){//管理员可以查看所有记录
			 records = recordDao.findAll(getRecordSpec(record),pageable);
		 }
		 if(smsUserDao.findOne(record.getUserId()).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&record.getUserIdOfDetail()!=null){//当部门管理员点击用户管理明细时查看当前企业的记录
			 records = recordDao.findByUserId(record.getUserIdOfDetail(),pageable);
		 }
		 if(smsUserDao.findOne(record.getUserId()).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER&&record.getUserIdOfDetail()==null){//部门管理员可以查看所有记录
			 records = recordDao.findAll(getRecordSpec(record),pageable);
		 }
		 List<SmsApplyRecord> historyList = records.getContent();
		 List<SmsApplyRecordBean> list = new ArrayList<SmsApplyRecordBean>();
		 SmsApplyRecordBean sphb = null;
		 for(SmsApplyRecord h : historyList){
			 sphb = new SmsApplyRecordBean();
			 sphb.setId(h.getId());
			 sphb.setApplyCode(h.getApplyCode());
			 sphb.setApplyDate(h.getApplyDate());
			 if(h.getConfirmer()!=null){
				 sphb.setConfirmerId(h.getConfirmer().getId());
			 }else{
				 sphb.setConfirmerId(null);
			 }
			 sphb.setConfirmTime(h.getConfirmTime());
			 sphb.setCustomerId(h.getSmsUser().getCustomerId());
			 sphb.setCustomerName(h.getSmsUser().getCustomerName());
			 sphb.setPrice(h.getSmsPackage().getPrice());
			 sphb.setMsgAmount(h.getSmsPackage().getMsgAmount());
			 sphb.setConfirmStatus(h.getConfirmStatus());
			 sphb.setUserId(h.getSmsUser().getId());
			 list.add(sphb);
		 }
		 SmsPage<SmsApplyRecordBean> page = new SmsPage<SmsApplyRecordBean>(records.getTotalPages(), records.getTotalElements(), list);
		 return page;
	}
	
	@Transactional(readOnly=true)
	public Specification<SmsApplyRecord> getRecordSpec(final SmsApplyRecordBean record){
		return new Specification<SmsApplyRecord>() {
			@Override
			public Predicate toPredicate(Root<SmsApplyRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				Join<SmsApplyRecord,SmsUser> join = root.join(root.getModel().getSingularAttribute("smsUser",SmsUser.class) , JoinType.LEFT);
				if(null != record.getApplyCode() && record.getApplyCode().length() != 0){
					list.add(cb.like(root.get("applyCode").as(String.class), "%"+record.getApplyCode()+"%"));
				}
				if(null != record.getCustomerId() && record.getCustomerId().length() != 0){
					list.add(cb.like(join.get("customerId").as(String.class),"%"+record.getCustomerId()+"%"));
				}
				if(null != record.getCustomerName() && record.getCustomerName().length() != 0){
					list.add(cb.like(join.get("customerName").as(String.class), "%"+record.getCustomerName()+"%"));
				}
				if(null != record.getStartTime() && record.getStartTime().length() != 0 && null != record.getEndTime() && record.getEndTime().length() != 0){
					SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date startTime = null;
					Date endTime = null;
					try {
						startTime = sdf.parse(record.getStartTime());
						endTime = sdf.parse(record.getEndTime());
					} catch (Exception e) {
						e.printStackTrace();
					}
					list.add(cb.between(root.get("applyDate").as(Date.class),startTime, endTime));
				}
				//从管理员中加入部门管理员的hql语句
				if(smsUserDao.findOne(record.getUserId()).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){
					list.add(cb.like(join.get("dept").as(String.class), "%"+smsUserDao.findOne(record.getUserId()).getDept()+"%"));
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
	 * 短信申购
	 * @param userId 企业id,packageId 套餐id
	 */
	public void smsApply(String userId,String packageId){
		//查询出最大申购编号
		String maxApplyCode = recordDao.findMaxApplyCode();
		//生成申购编号
		PrimaryGenerater generater = PrimaryGenerater.getInstance();
		String applyCode = generater.generaterNextNumber(maxApplyCode);
		SmsApplyRecord record = new SmsApplyRecord();
		record.setApplyCode(applyCode);
		record.setApplyDate(new Date());
		SmsUser smsUser = smsUserDao.findOne(userId);
		record.setSmsUser(smsUser);
		SmsPackage smsPackage = smsPackageDao.findOne(packageId);
		record.setSmsPackage(smsPackage);
		record.setConfirmStatus(ConstantClassField.SMS_APPLY_RECORD_NOT_CONFIRM);//未审核
		recordDao.save(record);
	}
	
	/**
	 * 短信申购审批
	 */
	public void smsApplyConfirm(String recordId){
		SmsApplyRecord record = recordDao.findOne(recordId);
		record.setConfirmTime(new Date());
		record.setConfirmStatus(ConstantClassField.SMS_APPLY_RECORD_CONFIRMED);//已审核
		//管理员的短信总量中扣除申购的短信量
		List<SmsUser> manager = smsUserDao.findByType(ConstantClassField.SMS_USER_TYPE_MANAGER);
		for(SmsUser m : manager){
			m.setMsgAmount(m.getMsgAmount()-record.getSmsPackage().getMsgAmount());
			smsUserDao.save(m);
		}
		//企业用户的短信总量中增加申购的短信量
		String userId = record.getSmsUser().getId();
		SmsUser customer = smsUserDao.findOne(userId);
		customer.setMsgAmount(customer.getMsgAmount()+record.getSmsPackage().getMsgAmount());
		smsUserDao.save(customer);
		recordDao.save(record);
	}
	
	/**
	 * 获取记录总数
	 * @return
	 */
	public int getCount(){
		return (int)recordDao.count();
	}
	
	/**
	 * 根据用户id查询申购记录总数
	 * @param userId
	 * @return
	 */
	public int getCountByUserId(String userId){
		return recordDao.getCountByUserId(userId);
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
	public int getCountBySmsUserDept(SmsApplyRecordBean record){
		String dept = smsUserDao.findOne(record.getUserId()).getDept();
		List list = smsUserDao.findByDept(dept);
		return list.size();
	}
	
	/**
	 * 删除当前这条申购记录
	 * */
	public void deleteApprove(String recordId){
		SmsApplyRecord record = recordDao.findOne(recordId);
		recordDao.delete(recordId);
	}
}
