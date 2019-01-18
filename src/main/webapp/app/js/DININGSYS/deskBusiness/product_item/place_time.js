$(function () {
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	 });
	 var grid = $("#grid-table");
     var sele = $("#sele");
     var isALL = $("#isALL");
     var placeIdArray = []; //初始化区域id数组，方便后面调用
     var colModelData = [
                         {name:'id',index:'id', width:'0%', align:'center' },
                         {name:'itemId',index:'itemId', width:'0%',align:'center',hidden:true},
                         {name:'itemCode',index:'itemCode', width:'15%',align:'center'},
                         {name:'name',index:'name', width:'15%',align:'center'},
                         {name:'sPrice',index:'sPrice', width:'15%',align:'center'}
                     ];
    var colNames = ["id","品项id","品项编号","品项名称","标准价格"];
    
    $.ajax({
        type:'POST',
        url:path+'/DININGSYS/placeTime/getTableItem',
        dataType:'JSON',
        success:function (data) {
            if(data.success){
            	 for (var i in data.items) { 
            		 var colData = {
                             name : 'd'+data.items[i].id,
                             index : 'd'+data.items[i].id,
                             width:'15%',
                             align : "center"   
                     };
            		 placeIdArray.push("d"+data.items[i].id);
            		 colNames.push(data.items[i].name); //赋值名称title
                     colModelData.push(colData);
            	 }
            	 
                 //动态增加列
                 $("#grid-table").jqGrid({
                     url:"DININGSYS/placeTime/getAllData",
                     datatype:"json", //数据来源，本地数据
                     mtype:"POST",//提交方式
                     height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
                     autowidth:true,//自动宽
                     rowNum:-1,
                     colNames:colNames,
                     colModel:colModelData,
                     rownumbers:true,//添加左侧行号
                     viewrecords: true,//是否在浏览导航栏显示记录总数
                     jsonReader:{
                         id: "id",//设置返回参数中，表格ID的名字为id
                         repeatitems : false
                     },
                     multiselect:true
                 });
                 $("#grid-table").hideCol("id");
              }
           }
    	});

    
    
    
    $("#add").click(function(){
    	$.get(path+"/DININGSYS/placeTime/toAddPlanDish",function(str){
            var addsIndex = layer.open({
                type: 1,
                title:'一菜多价按市别设置-品项选择窗口',
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
	                  url:path+'/DININGSYS/placeTime/getMealTimeAddDish',
	                  data:{chooseTrIds:chooseTrIds.toString()},
	                  dataType:'JSON',
	                  success:function (data) {
	                      if(data.success == true)
	                      {
	                    	  
                    		  //$("#grid-table").jqGrid("clearGridData"); //先清理
	                    	  var hava_rows = $("#grid-table").jqGrid('getRowData');
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
	                    		  		$("#grid-table").delRowData(gridTableIds[i]);
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
				                        $("#grid-table").addRowData(data.list[i].id, data.list[i],"first");
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
    
   $("#refresh").click(function(){
    	 $("#grid-table").trigger("reloadGrid");
    	
    });
    
    $("#save").click(function(){
        layer.confirm('确认保存当前市别品项价格?', {icon: 3, title:'提示'}, function(index){
        	var send='[';
        	var hava_rows = $("#grid-table").jqGrid('getRowData');
        	
        	if(hava_rows.length > 0){
                for (var i in hava_rows) {
                	var itemId = hava_rows[i].itemId; //品项id
                	var itemCode = hava_rows[i].itemCode;//品项编码
                	send +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",";
                	for(var j in placeIdArray)
         		    {
   		    		    var value = $("#grid-table").jqGrid('getCell', hava_rows[i].id,placeIdArray[j]);	
   		    		    send +="\""+placeIdArray[j]+"\":\""+value+"\",";
         		    }
                	send = send.substring(0,send.length-1);	
                    send += "},";
                }
            }	 
        	if(hava_rows.length >0)
        	{
            	send = send.substring(0,send.length-1);	
        	}
        	send += "]";
        
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/placeTime/saveData',
                    data:{data:send},
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
    
    
    $("#delb").click(function(){
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
                url:'DININGSYS/placeTime/delData',
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
    
    
    $("#set-s").click(function(){
  	  var discount = $(".discount").val();
  	  if(discount.length<0)
  	  {
  		  layer.alert('请设置折扣比例',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
  		  return;
  	  }
  	  var rows = grid.jqGrid('getRowData');
        if(rows.length > 0){
              for (var i in rows) {
              	    var getRow = rows[i];//获取当前的数据行  
     		        var id = getRow.id;
     		        var sPrice = getRow.sPrice;
	        		var nowPrice =  ((sPrice*discount)/100).toFixed(2); //保留两位小数
	        		var value = grid.jqGrid('getCell',id,'d'+sele.val());
 		        	if(isALL.val() == '1')
 		        	{
 		        		if(value == null || value.length == 0)
 		        		{
 		        			grid.jqGrid('setCell',id, 'd'+sele.val(), ''+nowPrice);
 		        	    }
 		        	}
 		        	else
 		        	{
 		        		grid.jqGrid('setCell',id, 'd'+sele.val(), ''+nowPrice);
 		        	}
              }
        }
	});
    
    $("#clear-s").click(function(){
    	  var rows = grid.jqGrid('getRowData');
          if(rows.length > 0){
                for (var i in rows) {
                  	var getRow = rows[i];//获取当前的数据行  
     		        var id = getRow.id;
				    grid.jqGrid('setCell',id, 'd'+sele.val(), null);
                }
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