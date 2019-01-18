<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "结算方式信息";
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
                    <x:Name>结算方式信息</x:Name>  
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
				<th>结算流水</th>
				<th>结算方式</th>
				<th>结算方式类型</th>
				<th>应收金额</th>
				<th>实收金额</th>
				<th>支付金额</th>
				<th>换算后金额</th>
				<th>找零金额</th>
				<th>修正标志</th>
				<th>结算时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${payWayList}" var="payway" varStatus="index">
			 <tr>
				<td align="center">${payway.cwNum}</td>
				<td align="center">${payway.cName}</td>
				<td align="center">${payway.cwType}</td>
				<td align="center">${payway.amountsReceivable}</td>
				<td align="center">${payway.paidAmount}</td>
				<td align="center">${payway.paidAmount}</td>
				<td align="center">${payway.paidAmount}</td>
				<td align="center">${payway.changeAmount}</td>
				<td align="center">${payway.clearingState==3?'<font color="red">是</font>':((payway.clearingState==0)?'':'<font color="green">否</font>')}</td>
				<td align="center">${payway.clearingTime}</td>
				<td align="center">${payway.clearingNotes}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
