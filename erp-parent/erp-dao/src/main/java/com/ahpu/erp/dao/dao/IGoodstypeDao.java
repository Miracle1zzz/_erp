package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.Goodstype;

public interface IGoodstypeDao extends IBaseDao<Goodstype>{

	public List<Goodstype> findGoodstypeById(String id);

	public Goodstype findByName(String gtype);

}
