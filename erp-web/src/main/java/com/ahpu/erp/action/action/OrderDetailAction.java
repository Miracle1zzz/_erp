package com.ahpu.erp.action.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.OrderDetail;
import com.ahpu.erp.service.service.IOrderDetailService;

/**
 * ProjectName：erp-web
 * ClassName：OrderDetailAction
 * Description：订单项管理实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月26日 下午1:33:39
 */
@Controller
@Scope("prototype")
public class OrderDetailAction extends BaseAction<OrderDetail>{

	@Autowired
	private IOrderDetailService detailService;
}
