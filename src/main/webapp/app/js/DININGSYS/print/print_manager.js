$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	$("#grid-table").jqGrid({
        url:"DININGSYS/PrintManger/getAllPrint?trash=0",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        colNames:["id","打印机名称","打印区域","是否切打","是否停用"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'areaNames',index:'areaNames', width:'20%',align:'center'},
            {name:'qOZ',index:'qOZ', width:'20%',align:'center',formatter:format},
            {name:'disable',index:'disable', width:'20%',align:'center',formatter:format},
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
        $.get("DININGSYS/PrintManger/toAddPrint",function(str){
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
        	      	if(inputSele.val() == '1')
        	    	{
        	          	grid = $("#grid-table-3");
        	    	}
        	      	else
        	      	{
        	      		grid = $("#grid-table-3-gate");
        	      	}
              	    var rows = grid.jqGrid('getRowData');
              	    var content = JSON.stringify(rows);
              	    
              	    var areas =  "";
              	    $(".area-checkbox").each(function()
              	    {
              	    	if($(this).prop("checked") == true)
              	    	{
              	    		areas += $(this).attr("id")+",";
              	    	}
              	    });
              	    if(areas.length > 0)
              	    {
              	    	areas = areas.substring(0,areas.length - 1);
              	    }
              	    $("#areaIds").val(areas);
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/PrintManger/toSavePrint',
                        data:$("#addPanForm").serialize(),
                        dataType:'JSON',
                        beforeSend : function(){
                        },
                        success:function (d) {
                            if(d.success && content.length >2){//插入主体数据成功后,插入附表数据
                            	 $.ajax({
                                     type:'POST',
                                     url:'DININGSYS/PrintManger/toSavePrintNext',
                                     data:{childContent:content,id:d.id},
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
                            	 layer.close(addIndex);
                            }
                        }
                    })
                }
            });
        })
    });
	
	
	$("#update").click(function(){
		
		var grid = $("#grid-table");
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
        
        $.get("DININGSYS/PrintManger/toAddPrint?id="+id,function(str){
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
        	      	if(inputSele.val() == '1')
        	    	{
        	          	grid = $("#grid-table-3");
        	    	}
        	      	else
        	      	{
        	      		grid = $("#grid-table-3-gate");
        	      	}
              	    var rows = grid.jqGrid('getRowData');
              	    var content = JSON.stringify(rows);
              	    
              	    var areas =  "";
              	    $(".area-checkbox").each(function()
              	    {
              	    	if($(this).prop("checked") == true)
              	    	{
              	    		areas += $(this).attr("id")+",";
              	    	}
              	    });
              	    if(areas.length > 0)
              	    {
              	    	areas = areas.substring(0,areas.length - 1);
              	    }
              	    $("#areaIds").val(areas);
              	                 	    
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/PrintManger/toSavePrint',
                        data:$("#addPanForm").serialize(),
                        dataType:'JSON',
                        beforeSend : function(){
                        },
                        success:function (d) {
                            if(d.success && content.length >2){//插入主体数据成功后,插入附表数据
                            	 $.ajax({
                                     type:'POST',
                                     url:'DININGSYS/PrintManger/toSavePrintNext',
                                     data:{childContent:content,id:d.id},
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
                            	layer.close(addIndex);
                            }
                        }
                    })
                }
            });
        })
	});
	
    $("#trash").click(function(){
        $.get("DININGSYS/PrintManger/to_trash",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'打印机管理(回收站)',
                skin: 'layui-layer-rim',
                area: ['850px', '80%'],
                content: str
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
                url:'DININGSYS/PrintManger/trash',
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
		window.location.href=path+"/DININGSYS/ProImportant/exportXls";
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