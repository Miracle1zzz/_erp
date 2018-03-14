package com.ahpu.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IWarningDao;
import com.ahpu.erp.service.service.IWarningService;
import com.ahpu.erp.utils.PageBean;

@Controller
@Transactional
public class WarningServiceImpl implements IWarningService{

	@Autowired
	private IWarningDao warningDao;

	/**
	 * Title: pageQuery 
	 * Description: 预警信息分页查询方法  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月30日 下午2:40:44   
	 * @see com.ahpu.erp.service.service.IWarningService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		warningDao.pageQuery(pageBean);
	}
}
