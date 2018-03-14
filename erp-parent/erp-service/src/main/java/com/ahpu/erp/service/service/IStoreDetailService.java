package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.StoreDetail;
import com.ahpu.erp.utils.PageBean;

public interface IStoreDetailService {

	public void pageQuery(PageBean pageBean);

	public List<StoreDetail> findListByIds(String ids);

	public List<StoreDetail> findAll();

	public List<Object> findGoodsGroupByStore();

}
