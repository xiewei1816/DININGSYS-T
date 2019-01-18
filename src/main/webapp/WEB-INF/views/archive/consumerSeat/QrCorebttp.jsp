<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
		<jsp:include page="../../head.jsp"/>
    </head>
<body>
<script>
    jQuery(document).ready(function () {
        $("#print").click(function () {
        	var headstr = "<html><head><title></title></head><body>";  
        	var footstr = "</body>";  
        	var printData = document.getElementById("BodyPanel").innerHTML;
        	var oldstr = document.body.innerHTML;  
        	document.body.innerHTML = headstr+printData+footstr;  
        	window.print();  
        	document.body.innerHTML = oldstr;  
        	location.reload();
        })
    })
</script>
<form id="myform">
    <div class="btn-toolbar">
         <span id="print" class="bluebtn"><i class="fa fa-edit"></i>打印</span>
    </div>
    <div id="BodyPanel">
        <div id="content" style="margin: 50px;">
            <img src="${pageContext.request.contextPath}/qrCode?id=${seatId}" id="target" style="max-width:300px;max-height:300px;"/>
        </div>
    </div>
</form>
</body>
</html>