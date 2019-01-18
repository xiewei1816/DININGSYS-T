$(function () {
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	 });
	 var grid = $("#grid-table");
     var sele = $("#sele");
     var isALL = $("#isALL");
     var placeIdArray = []; //初始化区域id数组，方便后面调用
     var mCount = $(".m-count");
    $("#grid-table").jqGrid({
        url:"DININGSYS/DgCurrent/getAllData",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:350,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        rowNum:-1,
        colNames:["id","品项id","品项编号","品项名称","标准价格","时价"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'itemId',index:'itemId', width:'0%',align:'center',hidden:true},
            {name:'itemCode',index:'itemCode', width:'10%',align:'center'},
            {name:'name',index:'name', width:'10%',align:'center'},
            {name:'sPrice',index:'sPrice', width:'10%',align:'center'},
            {name:'currentPrice',index:'currentPrice', width:'10%',align:'center',formatter:format},
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        multiselect:true,
        loadComplete : function() {
            var rows = grid.jqGrid('getRowData');
        	mCount.html("共计"+rows.length+"个促销品项");
        }
    });
    $("#grid-table").hideCol("id");
   
    
    
    $("#add").click(function(){
    	$.get(path+"/DININGSYS/DgCurrent/toAddPlanDish",function(str){
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
	                  url:path+'/DININGSYS/DgCurrent/getMealTimeAddDish',
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
				                        $("#grid-table").addRowData(data.list[i].id, data.list[i]);
	                    		    }
			                  }
	                          var rows = grid.jqGrid('getRowData');
	                      	  mCount.html("共计"+rows.length+"个促销品项");
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
    	
    	if(!checkUpdVals("table-form"))
		{
			return;
		}
        layer.confirm('确认保存当前市别品项价格?', {icon: 3, title:'提示'}, function(index){
        	var send='[';
        	var hava_rows = $("#grid-table").jqGrid('getRowData');
        	
        	if(hava_rows.length > 0){
                for (var i in hava_rows) {
                	var itemId = hava_rows[i].itemId; //品项id
                	var itemCode = hava_rows[i].itemCode;//品项编码
                	var currentPrice = $("#price-"+hava_rows[i].id).val();
            		send +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",\"currentPrice\":\""+currentPrice+"\"},";
                }
            }	 
        	if(hava_rows.length >0)
        	{
            	send = send.substring(0,send.length-1);	
        	}
        	send += "]";
        
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/DgCurrent/saveData',
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
                url:'DININGSYS/DgCurrent/delData',
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
    	

	  if(!checkUpdVal(".discount"))
	  {
			return;
	  }
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
// 		        	grid.jqGrid('setCell',id, 'proPrice', ''+nowPrice);
 		        	$("#price-"+id).val(nowPrice);
              }
        }
	});
    
});
function format(c,o,r){
	if(r.currentPrice == null)
	{
		return "<input class='item-price' type='text'  id='price-"+r.id+"' style='width:70px;' value='' dete='em-money'/>";
	}
	else
	{
		return "<input class='item-price' type='text'  id='price-"+r.id+"' style='width:70px;' value='"+r.currentPrice+"' dete='em-money'/>";	
	}
}