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
            pIdKey: "depDepartment",
            rootPId: 0
        },
        key: {
            name : "depName" //设置显示字段
        }
    },
    async : {
    enable : true,//是否异步加载
    url : 'DININGSYS/archive/dep/selectAllDep?isDel=0',//加载数据的url
    autoParam : [ "id","depDepartment","depName"],
    dataFilter : function(treeId, parentNode, childNodes) {
        for (var i = 0, l = childNodes.length; i < l; i++) {
            if (childNodes[i].counter > 0 ) {
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
    /*alert(treeNode.id + ", " +treeNode.depDepartment + ", " + treeNode.depName);*/
    var url;
    if(treeNode.id == 0){
        url = 'DININGSYS/archive/dep/getPageList?isDel=0&depDepartment=0';
        jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    }else if(treeNode.depDepartment == 0){
    	var id =  treeNode.id;
    	url = 'DININGSYS/archive/dep/selectSmallDep?isDel=0&depId='+id;
    	jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    }else if(treeNode.id > 1){
    	var id =  treeNode.id;
    	var depId = treeNode.depDepartment;
    	url = 'DININGSYS/archive/dep/selectSmallDep?isDel=0&depId='+depId+'&id='+id;
        jQuery("#grid-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
    }
}
/**
 * 初始化展开树
 */
function initTree(){
	var treeObj = $.fn.zTree.getZTreeObj("myTree");
	treeObj.expandAll(true);
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
		url : "DININGSYS/archive/dep/getPageList?isDel=0&depDepartment=1",
		colM : "id,depCode,depName,depSjm,depDepartment,depOrganization,useType",
		queryForm : "queryForm",
		colNames : "ID,部门代码,部门名称,速记码,上级部门,所属机构,使用类型",
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
		/*alert(node.id+" - "+node.cCode+" - "+node.depName);*/
        $.post("DININGSYS/archive/dep/toDepFormEdit",{depDepartment:node.id,depDepartmentName:node.depName},function(str){
            var addIndex = layer.open({
                type: 1,
                title:'新增部门信息',
                skin: 'layui-layer-rim',
                area: ['600px', '500px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkFrom()){
	                    $.ajax({
	                        type:'POST',
	                        url:'DININGSYS/archive/dep/saveDep',
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
            layer.alert('请选择右侧需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        if(selectRow.length > 1){
            layer.alert('请选择右侧一条信息进行修改！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var id = grid.jqGrid('getCell',rowId,'id');
        $.get("DININGSYS/archive/dep/toDepFormEdit",{id:id},function (str) {
            var addIndex = layer.open({
                type: 1,
                title:'修改部门信息',
                skin: 'layui-layer-rim',
                area: ['600px', '500px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkFrom()){
	                    $.ajax({
	                        type:'POST',
	                        url:'DININGSYS/archive/dep/saveDep',
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
    
	/**
	 * 表单验证
	 */
	function checkFrom(){
		var flag = true;
		var id = $("#hideId").val();
		var depName = $("#depName").val().trim();
		var depSjm = $("#depSjm").val().trim();
		var depOrganization = $("#depOrganizationSel").val();
		if(depName != "" && depSjm != "" || typeof(depOrganization) != "undefined"){
			$.ajax({
                type:'POST',
                url:"DININGSYS/archive/dep/checkParamIsHaved",
                data:{id:id,depOrganization:depOrganization,depName:depName,depSjm:depSjm},
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.depOrganizationCount > 0){
                    	layer.tips('该组织机构已经选择过了！', '#depOrganizationSel');
                    	flag = false;
                    }
                    if(data.depNameCount > 0){
                    	layer.tips('部门名称已经存在！', '#depName', {guide: 1, tipsMore:true, time: 2000});
                    	flag = false;
                    }
                    if(data.depSjmCount > 0){
                    	layer.tips('速记码已经存在！', '#depSjm', {guide: 1, tipsMore:true, time: 2000});
                    	flag = false;
                    }
                }
            })
		}
		if(depSjm == ""){
			layer.tips('速记码不能为空！', '#depSjm', {guide: 1, tipsMore:true, time: 2000});
			flag = false;
		}
		if(depName == ""){
			layer.tips('部门名称不能为空！', '#depName', {guide: 1, tipsMore:true, time: 2000});
			flag = false;
		}
		return flag;
	}
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/archive/dep/deleteDepTrash",						//删除数据提交的后台地址(必要)。
			success : function(resultData){												//删除数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
					refreshMyTree(); //刷新树结构
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
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
		var depDepartment = node.id;
		var url = "DININGSYS/archive/dep/exportXls?isDel=0&depDepartment="+depDepartment;
		window.location.href=url;
	});
		
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
   
    /* ******************************  部门信息回收站  **************************** */
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: '部门信息【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/dep/trash',
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