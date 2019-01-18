/**
 * Created by zhshuo on 2017-02-09.
 */
var itemFileType = function () {

    resize = function () {
        $("#itemFileTypeDetails").jqGrid("setGridHeight",$(window).height()-190,true);
        $("#itemFileTypeDetails").jqGrid("setGridWidth",$(window).width(),true);

        $("#itemFileTypeDetails_small").jqGrid("setGridHeight",$(window).height()-190,true);
        $("#itemFileTypeDetails_small").jqGrid("setGridWidth",$(window).width(),true);

        $("#itemFileTypeDetails_item").jqGrid("setGridHeight",$(window).height()-190,true);
        $("#itemFileTypeDetails_item").jqGrid("setGridWidth",$(window).width(),true);

        $("#itemFileTypeDetails_openWater").jqGrid("setGridHeight",$(window).height()-190,true);
        $("#itemFileTypeDetails_openWater").jqGrid("setGridWidth",$(window).width(),true);
    };

    pageInit = function () {

        $("#return").click(function () {
            var clickFlag = $("#clickFlag").val();

            if(clickFlag == 1){
                // $("#itemFileTypeDetails").trigger("reloadGrid");
                $("#itemFileTypeDetails").jqGrid("setGridState",'visible');
                $("#itemFileTypeDetails_small").jqGrid("setGridState",'hidden');

                $("#itemFileTypeDetails").jqGrid("setGridHeight",$(window).height()-190,true);
                $("#itemFileTypeDetails").jqGrid("setGridWidth",$(window).width(),true);

//                $("#clickFlag").removeAttr("val");
                $("#clickFlag").val(0);
                $("#pxdlLabel").empty();
                $("#return").hide();
                $("#tips").show();
            }else if(clickFlag == 2){
                // jQuery("#itemFileTypeDetails_small").jqGrid('setGridParam',{url:"DININGSYS/report/itemFileType/dataSearch_small?bigNum="+$("#bigTypeNum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()}).trigger("reloadGrid");
                $("#itemFileTypeDetails_small").jqGrid("setGridHeight",$(window).height()-190,true);
                $("#itemFileTypeDetails_small").jqGrid("setGridWidth",$(window).width(),true);

                $("#itemFileTypeDetails").jqGrid("setGridState",'hidden');
                $("#itemFileTypeDetails_small").jqGrid("setGridState",'visible');

                $("#clickFlag").val(1);
                $("#pxxlLabel").empty();
            }else if(clickFlag == 3){
                // jQuery("#itemFileTypeDetails_item").jqGrid('setGridParam',{url:"DININGSYS/report/itemFileType/dataSearch_item?smallNum="+$("#smallTypeNum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()}).trigger("reloadGrid");
                $("#itemFileTypeDetails_item").jqGrid("setGridHeight",$(window).height()-190,true);
                $("#itemFileTypeDetails_item").jqGrid("setGridWidth",$(window).width(),true);

                $("#itemFileTypeDetails_item").jqGrid("setGridState",'visible');
                $("#itemFileTypeDetails_small").jqGrid("setGridState",'hidden');

                $("#clickFlag").val(2);
                $("#pxLabel").empty();
            }
        });

        $("#itemFileTypeDetails").jqGrid({
            url: "DININGSYS/report/itemFileType/dataSearch_index?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
            datatype: "json",
            colNames: ['大类编号', '品项大类','折前销售金额','优惠金额','品项金额','制作费用','合计','数量','平均售价'],
            colModel: [
                { name: 'num'},
                { name: 'name'},
                { name: 'zqje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zrje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'pxje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zzfy',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zhje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'itemFileNumber'},
                { name: 'avgPrice',formatter:'number',formatoptions:{decimalPlaces: 2 }}
            ],
            styleUI : 'Bootstrap',
            rowNum:-1,
            rownumbers: true,
            footerrow:true,
            loadComplete:function () {
                layer.closeAll('loading');
                var $self = $(this),
                    zqje = $self.jqGrid("getCol", "zqje", false, "sum"),
                    zrje = $self.jqGrid("getCol", "zrje", false, "sum"),
                    pxje = $self.jqGrid("getCol", "pxje", false, "sum"),
                    zzfy = $self.jqGrid("getCol", "zzfy", false, "sum"),
                    zhje = $self.jqGrid("getCol", "zhje", false, "sum"),
                    itemFileNumber = $self.jqGrid("getCol", "itemFileNumber", false, "sum");
                $self.jqGrid("footerData", "set", {rn:$self.getGridParam("reccount"),zqje:zqje,zrje: zrje,pxje:pxje,zzfy:zzfy,zhje:zhje,itemFileNumber:itemFileNumber});
            },
            ondblClickRow:function (rowid,iRow,iCol,e) {
                var rowData = $("#itemFileTypeDetails").getRowData(rowid),
                    bigTypeNum = rowData['num'],bigTypeName = rowData['name'];

                $("#tips").hide();
                $("#return").show();
                $("#pxdlLabel").text(bigTypeName);
                $("#clickFlag").val(1);
                $("#bigTypeNum").val(bigTypeNum);

                $.when(loadingShades()).then(function () {
                    jQuery("#itemFileTypeDetails_small").jqGrid('setGridParam',{url:"DININGSYS/report/itemFileType/dataSearch_small?bigNum="+bigTypeNum+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val()}).trigger("reloadGrid");
                    $("#itemFileTypeDetails_small").jqGrid("setGridHeight",$(window).height()-190,true);
                    $("#itemFileTypeDetails_small").jqGrid("setGridWidth",$(window).width(),true);

                    $("#itemFileTypeDetails").jqGrid("setGridState",'hidden');
                    $("#itemFileTypeDetails_small").jqGrid("setGridState",'visible');
                })
            }
        });
        
        $("#doSearch").click(function(){
    		var url = "DININGSYS/report/itemFileType/dataSearch_index?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
    		jQuery("#itemFileTypeDetails").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
    	});
        
        $("#export").click(function(){
        	var clickFlag = $("#clickFlag").val();
        	var url = "";
        	if(clickFlag == 0){
        		url = "DININGSYS/report/itemFileType/exportXls0?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
        		window.location.href=url;
        	}
        	if(clickFlag == 1){
        		url = "DININGSYS/report/itemFileType/exportXls1?bigNum="+$("#bigTypeNum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
        		window.location.href=url;
        	}
			if(clickFlag == 2){
        		url = "DININGSYS/report/itemFileType/exportXls2?smallNum="+$("#smallTypeNum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
        		window.location.href=url;
        	}
			if(clickFlag == 3){
        		url = "DININGSYS/report/itemFileType/exportXls3?itemFileNum="+$("#itemFileNum").val()+"&startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val();
        		window.location.href=url;
        	}
    	});
        
    	$("#refresh").click(function(){
    		$("#itemFileTypeDetails").trigger("reloadGrid");
        });
    	
    	$("#print").click(function(){
    		var clickFlag = $("#clickFlag").val();
        	if(clickFlag == 0){
        		$("#gview_itemFileTypeDetails").printArea();
        	}
        	if(clickFlag == 1){
        		$("#gview_itemFileTypeDetails_small").printArea();
        	}
			if(clickFlag == 2){
				$("#gview_itemFileTypeDetails_item").printArea();
        	}
			if(clickFlag == 3){
				$("#gview_itemFileTypeDetails_openWater").printArea();
        	}
    	});

        $("#itemFileTypeDetails_small").jqGrid({
            url: "DININGSYS/report/itemFileType/dataSearch_small?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
            datatype: "json",
            colNames: ['大类编号', '品项大类','小类编号','品项小类','折前销售金额','优惠金额','品项金额','制作费用','合计','数量','平均售价'],
            colModel: [
                { name: 'num'},
                { name: 'name'},
                { name: 'smallNum'},
                { name: 'smallName'},
                { name: 'zqje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zrje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'pxje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zzfy',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zhje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'itemFileNumber'},
                { name: 'avgPrice',formatter:'number',formatoptions:{decimalPlaces: 2 }}
            ],
            styleUI : 'Bootstrap',
            rowNum:-1,
            rownumbers: true,
            footerrow:true,
            loadComplete:function () {
                layer.closeAll('loading');
                var $self = $(this),
                    zqje = $self.jqGrid("getCol", "zqje", false, "sum"),
                    zrje = $self.jqGrid("getCol", "zrje", false, "sum"),
                    pxje = $self.jqGrid("getCol", "pxje", false, "sum"),
                    zzfy = $self.jqGrid("getCol", "zzfy", false, "sum"),
                    zhje = $self.jqGrid("getCol", "zhje", false, "sum"),
                    itemFileNumber = $self.jqGrid("getCol", "itemFileNumber", false, "sum");
                $self.jqGrid("footerData", "set", {rn:$self.getGridParam("reccount"),zqje:zqje,zrje: zrje,pxje:pxje,zzfy:zzfy,zhje:zhje,itemFileNumber:itemFileNumber});
            },
            ondblClickRow:function (rowid,iRow,iCol,e) {
                $.when(loadingShades()).then(function () {
                    var rowData = $("#itemFileTypeDetails_small").getRowData(rowid),
                        smallTypeNum = rowData['smallNum'], smallTypeName = rowData['name'];

                    $("#pxxlLabel").text("——" + smallTypeName);
                    $("#clickFlag").val(2);
                    $("#smallTypeNum").val(smallTypeNum);

                    jQuery("#itemFileTypeDetails_item").jqGrid('setGridParam', {url: "DININGSYS/report/itemFileType/dataSearch_item?smallNum=" + smallTypeNum + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val()}).trigger("reloadGrid");
                    $("#itemFileTypeDetails_item").jqGrid("setGridHeight", $(window).height() - 190, true);
                    $("#itemFileTypeDetails_item").jqGrid("setGridWidth", $(window).width(), true);

                    $("#itemFileTypeDetails_item").jqGrid("setGridState", 'visible');
                    $("#itemFileTypeDetails_small").jqGrid("setGridState", 'hidden');
                })
            }
        });
        $("#itemFileTypeDetails_small").jqGrid("setGridState",'hidden');

        $("#itemFileTypeDetails_item").jqGrid({
            url: "DININGSYS/report/itemFileType/dataSearch_item?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
            datatype: "json",
            colNames: ['品项编号', '品项名称','单位','标准价格','折前销售金额','优惠金额','品项金额','制作费用','合计','数量','平均售价','品项小类','品项大类'],
            colModel: [
                { name: 'itemFileNum'},
                { name: 'itemFileName'},
                { name: 'unit'},
                { name: 'standardPrice',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zqje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zrje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'pxje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zzfy',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zhje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'itemFileNumber'},
                { name: 'avgPrice',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'smallName'},
                { name: 'bigName'}
            ],
            styleUI : 'Bootstrap',
            rowNum:-1,
            rownumbers: true,
            footerrow:true,
            loadComplete:function () {
                layer.closeAll('loading');
                var $self = $(this),
                    zqje = $self.jqGrid("getCol", "zqje", false, "sum"),
                    zrje = $self.jqGrid("getCol", "zrje", false, "sum"),
                    pxje = $self.jqGrid("getCol", "pxje", false, "sum"),
                    zzfy = $self.jqGrid("getCol", "zzfy", false, "sum"),
                    zhje = $self.jqGrid("getCol", "zhje", false, "sum"),
                    itemFileNumber = $self.jqGrid("getCol", "itemFileNumber", false, "sum");
                $self.jqGrid("footerData", "set", {rn:$self.getGridParam("reccount"),zqje:zqje,zrje: zrje,pxje:pxje,zzfy:zzfy,zhje:zhje,itemFileNumber:itemFileNumber});
            },
            ondblClickRow:function (rowid,iRow,iCol,e) {
                $.when(loadingShades()).then(function () {
                    var rowData = $("#itemFileTypeDetails_item").getRowData(rowid),
                        itemFileNum = rowData['itemFileNum'], itemFileName = rowData['itemFileName'];

                    $("#pxLabel").text("——" + itemFileName);
                    $("#clickFlag").val(3);
                    $("#itemFileNum").val(itemFileNum);

                    jQuery("#itemFileTypeDetails_openWater").jqGrid('setGridParam', {url: "DININGSYS/report/itemFileType/dataSearch_openWaters?itemFileNum=" + itemFileNum + "&startTime=" + $("#startTime").val() + "&endTime=" + $("#endTime").val()}).trigger("reloadGrid");
                    $("#itemFileTypeDetails_openWater").jqGrid("setGridHeight", $(window).height() - 190, true);
                    $("#itemFileTypeDetails_openWater").jqGrid("setGridWidth", $(window).width(), true);

                    $("#itemFileTypeDetails_openWater").jqGrid("setGridState", 'visible');
                    $("#itemFileTypeDetails_item").jqGrid("setGridState", 'hidden');
                })
            }
        });
        $("#itemFileTypeDetails_item").jqGrid("setGridState",'hidden');

        $("#itemFileTypeDetails_openWater").jqGrid({
            url: "DININGSYS/report/itemFileType/dataSearch_openWaters?startTime="+$("#startTime").val()+"&endTime="+$("#endTime").val(),
            datatype: "json",
            colNames: ['serviceId','areaName','品项名称', '营业流水','服务流水','结算流水','折前销售金额','优惠金额','品项金额',
                        '制作费用','合计','数量','加单时间','结算时间','客位','操作POS','结算价格'],
            colModel: [
                { name:'serviceId',hidden:true},
                { name:'areaName',hidden:true},
                { name: 'name'},
                { name: 'ow_num',width:360},
                { name: 'service_num',width:360},
                { name: 'cw_num',width:300},
                { name: 'zqje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zrje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'pxje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zzfy',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'zhje',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'item_file_number',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'service_time',width:250},
                { name: 'clearing_time',width:250},
                { name: 'seatName'},
                { name: 'posName'},
                { name: 'mdje',formatter:'number',formatoptions:{decimalPlaces: 2 }}
            ],
            styleUI : 'Bootstrap',
            rowNum:-1,
            rownumbers: true,
            footerrow:true,
            loadComplete:function () {
                layer.closeAll('loading');
                var $self = $(this),
                    allRows = $self.jqGrid('getDataIDs'),
                    zqje = $self.jqGrid("getCol", "zqje", false, "sum"),
                    zrje = $self.jqGrid("getCol", "zrje", false, "sum"),
                    pxje = $self.jqGrid("getCol", "pxje", false, "sum"),
                    zzfy = $self.jqGrid("getCol", "zzfy", false, "sum"),
                    zhje = $self.jqGrid("getCol", "zhje", false, "sum"),
                    itemFileNumber = $self.jqGrid("getCol", "item_file_number", false, "sum");
                $self.jqGrid("footerData", "set", {rn:$self.getGridParam("reccount"),zqje:zqje,zrje: zrje,pxje:pxje,zzfy:zzfy,zhje:zhje,item_file_number:itemFileNumber});
                for (var i=0; i < allRows.length; i++) {
                    var rowid = allRows[i];
                    if ( $self.jqGrid('getCell',rowid,'zhje') < 0 ) {
                        $self.jqGrid('setCell',rowid,'pxje','',{color:'red'});
                        $self.jqGrid('setCell',rowid,'zhje','',{color:'red'});
                        $self.jqGrid('setCell',rowid,'item_file_number','',{color:'red'});
                    }
                }
            },
            ondblClickRow:function (rowid,iRow,iCol,e) {
                var $self = $(this),
                    rowData = $self.jqGrid('getRowData', rowid),
                    areaName = rowData['areaName'],
                    seatName = rowData['seatName'],
                    owNum = rowData['ow_num'];

                var rowData = $("#itemFileTypeDetails_openWater").getRowData(rowid),
                    serviceId = rowData['serviceId'],
                    clearingTime = rowData['clearing_time'];

                $.get('DININGSYS/report/itemFileType/dataSearchDetailsIndex/' + serviceId + '/' + clearingTime, function (data) {
                    var index = layer.open({
                        type: 1,
                        title: '单据核对（区域：' + areaName + "     客座：" + seatName + "    营业流水：" + owNum + "）",
                        skin: 'layui-layer-rim',
                        area: ['800px', '700px'],
                        content: data,
                        btn: ['关闭'],
                    });
                })
            }
        });
        $("#itemFileTypeDetails_openWater").jqGrid("setGridState",'hidden');

        resize();

        $(window).resize(function () {
            resize();
        })

        function loadingShades(){
            var index = layer.load(1, {
                shade: [0.5,'#fff']
            });
            return index;
        }

    };

    return{
        pageInit :function () {
            pageInit();
        }
    }

}();