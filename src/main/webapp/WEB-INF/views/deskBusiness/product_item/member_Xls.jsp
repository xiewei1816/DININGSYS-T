<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "会员打折方案信息";
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
                    <x:Name>会员打折方案信息</x:Name>  
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
				<th>发布状态</th>
				<th>启用</th>
				<th>应用所有店</th>
				<th>开始日期</th>
				<th>结束日期</th>
				<th>说明</th>
				<th>最后修改日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${member}" var="item" varStatus="index">
			 <tr>
				<td align="center">${item.name}</td>
				<td align="center">${item.publish}</td>
				<td align="center">${item.enable}</td>
				<td align="center">${item.useAllShop}</td>
				<td align="center">${item.startDate}</td>
				<td align="center">${item.endDate}</td>
				<td align="center">${item.explains}</td>
				<td align="center">${item.updateTime}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
