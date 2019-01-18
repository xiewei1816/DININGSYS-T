<%--
  Created by zhshuo.
  Date: 2016-10-19
  Time: 16:33
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/archive/itemFile/itemFileType_validation.js"></script>
<script>
    jQuery(document).ready(function () {
        itemFileTypeValidation.itemFileNutritionValidation();
    })
</script>
<form id="editForm" class="editForm">
    <input type="hidden" id="hideId" name="id" value="${dgNutrition.id}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>编码</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入编码" type="text" class="form-control" name="code" value="${dgNutrition.code}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>名称</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="name" placeholder="请输入名称" value="${dgNutrition.name}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>标准摄入量</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="bzsrl" placeholder="请输入标准摄入量" value="${dgNutrition.bzsrl}"/>
            </div>
        </li>
        <li>
            <span class="title">单位</span>
            <div class="form-group bigest-wid">
                <select name="unit" class="form-control">
                    <option value="1" <c:if test="${dgNutrition.unit == 1}">selected</c:if> >千焦</option>
                    <option value="2" <c:if test="${dgNutrition.unit == 2}">selected</c:if> >克</option>
                </select>
            </div>
        </li>
        <li>
            <span class="title">助记符</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="zjf" placeholder="请输入助记符" value="${dgNutrition.zjf}"/>
            </div>
        </li>
    </ul>
</form>