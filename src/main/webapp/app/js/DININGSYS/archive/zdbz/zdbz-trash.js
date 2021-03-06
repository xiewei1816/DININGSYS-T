$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
    
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/zdbz/getPageList?isDel=1",
		colM : "zdbzNum,orderRemark,zdbzOrganization",
		queryForm : "queryForm",
		colNames : "编号,整单备注,所属机构",
		colWid : {"id":40,"orderRemark":200},
		formatter : {
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
					url : 'DININGSYS/archive/zdbz/replyZdbz',
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
					url : 'DININGSYS/archive/zdbz/deleteZdbz',
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