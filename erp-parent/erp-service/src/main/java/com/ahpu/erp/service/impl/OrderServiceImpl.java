package com.ahpu.erp.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahpu.erp.dao.dao.IOperDetailDao;
import com.ahpu.erp.dao.dao.IOrderDao;
import com.ahpu.erp.dao.dao.IStaffDao;
import com.ahpu.erp.dao.dao.IStoreDao;
import com.ahpu.erp.dao.dao.IStoreDetailDao;
import com.ahpu.erp.domain.Emp;
import com.ahpu.erp.domain.Goods;
import com.ahpu.erp.domain.OperDetail;
import com.ahpu.erp.domain.Order;
import com.ahpu.erp.domain.OrderDetail;
import com.ahpu.erp.domain.Staff;
import com.ahpu.erp.domain.Store;
import com.ahpu.erp.domain.StoreDetail;
import com.ahpu.erp.domain.Supplier;
import com.ahpu.erp.service.service.IOrderService;
import com.ahpu.erp.utils.AppException;
import com.ahpu.erp.utils.EmpUtils;
import com.ahpu.erp.utils.NumUtil;
import com.ahpu.erp.utils.PageBean;

/**
 * ProjectName：erp-service ClassName：OrderServiceImpl Description：订单管理service实现类
 * 
 * @Company：AHPU
 * @author：段乐
 * @version 1.0.1 Create Date：2018年1月25日 下午2:42:29
 */
@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private IOrderDao orderDao;
	@Autowired
	private IStaffDao staffDao;
	@Autowired
	private IStoreDetailDao storeDetailDao;
	@Autowired 
	private IStoreDao storeDao;
	@Autowired
	private IOperDetailDao operDetailDao;

	/**
	 * Title: pageQuery Description:分页查询
	 * 
	 * @param pageBean
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月25日 下午3:32:57
	 * @see com.ahpu.erp.service.service.IOrderService#pageQuery(com.ahpu.erp.utils.PageBean)
	 */
	public void pageQuery(PageBean pageBean) {
		orderDao.pageQuery(pageBean);
	}

	/**
	 * Title: delete Description: 删除选中的订单项
	 * 
	 * @param ids
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月25日 下午5:59:03
	 * @see com.ahpu.erp.service.service.IOrderService#delete(java.lang.String)
	 */
	public void delete(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] orderId = ids.split(",");
			for (String id : orderId) {
				orderDao.executeUpdate("order.delete", id);
				;
			}
		}
	}

	/**
	 * Title: save Description:订单及订单明细生成
	 * 
	 * @param order
	 * @param goodsId
	 * @param nums
	 * @param price
	 * @param emp
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午8:53:49
	 * @see com.ahpu.erp.service.service.IOrderService#save(com.ahpu.erp.domain.Order,
	 *      java.lang.String, java.lang.Integer, java.lang.Double,
	 *      com.ahpu.erp.domain.Emp)
	 */
	public void save(Order order, String goodsId, Integer nums, Double price, Emp emp) {
		// 保存订单
		// 设置订单号
		String orderNum = NumUtil.generatorOrderNum();
		order.setOrderNum(orderNum);
		// 订单创建时间是当前系统时间
		order.setCreateTime(new Date());
		// 当前保存的是采购订单
		order.setOrderType(Order.ORDER_ORDERTYPE_OF_BUY);
		// 新保存的订单未审核
		order.setType(Order.ORDER_TYPE_OF_BUY_NO_CHECK);
		// 制单人
		order.setCreater(emp);
		// 设置订单中对应的所有订单明细
		Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setNum(nums);
		orderDetail.setPrice(price);
		Goods goods = new Goods();
		goods.setId(goodsId);
		orderDetail.setGoods(goods);
		orderDetail.setOrder(order);
		orderDetail.setDeltag("0");
		orderDetails.add(orderDetail);
		order.setOrderDtail(orderDetails);
		// 设置订单总数
		order.setTotalNum(nums);
		// 设置订单总价格
		order.setTotalPrice(nums * price);
		orderDao.save(order);
	}

	/**
	 * Title: findAll Description: 根据id查询明细
	 * 
	 * @param orderId
	 * @return
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午5:33:19
	 * @see com.ahpu.erp.service.service.IOrderService#findAll(java.lang.String)
	 */
	public List<Order> findAll(String orderId) {
		return orderDao.findAllById(orderId);
	}

	/**
	 * Title: buyCheckPass Description: 订单审核通过
	 * 
	 * @param id
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午8:28:13
	 * @see com.ahpu.erp.service.service.IOrderService#buyCheckPass(java.lang.String)
	 */
	public void buyCheckPass(String id, Emp checker) {
		// 使用快照更新
		Order order = orderDao.findById(id);
		// 逻辑校验
		if (!order.getType().equals(Order.ORDER_TYPE_OF_BUY_NO_CHECK)) {
			throw new AppException("对不起，请不要进行非法操作");
		}
		// 审核状态
		order.setType(Order.ORDER_TYPE_OF_BUY_CHECK_PASS);
		// 审核时间
		order.setCheckTime(new Date());
		// 审核人
		order.setChecker(checker);
		orderDao.update(order);
	}

	/**
	 * Title: buyCheckNoPass Description: 审核驳回
	 * 
	 * @param orderIds
	 * @param checker
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月26日 下午8:53:30
	 * @see com.ahpu.erp.service.service.IOrderService#buyCheckNoPass(java.lang.String,
	 *      com.ahpu.erp.domain.Emp)
	 */
	public void buyCheckNoPass(String orderIds, Emp checker) {
		// 使用快照更新
		Order order = orderDao.findById(orderIds);
		// 逻辑校验
		if (!order.getType().equals(Order.ORDER_TYPE_OF_BUY_NO_CHECK)) {
			throw new AppException("对不起，请不要进行非法操作");
		}
		// 审核状态
		order.setType(Order.ORDER_TYPE_OF_BUY_CHECK_NO_PASS);
		// 审核时间
		order.setCheckTime(new Date());
		// 审核人
		order.setChecker(checker);
		orderDao.update(order);
	}

	/**
	 * Title: assginStaff Description:指派运输任务
	 * 
	 * @param orderIds
	 * @param staff
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月28日 上午11:00:23
	 * @see com.ahpu.erp.service.service.IOrderService#assignTask(java.lang.String,
	 *      com.ahpu.erp.domain.Staff)
	 */
	public void assginStaff(String id, Staff staff) {
		// 使用快照更新
		Order order = orderDao.findById(id);
		// 逻辑校验
		if (!order.getType().equals(Order.ORDER_TYPE_OF_BUY_CHECK_PASS)) {
			throw new AppException("对不起，请不要进行非法操作");
		}
		// 设置状态
		order.setType(Order.ORDER_TYPE_OF_BUY_BUYING);
		// 设置取派员
		order.setCompleter(staff);
		orderDao.update(order);
		staffDao.executeUpdate("staff.assgin", id, staff.getId());
	}

	/**
	 * Title: assignTask Description: 结单
	 * 
	 * @param taskid
	 * @author：段乐
	 * @version 1.0.1 Create Date：2018年1月31日 下午2:31:27
	 * @see com.ahpu.erp.service.service.IOrderService#assignTask(java.lang.String)
	 */
	public void assignTask(String taskid) {
		// 使用快照更新
		Order order = orderDao.findById(taskid);
		// 逻辑校验
		if (!order.getType().equals(Order.ORDER_TYPE_OF_BUY_BUYING)) {
			throw new AppException("对不起，请不要进行非法操作");
		}
		// 设置状态
		order.setType(Order.ORDER_TYPE_OF_BUY_IN_STORE);
		orderDao.update(order);
	}

	/**
	 * Title: findById 
	 * Description:根据id查询   
	 * @param id
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年1月31日 下午4:08:13   
	 * @see com.ahpu.erp.service.service.IOrderService#findById(java.lang.String)
	 */
	public Order findById(String id) {
		return orderDao.findById(id);
	}

	/**
	 * Title: assginStore 
	 * Description:结单操作   
	 * @param id
	 * @param order
	 * @param detail
	 * @param storeIds
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 上午8:56:10   
	 * @see com.ahpu.erp.service.service.IOrderService#assginStore(java.lang.String, com.ahpu.erp.domain.Order, com.ahpu.erp.domain.OrderDetail, java.lang.String)
	 */
	public void assginStore(String id, OrderDetail detail, Store store) {
		
		Order order = orderDao.findById(id);
		//设置最后操作时间
		order.setEndTime(new Date());
		//设置仓库
		order.setStore(store);
		//设置状态---结单
		order.setType(Order.ORDER_TYPE_OF_BUY_COMPLETE);
		orderDao.update(order);
		//保存仓库明细,如果同一个仓库商品相同就追加数量
		StoreDetail storeDetail = storeDetailDao.getByStoreAndGoods(store.getId(),detail.getGoods().getId());
		if(storeDetail != null){
			storeDetail.setNum(detail.getNum()+storeDetail.getNum());
			storeDetailDao.update(storeDetail);
		}else{
			storeDetail = new StoreDetail();
			storeDetail.setGoods(detail.getGoods());
			storeDetail.setNum(detail.getNum());
			storeDetail.setStore(store);
			storeDetailDao.save(storeDetail);
		}
		
		//保存仓库操作日志
		OperDetail operDetail = new OperDetail();
		operDetail.setNum(detail.getNum());
		operDetail.setOperTime(new Date());
		operDetail.setType(OperDetail.OPER_TYPE_OF_IN);
		operDetail.setGoods(detail.getGoods());
		operDetail.setStore(store);
		operDetail.setEmp(EmpUtils.getLoginUser());
		
		operDetailDao.save(operDetail);
		
		//将司机任务解除
		Staff staff = staffDao.findByOrder(id);
		if(staff != null){
			staff.setOrder_id(null);
			staffDao.update(staff);
		}
	}

	/**
	 * Title: findAll 
	 * Description:查询所有订单数据   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月1日 下午12:55:42   
	 * @see com.ahpu.erp.service.service.IOrderService#findAll()
	 */
	public List<Order> findAll(Integer type) {
		return orderDao.findAll(type);
	}

	/**
	 * Title: inputSalesOrder 
	 * Description: 销售订单录入  
	 * @param supplier
	 * @param goods
	 * @param nums
	 * @param price
	 * @param loginUser
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月4日 下午12:21:25   
	 * @see com.ahpu.erp.service.service.IOrderService#inputSalesOrder(com.ahpu.erp.domain.Supplier, com.ahpu.erp.domain.Goods, java.lang.Integer, java.lang.Double, com.ahpu.erp.domain.Emp)
	 */
	public void inputSalesOrder(Supplier supplier, Goods goods, Integer nums, Double price, Emp loginUser) {
		Order order = new Order();
		//设置订单号
		String orderNum = NumUtil.generatorOrderNum();
		order.setOrderNum(orderNum);
		//订单创建时间是当前系统时间
		order.setCreateTime(new Date());
		//当前保存的是销售订单
		order.setOrderType(Order.ORDER_ORDERTYPE_OF_SALE);
		//新保存的订单未审核
		order.setType(Order.ORDER_TYPE_OF_SALE_NO_CHECK);
		//制单人
		order.setCreater(loginUser);
		//
		order.setSupplier(supplier);
		//设置订单中对应的明细
		Set<OrderDetail> orderDetails = new HashSet<OrderDetail>();
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setNum(nums);
		orderDetail.setPrice(price);
		orderDetail.setGoods(goods);
		orderDetail.setOrder(order);
		orderDetail.setDeltag("0");
		orderDetails.add(orderDetail);
		order.setOrderDtail(orderDetails);
		//设置订单总数
		order.setTotalNum(nums);
		//设置订单总价格
		order.setTotalPrice(price * nums);
		orderDao.save(order);
	}

	/**
	 * Title: findListNotAssociation 
	 * Description:查询未关联客户的销售订单   
	 * @return
	 * @author：段乐
	 * @version 1.0.1
	 * Create Date：2018年2月4日 下午4:17:50   
	 * @see com.ahpu.erp.service.service.IOrderService#findListNotAssociation()
	 */
	public List<Order> findListNotAssociation() {
		return orderDao.findListNotAssociation();
	}

}
