package com.ahpu.erp.dao.impl;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IStaffDao;
import com.ahpu.erp.domain.Staff;

@Repository
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements IStaffDao{

	/**
	 * Title: findListNotAssociation 
	 * Description: 查询未关联司机  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 上午9:52:00   
	 * @see com.ahpu.erp.dao.dao.IStaffDao#findListNotAssociation()
	 */
	public List<Staff> findListNotAssociation() {
		String hql = "FROM Staff WHERE order_id IS NULL and deltag ='0'";
		List<Staff> list = (List<Staff>) this.getHibernateTemplate().find(hql);
		return list;
	}
	/**
	 * Title: findListHasAssociation 
	 * Description: 查询已关联司机  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 上午9:52:16   
	 * @see com.ahpu.erp.dao.dao.IStaffDao#findListHasAssociation()
	 */
	public List<Staff> findListHasAssociation(String id) {
		String hql = "FROM Staff WHERE order_id =? and deltag ='0'";
		List<Staff> list = (List<Staff>) this.getHibernateTemplate().find(hql,id);
		return list;
	}
	/**
	 * Title: findByOrder 
	 * Description:根据订单id查询司机   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 上午10:01:28   
	 * @see com.ahpu.erp.dao.dao.IStaffDao#findByOrder(java.lang.String)
	 */
	public Staff findByOrder(String id) {
		String hql = "FROM Staff WHERE order_id = ?";
		List<Staff> temp = (List<Staff>) this.getHibernateTemplate().find(hql, id);
		return temp.size()>0 ? temp.get(0):null;
	}

}
