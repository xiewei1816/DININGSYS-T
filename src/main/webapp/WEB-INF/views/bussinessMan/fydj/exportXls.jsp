<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "费用登记信息";
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
                    <x:Name>费用登记信息</x:Name>  
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
				<th>费用名称</th>
				<th>费用类型</th>
				<th>支出费用</th>
				<th>收入费用</th>
				<th>费用发生时间</th>
				<th>所属组织机构</th>
				<th>创建时间</th>
				<th>摘要</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fydjList}" var="fydj" varStatus="index">
			 <tr>
				<td align="center">${fydj.fydjName}</td>
				<td align="center">${fydj.fydjType}</td>
				<td align="center">${fydj.fydjExpend}</td>
				<td align="center">${fydj.fydjEarning}</td>
				<td align="center">${fydj.fydjTime}</td>
				<td align="center">${fydj.fydjOrganization}</td>
				<td align="center">${fydj.createTime}</td>
				<td align="center">${fydj.fydjAbstract}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
