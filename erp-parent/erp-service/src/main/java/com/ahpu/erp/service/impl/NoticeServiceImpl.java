package com.ahpu.erp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.INoticeDao;
import com.ahpu.erp.service.service.INoticeService;
import com.ahpu.erp.utils.PageBean;

@Service
@Transactional
public class NoticeServiceImpl implements INoticeService{

	@Autowired
	private INoticeDao noticeDao;

	/**
	 * Title: pageQuery 
	 * Description: 工告信息分页展示  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月30日 上午9:35:51   
	 * @see com.ahpu.erp.service.service.INoticeService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		noticeDao.pageQuery(pageBean);
	}
}
