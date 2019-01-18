<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "品项类别销售信息";
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
                    <x:Name>品项类别销售信息</x:Name>  
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
				<th>大类编号</th>
				<th>品项大类</th>
				<th>小类编号</th>
				<th>品项小类</th>
				<th>折前销售金额</th>
				<th>优惠金额</th>
				<th>品项金额</th>
				<th>制作费用</th>
				<th>合计</th>
				<th>数量</th>
				<th>平均售价</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemFileTypeSmallList}" var="itemFileTypeSmall" varStatus="index">
			 <tr>
				<td align="center">${itemFileTypeSmall.num}</td>
				<td align="center">${itemFileTypeSmall.name}</td>
				<td align="center">${itemFileTypeSmall.smallNum}</td>
				<td align="center">${itemFileTypeSmall.smallName}</td>
				<td align="center">${itemFileTypeSmall.zqje}</td>
				<td align="center">${itemFileTypeSmall.zrje}</td>
				<td align="center">${itemFileTypeSmall.pxje}</td>
				<td align="center">${itemFileTypeSmall.zzfy}</td>
				<td align="center">${itemFileTypeSmall.zhje}</td>
				<td align="center">${itemFileTypeSmall.itemFileNumber}</td>
				<td align="center">${itemFileTypeSmall.avgPrice}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
