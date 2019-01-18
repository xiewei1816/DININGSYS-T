<%--
  Created by mrren.
  Date: 2016-07-25
  Time: 14:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/system/user/user_validation.js"></script>
<script>
    jQuery(document).ready(function() {
        App.initHandleScrollers();
        userFormValidation.addFormInit();
        sysUser.initUserRoleTable();
        sysUser.initUserDeptTree(${allBdOrgan});
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">用户新增</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="userAddForm">
                <div class="form-body">
                    <div class="form-group" id="checkUserNameClassId">
                        <label  class="col-md-3 control-label">用户名<span class="required">*</span></label>
                        <div class="col-md-9">
                            <div class="input-icon right">
                                <input type="text" id="userName" name="username" class="form-control"  placeholder="请输入用户名" onblur="sysUser.checkUserNameExistAdd(this,'add')">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">姓名<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" name="nickname" class="form-control"  placeholder="请输入姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">性别<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="gender"  class="form-control">
                                <option value="男">男</option>
                                <option value="女">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">联系方式<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="phone" placeholder="请输入联系方式" maxlength="11">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">email<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="email" placeholder="请输入联系方式">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">所属部门<span class="required">*</span></label>
                        <div class="col-md-7">
                            <p class="form-control-static" id="showUserDeptP">当前未选择部门</p>
                            <input name="deptId" id="userDeptID" type="hidden"/>
                        </div>
                        <div class="col-md-2">
                            <p class="form-control-static"><a data-toggle="modal" data-target="#userDeptChoose" class="btn btn-xs default">部门选择 <i class="fa fa-user"></i></a></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">职位<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" name="job" placeholder="请输入职位">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">是否为领导<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="isLeader" class="form-control">
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">状态<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="state"  class="form-control">
                                <option value="normal">正常</option>
                                <option value="locked">锁定</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">角色选择<span class="required">*</span></label>
                        <div class="col-md-7">
                            <p class="form-control-static" id="userRoleID">当前已选<span id="checkedRoleLenthID">0</span>个角色</p>
                            <input name="userRoleIDs" id="userRoleIDs" type="hidden"/>
                        </div>
                        <div class="col-md-2">
                            <p class="form-control-static"><a data-toggle="modal" data-target="#userRoleChoose" class="btn btn-xs default">角色选择 <i class="fa fa-user"></i></a></p>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div id="userRoleChoose" class="modal fade" tabindex="-1" data-width="700px">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title">角色选择</h4>
    </div>
    <div class="modal-body">
        <table class="table table-striped table-bordered table-hover" id="user_role_table">
            <thead>
            <tr>
                <th class="table-checkbox"><input type="checkbox" class="group-checkable" data-set="#user_role_table .checkboxes" /></th>
                <th>角色名</th>
                <th >描述</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${sysRoles}" var="role">
                    <tr class="odd gradeX">
                        <td><input type="checkbox" class="checkboxes" value="${role.id}" /></td>
                        <td>${role.roleName}</td>
                        <td >${role.description}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
        <button type="button" class="btn btn-primary" onclick="sysUser.saveUserRoles()">确认</button>
    </div>
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
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">取消</button>
    <button type="button" class="btn blue" onclick="sysUser.insertUser()">保存</button>
</div>
