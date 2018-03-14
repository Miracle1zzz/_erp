package com.ahpu.erp.action.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Notice;
import com.ahpu.erp.service.service.INoticeService;

/**
 * ProjectName：erp-web
 * ClassName：NoticeAction
 * Description：工告信息管理
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月30日 上午9:34:15
 */
@Controller
@Scope("prototype")
public class NoticeAction extends BaseAction<Notice>{

	@Autowired
	private INoticeService noticeService;
	
	/**
	* @Title: pageQuery
	* @Description: 工告信息分页查询
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月30日 下午2:38:55
	 */
	public String pageQuery(){
		noticeService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{});
		return NONE;
	}
}
