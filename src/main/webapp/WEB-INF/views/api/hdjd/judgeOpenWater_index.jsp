<%--
  Date: 2017-07-20 10:26
  @Author zhshuo.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>核对单据</title>
    <jsp:include page="../../head.jsp"/>
    <script src="app/js/api/hddj_des.js"></script>
    <script>
        $(function () {
            hddj_des.pageInit();
        });
    </script>
</head>

<body>
<div id="bContent">
    <%--<div id="openWaterDiv" style="float: left">
        <table id="openWaterTable"></table>
    </div>
    <div id="serviceDiv" style="float: left">
    </div>--%>
    <div id="leftDataDiv" style="float: left">
        <ul id="myTab" class="nav nav-tabs nav-justified">
            <li class="active"><a href="#openWaterBDiv" data-toggle="tab">未结流水</a></li>
            <li><a href="#closedBillBDiv" data-toggle="tab">已结账单</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="openWaterBDiv">
                <div id="openWaterDiv" style="float: left">
                    <table id="openWaterTable"></table>
                </div>
            </div>
            <div class="tab-pane fade" id="closedBillBDiv">
                <div id="closedBillSearch" style="padding: 10px;">
                    <form class="form-horizontal">
                        <div class="row">
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="control-label col-md-4">开始时间:</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" id="startTime" name="startTime"
                                               placeholder="开始时间" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="control-label col-md-4">结束时间:</label>
                                    <div class="col-md-8">
                                        <input type="text" class="form-control" id="endTime" name="endTime"
                                               placeholder="结束时间" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group">
                                    <label class="control-label col-md-4">排序规则:</label>
                                    <div class="col-md-8">
                                        <select class="form-control" id="sortType">
                                            <option value="1">结算流水</option>
                                            <option value="2">实收金额</option>
                                            <option value="3">营业流水</option>
                                            <option value="4">结算时间</option>
                                            <option value="5">客座名称</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" style="text-align: right">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label class="control-label col-md-4"></label>
                                    <div class="col-md-8">
                                        <input class="btn btn-success" id="closedBillFlush" type="button" value="查询">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <table id="closedBillTable">

                    </table>
                </div>
            </div>
        </div>
    </div>
    <div id="serviceDiv" style="float: left">

    </div>
</div>
</body>
</html>
