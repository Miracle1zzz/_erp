package com.ahpu.erp.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.ahpu.erp.dao.base.dao.IBaseDao;
import com.ahpu.erp.utils.PageBean;


/**
 * 
 * ProjectName：bos-dao ClassName：BaseDaoImpl Description：持久层通用实现类
 * 
 * @author：Miracle Create Date：2017年12月21日 上午10:45:52
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	

	private Class<T> entityClass;

	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	// 在构造方法中动态获取entityClass
	public BaseDaoImpl() {
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		entityClass = (Class<T>) actualTypeArguments[0];
	}

	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	public T findById(Serializable id) {

		return this.getHibernateTemplate().get(entityClass, id);
	}

	public List<T> findAll() {
		String hql = "FROM " + entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		//为hql语句中？赋值
		int i = 0;
		for (Object object : objects) {
			query.setParameter(i++,object);
		}
		//执行更新
		query.executeUpdate();
	}
	/**
	* @Title: pageQueryOfSerach
	* @Description: 分页查询通用方法
	* @return void
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月23日 上午10:45:45
	 */
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();
		int pageSize = pageBean.getPageSize();
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//查询total总数据量
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long count = countList.get(0);
		pageBean.setTotal(count.intValue());
		
		//查询rows----当前页需要展示的数据集合
		detachedCriteria.setProjection(null);
		//指定hibernate查询数据时封装方式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}
	
	
	/**
	 * 
	 * Title: saveOrUpdate 
	 * Description: 保存或更新  
	 * @param entity   
	 * @see com.ahpu.bos.dao.base.dao.IBaseDao#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * Title: findByCriteria 
	 * @param detachedCriteria
	 * @return   
	 * @see com.ahpu.bos.dao.base.dao.IBaseDao#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
	/**
	 * Title: findListByQ 
	 * Description: 根据q参数进行模糊查询     
	 * @param q
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月25日 上午9:02:23   
	 * @see com.ahpu.erp.dao.base.dao.IBaseDao#findListByQ(java.lang.String)
	 */
	public List<T> findListByQ(String q) {
		String hql = "FROM "+entityClass.getSimpleName()+"t WHERE t.shortcode LIKE ?";
		List<T> list = (List<T>) this.getHibernateTemplate().find(hql, "%"+q+"%");
		return list;
	}

}
