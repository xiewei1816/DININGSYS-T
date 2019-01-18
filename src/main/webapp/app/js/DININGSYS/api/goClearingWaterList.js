$(function(){
	/**************************************** 营业流水基本信息 ****************************************/	
	$("#grid-table").jqGrid({
        url: "DININGSYS/yqshapi/queryPrint/selectOpenWaterBaseList?cwNum="+$("#cwNum").text(),
        datatype: "JSON",
        mtype: "POST",
        colNames: ["id","营业流水","客位","人数","品项费","优惠","服务费","包房费","最低消费","最低消费补齐","小计"],
        colModel: [
           { name: "id",hidden:true},
           { name: "owNum"},
           { name: "seatName"},
           { name: "peopleCount"},
           { name: "itemCostsSum"},
           { name: "discountCosts"},
           { name: "serviceCharge"},
           { name: "privateRoomCost"},
           { name: "minimumConsumption"},
           { name: "minimumChargeComplete"},
           { name: "subtotal"}
        ],
        height: 100,
        width: 700,
        rowNum : -1,
        gridview: true,
        autoencode: true,
        styleUI : 'Bootstrap',
        onSelectRow:function(rowId){   
        	var rowDatas = $("#grid-table").jqGrid('getRowData', rowId);   
        	var id = rowDatas["id"];
        	getTabsList(id);
        },
        loadComplete:function(data){
        	if(data != ""){
        		//初始化第一行选中
	        	var id = data[0].id;
	        	$("#grid-table").setSelection(id);
	        	getTabsList(id);
    		}
    	}
    });
	
	/**
	 * 刷新表格
	 */
	$("#refresh").click(function(){
		$("#grid-table").trigger("reloadGrid");
    });

	/**
	 * 加载4项Tab
	 */
	function getTabsList(id){
		
		/**
		 * 根据营业流水ID查询结算方式、消费明、优惠信息、发票信息
		 * @param id 营业流水ID
		 */
		$("#grid-table1").jqGrid({
	        url: "DININGSYS/yqshapi/queryPrint/selectTabsBaseList?flag=1&id="+id,
	        datatype: "JSON",
	        mtype: "POST",
	        colNames: ["id","结算方式名称","金额","换算金额","备注"],
	        colModel: [
	           { name: "id",hidden:true},
	           { name: "typeName",width:"160"},
	           { name: "settlementAmount",width:"160"},
	           { name: "conversionAmount",width:"160"},
	           { name: "notes",width:"220"}
	        ],
	        height: 100,
	        width: 700,
	        gridview: true,
	        autoencode: true,
	        styleUI : 'Bootstrap'
	    });
		
		$("#grid-table2").jqGrid({
	        url: "DININGSYS/yqshapi/queryPrint/selectTabsBaseList?flag=2&id="+id,
	        datatype: "JSON",
	        mtype: "POST",
	        colNames: ["id","编号","品项名称","单价","单位","数量","只数","制作费用","折扣","注","小计","客座"],
	        colModel: [
	           { name: "id",hidden:true},
	           { name: "num",width:"70"},
	           { name: "name",width:"120"},
	           { name: "item_final_price",width:"60"},
	           { name: "unit",width:"50"},
	           { name: "item_file_number",width:"50"},
	           { name: "item_file_zs",width:"50"},
	           { name: "null",width:"70"},
	           { name: "discount",width:"40"},
	           { name: "null",width:"40"},
	           { name: "subtotal",width:"80"},
	           { name: "guest",width:"70"}
	        ],
	        height: 100,
	        width: 700,
	        gridview: true,
	        autoencode: true,
	        styleUI : 'Bootstrap'
	    });
		
		$("#grid-table4").jqGrid({
	        url: "DININGSYS/yqshapi/queryPrint/selectTabsBaseList?flag=4&id="+id,
	        datatype: "JSON",
	        mtype: "POST",
	        colNames: ["id","发票张数","发票面额","发票金额","发票号码","备注"],
	        colModel: [
	           { name: "id",hidden:true},
	           { name: "receipt_count",width:"100"},
	           { name: "receipt_denomination",width:"100"},
	           { name: "receipt_amount",width:"100"},
	           { name: "receipt_num",width:"200"},
	           { name: "notes",width:"200"}
	        ],
	        height: 100,
	        width: 700,
	        gridview: true,
	        autoencode: true,
	        styleUI : 'Bootstrap'
	    });
		
		var url1 = "DININGSYS/yqshapi/queryPrint/selectTabsBaseList?flag=1&id="+id;
		jQuery("#grid-table1").jqGrid('setGridParam',{url:url1,page:1}).trigger("reloadGrid");
		var url2 = "DININGSYS/yqshapi/queryPrint/selectTabsBaseList?flag=2&id="+id;
		jQuery("#grid-table2").jqGrid('setGridParam',{url:url2,page:1}).trigger("reloadGrid");
		var url4 = "DININGSYS/yqshapi/queryPrint/selectTabsBaseList?flag=4&id="+id;
		jQuery("#grid-table4").jqGrid('setGridParam',{url:url4,page:1}).trigger("reloadGrid");
		
	}
});

/**
 * 单选刷新转账流水品项信息
 * @param owNum
 */
function getFileItemsByOwNum(owNum){
	var itemUrl = "DININGSYS/yqshapi/queryPrint/selectIntoOrOutItemFileList?owNum="+owNum;
	jQuery("#grid-table-item").jqGrid('setGridParam',{url:itemUrl,page:1}).trigger("reloadGrid");
}

