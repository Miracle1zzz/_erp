package com.ahpu.erp.domain;

import java.util.HashMap;
import java.util.Map;

public class Supplier {
	
	public static final Integer SUPPLIER_NEEDS_IS_YES = 1;
	public static final Integer SUPPLIER_NEEDS_IS_NO = 0;
	
	public static final String SUPPLIER_NEEDS_IS_YES_VIEW="自提";
	public static final String SUPPLIER_NEEDS_IS_NO_VIEW="送货";
	
	public static final Map<Integer, String> needsMap = new HashMap<Integer, String>();
	static{
		needsMap.put(SUPPLIER_NEEDS_IS_YES,SUPPLIER_NEEDS_IS_YES_VIEW);
		needsMap.put(SUPPLIER_NEEDS_IS_NO,SUPPLIER_NEEDS_IS_NO_VIEW );
	}
	private String id;
	private String name;
	private String address;
	private String contact;
	private String telephone;
	private Integer needs;
	private String shortcode;
	private String needsView;
	
	public String getNeedsView() {
		return needsView;
	}
	public String getShortcode() {
		return shortcode;
	}
	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
	public void setNeeds(Integer needs) {
		this.needs = needs;
		this.needsView = needsMap.get(needs);
	}
	public String deltag = "0";
	
	
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTelephone() {
		return telephone;
	}
	public Integer getNeeds() {
		return needs;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
