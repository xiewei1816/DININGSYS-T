<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "自定义金额信息";
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
                    <x:Name>自定义金额信息</x:Name>  
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
				<th>自定义金额名称</th>
				<th>品项代码</th>
				<th>品项名称</th>
				<th>标准价格</th>
				<th>套餐</th>
				<th>品项大类</th>
				<th>品项小类</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${custom}" var="item" varStatus="index">
			 <tr>
				<td align="center">${item.customMoneyName}</td>
				<td align="center">${item.itemCode}</td>
				<td align="center">${item.name}</td>
				<td align="center">${item.sPrice}</td>
				<td align="center">${item.tc}</td>
				<td align="center">${item.bName}</td>
				<td align="center">${item.sName}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
