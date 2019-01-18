<%--
  Created by mrren.
  Date: 2016-07-25
  Time: 15:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/system/user/user_validation.js"></script>
<script>
    jQuery(document).ready(function() {
        App.initHandleScrollers();
        sysUser.initUserDeptTree(${allBdOrgan});
        userFormValidation.EditFormInit();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">用户信息修改</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="userEditForm">
                <div class="form-body">
                    <div class="form-group" id="checkUserNameClassId">
                        <label  class="col-md-3 control-label">用户名<span class="required">*</span></label>
                        <div class="col-md-9">
                            <div class="input-icon right">
                                <input type="text" id="newName" name="username" class="form-control"  placeholder="请输入用户名" value="${sysUser.username}" onblur="sysUser.checkUserNameExistAdd(this,'edit')">
                                <input type="hidden" id="userId" name="id" value="${sysUser.id}">
                                <input type="hidden" id="oldName" name="oldName"value="${sysUser.username}">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">姓名<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="nickname"  placeholder="请输入姓名" value="${sysUser.nickname}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">性别<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="gender" class="form-control">
                                <option value="男" <c:if test="${sysUser.gender == '男'}">selected</c:if>>男</option>
                                <option value="女" <c:if test="${sysUser.gender == '女'}">selected</c:if>>女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">联系方式<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="phone"  placeholder="请输入联系方式" value="${sysUser.phone}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">email<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="email"  placeholder="请输入email" value="${sysUser.email}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">所属部门<span class="required">*</span></label>
                        <div class="col-md-7">
                            <p class="form-control-static" id="showUserDeptP">${sysUser.deptName}</p>
                            <input name="deptId" id="userDeptID" type="hidden" value="${sysUser.deptId}"/>
                        </div>
                        <div class="col-md-2">
                            <p class="form-control-static"><a data-toggle="modal" data-target="#userDeptChoose" class="btn btn-xs default">部门选择 <i class="fa fa-user"></i></a></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">职位<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" value="${sysUser.job}" name="job" placeholder="请输入职位">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">是否为领导<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="isLeader"  class="form-control">
                                <option value="1" <c:if test="${sysUser.isLeader == 1}">selected</c:if>>是</option>
                                <option value="0" <c:if test="${sysUser.isLeader == 0}">selected</c:if>>否</option>
                            </select>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn blue" onclick="sysUser.updateUser()">保存</button>
</div>

<div id="userDeptChoose" class="modal fade" tabindex="-1" data-width="400px">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title">用户部门选择</h4>
    </div>
    <div class="modal-body zTreeDemoBackground">
        <div class="scroller" style="height:450px;" data-always-visible="1" data-rail-visible="0">
            <ul id="userDeptTree" class="ztree"></ul>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn default" data-dismiss="modal">取消</button>
        <button type="button" class="btn blue" onclick="sysUser.saveUserDept()">保存</button>
    </div>
</div>