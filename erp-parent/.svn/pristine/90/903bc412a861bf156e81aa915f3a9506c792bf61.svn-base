package com.ahpu.erp.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IFunctionDao;
import com.ahpu.erp.domain.Function;

/**
 * ProjectName：erp-dao
 * ClassName：FunctionDaoImpl
 * Description：权限功能管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 下午6:20:50
 */
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	/**
	 * Title: findAllMenu 
	 * Description: 为系统内置账号查询所有菜单
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:44:22   
	 * @see com.ahpu.erp.dao.dao.IFunctionDao#findAllMenu()
	 */
	public List<Function> findAllMenu() {
		String hql = "from Function f where f.generatemenu='1' and deltag = '0' and type = '0' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		
		return list;
	}

	/**
	 * Title: findMenuByUserId 
	 * Description:根据登录用户id查询对应的菜单   
	 * @param userid
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:45:14   
	 * @see com.ahpu.erp.dao.dao.IFunctionDao#findMenuByUserId(java.lang.String)
	 */
	public List<Function> findMenuByUserId(String userid) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id =? and f.generatemenu='1' and deltag = '0' and type = '0' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql,userid);
		
		return list;
	}

	/**
	 * Title: findFunctionListByUserId 
	 * Description:根据用户id查询对应的权限   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午7:14:45   
	 * @see com.ahpu.erp.dao.dao.IFunctionDao#findFunctionListByUserId(java.lang.String)
	 */
	public List<Function> findFunctionListByUserId(String userId) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id =? and deltag = '0'";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql, userId);
		return list;
	}

	/**
	 * Title: findAllMenuForSystem 
	 * Description:为系统内置账号查询所有系统菜单   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月22日 下午7:04:31   
	 * @see com.ahpu.erp.dao.dao.IFunctionDao#findAllMenuForSystem()
	 */
	public List<Function> findAllMenuForSystem() {
		String hql = "from Function f where f.generatemenu='1' and deltag = '0' and type = '1' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		
		return list;
	}

	/**
	 * Title: findMenuByUserIdForSystem 
	 * Description: 根据登录用户id查询对应的系统菜单   
	 * @param userid
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月22日 下午7:04:53   
	 * @see com.ahpu.erp.dao.dao.IFunctionDao#findMenuByUserIdForSystem(java.lang.String)
	 */
	public List<Function> findMenuByUserIdForSystem(String userid) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r LEFT OUTER JOIN r.users u WHERE u.id =? and f.generatemenu='1' and deltag = '0' and type = '1' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql,userid);
		
		return list;
	}

	/**
	 * Title: findAllFunction 
	 * Description: 为角色管理生成权限树  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月22日 下午7:28:23   
	 * @see com.ahpu.erp.dao.dao.IFunctionDao#findAllFunction()
	 */
	public List<Function> findAllFunction() {
		String hql = "from Function f where deltag = '0' and type = '0' order by f.zindex desc";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		
		return list;
	}

}
