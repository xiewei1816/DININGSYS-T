<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<script src="app/js/DININGSYS/system/menu/sys_menu.js"></script>
<div class="row">
    <div class="col-md-6">
        <div class="portlet purple box">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>菜单展示</div>
                <div class="tools">
                    <a href="javascript:;" class="reload"></a>
                </div>
            </div>
            <div class="portlet-body zTreeDemoBackground">
                <%--scroller需要手动设置高度--%>
                <div class="scroller" style="height:650px" data-always-visible="1" data-rail-visible="0">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
                <div id="ajax-modal" class="modal fade" tabindex="-1"></div>
                <div id="delMenu" class="modal fade" tabindex="-1" data-width="300" data-height="80" data-id="">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                        <h4 class="modal-title">删除菜单提示</h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <input type="hidden" id="menuDelId"/>
                                <h4>确认删除该菜单？</h4>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
                        <button type="button" class="btn blue" onclick="sysMenu.delMenu()">删除</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <div class="portlet box green">
            <div class="portlet-title">
                <div class="caption"><i class="fa fa-cogs"></i>菜单信息</div>
                <div class="tools">
                    <a href="javascript:;" class="reload"></a>
                </div>
            </div>
            <div class="portlet-body">
                <div class="scroller" style="height:400px" data-always-visible="1" data-rail-visible="0">
                    <div class="row" >
                        <div class="col-md-12 form">
                            <form class="form-horizontal" role="form">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label"><span class="menuBtnClass">菜单</span>名称</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static" id="menuNameID"></p>
                                            <input type="hidden" id="menuModifyStateID" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">父级菜单</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static" id="menuParentNameID"></p>
                                        </div>
                                    </div>
                                    <div class="form-group" id="menuUrl">
                                        <label  class="col-md-3 control-label">菜单URL</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static" id="menuURLID"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label"><span class="menuBtnClass">菜单</span>代码</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static" id="menuCodeID"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">菜单类型</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static" id="menuTypeID"></p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label"><span class="menuBtnClass">菜单</span>状态</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static" id="menuStateID"></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-actions fluid">
                                    <div class="col-md-offset-3 col-md-9">
                                        <button id="enableBtnId" type="button" class="btn green" onclick="sysMenu.modifyMenuState('enable')">启用</button>
                                        <button id="disableBtnID" type="button" class="btn gray" onclick="sysMenu.modifyMenuState('disable')">禁用</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script>
    jQuery(document).ready(function() {
        sysMenu.getMenuData();
        App.initHandleScrollers();
        sysMenu.initAddOrEditModal();
    });
</script>