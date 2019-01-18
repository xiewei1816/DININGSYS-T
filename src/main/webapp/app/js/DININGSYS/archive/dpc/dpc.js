$(document).ready(function(){
	//树形结构加载
	$.fn.zTree.init($("#myTree"), setting);
	
	//初始化页面控件高宽
	initPage();
	
	//加载jqGrid
	initjqGrid();
});

//树形结构设置
var setting = {
	data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "cParent",
            rootPId: 0
        },
        key: {
            name : "cName" //设置显示字段
        }
    },
    async : {
    enable : true,//是否异步加载
    url : 'DININGSYS/archive/dpc/selectAllDpc?iDelFlg=0',//加载数据的url
    autoParam : [ "id","cParent","cName"],
    dataFilter : function(treeId, parentNode, childNodes) {
        for (var i = 0, l = childNodes.length; i < l; i++) {
            if (childNodes[i].cParent == null ) {
            		childNodes[i].open = true;
                }
            }
            return childNodes;
        }
    },
    callback : {
    	onClick: zTreeOnClick,
    	onAsyncSuccess : initTree
    }
};

/**
 * 点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    /*alert(treeNode.id + ", " +treeNode.cParent + ", " + treeNode.cName);*/
    var url;
    if(treeNode.cParent == 0){
    	//修改、删除禁用设置
    	$("#update").hide();
		$("#updateHide").show();
		$("#delb").hide();
		$("#delbHide").show();
        var url = 'DININGSYS/archive/dpc/getPageList?iDelFlg=0&cParent=1';
        jQuery("#grid-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
    }else{
    	$("#update").show();
		$("#updateHide").hide();
		$("#delb").show();
		$("#delbHide").hide();
    }
    if(treeNode.cParent == 1){
    	var id =  treeNode.id;
    	url = 'DININGSYS/archive/dpc/selectSmallDpc?iDelFlg=0&cParent='+id;
        jQuery("#grid-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
    }
    if(treeNode.cParent > 1){
    	var id =  treeNode.id;
    	var pId = treeNode.cParent;
    	url = 'DININGSYS/archive/dpc/selectSmallDpc?iDelFlg=0&cParent='+pId+'&id='+id;
        jQuery("#grid-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
    }
}
/**
 * 初始化展开树
 */
function initTree(){
//	var treeObj = $.fn.zTree.getZTreeObj("myTree");
//	treeObj.expandAll(true);
}

/**
 * 初始化页面控件高宽
 */
function initPage(){
	var winWidth = $(window).width();
	var winHeight = $(window).height();
	//设置table的宽度
//	$(".jqGrid_wrapper").width(winWidth * 0.8);
	//设置zTree的高度
	var height = $(".btn-toolbar").outerHeight(true);
	$(".menuTree").height(winHeight - height);
}

//初始化部门信息，加载jqGrid
function initjqGrid(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/dpc/getPageList?iDelFlg=0&cParent=1",
		colM : "id,cCode,cName,cKeyWD,cParent",
		queryForm : "queryForm",
		colNames : "ID,代码,名称,速记码,上级代码类型",
		colWid : {id:"0"},
		tabHei : $(window).height() - $(".btn-toolbar").innerHeight() - 40,
		rowNum : -1,
		rownumbers : true,
		formatter : {
		},
		loadComplete : function() {
			$("#" + pageUtil.tabId).hideCol("id");
		}
	});
	
	$("#add").click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var node = treeObj.getSelectedNodes()[0];
		if(node == undefined){
			layer.alert('请选中左侧树形菜单再做操作！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		/*alert(node.id+" - "+node.cCode+" - "+node.cName)*/
		var cParent = node.id;
		var cParentName = node.cName;
        $.post("DININGSYS/archive/dpc/toDpcFormEdit",{cParent:cParent,cParentName:cParentName},function(str){
            var addIndex = layer.open({
                type: 1,
                title:'新增公共代码',
                skin: 'layui-layer-rim',
                area: ['600px', '500px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkForm()){
	                    $.ajax({
	                        type:'POST',
	                        url:'DININGSYS/archive/dpc/saveDpc',
	                        data:$("#myform").serialize(),
	                        dataType:'JSON',
	                        success:function (data) {
	                            if(data.success){
	                                layer.close(addIndex);
	                                $("#grid-table").trigger("reloadGrid");
	                                refreshMyTree(); //刷新树
	                            }
	                        }
	                    })
                	}
                }
            });
        })
    });

    $("#update").click(function(){
        var grid = $("#grid-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length < 1){
            layer.alert('请选中右侧需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        if(selectRow.length > 1){
            layer.alert('请选择右侧一条信息进行修改！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var id = grid.jqGrid('getCell',rowId,'id');
        $.get("DININGSYS/archive/dpc/toDpcFormEdit",{id:id},function (str) {
            var addIndex = layer.open({
                type: 1,
                title:'修改公共代码',
                skin: 'layui-layer-rim',
                area: ['600px', '500px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkForm()){
	                    $.ajax({
	                        type:'POST',
	                        url:'DININGSYS/archive/dpc/saveDpc',
	                        data:$("#myform").serialize(),
	                        dataType:'JSON',
	                        success:function (data) {
	                            if(data.success){
	                                layer.close(addIndex);
	                                $("#grid-table").trigger("reloadGrid");
	                                refreshMyTree(); //刷新树
	                            }
	                        }
	                    })
                	}
                }
            });
        })
    });

	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/archive/dpc/deleteDpcTrash",						//删除数据提交的后台地址(必要)。
			success : function(resultData){												//删除数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
					refreshMyTree(); //刷新树
				} else if(resultData.success == 'isSys'){
					layer.alert('系统自带数据，删除失败！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				} else if(resultData.success == 'hasChild'){
					layer.alert('该节点下存在子节点，删除失败！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});	
				} else {
					layer.alert(resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});

	$("#export").click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("myTree");
		var node = treeObj.getSelectedNodes()[0];
		if(node == undefined){
			layer.alert('请选中左方树形菜单再做操作！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		var cParent = node.id;
		var url = "DININGSYS/archive/dpc/exportXls?iDelFlg=0&cParent="+cParent;
		window.location.href=url;
	});
		
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
   
    /* ******************************  部门信息回收站  **************************** */
	//表单验证
	function checkForm(){
		var flag = true;
		var cName = $("#cName").val();
		if(cName == ""){
			layer.tips('名称不能为空!', $("#cName") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		var cKeyWD = $("#cKeyWD").val();
		if(cKeyWD == ""){
			layer.tips('速记码不能为空!', $("#cKeyWD") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		var cOrder = $("#cOrder").val();
		if(cOrder == ""){
			layer.tips('排序值不能为空!', $("#cOrder") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		if(cName != "" && cKeyWD != ""){
			$.ajax({
                type:'POST',
                url:"DININGSYS/archive/dpc/checkParamIsHaved",
                data:$("#myform").serialize(),
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.cNameCount > 0){
                    	layer.tips('名称已经存在！', '#cName', {guide: 1, tipsMore:true, time: 2000});
                    	flag = false;
                    }
                    if(data.cKeyWDCount > 0){
                    	layer.tips('速记码已经存在！', '#cKeyWD', {guide: 1, tipsMore:true, time: 2000});
                    	flag = false;
                    }
                }
            });
		}
		return flag;
	}
	
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: '公共代码信息【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/dpc/trash',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
				  refreshMyTree(); //刷新树
			  }
		});
	});
	
	/**
	 * 刷新树结构
	 */
	function refreshMyTree(){
		var myTree = $.fn.zTree.getZTreeObj("myTree");
		myTree.reAsyncChildNodes(null, "refresh");
	}
}