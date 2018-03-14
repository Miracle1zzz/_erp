package com.ahpu.erp.action.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.domain.Order;
import com.ahpu.erp.domain.OrderDetail;
import com.ahpu.erp.domain.Staff;
import com.ahpu.erp.domain.Store;
import com.ahpu.erp.service.impl.EmpServiceImpl;
import com.ahpu.erp.service.service.IEmpService;
import com.ahpu.erp.service.service.IOrderDetailService;
import com.ahpu.erp.service.service.IOrderService;
import com.ahpu.erp.service.service.IStaffService;
import com.ahpu.erp.service.service.IStoreService;
import com.ahpu.erp.utils.EmpUtils;
import com.ahpu.erp.utils.FileUtils;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class OrderAction extends BaseAction<Order> {

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderDetailService detailService;
	@Autowired
	private IStaffService staffService;
	@Autowired
	private IStoreService storeService;
	@Autowired
	private IOrderDetailService orderDetailService;

	/**
	 * @Title: pageQuery
	 * @Description: 分页查询
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月25日 下午5:43:16
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		dc.add(Restrictions.eq("orderType", Order.ORDER_ORDERTYPE_OF_BUY));
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

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @Title: deleteBatch
	 * @Description: 删除选中的订单项
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月25日 下午5:57:21
	 */
	public String deleteBatch() {
		orderService.delete(ids);
		return LIST;
	}

	private Integer nums;
	private Double price;
	private String goodsId;

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getNums() {
		return nums;
	}

	public void setNums(Integer nums) {
		this.nums = nums;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @Title: add
	 * @Description: 保存采购订单信息及明细
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午3:34:06
	 */
	public String add() {

		orderService.save(model, goodsId, nums, price, EmpUtils.getLoginUser());
		return LIST;
	}

	private String orderId;

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @Title: buyDetail
	 * @Description: 根据order.id获取对应信息，加载到详情页面
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午3:36:21
	 */
	public String buyDetail() {
		// 根据order.id获取对应信息，加载到详情页面
		List<Order> orderList = orderService.findAll(orderId);
		ActionContext.getContext().put("orderList", orderList);
		List<OrderDetail> detailsList = detailService.findAllById(orderId);
		ActionContext.getContext().put("detailsList", detailsList);
		return "buyDetail";
	}

	// ----------------------------------------//
	// --------------采购订单审核-----------------//
	// ----------------------------------------//
	/**
	 * @Title: pageQueryByOrdertype
	 * @Description: 采购订单审核数据加载
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午6:49:58
	 */
	Integer[] orderTypes = new Integer[] { Order.ORDER_ORDERTYPE_OF_BUY, Order.ORDER_ORDERTYPE_OF_RETURN_BUY };

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

	/**
	 * @Title: buyCheckDetail
	 * @Description: 查询需要审核订单的明细
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午7:44:05
	 */
	public String buyCheckDetail() {
		// 根据order.id获取对应信息，加载到详情页面
		List<Order> orderList = orderService.findAll(orderId);
		ActionContext.getContext().put("orderList", orderList);
		List<OrderDetail> detailsList = detailService.findAllById(orderId);
		ActionContext.getContext().put("detailsList", detailsList);
		return "buyCheckDetail";
	}

	private String orderIds;

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	/**
	 * @Title: buyCheckPass
	 * @Description: 审核通过
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午8:20:24
	 */
	public String buyCheckPass() {
		orderService.buyCheckPass(orderIds, EmpUtils.getLoginUser());
		return "toBuyCheckList";
	}

	/**
	 * @Title: buyCheckNoPass
	 * @Description: 审核驳回
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午8:51:02
	 */
	public String buyCheckNoPass() {
		orderService.buyCheckNoPass(orderIds, EmpUtils.getLoginUser());
		return "toBuyCheckList";
	}

	/**
	 * @Title: exportXls
	 * @Description: 导出已结单订单明细
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年2月1日 下午12:48:49
	 * @throws IOException
	 */
	public String exportXls() throws IOException {
		// 获取所有订单数据、
		List<Order> orderList = orderService.findAll(Order.ORDER_TYPE_OF_BUY_COMPLETE);
		// 2.使用POI技术将数据写入excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("订单数据");
		// 3.创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("订单号");
		headRow.createCell(1).setCellValue("下单人");
		headRow.createCell(2).setCellValue("下单时间");
		headRow.createCell(3).setCellValue("审核人");
		headRow.createCell(4).setCellValue("审核时间");
		headRow.createCell(5).setCellValue("运输司机");
		headRow.createCell(6).setCellValue("订单类型");
		headRow.createCell(7).setCellValue("订单状态");
		headRow.createCell(8).setCellValue("订单总量");
		headRow.createCell(9).setCellValue("订单总价");
		headRow.createCell(10).setCellValue("供应商");
		headRow.createCell(11).setCellValue("所在仓库");
		// 遍历数据
		for (Order order : orderList) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(order.getOrderNum());
			dataRow.createCell(1).setCellValue(order.getCreater().getName());
			dataRow.createCell(2).setCellValue(order.getCreateTime());
			// 审核人
			dataRow.createCell(3).setCellValue(order.getChecker().getName());
			dataRow.createCell(4).setCellValue(order.getCheckTime());
			// 运输司机
			dataRow.createCell(5).setCellValue(order.getCompleter().getName());
			dataRow.createCell(6).setCellValue(order.getOrderTypeView());
			dataRow.createCell(7).setCellValue(order.getTypeView());
			dataRow.createCell(8).setCellValue(order.getTotalNum());
			dataRow.createCell(9).setCellValue(order.getTotalPriceView());
			dataRow.createCell(10).setCellValue(order.getSupplier().getName());
			dataRow.createCell(11).setCellValue(order.getStore().getName());
		}
		// 5.使用文件输出流进行文件下载
		String filename = "订单数据.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contentType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);

		workbook.write(out);
		return NONE;
	}

	// ----------------------------------------//
	// --------------采购订单运输指派--------------//
	// ----------------------------------------//
	private Integer[] taskTypes = new Integer[] { Order.ORDER_TYPE_OF_BUY_CHECK_PASS, Order.ORDER_TYPE_OF_BUY_BUYING, };

	/**
	 * @Title: pageQueryTask
	 * @Description: 分页查询方法
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月28日 上午10:31:05
	 */
	public String pageQueryTask() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		dc.add(Restrictions.in("type", taskTypes));
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
		this.java2Json(pageBean, new String[] { "checker", "completer", "orderDtail" });
		return NONE;
	}

	/**
	 * @Title: findListNotAssociation
	 * @Description: 查询当前未关联的司机
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 上午9:43:08
	 */
	public String findListNotAssociation() {
		List<Staff> list = staffService.findListNotAssociation();
		this.java2Json(list, new String[] {});
		return NONE;
	}

	/**
	 * @Title: findListHasAssociation
	 * @Description: 查询当前已关联的司机
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 上午9:44:21
	 */
	public String findListHasAssociation() {
		String id = model.getId();
		List<Staff> list = staffService.findListHasAssociation(id);
		this.java2Json(list, new String[] {});
		return NONE;
	}

	private String staffIds;

	public String getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(String staffIds) {
		this.staffIds = staffIds;
	}

	/**
	 * @Title: assignStaff
	 * @Description: 为订单关联司机
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 上午10:19:19
	 */
	public String assignStaff() {
		Staff staff = staffService.findById(staffIds);
		orderService.assginStaff(model.getId(), staff);
		return LIST;
	}

	// ----------------------------------------//
	// --------------查询当前任务-----------------//
	// ----------------------------------------//
	/**
	 * @Title: pageQueryTasks
	 * @Description: 查询当前人自己的任务
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月29日 上午9:24:15
	 */
	public String pageQueryTasks() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		dc.add(Restrictions.eq("type", Order.ORDER_TYPE_OF_BUY_BUYING));
		Integer orderType = model.getOrderType();
		if (orderType != null && orderType != -1) {
			dc.add(Restrictions.eq("orderType", orderType));
		}
		if (model.getSupplier() != null && model.getSupplier().getNeeds() != null
				&& model.getSupplier().getNeeds() != -1) {
			dc.createAlias("supplier", "s");
			dc.add(Restrictions.eq("s.needs", model.getSupplier().getNeeds()));
		}
		orderService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "checker", "orderDtail" });
		return NONE;
	}

	private String taskid;

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	/**
	 * @Title: assignTask
	 * @Description: 结单
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 下午2:29:53
	 */
	public String assignTask() {
		orderService.assignTask(taskid);
		return "assgin";
	}

	// ----------------------------------------//
	// --------------入库订单-----------------//
	// ----------------------------------------//
	/**
	 * @Title: pageQueryInStore
	 * @Description: 查询需要入库的订单
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 下午3:29:04
	 */
	public String pageQueryInStore() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		dc.add(Restrictions.eq("type", Order.ORDER_TYPE_OF_BUY_IN_STORE));
		String orderNum = model.getOrderNum();
		if (StringUtils.isNotBlank(orderNum)) {
			dc.add(Restrictions.eq("orderNum", orderNum));
		}
		if (model.getCompleter() != null && model.getCompleter().getName().trim() != null
				&& model.getCompleter().getName().trim().length() > 0) {
			dc.createAlias("completer", "c2");
			dc.add(Restrictions.like("c2.name", model.getCompleter().getName().trim()));
		}
		orderService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "checker", "orderDtail" });
		return NONE;
	}

	/**
	 * @Title: findListStoreNotAssociation
	 * @Description: 查询没有关联的仓库
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 下午3:29:22
	 */
	public String findListStoreNotAssociation() {
		List<Store> list = storeService.findListStoreNotAssociation();
		this.java2Json(list, new String[] {});
		return NONE;
	}

	/**
	 * @Title: findListStoreHasAssociation
	 * @Description: 查询已经关联的仓库
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 下午3:30:03
	 */
	public String findListStoreHasAssociation() {
		String id = model.getId();
		Order order = orderService.findById(id);
		if (order.getStore() != null) {
			String id2 = order.getStore().getId();
			List<Store> list = storeService.findListStoreHasAssociation(id2);
			this.java2Json(list, new String[] {});
		}
		return NONE;
	}

	private String storeIds;

	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}

	/**
	 * @Title: assignStore
	 * @Description: 分配仓库
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 下午5:15:34
	 */
	public String assignStore() {
		String id = model.getId();
		OrderDetail detail = orderDetailService.findAllByOrderId(id);
		Store store = storeService.findById(storeIds);
		orderService.assginStore(id, detail, store);
		return "assginStore";
	}
	
}
