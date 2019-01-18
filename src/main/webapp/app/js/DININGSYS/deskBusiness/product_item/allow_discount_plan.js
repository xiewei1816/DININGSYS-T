$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	
	$("#add").click(function(){
        $.get(path+"/DININGSYS/ProDiscountPan/toAddPlan",function(str){
            var addIndex = layer.open({
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
                	if(!checkUpdVals("addPanForm"))
                	{
                		return;
                	}
                	
                	var grid;
                	var inputSele = $(".input-sele");
                	var discountType;
                	var diableType;
        	      	if(inputSele.val() == '1')
        	    	{
        	          	grid = $("#grid-table-3");
        	          	discountType = "item-dis-";
        	          	diableType = "item-check-";
        	    	}
        	      	else
        	      	{
        	      		grid = $("#grid-table-3-gate");
        	          	discountType = "gate-dis-";
        	          	diableType = "gate-check-";
        	      	}
                    var content = "[";
              	    var rows = grid.jqGrid('getRowData');
              	    var isItemHava = false;
              	    
              	    if(rows.length > 0){
            			isItemHava = true;
            	        for (var i in rows) {
            	        		var id = rows[i].id;
            			        var pId = rows[i].itemId; //品项id
            			        var discount = $("#"+discountType+id).val();
            			        var disable = $("#"+diableType+id).val();
            			        content +="{\"pId\":\""+pId+"\",\"discount\":\""+discount+"\",\"disable\":\""+disable+"\"},";
         
            	        }
                    }	
              		if(isItemHava)
              		{
              			content = content.substring(0,content.length-1);
              		}
              		content += "]";
              		
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/ProDiscountPan/toSavePlan',
                        data:$("#addPanForm").serialize(),
                        dataType:'JSON',
                        beforeSend : function(){
                        },
                        success:function (d) {
                            if(d.success){//插入主体数据成功后,插入附表数据
                            	 $.ajax({
                                     type:'POST',
                                     url:path+'/DININGSYS/ProDiscountPan/toSavePlanNext',
                                     data:{childContent:content,id:d.id},
                                     dataType:'JSON',
                                     success:function (data) {
                                         if(data.success){//插入主体数据成功后,插入附表数据
                                             layer.close(addIndex);
                                             $("#grid-table-1").trigger("reloadGrid");
                                             
                                             $(".select-week").each(function(){
                                            	 var edl = $(this).attr("edl");
                                            	 var oldHtml = $(this).html();
                                            	 var newHtml = oldHtml;
                                            	 newHtml +='<option value ="'+d.list.id+'" edl="'+edl+'">'+d.list.name+'</option>';
                                            	 $(this).html(newHtml);
                                         	});
                                         }
                                     }
                                 })
                            }
                            else
                            {
                            	layer.alert(d.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});

                            }
                        }
                    })
                }
            });
        })
    });
	
	
	$("#update").click(function(){
		
		var grid = $("#grid-table-1");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        else if(selectRow.length >1)
        {
        	 layer.alert('只能同时修改一行数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
             return;
        }
        
        var id = grid.jqGrid('getCell',selectRow[0],'id'); //获取id
        
        $.get(path+"/DININGSYS/ProDiscountPan/toAddPlan?id="+id,function(str){
            var addIndex = layer.open({
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
                	if(!checkUpdVals("addPanForm"))
                	{
                		return;
                	}
                	
                    var grid;
                	var inputSele = $(".input-sele");
                	var discountType;
                	var diableType;
        	      	if(inputSele.val() == '1')
        	    	{
        	          	grid = $("#grid-table-3");
        	          	discountType = "item-dis-";
        	          	diableType = "item-check-";
        	    	}
        	      	else
        	      	{
        	      		grid = $("#grid-table-3-gate");
        	          	discountType = "gate-dis-";
        	          	diableType = "gate-check-";
        	      	}
                    var content = "[";
              	    var rows = grid.jqGrid('getRowData');
              	    var isItemHava = false;
              	    
              	    if(rows.length > 0){
            			isItemHava = true;
            	        for (var i in rows) {
            	        		var id = rows[i].id;
            			        var pId = rows[i].itemId; //品项id
            			        var discount = $("#"+discountType+id).val();
            			        var disable = $("#"+diableType+id).val();
            			        content +="{\"pId\":\""+pId+"\",\"discount\":\""+discount+"\",\"disable\":\""+disable+"\"},";
         
            	        }
                    }	
              		if(isItemHava)
              		{
              			content = content.substring(0,content.length-1);
              		}
              		content += "]";
              		
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/ProDiscountPan/toSavePlan',
                        data:$("#addPanForm").serialize(),
                        dataType:'JSON',
                        beforeSend : function(){
                        	
                        },
                        success:function (data) {
                            if(data.success){//插入主体数据成功后,插入附表数据
                            	 $.ajax({
                                     type:'POST',
                                     url:path+'/DININGSYS/ProDiscountPan/toSavePlanNext',
                                     data:{childContent:content,id:data.id},
                                     dataType:'JSON',
                                     success:function (data) {
                                         if(data.success){//插入主体数据成功后,插入附表数据
                                             layer.close(addIndex);
                                             $("#grid-table-1").trigger("reloadGrid");
                                         }
                                     }
                                 })
                            }
                            else
                            {
                            	layer.alert(data.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});

                            }
                        }
                    })
                }
            });
        })
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
                url:path+'/DININGSYS/ProDiscountPan/delPanData',
                data:{ids:ids.toString()},
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        $("#grid-table-1").trigger("reloadGrid");
                        $(".select-week").each(function(){
                        	$(this).empty();
                            var cId =  $(this).attr("id");
                            var edl = $(this).attr("edl");
                            var newHtml ='';
                            
                            
                            for (var i in ids) {
                                if (cId == ids[i]) {
                                    $(this).attr("id","0"); //id是方案id
                                    cId = '0';
                                    break;
                                }
                            }
                            
                             if(cId == '0')
                      		 {
		                       	 newHtml +='<option value ="'+0+'" edl="'+edl+'" selected></option>'; //val 是方案id edl 是周id name是方案name
                      		 }
                             else
                             {
                            	 newHtml +='<option value ="'+0+'" edl="'+edl+'"></option>';
                             }
	                       	 for(var w in data.pro)
	                       	 {	
	                       		 if(cId == data.pro[w].id)
	                       		 {
			                       	 newHtml +='<option value ="'+data.pro[w].id+'" edl="'+edl+'" selected>'+data.pro[w].name+'</option>';
	                       		 }
	                       		 else
	                       		 {
			                       	 newHtml +='<option value ="'+data.pro[w].id+'" edl="'+edl+'">'+data.pro[w].name+'</option>'; 
	                       		 }
	                       	 }
	                       	 $(this).html(newHtml);
                    	});
                    }
                }
            });

            layer.close(index);
        });
	});
	/**
	 * 保存星期
	 */
	
	$("#save").click(function(){
        layer.confirm('确认保存当前选择的品项打折方案数据?', {icon: 3, title:'提示'}, function(index){
        	var send='[';
        	$(".select-week option:selected").each(function(){
        		var id = $(this).attr("edl");
        		var val = $(this).val();
        		send +="{\"id\":\""+id+"\",\"val\":\""+val+"\"},";
        	});
        	send = send.substring(0,send.length-1);
        	send += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/ProDiscountPan/saveWeekData',
                    data:{data:send},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                        }
                    }
                });
                layer.close(index);
            });
		
		
	});
	
    
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/ProDiscountPan/exportXls";
	});
    
    
    $("#refresh").click(function(){
        $("#grid-table-1").trigger("reloadGrid");
    });
    
    $("#trash").click(function(){
        $.get("DININGSYS/ProDiscountPan/trash",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'打折方案(回收站)',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str
            });
        });
    });
	$("#grid-table-1").jqGrid({
        url:path+"/DININGSYS/ProDiscountPan/getAllPan?recyclebin=0",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei/2,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        colNames:["id","方案代码","方案名称","方案类型","停用","允许修改优惠比例","修改时间"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'code',index:'code', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'type',index:'type', width:'20%',align:'center',formatter:format1},
            {name:'disable',index:'disable', width:'20%',align:'center',formatter:format2},
            {name:'allowFDis',index:'allowFDis', width:'20%',align:'center',formatter:format3},
            {name:'time',index:'time', width:'20%',align:'center'}
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
        pager:$('#grid-pager-1')
    });
    $("#grid-table-1").hideCol("id");
});



function format1(v,x,r){
	if(r.type == '1')
	{
		return "按品项设置";
	}
	else if(r.type == '2')
	{
		return "按小类设置"
	}
}
function format2(v,x,r){
	if(r.disable == '1')
	{
		return "是";
	}
	else if(r.disable == '0')
	{
		return "否"
	}
}
function format3(v,x,r){
	if(r.allowFDis == '1')
	{
		return "是";
	}
	else if(r.allowFDis == '0')
	{
		return "否"
	}
}