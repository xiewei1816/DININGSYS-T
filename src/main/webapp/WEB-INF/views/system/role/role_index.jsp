<%--
  Created by mrren.
  Date: 2016-07-26
  Time: 17:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/system/role/sys_role.js"></script>
<script src="assets/plugins/bootbox/bootbox.min.js" type="text/javascript" ></script>
<div class="table-toolbar">
    <div class="clearfix">
        <a href="javascript:void(0)" class="btn green roleAdd" data-toggle="modal">新增 <i class="fa fa-plus"></i></a>
        <a href="javascript:void(0)" clickType="a" class="btn purple roleBaseEdit"><i class="fa fa-times"></i> 角色编辑</a>
        <a href="javascript:void(0)" clickType="a" class="btn red roleDelBtn"><i class="fa fa-times"></i> 删除</a>
    </div>
</div>
<table class="table table-striped table-bordered table-hover" id="role_table">
    <thead>
    <tr>
        <th class="table-checkbox"><input type="checkbox" class="group-checkable" data-set="#role_table .checkboxes" /></th>
        <th>角色名</th>
        <th>角色签名</th>
        <th>描述</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>权限操作</th>
        <th>锁定角色</th>
    </tr>
    </thead>
    <tbody>

    </tbody>
</table>

<div id="role_ajax_modal" class="modal fade" tabindex="-1"></div>
<script>

    jQuery(document).ready(function() {
        sysRole.init();
    });
</script>

