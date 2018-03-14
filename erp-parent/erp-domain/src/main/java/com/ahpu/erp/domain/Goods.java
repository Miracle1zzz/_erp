package com.ahpu.erp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ahpu.erp.util.FormatUtil;

/**
 * ProjectName：erp-domain
 * ClassName：Goods
 * Description：商品模块实体
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年2月2日 下午6:04:09
 */
public class Goods {
	private String id;
	private String name;
	private String origin;
	private String producer;
	private Double inPrice;
	private Double outPrice;
	private Double inPriceMax;
	private Double outPriceMax;
	private Integer purchaseNum;
	private Integer minNum;
	private Integer maxNum;
	private Date operTime;
	private String shortcode;
	
	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getOperTimeView(){
		if(operTime != null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(operTime);
			return format;
		}else{
			return "暂无数据";
		}
	}
	

	public Goods(String id, String name, String origin, String producer, Double inPrice, Double outPrice,
			Double inPriceMax, Double outPriceMax, Integer purchaseNum, Integer minNum, Integer maxNum, Date operTime,
			String inPriceView, String outPriceView, String deltag, String description, String image,
			Goodstype goodstype, Supplier supplier) {
		super();
		this.id = id;
		this.name = name;
		this.origin = origin;
		this.producer = producer;
		this.inPrice = inPrice;
		this.outPrice = outPrice;
		this.inPriceMax = inPriceMax;
		this.outPriceMax = outPriceMax;
		this.purchaseNum = purchaseNum;
		this.minNum = minNum;
		this.maxNum = maxNum;
		this.operTime = operTime;
		this.inPriceView = inPriceView;
		this.outPriceView = outPriceView;
		this.deltag = deltag;
		this.description = description;
		this.image = image;
		this.goodstype = goodstype;
		this.supplier = supplier;
	}
	

	public Goods(String id, String name, String origin, String producer, Double inPrice, Double outPrice,
			Integer minNum, Integer maxNum, String description, Goodstype goodstype) {
		super();
		this.id = id;
		this.name = name;
		this.origin = origin;
		this.producer = producer;
		this.inPrice = inPrice;
		this.outPrice = outPrice;
		this.minNum = minNum;
		this.maxNum = maxNum;
		this.description = description;
		this.goodstype = goodstype;
	}

	//default
	public Goods() {
		
	}

	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", origin=" + origin + ", producer=" + producer + ", inPrice="
				+ inPrice + ", outPrice=" + outPrice + ", inPriceMax=" + inPriceMax + ", outPriceMax=" + outPriceMax
				+ ", purchaseNum=" + purchaseNum + ", minNum=" + minNum + ", maxNum=" + maxNum + ", operTime="
				+ operTime + ", inPriceView=" + inPriceView + ", outPriceView=" + outPriceView + ", deltag=" + deltag
				+ ", description=" + description + ", image=" + image + ", goodstype=" + goodstype + ", supplier="
				+ supplier + "]";
	}

	public Integer getPurchaseNum() {
		return purchaseNum;
	}

	public void setPurchaseNum(Integer purchaseNum) {
		this.purchaseNum = purchaseNum;
	}

	private String inPriceView;
	private String outPriceView;
	
	public String getInPriceView() {
		return inPriceView;
	}

	public String getOutPriceView() {
		return outPriceView;
	}

	public Double getInPriceMax() {
		return inPriceMax;
	}

	public void setInPriceMax(Double inPriceMax) {
		this.inPriceMax = inPriceMax;
	}

	public Double getOutPriceMax() {
		return outPriceMax;
	}

	public void setOutPriceMax(Double outPriceMax) {
		this.outPriceMax = outPriceMax;
	}

	private String deltag = "0";
	private String description;
	private String image;
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	private Goodstype goodstype;
	private Supplier supplier;
	
	public Double getInPrice() {
		return inPrice;
	}

	public void setInPrice(Double inPrice) {
		this.inPrice = inPrice;
		this.inPriceView = FormatUtil.formatMoney(inPrice);
	}

	public Double getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(Double outPrice) {
		this.outPrice = outPrice;
		this.outPriceView = FormatUtil.formatMoney(outPrice);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeltag() {
		return deltag;
	}

	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}

	public Goodstype getGoodstype() {
		return goodstype;
	}

	public void setGoodstype(Goodstype goodstype) {
		this.goodstype = goodstype;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

}	
