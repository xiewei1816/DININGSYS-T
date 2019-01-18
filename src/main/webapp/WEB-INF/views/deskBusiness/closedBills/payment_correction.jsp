<%--
  Created by zhshuo.
  Date: 16-11-24
  Time: 下午2:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .clearingWayTable{
        border:1px solid grey;
    }
    .clearingWayTable tr td{
        border:1px solid grey;
    }
    .clearingWayTable tr:hover{
        background-color: #DFF0D8;
        cursor: pointer;
    }
    .clearingWayTable .clearingTableBackColor:hover{
        background-color: #A2CD5A;
        cursor: pointer;
    }
    .clearingTableBackColor{
        background-color: #A2CD5A;
    }
</style>
<script>
    $(document).ready(function () {
        closedBills.paymentCorrectInit();
    })    
</script>
<div class="panel panel-default" id="currentBisPanel">
    <div class="panel-body" style="overflow:auto;" id="contentDiv">
        <input type="hidden" value="${cwInfo.id}" id="cwId">
        <div class="row" style="border: 1px solid grey;margin-top: -15px;background-color: #9CBAE7">
            <div class="col-md-12">
                结算流水：【<span id="cwNum">${cwInfo.cwNum}</span>】
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <p class="form-control-static">消费金额：<span>${cwInfo.consumptionAmount}</span></p>
            </div>
            <div class="col-md-6">
                <p class="form-control-static">抹零金额：<span>${cwInfo.zeroAmount}</span></p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <p class="form-control-static">定额优惠：<span>${cwInfo.fixedDiscount}</span></p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="row" style="border: 1px solid grey;background-color: #9CBAE7">
                    <div class="col-md-12">
                        结算
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <span><button type="button" class="changeClearingWayDiv"> &nbsp;&nbsp;<&nbsp;&nbsp; </button><label>常用结算方式</label><button class="changeClearingWayDiv" type="button"> &nbsp;&nbsp;> &nbsp;&nbsp;</button></span>
                        <div id="commonWay" style="min-width: 150px; height: 150px;overflow: auto">
                            <table id="commonTable" class="clearingWayTable" style="border-bottom: 1px solid grey">
                                <c:forEach items="${commonWay}" var="way" varStatus="status">
                                    <tr>
                                        <td style="display: none;">${way.ccode}</td>
                                        <td style="width: 25px">${status.index+1}</td>
                                        <td style="width: 120px">${way.cname}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <div id="otherWay" style="display: none">
                            <table class="clearingWayTable">

                            </table>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <table class="table">
                            <tr>
                                <td>应收</td>
                                <td><span id="pcamountsReceivable">${cwInfo.amountsReceivable}</span></td>
                            </tr>
                            <tr>
                                <td>实收</td>
                                <td><span id="pcpaidAmount">${cwInfo.paidAmount}</span></td>
                            </tr>
                            <tr>
                                <td>找零</td>
                                <td style="color: green"><span id="pcchangeAmount">${cwInfo.changeAmount}</span></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table id="closedClearingWayTable"></table>
            </div>
        </div>
    </div>
</div>