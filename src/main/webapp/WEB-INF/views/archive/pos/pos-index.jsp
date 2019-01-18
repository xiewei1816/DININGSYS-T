<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<jsp:include page="../../head.jsp"/>
		<script>
			var single = '${single}';
		</script>
        <script type="text/javascript" src="assets/scripts/ls-tree.js" ></script>
        <script type="text/javascript" src="assets/scripts/myvalidatafrom.js" ></script>
        <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.exedit.js"></script>
        <link rel="stylesheet" href="assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
        <script type="text/javascript" src="app/js/DININGSYS/archive/pos/pos.js"></script>
    	<style type="text/css">
			.extra {width: 200px; border: 1px solid #f6a828; height:600px; float:left;overflow: auto;}
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
			.child {margin-left: 210px}
		</style>
		<script>
            jQuery(document).ready(function () {
            	
            	//初始化下拉列表数据
              	$("#organization option:first").prop("selected", 'selected');
            	
				var start = {
					format: 'YYYY-MM-DD',
					festival:true,
					ishmsVal:false,
					choosefun: function(elem,datas){
						end.minDate = datas;
					}
				};
				var end = {
					format: 'YYYY-MM-DD',
					festival:true,
					choosefun: function(elem,datas){
						start.maxDate = datas;
					}
				};
				$.jeDate('#crStartTime',start);
				$.jeDate('#crEndTime',end);
            })
		</script>
	</head>
	<body>
		<div id="menuTree" class="extra" >
            	<ul id="tree" class="ztree"></ul>
         </div>
		<div class="wrapper animated fadeInUp menuright">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li style="display: none;">
							<span class="title">消费区域</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="qur_consArea" disabled="disabled" />
								<input type="hidden" name="consumerAreas" value=""/>
							</div>
						</li>
						<li>
							<span class="title">编号/名称/助记符</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="conditions" />
							</div>
						</li>
						<li>
							<span class="title">创建时间</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crStartTime" id="crStartTime" name="crStartTime" editype="val" placeholder="开始时间" readonly>
							</div>
						</li>
						<li>
							<span class="title">至</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crEndTime" id="crEndTime" name="crEndTime" editype="val" placeholder="结束时间" readonly>
                            </div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
			    <c:if test="${single == '1' }">
				<span class="add"><i class="fa fa-file-o"></i>新增</span>
				<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
				<span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
				<span id="delo" class="graybtn"><i class="fa fa-trash-o"></i>回收站</span>
				</c:if>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <div id="editPanel" style="display: none;" title="POS信息">
	    	<form id="editForm" class="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
						<span class="title" title="POS编号"><span class="required">*</span>POS编号</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入POS编号" type="text" class="form-control edit_number" name="number" defaultVal="${number}" editype="val" dete="val" />
						</div>
					</li>
	    			<li>
	    				<span class="title" title="POS名称"><span class="required">*</span>POS名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_name" id="edit_named" name="name" editype="val" dete="val" placeholder="请输入POS名称"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="助记符">助记符</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_mnemonic" name="mnemonic" editype="val" placeholder="请输入助记符"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="所属组织结构"><span class="required">*</span>所属组织结构</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_organization" id="organization" name="organization" editype="val" dete="val">
								 <c:forEach items="${listOrg}" var="o" varStatus="index">
								 	<option  value="${o.id}">${o.orgName}</option>
								 </c:forEach>
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span id="consumerAreas" class="title" title="管理的消费区域"><span class="required">*</span>管理的消费区域</span>
						<div class="form-group bigest-wid areas">
							<input type="hidden" class="edit_consumerAreas" name="consumerAreas" editype="val">
							<c:forEach items="${areas }" var="area">
								<label class='lscheck'><b></b><input type='checkbox'  editype="check" value="${area.id }"><i></i>${area.name }</label>
							</c:forEach>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="IP地址">IP地址</span>
						<div class="input-groups bigest-wid ipinputs">
							<input type="hidden" class="edit_ipArea" name="ipArea" editype="val">
							<input type="text" class="form-control smaller-wid" id="IP1" onkeyup ="chkMaxLength(this.id)" placeholder="000" autocomplete="off"/>
							<input type="text" class="form-control smaller-wid" id="IP2" onkeyup ="chkMaxLength(this.id)" placeholder="000" autocomplete="off"/>
							<input type="text" class="form-control smaller-wid" id="IP3" onkeyup ="chkMaxLength(this.id)" placeholder="000" autocomplete="off"/>
							<input type="text" class="form-control smaller-wid" id="IP4" onkeyup ="chkMaxLength(this.id)" placeholder="000" autocomplete="off"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="结班功能">结班功能</span>
						<div class="input-groups bigest-wid ipinputs">
							<label class="radiobtn"><input type="radio" value="1"  name="canJb" editype="radio"><i></i><span></span>开</label>
							<label class="radiobtn"><input type="radio" value="0"  name="canJb" editype="radio"><i></i><span></span>关</label>
						</div>
	    			</li>
	    		</ul>
	    	</form>
	    </div>
	</body>
</html>
