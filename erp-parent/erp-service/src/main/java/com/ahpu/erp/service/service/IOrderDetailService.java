package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.OrderDetail;

public interface IOrderDetailService {

	public List<OrderDetail> findAllById(String orderId);

	public OrderDetail findAllByOrderId(String id);

}
