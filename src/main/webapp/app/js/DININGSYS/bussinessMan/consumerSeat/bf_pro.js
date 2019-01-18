var bf_pro = function () {
	
	function pagerInit()
	{
		$('#query-pan-seach :text').keypress(function(e){
            if(e.keyCode==13){
            	$("#query-pan-trash .btn").trigger("click");
            }
		});
		$('#query-pan-seach .btn').click(function(e){
			 e.preventDefault(); //阻止回车提交表单
             $("#grid-table-5").jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan-seach")});
 			 $("#grid-table-5").trigger("reloadGrid");
		});
		
		$("#grid-table-5").jqGrid({
	        url:"DININGSYS/consumerSeatManager/getPageBffPro?trash=0",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:320,
	        width:816,
	        colNames:["id","方案编号","方案名称","收费类型","总收费上限","最后修改日期"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'code',index:'code', width:'10%',align:'center'},
	            {name:'name',index:'name', width:'10%',align:'center',},
	            {name:'type',index:'type', width:'10%',align:'center',formatter:formatType},
	            {name:'amountUpLim',index:'amountUpLim', width:'10%',align:'center'},
	            {name:'time',index:'time', width:'10%',align:'center'},
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        rowNum:100,//每页显示记录数
	        rowList:[100,200],//用于改变显示行数的下拉列表框的元素数组。
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为id
	            repeatitems : false
	        },
	        multiselect:true,
	    });
	    $("#grid-table-5").hideCol("id");
		
	   $("#add-pro").click(function(){
		   
			$.get(path+"/DININGSYS/consumerSeatManager/toAddBfPro",function(str){
	    		addmIndex= layer.open({
	                type: 1,
	                title:'包房收费方案-增加方案',
	                skin: 'layui-layer-rim',
	                area: ['850px', '90%'],
	                content: str,
	                btn:['确定','关闭'],
	                yes:function () {
	                	
	                	/**
	                	 * 验证表单
	                	 */
	                	if(!checkUpdValForms("basics"))
	                	{
	                		return;
	                	}
	                	
	                	var type = $("#pro-type").val();
                    	var content = "[";
	                	if(type == '1' || type == '4') //访问6
	                	{
	                		var grid = $("#grid-table-6");
	                   	    var rows = grid.jqGrid('getRowData');
	                   	    var isItemHava = false;
	                   	    
	                   	    if(rows.length > 0){
	                 			isItemHava = true;
	                 	        for (var i in rows) {
	                 	        		var id = rows[i].id;
	                 			        var sd = rows[i].sd; //品项id
	                 			        var money = $("#mon-"+id).val();
	                 			        var discount = $("#dis-"+id).val();
	                 			        content +="{\"sd\":\""+sd+"\",\"money\":\""+money+"\",\"discount\":\""+discount+"\"},";
	              
	                 	        }
	                        }	
	                   		if(isItemHava)
	                   		{
	                   			content = content.substring(0,content.length-1);
	                   		}
	                   		content += "]";
	                	}
	                	else //访问7
	                	{
	                		var grid = $("#grid-table-7");
	                   	    var rows = grid.jqGrid('getRowData');
	                   	    var isItemHava = false;
	                   	    
	                   	    if(rows.length > 0){
	                 			isItemHava = true;
	                 	        for (var i in rows) {
	                 	        		var id = rows[i].id;
	                 			        var tLong = $("#tLong-"+id).val(); //品项打折
	                 			        var money = $("#mon-"+id).val(); //品项打折
	                 			        content +="{\"tLong\":\""+tLong+"\",\"money\":\""+money+"\"},";
	              
	                 	        }
	                        }	
	                   		if(isItemHava)
	                   		{
	                   			content = content.substring(0,content.length-1);
	                   		}
	                   		content += "]";
	                	}
	                    $.ajax({
	                        type:'POST',
	                        url:'DININGSYS/consumerSeatManager/saveBffPro',
	                        data:$("#basics").serialize(),
	                        dataType:'JSON',
	                        beforeSend : function(){
	                        	
	                        },
	                        success:function (d) {
	                        	if(d.success){//插入主体数据成功后,插入附表数据
	                            	 $.ajax({
	                                     type:'POST',
	                                     url:path+'/DININGSYS/consumerSeatManager/saveBffProNext',
	                                     data:{childContent:content,id:d.id,type:type},
	                                     dataType:'JSON',
	                                     success:function (data) {
	                                         if(data.success){//插入主体数据成功后,插入附表数据
	                                             layer.close(addmIndex);
	                                             $("#grid-table-5").trigger("reloadGrid");
	                                         }
	                                     }
	                                 })
	                             }
	                        }
	                    })
	                }
	            });
	        })
	   });
	    
	   $("#update-pro").click(function(){
			var id = $("#id");
			var grid = $("#grid-table-5");
	        var selectRow = grid.getGridParam("selarrrow");

	        if(selectRow.length < 1){
	            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        else if(selectRow.length >1)
	        {
	        	 layer.alert('只能同时修改一行数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	             return;
	        }
	        
	        var id = grid.jqGrid('getCell',selectRow[0],'id'); //获取id
	        
	        $.get(path+"/DININGSYS/consumerSeatManager/toAddBfPro?id="+id,function(str){
	            var updateIndex = layer.open({
	                type: 1,
	                title:'基本信息',
	                skin: 'layui-layer-rim',
	                area: ['850px', '90%'],
	                content: str,
	                btn:['确定','关闭'],
	                yes:function () {
	                	
	                	/**
	                	 * 验证表单
	                	 */
	                	if(!checkUpdValForms("basics"))
	                	{
	                		return;
	                	}
	                	
	                    $.ajax({
	                        type:'POST',
	                        url:path+'/DININGSYS/consumerSeatManager/saveBffPro',
	                        data:$("#basics").serialize(),
	                        dataType:'JSON',
	                        beforeSend : function(){
	                        	
	                        },
	                        success:function (d) {
	                            if(d.success){//插入主体数据成功后,插入附表数据
	                            	var type = $("#pro-type").val();
	                            	var content = "[";
	        	                	if(type == '1' || type == '4') //访问6
	        	                	{
	        	                		var grid = $("#grid-table-6");
	        	                   	    var rows = grid.jqGrid('getRowData');
	        	                   	    var isItemHava = false;
	        	                   	    
	        	                   	    if(rows.length > 0){
	        	                 			isItemHava = true;
	        	                 	        for (var i in rows) {
	        	                 	        		var id = rows[i].id;
	        	                 			        var sd = rows[i].sd; //品项id
	        	                 			        var money = $("#mon-"+id).val();
	        	                 			        var discount = $("#dis-"+id).val();
	        	                 			        content +="{\"sd\":\""+sd+"\",\"money\":\""+money+"\",\"discount\":\""+discount+"\"},";
	        	              
	        	                 	        }
	        	                        }	
	        	                   		if(isItemHava)
	        	                   		{
	        	                   			content = content.substring(0,content.length-1);
	        	                   		}
	        	                   		content += "]";
	        	                	}
	        	                	else //访问7
	        	                	{
	        	                		var grid = $("#grid-table-7");
	        	                   	    var rows = grid.jqGrid('getRowData');
	        	                   	    var isItemHava = false;
	        	                   	    
	        	                   	    if(rows.length > 0){
	        	                 			isItemHava = true;
	        	                 	        for (var i in rows) {
	        	                 	        		var id = rows[i].id;
	        	                 			        var tLong = $("#tLong-"+id).val(); //品项打折
	        	                 			        var money = $("#mon-"+id).val(); //品项打折
	        	                 			        content +="{\"tLong\":\""+tLong+"\",\"money\":\""+money+"\"},";
	        	              
	        	                 	        }
	        	                        }	
	        	                   		if(isItemHava)
	        	                   		{
	        	                   			content = content.substring(0,content.length-1);
	        	                   		}
	        	                   		content += "]";
	        	                	}
	        	                    $.ajax({
	        	                        type:'POST',
	        	                        url:'DININGSYS/consumerSeatManager/saveBffPro',
	        	                        data:$("#basics").serialize(),
	        	                        dataType:'JSON',
	        	                        beforeSend : function(){
	        	                        	
	        	                        },
	        	                        success:function (d) {
	        	                        	if(d.success){//插入主体数据成功后,插入附表数据
	        	                            	 $.ajax({
	        	                                     type:'POST',
	        	                                     url:path+'/DININGSYS/consumerSeatManager/saveBffProNext',
	        	                                     data:{childContent:content,id:d.id,type:type},
	        	                                     dataType:'JSON',
	        	                                     success:function (data) {
	        	                                         if(data.success){//插入主体数据成功后,插入附表数据
	        	                                             layer.close(updateIndex);
	        	                                             $("#grid-table-5").trigger("reloadGrid");
	        	                                         }
	        	                                     }
	        	                                 })
	        	                             }
	        	                        }
	        	                    })
	                            }
	                        }
	                    })
	                }
	            });
	        })
		});
	   
	    $("#delb-pro").click(function(){
			var grid = $("#grid-table-5");
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
	        layer.confirm('确认删除选中的数据?删除后将无法还原!', {icon: 3, title:'提示'}, function(index){

	        $.ajax({
	                type:'POST',
	                url:'DININGSYS/consumerSeatManager/delData',
	                data:{ids:ids.toString()},
	                dataType:'JSON',
	                success:function (data) {
	                    if(data.success){
	                        $("#grid-table-5").trigger("reloadGrid");
	                    }
	                }
	            });

	            layer.close(index);
	        });
		});
	    
	    
	    $("#trash-pro").click(function(){
	        $.get("DININGSYS/consumerSeatManager/trash",function(str){
	            var addIndex = layer.open({
	                type: 1,
	                title:'包房收费方案(回收站)',
	                skin: 'layui-layer-rim',
	                area: ['850px', '90%'],
	                content: str,
	                btn:['关闭']
	            });
	        });
	    });
	    $("#refresh-pro").click(function()
	    {
	    	 $("#grid-table-5").trigger("reloadGrid");
	    });
	}
	
    return {
 		pagerInit:function (){
 			pagerInit();
 		}
}
}();

function formatType(z,x,f){
	if(z == '1')
	{
		return '时段收费(按分钟)';
	}
	else if(z == '2')
	{
		return '时阶收费';
	}
	else if(z == '3')
	{
		return '时长收费';
	}
	else if(z == '4')
	{
		return '时段收费按小时';
	}
}