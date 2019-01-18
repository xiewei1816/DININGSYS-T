<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "预订单信息";
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
                    <x:Name>预订单信息</x:Name>
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
				<th>预定单号</th>
				<th>客位号</th>
				<th>姓名</th>

				<th>性别</th>
				<th>人数</th>
				<th>电话号码</th>

				<th>下单时间</th>
				<th>就餐时间</th>
				<th>晚餐或晚餐</th>
				<th>状态</th>


			</tr>
		</thead>
		<tbody>
			<c:forEach items="${reserveManagerList}" var="reserveManager" varStatus="index">
			 <tr>
				<td align="center">${reserveManager.ydNum}</td>
				<td align="center">${reserveManager.seatName}</td>
				<td align="center">${reserveManager.name}${reserveManager.sex == '2'?'女士':'男士'}</td>

				 <td align="center">${reserveManager.sex == '2'?'女士':'男士'}</td>
				 <td align="center">${reserveManager.number}</td>
				 <td align="center">${reserveManager.phone}</td>

				 <td align="center">${reserveManager.time}</td>
				 <td align="center">${reserveManager.ydTime}</td>
				 <td align="center">${reserveManager.wOw == '1'?'午餐':'晚餐'}</td>
				 <td align="center">
					 <c:if test="${reserveManager.state == '0'}" ><font color="#000">初始化</font></c:if>
					 <c:if test="${reserveManager.state == '1'}" ><font color="green">已成</font></c:if>
					 <c:if test="${reserveManager.state == '-1'}" ><font color="red">未到</font></c:if>
					 <c:if test="${reserveManager.state == '3'}" ><font color="blue">已通知</font></c:if>
				 </td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
