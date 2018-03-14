package com.ahpu.erp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IOrderDao;
import com.ahpu.erp.domain.Order;
import com.ahpu.erp.domain.OrderDetail;

/**
 * ProjectName：erp-dao
 * ClassName：OrderDaoImpl
 * Description：订单管理实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月25日 下午2:40:23
 */
@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements IOrderDao{

	/**
	 * Title: findAllById 
	 * Description: 根据id查询明细  
	 * @param orderId
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 下午5:34:33   
	 * @see com.ahpu.erp.dao.dao.IOrderDao#findAllById(java.lang.String)
	 */
	public List<Order> findAllById(String orderId) {
		String hql ="FROM Order WHERE id = ?";
		List<Order> list = (List<Order>) this.getHibernateTemplate().find(hql, orderId);
		return list;
	}

	/**
	 * Title: findAll 
	 * @param type
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午1:30:10   
	 * @see com.ahpu.erp.dao.dao.IOrderDao#findAll(java.lang.Integer)
	 */
	public List<Order> findAll(Integer type) {
		String hql ="FROM Order WHERE type = ?";
		List<Order> list = (List<Order>) this.getHibernateTemplate().find(hql, type);
		return list;
	}

	/**
	 * Title: findListNotAssociation 
	 * Description: 查询未关联客户的订单  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月4日 下午4:19:23   
	 * @see com.ahpu.erp.dao.dao.IOrderDao#findListNotAssociation()
	 */
	public List<Order> findListNotAssociation() {
		String hql = "FROM Order WHERE customer_id IS NULL and deltag ='0' and orderType =?";
		List<Order> list = (List<Order>) this.getHibernateTemplate().find(hql, Order.ORDER_ORDERTYPE_OF_SALE);
		return list;
	}
}
