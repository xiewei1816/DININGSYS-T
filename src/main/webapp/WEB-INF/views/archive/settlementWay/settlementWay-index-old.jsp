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
	    				<span class="title">类型名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_name"
							 editype="val" name="name" dete="val" id="edit_typename"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="是否含有‘卷面值’">是否含有‘卷面值’</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_hasRollFace"
							 name="hasRollFace" editype="radio" /><i></i><span></span>是</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_hasRollFace"
							 name="hasRollFace" editype="radio" /><i></i><span></span>否</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="是否含有‘计入实际收入比例’">是否含有‘计入实际收入比例’</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_hasActualIncomeRatio"
							 name="hasActualIncomeRatio" editype="radio" /><i></i><span></span>是</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_hasActualIncomeRatio"
							 name="hasActualIncomeRatio" editype="radio" /><i></i><span></span>否</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="是否含有‘不计入实际收入比例’">是否含有‘不计入实际收入比例’</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_hasNotActualIncomeRatio"
							 name="hasNotActualIncomeRatio" editype="radio" /><i></i><span></span>是</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_hasNotActualIncomeRatio"
							 name="hasNotActualIncomeRatio" editype="radio" /><i></i><span></span>否</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="是否含有‘本位币’">是否含有‘本位币’</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_hasCurrency"
							 name="hasCurrency" editype="radio" /><i></i><span></span>是</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_hasCurrency"
							 name="hasCurrency" editype="radio" /><i></i><span></span>否</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="是否含有‘允许找零’">是否含有‘允许找零’</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_hasAllowChange"
							 name="hasAllowChange" editype="radio" /><i></i><span></span>是</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_hasAllowChange"
							 name="hasAllowChange" editype="radio" /><i></i><span></span>否</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">类型说明 </span>
						<div class="form-group bigest-wid">
							<textarea class="form-control edit_explains" name="explains" editype="val"></textarea>
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
							<span class="title">结算方式类型</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="qur_consArea" disabled="disabled" />
								<input type="hidden" name="type" value="0"/>
							</div>
						</li>
						<li>
							<span class="title">编号/名称</span>
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
				<!-- <span class="csbtn"><i class="fa fa-laptop"></i>查看</span> -->
				<!-- <span class="redbtn"><i class="fa fa-upload"></i>导出</span>
				<span class="royalbtn"><i class="fa fa-download"></i>导入</span> -->
			</div>
	        <div class="jqGrid_wrapper">
                <table id="grid-table"></table>
                <div id="grid-pager"></div>
            </div>
	    </div>
	    <div id="editPanel" style="display: none;" title="结算方式">
	    	<form id="editForm" class="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
						<span class="title" title="结算编号"><span class="required">*</span>结算编号</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入结算编号" type="text" class="form-control edit_number" name="number" editype="val" dete="val" />
						</div>
					</li>
	    			<li>
	    				<span class="title" title="结算名称"><span class="required">*</span>结算名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_name" id="edit_named" name="name" editype="val" dete="val" placeholder="请输入结算名称"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="结算方式类型"><span class="required">*</span>结算方式类型</span>
						<div class="form-group bigest-wid">
							<select class="chosens edit_type" name="type" editype="val">
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="汇率"><span class="required">*</span>汇率</span>
						<div class="form-group bigest-wid">
							<input type="number" class="form-control edit_exchangeRate isnumber" dete="val"
							 name="exchangeRate" editype="val" placeholder="请输入汇率" defaultVal="1.00"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="快捷键">快捷键</span>
						<div class="form-group bigest-wid">
							<select class="chosens edit_shortcutKey" name="shortcutKey" editype="val">
								<option value="A">A</option><option value="B">B</option><option value="C">C</option>
								<option value="D">D</option><option value="E">E</option><option value="F">F</option>
								<option value="G">G</option><option value="H">H</option><option value="I">I</option>
								<option value="J">J</option><option value="K">K</option><option value="L">L</option>
								<option value="M">M</option><option value="N">N</option><option value="O">O</option>
								<option value="P">P</option><option value="Q">Q</option><option value="R">R</option>
								<option value="S">S</option><option value="T">T</option><option value="U">U</option>
								<option value="V">V</option><option value="W">W</option><option value="X">X</option>
								<option value="Y">Y</option><option value="Z">Z</option>
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="巻面值">巻面值</span>
						<div class="form-group bigest-wid">
							<input type="number" class="form-control edit_rollFaceValue isnumber"
							 name="rollFaceValue" editype="val" placeholder="请输入巻面值" defaultVal="0" notdis="hasRollFace"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="计入实际收入比例">计入实际收入比例</span>
						<div class="form-group input-groups">
							<input type="number" class="form-control edit_actualIncomeRatio small-wid isnumber"
							 name="actualIncomeRatio" editype="val" placeholder="请输入计入实际收入比例" defaultVal="0" notdis="hasActualIncomeRatio"/>
							 <span class="symbol">%</span>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="不计入实际收入比例">不计入实际收入比例</span>
						<div class="form-group input-groups">
							<input type="number" class="form-control edit_notActualIncomeRatio small-wid isnumber"
							 name="notActualIncomeRatio" editype="val" placeholder="请输入不计入实际收入比例" defaultVal="0" notdis="hasNotActualIncomeRatio"/>
							 <span class="symbol">%</span>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="停用">停用</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isDisabled"
							 name="isDisabled" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isDisabled"
							 name="isDisabled" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="常用">常用</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isCommon"
							 name="isCommon" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isCommon"
							 name="isCommon" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="系统保留">系统保留</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isKeepSystem"
							 name="isKeepSystem" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isKeepSystem"
							 name="isKeepSystem" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="结算时必须输入附加信息">结算时必须输入附加信息</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isMustInformation"
							 name="isMustInformation" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isMustInformation"
							 name="isMustInformation" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="单独使用，不能与其他计算方式同时使用">单独使用，不能与其他计算方式同时使用</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isAloneUse"
							 name="isAloneUse" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isAloneUse"
							 name="isAloneUse" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="本位币（仅针对现金类结算方式）">本位币（仅针对现金类结算方式）</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isCurrency"
							 name="isCurrency" editype="radio" notdis="hasCurrency"/><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isCurrency"
							 name="isCurrency" editype="radio" notdis="hasCurrency"/><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title" title="允许找零">允许找零</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isAllowChange"
							 name="isAllowChange" editype="radio" notdis="hasAllowChange"/><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isAllowChange"
							 name="isAllowChange" editype="radio" notdis="hasAllowChange"/><i></i><span></span>是</label>
						</div>
	    			</li>
	    			
	    			<li class="bigger">
	    				<span class="title" title="说明">说明</span>
						<div class="form-group bigest-wid">
							<textarea class="form-control edit_explains" name="explains" editype="val"></textarea>
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
	    				$(".edit_type").trigger("change");
	    			}
	    		});
	    		getTypeOptions();
	    		
	    		$(".edit_type").change(function(){
	    			var sdata = $(this).find("option[value='"+$(this).val()+"']").data().sdata;
	    			
	    			$.each($("#editForm [notdis]"),function(i,e){
	    				var name = $(e).attr("notdis");
	    				var type = $(e).attr("editype");
	    				var val = sdata[name];
	    				if(val == 1){
	    					if(type == "val"){
	    						$(e).val($(e).attr("defaultval")).prop("disabled",true);
	    					}
	    					if(type == "radio"){
	    						if($(e).val() == 0){
	    							$(e).closest("label").trigger("click");
	    						}
	    						$(e).prop("disabled",true);
	    					}
	    				}else{
	    					$(e).prop("disabled",false);
	    				}
	    			});
	    		});
	    		
	    		$(".edit_username").blur(function(){
	    			checkName();
	    		});
	    		$(".leftmenus").getLSTree({
	    			url : "${ctx}/DININGSYS/settlementWay/getTreeList",  		 				//通过url获取获取
	    			dynamicHei : 40,											 				//动态高度（dynamicHei是被减数为：$(window).height() - dynamicHei）
	    			allowOperSeries : [1],									 	 				//限制可以编辑的层级（数组结构）
	    			allowAdd : false,											 				//是否允许整个树形结构中的添加功能(默认为true)
	    			rootNodeName : "结算方式类型",									 				//树形结构的标题值
	    			formId : "classForm",										 				//数据编辑的form表单id值
	    			delIdName : "editIds",										 				//数据删除操作传入删除结构的字段名称（默认为nodeIdName的值）
	    			formWid : "950px",
	    			allowClickSeries : [1],									     				//限制树形结构中可以点击的节点的层级（数组结构，与nodeclick事件相关）
	    			saveUrl : "${ctx}/DININGSYS/settlementWayType/saveDgSettlementWayType",   		//编辑功能后台保存数据url地址（新增，修改同一地址，修改保存时会传入修改的id值）
	    			getNodeUrl : "${ctx}/DININGSYS/settlementWayType/getDgSettlementWayTypeByID",		//编辑功能中获取节点完整数据的url地址（点击修改按钮时执行）
	    			delNodeUrl : "${ctx}/DININGSYS/settlementWayType/deleteDgSettlementWayType",		//编辑功能中删除节点执行的url地址
	    			refresh : function(){										 				//点击刷新按钮刷新数据之后执行的方法
	    				$("#qur_consArea").val("").siblings(":hidden").val(0);
	    				$(".query-pan .search-btns").trigger("click");
	    			},
	    			nodeclick : function(dom,nodeData){							 				//点击树形结构中所有节点执行方法（传入nodeData,该节点的数据）
	    				$("#qur_consArea").val(nodeData.name).siblings(":hidden").val(nodeData.id);
	    				$(".query-pan .search-btns").trigger("click");
	    			},	
	    			saveBefore : function(data){
	    				return checkTypeName(data.id);
	    			},
	    			getNodeSuccess : function(data){							 				//编辑功能中获取节点完整数据后执行的方法，给元素赋值等
	    				$(".leftmenus").getUpdingData(data.settlementWayType,"classForm"); 
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
	    				saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
	    					$("#editForm [notdis]").prop("disabled",false);
	    					return checkName();
	    				},
	    				saveUrl : "${ctx}/DININGSYS/settlementWay/saveSettlementWay", 						//添加保存的地址(必要)。
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
	    				updUrl : "${ctx}/DININGSYS/settlementWay/getSettlementWayByID",									//获取修改数据的后台地址(必要)。
	    				updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
	    					pageUtil.getUpdingData(data.settlementWay);                            	//自动给编辑界面里填值。
	    				},
						saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
							$("#editForm [notdis]").prop("disabled",false);
							return checkName();
	    				},
	    				saveUrl : "${ctx}/DININGSYS/settlementWay/saveSettlementWay", 									//添加保存的地址(必要)。
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
	    				url : "${ctx}/DININGSYS/settlementWay/deleteSettlementWay",										//删除数据提交的后台地址(必要)。
	    				success : function(){												//删除数据成功之后执行的方法(非必要)。
	    					
	    				}
	    			});
	    		});
	    		
	    		$("#" + pageUtil.tabId).getJqGrid({
	    			url : "${ctx}/DININGSYS/settlementWay/getPageList",
	    			colM : "number,name,dwType.name,exchangeRate,isCommon,shortcutKey,isDisabled"
	    				+",isCurrency,isAloneUse,isKeepSystem,isMustInformation,rollFaceValue"
	    				+",isAllowChange,actualIncomeRatio,notActualIncomeRatio",
	    			queryForm : "queryForm",
	    			colNames : "编号,结算方式名称,结算方式类型,汇率,常用,快捷键,停用,本位币,单独使用"
	    					+",系统保留,输入附加信息,巻面值,允许找零,计入实际收入比例,不计入实际收入比例",
	    			colWid : {"name":140,"all_name":140,"explain":180},
	    			formatter : {
	    				"isCommon" : function(v){
	    					if (v == '0') {
	    						return '<font color="green">否</font>';
	    					} else {
	    						return '<font color="red">是</font>';
	    					}
	    				},
	    				"isDisabled" : function(v){
	    					if (v == '0') {
	    						return '<font color="green">否</font>';
	    					} else {
	    						return '<font color="red">是</font>';
	    					}
	    				},
	    				"isCurrency" : function(v){
	    					if (v == '0') {
	    						return '<font color="green">否</font>';
	    					} else {
	    						return '<font color="red">是</font>';
	    					}
	    				},
	    				"isAloneUse" : function(v){
	    					if (v == '0') {
	    						return '<font color="green">否</font>';
	    					} else {
	    						return '<font color="red">是</font>';
	    					}
	    				},
	    				"isKeepSystem" : function(v){
	    					if (v == '0') {
	    						return '<font color="green">否</font>';
	    					} else {
	    						return '<font color="red">是</font>';
	    					}
	    				},
	    				"isMustInformation" : function(v){
	    					if (v == '0') {
	    						return '<font color="green">否</font>';
	    					} else {
	    						return '<font color="red">是</font>';
	    					}
	    				},
	    				"isAllowChange" : function(v){
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
	    		$(".edit_type").empty();
	    		$.getJSON("${ctx}/DININGSYS/settlementWayType/getAllList",function(data){
	    		    $.each(data.list, function(i,e){
	    		     	var option = $("<option value='"+e.id+"'>"+e.name+"</option>");
	    		     	$(".edit_type").append(option);
	    		     	option.data({sdata:e});
	    		    });
	    		    $(".edit_type").trigger('chosen:updated');
	    		});
	    	}
	    	
	    	function checkName(){
	    		var istrue = true;
	    		var id = $(".edit_id").val();
    			$.ajax({
    				url : "${ctx}/DININGSYS/settlementWay/checkSettlementWayByName",
    				data : {"id": id == "" ? 0 : id,checkName:$("#edit_named").val()},
    				dataType : "json",
    				type : "post",
    				async : false,
    				success : function(d){
    					if(d.state == 2){
    						layer.alert('你提交的结算方式的名称已经存在，请重新填写！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
    						istrue = false;
    					}
    				}
    			});
    			return istrue;
	    	}
	    	function checkTypeName(id){
	    		var istrue = true;
    			$.ajax({
    				url : "${ctx}/DININGSYS/settlementWayType/checkDgSettlementWayTypeByName",
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
