package com.ahpu.erp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IGoodstypeDao;
import com.ahpu.erp.domain.Goodstype;
import com.ahpu.erp.service.service.IGoodstypeService;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service
 * ClassName：GoodstypeServiceImpl
 * Description：商品类别信息service实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月23日 下午3:08:05
 */
@Service
@Transactional
public class GoodstypeServiceImpl implements IGoodstypeService{

	@Autowired
	private IGoodstypeDao goodstypeDao;

	/**
	 * Title: pageQuery 
	 * Description:   
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午3:35:47   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		goodstypeDao.pageQuery(pageBean);
	}

	/**
	 * Title: save 
	 * Description: 保存添加商品类别  
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午3:48:13   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#save(com.ahpu.erp.domain.Goodstype)
	 */
	public void save(Goodstype model) {
		goodstypeDao.save(model);
	}

	/**
	 * Title: delete 
	 * Description:删除选中的商品类别   
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午3:50:49   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] goodstypeId = ids.split(",");
			for(String id : goodstypeId){
				goodstypeDao.executeUpdate("goodstype.delete", id);;
			}
		}
	}

	/**
	 * Title: findById 
	 * Description: 根据id查询原始数据  
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午3:58:01   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#findById(java.lang.String)
	 */
	public Goodstype findById(String id) {
		return goodstypeDao.findById(id);
	}

	/**
	 * Title: update 
	 * Description:更新数据   
	 * @param goodstype
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月23日 下午3:58:26   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#update(com.ahpu.erp.domain.Goodstype)
	 */
	public void update(Goodstype goodstype) {
		goodstypeDao.update(goodstype);
	}

	/**
	 * Title: findAllByQ 
	 * Description: 根据q参数进行模糊查询  
	 * @param q
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 上午9:07:33   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#findAllByQ(java.lang.String)
	 */
	public List<Goodstype> findAllByQ(String q) {
		return goodstypeDao.findListByQ(q);
	}

	/**
	 * Title: findAll 
	 * Description: 查询所有商品分类  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 上午9:07:41   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#findAll()
	 */
	public List<Goodstype> findAll() {
		return goodstypeDao.findAll();
	}

	/**
	 * Title: findAllById 
	 * Description:根据供应商id查询对应商品类型   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 上午9:59:18   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#findAllById(java.lang.String)
	 */
	public List<Goodstype> findAllById(String id) {
		return goodstypeDao.findGoodstypeById(id);
	}

	/**
	 * Title: findByName 
	 * Description: 根据name查询 商品类别
	 * @param gtype
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月2日 下午4:12:31   
	 * @see com.ahpu.erp.service.service.IGoodstypeService#findByName(java.lang.String)
	 */
	public Goodstype findByName(String gtype) {
		return goodstypeDao.findByName(gtype);
	}
}
