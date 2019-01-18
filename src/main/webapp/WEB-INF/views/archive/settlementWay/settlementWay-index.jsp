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
        <script type="text/javascript" src="assets/scripts/myvalidatafrom.js" ></script>
        <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.exedit.js"></script>
	    <script type="text/javascript" src="app/js/DININGSYS/archive/settlementWay/settlementWay-index.js"></script>
        <link rel="stylesheet" href="assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
		<script>
            jQuery(document).ready(function () {
            	
            	//初始化下拉列表数据
              	$("#typeEdit option:first").prop("selected", 'selected');
            	
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
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">结算方式类型</span>
							<div class="form-group big-wid">
								<select class="form-control" id="type" name="type" >
									<option selected ></option>
									<c:forEach items="${list}" var="map" varStatus="status"> 
										<c:forEach items="${map.JSFS}" var="o" varStatus="index">
										 	<option value="${o.id}" >${o.cName}</option>
										 </c:forEach>
									</c:forEach>
								</select>
							</div>
						</li>
						<li>
							<span class="title">编号/名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="conditions" />
							</div>
						</li>
						<li>
							<span class="title">创建时间</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crStartTime" id="crStartTime" name="crStartTime" placeholder="开始时间" readonly>
							</div>
						</li>
						<li>
							<span class="title">至</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crEndTime" id="crEndTime" name="crEndTime" placeholder="结束时间" readonly>
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
				</c:if>
				<span id="zksz" class="greenbtn"><i class="fa fa-file-o"></i>抵扣品项选择</span>
				<span id="rank" class="royalbtn"><i class="fa fa-fa-sort-numeric-asc"></i>排序</span>
				<!-- <span class="csbtn"><i class="fa fa-laptop"></i>查看</span>
				<span class="redbtn"><i class="fa fa-upload"></i>导出</span>
				<span class="royalbtn"><i class="fa fa-download"></i>导入</span> -->
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <div id="editPanel" style="display: none;" title="结算方式">
	    	<form id="editForm" class="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
						<span class="title" title="结算编号"><span class="required">*</span>结算编号</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入结算编号" type="text" class="form-control edit_number" name="number" editype="val" dete="val" />
						</div>
					</li>
	    			<li>
	    				<span class="title" title="结算名称"><span class="required">*</span>结算名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_name" id="edit_named" name="name" editype="val" dete="val" placeholder="请输入结算名称"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="结算方式类型"><span class="required">*</span>结算方式类型</span>
						<div class="form-group bigest-wid">
							<input type="hidden" class="form-control edit_type" id="typeVal" name="typeVal" editype="val" />
							<select class="form-control" id="typeEdit" name="type" dete="val">
								<c:forEach items="${list}" var="map" varStatus="status"> 
									<c:forEach items="${map.JSFS}" var="o" varStatus="index">
									 	<option value="${o.id}" >${o.cName}</option>
									 </c:forEach>
								</c:forEach>
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="汇率"><span class="required">*</span>汇率</span>
						<div class="form-group bigest-wid">
							<input type="number" class="form-control edit_exchangeRate isnumber" dete="val"
							 name="exchangeRate" editype="val" placeholder="请输入汇率" defaultVal="1.00"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="快捷键">快捷键</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_shortcutKey" name="shortcutKey" editype="val">
								<option value="A">A</option><option value="B">B</option><option value="C">C</option>
								<option value="D">D</option><option value="E">E</option><option value="F">F</option>
								<option value="G">G</option><option value="H">H</option><option value="I">I</option>
								<option value="J">J</option><option value="K">K</option><option value="L">L</option>
								<option value="M">M</option><option value="N">N</option><option value="O">O</option>
								<option value="P">P</option><option value="Q">Q</option><option value="R">R</option>
								<option value="S">S</option><option value="T">T</option><option value="U">U</option>
								<option value="V">V</option><option value="W">W</option><option value="X">X</option>
								<option value="Y">Y</option><option value="Z">Z</option>
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="巻面值">巻面值</span>
						<div class="form-group bigest-wid">
							<input type="number" class="form-control edit_rollFaceValue isnumber"
							 name="rollFaceValue" editype="val" placeholder="请输入巻面值" defaultVal="0" notdis="hasRollFace"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="计入实际收入比例">计入实际收入比例</span>
						<div class="form-group input-groups">
							<input type="number" class="form-control edit_actualIncomeRatio small-wid isnumber" style="width: 260px;"
							 name="actualIncomeRatio" editype="val" placeholder="请输入计入实际收入比例" defaultVal="0" notdis="hasActualIncomeRatio"/>
							 <span class="symbol">%</span>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="不计入实际收入比例">不计入实际收入比例</span>
						<div class="form-group input-groups">
							<input type="number" class="form-control edit_notActualIncomeRatio small-wid isnumber" style="width: 260px;"
							 name="notActualIncomeRatio" editype="val" placeholder="请输入不计入实际收入比例" defaultVal="0" notdis="hasNotActualIncomeRatio"/>
							 <span class="symbol">%</span>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="停用">停用</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isDisabled"
							 name="isDisabled" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isDisabled"
							 name="isDisabled" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="常用">常用</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isCommon"
							 name="isCommon" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isCommon"
							 name="isCommon" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="系统保留">系统保留</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isKeepSystem"
							 name="isKeepSystem" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isKeepSystem"
							 name="isKeepSystem" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="结算时必须输入附加信息">结算时必须输入附加信息</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isMustInformation"
							 name="isMustInformation" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isMustInformation"
							 name="isMustInformation" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="单独使用，不能与其他计算方式同时使用">单独使用，不能与其他计算方式同时使用</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isAloneUse"
							 name="isAloneUse" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isAloneUse"
							 name="isAloneUse" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="本位币（仅针对现金类结算方式）">本位币（仅针对现金类结算方式）</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isCurrency"
							 name="isCurrency" editype="radio" notdis="hasCurrency"/><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isCurrency"
							 name="isCurrency" editype="radio" notdis="hasCurrency"/><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="允许找零">允许找零</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isAllowChange"
							 name="isAllowChange" editype="radio" notdis="hasAllowChange"/><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isAllowChange"
							 name="isAllowChange" editype="radio" notdis="hasAllowChange"/><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li class="bigger">
	    				<span class="title" title="说明">说明</span>
						<div class="form-group bigest-wid">
							<textarea class="form-control edit_explains" name="explains" editype="val"></textarea>
						</div>
	    			</li>
	    		</ul>
	    	</form>
	    </div>
	</body>
</html>
