$(function(){

	//初始化下拉列表数据
  	$("#rfcType option:first").prop("selected", 'selected');
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/rfc/getPageList?isDel=0",
		colM : "rfcCode,rfcName,rfcType,rfcZjf,isMaterialLoss,isTdsgqpx,createTime,rfcExplain",
		queryForm : "queryForm",
		colNames : "编号,名称,退菜原因类型,助记符,原料损失,退单时沽清品项,创建时间,说明",
		colWid : {"id":40},
		formatter : {
			"isMaterialLoss" : function(v){
				if (v == '1') {
					return '<font color="green">是</font>';
				} else {
					return '<font color="red">否</font>';
				}
			},
			"isTdsgqpx" : function(v){
				if (v == '1') {
					return '<font color="green">是</font>';
				} else {
					return '<font color="red">否</font>';
				}
			}
		},
		loadComplete : function() {
		}
	});
	
	$("#add").click(function(){
		pageUtil.addOper({
			addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/rfc/saveRfc", 							//添加保存的地址(必要)。
			saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( resultData.error	,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#update").click(function(){
		pageUtil.updOper({
			updBefore : function(){												//修改数据界面之前执行的方法(非必要)。
			},
			updUrl : "DININGSYS/archive/rfc/getRfcById",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				var rfctId = data.rfc.rfcType;
				$("#rfcType").val(rfctId);
				var orgId = data.rfc.rfcOrganization;
				$("#rfcOrganization").val(orgId);
				pageUtil.getUpdingData(data.rfc);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/rfc/saveRfc", 									//添加保存的地址(必要)。
			saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#gorfct").click(function(){
		layer.open({
			  type: 2,
			  title: '退菜原因类型维护',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/rfct/index',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		}); 
	});
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/archive/rfc/deleteRfcTrash",						//删除数据提交的后台地址(必要)。
			success : function(resultData){												//删除数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert(resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});

	$("#export").click(function(){
		window.location.href="DININGSYS/archive/rfc/exportXls?isDel=0&rfcName="+$('#rfcName').val()+"&rfcCode="+$('#rfcCode').val();
	});
	
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });

    /* ******************************  退菜原因信息回收站  **************************** */
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: '退菜原因信息【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/rfc/trash',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});
	});

 });