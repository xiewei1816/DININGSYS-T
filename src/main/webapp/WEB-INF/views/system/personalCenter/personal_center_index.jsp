<%--
  Created by mrren.
  Date: 2016-08-17
  Time: 16:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/system/personalCenter/personalCenter.js"></script>
<script src="app/js/DININGSYS/system/personalCenter/personalPwd_validation.js"></script>
<script src="app/lib/security/sha256.js" type="text/javascript"></script>
<script>
    $(function () {
        modifyPwdFormValidation.modifyPwdFormInit();
        personalCenter.init();
    })
</script>
<div class="row profile">
    <div class="col-md-12">
        <!--BEGIN TABS-->
        <div class="tabbable tabbable-custom tabbable-full-width">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#tab_1_1" data-toggle="tab">基本信息</a></li>
                <li><a href="#tab_1_3" data-toggle="tab">账号</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab_1_1">
                    <div class="row">
                        <div class="col-md-3">
                        </div>
                        <div class="col-md-9">
                            <form class="form-horizontal" role="form">
                                <div class="form-body">
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">用户名</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.username}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">姓名</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.nickname}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">状态</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.state}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">创建时间</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.createTime}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">联系方式</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.phone}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">性别</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.gender}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">email</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.email}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">所属部门</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.deptName}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">职位</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.job}</p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label  class="col-md-3 control-label">是否为领导</label>
                                        <div class="col-md-9">
                                            <p class="form-control-static">${userInfo.isLeader == '1' ? '领导' :'工作人员'}</p>
                                        </div>
                                    </div>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <!--tab_1_2-->
                <div class="tab-pane" id="tab_1_3">
                    <div class="row profile-account">
                        <div class="col-md-3">
                            <ul class="ver-inline-menu tabbable margin-bottom-10">
                                <li class="active">
                                    <a data-toggle="tab" href="#tab_1-1">
                                        <i class="fa fa-cog"></i>
                                        个人信息
                                    </a>
                                    <span class="after"></span>
                                </li>
                                <li ><a data-toggle="tab" href="#tab_3-3"><i class="fa fa-lock"></i> 密码修改</a></li>
                            </ul>
                        </div>
                        <div class="col-md-9">
                            <div class="tab-content">
                                <div id="tab_1-1" class="tab-pane active">
                                    <form role="form" id="userBaseInfo">
                                        <div class="form-group">
                                            <label class="control-label">姓名</label>
                                            <input type="text" name="nickname" placeholder="${userInfo.nickname}" class="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">性别</label>
                                            <select class="form-control" name="gender">
                                                <option <c:if test="${userInfo.gender == '男'}">selected</c:if>>男</option>
                                                <option <c:if test="${userInfo.gender == '女'}">selected</c:if>>女</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">联系方式</label>
                                            <input name="phone" type="text" placeholder="${userInfo.phone}" class="form-control" />
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">email</label>
                                            <input name="email" type="text" placeholder="${userInfo.email}" class="form-control" />
                                        </div>
                                        <div class="margiv-top-10">
                                            <a href="javascript:void(0)" class="btn green" onclick="personalCenter.updateUserBaseInfo()">保存</a>
                                        </div>
                                    </form>
                                </div>
                                <div id="tab_3-3" class="tab-pane">
                                    <form id="modifyPwd">
                                        <div class="form-group">
                                            <span class="label label-danger">NOTE!</span>
                                            <span>密码修改成功后，下次登录系统请使用新密码！</span>
                                        </div>
                                        <div class="form-group oldPwdDiv">
                                            <label class="control-label">当前密码<span class="required">*</span></label>
                                            <input type="password" name="oldPwd" class="form-control currentPwd"/>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">新密码<span class="required">*</span></label>
                                            <input type="password" name="newPwd" class="form-control newPwd">
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label">新密码确认<span class="required">*</span></label>
                                            <input type="password" name="reNewPwd" class="form-control reNewPwd" />
                                        </div>
                                        <div class="margin-top-10">
                                            <a href="javascript:void(0)" class="btn green modifyPwd">修改</a>
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
</div>