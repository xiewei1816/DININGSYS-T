<%--
  Created by zhshuo.
  Date: 2016-07-29
  Time: 15:24
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table id="organ-index-table">
    <thead>
        <tr>
            <td>组织机构名称</td>
            <td>组织机构代码</td>
            <td>组织机构描述</td>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${allSysOrgan}" var="organ">
            <tr organ-id="${organ.id}" data-tt-id="${organ.id}" <c:if test="${organ.parentId != null}">data-tt-parent-id="${organ.parentId}"</c:if> >
                <td>${organ.organName}</td>
                <td>${organ.organCode}</td>
                <td>${organ.description}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<script>
    $("#organ-index-table").treetable({ expandable: true });
    $("#organ-index-table tbody").on("mousedown", "tr", function() {
        $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
    });
</script>