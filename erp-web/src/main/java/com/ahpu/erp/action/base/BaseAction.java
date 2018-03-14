package com.ahpu.erp.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.ahpu.erp.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ProjectName：bos-web 
 * ClassName：BaseAction 
 * Description：表现层代码抽取
 * @author：Miracle Create Date：2017年12月21日 上午11:13:45
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected PageBean pageBean = new PageBean();

	// 创建离线查询对象
	DetachedCriteria detachedCriteria = null;
	public static final String HOME = "home";
	public static final String LIST = "list";
	public static final String ERROR = "error";
	// 模型对象
	protected T model;

	public T getModel() {
		return model;
	}

	// 创建构造方法，通过反射创建model对象
	public BaseAction() {
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = superclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: java2Json
	* @Description: 将指定Java对象转为json，并响应到客户端
	* @param @param o
	* @param @param excludes
	* @return void
	* @Company：AHPU
	* @author：Miracle 
	* @version 1.0.1
	* Create Date：2018年1月12日 下午1:58:07
	 */
	public void java2Json(Object o, String[] excludes){
		// 去掉不想要的数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);

		// 将数据转为json
		String json = JSONObject.fromObject(o, jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 
	* @Title: java2Json
	* @Description: 将指定Java对象转为json，并响应到客户端
	* @param list
	* @param excludes
	* @return void
	* @Company：AHPU
	* @author：Miracle 
	* @version 1.0.1
	* Create Date：2018年1月12日 下午2:25:03
	 */
	public void java2Json(List list, String[] excludes){
		// 去掉不想要的数据
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);

		// 将数据转为json
		String json = JSONArray.fromObject(list, jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

}
