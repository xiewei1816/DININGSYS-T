<%--
  Created by zhshuo.
  Date: 2016-10-31
  Time: 9:11
  des:系统使用权限，用户权限组编辑
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    sysPer.userRoleInit();
</script>
<form class="form-horizontal" id="userRoleForm" style="width: 97%">
    <input type="hidden" value="${sysUser.id}" name="id">
    <input type="hidden" name="roleIds" id="roleIds">
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label class="control-label col-md-6">编号:</label>
                <div class="col-md-6">
                    <p class="form-control-static">${sysUser.empCode}</p>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label class="control-label col-md-6">用户名称:</label>
                <div class="col-md-6">
                    <p class="form-control-static">${sysUser.empName}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label class="control-label col-md-3">隶属权限组</label>
                <div class="col-md-9">
                    <select class="multi-select" multiple="" id="user_choose_role" >
                        <c:forEach items="${sysRoles}" var="role">
                            <option value="${role.id}" <c:if test="${not empty role.selected}">selected</c:if> >${role.roleName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
</form>