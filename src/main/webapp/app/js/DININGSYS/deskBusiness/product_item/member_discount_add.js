var member_discount_add = function () {
	
	function pagerInit()
	{
		var id = $("#Pid").val();
		/*$("#grid-table-1").jqGrid({
	        url:"DININGSYS/DgMemberDiscount/getOrgChild?id="+id,
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:100,//高度，表格高度。可为数值、百分比或'auto'
	        width:800,//自动宽
	        rowNum:-1,
	        colNames:["id","选择","所属机构"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'enable',index:'enable', width:'5%',align:'center',formatter:format2},
	            {name:'name',index:'name', width:'20%',align:'center'},
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为id
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        loadComplete : function() {
	        	*//**
	        	 * 隐藏grid中id项
	        	 *//*
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
	    });*/
	    /*$("#grid-table-1").hideCol("id");*/
	    
	    
		$("#grid-table-2").jqGrid({
	        url:"DININGSYS/DgMemberDiscount/getAllImportChild?id="+id,
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:300,//高度，表格高度。可为数值、百分比或'auto'
	        width:800,//自动宽
	        rowNum:-1,
	        colNames:["id","品项id","代码","品项名称","套餐","标准价格","方案价格","启用","价格不一致"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'itemId',index:'itemId', width:'0%', align:'center',hidden:true},
	            {name:'itemCode',index:'itemCode', width:'20%',align:'center'},
	            {name:'name',index:'name', width:'20%',align:'center'},
	            {name:'tc',index:'tc', width:'20%',align:'center',formatter:format1},
	            {name:'sPrice',index:'sPrice', width:'20%',align:'center'},
	            {name:'price',index:'price', width:'20%',align:'center'},
	            {name:'enable',index:'enable', width:'20%',align:'center',formatter:format3},
	            {name:'consistent',index:'consistent', width:'20%',align:'center'},
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
	        	$('.item-checkbox').click(function () {
	        		   this.blur();  
	        		   this.focus();
	        	});  
	        		 
	        	$(".item-checkbox").change(function() {
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
	    
	 
	    
//	    $("#set-s").click(function(){
//	    	  var discount = $(".discount").val();
//	    	  if(discount.length<0)
//	    	  {
//	    		  layer.alert('请设置折扣比例',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
//	    		  return;
//	    	  }
//	      	  var grid = $("#grid-table-1");
//	    	  
//	    	  var rows = grid.jqGrid('getRowData');
//	          if(rows.length > 0){
//	                for (var i in rows) {
//	                	var getRow = rows[i];//获取当前的数据行  
//	       		        var id = getRow.id;
//	       		     	grid.jqGrid('setCell',id, 'discount', discount);
//	              }
//	          }
//		});
	    
	    
	    
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
		
		if(id.length == 0)
		{
			$("#start-time").val("00:00:00");
			$("#end-time").val("23:59:59");
			$("#inlineRadio1").prop("checked",true);
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
		
		$("#set-s").click(function(){
			var grid = $("#grid-table-2");
			var priceType = $("input[name='price-type']:checked").val();
			if(priceType == '1') //比例设置
		    {
					if(!checkUpdVal(".discount"))
					{
						return;
					}
				  var discount = $(".discount").val();
		    	  var rows = grid.jqGrid('getRowData');
		          if(rows.length > 0){
		                for (var i in rows) {
		                	var getRow = rows[i];//获取当前的数据行  
		       		        var id = getRow.id;
		       		        var sPrice = getRow.sPrice;
		       		        var nowPrice =  ((sPrice*discount)/100).toFixed(2); //保留两位小数
		       		     	grid.jqGrid('setCell',id, 'price', nowPrice);
		              }
		          }
		    }
			else //金额设置
			{
				
				if(!checkUpdVal(".moeny"))
				{
					return;
				}
				  var moeny = $(".moeny").val();
		    	  var rows = grid.jqGrid('getRowData');
		          if(rows.length > 0){
		                for (var i in rows) {
		                	var getRow = rows[i];//获取当前的数据行  
		       		        var id = getRow.id;
		       		     	grid.jqGrid('setCell',id, 'price', moeny);
		              }
		          }
			}
			
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
		                  url:path+'/DININGSYS/DgMemberDiscount/getMealTimeAddDish',
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
	    
	    
		var week = $("#week").val();
		if(week.length >0){
			var sub_weeks = week.split("-");
			for(var i in sub_weeks)
			{
				if(sub_weeks[i] == '1')
				{
					var index = Number(i)+1;
					$("#week"+index).prop("checked",true);
				}
				else if(sub_weeks[i] == '0')
				{
					var index = Number(i)+1;
					$("#week"+index).prop("checked",false);
				}
			}
		}
		
		/*$("#sele-all").click(function(){
			  var grid = $("#grid-table-1");
	    	  var rows = grid.jqGrid('getRowData');
	          if(rows.length > 0){
	                for (var i in rows) {
	                	var getRow = rows[i];//获取当前的数据行  
	       		        var id = getRow.id;
	       		        $("#org-"+id).prop("checked",true);
	              }
	          }
		});*/
	    
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
	if(f.enable == '1')
	{
		return "<input class='item-checkbox' type='checkbox' checked='true' value='"+z+"' id='item-"+f.id+"'/>";
	}
	else if(f.enable == '0')
	{
		return "<input class='item-checkbox' type='checkbox' value='"+z+"' id='item-"+f.id+"'/>";
	}
}