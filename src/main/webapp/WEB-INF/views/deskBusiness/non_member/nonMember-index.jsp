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
        <script type="text/javascript" src="assets/scripts/ls-tree.js" ></script>
        <script type="text/javascript" src="assets/scripts/myvalidatafrom.js" ></script>
        <script src="app/js/DININGSYS/deskBusiness/non_member/nonMember.js"></script>
		<script>
			jQuery(document).ready(function () {
				var start = {
					format: 'YYYY-MM-DD',
                    festival:true,
					ishmsVal:false,
					choosefun: function(elem,datas){
						end.minDate = datas; //开始日选好后，重置结束日的最小日期
					}
				};
				var end = {
					format: 'YYYY-MM-DD',
                    festival:true,
                    choosefun: function(elem,datas){
						start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
					}
				};
				$('#crStartTime').jeDate(start);
				$('#crEndTime').jeDate(end);
            })

		</script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">编号/名称/助记符</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="conditions" />
							</div>
						</li>
						<li>
							<span class="title">账户类型</span>
							<div class="form-group big-wid">
								<select class="form-control edit_accountType" name="accountType">
									<option value="0">请选择</option>
									<option value="1">个人</option>
									<option value="2">公司</option>
								</select>
							</div>
						</li>
						<li>
							<span class="title">客户经理</span>
							<div class="form-group big-wid">
								<select class="form-control edit_empId" name="empId">
									<option value="0">请选择</option>
									<c:forEach items="${emps }" var="user">
										<option value="${user.id }">${user.empName }</option>
									</c:forEach>
								</select>
							</div>
						</li>
						<li>
							<span class="title">创建时间</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crStartTime" id="crStartTime" name="crStartTime" placeholder="开始时间" readonly
    								>
							</div>
						</li>
						<li>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crEndTime" id="crEndTime" name="crEndTime" placeholder="结束时间" readonly
    								>
							</div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
				<form class="queryForm1" id="queryForm1" style="display: none;">
					<input type="hidden" name="memberId" id="query_memberId_1"/>
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
            <div class="jqGrid_wrapper">
                <table id="grid-table1"></table>
                <div id="grid-pager1"></div>
            </div>
	    </div>
	    <jsp:include page="nonMember-edit.jsp"></jsp:include>
	</body>
</html>
