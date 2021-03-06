<%--
  Created by mrren.
  Date: 2016-07-27
  Time: 15:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="app/js/DININGSYS/system/role/role_validation.js"></script>
<script>
    jQuery(document).ready(function() {
        roleFormValidation.addFormInit();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">角色新增</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="roleAddForm">
                <div class="form-body">
                    <div class="form-group">
                        <label  class="col-md-3 control-label">角色名称<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" name="roleName" class="form-control"  placeholder="请输入角色名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">角色签名<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" name="roleSign" class="form-control" placeholder="请输入角色签名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">角色描述<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="description"  placeholder="角色描述">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">角色状态<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="state"  class="form-control">
                                <option value="1">正常</option>
                                <option value="0">锁定</option>
                            </select>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">取消</button>
    <button type="button" class="btn blue" onclick="sysRole.addRole()">保存</button>
</div>