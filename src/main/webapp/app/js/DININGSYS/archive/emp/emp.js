$(function(){
	
	//初始化下拉列表默认选中第一条数据
	$("#empOrganization option:first").prop("selected", 'selected');
	$("#empDepartment").find("option").val(0).text("顶级部门").prop("selected",true);
	$("#empDuties option:first").prop("selected", 'selected');
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#add").click(function(){
		pageUtil.addOper({
			addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
				$(".menuTree").hide(); //添加之前隐藏部门选择
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/emp/saveEmp", 							//添加保存的地址(必要)。
			saveSuccess : function(data){  								//保存数据成功之后执行的方法(非必要)。
				if (data.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert(data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#update").click(function(){
		pageUtil.updOper({
			updBefore : function(){												//修改数据界面之前执行的方法(非必要)。
				$(".menuTree").hide(); //修改之前隐藏部门选择
			},
			updUrl : "DININGSYS/archive/emp/getEmpById",									//获取修改数据的后台地址(必要)。
			updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
				initUpdateForm(data);//修改初始化数据
				pageUtil.getUpdingData(data.emp);                            	//自动给编辑界面里填值。
			},
			saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
			},
			saveUrl : "DININGSYS/archive/emp/saveEmp", 									//添加保存的地址(必要)。
			saveSuccess : function(data){  							    //保存数据成功之后执行的方法(非必要)。
				if (data.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	function initUpdateForm(data){
		//初始化组织机构
		var empOrganization = data.emp.empOrganization;
		$("#empOrganization").val(empOrganization);
		//初始化部门
		if(data.dep != null){
			var id = data.dep.id;
			var depName = data.dep.depName; 
			$("#empDepartment").find("option").val(id).text(depName).prop("selected",true);
		}
		//初始化职位
		var empDuties = data.emp.empDuties;
		$("#empDuties").val(empDuties);
		//初始化民族
		var nation = data.emp.nation;
		$("#nation").val(nation);
	}


	$("#resetPwd").click(function () {
        var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要重置密码的用户！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        else if(selectRow.length >1)
        {
            layer.alert('只能同时重置一位用户的密码！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }

        var id = grid.jqGrid('getCell',selectRow[0],'id'); //获取id

		layer.confirm("是否确定重置该用户密码？",function (index) {
			$.ajax({
			    type:'POST',
			    url:'DININGSYS/archive/emp/resetPwd',
			    data:{id:id},
			    dataType:'JSON',
			    success:function(data){
			        if(data.success){
						layer.msg("用户密码重置成功");
						layer.close(index);
			        }else{
			        	layer.msg("服务器忙");
					}
			    }
			})
        })

    })

	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/archive/emp/deleteEmpTrash",						//删除数据提交的后台地址(必要)。
			success : function(data){												//删除数据成功之后执行的方法(非必要)。
				if (data.success == 'OK') {
					layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
			}
		});
	});
	
	$("#export").click(function(){
		window.location.href="DININGSYS/archive/emp/exportXls?isDel=0&empCode="+$('#empCode').val()+"&empName="+$('#empName').val();
	});
	
 	$("#refresh").click(function(){
		$("#" + pageUtil.tabId).trigger("reloadGrid");
    });
		
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/archive/emp/getPageList?isDel=0",
		colM : "id,empCode,empName,empOrganization,empDepartment,empDuties,empDob,empCardno,empSex,isMarry,empCardid,isOrguserFlag,isManagerFlag,isStartFlag",
		queryForm : "queryForm",
		colNames : "id,员工编号,员工姓名,所属机构,所属部门,职务,出生日期,身份证号,性别,婚姻状况,员工卡ID,集团用户标志,管理员标志,启动标志",
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
			//$("#" + pageUtil.tabId).setRowData(2,null,{display: 'none'});
            $("#" + pageUtil.tabId).hideCol("id");
		}
	});

    
	/* ******************************  员工信息回收站  **************************** */	
	$("#delo").click(function(){
		layer.open({
			  type: 2,
			  title: '员工信息【回收站】',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['80%', '90%'],
			  content: 'DININGSYS/archive/emp/trash',
			  end:function(){
				  $("#" + pageUtil.tabId).trigger("reloadGrid");
			  }
		});
	});

});