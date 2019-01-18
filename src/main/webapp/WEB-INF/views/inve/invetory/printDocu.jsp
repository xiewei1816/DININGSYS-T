<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>打印</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="expires" content="0">
</head>
<body>
	<table style="border: 1px solid #cccccc;">
		<thead>
			<tr>
				<th>仓库编码</th>
				<th>仓库名称</th>
				<th>物品编码</th>
				<th>物品名称</th>
				<th>数量</th>
				<th>单位</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${inve}" var="i" varStatus="index">
			 <tr>
			 	<td align="center">${i.wareNo}</td>
				<td align="center">${i.wareName}</td>
				<td align="center">${i.itemNo}</td>
				<td align="center">${i.joinItemName}</td>
				<td align="center">${i.number}</td>
				<td align="center">${i.unit}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script type="text/javascript">
	window.print();
</script>
</html>
