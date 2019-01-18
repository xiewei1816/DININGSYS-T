<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${ctx }/assets/css/font-awesome.min93e3.css" />
    	<link rel="stylesheet" href="${ctx }/assets/css/animate.min.css" />
    	<link rel="stylesheet" href="${ctx }/assets/scripts/jqgrid/ui.jqgridffe4.css" />
    	<link rel="stylesheet" href="${ctx }/assets/css/bootstrap.min14ed.css?v=3.3.6" />
    	<link rel="stylesheet" href="${ctx }/assets/css/chosen.css" />
    	<link rel="stylesheet" href="${ctx }/assets/css/ls_form.css" />
    	<link rel="stylesheet" href="${ctx }/assets/scripts/layer/skin/layer.css" />
    	<link rel="stylesheet" href="${ctx }/assets/scripts/jedate/skin/jedate.css" />
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">供应商名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="supName" />
							</div>
						</li>
					</ul>
					<div class="search-btns">
						<button class="btn btn-primary">查询</button>
					</div>
				</form> 
			</div>
			<div class="btn-toolbar">
				<span class="add"><i class="fa fa-file-o"></i>新增</span>
				<span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
				<span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <div id="editPanel" style="display: none;" title="供应商信息">
	    	<form id="editForm" class="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
						<span class="title"><span class="required">*</span>名称</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入供应商名称" type="text" class="form-control edit_supName" maxlength="32" name="supName" editype="val" dete="val" />
						</div>
					</li>
	    			<li>
	    				<span class="title">联系人</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_contactName" name="contactName" editype="val" maxlength="20"  placeholder="请输入联系人"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>联系电话</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_phone" name="phone" editype="val" dete="val"maxlength="20"  placeholder="请输入联系电话"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">email</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_email" name="email" editype="val"maxlength="32" placeholder="请输入email"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">传真</span>
						<div class="form-group bigest-wid">
							<input type="text"" class="form-control edit_fax" name="fax" editype="val" maxlength="20" placeholder="请输入传真"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">地址</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_address" name="address" editype="val" maxlength="120" placeholder="请输入地址"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">等级</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_level" name="level" editype="val" maxlength="20" placeholder="请输入等级"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">备注</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_remark" name="remark" editype="val" maxlength="200" placeholder="请输入备注"/>
						</div>
	    			</li>
	    		</ul>
	    	</form>
	    </div>
	
	    <script type="text/javascript" src="${ctx }/assets/scripts/jquery-2.1.1.js" ></script>
    	<script type="text/javascript" src="${ctx }/assets/scripts/bootstrap.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/i18n/grid.locale-cnffe4.js"></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/jquery.jqGrid.minffe4.js"></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/chosen.proto.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/chosen.jquery.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/layer/layer.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jedate/jedate.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/yqsh-ls.js" ></script>
	    <script type="text/javascript" src="${ctx }/app/js/DININGSYS/inve/dgSupplier.js"></script>
	</body>
</html>
