var radioType = '0';
$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	/**
	 * 
	 * 几率选择没有,只能另外使用个列,checkbox不能改变
	 */
    /**
	初始化所有radio
     **/  
	$('input:radio[name="mType"]').each(function() {
		if ($(this).prop("checked") == true) {
			radioType = $(this).val();
		}
	});
	
	$("#grid-table-1").jqGrid({
        url:path+"/DININGSYS/DgCustomMoney/getItem",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:'280px',//高度，表格高度。可为数值、百分比或'auto'
        width:650,//这个宽度不能为百分比
        rowNum:-1,
        colNames:["id","品项id","代码","品项名称","标准价格","套餐","品项大类","品项小类"],
        colModel:[
            {name:'id',index:'id', width:'10%', align:'center',hidden:true},
            {name:'itemId',index:'itemId', width:'20%',align:'center',hidden:true},
            {name:'itemCode',index:'itemCode', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'sPrice',index:'sPrice', width:'20%',align:'center'},
            {name:'tc',index:'tc', width:'20%',align:'center',formatter:format},
            {name:'bName',index:'bName', width:'20%',align:'center'},
            {name:'sName',index:'sName', width:'20%',align:'center'}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为blackId
            repeatitems : false
        },
        postData:{
        	'type':radioType
        },
        gridview: true,
        autoencode: true,
        multiselect:true,
        loadComplete : function() {
        	
		}
    });
	
	$("#grid-table-gate").jqGrid({
        url:path+"/DININGSYS/DgCustomMoney/gate",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:200,//高度，表格高度。可为数值、百分比或'auto'
        width:200,//这个宽度不能为百分比
        rowNum:-1,
        colNames:["id","品项小类","默认值"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'name',index:'name', width:'50%',align:'center'},
            {name:'allowCustom',index:'allowCustom', width:'50%',align:'center',formatter:format2},
        ],
        rownumbers:false,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        gridview: true,
        autoencode: true,
        loadComplete : function() {
	        	$('.gate-checkbox').click(function () {
	        		   this.blur();  
	        		   this.focus();
	        	});  
	        		 
	        	$(".gate-checkbox").change(function() {
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
	
	
	$(".sele-all").click(function() {
	    var grid = $("#grid-table");
		var ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
	    var len = ids.length; 
	   
		if($(".dish-checkbox").prop("checked") == true)
		{
			$(".dish-checkbox").prop("checked",false);
			for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        grid.jqGrid('setCell',colVal, 'discount', "0");
		    }
		}
		else
		{
			$(".dish-checkbox").prop("checked",true);
			for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        grid.jqGrid('setCell',colVal, 'discount', "1");
		    }
		}
	});
	
    $("#grid-table-gate").hideCol("id");
    $("#grid-table-1").hideCol("id");
    
    $("#refresh").click(function(){
        $("#grid-table-gate").trigger("reloadGrid");
        $("#grid-table-1").trigger("reloadGrid");
    });
    
    
    $("#add").click(function(){
    	$.get(path+"/DININGSYS/DgCustomMoney/add",function(str){
            var addsIndex = layer.open({
                type: 1,
                title:'品项自定义金额-品项选择窗口',
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
	                  url:path+'/DININGSYS/DgCustomMoney/toAddItem',
	                  data:{chooseTrIds:chooseTrIds.toString()},
	                  dataType:'JSON',
	                  success:function (data) {
	                      if(data.success == true)
	                      {
	                    	  
                    		  //$("#grid-table").jqGrid("clearGridData"); //先清理
	                    	  var hava_rows = $("#grid-table-1").jqGrid('getRowData');
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
	                    		  		$("#grid-table-1").delRowData(gridTableIds[i]);
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
				                        $("#grid-table-1").addRowData(data.list[i].id, data.list[i],"first");
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
    
    $("#save").click(function(){
        layer.confirm('是否保存当前数据?', {icon: 3, title:'提示'}, function(index){
        	var gate_content = "[";
      	    var grid = $("#grid-table-gate");
      		var ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
      	    var len = ids.length;
      	    var isHava = false;
      		for(var i=0; i<len; i++){ 
      			isHava = true;
  		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
  		        var colVal = getRow.id;
  		        var allowCustom = $("#gate-"+colVal).val();
  		        gate_content +="{\"id\":\""+colVal+"\",\"allowCustom\":\""+allowCustom+"\"},";
      		}
      		if(isHava)
      		{
      			gate_content = gate_content.substring(0,gate_content.length-1);
      		}
      		gate_content += "]";
      		
      		
      		var dish_content = "[";
      	    grid = $("#grid-table-1");
      		ids = grid.getDataIDs();
      	    len = ids.length;
      	    var isdishHava = false;
      		for(var i=0; i<len; i++){ 
      			isdishHava = true;
  		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
  		        var itemId = getRow.itemId;
  		        var itemCode = getRow.itemCode;
  		        dish_content +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\"},";
      		}
      		if(isdishHava)
      		{
      			dish_content = dish_content.substring(0,dish_content.length-1);
      		}
      		dish_content += "]";
      		
      		var name_content = "[";
      		name_content +="{\"customName\":\""+$("#customName1").val()+"\",\"index\":\""+$("#customName1").attr("index")+"\"},";
      		name_content +="{\"customName\":\""+$("#customName2").val()+"\",\"index\":\""+$("#customName2").attr("index")+"\"},";
      		name_content +="{\"customName\":\""+$("#customName3").val()+"\",\"index\":\""+$("#customName3").attr("index")+"\"}]";
      		
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/DgCustomMoney/saveCustom',
                    data:{gate:gate_content,
                    	dish:dish_content,
                    	type:radioType,
                    	nameContent:name_content},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                            layer.close(index);
                            $(".radios").empty();//清空所有数据
                            
                            var raiosContent = '<label  class="col-md-3 control-label"><h5 style="margin-left: 15px;margin-top: 5px" value="0">设定类型:</h5></label>';
                            var isFrist = true;
                            for(var i in data.names)
                            {
                            	if(data.names[i].customMoneyName.length >0)
                            	{
                            		if(isFrist) //第一个设置为checked
                            		{
                            			raiosContent +='<input  type="radio" name="mType" id="isALL"  class="input-button" value="'+data.names[i].id+'" style="margin-left: 35px;margin-bottom: 8px"  checked/>'+
                   		    			'<span style="text-align: left;">'+data.names[i].customMoneyName+'</span>';	
                            			isFrist = false;
                            		}
                            		else
                            		{
                            			raiosContent +='<input  type="radio" name="mType" id="isALL"  class="input-button" value="'+data.names[i].id+'" style="margin-left: 35px;margin-bottom: 8px" />'+
                   		    			'<span style="text-align: left;">'+data.names[i].customMoneyName+'</span>';	
                            		}
                            	}
                            }
                            $(".radios").html(raiosContent);
                            initRadio();
                        }
                    }
               })
            });
    	
    });
    
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/DgCustomMoney/exportXls?type="+radioType;
	});
    $("#delb").click(function(){
		var grid = $("#grid-table-1");
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
                url:'DININGSYS/DgCustomMoney/delData',
                data:{ids:ids.toString()},
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        $("#grid-table-1").trigger("reloadGrid");
                    }
                }
            });

            layer.close(index);
        });
	});
    initRadio();
});

function initRadio()
{
	$('input:radio[name="mType"]').each(function() {
		if ($(this).prop("checked") == true) {
			radioType = $(this).val();
			$("#grid-table-1").jqGrid('setGridParam',{
			    datatype:'json', 
		        postData:{'type':radioType,
		        	'itemCode':$(".itemCode").val(),
		        	'name':$(".name").val()}}).trigger("reloadGrid");
		}
	});
	
	$('input:radio[name="mType"]').click(function () {
		 this.blur();  
		 this.focus();
	});  
		 
	$('input:radio[name="mType"]').change(function() {
		if ($(this).prop("checked") == true) {
			radioType = $(this).val();
			$("#grid-table-1").jqGrid('setGridParam',{
			    datatype:'json', 
		        postData:{'type':radioType,
		        	'itemCode':$(".itemCode").val(),
		        	'name':$(".name").val()}}).trigger("reloadGrid");
		}
	});
}


function format(v,x,r){
	if(v == 1)
	{
		return "是";
	}
	else if(v == 0)
	{
		return "否";
	}
	else{
		return "否";
	}
}


function format2(v,x,r){
	if(r.allowCustom == '1')
	{
		return "<input class='gate-checkbox' type='checkbox' checked='true' value='"+v+"' id='gate-"+r.id+"'/>";
	}
	else if(r.allowCustom == '0')
	{
		return "<input class='gate-checkbox' type='checkbox' value='"+v+"' id='gate-"+r.id+"'/>";
	}
}