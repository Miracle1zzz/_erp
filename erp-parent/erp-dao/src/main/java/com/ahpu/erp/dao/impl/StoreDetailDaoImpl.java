package com.ahpu.erp.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IStoreDetailDao;
import com.ahpu.erp.domain.StoreDetail;

@Repository
public class StoreDetailDaoImpl extends BaseDaoImpl<StoreDetail> implements IStoreDetailDao{

	/**
	 * Title: getByStoreAndGoods 
	 * Description:根据仓库和商品id查询   
	 * @param id
	 * @param id2
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 上午9:51:01   
	 * @see com.ahpu.erp.dao.dao.IStoreDetailDao#getByStoreAndGoods(java.lang.String, java.lang.String)
	 */
	public StoreDetail getByStoreAndGoods(String storeId, String goodsId) {
		String hql = "FROM StoreDetail WHERE store.id =? and goods.id =? ";
		List<StoreDetail> temp = (List<StoreDetail>) this.getHibernateTemplate().find(hql, storeId,goodsId);
		return temp.size()>0 ? temp.get(0):null;
	}

	/**
	 * Title: findListByIds 
	 * Description:获取选中的数据   
	 * @param ids
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午12:11:06   
	 * @see com.ahpu.erp.dao.dao.IStoreDetailDao#findListByIds(java.lang.String)
	 */
	public List<StoreDetail> findListByIds(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] storeDetailId = ids.split(",");
			for(String id : storeDetailId){
				String hql = "FROM StoreDetail WHERE id =?";
				List<StoreDetail> list = (List<StoreDetail>) this.getHibernateTemplate().find(hql, id);
				return list;
			}
		}
		return null;
	}

	/**
	 * Title: findGoodsGroupByStore 
	 * Description:查询商品分布数据  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午2:56:55   
	 * @see com.ahpu.erp.dao.dao.IStoreDetailDao#findGoodsGroupByStore()
	 */
	public List<Object> findGoodsGroupByStore() {
		String hql = "SELECT r.name,SUM(s.num) FROM StoreDetail s	LEFT OUTER JOIN s.store r GROUP BY r.name";
		return (List<Object>) this.getHibernateTemplate().find(hql);
	}

}
