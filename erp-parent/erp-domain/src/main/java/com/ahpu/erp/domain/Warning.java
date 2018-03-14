package com.ahpu.erp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName：erp-domain
 * ClassName：Warning
 * Description：预警信息
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月30日 下午2:28:37
 */
public class Warning {
	
	public static final Integer WARNING_PRIORITY_OF_HINGE = 1;
	public static final Integer WARNING_PRIORITY_OF_MAJOR = 2;
	public static final Integer WARNING_PRIORITY_OF_MEDIUM = 3;
	public static final Integer WARNING_PRIORITY_OF_SORT = 4;
	
	public static final String WARNING_PRIORITY_OF_HINGE_VIEW = "关键";
	public static final String WARNING_PRIORITY_OF_MAJOR_VIEW = "重要";
	public static final String WARNING_PRIORITY_OF_MEDIUM_VIEW = "中等";
	public static final String WARNING_PRIORITY_OF_SORT_VIEW = "一般";
	
	public static final Map<Integer, String> priorityMap = new HashMap<Integer, String>();
	
	static{
		priorityMap.put(WARNING_PRIORITY_OF_HINGE, WARNING_PRIORITY_OF_HINGE_VIEW);
		priorityMap.put(WARNING_PRIORITY_OF_MAJOR, WARNING_PRIORITY_OF_MAJOR_VIEW);
		priorityMap.put(WARNING_PRIORITY_OF_MEDIUM, WARNING_PRIORITY_OF_MEDIUM_VIEW);
		priorityMap.put(WARNING_PRIORITY_OF_SORT, WARNING_PRIORITY_OF_SORT_VIEW);
	}
	
	private String id;
	private Integer priority;
	private String priorityView;
	private Emp sender;
	private Date senddate;
	private String subject;
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	private String content;
	private String deltag = "0";
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	public String getSenddateView(){
		if(senddate != null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(senddate);
			return format;
		}else{
			return "暂无数据";
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPriority() {
		return priority;
	}
	public String getPriorityView() {
		return priorityView;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
		this.priorityView = priorityMap.get(priority);
	}

	public Emp getSender() {
		return sender;
	}
	public void setSender(Emp sender) {
		this.sender = sender;
	}
	public Date getSenddate() {
		return senddate;
	}
	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
