<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>物品寄存</title>
		<jsp:include page="../head.jsp"/>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
		<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
		<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	    <script src="app/js/DININGSYS/api/addGoodsConsign.js"></script>
	</head>
	<style type="text/css">
		body{width:100%;font-family:"微软雅黑";font-size:16px;overflow:auto;}
		input{width:120px;height: 24px;vertical-align:middle;}
		select{width:120px;height: 24px;vertical-align:middle;}
		input[type=checkbox]{height:16px;width:16px;vertical-align:middle;}
		label{vertical-align:middle;}
		.searchItemsTab{border-collapse:separate; border-spacing:8px;}
		#header{width: 100%; height: 20%;}
		#container{width: 100%; height: 80%;}
		
		.border-table {   
        	border-collapse: collapse;   
        	border: none;   
	    }   
	    .border-table td {   
	        border: solid #000 1px;   
	    }   
	</style>
	<body>
	<div id="main" style="width: 100%; height: auto;">
		<div id="header" class="search-wrapper input-groups">
			<form class="query-pan" id="query-pan">
				<table class="searchItemsTab">
					<tr>
						<td>
							<label>&nbsp;寄存日期&nbsp;</label>
							<input readonly type="text" id="jcStartTime" name="jcStartTime" >
	  						至&nbsp;<input readonly type="text" id="jcEndTime" name="jcEndTime" >
  						</td>
						<td>
							<label>&nbsp;物品名称&nbsp;</label><input type="text" maxlength="16" id="goodsName" name="goodsName" />
						</td>
						<td>
							<label>&nbsp;物品颜色&nbsp;</label><input type="text" maxlength="8" id="goodsColor" name="goodsColor" />
						</td>
						<td>
							<label>&nbsp;寄存POS&nbsp;</label>
							<select id="gcPos" name="gcPos">
								<option selected="selected"></option>
								<c:forEach items="${posList}" var="pos" varStatus="status">
									<option  value="${pos.id}" >${pos.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label>&nbsp;取走日期&nbsp;</label>
							<input readonly type="text" id="qzStartTime" name="qzStartTime" >
  							至&nbsp;<input readonly type="text" id="qzEndTime" name="qzEndTime" >
  						</td>
						<td>
							<label>&nbsp;客户&#12288;&#12288;&nbsp;</label><input type="text" maxlength="12" id="clientName" name="clientName" />
						</td>
						<td>
							<label>&nbsp;客位&#12288;&#12288;</label>
							<select id="clientSeat" name="clientSeat">
								<option selected="selected"></option>
								<c:forEach items="${seatList}" var="seat" varStatus="status">
									<option  value="${seat.id}" >${seat.name}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<label>&nbsp;消费区域&nbsp;</label>
							<select id="expArea" name="expArea">
								<option selected="selected"></option>
								<c:forEach items="${areaList}" var="area" varStatus="status">
									<option  value="${area.id}" >${area.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<label>&nbsp;超出截止日期&nbsp;</label><input type="number" id="aboveDays" name="aboveDays" />&nbsp;天
						</td>
						<td>
							<input type="checkbox" id="cbx10" onclick="setSearchVal(10)" value="1"/>
							<label>&nbsp;超出保存期</label><input style="display: none;" type="text" id="goodsExpirationDate" name="goodsExpirationDate" />
						</td>
						<td>
							<input type="checkbox" id="cbx11" onclick="setSearchVal(11)" value="1"/>
							<label>&nbsp;超出寄存截止日期</label><input style="display: none;" type="text" id="gcEndTime" name="gcEndTime" />
						</td>
					</tr>
				</table>
				<div class="search-btns" style="margin-top: 10px;">
					<button class="btn btn-primary">查询</button>
				</div>
			</form>
		</div>
		<div id="container">
			<div class="menuTree" style="width: 20%; height: 800px; float: left; border: 1px solid #C0C0C0; overflow:auto;">
				<ul id="myTree" class="ztree"></ul>
			</div>
				<div class="btn-toolbar">
					<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>添加</span>
					<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
					<span id="takeout" class="royalbtn"><i class="fa fa-upload"></i>取出</span>
					<span id="todo" class="graybtn"><i class="fa fa-trash-o"></i>处理</span>
					<span id="del" class="redbtn"><i class="fa fa-trash"></i>删除</span>
				</div>
			<div class="wrapper animated fadeInUp" style="float: left; width: 80%; overflow:auto;">
	       	 	<div class="jqGrid_wrapper">
	                <table id="grid-table" ></table>
				</div>
		    </div>
		</div>
	</div>
	</body>
</html>
