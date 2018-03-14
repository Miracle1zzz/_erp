package com.ahpu.erp.dao.base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.ahpu.erp.utils.PageBean;


/**
 * ProjectName：bos-dao
 * ClassName：IBaseDao
 * Description：通用dao抽取
 * @author：Miracle 
 * Create Date：2017年12月21日 上午10:40:23
 */
public interface IBaseDao<T> {
	
	public void save(T entity);
	
	public void delete(T entity);
	
	public void update(T entity);
	
	public T findById(Serializable id);
	
	public List<T> findAll();
	
	public void executeUpdate(String queryName,Object...objects);

	public void pageQuery(PageBean pageBean);
	
	public void saveOrUpdate(T entity);
	
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
	
	public List<T> findListByQ(String q);
	
}
