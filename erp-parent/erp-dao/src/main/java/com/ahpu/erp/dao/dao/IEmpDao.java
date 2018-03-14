package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.Emp;

public interface IEmpDao extends IBaseDao<Emp>{

	public Emp findByUsernameAndPassword(String username, String password);

	public Emp findUserByUsername(String username);

	public List<Emp> findAllByDep();

	public List<Emp> findAllByDep(String id);

}
