$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/dgAllowNumber/getPageList",
		colM : "id,name,minAllowNumber,maxAllowNumber",
		queryForm : "queryForm",
		colNames : "id,名称,最小容纳人数,最大容纳人数",
		colWid : {id:"0"},
		rowNum : 10,
		rownumbers : true,
		formatter : {},
		loadComplete : function() {
			$("#" + pageUtil.tabId).hideCol("id");
		}
	});	  
	
	
	$("#add").click(function(){
        $.get("DININGSYS/dgAllowNumber/addOrUpdate",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'新增客位管理信息',
                skin: 'layui-layer-rim',
                area: ['500px', '440px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm")){
            			return;
            		}
            		if(checkAllowNumber()){
	                    $.ajax({
	                        type:'POST',
	                        url:'DININGSYS/dgAllowNumber/insertOrUpdate',
	                        data:$("#editForm").serialize(),
	                        dataType:'JSON',
	                        success:function (data) {
	                            if(data.success){
	                                $("#grid-table").trigger("reloadGrid");
	                            }else{
	                            	 layer.alert(data.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	                            }
	                        }
	                    });
	                    layer.close(addIndex);
            		}
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
        
        
        $.get("DININGSYS/dgAllowNumber/addOrUpdate?id="+id,function(str){
            var addIndex = layer.open({
                type: 1,
                title:'修改客位管理信息',
                skin: 'layui-layer-rim',
                area: ['500px', '440px'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
            		if(!checkUpdVals("editForm"))
            		{
            			return;
            		}
            		if(checkAllowNumber()){
            			$.ajax({
            				type:'POST',
            				url:'DININGSYS/dgAllowNumber/insertOrUpdate',
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
                url:'DININGSYS/dgAllowNumber/delAllowNumber',
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


//容纳人数验证
function checkAllowNumber(){
	var flag = true;
	var minAllowNumber = parseInt($("#minAllowNumber").val());
	var maxAllowNumber = parseInt($("#maxAllowNumber").val());
	if(!isNaN(minAllowNumber) && minAllowNumber < 1){
		$("#minAllowNumber").val(1);
		layer.tips('最小容纳人数不能小于1!', $("#minAllowNumber") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
	}
	if(!isNaN(maxAllowNumber) && maxAllowNumber < 1){
		$("#maxAllowNumber").val(1);
		layer.tips('最大容纳人数不能小于1!', $("#maxAllowNumber") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
	}
	if(!isNaN(minAllowNumber) && !isNaN(maxAllowNumber)){
		if(minAllowNumber > maxAllowNumber){
			layer.tips('最大容纳人数不能小于最小容纳人数!', $("#maxAllowNumber") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
	}
	return flag;
}