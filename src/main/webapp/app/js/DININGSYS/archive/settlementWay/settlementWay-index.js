$(function(){
		pageUtil.pageInit({
			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});

	$(".add").click(function(){
		pageUtil.addOper({
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
				$("#editForm [notdis]").prop("disabled",false);
				return checkName();
			},
			saveUrl : "DININGSYS/settlementWay/saveSettlementWay", 						//添加保存的地址(必要)。
			saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#update").click(function(){
		pageUtil.updOper({
			updUrl : "DININGSYS/settlementWay/getSettlementWayByID",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				//初始化结算方式
				var type = data.settlementWay.type;
				$("#typeEdit").val(type);
				pageUtil.getUpdingData(data.settlementWay);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
				$("#editForm [notdis]").prop("disabled",false);
				//return checkName();
			},
			saveUrl : "DININGSYS/settlementWay/saveSettlementWay", 									//添加保存的地址(必要)。
			saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/settlementWay/deleteSettlementWay",										//删除数据提交的后台地址(必要)。
			success : function(){												//删除数据成功之后执行的方法(非必要)。
				
			}
		});
	});

	$("#zksz").click(function () {
        var grid = $("#grid-table"),
            rowId = grid.getGridParam("selrow"),
            type = grid.jqGrid('getCell', rowId, 'type'),
            wayId = grid.jqGrid('getCell', rowId, 'id'),
            dklx = grid.jqGrid('getCell', rowId, 'dklx'),
            qyxdksz = grid.jqGrid('getCell', rowId, 'qyxdksz');

            if((type == "券" || dklx == 1) && qyxdksz == 1){
                layer.alert("抵扣品项无限制");
                return;
            }

        	if((type == "券" || dklx == 1) && qyxdksz != 1){
                $.get('DININGSYS/settlementWay/wayItemFileType',{id:wayId,type:qyxdksz},function (data) {
                    var pageIndex = layer.open({
                        type: 1,
                        title:"抵扣品项设置",
                        skin: 'layui-layer-rim',
                        area: ['800px', '700px'],
                        content: data,
                        btn:['保存','关闭'],
                        yes:function () {
                            var rowDatas;
                            if(qyxdksz == 2 || qyxdksz == 3){
                                rowDatas = $("#itemFileTypeYxTable").jqGrid('getRowData');
                            }else{
                                rowDatas = $("#itemFileYxTable").jqGrid('getRowData');
                            }

                            var bigTypeArray = new Array();

                            $.each(rowDatas,function () {
                                bigTypeArray.push($(this)[0]['id']);
                            })

                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/settlementWay/upateWayItemSet',
                                data:{wayId:wayId,ids:bigTypeArray.toString()},
                                dataType:'JSON',
                                success:function(data){
                                    if(data.success){
                                        layer.close(pageIndex);
                                        layer.msg("数据已保存");
                                    }else{
                                        layer.msg("数据保存失败");
                                    }
                                }
                            })
                        }
                    });
                })
			}else{
        	    layer.alert("只有券以及支持折扣的支付方式支持此品项设置");
            }
    })


    $("#" + pageUtil.tabId).jqGrid({
        url: "DININGSYS/settlementWay/getPageList",
        datatype: "json",
        colNames: ["id","编号","结算方式名称","结算方式类型","汇率","常用","dklx",
			"抵扣类型","券面值/抵扣百分比","qyxdksz","抵扣品项","停用","本位币","单独使用","系统保留",
			"允许找零","计入实际收入比例","不计入实际收入比例"],
        colModel: [
            { name: "id",hidden:true},
            { name: "number" },
            { name: "name"},
            { name: "type"},
            { name: "exchangeRate",width:90},
            { name: "isCommon",width:90,formatter:function (v) {
                if (v == '0') {
                    return '<font color="green">否</font>';
                } else {
                    return '<font color="red">是</font>';
                }
            }},
            { name: "dklx",hidden:true},
            { name: "dklxName"},
            { name: "rollFaceValue"},
            { name: "qyxdksz",hidden:true},
            { name: "qyxdkszName"},
            { name: "isDisabled",width:90,formatter:function (v) {
                if (v == '0') {
                    return '<font color="green">否</font>';
                } else {
                    return '<font color="red">是</font>';
                }
            }},
            { name: "isCurrency",width:100,formatter:function (v) {
                if (v == '0') {
                    return '<font color="green">否</font>';
                } else {
                    return '<font color="red">是</font>';
                }
            }},
            { name: "isAloneUse",width:110,formatter:function (v) {
                if (v == '0') {
                    return '<font color="green">否</font>';
                } else {
                    return '<font color="red">是</font>';
                }
            }},
            { name: "isKeepSystem",width:110,formatter:function (v) {
                if (v == '0') {
                    return '<font color="green">否</font>';
                } else {
                    return '<font color="red">是</font>';
                }
            }},
            { name: "isAllowChange",width:110,formatter:function (v) {
                if (v == '0') {
                    return '<font color="green">否</font>';
                } else {
                    return '<font color="red">是</font>';
                }
            }},
            { name: "actualIncomeRatio"},
            { name: "notActualIncomeRatio"}
        ],
        viewrecords: true,
        autoencode: true,
		autoWidth:true,
		rowNumber:20,
		rowList:[20,50,100],
        styleUI : 'Bootstrap',
		pager:"grid-pager"
    });

    $("#" + pageUtil.tabId).jqGrid("setGridWidth",$(window).width()-20,true);
    $("#" + pageUtil.tabId).jqGrid("setGridHeight",$(window).height()-200,true);

	$(window).resize(function () {
        $("#" + pageUtil.tabId).jqGrid("setGridWidth",$(window).width()-20,true);
        $("#" + pageUtil.tabId).jqGrid("setGridHeight",$(window).height()-200,true);
    })


	/* ******************************  排序  **************************** */	
	$("#rank").click(function(){
		layer.open({
			  type: 2,
			  title: '结算方式排序',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/settlementWay/goRank',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});
	});
});

function checkName(){
	var istrue = true;
	var id = $(".edit_id").val();
	$.ajax({
		url : "DININGSYS/settlementWay/checkSettlementWayByName",
		data : {"id": id == "" ? 0 : id,checkName:$("#edit_named").val()},
		dataType : "json",
		type : "post",
		async : false,
		success : function(d){
			if(d.state == 2){
				layer.alert('你提交的结算方式的名称已经存在，请重新填写！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				istrue = false;
			}
		}
	});
	return istrue;
}
