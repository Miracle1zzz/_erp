package com.ahpu.erp.interceptor;

import com.ahpu.erp.utils.AppException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor{

	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (AppException e) {
			//记录日志
			//发送日志到程序员邮箱
			ActionSupport as = (ActionSupport) invocation.getAction();
			as.addActionError(e.getMessage());
			return "error";
		} catch (Exception e) {
			ActionSupport as = (ActionSupport) invocation.getAction();
			as.addActionError("对不起服务器已关闭,请联系管理员！");
			e.printStackTrace();
			return invocation.invoke();
		}
	}

}
