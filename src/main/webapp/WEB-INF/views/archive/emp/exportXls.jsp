<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="application/msexcel" %> 
<% 
	String fileName = "员工信息";
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
                    <x:Name>员工信息</x:Name>  
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
				<th>员工编号</th>
				<th>员工姓名</th>
				<th>所属机构</th>
				<th>所属部门</th>
				<th>职务</th>
				<th>出生日期</th>
				<th>身份证号</th>
				<th>性别</th>
				<th>婚姻状况</th>
				<th>员工卡ID</th>
				<th>授权号1</th>
				<th>授权号2</th>
				<th>集团用户标志</th>
				<th>管理员标志</th>
				<th>启动标志</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${empList}" var="emp" varStatus="index">
			 <tr>
				<td align="center">${emp.empCode}</td>
				<td align="center">${emp.empName}</td>
				<td align="center">${emp.empOrganization}</td>
				<td align="center">${emp.empDepartment}</td>
				<td align="center">${emp.empDuties}</td>
				<td align="center">${emp.empDob}</td>
				<td align="center">${emp.empCardno}</td>
				<td align="center">${emp.empSex}</td>
				<td align="center">${emp.isMarry}</td>
				<td align="center">${emp.empCardid}</td>
				<td align="center">${emp.sqhNo1}</td>
				<td align="center">${emp.sqhNo2}</td>
				<td align="center">${emp.isOrguserFlag==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${emp.isManagerFlag==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
				<td align="center">${emp.isStartFlag==1?'<font color="green">是</font>':'<font color="red">否</font>'}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
