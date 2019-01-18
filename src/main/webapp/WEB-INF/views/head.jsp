<%--
  Created by zhshuo.
  Date: 2016-09-30
  Time: 10:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8" />
    <link rel="stylesheet" href="assets/css/font-awesome.min93e3.css" />
    <link rel="stylesheet" href="assets/css/animate.min.css" />
    <link rel="stylesheet" href="assets/scripts/jqgrid/ui.jqgridffe4.css" />
    <link rel="stylesheet" href="assets/css/bootstrap.min14ed.css?v=3.3.6" />
    <link rel="stylesheet" href="assets/css/chosen.css" />
    <link rel="stylesheet" href="assets/css/ls_form.css" />
    <link rel="stylesheet" href="assets/scripts/layer/skin/layer.css" />
    <link rel="stylesheet" href="assets/scripts/jedate/skin/jedate.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-select/css/bootstrap-select.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/font-awesome/css/font-awesome.min.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/jquery-multi-select/css/multi-select.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/layui/css/layui.css"/>

    <script type="text/javascript" src="assets/scripts/jquery-2.1.1.js" ></script>
    <script type="text/javascript" src="assets/scripts/jquery.PrintArea.js"></script>
    <script type="text/javascript" src="assets/scripts/bootstrap.min.js" ></script>
    <script type="text/javascript" src="assets/scripts/jqgrid/i18n/grid.locale-cnffe4.js"></script>
    <script type="text/javascript" src="assets/scripts/jqgrid/jquery.jqGrid.minffe4.js"></script>
    <script type="text/javascript" src="assets/scripts/chosen.proto.js" ></script>
    <script type="text/javascript" src="assets/scripts/chosen.jquery.js" ></script>
    <script type="text/javascript" src="assets/scripts/layer/layer.js" ></script>
    <script type="text/javascript" src="assets/scripts/jedate/jquery.jedate.js" ></script>
    <script type="text/javascript" src="assets/scripts/yqsh-ls.js" ></script>
    <script type="text/javascript" src="assets/plugins/jquery-validation/dist/jquery.validate.js"></script>
    <script type="text/javascript" src="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.js"></script>
    <script type="text/javascript" src="assets/plugins/bootstrap-select/js/bootstrap-select.js"></script>
    <script type="text/javascript" src="assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>
    <script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
    <script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script>
    <script type="text/javascript" src="assets/plugins/layui/layui.js"></script>
    <script type="text/javascript" src="app/lib/decimal/decimal.js"></script>
</head>
</html>
