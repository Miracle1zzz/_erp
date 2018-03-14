package com.ahpu.erp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ahpu.erp.util.FormatUtil;

public class Order implements java.io.Serializable{
	
	public static final Integer ORDER_ORDERTYPE_OF_BUY = 1;
	public static final Integer ORDER_ORDERTYPE_OF_SALE = 2;
	public static final Integer ORDER_ORDERTYPE_OF_RETURN_BUY = 3;
	public static final Integer ORDER_ORDERTYPE_OF_RETURN_SALE = 4;
	
	public static final String ORDER_ORDERTYPE_OF_BUY_VIEW = "采购";
	public static final String ORDER_ORDERTYPE_OF_SALE_VIEW = "销售";
	public static final String ORDER_ORDERTYPE_OF_RETURN_BUY_VIEW = "采购退货";
	public static final String ORDER_ORDERTYPE_OF_RETURN_SALE_VIEW = "销售退货";
	
	public static final Integer ORDER_TYPE_OF_BUY_NO_CHECK = 111;
	public static final Integer ORDER_TYPE_OF_BUY_CHECK_PASS = 121;
	public static final Integer ORDER_TYPE_OF_BUY_CHECK_NO_PASS = 120;
	public static final Integer ORDER_TYPE_OF_BUY_BUYING = 131;
	public static final Integer ORDER_TYPE_OF_BUY_IN_STORE = 141;
	public static final Integer ORDER_TYPE_OF_BUY_COMPLETE = 199;
	
	public static final String ORDER_TYPE_OF_BUY_NO_CHECK_VIEW = "未审核";
	public static final String ORDER_TYPE_OF_BUY_CHECK_PASS_VIEW = "通过";
	public static final String ORDER_TYPE_OF_BUY_CHECK_NO_PASS_VIEW = "驳回";
	public static final String ORDER_TYPE_OF_BUY_BUYING_VIEW = "采购中";
	public static final String ORDER_TYPE_OF_BUY_IN_STORE_VIEW = "入库中";
	public static final String ORDER_TYPE_OF_BUY_COMPLETE_VIEW = "结单";
	
	public static final Integer ORDER_TYPE_OF_SALE_NO_CHECK = 211;
	public static final Integer ORDER_TYPE_OF_SALE_CHECK_PASS = 221;
	
	public static final String ORDER_TYPE_OF_SALE_NO_CHECK_VIEW = "未审核";
	public static final String ORDER_TYPE_OF_SALE_CHECK_PASS_VIEW = "通过";

	public static final Map<Integer, String> orderTypeMap = new HashMap<Integer, String>();
	
	public static final Map<Integer, String> buyTypeMap = new TreeMap<Integer, String>();
	public static final Map<Integer, String> saleTypeMap = new TreeMap<Integer, String>();
	
	private static final Map<Integer, String> typeMap = new HashMap<Integer, String>();
	static{
		orderTypeMap.put(ORDER_ORDERTYPE_OF_BUY, ORDER_ORDERTYPE_OF_BUY_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_SALE, ORDER_ORDERTYPE_OF_SALE_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_RETURN_BUY, ORDER_ORDERTYPE_OF_RETURN_BUY_VIEW);
		orderTypeMap.put(ORDER_ORDERTYPE_OF_RETURN_SALE, ORDER_ORDERTYPE_OF_RETURN_SALE_VIEW);
	

		buyTypeMap.put(ORDER_TYPE_OF_BUY_NO_CHECK, ORDER_TYPE_OF_BUY_NO_CHECK_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_CHECK_PASS, ORDER_TYPE_OF_BUY_CHECK_PASS_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_CHECK_NO_PASS, ORDER_TYPE_OF_BUY_CHECK_NO_PASS_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_BUYING, ORDER_TYPE_OF_BUY_BUYING_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_IN_STORE, ORDER_TYPE_OF_BUY_IN_STORE_VIEW);
		buyTypeMap.put(ORDER_TYPE_OF_BUY_COMPLETE, ORDER_TYPE_OF_BUY_COMPLETE_VIEW);
		
		saleTypeMap.put(ORDER_TYPE_OF_SALE_NO_CHECK, ORDER_TYPE_OF_SALE_NO_CHECK_VIEW);
		saleTypeMap.put(ORDER_TYPE_OF_SALE_CHECK_PASS, ORDER_TYPE_OF_SALE_CHECK_PASS_VIEW);
		
		typeMap.putAll(buyTypeMap);
		typeMap.putAll(saleTypeMap);
	}
	private String id;
	
	private String orderNum;
	private Integer totalNum;
	private Integer totalNumMax;

	private Date createTime;
	private Date createTimeLast;
	private Date checkTime;
	private Date endTime;
	private Integer orderType;
	private String orderTypeView;
	public String getOrderTypeView() {
		return orderTypeView;
	}
	private Integer type;
	private String typeView;
	public String getTypeView() {
		return typeView;
	}
	private String deltag = "0";
	private Double totalPrice;
	private Double totalPriceMax;
	private String customer_id;
	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCreateTimeView(){
		if(createTime != null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(createTime);
			return format;
		}else{
			return "暂无数据";
		}
	}
	
	public Integer getTotalNumMax() {
		return totalNumMax;
	}

	public Staff getCompleter() {
		return completer;
	}

	public void setCompleter(Staff completer) {
		this.completer = completer;
	}

	public void setTotalNumMax(Integer totalNumMax) {
		this.totalNumMax = totalNumMax;
	}
	public Date getCreateTimeLast() {
		return createTimeLast;
	}
	public void setCreateTimeLast(Date createTimeLast) {
		this.createTimeLast = createTimeLast;
	}
	public Double getTotalPriceMax() {
		return totalPriceMax;
	}
	public void setTotalPriceMax(Double totalPriceMax) {
		this.totalPriceMax = totalPriceMax;
	}
	private String totalPriceView;
	
	private Staff completer;
	private Emp creater;
	private Emp checker;
	private Supplier supplier;
	private Store store;
	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	//对订单明细一对多
	private Set<OrderDetail> orderDtail;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
		this.orderTypeView = orderTypeMap.get(orderType);
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
		this.typeView = typeMap.get(type);
	}
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
		this.totalPriceView = FormatUtil.formatMoney(totalPrice);
	}
	public Emp getCreater() {
		return creater;
	}
	public void setCreater(Emp creater) {
		this.creater = creater;
	}
	public Emp getChecker() {
		return checker;
	}
	public void setChecker(Emp checker) {
		this.checker = checker;
	}

	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Set<OrderDetail> getOrderDtail() {
		return orderDtail;
	}
	public void setOrderDtail(Set<OrderDetail> orderDtail) {
		this.orderDtail = orderDtail;
	}
	public String getTotalPriceView() {
		return totalPriceView;
	}
	
}
















