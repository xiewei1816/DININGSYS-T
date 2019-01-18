<%--
  Created by zhshuo.
  Date: 2016-10-31
  Time: 10:39
  des: 系统使用权限，角色下面用户选择
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    sysPer.roleUserInit();
</script>
<form class="form-horizontal" style="width: 97%">
    <input type="hidden" id="roleId" value="${sysRole.id}"/>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label class="control-label col-md-6">组名称:</label>
                <div class="col-md-6">
                    <p class="form-control-static">${sysRole.roleName}</p>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label class="control-label col-md-6">描述:</label>
                <div class="col-md-6">
                    <p class="form-control-static">${sysRole.description}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label class="control-label col-md-3">用户选择</label>
                <div class="col-md-9">
                    <select name="country" class="multi-select" multiple="" id="role_choose_user" >
                        <c:forEach items="${sysUsers}" var="user">
                            <option value="${user.id}" <c:if test="${not empty user.selected}">selected</c:if> >${user.empCode}-${user.empName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
</form>