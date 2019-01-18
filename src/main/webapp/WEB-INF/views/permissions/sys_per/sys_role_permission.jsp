<%--
  Created by zhshuo.
  Date: 2016-11-02
  Time: 14:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    jQuery(document).ready(function () {
        sysPer.initRolePermissionPage();
    })
</script>
<style>
    .tab-pane{
        height:450px;
        overflow-y:scroll;
        overflow-x:scroll;
    }
</style>

<form class="form-horizontal" role="form" style="width: 97%">
    <input type="hidden" id="roleId" value="${sysRole.id}"/>
    <div class="row">
        <div class="col-md-6">
            <div class="form-group">
                <label class="control-label col-md-4">组名称:</label>
                <div class="col-md-8">
                    <p class="form-control-static">${sysRole.roleName}</p>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="form-group">
                <label class="control-label col-md-2">描述:</label>
                <div class="col-md-10">
                    <p class="form-control-static">${sysRole.description}</p>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="padding-left: 30px;">
        <div class="col-md-11">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">系统模块</h3>
                    </span>
                </div>
                <div class="panel-body">
                    <div class="tab-content">
                        <div class="tab-pane active">
                            <ul id="sysMenuTree" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>