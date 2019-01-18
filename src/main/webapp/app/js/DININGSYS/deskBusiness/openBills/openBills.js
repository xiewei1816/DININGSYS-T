/**
 * Created by zhshuo on 16-11-30.
 */
var openBills = function () {
    var windowW,windowH;
    function reset() {
        windowW = $(window).width(),windowH = $(window).height();
        $("#overflowPanel").height(windowH-55);
        $("#overflowPanel").width(windowW);

        $("#tabContent").width(windowW);

        $("#searchTable").width($("#openBillsLeftDiv").width());
        $("#rightTopTable").width($("#openBillsRightDiv").width());

        $("#openBillsTable").jqGrid("setGridWidth",$("#openBillsLeftDiv").width(),true);
        $("#openBillsTable").jqGrid("setGridHeight",$("#openBillsLeftDiv").height()-120,true);

        $("#openWaterItemFileDetails").jqGrid("setGridWidth",$("#openBillsRightDiv").width(),true);
        $("#openWaterItemFileDetails").jqGrid("setGridHeight",$("#openBillsRightDiv").height()-180,true);

    }

    function pageInit() {
        $("#openBillsTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","客位","营业流水","开单时间","开单POS","转账","转账营业流水"],
            colModel: [
                { name: "id",hidden:true},
                { name: "seatName"},
                { name: "owNum",width:450},
                { name: "firstTime",width:280},
                { name: "posName"},
                { name: "joinTeamNotes"},
                { name: "transferTarget",width:300}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true,
            onSelectRow:openWaterSelect
        });

        $("#openBillsTable").jqGrid("setGridWidth",$("#openBillsLeftDiv").width(),true);
        $("#openBillsTable").jqGrid("setGridHeight",$("#openBillsLeftDiv").height()-120,true);

        $("#openWaterItemFileDetails").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","品项编号","品项名称","单位","数量","价格","小计","注"],
            colModel: [
                { name: "id",hidden:true},
                { name: "itemFileNum"},
                { name: "itemFileName"},
                { name: "unit"},
                { name: "itemFileNumber"},
                { name: "standardPrice",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "subtotal" ,formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "notes"}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#openWaterItemFileDetails").jqGrid("setGridWidth",$("#openBillsRightDiv").width(),true);
        $("#openWaterItemFileDetails").jqGrid("setGridHeight",$("#openBillsRightDiv").height()-180,true);

        $(window).resize(function(){
            reset();
        });
    }

    function openWaterSelect(rowid, status, e) {
        $(".resetSpanClass").empty();
        $("#openWaterIdTwo").html("无");
        var rowData = $("#openBillsTable").jqGrid('getRowData',rowid);
        $("#openWaterItemFileDetails").jqGrid("clearGridData");

        $("#openWaterId").html(rowData['owNum']);
        $("#openWaterIdTwo").html(rowData['owNum']);
        $("#kaiDanPos").html(rowData['posName']);
        $("#kaiDanTime").html(rowData['firstTime']);
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/openBills/selectOpenWaterInfo/'+rowid,
            dataType:'JSON',
            async:false,
            success:function(data){
                var itemData = data['itemFileInfo'],owData = data['owInfo'];
                for(var i in itemData){
                    $("#openWaterItemFileDetails").jqGrid('addRowData',itemData[i]['id'],itemData[i]);
                }

                $("#kaiDanRs").html(owData['peopleCount']);
                $("#servicer").html(owData['waiterName']);
                $("#client").html(owData['crId']);
                $("#documents").html(owData['documents']);
                $("#owNotes").html(owData['owNotes']);
                $("#teamMembers").html(owData['teamMembers']);
            }
        })
    }

    function pageDataInit() {
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/openBills/getCurrentOpenWater',
            data:{place:$("#place").val(),seatName:$("#seatName").val(),owNum:$("#owNum").val()},
            dataType:'JSON',
            success:function(data){
                $("#openBillsTable").jqGrid("clearGridData");
                for(var i in data){
                    $("#openBillsTable").jqGrid('addRowData',data[i]['id'],data[i]);
                }
            }
        }).done(function () {
            var rowData = $("#openBillsTable").jqGrid('getRowData');
            if(rowData.length > 0){
                var firstRowData = rowData[0];
                $("#openBillsTable").jqGrid('setSelection',firstRowData['id']);
            }else{
                $(".resetSpanClass").empty();
                $("#openWaterIdTwo").html("无");
                $("#openWaterItemFileDetails").jqGrid("clearGridData");
            }
        })
    }

    /*function tableTimerInit() {
        pageDataInit();
        var timeTaskSet = Number($("#timerTaskId").val()) * 1000;
        setTimeout("openBills.tableTimerInit()",timeTaskSet);
    }*/

    function pageBtnInit() {
        $("#flushOpenBills").click(function () {
            pageDataInit();
        })
        $("#flushOpenBills").trigger("click");
    }

    return{
        reset:function(){
            reset();
        },
        pageInit:function () {
            pageInit();
        },
        pageBtnInit:function () {
            pageBtnInit();
        }
        /*pageDataInit:function () {
            pageDataInit();
        },*/
    }

}();

/**
 * 根据条件查询
 */
function changeClick(){
	var place = $("#place").val();
	var seatName = $("#seatName").val();
	var owNum = $("#owNum").val().trim();
	/*alert(place + "/" + seatName + "/" + owNum);*/
	$.ajax({
        type:'POST',
        url:'DININGSYS/deskBusiness/openBills/getCurrentOpenWater',
        data:{place:place,seatName:seatName,owNum:owNum},
        dataType:'JSON',
        async:false,
        success:function(data){
        	 $("#openBillsTable").jqGrid("clearGridData");
             for(var i in data){
                 $("#openBillsTable").jqGrid('addRowData',data[i]['id'],data[i]);
             }
        }
    }).done(function () {
    	var rowData = $("#openBillsTable").jqGrid('getRowData');
        if(rowData.length > 0){
            var firstRowData = rowData[0];
            $("#openBillsTable").jqGrid('setSelection',firstRowData['id']);
        }else{
            $(".resetSpanClass").empty();
            $("#openWaterIdTwo").html("无");
            $("#openWaterItemFileDetails").jqGrid("clearGridData");
        }
    });
	
}