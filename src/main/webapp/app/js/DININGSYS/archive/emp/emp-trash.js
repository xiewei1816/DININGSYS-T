$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/emp/getPageList?isDel=1",
		colM : "empCode,empName,empOrganization,empDepartment,empDuties,empDob,empCardno,empSex,isMarry,empCardid,sqhNo1,sqhNo2,isOrguserFlag,isManagerFlag,isStartFlag",
		queryForm : "queryForm",
		colNames : "员工编号,员工姓名,所属机构,所属部门,职务,出生日期,身份证号,性别,婚姻状况,员工卡ID,授权号1,授权号2,集团用户标志,管理员标志,启动标志",
		colWid : {"id":40,"empCode":140},
		formatter : {
			"isOrguserFlag" : function(v){
				if (v == '1') {
					return '<font color="green">是</font>';
				} else {
					return '<font color="red">否</font>';
				}
			},
			"isManagerFlag" : function(v){
				if (v == '1') {
					return '<font color="green">是</font>';
				} else {
					return '<font color="red">否</font>';
				}
			},
			"isStartFlag" : function(v){
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
					url : 'DININGSYS/archive/emp/replyEmp',
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
					url : 'DININGSYS/archive/emp/deleteEmp',
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