package com.ahpu.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IOperDetailDao;
import com.ahpu.erp.domain.OperDetail;
import com.ahpu.erp.service.service.IOperDetailService;
import com.ahpu.erp.utils.PageBean;

@Service
@Transactional
public class OperDetailServiceImpl implements IOperDetailService{

	@Autowired
	private IOperDetailDao operDetailDao;

	/**
	 * Title: pageQuery 
	 * Description: 库存明细分页查询  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午6:45:13   
	 * @see com.ahpu.erp.service.service.IOperDetailService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		operDetailDao.pageQuery(pageBean);
	}

	/**
	 * Title: findAll 
	 * Description:库存操作明细查询  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午6:58:17   
	 * @see com.ahpu.erp.service.service.IOperDetailService#findAll()
	 */
	public List<OperDetail> findAll() {
		return operDetailDao.findAll();
	}
	
}
