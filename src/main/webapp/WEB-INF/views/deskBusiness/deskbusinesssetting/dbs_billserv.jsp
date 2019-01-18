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
            <form class="form-horizontal" role="form" id="billservForm">

                    <div class="row">
                        <%--<div class=" col-md-6 checkbox">
                            <label>
                                <input name="isForegiftInSettlement" type="checkbox"  <c:if test="${dbsBillServDTO.isForegiftInSettlement == 1}">checked</c:if> > 押金参与结算
                            </label>
                        </div>--%>
                        <%--<div class=" col-md-6 checkbox">
                            <label>
                                <input name="isNoLimitDiscountScheme"  type="checkbox"  <c:if test="${dbsBillServDTO.isNoLimitDiscountScheme == 1}">checked</c:if>>【品项打折方案】不受【允许打折品项】限制
                            </label>
                        </div>--%>
                    </div>
                    <div class="row">
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isPrintVoucherToGetInvoice" type="checkbox"  <c:if test="${dbsBillServDTO.isPrintVoucherToGetInvoice == 1}">checked</c:if> > 开发票和补开发票打印领取发票凭证
                            </label>
                        </div>
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isNoForceInputInvoiceNumberAndMoney"  type="checkbox"  <c:if test="${dbsBillServDTO.isNoForceInputInvoiceNumberAndMoney == 1}">checked</c:if>> 开具发票时不强制输入发票号和金额
                            </label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isAllowNegativeNumberInQuotaDiscount" type="checkbox"  <c:if test="${dbsBillServDTO.isAllowNegativeNumberInQuotaDiscount == 1}">checked</c:if> > 结算时定额优惠允许输入负数
                            </label>
                        </div>
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isQuotaDiscountAndNoSmallChangeApportionToItems"  type="checkbox"  <c:if test="${dbsBillServDTO.isQuotaDiscountAndNoSmallChangeApportionToItems == 1}">checked</c:if>> 结算时定额优惠和抹零分摊到品项上
                            </label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isLimitMemCardConsumeArea" type="checkbox"  <c:if test="${dbsBillServDTO.isLimitMemCardConsumeArea == 1}">checked</c:if> > 启用会员卡消费区域限定功能
                            </label>
                        </div>
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isQuotaDiscountToItemsAsPossible"  type="checkbox"  <c:if test="${dbsBillServDTO.isQuotaDiscountToItemsAsPossible == 1}">checked</c:if>> 尽可能把定额优惠分摊到允许打折的品项上
                            </label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isUploadConsumeDetailWhenUnpaid" type="checkbox"  <c:if test="${dbsBillServDTO.isUploadConsumeDetailWhenUnpaid == 1}">checked</c:if> > 会员挂账时往会员系统提交消费明细
                            </label>
                        </div>
                        <!--  <div class="col-md-6 checkbox">
                            <label>
                                <input name="isNoServiceChargeNoSettlement"  type="checkbox"  <c:if test="${dbsBillServDTO.isNoServiceChargeNoSettlement == 1}">checked</c:if>> 零服务费时无法结算
                            </label>
                        </div>-->
                    </div>

                    <div class="row">
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isDiscountCardNoUsePromotionItems" type="checkbox"  <c:if test="${dbsBillServDTO.isDiscountCardNoUsePromotionItems == 1}">checked</c:if> > 会员打折卡不能对促销方案品项进行二次打折
                            </label>
                        </div>
                        <div class="col-md-6 checkbox">
                            <label>
                                <input name="isServiceChargeNoDiscount"  type="checkbox"  <c:if test="${dbsBillServDTO.isServiceChargeNoDiscount == 1}">checked</c:if>> 服务费不参与任何优惠
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 checkbox">
                            <label class="col-md-2 " style="text-align: left; padding-top:5px;">消费满</label>
                            <div class="col-md-5">
                                <input placeholder="请输入金额" class="form-control" name="noServiceChargeOverMoney" type="text" value="${dbsBillServDTO.noServiceChargeOverMoney}">
                            </div>
                            <span class="col-md-5" style="text-align: left; padding-top:5px;">免服务费</span>
                        </div>

                        <div class=" col-md-6 checkbox">
                            <label class="col-md-2" style="text-align: left; padding-top:5px;">消费满</label>
                            <div class="col-md-5">
                                <input placeholder="请输入金额" class="form-control" name="noRoomuseChargeOverMoney" type="text" value="${dbsBillServDTO.noRoomuseChargeOverMoney}">
                            </div>
                            <label class="col-md-5" style="text-align: left; padding-top:5px;">免包房费</label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>
                                <input name="isOnlyOffWork" type="checkbox"  <c:if test="${dbsBillServDTO.isOnlyOffWork == 1}">checked</c:if> > 前台结班不显示结班窗口，不打印结班报表，只进行结班操作
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-4 checkbox">
                            <label>
                                <input name="isSendSmsOffWork" type="checkbox"  <c:if test="${dbsBillServDTO.isSendSmsOffWork == 1}">checked</c:if> > 结班以后发送结班信息短信
                            </label>
                        </div>

                        <div class=" col-md-8 checkbox">
                            <label class="col-md-4" style="text-align: left; padding-top:5px;">接收短信号码</label>
                            <div class="col-md-8">
                                <input placeholder="请输入" class="form-control" name="resaveSmsNumber" type="text" value="${dbsBillServDTO.resaveSmsNumber}">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">摸零方式：</label>
                            <div class="col-md-9">
                                <select name="noSmallChangeWay" placeholder="请选择摸零方式" class="form-control">
                                    <option value="0" <c:if test="${dbsBillServDTO.noSmallChangeWay == 0}">selected</c:if>>不抹零</option>
                                    <option value="1" <c:if test="${dbsBillServDTO.noSmallChangeWay == 1}">selected</c:if>>收尾法</option>
                                    <option value="2" <c:if test="${dbsBillServDTO.noSmallChangeWay == 2}">selected</c:if>>去尾法</option>
                                    <option value="3" <c:if test="${dbsBillServDTO.noSmallChangeWay == 3}">selected</c:if>>四舍五入法</option>
                                </select>
                            </div>
                        </div>
                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">摸零位置：</label>
                            <div class="col-md-9">
                                <select name="noSmallChangePlace" placeholder="请选择摸零位置" class="form-control">
                                    <option value="0" <c:if test="${dbsBillServDTO.noSmallChangePlace == 0}">selected</c:if>>取到角</option>
                                    <option value="1" <c:if test="${dbsBillServDTO.noSmallChangePlace == 1}">selected</c:if>>取到元</option>
                                    <option value="2" <c:if test="${dbsBillServDTO.noSmallChangePlace == 2}">selected</c:if>>取到五元</option>
                                    <option value="3" <c:if test="${dbsBillServDTO.noSmallChangePlace == 3}">selected</c:if>>取到十元</option>
                                </select>
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
	    <button type="button" class="btn blue saveInfo" id="billservBtn">保存</button>
	</div>
</c:if>