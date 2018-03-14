package com.ahpu.erp.dao.dao;

import java.util.List;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.domain.Staff;

public interface IStaffDao extends IBaseDao<Staff>{

	public List<Staff> findListNotAssociation();

	public List<Staff> findListHasAssociation(String id);

	public Staff findByOrder(String id);

}
