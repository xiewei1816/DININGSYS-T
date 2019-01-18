<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>颜色管理</title>
		<script type="text/javascript">
			 jQuery(document).ready(function () {
				 itemFileColorManager.pageInit();
			 })
		</script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
	        <div class="jqGrid_wrapper">
                <table id="grid-table-color"></table>
            </div>
	    </div>
	</body>
</html>
