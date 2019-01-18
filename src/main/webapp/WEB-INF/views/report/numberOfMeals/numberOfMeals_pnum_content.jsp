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
        width: 100px;
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
    <h1 style="font-size: 20px;font-weight: bolder">就餐人数情况-人数,次数</h1>
    <div style="margin: 10px auto 0;width: 700px;height: 30px;text-align: center;line-height: 30px;">
        营业日:${startTime} 至${endTime} PS：人数*次数=合计
    </div>
    <table class="imagetable">
        <tr>
            <th>市别</th><th>开台人数</th><th>次数</th><th>就餐人数合计</th><th>占总比列人数</th><th>消费金额</th><th>人均消费金额</th>
        </tr>
        <c:forEach items="${list}" var="listdata" >
            <c:forEach items="${listdata.datas}" var="data" varStatus="status">
                <tr>
                    <c:if test="${status.index == 0}"><td rowspan="${fn:length(listdata.datas)}">${listdata.bis.bisName}</td></c:if><td>${data.pcount}人</td><td>${data.psumcount}次</td><td>${data.hj}</td><td>${data.pj}%</td><td>${data.cnum}</td><td>${data.rjxf}元</td>
                </tr>
            </c:forEach>
            <tr style="background:#b5cfd2;">
                <td>${listdata.bis.bisName}小计</td><td></td><td>${listdata.s_jccs}次</td><td>${listdata.s_hj}</td><td>${listdata.s_pj}%</td><td>${listdata.s_cnum}</td><td></td>
            </tr>
        </c:forEach>
        <tr>
            <td>合计</td><td></td><td>${cssum}次</td><td>${psum}</td><td></td><td>${jesum}</td><td></td>
        </tr>
    </table>
</div>