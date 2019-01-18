<%--
  Created by zhshuo.
  Date: 2016-11-07
  Time: 17:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    jQuery(document).ready(function () {
        busi_per.itemTypeChoosePage();
    })
</script>
<form class="form-horizontal" role="form" style="width: 95%">
    <input type="hidden" id="itemFileTypeYxIds" value="${itemFileTypeYxIds}">
    <div class="row">
        <div class="col-md-3">
            <div class="panel panel-default">
                <div class="panel-body" style="overflow:auto;height: 473px">
                    <ul id="itemFileTypeTree" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="panel panel-default">
                <div class="panel-body" style="height: 473px;overflow-y: scroll">
                    <div style="overflow:auto;">
                        <span class="col-md-6">可选内容：<span id="itemFileTypeKx">0</span></span>
                        <span class="col-md-6" style="text-align: right;cursor: pointer" id="itemFileTypeChooseAll">全部选择</span>
                        <table id="itemFileTypeTable"></table>
                    </div>
                    <br>
                    <div  style="overflow:auto;">
                        <span class="col-md-6">已选内容：<span id="itemFileTypeYx">0</span></span>
                        <span class="col-md-6" style="text-align: right;cursor: pointer" id="itemFileTypeCancelAll">全部取消</span>
                        <table id="itemFileTypeYxTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>