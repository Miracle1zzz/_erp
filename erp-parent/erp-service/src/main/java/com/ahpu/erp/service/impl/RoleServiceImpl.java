package com.ahpu.erp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IRoleDao;
import com.ahpu.erp.domain.Function;
import com.ahpu.erp.domain.Role;
import com.ahpu.erp.service.service.IRoleService;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service
 * ClassName：RoleServiceImpl
 * Description：角色管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 下午6:22:58
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private IRoleDao roleDao;

	/**
	 * Title: save 
	 * Description: 保存角色  
	 * @param model
	 * @param functionIds
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:52:58   
	 * @see com.ahpu.erp.service.service.IRoleService#save(com.ahpu.erp.domain.Role, java.lang.String)
	 */
	public void save(Role model, String functionIds) {
		roleDao.save(model);
		if(StringUtils.isNotBlank(functionIds)){
			String[] splits = functionIds.split(",");
			for(String functionId : splits){
				//手动构造一个权限对象，设置id，对象状态为托管状态
				Function function = new Function(functionId);
				//角色关联权限
				model.getFunctions().add(function);
			}
		}
	}

	/**
	 * Title: pageQuery 
	 * Description: 分页查询  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:52:46   
	 * @see com.ahpu.erp.service.service.IRoleService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	/**
	 * Title: findAll 
	 * Description:ajax请求查询所有角色   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:52:35   
	 * @see com.ahpu.erp.service.service.IRoleService#findAll()
	 */
	public List<Role> findAll() {
		return roleDao.findAll();
	}
	
}
