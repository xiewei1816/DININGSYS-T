<%--
  Created by zhshuo.
  Date: 2016-10-09
  Time: 10:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/archive/giftForm/giftForm_validation.js"></script>
<script type="text/javascript">
    $(function(){
        giftFormValidation.editFormInit();
    });
</script>
<form id="editForm" class="editForm">
    <input type="hidden" id="hideId" name="id" value="${dgGiftForm.id}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>编码</span>
            <div class="form-group biger-wid">
                <c:choose>
                    <c:when test="${not empty nextCode}">
                        <input readonly placeholder="请输入编码" type="text" class="form-control" name="gfcode" value="${nextCode}"/>
                    </c:when>
                    <c:otherwise>
                        <input readonly placeholder="请输入编码" type="text" class="form-control" name="gfcode" value="${dgGiftForm.gfcode}"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>名称</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" id="gfname" name="gfname" placeholder="请输入名称" value="${dgGiftForm.gfname}"/>
            </div>
        </li>
        <li>
            <span class="title">赠单原因类型</span>
            <div class="form-group bigest-wid">
                <select class="form-control" name="gfreason">
                    <option value="">默认类型</option>
                    <c:forEach items="${allGiftFormReason}" var="item">
                        <option <c:if test="${dgGiftForm.gfreason == item.id}">selected</c:if> value="${item.id}">${item.gfrtype}</option>
                    </c:forEach>
                </select>
            </div>
        </li>
        <li>
            <span class="title">说明</span>
            <div class="form-group bigest-wid">
                <textarea class="form-control" name="gfdescription" rows="2" cols="2"
                          placeholder="说明信息">${dgGiftForm.gfdescription}</textarea>
            </div>
        </li>
    </ul>
</form>