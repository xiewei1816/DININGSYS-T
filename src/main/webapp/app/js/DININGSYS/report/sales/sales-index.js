$(function(){
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});

    var marketingStaff = $("#marketingStaff").val();
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	var ppxlId = $("#ppxlId").val();
	console.log("--"+ppxlId);
	$("#grid-table").jqGrid({
        url: "DININGSYS/report/sales/selectSalesReportList?marketingStaff="+marketingStaff+"&startTime="+startTime+"&endTime="+endTime+"&ppxlId="+ppxlId,
        datatype: "json",
        colNames: ['品项名称','销售数量',/*'标准价格','优惠金额',*/'销售金额(优惠后)','营销员'],
        colModel: [
            { name: 'itemFileName',sortable: true},
            { name: 'number',sortable: true,formatter:'number',formatoptions:{decimalPlaces: 2 }},
            /*{ name: 'standardPrice',sortable: true},
            { name: 'discountMoney',sortable: true},*/
            { name: 'total',sortable: true,formatter:'number',formatoptions:{decimalPlaces: 2 }},
            { name: 'marketingStaffName',sortable: true}
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
                number = $self.jqGrid("getCol", "number", false, "sum");
                total = $self.jqGrid("getCol", "total", false, "sum");
                console.log("total1: "+total);

            $self.jqGrid("footerData", "set", {itemFileName:"总计",number:number,total:total});
        }
	});

    $("#grid-table").jqGrid("setGridWidth",$("#grid-table").closest(".jqGrid_wrapper").width(),true);
    $("#grid-table").jqGrid("setGridHeight",$(window).height()-220,true);
	
	$("#doSearch").click(function(){
		var marketingStaff = $("#marketingStaff").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
        var ppxlId = $("#ppxlId").val();
		var url = "DININGSYS/report/sales/selectSalesReportList?marketingStaff="+marketingStaff+"&startTime="+startTime+"&endTime="+endTime+"&ppxlId="+ppxlId;
		jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
	});
	
	$("#export").click(function(){
        var marketingStaff = $("#marketingStaff").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
        var ppxlId = $("#ppxlId").val();
        window.location.href="DININGSYS/report/sales/exportXls?marketingStaff="+marketingStaff+"&startTime="+startTime+"&endTime="+endTime+"&ppxlId="+ppxlId;
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