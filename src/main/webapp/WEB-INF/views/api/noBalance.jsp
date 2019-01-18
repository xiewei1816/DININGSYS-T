<%--
  Created by IntelliJ IDEA.
  User: yqsh-zc
  Date: 2016/12/30
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>未结算品项查询</title>
    <style>
        .divcss5{ width:950px;height: 400px;overflow: auto;}
        .divcss5 table{ background:#CCC;}
        .divcss5 table td{ background:#FFF;width: 100px;text-align: center;}
    </style>
</head>
<body>
<div class="divcss5">
    <table width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
            <th>客位</th>
            <th width="200">营业流水</th>
            <th>人数</th>
            <th>品项编号</th>
            <th>品项名称</th>
            <th>单位</th>
            <th>数量</th>
            <th>单价</th>
            <th>开单时间</th>
            <th>开单POS</th>
            <th>服务员</th>
        </tr>
        <c:forEach items="${datas}" var="data">
            <tr>
                <td>${data.tablename}</td>
                <td>${data.ow_num}</td>
                <td>${data.people_count}</td>
                <td>${data.num}</td>
                <td>${data.name}</td>
                <td>${data.unit}</td>
                <td>${data.item_file_number}</td>
                <td>${data.item_final_price}</td>
                <td>${data.first_time}</td>
                <td>${data.posname}</td>
                <td>${data.waiter}</td>
            </tr>
        </c:forEach>

    </table>
</div>
</body>
</html>