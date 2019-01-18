<%--
  Created by mrren.
  Date: 2016-07-25
  Time: 15:34
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">用户详情</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form">
                <div class="form-body">
                    <div class="form-group">
                        <label  class="col-md-3 control-label">用户名</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.username}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">姓名</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.nickname}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">状态</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.state}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">创建时间</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.createTime}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">联系方式</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.phone}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">性别</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.gender}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">email</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.email}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">所属部门</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.deptName}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">职位</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.job}</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">是否为领导</label>
                        <div class="col-md-9">
                            <p class="form-control-static">${sysUser.isLeader == '1' ? '领导' :'工作人员'}</p>
                        </div>
                    </div>

                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">关闭</button>
</div>