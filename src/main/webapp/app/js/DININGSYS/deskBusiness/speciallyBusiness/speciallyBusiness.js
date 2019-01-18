$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	getSele();
	
	$(".edit_username").blur(function(){
		checkName();
	});
	 var setting = {
			 	view: {
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					selectedMulti: false
				},
				edit:{
					enable: true,
					showRemoveBtn: showRemoveBtn,
				},
				data: {
					simpleData: {
						enable: true
					}
				},
			    async : {
			    enable : true,//是否异步加载
		        url : 'DININGSYS/speciallyBusiness/getTreeNodes',//加载数据的url
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
					beforeEditName: beforeEditName,
					beforeRemove: beforeRemove,
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
				return checkName();
			},
			saveUrl : "DININGSYS/speciallyBusiness/saveSpeciallyBusiness", 						//添加保存的地址(必要)。
			saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
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
			updUrl : "DININGSYS/speciallyBusiness/getSpeciallyBusinessByID",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				console.log(data);
				pageUtil.getUpdingData(data.speciallyBusiness);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
				//return checkName();
			},
			saveUrl : "DININGSYS/speciallyBusiness/saveSpeciallyBusiness", 									//添加保存的地址(必要)。
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
			url : "DININGSYS/speciallyBusiness/deleteSpeciallyBusiness",										//删除数据提交的后台地址(必要)。
			success : function(){												//删除数据成功之后执行的方法(非必要)。
				
			}
		});
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/speciallyBusiness/getPageList",
		colM : "name,discountRate,isEditRate,business.cname",
		queryForm : "queryForm",
		colNames : "打折方案名称,优惠比例,前台是否可以修改优惠比例,特约商户",
		colWid : {},
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

function initTree() {
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	expandNodes(treeObj.getNodes());
}

function onClick(event, treeId, treeNode) {
	if(treeNode.id != '32')
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

function beforeEditName(treeId, treeNode) {
	var postData = JSON.parse("{\"id\":\""+treeNode.id+"\"}");
	$.ajax({
		url : 'DININGSYS/archive/dpc/getDpcById',
		data : postData,
		dataType : "json",
		type : "post",
		error : function(request) {
			layer.alert('你提交的数据有误，请检查后重新提交！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
		},
		success : function(d){
			getFormData(d,'classForm');
			
			layer.open({
	    		title : "修改" + treeNode.name,
	    		type : 1,
	    		area : ["550px","420px"],
	    		content : $("#classForm"),
	    		zIndex : 1050,
	    		btn : ["保存","取消"],
	    		yes : function(index, layero){
	    			$.ajax({
						url : 'DININGSYS/archive/dpc/saveDpc',
						data : $('#classForm').serialize(),
						dataType : "json",
						type : "post",
						success : function(d){
							layer.close(index);
							refreshMyTree();
						}
	    			})
	    		},
	    		btn2 : function(index, layero){
	    			layer.close(index);
	    		}
	    	});
		}
	});
	return false;
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

/**
 * 刷新树结构
 */
function refreshMyTree(){
	var myTree = $.fn.zTree.getZTreeObj("tree");
	myTree.reAsyncChildNodes(null, "refresh");
}

function addHoverDom(treeId, treeNode) {
	//清空表单数据
	$('#c-id').val('');
	$('#classForm')[0].reset();
	
	if(treeNode.id == '32')
	{
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			$("#cParent").val('32');
			layer.open({
	    		title : "增加特约商户",
	    		type : 1,
	    		area : ["550px","420px"],
	    		content : $("#classForm"),
	    		zIndex : 1050,
	    		btn : ["保存","取消"],
	    		yes : function(index, layero){
	    			$.ajax({
						url : 'DININGSYS/archive/dpc/saveDpc',
						data : $('#classForm').serialize(),
						dataType : "json",
						type : "post",
						success : function(d){
							layer.close(index);
							refreshMyTree();
							getSele();
						}
	    			})
	    		},
	    		btn2 : function(index, layero){
	    			layer.close(index);
	    		}
	    	});
			return false;
		});	
	}
}
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
}
function showRemoveBtn(treeId, treeNode) {
	if(treeNode.id=='32')
	{
		return false;
	}
	else
	{
		return true;
	}
}

function beforeRemove(treeId, treeNode) {
	var postData = JSON.parse("{\"id\":\""+treeNode.id+"\"}");
	layer.confirm('确认删除次节点数据?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
                type:'POST',
                url:'DININGSYS/archive/dpc/deleteDpc',
                data:postData,
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
			            layer.close(index);
			            refreshMyTree();
			            getSele();
                    }
                }
            })

        });
	return false;
}


function resize(){
    $(window).resize(function() { 
        $(".extra").outerHeight($(window).innerHeight());
   }) 
}

function getTypeOptions(){
	$(".edit_businessId").empty();
	$.getJSON("DININGSYS/archive/publicCode/getAllDataTreeByPar",{cparent:33},function(data){
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
		url : "DININGSYS/speciallyBusiness/checkSpeciallyBusinessByName",
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
		url : "DININGSYS/speciallyBusiness/checkConsumptionAreaByName",
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

function getSele()
{
	$.ajax({
		url : "DININGSYS/speciallyBusiness/getTYPbc",
		type : "post",
		dataType : "json",
		async : false,
		success : function(d){
			$.each(d, function(i,e){
		     	var option = $("<option value='"+e.id+"'>"+e.cName+"</option>");
		     	$(".edit_businessId").append(option);
		    });
		    $(".edit_businessId").trigger('chosen:updated');
		}
	});
}

function getFormData(data,form)
{
	$.each(data.dpc,function(i,e){
		var inputs = $("#"+form+" .edit_" + i);
		$.each(inputs,function(j,m){
			var type = $(m).attr("editype");
			switch(type){
				case "val" : {
					$(m).val(e);
					if($(m).hasClass("chosens")){
						$(m).trigger('chosen:updated');
					}
				}
				break;
				case "radio" : {
					if($(m).val() == e){
						$(m).trigger("click");
					}
                }
                    break;
				case "check" : {
					if($(m).val() == e){
						$(m).prop("checked",true);
					}
                }
                    break;
				case "checks" : {
					if(("," + e + ",").indexOf("," + $(m).val() + ",") != -1){
						$(m).prop("checked",true);
					}
                }
                    break;
			}
		});
	});
}
