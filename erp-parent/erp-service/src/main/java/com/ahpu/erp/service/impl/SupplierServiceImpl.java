package com.ahpu.erp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.ISupplierDao;
import com.ahpu.erp.domain.Supplier;
import com.ahpu.erp.service.service.ISupplierService;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service
 * ClassName：SupplierServiceImpl
 * Description：供应商信息管理service实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月23日 下午1:54:17
 */
@Service
@Transactional
public class SupplierServiceImpl implements ISupplierService{

	@Autowired
	private ISupplierDao supplierDao;

	/**
	 * Title: save 
	 * Description:添加供应商信息   
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午2:00:53   
	 * @see com.ahpu.erp.service.service.ISupplierService#save(com.ahpu.erp.domain.Supplier)
	 */
	public void save(Supplier model) {
		supplierDao.save(model);
	}

	/**
	 * Title: pageQuery 
	 * Description:供应商信息分页查询   
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午2:06:57   
	 * @see com.ahpu.erp.service.service.ISupplierService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		supplierDao.pageQuery(pageBean);
	}

	/**
	 * Title: delete 
	 * Description:供应商删除  
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午2:11:43   
	 * @see com.ahpu.erp.service.service.ISupplierService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] supplierId = ids.split(",");
			for(String id : supplierId){
				supplierDao.executeUpdate("", id);;
			}
		}
	}

	/**
	 * Title: findById 
	 * Description:根据id查询供应商详细信息   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午2:24:18   
	 * @see com.ahpu.erp.service.service.ISupplierService#findById(java.lang.String)
	 */
	public Supplier findById(String id) {
		
		return supplierDao.findById(id);
	}

	/**
	 * Title: update 
	 * Description:供应商信息更新   
	 * @param supplier
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午2:28:19   
	 * @see com.ahpu.erp.service.service.ISupplierService#update(com.ahpu.erp.domain.Supplier)
	 */
	public void update(Supplier supplier) {
		supplierDao.update(supplier);
	}

	/**
	 * Title: findAll 
	 * Description:查询所有供应商  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午3:25:57   
	 * @see com.ahpu.erp.service.service.ISupplierService#findAll()
	 */
	public List<Supplier> findAll() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Supplier.class);
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return supplierDao.findByCriteria(detachedCriteria);
	}

	/**
	 * Title: findAllByQ 
	 * Description: 根据页面输入值查询供应商  
	 * @param q
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午4:27:35   
	 * @see com.ahpu.erp.service.service.ISupplierService#findAllByQ(java.lang.String)
	 */
	public List<Supplier> findAllByQ(String q) {
		
		return supplierDao.findListByQ(q);
	}
}
