package com.ahpu.erp.action.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.domain.Goodstype;
import com.ahpu.erp.service.service.IGoodsService;
import com.ahpu.erp.service.service.IGoodstypeService;
import com.ahpu.erp.utils.PinYin4jUtils;
import com.ahpu.erp.utils.UUIDutil;
import com.opensymphony.xwork2.ActionContext;

/**
 * ProjectName：erp-web ClassName：GoodsAction Description：商品信息管理action
 * 
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1 Create Date：2018年1月23日 下午6:44:07
 */
@Controller
@Scope("prototype")
public class GoodsAction extends BaseAction<Goods> {

	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IGoodstypeService goodstypeService;

	private File file;
	private String fileContentType;
	private String fileFileName;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @Title: pageQuery
	 * @Description: 分页查询方法
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月25日 下午12:42:38
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		// 商品类别
		if (model.getGoodstype() != null && model.getGoodstype().getName() != null
				&& model.getGoodstype().getName().trim().length() > 0) {
			dc.createAlias("goodstype", "gt");
			dc.add(Restrictions.like("gt.name", "%" + model.getGoodstype().getName().trim() + "%"));
		}
		String name = model.getName();
		if (StringUtils.isNotBlank(name)) {
			dc.add(Restrictions.like("name", "%" + name + "%"));
		}
		String origin = model.getOrigin();
		if (StringUtils.isNotBlank(origin)) {
			dc.add(Restrictions.like("origin", "%" + origin + "%"));
		}
		String producer = model.getProducer();
		if (StringUtils.isNotBlank(producer)) {
			dc.add(Restrictions.like("producer", "%" + producer + "%"));
		}
		Double inPrice = model.getInPrice();
		Double inPriceMax = model.getInPriceMax();
		if (inPrice != null) {
			dc.add(Restrictions.ge("inPrice", inPrice));
		}
		if (inPriceMax != null) {
			dc.add(Restrictions.le("inPrice", inPriceMax));
		}
		Double outPrice = model.getOutPrice();
		Double outPriceMax = model.getOutPriceMax();
		if (outPrice != null) {
			dc.add(Restrictions.ge("outPrice", outPrice));
		}
		if (outPriceMax != null) {
			dc.add(Restrictions.le("outPrice", outPriceMax));
		}
		goodsService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "currentPage", "detachedCriteria", "pageSize", "supplier" });
		return NONE;
	}

	/**
	 * @Title: add
	 * @Description: 商品保存
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月25日 下午12:43:22
	 * @throws IOException 
	 */
	public String add() throws IOException {
		String path = ServletActionContext.getServletContext().getRealPath("/goods");
		String realPath = path + "\\" + fileFileName;
		//String newRealpath= newpath + "\\" + imageFileName;
		if(file != null){
		File diskFile = new File(realPath);
		FileUtils.copyFile(file, diskFile);
		//String suffix = fileFileName.substring(fileFileName.lastIndexOf("."+1));
		//System.out.println(suffix);
		model.setImage("goods/" + fileFileName);
		}
		String[] headByString = PinYin4jUtils.getHeadByString(model.getName());
		String shortcode = StringUtils.join(headByString);
		model.setShortcode(shortcode);
		goodsService.save(model);
		return LIST;
	}

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @Title: delete
	 * @Description: 删除选中的商品
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月25日 下午12:45:51
	 */
	public String delete() {
		goodsService.delete(ids);
		return LIST;
	}

	/**
	 * @Title: edit
	 * @Description: 保存编辑数据
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 上午8:43:33
	 */
	public String edit() {
		Goods goods = goodsService.findById(model.getId());
		String[] headByString = PinYin4jUtils.getHeadByString(model.getName());
		String shortcode = StringUtils.join(headByString);
		goods.setName(model.getName());
		goods.setGoodstype(model.getGoodstype());
		goods.setInPrice(model.getInPrice());
		goods.setOrigin(model.getOrigin());
		goods.setOutPrice(model.getOutPrice());
		goods.setProducer(model.getProducer());
		goods.setDescription(model.getDescription());
		goods.setShortcode(shortcode);
		goods.setOperTime(new Date());
		goodsService.update(goods);
		return LIST;
	}
	
	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * @Title: listajax
	 * @Description: 根据ajax请求获取数据
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 上午8:44:25
	 */
	public String listajax() {
		List<Goods> list = null;
		if(StringUtils.isNotBlank(q)){
			list = goodsService.findAllByQ(q);
		}else{
			list = goodsService.findAll();
		}
		this.java2Json(list, new String[] { "supplier" });
		return NONE;
	}

	private String goodstypeid;

	public String getGoodstypeid() {
		return goodstypeid;
	}

	public void setGoodstypeid(String goodstypeid) {
		this.goodstypeid = goodstypeid;
	}

	/**
	 * @Title: listajaxById
	 * @Description: 根据商品类型ID查询对应商品
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 上午8:47:02
	 */
	public String listajaxById() {
		List<Goods> list = goodsService.findAllByID(goodstypeid);
		this.java2Json(list, new String[] { "supplier" });
		return NONE;
	}

	private String goodsid;

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	/**
	 * @Title: findPriceById
	 * @Description: 根据选中的商品动态加载单价
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 上午8:58:10
	 */
	public String findPriceById() {
		List<Goods> list = goodsService.findPriceById(goodsid);
		this.java2Json(list, new String[] { "supplier" });
		return NONE;
	}

	/**
	 * @Title: exportXls
	 * @Description: 导出数据
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年2月2日 下午12:08:29
	 * @throws IOException
	 */
	public String exportXls() throws IOException {
		List<Goods> goodsList = goodsService.findAll();
		// 2.使用POI技术将数据写入excel表格
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个标签页
		HSSFSheet sheet = workbook.createSheet("商品数据");
		// 3.创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("商品编号");
		headRow.createCell(1).setCellValue("商品名称");
		headRow.createCell(2).setCellValue("商品类别");
		headRow.createCell(3).setCellValue("生产厂家");
		headRow.createCell(4).setCellValue("生产厂地");
		headRow.createCell(5).setCellValue("进货价格");
		headRow.createCell(6).setCellValue("售货价格");
		headRow.createCell(7).setCellValue("最小库存");
		headRow.createCell(8).setCellValue("最大库存");
		headRow.createCell(9).setCellValue("商品描述");
		for (Goods goods : goodsList) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(goods.getId());
			dataRow.createCell(1).setCellValue(goods.getName());
			dataRow.createCell(2).setCellValue(goods.getGoodstype().getName());
			dataRow.createCell(3).setCellValue(goods.getProducer());
			dataRow.createCell(4).setCellValue(goods.getOrigin());
			dataRow.createCell(5).setCellValue(goods.getInPrice());
			dataRow.createCell(6).setCellValue(goods.getOutPrice());
			dataRow.createCell(7).setCellValue(goods.getMinNum());
			dataRow.createCell(8).setCellValue(goods.getMaxNum());
			dataRow.createCell(9).setCellValue(goods.getDescription());
		}
		// 5.使用文件输出流进行文件下载
		String filename = "商品数据.xls";
		String contentType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contentType);
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = com.ahpu.erp.utils.FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename=" + filename);

		workbook.write(out);
		return NONE;
	}
	
	private File goodsFile;
	public void setGoodsFile(File goodsFile) {
		this.goodsFile = goodsFile;
	}

	/**
	* @Title: importXls
	* @Description: 导入商品数据
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年2月2日 下午3:53:46
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String importXls() throws FileNotFoundException, IOException{
		List<Goods> goodsList = new ArrayList<Goods>();
	
		//使用POI解析excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(goodsFile));
		//根据名称获取指定的sheet对象
		HSSFSheet hssfSheet = workbook.getSheet("商品数据");
		//获取数据并排除标题行
		for(Row row : hssfSheet){
			int rowNum = row.getRowNum();
			if(rowNum == 0){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String name = row.getCell(1).getStringCellValue();
			String gtype = row.getCell(2).getStringCellValue();
			Goodstype goodstype = goodstypeService.findByName(gtype);
			gtype = goodstype.getId();
			String producer = row.getCell(3).getStringCellValue();
			String origin = row.getCell(4).getStringCellValue();
			Double inPrice = row.getCell(5).getNumericCellValue();
			Double outPrice = row.getCell(6).getNumericCellValue();
			
			Integer minNum = (int) row.getCell(7).getNumericCellValue();
			Integer maxNum = (int) row.getCell(8).getNumericCellValue();
			String description = row.getCell(9).getStringCellValue();
			
			//包装一个商品对象
			Goods goods = new Goods(id, name, origin, producer, inPrice, outPrice, minNum, maxNum, description, goodstype);
			goods.setDeltag("0");
			goods.setOperTime(new Date());
			goods.setPurchaseNum(0);
			goodsList.add(goods);
		}
		goodsService.saveBatch(goodsList);
		return LIST;
	}
}
