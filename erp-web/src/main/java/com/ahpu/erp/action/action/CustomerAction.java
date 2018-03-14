package com.ahpu.erp.action.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.service.crm.Customer;
import com.ahpu.erp.service.crm.ICustomerService;

public class CustomerAction extends BaseAction<Customer>{

	@Autowired
	private ICustomerService proxy;
	//TODO
	/**
	* @Title: pageQuery
	* @Description: 查询所有客户信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月4日 下午4:44:40
	 */
	public String pageQuery(){
		List<Customer> list = proxy.findAll();
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
}
