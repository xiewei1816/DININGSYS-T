/**
 * 当前营业情况
 * Created by zhshuo on 2016-11-10.
 */
var cuOw = function () {
    var windowW,windowH,layerIndex;
    function reset() {
        windowW = $(window).width(),windowH = $(window).height();
        $("#disRightDiv").height(windowH-70);
        $("#bisLeftDiv").height(windowH-70);

        $("#overflowPanel").height(windowH-70);

        $("#bisLeftDiv").width($("#overflowPanel").width()-900);

        $("#disRightDiv").width($("#overflowPanel").width()-$("#bisLeftDiv").width()-50);

        $("#currentBisTable").jqGrid("setGridWidth",$("#bisContent").width(),true);
        $("#currentBisTable").jqGrid("setGridHeight",$("#disRightDiv").height()-455,true);

        $("#bisRightTale").jqGrid("setGridWidth",$("#disRightDiv").width()-30,true);
        $("#bisRightTale").jqGrid("setGridHeight",$("#disRightDiv").height()-$("#bisRightPanelHead").height()-400,true);

        $("#settlementTypeTable").jqGrid("setGridWidth",$("#bisTabDiv").width(),true);
        $("#settlementTypeTable").jqGrid("setGridHeight",170,true);

        $(".container").width($("#disRightDiv").width()-20);
    }

    function pageInit() {
        $("#currentBisTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","编号","areaName","seatLabel","seatState","名称","状态","客位数","开台时间"],
            colModel: [
                { name: "id",hidden:true},
                { name: "number"},
                { name: "areaName",hidden:true},
                { name: "seatLabel",hidden:true},
                { name: "seatState",hidden:true},
                { name: "name"},
                { name: "seatStateName",width:100,formatter:bisTableDataFormate},
                { name: "allowNumber",width:60,formatter:bisTableDataFormate},
                { name: "lastOpenTime",width:140}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            onSelectRow : currentBisTableSelect,
            autowidth : true
        });

        $("#currentBisTable").jqGrid("setGridWidth",$("#bisContent").width(),true);
        $("#currentBisTable").jqGrid("setGridHeight",$("#disRightDiv").height()-455,true);

        $("#bisRightTale").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","品项","注","价格","数量","做法金额","小计"],
            colModel: [
                { name: "id",hidden:true},
                { name: "itemFileName"},
                { name: "notes",formatter:detailFormate},
                { name: "item_final_price" },
                { name: "item_file_number",width:80},
                { name: "production_costs"},
                { name: "subtotal"}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#bisRightTale").jqGrid("setGridWidth",$("#disRightDiv").width()-30,true);
        $("#bisRightTale").jqGrid("setGridHeight",$("#disRightDiv").height()-$("#bisRightPanelHead").height()-400,true);

        $("#settlementTypeTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["payWayCode","结算方式","应收金额","实收金额","找零金额"],
            colModel: [
                { name: "payWayCode",hidden:true},
                { name: "payWayName" },
                { name: "amountsReceivable",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "settlementAmount",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "changeAmount",formatter:'number',formatoptions:{decimalPlaces: 2 }}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true,
            footerrow: true,
            gridComplete: function() {
                $(this).jqGrid('footerData', 'set', { 'amountsReceivable': '0.00','settlementAmount':'0.00','changeAmount':'0.00' });
            }
        });

        $("#settlementTypeTable").jqGrid("setGridWidth",$("#bisTabDiv").width(),true);
        $("#settlementTypeTable").jqGrid("setGridHeight",170,true);

        $(window).resize(function(){
            reset();
        });

        $("#bis").change(function () {
           $("#currentBis").text($(this).find("option:selected").text());
        });

        $("#panelHeadDiv > button").click(function () {
            var btnId = $(this).attr("id");
            if(btnId == "currentOpenWaterFlush"){
                tableDataInit();
            }else if(btnId == "currentOpenWaterSetting"){
                var timeSet = Number($("#timeTaskSet").val());
                var layerIndex = layer.open({
                    title: '修改自动刷新时间（秒）'
                    ,content: '<input id="customFlushTime" type="text" class="form-control" value="'+timeSet+'">',
                    btn:['确定',"取消"],
                    yes:function () {
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/updateCommonsValueByCode',
                            data:{cvCode:'current_open_flush',cvValue:$("#customFlushTime").val()},
                            dataType:'JSON',
                            success:function(data){
                                if(data.success){
                                    $("#timeTaskSet").val($("#customFlushTime").val());
                                    layer.close(layerIndex);
                                }
                            }
                        })
                    }
                });
            }else if(btnId == "toSeatLastClosedCheck"){
                var rowId = $("#currentBisTable").getGridParam("selrow");
                if(!rowId){
                    layer.msg('请选择您想查看的客位！',{
                        time:2000
                    });
                    return;
                }
                var tableDataId = $("#currentBisTable").jqGrid('getCell',rowId,'id');
                $.get('DININGSYS/deskBusiness/currentOpen/toSeatLastClosedCheck/'+tableDataId,function (data) {
                    if(data.startsWith("layer")){
                        eval(data);
                    }else{
                        layerIndex = layer.open({
                            type: 1,
                            title:'已结账单',
                            skin: 'layui-layer-rim',
                            area: [windowW-100+'px', windowH+'px'],
                            content: data
                        })
                    }
                })
            }
        })
    }
    
    function bisTableDataFormate(cellvalue, options, rowObject) {
        if(options.colModel.name == 'seatStateName'){
            var seatState = rowObject['seatState'];
            if(seatState == 1){
                return "<span style='color: green'>空闲</span>";
            }else if(seatState == 2){
                return "<span style='color: red'>占用</span>";
            }else if(seatState == 3){
                return "<span style='color: #FF8000'>清扫</span>";
            }else if(seatState == 4){
                return "<span style='color: #0A246A'>预定</span>";
            }else if(seatState == 5){
                return "<span style='color: darkred'>埋单</span>";
            }
        }else if(options.colModel.name == 'allowNumber') {
            if (rowObject['seatState'] == 2 || rowObject['seatState'] == 5) {
                return rowObject['realTimePeopleCount'] + "/" + cellvalue+"P";
            } else {
                return "";
            }
        }
    }

    function tableTimerInit() {
        tableDataInit();
        var timeTaskSet = Number($("#timeTaskSet").val()) * 1000;
        setTimeout("cuOw.tableTimerInit()",timeTaskSet);
    }

    function tableDataInit() {
        //客座统计信息清空
        $(".clearP").html("0");
        $(".clearPe").html("0%");
        //结算方式表格数据清空
        $("#settlementTypeTable").jqGrid("clearGridData");

        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/currentOpen/selectCurrentSeatInfo',
            data:$("#searchForm").serialize(),
            dataType:'JSON',
            async:false,
            success:function(data){
                $("#currentBisTable").jqGrid('clearGridData');
                var seatCheckInfo = data['classStatement'],seatInfo = data['currentSeatInfo'],
                    currentSeatCount = data['currentSeatCount'],currentPayInfo = data['currentPayInfo'],
                    todayReImp = data['todayReservationInfo'],todayPBReInfo = data['todayPBReInfo'];

                //客位状态信息
                for(var i in seatInfo){
                    $("#currentBisTable").jqGrid('addRowData',seatInfo[i].id,seatInfo[i]);
                }

                //j结算方式表格
                for(var i in currentPayInfo){
                    $("#settlementTypeTable").jqGrid('addRowData',currentPayInfo[i].payWayCode,currentPayInfo[i]);
                }
                var $self = $("#settlementTypeTable"),
                    totalAmountReceivable = $self.jqGrid("getCol", "amountsReceivable", false, "sum"),
                    totalSettlementAmount = $self.jqGrid("getCol", "settlementAmount", false, "sum"),
                    totoalChangeAmount = $self.jqGrid("getCol", "changeAmount", false, "sum");
                $self.jqGrid('footerData', 'set', { 'amountsReceivable': totalAmountReceivable,'settlementAmount':totalSettlementAmount,'changeAmount':totoalChangeAmount });

                //客位信息
                if(currentSeatCount != null){
                    $("#seatTotal").html(currentSeatCount['totalCount']);
                    $("#clearingSeat").html(currentSeatCount['3']);
                    $("#busySeat").html(currentSeatCount['2']);
                    $("#freeSeat").html(currentSeatCount['1']);
                    $("#paySeat").html(currentSeatCount['5']);
                    $("#reservationSeat").html(currentSeatCount['4']);

                    if(typeof currentSeatCount['2'] == "undefined"){
                        $("#firstRate").html("0%");
                    }else{
                        var firstRate = Number(currentSeatCount['totalCount'])/Number(currentSeatCount['2']);
                        $("#firstRate").html(firstRate+"%");
                    }
                }

                $("#attendance").html(data['attendance']+"%");//上座率

                //账单信息
                if(seatCheckInfo != null){
                    $("#bisClosedClass").html(seatCheckInfo['bisClosedClassCount']);
                    $("#bisClosedAmount").html(seatCheckInfo['bisClosedClassAmount']);
                    $("#bisClosedPeople").html(seatCheckInfo['bisClosedClassPeopleCount']);
                    $("#openIncome").html(seatCheckInfo['bisOpenClassCount']);
                    $("#todayCheck").html(seatCheckInfo['allClassCount']);
                    $("#operatingIncome").html(seatCheckInfo['operatingIncome']);
                    $("#openCount").html(seatCheckInfo['bisOpenClassAmount']);
                    $("#openPeopleCount").html(seatCheckInfo['bisOpenClassPeopleCount']);
                    $("#operatingPeopleCount").html(seatCheckInfo['allClassPeople']);
                    $("#allAmount").html(seatCheckInfo['allAmount']);
                }

                if(todayReImp != null){
                    $("#todayReservationSeat").html(todayReImp['todatRe']);
                    $("#todayImpl").html(todayReImp['todayImp']);
                }

                if(todayPBReInfo != null){
                    $("#todayPBRe").html(todayPBReInfo['todatPBRe']);
                    $("#todayPBImp").html(todayPBReInfo['todayPBImp']);
                }
            },
            error:function () {
                console.info("数据加载出错！");
            }
        })
    }
    
    function currentBisTableSelect(rowId, e) {
        var rowData = $("#currentBisTable").jqGrid('getRowData',rowId);
        $("#seatInfoId").html(rowData['number']+"-"+rowData['name']);
        $("#areaId").html(rowData['areaName']);
        $("#seatLabel").html(rowData['seatLabel']);

        if(rowData['seatState'] == 2 || rowData['seatState'] == 5 ){
            $.ajax({
                type:'POST',
                url:'DININGSYS/deskBusiness/currentOpen/getYyInfoBySeatId',
                data:{seatId:rowData['id']},
                dataType:'JSON',
                async:false,
                success:function(data){
                    //循环出多个营业流水
                    var liStatement = "";
                    for (var i in data){
                        liStatement += "<li>"+data[i].owNum+"</li>"
                    }
                    $("#openWaterNums").html(liStatement);
                    horizontalScroll();
                }
            }).done(function () {
                var openWaters = $("#openWaterNums li");
                if(openWaters.length > 0){
                    openWaterClickInit();
                    $("#openWaterNums li:eq(0)").trigger("click");
                }
            })
        }
    }

    function detailFormate(cellvalue, options, rowObject) {
        var cellV = Number(cellvalue);
        if(options.colModel.name == 'notes'){
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
        }
    }

    //翻台账单页面
    function lastClosedCheckPageInit() {
        $("#lastClosedCheckPanel").height(windowH-160);
        $("#lastClosedCheckPanel").width(windowW-225);

        $("#seatLastCheck").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","营业流水","客位","人数","品项费","优惠","服务费","包房费","最低消费","最低消费补齐","小计"],
            colModel: [
                { name: "id",hidden:true},
                { name: "owNum",width:450},
                { name: "seatId"},
                { name: "peopleCount" },
                { name: "itemCostsSum"},
                { name: "discountCosts"},
                { name: "serviceCharge"},
                { name: "privateRoomCost"},
                { name: "minimumConsumption"},
                { name: "minimumChargeComplete"},
                { name: "subtotal"}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true,
            onSelectRow:openWaterSelect
        });

        $("#seatLastCheck").jqGrid("setGridWidth",$("#lastClosedCheckPage").width()-100,true);
        $("#seatLastCheck").jqGrid("setGridHeight",200,true);

        $("#settlementWayDivTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","结算方式名称","金额","换算金额","备注"],
            colModel: [
                { name: "id",hidden:true},
                { name: "seName"},
                { name: "settlementAmount"},
                { name: "conversionAmount" },
                { name: "notes"}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#settlementWayDivTable").jqGrid("setGridWidth",$("#lastClosedCheckPage").width()-100,true);
        $("#settlementWayDivTable").jqGrid("setGridHeight",200,true);

        $("#consumerDetailsDivTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","编号","品项名称","单价","单位","数量","制作费用","折扣","注","小计"],
            colModel: [
                { name: "id",hidden:true},
                { name: "itemFileNum"},
                { name: "itemFileName"},
                { name: "itemFinalPrice"},
                { name: "unit"},
                { name: "itemFileNumber"},
                { name: "productionCosts"},
                { name: "discount"},
                { name: "notes",formatter:detailFormate},
                { name: "subtotal" }
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#consumerDetailsDivTable").jqGrid("setGridWidth",$("#lastClosedCheckPage").width()-100,true);
        $("#consumerDetailsDivTable").jqGrid("setGridHeight",200,true);

        $("#classStatementDivTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["优惠信息","授权人"],
            colModel: [
                { name: "discountInfo"},
                { name: "authorizedPersonName" }
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#classStatementDivTable").jqGrid("setGridWidth",$("#lastClosedCheckPage").width()-100,true);
        $("#classStatementDivTable").jqGrid("setGridHeight",200,true);

        $("#invoiceInfoDivTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","发票张数","发票面额","发票金额","发票号码","备注"],
            colModel: [
                { name: "invoiceId",hidden:true},
                { name: "receiptCount"},
                { name: "receiptDenomination"},
                { name: "receiptAmount" },
                { name: "receiptNum"},
                { name: "notes"}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#invoiceInfoDivTable").jqGrid("setGridWidth",$("#lastClosedCheckPage").width()-100,true);
        $("#invoiceInfoDivTable").jqGrid("setGridHeight",200,true);
    }

    function openWaterSelect(rowid, status, e) {

        var rowData = $("#seatLastCheck").jqGrid('getRowData',rowid),
            id = rowData['id'];


        $("#consumerDetailsDivTable").jqGrid("clearGridData");
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/currentOpen/getOpenWaterConDeInfo/'+id,
            dataType:'JSON',
            success:function(data){
                for(var i in data)
                    $("#consumerDetailsDivTable").jqGrid('addRowData',data[i]['id'],data[i]);
            }
        })
    }

    function lastClosedCheckDataInit(){
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/currentOpen/getOpenWaterOtherInfo/'+$("#cwId").val(),
            dataType:'JSON',
            async:false,
            success:function(data){
                var settlements = data['settlements'],discounts = data['discounts'],receipts = data['receipts'];
                for(var i in settlements){
                    $("#settlementWayDivTable").jqGrid('addRowData',settlements[i]['id'],settlements[i]);
                }
                for(var i in discounts){
                    $("#classStatementDivTable").jqGrid('addRowData',discounts[i]['id'],discounts[i]);
                }
                for(var i in receipts){
                    $("#invoiceInfoDivTable").jqGrid('addRowData',receipts[i]['id'],receipts[i]);
                }
            }
        });

        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/currentOpen/getSeatOpenWater',
            data:{cwId:$("#cwId").val()},
            dataType:'JSON',
            async:false,
            success:function(data){
                for(var i in data){
                    $("#seatLastCheck").jqGrid('addRowData',data[i]['id'],data[i]);
                }
            }
        }).done(function(){
            var rowData = $("#seatLastCheck").jqGrid('getRowData');
            if(rowData.length > 0){
                var firstRowData = rowData[0];
                $("#seatLastCheck").jqGrid('setSelection',firstRowData['id']);
            }
        })
    }

    function lastClosedCheckBntInit() {
        $("#cancel").click(function () {
            layer.closeAll('page');
        });

        $("#editCloseNotes").click(function () {

            $.get('DININGSYS/deskBusiness/currentOpen/toClearingNotes/'+$("#cwId").val(),function (data) {
                var index = layer.open({
                    title: '结算备注'
                    ,content: data,
                    area:['500px','350px'],
                    btn:['确认','取消'],
                    yes:function () {
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/deskBusiness/currentOpen/editClearingNotes',
                            data:$("#clearingNotesForm").serialize(),
                            dataType:'JSON',
                            success:function(data){
                                if(data.success){
                                    layer.close(index);
                                }
                            }
                        })
                    }
                })
            })
        })
    }

    function horizontalScroll() {
        $(".control .pre").click(function () {
            openWaterClickInit();
        });
        $(".control .next").click(function () {
            openWaterClickInit();
        });

        $('#openWatersDiv').rollSlide({
            orientation: 'left',
            num: 1,
            v: 1000,
            space: 3000,
            callBack:function () {
                openWaterClickInit();
            }
        });
    }

    function loadOpenWaterInfo(openWaterNum) {
        /**每次点击营业流水LI，先清除右边数据**/
        $("#yyNum").html("无");
        $("#kaiDanPos").html("");
        $("#kaiDanTime").html("");
        $("#kaiDanRs").html("");
        $("#servicer").html("");
        $("#members").html("");
        $("#documents").html("");
        $("#owNotes").html("");
        $("#total").html("");
        $("#bisRightTale").jqGrid("clearGridData");
        /**END**/

        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/currentOpen/selectOpenWaterInfo',
            data:{openWaterNum:openWaterNum},
            dataType:'JSON',
            async:false,
            success:function(data){
                var openWaterInfo = data['openWaterInfo'],openWaterItemFiles = data['openWaterItemFiles'];
                 $("#yyNum").html(openWaterInfo['owNum']==null?"无":openWaterInfo['owNum']);
                 $("#kaiDanPos").html(openWaterInfo['posName']);
                 $("#kaiDanTime").html(openWaterInfo['firstTime']);
                 $("#kaiDanRs").html(openWaterInfo['peopleCount']);
                 $("#servicer").html(openWaterInfo['waiterCode']+"-"+openWaterInfo['waiterName']);
                 $("#members").html(openWaterInfo['crName']);
                 $("#documents").html(openWaterInfo['documents']);
                 $("#owNotes").html(openWaterInfo['owNotes']);
                 $("#total").html(data['total']);

                 for(var i in openWaterItemFiles){
                 $("#bisRightTale").jqGrid('addRowData',i,openWaterItemFiles[i]);
                 }
            }
        })
    }

    function openWaterClickInit() {
        $("#openWaterNums li").click(function () {
            var openWaterNum = $(this).text();
            loadOpenWaterInfo(openWaterNum);
        })
    }

    return{
         reset:function(){
            reset();
        },
        pageInit:function () {
            pageInit();
        },
        /*tableDataInit:function () {
            tableDataInit();
        },*/
        tableTimerInit:function () {
            tableTimerInit();
        },
        lastClosedCheckPageInit:function () {
            lastClosedCheckPageInit();
            lastClosedCheckDataInit();
            lastClosedCheckBntInit();
        }
    }
}();