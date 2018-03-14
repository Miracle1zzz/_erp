package com.ahpu.erp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IStaffDao;
import com.ahpu.erp.domain.Staff;
import com.ahpu.erp.service.service.IStaffService;
import com.ahpu.erp.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService{

	@Autowired
	private IStaffDao staffDao;
	
	/**
	 * Title: save 
	 * Description: 保存取派员信息  
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月27日 上午9:18:32   
	 * @see com.ahpu.erp.service.service.IStaffService#save(com.ahpu.erp.domain.Staff)
	 */
	public void save(Staff model) {
		staffDao.save(model);
	}

	/**
	 * Title: pageQuery 
	 * Description: 分页查询方法  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月27日 上午9:18:46   
	 * @see com.ahpu.erp.service.service.IStaffService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}
	/**
	 * Title: delete 
	 * Description:  取派员批量删除,逻辑删除，将deltag改为1 
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月27日 上午9:17:32   
	 * @see com.ahpu.erp.service.service.IStaffService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] staffIds = ids.split(",");
			for(String id:staffIds){
				staffDao.executeUpdate("staff.delete", id);
			}
		}
	}

	/**
	 * Title: findById 
	 * Description: 根据id查询  
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月27日 上午9:17:49   
	 * @see com.ahpu.erp.service.service.IStaffService#findById(java.lang.String)
	 */
	public Staff findById(String id) {
		return staffDao.findById(id);
	}

	/**
	 * Title: update 
	 * Description: 更新取派员  
	 * @param staff
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月27日 上午9:17:56   
	 * @see com.ahpu.erp.service.service.IStaffService#update(com.ahpu.erp.domain.Staff)
	 */
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	/**
	 * Title: findListNotDelete 
	 * Description:   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月27日 上午9:18:02   
	 * @see com.ahpu.erp.service.service.IStaffService#findListNotDelete()
	 */
	public List<Staff> findListNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		//添加过滤条件--》》deltag = 0
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(detachedCriteria);
	}

	/**
	 * Title: findAll 
	 * Description:查询所有取派员   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月28日 下午12:44:55   
	 * @see com.ahpu.erp.service.service.IStaffService#findAll()
	 */
	public List<Staff> findAll() {
		return staffDao.findAll();
	}
	

	/**
	 * Title: findListNotAssociation 
	 * Description: 查询未关联司机  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 上午9:50:16   
	 * @see com.ahpu.erp.service.service.IStaffService#findListNotAssociation()
	 */
	public List<Staff> findListNotAssociation() {
		return staffDao.findListNotAssociation();
	}

	/**
	 * Title: findListHasAssociation 
	 * Description:查询已关联司机   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 上午9:51:02   
	 * @see com.ahpu.erp.service.service.IStaffService#findListHasAssociation()
	 */
	public List<Staff> findListHasAssociation(String id) {
		return staffDao.findListHasAssociation(id);
	}

	/**
	 * Title: assginStaff 
	 * Description:为订单指派司机   
	 * @param id
	 * @param staffIds
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 上午10:06:16   
	 * @see com.ahpu.erp.service.service.IStaffService#assginStaff(java.lang.String, java.util.List)
	 */
	public void assginStaff(String id, List<Integer> staffIds) {
		//staffDao.executeUpdate("staff.update", id);
		for(Integer staffId : staffIds){
			staffDao.executeUpdate("staff.assgin", id,staffId);
		}
	}
}
