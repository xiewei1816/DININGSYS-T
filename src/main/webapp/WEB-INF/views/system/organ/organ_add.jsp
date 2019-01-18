<%--
  Created by zhshuo.
  Date: 2016-07-29
  Time: 13:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    jQuery(document).ready(function() {
        sysOrganValidation.addFormInit();
        sysOrgan.getsysOrganData();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">组织新增</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="sysOrganAddForm">
                <div class="form-body">
                    <div class="form-group">
                        <label  class="col-md-3 control-label">组织名称<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" value="${sysOrgan.organName}" name="organName" class="form-control"  placeholder="请输入组织名称">
                                <input type="hidden" value="${sysOrgan.id}" name="id">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">组织代码<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" value="${sysOrgan.organCode}" name="organCode" class="form-control" placeholder="请输入组织代码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">上级组织</label>
                        <div class="col-md-7">
                            <p class="form-control-static" id="sysOrganParentID">
                                <c:choose>
                                    <c:when test="${sysOrgan.parentName != null}">
                                        ${sysOrgan.parentName}
                                    </c:when>
                                    <c:otherwise>
                                        当前未选择上级组织
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            <input value="${sysOrgan.parentId}" name="parentId" id="parentId" type="hidden"/>
                            <span class="help-block">顶级组织无需选择此项</span>
                        </div>
                        <div class="col-md-2">
                            <p class="form-control-static"><a data-toggle="modal" data-target="#sysOrganParentChoose" class="btn btn-xs default">组织选择</a></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">组织描述<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" value="${sysOrgan.description}" class="form-control" name="description"  placeholder="请输入组织简略描述">
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="sysOrganParentChoose" class="modal fade" tabindex="-1" data-width="450px">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title">上级组织选择</h4>
    </div>
    <div class="modal-body zTreeDemoBackground">
        <ul id="sysOrganParentTree" class="ztree"></ul>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
        <button type="button" class="btn btn-primary" onclick="sysOrgan.saveSelectedParentOrgan()">确认</button>
    </div>
</div>

<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">取消</button>
    <button type="button" class="btn blue" onclick="sysOrgan.addSysOrgan('${type}')">保存</button>
</div>