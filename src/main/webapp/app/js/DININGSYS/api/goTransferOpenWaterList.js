$(function(){
	pageUtil.pageInit({
		initFormVals : function(){
		}
	});
	
	/**************************************** 转入 ****************************************/	
	$("#grid-table-into").jqGrid({
        url: "DININGSYS/yqshapi/queryPrint/selectIntoOrOutOpenWaterList?joinTeamNotes=1",
        datatype: "JSON",
        mtype: "GET",
        colNames: ["id","客位","营业流水","开单时间","开单POS","人数","服务员","单据","整单备注","团队成员","结算流水"],
        colModel: [
           { name: "id",hidden:true},
           { name: "seatName"},
           { name: "owNum"},
           { name: "firstTime"},
           { name: "openPos"},
           { name: "peopleCount",hidden:true},
           { name: "waiter",hidden:true},
           { name: "documents",hidden:true},
           { name: "owNotes",hidden:true},
           { name: "teamMembers",hidden:true},
           { name: "cwNum",hidden:true}
        ],
        height: 500,
        width:960 * 0.45 * 0.98,
        rowNum : -1,
        gridview: true,
        autoencode: true,
        styleUI : 'Bootstrap',
        onSelectRow:function(rowId){
        	var rowDatas = $("#grid-table-into").jqGrid('getRowData', rowId);
        	var owNum = rowDatas["owNum"];
        	var openPos = rowDatas["openPos"];
        	var firstTime = rowDatas["firstTime"];
        	var peopleCount = rowDatas["peopleCount"];
        	var waiter = rowDatas["waiter"];
        	var documents = rowDatas["documents"];
        	var owNotes = rowDatas["owNotes"];
        	var teamMembers = rowDatas["teamMembers"];
        	var cwNum = rowDatas["cwNum"];
        	
        	$("#owNumTitle").text(owNum);
        	$("#owNumTab").text(owNum);
        	
        	$("#openPos").text(openPos);
        	$("#firstTime").text(firstTime);
        	$("#peopleCount").text(peopleCount);
        	$("#waiter").text(waiter);
        	$("#documents").text(documents);
        	$("#owNotes").text(owNotes);
        	$("#teamMembers").text(teamMembers);
        	$("#cwNum").text(cwNum);
        	
        	/**
        	 * 加载营业流水下品项
        	 */
        	getFileItemsByOwNum(owNum);
        },
        loadComplete:function(data){
        	if(data != ""){
        		//初始化第一行选中
	        	var rowId = data[0].id;
	        	
	        	$("#grid-table-into").setSelection(rowId);
	        	var rowDatas = jQuery("#grid-table-into").getRowData(rowId);
	        	var owNum = rowDatas["owNum"];
	        	var openPos = rowDatas["openPos"];
	        	var firstTime = rowDatas["firstTime"];
	        	var peopleCount = rowDatas["peopleCount"];
	        	var waiter = rowDatas["waiter"];
	        	var documents = rowDatas["documents"];
	        	var owNotes = rowDatas["owNotes"];
	        	var teamMembers = rowDatas["teamMembers"];
	        	var cwNum = rowDatas["cwNum"];
	        	
	        	$("#owNumTitle").text(owNum);
	        	$("#owNumTab").text(owNum);
	        	
	        	$("#openPos").text(openPos);
	        	$("#firstTime").text(firstTime);
	        	$("#peopleCount").text(peopleCount);
	        	$("#waiter").text(waiter);
	        	$("#documents").text(documents);
	        	$("#owNotes").text(owNotes);
	        	$("#teamMembers").text(teamMembers);
	        	$("#cwNum").text(cwNum);
	        	
	        	/**
	        	 * 加载营业流水下品项
	        	 */
	        	getFileItemsByOwNum(owNum);
    		}
    	}
    });

	
	/**************************************** 转出 ****************************************/
	$("#grid-table-out").jqGrid({
        url: "DININGSYS/yqshapi/queryPrint/selectIntoOrOutOpenWaterList?joinTeamNotes=2",
        datatype: "JSON",
        mtype: "GET",
        colNames: ["id","客位","营业流水","开单时间","开单POS","人数","服务员","单据","整单备注","团队成员","结算流水"],
        colModel: [
           { name: "id",hidden:true},
           { name: "seatName"},
           { name: "owNum"},
           { name: "firstTime"},
           { name: "openPos"},
           { name: "peopleCount",hidden:true},
           { name: "waiter",hidden:true},
           { name: "documents",hidden:true},
           { name: "owNotes",hidden:true},
           { name: "teamMembers",hidden:true},
           { name: "cwNum",hidden:true}
        ],
        height: 500,
        width:960 * 0.45 * 0.98,
        rowNum : -1,
        gridview: true,
        autoencode: true,
        styleUI : 'Bootstrap',
        onSelectRow:function(rowId){   
        	var rowDatas = $("#grid-table-out").jqGrid('getRowData', rowId);   
        	var owNum = rowDatas["owNum"];
        	var openPos = rowDatas["openPos"];
        	var firstTime = rowDatas["firstTime"];
        	var peopleCount = rowDatas["peopleCount"];
        	var waiter = rowDatas["waiter"];
        	var documents = rowDatas["documents"];
        	var owNotes = rowDatas["owNotes"];
        	var teamMembers = rowDatas["teamMembers"];
        	var cwNum = rowDatas["cwNum"];
        	
        	$("#owNumTitle").text(owNum);
        	$("#owNumTab").text(owNum);
        	
        	$("#openPos").text(openPos);
        	$("#firstTime").text(firstTime);
        	$("#peopleCount").text(peopleCount);
        	$("#waiter").text(waiter);
        	$("#documents").text(documents);
        	$("#owNotes").text(owNotes);
        	$("#teamMembers").text(teamMembers);
        	$("#cwNum").text(cwNum);
        	
        	/**
        	 * 加载营业流水下品项
        	 */
        	getFileItemsByOwNum(owNum);
        }
    });
	
	/**
	 * 刷新表格
	 */
	$("#refresh").click(function(){
		$("#grid-table-into").trigger("reloadGrid");
		$("#grid-table-out").trigger("reloadGrid");
    });
	
	/**
	 * 查看结账单
	 */
	$("#goClearingWater").click(function(){
		var cwNum = $("#cwNum").text();
		if(cwNum == "" || cwNum == null){
			return;
		}
		layer.open({
			  type: 2,
			  title: '已结账单',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['720px', '532px'],
			  content: 'DININGSYS/yqshapi/queryPrint/goClearingWaterList?cwNum='+cwNum,
			  end:function(){
				  $("#show-item-table").trigger("reloadGrid");
			  }
		});
	});
});

/**
 * 单选刷新转账流水品项信息
 * @param owNum
 */
function getFileItemsByOwNum(owNum){
	/**************************************** 品项 ****************************************/
	$("#grid-table-item").jqGrid({
		url: "DININGSYS/yqshapi/queryPrint/selectIntoOrOutItemFileList?owNum="+owNum,
        datatype: "JSON",
        mtype: "GET",
        colNames: ["id","品项编号","品项名称","单位","数量","价格","小计","注"],
        colModel: [
           { name: "id",hidden:true},
           { name: "num"},
           { name: "name"},
           { name: "unit"},
           { name: "item_file_number"},
           { name: "item_final_price"},
           { name: "subtotal"},
           { name: "null"}
        ],
        height: 500 - 34,
        width:960 * 0.55,
        gridview: true,
        autoencode: true,
        styleUI : 'Bootstrap',
        footerrow:true,
        loadComplete:function () {
            var $self = $(this),
            item_file_number = $self.jqGrid("getCol", "item_file_number", false, "sum"),
            item_file_zs = $self.jqGrid("getCol", "item_file_zs", false, "sum"),
            item_final_price = $self.jqGrid("getCol", "item_final_price", false, "sum"),
            subtotal = $self.jqGrid("getCol", "subtotal", false, "sum");
            $self.jqGrid("footerData", "set", {num:"总计",item_file_number:item_file_number,item_file_zs: item_file_zs,item_final_price:item_final_price,subtotal:subtotal});
        }
	});
	
	var itemUrl = "DININGSYS/yqshapi/queryPrint/selectIntoOrOutItemFileList?owNum="+owNum;
	jQuery("#grid-table-item").jqGrid('setGridParam',{url:itemUrl,page:1}).trigger("reloadGrid");
}

/**
 * 根据条件查询
 */
function changeClick(){
	var expArea = $("#expArea").val();
	var clientSeat = $("#clientSeat").val();
	var owNum = $("#owNum").val();
	var intoUrl = 'DININGSYS/yqshapi/queryPrint/selectIntoOrOutOpenWaterList?joinTeamNotes=1&expArea='+expArea+'&clientSeat='+clientSeat+'&owNum='+owNum+'';
	var outUrl = 'DININGSYS/yqshapi/queryPrint/selectIntoOrOutOpenWaterList?joinTeamNotes=2&expArea='+expArea+'&clientSeat='+clientSeat+'&owNum='+owNum+'';
	jQuery("#grid-table-into").jqGrid('setGridParam',{url:intoUrl,page:1}).trigger("reloadGrid");
    jQuery("#grid-table-out").jqGrid('setGridParam',{url:outUrl,page:1}).trigger("reloadGrid");
	
}



