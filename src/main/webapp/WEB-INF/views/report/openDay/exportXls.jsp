<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "日营业报表信息";
	response.setHeader("Content-disposition","inline; filename="+new String(fileName.getBytes("gb2312"),"iso8859-1")+".xls"); 
	//以上这行设定传送到前端浏览器时的档名为test1.xls 
	//就是靠这一行，让前端浏览器以为接收到一个excel档 
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html xmlns:x="urn:schemas-microsoft-com:office:excel">
  <head>
    <title>导出Excel</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="0">
	<xml>  
        <x:ExcelWorkbook>  
            <x:ExcelWorksheets>  
                <x:ExcelWorksheet>  
                    <x:Name>日营业报表信息</x:Name>  
                        <x:WorksheetOptions>  
                            <x:Print>  
                                <x:ValidPrinterInfo />  
                            </x:Print>  
                        </x:WorksheetOptions>  
                    </x:ExcelWorksheet>  
                </x:ExcelWorksheets>  
            </x:ExcelWorkbook>  
        </xml> 
</head>
<body>
	<table style="border: 1px solid #cccccc;">
		<thead>
			<tr><td style="font-size:24px; font-weight:bold;" colspan="3" align="center">${attrs['orgName']}</td></tr>
			<tr><td>开始时间：</td><td align="left" colspan="2">${attrs['startTime']}</td></tr>
			<tr><td>结束时间：</td><td align="left" colspan="2">${attrs['endTime']}</td></tr>
			
			<tr><td style="font-size:18px; font-weight:bold;" colspan="3">营业总额</td></tr>
			<tr><td></td><td>营业收入</td><td align="right">${attrs['yeze']}</td></tr>
			<tr><td></td><td>计入实际收入合计</td><td align="right">${attrs['jr']}</td></tr>
			<tr><td></td><td>不计入实际收入合计</td><td align="right">${attrs['bjr']}</td></tr>
			<tr><td></td><td>找零</td><td align="right">${attrs['zl']}</td></tr>
			<tr><td></td><td>抹零</td><td align="right">${attrs['mlje']}</td></tr>
			<tr><td></td><td>定额优惠</td><td align="right">${attrs['deyh']}</td></tr>
			<tr><td></td><td>退单金额</td><td align="right">${attrs['backSubtotal']}</td></tr>
			<tr><td></td><td>赠单优惠</td><td align="right">${attrs['zdyhje']}</td></tr>
			<tr><td></td><td>比例全单优惠</td><td align="right">${attrs['yhje']}</td></tr>
			<tr><td></td><td>品项促销优惠金额合计</td><td align="right">${attrs['cxyhje']}</td></tr>
			
			<tr><td style="font-size:18px; font-weight:bold;" colspan="3">结算方式统计</td></tr>
			<tr><td></td><td style="font-size:14px; font-weight:bold;">计入实际收入合计</td>
				<td style="font-size:14px; font-weight:bold;" align="right">${attrs['jr']}</td></tr>
			<c:forEach items="${attrs['clearingWay']}" var="list" varStatus="status">
				<c:if test="${(list.actualIncomeRatio == 0||list.actualIncomeRatio == 100) && list.notActualIncomeRatio != 100}">
					<tr><td></td><td>${list.seName}</td><td align="right">${list.settlementAmount}</td></tr>
				</c:if>
				<c:if test="${list.actualIncomeRatio != 0 && list.actualIncomeRatio < 100}">
					<tr><td></td><td>${list.seName}</td><td align="right">${(list.settlementAmount)*(list.actualIncomeRatio/100)}</td></tr>
				</c:if>
			</c:forEach>
			<tr><td></td><td style="font-size:14px; font-weight:bold;">不计入实际收入合计</td>
			<td style="font-size:14px; font-weight:bold;" align="right">${attrs['bjr']}</td></tr>
			<c:forEach items="${attrs['clearingWay']}" var="list" varStatus="status">
				<c:if test="${list.notActualIncomeRatio == 100}">
					<tr><td></td><td>${list.seName}</td><td align="right">${list.settlementAmount}</td></tr>
				</c:if>
				<c:if test="${list.notActualIncomeRatio != 0 && list.notActualIncomeRatio < 100}">
					<tr><td></td><td>${list.seName}</td><td align="right">${(list.settlementAmount)*(list.notActualIncomeRatio/100)}</td></tr>
				</c:if>
			</c:forEach>
			<tr><td style="font-size:18px; font-weight:bold;" colspan="3">会员充值统计</td></tr>
			<c:forEach items="${attrs['rechargeDatas']}" var="list" varStatus="status">
					<tr><td></td><td>${list.payType}</td><td align="right">${list.payMoney}</td></tr>
			</c:forEach>
			<tr><td style="font-size:18px; font-weight:bold;" colspan="3">品项小类销售统计</td></tr>
			<c:forEach items="${attrs['smallSell']}" var="list" varStatus="status">
					<tr><td></td><td>${list.pxxl}</td><td align="right">${list.subtotal}</td></tr>
			</c:forEach>
			<tr><td style="font-size:18px; font-weight:bold;" colspan="3">账单和客位信息</td></tr>
			<tr><td></td><td>正常结算流水总数</td><td align="right">${attrs['normalOpenWaterCount']}</td></tr>
			<tr><td></td><td>返回结算流水总数</td><td align="right">${attrs['fwjsCount']}</td></tr>
			<tr><td></td><td>正常营业流水总数</td><td align="right">${attrs['noramlOpenWater']}</td></tr>
			<tr><td></td><td>今日就餐总人数</td><td align="right">${attrs['jcrs']}</td></tr>
			<tr><td></td><td>今日结算人数</td><td align="right">${attrs['jsrs']}</td></tr>
			<tr><td></td><td>S账单数</td><td align="right">${attrs['sbill']}</td></tr>
			
			<tr><td style="font-size:18px; font-weight:bold;" colspan="3">特殊品项统计</td></tr>
			<tr><td></td><td>免除的最低消费</td><td align="right">${attrs['freeMinSpeed']}</td></tr>
			<tr><td></td><td>免除的包房费</td><td align="right">${attrs['freePrivateRoom']}</td></tr>
			<tr><td></td><td>包房费总金额</td><td align="right">${attrs['allPrivateRoom']}</td></tr>
			<tr><td></td><td>免除的服务费</td><td align="right">${attrs['freeServiceCharge']}</td></tr>

			<tr><td style="font-size:18px; font-weight:bold;" colspan="8">品项销售统计</td></tr>
			<tr><td></td><td>品项名称</td><td>正常数量</td><td>赠送数量</td><td>宴请数量</td><td>正常金额</td><td>赠送金额</td><td>宴请金额</td></tr>
			<c:forEach items="${itemFileDataSaleStatisticalList}" var="map" varStatus="status">
				<tr><td></td><td>${map['itemFileName']}</td>
					<td>${empty map['itemFileNumber']?0:map['itemFileNumber']}</td>
					<td>${empty map['itemFileNumber2']?0:map['itemFileNumber2']}</td>
					<td>${empty map['itemFileNumber3']?0:map['itemFileNumber3']}</td>
					<td>${empty map['finalPrice']?0.0:map['finalPrice']}</td>
					<td>${empty map['finalPrice2']?0.0:map['finalPrice2']}</td>
					<td>${empty map['finalPrice3']?0.0:map['finalPrice3']}</td></tr>
			</c:forEach>
						
		</thead>
	</table>
		
</body>
</html>
