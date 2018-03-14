package com.ahpu.erp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IGoodstypeDao;
import com.ahpu.erp.domain.Goodstype;

/**
 * ProjectName：erp-dao
 * ClassName：GoodstypeDaoImpl
 * Description：商品类别信息dao实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月23日 下午3:09:02
 */
@Repository
public class GoodstypeDaoImpl extends BaseDaoImpl<Goodstype> implements IGoodstypeDao{

	/**
	 * Title: findGoodstypeById 
	 * Description: 根据供应商id查询对应商品类别  
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 上午10:07:20   
	 * @see com.ahpu.erp.dao.dao.IGoodstypeDao#findGoodstypeById(java.lang.String)
	 */
	public List<Goodstype> findGoodstypeById(String id) {
		String hql ="FROM Goodstype g WHERE g.supplier.id = ?";
		List<Goodstype> list = (List<Goodstype>) this.getHibernateTemplate().find(hql, id);
		return list;
	}

	/**
	 * Title: findByName 
	 * Description:根据名称查询商品类别信息   
	 * @param gtype
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月2日 下午4:13:47   
	 * @see com.ahpu.erp.dao.dao.IGoodstypeDao#findByName(java.lang.String)
	 */
	public Goodstype findByName(String gtype) {
		String hql ="FROM Goodstype WHERE name=?";
		List<Goodstype> temp = (List<Goodstype>) this.getHibernateTemplate().find(hql, gtype);
		return temp.size()>0 ? temp.get(0):null;
	}

}
