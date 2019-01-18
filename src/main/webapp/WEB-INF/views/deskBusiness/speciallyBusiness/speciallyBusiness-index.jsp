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
		<jsp:include page="../../head.jsp"/>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
		<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
		<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
		<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
		<script type="text/javascript" src="app/js/DININGSYS/deskBusiness/speciallyBusiness/speciallyBusiness.js"></script>
    	<style type="text/css">
			.extra {width: 200px; border: 1px solid #f6a828; height:600px; float:left;overflow: auto;}
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
			.child {margin-left: 210px}
		</style>
		<script>
            jQuery(document).ready(function () {
                var start = {
                    format: 'YYYY-MM-DD',
                    festival:true,
                    ishmsVal:false,
                    choosefun: function(elem,datas){
                        end.minDate = datas; //开始日选好后，重置结束日的最小日期
                    }
                };
                var end = {
                    format: 'YYYY-MM-DD',
                    festival:true,
                    choosefun: function(elem,datas){
                        start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
                    }
                };
                $('#crStartTime').jeDate(start);
                $('#crEndTime').jeDate(end);
            })

		</script>
	</head>
	<body>
		<div id="menuTree" class="extra" >
            <ul id="tree" class="ztree"></ul>
         </div>
		<div class="menus-form" style="margin-left: 210px">
			<form class="editForm" id="classForm" style="display: none;">
				<ul>
					<li>
	    				<span class="title">上级代码类型</span>
						<div class="form-group bigest-wid">
						    <input type="text" class="form-control has-dis" value="特约商户" name="cParentName" disabled="disabled"/>
							<input type="hidden" class="form-control edit_cParent" editype="val" name="cParent" id="cParent">
							<input type="hidden" class="form-control edit_id" editype="val" name="id" id="c-id">
						</div>
	    			</li>
					<li>
	    				<span class="title">代码</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_cCode" editype="val" name="cCode" dete="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_cName" editype="val" name="cName" dete="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">速记码</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_cKeyWD" editype="val" name="cKeyWD"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">排序值</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_cOrder" editype="val" name="cOrder"/>
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
							<span class="title">特约商户</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="qur_consArea" disabled="disabled" />
								<input type="hidden" name="businessId" value="0"/>
							</div>
						</li>
						<li>
							<span class="title">方案名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_checkName" id="checkName" name="checkName" placeholder="方案名称"/>
							</div>
						</li>
						<li>
							<span class="title">创建时间</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crStartTime" id="crStartTime" name="crStartTime" placeholder="开始时间" readonly
    								>
							</div>
						</li>
						<li>
							<div class="form-group big-wid">
								<input type="text" class="form-control edit_crEndTime" id="crEndTime" name="crEndTime" placeholder="结束时间" readonly
    								>
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
	    <div id="editPanel" style="display: none;" title="特约商户打折方案">
	    	<form id="editForm" class="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
	    				<span class="title" title="方案名称"><span class="required">*</span>方案名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_name" id="edit_named" name="name" editype="val" dete="val" placeholder="请输入方案名称"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="特约商户"><span class="required">*</span>特约商户</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_businessId" name="businessId" editype="val">
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="打折比例">打折比例</span>
						<div class="form-group input-groups">
							<input type="text" class="form-control small-wid edit_discountRate isnumber" style="width: 260px;"
							 name="discountRate" editype="val" placeholder="请输入打折比例"/><span class="symbol">%</span>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="前台是否可以修改优惠比例">前台是否可以修改优惠比例</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isEditRate"
							 name="isEditRate" editype="radio" /><i></i><span></span>不可以</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isEditRate"
							 name="isEditRate" editype="radio" /><i></i><span></span>可以</label>
						</div>
	    			</li>
	    		</ul>
	    	</form>
	    </div>
	</body>
</html>
