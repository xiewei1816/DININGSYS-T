<%--
  Created by zhshuo.
  Date: 2016-10-17
  Time: 13:35
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    jQuery(document).ready(function () {
        itemFile.publicProMethodsInit();
    })
</script>
<div style="margin: 5px">
    <div style="height: 600px;float: left;width: 200px;">
        <div class="content zTreeDemoBackground">
            <ul id="pubProMeType" class="ztree"></ul>
        </div>
    </div>
    <div style="height: 300px;width: 460px;float: left;padding-left: 5px;overflow: auto;margin-bottom: 50px">
        <h4 style="display: inline">可选内容：<span id="kxids"></span></h4><span id="chooseAll" style="margin-left:250px;cursor: pointer">全部选择</span>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>说明</th>
            </tr>
            </thead>
            <tbody id="pubProMeT1">
            </tbody>
        </table>
    </div>
    <div style="height: 300px;width: 460px;overflow: auto;float: left;padding-left: 5px">
        <h4 style="display: inline">已选内容：<span id="yxids"></span></h4><span id="cancelAll" style="margin-left:250px;cursor: pointer">全部取消</span>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>说明</th>
            </tr>
            </thead>
            <tbody id="pubProMeT2">
                <c:forEach items="${proMeInIds}" var="pubPro">
                    <tr>
                        <td style="display: none">${pubPro.id}</td>
                        <td>${pubPro.pmcode}</td>
                        <td>${pubPro.pmname}</td>
                        <td>${pubPro.description}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>