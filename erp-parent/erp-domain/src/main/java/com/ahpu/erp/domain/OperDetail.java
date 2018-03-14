package com.ahpu.erp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperDetail {
	
	public static final Integer OPER_TYPE_OF_IN = 1;
	public static final Integer OPER_TYPE_OF_OUT = 2;
	
	public static final String OPER_TYPE_OF_IN_VIEW = "入库";
	public static final String OPER_TYPE_OF_OUT_VIEW = "出库";
	
	public static final Map<Integer, String> typeMap = new HashMap<Integer, String>();
	static{
		typeMap.put(OPER_TYPE_OF_IN, OPER_TYPE_OF_IN_VIEW);
		typeMap.put(OPER_TYPE_OF_OUT, OPER_TYPE_OF_OUT_VIEW);
	}
	
	private String id;
	
	private Integer num;
	private Integer maxNum;

	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	private Date operTime;
	private Date lastOperTime;
	public Date getLastOperTime() {
		return lastOperTime;
	}
	public void setLastOperTime(Date lastOperTime) {
		this.lastOperTime = lastOperTime;
	}
	private Integer type;
	
	private String typeView;
	private String deltag = "0";
	
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	private Emp emp;
	private Goods goods;
	private Store store;
	
	public String getOperTimeView() {
		if(operTime != null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(operTime);
			return format;
		}else{
			return "暂无数据";
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public Emp getEmp() {
		return emp;
	}
	public void setEmp(Emp emp) {
		this.emp = emp;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Store getStore() {
		return store;
	}
	public void setStore(Store store) {
		this.store = store;
	}
	public String getTypeView() {
		return typeView;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
		this.typeView = typeMap.get(type);
	}
}
