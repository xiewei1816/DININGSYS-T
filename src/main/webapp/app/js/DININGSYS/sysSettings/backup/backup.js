$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
		
	
	$(".add").click(function(){
        $.get(path+"/DININGSYS/sysbackup/toBackup",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'备份信息',
                skin: 'layui-layer-rim',
                area: ['500px', '440px'],
                content: str,
                btn:['备份','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm"))
            		{
            			return;
            		}
            		
            		
                	var ii = layer.load(3,{shade: [0.5, '#393D49']});
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/sysbackup/doBackup',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
                                $("#grid-table").trigger("reloadGrid");
                                layer.close(ii);
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
                url:path+'/DININGSYS/sysbackup/delData',
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
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : path+"/DININGSYS/sysbackup/getPageList",
		colM : "id,productNumber,productName,backupName,time,backupFileName,path",
		queryForm : "queryForm",
		colNames : "id,产品编号,产品名称,备份人,备份时间,备份文件名,备份文件地址",
		colWid : {"id":40,"path":500},
		loadComplete : function() {
		}
	});
	/**
	 * 隐藏grid中id项
	 */
    $("#grid-table").hideCol("id");

});