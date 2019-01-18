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
            pIdKey: "pId",
            rootPId: -1
        },
        key: {
            name : "name" //设置显示字段
        }
    },
    async : {
    enable : true,//是否异步加载
    url : 'DININGSYS/yqshapi/queryPrint/getTeamBySeatNameList',//加载数据的url
    autoParam : [ "id","pId","name"],
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
    /*alert(treeNode.id + ", " +treeNode.pId + ", " + treeNode.name);*/
    var url;
    if(treeNode.pId == -1){
        url = 'DININGSYS/yqshapi/queryPrint/selectTeamList';
        jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    }
    if(treeNode.pId == 0){
    	var teamMembers = treeNode.name;
        url = 'DININGSYS/yqshapi/queryPrint/selectTeamList?teamMembers='+teamMembers;
        jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    }
    if(treeNode.pId > 0){
    	var owNum = treeNode.owNum;
    	var teamMembers = treeNode.pId;
    	var seatId = treeNode.id;
        url = 'DININGSYS/yqshapi/queryPrint/selectTeamList?seatId='+seatId+'&teamMembers='+teamMembers+'&owNum='+owNum;
        jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
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
    $("#menuTree").width(250);
    $("#menuTree").height($(window).height()-100);
}

//初始化部门信息，加载jqGrid
function initjqGrid(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#grid-table").jqGrid({
		url: "DININGSYS/yqshapi/queryPrint/selectTeamList",
        datatype: "JSON",
        mtype: "GET",
        colNames: ["id","团队","成员","营业流水","人数","加入团队","消费金额","开单时间","状态","关账","加入团队/转账时间"],
        colModel: [
           { name: "id",hidden:true},
           { name: "teamMembers"},
           { name: "seatName",width:100},
           { name: "owNum",width:250},
           { name: "peopleCount",width:100},
           { name: "joinTeamNotes",width:300},
           { name: "subtotal",width:100},
           { name: "firstTime"},
           { name: "owState",hidden:true},
           { name: "closeBill",formatter:customFmatter},
           { name: "joinTeamTime"}
        ],
        rowNum:-1,
        gridview: true,
        autoencode: true,
        multiselect: true,
        styleUI : 'Bootstrap',
        onCellSelect : function(rowId) { //单击事件
			var grid = $("#grid-table");
			var owState = grid.jqGrid('getCell',rowId,'owState');
			if(owState == 4 || owState == 5){
				$("#exitteam").hide();
				$("#exitteamHide").show();
				$("#repeal").show();
				$("#repealHide").hide();
			}else{
				$("#exitteam").show();
				$("#exitteamHide").hide();
				$("#repeal").hide();
				$("#repealHide").show();
			}
		}
	});

    $("#grid-table").jqGrid("setGridWidth",$(window).width() - $("#menuTree").width()-20,true);
    $("#grid-table").jqGrid("setGridHeight",$(window).height() - 160 ,true);

	function customFmatter(cellvalue, options, rowObject){  
		if(cellvalue == '-1'){
			return '√';
		}else{
			return '';
		}

}
    $("#exitteam").click(function(){
        var grid = $("#grid-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        var owNum = grid.jqGrid('getCell',rowId,'owNum');
        if(selectRow.length < 1){
            layer.alert('请选择需要退出团队的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        if(selectRow.length > 1){
            layer.alert('请选择一条信息进行退出团队！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //判断营业流水是否能进行退出团队操作。
        //营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账
        var owState = grid.jqGrid('getCell',rowId,'owState');
        if(owState == 1 || owState == 3 || owState == 4 || owState == 5){
        }else{
        	layer.alert('该营业流水【"'+owNum+'"】已结算。',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        $.ajax({
            type:'POST',
            url:'DININGSYS/yqshapi/queryPrint/updWaterJoinTeamNotes?owNum='+owNum,
            dataType:'JSON',
            success:function (data) {
                if(data.msg == 'success'){
                	layer.alert('退出团队成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                	refreshMyTree(); //刷新树结构
                    $("#grid-table").trigger("reloadGrid");
                }else{
                    layer.alert( data.msg,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
                }
            }
        })
    });
    
    $("#repeal").click(function(){
        var grid = $("#grid-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        var owNum = grid.jqGrid('getCell',rowId,'owNum');
        if(selectRow.length < 1){
            layer.alert('请选择需要撤销转账的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        if(selectRow.length > 1){
            layer.alert('请选择一条信息进行撤销转账！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //判断营业流水是否能进行撤销转账操作。
        //营业流水状态1:初始化状态、2已经结算、3埋单、4代表该营业流水已转账但未结算、5代表该营业流水已经转账并且已经埋单、6代表该营业流水已经转账且已经结算、7代表该营业流水挂S账、8手工锁定、9结算锁定-1为关账
        var owState = grid.jqGrid('getCell',rowId,'owState');
        if(owState == 2 || owState == 6){
            layer.alert('该营业流水【"'+owNum+'"】已结算。',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        $.ajax({
            type:'POST',
            url:'DININGSYS/yqshapi/queryPrint/updWaterRepealTeamNotes?owNum='+owNum,
            dataType:'JSON',
            success:function (data) {
                if(data.msg == 'success'){
                	layer.alert('撤销转账成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                	refreshMyTree(); //刷新树结构
                    $("#grid-table").trigger("reloadGrid");
                }else{
                	layer.alert( '撤销转账失败！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
                }
            }
        })
    });
		
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
		refreshMyTree(); //刷新树结构
    });

	/**
	 * 刷新树结构
	 */
	function refreshMyTree(){
		var myTree = $.fn.zTree.getZTreeObj("myTree");
		myTree.reAsyncChildNodes(null, "refresh");
	}
}