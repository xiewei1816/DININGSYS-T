<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "结账单查询信息";
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
                    <x:Name>结账单查询信息</x:Name>  
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
			<tr>
				<th>结算流水编号</th><th>营业流水</th><th>结算时间</th><th>最早开台时间</th><th>客位列表</th><th>就餐人数</th>
				<th>返位结算</th><th>市别</th><th>品项消费</th><th>服务费</th>
				<th>实收服务费</th><th>包房费</th><th>最低消费补齐</th><th>消费总金额</th><th>优惠金额</th>
				<th>优惠方式</th><th>优惠授权人</th><th>抹零</th><th>定额优惠</th><th>实收金额</th><th>应收金额</th><th>找零</th>
				<%-- <th>现金</th><th>信用卡</th><th>会员卡</th><th>会员挂账</th><th>支票</th><th>支付宝</th><th>微信</th> --%>
				<th>押金</th><th>结算POS</th><th>结算操作员</th>
				<th>打印次数</th><th>开具发票</th><th>发票金额</th>
				<!-- <th>返位时间</th><th>返位POS</th>
				<th>操作员</th><th>授权人</th><th>结算备注</th><th>结账单备注</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${statementList}" var="statement" varStatus="index">
			 <tr>
				<td align="center">${statement.cw_num}</td>
				<td align="center">${statement.ow_num}</td>
				<td align="center">${statement.clearingTime}</td>
				<td align="center">${statement.firstTime}</td>
				<td align="center">${statement.seatName}</td>
				<td align="center">${statement.people_count}</td>
				
				<td align="center">${statement.clearing_state==3?'√':'×'}</td>
				<td align="center">${statement.bis_name}</td>
				<td align="center">${statement.consumption_amount}</td>
				<td align="center">${statement.service_charge}</td>
				
				<td align="center">${statement.service_charge}</td>
				<td align="center">${statement.private_room_cost}</td>
				<td align="center">${statement.minimum_charge_complete}</td>
				<td align="center">${statement.consumption_amount}</td>
				<td align="center">${statement.discount_costs}</td>
				
				<td align="center">${statement.discount_info}</td>
				<td align="center">${statement.authorized_person_name}</td>
				<td align="center">${statement.zero_amount}</td>
				<td align="center">${statement.fixed_discount}</td>
				<td align="center">${statement.paid_amount}</td>
				<td align="center">${statement.amounts_receivable}</td>
				<td align="center">${statement.change_amount}</td>
				
				<%--<td align="center">${statement.RMB}</td>
				<td align="center">${statement.CREDIT_CARD}</td>
				<td align="center">${statement.HYZF}</td>
				<td align="center">${statement.HYGZ}</td>
				<td align="center">${statement.CHEQUE}</td>
				<td align="center">${statement.ALIPAY}</td>
				<td align="center">${statement.WECHAT}</td> --%>
				
				<td align="center">${statement.cp_money}</td>
				<td align="center">${statement.posName}</td>
				<td align="center">${statement.operatorName}</td>
				
				<td align="center">${statement.print_cont}</td>
				<%-- <td align="center">${statement.allChequeCheck}</td> --%>
				<td align="center">${statement.allCheque>0?'√':'×'}</td>
				<td align="center">${statement.allCheque}</td>
				
				<!-- <td align="center">${statement.num}</td>
				<td align="center">${statement.name}</td>
				
				<td align="center">${statement.unit}</td>
				<td align="center">${statement.pxdl}</td>
				<td align="center">${statement.clearing_notes}</td>
				<td align="center">${statement.statement_label}</td> -->
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
