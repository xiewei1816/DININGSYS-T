/**
 * Created by zhshuo on 16-11-22.
 */
var closedBills = function () {
    var rightTableHeight,confirmIndex;
    function reset() {
        $("#contentDiv").height($(window).height()-80);
        $("#contentDiv").width($(window).width()-30);

        $("#contentBodyDiv").height($(window).height());
        $("#contentBodyDiv").width($(window).width()-50);

        $("#leftPanelDiv").height($("#contentBodyDiv").height());
        $("#rightPanelDiv").height($("#contentBodyDiv").height());

        $("#closedBillsTable").jqGrid("setGridWidth",$("#lefeDiv").width()-1,true);
        $("#closedBillsTable").jqGrid("setGridHeight",$("#lefeDiv").height()-250,true);

        $("#openWaterTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#openWaterTable").jqGrid("setGridHeight",150,true);

        $("#settlementWayDivTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#settlementWayDivTable").jqGrid("setGridHeight",rightTableHeight,true);

        $("#consumerDetailsDivTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#consumerDetailsDivTable").jqGrid("setGridHeight",rightTableHeight,true);

        $("#invoiceInfoDivTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#invoiceInfoDivTable").jqGrid("setGridHeight",rightTableHeight,true);
    }

    function pageInit(){
        $("#openDate").jeDate({
            format:"YYYY-MM-DD",
            isTime:false,
            festival:true,
            isinitVal:true,
            okfun:function (elem, val) {
                pageDataInit();
            },
            choosefun:function(elem, val) {
                pageDataInit();
            },
            clearfun:function(elem, val) {
                pageDataInit();
            }
        });

        //已结账单table
        $("#closedBillsTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["cwid","owId","结算流水","客位","结算时间","营业流水"],
            colModel: [
                { name: "closedId",hidden:true},
                { name: "openId",hidden:true},
                { name: "cwNum",width:100,sortable:false},
                { name: "seatName",width:30,sortable:false},
                { name: "clearingTime",width:80,sortable:false},
                { name: "owNum",width:110,sortable:false}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true,
            onSelectRow:closedBillsSelect
        });

        $("#closedBillsTable").jqGrid("setGridWidth",$("#lefeDiv").width()-1,true);
        $("#closedBillsTable").jqGrid("setGridHeight",$("#lefeDiv").height()-250,true);

        //营业流水table
        $("#openWaterTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","clearingWaterId","营业流水","客位","人数","品项费","优惠","服务费","包房费","最低消费","最低消费补齐","小计"],
            colModel: [
                { name: "id",hidden:true},
                { name: "clearingWaterId",hidden:true},
                { name: "owNum",width:300},
                { name: "seatName"},
                { name: "peopleCount"},
                { name: "itemCostsSum",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "discountCosts",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "serviceCharge",formatter:'number',formatoptions:{decimalPlaces: 2 },hidden:true},
                { name: "privateRoomCost",formatter:'number',formatoptions:{decimalPlaces: 2 },hidden:true},
                { name: "minimumConsumption",formatter:'number',formatoptions:{decimalPlaces: 2 },hidden:true},
                { name: "minimumChargeComplete",formatter:'number',formatoptions:{decimalPlaces: 2 },hidden:true},
                { name: "subtotal",formatter:'number',formatoptions:{decimalPlaces: 2 }}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true,
            shrinkToFit:false,
            autoScroll: true,
            onSelectRow:openWaterSelect
        });

        $("#openWaterTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#openWaterTable").jqGrid("setGridHeight",150,true);

        rightTableHeight = $("#rightDiv").height()-$("#row1").height()-$("#row2").height()-130;

        //结算方式table
        $("#settlementWayDivTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","结算方式名称","金额","换算金额","备注"],
            colModel: [
                { name: "id",hidden:true},
                { name: "seName"},
                { name: "settlementAmount",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "conversionAmount",formatter:'number',formatoptions:{decimalPlaces: 2 } },
                { name: "notes"}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#settlementWayDivTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#settlementWayDivTable").jqGrid("setGridHeight",rightTableHeight,true);

        //消费明细table
        $("#consumerDetailsDivTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","编号","品项名称","单价","单位","数量","制作费用","折扣","注","小计"],
            colModel: [
                { name: "id",hidden:true},
                { name: "itemFileNum"},
                { name: "itemFileName"},
                { name: "standardPrice",formatter:'number',formatoptions:{decimalPlaces: 2 } },
                { name: "unit"},
                { name: "itemFileNumber"},
                { name: "productionCosts",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "discount",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "notes",formatter:detailFormate},
                { name: "subtotal" ,formatter:'number',formatoptions:{decimalPlaces: 2 }}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#consumerDetailsDivTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#consumerDetailsDivTable").jqGrid("setGridHeight",rightTableHeight,true);

        //优惠信息table
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

        $("#classStatementDivTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#classStatementDivTable").jqGrid("setGridHeight",rightTableHeight,true);

        //发票信息
        $("#invoiceInfoDivTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","发票张数","发票面额","发票金额","发票号码","备注"],
            colModel: [
                { name: "invoiceId",hidden:true},
                { name: "receiptCount"},
                { name: "receiptDenomination",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "receiptAmount" ,formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "receiptNum"},
                { name: "notes"}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#invoiceInfoDivTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#invoiceInfoDivTable").jqGrid("setGridHeight",rightTableHeight,true);

        $(window).resize(function () {
            reset();
        })

    }

    //营业流水选中event
    function openWaterSelect(rowid, status, e) {
        $("#consumerDetailsDivTable").jqGrid("clearGridData");

        var owId = $("#openWaterTable").jqGrid('getCell',rowid,'id');

        var rowId = $("#closedBillsTable").getGridParam("selrow");
        var cwId = $("#closedBillsTable").jqGrid('getCell',rowId,'closedId');

        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/closedBills/getOpenWaterConDeInfo/'+owId+'/'+cwId,
            data:{time:$("#openDate").val()},
            dataType:'JSON',
            async:false,
            success:function(data){
                $("#settlementWayDivTable").jqGrid("clearGridData");
                $("#classStatementDivTable").jqGrid("clearGridData");
                $("#invoiceInfoDivTable").jqGrid("clearGridData");

                var detailInfo = data['openWaterConDeInfo'];
                //结算方式表
                for(var i in data['settlements'])$("#settlementWayDivTable").jqGrid('addRowData',data['settlements'][i]['id'],data['settlements'][i]);
                //优惠信息
                for(var i in data['discounts'])$("#classStatementDivTable").jqGrid('addRowData',data['discounts'][i]['id'],data['discounts'][i]);
                //发票信息
                for(var i in data['receipts'])$("#invoiceInfoDivTable").jqGrid('addRowData',data['receipts'][i]['id'],data['receipts'][i]);

                for(var i in detailInfo)$("#consumerDetailsDivTable").jqGrid('addRowData',detailInfo[i]['id'],detailInfo[i]);
            }
        })
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

    //结算流水选中event
    function closedBillsSelect(rowid, status, e) {
        $("#openWaterTable").jqGrid("clearGridData");
        $("#settlementWayDivTable").jqGrid("clearGridData");
        $("#classStatementDivTable").jqGrid("clearGridData");
        $("#invoiceInfoDivTable").jqGrid("clearGridData");
        $(".clearsp").html("");
        $("#closedWaterNum").html("无");

        var cwId = $("#closedBillsTable").jqGrid('getCell',rowid,'closedId'),
            owId = $("#closedBillsTable").jqGrid('getCell',rowid,'openId');

        console.info($("#openDate").val())

        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/closedBills/selectClosedClearingWaterInfo/'+cwId,
            data:{time:$("#openDate").val()},
            dataType:'JSON',
            async:false,
            success:function(data){
                var cwInfo = data['cwInfo'],owInfo = data['owInfo'],cwOther = data['cwOther'];

                //头部基本信息填充
                for(var key in cwInfo){
                    var spanObj = "#"+key;
                    $(spanObj).html(cwInfo[key]);
                }

                $("#closedWaterNum").html(cwInfo['cwNum']);

                //营业流水表
                for(var i in owInfo)$("#openWaterTable").jqGrid('addRowData',owInfo[i]['id'],owInfo[i]);
                // //结算方式表
                // for(var i in cwOther['settlements'])$("#settlementWayDivTable").jqGrid('addRowData',cwOther['settlements'][i]['id'],cwOther['settlements'][i]);
                // //优惠信息
                // for(var i in cwOther['discounts'])$("#classStatementDivTable").jqGrid('addRowData',cwOther['discounts'][i]['id'],cwOther['discounts'][i]);
                // //发票信息
                // for(var i in cwOther['receipts'])$("#invoiceInfoDivTable").jqGrid('addRowData',cwOther['receipts'][i]['id'],cwOther['receipts'][i]);
            }
        }).done(function () {
            $("#openWaterTable").jqGrid('setSelection',owId);
        })
    }

    function pageDataInit() {
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/closedBills/selectClosedOpenWaterByDate',
            data:{date:$("#openDate").val()},
            dataType:'JSON',
            async:false,
            success:function(data){
                $("#closedBillsTable").jqGrid("clearGridData");
                for(var i in data){
                    $("#closedBillsTable").jqGrid('addRowData',data[i]['openId'],data[i]);
                }
                $("#totalWater").html(data.length);
            }
        }).done(function () {
            var rowData = $("#closedBillsTable").jqGrid('getRowData');
            if(rowData.length > 0){
                var firstRowData = rowData[0];
                $("#closedBillsTable").jqGrid('setSelection',firstRowData['closedId']);
            }
        })
    }

    function pageBtnInit(){
        $("#flushOpenBills").click(function () {
            changeClick();
        });

        $("#returnSettlement").click(function () {
            var rowId = $("#closedBillsTable").getGridParam("selrow");
            if(!rowId){
                layer.msg('请选择一条已结账单！',{
                    time:2000
                });
                return;
            }

            confirmIndex = layer.confirm('确定将当前结算流水进行返位操作吗？', {
                btn: ['确定','取消']
            }, function(){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/deskBusiness/closedBills/checkPermission?userCode='+$("#userCode").val()+'&fcode=fwjs',
                    dataType:'JSON',
                    success:function(data){
                        if(data){
                            if(data.state == 0){
                                layer.alert("参数有误");
                            }else if(data.state == 1){
                                layer.prompt({formType: 1,title:"请输入授权码："},
                                    function(val, index){
                                    fwjsAjax(val,rowId);
                                    layer.close(index);
                                });
                            }else{
                                fwjsAjax(null,rowId);
                            }
                        }
                    }
                })

            });
        });

        $("#printOpenBills").click(function () {
            var rowId = $("#closedBillsTable").getGridParam("selrow");
            if(!rowId){
                layer.msg('请选择一条已结账单！',{
                    time:2000
                });
                return;
            }

            var closedId = $("#closedBillsTable").jqGrid('getCell',rowId,'closedId');

            $.ajax({
                type:'POST',
                url:'DININGSYS/deskBusiness/closedBills/checkPermission?userCode='+$("#userCode").val()+'&fcode=fkxz',
                dataType:'JSON',
                success:function(data){
                    if(data){
                        if(data.state == 0){
                            layer.alert("参数有误");
                        }else if(data.state == 1){
                            layer.prompt({title:"请输入授权码："},
                                function(val, index){
                                    fkxzAjax(val,closedId);
                                    layer.close(index);
                                });
                        }else{
                            fkxzAjax(null,closedId);
                        }
                    }
                }
            })
        });

        $("#repairInvoice").click(function () {
            var rowId = $("#closedBillsTable").getGridParam("selrow");

            var cwId = $("#closedBillsTable").jqGrid('getCell',rowId,'closedId');

            if(!rowId){
                layer.msg('请选择一条已结账单！',{
                    time:2000
                });
                return;
            }

            $.ajax({
                type:'POST',
                url:'DININGSYS/deskBusiness/closedBills/checkPermission?userCode='+$("#userCode").val()+'&fcode=bkfp',
                dataType:'JSON',
                success:function(data){
                    if(data){
                        if(data.state == 0){
                            layer.alert("参数有误");
                        }else if(data.state == 1){
                            layer.prompt({title:"请输入授权码："},
                                function(val, index){
                                    bkfpAjax(val,cwId);
                                    layer.close(index);
                                });
                        }else{
                            bkfpAjax(null,cwId);
                        }
                    }
                }
            })

        })
    }

    function fkxzAjax(authCode,closedId){
        if(authCode){
            $.ajax({
                type:'POST',
                url:'DININGSYS/deskBusiness/closedBills/checkFkxzPermission',
                data:{authCode:authCode},
                dataType:'JSON',
                success:function(data){
                    if(data && data.success){
                        fkxzAjax_send(closedId);
                    }else{
                        layer.msg(data.errorMsg,{time:1000});
                    }
                }
            })
        }else{
            bkfpAjax_send(closedId);
        }
    }

    function fkxzAjax_send(closedId){
        $.get('DININGSYS/deskBusiness/closedBills/toPaymentCorrect/'+closedId,function (str) {
            var height;
            if($(window).height()-200 > 800){
                height = 800;
            }else{
                height = 500;
            }
            var payLayer = layer.open({
                type: 1,
                title: '付款修正'
                ,content: str,
                area:['600px',height+'px'],
                btn:['确认','取消'],
                yes:function () {
                    var allRowData = $("#closedClearingWayTable").jqGrid("getRowData"),
                        dataJson = JSON.stringify(allRowData);

                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/deskBusiness/closedBills/updateClearingWay',
                        data:{tableJsonData:dataJson,cwId:$("#cwId").val(),ss:$("#pcpaidAmount").text(),zl:$("#pcchangeAmount").text()},
                        dataType:'JSON',
                        success:function(data){
                            if(data.success){
                                layer.msg("以保存",{
                                    time:1000
                                })
                            }else{
                                layer.msg("出错",{
                                    time:1000
                                })
                            }
                        }
                    })
                }
            })
        })

    }

    function bkfpAjax(authCode,cwId){
        if(authCode){
            $.ajax({
                type:'POST',
                url:'DININGSYS/deskBusiness/closedBills/checkBkfpPermission',
                data:{authCode:authCode},
                dataType:'JSON',
                success:function(data){
                    if(data && data.success){
                        bkfpAjax_send(cwId);
                    }else{
                        layer.msg(data.errorMsg,{time:1000});
                    }
                }
            })
        }else{
            bkfpAjax_send(cwId);
        }
    }

    function bkfpAjax_send(cwId){
        $.get('DININGSYS/deskBusiness/closedBills/toRepairInvoice/'+cwId,{time:$("#openDate").val()},function (str) {
            var height;
            if($(window).height()-200 > 800){
                height = 800;
            }else{
                height = 500;
            }
            var index = layer.open({
                type: 1,
                title: '补开发票'
                ,content: str,
                area:['600px',height+'px'],
                btn:['确认','取消'],
                yes:function () {
                    var allRowData = $("#invoicedTable").jqGrid('getRowData'),
                        cwId = $("#cwId").val(),tableJsonData = null;

                    if(allRowData.length > 0){
                        tableJsonData = JSON.stringify(allRowData)
                    }

                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/deskBusiness/closedBills/repairInvoice',
                        data:{tableJsonData:tableJsonData,cwId:cwId,time:$("#openDate").val()},
                        dataType:'JSON',
                        success:function(data){
                            if(data.success){
                                layer.msg('以保存！',{
                                    time:500
                                });
                            }
                        }
                    })
                }
            })
        })
    }

    function fwjsAjax(authCode,rowId){
        var cwId = $("#closedBillsTable").jqGrid('getCell',rowId,'closedId');
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/closedBills/returnSettlement',
            data:{deskUserCode:$("#userCode").val(),authCode:authCode,clearingWaterId:cwId,time:$("#openDate").val()},
            async:false,
            dataType:'JSON',
            success:function(data){
                if(data.success){
                    $("#flushOpenBills").trigger("click");
                    layer.close(confirmIndex);
                }else{
                    if(data.errorMsg != null){
                        layer.msg(data.errorMsg,{time:1000});
                    }
                }
            }
        })
    }

    function repairInvoiceInit(){
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/closedBills/getReceiptInfoByCwId/'+$("#cwId").val(),
            data:{time:$("#openDate").val()},
            dataType:'JSON',
            success:function(data){
                for(var i in data){
                    $("#invoicedTable").jqGrid('addRowData',data[i]['invoiceRowId'],data[i]);
                }
            }
        }).done(function () {
            countTotalInvoiceAmount();
        });

        $("#invoiceCountTable tr td div").click(function () {
            var invoiceSpan = $(this).find("span"),
                invocieAmount = invoiceSpan.text();
            var rowData = $("#invoicedTable").jqGrid('getRowData');
            if(rowData.length > 0){
                var invoiceData = $("#invoicedTable").jqGrid('getRowData',invocieAmount);
                if(invoiceData['receiptDenomination'] == null){
                    $("#invoicedTable").jqGrid('addRowData',invocieAmount,{'invoiceRowId':invocieAmount,'receiptCount':1,'receiptDenomination':invocieAmount,'receiptAmount':invocieAmount});
                }else{
                    var count = Number(invoiceData['receiptCount']) + 1,
                        amount = count * Number(invoiceData['receiptDenomination']);
                    $("#invoicedTable").jqGrid('setRowData',invoiceData['invoiceRowId'],{'receiptCount':count,'receiptAmount':amount});
                }
            }else{
                $("#invoicedTable").jqGrid('addRowData',invocieAmount,{'invoiceRowId':invocieAmount,'receiptCount':1,'receiptDenomination':invocieAmount,'receiptAmount':invocieAmount});
            }
            countTotalInvoiceAmount();
        });

        $("#tableInvoiceDel").click(function () {
            var rowData = $("#invoicedTable").jqGrid('getRowData'),
                rowId = rowData[rowData.length-1]['invoiceRowId'];
                selRowId = $("#invoicedTable").getGridParam("selrow");
            if(!selRowId){
                $('#invoicedTable').jqGrid('delRowData',rowId);
            }else{
                $('#invoicedTable').jqGrid('delRowData',selRowId);
            }
            countTotalInvoiceAmount();
        });
        
        $("#tabInvoiceAdd").click(function () {
            var addInvoiceAmount = $("#addInoviceAmount").val(),
                addInvoiceNum = $("#addInoviceNum").val(),
                addInoviceNotes = $("#addInoviceNotes").val(),
                rowData = $("#invoicedTable").jqGrid('getRowData'),addRowData = [];

            if($.trim(addInvoiceAmount)== ''){
                layer.tips('发票金额为必填', '#addInoviceAmount');
                return;
            }
            if($.trim(addInvoiceNum)== ''){
                layer.tips('发票号码为必填', '#addInoviceNum');
                return;
            }

            for(var i in rowData){
                if(rowData[i]['receiptNum'] != null){
                    addRowData.push(rowData[i]);
                }
                if(rowData[i]['receiptNum'] == addInvoiceNum){
                    layer.tips('该发票号码已存在', '#addInoviceNum');
                    return;
                }
            }
            var addRowId = 'addRow'+addRowData.length;
            $("#invoicedTable").jqGrid('addRowData',addRowId,{'invoiceRowId':addRowId,'receiptCount':1,'receiptDenomination':0.00,'receiptAmount':addInvoiceAmount,'receiptNum':addInvoiceNum,'notes':addInoviceNotes});
            countTotalInvoiceAmount();

            $("#addInoviceAmount").val('');
            $("#addInoviceNum").val('');
            $("#addInoviceNotes").val('');
        });

        $("#invoicedTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","张数","面额","发票金额","发票号码","备注"],
            colModel: [
                { name: "invoiceRowId",hidden:true},
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
        })
    }

    function countTotalInvoiceAmount() {
        var newRowData = $("#invoicedTable").jqGrid('getRowData'),totalAmount = Number(0);
        for(var i in newRowData){
            totalAmount +=  Number(newRowData[i]['receiptAmount']);
        }
        $("#invoiceAmount").text(totalAmount);
    }

    //付款修正页面
    function paymentCorrectInit() {
        $("#closedClearingWayTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","rowId","cwCode","notes","nonZeroAmount","foreignPay","已选择","金额","换算金额"],
            colModel: [
                { name: "id",hidden:true},
                { name: "nrowId",hidden:true},
                { name: "cwCode",hidden:true},
                { name: "notes",hidden:true},
                { name: "nonZeroAmount",hidden:true},
                { name: "foreignPay",hidden:true},
                { name: "seName"},
                { name: "settlementAmount"},
                { name: "conversionAmount"}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $(".changeClearingWayDiv").click(function () {
            var commonDiv = $("#commonWay"),otherDiv = $("#otherWay");
            if(commonDiv.css('display') == 'none'){
                commonDiv.show();
                otherDiv.hide();
            }else{
                commonDiv.hide();
                otherDiv.show();
            }
        });

        $(".clearingWayTable tr").click(function () {
            var clickTable = $(this).parents("table");
            clickTable.find("tr").filter(".clearingTableBackColor").removeClass("clearingTableBackColor");
            $(this).addClass("clearingTableBackColor");
        }).dblclick(function () {
            var ar = Number($("#amountsReceivable").text()),pa = Number($("#pcpaidAmount").text());
            if(pa >= ar){
                layer.msg("实收金额已经足够，不允许再增加结算方式",{
                    time:1000
                })
            }else{
                var tableTr = $("#commonTable").find("tr").filter(".clearingTableBackColor");
                var seCode = tableTr.find("td:eq(0)").text(),seName = tableTr.find("td:eq(2)").text(),ysje=$("#pcpaidAmount").text();

                $.post('DININGSYS/deskBusiness/closedBills/checkCwClearingWay/'+$("#cwId").val(),{seCode:seCode,seName:seName,ysje:ysje},function (data) {
                    var addMoneyIndex = layer.open({
                        type: 1,
                        title: '增加付款'
                        ,content: data,
                        area:['500px','300px'],
                        btn:['确认','取消'],
                        yes:function () {
                            if($.trim($("#addshje").val()) == ""){
                                layer.tips('实收金额为必填', '#addshje');
                                return;
                            }

                            var na = Number($("#pcpaidAmount").text())+Number($("#addshje").val());
                            var ca = Number($("#pcchangeAmount").text())+Number($("#addzlje").val());

                            $("#pcpaidAmount").html(na);
                            $("#pcchangeAmount").html(ca);

                            var allRowData = $("#closedClearingWayTable").jqGrid('getRowData'),temp = [];
                            for(var i in allRowData){
                                console.info(allRowData[i]['nrowId']);
                                if(allRowData[i]['nrowId'].indexOf('O') == -1){
                                    temp.push(allRowData[i]);
                                }
                            }

                            var aRowId = "A"+temp.length;
                            $("#closedClearingWayTable").jqGrid('addRowData',aRowId,{'id':aRowId,'nrowId':aRowId,'cwCode':$("#seCode").val(),'notes':$("#addPNotes").val(),'seName':$("#seName").val(),'settlementAmount':$("#addshje").val(),'conversionAmount':$("#addshje").val()});
                            layer.close(addMoneyIndex);
                        }
                    })
                })
            }
        });

        paymentCorrectDataInit();
    }

    function paymentCorrectDataInit() {
        $.ajax({
            type:'POST',
            url:'DININGSYS/deskBusiness/closedBills/selectClearingWayByCwId/'+$("#cwId").val(),
            dataType:'JSON',
            success:function(data){
                for(var i in data){
                    $("#closedClearingWayTable").jqGrid('addRowData',data[i]['nrowId'],data[i]);
                }
            }
        })
    }

    function paymentAddInit() {
        $("#addshje").change(function () {
            var val = Number($.trim($(this).val())),needMoneyV = Number($("#needMoney").text());
            if(val > needMoneyV){
                var temp = val-needMoneyV;
                $("#addzlje").val(temp);
            }
        })
    }

    return{
        reset:function () {
            reset();
        },
        pageInit:function () {
            pageInit();
        },
        pageDataInit:function () {
            pageDataInit();
        },
        pageBtnInit:function () {
            pageBtnInit();
        },
        repairInvoiceInit:function(){
            repairInvoiceInit();
        },
        paymentCorrectInit:function () {
            paymentCorrectInit();
        },
        paymentAddInit:function () {
            paymentAddInit();
        }
    }

}();

/**
 * 根据条件查询
 */
function changeClick(){
	var openDate = $("#openDate").val();
	var clientSeat = $("#clientSeat").val();
	var bis = $("#bis").val().trim();
	var expArea = $("#expArea").val();
	var pos = $("#pos").val();
	var choiceCode = $("#choiceCode").val();
	var code = $("#code").val().trim();	
	$.ajax({
        type:'POST',
        url:'DININGSYS/deskBusiness/closedBills/selectClosedOpenWaterByDate',
        data:{date:openDate,clientSeat:clientSeat,bis:bis,expArea:expArea,pos:pos,choiceCode:choiceCode,code:code},
        dataType:'JSON',
        async:false,
        success:function(data){
            $("#closedBillsTable").jqGrid("clearGridData");
            for(var i in data){
                $("#closedBillsTable").jqGrid('addRowData',data[i]['openId'],data[i]);
            }
            $("#totalWater").html(data.length);
        }
    }).done(function () {
        var rowData = $("#closedBillsTable").jqGrid('getRowData');
        if(rowData.length > 0){
            var firstRowData = rowData[0];
            $("#closedBillsTable").jqGrid('setSelection',firstRowData['closedId']);
        }
    });
	
}