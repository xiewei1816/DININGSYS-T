<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "赠送品项方案信息";
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
                    <x:Name>赠送品项方案信息</x:Name>  
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
				<th>方案名称</th>
				<th>品项编码</th>
				<th>品项名称</th>
				<th>开始日期</th>
				<th>开始时间</th>
				<th>结束日期</th>
				<th>结束时间</th>
				<th>星期一</th>
				<th>星期二</th>
				<th>星期三</th>
				<th>星期四</th>
				<th>星期五</th>
				<th>星期六</th>
				<th>星期日</th>
				<th>赠送次数限定</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${gift}" var="item" varStatus="index">
			  <tr>
				<td align="center">${item.name}</td>
				<td align="center">${item.itemCode}</td>
				<td align="center">${item.itemName}</td>
				<td align="center">${item.startDate}</td>
				<td align="center">${item.startTime}</td>
				<td align="center">${item.endDate}</td>
				<td align="center">${item.endTime}</td>
				<td align="center">${item.week1 ==1?'<font color="green">启用</font>':'<font color="red">停用</font>'}</td>
				<td align="center">${item.week2 ==1?'<font color="green">启用</font>':'<font color="red">停用</font>'}</td>
				<td align="center">${item.week3 ==1?'<font color="green">启用</font>':'<font color="red">停用</font>'}</td>
				<td align="center">${item.week4 ==1?'<font color="green">启用</font>':'<font color="red">停用</font>'}</td>
				<td align="center">${item.week5 ==1?'<font color="green">启用</font>':'<font color="red">停用</font>'}</td>
				<td align="center">${item.week6 ==1?'<font color="green">启用</font>':'<font color="red">停用</font>'}</td>
				<td align="center">${item.week7 ==1?'<font color="green">启用</font>':'<font color="red">停用</font>'}</td>
				<td align="center">${item.giftFrequencyLimit}</td>
			  </tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
