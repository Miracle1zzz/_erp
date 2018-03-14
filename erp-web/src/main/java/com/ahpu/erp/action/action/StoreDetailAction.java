package com.ahpu.erp.action.action;

import java.io.IOException;
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
import com.ahpu.erp.domain.StoreDetail;
import com.ahpu.erp.service.service.IStoreDetailService;
import com.ahpu.erp.utils.FileUtils;

@Controller
@Scope("prototype")
public class StoreDetailAction extends BaseAction<StoreDetail> {

	@Autowired
	private IStoreDetailService storeDetailService;

	/**
	 * @Title: pageQuery
	 * @Description: 查询库存量
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年2月1日 上午10:43:08 
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		if (model.getStore() != null && model.getStore().getName() != null
				&& model.getStore().getName().trim().length() > 0) {
			dc.createAlias("store", "s");
			dc.add(Restrictions.like("s.name", "%" + model.getStore().getName() + "%"));

		}
		if (model.getGoods() != null && model.getGoods().getName() != null
				&& model.getGoods().getName().trim().length() > 0) {
			dc.createAlias("goods", "g");
			dc.add(Restrictions.like("g.name", "%" + model.getGoods().getName() + "%"));
		}
		storeDetailService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] {});
		return NONE;
	}

	/**
	 * @Title: exportXls
	 * @Description: 将当前仓库明细数据导出
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年2月1日 上午11:58:50
	 * @throws IOException 
	 */
	public String exportXls() throws IOException {
		// 获取数据
		List<StoreDetail> list = storeDetailService.findAll();
		// 2.使用POI技术将数据写入excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("仓库明细数据");
		// 3.创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("仓库名称");
		headRow.createCell(1).setCellValue("商品名称");
		headRow.createCell(2).setCellValue("当前库存量");
		headRow.createCell(3).setCellValue("仓库位置");
		//遍历数据
		for(StoreDetail detail : list){
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(detail.getStore().getName());
			dataRow.createCell(1).setCellValue(detail.getGoods().getName());
			dataRow.createCell(2).setCellValue(detail.getNum());
			dataRow.createCell(3).setCellValue(detail.getStore().getAddress());
		}
		//5.使用文件输出流进行文件下载
		String filename = "仓库明细数据.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contentType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		
		workbook.write(out);
		return NONE;
	}
	/**
	* @Title: findGoodsGroupByStore
	* @Description: 查询商品分布数据
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月1日 下午2:50:31
	 */
	public String findGoodsGroupByStore(){
		List<Object> list = storeDetailService.findGoodsGroupByStore();
		this.java2Json(list, new String[]{});
		return NONE;
	}
}
