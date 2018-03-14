package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.Goodstype;
import com.ahpu.erp.utils.PageBean;

public interface IGoodstypeService {

	public void pageQuery(PageBean pageBean);

	public void save(Goodstype model);

	public void delete(String ids);

	public Goodstype findById(String id);

	public void update(Goodstype goodstype);

	public List<Goodstype> findAllByQ(String q);

	public List<Goodstype> findAll();

	public List<Goodstype> findAllById(String id);

	public Goodstype findByName(String gtype);

}
