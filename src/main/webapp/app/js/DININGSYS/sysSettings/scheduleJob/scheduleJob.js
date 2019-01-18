$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});

	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/scheduleJob/getScheduleJobList",
		colM : "jobid,jobname,jobgroup,description,methodname,beanclass,cronexpression,jobstatus,sy",
		queryForm : "queryForm",
		colNames : "任务ID,任务名称,任务组名称,任务描述,任务方法,任务方法类,表达式,任务状态,操作",
		colWid : {"jobid":40},
		formatter : {
			"jobstatus" : function(v){
				if (v == '1') {
					return '<font color="green">已激活</font>';
				} else {
					return '<font color="red">未激活</font>';
				}
			}, 
			"sy" : function(value, grid, rows, state){
				var jobid = rows.jobid;
				var jobstatus = rows.jobstatus;
                var start = "start('" + jobid + "')";
                var stop = "stop('" + jobid + "')";
	            var button = '';
	            if(jobstatus == '0'){
	            	button = '<input type="button" name="start" value="激活"  onclick="'+ start + '"/>';
	            }else if(jobstatus == '1'){
	            	button = '<input type="button" name="stop" value="停止" onclick="'+ stop + '"/>';
	            }
	            console.log(button);
	            return button;
			}
		},
		loadComplete : function() {
			$("#" + pageUtil.tabId).hideCol("jobid");
		}
	});
	
	$("#refresh").click(function(){
        $("#grid-table").jqGrid('setGridParam',{
            url:"DININGSYS/scheduleJob/getScheduleJobList?jobname="+$("#jobname_searchitem").val(),
            page:1 }).trigger("reloadGrid");
    });

	$("#add").click(function(){
		$.get("DININGSYS/scheduleJob/toAddOrUpdate",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'新增定时任务',
                skin: 'layui-layer-rim',
                area: ['40%', '60%'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkForm()){
                		$.ajax({
                			type:'POST',
                			url:'DININGSYS/scheduleJob/add',
                			data:$("#myfrom").serialize(),
                			dataType:'JSON',
                			success:function (data) {
                				if(data.flag){
                					layer.close(addIndex);
                					layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                					$("#" + pageUtil.tabId).trigger("reloadGrid");
                				}else{
                					layer.alert(data.msg,{title :'提示',icon: 2, skin: 'layer-ext-moon'});
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
        if(selectRow.length < 1){
            layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }else if(selectRow.length >1){
            layer.alert('只能同时修改一行数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var jobid = grid.jqGrid('getCell',rowId,'jobid');
        $.get("DININGSYS/scheduleJob/toAddOrUpdate",{jobid:jobid},function (str) {
            var addIndex = layer.open({
                type: 1,
                title:'修改定时任务',
                skin: 'layui-layer-rim',
                area: ['70%', '80%'],
                content: str,
                btn:['保存','关闭'],
                yes:function () {
                	if(checkForm()){
                		$.ajax({
                			type:'POST',
                			url:'DININGSYS/scheduleJob/updateCron',
                			data:$("#myfrom").serialize(),
                			dataType:'JSON',
                			success:function (data) {
                				if(data.flag){
                					layer.close(addIndex);
                					layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                					$("#" + pageUtil.tabId).trigger("reloadGrid");
                				}else{
                					layer.alert(data.msg,{title :'提示',icon: 2, skin: 'layer-ext-moon'});
                				}
                			}
                		});
                	}
                }
            });
        });
	});
	
	$("#delb").click(function(){
		var grid = $("#grid-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length < 1){
            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var jobIds = [];
        for(var i=0;i<selectRow.length;i++){
            var jobid = grid.jqGrid('getCell',selectRow[i],'jobid');
            jobIds.push(jobid);
        }
		layer.confirm('提示,确认删除该任务吗？', {
			  icon : 0,
			  btn : ['确定','取消'] //按钮
			  }, function(index){
				$.ajax({
					type:'POST',
					url:'DININGSYS/scheduleJob/deleteJob',
					data:{jobIds:jobIds.toString()},
					dataType:'JSON',
					success:function (data) {
						if(data.flag){
							layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
							$("#" + pageUtil.tabId).trigger("reloadGrid");
						}else{
							layer.alert(data.msg,{title :'提示',icon: 2, skin: 'layer-ext-moon'});
						}
					}
				});
			}, function(){
						  
		});
	});
	
 	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
	
	//表单验证
	function checkForm(){
		var flag = true;
		
		var jobname = $("#jobname").val();
		if(jobname == ""){
			layer.tips('任务名称不能为空!', $("#jobname") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		var jobgroup = $("#jobgroup").val();
		if(jobgroup == ""){
			layer.tips('任务组名称不能为空!', $("#jobgroup") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		var description = $("#description").val();
		if(description == ""){
			layer.tips('任务描述不能为空!', $("#description") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		var methodname = $("#methodname").val();
		if(methodname == ""){
			layer.tips('任务名称不能为空!', $("#methodname") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		var beanclass = $("#beanclass").val();
		if(beanclass == ""){
			layer.tips('任务方法类不能为空!', $("#beanclass") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		var cronexpression = $("#cronexpression").val();
		if(cronexpression == ""){
			layer.tips('表达式不能为空!', $("#cronexpression") , {guide: 1, tipsMore:true, time: 2000}); 
			flag = false;
		}
		return flag;
	}

});

function start(jobid){
	var cmd = 'start';
	layer.confirm('提示,确认激活该任务吗？', {
		  icon : 0,
		  btn : ['确定','取消'] //按钮
		  }, function(index){
			$.ajax({
				type:'POST',
				url:'DININGSYS/scheduleJob/changeJobStatus',
				data:{jobId:jobid,cmd:cmd},
				dataType:'JSON',
				success:function (data) {
					if(data.flag){
						layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
						$("#" + pageUtil.tabId).trigger("reloadGrid");
					}else{
						layer.alert(data.msg,{title :'提示',icon: 2, skin: 'layer-ext-moon'});
					}
				}
			});
		}, function(){
					  
	});
}

function stop(jobid){
	var cmd = 'stop';
	layer.confirm('提示,确认停止该任务吗？', {
		  icon : 0,
		  btn : ['确定','取消'] //按钮
		  }, function(index){
			$.ajax({
				type:'POST',
				url:'DININGSYS/scheduleJob/changeJobStatus',
				data:{jobId:jobid,cmd:cmd},
				dataType:'JSON',
				success:function (data) {
					if(data.flag){
						layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
						$("#" + pageUtil.tabId).trigger("reloadGrid");
					}else{
						layer.alert(data.msg,{title :'提示',icon: 2, skin: 'layer-ext-moon'});
					}
				}
			});
		}, function(){
			  
	});
}
