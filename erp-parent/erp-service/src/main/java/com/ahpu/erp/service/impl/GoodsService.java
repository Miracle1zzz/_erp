package com.ahpu.erp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IGoodsDao;
import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.service.service.IGoodsService;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service
 * ClassName：GoodsService
 * Description：商品信息管理service实现类
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月23日 下午6:42:59
 */
@Service
@Transactional
public class GoodsService implements IGoodsService{

	@Autowired
	private IGoodsDao goodsDao;

	/**
	 * Title: pageQuery 
	 * Description:分页查询  
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 上午9:22:09   
	 * @see com.ahpu.erp.service.service.IGoodsService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		goodsDao.pageQuery(pageBean);
	}

	/**
	 * Title: save 
	 * Description:   
	 * @param model
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 下午12:44:42   
	 * @see com.ahpu.erp.service.service.IGoodsService#save(com.ahpu.erp.domain.Goods)
	 */
	public void save(Goods model) {
		//设置商品使用次数为0
		model.setPurchaseNum(0);
		model.setOperTime(new Date());
		goodsDao.save(model);
	}

	/**
	 * Title: delete 
	 * Description:删除选中的商品   
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 下午12:46:46   
	 * @see com.ahpu.erp.service.service.IGoodsService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] goodsId = ids.split(",");
			for(String id : goodsId){
				goodsDao.executeUpdate("goods.delete", id);
			}
		}
	}

	/**
	 * Title: findById 
	 * Description:根据id查询  
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 下午12:51:18   
	 * @see com.ahpu.erp.service.service.IGoodsService#findById(java.lang.String)
	 */
	public Goods findById(String id) {
		return goodsDao.findById(id);
	}

	/**
	 * Title: update 
	 * Description:更新快照   
	 * @param goods
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 下午12:56:17   
	 * @see com.ahpu.erp.service.service.IGoodsService#update(com.ahpu.erp.domain.Goods)
	 */
	public void update(Goods goods) {
		goodsDao.update(goods);
	}

	/**
	 * Title: findAll 
	 * Description: 根据ajax请求获取数据  
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 上午8:49:27   
	 * @see com.ahpu.erp.service.service.IGoodsService#findAll()
	 */
	public List<Goods> findAll() {
		return goodsDao.findAll();
	}

	/**
	 * Title: findAllByID 
	 * Description: 根据商品类型ID查询对应商品  
	 * @param goodstypeid
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 上午8:50:24   
	 * @see com.ahpu.erp.service.service.IGoodsService#findAllByID(java.lang.String)
	 */
	public List<Goods> findAllByID(String goodstypeid) {
		return goodsDao.findAllById(goodstypeid);
	}

	/**
	 * Title: findPriceById 
	 * Description: 根据选中的商品动态加载单价  
	 * @param goodsid
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 上午9:05:41   
	 * @return 
	 * @see com.ahpu.erp.service.service.IGoodsService#findPriceById(java.lang.String)
	 */
	public List<Goods> findPriceById(String goodsid) {
		
		return goodsDao.findPriceById(goodsid);
	}
	/**
	 * Title: getWarnInfo 
	 * Description:获取库存预警商品信息   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月2日 下午6:01:36   
	 * @see com.ahpu.erp.service.service.IGoodsService#getWarnInfo()
	 */
	public List<Object[]> getWarnInfo() {
		return goodsDao.getWarnInfo();
	}

	/**
	 * Title: saveBatch 
	 * Description: 商品信息导入  
	 * @param goodsList
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月2日 下午6:02:07   
	 * @see com.ahpu.erp.service.service.IGoodsService#saveBatch(java.util.List)
	 */
	public void saveBatch(List<Goods> goodsList) {
		for(Goods goods : goodsList){
			goodsDao.saveOrUpdate(goods);
		}
	}

	/**
	 * Title: findAllByQ 
	 * Description:根据页面输入值匹配简码实现快速查找   
	 * @param q
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月3日 下午8:14:58   
	 * @see com.ahpu.erp.service.service.IGoodsService#findAllByQ(java.lang.String)
	 */
	public List<Goods> findAllByQ(String q) {
		return goodsDao.findListByQ(q);
	}
	
}
