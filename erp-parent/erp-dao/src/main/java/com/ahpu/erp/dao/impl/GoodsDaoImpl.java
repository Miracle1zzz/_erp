package com.ahpu.erp.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.ahpu.erp.dao.base.impl.BaseDaoImpl;
import com.ahpu.erp.dao.dao.IGoodsDao;
import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.domain.OperDetail;

@Repository
public class GoodsDaoImpl extends BaseDaoImpl<Goods> implements IGoodsDao{

	/**
	 * Title: findAllById 
	 * Description: 根据商品类型ID查询对应商品  
	 * @param goodstypeid
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 上午8:51:44   
	 * @see com.ahpu.erp.dao.dao.IGoodsDao#findAllById(java.lang.String)
	 */
	public List<Goods> findAllById(String goodstypeid) {
		String hql ="FROM Goods g WHERE g.goodstype.id = ?";
		List<Goods> list = (List<Goods>) this.getHibernateTemplate().find(hql, goodstypeid);
		return list;
	}

	/**
	 * Title: findPriceById 
	 * Description: 根据选中的商品动态加载单价    
	 * @param goodsid
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月26日 上午9:06:54   
	 * @return 
	 * @see com.ahpu.erp.dao.dao.IGoodsDao#findPriceById(java.lang.String)
	 */
	public List<Goods> findPriceById(String goodsid) {
		String hql ="FROM Goods where id =?";
		List<Goods> list = (List<Goods>) this.getHibernateTemplate().find(hql, goodsid);
		return list;
	}

	/**
	 * Title: goodsPurchaseNumUpdate 
	 * Description: 商品采购频度维护  
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月2日 上午9:21:24   
	 * @see com.ahpu.erp.dao.dao.IGoodsDao#goodsPurchaseNumUpdate()
	 */
	public void goodsPurchaseNumUpdate() {
		String hql = "UPDATE Goods g SET g.purchaseNum =(SELECT COUNT(od.id) FROM OperDetail od WHERE g.id = od.goods.id AND od.type =?)";
		this.getHibernateTemplate().bulkUpdate(hql,OperDetail.OPER_TYPE_OF_IN);
	}


	/**
	 * Title: getWarnInfo 
	 * Description:获取库存预警信息   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月2日 上午10:59:25   
	 * @see com.ahpu.erp.dao.dao.IGoodsDao#getWarnInfo()
	 */
	public List<Object[]> getWarnInfo() {
		String sql = "SELECT g.name,sum(sd.num)>g.maxNum,sum(sd.num)<g.minNum FROM tbl_goods g,tbl_storedetail sd WHERE sd.goodsUuid = g.id GROUP BY sd.goodsUuid";
		SessionFactory sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session currentSession = sessionFactory.getCurrentSession();
		SQLQuery sq = currentSession.createSQLQuery(sql);
		return sq.list();
	}
}
