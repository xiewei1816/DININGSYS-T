<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "充值记录信息";
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
                    <x:Name>充值记录</x:Name>  
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
				<th>商户订单号</th>
				<th>第三方订单号</th>
				<th>店内流水号</th>
				<th>支付金额</th>
				<th>支付类型</th>
				<th>支付状态</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listObj}" var="o" varStatus="index">
			 <tr>
				<td align="center" style='mso-number-format:"\@";'>${o.outTradeNo}</td>
				<td align="center" style='mso-number-format:"\@";'>${o.threeTradeNo}</td>
				<td align="center" style='mso-number-format:"\@";'>${o.orderNo}</td>
				<td align="center" style='mso-number-format:"\@";'>${o.payMoney}</td>
				<td align="center">${o.payType}</td>
				<td align="center">${o.payState==0?'成功':'失败'}</td>
				<td align="center" style='mso-number-format:"\@";'>${o.createDate}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
