package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.Staff;
import com.ahpu.erp.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void delete(String ids);

	public Staff findById(String id);

	public void update(Staff staff);

	public List<Staff> findListNotDelete();

	public List<Staff> findAll();

	public List<Staff> findListNotAssociation();

	public List<Staff> findListHasAssociation(String id);

	public void assginStaff(String id, List<Integer> staffIds);

}
