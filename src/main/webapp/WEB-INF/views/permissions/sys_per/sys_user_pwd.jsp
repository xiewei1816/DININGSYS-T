<%--
  Created by zhshuo.
  Date: 2016-11-03
  Time: 21:21
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="app/lib/security/sha256.js" type="text/javascript"></script>
<form class="form-horizontal" id="roleUserForm" style="width: 94%">
    <input type="hidden" id="id" name="id" value="${id}"/>
    <div class="row">
        <div class="col-md-12">
            <div class="form-group">
                <label class="control-label col-md-3">新密码:</label>
                <div class="col-md-9">
                    <input id="newPwd" placeholder="请输入新密码" class="form-control" name="newPwd" type="password" />
                </div>
            </div>
        </div>
    </div>
</form>