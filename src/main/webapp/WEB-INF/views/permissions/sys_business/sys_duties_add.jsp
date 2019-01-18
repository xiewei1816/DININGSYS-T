<%--
  Created by zhshuo.
  Date: 16-11-30
  Time: 下午4:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-12">
    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label for="dutiesCode" class="col-sm-2 control-label">编码</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="dutiesCode" value="${nextCode}" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="dutiesName" class="col-sm-2 control-label">名称</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="dutiesName" placeholder="名称">
            </div>
        </div>
    </form>
</div>