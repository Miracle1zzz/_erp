package com.ahpu.erp.action.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Store;
import com.ahpu.erp.service.service.IStoreService;

/**
 * ProjectName：erp-web
 * ClassName：StoreAction
 * Description：仓库信息管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月31日 下午12:39:22
 */
@Controller
@Scope("prototype")
public class StoreAction extends BaseAction<Store>{

	@Autowired
	private IStoreService storeService;
	
	/**
	* @Title: add
	* @Description: 仓库信息保存
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月31日 下午12:39:01
	 */
	public String add(){
		storeService.save(model);
		return LIST;
	}
	/**
	* @Title: edit
	* @Description: 仓库信息修改
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月31日 下午12:42:15
	 */
	public String edit(){
		Store store = storeService.findById(model.getId());
		store.setName(model.getName());
		store.setAddress(model.getAddress());
		store.setEmp(model.getEmp());
		storeService.update(store);
		return LIST;
	}
	/**
	* @Title: pageQuery
	* @Description: 仓库信息分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月31日 下午12:42:31
	 */
	public String pageQuery(){
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String name = model.getName();
		if(StringUtils.isNotBlank(name)){
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(model.getEmp() != null && model.getEmp().getName() != null
				&& model.getEmp().getName().trim().length() > 0) {
			dc.createAlias("emp", "e");
			dc.add(Restrictions.like("e.name", "%" + model.getEmp().getName().trim() + "%"));
		}
		storeService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{});
		return NONE;
	}
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	* @Title: deleteBatch
	* @Description: 删除选中的仓库信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月31日 下午12:55:06
	 */
	public String deleteBatch(){
		storeService.delete(ids);
		return LIST;
	}
	/**
	* @Title: listajax
	* @Description: ajax异步获取仓库信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月1日 上午11:49:36
	 */
	public String listajax(){
		List<Store> list = storeService.findAll();
		this.java2Json(list, new String[]{});
		return NONE;
	}
}
