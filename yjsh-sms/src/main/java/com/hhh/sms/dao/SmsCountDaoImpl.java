package com.hhh.sms.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.hhh.sms.common.ConstantClassField;
import com.hhh.sms.dao.entity.SmsConsumption;
import com.hhh.sms.web.model.SmsCountBean;

@Repository
public class SmsCountDaoImpl implements SmsCountDao {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SmsUserDao userDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsCountBean> findSmsCount(String userId,String customerId,String customerName,Pageable pageable){
		StringBuffer sql=new StringBuffer("SELECT su.id userId,su.customer_id customerId,su.customer_name customerName,COUNT(sc.user_id) sendedAmount,su.msg_amount msgAmount "
				+ " FROM sms_user su "
				+ " LEFT JOIN sms_consumption sc "
				+ " on su.id = sc.user_id AND sc.`status`=1"
				+ " WHERE 1=1 AND su.type=0 ");
		if(userDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_CUSTOMER){//如果是企业用户就只查看自己的短信统计记录
			sql.append(" and su.id ='"+userId+"'");
		}
		if(userDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){//如果是部门管理员就只查看部门的短信统计记录
			String dept = userDao.findOne(userId).getDept();
			sql.append(" and su.dept ='"+dept+"'");
		}
		if(customerId!=null&&StringUtils.isNotBlank(customerId)){
			sql.append(" and su.customer_id like '%" + customerId + "%'");
		}
		if(customerName!=null&&StringUtils.isNotBlank(customerName)){
			sql.append(" and su.customer_name like '%" + customerName + "%'");
		}
		sql.append(" GROUP BY su.id");
		Query query = entityManager.createNativeQuery(sql.toString());
		if(pageable!=null){
			int pageSize = pageable.getPageSize();
			int start = pageable.getPageNumber()*pageSize;
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		List resultList = query.getResultList();
		List<SmsCountBean> list = new ArrayList<SmsCountBean>();
		SmsCountBean countBean = null;
		for(int i=0;i<resultList.size();i++) {   
						countBean = new SmsCountBean();
			            Object[] obj = (Object[]) resultList.get(i);   
			             //使用obj[0],obj[1],obj[2]取出属性
			            countBean.setUserId(obj[0].toString());
			            countBean.setCustomerId(obj[1].toString());
			            countBean.setCustomerName(obj[2].toString());
			            countBean.setSendedAmount(((BigInteger)obj[3]).intValue());
			            countBean.setMsgAmount((Integer)obj[4]);
			            list.add(countBean);
			       } 
		return list;
	}

	@Override
	public int getCount(String userId, String customerId, String customerName) {
		StringBuffer sql=new StringBuffer("SELECT su.id userId,su.customer_id customerId,su.customer_name customerName,COUNT(sc.user_id) sendedAmount,su.msg_amount msgAmount "
				+ " FROM sms_user su "
				+ " LEFT JOIN sms_consumption sc "
				+ " on su.id = sc.user_id AND sc.`status`=1"
				+ " WHERE 1=1 ");
		if(userDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_CUSTOMER){//如果是企业用户就只查看自己的短信统计记录
			sql.append(" and su.id ='"+userId);
		}
		if(userDao.findOne(userId).getType()==ConstantClassField.SMS_USER_TYPE_DEPTMANAGER){//如果是部门管理员就只查看部门的短信统计记录
			String dept = userDao.findOne(userId).getDept();
			sql.append(" and su.dept ='"+dept+"'");
		}
		if(customerId!=null&&StringUtils.isNotBlank(customerId)){
			sql.append(" and su.customer_id like '%" + customerId + "%'");
		}
		if(customerName!=null&&StringUtils.isNotBlank(customerName)){
			sql.append(" and su.customer_name like '%" + customerName + "%'");
		}
		sql.append(" GROUP BY su.id");
		Query query = entityManager.createNativeQuery(sql.toString());
		List resultList = query.getResultList();
		return resultList.size();//总记录数
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsCountBean> findSmsCountByDept(Pageable pageable){
		StringBuffer sql=new StringBuffer("select DISTINCT sum(t1.msg_amount) as msgCount,IFNULL(t2.sendCount,0) as sendCount,t1.dept "
				+ " from sms_user t1 LEFT JOIN("
				+ " select su.dept as dept,count(1) as sendCount from sms_user su "
				+ " inner join sms_consumption sc on su.id=sc.user_id "
				+ "where sc.status=1 GROUP BY su.dept) t2 on t1.dept=t2.dept GROUP BY t1.dept");
		Query query = entityManager.createNativeQuery(sql.toString());
		if(pageable!=null){
			int pageSize = pageable.getPageSize();
			int start = pageable.getPageNumber()*pageSize;
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		List resultList = query.getResultList();
		List<SmsCountBean> list = new ArrayList<SmsCountBean>();
		SmsCountBean countBean = null;
		for(int i=0;i<resultList.size();i++) {   
						countBean = new SmsCountBean();
			            Object[] obj = (Object[]) resultList.get(i);   
			             //使用obj[0],obj[1],obj[2]取出属性
			            if(obj[2] != null){
			            	countBean.setCustomerName(obj[2].toString());
			            }else{
			            	countBean.setCustomerName("");
			            }
			            countBean.setSendedAmount(Integer.parseInt(obj[1].toString()));
			            countBean.setMsgAmount(Integer.parseInt(obj[0].toString()));
			            list.add(countBean);
			       } 
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsCountBean> findCountByDept(String dept,Pageable pageable){
		StringBuffer sql=new StringBuffer("select DISTINCT sum(t1.msg_amount) as msgCount,IFNULL(t2.sendCount,0) as sendCount,t1.dept "
				+ "from sms_user t1 LEFT JOIN("
				+ "select su.dept as dept,count(1) as sendCount from sms_user su "
				+ "inner join sms_consumption sc on su.id=sc.user_id "
				+ "where sc.status=1 GROUP BY su.dept) t2 on t1.dept=t2.dept GROUP BY t1.dept having dept=:dept");
		Query query = entityManager.createNativeQuery(sql.toString());
		query.setParameter("dept", dept);
		if(pageable!=null){
			int pageSize = pageable.getPageSize();
			int start = pageable.getPageNumber()*pageSize;
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		List resultList = query.getResultList();
		List<SmsCountBean> list = new ArrayList<SmsCountBean>();
		SmsCountBean countBean = null;
		for(int i=0;i<resultList.size();i++) {   
						countBean = new SmsCountBean();
			            Object[] obj = (Object[]) resultList.get(i);   
			             //使用obj[0],obj[1],obj[2]取出属性
			            if(obj[2] != null){
			            	countBean.setCustomerName(obj[2].toString());
			            }else{
			            	countBean.setCustomerName("");
			            }
			            countBean.setSendedAmount(Integer.parseInt(obj[1].toString()));
			            countBean.setMsgAmount(Integer.parseInt(obj[0].toString()));
			            list.add(countBean);
			       } 
		return list;
	}
	public List<SmsCountBean> findAllCount(){
		StringBuffer sql=new StringBuffer("select DISTINCT sum(t1.msg_amount) as msgCount,IFNULL(t2.sendCount,0) as sendCount,t1.dept "
				+ "from sms_user t1 LEFT JOIN("
				+ "select su.dept as dept,count(1) as sendCount from sms_user su "
				+ "inner join sms_consumption sc on su.id=sc.user_id "
				+ "where sc.status=1 GROUP BY su.dept) t2 on t1.dept=t2.dept GROUP BY t1.dept ");
		Query query = entityManager.createNativeQuery(sql.toString());
		List resultList = query.getResultList();
		return resultList;
	}
}
