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
							<span class="title">名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="itemTypeName" />
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
	    <div id="editPanel" style="display: none;" title="商户信息">
		<form id="editForm" class="editForm">
			<input type="hidden" name="id" value="" editype="val" class="edit_id">
			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a href="#home"
					role="tab" data-toggle="tab">基本信息</a></li>
				<li role="presentation"><a href="#profile" role="tab"
					data-toggle="tab">微信配置</a></li>
				<li role="presentation"><a href="#messages" role="tab"
					data-toggle="tab">支付宝配置</a></li>
			</ul>
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active" id="home">
					<ul class="edit-contents animated fadeInUp">
						<li><span class="title"><span class="required">*</span>商户名称</span>
							<div class="form-group biger-wid">
								<input placeholder="请输入商户名称" type="text"
									class="form-control edit_nickName" maxlength="50"
									name="nickName" editype="val"  />
							</div>
						</li>
						<li>
							<span class="title"><span class="required">*</span>所属店面</span>
							<div class="form-group biger-wid">
								<select name="orgId" class="form-control edit_orgId chosens"
							          editype="val">
							   	 	<c:forEach items="${listOrg}" var="ld" varStatus="index">
										<option value="${ld.id}">${ld.orgName}</option>
							    	</c:forEach>
								</select>
							</div>
						</li>
						<li>
							<span class="title">备注</span>
							<div class="form-group bigest-wid">
								<textarea rows="5" cols="" class="form-control edit_remark"
									name="remark" placeholder="请输入备注" editype="val" maxlength="250"></textarea>
							</div>
						</li>
					</ul>
				</div>
				<div role="tabpanel" class="tab-pane" id="profile">
					<ul class="edit-contents animated fadeInUp">
					<li><span class="title">公众号编号</span>
							<div class="form-group biger-wid">
								<input placeholder="请输入微信公众号编号" type="text"
									class="form-control edit_wxAppId" maxlength="120"
									name="wxAppId" editype="val"  />
							</div>
						</li>
						<li><span class="title">公众号秘钥</span>
							<div class="form-group biger-wid">
								<input placeholder="请输入微信应用秘钥" type="text"
									class="form-control edit_wxAppSecret" maxlength="120"
									name="wxAppSecret" editype="val"  />
							</div>
						</li>
						<li><span class="title">公众号api秘钥</span>
							<div class="form-group biger-wid">
								<input placeholder="请输入微信api秘钥" type="text"
									class="form-control edit_wxApiSecretKey" maxlength="64"
									name="wxApiSecretKey" editype="val"  />
							</div>
						</li>
						<li><span class="title">微信商户号</span>
							<div class="form-group biger-wid">
								<input placeholder="请输入微信商户号" type="text"
									class="form-control edit_wxMchId" maxlength="64"
									name="wxMchId" editype="val"  />
							</div>
						</li>
					</ul>
				</div>
				<div role="tabpanel" class="tab-pane" id="messages">
					<ul class="edit-contents animated fadeInUp">
						<li><span class="title">身份标识</span>
							<div class="form-group biger-wid">
								<input placeholder="请输入支付宝合作者身份标识" type="text"
									class="form-control edit_zfbPid" maxlength="32"
									name="zfbPid" editype="val" />
							</div>
						</li>
						<li><span class="title">应用id</span>
							<div class="form-group biger-wid">
								<input placeholder="请输入支付宝支付宝应用id" type="text"
									class="form-control edit_zfbAppid" maxlength="32"
									name="zfbAppid" editype="val" />
							</div>
						</li>
						<li>
							<span class="title">商户私钥</span>
							<div class="form-group bigest-wid">
								<textarea rows="5" cols="" class="form-control edit_zfbPrivateKey"
									name="zfbPrivateKey" placeholder="请输入支付宝商户私钥" editype="val" maxlength="2048">${zfbPrivateKey}</textarea>
							</div>
						</li>
						<li>
							<span class="title">商户公钥</span>
							<div class="form-group bigest-wid">
								<textarea rows="5" cols="" class="form-control edit_zfbPublicKey"
									name="zfbPublicKey" placeholder="请输入备注" editype="val" maxlength="500">${zfbPublicKey}</textarea>
							</div>
						</li>
						<li>
							<span class="title">支付宝公钥</span>
							<div class="form-group bigest-wid">
								<textarea rows="5" cols="" class="form-control edit_zfbAlipayPublicKey"
									name="zfbAlipayPublicKey" placeholder="请输入备注" editype="val" maxlength="500">${zfbAlipayPublicKey}</textarea>
							</div>
						</li>
					</ul>
				</div>
			</div>
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
	    <script type="text/javascript" src="${ctx }/app/js/DININGSYS/pay/dgmerchants.js"></script>
	</body>
</html>
