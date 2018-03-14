package com.ahpu.erp.action.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Goodstype;
import com.ahpu.erp.service.service.IGoodstypeService;
import com.ahpu.erp.utils.PinYin4jUtils;

@Controller
@Scope("prototype")
public class GoodstypeAction extends BaseAction<Goodstype>{

	@Autowired
	private IGoodstypeService goodstypeService;
	
	/**
	* @Title: pageQuery
	* @Description: 商品类型分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午3:32:29
	 */
	public String pageQuery(){
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String name = model.getName();
		if(StringUtils.isNotBlank(name)){
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		goodstypeService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize"});
		return NONE;
	}
	/**
	* @Title: add
	* @Description: 商品类别添加
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午3:46:10
	 */
	public String add(){
		String[] headByString = PinYin4jUtils.getHeadByString(model.getName());
		String shortcode = StringUtils.join(headByString);
		model.setShortcode(shortcode);
		goodstypeService.save(model);
		return LIST;
	}
	
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	* @Title: deleteBatch
	* @Description: 删除选中的商品类别
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午3:49:50
	 */
	public String deleteBatch(){
		goodstypeService.delete(ids);
		return LIST;
	}
	/**
	* @Title: edit
	* @Description: 保存编辑商品类别信息
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 下午3:54:10
	 */
	public String edit(){
		Goodstype goodstype = goodstypeService.findById(model.getId());
		String[] headByString = PinYin4jUtils.getHeadByString(model.getName());
		String shortcode = StringUtils.join(headByString);
		goodstype.setShortcode(shortcode);
		goodstype.setName(model.getName());
		goodstype.setSupplier(model.getSupplier());
		goodstypeService.update(goodstype);
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
	* Create Date：2018年1月25日 上午8:58:39
	 */
	public String listajax(){
		List<Goodstype> list = null;
		if(StringUtils.isNotBlank(q)){
			list = goodstypeService.findAllByQ(q);
		}else{
			list = goodstypeService.findAll();
		}
		this.java2Json(list, new String[]{"supplier"});
		return NONE;
	}
	
	private String supplierid;
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	/**
	* @Title: listajaxById
	* @Description: 根据供应商ID查询对应商品类型
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月25日 上午9:57:12
	 */
	public String listajaxById(){
		List<Goodstype> list = goodstypeService.findAllById(supplierid);
		this.java2Json(list, new String[]{"supplier"});
		return NONE;
	}
}
