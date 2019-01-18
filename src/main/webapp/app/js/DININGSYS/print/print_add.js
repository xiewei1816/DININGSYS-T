var print_add = function () {
	function pagerInit()
	{
		pageUtil.pageInit({
			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
			}
		});
		var printers = [];
		$("#printName option").each(function(){
			var printer = {
					num:$(this).attr("num"),
					name:$(this).val()
			};
			printers.push(printer);
		});
		
		var areaIds = $("#areaIds").val();
		if(areaIds != null && areaIds.length > 0)
		{
			var areas = areaIds.split(",");
			$(".area-checkbox").each(function(){
				for(var i in areas)
				{
					if(areas[i] == $(this).attr("id"))
					{
						$(this).prop("checked","checked");
						break;
					}
				}
			});
		}
		
		seleType = $("#sele-type").val();
		var Pid = $("#Pid");
		
		
		$("#grid-table-3").jqGrid({
	        url:"DININGSYS/PrintManger/getPrintItem",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:260,//高度，表格高度。可为数值、百分比或'auto'
	        width:816,//自动宽
	        colNames:["id","品项id","代码","品项名称"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'itemId',index:'itemId', width:'0%', align:'center',hidden:true},
	            {name:'num',index:'num', width:'20%',align:'center'},
	            {name:'name',index:'name', width:'20%',align:'center'}
	        ],
	        postData:{"id":Pid.val(),"type":"1"},
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        rowNum:-1,//每页显示记录数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为blackId
	            repeatitems : false
	        },
	        multiselect:true,
	        loadComplete : function() {
	        	setClick();
			}
	    });
		
		
		$("#grid-table-3-gate").jqGrid({
	        url:"DININGSYS/PrintManger/getPrintItem",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:260,//高度，表格高度。可为数值、百分比或'auto'
	        width:816,//自动宽
	        colNames:["id","品项id","代码","小类名称"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'itemId',index:'itemId', width:'0%', align:'center',hidden:true},
	            {name:'num',index:'num', width:'20%',align:'center'},
	            {name:'name',index:'name', width:'20%',align:'center'}
	        ],
	        postData:{"id":Pid.val(),"type":"2"},
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        rowNum:-1,//每页显示记录数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为blackId
	            repeatitems : false
	        },
	        multiselect:true,
	        loadComplete : function() {
	        	setClick();
			}
	    });
		
		
	    $("#grid-table-3").hideCol("id");
	    $("#grid-table-3").hideCol("pId");
	    
	    
	    $("#grid-table-3-gate").hideCol("id");
		var inputSele = $(".input-sele");
		if(inputSele.val()=='1')
		{
			$("#grid-table-3").closest(".ui-jqgrid").show();
			$("#grid-table-3-gate").closest(".ui-jqgrid").hide();	
		}
		else
		{
			$("#grid-table-3").closest(".ui-jqgrid").hide();
			$("#grid-table-3-gate").closest(".ui-jqgrid").show();
		}
	    
	   
	    
	    $("#add-s").click(function(){
	    	$.get("DININGSYS/PrintManger/toAddPrintDish?type="+inputSele.val(),function(str){
	            var addsIndex = layer.open({
	                type: 1,
	                title:'打印机-品项选择窗口',
	                skin: 'layui-layer-rim',
	                area: ['850px', '600px'],
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
		                  url:'DININGSYS/PrintManger/getPrintItemByAdd',
		                  data:{chooseTrIds:chooseTrIds.toString(),type:inputSele.val()},
		                  dataType:'JSON',
		                  success:function (data) {
		                      if(data.success == true)
		                      {
		                    	  if(inputSele.val() == '1')
		                    	  {
		                    		  
			                    	  $("#grid-table-3").jqGrid("clearGridData"); //先清理
			                    	  for (var i in data.list) {
					                        $("#grid-table-3").addRowData(data.list[i].id, data.list[i],"first");
					                  }  
		                    	  }
		                    	  else
		                    	  {
		                    		  $("#grid-table-3-gate").jqGrid("clearGridData"); //先清理
			                    	  for (var i in data.list) {
					                        $("#grid-table-3-gate").addRowData(data.list[i].id, data.list[i],"first");
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
	    
	    $("#sub-s").click(function(){
	    	var grid;
	    	if(inputSele.val() == '1')
	  	    {
	        	grid = $("#grid-table-3");
	  	    }
	    	else
	    	{
	    		grid = $("#grid-table-3-gate");
	    	}
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
	    	    
	    for(var pIndex in printers)
	    {
	    	if(printers[pIndex].name == $("#printName").val())
	    	{
	    		$("#printNum").val(printers[pIndex].num);
	    		break;
	    	}
	    }
	    $("#printName").bind("change",function() {
		    for(var pIndex in printers)
		    {
		    	if(printers[pIndex].name == $(this).val())
		    	{
		    		$("#printNum").val(printers[pIndex].num);
		    		break;
		    	}
		    }
		});
	    
	    function format(v,x,r){
	    	if(v == '1')
	    	{
	    		return "是";
	    	}
	    	else
	    	{
	    		return "否"
	    	}
	    }

	    function setClick(){
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
	    /**
	    		初始化所有checkbox
	  	**/  
	    setClick();
	}
    return {
 		pagerInit:function (){
 			pagerInit();
 	}
}
}();

var seleType;
function yanz(obj)
{
	var y = '/(^0$)|(^100$)|(^\d{1,2}$)/';
	if(!y.test($(obj).val()))
	{
		$(obj).val('');
		alert('输入格式不正确!');
	}
}

function seleChange(obj){
	if($(obj).val() == '1')
	{
		$("#grid-table-3-gate").closest(".ui-jqgrid").hide();
		$("#grid-table-3").closest(".ui-jqgrid").show();
	}
	else{	
		$("#grid-table-3-gate").closest(".ui-jqgrid").show();
		$("#grid-table-3").closest(".ui-jqgrid").hide();
	}
}