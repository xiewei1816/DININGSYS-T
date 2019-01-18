<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "机构信息";
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
                    <x:Name>机构信息</x:Name>  
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
				<th>机构编码</th>
				<th>机构名称</th>
				<th>速记码</th>
				<th>开店日期</th>
				<th>加密狗号</th>
				<th>电话</th>
				<th>管理模式</th>
				<th>区域</th>
				<th>品牌</th>
				<th>加盟商</th>
				<th>最大容纳客数</th>
				<th>地址</th>
				<th>本店标志</th>
				<th>启用标志</th>
				<th>新店标志</th>
				<th>排队标志</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orgList}" var="org" varStatus="index">
			 <tr>
				<td align="center">${org.orgCode}</td>
				<td align="center">${org.orgName}</td>
				<td align="center">${org.orgSjm}</td>
				<td align="center">${org.orgKdsj}</td>
				<td align="center">${org.orgJmgh}</td>
				<td align="center">${org.orgPhone}</td>
				<td align="center">${org.orgGlms}</td>
				<td align="center">${org.orgArea}</td>
				<td align="center">${org.orgBrand}</td>
				<td align="center">${org.franchisees}</td>
				<td align="center">${org.maxCustomer}</td>
				<td align="center">${org.address}</td>
				<td align="center">${org.isOwnFlag==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${org.isStartFlag==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${org.isNewstoreFlag==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${org.isLineFlag==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${org.remark}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
