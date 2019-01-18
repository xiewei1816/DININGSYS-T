$(function(){
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});

    var cwName = $("#cwName").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	$("#grid-table").jqGrid({
        url: "DININGSYS/report/payway/selectPayWayReportList?cwName="+cwName+"&startTime="+startTime+"&endTime="+endTime,
        datatype: "json",
        colNames: ['结算流水','结算方式','结算方式类型','应收金额','实收金额','找零金额','返位结算','结算时间','备注'],
        colModel: [
            { name: 'cwNum',sortable: true},
            { name: 'cName',sortable: true},
            { name: 'cwType',sortable: true},
            { name: 'amountsReceivable',sortable: true,formatter:'number',formatoptions:{decimalPlaces: 2 }},
            { name: 'paidAmount',sortable: true,formatter:'number',formatoptions:{decimalPlaces: 2 }},
            { name: 'changeAmount',sortable: true,formatter:'number',formatoptions:{decimalPlaces: 2 }},
            { name: 'clearingState',sortable: true,formatter:'select', formatter:function (cellvalue, options, rowObject) {
				if(cellvalue == "3"){
					return "是";
				}else{
					return "否";
				}
            }},
            { name: 'clearingTime',sortable: true},
            { name: 'clearingNotes',sortable: true}
        ],
        pager : "pager_list_1",
        styleUI : 'Bootstrap',
        rowNum:-1,
        rownumbers: true,
        footerrow:true,
        rowList:[ 20, 50, 100 ],
        rowNum:20,
        loadComplete:function () {
            var $self = $(this),
            amountsReceivable = $self.jqGrid("getCol", "amountsReceivable", false, "sum"),
            paidAmount = $self.jqGrid("getCol", "paidAmount", false, "sum"),
            changeAmount = $self.jqGrid("getCol", "changeAmount", false, "sum");

            var rowData = $self.getRowData();
            $.each(rowData,function (i) {
                var state = rowData[i]['clearingState'];
                if(state == "是"){
                    amountsReceivable -= rowData[i]['amountsReceivable'];
                    paidAmount -= rowData[i]['paidAmount'];
                    changeAmount -= rowData[i]['changeAmount'];
                }
            })

            $self.jqGrid("footerData", "set", {cwNum:"总计",amountsReceivable:amountsReceivable,paidAmount: paidAmount,changeAmount:changeAmount});
        }
	});

    $("#grid-table").jqGrid("setGridWidth",$("#grid-table").closest(".jqGrid_wrapper").width(),true);
    $("#grid-table").jqGrid("setGridHeight",$(window).height()-220,true);
	
	$("#doSearch").click(function(){
		var cwName = $("#cwName").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var url = "DININGSYS/report/payway/selectPayWayReportList?cwName="+cwName+"&startTime="+startTime+"&endTime="+endTime;
		jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
	});
	
	$("#export").click(function(){
		var cwName = $("#cwName").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		window.location.href="DININGSYS/report/payway/exportXls?cwName="+cwName+"&startTime="+startTime+"&endTime="+endTime;
	});
	
	$("#refresh").click(function(){
		$("#grid-table").trigger("reloadGrid");
    });
	
	$("#print").click(function(){
    	$("#gbox_grid-table").printArea();
	});
	
	$(window).resize(function () {
        $("#grid-table").jqGrid("setGridWidth",$("#grid-table").closest(".jqGrid_wrapper").width(),true);
        $("#grid-table").jqGrid("setGridHeight",$(window).height()-220,true);
    })
	
});