 $(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#add").click(function(){
		pageUtil.addOper({
			addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/rfct/saveRfct", 							//添加保存的地址(必要)。
			saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error	,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#update").click(function(){
		pageUtil.updOper({
			updBefore : function(){												//修改数据界面之前执行的方法(非必要)。
			},
			updUrl : "DININGSYS/archive/rfct/getRfctById",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				var orgId = data.rfct.rfctOrganization;
				$("#rfctOrganization").val(orgId);
				pageUtil.getUpdingData(data.rfct);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/rfct/saveRfct", 									//添加保存的地址(必要)。
			saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/archive/rfct/deleteRfctTrash",						//删除数据提交的后台地址(必要)。
			success : function(resultData){												//删除数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});

	$("#export").click(function(){
		window.location.href="DININGSYS/archive/rfct/exportXls?isDel=0&rfctName="+$('#rfctName').val()+"&rfctCode="+$('#rfctCode').val();
	});
	
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
		
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/rfct/getPageList?isDel=0",
		colM : "rfctCode,rfctName,rfctOrganization,createTime,updateTime,isDefaultFlag",
		queryForm : "queryForm",
		colNames : "编号,名称,所属机构,创建时间,修改时间,默认标志",
		colWid : {"id":40},
		formatter : {
			"isDefaultFlag" : function(v){
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

	/* ******************************  退菜原因类型维护信息回收站  **************************** */
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: '退菜原因类型维护信息【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/rfct/trash',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});
	});

});
