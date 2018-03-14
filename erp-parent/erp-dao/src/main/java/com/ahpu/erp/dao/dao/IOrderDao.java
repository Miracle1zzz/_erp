package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.domain.Order;
import com.ahpu.erp.domain.OrderDetail;

public interface IOrderDao extends IBaseDao<Order>{

	public List<Order> findAllById(String orderId);

	public List<Order> findAll(Integer type);

	public List<Order> findListNotAssociation();



}
