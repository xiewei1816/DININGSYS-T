<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script src="app/js/DININGSYS/system/menu/menu_validation.js"></script>
<script>
    jQuery(document).ready(function() {
        menuFormValidation.addFormInit();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title"><span class="menuBtnEditClass">菜单</span>新增</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="menuAddForm">
                <div class="form-body">
                    <div class="form-group">
                        <label  class="col-md-3 control-label"><span class="menuBtnEditClass">菜单</span>名称<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" name="menuName" class="form-control"  placeholder="请输入名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">父级菜单<span class="required">*</span></label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysMenu.menuName}</p>
                            <input type="hidden" name="parentId" class="form-control" value="${sysMenu.id}">
                            <input type="hidden" name="menuOrder" class="form-control" value="${order}">
                        </div>
                    </div>
                    <div class="form-group" id="menuBtnEditUrl">
                        <label  class="col-md-3 control-label" id="menuUrlValid">菜单URL<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" id="menuAddUrl" value="javascript:void(0)" class="form-control" name="menuUrl"  placeholder="请输入菜单URL，DININGSYS/xx">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label"><span class="menuBtnEditClass">菜单</span>代码<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="menuCode" placeholder="请输入代码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">菜单类型<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="menuType"  class="form-control" onchange="sysMenu.changeInput(this);" id="menuTypeEditID">
                                <option value="default">默认</option>
                                <option value="button">按钮</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label"><span class="menuBtnEditClass">菜单</span>状态<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="menuState"  class="form-control">
                                <option value="enable">启用</option>
                                <option value="disabled">禁用</option>
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
    <button type="button" class="btn blue" onclick="sysMenu.addMenu()">保存</button>
</div>