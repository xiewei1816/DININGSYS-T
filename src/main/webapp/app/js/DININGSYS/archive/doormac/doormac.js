$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/dgSeatDoorMac/getPageList",
		colM : "id,mac,seatName",
		queryForm : "queryForm",
		colNames : "id,mac地址,客位名称",
		colWid : {id:"0"},
		loadComplete : function() {
			$("#" + pageUtil.tabId).hideCol("id");
		}
	});	  
	
	
	$("#add").click(function(){
        $.get("DININGSYS/dgSeatDoorMac/addOrUpdate",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'绑定信息',
                skin: 'layui-layer-rim',
                area: ['500px', '440px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm"))
            		{
            			return;
            		}
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/dgSeatDoorMac/insertOrUpdate',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
                                $("#grid-table").trigger("reloadGrid");
                            }
                            else
                            {
                            	 layer.alert(data.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                            }
                        }
                    });
                    layer.close(addIndex);
                }
            });
        })
    });
	
	
	$("#update").click(function(){
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
        
        
        $.get("DININGSYS/dgSeatDoorMac/addOrUpdate?id="+id,function(str){
            var addIndex = layer.open({
                type: 1,
                title:'备份信息',
                skin: 'layui-layer-rim',
                area: ['500px', '440px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm"))
            		{
            			return;
            		}
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/dgSeatDoorMac/insertOrUpdate',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
                                $("#grid-table").trigger("reloadGrid");
                            }
                        }
                    });
                    layer.close(addIndex);
                }
            });
        })
    });
	
	
	
	$("#del").click(function(){
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
                url:'DININGSYS/dgSeatDoorMac/delData',
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
});