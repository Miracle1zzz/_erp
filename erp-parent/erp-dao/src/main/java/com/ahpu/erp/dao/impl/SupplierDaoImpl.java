package com.ahpu.erp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.ISupplierDao;
import com.ahpu.erp.domain.Supplier;

/**
 * ProjectName：erp-dao
 * ClassName：SupplierDaoImpl
 * Description：供应商信息实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月23日 下午1:52:49
 */
@Repository
public class SupplierDaoImpl extends BaseDaoImpl<Supplier> implements ISupplierDao{

	/**
	 * Title: findListByQ 
	 * Description:根据q参数进行模糊查询   
	 * @param q
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午4:29:21   
	 * @see com.ahpu.erp.dao.dao.ISupplierDao#findListByQ(java.lang.String)
	 */
	public List<Supplier> findListByQ(String q) {
		String hql = "FROM Supplier s WHERE s.shortcode LIKE ?";
		List<Supplier> list = (List<Supplier>) this.getHibernateTemplate().find(hql, "%"+q+"%");
		return list;
	}

}
