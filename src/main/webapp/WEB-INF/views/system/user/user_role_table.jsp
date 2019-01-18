<%--
  Created by mrren.
  Date: 2016-07-28
  Time: 10:47
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    jQuery(document).ready(function() {
        sysUser.initUserRoleTable();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">角色选择</h4>
</div>
<div class="modal-body">
    <table class="table table-striped table-bordered table-hover" id="user_role_table">
        <thead>
        <tr>
            <th class="table-checkbox"><input type="checkbox" class="group-checkable" data-set="#user_role_table .checkboxes" /></th>
            <th>角色名</th>
            <th >描述</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sysRoles}" var="role">
            <tr class="odd gradeX">
                <td><input type="checkbox" class="checkboxes" value="${role.id}" <c:if test="${role.checked}">checked="checked"</c:if> /></td>
                <td>${role.roleName}</td>
                <td >${role.description}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
    <button type="button" class="btn btn-primary" onclick="sysUser.saveUserRoles(${useId})">保存</button>
</div>