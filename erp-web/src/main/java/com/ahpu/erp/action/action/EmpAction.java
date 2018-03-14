package com.ahpu.erp.action.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ahpu.erp.action.base.BaseAction;
import com.ahpu.erp.domain.Dep;
import com.ahpu.erp.domain.Emp;
/**
 * ProjectName：erp-web
 * ClassName：EmpAction
 * Description：员工信息处理action
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1
 * Create Date：2018年1月21日 上午10:56:16
 */
import com.ahpu.erp.service.service.IEmpService;
import com.ahpu.erp.utils.AppException;
import com.ahpu.erp.utils.EmpUtils;
import com.ahpu.erp.utils.MD5Utils;

@Controller
@Scope("prototype")
public class EmpAction extends BaseAction<Emp> {

	private String checkcode;// 页面验证码
	@Autowired
	private IEmpService empService;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	/**
	 * @Title: login
	 * @Description: 员工登录
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 上午11:17:18
	 */
	public String login() {

		// 检验验证码是否正确
		// 从session中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		// 判断输入值是否正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)) {
			// 使用shiro框架提供的方式进行认证操作
			Subject subject = SecurityUtils.getSubject();
			String password = MD5Utils.md5(model.getPassword());
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), password);
			try {
				subject.login(token);
			}  catch (IncorrectCredentialsException e1) {
				this.addActionError("用户名或者密码错误！");
				return LOGIN;
			}catch (UnknownAccountException e) {
				this.addActionError("用户名或密码不能为空！");
				return LOGIN;
			} catch (Exception e2) {
				throw new AppException("出错啦！请耐心等待！");
			}
			Emp emp = (Emp) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute(Emp.EMP_LOGIN_USER_OBJECT_NAME, emp);
			return HOME;
		} else {
			// 输入验证码错误
			this.addActionError("输入验证码错误！");
			return LOGIN;
		}
	}

	/**
	 * @Title: editPassword
	 * @Description: 修改密码
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午12:40:40
	 * @throws IOException
	 */
	public String editPassword() throws IOException {
		String flag = "1";
		Emp emp = EmpUtils.getLoginUser();
		try {
			empService.editPassword(emp.getId(), model.getPassword());

		} catch (Exception e) {
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}

	/**
	 * @Title: logout
	 * @Description: 退出系统
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午12:41:35
	 */
	public String logout() {
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}

	/**
	 * @Title: pageQuery
	 * @Description: 分页查询
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午3:17:06
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String username = model.getUsername();
		if(StringUtils.isNotBlank(username)){
			dc.add(Restrictions.like("username", "%"+username+"%"));
		}
		String name = model.getName();
		if(StringUtils.isNotBlank(name)){
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		String telephone = model.getTelephone();
		if(StringUtils.isNotBlank(telephone)){
			dc.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		Integer gender = model.getGender();
		if(gender != null && gender != -1){
			dc.add(Restrictions.eq("gender",gender));
		}
		String email = model.getEmail();
		if(StringUtils.isNotBlank(email)){
			dc.add(Restrictions.like("email", "%"+email+"%"));
		}
		/*Dep dep = model.getDep();
		if(dep != null && dep.getId() != null){
			dc.add(Restrictions.eq("dep",dep));
		}*/
		Date birthday = model.getBirthday();
		Date lastbirthday = model.getLastbirthday();
		if(birthday != null){
			dc.add(Restrictions.ge("birthday", birthday));
		}
		if(lastbirthday != null){
			dc.add(Restrictions.le("birthday", lastbirthday));
		}
		empService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[] { "currentPage", "detachedCriteria", "pageSize","roles" });
		return NONE;
	}

	// 属性驱动，接收多个角色id
	private String[] roleIds;

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	/**
	 * @Title: add
	 * @Description: 添加员工信息
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午3:17:58
	 */
	public String add() {
		empService.save(model,roleIds);
		return LIST;
	}

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * @Title: deleteBatch
	 * @Description: 删除员工信息
	 * @return String
	 * @Company：AHPU
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月21日 下午3:36:05
	 */
	public String deleteBatch() {
		empService.delete(ids);
		return LIST;
	}
	/**
	* @Title: listajax
	* @Description: 根据当前登陆人查询其部门所有人
	* @return String
	* @Company：AHPU
	* @author：段乐
	* @version 1.0.1
	* Create Date：2018年1月31日 下午12:26:20
	 */
	public String listajax(){
		List<Emp> list = empService.findAllByDep(EmpUtils.getLoginUser().getDep().getId());
		this.java2Json(list, new String[]{"roles"});
		return NONE;
	}
}
