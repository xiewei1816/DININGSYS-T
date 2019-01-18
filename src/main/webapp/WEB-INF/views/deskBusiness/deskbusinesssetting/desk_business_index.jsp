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

<script src="app/js/DININGSYS/deskBusiness/deskbusinesssetting/desk_business_setting.js"></script>
<script>
    jQuery(document).ready(function () {
        dbs_set.pageInit();

        $(".panel-body").height($(window).height()-100);
        $(".tab-pane-div").height($(window).height()-150);
        $("#business_table").height($(window).height()-50);
    })
</script>
<style>
    .tab-pane-div{
        padding: 10px 10px 10px 10px
    }
</style>
<form class="form-horizontal" role="itemFileChoosePageform">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 clas1s="panel-title">前台营业设置</h3>
                </div>
                <div class="panel-body" style="overflow:auto;" id="noPostHideDiv">
                    <ul class="nav nav-tabs" role="tablist" id="myTab">
                        <li id="seatserv" class="active"><a href="#seatserv" role="tab" data-toggle="tab">客位服务</a></li>
                        <li id="billserv"><a href="#billserv" role="tab" data-toggle="tab">账单服务</a></li>
                        <%--<li id="printersetting"><a href="#printersetting" role="tab" data-toggle="tab">打印选项</a></li>--%>
                        <li id="serialrul"><a href="#serialrul" role="tab" data-toggle="tab">流水号生成规则</a></li>
                        <%--<li id="loungesetting"><a href="#loungesetting" role="tab" data-toggle="tab">雅座设置</a></li>--%>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane tab-pane-div active" id="dbscontent" style="overflow-y: auto;overflow-x: hidden"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>