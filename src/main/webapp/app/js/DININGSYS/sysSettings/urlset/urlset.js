$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
		
	
	$("#add").click(function(){
        $.get(path+"/DININGSYS/urlset/toAdd",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'url编辑',
                skin: 'layui-layer-rim',
                area: ['500px', '440px'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm"))
            		{
            			return;
            		}
            		
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/urlset/insertOrUpdate',
                        data:$("#editForm").serialize(),
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
                                $("#grid-table").trigger("reloadGrid");
                                layer.close(ii);
                            }
                            else
                            {
                            	layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
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
        
        $.get(path+"/DININGSYS/urlset/toAdd?id="+id,function(str){
            var addIndex = layer.open({
                type: 1,
                title:'url编辑',
                skin: 'layui-layer-rim',
                area: ['500px', '440px'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm"))
            		{
            			return;
            		}
            		
                    $.ajax({
                        type:'POST',
                        url:path+'/DININGSYS/urlset/insertOrUpdate',
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
	
	
	
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : path+"/DININGSYS/urlset/getPageList",
		colM : "id,code,name,value",
		queryForm : "queryForm",
		colNames : "id,编码,名称,地址",
		colWid : {"id":40,"path":500},
		loadComplete : function() {
		}
	});
	/**
	 * 隐藏grid中id项
	 */
    $("#grid-table").hideCol("id");

});