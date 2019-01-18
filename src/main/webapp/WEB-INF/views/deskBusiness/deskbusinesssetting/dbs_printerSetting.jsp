<%--
  Created by mrren.
  Date: 2016-07-27
  Time: 15:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
    jQuery(document).ready(function() {
        dbs_set.itemCheckboxInit();
        dbs_set.saveInfoBtnInit();
    });
</script>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="pringterForm">
                <div class="form-body">
                    <div class="row">
                        <div class="checkbox col-md-12">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">结算打印控制：</label>
                            <div class="col-md-9">
                                <select name="settlementPrintControll" placeholder="结算打印控制" class="form-control">
                                    <option value="0" <c:if test="${dbsPrinterSettingDTO.settlementPrintControll == 0}">selected</c:if>>结算后打印结账单，不提示是否打印</option>
                                    <option value="1" <c:if test="${dbsPrinterSettingDTO.settlementPrintControll == 1}">selected</c:if>>结算后提示是否打印结账单</option>
                                    <option value="2" <c:if test="${dbsPrinterSettingDTO.settlementPrintControll == 2}">selected</c:if>>结算后不打印结账单，也不提示是否打印</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">标题：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入标题" class="form-control" name="title" type="text" value="${dbsPrinterSettingDTO.title}">

                            </div>
                        </div>
                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">电话：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入电话" class="form-control" name="mobile" type="text" value="${dbsPrinterSettingDTO.mobile}">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">地址：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入地址" class="form-control" name="address" type="text" value="${dbsPrinterSettingDTO.address}">

                            </div>
                        </div>
                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">广告语：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入广告语" class="form-control" name="slogan" type="text" value="${dbsPrinterSettingDTO.slogan}">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4 checkbox">
                            <label>
                                <input name="isNoPrintParallelPortAddOrder" type="checkbox"  <c:if test="${dbsPrinterSettingDTO.isNoPrintParallelPortAddOrder == 1}">checked</c:if> > 不打印并口加单
                            </label>
                        </div>

                        <div class="col-md-4 checkbox">
                            <label>
                                <input name="isNoPrintParallelPortChargeback" type="checkbox"  <c:if test="${dbsPrinterSettingDTO.isNoPrintParallelPortChargeback == 1}">checked</c:if> > 不打印并口退单
                            </label>
                        </div>

                        <div class="col-md-4 checkbox">
                            <label>
                                <input name="isNoPrintParallelPortGiftOrder" type="checkbox"  <c:if test="${dbsPrinterSettingDTO.isNoPrintParallelPortGiftOrder == 1}">checked</c:if> > 不打印并口赠单
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>
                                <input name="isPrintBelongSetmealInOneMealOneOrder" type="checkbox"  <c:if test="${dbsPrinterSettingDTO.isPrintBelongSetmealInOneMealOneOrder == 1}">checked</c:if> > 打印套餐明细时，同时打印所属套餐
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>
                                <input name="isSendSmsOffWork" type="checkbox"  <c:if test="${dbsPrinterSettingDTO.isEnablePromptBeforePrintCustomeTicketAndCheckBill == 1}">checked</c:if> > 【打印客用单】及【买单结算】之前，启用提示功能
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="checkbox col-md-12">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">输入提示信息：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入提示信息" class="form-control" name="promptMessage" type="text" value="${dbsPrinterSettingDTO.promptMessage}">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>
                                <input name="isReprintServiceFlowInBillWindow" type="checkbox"  <c:if test="${dbsPrinterSettingDTO.isReprintServiceFlowInBillWindow == 1}">checked</c:if> > 核对单据窗口，允许打印已经打印过的服务流水
                            </label>
                        </div>
                    </div>

                </div>
            </form>
        </div>
    </div>
</div>
<c:if test="${single == '1' }">
	<div class="modal-footer">
	    <button type="button" class="btn default" data-dismiss="modal">取消</button>
	    <button type="button" class="btn blue saveInfo" id="pringterBtn">保存</button>
	</div>
</c:if>