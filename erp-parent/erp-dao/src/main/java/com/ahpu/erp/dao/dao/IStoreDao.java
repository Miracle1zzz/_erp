package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.Store;

public interface IStoreDao extends IBaseDao<Store>{

	public List<Store> findListStoreNotAssociation();

	public List<Store> findListStoreHasAssociation(String id);

}
