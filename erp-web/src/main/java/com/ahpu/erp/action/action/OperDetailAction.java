package com.ahpu.erp.action.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.OperDetail;
import com.ahpu.erp.service.service.IOperDetailService;
import com.ahpu.erp.utils.FileUtils;

@Controller
@Scope("prototype")
public class OperDetailAction extends BaseAction<OperDetail> {

	@Autowired
	private IOperDetailService operDetailService;

	/**
	 * @Title: pageQuery
	 * @Description: 库存明细查询
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年2月1日 下午5:50:04
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		// 仓库名称
		if (model.getStore() != null && model.getStore().getName() != null
				&& model.getStore().getName().trim().length() > 0) {
			dc.createAlias("store", "s");
			dc.add(Restrictions.like("s.name", "%" + model.getStore().getName().trim() + "%"));
		}
		// 操作类别
		Integer type = model.getType();
		if (type != null && type != -1) {
			dc.add(Restrictions.eq("type", type));
		}
		// 操作时间
		Date operTime = model.getOperTime();
		Date operTimeLst = model.getLastOperTime();
		if (operTime != null) {
			dc.add(Restrictions.ge("operTime", operTime));
		}
		if (operTimeLst != null) {
			dc.add(Restrictions.le("operTime", operTimeLst));
		}
		// 操作人
		if (model.getEmp() != null && model.getEmp().getName() != null
				&& model.getEmp().getName().trim().length() > 0) {
			dc.createAlias("emp", "e");
			dc.add(Restrictions.like("e.name", "%" + model.getEmp().getName().trim() + "%"));
		}
		// 货物名称
		if (model.getGoods() != null && model.getGoods().getName() != null
				&& model.getGoods().getName().trim().length() > 0) {
			dc.createAlias("goods", "g");
			dc.add(Restrictions.like("g.name", "%" + model.getGoods().getName().trim() + "%"));
		}
		// 数量
		Integer num = model.getNum();
		Integer maxNum = model.getMaxNum();
		if (num != null) {
			dc.add(Restrictions.ge("num", num));
		}
		if (maxNum != null) {
			dc.add(Restrictions.le("num", maxNum));
		}
		operDetailService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {});
		return NONE;
	}

	/**
	 * @Title: exportXls
	 * @Description: 导出库存操作明细数据
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年2月1日 下午6:54:19
	 * @throws IOException
	 */
	public String exportXls() throws IOException {
		// 获取数据
		List<OperDetail> operList = operDetailService.findAll();
		// 2.使用POI技术将数据写入excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("仓库库存操作明细数据");
		// 3.创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("仓库名称");
		headRow.createCell(1).setCellValue("操作类别");
		headRow.createCell(2).setCellValue("操作时间");
		headRow.createCell(3).setCellValue("操作人");
		headRow.createCell(4).setCellValue("货物名称");
		headRow.createCell(5).setCellValue("货物数量");
		for (OperDetail operDetail : operList) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(operDetail.getStore().getName());
			dataRow.createCell(1).setCellValue(operDetail.getTypeView());
			dataRow.createCell(2).setCellValue(operDetail.getOperTimeView());
			dataRow.createCell(3).setCellValue(operDetail.getEmp().getName());
			dataRow.createCell(4).setCellValue(operDetail.getGoods().getName());
			dataRow.createCell(5).setCellValue(operDetail.getNum());
		}
		// 5.使用文件输出流进行文件下载
		String filename = "仓库库存操作明细数据.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contentType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);

		workbook.write(out);
		return NONE;
	}
}
