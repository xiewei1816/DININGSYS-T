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
		<div class="wrapper animated fadeInUp menuright">
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						<li>
							<span class="title">消费区域</span>
							<div class="form-group big-wid">
								<input type="text" class="form-control" id="qur_consArea" disabled="disabled" />
								<input type="hidden" name="consumerAreas" value=""/>
							</div>
						</li>
						<li>
							<span class="title">编号/名称/助记符</span>
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
	    <div id="editPanel" style="display: none;" title="POS信息">
	    	<form id="editForm" class="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
						<span class="title" title="POS编号"><span class="required">*</span>POS编号</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入POS编号" type="text" class="form-control edit_number" name="number" defaultVal="${number}" editype="val" dete="val" />
						</div>
					</li>
	    			<li>
	    				<span class="title" title="POS名称"><span class="required">*</span>POS名称</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_name" id="edit_named" name="name" editype="val" dete="val" placeholder="请输入POS名称"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="助记符">助记符</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_mnemonic" name="mnemonic" editype="val" placeholder="请输入助记符"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="所属组织结构">所属组织结构</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_organization" name="organization" editype="val" placeholder="请输入所属组织结构"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="管理的消费区域"><span class="required">*</span>管理的消费区域</span>
						<div class="form-group bigest-wid areas">
							<input type="hidden" class="edit_consumerAreas" name="consumerAreas" editype="val">
							<c:forEach items="${areas }" var="area">
								<label class='lscheck'><b></b><input type='checkbox'  editype="check" value="${area.id }"><i></i>${area.name }</label>
							</c:forEach>
						</div>
	    			</li>
	    			<li>
	    				<span class="title" title="IP地址">IP地址</span>
						<div class="input-groups bigest-wid ipinputs">
							<input type="hidden" class="edit_ipArea" name="ipArea" editype="val">
							<input type="text" class="form-control smaller-wid" maxlength="3" defaultVal="000" editype="val"/>
							<input type="text" class="form-control smaller-wid" maxlength="3" defaultVal="000" editype="val"/>
							<input type="text" class="form-control smaller-wid" maxlength="3" defaultVal="000" editype="val"/>
							<input type="text" class="form-control smaller-wid" maxlength="3" defaultVal="000" editype="val"/>
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
	    		
	    		$(".ipinputs :text").blur(function(){
	    			var val = $(this).val();
	    			if(isNaN(Number(val))){
	    				$(this).val("000");
	    			}else if(Number(val) > 255){
	    				$(this).val("255");
	    			}
	    		});
	    		
	    		$(".edit_username").blur(function(){
	    			checkName();
	    		});
	    		$(".leftmenus").getLSTree({
	    			url : "${ctx}/DININGSYS/pos/getTreeList",  		 							//通过url获取获取
	    			dynamicHei : 10,											 				//动态高度（dynamicHei是被减数为：$(window).height() - dynamicHei）
	    			isEdit : false,											 				
	    			isCheck : false,
	    			rootNodeName : "消费区域",									 				//树形结构的标题值
	    			refresh : function(){										 				//点击刷新按钮刷新数据之后执行的方法
	    				$("#qur_consArea").val("").siblings(":hidden").val("");
	    				$(".query-pan .search-btns").trigger("click");
	    			},
	    			nodeclick : function(dom,nodeData){							 				//点击树形结构中所有节点执行方法（传入nodeData,该节点的数据）
	    				$("#qur_consArea").val(nodeData.name).siblings(":hidden").val(nodeData.id);
	    				$(".query-pan .search-btns").trigger("click");
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
	    					$(".edit_ipArea").val($(".ipinputs :text").map(function(){
	    						return $(this).val();
	    					}).get().join("."));
	    					$(".edit_consumerAreas").val($(".areas :checkbox:checked").map(function(){
	    						return $(this).val();
	    					}).get().join(","));
	    					return checkName();
	    				},
	    				saveUrl : "${ctx}/DININGSYS/pos/savePos", 							//添加保存的地址(必要)。
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
	    				updUrl : "${ctx}/DININGSYS/pos/getPosByID",									//获取修改数据的后台地址(必要)。
	    				updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
	    					pageUtil.getUpdingData(data.pos);                            	//自动给编辑界面里填值。
	    					var area = data.pos.consumerAreas;
	    					area = area != null && area != "" ? "," + area + "," : "";
	    					$(".areas :checkbox").each(function(i,e){
	    						if(area.indexOf("," +$(e).val() + ",") != -1){
	    							$(e).prop("checked",true);
	    						}
	    					});
	    					$(".ipinputs :text").each(function(i,e){
	    						$(e).val(data.pos.ipArea.split(".")[$(e).index() - 1]);
	    					});
	    				},
						saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
							$(".edit_ipArea").val($(".ipinputs :text").map(function(){
	    						return $(this).val();
	    					}).get().join("."));
	    					$(".edit_consumerAreas").val($(".areas :checkbox:checked").map(function(){
	    						return $(this).val();
	    					}).get().join(","));
							return checkName();
	    				},
	    				saveUrl : "${ctx}/DININGSYS/pos/savePos", 									//添加保存的地址(必要)。
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
	    				url : "${ctx}/DININGSYS/pos/deletePos",										//删除数据提交的后台地址(必要)。
	    				success : function(){												//删除数据成功之后执行的方法(非必要)。
	    					
	    				}
	    			});
	    		});
	    		
	    		$("#" + pageUtil.tabId).getJqGrid({
	    			url : "${ctx}/DININGSYS/pos/getPageList",
	    			colM : "number,name,mnemonic,ipArea,consumerAreas,organization,createTime",
	    			queryForm : "queryForm",
	    			colNames : "编号,POS名称,助记符,所属ip地址,管理的消费区域,所属组织结构,创建时间",
	    			colWid : {"name":140},
	    			loadComplete : function() {
	    			}
	    		});
	    	});
	    	function checkName(){
	    		var istrue = true;
	    		var id = $(".edit_id").val();
    			$.ajax({
    				url : "${ctx}/DININGSYS/pos/checkPosByName",
    				data : {"id": id == "" ? 0 : id,checkName:$("#edit_named").val()},
    				dataType : "json",
    				type : "post",
    				async : false,
    				success : function(d){
    					if(d.state == 2){
    						layer.alert('你提交的POS名称已经存在，请重新填写！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
    						istrue = false;
    					}
    				}
    			});
    			return istrue;
	    	}
	    </script>
	</body>
</html>
