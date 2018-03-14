package com.ahpu.erp.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ahpu.erp.util.FormatUtil;

public class Notice {
	public static final Integer NOTICE_PRIORITY_OF_HINGE = 1;
	public static final Integer NOTICE_PRIORITY_OF_MAJOR = 2;
	public static final Integer NOTICE_PRIORITY_OF_MEDIUM = 3;
	public static final Integer NOTICE_PRIORITY_OF_SORT = 4;
	
	public static final String NOTICE_PRIORITY_OF_HINGE_VIEW = "关键";
	public static final String NOTICE_PRIORITY_OF_MAJOR_VIEW = "重要";
	public static final String NOTICE_PRIORITY_OF_MEDIUM_VIEW = "中等";
	public static final String NOTICE_PRIORITY_OF_SORT_VIEW = "一般";
	
	public static final Map<Integer, String> priorityMap = new HashMap<Integer, String>();
	
	static{
		priorityMap.put(NOTICE_PRIORITY_OF_HINGE, NOTICE_PRIORITY_OF_HINGE_VIEW);
		priorityMap.put(NOTICE_PRIORITY_OF_MAJOR, NOTICE_PRIORITY_OF_MAJOR_VIEW);
		priorityMap.put(NOTICE_PRIORITY_OF_MEDIUM, NOTICE_PRIORITY_OF_MEDIUM_VIEW);
		priorityMap.put(NOTICE_PRIORITY_OF_SORT, NOTICE_PRIORITY_OF_SORT_VIEW);
	}
	
	private String id;
	private Integer priority;
	private String priorityView;
	private Emp despatcher;
	private Date time;
	
	private String title;
	private String content;
	private String deltag = "0";
	public String getDeltag() {
		return deltag;
	}
	public void setDeltag(String deltag) {
		this.deltag = deltag;
	}
	public String getTimeView(){
		if(time != null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(time);
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
	public Emp getDespatcher() {
		return despatcher;
	}
	public void setDespatcher(Emp despatcher) {
		this.despatcher = despatcher;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
