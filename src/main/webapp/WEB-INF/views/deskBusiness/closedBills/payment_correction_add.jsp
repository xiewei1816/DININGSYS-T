<%--
  Created by zhshuo.
  Date: 16-11-24
  Time: 下午4:29
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(document).ready(function () {
        closedBills.paymentAddInit();
    })
</script>
<div class="panel panel-default">
    <div class="panel-body" style="overflow:auto;" id="contentDiv">
        <input type="hidden" value="${seCode}" id="seCode">
        <input type="hidden" value="${seName}" id="seName">
        <div class="row" style="border: 1px solid grey;margin-top: -15px;background-color: #9CBAE7">
            <div class="col-md-12">
                结算流水：【<span id="cwNum">${cwInfo.cwNum}</span>】
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <p class="form-control-static">应收金额：<span>${cwInfo.amountsReceivable}</span></p>
            </div>
            <div class="col-md-6">
                <p class="form-control-static">已收：<span id="addPaidAmount">${ysje}</span></p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <p class="form-control-static">剩下金额：<span id="needMoney">${needMoney}</span></p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="row" style="border: 1px solid grey;background-color: #9CBAE7">
                    <div class="col-md-12">
                        付款方式：【${seName}】
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-3">实收金额：</label>
                        <div class="col-sm-6">
                            <input type="text" class="fomr-control" id="addshje">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-3">找零金额：</label>
                        <div class="col-sm-6">
                            <input type="text" class="fomr-control" readonly id="addzlje">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-3">备注：</label>
                        <div class="col-sm-6">
                            <textarea id="addPNotes" class="form-control"></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>