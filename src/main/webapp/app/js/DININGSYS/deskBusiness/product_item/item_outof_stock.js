$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	 var grid = $("#grid-table");
     var sele = $("#sele");
     var isALL = $("#isALL");
     var radioType = '1';
	$("#grid-table").jqGrid({
        url:"DININGSYS/ProItemOutofStock/getAllData",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:350,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        rowNum:-1,
        colNames:["id","品项id","品项代码","品项名称","套餐","标准价格","品项大类","品项小类"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'itemId',index:'itemId', width:'0%',align:'center',hidden:true},
            {name:'itemCode',index:'itemCode', width:'10%',align:'center'},
            {name:'name',index:'name', width:'10%',align:'center'},
            {name:'tc',index:'tc', width:'10%',align:'center',formatter:format},
            {name:'sPrice',index:'sPrice', width:'10%',align:'center'},
            {name:'bName',index:'bName', width:'10%',align:'center'},
            {name:'sName',index:'sName', width:'10%',align:'center'},
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        multiselect:true
    });
    $("#grid-table").hideCol("id");
    
    
  
	
    
    $("#add").click(function(){
    	$.get(path+"/DININGSYS/ProItemOutofStock/toAddPlanDish",function(str){
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
	                  url:path+'/DININGSYS/ProItemOutofStock/getMealTimeAddDish',
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
//    	 $("#grid-table").trigger("reloadGrid");
 		$("#grid-table").jqGrid('setGridParam',{
		    datatype:'json', 
	        postData:{'mType':radioType,
	        	'itemCode':$(".itemCode").val(),
	        	'name':$(".name").val()}}).trigger("reloadGrid");
    });
    
    $("#save").click(function(){
        layer.confirm('确认保存当前市别品项价格?', {icon: 3, title:'提示'}, function(index){
        	var send='[';
        	var hava_rows = $("#grid-table").jqGrid('getRowData');
        	
        	if(hava_rows.length > 0){
                for (var i in hava_rows) {
                	var itemId = hava_rows[i].itemId; //品项id
                	var itemCode = hava_rows[i].itemCode;//品项编码
                	var mType = radioType;//品项编码
            		send +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",\"mType\":\""+mType+"\"},";
              }
            }	 
        	if(hava_rows.length >0)
        	{
            	send = send.substring(0,send.length-1);	
        	}
        	send += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/ProItemOutofStock/saveData',
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
                url:'DININGSYS/ProItemOutofStock/delData',
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
    
    
    $('input:radio[name="mType"]').click(function () {
		 this.blur();  
		 this.focus();
	});  
		 
	$('input:radio[name="mType"]').change(function() {
		radioType = $(this).val();
		$("#grid-table").jqGrid('setGridParam',{
		    datatype:'json', 
	        postData:{'mType':$(this).val(),
	        	'itemCode':$(".itemCode").val(),
	        	'name':$(".name").val()}}).trigger("reloadGrid");
	});
	
});

function format(c,o,r){
	if(c == '1')
	{
		return '是';
	}
	else
	{
		return '否';
	}
}