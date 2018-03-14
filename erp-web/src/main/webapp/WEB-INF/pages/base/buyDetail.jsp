<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Bootstrap 3, from LayoutIt!</title>

    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">

    <link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/css/style1.css" rel="stylesheet">

  </head>
  <body style="height: 80%;">
    <div class="container-fluid" >
	<div class="row">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-6">
					 
					<a type="button" id="button" class="btn btn-success" href="javascript:history.back()">
						BACK
					</a>
				</div>
			</div>
			<s:iterator value="orderList" var="order">
			<table class="table">
			
				<tbody>
					<tr class="warning">
						<td>
							企业名称：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.supplier.name}</span>
						</td>
						<td>
							下单时间：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.createTimeView}</span>
						</td>
						<td>
							下单人：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.creater.name}</span>
						</td>
						<td>
							订单号：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.orderNum }</span>
						</td>
					</tr>
					
					<tr class="warning">
						<td>
							订单类别：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.orderTypeView}</span>
						</td>
						<td>
							订单状态：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.typeView }</span>
						</td>
						<td>
							商品总量：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.totalNum}</span>
						</td>
						<td>
							订单总额：
						</td>
						<td>
							<span style="font-size: 14px;color: blue;">${order.totalPrice}</span>
						</td>
					</tr>
				</tbody>
			</table>
			</s:iterator>
			<h4 class="text-center" style="margin-top: 100px;">订单明细</h4>
			<hr>
			<table class="table table-striped table-hover table-condensed">
				<thead>
					<tr>
						<th>
							商品类别
						</th>
						<th>
							商品名称
						</th>
						<th>
							购买数量
						</th>
						<th>
							进货单价
						</th>
						<th>
							合计
						</th>
					</tr>
				</thead>
				<tbody>
				<s:iterator value="detailsList">
					<tr class="active">
						<td>
							${goods.goodstype.name }
						</td>
						<td>
							${goods.name }
						</td>
						<td>
							${num }
						</td>
						<td>
							${priceView}
						</td>
						<td>
							${totalPriceView }
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
			
		</div>
	</div>
</div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/scripts.js"></script>
  </body>
</html>