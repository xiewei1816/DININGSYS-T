$(document).ready(function(){
	pageInit();
	//树形结构加载
	$.fn.zTree.init($("#showItemTree"), setting2);
	
	refreshLabel(); //刷新可选、已选数据条数
});

//树形结构设置
var setting2 = {
	data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: 0
        },
        key: {
            name : "name" //设置显示字段
        }
    },
    async : {
    enable : true,//是否异步加载
    url : 'DININGSYS/archive/itemFile/getAllItemFileType?flag=1',//加载数据的url
    autoParam : [ "id","pId","name"],
    dataFilter : function(treeId, parentNode, childNodes) {
    	for (var i = 0, l = childNodes.length; i < l; i++) {
	    		childNodes[i].open = true;
	        }
            return childNodes;
        }
    },
    callback : {
    	onClick: zTreeOnClick2
    }
};

/**
 * 点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick2(event, treeId, treeNode) {
   /*alert(treeNode.id + ", " +treeNode.pId + ", " + treeNode.name);*/
	var id = treeNode.id;
    var url;
    if(treeNode.id == 0 && treeNode.pId == 0){
    	url = 'DININGSYS/businessMan/itemShowRank/selectDgItemFileNoShowRankList?pxlx=0&isShow=1';
        jQuery("#show-item-table1").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    }else if(treeNode.id > 0 && treeNode.pId == 0){
        url = 'DININGSYS/businessMan/itemShowRank/selectDgItemFileNoShowRankList?pxlx=0&isShow=1&ppdlId='+id+'';
        jQuery("#show-item-table1").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    }else if(treeNode.pId >= 1){
    	url = 'DININGSYS/businessMan/itemShowRank/selectDgItemFileNoShowRankList?pxlx=0&isShow=1&ppxlId='+id+'';
    	jQuery("#show-item-table1").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    }
    refreshLabel(); //刷新可选、已选数据条数
}
/**************************************** 不显示消费品项设置 - 按品项 ****************************************/
function pageInit(){
	$("#show-item-table1").jqGrid({
        url: "DININGSYS/businessMan/itemShowRank/selectDgItemFileNoShowRankList?pxlx=0&isShow=1",
        datatype: "JSON",
        mtype: "GET",
        colNames: ["id","编号","名称","单位","连锁方案价格","说明"],
        colModel: [
            { name: "id",hidden:true},
            { name: "num" },
            { name: "name"},
            { name: "unit"},
            { name: "costPrice"},
            { name: "sm"}
        ],
        rowNum : -1,
    	rownumbers : true,
        gridview: true,
        autoencode: true,
        styleUI : 'Bootstrap',
        ondblClickRow: showItemEdit1,
        loadComplete: function(){ //加载完成（初始加载），回调函数
        	refreshLabel(); //刷新可选、已选数据条数
        }
    });

	$("#show-item-table2").jqGrid({
        url: "DININGSYS/businessMan/itemShowRank/selectDgItemFileList?pxlx=0&isShow=1",
        datatype: "JSON",
        mtype: "GET",
        colNames: ["id","编号","名称","单位","连锁方案价格","说明"],
        colModel: [
            { name: "id",hidden:true},
            { name: "num" },
            { name: "name"},
            { name: "unit"},
            { name: "costPrice"},
            { name: "sm"}
        ],
        rowNum : -1,
    	rownumbers : true,
        gridview: true,
        autoencode: true,
        styleUI : 'Bootstrap',
        ondblClickRow: showItemEdit2,
        loadComplete: function(){ //加载完成（初始加载），回调函数
        	refreshLabel(); //刷新可选、已选数据条数
        }
    });
	
	/**
	 * 全部选择上表1
	 */
	$("#sele-all-1").click(function(){
	    var ids = [];
	    var rowData = $("#show-item-table1").jqGrid("getRowData");
	    jQuery(rowData).each(function(){
	    	jQuery('#show-item-table1').jqGrid('setSelection',this.id); //全选
	    	ids.push(this.id);
	    });
	    $.ajax({
	        type:'POST',
	        url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
	        data:{isShow:1,pxlx:0,ids:ids.toString()},
	        dataType:'JSON',
	        async:false,
	        success:function (data) {
	            if(data.success == 'OK'){
	            	 $("#show-item-table1").trigger("reloadGrid");
	                 $("#show-item-table2").trigger("reloadGrid");
	            }
	        }
	    });
	    refreshLabel(); //刷新可选、已选数据条数
	});

	/**
	 * 全部选择下表2
	 */
	$("#sele-all-2").click(function(){
		var ids = [];
	    var rowData = $("#show-item-table2").jqGrid("getRowData");
	    jQuery(rowData).each(function(){
	    	jQuery('#show-item-table2').jqGrid('setSelection',this.id); //全选
	    	ids.push(this.id);
	    });
	    $.ajax({
	        type:'POST',
	        url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
	        data:{isShow:0,pxlx:0,ids:ids.toString()},
	        dataType:'JSON',
	        async:false,
	        success:function (data) {
	            if(data.success == 'OK'){
	            	 $("#show-item-table1").trigger("reloadGrid");
	                 $("#show-item-table2").trigger("reloadGrid");
	            }
	        }
	    });
	    refreshLabel(); //刷新可选、已选数据条数
	});
}
	
/**
 * 双击编辑上表1
 * @param id
 */
function showItemEdit1(id) {
	$.ajax({
        type:'POST',
        url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
        data:{isShow:1,pxlx:0,ids:id.toString()},
        dataType:'JSON',
        async:false,
        success:function (data) {
            if(data.success == 'OK'){
                $("#show-item-table1").trigger("reloadGrid");
                $("#show-item-table2").trigger("reloadGrid");
                refreshLabel(); //刷新可选、已选数据条数
            }
        }
    })
}

/**
 * 双击编辑下表2
 * @param id
 */
function showItemEdit2(id) {
	$.ajax({
        type:'POST',
        url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
        data:{isShow:0,pxlx:0,ids:id.toString()},
        dataType:'JSON',
        async:false,
        success:function (data) {
            if(data.success == 'OK'){
                $("#show-item-table1").trigger("reloadGrid");
                $("#show-item-table2").trigger("reloadGrid");
                refreshLabel(); //刷新可选、已选数据条数
            }
        }
    })
}

/**
 * 查询文本框onblur事件执行函数
 * @param id
 */
function goSearch(id){
	var param = document.getElementById(id).value.trim(); //获取查询条件
	$("#show-item-table1").jqGrid('setGridParam',{page:1,postData:{"param":param}});
	$("#show-item-table1").trigger("reloadGrid");
	refreshLabel(); //刷新可选、已选数据条数
}

/**
 * 刷新可选、已选的数据条数
 */
function refreshLabel(){
	setTimeout(function () {
        $("#len-1").html($("#show-item-table1").jqGrid('getGridParam','records'));
        $("#len-2").html($("#show-item-table2").jqGrid('getGridParam','records'));
	  }, 100);
}
