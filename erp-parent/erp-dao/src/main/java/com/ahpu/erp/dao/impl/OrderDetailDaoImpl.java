package com.ahpu.erp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IOrderDetailDao;
import com.ahpu.erp.domain.OrderDetail;

/**
 * ProjectName：erp-dao
 * ClassName：OrderDetailDaoImpl
 * Description：订单项管理实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月26日 下午1:30:52
 */
@Repository
public class OrderDetailDaoImpl extends BaseDaoImpl<OrderDetail> implements IOrderDetailDao{

	/**
	 * Title: findAllById 
	 * Description: 根据订单id查询明细  
	 * @param orderId
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 下午5:50:56   
	 * @see com.ahpu.erp.dao.dao.IOrderDetailDao#findAllById(java.lang.String)
	 */
	public List<OrderDetail> findAllById(String orderId) {
		String hql ="FROM OrderDetail WHERE order.id = ?";
		List<OrderDetail> list = (List<OrderDetail>) this.getHibernateTemplate().find(hql, orderId);
		return list;
	}

	/**
	 * Title: findByOrderId 
	 * Description:根据订单id查询明细     
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 上午9:29:11   
	 * @see com.ahpu.erp.dao.dao.IOrderDetailDao#findByOrderId(java.lang.String)
	 */
	public OrderDetail findByOrderId(String id) {
		String hql ="FROM OrderDetail WHERE order.id = ?";
		List<OrderDetail> list = (List<OrderDetail>) this.getHibernateTemplate().find(hql, id);
		return list.size() > 0 ? list.get(0) : null;
	}

}
