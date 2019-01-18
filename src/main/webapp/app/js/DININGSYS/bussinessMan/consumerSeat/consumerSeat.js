var addmIndex;
var pros = [];//对象数组 ;
$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	/**
	 * 保存星期
	 */
	
	var bffIndex;
	$(".setting1").show();
	$(".setting2").hide();
	
	$("#setting1_btn").click(function(){
		$(this).parent().addClass("active");
		$(this).parent().siblings().removeClass("active");
		$(".setting1").show();
		$(".setting2").hide();
	});
	
	
	$("#setting2_btn").click(function(){
		$(this).parent().addClass("active");
		$(this).parent().siblings().removeClass("active");
		$(".setting2").show();
		$(".setting1").hide();
	});
	
	
    /**
	初始化所有radio
     **/  
	$('input:radio').each(function() {
		if ($(this).prop("checked") == true) {
			bffIndex = $(this).val();
			var par = $(this).parent().parent();
			if($(this).val() == '1') //权限控制
			{
				par.siblings().find("[isshow_extra]").attr("disabled","disabled");
				par.siblings().find("[isshow]").attr("disabled","disabled");
			}
			else
			{
				par.find("[isshow]").removeAttr("disabled");
				par.siblings().find("[isshow_extra]").removeAttr("disabled","disabled");
				par.siblings().find("[isshow]").attr("disabled","disabled");
			}
		}
	});
	
	
	$("#bffGdPro option").each(function(){
		var pro = {
				id:$(this).val(),
				name:$(this).html()
		};
		pros.push(pro);
	});
	
	$("#save").click(function(){
    	/**
    	 * 验证表单
    	 */
    	if(!checkUpdValForms("editForm"))
    	{
    		return;
    	}
    	
        layer.confirm('确认保存当前客位管理数据?', {icon: 3, title:'提示'}, function(index){
        	
        	
        	 var send='[';
        	 var rows = $("#grid-table-2").jqGrid('getRowData');
        	 var hava = false;
             if(rows.length > 0){
            	   hava = true;
                   for (var i in rows) {
                	   var id = rows[i].id;
                	   var itemId = rows[i].itemId;
                	   var itemCode = rows[i].itemCode;
                	   var count = $("#count-"+id).val();
                	   var useOpenCounter = $("#use-"+id).val();
                	   send +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",\"count\":\""+count+"\",\"useOpenCounter\":\""+useOpenCounter+"\"},";
                }
            }	      
            if(hava)
            {
            	send = send.substring(0,send.length-1);
            }
        	send += "]";
        	$("#seatId").val($("#seat").val());
        	
        	
        	
        	var sendWeek='[';
        	var weekRows = $("#grid-table-3").jqGrid('getRowData');
        	
        	if(weekRows.length > 0){
                for (var i in weekRows) {
                	var faId = $("#sele-"+weekRows[i].id).val();
                	var id = weekRows[i].id; //品项id
                	var week = weekRows[i].week; //品项id
                	var itemCode = weekRows[i].itemCode;//品项编码
                	sendWeek +="{\"week\":\""+week+"\",\"id\":\""+id+"\",\"faId\":\""+faId+"\"},";
                }
            }	 
        	if(weekRows.length >0)
        	{
        		sendWeek = sendWeek.substring(0,sendWeek.length-1);	
        	}
        	sendWeek += "]";
        	
        	$("#bffWeekProD").val(sendWeek);
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/consumerSeatManager/saveConsumerSeatMan',
                    data:$("#editForm").serialize(),
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                        	  $.ajax({
                                  type:'POST',
                                  url:path+'/DININGSYS/consumerSeatManager/saveConsumerSeatNext',
                                  data:{seatId:$("#seat").val(),content:send},
                                  dataType:'JSON',
                                  success:function (data) {
                                      if(data.success){
                                          layer.close(index);
                                          layer.msg("数据已保存",{time:1000});
                                      }
                                  }
                              })
                        }
                    }
                })
        });
	});
	
    
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/consumerSeatManager/exportSeatXls?id="+$("#id").val();
	});
    
    
    $("#refresh").click(function(){
        $("#grid-table-1").trigger("reloadGrid");
    });
    
	$("#grid-table-2").jqGrid({
        url:path+"/DININGSYS/consumerSeatManager/getConsumerSeatItem?id="+$("#seat").val(),
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:230,//高度，表格高度。可为数值、百分比或'auto'
        width:400,//自动宽
        rowNum:-1,
        colNames:["id","品项id","编号","品项名称","数量","按开单人数"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'itemId',index:'itemId', width:'20%',align:'center',hidden:true},
            {name:'itemCode',index:'itemCode', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'count',index:'count', width:'20%',align:'center',formatter:format2},
            {name:'useOpenCounter',index:'useOpenCounter', width:'20%',align:'center',formatter:format1},
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
        	$("#grid-table-2 tr.jqgrow td").css({"height":"15px","padding":"1px"});
        	$("#gbox_grid-table-2 tr.ui-jqgrid-labels th").css({"height":"15px","padding":"3px"});
        	
        	initCheckbox();
        }
    });
    $("#grid-table-2").hideCol("id");
    
    
	$("#grid-table-3").jqGrid({
        url:path+"/DININGSYS/consumerSeatManager/getConsumerWeekItem?id="+$("#seat").val(),
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:140,//高度，表格高度。可为数值、百分比或'auto'
        width:280,//自动宽
        rowNum:-1,
        colNames:["id","星期","包房收费方案id","包房收费方案"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'week',index:'week', width:'20%',align:'center'},
            {name:'faId',index:'faId', width:'20%',align:'center',hidden:true},
            {name:'faName',index:'faName', width:'20%',align:'center',formatter:formatSele}
        ],
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
        	$("#grid-table-3 tr.jqgrow td").css("height","15px").css({"height":"15px","padding":"3px"});
        	$("#gbox_grid-table-3 tr.ui-jqgrid-labels th").css({"height":"15px","padding":"3px"});
        	
        	if(bffIndex == 5)
        	{
                $("#grid-table-3").find("[isshow_grid]").removeAttr("disabled");
            }
        	else
        	{
        		$("#grid-table-3").find("[isshow_grid]").attr("disabled","disabled");
        	}
        }
    });
    $("#grid-table-3").hideCol("id");
    
	$("#bf-fa").click(function(){
		
		$.get(path+"/DININGSYS/consumerSeatManager/toBfPro",function(str){
            var addsIndex = layer.open({
                type: 1,
                title:'消费区域客位-包房收费方案',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
                	layer.close(addsIndex);
                }
            })
		})	
	});
    
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
	                  url:path+'/DININGSYS/consumerSeatManager/getSeatAddItem',
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
	                    	  
	                    	  $("#grid-table-2 tr.jqgrow td").css("height","15px").css("padding","1px");
	                          $("#gbox_grid-table-2 tr.ui-jqgrid-labels th").css("height","15px").css("padding","3px");
	                          initCheckbox();
	                          initCheckVals();
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

		
	    $("#add-m1").click(function(){
	    	$.get(path+"/DININGSYS/DgItemGiftPlan/addHostItem",function(str){
	    		addmIndex= layer.open({
	                type: 1,
	                title:'包房费品项-品项选择窗口',
	                skin: 'layui-layer-rim',
	                area: ['850px', '90%'],
	                content: str,
	                yes:function () {
	                	layer.close(addmIndex);
	                }
	            });
	        })
	    });
    
	
    
    $("input:radio").change(function() {
		if ($(this).prop("checked") == true) {
			var par = $(this).parent().parent();
			if($(this).val() == '1') //权限控制
			{
				par.siblings().find("[isshow_extra]").attr("disabled","disabled");
				par.siblings().find("[isshow]").attr("disabled","disabled");
                $("#grid-table-3").find("[isshow_grid]").attr("disabled");
            }
			else
			{
				par.find("[isshow]").removeAttr("disabled");
				par.siblings().find("[isshow_extra]").removeAttr("disabled","disabled");
				par.siblings().find("[isshow]").attr("disabled","disabled");
				if($(this).val() == 5)
	        	{
	        		$("#grid-table-3").find("[isshow_grid]").removeAttr("disabled");
	        	}
	        	else
	        	{
	        		$("#grid-table-3").find("[isshow_grid]").attr("disabled","disabled");
	        	}
			}
		}
	});
    //输入值验证
	initCheckVals();
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
			
		}
		else
		{
			$(this).val('0');
		}
	});
	
    
});



function callBack(id,code,name)
{
	 layer.close(addmIndex); //关闭窗口
	 $("#bffItemName").val(name);
	 $("#bffItemId").val(id);
}

function initCheckbox()
{
  	$('.use-checkbox').click(function () {
		   this.blur();  
		   this.focus();
  	});  
  		 
  	$(".use-checkbox").change(function() {
  		if ($(this).prop("checked") == true) {
  			$(this).val("1");
  	    }
  	    //点击之后变为不是打钩时触发
  	    else
  	    {
  			$(this).val("0");
  	    }
  	});	
}

function format1(v,x,r){
	if(r.useOpenCounter == '1')
	{
		return "<input class='use-checkbox' type='checkbox' checked='true'  id='use-"+r.id+"' value='"+r.useOpenCounter+"'/>";
	}
	else if(r.useOpenCounter == '0')
	{
		return "<input class='use-checkbox' type='checkbox' id='use-"+r.id+"'  value='"+r.useOpenCounter+"'/>"
	}
}
function format2(v,x,r){
		return "<input class='count-text number' type='text' checked='true'  id='count-"+r.id+"' value='"+r.count+"' dete='isnumber'/>";
}

function formatSele(c,o,r){
	var ret="<select id='sele-"+r.id+"'  isshow_grid>";
	if(c == '0')
	{
		ret +="<option value='0' selected></option>";
	}
	else
	{
		ret +="<option value='0'></option>";
	}
	for(var i in pros)
	{
		if(r.faId == pros[i].id)
		{
			ret +="<option value='"+pros[i].id+"' selected>"+pros[i].name+"</option>";	
		}
		else
		{
			ret +="<option value='"+pros[i].id+"'>"+pros[i].name+"</option>";
		}
	}
    ret +="</select>";
    return ret;
}