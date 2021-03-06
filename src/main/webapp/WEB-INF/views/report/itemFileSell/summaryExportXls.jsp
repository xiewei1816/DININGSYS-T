<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "品项销售明细汇总信息";
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
                    <x:Name>品项销售明细汇总信息</x:Name>  
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
				<th>品项编号</th>
				<th>品项名称</th>
				<th>单位名称</th>
				<th>大类名称</th>
				<th>小类名称</th>
				<th>价格</th>
				<th>数量</th>
				<th>金额</th>
				<th>品项标志</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemFileSellSummaryList}" var="itemFileSellSummary" varStatus="index">
			 <tr>
				<td align="center">${itemFileSellSummary.num}</td>
				<td align="center">${itemFileSellSummary.name}</td>
				<td align="center">${itemFileSellSummary.unit}</td>
				<td align="center">${itemFileSellSummary.pxdl}</td>
				<td align="center">${itemFileSellSummary.pxxl}</td>
				<td align="center">${itemFileSellSummary.item_final_price}</td>
				<td align="center">${itemFileSellSummary.number}</td>
				<td align="center">${itemFileSellSummary.subtotal}</td>
				<td align="center">${itemFileSellSummary.is_tc==1?'套餐品项':'非套餐品项'}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
