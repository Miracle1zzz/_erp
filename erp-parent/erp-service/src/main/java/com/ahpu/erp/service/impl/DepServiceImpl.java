package com.ahpu.erp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IDepDao;
import com.ahpu.erp.domain.Dep;
import com.ahpu.erp.service.service.IDepService;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service
 * ClassName：DepServiceImpl
 * Description：部门信息管理service实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 下午1:01:40
 */
@Service
@Transactional
public class DepServiceImpl implements IDepService{

	@Autowired
	private IDepDao depDao;

	/**
	 * Title: pageQuery 
	 * Description: 分页查询方法  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午1:04:41   
	 * @see com.ahpu.erp.service.service.IDepService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		depDao.pageQuery(pageBean);
	}

	/**
	 * Title: save 
	 * Description:保存部门信息   
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午1:09:14   
	 * @see com.ahpu.erp.service.service.IDepService#save(com.ahpu.erp.domain.Dep)
	 */
	public void save(Dep model) {
		depDao.save(model);
	}

	/**
	 * Title: delete 
	 * Description:删除部门信息   
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午1:22:20   
	 * @see com.ahpu.erp.service.service.IDepService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] depIds = ids.split(",");
			for(String id : depIds){
				depDao.executeUpdate("dep.delete", id);
			}
		}
	}

	/**
	 * Title: findById 
	 * Description:根据id查询原始数据   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午1:27:15   
	 * @see com.ahpu.erp.service.service.IDepService#findById(java.lang.String)
	 */
	public Dep findById(String id) {
		return depDao.findById(id);
	}

	/**
	 * Title: update 
	 * Description:保存编辑部门信息   
	 * @param dep
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午1:29:34   
	 * @see com.ahpu.erp.service.service.IDepService#update(com.ahpu.erp.domain.Dep)
	 */
	public void update(Dep dep) {
		depDao.update(dep);
	}

	/**
	 * Title: findListAll 
	 * Description: ajax异步查询所有部门信息  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月21日 下午3:06:21   
	 * @see com.ahpu.erp.service.service.IDepService#findListAll()
	 */
	public List<Dep> findListAll() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Dep.class);
		//添加过滤条件--》》deltag = 0
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return depDao.findByCriteria(detachedCriteria);
	}

}
