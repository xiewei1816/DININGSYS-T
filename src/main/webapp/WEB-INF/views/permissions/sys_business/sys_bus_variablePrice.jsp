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
        busi_per.tableInit('vp');
        busi_per.saveInfoBtnInit();
        busi_per.itemRadioBtnInit('znbjzdpx');
        busi_per.itemChooseBtnInit('vp');
    })
</script>
<form class="form-horizontal" role="form" id="variablePriceForm" style="width: 95%">
    <input type="hidden" value="${zwCode}" name="zwCode">
    <input type="hidden" value="${sysPerOverview.id}" name="overViewId">
    <input type="hidden" value="${hasVariablePriceData}" name="hasVariablePriceData">

    <input type="hidden" name="variablePriceDataIds" id="variablePriceDataIds">
    <input type="hidden" name="variablePriceDataType" id="variablePriceDataType">
    <div class="row">
        <div class="col-md-6">
            <div class="radio">
                <label>
                    <input class="radioOne" type="radio" name="znbjzdpx" id="znzszdpxxl" <c:if test="${empty sysPerOverview.variablePriceType or sysPerOverview.variablePriceType == 1}">checked</c:if> value="1"> 只能变价指定品项小类
                </label>
            </div>
        </div>
       <c:if test="${single == '1'}">
	        <div class="col-md-6" style="text-align: right">
	            <button type="button" <c:if test="${sysPerOverview.variablePriceType == 2}">disabled</c:if> class="btn btn-default btn-sm btnOne btnItemTypeChoose">选择</button>
	            <button type="button" <c:if test="${sysPerOverview.variablePriceType == 2}">disabled</c:if> class="btn btn-default btn-sm btnOne btnItemTypeDel">删除</button>
	            <button type="button" <c:if test="${sysPerOverview.variablePriceType == 2}">disabled</c:if> class="btn btn-default btn-sm btnOne btnItemTypeClear">清空</button>
	        </div>
        </c:if>
    </div>
    <div class="row">
        <div class="col-md-11">
            <table id="vpItemFileTypeTable"></table>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-6">
            <div class="radio">
                <label>
                    <input class="radioTwo" type="radio" name="znbjzdpx" id="znzszdpx" <c:if test="${sysPerOverview.variablePriceType == 2}">checked</c:if>  value="2"> 只能变价指定品项
                </label>
            </div>
        </div>
        <c:if test="${single == '1'}">
	        <div class="col-md-6" style="text-align: right">
	            <button <c:if test="${empty sysPerOverview.variablePriceType or sysPerOverview.variablePriceType == 1}">disabled</c:if> type="button" class="btn btn-default btn-sm btnTwo btnItemChoose">选择</button>
	            <button <c:if test="${empty sysPerOverview.variablePriceType or sysPerOverview.variablePriceType == 1}">disabled</c:if> type="button" class="btn btn-default btn-sm btnTwo btnItemDel">删除</button>
	            <button <c:if test="${empty sysPerOverview.variablePriceType or sysPerOverview.variablePriceType == 1}">disabled</c:if> type="button" class="btn btn-default btn-sm btnTwo btnItemClear">清空</button>
	        </div>
        </c:if>
    </div>
    <div class="row">
        <div class="col-md-11">
            <table id="vpItemFileTable"></table>
        </div>
    </div>
    <hr>
    <c:if test="${single == '1'}">
	    <div class="row">
	        <div class="col-md-6">
	        </div>
	        <div class="col-md-6" style="text-align: right;padding-right: 70px">
	            <button type="button" class="btn btn-success saveInfo" id="variablePriceSaveData" >保存信息</button>
	        </div>
	    </div>
    </c:if>
</form>