package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.OrderDetail;

public interface IOrderDetailDao extends IBaseDao<OrderDetail>{

	public List<OrderDetail> findAllById(String orderId);

	public OrderDetail findByOrderId(String id);

}
