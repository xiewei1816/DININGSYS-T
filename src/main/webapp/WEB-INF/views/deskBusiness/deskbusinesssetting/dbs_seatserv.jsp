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
            <form class="form-horizontal" id="seatServForm">
                <div class="row">
                    <div class="col-md-12">
                        <div class="">
                            <label>
                                加单时默认点单员:
                            </label>
                            <label>
                                <input type="radio" value="server" name="defaultWaiter" <c:if test="${dbsSeetServDTO.defaultWaiter == 'server'}">checked</c:if>> 当前服务员
                            </label>
                            <label>
                                <input type="radio" value="waiter" name="defaultWaiter" <c:if test="${dbsSeetServDTO.defaultWaiter == 'waiter'}">checked</c:if>> 当前点单员
                            </label>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isCanModifyServerMan" type="checkbox"  <c:if test="${dbsSeetServDTO.isCanModifyServerMan == 1}">checked</c:if> > 加单，退单时允许修改点单员
                            </label>
                        </div>
                    </div>
                    <%--<div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isManualInputOrderNum"  type="checkbox"  <c:if test="${dbsSeetServDTO.isManualInputOrderNum == 1}">checked</c:if>> 加单，退单时必须输入手工单号
                            </label>
                        </div>
                    </div>--%>
                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isUnsubscribeReasonNeed"  type="checkbox"  <c:if test="${dbsSeetServDTO.isUnsubscribeReasonNeed == 1}">checked</c:if>> 退单时必须输入退单原因
                            </label>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isUseGiftOrderReason"  type="checkbox"  <c:if test="${dbsSeetServDTO.isUseGiftOrderReason == 1}">checked</c:if>> 使用赠单原因
                            </label>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isUseGiftOrderReasonNeed"  type="checkbox"  <c:if test="${dbsSeetServDTO.isUseGiftOrderReasonNeed == 1}">checked</c:if>> 必须填写赠单原因
                            </label>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isRetainRoomReserve"  type="checkbox"  <c:if test="${dbsSeetServDTO.isRetainRoomReserve == 1}">checked</c:if>> 内部留房允许开台，预定
                            </label>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isNewOrderNeedForegift"  type="checkbox"  <c:if test="${dbsSeetServDTO.isNewOrderNeedForegift == 1}">checked</c:if>> 开单使用押金
                            </label>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isReserveForegiftToDeposit"  type="checkbox"  <c:if test="${dbsSeetServDTO.isReserveForegiftToDeposit == 1}">checked</c:if>> 执行预定时将预订订金转押金
                            </label>
                        </div>
                    </div>

                    <%--<div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isAutoShowBusinessInfo"  type="checkbox"  <c:if test="${dbsSeetServDTO.isAutoShowBusinessInfo == 1}">checked</c:if>> 前台营业的自定义视图下，鼠标点击客位显示营业信息
                            </label>
                        </div>
                    </div>--%>

                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isAutoInsertDeskLabelToSettlementRemarks"  type="checkbox"  <c:if test="${dbsSeetServDTO.isAutoInsertDeskLabelToSettlementRemarks == 1}">checked</c:if>> 结算时是否将前台客位标签内容自动插入到结算备注中
                            </label>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                员工卡授权的卡类型：
                                <select name="staffCardAuthorizType" >
                                    <option <c:if test="${dbsSeetServDTO.staffCardAuthorizType=='ICCard'}">selected</c:if>>IC卡</option>
                                    <option <c:if test="${dbsSeetServDTO.staffCardAuthorizType=='magcard'}">selected</c:if>>磁卡</option>
                                </select>
                            </label>

                            <label>
                                <input name="isAllowManualInputAuthorizNumber"  type="checkbox"  <c:if test="${dbsSeetServDTO.isAllowManualInputAuthorizNumber == 1}">checked</c:if>> 允许手工输入授权卡号
                            </label>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isGetPosNumberByIp"  type="checkbox"  <c:if test="${dbsSeetServDTO.isGetPosNumberByIp == 1}">checked</c:if>> 通过ip地址方式读取pos号
                            </label>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isOnlyAllowModifyIntradayData"  type="checkbox"  <c:if test="${dbsSeetServDTO.isOnlyAllowModifyIntradayData == 1}">checked</c:if>> 吧台盘点，只允许修改当日盘点数量
                            </label>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isChangeSeatOrServerCorrespond"  type="checkbox"  <c:if test="${dbsSeetServDTO.isChangeSeatOrServerCorrespond == 1}">checked</c:if>> 更换客位，更改服务员用【服务员服务客位】中对应目的客位的服务员
                            </label>
                        </div>
                    </div>
                    <%--<div class="col-md-12">
                        <div class="checkbox">
                            <label>
                                <input name="isNormalMealMustOptionsNumber"  type="checkbox"  <c:if test="${dbsSeetServDTO.isNormalMealMustOptionsNumber == 1}">checked</c:if>> 允许修改常规套餐必选项的数量
                            </label>
                        </div>
                    </div>--%>

                </div>
            </form>
        </div>
    </div>
</div>
<c:if test="${single == '1' }">
	<div class="modal-footer">
	    <button type="button" class="btn default" data-dismiss="modal">取消</button>
	    <button type="button" class="btn blue saveInfo" id="seatservBtn">保存</button>
	</div>
</c:if>