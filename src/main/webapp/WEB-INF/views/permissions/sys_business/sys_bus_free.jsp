<%--
  Created by zhshuo.
  Date: 2016-11-07
  Time: 9:49
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
    $(document).ready(function () {
        busi_per.tableInit('free');
        busi_per.saveInfoBtnInit();
        busi_per.itemRadioBtnInit('znzszdpx');
        busi_per.itemChooseBtnInit('free');
    })

    function freeTotalMoneyChange(obj) {
        if($(obj).is(":checked")){
            $("#freeTotalMoney").prop("readonly",false);
        }else{
            $("#freeTotalMoney").prop("readonly",true);
        }
    }
    function freeMaxPriceChange(obj) {
        if($(obj).is(":checked")){
            $("#freeMaxPrice").prop("readonly",false);
        }else{
            $("#freeMaxPrice").prop("readonly",true);
        }
    }
</script>
<form class="form-horizontal" role="form" id="freePerForm" style="width: 95%">
    <input type="hidden" value="${zwCode}" name="zwCode">
    <input type="hidden" value="${sysPerOverview.id}" name="overViewId">
    <input type="hidden" value="${hasFreeData}" name="hasFreeData">

    <input type="hidden" name="freeDataIds" id="freeDataIds">
    <input type="hidden" name="freeDataType" id="freeDataType">
    <div class="row">
        <div class="col-md-6">
            <div class="radio">
                <label>
                    <input class="radioOne" type="radio" name="znzszdpx" id="znzszdpxxl" <c:if test="${empty sysPerOverview.freeType or sysPerOverview.freeType == 1}">checked</c:if> value="1"> 只能赠送指定品项小类
                </label>
            </div>
        </div>
        <c:if test="${single == '1'}">
	        <div class="col-md-6" style="text-align: right">
	            <button type="button" <c:if test="${sysPerOverview.freeType == 2}">disabled</c:if> class="btn btn-default btn-sm btnOne btnItemTypeChoose">选择</button>
	            <button type="button" <c:if test="${sysPerOverview.freeType == 2}">disabled</c:if> class="btn btn-default btn-sm btnOne btnItemTypeDel">删除</button>
	            <button type="button" <c:if test="${sysPerOverview.freeType == 2}">disabled</c:if> class="btn btn-default btn-sm btnOne btnItemTypeClear">清空</button>
	        </div>
        </c:if>
    </div>
    <div class="row">
        <div class="col-md-11">
            <table id="freeItemFileTypeTable"></table>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="radio">
                <label>
                    <input class="radioTwo" type="radio" name="znzszdpx" id="znzszdpx" <c:if test="${sysPerOverview.freeType == 2}">checked</c:if>  value="2"> 只能赠送指定品项
                </label>
            </div>
        </div>
       <c:if test="${single == '1'}">
	        <div class="col-md-6" style="text-align: right">
	            <button type="button" <c:if test="${empty sysPerOverview.freeType or sysPerOverview.freeType == 1}">disabled</c:if> class="btn btn-default btn-sm btnTwo btnItemChoose">选择</button>
	            <button type="button" <c:if test="${empty sysPerOverview.freeType or sysPerOverview.freeType == 1}">disabled</c:if> class="btn btn-default btn-sm btnTwo btnItemDel">删除</button>
	            <button type="button" <c:if test="${empty sysPerOverview.freeType or sysPerOverview.freeType == 1}">disabled</c:if> class="btn btn-default btn-sm btnTwo btnItemClear">清空</button>
	        </div>
        </c:if>
    </div>
    <div class="row">
        <div class="col-md-11">
            <table id="freeItemFileTable"></table>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-6">
            <div class="checkbox">
                <label>
                    <input type="checkbox" onchange="freeMaxPriceChange(this)">只能赠送价格低于
                </label>
                <input readonly id="freeMaxPrice" class="form-control" style="width: 200px;display: inline" type="text" value="${empty sysPerOverview.freeMaxPrice ? 10 :sysPerOverview.freeMaxPrice}" name="freeMaxPrice">
                元的品项
            </div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-9">
            <div class="checkbox">
                <label>
                    <input type="checkbox" onchange="freeTotalMoneyChange(this)" >每日赠送金额累计不超过
                </label>
                <input readonly id="freeTotalMoney" class="form-control" style="width: 200px;display: inline" type="text" value="${empty sysPerOverview.freeTotalMoney ? 10 :sysPerOverview.freeTotalMoney}" name="freeTotalMoney">
                元的
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="freeTemporary" <c:if test="${sysPerOverview.freeTemporary == 1}">checked</c:if> value="1"> 赠送临时品项
                </label>
            </div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-6">
        </div>
        
        <c:if test="${single == '1'}">
	        <div class="col-md-6" style="text-align: right;padding-right: 70px">
	            <button type="button" class="btn btn-success saveInfo" id="freeSaveData">保存信息</button>
	        </div>
        </c:if>
    </div>
</form>