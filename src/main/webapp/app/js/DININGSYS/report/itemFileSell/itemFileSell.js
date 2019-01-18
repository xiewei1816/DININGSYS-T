/**
 * Created by zhshuo on 2017-02-06.
 */
var itemFileSell = function () {
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});

    function windowReset() {
        $("#itemFileSellDetails").jqGrid("setGridHeight",$(window).height()-180,true);
        $("#itemFileSellDetails").jqGrid("setGridWidth",$(window).width(),true);

        $("#itemFileSellSummary").jqGrid("setGridHeight",$(window).height()-180,true);
        $("#itemFileSellSummary").jqGrid("setGridWidth",$(window).width(),true);
    }

    function pageInit() {
    	
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	var itemFileType = $("#itemFileType").val();
    	var bis = $("#bis").val();
        $("#itemFileSellDetails").getJqGrid({
        	url: "DININGSYS/report/itemFileSell/dataSearch?searchDataType=1&startTime="+startTime+"&endTime="+endTime+"&itemFileType="+itemFileType+"&bis="+bis+"&itemFileName="+$("#itemFileName").val()+"&serviceType="+$("#serviceType").val(),
    		colM : "serviceType,num,name,unit,pxdl,pxxl,itemFinalPrice,itemFileNumber,subtotal," +
    				"serviceTime,isTc,pos,userName,seatName,owNum,serviceNum",
    		queryForm : "queryForm",
    		colNames : "服务方式,品项编号,品项名称,单位名称,大类名称,小类名称," +
    				"价格,数量,金额,日期时间,品项标志,服务POS,操作员," +
    				"客位,营业流水编号,服务流水编号",
    		colWid : {},
    		pager : "pager_list_1",
    		formatter : {
    			"serviceType" : function(cellV){
    				if(cellV == 1 || cellV == 6){
    	                return "自增品项";
    	            }else if(cellV == 2){
    	                return "加单";
    	            }else if(cellV == 3){
    	                return "赠单";
    	            }else if(cellV == 4){
    	                return "退单";
    	            }else if(cellV == 5){
    	                return "自减品项";
    	            }else{
    	                return "其他";
    	            }
    			},
    			"isTc" : function(cellV){
    				if(cellV == 1){
    	                return "套餐品项";
    	            }else{
    	                return "非套餐品项";
    	            }
    			}
    		},
    		loadComplete : function() {
    			//$("#" + pageUtil.tabId).setRowData(2,null,{display: 'none'});
    		}
        });

        $("#itemFileSellSummary").getJqGrid({
            url: "DININGSYS/report/itemFileSell/dataSearch?searchDataType=2&startTime="+startTime+"&endTime="+endTime+"&itemFileType="+itemFileType+"&bis="+bis+"&itemFileName="+$("#itemFileName").val()+"&serviceType="+$("#serviceType").val(),
            colM : "num,name,unit,pxdl,pxxl,itemFinalPrice,number,subtotal,isTc",
            queryForm : "queryForm",
    		colNames : "品项编号,品项名称,单位名称,大类名称,小类名称,价格,数量,金额,品项标志",
    		colWid : {},
    		pager : "pager_list_2",
    		formatter : {
    			"isTc" : function(cellV){
    				if(cellV == 1){
    	                return "套餐品项";
    	            }else{
    	                return "非套餐品项";
    	            }
    			}
    		},
    		loadComplete : function() {
    			//$("#" + pageUtil.tabId).setRowData(2,null,{display: 'none'});
    		}
        });
        
    	$("#doSearch").click(function(){
        	var itemFileType = $("#itemFileType").val();
        	var bis = $("#bis").val();
    		var startTime = $("#startTime").val();
    		var endTime = $("#endTime").val();
    		var url_1 = "DININGSYS/report/itemFileSell/dataSearch?searchDataType=1&startTime="+startTime+"&endTime="+endTime+"&itemFileType="+itemFileType+"&bis="+bis+"&itemFileName="+$("#itemFileName").val()+"&serviceType="+$("#serviceType").val();
    		jQuery("#itemFileSellDetails").jqGrid('setGridParam',{url:url_1,page:1}).trigger("reloadGrid");
    		var url_2 = "DININGSYS/report/itemFileSell/dataSearch?searchDataType=2&startTime="+startTime+"&endTime="+endTime+"&itemFileType="+itemFileType+"&bis="+bis+"&itemFileName="+$("#itemFileName").val()+"&serviceType="+$("#serviceType").val();
    		jQuery("#itemFileSellSummary").jqGrid('setGridParam',{url:url_2,page:1}).trigger("reloadGrid");
    	});
    	
    	$("#export").click(function(){
    		var itemFileType = $("#itemFileType").val();
        	var bis = $("#bis").val();
    		var startTime = $("#startTime").val();
    		var endTime = $("#endTime").val();
    		
    		var url = "";
    		var value = $("input[type='radio']:checked").val();
    		if(value == "details"){
    			url = "DININGSYS/report/itemFileSell/detailExportXls?searchDataType=1&startTime="+startTime+"&endTime="+endTime+"&itemFileType="+itemFileType+"&bis="+bis+"&itemFileName="+$("#itemFileName").val()+"&serviceType="+$("#serviceType").val();
    		}else{
    			url = "DININGSYS/report/itemFileSell/summaryExportXls?searchDataType=2&startTime="+startTime+"&endTime="+endTime+"&itemFileType="+itemFileType+"&bis="+bis+"&itemFileName="+$("#itemFileName").val()+"&serviceType="+$("#serviceType").val();
    		}
    		window.location.href = url;
    	});
    	
    	
    	$("#refresh").click(function(){
    		var url = "";
    		var value = $("input[type='radio']:checked").val();
    		if(value == "details"){
    			$("#itemFileSellDetails").trigger("reloadGrid");
    		}else{
    			$("#itemFileSellSummary").trigger("reloadGrid");
    		}
        });
    	
    	$("#print").click(function(){
    		var value = $("input[type='radio']:checked").val();
    		if(value == "details"){
    			$("#gview_itemFileSellDetails").printArea();
    		}else{
    			$("#gview_itemFileSellSummary").printArea();
    		}
    	});
    	
        windowReset();

        $(window).resize(function () {
            windowReset();
        })

    }

    function tabelCellFormatter(cellvalue, options, rowObject) {
        var cellV = Number(cellvalue),cellName = options.colModel.name;
        if(cellName == 'service_type'){
            if(cellV == 1 || cellV == 6){
                return "自增品项";
            }else if(cellV == 2){
                return "加单";
            }else if(cellV == 3){
                return "赠单";
            }else if(cellV == 4){
                return "退单";
            }else if(cellV == 5){
                return "自减品项";
            }else{
                return "其他";
            }
        }else if(cellName == 'is_tc'){
            if(cellV == 1){
                return "套餐品项";
            }else{
                return "非套餐品项";
            }
        }
    }


    function eventInit() {
        $("input[name='dataType']").click(function () {
            var $self = $(this),eventId = $self.attr("id");
            if(eventId === "details"){
                $("#tableDetails").show();
                $("#tableSummary").hide();
            }else if(eventId === "summary"){
                $("#tableDetails").hide();
                $("#tableSummary").show();
            }
        })
    }

    return{
        pageInit:function () {
            pageInit();
            eventInit();
        }
    }
}();
