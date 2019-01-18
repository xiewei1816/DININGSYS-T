 $(function(){
	 
	//初始化下拉列表默认选中第一条数据
	$("#orgGlms option:first").prop("selected", 'selected');
	$("#orgArea option:first").prop("selected", 'selected');
	$("#orgBrand option:first").prop("selected", 'selected');
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#add").click(function(){

		var grid = $("#grid-table");
		var rows = grid.jqGrid('getRowData'); //获取当前显示的记录

		if(rows != undefined && rows.length > 0){
			layer.alert('只能添加一条本店数据',{title :'提示',icon: 2, skin: 'layer-ext-moon'});
			return ;
		}
		pageUtil.addOper({
			addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/org/saveOrg", 							//添加保存的地址(必要)。
			saveSuccess : function(data){  								//保存数据成功之后执行的方法(非必要)。
				if (data.success == 'OK') {
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
			updUrl : "DININGSYS/archive/org/getOrgById",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				initUpdateForm(data);//修改初始化数据
				pageUtil.getUpdingData(data.org);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/org/saveOrg", 									//添加保存的地址(必要)。
			saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	function initUpdateForm(data){
		//初始化管理模式
		var orgGlms = data.org.orgGlms;
		$("#orgGlms").val(orgGlms);
		//初始化区域
		var orgArea = data.org.orgArea;
		$("#orgArea").val(orgArea);
		//初始化品牌
		var orgBrand = data.org.orgBrand;
		$("#orgBrand").val(orgBrand);
	}
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/archive/org/deleteOrg",						//删除数据提交的后台地址(必要)。
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
		window.location.href="DININGSYS/archive/org/exportXls?orgCode="+$('#orgCode').val()+"&orgName="+$('#orgName').val();
	});
		
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/org/getPageList",
		colM : "orgCode,orgName,orgSjm,orgKdsj,orgJmgh,orgPhone,orgGlms,orgArea,orgBrand,franchisees,maxCustomer,address,isOwnFlag,isStartFlag,isNewstoreFlag,isLineFlag,remark",
		queryForm : "queryForm",
		colNames : "机构代码,机构名称,速记码,开店时间,加密狗号,电话,管理模式,区域,品牌,加盟商,最大容纳客数,地址,本店标志,启用标志,新店标志,排队标志,备注",
		colWid : {"id":40},
		formatter : {
			"isOwnFlag" : function(v){
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
			},
			"isNewstoreFlag" : function(v){
				if (v == '1') {
					return '<font color="green">是</font>';
				} else {
					return '<font color="red">否</font>';
				}
			},
			"isLineFlag" : function(v){
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
});