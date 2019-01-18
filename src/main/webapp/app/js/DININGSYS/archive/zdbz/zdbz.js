$(function(){
	
	//初始化下拉列表数据
  	$("#zdbzOrganization option:first").prop("selected", 'selected');
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/zdbz/getPageList?isDel=0",
		colM : "zdbzNum,orderRemark,zdbzOrganization",
		queryForm : "queryForm",
		colNames : "编号,整单备注,所属机构",
		colWid : {"id":40,"orderRemark":200},
		formatter : {
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
			saveUrl : "DININGSYS/archive/zdbz/saveZdbz", 							//添加保存的地址(必要)。
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
			updUrl : "DININGSYS/archive/zdbz/getZdbzById",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				var orgId = data.zdbz.zdbzOrganization;
				$("#zdbzOrganization").val(orgId);
				pageUtil.getUpdingData(data.zdbz);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/zdbz/saveZdbz", 									//添加保存的地址(必要)。
			saveSuccess : function(data){  							    //保存数据成功之后执行的方法(非必要)。
				if (data.success == 'OK') {
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
			url : "DININGSYS/archive/zdbz/deleteZdbzTrash",						//删除数据提交的后台地址(必要)。
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
		window.location.href="DININGSYS/archive/zdbz/exportXls?isDel=0&zdbzNum="+$('#zdbzNum').val();
	});
	
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
	    
    /* ******************************  整单备注信息回收站  **************************** */
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: '整单备注信息【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/zdbz/trash',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});	
	});
    
});