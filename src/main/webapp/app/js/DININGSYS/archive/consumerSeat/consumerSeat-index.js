$(function () {
    pageUtil.pageInit({
        initFormVals: function () {  											//编辑界面的初始化界面里的数据方法。
        }
    });
    var treeEdit;
    var treeView;
    var treeCallBack;
	if(single == '1'){
		treeView =  {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			};
		treeCallBack = {
				onAsyncSuccess: initTree,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				onClick: onClick
		    };
		 treeEdit = {
				enable: true,
				showRemoveBtn: showRemoveBtn
			};
			
	} else {
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
	}
	getSele();

    $(".edit_username").blur(function () {
        checkName();
    });
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
	        url : 'DININGSYS/consumerSeat/getTreeNodes',//加载数据的url
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
		    callback : treeCallBack
	};
    $.fn.zTree.init($("#tree"), setting);
    $(".extra").outerHeight($(window).innerHeight());
    resize();

    $(".add").click(function () {
        $.get('DININGSYS/consumerSeat/addOrUpdate?id=0', function (dataStr) {
            var w = $(document.body).width() * 0.9 + 'px';
            var h = $(document.body).height() * 0.9 + 'px';
            var infoView = layer.open({
                type: 1,
                title: "添加",
                skin: 'layui-layer-rim',
                area: [w, h],
                content: dataStr,
                btn: ['保存', '关闭'],
                yes: function () {
                    if (!checkFromVals("myfrom")) {
                        return;
                    }
                    if (!isLegalAllowNumber()) {
                        return;
                    }
                    var param = $("#myfrom").serialize();
                    $.ajax({
                        type: 'POST',
                        url: 'DININGSYS/consumerSeat/saveConsumerSeat',
                        dataType: 'JSON',
                        data: param,
                        success: function (data) {
                            if (data.success == 'OK' ) {
                                layer.close(infoView);
                                $("#" + pageUtil.tabId).trigger("reloadGrid");
                            }else if(data.success == "false"){
                               layer.alert(data.error,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                            }
                        }
                    })
                }
            });
        });
    });

    $("#update").click(function () {
        var table = $("#grid-table");
        var rowId = table.getGridParam("selrow");
        var selectRow = table.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请在右侧表格中选择一条！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        $.get('DININGSYS/consumerSeat/addOrUpdate?id='+rowId, function (dataStr) {
            var w = $(document.body).width() * 0.9 + 'px';
            var h = $(document.body).height() * 0.9 + 'px';
            var infoView = layer.open({
                type: 1,
                title: "编辑数据",
                skin: 'layui-layer-rim',
                area: [w, h],
                content: dataStr,
                btn: ['保存', '关闭'],
                beforeSend: function () {
                    pageUtil.loadInd = layer.msg("数据获取中,请等待...", {icon: 16});
                },
                yes: function () {
                    if (!checkFromVals("myfrom")) {
                        return;
                    }
                    if (!isLegalAllowNumber()) {
                        return;
                    }
                    var param = $("#myfrom").serialize();
                	$.ajax({
                		type: 'POST',
                		url: 'DININGSYS/consumerSeat/saveConsumerSeat',
                		dataType: 'JSON',
                		data: param,
                		success: function (data) {
                			layer.close(pageUtil.loadInd);
                			if (data.success == 'OK' ) {
                				layer.close(infoView);
                				$("#" + pageUtil.tabId).trigger("reloadGrid");
                			}else if(data.success == "false"){
                				layer.alert(data.error,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                			}
                		}
                	});
                }
            });
        });
    });

    $("#delb").click(function () {
        pageUtil.delOper({
            before: function () {												//删除数据界面之前执行的方法(非必要)。
            },
            url: "DININGSYS/consumerSeat/deleteConsumerSeat", //删除数据提交的后台地址(必要)。
            success: function () {										//删除数据成功之后执行的方法(非必要)。
            	
            }
        });
    });

    $("#" + pageUtil.tabId).getJqGrid({
        url: "DININGSYS/consumerSeat/getPageList",
        colM: "number,name,seatType,minAllowNumber,maxAllowNumber,allowNumber,insetRetentionRoom"
                + ",disabledSeat,area.name,allName,explains",
        queryForm: "queryForm",
        colNames: "编号,客位名称,客位类型,最小容纳人数,最大容纳人数,容纳人数,内部留房,是否停用,所属消费区域,全名称,说明",
        colWid: {"name": 140, "all_name": 140, "explain": 180},
        formatter: {
        	"insetRetentionRoom": function (v) {
                if (v == '0') {
                    return '<font color="red">否</font>';
                } else {
                    return '<font color="green">是</font>';
                }
            },
            "disabledSeat": function (v) {
                if (v == '0') {
                    return '<font color="red">否</font>';
                } else {
                    return '<font color="green">是</font>';
                }
            }
        },
        loadComplete: function () {
        }
    });
    
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });


	$("#trash").click(function(){
	        $.get("DININGSYS/consumerSeat/trash",function(str){
	            var addIndex = layer.open({
	                type: 1,
	                title:'客位管理(回收站)',
	                skin: 'layui-layer-rim',
	                area: ['850px', '80%'],
	                content: str
	            });
	        });
	  });
	  
	  

	/* ******************************  台卡管理  **************************** */
	$("#managercard").click(function(){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请先选择桌位',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}

		if(rowids.length > 1){
			layer.alert('只能选择一个桌位',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		var rowId = $("#" + pageUtil.tabId).jqGrid('getGridParam','selrow');

		layer.open({
			type: 2,
			title: '台卡管理',
			shadeClose: true,
			shade: 0.3,
			area: ['80%', '90%'],
			content: 'DININGSYS/consumerSeat/getCardByConsumerSeat?id='+rowId
		});
	});
	
	
	/**桌位二维码生存**/
	$("#seatQRCode").click(function(){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请先选择桌位',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}

		if(rowids.length > 1){
			layer.alert('只能选择一个桌位',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		
		var rowId = $("#" + pageUtil.tabId).jqGrid('getGridParam','selrow');

		layer.open({
			type: 2,
			title: '二维码',
			shadeClose: true,
			shade: 0.3,
			area: ['450px', '500px'],
			content: 'DININGSYS/consumerSeat/toQrCode?id='+rowId
		});
	});
	
	/* ******************************  客位分类  **************************** */
	$("#managerseat").click(function(){
		layer.open({
			type: 2,
			title: '客位分类',
			shadeClose: true,
			shade: 0.3,
			area: ['80%', '90%'],
			content: 'DININGSYS/dgAllowNumber/index',
			end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});
	});
	
	$("#yxequxs").click(function(){
		layer.open({
			type: 2,
			title: '易小二区域显示',
			shadeClose: true,
			shade: 0.3,
			area: ['500px', '90%'],
			content: 'DININGSYS/yxe/yxeConsumerItem',
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

function beforeEditName(treeId, treeNode) {
	var postData = JSON.parse("{\"id\":\""+treeNode.id+"\"}");
	$.ajax({
		url : 'DININGSYS/consumptionArea/getConsumptionAreaByID',
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
	    		area : ["560px","420px"],
	    		content : $("#classForm"),
	    		zIndex : 1050,
	    		btn : ["保存","取消"],
	    		yes : function(index, layero){
	    			if(checkForm()){
		    			$.ajax({
							url : 'DININGSYS/consumptionArea/saveConsumptionArea',
							data : $('#classForm').serialize(),
							dataType : "json",
							type : "post",
							success : function(d){
								layer.close(index);
								refreshMyTree();
							}
		    			})
	    			}
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
	if(treeNode.id == '-1')
	{
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			$("#c-id").val('');
			layer.open({
	    		title : "增加消费区域",
	    		type : 1,
	    		area : ["560px","420px"],
	    		content : $("#classForm"),
	    		zIndex : 1050,
	    		btn : ["保存","取消"],
	    		yes : function(index, layero){
	    			if(checkForm()){
		    			$.ajax({
							url : 'DININGSYS/consumptionArea/saveConsumptionArea',
							data : $('#classForm').serialize(),
							dataType : "json",
							type : "post",
							success : function(d){
								layer.close(index);
								refreshMyTree();
								getSele();
							}
		    			})
	    			}
	    		},
	    		btn2 : function(index, layero){
	    			layer.close(index);
	    		}
	    	});
			return false;
		});	
	}
}
//表单验证
function checkForm(){
	var flag = true;
	var menNumber = $("#men_number").val();
	if(menNumber == ""){
		layer.tips('编号不能为空!', $("#men_number") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var editTypename = $("#edit_typename").val();
	if(editTypename == ""){
		layer.tips('名称不能为空!', $("#edit_typename") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var menMnemonic = $("#men_mnemonic").val();
	if(menMnemonic == ""){
		layer.tips('助记符不能为空!', $("#men_mnemonic") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	return flag;
}

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
}
function showRemoveBtn(treeId, treeNode) {
	if(treeNode.id=='-1')
	{
		return false;
	}
	else
	{
		return true;
	}
}

function beforeRemove(treeId, treeNode) {
	layer.confirm('确认删除此节点数据?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
                type:'POST',
                url:'DININGSYS/consumptionArea/deleteConsumptionArea',
                data:{id:treeNode.id},
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
			            layer.close(index);
			            refreshMyTree();
			            getSele();
                    }else{
                    	layer.msg(data.errorMsg,{time:1000});
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


function getSele()
{
	$(".edit_consArea").empty();
	$.ajax({
		url : "DININGSYS/consumerSeat/getAllType",
		type : "post",
		dataType : "json",
		async : false,
		success : function(d){
			$.each(d, function(i,e){
		     	var option = $("<option value='"+e.id+"'>"+e.cName+"</option>");
		     	$(".edit_consArea").append(option);
		    });
		    $(".edit_consArea").trigger('chosen:updated');
		}
	});
}

function getTypeOptions() {
    $(".edit_consArea").empty();
    $.getJSON("DININGSYS/consumptionArea/getAllList", function (data) {
        $.each(data.list, function (i, e) {
            var option = $("<option value='" + e.id + "'>" + e.name + "</option>");
            $(".edit_consArea").append(option);
        });
        $(".edit_consArea").trigger('chosen:updated');
    });
}

function checkName() {
    var istrue = true;
    var id = $(".edit_id").val();
    $.ajax({
        url: "DININGSYS/consumerSeat/checkConsumerSeatByName",
        data: {"id": id == "" ? 0 : id, checkName: $("#edit_named").val()},
        dataType: "json",
        type: "post",
        async: false,
        success: function (d) {
            if (d.state == 2) {
                layer.alert('你提交的客位名称已经存在，请重新填写！', {title: '提示', icon: 0, skin: 'layer-ext-moon'});
                istrue = false;
            }
        }
    });
    return istrue;
}
function checkTypeName(id) {
    var istrue = true;
    $.ajax({
        url: "DININGSYS/consumptionArea/checkConsumptionAreaByName",
        data: {"id": id == undefined ? 0 : id, checkName: $("#edit_typename").val()},
        dataType: "json",
        type: "post",
        async: false,
        success: function (d) {
            if (d.state == 2) {
                layer.alert('你提交的结算方式类型的名称已经存在，请重新填写！', {title: '提示', icon: 0, skin: 'layer-ext-moon'});
                istrue = false;
            }
        }
    });
    return istrue;
}

function getFormData(data,form)
{
	$.each(data.consumptionArea,function(i,e){
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