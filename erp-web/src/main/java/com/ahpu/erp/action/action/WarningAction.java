package com.ahpu.erp.action.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Warning;
import com.ahpu.erp.service.service.IWarningService;

@Controller
@Scope("prototype")
public class WarningAction extends BaseAction<Warning>{

	@Autowired
	private IWarningService warningService;
	
	/**
	* @Title: pageQuery
	* @Description: 预警信息分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月30日 下午2:39:29
	 */
	public String pageQuery(){
		warningService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{});
		return NONE;
	}
}
