<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="app/css/DININGSYS/deskBusiness/product_item/price_priority.css">
<script src="app/js/DININGSYS/deskBusiness/product_item/price_priority.js"></script>
<title>品项价格按市别</title>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
</head>
<body>
	<div class="wrapper animated fadeInUp">
			<c:if test="${single == '1' }">
				<div class="btn-toolbar">
					<span id="save" class="greenbtn"><i class="fa fa-file-o"></i>保存</span>
				</div>
			</c:if>
			
			<div class="content overflow">
					<div class="left-table">
					    <form class="form-horizontal" role="form" id="userImpDisForm">
							<ul class="left-content">
								 <c:forEach items="${list}" var="item">
									<li move='0'>
									  <div class="left-5">
									      <input type="checkbox"  name="${item.id }" <c:if test="${'1' == item.enable}">checked</c:if>/> 
									      <a onclick="aClick(this);" class="m-a">${item.name}</a>
									  </div>
									</li>
								 </c:forEach>
							</ul>
					    </form>
					</div>
					<c:if test="${single == '1' }">
						<div class="right-content">
							<input type="button" value="上移" onclick="up();"  class="r-button-up">
							<input type="button" value="下移" onclick="down();" class="r-button-down">
							<input type="button" value="默认设置" class="r-button-mo" id="mo">
						</div>
					</c:if>
			    </div>
			    <div class="container">
			       		<fieldset class="c-fieldset">
			       			<legend class="container-title">说明</legend>
			                    <div>
			                        <label>1、前台加单时,将按照你设置的顺序读取品项价格。</label>
			                    </div>
			                    <div >
			                        <label>2、只要读取成功其中一个价格,就不再向下读取。</label>
			                    </div>
			                    <div>
			                        <label>3、勾选对应的品项价格方案,才使用此品项价格方案。</label>
			                    </div>
			       		</fieldset>
	       			</div> 
	            </div>
		</body>
</html>