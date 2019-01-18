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
    	<link rel="stylesheet" href="${ctx }/assets/css/jquery.Jcrop.css"/>
	</head>
	<body>
		<div class="leftmenus"></div>
		<div class="menus-form">
			<form class="editForm" id="classForm" style="display: none;">
				<ul>
					<li>
	    				<span class="title">上级代码类型</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control has-dis" value="特约商户" disabled="disabled"/>
							<input type="hidden" value="33" name="cparent">
						</div>
	    			</li>
					<li>
	    				<span class="title">代码</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_ccode" editype="val" name="ccode" dete="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_cname" editype="val" name="cname" dete="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">速记码</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_ckeywd" editype="val" name="ckeywd"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">排序值</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_iorder" editype="val" name="iorder"/>
						</div>
	    			</li>
				</ul>
			</form>
		</div>
		<div class="wrapper animated fadeInUp menuright">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">特约商户</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="qur_consArea" disabled="disabled" />
								<input type="hidden" name="businessId" value="0"/>
							</div>
						</li>
						<li>
							<span class="title">方案名称</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" name="conditions" />
							</div>
						</li>
						<li>
							<span class="title">创建时间</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control gjeDate notdefTime" id="crStartTime" name="crStartTime" placeholder="创建开始时间" end="crEndTime" />
							</div>
						</li>
						<li>
							<div class="form-group big-wid">
								<input type="text" class="form-control gjeDate notdefTime" id="crEndTime" name="crEndTime" placeholder="创建结束时间"  start="crStartTime" />
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
							<select class="chosens edit_businessId" name="businessId" editype="val">
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="打折比例">打折比例</span>
						<div class="form-group input-groups">
							<input type="text" class="form-control small-wid edit_discountRate isnumber"
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
	
	    <script type="text/javascript" src="${ctx }/assets/scripts/jquery-2.1.1.js" ></script>
    	<script type="text/javascript" src="${ctx }/assets/scripts/bootstrap.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/i18n/grid.locale-cnffe4.js"></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/jquery.jqGrid.minffe4.js"></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/chosen.proto.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/chosen.jquery.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/layer/layer.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jedate/jedate.min.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/jquery.Jcrop.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/yqsh-ls.js" ></script>
	    <script type="text/javascript" src="${ctx }/assets/scripts/ls-tree.js" ></script>
	    <script>
	    	$(function(){
	    		pageUtil.pageInit({
	    			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
	    			}
	    		});
	    		
	    		getTypeOptions();
	    		
	    		$(".edit_username").blur(function(){
	    			checkName();
	    		});
	    		$(".leftmenus").getLSTree({
	    			url : "${ctx}/DININGSYS/archive/publicCode/getAllDataTreeByPar",  		 				//通过url获取获取
	    			postData : {cparent:33},
	    			nodeIdName : "cgpcid",
	    			nodePidName : "cparent",
	    			nodeTitleName : "cname",
	    			dynamicHei : 40,											 				//动态高度（dynamicHei是被减数为：$(window).height() - dynamicHei）
	    			rootNodeName : "特约商户",									 				//树形结构的标题值
	    			allowAdd : false,
	    			formId : "classForm",										 				//数据编辑的form表单id值
	    			delIdName : "editIds",										 				//数据删除操作传入删除结构的字段名称（默认为nodeIdName的值）
	    			saveUrl : "${ctx}/DININGSYS/archive/publicCode/savePublicCode",   			//编辑功能后台保存数据url地址（新增，修改同一地址，修改保存时会传入修改的id值）
	    			getNodeUrl : "${ctx}/DININGSYS/archive/publicCode/getPublicCodeByIds",		//编辑功能中获取节点完整数据的url地址（点击修改按钮时执行）
	    			delNodeUrl : "${ctx}/DININGSYS/archive/publicCode/deleteDataWithLogic",		//编辑功能中删除节点执行的url地址
	    			refresh : function(){										 				//点击刷新按钮刷新数据之后执行的方法
	    				$("#qur_consArea").val("");
	    				$("#qur_consArea").siblings(":hidden").val(0);
	    				$(".query-pan .search-btns").trigger("click");
	    			},
	    			nodeclick : function(dom,nodeData){							 				//点击树形结构中所有节点执行方法（传入nodeData,该节点的数据）
	    				$("#qur_consArea").val(nodeData.cname);
	    				$("#qur_consArea").siblings(":hidden").val(nodeData.cgpcid);
	    				$(".query-pan .search-btns").trigger("click");
	    			},	
	    			saveBefore : function(data){
	    				//return checkTypeName(data.id);
	    			},
	    			getNodeSuccess : function(data){							 				//编辑功能中获取节点完整数据后执行的方法，给元素赋值等
	    				$(".leftmenus").getUpdingData(data[0],"classForm"); 
	    			},
	    			delSuccess : function(data){								 				//编辑功能中删除节点成功之后执行的方法
	    				getTypeOptions();
	    			},
	    			saveSuccess : function(data){								 				//编辑功能中节点数据保存成功之后执行的方法
	    				getTypeOptions();
	    			},
	    			leftHide : function(){										 				//当点击隐藏按钮执行的方法
	    				$(".menuright").animate({marginLeft:70},300);
	    			},
	    			leftShow : function(){
	    				$(".menuright").animate({marginLeft:230},300);
	    			}
	    		});
	    		
	    		$(".add").click(function(){
	    			pageUtil.addOper({
	    				addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
	    				},
	    				saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
	    					return checkName();
	    				},
	    				saveUrl : "${ctx}/DININGSYS/speciallyBusiness/saveSpeciallyBusiness", 						//添加保存的地址(必要)。
	    				saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
	    					if (resultData.success == 'OK') {
	    						layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
	    						$(".leftmenus").lSTreeRefresh();
							} else {
								layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
							}
	    				}
	    			});
		    	});
	    		
	    		$("#update").click(function(){
	    			pageUtil.updOper({
	    				updBefore : function(){												//修改数据界面之前执行的方法(非必要)。
	    					
	    				},
	    				updUrl : "${ctx}/DININGSYS/speciallyBusiness/getSpeciallyBusinessByID",									//获取修改数据的后台地址(必要)。
	    				updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
	    					console.log(data);
	    					pageUtil.getUpdingData(data.speciallyBusiness);                            	//自动给编辑界面里填值。
	    				},
						saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
							return checkName();
	    				},
	    				saveUrl : "${ctx}/DININGSYS/speciallyBusiness/saveSpeciallyBusiness", 									//添加保存的地址(必要)。
	    				saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
	    					if (resultData.success == 'OK') {
	    						layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
							} else {
								layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
							}
	    				}
	    			});
	    		});
	    		
	    		$("#delb").click(function(){
	    			pageUtil.delOper({
	    				before : function(){												//删除数据界面之前执行的方法(非必要)。
	    				},
	    				url : "${ctx}/DININGSYS/speciallyBusiness/deleteSpeciallyBusiness",										//删除数据提交的后台地址(必要)。
	    				success : function(){												//删除数据成功之后执行的方法(非必要)。
	    					
	    				}
	    			});
	    		});
	    		
	    		$("#" + pageUtil.tabId).getJqGrid({
	    			url : "${ctx}/DININGSYS/speciallyBusiness/getPageList",
	    			colM : "name,discountRate,isEditRate,business.cname",
	    			queryForm : "queryForm",
	    			colNames : "打折方案名称,优惠比例,前台是否可以修改优惠比例,特约商户",
	    			colWid : {"name":140,"isEditRate":60,"explain":180},
	    			formatter : {
	    				"isEditRate" : function(v){
	    					if (v == '0') {
	    						return '<font color="green">否</font>';
	    					} else {
	    						return '<font color="red">是</font>';
	    					}
	    				}
	    			},
	    			loadComplete : function() {
	    			}
	    		});
	    	});
	    	function getTypeOptions(){
	    		$(".edit_businessId").empty();
	    		$.getJSON("${ctx}/DININGSYS/archive/publicCode/getAllDataTreeByPar",{cparent:33},function(data){
	    		    $.each(data, function(i,e){
	    		     	var option = $("<option value='"+e.cgpcid+"'>"+e.cname+"</option>");
	    		     	$(".edit_businessId").append(option);
	    		    });
	    		    $(".edit_businessId").trigger('chosen:updated');
	    		});
	    	}
	    	
	    	function checkName(){
	    		var istrue = true;
	    		var id = $(".edit_id").val();
    			$.ajax({
    				url : "${ctx}/DININGSYS/speciallyBusiness/checkSpeciallyBusinessByName",
    				data : {"id": id == "" ? 0 : id,checkName:$("#edit_named").val()},
    				dataType : "json",
    				type : "post",
    				async : false,
    				success : function(d){
    					if(d.state == 2){
    						layer.alert('你提交的打折方案名称已经存在，请重新填写！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
    						istrue = false;
    					}
    				}
    			});
    			return istrue;
	    	}
	    	function checkTypeName(id){
	    		var istrue = true;
    			$.ajax({
    				url : "${ctx}/DININGSYS/speciallyBusiness/checkConsumptionAreaByName",
    				data : {"id": id == undefined ? 0 : id,checkName:$("#edit_typename").val()},
    				dataType : "json",
    				type : "post",
    				async : false,
    				success : function(d){
    					if(d.state == 2){
    						layer.alert('你提交的结算方式类型的名称已经存在，请重新填写！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
    						istrue = false;
    					}
    				}
    			});
    			return istrue;
	    	}
	    </script>
	</body>
</html>
