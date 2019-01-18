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
        busi_per.chexBoxBtnInit();
        busi_per.discountRadioInit();
        busi_per.saveInfoBtnInit();
    })
</script>
<form class="form-horizontal" role="form" id="discountForm" style="width: 95%">
    <input type="hidden" name="zwCode" value="${zwCode}"> <%--职务CODE--%>
    <input type="hidden" id="generalOverViewId"  name="overViewId" value="${sysPerOverview.id}"> <%--业务权限overview表ID--%>
    <input type="hidden" name="id" value="${sysPerDiscount.id}"> <%--业务优惠权限ID--%>
    <div class="row">
        <div class="col-md-6">
            <span class="col-md-3">常规优惠最低比例</span>
            <div class="col-md-6">
                <div class="input-group">
                    <input  class="form-control" type="text" value="${sysPerDiscount.cgyhzdbl == null?100:sysPerDiscount.cgyhzdbl}" name="cgyhzdbl">
                    <span class="input-group-addon"><i>%</i></span>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <span class="col-md-4">全单比例优惠最低比例</span>
            <div class="col-md-6">
                <div class="input-group">
                    <input class="form-control" type="text" value="${sysPerDiscount.qdblyhzdbl == null?100:sysPerDiscount.qdblyhzdbl}" name="qdblyhzdbl" >
                    <span class="input-group-addon"><i>%</i></span>
                </div>
            </div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-12">
                <div class="checkbox">
                    <label>
                        <input id="deyhCheck" type="checkbox" value="1" name="deyh" <c:if test="${sysPerDiscount.deyh ==1}">checked</c:if> > 定额优惠
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-11">
            <div class="col-md-6">
                <div class="radio">
                    <c:choose>
                        <c:when test="${empty sysPerDiscount.deyh}">
                            <label>
                                <input class="deyhRadioClass" name="deyhType"value="1"  type="radio" checked disabled>每单定额优惠在
                            </label>
                            <input disabled id="mddeyhOne" class="form-control deyhInput" style="width: 200px;display: inline" type="text" value="500" name="mddeyhOne">
                            元以内
                        </c:when>
                        <c:when test="${sysPerDiscount.deyhType == 1}">
                            <label>
                                <input class="deyhRadioClass" name="deyhType"value="1"  type="radio" checked>每单定额优惠在
                            </label>
                            <input id="mddeyhOne" class="form-control deyhInput" style="width: 200px;display: inline" type="text" value="${sysPerDiscount.mddeyhOne}" name="mddeyhOne">
                            元以内
                        </c:when>
                        <c:when test="${sysPerDiscount.deyhType ==2}">
                            <label>
                                <input class="deyhRadioClass" name="deyhType" value="1"  type="radio">每单定额优惠在
                            </label>
                            <input id="mddeyhOne" readonly class="form-control deyhInput" style="width: 200px;display: inline" type="text" value="500" name="mdpccexzOne">
                            元以内
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-6">
                <div class="radio">
                    <c:choose>
                        <c:when test="${empty sysPerDiscount.deyh}">
                            <label>
                                <input class="deyhRadioClass" name="deyhType" value="2" type="radio" disabled>每单定额优惠限制在消费额的
                            </label>
                            <input disabled id="mddeyhTwo" class="form-control deyhInput" style="width: 200px;display: inline" type="text" value="5" name="mddeyhTwo">
                            %以内
                        </c:when>
                        <c:when test="${sysPerDiscount.deyhType == 1}">
                            <label>
                                <input class="deyhRadioClass" name="deyhType" value="2" type="radio">每单定额优惠限制在消费额的
                            </label>
                            <input readonly id="mddeyhTwo" class="form-control deyhInput" style="width: 200px;display: inline" type="text" value="5" name="mddeyhTwo">
                            %以内
                        </c:when>
                        <c:when test="${sysPerDiscount.deyhType ==2}">
                            <label>
                                <input class="deyhRadioClass" name="deyhType" value="2" type="radio" checked>每单定额优惠限制在消费额的
                            </label>
                            <input id="mddeyhTwo" class="form-control deyhInput" style="width: 200px;display: inline" type="text" value="${sysPerDiscount.mddeyhTwo}" name="mddeyhTwo">
                            %以内
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-12">
                <div class="checkbox">
                    <label>
                        <input id="pcceqxCheck" type="checkbox"name="pcceqx" value="1" <c:if test="${sysPerDiscount.pcceqx == 1}">checked</c:if> > 配餐超额权限（说明：加宴会套餐时，菜品合计与套餐价格差额限定的权限）
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-1"></div>
        <div class="col-md-11">
            <div class="col-md-6">
                <div class="radio">
                    <c:choose>
                        <c:when test="${empty sysPerDiscount.pcceqx}">
                            <label>
                                <input class="pcceRadioClass" name="pcceType"value="1"  type="radio" checked disabled>每单配餐超额限制
                            </label>
                            <input disabled id="mdpccexzOne" class="form-control pcceqxInput" style="width: 200px;display: inline" type="text" value="500" name="mdpccexzOne">
                            元以内
                        </c:when>
                        <c:when test="${sysPerDiscount.pcceType ==2}">
                            <label>
                                <input class="pcceRadioClass" name="pcceType"value="1"  type="radio">每单配餐超额限制
                            </label>
                            <input readonly id="mdpccexzOne" class="form-control pcceqxInput" style="width: 200px;display: inline" type="text" value="500" name="mdpccexzOne">
                            元以内
                        </c:when>
                        <c:when test="${sysPerDiscount.pcceType ==1}">
                            <label>
                                <input class="pcceRadioClass" name="pcceType"value="1"  type="radio" checked>每单配餐超额限制
                            </label>
                            <input id="mdpccexzOne" class="form-control pcceqxInput" style="width: 200px;display: inline" type="text" value="${sysPerDiscount.mdpccexzOne}" name="mdpccexzOne">
                            元以内
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="col-md-6">
                <div class="radio">
                    <c:choose>
                        <c:when test="${empty sysPerDiscount.pcceqx}">
                            <label>
                                <input class="pcceRadioClass" name="pcceType" value="2" type="radio" disabled>每单配餐超额限制在菜品合计的
                            </label>
                            <input disabled id="mdpccexzTwo" class="form-control pcceqxInput" style="width: 200px;display: inline" type="text" value="50" name="mdpccexzTwo">
                            %以内
                        </c:when>
                        <c:when test="${sysPerDiscount.pcceType == 1}">
                            <label>
                                <input class="pcceRadioClass" name="pcceType" value="2" type="radio">每单配餐超额限制在菜品合计的
                            </label>
                            <input readonly id="mdpccexzTwo" class="form-control pcceqxInput" style="width: 200px;display: inline" type="text" value="50" name="mdpccexzTwo">
                            %以内
                        </c:when>
                        <c:when test="${sysPerDiscount.pcceType == 2}">
                            <label>
                                <input class="pcceRadioClass" name="pcceType" value="2" type="radio" checked>每单配餐超额限制在菜品合计的
                            </label>
                            <input id="mdpccexzTwo" class="form-control pcceqxInput" style="width: 200px;display: inline" type="text" value="${sysPerDiscount.mdpccexzTwo}" name="mdpccexzTwo">
                            %以内
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="col-md-9">
                <div class="checkbox">
                    <label>
                        <input id="yqzgedCheck" type="checkbox" value="1" name="yqzged" <c:if test="${sysPerDiscount.yqzged == 1}">checked</c:if> >宴请最高额度
                    </label>
                    <input <c:if test="${empty sysPerDiscount.yqzged}">disabled</c:if>  class="form-control yqzgedInput" style="width: 200px;display: inline" type="text" value="${sysPerDiscount.yqzgednum == null?0:sysPerDiscount.yqzgednum}" name="yqzgednum">
                    元
                </div>
            </div>
        </div>
    </div>
    <hr>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="mfwf"  <c:if test="${sysPerDiscount.mfwf == 1}">checked</c:if> > 免服务费
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xglbyh" <c:if test="${sysPerDiscount.xglbyh == 1}">checked</c:if> > 修改类别优惠
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="mbff" <c:if test="${sysPerDiscount.mbff == 1}">checked</c:if> > 免包房费
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="gbff" <c:if test="${sysPerDiscount.gbff == 1}">checked</c:if> > 改包房费
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xgdpyh" <c:if test="${sysPerDiscount.xgdpyh == 1}">checked</c:if> > 修改单品优惠
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="mzdxf" <c:if test="${sysPerDiscount.mzdxf == 1}">checked</c:if> > 免最低消费
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="gfwf" <c:if test="${sysPerDiscount.gfwf == 1}">checked</c:if> > 改服务费
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="zq" <c:if test="${sysPerDiscount.zq == 1}">checked</c:if> > 赠券
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xgzqje" <c:if test="${sysPerDiscount.xgzqje == 1}">checked</c:if> > 修改赠券金额
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
	            <button type="button" class="btn btn-primary checkBoxAll" id="discountAll">全部选择</button>
	            <button type="button" class="btn btn-warning checkBoxCancel" id="discountCancel">全部取消</button>
	            <button type="button" class="btn btn-success saveInfo" id="discountSave">保存信息</button>
	        </div>
        </c:if>
    </div>
</form>