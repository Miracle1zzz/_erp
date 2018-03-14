package com.ahpu.erp.action.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Role;
import com.ahpu.erp.service.service.IRoleService;

/**
 * ProjectName：erp-web
 * ClassName：RoleAction
 * Description：角色管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 下午6:26:58
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

	@Autowired
	private IRoleService roleService;
	
	//属性驱动，接收权限的id
	private String functionIds;

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
	
	/**
	* @Title: add
	* @Description: 添加角色
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午6:47:32
	 */
	public String add(){
		roleService.save(model,functionIds);
		return LIST;
	}
	/**
	* @Title: pageQuery
	* @Description: 分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午6:49:10
	 */
	public String pageQuery(){
		roleService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"functions","users"});
		return NONE;
	}
	/**
	* @Title: listajax
	* @Description: ajax请求查询所有角色
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午6:52:01
	 */
	public String listajax(){
		List<Role> list = roleService.findAll();
		this.java2Json(list, new String[]{"functions","users"});
		return NONE;
	}
}
