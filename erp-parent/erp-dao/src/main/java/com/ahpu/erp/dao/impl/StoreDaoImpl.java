package com.ahpu.erp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IStoreDao;
import com.ahpu.erp.domain.Store;

/**
 * ProjectName：erp-dao
 * ClassName：StoreDaoImpl
 * Description：仓库信息管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月31日 下午3:43:52
 */
@Repository
public class StoreDaoImpl extends BaseDaoImpl<Store> implements IStoreDao{

	/**
	 * Title: findListStoreNotAssociation 
	 * Description:查询没有关联的仓库   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午3:43:48   
	 * @see com.ahpu.erp.dao.dao.IStoreDao#findListStoreNotAssociation()
	 */
	public List<Store> findListStoreNotAssociation() {
		String hql = "FROM Store WHERE state = ? and deltag ='0'";
		List<Store> list = (List<Store>) this.getHibernateTemplate()
				.find(hql,Store.STORE_STATE_OF_YES);
		return list;
	}

	/**
	 * Title: findListStoreHasAssociation 
	 * Description:查询已经关联的仓库   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午3:50:36   
	 * @see com.ahpu.erp.dao.dao.IStoreDao#findListStoreHasAssociation(java.lang.String)
	 */
	public List<Store> findListStoreHasAssociation(String id) {
		String hql = "FROM Store WHERE state = ? and id = ? and deltag ='0'";
		List<Store> list = (List<Store>) this.getHibernateTemplate()
				.find(hql,Store.STORE_STATE_OF_YES,id);
		return list;
	}

}
