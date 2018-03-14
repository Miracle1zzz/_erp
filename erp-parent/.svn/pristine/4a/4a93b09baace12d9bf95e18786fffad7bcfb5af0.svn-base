package com.ahpu.erp.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.ahpu.erp.domain.Emp;

public class EmpUtils {

	//获取session对象
	public static HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	//获取登录用户对象
	public static Emp getLoginUser(){
		return (Emp) getSession().getAttribute(Emp.EMP_LOGIN_USER_OBJECT_NAME);
	}
}
