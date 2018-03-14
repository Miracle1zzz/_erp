package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.domain.Order;
import com.ahpu.erp.domain.OrderDetail;
import com.ahpu.erp.domain.Staff;
import com.ahpu.erp.domain.Store;
import com.ahpu.erp.domain.Supplier;
import com.ahpu.erp.utils.PageBean;

public interface IOrderService {

	public void pageQuery(PageBean pageBean);

	public void delete(String ids);

	public void save(Order model, String goodsId, Integer nums, Double price, Emp loginUser);

	public List<Order> findAll(String orderId);

	public void buyCheckPass(String id, Emp loginUser);

	public void buyCheckNoPass(String orderIds, Emp loginUser);

	public void assginStaff(String id, Staff staff);

	public void assignTask(String taskid);

	public Order findById(String id);

	public void assginStore(String id, OrderDetail detail, Store store);

	public List<Order> findAll(Integer type);

	public void inputSalesOrder(Supplier supplier, Goods goods, Integer nums, Double price, Emp loginUser);

	public List<Order> findListNotAssociation();


}
