/**
 * Created by zhshuo on 2017-01-22.
 */
var apiOpenClass = function () {

     function pageTableInit() {
         $("#bigType").jqGrid({
             url: "DININGSYS/yqshapi/checkService/bigTypeReport?loginPos="+$("#loginPos").val()+"&userCode="+$("#userCode").val(),
             datatype: "JSON",
             colNames: ["大类名称","数量","应收金额","折后金额"],
             colModel: [
                 { name: "bigTypeName"},
                 { name: "bigItemNumber"},
                 { name: "bigNetIncome",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                 { name: "bigTotalReceivables",formatter:'number',formatoptions:{decimalPlaces: 2 }}
             ],
             viewrecords: true,
             autoencode: true,
             styleUI : 'Bootstrap',
             footerrow: true,
             autowidth : true,
             loadComplete: function () {
                 var $self = $(this),
                     sumItemFile = $self.jqGrid("getCol", "bigItemNumber", false, "sum"),
                     sumIncome = $self.jqGrid("getCol", "bigNetIncome", false, "sum"),
                     sumTotalReceivables = $self.jqGrid("getCol", "bigTotalReceivables", false, "sum");
                 $self.jqGrid("footerData", "set", {bigTypeName:"合计",bigItemNumber:sumItemFile,bigNetIncome: sumIncome,bigTotalReceivables:sumTotalReceivables});
             }
         });
         $("#bigType").jqGrid("setGridWidth",550,true);
         $("#bigType").jqGrid("setGridHeight",350,true);

         $("#smallType").jqGrid({
             url: "DININGSYS/yqshapi/checkService/smallTypeReport?loginPos="+$("#loginPos").val()+"&userCode="+$("#userCode").val(),
             datatype: "JSON",
             colNames: ["小类名称","数量","应收金额","折后金额"],
             colModel: [
                 { name: "smallTypeName"},
                 { name: "smallItemNumber"},
                 { name: "smallNetIncome",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                 { name: "smallTotalReceivables",formatter:'number',formatoptions:{decimalPlaces: 2 }}
             ],
             viewrecords: true,
             autoencode: true,
             styleUI : 'Bootstrap',
             footerrow: true,
             autowidth : true,
             loadComplete: function () {
                 var $self = $(this),
                     sumItemFile = $self.jqGrid("getCol", "smallItemNumber", false, "sum"),
                     sumIncome = $self.jqGrid("getCol", "smallNetIncome", false, "sum"),
                     sumTotalReceivables = $self.jqGrid("getCol", "smallTotalReceivables", false, "sum");
                 $self.jqGrid("footerData", "set", {smallTypeName:"合计",smallItemNumber:sumItemFile,smallNetIncome: sumIncome,smallTotalReceivables:sumTotalReceivables});
             }
         });
         $("#smallType").jqGrid("setGridWidth",550,true);
         $("#smallType").jqGrid("setGridHeight",350,true);

         $("#clearingWay").jqGrid({
             url: "DININGSYS/yqshapi/checkService/clearingWayReport?loginPos="+$("#loginPos").val()+"&userCode="+$("#userCode").val(),
             datatype: "json",
             colNames: ["结算方式","支付金额"],
             colModel: [
                 { name: "seName"},
                 { name: "settlementAmount",formatter:'number',formatoptions:{decimalPlaces: 2 }}
             ],
             viewrecords: true,
             autoencode: true,
             styleUI : 'Bootstrap',
             footerrow: true,
             autowidth : true,
             loadComplete: function () {
                 var $self = $(this),
                     sumSettlementAmount = $self.jqGrid("getCol", "settlementAmount", false, "sum");
                 $self.jqGrid("footerData", "set", {seName:"合计",settlementAmount:sumSettlementAmount});
             }
         });
         $("#clearingWay").jqGrid("setGridWidth",550,true);
         $("#clearingWay").jqGrid("setGridHeight",350,true);

         $("#memberGZ").jqGrid({
             url: "DININGSYS/yqshapi/checkService/memberGZReport?loginPos="+$("#loginPos").val()+"&userCode="+$("#userCode").val(),
             datatype: "json",
             colNames: ["客户名称","支付金额","结算流水"],
             colModel: [
                 { name: "memberName"},
                 { name: "settlement_amount",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                 { name: "cw_num"}
             ],
             viewrecords: true,
             autoencode: true,
             styleUI : 'Bootstrap',
             footerrow: true,
             autowidth : true,
             loadComplete: function () {
                 var $self = $(this),
                     sumSettlementAmount = $self.jqGrid("getCol", "settlement_amount", false, "sum");
                 $self.jqGrid("footerData", "set", {memberName:"合计",settlement_amount:sumSettlementAmount});
             }
         });
         $("#memberGZ").jqGrid("setGridWidth",550,true);
         $("#memberGZ").jqGrid("setGridHeight",350,true);

         $("#itemFile").jqGrid({
             url: "DININGSYS/yqshapi/checkService/itemFileReport?loginPos="+$("#loginPos").val()+"&userCode="+$("#userCode").val(),
             datatype: "json",
             colNames: ["名称","数量","单位","单价","净收入","优惠","大类","小类"],
             colModel: [
                 { name: "itemFileName"},
                 { name: "itemFileNumber"},
                 { name: "unit"},
                 { name: "itemFinalPrice",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                 { name: "netIncome",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                 { name: "totalReceivables",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                 { name: "bigTypeName"},
                 { name: "smallTypeName"}
             ],
             rowNum : -1,
             viewrecords: true,
             autoencode: true,
             styleUI : 'Bootstrap',
             footerrow: true,
             autowidth : true,
             loadComplete: function () {
                 var $self = $(this),
                     sumItemFile = $self.jqGrid("getCol", "itemFileNumber", false, "sum"),
                     sumIncome = $self.jqGrid("getCol", "netIncome", false, "sum"),
                     sumReceviables = $self.jqGrid("getCol", "totalReceivables", false, "sum");
                 $self.jqGrid("footerData", "set", {itemFileName:"合计",itemFileNumber:sumItemFile,netIncome: sumIncome,totalReceivables:sumReceviables});
             }
         });
         $("#itemFile").jqGrid("setGridWidth",550,true);
         $("#itemFile").jqGrid("setGridHeight",350,true);
     }

    return{
        pageTableInit:function () {
            pageTableInit();
        }
    }

}();