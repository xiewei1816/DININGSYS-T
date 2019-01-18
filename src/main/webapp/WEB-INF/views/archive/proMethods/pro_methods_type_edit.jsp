<%--
  Created by zhshuo.
  Date: 2016-10-11
  Time: 13:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="app/js/DININGSYS/archive/proMethods/pro_methods_validation.js"></script>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript">
$(function () {
    proMethodsValidation.proMethodsTypeFormInit();
	//初始化客位类型
	var organidVal = $("#organidVal").val();
	$("#organid").val(organidVal);
});
</script>
<form id="proMethTypeEditForm" class="editForm">
    <input type="hidden" id="hideId" name="id" value="${dgProMethodsType.id}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>名称</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入名称" type="text" class="form-control" name="pmtname" value="${dgProMethodsType.pmtname}"/>
            </div>
        </li>
        <li>
            <span class="title">顺序号</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="pmtorder" value="${dgProMethodsType.pmtorder}"/>
            </div>
        </li>
        <li>
            <span class="title">所属组织机构<span class="required">*</span></span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" id="organidVal" value="${dgProMethodsType.organid}" style="display: none;"/>
                <select class="form-control edit_organid" id="organid" name="organid" editype="val" dete="val">
                    <option value="">请选择组织机构</option>
					 <c:forEach items="${listOrg}" var="o" varStatus="index">
					 	<option  value="${o.id}" <c:if test="${dgProMethodsType.organid == o.id}">selected</c:if>>${o.orgName}</option>
					 </c:forEach>
				</select>
            </div>
        </li>
    </ul>
</form>
