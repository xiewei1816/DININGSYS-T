$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	
	 var time ={
				format:"YYYY-MM-DD",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				zIndex:29891015,
				choosefun: function(elem,datas){
			         end.minDate = datas; //开始日选好后，重置结束日的最小日期
			    }
	};
	$('#time').jeDate(time);
			
			
	$("#" + pageUtil.tabId).getJqGrid({
		url : path+"/DININGSYS/yqshapi/yd/getPageList",
		colM : "id,ydNum,seatName,name,sex,number,phone,time,ydTime,wOw,state",
		queryForm : "query-pan",
		colNames : "id,预订单号,客位号,姓名,性别,人数,电话号码,下单时间,就餐时间,晚餐或晚餐,状态",
		colWid : {"id":40},
		formatter: {
			"name": function (v, o, r) {
				if(r.sex == '2'){
					if(v.indexOf("(")!=-1){
						return v.trim();
					} else {
						return v.trim()+'(女士)';	
					}
				} else {
					if(v.indexOf("(")!=-1){
						 return v.trim();
					} else {
						return v.trim()+'(先生)';	
					}
				}
				
            },
            "wOw": function (v, o, r) {
				if(r.wOw == '1'){
				     return '午餐';	
				} else {
					 return '晚餐';
				}
            },
            "state":function (v,o,r){
            	if(v == '0'){
				     return '<font color="#000">初始化</font>';	
				} else if(v == '1'){
					 return '<font color="green">已成</font>';	
				} else if(v == '-1'){
					 return '<font color="red">未到</font>';	
				} else if(v == '3'){
					 return '<font color="blue">已通知</font>';	
				}
            }
		}
	});	  
	
	/**
	 * 隐藏grid中id项
	 */
    $("#grid-table").hideCol("id");
    $("#grid-table").hideCol("sex");
	
	$("#add").click(function(){
        $.get(path+"/DININGSYS/yqshapi/yd/addOrUpdate",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'绑定信息',
                skin: 'layui-layer-rim',
                area: ['80%', '80%'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm")){
            			return;
            		}
                    // var verVal = VverificTime($("#wOw").val(),$("#ydTime").val());
					// if(verVal!=0){
					// 	switch(verVal){
					// 		case -1:
					// 			layer.alert("周一到周四中午预定时间不能超过11:30",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					// 		break;
					// 		case -2:
					// 			layer.alert("周一到周四晚上预定时间不能超过17:10",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					// 		break;
					// 		case -3:
					// 			layer.alert("周一到周四晚上预定时间不能小于14:00",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					// 		break;
					// 		case -4:
					// 			layer.alert("周五到周天中午预定时间不能超过10:50",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					// 		break;
					// 		case -5:
					// 			layer.alert("周五到周天晚上预定时间不能超过16:50",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					// 		break;
					// 		case -6:
					// 			layer.alert("周五到周天晚上预定时间不能小于14:00",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
					// 		break;
					// 	}
					// 	return;
					// }
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/yqshapi/yd/insertOrUpdate',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
                                $("#grid-table").trigger("reloadGrid");
                            } else {
                            	 layer.alert(data.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                            }
                        }
                    });
                    layer.close(addIndex);
                }
            });
        })
    });
	
	
	$("#update").click(function(){
		var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        else if(selectRow.length >1){
        	 layer.alert('只能同时修改一行数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
             return;
        }
        
        var id = grid.jqGrid('getCell',selectRow[0],'id'); //获取id
        var state = grid.jqGrid('getCell',selectRow[0],'state'); //获取id
        if(state.indexOf("已成") != -1){
        	 layer.alert('已成订单不能修改',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
             return;
        }
        
        $.get(path+"/DININGSYS/yqshapi/yd/addOrUpdate?id="+id,function(str){
            var addIndex = layer.open({
                type: 1,
                title:'绑定信息',
                skin: 'layui-layer-rim',
                area: ['80%', '80%'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm")){
            			return;
            		}
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/yqshapi/yd/insertOrUpdate',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
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
            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }

        var ids = [];
        for(var i = 0;i < selectRow.length;i++){
            var id = grid.jqGrid('getCell',selectRow[i],'id');
            ids.push(id);
        }
        layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
        
        $.ajax({
                type:'POST',
                url:path+'/DININGSYS/yqshapi/yd/delData',
                data:{ids:ids.toString()},
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        $("#grid-table").trigger("reloadGrid");
                    }
                }
            });

            layer.close(index);
        });

    });

	
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).jqGrid('setGridParam',{page:1,url:path+"/DININGSYS/yqshapi/yd/getPageList",postData:changeJOSNr("query-pan")});
		$("#" + pageUtil.tabId).trigger("reloadGrid");
	});

    $("#export").click(function(){
        var posId = $("#posId").val();
        var state = $("#state").val();
        var search = $("#searchValue").val();
        var time = $("#time").val();

        var url = path+"/DININGSYS/yqshapi/yd/exportXls?posId="+posId+"&state="+state+"&search="+search+"&searchTime="+time;

        window.location.href = url;
    });
	
	 function VverificTime(type,time){
    	var week = new Date().getDay();  
    	if(week>=1 && week <=4){
    		if(type ==1){ //午餐
    			if(time > getNowFormatDate('11','30','00')){
    				return -1;
    			} else {
    				return 0;
    			}
    		} else {
    			if(time > getNowFormatDate('17','10','00')){
    				return -2;
    			}else if(time < getNowFormatDate('14','00','00')){
    				return -3;
    			}else {
    				return 0;
    			}
    		}
    	} else {
    		if(type ==1){ //午餐
    			if(time > getNowFormatDate('10','50','00')){
    				return -4;
    			}else {
    				return 0;
    			}
    		} else {
    			if(time > getNowFormatDate('16','50','00')){
    				return -5;
    			} else if(time < getNowFormatDate('14','00','00')){
    				return -6;
    			}else {
    				return 0;
    			}
    		}
    	}
    }
    
    function getNowFormatDate(hour,mintes,seconds) {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + hour + seperator2 + mintes
	            + seperator2 + seconds;
	    return currentdate;
	} 
	
	$("#online").click(function(){
    	window.location.href=path+"/DININGSYS/yqshapi/yd/getOnlineYdManager?posId="+$("#posId").val();
    });
	
    $("#delGrid").click(function(){
    	window.location.href=path+"/DININGSYS/yqshapi/yd/getYdTrashManager?posId="+$("#posId").val();
    });
});