package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.Goods;

public interface IGoodsDao extends IBaseDao<Goods>{

	public List<Goods> findAllById(String goodstypeid);

	public List<Goods> findPriceById(String goodsid);

	public void goodsPurchaseNumUpdate();

	public List<Object[]> getWarnInfo();

}
