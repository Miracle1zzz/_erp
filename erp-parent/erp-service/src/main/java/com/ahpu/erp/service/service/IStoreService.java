package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.Store;
import com.ahpu.erp.utils.PageBean;

public interface IStoreService {

	public void save(Store model);

	public void pageQuery(PageBean pageBean);

	public Store findById(String id);

	public void update(Store store);

	public void delete(String ids);

	public List<Store> findListStoreNotAssociation();

	public List<Store> findListStoreHasAssociation(String id);

	public List<Store> findAll();

}
