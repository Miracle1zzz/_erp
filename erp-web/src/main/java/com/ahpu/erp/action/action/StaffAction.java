package com.ahpu.erp.action.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Staff;
import com.ahpu.erp.service.service.IStaffService;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{

	@Autowired
	private IStaffService staffService;
	
	/**
	* @Title: add
	* @Description:  添加取派员
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月27日 上午9:14:17
	 */
	public String add(){
		staffService.save(model);
		return LIST;
	}
	
	/**
	* @Title: pageQuery
	* @Description: 分页查询方法
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月27日 上午9:14:35
	 */
	public String pageQuery() throws IOException{
		staffService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	private String ids;

	/**
	* @Title: deleteBatch
	* @Description: 删除取派员
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月27日 上午9:15:01
	 */
	//@RequiresPermissions("staff-delete")
	public String deleteBatch(){
		
		staffService.delete(ids);
		return LIST;
	}
	/**
	* @Title: edit
	* @Description: 修改取派员
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月27日 上午9:15:17
	 */
	public String edit(){
		//根据id查询原始数据
		Staff staff = staffService.findById(model.getId());
		//使用页面提交的数据进行覆盖
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return LIST;
	}
	/**
	* @Title: listAjax
	* @Description: 查询所有未删除的取派员数据
	* @return String
	* @Company：AHPU
	* @author：Miracle 
	* @version 1.0.1
	* Create Date：2018年1月13日 下午3:10:04
	 */
	public String listAjax(){
		List<Staff> list = staffService.findListNotDelete();
		this.java2Json(list, new String[]{});
		return NONE;
	}
	
	
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
