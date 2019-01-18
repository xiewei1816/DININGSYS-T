<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
		<jsp:include page="../../head.jsp"/>
		<script>
			var single = '${single}';
		</script>
        <script type="text/javascript" src="assets/scripts/ls-tree.js" ></script>
        <script type="text/javascript" src="assets/scripts/myvalidatafrom.js" ></script>
        <script type="text/javascript" src="app/js/DININGSYS/archive/consumerSeat/consumerSeat-index.js" ></script>
        <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.exedit.js"></script>
        <link rel="stylesheet" href="assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
        <script type="text/javascript" src="app/js/DININGSYS/archive/consumerSeat/consumerSeatInfo.js"></script>
        <script type="text/javascript" src="app/js/DININGSYS/archive/consumerSeat/consumerSeat_trash.js"></script>
    	<style type="text/css">
			.extra {width: 200px; border: 1px solid #f6a828; height:600px; float:left;overflow: auto;}
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
			.child {margin-left: 210px}
		</style>
    </head>
    <body>
		<div id="menuTree" class="extra" >
            	<ul id="tree" class="ztree"></ul>
         </div>
        <div class="menus-form">
            <form class="editForm" id="classForm" style="display: none;">
                <ul>
                    <li>
                        <span class="title"><span class="required">*</span>编号</span>
                        <input type="hidden" class="form-control edit_id" editype="val" name="id" id="c-id">
                        <div class="form-group bigest-wid">
                            <input type="text" class="form-control edit_number" editype="val" name="number" dete="val" id="men_number"/>
                        </div>
                    </li>
                    <li>
                        <span class="title"><span class="required">*</span>名称</span>
                        <div class="form-group bigest-wid">
                            <input type="text" class="form-control edit_name" editype="val" name="name" dete="val" id="edit_typename"/>
                        </div>
                    </li>
                    <li>
                        <span class="title"><span class="required">*</span>助记符</span>
                        <div class="form-group bigest-wid">
                            <input type="text" class="form-control edit_mnemonic" editype="val" name="mnemonic" id="men_mnemonic"/>
                        </div>
                    </li>
                    <li>
                        <span class="title">目录描述 </span>
                        <div class="form-group bigest-wid">
                            <textarea class="form-control edit_explains" name="explains" editype="val" id="men_explains"></textarea>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
        <div class="wrapper animated fadeInUp menuright">
            <div class="search-wrapper input-groups">
                <form class="query-pan" id="query-pan">
                    <ul>
                        <li style="display: none;">
                            <span class="title">消费区域</span>
                            <div class="form-group big-wid">
                                <input type="text" class="form-control" id="qur_consArea" disabled="disabled" />
                                <input type="hidden" name="consArea" value="0"/>
                            </div>
                        </li>
                        <li>
                            <span class="title">编号/名称/助记符</span>
                            <div class="form-group big-wid">
                                <input type="text" class="form-control" name="conditions" />
                            </div>
                        </li>
                        <li>
                            <span class="title">客位类型</span>
                            <div class="form-group big-wid">
                            	<select class="form-control edit_seatType" id="seatType" name="seatType" editype="val" dete="val">
                                    <option selected="selected"></option>
										<c:forEach items="${list}" var="map" varStatus="status"> 
											<c:forEach items="${map.KWLX}" var="o" varStatus="index">
											 	<option  value="${o.id}" >${o.cName}</option>
											 </c:forEach>
										</c:forEach>
                                </select>
                            </div>
                        </li>
                        <li>
                            <span class="title">创建时间</span>
                            <div class="form-group big-wid">
                            	<input type="text" class="form-control edit_crStartTime" id="crStartTime" name="crStartTime" editype="val" placeholder="开始时间" readonly>
                            </div>
                        </li>
                        <li>
                        	<span class="title">至</span>
                            <div class="form-group big-wid">
                            	<input type="text" class="form-control edit_crEndTime" id="crEndTime" name="crEndTime" editype="val" placeholder="结束时间" readonly>
                            </div>
                        </li>
                    </ul>
                    <div class="search-btns">
                        <button class="btn btn-primary">查询</button>
                    </div>
                </form> 
            </div>
            <div class="btn-toolbar">
            	<c:if test="${single == '1' }">
	                <span class="add"><i class="fa fa-file-o"></i>新增</span>
	                <span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
	                <span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
	                <span id="trash" class="graybtn"><i class="fa fa-trash-o"></i>回收站</span>
                </c:if>
                <span id="managercard" class="bluebtn"><i class="fa fa-edit"></i>绑定台卡</span>
                <span id="managerseat" class="redbtn"><i class="fa fa-edit"></i>客位分类</span>
                <span id="yxequxs" class="greenbtn"><i class="fa fa-edit"></i>易小二品项区域显示</span>
                <span id="seatQRCode" class="redbtn"><i class="fa fa-edit"></i>生成二维码</span>
                <span id="refresh" class="graybtn"><i class="fa fa-refresh"></i>刷新</span>
            </div>
            <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
        </div>
    </body>
</html>
