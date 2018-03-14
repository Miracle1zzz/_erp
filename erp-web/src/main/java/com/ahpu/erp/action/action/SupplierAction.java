package com.ahpu.erp.action.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Supplier;
import com.ahpu.erp.service.service.ISupplierService;
import com.ahpu.erp.utils.PinYin4jUtils;

@Controller
@Scope("prototype")
public class SupplierAction extends BaseAction<Supplier>{

	@Autowired
	private ISupplierService supplierService;
	
	/**
	* @Title: add
	* @Description: 添加供应商信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午1:57:19
	 */
	public String add(){
		String[] headByString = PinYin4jUtils.getHeadByString(model.getName());
		String shortcode = StringUtils.join(headByString);
		model.setShortcode(shortcode);
		supplierService.save(model);
		return LIST;
	}
	/**
	* @Title: pageQuery
	* @Description: 供应商信息分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午2:01:55
	 */
	public String pageQuery(){
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String name = model.getName();
		if(StringUtils.isNotBlank(name)){
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		String contact = model.getContact();
		if(StringUtils.isNotBlank(contact)){
			dc.add(Restrictions.like("contact", "%"+contact+"%"));
		}
		String telephone = model.getTelephone();
		if(StringUtils.isNotBlank(telephone)){
			dc.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		Integer needs = model.getNeeds();
		if(needs != null && needs != -1){
			dc.add(Restrictions.eq("needs", needs));
		}
		supplierService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	* @Title: deleteBatch
	* @Description: 删除选中供应商
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午2:10:23
	 */
	public String deleteBatch(){
		supplierService.delete(ids);
		return LIST;
	}
	/**
	* @Title: edit
	* @Description: 修改供应商信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午2:22:05
	 */
	public String edit(){
		Supplier supplier = supplierService.findById(model.getId());
		String[] headByString = PinYin4jUtils.getHeadByString(model.getName());
		String shortcode = StringUtils.join(headByString);
		supplier.setShortcode(shortcode);
		supplier.setName(model.getName());
		supplier.setAddress(model.getAddress());
		supplier.setContact(model.getContact());
		supplier.setNeeds(model.getNeeds());
		supplier.setTelephone(model.getTelephone());
		supplierService.update(supplier);
		return LIST;
	}
	private String q;
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	/**
	* @Title: listajax
	* @Description: ajax请求获取数据
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午3:22:44
	 */
	public String listajax(){
		List<Supplier> list = null;
		if(StringUtils.isNotBlank(q)){
			list = supplierService.findAllByQ(q);
		}else{
			list = supplierService.findAll();
		}
		this.java2Json(list, new String[]{});
		return NONE;
	}
}
