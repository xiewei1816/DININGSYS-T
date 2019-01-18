<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "品项销售明细信息";
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
                    <x:Name>品项销售明细信息</x:Name>  
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
				<th>服务方式</th>
				<th>品项编号</th>
				<th>品项名称</th>
				<th>单位名称</th>
				<th>大类名称</th>
				<th>小类名称</th>
				<th>价格</th>
				<th>数量</th>
				<th>金额</th>
				<th>日期时间</th>
				<th>品项标志</th>
				<th>服务POS</th>
				<th>操作员</th>
				<th>客位</th>
				<th>营业流水编号</th>
				<th>服务流水编号</th>
				<th>销售类型</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemFileSellDetailList}" var="itemFileSellDetail" varStatus="index">
			 <tr>
				<td align="center">${itemFileSellDetail.service_type}</td>
				<td align="center">${itemFileSellDetail.num}</td>
				<td align="center">${itemFileSellDetail.name}</td>
				<td align="center">${itemFileSellDetail.unit}</td>
				<td align="center">${itemFileSellDetail.pxdl}</td>
				<td align="center">${itemFileSellDetail.pxxl}</td>
				<td align="center">${itemFileSellDetail.item_final_price}</td>
				<td align="center">${itemFileSellDetail.item_file_number}</td>
				<td align="center">${itemFileSellDetail.subtotal}</td>
				<td align="center">${itemFileSellDetail.service_time}</td>
				<td align="center">${itemFileSellDetail.is_tc == 0?'非套餐品项':'套餐品项'}</td>
				<td align="center">${itemFileSellDetail.pos}</td>
				<td align="center">${itemFileSellDetail.userName}</td>
				<td align="center">${itemFileSellDetail.seatName}</td>
				<td align="center">${itemFileSellDetail.ow_num}</td>
				<td align="center">${itemFileSellDetail.service_num}</td>
				<td align="center">${itemFileSellDetail.xxlx_id}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
