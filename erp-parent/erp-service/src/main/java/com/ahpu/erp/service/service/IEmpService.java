package com.ahpu.erp.service.service;


import java.util.List;

import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.utils.PageBean;

public interface IEmpService {

	public void editPassword(String id, String password);

	public void pageQuery(PageBean pageBean);

	public Emp login(Emp model, String loginIp);

	public void save(Emp model, String[] roleIds);

	public void delete(String ids);

	public List<Emp> findAll();

	public List<Emp> findAllByDep(String id);

}
