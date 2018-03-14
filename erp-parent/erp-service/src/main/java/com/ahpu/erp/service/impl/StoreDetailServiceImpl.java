package com.ahpu.erp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IStoreDetailDao;
import com.ahpu.erp.domain.StoreDetail;
import com.ahpu.erp.service.service.IStoreDetailService;
import com.ahpu.erp.utils.PageBean;

@Service
@Transactional
public class StoreDetailServiceImpl implements IStoreDetailService{

	@Autowired
	private IStoreDetailDao storeDetailDao;

	/**
	 * Title: pageQuery 
	 * Description: 查询当前仓库库存  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 上午10:46:59   
	 * @see com.ahpu.erp.service.service.IStoreDetailService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		storeDetailDao.pageQuery(pageBean);
	}

	/**
	 * Title: findListByIds 
	 * Description:获取选中的数据   
	 * @param ids
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午12:09:58   
	 * @see com.ahpu.erp.service.service.IStoreDetailService#findListByIds(java.lang.String)
	 */
	public List<StoreDetail> findListByIds(String ids) {
		return storeDetailDao.findListByIds(ids);
	}

	public List<StoreDetail> findAll() {
		return storeDetailDao.findAll();
	}

	/**
	 * Title: findGoodsGroupByStore 
	 * Description:查询商品分布数据   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午2:54:14   
	 * @see com.ahpu.erp.service.service.IStoreDetailService#findGoodsGroupByStore()
	 */
	public List<Object> findGoodsGroupByStore() {
		return storeDetailDao.findGoodsGroupByStore();
	}
}
