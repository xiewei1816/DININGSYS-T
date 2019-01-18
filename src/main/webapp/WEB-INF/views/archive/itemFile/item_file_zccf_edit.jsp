<%--
  Created by heshuai.
  Date: 2017-05-19
  Time: 10:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="app/css/fileInput.css"/>
<script>
    jQuery(document).ready(function () {
        itemFile.itemFileEditPageInit();
    });
</script>
<div>
    <div>
        <div>
            <div style="height: 50px;text-align: right">
                <a href="javascript:void(0)" id="zccfAdd" class="btn btn-default btn-small active"
                   role="button">新增</a>
                <a href="javascript:void(0)" id="zccfDel" class="btn btn-default btn-small active"
                   role="button">删除</a>
                <a href="javascript:void(0)" id="zccfClear" class="btn btn-default btn-small active"
                   role="button">清空</a>
            </div>
            <div class="table-responsive" style="height: 400px">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>成分名称</th>
                        <th>成分类型名称</th>
                        <th>单位</th>
                        <th>数量</th>
                    </tr>
                    </thead>
                    <tbody id="zccfTbody">
                    <c:forEach items="${zccfMethods}" var="zccfMethods">
                        <tr>
                            <td style="display: none">
                                    ${zccfMethods.id}
                            </td>
                            <td style="display: none">
                                    ${zccfMethods.inveItemId}
                            </td>
                            <td>${zccfMethods.inveItemCode}</td>
                            <td>${zccfMethods.name}</td>
                            <td>${zccfMethods.bName}</td>
                            <td>${zccfMethods.unit}</td>
                            <td><input type="text" value="${zccfMethods.counter}"></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>