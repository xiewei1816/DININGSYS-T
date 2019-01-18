/**
 * Created by zhshuo on 2017-02-07.
 */
var statementQuery = function () {

    function windowReset() {
        $("#statementQuery").jqGrid("setGridHeight",$(window).height()-280,true);
        // $("#statementQuery").jqGrid("setGridWidth",$(window).width(),true);
    }

    function pageInit() {
    	
    	pageUtil.pageInit({
    		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
    		}
    	});
    	
    	//动态获取支付方式
    	//var settlementWayColNames = getSettlementWayColNames();
    	
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var consumerArea = $("#consumerArea").val();
		var bis = $("#bis").val();
		var pos = $("#pos").val();
		var colNames = ["id","结算流水编号","营业流水","结算时间","结算方式","最早开台时间","客位列表","就餐人数",//line-1
	                "返位结算","市别","品项消费","消费总金额","优惠金额",//line-3
	                "优惠方式","优惠授权人","抹零","定额优惠","实收金额","应收金额","找零",//line-4
	                "结算POS","结算操作员"//line-6
	                ];
		var colModel = [
	                {name:"dowId",hidden:true},{ name: "cwNum",index:'cw_num',width:190, sortable: true},{ name: "owNum",index:'ow_num',width:220, sortable: true},{ name: "clearingTime",index:'clearing_time', sortable: true},{name:"payInfo", sortable: false,width:150},
	                { name: "firstTime",index:'first_time', sortable: true},{ name: "seatName",width:90, sortable: false},{ name: "peopleCount",index:'people_count',width:90, sortable: true},//line-1
	                { name: "clearingState",index:'clearing_state',width:90, sortable: true,formatter:function (cellValue,option,rowObj) {
	                    if(cellValue == 3){
	                        return "是";
	                    }
	                    return "否";
	                },align:"center"},{ name: "bisName",width:90, sortable: false},
	                { name: "pxjgh",width:100,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},
	                { name: "consumptionAmount",width:90,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},
	                { name: "discountCosts",width:90,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},//line-3
	                { name: "discountInfo", sortable: false},{ name: "authorizedPersonName",width:90, sortable: false},
	                { name: "zeroAmount",width:90,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},
	                { name: "fixedDiscount",width:90,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},
	                { name: "paidAmount",width:90,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},
	                { name: "amountsReceivable",width:90,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},
	                { name: "changeAmount",width:90,formatter:'number',formatoptions:{decimalPlaces: 2 }, sortable: false},//line-4
	                { name: "posName", sortable: false},{ name: "operatorName", sortable: false}
	            ]
		
        $("#statementQuery").jqGrid({
            url: "DININGSYS/report/statement/dataSearch?startTime="+startTime+"&endTime="+endTime+"&consumerArea="+consumerArea+"&bis="+bis+"&pos="+pos+"&clearingNum="+$("#clearingNum").val(),
            datatype: "json",
            colNames: colNames,
            colModel: colModel,
            rowNum:20,
            rowList:[ 20, 50, 100 ],
            autoencode: true,
            rownumbers:true,
            shrinkToFit:false,
            autoScroll: true,
            width:'100%',
            styleUI : 'Bootstrap',
            pager:"#pager_list_1",
            footerrow:true,
             loadComplete:function () {
             var $self = $(this),
             peopleCount = $self.jqGrid("getCol", "peopleCount", false, "sum"),
             consumptionAmount = $self.jqGrid("getCol", "consumptionAmount", false, "sum"),
             discountCosts = $self.jqGrid("getCol", "discountCosts", false, "sum"),
             zeroAmount = $self.jqGrid("getCol", "zeroAmount", false, "sum"),
             fixedDiscount = $self.jqGrid("getCol", "fixedDiscount", false, "sum"),
             paidAmount = $self.jqGrid("getCol", "paidAmount", false, "sum"),
             amountsReceivable = $self.jqGrid("getCol", "amountsReceivable", false, "sum"),
             changeAmount = $self.jqGrid("getCol", "changeAmount", false, "sum");
             
             $self.jqGrid("footerData", "set", {rn:$self.getGridParam("reccount"),peopleCcount:peopleCount,
            	 consumptionAmount:consumptionAmount,discountCosts:discountCosts,
            	 zeroAmount:zeroAmount,fixedDiscount:fixedDiscount,paidAmount:paidAmount,amountsReceivable:amountsReceivable,changeAmount:changeAmount
             });
             }
        });

		/*$("#statementQuery").getJqGrid({
			url: "DININGSYS/report/statement/dataSearch?startTime="+startTime+"&endTime="+endTime+"&consumerArea="+consumerArea+"&bis="+bis+"&pos="+pos+"&clearingNum="+$("#clearingNum").val(),
			colM : "dowId,cwNum,owNum,clearingTime,payInfo,firstTime,seatName,peopleCount," + //line-1
					"clearingState,bisName,pxjgh,consumptionAmount,discountCosts," + //line-2
					"discountInfo,authorizedPersonName,zeroAmount,fixedDiscount,paidAmount,amountsReceivable,changeAmount," + //line-3
					"posName,operatorName", //line-4
			queryForm : "queryForm",
			colNames : "id,结算流水,营业流水,结算时间,结算方式,最早开台时间,客位列表,就餐人数," + //line-1
					"返位结算,市别,品项消费,消费总金额,优惠金额," + //line-2
					"优惠方式,优惠授权人,抹零,定额优惠,实收金额,应收金额,找零," + //line-3
					"结算POS,结算操作员", //line-4
			colWid : {
				"cwNum":450,"owNum":450,"clearingTime":450,"payInfo":450,"firstTime":450,"seatName":450,"peopleCount":450, //line-1
				"clearingState":450,"bisName":450,"pxjgh":450,"consumptionAmount":450,"discountCosts":450, //line-2
				"discountInfo":450,"authorizedPersonName":450,"zeroAmount":450,"fixedDiscount":450,"paidAmount":450,"amountsReceivable":450,"changeAmount":450, //line-3
				"posName":450,"operatorName":450  //line-4
			},
			pager : "pager_list_1",
			formatter : {
				"clearingState" : function(cellV){
					if(cellV == 2){
		                return '<font color="green">否</font>';
		            }else if(cellV == 3){
		                return '<font color="red">是</font>';
		            }else{
		                return '';
		            }
				}
			},
			footerrow:true,
			loadComplete : function() {
				/!*$("#statementQuery").setRowData(2,null,{display: 'none'});*!/
				$("#statementQuery").hideCol("dowId");

				var $self = $("#statementQuery"),
				peopleCount = $self.jqGrid("getCol", "peopleCount", false, "sum"),
				consumptionAmount = $self.jqGrid("getCol", "consumptionAmount", false, "sum"),
				consumptionAmount = $self.jqGrid("getCol", "consumptionAmount", false, "sum"),
				discountCosts = $self.jqGrid("getCol", "discountCosts", false, "sum"),
				zeroAmount = $self.jqGrid("getCol", "zeroAmount", false, "sum"),
				fixedDiscount = $self.jqGrid("getCol", "fixedDiscount", false, "sum"),
				paidAmount = $self.jqGrid("getCol", "paid_amount", false, "sum"),
				amountsReceivable = $self.jqGrid("getCol", "amountsReceivable", false, "sum"),
				changeAmount = $self.jqGrid("getCol", "changeAmount", false, "sum");

				$self.jqGrid("footerData", "set", {rn:$self.getGridParam("reccount"),peopleCount:peopleCount,
					consumptionAmount: consumptionAmount,
					consumptionAmount:consumptionAmount,discountCosts:discountCosts,
					zeroAmount:zeroAmount,fixedDiscount:fixedDiscount,paidAmount:paidAmount,amountsReceivable:amountsReceivable,changeAmount:changeAmount
				});
			}
	    });*/
		
        
        $("#doSearch").click(function(){
    		var startTime = $("#startTime").val();
    		var endTime = $("#endTime").val();
    		var consumerArea = $("#consumerArea").val();
    		var bis = $("#bis").val();
    		var pos = $("#pos").val();
    		var url = "DININGSYS/report/statement/dataSearch?startTime="+startTime+"&endTime="+endTime+"&consumerArea="+consumerArea+"&bis="+bis+"&pos="+pos+"&clearingNum="+$("#clearingNum").val();
    		jQuery("#statementQuery").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    	});
        
        $("#export").click(function(){
        	var startTime = $("#startTime").val();
    		var endTime = $("#endTime").val();
    		var consumerArea = $("#consumerArea").val();
    		var bis = $("#bis").val();
    		var pos = $("#pos").val();
    		var url = "DININGSYS/report/statement/exportXls?startTime="+startTime+"&endTime="+endTime+"&consumerArea="+consumerArea+"&bis="+bis+"&pos="+pos+"&clearingNum="+$("#clearingNum").val();
    		window.location.href=url;
    	});
        
    	$("#refresh").click(function(){
    		$("#statementQuery").trigger("reloadGrid");
        });
    	
    	$("#print").click(function(){
    		$("#gbox_statementQuery").printArea();
    	});

        windowReset();

        $(window).resize(function () {
            windowReset();
        });
    }

    function tableReset(){
        var iele = $(window.parent.document).find(".scrolbar>i");
        if($(iele).hasClass('fa-angle-left')){
            iele.trigger("click");
        }
    }
    
    /**
     * 动态获取支付方式
     */
    function getSettlementWayColNames(){
    	var settlementWayColNames = "";
    	$.ajax({
            type:'POST',
            url:"DININGSYS/settlementWay/getAllList",
            dataType:'JSON',
            async:false,
            success:function (data) {
                if(data.success == 'OK'){
                	var result = data.list;
                	for(var i in result){
                	   settlementWayColNames += '"'+result[i].name + '",';
                	}
                	return settlementWayColNames;
                }
            }
        });
    }

    return{
        pageInit:function () {
            pageInit();
            tableReset();
        }

    }
}();
