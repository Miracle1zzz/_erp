package com.ahpu.erp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IFunctionDao;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.domain.Function;
import com.ahpu.erp.service.realm.ErpRealm;
import com.ahpu.erp.service.service.IFunctionService;
import com.ahpu.erp.utils.EmpUtils;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service
 * ClassName：FunctionServiceImpl
 * Description：权限功能管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 下午6:24:25
 */
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{
	
	@Autowired
	private IFunctionDao functionDao;

	/**
	 * Title: findAll 
	 * Description:查询   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:31:49   
	 * @see com.ahpu.erp.service.service.IFunctionService#findAll()
	 */
	public List<Function> findAll() {
		return functionDao.findAllFunction();
	}

	/**
	 * Title: save 
	 * Description: 保存权限  
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:34:57   
	 * @see com.ahpu.erp.service.service.IFunctionService#save(com.ahpu.erp.domain.Function)
	 */
	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction != null && parentFunction.getId().equals("")){
			model.setParentFunction(null);
		}
		functionDao.save(model);
	}
	/**
	 * Title: pageQuery 
	 * Description:分页查询   
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:37:39   
	 * @see com.ahpu.erp.service.service.IFunctionService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	/**
	 * Title: findMenu 
	 * Description: 查询菜单  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午6:41:32   
	 * @see com.ahpu.erp.service.service.IFunctionService#findMenu()
	 */
	public List<Function> findMenu() {
		List<Function> list = null;
		Emp loginUser = EmpUtils.getLoginUser();
		if(loginUser.getUsername().equals("admin")){
			//如果是超级管理员查询所有管理员
			list = functionDao.findAllMenu();
		}else{
			//其他用户，根据用户id查询菜单
			list = functionDao.findMenuByUserId(loginUser.getId());
		}
		return list;
	}

	/**
	 * Title: delete 
	 * Description:删除选中功能权限   
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月22日 下午6:27:38   
	 * @see com.ahpu.erp.service.service.IFunctionService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] depIds = ids.split(",");
			for(String id : depIds){
				functionDao.executeUpdate("function.delete", id);
			}
		}
	}

	/**
	 * Title: findMenuForSystem 
	 * Description:查询系统菜单   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月22日 下午7:02:14   
	 * @see com.ahpu.erp.service.service.IFunctionService#findMenuForSystem()
	 */
	public List<Function> findMenuForSystem() {
		List<Function> list = null;
		Emp loginUser = EmpUtils.getLoginUser();
		if(loginUser.getUsername().equals("admin")){
			//如果是超级管理员查询所有管理员
			list = functionDao.findAllMenuForSystem();
		}else{
			//其他用户，根据用户id查询菜单
			list = functionDao.findMenuByUserIdForSystem(loginUser.getId());
		}
		return list;
	}
	
}
