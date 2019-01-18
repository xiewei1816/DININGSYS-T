$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	 var grid = $("#grid-table");
     var sele = $("#sele");
     var isALL = $("#isALL");
     
	$("#grid-table").jqGrid({
        url:"DININGSYS/weekTime/getAllData",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:350,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        colNames:["id","品项id","品项编号","品项名称","标准价格","星期一","星期二","星期三","星期四","星期五","星期六","星期日"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center',hidden:true },
            {name:'itemId',index:'itemId', width:'0%',align:'center',hidden:true},
            {name:'itemCode',index:'itemCode', width:'10%',align:'center'},
            {name:'name',index:'name', width:'10%',align:'center'},
            {name:'sPrice',index:'sPrice', width:'10%',align:'center'},
            {name:'x1',index:'x1', width:'10%',align:'center'},
            {name:'x2',index:'x2', width:'10%',align:'center'},
            {name:'x3',index:'x3', width:'10%',align:'center'},
            {name:'x4',index:'x4', width:'10%',align:'center'},
            {name:'x5',index:'x5', width:'10%',align:'center'},
            {name:'x6',index:'x6', width:'10%',align:'center'},
            {name:'x7',index:'x7', width:'10%',align:'center'},
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        multiselect:true,
		rowNum:-1
    });

    
    
    $("#add").click(function(){
    	$.get(path+"/DININGSYS/weekTime/toAddPlanDish",function(str){
            var addsIndex = layer.open({
                type: 1,
                title:'一菜多价按星期设置-品项选择窗口',
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
	                  url:path+'/DININGSYS/weekTime/getMealTimeAddDish',
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
                	var x1 = hava_rows[i].x1; //午市
                	var x2 = hava_rows[i].x2;//晚市
                	var x3 = hava_rows[i].x3;//晚市
                	var x4 = hava_rows[i].x4;//晚市
                	var x5 = hava_rows[i].x5;//晚市
                	var x6 = hava_rows[i].x6;//晚市
                	var x7 = hava_rows[i].x7;//晚市
            		send +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",\"x1\":\""+x1+"\",\"x2\":\""+x2+"\"" +
            				",\"x3\":\""+x3+"\",\"x4\":\""+x4+"\",\"x5\":\""+x5+"\"" +
            				",\"x6\":\""+x6+"\",\"x7\":\""+x7+"\"},";
              }
            }	 
        	if(hava_rows.length >0)
        	{
            	send = send.substring(0,send.length-1);	
        	}
        	send += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/weekTime/saveData',
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
                url:'DININGSYS/weekTime/delData',
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
	        		var x1 = getRow.x1;
	        		var x2 = getRow.x2;
	        		var x3 = getRow.x3;
	        		var x4 = getRow.x4;
	        		var x5 = getRow.x5;
	        		var x6 = getRow.x6;
	        		var x7 = getRow.x7;
     		        if(sele.val() == '1')
     		        {
     		        	if(isALL.val() == '1')
     		        	{
     		        		if(x1 == null || x1.length == 0)
     		        		{
     		        			grid.jqGrid('setCell',id, 'x1', ''+nowPrice);
     		        	    }
     		        	}
     		        	else
     		        	{
     		        		grid.jqGrid('setCell',id, 'x1', ''+nowPrice);
     		        		
     		        	}
     		        }
     		        else if(sele.val() == '2')
     		        {
     		        	if(isALL.val() == '1')
     		        	{
     		        		if(x2 == null || x2.length == 0)
     		        		{
     		        			grid.jqGrid('setCell',id, 'x2',  ''+nowPrice);
     		        	    }
     		        	}
     		        	else
     		        	{
     		        		grid.jqGrid('setCell',id, 'x2', ''+nowPrice);
     		        		
     		        	}
     		        }
     		       else if(sele.val() == '3')
    		        {
    		        	if(isALL.val() == '1')
    		        	{
    		        		if(x3 == null || x3.length == 0)
    		        		{
    		        			grid.jqGrid('setCell',id, 'x3',  ''+nowPrice);
    		        	    }
    		        	}
    		        	else
    		        	{
    		        		grid.jqGrid('setCell',id, 'x3', ''+nowPrice);
    		        		
    		        	}
    		        }
     		       else if(sele.val() == '4')
   		           {
	   		        	if(isALL.val() == '1')
	   		        	{
	   		        		if(x4 == null || x4.length == 0)
	   		        		{
	   		        			grid.jqGrid('setCell',id, 'x4',  ''+nowPrice);
	   		        	    }
	   		        	}
	   		        	else
	   		        	{
	   		        		grid.jqGrid('setCell',id, 'x4', ''+nowPrice);
	   		        		
	   		        	}
   		           }
     		       else if(sele.val() == '5')
      		       {
      		        	if(isALL.val() == '1')
      		        	{
      		        		if(x5 == null || x5.length == 0)
      		        		{
      		        			grid.jqGrid('setCell',id, 'x5',  ''+nowPrice);
      		        	    }
      		        	}
      		        	else
      		        	{
      		        		grid.jqGrid('setCell',id, 'x5', ''+nowPrice);
      		        		
      		        	}
      		       }
     		       else if(sele.val() == '6')
      		       {
      		        	if(isALL.val() == '1')
      		        	{
      		        		if(x6 == null || x6.length == 0)
      		        		{
      		        			grid.jqGrid('setCell',id, 'x6',  ''+nowPrice);
      		        	    }
      		        	}
      		        	else
      		        	{
      		        		grid.jqGrid('setCell',id, 'x6', ''+nowPrice);
      		        		
      		        	}
      		       }
     		       else if(sele.val() == '7')
      		       {
      		        	if(isALL.val() == '1')
      		        	{
      		        		if(x7 == null || x7.length == 0)
      		        		{
      		        			grid.jqGrid('setCell',id, 'x7',  ''+nowPrice);
      		        	    }
      		        	}
      		        	else
      		        	{
      		        		grid.jqGrid('setCell',id, 'x7', ''+nowPrice);
      		        		
      		        	}
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
			    	if(sele.val() == '1')
				    {
				        grid.jqGrid('setCell',id, 'x1', null);		
				    }
				    else if(sele.val() == '2')
				    {
				        grid.jqGrid('setCell',id, 'x2', null);		
				    }
				    else if(sele.val() == '3')
				    {
				        grid.jqGrid('setCell',id, 'x3', null);		
				    }
				    else if(sele.val() == '4')
				    {
				        grid.jqGrid('setCell',id, 'x4', null);		
				    }
				    else if(sele.val() == '5')
				    {
				        grid.jqGrid('setCell',id, 'x5', null);		
				    }
				    else if(sele.val() == '6')
				    {
				        grid.jqGrid('setCell',id, 'x6', null);		
				    }
				    else if(sele.val() == '7')
				    {
				        grid.jqGrid('setCell',id, 'x7', null);		
				    }
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