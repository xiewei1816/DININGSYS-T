$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/rfc/getPageList?isDel=1",
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

	$("#reply").click(function(){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请选择需要还原的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		layer.confirm('请注意,你确定还原数据吗？', {
		  icon : 0,
		  btn : ['确定','取消'] //按钮
		  }, function(index){
			  jQuery.ajax({
					url : 'DININGSYS/archive/rfc/replyRfc',
					data : {"editIds":rowids + ""},
					type : "POST",
					dataType:"json",
					error : function(request) {
						layer.alert('你提交的数据有错误！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
					},
					success : function(data) {
						if(data.success == 'OK'){
							layer.close(index);
							$("#" + pageUtil.tabId).trigger("reloadGrid");
						}
					}
				});
		  }, function(){
			  
		  });
	});
	
	$("#delete").click(function(){
		var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
		if( rowids == null || rowids == "" ) {
			layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return;
		}
		layer.confirm('请注意,该功能删除的数据不可逆.你确定吗?', {
		  icon : 0,
		  btn : ['确定','取消'] //按钮
		  }, function(index){
			  jQuery.ajax({
					url : 'DININGSYS/archive/rfc/deleteRfc',
					data : {"editIds":rowids + ""},
					type : "POST",
					dataType:"json",
					error : function(request) {
						layer.alert('你提交的数据有错误！',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
					},
					success : function(data) {
						if(data.success == 'OK'){
							layer.close(index);
							$("#" + pageUtil.tabId).trigger("reloadGrid");
						}
					}
				});
		  }, function(){
			  
		  });
	});
	   
	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
	});

});