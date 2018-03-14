package com.ahpu.erp.service.service;

import java.util.List;

import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.utils.PageBean;

public interface IGoodsService {

	public void pageQuery(PageBean pageBean);

	public void save(Goods model);

	public void delete(String ids);

	public Goods findById(String id);

	public void update(Goods goods);

	public List<Goods> findAll();

	public List<Goods> findAllByID(String goodstypeid);

	public List<Goods> findPriceById(String goodsid);

	public List<Object[]> getWarnInfo();

	public void saveBatch(List<Goods> goodsList);

	public List<Goods> findAllByQ(String q);

}
