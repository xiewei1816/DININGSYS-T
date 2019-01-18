$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	$("#grid-table").jqGrid({
        url:"DININGSYS/DgMemberDiscount/getAllData?recycleBin=0",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        colNames:["id","方案名称","发布状态","启用","应用所有店","开始日期","结束日期","说明","最后修改日期"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'name',index:'name', width:'10%',align:'center'},
            {name:'publish',index:'publish', width:'10%',align:'center',formatter:format},
            {name:'enable',index:'enable', width:'10%',align:'center',formatter:format},
            {name:'useAllShop',index:'useAllShop', width:'10%',align:'center',formatter:format},
            {name:'startDate',index:'startDate', width:'10%',align:'center'},
            {name:'endDate',index:'endDate', width:'10%',align:'center'},
            {name:'explains',index:'explains', width:'10%',align:'center'},
            {name:'updateTime',index:'updateTime', width:'10%',align:'center'}
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
        pager:$('#grid-pager')
    });
    $("#grid-table").hideCol("id");
	
    $("#add").click(function(){
        $.get("DININGSYS/DgMemberDiscount/add",function(str){
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
                	if(!checkUpdVals("basis"))
                	{
                		return;
                	}
                	
                	var week = $("#week");
                	week.val($("#week1").val()+'-'+$("#week2").val()+"-"+$("#week3").val()+"-"+$("#week4").val()+
                	"-"+$("#week5").val()+"-"+$("#week6").val()+"-"+$("#week7").val());//星期用一个字段,间隔符号用'-';
                	/*var grid = $("#grid-table-1");
                	var content = "[";
               	    var rows = grid.jqGrid('getRowData');
               	    var isItemHava = false;
               	    
               	    if(rows.length > 0){
             			isItemHava = true;
             	        for (var i in rows) {
             	        		var id = rows[i].id;
             			        var name = rows[i].name; //品项id
             			        var enable = $("#org-"+id).val(); //品项打折
             			        content +="{\"id\":\""+id+"\",\"name\":\""+name+"\",\"enable\":\""+enable+"\"},";
          
             	        }
                    }	
               		if(isItemHava)
               		{
               			content = content.substring(0,content.length-1);
               		}
               		content += "]";
               		
               		
               		var affiliation = $("#affiliation");
               		affiliation.val(content);*/
               	
               		
               		
               		/**
               		 *  加入会员打折品相
               		 */
               		
               	
                	var grid2 = $("#grid-table-2");
                	var content2 = "[";
               	    var rows2 = grid2.jqGrid('getRowData');
               	    var isItemHava2 = false;
               	    
               	    if(rows2.length > 0){
             			isItemHava2 = true;
             	        for (var i in rows2) {
             	        		var id = rows2[i].id;
             	        		var itemId = rows2[i].itemId;
             			        var price = rows2[i].price; //品项id
             			        var enable = $("#item-"+id).val(); //品项打折
             			        content2 +="{\"itemId\":\""+itemId+"\",\"price\":\""+price+"\",\"enable\":\""+enable+"\"},";
          
             	        }
                    }	
               		if(isItemHava2)
               		{
               			content2 = content2.substring(0,content2.length-1);
               		}
               		content2 += "]";
               		
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/DgMemberDiscount/save',
                        data:$("#basis").serialize(),
                        dataType:'JSON',
                        beforeSend : function(){
                        	
                        },
                        success:function (d) {
                            if(d.success){//插入主体数据成功后,插入附表数据
                            	 $.ajax({
                                     type:'POST',
                                     url:path+'/DININGSYS/DgMemberDiscount/saveChild',
                                     data:{childContent:content2,id:d.id},
                                     dataType:'JSON',
                                     success:function (data) {
                                         if(data.success){//插入主体数据成功后,插入附表数据
                                             layer.close(addIndex);
                                             $("#grid-table").trigger("reloadGrid");
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
                url:'DININGSYS/DgMemberDiscount/delData',
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
    
    $("#refresh").click(function(){
    	 $("#grid-table").trigger("reloadGrid");
    	
    });
    
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/DgMemberDiscount/exportXls";
	});
    
    $("#publich").click(function(){
    	var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要发布的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        else if(selectRow.length > 1)
        {
        	 layer.alert('每次只能发布一个方案！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
             return;
        }
        var id;
        for(var i = 0;i < selectRow.length;i++){
            id = grid.jqGrid('getCell',selectRow[i],'id');
        }
        layer.confirm('确认发布选中的数据？', {icon: 3, title:'提示'}, function(index){

        $.ajax({
                type:'POST',
                url:'DININGSYS/DgMemberDiscount/publish',
                data:{id:id},
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        layer.close(index);
                        $("#grid-table").trigger("reloadGrid");
                    }
                }
            })
        });
    });
    $("#trash").click(function(){
        $.get("DININGSYS/DgMemberDiscount/trash",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'会员打折(回收站)',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str
            });
        });
    });

	$("#update").click(function(){
		var Pid = $("#Pid");
		var grid = $("#grid-table");
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
        
        $.get("DININGSYS/DgMemberDiscount/add?id="+id,function(str){
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
                	if(!checkUpdVals("basis"))
                	{
                		return;
                	}
                	
                	var week = $("#week");
                	week.val($("#week1").val()+'-'+$("#week2").val()+"-"+$("#week3").val()+"-"+$("#week4").val()+
                	"-"+$("#week5").val()+"-"+$("#week6").val()+"-"+$("#week7").val());//星期用一个字段,间隔符号用'-';
                	/*var grid = $("#grid-table-1");
                	var content = "[";
               	    var rows = grid.jqGrid('getRowData');
               	    var isItemHava = false;
               	    
               	    if(rows.length > 0){
             			isItemHava = true;
             	        for (var i in rows) {
             	        		var id = rows[i].id;
             			        var name = rows[i].name; //品项id
             			        var enable = $("#org-"+id).val(); //品项打折
             			        content +="{\"id\":\""+id+"\",\"name\":\""+name+"\",\"enable\":\""+enable+"\"},";
          
             	        }
                    }	
               		if(isItemHava)
               		{
               			content = content.substring(0,content.length-1);
               		}
               		content += "]";
               		
               		
               		var affiliation = $("#affiliation");
               		affiliation.val(content);*/
               	
               		
               		
               		/**
               		 *  加入会员打折品相
               		 */
               		
               	
                	var grid2 = $("#grid-table-2");
                	var content2 = "[";
               	    var rows2 = grid2.jqGrid('getRowData');
               	    var isItemHava2 = false;
               	    
               	    
               	    if(rows2.length > 0){
             			isItemHava2 = true;
             	        for (var i in rows2) {
             	        		var id = rows2[i].id;
             	        		var itemId = rows2[i].itemId;
             			        var price = rows2[i].price; //品项id
             			        var enable = $("#item-"+id).val(); //品项打折
             			        content2 +="{\"itemId\":\""+itemId+"\",\"price\":\""+price+"\",\"enable\":\""+enable+"\"},";
          
             	        }
                    }	
               		if(isItemHava2)
               		{
               			content2 = content2.substring(0,content2.length-1);
               		}
               		content2 += "]";
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/DgMemberDiscount/save',
                        data:$("#basis").serialize(),
                        dataType:'JSON',
                        beforeSend : function(){
                        },
                        success:function (d) {
                            if(d.success){//插入主体数据成功后,插入附表数据
                            	 $.ajax({
                                     type:'POST',
                                     url:path+'/DININGSYS/DgMemberDiscount/saveChild',
                                     data:{childContent:content2,id:d.id},
                                     dataType:'JSON',
                                     success:function (data) {
                                         if(data.success){//插入主体数据成功后,插入附表数据
                                             layer.close(updateIndex);
                                             $("#grid-table").trigger("reloadGrid");
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
});


function format(z,x,f){
	if(z == '1')
	{
		return '是';
	}
	else
	{
		return '否';
	}
}