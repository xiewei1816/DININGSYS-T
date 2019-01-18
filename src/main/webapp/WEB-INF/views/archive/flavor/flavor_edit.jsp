<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by zengchao
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="app/js/DININGSYS/archive/flavor/flavor_validation.js"></script>
<script>
    jQuery(document).ready(function () {
        flavorValidation.validFormInit();
    })
</script>

<form id="flavorEditForm" class="editForm">
    <input type="hidden" name="id" value="${flavor.id}">
    <input type="hidden" name="parentid" value="${flavor.parentid}">
    <input type="hidden" name="createtime" value="${flavor.createtime}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>编号</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入编号" type="text" class="form-control"  name="number" value="${flavor.number}"/>
            </div>
        </li>
        <li> 
            <span class="title"><span class="required">*</span>名称</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入名称" type="text" class="form-control"  name="name" value="${flavor.name}"/>
            </div>
        </li>
        <li>
            <span class="title">助记符</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="zjf" value="${flavor.zjf}"/>
            </div>
        </li>
        <li>
            <span class="title">顺序号</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="px" value="${flavor.px}"/>
            </div>
        </li>
        <c:if test="${flavor.parentid == 1 or flavor.parentid == 2}">
            <li>
                <span class="title">是否单选</span>
                <div class="form-group bigest-wid">
                    <input type="checkbox" name="isonly" value="1" <c:if test='${flavor.isonly == 1}'>checked="checked"</c:if> >
                    
                </div>
            </li>
        </c:if>
        
    </ul>
</form>