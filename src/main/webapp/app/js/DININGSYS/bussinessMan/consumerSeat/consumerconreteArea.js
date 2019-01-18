$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	/**
	 * 保存星期
	 */
	
	$("#save").click(function(){
        layer.confirm('确认保存当前消费区域管理数据?', {icon: 3, title:'提示'}, function(index){
        	 var send='[';
        	 var rows = $("#grid-table-2").jqGrid('getRowData');
        	 var hava = false;
             if(rows.length > 0){
            	   hava = true;
                   for (var i in rows) {
                	   var itemId = rows[i].itemId;
                	   var itemCode = rows[i].itemCode;
                	   send +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\"},";
                }
            }	      
            if(hava)
            {
            	send = send.substring(0,send.length-1);
            }
        	send += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/consumerSeatManager/saveConsumerAreaMan',
                    data:$("#editForm").serialize(),
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                        	  $.ajax({
                                  type:'POST',
                                  url:path+'/DININGSYS/consumerSeatManager/saveConsumerAreaManNext',
                                  data:{areaId:$("#area_id").val(),content:send},
                                  dataType:'JSON',
                                  success:function (data) {
                                      if(data.success){
                                          layer.close(index);
                                      }
                                  }
                              })
                        }
                    }
                })
        });
	});
	
	
	
    
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/consumerSeatManager/exportConAreaXls?areaId="+$("#areaId").val();
	});
    
    
    $("#refresh").click(function(){
        $("#grid-table-2").trigger("reloadGrid");
        $("#grid-table-3").trigger("reloadGrid");
    });
    
	$("#grid-table-2").jqGrid({
        url:path+"/DININGSYS/consumerSeatManager/getConsumerAreaItem?id="+$("#area_id").val(),
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:160,//高度，表格高度。可为数值、百分比或'auto'
        width:300,//自动宽
        rowNum:-1,
        colNames:["id","itemid","编号","品项名称"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center',hidden:true},
            {name:'itemId',index:'itemId', width:'20%',align:'center',hidden:true},
            {name:'itemCode',index:'itemCode', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
        ],
        multiselect:true,
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        loadComplete: function() {
        	/**
        	 *改变行高
        	 */
        	$("#grid-table-2 tr.jqgrow td").css({"height":"15px","padding":"3px"});
        	$("#gbox_grid-table-2 tr.ui-jqgrid-labels th").css({"height":"15px","padding":"3px"});
        }
    });
    $("#grid-table-2").hideCol("id");
    
    
	$("#grid-table-3").jqGrid({
        url:path+"/DININGSYS/consumerSeatManager/getAllConsumerSeatItem?id="+$("#area_id").val(),
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei/2,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        rowNum:-1,
        colNames:["id","编号","客位名称","所属消费区域","开单自动增加品项数目","是否收取","收取方式"
                  ,"收取金额","是否收取","收取方式","上限","是否收取","收取方式","收取金额","包房费品项"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'seatCode',index:'seatCode', width:'20%',align:'center'},
            {name:'seatName',index:'seatName', width:'20%',align:'center'},
            {name:'areaString',index:'areaString', width:'20%',align:'center'},
            {name:'itemCount',index:'itemCount', width:'20%',align:'center'},
            {name:'zdxf',index:'zdxf', width:'20%',align:'center',formatter:formatIS},
            {name:'zdxfType',index:'zdxfType', width:'20%',align:'center',formatter:formatZdxf},
            {name:'zdxfMoney',index:'zdxfMoney', width:'20%',align:'center'},
            {name:'fwf',index:'fwf', width:'20%',align:'center',formatter:formatIS},
            {name:'fwfType',index:'fwfType', width:'20%',align:'center',formatter:formatFwf},
            {name:'fwfSx',index:'fwfSx', width:'20%',align:'center'},
            {name:'bff',index:'bff', width:'20%',align:'center',formatter:formatIS},
            {name:'bffType',index:'bffType', width:'20%',align:'center',formatter:formatBff},
            {name:'bffGd',index:'bffGd', width:'20%',align:'center'},
            {name:'bffItemName',index:'bffItemName', width:'20%',align:'center'}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        }
    });
	
	jQuery("#grid-table-3").jqGrid('setGroupHeaders', {
	    useColSpanStyle: true,
	    groupHeaders:[
	        {startColumnName:'zdxf', numberOfColumns:3, titleText: '最低消费'},
	        {startColumnName:'fwf', numberOfColumns: 3, titleText: '服务费'},
	        {startColumnName:'bff', numberOfColumns: 4, titleText: '包房费'}
	    ]
	});
	
    $("#grid-table-3").hideCol("id");
	
	$("#btn-add").click(function(){
	    	$.get(path+"/DININGSYS/consumerSeatManager/toAddItem?grid=grid-table-2&yxdz=0",function(str){
	            var addsIndex = layer.open({
	                type: 1,
	                title:'消费区域限售品项-品项选择窗口',
	                skin: 'layui-layer-rim',
	                area: ['850px', '90%'],
	                content: str,
	                btn:['确定','关闭'],
	                yes:function () {
		                  var rows = $("#grid-table-5").jqGrid('getRowData'),
		                  chooseTrIds = [];
			              if(rows.length > 0){
			                    for (var i in rows) {
			                        chooseTrIds.push(rows[i].id);
			                  }
			              }	                
		                  $.ajax({
		                  type:'POST',
		                  url:path+'/DININGSYS/DgMemberDiscount/getMealTimeAddDish',
		                  data:{chooseTrIds:chooseTrIds.toString()},
		                  dataType:'JSON',
		                  success:function (data) {
		                      if(data.success == true)
		                      {
		                    	  var hava_rows = $("#grid-table-2").jqGrid('getRowData');
		                    	  havaIds = [];
		                    	  gridTableIds = [];
		    		              if(hava_rows.length > 0){
		    		                    for (var i in hava_rows) {
		    		                    	havaIds.push(hava_rows[i].itemId);
		    		                    	gridTableIds.push(hava_rows[i].id);
		    		                  }
		    		              }	 
		    		              
		                    	  //先删除没有的项
		                    	  for (var i in havaIds) { 
		                    		    var ishava = false;
		                    		    for(var j in data.list)
		                    		    {
		                  		    		if(havaIds[i] == data.list[j].itemId)
		                  		    		{
		                  		    			ishava = true;
		                  		    			break;
		                  		    		}		
		                    		    }
		                    		    if(!ishava) //找到当前表格中没有,就添加数据
		                    		    {
		                    		  		$("#grid-table-2").delRowData(gridTableIds[i]);
		                    		    }
				                  }
		                    	  
		                    	 //再增加没有的项
		                    	  for (var i in data.list) { 
		                    		    var ishava = false;
		                    		    for(var j in havaIds)
		                    		    {
	                    		    		if(havaIds[j] == data.list[i].itemId)
	                    		    		{
	                    		    			ishava = true; //存在就不增加
	                    		    		}		
		                    		    }
		                    		    if(!ishava) //找到当前表格中没有,就添加数据
		                    		    {
					                        $("#grid-table-2").addRowData(data.list[i].id, data.list[i],"first");
		                    		    }
				                  }
		                    	  
		                    	  $("#grid-table-2 tr.jqgrow td").css({"height":"15px","padding":"3px"});
		                          $("#gbox_grid-table-2 tr.ui-jqgrid-labels th").css({"height":"15px","padding":"3px"});
		                          layer.close(addsIndex);
		                      }
		                   }
	                   })
	                }
	            });
	        })
		
		
	});
	
	/**
	 * 删除指定项
	 */
    $("#btn-sub").click(function(){
		var grid = $("#grid-table-2");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length < 1){
            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
        	 var len = selectRow.length;
             for (var i = 0; i < len; i ++) {
             	grid.jqGrid("delRowData", selectRow[0]);
             }
             layer.close(index);
        });
	});
    
    initCheckVals();
    
    /**
     * 清空
     */
    $("#btn-clear").click(function(){
		var grid = $("#grid-table-2");
        layer.confirm('确认删除所有品项?', {icon: 3, title:'提示'}, function(index){
        	 grid.jqGrid("clearGridData");
             layer.close(index);
        });
	});
    
	 /**
	初始化所有checkbox
     **/  
	$('input:checkbox').each(function() {
		if ($(this).prop("checked") == true) {
			$(this).val('1');
		}
		else
		{
			$(this).val('0');
		}
	});
    $('input:checkbox').click(function () {
		 this.blur();  
		 this.focus();
	});  
		 
	$("input:checkbox").change(function() {
		if ($(this).prop("checked") == true) {
			$(this).val('1');
			if($(this).attr("id")=='bffFree')
			{
				$("#bffTimeFree").removeAttr("disabled");
				$("#bffSurplusRemind").removeAttr("disabled");
			}
		}
		else
		{
			$(this).val('0');
			if($(this).attr("id")=='bffFree')
			{
				$("#bffTimeFree").attr("disabled","disabled");
				$("#bffSurplusRemind").attr("disabled","disabled");
			}
		}
	});
	
});



function formatIS(v,x,r){
	if(v == '1' || v == null)
	{
		return "否";
	}
	else
	{
		return "是"
	}
	
}
function formatZdxf(v,x,r){
	if(v == '1')
	{
		return "按每客位";
	}
	else if(v == '2')
	{
		return "按每客位人数";
	}
	else
	{
		return "";
	}
}
function formatFwf(v,x,r){
	if(v == '1' || v == null)
	{
		return "不收取";
	}
	else if(v == '2')
	{
		return "按固定金额"
	}
	else if(v == '3')
	{
		return "按消费比例"
	}
	else if(v == '4')
	{
		return "按客位人数"
	}
}
function formatBff(v,x,r){
	if(v == '1' || v == null)
	{
		return "不收取";
	}
	else if(v == '2')
	{
		return "按固定金额"
	}
	else if(v == '3')
	{
		return "按客位人数收取"
	}
	else if(v == '4')
	{
		return "固定包房收费方案"
	}
	else if(v == '5')
	{
		return "一周内设置不同的包房收费方案"
	}
}