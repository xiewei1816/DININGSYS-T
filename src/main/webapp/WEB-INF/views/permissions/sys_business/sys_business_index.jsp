<%--
  Created by zhshuo.
  Date: 2016-11-04
  Time: 15:19
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script src="app/js/DININGSYS/permission/business_per/business_permission.js"></script>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
	var single = '${single}';
    jQuery(document).ready(function () {
        $("#rightBigPanel").height($(window).height()-60);

        $("#jobTreeDiv").height($(window).height()-95);

        $("#sysPerDiv").height($("#rightBigPanel").height()-40);
        $("#busPerDiv").height($("#rightBigPanel").height()-60);

        $(".sysPerPanelBody").height($("#sysPerDiv").height()-100);

        busi_per.pageInit();

    })
</script>
<style>
    .tab-pane-div{
        padding: 10px 10px 10px 10px
    }
</style>
<div class="btn-toolbar">
    <button id="ywczPermissonBtn" type="button" class="btn btn-sm btn-primary roleBtn" >权限控制</button>
    <button id="empAreaStaBtn" type="button" class="btn btn-sm btn-info roleBtn" >员工统计信息权限控制</button>
    <c:if test="${single == '1'}">
    	<button id="addNewDuties" type="button" class="btn btn-sm btn-success roleBtn" >新增职务</button>
    </c:if>
</div>
<form class="form-horizontal" role="form">
    <div class="row">
        <div class="col-md-2" id="leftJobDiv">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">系统职务</h3>
                    <input type="hidden" id="hideDutiesCode">
                    </span>
                </div>
                <div class="panel-body" style="overflow:auto;padding:0px" id="jobTreeDiv">
                    <ul id="businessPostTree" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-10" id="rightBigPanel">
            <ul class="nav nav-tabs" role="tablist">
                <li class="active"><a href="#sysPerDiv" role="tab" data-toggle="tab">系统使用权限</a></li>
                <li><a href="#busPerDiv" role="tab" data-toggle="tab">业务使用权限</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane tab-pane-div active" id="sysPerDiv" style="overflow-y: auto;overflow-x: hidden">
                    <div class="col-md-12">
                        <div class="col-md-6" style="margin-left: -35px">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">后台模块</h3>
                                    </span>
                                </div>
                                <div class="panel-body sysPerPanelBody" style="overflow:auto;" id="sysPerPanelBackgroud">
                                    <ul id="sysPerBackgroudZtree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                        <div  class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">前台模块</h3>
                                    </span>
                                </div>
                                <div class="panel-body sysPerPanelBody" style="overflow:auto;" id="sysPerPanelDesk">
                                    <ul id="sysPerDeskZtree" class="ztree"></ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane tab-pane-div" id="busPerDiv" style="overflow-y: auto;overflow-x: hidden;">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title"><span id="postName"></span></h3>
                            </span>
                        </div>
                        <div class="panel-body" style="overflow:auto;" id="noPostHideDiv">
                            <ul class="nav nav-tabs" role="tablist" id="myTab">
                                <li id="generalBus" class="active"><a href="#normal" role="tab" data-toggle="tab">常规</a></li>
                                <li id="discountBus"><a href="#discount" role="tab" data-toggle="tab">优惠</a></li>
                                <li id="freeBus"><a href="#free" role="tab" data-toggle="tab">赠送</a></li>
                                <li id="chargebackBus"><a href="#chargeback" role="tab" data-toggle="tab">退单</a></li>
                                <li id="variablePriceBus"><a href="#variablePrice" role="tab" data-toggle="tab">变价</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane tab-pane-div active smallTabContent" id="normal" style="overflow-y: auto;overflow-x: hidden"></div>
                                <div class="tab-pane tab-pane-div smallTabContent" id="discount" style="overflow-y: auto;overflow-x: hidden"></div>
                                <div class="tab-pane tab-pane-div smallTabContent" id="free" style="overflow-y: auto;overflow-x: hidden">
                                </div>
                                <div class="tab-pane tab-pane-div smallTabContent" id="chargeback" style="overflow-y: auto;overflow-x: hidden">
                                </div>
                                <div class="tab-pane tab-pane-div smallTabContent" id="variablePrice" style="overflow-y: auto;overflow-x: hidden">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>