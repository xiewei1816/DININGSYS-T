<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "营业收入分析报表";
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
                    <x:Name>营业收入分析报表</x:Name>  
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
				<th>
        			统计项名称
        		</th>
        		<th>
        			人民币
        		</th>
        		<th>
        			应收金额
        		</th>
        		<th>
        			实收金额
        		</th>
        		<th>
        			找零金额
        		</th>
        		<th>
        			定额优惠
        		</th>
        		<th>
        			抹零金额
        		</th>
        		<th>
        			消费金额
        		</th>
        		<th>
        			赠卷金额
        		</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${data.zb}" var="item" varStatus="index">
			 <tr>
				<td align="center">${item.name}</td>
				<td align="center">${item.ys}</td>
				<td align="center">${item.ys}</td>
				<td align="center">${item.ss}</td>
				<td align="center">${item.zl}</td>
				<td align="center">${item.de}</td>
				<td align="center">${item.ml}</td>
				<td align="center">${item.xf}</td>
				<td align="center">${item.zs}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
