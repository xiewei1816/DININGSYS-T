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
		<jsp:include page="../../head.jsp"/>
	    <script type="text/javascript" src="app/js/DININGSYS/inve/dgTransfers.js"></script>
	    <script type="text/javascript" src="assets/scripts/ls-tree.js" ></script>
    	<script type="text/javascript">
    		var ctx="${ctx }";
    	</script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">物品名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="itemName" />
							</div>
						</li>
						<li>
						<span class="title">调出仓库</span>
						<div class="form-group biger-wid">
							<select name="outWareId"  class="form-control edit_outWareID"
							          editype="val" style="overflow:visible;">
							          <option value="">==请选择调出仓库==</option>
							    <c:forEach items="${listWare}" var="lw" varStatus="index">
								<option value="${lw.id}">${lw.wareName}</option>
							    </c:forEach>
							</select>
						</div>
					</li>
						<li>
						<span class="title">调入仓库</span>
						<div class="form-group biger-wid">
							<select name="inWareId"  class="form-control edit_inWareId"
							          editype="val" style="overflow:visible;">
							          <option value="">==请选择调入仓库==</option>
							    <c:forEach items="${listWare}" var="lw" varStatus="index">
								<option value="${lw.id}">${lw.wareName}</option>
							    </c:forEach>
							</select>
						</div>
					</li>
					<li>
						<span class="title">创建时间</span>
						<div class="form-group big-wid">
							<input type="text" class="form-control edit_crStartTime" id="crStartTime" name="crStartTime" editype="val" placeholder="开始时间" readonly
   								onclick="$.jeDate('#crStartTime',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})" dete="em-date">
						</div>
					</li>
					<li>
						<span class="title">至</span>
						<div class="form-group big-wid">
							<input type="text" class="form-control edit_crEndTime" id="crEndTime" name="crEndTime" editype="val" placeholder="结束时间" readonly
   								onclick="$.jeDate('#crEndTime',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})" dete="em-date">
                           </div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
				<span class="j-tran" ><i class="fa fa-file-o"></i>调拨</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <jsp:include page="editTran.jsp"></jsp:include>
	</body>
</html>
