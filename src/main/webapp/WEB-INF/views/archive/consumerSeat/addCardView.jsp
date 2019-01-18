<%-- 
    Document   : consumerSeatInfo
    Created on : 2016-11-11, 12:55:22
    Author     : yqsh-zc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<script>
    $("select.chosens").chosen({
    		width : "200px"
    });
    $(function(){
		$('#cardnum').keypress(function(e){
            if(e.keyCode==13){
   				e.preventDefault(); //阻止回车提交表单
   				callBack();
            }
		});
    });
</script>
<style>
    .required{
            display:block;
            position:absolute;
            color: red;
            right:5px;
            top:6px;
            font-size: 25px;
    }
</style>
</head>
<body style="overflow-x:hidden;overflow-y: auto;">
<form class="form-horizontal" role="form" id="myfrom" method="post">
        <input type="hidden" name="consumerid" value="${id}" >
        <div class="form-group">
            <div class="col-sm-12">
                    <label class="col-sm-4 control-label"><span class="required">*</span>台卡卡号:</label>
                    <div class="col-sm-7">
                        <input placeholder="请输入台卡卡号" type="text" class="form-control" name="cardnum" dete="val" id="cardnum"/>
                    </div>
            </div>
        </div>
</form>
</body>