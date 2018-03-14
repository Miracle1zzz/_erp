package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.OperDetail;
import com.ahpu.erp.utils.PageBean;

public interface IOperDetailService {

	public void pageQuery(PageBean pageBean);

	public List<OperDetail> findAll();

}
