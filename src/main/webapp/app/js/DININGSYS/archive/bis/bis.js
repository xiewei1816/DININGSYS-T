$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
			$("#bisName").val("");
			$("#bisTime").val("");
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/bis/getPageList?isDel=0",
		colM : "id,bisName,bisTime,bisOrganization",
		queryForm : "queryForm",
		colNames : "ID,名称,开始时间,所属机构",
		colWid : {"id":40},
		formatter : {
		},
		loadComplete : function() {
			$("#" + pageUtil.tabId).hideCol("id");
		}
		
	});
	
	$("#add").click(function(){
		$.get("DININGSYS/archive/bis/toEdit",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'新增营业市别信息',
                skin: 'layui-layer-rim',
                area: ['70%', '80%'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkForm()){
                		$.ajax({
                			type:'POST',
                			url:'DININGSYS/archive/bis/saveBis',
                			data:$("#myfrom").serialize(),
                			dataType:'JSON',
                			success:function (data) {
                				if(data.success == 'OK'){
                					layer.close(addIndex);
                					layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                					$("#" + pageUtil.tabId).trigger("reloadGrid");
                				}else{
                					layer.alert(data.error,{title :'提示',icon: 2, skin: 'layer-ext-moon'});
                				}
                			}
                		});
                	}
                }
            });
        });
	});
	
	$("#update").click(function(){
		var grid = $("#grid-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length > 1){
            layer.alert('请选择一条信息进行修改！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var id = grid.jqGrid('getCell',rowId,'id');
        $.get("DININGSYS/archive/bis/toEdit",{id:id},function (str) {
            var addIndex = layer.open({
                type: 1,
                title:'修改营业市别信息品',
                skin: 'layui-layer-rim',
                area: ['70%', '80%'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkForm()){
                		$.ajax({
                			type:'POST',
                			url:'DININGSYS/archive/bis/saveBis',
                			data:$("#myfrom").serialize(),
                			dataType:'JSON',
                			success:function (data) {
                				if(data.success == 'OK'){
                					layer.close(addIndex);
                					layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                					$("#" + pageUtil.tabId).trigger("reloadGrid");
                				}else{
                					layer.alert(data.error,{title :'提示',icon: 2, skin: 'layer-ext-moon'});
                				}
                			}
                		});
                	}
                }
            });
        });
	});
    
	/**
	 * 表单验证
	 */
    function checkForm(){
    	var flag = true;
    	var bisName = $("#bisName").val();
    	if(bisName == ""){
    		layer.tips('名称不能为空!', $("#bisName") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	var bisTime = $("#bisTime").val();
    	if(bisTime == ""){
    		layer.tips('开始时间不能为空!', $("#bisTime") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	var bisOrganization = $("#bisOrganization").val();
    	if(bisOrganization == ""){
    		layer.tips('所属机构不能为空!', $("#bisOrganization") , {guide: 1, tipsMore:true, time: 2000}); 
    		flag = false;
    	}
    	return flag;
    }
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/archive/bis/deleteBisTrash",						//删除数据提交的后台地址(必要)。
			success : function(resultData){												//删除数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});

	$("#export").click(function(){
		window.location.href="DININGSYS/archive/bis/exportXls?isDel=0&bisName="+$('#queryBisName').val();
	});
		
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
	
	/* ******************************  部门信息回收站  **************************** */
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: '营业市别信息【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/bis/trash',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});
	});
});