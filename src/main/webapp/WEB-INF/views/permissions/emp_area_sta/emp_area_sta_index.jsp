<%--
  Created by zhshuo.
  Date: 16-11-25
  Time: 上午11:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/permission/emp_area_sta/emp_area_sta.js"></script>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
    $(document).ready(function () {
        empAreaSta.resize();
        empAreaSta.pageInit();
    })
</script>
<div class="panel panel-default" id="currentBisPanel">
    <div class="panel-heading" id="panelHeadDiv">
        <div class="row">
             <c:if test="${single == '1'}">
	             <div class="col-md-5">
	                <div class="checkbox">
	                    <label>
	                        <input id="masterCheck" type="checkbox" <c:if test="${isOpen == 1}">checked</c:if>> 启用员工查看消费区域统计信息权限
	                    </label>
	                </div>
	             </div>
	             <div class="col-md-6" style="text-align: right">
	                    <button class="btn btn-default btn-sm" type="button" id="allOpen">全部启用</button>
	                    <button class="btn btn-default btn-sm" type="button" id="allClose">全不启用</button>
	             </div>
             </c:if>
        </div>
    </div>
    <div class="panel-body" style="overflow:auto;" id="contentDiv">
        <div style="min-width: 900px;min-height: 500px">
            <div class="row">
                <div class="col-md-6" id="leftDiv">
                    <table id="empAreaStaEmpTable"></table>
                </div>
                <div class="col-md-6" id="rightDiv">
                    <table id="empAreaStaAreaTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>