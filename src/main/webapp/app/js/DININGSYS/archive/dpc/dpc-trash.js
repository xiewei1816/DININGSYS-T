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
	    url : 'DININGSYS/archive/dpc/selectAllDpc?iDelFlg=1',//加载数据的url
	    autoParam : [ "id","cParent","cName"],
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
/*    alert(treeNode.id + ", " +treeNode.cParent + ", " + treeNode.cName);*/
    var pId = getcParentIdById(treeNode.id);
    var url;
    if(pId == 0){
        var url = 'DININGSYS/archive/dpc/getPageList?iDelFlg=1&cParent=1';
        jQuery("#grid-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
    }
    if(pId == 1){
    	var id =  treeNode.id;
    	url = 'DININGSYS/archive/dpc/selectSmallDpc?iDelFlg=1&cParent='+id;
        jQuery("#grid-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
    }
    if(pId > 1){
    	var id =  treeNode.id;
    	url = 'DININGSYS/archive/dpc/selectSmallDpc?iDelFlg=1&cParent='+pId+'&id='+id;
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
	$(".jqGrid_wrapper").width(winWidth * 0.8);
	//设置zTree的高度
	var height = $(".btn-toolbar").outerHeight(true);
	$(".menuTree").height(winHeight - height);
}

/**
 * 根据id 获取父级id
 * @param id
 * @returns {Number}
 */
function getcParentIdById(id){
	var pId = 0;
	$.ajax({
	    type:'POST',
	    url:"DININGSYS/archive/dpc/getcParentIdById",
	    data:{id:id},
	    dataType:'JSON',
	    async:false,
	    success:function (data) {
	        if(data.success == 'OK'){
	        	pId = data.dpc.cParent;
	        }
	    }
	});
	return pId;
}

//初始化部门信息，加载jqGrid
function initjqGrid(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/dpc/getPageList?iDelFlg=1&cParent=1",
		colM : "id,cCode,cName,cParent",
		queryForm : "queryForm",
		colNames : "ID,代码,名称,上级代码类型",
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
	
	$("#reply").click(function(){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请选择右侧需要还原的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		layer.confirm('请注意,你确定还原数据吗？', {
		  icon : 0,
		  btn : ['确定','取消'] //按钮
		  }, function(index){
			  jQuery.ajax({
					url : 'DININGSYS/archive/dpc/replyDpc',
					data : {"editIds":rowids + ""},
					type : "POST",
					dataType:"json",
					error : function(request) {
						layer.alert('你提交的数据有错误！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
					},
					success : function(data) {
						if(data.success == 'OK'){
							layer.close(index);
							$("#" + pageUtil.tabId).trigger("reloadGrid");
							refreshMyTree(); //刷新树
						}
					}
				});
		  }, function(){
			  
		  });
	});
	
	$("#delete").click(function(){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请选择右侧需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		layer.confirm('请注意,该功能删除的数据不可逆.你确定吗?', {
		  icon : 0,
		  btn : ['确定','取消'] //按钮
		  }, function(index){
			  jQuery.ajax({
					url : 'DININGSYS/archive/dpc/deleteDpc',
					data : {"editIds":rowids + ""},
					type : "POST",
					dataType:"json",
					error : function(request) {
						layer.alert('你提交的数据有错误！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
					},
					success : function(data) {
						if(data.success == 'OK'){
							layer.close(index);
							$("#" + pageUtil.tabId).trigger("reloadGrid");
							refreshMyTree(); //刷新树
						} else if(data.success == 'hasChild'){
							layer.alert('该节点下存在子节点，删除失败！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});	
						}
					}
				});
		  }, function(){
			  
		  });
	});
	   
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
	});
	
	/**
	 * 刷新树结构
	 */
	function refreshMyTree(){
		var myTree = $.fn.zTree.getZTreeObj("myTree");
		myTree.reAsyncChildNodes(null, "refresh");
	}
}