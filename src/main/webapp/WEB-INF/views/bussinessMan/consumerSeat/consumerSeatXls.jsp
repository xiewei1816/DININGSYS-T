<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "消费区域客位管理信息";
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
                    <x:Name>消费区域客位管理信息</x:Name>  
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
				<th>客位编号</th>
				<th>客位名称</th>
				<th>最低消费是否收取</th>
				<th>最低消费收取方式</th>
				<th>最低消费收取金额</th>
				<th>服务费是否收取</th>
				<th>服务费收取方式</th>
				<th>服务费收取上限</th>
				<th>包房费是否收取</th>
				<th>包房费收取方式</th>
				<th>包房费收取金额</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td align="center">${item.seatCode}</td>
				<td align="center">${item.seatName}</td>
				<td align="center">${item.zdxf ==1?'<font color="green">否</font>':'<font color="red">是</font>'}</td>
				<td align="center">${item.zdxfType ==1 ?'按每客位 ':'每客位人数类型'}</td>
				<td align="center">${item.zdxfMoney}</td>
				<td align="center">${item.fwf ==1?'<font color="green">否</font>':'<font color="red">是</font>'}</td>
				<td align="center">${item.fwf ==1?'不收取':(item.fwf ==2 ?'固定金额收取':(item.fwf ==3 ?'消费比例收取':'客位人数'))}</td>
				<td align="center">${item.fwfSx}</td>
				<td align="center">${item.bff ==1?'<font color="green">否</font>':'<font color="red">是</font>'}</td>
				<td align="center">${item.bff ==1?'不收取':(item.fwf ==2 ?'固定金额收取':(item.fwf ==3 ?'消费比例收取':'包房方案收取'))}</td>
				<td align="center">${item.bffGd}</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
