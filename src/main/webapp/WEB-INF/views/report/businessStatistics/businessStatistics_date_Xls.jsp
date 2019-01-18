<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "营业统计日报";
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
                    <x:Name>营业统计日报</x:Name>  
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
				<th>事项</th><th>昨日累计</th><th>本日累计</th><th>本月累计</th><th>本年累计</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data.srfl}" var="item" varStatus="index">
			 <tr>
				<td align="center">${item.name}</td>
				<td align="center">${item.zr}</td>
				<td align="center">${item.jr}</td>
				<td align="center">${item.by}</td>
				<td align="center">${item.bn}</td>
			</tr>
			</c:forEach>
			<c:forEach items="${data.skfl}" var="item" varStatus="index">
				 <tr>
					<td align="center">${item.name}</td>
					<td align="center">${item.znsum}</td>
					<td align="center">${item.jsum}</td>
					<td align="center">${item.bysum}</td>
					<td align="center">${item.bnsum}</td>
				</tr>
			</c:forEach>
			<c:forEach items="${data.rjfl}" var="item" varStatus="index">
				 <tr>
					<td align="center">${item.name}</td>
					<td align="center">${item.zrp}</td>
					<td align="center">${item.jrp}</td>
					<td align="center">${item.byp}</td>
					<td align="center">${item.bnp}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
