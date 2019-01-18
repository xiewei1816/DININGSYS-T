<%--
  Date: 2017-10-18 10:33
  @Author zhshuo.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form id="editForm" class="editForm">
    <input type="hidden" id="wayId" name="wayId" value="">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>支付方式</span>
            <div class="form-group biger-wid">
                <input readonly type="text" class="form-control"
                       value="${wayName}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>支付类型</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" value="${dgGiftForm.gfname}"/>
            </div>
        </li>
        <li>
            <span class="title">抵扣方式</span>
            <div class="form-group bigest-wid">
                <select class="form-control" name="gfreason">
                    <option value="">默认类型</option>
                    <c:forEach items="${allGiftFormReason}" var="item">
                        <option
                                <c:if test="${dgGiftForm.gfreason == item.id}">selected</c:if>
                                value="${item.id}">${item.gfrtype}</option>
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