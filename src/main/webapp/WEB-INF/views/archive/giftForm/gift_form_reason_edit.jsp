<%--
  Created by zhshuo.
  Date: 2016-10-09
  Time: 10:51
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/archive/giftForm/giftForm_reason_validation.js"></script>
<script type="text/javascript">
    $(function(){
        giftFormReasonValidation.editFormInit();
    });
</script>
<form id="giftReasonEditForm" class="editForm">
    <input type="hidden" id="hideId" name="id" value="${dgGiftFormReason.id}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>编码</span>
            <div class="form-group biger-wid">
                <c:choose>
                    <c:when test="${not empty nextCode}">
                        <input placeholder="请输入编码" readonly type="text" class="form-control" name="gfcode" value="${nextCode}"/>
                    </c:when>
                    <c:otherwise>
                        <input placeholder="请输入编码" readonly type="text" class="form-control" name="gfcode" value="${dgGiftFormReason.gfcode}"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>赠单原因类型</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" id="gfrtype" name="gfrtype" placeholder="请输入赠单原因类型" value="${dgGiftFormReason.gfrtype}"/>
            </div>
        </li>
    </ul>
</form>