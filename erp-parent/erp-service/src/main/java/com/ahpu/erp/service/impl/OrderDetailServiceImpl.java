package com.ahpu.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IOrderDetailDao;
import com.ahpu.erp.domain.OrderDetail;
import com.ahpu.erp.service.service.IOrderDetailService;

/**
 * ProjectName：erp-service
 * ClassName：OrderDetailServiceImpl
 * Description：订单项管理实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月26日 下午1:32:22
 */
@Service
@Transactional
public class OrderDetailServiceImpl implements IOrderDetailService{

	@Autowired
	private IOrderDetailDao detailDao;

	/**
	 * Title: findAllById 
	 * Description:根据订单id查询所有明细   
	 * @param orderId
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 下午5:48:40   
	 * @see com.ahpu.erp.service.service.IOrderDetailService#findAllById(java.lang.String)
	 */
	public List<OrderDetail> findAllById(String orderId) {
		
		return detailDao.findAllById(orderId);
	}

	/**
	 * Title: findAllByOrderId 
	 * Description:根据订单id查询所有明细      
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 上午9:30:20   
	 * @see com.ahpu.erp.service.service.IOrderDetailService#findAllByOrderId(java.lang.String)
	 */
	public OrderDetail findAllByOrderId(String id) {
		return detailDao.findByOrderId(id);
	}
	
}
