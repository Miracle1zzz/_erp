package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.Function;

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserId(String id);

	public List<Function> findFunctionListByUserId(String id);

	public List<Function> findAllMenuForSystem();

	public List<Function> findMenuByUserIdForSystem(String id);

	public List<Function> findAllFunction();

}
