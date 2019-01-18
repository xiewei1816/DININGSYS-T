<%--
  Created by zhshuo.
  Date: 2017-02-09
  Time: 16:06
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>品项类别销售信息</title>
    <jsp:include page="../../head.jsp"/>
    <script type="text/javascript" src="app/js/report.js"></script>
    <script type="text/javascript" src="app/js/DININGSYS/report/itemFileType/itemFileType.js"></script>
    <script>
        $(document).ready(function () {
            itemFileType.pageInit();
        });
    </script>
</head>
<body>
<div class="wrapper animated fadeInUp">
    <div class="search-wrapper input-groups">
        <form class="query-pan" id="query-pan">
            <ul>
                <li id="dateFastSel" ></li>
                <li>
                    <span class="title">时间</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control edit_startTime" id="startTime" name="startTime" placeholder="开始时间" readonly>
                    </div>
                </li>
                <li>
                    <span class="title">至</span>
                    <div class="form-group big-wid">
                        <input type="text" class="form-control edit_endTime" id="endTime" name="endTime" placeholder="结束时间" readonly>
                    </div>
                </li>
            </ul>
            <div style="position: absolute;right: 20px;bottom: 5px;">
				<input class="btn btn-success" id="doSearch" type="button" value="查询">
			</div>
        </form>
    </div>
    <div class="btn-toolbar">
        <span id="export" class="royalbtn"><i class="fa fa-upload"></i>导出</span>
        <span id="refresh" class="greenbtn"><i class="fa fa-refresh"></i>刷新</span>
        <span id="print" class="graybtn"><i class="fa fa-print"></i>打印</span>
        <span style="display: none" class="glyphicon glyphicon-menu-up" id="return"></span>
        <div style="margin-top: 5px;">
            <input type="hidden" id="clickFlag" value="0"><!-- 表格层数 0,1,2,3 分别表示4层数据 -->
            <input type="hidden" id="bigTypeNum">
            <input type="hidden" id="smallTypeNum">
            <input type="hidden" id="itemFileNum">
            <label id="tips">
                双击表格数据查看下级信息
            </label>
            <label id="pxdlLabel">
            </label>
            <label id="pxxlLabel">
            </label>
            <label id="pxLabel">
            </label>
        </div>
    </div>
    <div class="col-md-12">
        <table id="itemFileTypeDetails"></table>
        <table id="itemFileTypeDetails_small"></table>
        <table id="itemFileTypeDetails_item"></table>
        <table id="itemFileTypeDetails_openWater"></table>
    </div>
</div>
</body>
</html>