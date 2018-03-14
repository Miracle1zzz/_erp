package com.ahpu.erp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IStoreDao;
import com.ahpu.erp.domain.Store;
import com.ahpu.erp.service.service.IStoreService;
import com.ahpu.erp.utils.PageBean;

@Service
@Transactional
public class StoreServiceImpl implements IStoreService{

	@Autowired
	private IStoreDao storeDao;

	/**
	 * Title: save 
	 * Description:仓库信息保存   
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午12:40:08   
	 * @see com.ahpu.erp.service.service.IStoreService#save(com.ahpu.erp.domain.Store)
	 */
	public void save(Store model) {
		storeDao.save(model);
	}

	/**
	 * Title: pageQuery 
	 * Description:仓库信息分页查询   
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午12:49:54   
	 * @see com.ahpu.erp.service.service.IStoreService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		storeDao.pageQuery(pageBean);
	}

	/**
	 * Title: findById 
	 * Description: 根据id查询仓库信息  
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午12:52:29   
	 * @see com.ahpu.erp.service.service.IStoreService#findById(java.lang.String)
	 */
	public Store findById(String id) {
		return storeDao.findById(id);
	}

	/**
	 * Title: update 
	 * Description: 更新  
	 * @param store
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午12:54:12   
	 * @see com.ahpu.erp.service.service.IStoreService#update(com.ahpu.erp.domain.Store)
	 */
	public void update(Store store) {
		storeDao.update(store);
	}

	/**
	 * Title: delete 
	 * Description:删除选中的仓库信息   
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午12:56:38   
	 * @see com.ahpu.erp.service.service.IStoreService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] storeId = ids.split(",");
			for(String id : storeId){
				storeDao.executeUpdate("store.delete", id);
			}
		}
	}

	/**
	 * Title: findListStoreNotAssociation 
	 * Description:查询没有关联的仓库  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午3:41:39   
	 * @see com.ahpu.erp.service.service.IStoreService#findListStoreNotAssociation()
	 */
	public List<Store> findListStoreNotAssociation() {
		return storeDao.findListStoreNotAssociation();
	}
	/**
	 * Title: findListStoreHasAssociation 
	 * Description: 查询已经关联的仓库  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午3:41:45   
	 * @see com.ahpu.erp.service.service.IStoreService#findListStoreHasAssociation()
	 */
	public List<Store> findListStoreHasAssociation(String id) {
		return storeDao.findListStoreHasAssociation(id);
	}

	/**
	 * Title: findAll 
	 * Description:ajax异步获取仓库数据   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 上午11:51:15   
	 * @see com.ahpu.erp.service.service.IStoreService#findAll()
	 */
	public List<Store> findAll() {
		return storeDao.findAll();
	}
}
