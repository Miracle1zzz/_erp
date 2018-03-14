package com.ahpu.erp.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 权限实体
 */

public class Function implements java.io.Serializable {

	// Fields

	private String id;
	private Function parentFunction;//当前权限的上级权限
	private String name;
	private String code;
	private String description;
	private String page;
	private String generatemenu;//是否生成菜单，1：是 0：否
	private Integer zindex;
	private String deltag = "0";
	private String type = "0";//是否是系统菜单
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	private Set roles = new HashSet(0);//当前权限对应的多个角色
	private Set children = new HashSet(0);//当前权限的下级权限
	
	public Function(String functionId){
		this.id = functionId;
	}
	public String getpId(){
		if(parentFunction == null){
			return "0";
		}
		return parentFunction.getId();
	}
	
	public Function() {}

	public String getText(){
		return name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Function getParentFunction() {
		return parentFunction;
	}
	public void setParentFunction(Function parentFunction) {
		this.parentFunction = parentFunction;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getGeneratemenu() {
		return generatemenu;
	}
	public void setGeneratemenu(String generatemenu) {
		this.generatemenu = generatemenu;
	}
	public Integer getZindex() {
		return zindex;
	}
	public void setZindex(Integer zindex) {
		this.zindex = zindex;
	}
	public Set getRoles() {
		return roles;
	}
	public void setRoles(Set roles) {
		this.roles = roles;
	}
	public Set getChildren() {
		return children;
	}
	public void setChildren(Set children) {
		this.children = children;
	}
}