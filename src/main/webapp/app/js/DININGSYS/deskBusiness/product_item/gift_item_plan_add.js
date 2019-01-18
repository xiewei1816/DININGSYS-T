
var addmIndex;
var gift_item_plan_add = function () {
	
	function pagerInit()
	{
		var id = $("#Pid").val();
		$("#grid-table-2").jqGrid({
	        url:"DININGSYS/DgItemGiftPlan/getAllImportChild?id="+id,
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:300,//高度，表格高度。可为数值、百分比或'auto'
	        width:800,//自动宽
	        rowNum:-1,
	        colNames:["id","品项id","代码","品项名称","赠送数量","启用"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'itemId',index:'itemId', width:'0%', align:'center',hidden:true},
	            {name:'itemCode',index:'itemCode', width:'20%',align:'center'},
	            {name:'name',index:'name', width:'20%',align:'center'},
	            {name:'giftAcount',index:'giftAcount', width:'20%',align:'center',formatter:format3},
	            {name:'enable',index:'enable', width:'20%',align:'center',formatter:format2}
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为id
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        multiselect:true,
	        loadComplete : function() {
	        	
	        	/**
	        	 * 隐藏grid中id项
	        	 */
	        	$('.org-checkbox').click(function () {
	        		   this.blur();  
	        		   this.focus();
	        	});  
	        		 
	        	$(".org-checkbox").change(function() {
	                var id = $(this).attr("id");
	        		if ($(this).prop("checked") == true) {
	        			$(this).val("1");
	        	    }
	        	    else
	        	    {
	        	    	$(this).val("0");
	        	    }
	        	});
	        }
	    });
	    $("#grid-table-2").hideCol("id");
	    
	 
	    
	    var start ={
				format:"YYYY-MM-DD",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				minDate:$.nowDate(0), //设定最小日期为当前日期
				zIndex:29891015,
				choosefun: function(elem,datas){
			         end.minDate = datas; //开始日选好后，重置结束日的最小日期
			    }
		};

		var end ={
				format:"YYYY-MM-DD",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				minDate:$.nowDate(0), //设定最小日期为当前日期
				zIndex:29891015,
				choosefun: function(elem,datas){
			         start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
			    }
		};
		
		$('#start-date').jeDate(start);
		$('#end-date').jeDate(end);
		
		if(id.length == 0) //不修改时
		{
			$("#start-time").val("00:00:00");
			$("#end-time").val("23:59:59");
			$("#giftAmount").val("1");
			$("#giftFrequencyLimit").val("0");
		}
		else
		{
			$('input:checkbox').each(function() {
				if($(this).val() == '1')
				{
					$(this).prop("checked",true);
				}
				else
				{
					$(this).prop("checked",false);
				}
			});
		}
	    var start_time ={
				dateCell:"#start-time",  //目标元素。由于jedate.js封装了一个轻量级的选择器，因此dateCell还允许你传入class、tag这种方式 '#id .class' hh:mm:ss
				format:"hh:mm:ss",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				minDate:'00:00:00', //设定最小日期为当前日期
				zIndex:29891015,
				choosefun: function(elem,datas){
					end_time.minDate = datas; //开始日选好后，重置结束日的最小日期
			    }
		};

		var end_time ={
				dateCell:"#end-time",  //目标元素。由于jedate.js封装了一个轻量级的选择器，因此dateCell还允许你传入class、tag这种方式 '#id .class'
				format:"hh:mm:ss",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				minDate:'00:00:00', //设定最小日期为当前日期
				zIndex:29891015,
				choosefun: function(elem,datas){
					start_time.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
			    }
		};
		
		
		$('#start-time').jeDate(start_time);
		$('#end-time').jeDate(end_time);
		
		$("#basis").show();
		$("#detail").hide();
		
		$("#basis-a").click(function(){
			$(this).parent().addClass("active");
			$(this).parent().siblings().removeClass("active");
			
			
			$("#basis").show();
			$("#detail").hide();
		});
		
		
		$("#detail-a").click(function(){
			$(this).parent().addClass("active");
			$(this).parent().siblings().removeClass("active");
			
			$("#basis").hide();
			$("#detail").show();
		});
		
		
		$("#sub-s").click(function(){
	        layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
	        	var grid = $("#grid-table-2");
		        var selectRow = grid.getGridParam("selarrrow");

		        if(selectRow.length < 1){
		            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
		            return;
		        }
		        var len = selectRow.length;
	            for (var i = 0; i < len; i ++) {
	            	$("#grid-table-2").jqGrid("delRowData", selectRow[0]);
	            }
	            layer.close(index);
	        });
		});
		
		$("#add-s").click(function(){
	    	$.get(path+"/DININGSYS/DgMemberDiscount/toAddPlanDish",function(str){
	            var addsIndex = layer.open({
	                type: 1,
	                title:'连锁会员打折方案-品项选择窗口',
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
		                  url:path+'/DININGSYS/DgItemGiftPlan/getItemGiftAddDish',
		                  data:{chooseTrIds:chooseTrIds.toString()},
		                  dataType:'JSON',
		                  success:function (data) {
		                      if(data.success == true)
		                      {
	                    		  //$("#grid-table").jqGrid("clearGridData"); //先清理
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
		                          layer.close(addsIndex);
		                      }
		                   }
	                   })
	                }
	            });
	        })
	    });
	    
	    
	    $("#add-m").click(function(){
	    	$.get(path+"/DININGSYS/DgItemGiftPlan/addHostItem",function(str){
	    		addmIndex= layer.open({
	                type: 1,
	                title:'促销品项方案-品项选择窗口',
	                skin: 'layui-layer-rim',
	                area: ['850px', '90%'],
	                content: str
	            });
	        })
	    });
	    
	    
	    $("#sub-m").click(function(){
	    	/**
	    	 * 都设置为null
	    	 */
		   	 $("#itemId").val("");
			 $("#itemCode").val("");
			 $("#itemName").val("");
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
			}
			else
			{
				$(this).val('0');
			}
		});
		
	}
    return {
 		pagerInit:function (){
 			pagerInit();
 		}
}

}();

function callBack(id,code,name)
{
	 layer.close(addmIndex); //关闭窗口
	 $("#itemName").val(name);
	 $("#itemCode").val(code);
	 $("#itemId").val(id);
}

function format1(z,x,f){
	if(!z)
    {
		return '否'
    }
	
	if(z == '1')
	{
		return '是';
	}
	else
	{
		return '否';
	}
}

function format2(z,x,f){
	if(f.enable == '1')
	{
		return "<input class='org-checkbox' type='checkbox' checked='true' value='"+z+"' id='org-"+f.id+"'/>";
	}
	else if(f.enable == '0')
	{
		return "<input class='org-checkbox' type='checkbox' value='"+z+"' id='org-"+f.id+"'/>";
	}
}

function format3(z,x,f){
	return "<input class='gift-account' type='text' value='"+z+"' id='gift-account-"+f.id+"'  dete='isnumber'/>";
}