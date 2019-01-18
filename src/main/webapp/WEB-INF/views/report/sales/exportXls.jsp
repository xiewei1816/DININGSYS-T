<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "营销员销售信息";
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
                    <x:Name>营销员销售信息</x:Name>
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
				<th>品项名称</th>
				<th>销售数量</th>
				<%--<th>标准价格</th>
				<th>优惠金额</th>--%>
				<th>销售金额(优惠后)</th>
				<th>营销员</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${salesList}" var="sales" varStatus="index">
			 <tr>
				<td align="center">${sales.itemFileName}</td>
				<td align="center">${sales.number}</td>
				<%--<td align="center">${sales.standardPrice}</td>
				<td align="center">${sales.discountMoney}</td>--%>
				<td align="center">${sales.total}</td>
				<td align="center">${sales.marketingStaffName}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
