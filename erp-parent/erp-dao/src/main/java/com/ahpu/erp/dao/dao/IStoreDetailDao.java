package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.StoreDetail;

public interface IStoreDetailDao extends IBaseDao<StoreDetail>{

	public StoreDetail getByStoreAndGoods(String id, String id2);

	public List<StoreDetail> findListByIds(String ids);

	public List<Object> findGoodsGroupByStore();

}
