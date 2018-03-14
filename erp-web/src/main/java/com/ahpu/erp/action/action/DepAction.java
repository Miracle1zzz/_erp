package com.ahpu.erp.action.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Dep;
import com.ahpu.erp.service.service.IDepService;


/**
 * ProjectName：erp-web
 * ClassName：DepAction
 * Description：部门信息管理action
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 下午12:58:27
 */
@Controller
@Scope("prototype")
public class DepAction extends BaseAction<Dep>{

	@Autowired
	private IDepService depService;
	
	/**
	* @Title: pageQuery
	* @Description: 分页查询方法
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午1:07:46
	 */
	public String pageQuery(){
		//动态添加过滤条件
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String name = model.getName();
		if(StringUtils.isNotBlank(name)){
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		String telephone = model.getTelephone();
		if(StringUtils.isNotBlank(telephone)){
			dc.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		depService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize","emps"});
		return NONE;
	}
	/**
	* @Title: add
	* @Description: 添加部门信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午1:20:20
	 */
	@RequiresPermissions("dep-add")
	public String add(){
		depService.save(model);
		return LIST;
	}
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	* @Title: deleteBatch
	* @Description: 删除选中部门信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午1:21:14
	 */
	@RequiresPermissions("dep-delete")
	public String deleteBatch(){
		depService.delete(ids);
		return LIST;
	}
	/**
	* @Title: edit
	* @Description: 编辑部门信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午3:03:51
	 */
	@RequiresPermissions("dep-edit")
	public String edit(){
		//根据id查询原始数据
		Dep dep = depService.findById(model.getId());
		//使用页面提交数据进行覆盖
		dep.setName(model.getName());
		dep.setTelephone(model.getTelephone());
		depService.update(dep);
		return LIST;
	}
	/**
	* @Title: listAjax
	* @Description: ajax查询部门信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午3:08:04
	 */
	public String listAjax(){
		List<Dep> list = depService.findListAll();
		this.java2Json(list, new String[]{});
		return NONE;
	}
}
