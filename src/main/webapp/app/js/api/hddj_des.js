/**
 * Created by zhshuo on 2017-07-20.
 */
var hddj_des = function () {

    function pageInit(){
        $("#startTime").jeDate({
            isinitVal:true,
            festival: true,
            format: 'YYYY-MM-DD hh:mm:ss'
        });

        $("#startTime").val(GetDateStr(0));

        $("#endTime").jeDate({
            isinitVal:true,
            festival: true,
            format: 'YYYY-MM-DD hh:mm:ss'
        });
        $("#endTime").val(GetDateStr(1));

        resize();

        $("#openWaterTable").jqGrid({
            url: "DININGSYS/yqshapi/queryPrint/getINGOpenWater",
            datatype: "JSON",
            mtype: "GET",
            colNames: ["营业流水号","开台时间","客座","状态"],
            colModel: [
                { name: "owNum",width:200},
                { name: "firstTime" },
                { name: "seatName"},
                { name: "owState",formatter:function (cellValue,option,rowObj) {
                    if(cellValue == 1){
                        return "正常";
                    }else if(cellValue == 3){
                        return "埋单";
                    }else if(cellValue == 4){
                        return "转账未结算";
                    }else if(cellValue == 5){
                        return "转账已埋单";
                    }else if(cellValue == 8){
                        return "手工锁定";
                    }else if(cellValue == 9){
                        return "结算锁定";
                    }
                }}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            onSelectRow:showService
        });

        $("#closedBillTable").jqGrid({
            url: "DININGSYS/yqshapi/queryPrint/selectClosedWater?beginTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()+"&sortType="+$("#sortType").val(),
            datatype: "JSON",
            mtype: "GET",
            colNames: ["结算流水","实收金额","营业流水","结算时间","客座名称"],
            colModel: [
                { name: "cwNum",width:200},
                { name: "paidMoney",width:100,formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "owNum",width:200},
                { name: "clearingTime"},
                { name: "seatName"}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            onSelectRow:showService
        });

        setTableWidthAndHeight();

        $(window).resize(function () {
            resize();
            setTableWidthAndHeight();
        })

        $("#closedBillFlush").click(function () {
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            var sortType = $("#sortType").val();

            var url = "DININGSYS/yqshapi/queryPrint/selectClosedWater?beginTime="+startTime+"&endTime="+endTime+"&sortType="+sortType;
            jQuery("#closedBillTable").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
        })
    }

    function showService(rowid, status, e){
        var $self = $(this),
            rowData = $self.jqGrid('getRowData',rowid),
            owNum = rowData['owNum'];

        $.get('DININGSYS/yqshapi/queryPrint/serviceData/'+owNum,{startTime:$("#startTime").val(),endTime:$("#endTime").val()},function (data) {
            $("#serviceDiv").html(data);
        })
    }

    function setTableWidthAndHeight() {
        $("#openWaterTable").jqGrid("setGridWidth",$("#openWaterDiv").width(),true);
        $("#openWaterTable").jqGrid("setGridHeight",$("#openWaterDiv").height()-100,true);

        $("#closedBillTable").jqGrid("setGridWidth",$("#closedBillSearch").width(),true);
        $("#closedBillTable").jqGrid("setGridHeight",$("#closedBillSearch").height()-100,true);
    }


    function resize(){
        $("#bContent").width($(window).width());
        $("#bContent").height($(window).height());

        $("#leftDataDiv").width($(window).width()*0.55);
        $("#leftDataDiv").height($(window).height()-50);

        $("#openWaterDiv").width($("#leftDataDiv").width());
        $("#openWaterDiv").height($("#leftDataDiv").height()-50);

        $("#serviceDiv").width($(window).width()*0.4);
        $("#serviceDiv").height($(window).height());

        $("#closedBillSearch").height($("#openWaterDiv").height() - 100);
        $("#closedBillSearch").width($("#openWaterDiv").width() - 20);
    }

    function GetDateStr(AddDayCount) {
        var dd = new Date();
        dd.setDate(dd.getDate()+AddDayCount);
        var y = dd.getFullYear();
        var m = dd.getMonth()+1;
        if(m < 10){
            m = "0" + m;
        }
        var d = dd.getDate();
        if(d < 10){
            d = "0" + d;
        }
        return y+"-"+m+"-"+d + " 09:00:00";
    }


    return{
        pageInit:function () {
            pageInit();
        }
    }

}();
