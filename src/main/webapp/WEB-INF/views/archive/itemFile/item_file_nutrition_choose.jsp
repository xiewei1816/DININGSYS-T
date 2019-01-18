<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by zhshuo.
  Date: 2016-10-20
  Time: 9:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function ($) {
        itemFile.initNutritionChoose();
    })
</script>
<div style="margin: 5px">
    <div style="height: 300px;width: 460px;float: left;padding-left: 5px;overflow: auto;margin-bottom: 50px">
        <h4 style="display: inline">可选内容：<span id="kxids"></span></h4><span id="nutritionChooseAll" style="margin-left:250px;cursor: pointer">全部选择</span>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>标准摄入量</th>
                <th>单位</th>
            </tr>
            </thead>
            <tbody id="nutritionTab1">
                <c:forEach items="${nutritionNotInIds}" var="nutrition">
                    <tr>
                        <td style="display: none">${nutrition.id}</td>
                        <td>${nutrition.code}</td>
                        <td>${nutrition.name}</td>
                        <td>${nutrition.bzsrl}</td>
                        <td>${nutrition.unit == 1?'千焦':'克'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div style="height: 300px;width: 460px;overflow: auto;float: left;padding-left: 5px">
        <h4 style="display: inline">已选内容：<span id="yxids"></span></h4><span id="nutritionCancelAll" style="margin-left:250px;cursor: pointer">全部取消</span>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>编号</th>
                <th>名称</th>
                <th>标准摄入量</th>
                <th>单位</th>
            </tr>
            </thead>
            <tbody id="nutritionTab2">
            <c:forEach items="${nutritionInIds}" var="nutrition">
                <tr>
                    <td style="display: none">${nutrition.id}</td>
                    <td>${nutrition.code}</td>
                    <td>${nutrition.name}</td>
                    <td>${nutrition.bzsrl}</td>
                    <td>${nutrition.unit == 1?'千焦':'克'}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>