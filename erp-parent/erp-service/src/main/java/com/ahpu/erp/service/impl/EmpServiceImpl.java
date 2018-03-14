package com.ahpu.erp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IEmpDao;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.domain.Role;
import com.ahpu.erp.service.service.IEmpService;
import com.ahpu.erp.utils.MD5Utils;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service ClassName：EmpServiceImpl Description：员工信息处理service实现类
 * 
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1 Create Date：2018年1月21日 上午10:59:26
 */
@Service
@Transactional
public class EmpServiceImpl implements IEmpService {

	@Autowired
	private IEmpDao empDao;

	/**
	 * Title: editPassword Description: 修改密码
	 * 
	 * @param id
	 * @param password
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午12:44:27
	 * @see com.ahpu.erp.service.service.IEmpService#editPassword(java.lang.String,
	 *      java.lang.String)
	 */
	public void editPassword(String id, String password) {
		password = MD5Utils.md5(password);
		empDao.executeUpdate("emp.editPassword", password, id);
	}

	/**
	 * Title: pageQuery Description:分页查询
	 * 
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午1:56:26
	 * @see com.ahpu.erp.service.service.IEmpService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		empDao.pageQuery(pageBean);
	}

	/**
	 * Title: login 
	 * Description:员工登录   
	 * @param emp
	 * @param loginIp
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午2:21:57   
	 * @see com.ahpu.erp.service.service.IEmpService#login(com.ahpu.erp.domain.Emp, java.lang.String)
	 */
	public Emp login(Emp emp, String loginIp) {
		// 使用MD5加密
		String password = MD5Utils.md5(emp.getPassword());
		Emp loginEm = empDao.findByUsernameAndPassword(emp.getUsername(), password);
		return loginEm;
	}

	/**
	 * Title: save 
	 * Description:   
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午3:20:04   
	 * @see com.ahpu.erp.service.service.IEmpService#save(com.ahpu.erp.domain.Emp)
	 */
	public void save(Emp model, String[] roleIds) {
		String password = MD5Utils.md5(model.getPassword());
		model.setPassword(password);
		empDao.save(model);
		if(roleIds != null && roleIds.length >0){
			for (String roleId : roleIds) {
				Role role = new Role(roleId);
				//用户对象关联角色对象
				model.getRoles().add(role);
			}
		}
	}

	/**
	 * Title: delete 
	 * Description: 删除员工信息  
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午3:37:01   
	 * @see com.ahpu.erp.service.service.IEmpService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] empIds = ids.split(",");
			for(String id : empIds){
				empDao.executeUpdate("emp.delete", id);
			}
		}
	}

	/**
	 * Title: findAll 
	 * Description:   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月29日 上午9:44:54   
	 * @see com.ahpu.erp.service.service.IEmpService#findAll()
	 */
	public List<Emp> findAll() {
		return empDao.findAllByDep();
	}

	/**
	 * Title: findAllByDep 
	 * Description:根据当前登陆人查询其部门所有人   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午12:29:23   
	 * @see com.ahpu.erp.service.service.IEmpService#findAllByDep(java.lang.String)
	 */
	public List<Emp> findAllByDep(String id) {
		return empDao.findAllByDep(id);
	}
}
