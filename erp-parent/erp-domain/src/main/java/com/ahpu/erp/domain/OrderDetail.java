package com.ahpu.erp.domain;

import com.ahpu.erp.util.FormatUtil;

public class OrderDetail {
	private String id;
	
	private Integer num;
	private Integer surplus;

	private Double price;
	
	private String priceView;
	private String totalPriceView;
	
	private Goods goods;
	private Order order;
	
	private String deltag = "0";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	public Integer getSurplus() {
		return surplus;
	}
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	public String getTotalPriceView() {
		return totalPriceView;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
		this.priceView = FormatUtil.formatMoney(price);
		this.totalPriceView = FormatUtil.formatMoney(num*price);
	}
	public String getPriceView() {
		return priceView;
	}
	
	
}
