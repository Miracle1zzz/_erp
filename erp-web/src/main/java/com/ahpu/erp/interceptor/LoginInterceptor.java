package com.ahpu.erp.interceptor;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.utils.EmpUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * ProjectName：bos-web
 * ClassName：LoginInterceptor
 * Description：自定义登录拦截器
 * @author：Miracle 
 * Create Date：2017年12月21日 下午3:23:48
 */
public class LoginInterceptor extends MethodFilterInterceptor{

	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//从session获取用户对象
		Emp emp = EmpUtils.getLoginUser();
		if(emp == null){
			//没有登录，跳转到登录页面
			return "login";
		}
		return invocation.invoke();
	}

}
