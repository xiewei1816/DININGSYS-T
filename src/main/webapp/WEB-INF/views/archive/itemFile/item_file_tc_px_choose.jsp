<%--
  Created by zhshuo.
  Date: 2016-10-24
  Time: 14:25
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(function ($) {
        itemFile.tcpxChoosePageInit();
    })
</script>
<div style="margin: 5px">
    <input type="hidden" value="${selectedTcId}" id="selectedTcId"/>
    <input type="hidden" value="${tcYxPxIds}" id="tcYxPxIds"/>
    <div style="height: 600px;float: left;width: 200px;">
        <div class="content zTreeDemoBackground">
            <ul id="tcPxChooseTree" class="ztree"></ul>
        </div>
    </div>
    <div style="height: 300px;width: 460px;float: left;padding-left: 5px;overflow: auto;margin-bottom: 50px">
        <h4 style="display: inline">可选内容：<span id="tcPxChooseKxids"></span></h4><span id="tcPxChooseAll" style="margin-left:250px;cursor: pointer">全部选择</span>
        <table id="bxpxChooseTable">
        </table>
    </div>
    <div style="height: 280px;width: 460px;overflow: auto;float: left;padding-left: 5px">
        <h4 style="display: inline">已选内容：<span id="tcpxChooseYxids">${yxSize}</span></h4><span id="tcPxCancelAll" style="margin-left:250px;cursor: pointer">全部取消</span>
        <table id="bxpxYxChooseTable">
        </table>
    </div>
</div>