<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${ctx }/assets/css/font-awesome.min93e3.css" />
    	<link rel="stylesheet" href="${ctx }/assets/css/animate.min.css" />
    	<link rel="stylesheet" href="${ctx }/assets/scripts/jqgrid/ui.jqgridffe4.css" />
    	<link rel="stylesheet" href="${ctx }/assets/css/bootstrap.min14ed.css?v=3.3.6" />
    	<link rel="stylesheet" href="${ctx }/assets/css/chosen.css" />
    	<link rel="stylesheet" href="${ctx }/assets/css/ls_form.css" />
    	<link rel="stylesheet" href="${ctx }/assets/scripts/layer/skin/layer.css" />
    	<link rel="stylesheet" href="${ctx }/assets/scripts/jedate/skin/jedate.css" />
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="itemName" />
							</div>
						</li>
						<li>
							<span class="title">物品类型</span>
							<div class="form-group big-wid">
								<select name="itemTypeId" class="form-control">
									<option value="">请选择物品类型</option>
									<c:forEach items="${listItemType}" var="it" varStatus="index">
										<option value="${it.id}">${it.itemTypeName}</option>
									</c:forEach>
								</select>
							</div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
				<span class="add"><i class="fa fa-file-o"></i>新增</span>
				<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
				<span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <div id="editPanel" style="display: none;" title="物品信息">
	    	<form id="editForm" class="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
						<span class="title"><span class="required">*</span>物品类型</span>
						<div class="form-group biger-wid">
							<select class="form-control edit_itemTypeId" name="itemTypeId" editype="val" dete="val">
								<c:if test="${empty listItemType}"><option selected></option></c:if>
							    <c:forEach items="${listItemType}" var="it" varStatus="index">
									<option value="${it.id}">${it.itemTypeName}</option>
							    </c:forEach>
							</select>
						</div>
					</li>
					<li>
						<span class="title"><span class="required">*</span>编码</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入物品编码" type="text" class="form-control edit_itemNo"
							        maxlength="32" name="itemNo" editype="val" dete="en-num" />
						</div>
					</li>
	    			<li>
						<span class="title"><span class="required">*</span>名称</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入物品名称" type="text" class="form-control edit_itemName"
							        maxlength="32" name="itemName" editype="val" dete="val" />
						</div>
					</li>
					<li>
						<span class="title"><span class="required">*</span>单位</span>
						<div class="form-group biger-wid">
							<select class="form-control edit_unit" name="unit" editype="val" dete="val">
							    <%-- <c:forEach items="${listCode}" var="pc" varStatus="idx">
								<option value="${pc.cname}">${pc.cname}</option>
							    </c:forEach> --%>
							    <c:forEach items="${list}" var="map" varStatus="status"> 
									<c:forEach items="${map.JBDW}" var="o" varStatus="index">
									 	<option  value="${o.cName}" >${o.cName}</option>
									 </c:forEach>
								</c:forEach>
							</select>
						</div>
					</li>
					<li>
						<span class="title"><span class="required">*</span>单价</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入物品价格" type="text" class="form-control edit_price"
							        maxlength="18" name="price" editype="val" dete="val" />
						</div>
					</li>
					
					
					<li>
						<span class="title"><span class="required">*</span>最小存储</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入最小存储" type="text" class="form-control edit_minStorage"
							        maxlength="9" name="minStorage" editype="val" dete="val" />
						</div>
					</li>
					<li>
						<span class="title"><span class="required">*</span>最大存储</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入最大存储" type="text" class="form-control edit_maxStorage"
							        maxlength="9" name="maxStorage" editype="val" dete="val" />
						</div>
					</li>
					<li>
						<span class="title">领料减少库存</span>
						<div class="form-group biger-wid">
							<%--<input placeholder="请输入助记码" type="text" class="form-control edit_inCode"
							        maxlength="10" name="inCode" editype="val" />--%>
							<select class="form-control edit_inCode" name="inCode" editype="val">
									<option value="1">是</option>
									<option value="0">否</option>
							</select>
						</div>
					</li>
	    			<li>
	    				<span class="title">备注</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_remark" name="remark" maxlength="200" editype="val" placeholder="请输入备注"/>
						</div>
	    			</li>

					<li>
						<span class="title">出品仓库</span>
						<div class="form-group biger-wid">
							<select class="form-control edit_outWareId" name="outWareId" editype="val">
								<option value="">选择出品仓库</option>
								<c:forEach items="${houses}" var="house" varStatus="index">
									<option value="${house.id}">${house.wareName}</option>
								</c:forEach>
							</select>
						</div>
					</li>
	    		</ul>
	    	</form>
	    </div>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jquery-2.1.1.js" ></script>
    	<script type="text/javascript" src="${ctx }/assets/scripts/bootstrap.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/i18n/grid.locale-cnffe4.js"></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/jquery.jqGrid.minffe4.js"></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/chosen.proto.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/chosen.jquery.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/layer/layer.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jedate/jedate.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/yqsh-ls.js" ></script>
	    <script type="text/javascript" src="${ctx }/app/js/DININGSYS/inve/dgInveItems.js"></script>
	</body>
</html>
