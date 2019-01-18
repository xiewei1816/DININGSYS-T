$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$(".edit_username").blur(function(){
		checkName();
	});
	
	$(".add").click(function(){
		pageUtil.addOper({
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
				return checkName();
			},
			saveUrl : "DININGSYS/nonMember/saveNonMember", 						//添加保存的地址(必要)。
			saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#update").click(function(){
		pageUtil.updOper({
			updUrl : "DININGSYS/nonMember/getNonMemberByID",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				pageUtil.getUpdingData(data.nonMember);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
				return checkName();
			},
			saveUrl : "DININGSYS/nonMember/saveNonMember", 									//添加保存的地址(必要)。
			saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
				if (resultData.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/nonMember/deleteNonMember",										//删除数据提交的后台地址(必要)。
			success : function(){												//删除数据成功之后执行的方法(非必要)。
				
			}
		});
	});
	
	$("#grid-table").getJqGrid({
		url : "DININGSYS/nonMember/getPageList",
		colM : "name,emp.empName,accountType,balance,creditLimit,repaymentPeriod"
				+",isDisable,contacts,phone,address,explains,createTime",
		queryForm : "queryForm",
		colNames : "名称,客户经理,账户类型,账户余额,信用额度,还款周期,停用,联系人,电话,地址,说明,创建时间",
		colWid : {"name":140,"explain":180},
		tabHei : 150,
		onCellSelect : function(rowid){
			$("#query_memberId_1").val(rowid);
			$("#grid-table1").jqGrid('setGridParam',{page:1,postData:changeJOSNr("queryForm1")});
			$("#grid-table1").trigger("reloadGrid");
		},
		pager : "grid-pager",
		formatter : {
			"accountType" : function(v){
				if (v == '1') {
					return '<font color="green">个人</font>';
				} else {
					return '<font color="blue">公司</font>';
				}
			},
			"isDisable" : function(v){
				if (v == '0') {
					return '<font color="green">否</font>';
				} else {
					return '<font color="red">已停用</font>';
				}
			}
		},
		loadComplete : function() {
		}
	});
	
	
	$("#grid-table1").getJqGrid({
		url : "DININGSYS/nonMemberCredit/getPageList",
		colM : "documentNo,documentType,aymentAmount,creditAmount,paidAmount,discountAmount"
				+",paymentTime,operator.empName,explains,settlementFlowNum",
		queryForm : "queryForm1",
		colNames : "单据号,付款单类型,付款金额,挂账金额,实收金额,优惠金额,付款时间,操作员,备注,结算流水编号",
		colWid : {"name":140,"explain":180},
		pager : "grid-pager1",
		dynamicHei : 450,
		formatter : {
			"accountType" : function(v){
				if (v == '1') {
					return '<font color="green">个人</font>';
				} else {
					return '<font color="blue">公司</font>';
				}
			},
			"isDisable" : function(v){
				if (v == '0') {
					return '<font color="green">否</font>';
				} else {
					return '<font color="red">已停用</font>';
				}
			}
		},
		loadComplete : function() {
		}
	});
});

function checkName(){
	var istrue = true;
	var id = $(".edit_id").val();
	$.ajax({
		url : "DININGSYS/nonMember/checkNonMemberByName",
		data : {"id": id == "" ? 0 : id,checkName:$("#edit_named").val()},
		dataType : "json",
		type : "post",
		async : false,
		success : function(d){
			if(d.state == 2){
				layer.alert('你提交的非会员名称已经存在，请重新填写！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				istrue = false;
			}
		}
	});
	return istrue;
}