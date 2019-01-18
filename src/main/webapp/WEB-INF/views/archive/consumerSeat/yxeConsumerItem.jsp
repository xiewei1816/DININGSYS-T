<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script src="app/js/DININGSYS/archive/consumerSeat/yxeConsumerItem.js"></script>
<style>
	.wd150{width:150px !important;float:left;}
	.wd250{width:250px !important;float:left;margin-left: 20px;}
</style>
<script>
    $(function ($) {
        $(".panel-body").height($(window).height()-100);
    })
</script>

<body>
<div class="wrapper animated fadeInUp">
   <div class="wd150">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">区域信息</h3>
                </div>
                <div class="panel-body sysPerPanelBody" style="overflow: scroll;">
            		<ul id="consumerTree" class="ztree"></ul>
                </div>
            </div>
     </div>
   <div class="wd250">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">品项信息</h3>
                </div>
                <div  class="panel-body sysPerPanelBody" style="overflow:scroll;">
						<ul id="itemTree" class="ztree"></ul>
                </div>
            </div>
     </div>
</div>