package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.Dep;
import com.ahpu.erp.utils.PageBean;

public interface IDepService {

	public void pageQuery(PageBean pageBean);

	public void save(Dep model);

	public void delete(String ids);

	public Dep findById(String id);

	public void update(Dep dep);

	public List<Dep> findListAll();

}
