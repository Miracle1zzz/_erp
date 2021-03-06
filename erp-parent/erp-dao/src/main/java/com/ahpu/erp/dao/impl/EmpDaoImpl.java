package com.ahpu.erp.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IEmpDao;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-dao
 * ClassName：EmpDaoImpl
 * Description：员工信息处理dao实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 上午10:59:59
 */
@Repository
public class EmpDaoImpl extends BaseDaoImpl<Emp> implements IEmpDao {

	/**
	 * Title: findByUsernameAndPassword 
	 * Description: 根据用户名密码查询用户  
	 * @param username
	 * @param password
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 上午11:43:38   
	 * @see com.ahpu.erp.dao.dao.IEmpDao#findByUsernameAndPassword(java.lang.String, java.lang.String)
	 */
	public Emp findByUsernameAndPassword(String username, String password) {
		String hql = "FROM Emp e WHERE e.username = ? and e.password =?";
		List<Emp> list = (List<Emp>) this.getHibernateTemplate().find(hql,username,password);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * Title: findUserByUsername 
	 * Description: 根据用户名查询用户  
	 * @param username
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:59:50   
	 * @see com.ahpu.erp.dao.dao.IEmpDao#findUserByUsername(java.lang.String)
	 */
	public Emp findUserByUsername(String username) {
		String hql = "FROM Emp e WHERE e.username = ?";
		List<Emp> list = (List<Emp>) this.getHibernateTemplate().find(hql,username);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * Title: findAllByDep 
	 * Description:   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月29日 上午9:48:48   
	 * @see com.ahpu.erp.dao.dao.IEmpDao#findAllByDep()
	 */
	public List<Emp> findAllByDep() {
		String hql = "FROM Emp where depUuid = '40284481613f9dbd01613f9f10540000'";
		List<Emp> list = (List<Emp>) this.getHibernateTemplate().find(hql);
		return list;
	}

	/**
	 * Title: findAllByDep 
	 * Description:根据当前登陆人查询其部门所有人   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午12:31:37   
	 * @see com.ahpu.erp.dao.dao.IEmpDao#findAllByDep(java.lang.String)
	 */
	public List<Emp> findAllByDep(String id) {
		String hql = "FROM Emp where depUuid = ?";
		List<Emp> list = (List<Emp>) this.getHibernateTemplate().find(hql,id);
		return list;
	}
	
}
