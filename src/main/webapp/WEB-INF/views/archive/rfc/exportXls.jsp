<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "退菜原因信息";
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
                    <x:Name>退菜原因信息</x:Name>  
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
				<th>编号</th>
				<th>名称</th>
				<th>退菜原因类型</th>
				<th>助记符</th>
				<th>原料损失</th>
				<th>退单时沽清品项</th>
				<th>创建时间</th>
				<th>说明</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${rfcList}" var="rfc" varStatus="index">
			 <tr>
				<td align="center">${rfc.rfcCode}</td>
				<td align="center">${rfc.rfcName}</td>
				<td align="center">${rfc.rfcType}</td>
				<td align="center">${rfc.rfcZjf}</td>
				<td align="center">${rfc.isMaterialLoss==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${rfc.isTdsgqpx==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${rfc.createTime}</td>
				<td align="center">${rfc.rfcExplain}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
