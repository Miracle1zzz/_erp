package com.ahpu.erp.action.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Function;
import com.ahpu.erp.service.service.IFunctionService;

/**
 * ProjectName：erp-web
 * ClassName：FunctionAction
 * Description：权限功能管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 下午6:26:40
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{

	@Autowired
	private IFunctionService functionService;
	
	/**
	* @Title: listajax
	* @Description: 父功能点
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午6:30:22
	 */
	public String listajax(){
		List<Function> list = functionService.findAll();
		this.java2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	/**
	* @Title: add
	* @Description: 保存权限
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午6:34:18
	 */
	public String add(){
		functionService.save(model);
		return LIST;
	}
	
	/**
	* @Title: pageQuery
	* @Description: 权限信息分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午6:35:51
	 */
	public String pageQuery(){
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	/**
	* @Title: findMenu
	* @Description: 查询菜单
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月21日 下午6:40:14
	 */
	public String findMenu(){
		List<Function> list = functionService.findMenu();
		this.java2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	/**
	* @Title: findMenuForSystem
	* @Description: 查询系统菜单
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月22日 下午7:05:28
	 */
	public String findMenuForSystem(){
		List<Function> list = functionService.findMenuForSystem();
		this.java2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	private String ids;//属性驱动，获取选中行id
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	* @Title: deleteBatch
	* @Description: 删除功能权限
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月22日 下午6:22:41
	 */
	public String deleteBatch(){
		functionService.delete(ids);
		return LIST;
	}
}
