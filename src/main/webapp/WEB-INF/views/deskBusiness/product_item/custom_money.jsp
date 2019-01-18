<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>品项管理管理</title>
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript">
			var path = "${ctx}";
		</script>
	      <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="${ctx}/assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <link rel="stylesheet" href="${ctx}/assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
	    <link rel="stylesheet" href="${ctx}/app/css/DININGSYS/deskBusiness/product_item/custom_money.css">
	    <script src="${ctx}/app/js/DININGSYS/deskBusiness/product_item/custom_money.js"></script>
	    <script src="${ctx}/app/js/DININGSYS/deskBusiness/product_item/custom_money_add.js"></script>
	    <link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
		<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.css">
	</head>
	
	<body>
		<div class="btn-toolbar">
			<span id="save" class="royalbtn"><i class="fa fa-file-o"></i>保存</span>
			<span id="add" class="greenbtn"><i class="fa fa-file-o"></i>新增</span>
			<span id="delb" class="redbtn"><i class="fa fa-trash"></i>删除</span> 
			<span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span> 
			<span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
	   </div>
	   <div class="top-menu">
			<div class="left-menu">
				<div class="left-content">
	       			 <div id="smallGate" class="smallGate">
	       			 	<p>
	       			 	  新建品项时,品项允许自定义金额金额的默认值(按品项小类设置)
	       			 	</p>
	       			 	<table id="grid-table-gate"></table>
	       			 </div>
				</div>
	        </div>
	        <div class="right-content">
				<div class="form-group radios" style="height: 30px">
                        <label  class="col-md-3 control-label"><h5 style="margin-left: 15px;margin-top: 5px;" value="0">设定类型:</h5></label>
                          <c:forEach items="${names}" var="item" varStatus="status">
                          	<c:if test="${status.index == 0 && fn:length(item.customMoneyName) >0}">
                       		    <label class="radio-inline">
								  <input type="radio" name="mType" id="isALL" value='${item.id}' checked> ${item.customMoneyName}
								</label>
		                     </c:if>
		                     <c:if test="${status.index > 0 && fn:length(item.customMoneyName) >0}">
                       		    
                       		    <label class="radio-inline">
								  <input type="radio" name="mType" id="isALL" value='${item.id}' > ${item.customMoneyName}
								</label>
		                     </c:if>
					    </c:forEach>		
	            </div>
		        <table id="grid-table-1"></table>
	         </div> 
        </div> 
        <div class="col-md-12 form">
        	<form class="form-horizontal" role="form">
        		   <c:if test="${fn:length(names) >0}">
        		   <div class="form-group">                       
                        <c:forEach items="${names}" var="item" varStatus="status">
                        	<c:if test="${status.index < 2 }">
									<label  class="col-md-2 control-label">自定义金额${status.index+1}名称:</label>
			                        <div class="col-md-3" style="display: inline-block;">
			                            <input type="text" name="customName${status.index+1}" index="${status.index+1}" class="form-control" id="customName${status.index+1}"  value="${item.customMoneyName}">
			                        </div>
			                </c:if>
						</c:forEach>		
                    </div>
                    <div class="form-group">
                        <c:forEach items="${names}" var="item" varStatus="status">
                        		<c:if test="${status.index == 2 }">
									<label  class="col-md-2 control-label">自定义金额${status.index+1}名称:</label>
			                        <div class="col-md-3" style="display: inline-block;">
			                            <input type="text" name="customName${status.index+1}" index="3" class="form-control" id="customName${status.index+1}"  value="${item.customMoneyName}">
			                        </div>
			                     </c:if>
						</c:forEach>
                    </div>
                    </c:if>
                   <c:if test="${fn:length(names) ==0}">
	        		   <div class="form-group">                       
								<label  class="col-md-2 control-label">自定义金额1名称:</label>
		                        <div class="col-md-3" style="display: inline-block;">
		                            <input type="text" name="customName1" class="form-control" id="customName1"  value="" index="1">
		                        </div>
		                        <label  class="col-md-2 control-label">自定义金额2名称:</label>
		                        <div class="col-md-3" style="display: inline-block;">
		                            <input type="text" name="customName2" class="form-control" id="customName2"  value="" index="2">
		                        </div>		
	                    </div>
	                    <div class="form-group">
								<label  class="col-md-2 control-label">自定义金额3名称:</label>
		                        <div class="col-md-3" style="display: inline-block;">
		                            <input type="text" name="customName3" class="form-control" id="customName3"  value="" index="3">
		                        </div>
	                    </div>
                    </c:if>
             </form>
        </div>
       <div class="container">
       		<fieldset class="c-fieldset">
       			<legend class="container-title">说明</legend>
                    <div>
                        <label>设定前台接班报表自定义结算余额对品项的统计方法。</label>
                    </div>
       		</fieldset>
	   </div> 
	</body>
</html>
