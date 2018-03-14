package com.ahpu.erp.domain;

import java.util.HashMap;
import java.util.Map;

public class Store {
	
	public static final Integer STORE_STATE_OF_YES = 1;
	public static final Integer STORE_STATE_OF_NO = 2;
	public static final Integer STORE_STATE_OF_WARNING = 3;
	
	public static final String STORE_STATE_OF_YES_VIEW = "正常使用";
	public static final String STORE_STATE_OF_NO_VIEW = "停用";
	public static final String STORE_STATE_OF_WARNING_VIEW = "货物已满";
	
	public static final Map<Integer, String> storeStateMap = new HashMap<Integer, String>();
	
	static{
		storeStateMap.put(STORE_STATE_OF_YES, STORE_STATE_OF_YES_VIEW);
		storeStateMap.put(STORE_STATE_OF_NO, STORE_STATE_OF_NO_VIEW);
		storeStateMap.put(STORE_STATE_OF_WARNING, STORE_STATE_OF_WARNING_VIEW);
	}
	
	private String id;
	private String name;
	private String address;
	private String deltag ="0";
	private Integer state = 1;
	private String stateView;
	
	public String getStateView() {
		return stateView;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
		this.stateView = storeStateMap.get(state);
	}

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	private Emp emp;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	
}
