<%--
  Created by zhshuo.
  Date: 2016-11-02
  Time: 9:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    sysPer.roleUserInit();
</script>
<form class="form-horizontal" id="roleUserForm" style="width: 97%">
    <input type="hidden" name="id" value="${sysRole.id}"/>
    <input type="hidden" name="userIds" id="userIds"/>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label class="control-label col-md-3">组名称:</label>
                <div class="col-md-9">
                    <input placeholder="请输入组名称" class="form-control" name="roleName" type="text" value="${sysRole.roleName}" />
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label class="control-label col-md-3">描述:</label>
                <div class="col-md-9">
                    <input placeholder="请输入组描述" type="text" class="form-control" name="description" value="${sysRole.description}">
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label class="control-label col-md-3">用户选择:</label>
                <div class="col-md-9">
                    <select class="multi-select" multiple="" id="role_choose_user" >
                        <c:forEach items="${sysUsers}" var="user">
                            <option value="${user.id}" <c:if test="${not empty user.selected}">selected</c:if> >${user.empCode}-${user.empName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
</form>