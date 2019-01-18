<%--
  Created by zhshuo.
  Date: 2017-03-20
  Time: 16:37
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
	table.ryybb_table_title{
		width: 340px;
		margin:0 auto;
		border-top: 1px solid;
	}
	table.ryybb_table{
		margin:0 auto;
		width: 340px;
		border-right: 1px dashed;
	}
	table.ryybb_table th {
		width:25%;
	}
	table.ryybb_table td {
		width:25%;
		text-align: right;
		border-right: 1px dashed;
		padding-right: 10px;
	}
	
	table.ryybb_itemfile_table{
		margin:0 auto;
		width: 540px;
		border-right: 1px dashed;
	}
	table.ryybb_itemfile_table th {
		width:auto;
		text-align: right;
	}
	table.ryybb_itemfile_table td {
		width:auto;
		text-align: right;
		border-right: 1px dashed;
		padding-right: 10px;
	}
	
	.th_sml{
		padding-left:20px;
	}
	.th_sml1{
		padding-left:40px;
	}
</style>
<div class="ryybb_div">
	<h1 style="font-size:20px;font-weight:bold;text-align:center;"><span id="orgName"></span></h1>
	<table class="ryybb_table_title">
		<tr><th>开始时间:</th><td id="headStartTime"></td></tr>
		<tr><th>结束时间:</th><td id="headEndTime"></td></tr>
	</table>
</div>

<div class="ryybb_div">
	<table id="printYYZE" class="ryybb_table">
		<tr><th style="padding: 8px 0;font-size: 18px;" colspan="4">营业总额</th></tr>
		<tr><th class="th_sml">营业收入</th><td><fmt:formatNumber value="${attrs['yeze']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">折前总金额</th><td><fmt:formatNumber value="${attrs['itemCostsSum']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">折后总金额</th><td><fmt:formatNumber value="${attrs['yeze']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">优惠总金额</th><td><fmt:formatNumber value="${attrs['yhze']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">计入实际收入合计</th><td><fmt:formatNumber value="${attrs['jr']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">不计入实际收入合计</th><td><fmt:formatNumber value="${attrs['bjr']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">找零</th><td><fmt:formatNumber value="${attrs['zl']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">抹零</th><td><fmt:formatNumber value="${attrs['mlje']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">定额优惠</th><td><fmt:formatNumber value="${attrs['deyh']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">赠单金额</th><td><fmt:formatNumber value="${attrs['zdyhje']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">退单金额</th><td><fmt:formatNumber value="${attrs['backSubtotal']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">比例全单优惠</th><td><fmt:formatNumber value="${attrs['yhje']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">会员充值金额</th><td><fmt:formatNumber value="${attrs['rechargeData']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">品项金额合计</th><td><fmt:formatNumber value="${attrs['itemCostsSum']}" pattern="0.00"/></td></tr>
		<tr><th class="th_sml">品项促销优惠金额</th><td><fmt:formatNumber value="${attrs['cxyhje']}" pattern="0.00"/></td></tr>
		<%-- <tr><th class="th_sml">发票金额:</th><td>${attrs['fpje']}</td></tr> --%>
	</table>

	<table id="printJSFSTJ" class="ryybb_table">
		<tr><th style="padding: 8px 0;font-size: 18px;" colspan="4">结算方式统计</th></tr>
		<tr><th class="th_sml" style="font-size: 16px;">计入实际收入合计</th><td><fmt:formatNumber value="${attrs['jr']}" pattern="0.00"/></td></tr>
		<c:forEach items="${attrs['clearingWay']}" var="list" varStatus="status">
			<c:if test="${(list.actualIncomeRatio == 0||list.actualIncomeRatio == 100) && list.notActualIncomeRatio != 100}">
                <tr>
                    <th class="th_sml1">${list.seName}</th>
                    <td><fmt:formatNumber value="${list.settlementAmount}" pattern="0.00"/></td>
                </tr>
            </c:if>
            <c:if test="${list.actualIncomeRatio != 0 && list.actualIncomeRatio < 100}">
                <tr>
                    <th class="th_sml1">${list.seName}</th>
                    <td><fmt:formatNumber value="${(list.settlementAmount)*(list.actualIncomeRatio/100)}" pattern="0.00"/></td>
                </tr>
            </c:if>
		</c:forEach>
		<tr><th class="th_sml" style="font-size: 16px;">不计入实际收入合计</th><td><fmt:formatNumber value="${attrs['bjr']}" pattern="0.00"/></td></tr>
		<c:forEach items="${attrs['clearingWay']}" var="list" varStatus="status">
			<c:if test="${list.notActualIncomeRatio == 100}">
                <tr>
                    <th class="th_sml1">${list.seName}</th>
                    <td><fmt:formatNumber value="${(list.settlementAmount)}" pattern="0.00"/></td>
                </tr>
            </c:if>
            <c:if test="${list.notActualIncomeRatio != 0 && list.notActualIncomeRatio < 100}">
                <tr>
                    <th class="th_sml1">${list.seName}</th>
                    <td><fmt:formatNumber value="${(list.settlementAmount)*(list.notActualIncomeRatio/100)}" pattern="0.00"/></td>
                </tr>
            </c:if>
		</c:forEach>
	</table>

	<table id="recharge_table" class="ryybb_table">
        <tr><th style="padding: 8px 0;font-size: 18px;" colspan="4">会员充值统计</th></tr>
        <c:forEach items="${attrs['rechargeDatas']}" var="list" varStatus="status">
                <tr><th class="th_sml1">${list.payType}</th><td><fmt:formatNumber value="${list.payMoney}" pattern="0.00"/></td></tr>
        </c:forEach>
    </table>
    
    
    <table id="smallsell_table" class="ryybb_table">
        <tr><th style="padding: 8px 0;font-size: 18px;" colspan="4">品项小类销售统计</th></tr>
        <c:forEach items="${attrs['smallSell']}" var="list" varStatus="status">
                <tr><th class="th_sml1">${list.pxxl}</th><td><fmt:formatNumber value="${list.subtotal}" pattern="0.00"/></td></tr>
        </c:forEach>
    </table>
    
    
	<table id="printZDHKWXX" class="ryybb_table">
		<tr><th style="padding: 8px 0;font-size: 18px;" colspan="4">账单和客位信息</th></tr>
		<tr><th class="th_sml">正常结算流水总数:</th><td>${attrs['normalOpenWaterCount']}</td></tr>
		<tr><th class="th_sml">返回结算流水总数:</th><td>${attrs['fwjsCount']}</td></tr>
		<tr><th class="th_sml">正常营业流水总数:</th><td>${attrs['noramlOpenWater']}</td></tr>
		<tr><th class="th_sml">今日就餐总人数:</th><td>${attrs['jcrs']}</td></tr>
		<tr><th class="th_sml">今日结算人数:</th><td>${attrs['jsrs']}</td></tr>
		<tr><th class="th_sml">S账单数:</th><td>${attrs['sbill']}</td></tr>
	</table>

	<%-- <table class="ryybb_table" style="width: 24%">
        <tr><th style="background: #ffd39f;" colspan="4">应收应付类</th></tr>
        <tr><th>会员结账:</th><td>${attrs['HYZF']}</td></tr>
        <tr><th>会员挂账:</th><td>${attrs['HYGZ']}</td></tr>
          <tr><th>登记押金:</th><td>${attrs['djyj']}</td></tr>
        <tr><th>已退押金:</th><td>${attrs['ytyj']}</td></tr>
          <tr><th>未结押金:</th><td>${attrs['djyj'] - attrs['ytyj']}</td></tr>
        <tr><th>挂S账金额:</th><td>${attrs['sbillSubtotal']}</td></tr>
    </table> --%>
</div>
<div id="printTSPXTJ" class="ryybb_div">
	<table class="ryybb_table">
		<tr><th style="padding: 8px 0;font-size: 18px;" colspan="4">特殊品项统计</th></tr>
		<tr><th class="th_sml">免除的最低消费:</th><td>${attrs['freeMinSpeed']}</td></tr>
		<tr><th class="th_sml">免除的包房费:</th><td>${attrs['freePrivateRoom']}</td></tr>
		<tr><th class="th_sml">包房费总金额:</th><td>${attrs['allPrivateRoom']}</td></tr>
		<tr><th class="th_sml">免除的服务费:</th><td>${attrs['freeServiceCharge']}</td></tr>
	</table>
</div>

<div id="printPXXSTJ" class="ryybb_div">
   <table class="ryybb_itemfile_table" id="ryybb_itemfile_table">
       <tr>
           <th style="padding: 8px 0;font-size: 18px;" colspan="4">品项销售统计</th>
       </tr>
       <tr id="headTr">
           <th>品项名称</th>
           <th>正常数量</th>
           <th>赠送数量</th>
           <th>宴请数量</th>
           <th>正常金额</th>
           <th>赠送金额</th>
           <th>宴请金额</th>
       </tr>
   </table>
</div>

	<%-- <table class="ryybb_table" style="width: 24%">
     <tr><th style="background: #8dd7d8;" colspan="4">会员卡统计</th></tr>
     <tr><th>开户数量:</th><td>${attrs['khsl']}</td></tr>
     <tr><th>充值金额:</th><td>${attrs['czje']}</td></tr>
       <tr><th>卡消费金额:</th><td>${attrs['kxfje']}</td></tr>
     <tr><th>退款金额:</th><td>${attrs['ktkje']}</td></tr>
 </table> --%>

	<%-- <table class="ryybb_table" style="width: 24%">
        <tr><th style="background: #8ebdd9;" colspan="4">消费区域包房费</th></tr>
        <c:forEach var="areaS" items="${attrs['areaSpeed']}">
            <tr><th>${areaS.name}:</th><td>${areaS.roomCosts}</td></tr>
        </c:forEach>
    </table> --%>

	<%-- <table class="ryybb_table" style="width: 24%">
        <tr><th style="background: #ffd39f;" colspan="4">就餐人数统计</th></tr>
        <c:forEach var="item" items="${attrs['numberOfMeals']}">
            <tr><th>${item.numberOfMealsBisName}:</th><td>${item.numberOfMealsPeopleCount}(人)</td></tr>
        </c:forEach>
    </table> --%>
    
<%-- <div class="ryybb_div">
    <table class="ryybb_table" style="width: 48%">
		<tr><th style="background: #8c8977;" colspan="4">品项销售统计</th></tr>
		<tr>
			<th>品项大类</th>
			<th>品项小类</th>
			<th>数量</th>
			<th>金额</th>
		</tr>
		<c:forEach varStatus="index" var="itemSale" items="${attrs['itemSaleList']}">
			<c:forEach var="pxSale" items="${itemSale['pxSaleList']}" varStatus="pxSaleIndex">
			<tr>
				<c:if test="${pxSaleIndex.index eq 0}">
					<th>${itemSale['pxdlName']}</th>
				</c:if>
				<c:if test="${pxSaleIndex.index gt 0}">
					<th></th>
				</c:if>
				<th>${pxSale.pxxlName}:</th>
				<td>${pxSale.itemFileNumber}</td>
				<td>${pxSale.itemFileSale}</td>
			</c:forEach>
			</tr>
		</c:forEach>
    </table>

    <table class="ryybb_table" style="width: 48%">
		<tr><th style="background: #ffd39f;" colspan="4">品项类别现金销售统计</th></tr>
		<tr>
			<th>品项大类</th>
			<th>品项小类</th>
			<th>数量</th>
			<th>金额</th>
		</tr>
		<c:forEach varStatus="index" var="itemSaleForCash" items="${attrs['itemSaleForCashList']}">
			<c:forEach var="pxSale" items="${itemSaleForCash['pxSaleList']}" varStatus="pxSaleIndex">
			<tr>
				<c:if test="${pxSaleIndex.index eq 0}">
					<th>${itemSaleForCash['pxdlName']}</th>
				</c:if>
				<c:if test="${pxSaleIndex.index gt 0}">
					<th></th>
				</c:if>
				<th>${pxSale.pxxlName}:</th>
				<td>${pxSale.itemFileNumber}</td>
				<td>${pxSale.itemFileSale}</td>
			</tr>
			</c:forEach>
		</c:forEach>
    </table>
</div> --%>

<script>
    $(function () {
        $.get('DININGSYS/report/openDay/itemFileDataSaleStatistical', {
            startTime: $("#startTime").val(),
            endTime: $("#endTime").val(),
            pos: $("#pos").val(),
            area: $("#area").val(),
            timeType: $("input[name='timeType']:checked").val()
        }, function (data) {
        	console.log(data);
            var trs = "";
            /* var itemFileNumberTotal = 0;
            var itemFileNumberTotal2 = 0;
            var itemFileNumberTotal3 = 0;
            var finalPriceTotal = 0.0;
            var finalPriceTotal2 = 0.0;
            var finalPriceTotal3 = 0.0; */
            for (var i in data) {
            	var itemFileName = data[i]['itemFileName'];
            	var itemFileNumber = typeof(data[i]['itemFileNumber'])=='undefined'?0:data[i]['itemFileNumber'];
            	var itemFileNumber2 = typeof(data[i]['itemFileNumber2'])=='undefined'?0:data[i]['itemFileNumber2'];
            	var itemFileNumber3 = typeof(data[i]['itemFileNumber3'])=='undefined'?0:data[i]['itemFileNumber3'];
            	var finalPrice = typeof(data[i]['finalPrice'])=='undefined'?0.0:data[i]['finalPrice'];
            	var finalPrice2 = typeof(data[i]['finalPrice2'])=='undefined'?0.0:data[i]['finalPrice2'];
            	var finalPrice3 = typeof(data[i]['finalPrice3'])=='undefined'?0.0:data[i]['finalPrice3'];
            	/* itemFileNumberTotal += itemFileNumber;
            	itemFileNumberTotal2 += itemFileNumber2;
            	itemFileNumberTotal3 += itemFileNumber3;
            	finalPriceTotal += finalPrice;
            	finalPriceTotal2 += finalPrice2;
            	finalPriceTotal3 += finalPrice3; */
                trs += "<tr><td>"+itemFileName+"</td><td>"+itemFileNumber+"</td><td>"+itemFileNumber2+"</td><td>"+itemFileNumber3+"</td><td>"+finalPrice+"</td><td>"+finalPrice2+"</td><td>"+finalPrice3+"</td></tr>"
            }
            //var totaltrs = "<tr><th>汇总</th><th>"+itemFileNumberTotal+"</th><th>"+itemFileNumberTotal2+"</th><th>"+itemFileNumberTotal3+"</th><th>"+finalPriceTotal+"</th><th>"+finalPriceTotal2+"</th><th>"+finalPriceTotal3+"</th></tr>"
            //$("#headTr").after(totaltrs+trs);
            $("#headTr").after(trs);
        })
    })
</script>