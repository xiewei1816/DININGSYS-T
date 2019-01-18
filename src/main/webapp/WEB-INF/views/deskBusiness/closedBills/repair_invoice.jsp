<%--
  Created by zhshuo.
  Date: 16-11-24
  Time: 上午9:18
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    fieldset.scheduler-border {
        border: 1px groove #ddd !important;
        padding: 0 1.4em 1.4em 1.4em !important;
        margin: 0 0 1.5em 0 !important;
        -webkit-box-shadow:  0px 0px 0px 0px #000;
        box-shadow:  0px 0px 0px 0px #000;
    }

    legend.scheduler-border {
        font-size: 1.2em !important;
        font-weight: bold !important;
        text-align: left !important;
        width:auto;
        padding:0 10px;
        border-bottom:none;
    }
</style>
<script>
    jQuery(document).ready(function () {
        closedBills.repairInvoiceInit();
    })
</script>
<div class="panel panel-default" id="currentBisPanel">
    <div class="panel-body" style="overflow:auto;" id="contentDiv">
        <input type="hidden" value="${cwInfo.id}" id="cwId">
        <fieldset class="scheduler-border">
            <legend class="scheduler-border">已开发票</legend>
            <div class="control-group">
                <table id="invoicedTable"></table>
            </div>
            <div class="col-md-12" style="margin-top:10px;margin-bottom: -5px">
                <div class="col-md-4">
                    应收金额：<span id="amountsReceivable">${cwInfo.amountsReceivable}</span>
                </div>
                <div class="col-md-4">
                    发票总额：<span id="invoiceAmount"></span>
                </div>
                <div class="col-md-4">
                    <button class="btn btn-default btn-sm" id="tableInvoiceDel" type="button" style="margin-left: 80px">删除</button>
                </div>
            </div>
        </fieldset>
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#invoiceCount" aria-controls="home" role="tab" data-toggle="tab">登记发票张数</a></li>
            <li role="presentation"><a href="#invoiceNum" aria-controls="profile" role="tab" data-toggle="tab">登记发票号码</a></li>
        </ul>
        <div class="tab-content" style="max-height: 300px;">
            <div role="tabpanel" class="tab-pane active" id="invoiceCount">
                <table id="invoiceCountTable" class="table">
                    <tr>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #9CBAE7;cursor: pointer;text-align: center">
                                <span>100</span>元
                            </div>
                        </td>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #9CBAE7;cursor: pointer;text-align: center">
                                <span>50</span>元
                            </div>
                        </td>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #9CBAE7;cursor: pointer;text-align: center">
                                <span>20</span>元
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #9CBAE7;cursor: pointer;text-align: center">
                                <span>10</span>元
                            </div>
                        </td>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #9CBAE7;cursor: pointer;text-align: center">
                                <span>5</span>元
                            </div>
                        </td>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #9CBAE7;cursor: pointer;text-align: center">
                                <span>1</span>元
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #FF9E6B;cursor: pointer;text-align: center">
                                <span>1000</span>元
                            </div>
                        </td>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #FF9E6B;cursor: pointer;text-align: center">
                                <span>500</span>元
                            </div>
                        </td>
                        <td>
                            <div style="width: 80%;height:100%;background-color: #FF9E6B;cursor: pointer;text-align: center">
                                <span>200</span>元
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div role="tabpanel" class="tab-pane" id="invoiceNum">
                <form class="form-horizontal" style="margin-top: 20px">
                    <div class="form-group">
                        <label for="addInoviceAmount" class="col-sm-3 control-label" style="color: red">发票金额：</label>
                        <div class="col-sm-6">
                            <input id="addInoviceAmount" type="text" class="form-control">
                        </div>
                        <div class="col-sm-3">
                            <button class="btn btn-default btn-sm" id="tabInvoiceAdd" type="button" style="margin-left: 55px">增加</button>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="addInoviceAmount" class="col-sm-3 control-label" style="color: red">发票号码：</label>
                        <div class="col-sm-9">
                            <input id="addInoviceNum" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="addInoviceAmount" class="col-sm-3 control-label">备注：</label>
                        <div class="col-sm-9">
                            <textarea id="addInoviceNotes" class="form-control"></textarea>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
