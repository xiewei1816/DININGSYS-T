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
				<th>品项名称</th>
				<th>营业流水</th>
				<th>服务流水</th>
				<th>结算流水</th>
				<th>折前销售金额</th>
				<th>优惠金额</th>
				<th>品项金额</th>
				<th>制作费用</th>
				<th>合计</th>
				<th>数量</th>
				<th>加单时间</th>
				<th>结算时间</th>
				<th>客位</th>
				<th>操作POS</th>
				<th>结算价格</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemFileTypeOpenWaterList}" var="itemFileTypeOpenWater" varStatus="index">
			 <tr>
				<td align="center">${itemFileTypeOpenWater.name}</td>
				<td align="center">${itemFileTypeOpenWater.ow_num}</td>
				<td align="center">${itemFileTypeOpenWater.service_num}</td>
				<td align="center">${itemFileTypeOpenWater.cw_num}</td>
				<td align="center">${itemFileTypeOpenWater.zqje}</td>
				<td align="center">${itemFileTypeOpenWater.zrje}</td>
				<td align="center">${itemFileTypeOpenWater.pxje}</td>
				<td align="center">${itemFileTypeOpenWater.zzfy}</td>
				<td align="center">${itemFileTypeOpenWater.zhje}</td>
				<td align="center">${itemFileTypeOpenWater.item_file_number}</td>
				<td align="center">${itemFileTypeOpenWater.service_time}</td>
				<td align="center">${itemFileTypeOpenWater.clearing_time}</td>
				<td align="center">${itemFileTypeOpenWater.seatName}</td>
				<td align="center">${itemFileTypeOpenWater.posName}</td>
				<td align="center">${itemFileTypeOpenWater.mdje}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
