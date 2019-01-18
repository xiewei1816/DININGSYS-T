$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : path+"/DININGSYS/yqshapi/yd/getOnlinePageList",
		colM : "orderId,contactuser,persons,gender,contacttel,createTime,reserveTime,seatType,sectionID,allowShiftSeatType,note",
		queryForm : "query-pan",
		colNames : "id,姓名,人数,性别,电话号码,下单时间,预定时间,客位类型,午餐or晚餐,没包间是否选择大厅,备注",
		colWid : {"id":40},
		formatter: {
			"contactuser": function (v, o, r) {
				if(r.gender == '1'){
				     return v.trim()+"(先生)";
				} else {
				     return v.trim()+"(女士)";
				}	
            },
        	"sectionID": function (v, o, r) {
                if (v == '0') {
                    return '<font color="green">午餐</font>';
                } else {
                    return '<font color="green">晚餐</font>';
                }
            },
            "allowShiftSeatType": function (v, o, r) {
            	console.log(v);
                if (v == '1') {
                    return '<font color="green">是</font>';
                } else {
                    return '<font color="red">否</font>';
                }
            },
            "gender": function (v, o, r) {
                if (v == '2') {
                    return '<font color="red">女</font>';
                } else {
                    return '<font color="green">男</font>';
                }
            },
            "seatType": function (v) {
                if (v == '0') {
                    return '<font color="green">大厅</font>';
                } else if(v == '1'){
                    return '<font color="green">包间</font>';
                } else {
                	return '<font color="green">不限</font>';
                }
            }
        }
	});	  
	
	/**
	 * 隐藏grid中id项
	 */
    $("#grid-table").hideCol("orderId");
    $("#grid-table").hideCol("gender");
//    $("#grid-table").hideCol("seatType");
	
	$("#add").click(function(){
		var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要添加客位的线上预定数据!',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        } else if(selectRow.length >1) {
        	 layer.alert('只能同时添加客位到一条数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
             return;
        }
        
//        var id = grid.jqGrid('getCell',selectRow[0],'id'); //获取id
        
        $("#ydTime").val(grid.jqGrid('getCell',selectRow[0],'reserveTime')+":00");
        var seatType = grid.jqGrid('getCell',selectRow[0],'seatType');
        if(seatType.indexOf("大厅") != -1){
             $("#crId").val("0");	
        } else if(seatType.indexOf("包间") != -1){
        	 $("#crId").val("1");
        } else{
        	 $("#crId").val("-1");
        }
        var contactuser = grid.jqGrid('getCell',selectRow[0],'contactuser');
        $("#name").val(contactuser.substring(0,contactuser.indexOf("(")));
        $("#phone").val(grid.jqGrid('getCell',selectRow[0],'contacttel'));
        $("#ydResoure").val(grid.jqGrid('getCell',selectRow[0],'orderId'));
        $("#time").val(grid.jqGrid('getCell',selectRow[0],'createTime'));
//        $("#sex").val(grid.jqGrid('getCell',selectRow[0],'gender'));
        var sex = grid.jqGrid('getCell',selectRow[0],'gender');
        if(sex.indexOf("男") != -1){
             $("#sex").val("1");	
        }else{
        	 $("#sex").val("2");
        }
        
        $("#number").val(grid.jqGrid('getCell',selectRow[0],'persons'));
        
        var sectionID = grid.jqGrid('getCell',selectRow[0],'sectionID');
        if(sectionID.indexOf("午餐") != -1){
             $("#wOw").val("1");	
        }else{
        	 $("#wOw").val("2");
        }
        
        var allowShiftSeatType = grid.jqGrid('getCell',selectRow[0],'allowShiftSeatType');
        if(allowShiftSeatType.indexOf("是") != -1){
             $("#bsd").val("1");	
        }else{
        	 $("#bsd").val("0");
        }
        $.get(path+"/DININGSYS/yqshapi/yd/getOnlineSeatPage",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'选择预定客位',
                skin: 'layui-layer-rim',
                area: ['80%', '80%'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
                	var seatUlAppend = '';
                	var seatGrid = $("#grid-table-seat");
                	var selectRow = seatGrid.getGridParam("selarrrow");
                	for(var index in selectRow){
                		seatUlAppend += "<li><input type='hidden' name='seatIds["+index+"].seatId' value='"+seatGrid.jqGrid('getCell',selectRow[index],'id')+"'/></li>";
                	}
                	$("#seatUl").html(seatUlAppend);
                	
            		if(!checkUpdVals("editForm")){
            			return;
            		}
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/yqshapi/yd/insertOnline',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
                            	layer.alert("预定成功!",{title :'预定成功!',icon: 6, skin: 'layer-ext-moon'});
                                $("#grid-table").trigger("reloadGrid");
                            } else{
                            	 layer.alert(data.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                            }
                        }
                    });
                    layer.close(addIndex);
                }
            });
        })
    });
    
    $("#del").click(function(){
        var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要拒绝的订单！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }

        var ids = [];
        for(var i = 0;i < selectRow.length;i++){
            var id = grid.jqGrid('getCell',selectRow[i],'orderId');
            ids.push(id);
        }
//        layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
        

        	
    	$.get(path+"/DININGSYS/yqshapi/yd/getRefusePage",function(str){
	            var addIndex = layer.open({
	                type: 1,
	                title:'拒绝原因',
	                skin: 'layui-layer-rim',
	                area: ['70%', '70%'],
	                content: str,
	                btn:['确定','关闭'],
	                yes:function () {
	                	if(!checkUpdVals("editFormRefuse")){
            				return;
            			}

            	        $.ajax({
			                type:'POST',
			                url:path+'/DININGSYS/yqshapi/yd/delOnelineData',
			                data:{ids:ids.toString(),msg:$("msg").val()},
			                dataType:'JSON',
			                beforeSend: function(){
			                	 loading = layer.load(1, {
									  shade: [0.1,'#fff'] //0.1透明度的白色背景
									});
			                },
			                success:function (data) {
			                	layer.close(loading);
			                    if(data.success){
			                    	layer.alert("提交信息成功!",{title :'预定成功!',icon: 6, skin: 'layer-ext-moon'});
			                    	layer.close(addIndex);
			                        $("#grid-table").trigger("reloadGrid");			                	
			                    }else {
			                		layer.alert(data.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			                	}
			                }
				        });
	                }
	            });
	        })
//        })

    });
    
    
    $("#manager").click(function(){
    	window.location.href=path+"/DININGSYS/yqshapi/yd/getYdManager?posId="+$("#posId").val();
    });

    $("#onlineDelGrid").click(function(){
    	window.location.href=path+"/DININGSYS/yqshapi/yd/getOnlineYdRefuseManager";
    });
    
	$("#refresh").click(function(){
		$("#grid-table").trigger("reloadGrid");
	});
});