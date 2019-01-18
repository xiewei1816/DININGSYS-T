<%--
  Created by mrren.
  Date: 2016-07-27
  Time: 11:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function () {
        App.initHandleScrollers();
        var menuList = ${sysMenuList};
        sysRole.initZtree(menuList['sysMenuList']);
    })
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">角色权限详情编辑</h4>
</div>
<div class="modal-body zTreeDemoBackground">
    <input type="hidden" id="roleID" value="${roleId}">
    <%--<span class="help-block">蓝色菜单代表具体功能权限</span>--%>
    <div class="scroller" style="height:450px;" data-always-visible="1" data-rail-visible="0">
        <ul id="roleTreeDemo" class="ztree"></ul>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">取消</button>
    <button type="button" class="btn blue" onclick="sysRole.editRoleQX()">保存</button>
</div>

