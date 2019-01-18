<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="editForm" class="editForm">
    <ul class="edit-contents animated fadeInUp">
    	<input type="hidden" name="id" value="${item.id}"/>
        <li>
            <span class="title"><span class="required">*</span>mac地址</span>
            <div class="form-group biger-wid">
                <input placeholder="mac地址" type="text" class="form-control" name="mac" dete="val" value="${item.mac}" <c:if test="${item.mac != null}"> readonly="true"</c:if>/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>选择客位:</span>
            <div class="form-group bigest-wid">
                <select class="form-control" id="seatid" name="seatid" dete="val" >
                	<c:if test="${empty itemSeats}"><option selected></option></c:if>
					 <c:forEach items="${itemSeats}" var="o" varStatus="index">
					 		<option  value="${o.id}" <c:if test="${item.seatid == o.id}">selected</c:if>>${o.name}</option>
					 </c:forEach>
			    </select>
            </div>
        </li>
    </ul>
</form>