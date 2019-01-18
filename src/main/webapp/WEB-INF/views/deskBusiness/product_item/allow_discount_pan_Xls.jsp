<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "品项打折方案信息";
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
                    <x:Name>品项打折方案信息</x:Name>  
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
				<th>方案代码</th>
				<th>方案名称</th>
				<th>方案类型</th>
				<th>停用</th>
				<th>允许修改优惠比例</th>
				<th>修改时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${discountPan}" var="item" varStatus="index">
			 <tr>
				<td align="center">${item.code}</td>
				<td align="center">${item.name}</td>
				<td align="center">${item.type ==1?'<font color="green">按品项设置</font>':'<font color="green">按小类设置</font>'}</td>
				<td align="center">${item.disable ==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${item.allowFDis ==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${item.time}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
