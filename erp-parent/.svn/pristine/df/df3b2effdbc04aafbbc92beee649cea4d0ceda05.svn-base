package com.ahpu.erp.service.realm;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.ahpu.erp.dao.dao.IEmpDao;
import com.ahpu.erp.dao.dao.IFunctionDao;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.domain.Function;
import com.ahpu.erp.service.service.IEmpService;

public class ErpRealm extends AuthorizingRealm {

	@Autowired
	private IEmpDao empDao;
	@Autowired
	private IFunctionDao functionDao;

	/**
	 * Title: doGetAuthenticationInfo Description:认证方法
	 * 
	 * @param arg0
	 * @return
	 * @throws AuthenticationException
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午6:55:06
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
		// 获取页面输入的用户名
		String username = passwordToken.getUsername();
		// 根据用户名查询数据库中的密码
		Emp emp = empDao.findUserByUsername(username);
		if (emp == null) {
			// 页面输入的用户名不存在
			return null;
		}
		AuthenticationInfo info = new SimpleAuthenticationInfo(emp, emp.getPassword(), this.getName());
		return info;
	}

	/**
	 * Title: doGetAuthorizationInfo Description:授权方法
	 * 
	 * @param arg0
	 * @return
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午6:55:25
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为登录用户授权
		//根据当前登录用户查询数据库，获取实际对应的权限
		//获取当前登录用户对象
		Emp emp = (Emp) SecurityUtils.getSubject().getPrincipal();
		List<Function> list = null;
		if(emp.getUsername().equals("admin")){
			//内置管理员，直接查询所有权限数据
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Function.class);
			detachedCriteria.add(Restrictions.eq("deltag", "0"));
			list = functionDao.findByCriteria(detachedCriteria);
		}else{
			list = functionDao.findFunctionListByUserId(emp.getId());
		}
		for(Function function : list ){
			info.addStringPermission(function.getCode());
		}
		return info;
	}

}
