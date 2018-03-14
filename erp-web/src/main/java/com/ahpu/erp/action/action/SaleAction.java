package com.ahpu.erp.action.action;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.domain.Order;
import com.ahpu.erp.domain.Supplier;
import com.ahpu.erp.service.service.IGoodsService;
import com.ahpu.erp.service.service.IOrderService;
import com.ahpu.erp.service.service.ISupplierService;
import com.ahpu.erp.utils.EmpUtils;

@Controller
@Scope("prototype")
public class SaleAction extends BaseAction<Order> {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private ISupplierService supplierService;

	private String supplierId;
	private String goodsId;
	private Integer nums;
	private Double price;
	// ----------------------------------------//
	// --------------SALES ORDER---------------//
	// ----------------------------------------//

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @Title: inputSalesOrder
	 * @Description: *将客户订单录入ERP系统*
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年2月3日 下午5:28:30
	 */
	// order表
	// id
	// 购买的商品（必须是仓库里面含有的商品）
	// 根据商品查询供应商
	// totalNum 总数量
	// totalPrice 总价格
	// orderType 2
	// creater 下单人当前登陆人（本是客户订单由当前录单人录入，现在省去客户下单流程）
	// createTime 当前系统时间
	// orderNum 生成
	// 关联客户---远程调用crm
	public String inputSalesOrder() {
		Goods goods = goodsService.findById(goodsId);
		Supplier supplier = supplierService.findById(supplierId);
		orderService.inputSalesOrder(supplier,
				goods,nums,price,EmpUtils.getLoginUser());
		return LIST;
	}
	/**
	* @Title: pageQuery
	* @Description: 分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月4日 下午12:35:40
	 */
	public String pageQuery(){
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		dc.add(Restrictions.eq("orderType", Order.ORDER_ORDERTYPE_OF_SALE));
		Integer type = model.getType();
		if (type != null && type != -1) {
			dc.add(Restrictions.eq("type", type));
		}
		if (model.getCreater() != null && model.getCreater().getName() != null
				&& model.getCreater().getName().trim().length() > 0) {
			dc.createAlias("creater", "c1");
			dc.add(Restrictions.like("c1.name", "%" + model.getCreater().getName().trim() + "%"));
		}
		Integer totalNum = model.getTotalNum();
		Integer totalNumMax = model.getTotalNumMax();
		if (totalNum != null) {
			dc.add(Restrictions.ge("totalNum", totalNum));
		}
		if (totalNumMax != null) {
			dc.add(Restrictions.le("totalNum", totalNumMax));
		}
		Date createTime = model.getCreateTime();
		Date createTimeLast = model.getCreateTimeLast();
		if (createTime != null) {
			dc.add(Restrictions.ge("createTime", createTime));
		}
		if (createTimeLast != null) {
			dc.add(Restrictions.le("createTime", createTimeLast));
		}

		Double totalPrice = model.getTotalPrice();
		Double totalPriceMax = model.getTotalPriceMax();

		if (totalPrice != null) {
			dc.add(Restrictions.ge("totalPrice", totalPrice));
		}
		if (totalPriceMax != null) {
			dc.add(Restrictions.le("totalPrice", totalPriceMax));
		}
		orderService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "checker", "orderDtail" });
		return NONE;
	}
	
	
	// ----------------------------------------//
	// --------------关联客户———-----------------//
	// ----------------------------------------//
	/**
	* @Title: findListNotAssociation
	* @Description: TODO
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月4日 下午1:04:44
	 */
	public String findListNotAssociation(){
		List<Order> orderList = orderService.findListNotAssociation();
		return NONE;
	}
	/**
	* @Title: findListHasAssociation
	* @Description: TODO
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月4日 下午1:04:49
	 */
	public String findListHasAssociation(String customer_id){
		return NONE;
	}
	
	// ----------------------------------------//
	// --------------销售订单审核-----------------//
	// ----------------------------------------//
	
	Integer[] orderTypes = new Integer[] { Order.ORDER_ORDERTYPE_OF_SALE, Order.ORDER_ORDERTYPE_OF_RETURN_SALE };
	
	/**
	* @Title: pageQueryByOrdertype
	* @Description: 销售订单审核数据加载
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月4日 下午12:56:00
	 */
	public String pageQueryByOrdertype() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		dc.add(Restrictions.in("orderType", orderTypes));
		Integer type = model.getType();
		if (type != null && type != -1) {
			dc.add(Restrictions.eq("type", type));
		}
		if (model.getCreater() != null && model.getCreater().getName() != null
				&& model.getCreater().getName().trim().length() > 0) {
			dc.createAlias("creater", "c1");
			dc.add(Restrictions.like("c1.name", "%" + model.getCreater().getName().trim() + "%"));
		}
		Integer totalNum = model.getTotalNum();
		Integer totalNumMax = model.getTotalNumMax();
		if (totalNum != null) {
			dc.add(Restrictions.ge("totalNum", totalNum));
		}
		if (totalNumMax != null) {
			dc.add(Restrictions.le("totalNum", totalNumMax));
		}
		Date createTime = model.getCreateTime();
		Date createTimeLast = model.getCreateTimeLast();
		if (createTime != null) {
			dc.add(Restrictions.ge("createTime", createTime));
		}
		if (createTimeLast != null) {
			dc.add(Restrictions.le("createTime", createTimeLast));
		}

		Double totalPrice = model.getTotalPrice();
		Double totalPriceMax = model.getTotalPriceMax();

		if (totalPrice != null) {
			dc.add(Restrictions.ge("totalPrice", totalPrice));
		}
		if (totalPriceMax != null) {
			dc.add(Restrictions.le("totalPrice", totalPriceMax));
		}
		orderService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "checker", "orderDtail" });
		return NONE;
	}
}
