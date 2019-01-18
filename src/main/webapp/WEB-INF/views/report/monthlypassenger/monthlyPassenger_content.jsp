<%--
  Created by zhshuo.
  Date: 2017-03-20
  Time: 16:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
    table.imagetable {
        font-family: verdana,arial,sans-serif;
        font-size:11px;
        color:#333333;
        border-width: 1px;
        border-color: #999999;
        border-collapse: collapse;
        margin: 20px auto 0;
    }
    table.imagetable th {
        background:#b5cfd2;
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #999999;
        width: 200px;
        text-align: center;
    }
    table.imagetable td {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #999999;
    }
</style>
<div class="page-header col-md-12" style="text-align: center;">
    <h1 style="font-size: 20px;font-weight: bolder">月客流量统计</h1>
    <table class="imagetable">
        <tr>
            <th>${ctime}/日期</th><th>数量</th><th>${ptime}/日期</th><th>数量</th>
        </tr>
            <c:forEach items="${list}" var="data" varStatus="status">
                <tr>
                    <td>${status.index+1 }日</td><td>${data.c}人</td><td>${status.index+1}日</td><td>${data.pre}人</td>
                </tr>
            </c:forEach>
    </table>
</div>
