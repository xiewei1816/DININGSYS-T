<%--
  Created by zhshuo.
  Date: 2016-10-24
  Time: 11:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(function ($) {
        itemFile.initTcPage();

        if($("input:radio[name='tcType']").is(":checked")){
            var typeVal = $("input:radio[name='tcType']:checked").val();
            if(typeVal == "1"){
                $(".thpxBtn").attr("disabled","disabled");
                $(".kxpxBtn").attr("disabled","disabled");
                $("#hideLi").hide();
            }else{
                $(".thpxBtn").removeAttr("disabled");
                $(".kxpxBtn").removeAttr("disabled");
                $("#hideLi").show();
            }
        }
    })
</script>
<input type="hidden" value="${dgItemFile.id}" id="selectedItemId">
<input type="hidden" id="tcId" value="${packageBaseInfo.id}">
<ul id="myTab" class="nav nav-tabs">
    <li class="active">
        <a href="#tcBaseInfo" data-toggle="tab">
            基本信息
        </a>
    </li>
    <li id="hideLi"><a href="#otherKxpx" data-toggle="tab">可选品项其他设定</a></li>
</ul>
<div id="myTabContent" class="tab-content">
    <div id="tcBaseInfo" class="tab-pane fade in active">
        <div style="width: 600px;float: left;margin: 20px">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label for="tcName" class="col-sm-2 control-label">套餐名称</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="tcName" value="${dgItemFile.name}" placeholder="套餐名称">
                    </div>
                </div>
                <div class="form-group">
                    <label for="tcPrice" class="col-sm-2 control-label">套餐价格</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="tcPrice" value="${dgItemFile.standardPrice}" placeholder="套餐价格">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10" id="tcTypeDiv">
                        <div class="radio">
                            <div class="row" style="margin-left: 5px">
                                <label>
                                    <input type="radio" name="tcType" value="1" <c:if test="${empty packageBaseInfo or packageBaseInfo.packageType == 1}">checked="checked"</c:if>> 宴会
                                </label>
                            </div>
                            <label style="color: blue">说明：前台销售时套餐价格和套餐明细品项的内容可以任意修改</label>
                        </div>
                        <div class="radio">
                            <div class="row" style="margin-left: 5px">
                                <label>
                                    <input type="radio" name="tcType" value="2" <c:if test="${packageBaseInfo.packageType == 2}">checked="checked"</c:if>> 常规
                                </label>
                            </div>
                            <label  style="color: blue">说明：前台销售时不能修改套餐品项及价格，可以使用可选品项和替换品项进行设定</label>
                        </div>
                        <div class="checkbox">
                            <div class="row" style="margin-left: 5px">
                                <label>
                                    <input type="checkbox" id="tcWinNum" value="1" <c:if test="${packageBaseInfo.packageBanquet == 1}">checked="checked"</c:if>> 宴会套餐数量跟套餐明细数量有关
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <h5>必选品项</h5>
                <div  class="form-group">
                    <%--<span style="margin-left: 20px">
                        <button id="bxpxTop" class="bxpxBtn btn btn-default" type="button">上移</button>
                        <button id="bxpxDown" class="bxpxBtn btn btn-default" type="button">下移</button>
                    </span>--%>
                    <span style="margin-left: 20px">
                    	<c:if test="${single == '1'}">
                    	    <button id="bxpxAdd" class="bxpxBtn btn btn-default" type="button">新增</button>
	                        <button id="bxpxRemove" class="bxpxBtn btn btn-default" type="button">删除</button>
	                        <button id="bxpxCancel" class="bxpxBtn btn btn-default" type="button">清空</button>
                    	</c:if>
                    </span>
                </div>
                <div style="width: 600px;overflow: auto;height: 250px">
                    <table id="tcBxPx">
                    </table>
                </div>
            </form>
        </div>
        <div style="width: 550px;float: left">
            <h5>可选品项</h5>
            <div  class="form-group">
                <%--<span style="margin-left: 20px">
                    <button id="kxpxTop" class="kxpxBtn btn btn-default" type="button">上移</button>
                    <button id="kxpxDown" class="kxpxBtn btn btn-default" type="button">下移</button>
                </span>--%>
                <span style="margin-left: 20px">
                        <c:if test="${single == '1'}">
	                        <button id="kxpxAdd" class="kxpxBtn btn btn-default" type="button">新增</button>
	                        <button id="kxpxRemove" class="kxpxBtn btn btn-default" type="button">删除</button>
	                        <button id="kxpxCancel" class="kxpxBtn btn btn-default" type="button">清空</button>
                        </c:if>
                </span>
            </div>
            <div style="height: 200px;;width: 550px;overflow: auto;">
                <table id="kxpxTable">
                </table>
            </div>
            <h5>必选品项的替换品项</h5>
            <div  class="form-group">
                <%--<span style="margin-left: 20px">
                    <button id="thpxTop" class="thpxBtn btn btn-default" type="button">上移</button>
                    <button id="thpxDown" class="thpxBtn btn btn-default" type="button">下移</button>
                </span>--%>
                <span style="margin-left: 20px">
                   	<c:if test="${single == '1'}">
	                    <button id="thpxAdd" class="thpxBtn btn btn-default" type="button">新增</button>
	                    <button id="thpxRemove" class="thpxBtn btn btn-default" type="button">删除</button>
	                    <button id="thpxCancel" class="thpxBtn btn btn-default" type="button">清空</button>
                    </c:if>
                </span>
            </div>
            <div style="height: 200px;width: 550px;overflow: auto;">
                <table id="thpxTable">
                </table>
            </div>
        </div>
    </div>
    <div id="otherKxpx" class="tab-pane fade">
        <div style="width: 40%;float: left;margin-left: 50px">
            <h5>可选品项大类数量限定</h5>
            <div style="height: 500px">
                <table id="kxpxdlslxd">
                </table>
            </div>
        </div>
        <div style="width: 40%;float:left;margin-left:50px">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-10">
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" id="canUpdate" value="1" <c:if test="${packageBaseInfo.canupdateItemNum == 1}">checked="checked"</c:if>> 允许修改可选品项数量
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="minNum" class="col-sm-5 control-label">可选品项最小总数量：</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="minNum" value="${packageBaseInfo.minNum}">
                        <span  class="help-block">注：当为零，不限制最小数量。</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="maxNum" class="col-sm-5 control-label">可选品项最大总数量：</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="maxNum" value="${packageBaseInfo.maxNum}">
                        <span class="help-block">注：当为零，不限制最大数量。</span>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
