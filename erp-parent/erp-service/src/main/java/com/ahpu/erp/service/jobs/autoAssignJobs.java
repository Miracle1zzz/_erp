package com.ahpu.erp.service.jobs;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.ahpu.erp.dao.dao.IGoodsDao;
import com.ahpu.erp.service.service.IGoodsService;
import com.ahpu.erp.util.FormatUtil;

public class autoAssignJobs {

	@Resource
	private IGoodsDao goodsDao;
	@Autowired
	private IGoodsService goodsService;
	private MailSender mailSender;;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	/**
	* @Title: goodsPurchaseNumUpdate
	* @Description: 商品采购频度维护  
	* @return void
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月2日 上午9:22:06
	 */
	public void goodsPurchaseNumUpdate(){
		goodsDao.goodsPurchaseNumUpdate();
	}
	/**
	* @Title: storeWarn
	* @Description: 库存预警
	* @return void
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月2日 上午9:53:08
	 */
	public void storeWarn(){
		List<Object[]> warnList = goodsService.getWarnInfo();
		//组织要发送的Email信息内容
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setFrom("13170036608@163.com");
		
		simpleMessage.setTo("kevl5sha@163.com");
		
		simpleMessage.setSubject("库存预警【"+FormatUtil.formatDateTime(System.currentTimeMillis())+"】");
		
		StringBuilder info = new StringBuilder();
		for(Object[] objects : warnList){
			BigInteger maxFlag = (BigInteger) objects[1];
			if(maxFlag.intValue() == 1){
				String name = objects[0].toString();
				info.append("商品【");
				info.append(name);
				info.append("】库存超过上限，请停止补货！\r\n");
				continue;
			}
			BigInteger minFlag = (BigInteger) objects[2];
			if(minFlag.intValue() == 1){
				String name = objects[0].toString();
				info.append("商品【");
				info.append(name);
				info.append("】库存低于下限，请及时补货！\r\n");
				
			}
		}
		simpleMessage.setText(info.toString());
		
		simpleMessage.setSentDate(new Date());
		
		//发送email
		mailSender.send(simpleMessage);
		
		System.out.println("-----------------------------");
		System.out.println("-----------------------------");
		System.out.println(mailSender);
		System.out.println("-----------------------------");
		System.out.println("-----------------------------");
	}
}
