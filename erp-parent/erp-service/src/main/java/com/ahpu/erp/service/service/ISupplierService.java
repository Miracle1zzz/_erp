package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.Supplier;
import com.ahpu.erp.utils.PageBean;

public interface ISupplierService {

	public void save(Supplier model);

	public void pageQuery(PageBean pageBean);

	public void delete(String ids);

	public Supplier findById(String id);

	public void update(Supplier supplier);

	public List<Supplier> findAll();

	public List<Supplier> findAllByQ(String q);

}
