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
	
	
    var treeEdit;
    var treeView;
    var treeCallBack;
//	if(single == '1'){
//		treeView =  {
//				addHoverDom: addHoverDom,
//				removeHoverDom: removeHoverDom,
//				selectedMulti: false
//			};
//		treeCallBack = {
//				onAsyncSuccess: initTree,
//				beforeEditName: beforeEditName,
//				beforeRemove: beforeRemove,
//				onClick: onClick
//		    };
//		 treeEdit = {
//				enable: true,
//				showRemoveBtn: showRemoveBtn
//			};
//			
//	} else {
		treeView =  {
				selectedMulti: false
		};
		treeCallBack = {
				onAsyncSuccess: initTree,
				onClick: onClick
		};
		treeEdit = {
				enable: false
		};
//	}
	
	var setting = {
		view: treeView,
		edit:treeEdit,
		data: {
			simpleData: {
				enable: true
			}
		},
	    async : {
	    enable : true,//是否异步加载
        url : 'DININGSYS/pos/getTreeNodes',//加载数据的url
        autoParam : [ "id","childCount"],//异步发送请求时,表示自动传指定属性的参数值
		type: "get",
        dataFilter : function(treeId, parentNode, childNodes) {//这里由于本人设置的节点属性跟zTree不一致所以进行了过滤     
            for (var i = 0, l = childNodes.length; i < l; i++) {
                if (childNodes[i].childCount > 0 ) {
                    //当主节点  下面还有父节点时自动将isParent=true
                    //这样点击父节点zTree会自动再加载其子节点的数据
	                    childNodes[i].isParent = true;
	                }
	                else
	                {
	                	childNodes[i].isParent = false;
	                }
	            }
	            return childNodes;
	        }
	    },
	    callback : {
			onAsyncSuccess: initTree,
			onClick: onClick
	    }
	};
	
    $.fn.zTree.init($("#tree"), setting);
    $(".extra").outerHeight($(window).innerHeight());
    resize();
	
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
				
				//判断消费区域必选
				var area = $(".edit_consumerAreas").val();
				if(area == ""){
					layer.tips('管理的消费区域不能为空!', $("#consumerAreas") , {guide: 1, tipsMore:true, time: 2000}); 
					return false;
				}
				
				return checkName();
			},
			saveUrl : "DININGSYS/pos/savePos", 							//添加保存的地址(必要)。
			saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
					//$(".leftmenus").lSTreeRefresh();
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
			updUrl : "DININGSYS/pos/getPosByID",									//获取修改数据的后台地址(必要)。
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
				//return checkName();
			},
			saveUrl : "DININGSYS/pos/savePos", 									//添加保存的地址(必要)。
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
			url : "DININGSYS/pos/deletePosTrash",						//删除数据提交的后台地址(必要)。
			success : function(data){												//删除数据成功之后执行的方法(非必要)。
				if (data.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/pos/getPageList?delFlag=0",
		colM : "number,name,mnemonic,ipArea,consumerAreas,organization,createTime",
		queryForm : "queryForm",
		colNames : "编号,POS名称,助记符,所属ip地址,管理的消费区域,所属组织结构,创建时间",
		colWid : {"name":140},
		loadComplete : function(a) {
			//$("#" + pageUtil.tabId).setRowData(1,null,{display: 'none'});
		},
	});
	
	/* ******************************  POS档案回收站  **************************** */	
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: 'POS档案【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/pos/trash',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});
	});
});

function initTree() {
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	expandNodes(treeObj.getNodes());
}


function resize(){
    $(window).resize(function() { 
        $(".extra").outerHeight($(window).innerHeight());
   }) 
}

function onClick(event, treeId, treeNode) {
	if(treeNode.id != '-1')
	{
		$("#qur_consArea").val(treeNode.name);
		$("#qur_consArea").siblings(":hidden").val(treeNode.id);	
	}
	else
	{
		$("#qur_consArea").val("");
		$("#qur_consArea").siblings(":hidden").val("");	
	}
	$(".query-pan .search-btns").trigger("click");
	return true;
}
function expandNodes(nodes) {
	if (!nodes) return;
	var zTree = $.fn.zTree.getZTreeObj("tree");
	for (var i=0, l=nodes.length; i<l; i++) {
		zTree.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync) {
			expandNodes(nodes[i].children);
		}
	}
}

function checkName(){
	var istrue = true;
	var id = $(".edit_id").val();
	$.ajax({
		url : "DININGSYS/pos/checkPosByName",
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

/**
 * 验证IP
 * @param id
 */
function chkMaxLength(id){
	var no = parseInt(id.slice(-1)) + 1;
	var val = $("#"+id).val();
	if(parseInt(val) > 255){
		layer.tips('IP值不能大于255!', $("#"+id) , {guide: 1, tipsMore:true, time: 2000});
	}
	var len = val.length;
	var nIP = "IP"+ no;
	if(len == 3){
		if(nIP != "IP5"){
			$("#"+nIP).focus();
		}else{
			$("#IP4").blur();
		}
	}else if(len > 3){
		$("#"+id).val(val.substring(0,3));
	}
}
