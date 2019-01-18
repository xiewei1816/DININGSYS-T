<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/system/menu/menu_validation.js"></script>
<script>
    jQuery(document).ready(function() {
        menuFormValidation.EditFormInit();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">${type}修改</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="menuEditForm">
                <div class="form-body">
                    <div class="form-group">
                        <label  class="col-md-3 control-label">${type}名称<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" name="menuName" class="form-control"  placeholder="请输入名称" value="${SysMenu.menuName}">
                            <input type="hidden" name="id" value="${SysMenu.id}">
                            <input type="hidden" name="menuOrder"  value="${SysMenu.menuOrder}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">父级菜单<span class="required">*</span></label>
                        <div class="col-md-9">
                            <c:choose>
                                <c:when test="${SysMenu.menuType == 'button'}">
                                    <p class="form-control-static">${SysMenu.parentName}</p>
                                    <input type="hidden" name="parentId" value="${SysMenu.parentId}">
                                </c:when>
                                <c:otherwise>
                                    <select name="parentId"  class="form-control">
                                        <option value="">顶级菜单</option>
                                        <c:forEach items="${allParentSysMenu}" var="parentSysMeu">
                                            <option value="${parentSysMeu.id}" <c:if test="${SysMenu.parentId == parentSysMeu.id}">selected</c:if> >${parentSysMeu.menuName}</option>
                                        </c:forEach>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <c:if test="${SysMenu.menuType == 'default'}">
                        <div class="form-group">
                            <label  class="col-md-3 control-label">菜单URL<span class="required">*</span></label>
                            <div class="col-md-9">
                                <input type="text" class="form-control" name="menuUrl"  placeholder="请输入菜单URL，DININGSYS/xx" value="${SysMenu.menuUrl}">
                            </div>
                        </div>
                    </c:if>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">${type}代码<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="menuCode" placeholder="请输入代码" value="${SysMenu.menuCode}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">菜单类型<span class="required">*</span></label>
                        <div class="col-md-9">
                            <p class="form-control-static">
                                <c:if test="${SysMenu.menuType == 'default'}">
                                    默认
                                </c:if>
                                <c:if test="${SysMenu.menuType == 'button'}">
                                    按钮
                                </c:if>
                            </p>
                            <input type="hidden" name="menuType" value="${SysMenu.menuType}">
                            <%--<select name="menuType"  class="form-control" onchange="sysMenu.changeInput(this);" id="menuTypeEditID">
                                <option value="default" <c:if test="${SysMenu.menuType == 'default'}">selected="selected"</c:if>>默认</option>
                                <option value="button" <c:if test="${SysMenu.menuType == 'button'}">selected="selected"</c:if>>按钮</option>
                            </select>--%>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">${type}状态<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="menuState"  class="form-control">
                                <option value="enable" <c:if test="${SysMenu.menuState == 'enable'}">selected="selected"</c:if>>启用</option>
                                <option value="disabled" <c:if test="${SysMenu.menuState == 'disabled'}">selected="selected"</c:if>>禁用</option>
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
    <button type="button" class="btn blue" onclick="sysMenu.updateMenu()">保存</button>
</div>