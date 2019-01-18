$(function(){
	
	/**
 	* 初始化时间
 	*/
	var start = {
	    format: 'YYYY-MM-DD hh:mm:ss',
	    festival:true,
	    ishmsVal:false,
	    maxDate: '2099-06-30 23:59:59', //最大日期
	    choosefun: function(elem,datas){
	        end.minDate = datas; //开始日选好后，重置结束日的最小日期
	    }
	};
	var end = {
	    format: 'YYYY-MM-DD hh:mm:ss',
	    festival:true,
	    maxDate: '2099-06-16 23:59:59', //最大日期
	    choosefun: function(elem,datas){
	        start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
	    }
	};
	$('#startTime').jeDate(start);
	$('#endTime').jeDate(end);

	/* ******************************************************************************************** */
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#add").click(function(){
		pageUtil.addOper({
			addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
				return checkForm();
			},
			saveUrl : "DININGSYS/businessMan/fydj/saveFydj", 							//添加保存的地址(必要)。
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
			updUrl : "DININGSYS/businessMan/fydj/getFydjById",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				//初始化组织机构
				var orgId = data.fydj.fydjOrganization;
				$("#fydjOrganization").val(orgId);
				//初始化费用名称
				var fydjId = data.fydj.fydjName;
				$("#fydjName").val(fydjId);
				pageUtil.getUpdingData(data.fydj);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
				return checkForm();
			},
			saveUrl : "DININGSYS/businessMan/fydj/saveFydj", 									//添加保存的地址(必要)。
			saveSuccess : function(data){  							    //保存数据成功之后执行的方法(非必要)。
				if (data.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});

	$("#export").click(function(){
		window.location.href="DININGSYS/businessMan/fydj/exportXls?startTime="+$('#startTime').val()+"&endTime="+$('#endTime').val();
	});
	
	$("#refresh").click(function(){
    	$("#" + pageUtil.tabId).jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan")});
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
		
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/businessMan/fydj/getPageList",
		colM : "fydjName,fydjType,fydjExpend,fydjEarning,fydjTime,fydjOrganization,createTime,fydjAbstract",
		queryForm : "queryForm",
		colNames : "费用名称,费用类型,支出费用,收入费用,费用发生时间,所属组织机构,创建时间,摘要",
		colWid : {"id":40},
		formatter : {
		},
		loadComplete : function() {
		}
	});
	
});

function cheakChooseOne(id){
	var chooseOne = $("#"+id).val().trim();
	var fydjExpend = $("#fydjExpend").val().trim();
	var fydjEarning = $("#fydjEarning").val().trim();
	if(chooseOne == ""){
		if(fydjExpend != "" || fydjEarning != ""){
			$("#"+id).blur();
			layer.tips('支出费用与收入费用只能选择输入其中一种！', '#'+id);
		}
	}
}

//表单验证
function checkForm(){
	var flag = true;
	var fydjName = $("#fydjName").val();
	if(fydjName == ""){
		layer.tips('费用名称不能为空!', $("#fydjName") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var fydjExpend = $("#fydjExpend").val();
	var fydjEarning = $("#fydjEarning").val();
	if(fydjExpend == "" && fydjEarning == ""){
		layer.tips('请输入支出费用或收入费用！', $("#fydjExpend") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var fydjTime = $("#fydjTime").val();
	if(fydjTime == ""){
		layer.tips('发生时间不能为空!', $("#fydjTime") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var fydjOrganization = $("#fydjOrganization").val();
	if(fydjOrganization == ""){
		layer.tips('所属机构不能为空!', $("#fydjOrganization") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	return flag;
}