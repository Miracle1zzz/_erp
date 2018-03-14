package com.ahpu.erp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Emp entity. @author MyEclipse Persistence Tools
 */

public class Emp implements java.io.Serializable {
	
	public static final String EMP_LOGIN_USER_OBJECT_NAME = "loginEm";

	// Fields

	private String id;
	private Dep dep;
	private String username;
	private String password;
	private String name;
	private String email;
	private String telephone;
	private String address;
	private Integer gender;
	private Date birthday;
	private Date lastbirthday;
	public Date getLastbirthday() {
		return lastbirthday;
	}
	public void setLastbirthday(Date lastbirthday) {
		this.lastbirthday = lastbirthday;
	}

	private String deltag = "0";
	private Set<Role> roles = new HashSet(0);

	// Constructors

	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getRoleNames(){
		String roleNames = "";
		for(Role role : roles){
			String name = role.getName();
			roleNames += name + " ";
		}
		return roleNames;
	}
	
	public String getBirthdayView(){
		if(birthday != null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(birthday);
			return format;
		}else{
			return "暂无数据";
		}
	}
	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	/** default constructor */
	public Emp() {
	}

	/** minimal constructor */
	public Emp(String id, Dep dep, String username, String password, String name, String email, String telephone,
			String address, Integer gender, Date birthday,String deltag) {
		this.id = id;
		this.dep = dep;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.telephone = telephone;
		this.address = address;
		this.gender = gender;
		this.birthday = birthday;
		this.deltag = deltag;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Dep getDep() {
		return this.dep;
	}

	public void setDep(Dep dep) {
		this.dep = dep;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

}