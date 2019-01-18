<%--
  Created by IntelliJ IDEA.
  User: 35079_000
  Date: 2016-07-21
  Time: 15:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/system/user/sys_user.js"></script>
<div class="table-toolbar">
    <div class="clearfix">
        <a href="javascript:void(0)" class="btn green userTopAdd" data-toggle="modal">新增 <i class="fa fa-plus"></i></a>
        <a href="javascript:void(0)" clickType="a" class="btn blue userInfoBtn">详情 <i class="fa fa-info"></i></a>
        <a href="javascript:void(0)" clickType="a" class="btn purple userEditBtn">修改 <i class="fa fa-edit"></i></a>
        <a href="javascript:void(0)" clickType="a" class="btn red userDelBtn"><i class="fa fa-times"></i> 删除</a>
        <a href="javascript:void(0)" clickType="a" class="btn dark userLockBtn">冻结 <i class="fa fa-lock"></i></a>
        <a href="javascript:void(0)" clickType="a" class="btn yellow userResetBtn">密码重置 <i class="fa fa-exclamation"></i></a>
    </div>
</div>
<table class="table table-striped table-bordered table-hover" id="user_table">
    <thead>
        <tr>
            <th class="table-checkbox"><input type="checkbox" class="group-checkable" data-set="#user_table .checkboxes" /></th>
            <th>ID</th>
            <th>用户名</th>
            <th>姓名</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>email</th>
            <th>角色</th>
           <%-- <th style="width: 200px">操作</th>--%>
        </tr>
    </thead>
    <tbody>

    </tbody>
</table>

<div id="user_ajax_modal" class="modal fade" tabindex="-1" data-width="700px"></div>
<script>

    jQuery(document).ready(function() {
        sysUser.init();
    });
</script>
