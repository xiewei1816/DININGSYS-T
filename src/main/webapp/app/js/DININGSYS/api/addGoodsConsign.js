$(document).ready(function(){
	//树形结构加载
	$.fn.zTree.init($("#myTree"), setting, zNodes);
	
	//加载jqGrid
	initjqGrid();
	
	//日期选择限制
	initDateTime();
});

	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: "0"
			}
		},
		callback: {
			onClick: zTreeOnClick
		}
	};

	var zNodes =[
		{id:1, pId:0, name:"寄存物品状态", open:true},
		{id:101, pId:1, name:"寄存"},
		{id:102, pId:1, name:"取出"},
		{id:103, pId:1, name:"处理"}
	];
	
	/**
	 * 点击事件
	 * @param event
	 * @param treeId
	 * @param treeNode
	 */
	function zTreeOnClick(event, treeId, treeNode) {
	    /*alert(treeNode.id + ", " +treeNode.pId + ", " + treeNode.name);*/
	    var url;
	    if(treeNode.pId == 0){
	        url = 'DININGSYS/yqshapi/code/selectGoodsConsign';
	        jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
	    }
	    if(treeNode.pId == 1){
	    	var gcFlag = '';
	    	var id = treeNode.id;
	    	switch(id){
		    	 case 101:gcFlag = '1';break;
		    	 case 102:gcFlag = '2';break;
		    	 case 103:gcFlag = '3';break;
	    	 }
	        url = 'DININGSYS/yqshapi/code/selectGoodsConsign?gcFlag='+gcFlag;
	        jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
	    }
    }
//初始化部门信息，加载jqGrid
	function initjqGrid(){
		pageUtil.pageInit({
			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
			}
		});
		
		$("#" + pageUtil.tabId).getJqGrid({
			url : "DININGSYS/yqshapi/code/selectGoodsConsign",
			colM : "id,gcFlag,clientSeat,clientName,clientPhone,goodsType,goodsCode,goodsName,goodsNumber,goodsSpecification,goodsColor,goodsExplain,goodsExpirationDate",
			queryForm : "queryForm",
			colNames : "序号,状态,客位,名称,电话,寄存种类,编号,名称,数量,规格,颜色,说明,保质截止日期",
			colWid : {id:"0",gcFlag:"100",clientSeat:"100",clientName:"100",clientPhone:"100",goodsType:"140",goodsCode:"100",goodsName:"100",goodsNumber:"100",goodsSpecification:"100",goodsColor:"100",goodsExplain:"160",goodsExpirationDate:"200"},
			tabHei : 705,
			rowNum : -1,
			rownumbers : true,
			formatter : {
				"gcFlag" : function(v){ //1-寄存；2-取出；3-处理
					if(v == '1'){
						return '寄存';
					}else if(v == '2'){
						return '<font color="green">取出</font>';
					}else{
						return '<font color="blue">处理</font>';
					}
				}
			},
			loadComplete : function() {
				$("#" + pageUtil.tabId).hideCol("id");
			}
		});
		
		$("#add").click(function(){
			$.get("DININGSYS/yqshapi/code/editGoodsConsign",function(str){
	            var addIndex = layer.open({
	                type: 1,
	                title:'添加寄存物品',
	                skin: 'layui-layer-rim',
	                area: ['70%', '80%'],
	                content: str,
	                btn:['保存','关闭'],
	                yes:function () {
	                	if(checkForm()){
	                		$.ajax({
	                			type:'POST',
	                			url:'DININGSYS/yqshapi/code/addGoodsConsign',
	                			data:$("#formid").serialize(),
	                			dataType:'JSON',
	                			success:function (data) {
	                				if(data.msg=="success"){
	                					layer.close(addIndex);
	                					layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
	                					$("#" + pageUtil.tabId).trigger("reloadGrid");
	                				}
	                			}
	                		});
	                	}
	                }
	            });
	        });
		});
		
		$("#update").click(function(){
			var grid = $("#grid-table");
	        var rowId = grid.getGridParam("selrow");
	        var selectRow = grid.getGridParam("selarrrow");
	        if(selectRow.length != 1){
	            layer.alert('请选择一项需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        
	        $.get("DININGSYS/yqshapi/code/editGoodsConsign",{rowId:rowId},function (str) {
	            var addIndex = layer.open({
	                type: 1,
	                title:'修改寄存物品',
	                skin: 'layui-layer-rim',
	                area: ['70%', '80%'],
	                content: str,
	                btn:['保存','关闭'],
	                yes:function () {
	                	if(checkForm()){
	                		$.ajax({
	                			type:'POST',
	                			url:'DININGSYS/yqshapi/code/addGoodsConsign',
	                			data:$("#formid").serialize(),
	                			dataType:'JSON',
	                			success:function (data) {
	                				if(data.msg=="success"){
	                					layer.close(addIndex);
	                					layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
	                					$("#" + pageUtil.tabId).trigger("reloadGrid");
	                				}
	                			}
	                		});
	                	}
	                }
	            });
	        });
		});
		
		$("#takeout").click(function(){		
			var grid = $("#grid-table");
	        var rowId = grid.getGridParam("selrow");
	        var selectRow = grid.getGridParam("selarrrow");
	        if(selectRow.length < 1){
	            layer.alert('请选择需要取出的物品！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        var ids = [];
	        for(var i=0;i<selectRow.length;i++){
	            var tid = grid.jqGrid('getCell',selectRow[i],'id');
	            ids.push(tid);
	        }
	        
	        //判断状态
	        var gcFlag = grid.jqGrid('getCell',selectRow[0],'gcFlag');
	        if(gcFlag == '<font color="green">取出</font>'){
	        	layer.alert('该物品已取出！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        
	        layer.confirm('确认取出物品吗？', {icon: 3, title:'提示'}, function(index){
	            $.ajax({
	                type:'POST',
	                url:"DININGSYS/yqshapi/code/addGoodsConsignByQz",
	                data:{editIds:ids.toString(),gcFlag:2},
	                dataType:'JSON',
	                async:false,
	                success:function (data) {
	                    if(data.msg == 'success'){
	                    	$("#" + pageUtil.tabId).trigger("reloadGrid");
	                    }
	                }
	            });
	           layer.close(index);
	        });
		});
		
		$("#todo").click(function(){
			var grid = $("#grid-table");
	        var rowId = grid.getGridParam("selrow");
	        var selectRow = grid.getGridParam("selarrrow");
	        if(selectRow.length != 1){
	            layer.alert('请选择一项需要处理的物品！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        
	        //判断状态
	        var gcFlag = grid.jqGrid('getCell',selectRow[0],'gcFlag');
	        if(gcFlag == '<font color="green">取出</font>'){
	        	layer.alert('该物品已取出，不能进行处理！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        if(gcFlag == '<font color="green">处理</font>'){
	        	layer.alert('该物品已处理！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        
	        $.get("DININGSYS/yqshapi/code/editGoodsConsign",{clFlag:3,rowId:rowId},function (str) {
	            var addIndex = layer.open({
	                type: 1,
	                title:'处理寄存物品',
	                skin: 'layui-layer-rim',
	                area: ['70%', '80%'],
	                content: str,
	                btn:['保存','关闭'],
	                yes:function () {
	                    $.ajax({
	                        type:'POST',
	                        url:'DININGSYS/yqshapi/code/addGoodsConsign',
	                        data:$("#formid").serialize(),
	                        dataType:'JSON',
	                        success:function (data) {
	                        	if(data.msg=="success"){
	                        		layer.close(addIndex);
	                        		layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
	                        		$("#" + pageUtil.tabId).trigger("reloadGrid");
	        				    }
	                        }
	                    });
	                }
	            });
	        });
		});
		
		$("#del").click(function(){
			var grid = $("#grid-table");
	        var rowId = grid.getGridParam("selrow");
	        var selectRow = grid.getGridParam("selarrrow");
	        if(selectRow.length < 1){
	            layer.alert('请选择需要删除的物品！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }
	        var ids = [];
	        for(var i=0;i<selectRow.length;i++){
	            var tid = grid.jqGrid('getCell',selectRow[i],'id');
	            ids.push(tid);
	        }
	        layer.confirm('确认删除物品吗？', {icon: 3, title:'提示'}, function(index){
	            $.ajax({
	                type:'POST',
	                url:"DININGSYS/yqshapi/code/delGoodsConsign",
	                data:{editIds:ids.toString()},
	                dataType:'JSON',
	                async:false,
	                success:function (data) {
	                    if(data.msg == 'success'){
	                    	layer.alert('操作成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
	                    	$("#" + pageUtil.tabId).trigger("reloadGrid");
	                    }
	                }
	            });
	           layer.close(index);
	        });
		});
		
		$("#refresh").click(function(){
			$("#" + pageUtil.tabId).trigger("reloadGrid");
	    });
	}
	
	/**
	 * 日期选择限制
	 */
	function initDateTime(){
		var jcstart = {
				format: 'YYYY-MM-DD',
				festival:true,
				ishmsVal:false,
				choosefun: function(elem,datas){
					jcend.minDate = datas;
				}
			};
		var jcend = {
			format: 'YYYY-MM-DD',
			festival:true,
			choosefun: function(elem,datas){
				jcstart.maxDate = datas;
			}
		};
		$.jeDate('#jcStartTime',jcstart);
		$.jeDate('#jcEndTime',jcend);
		
		var qzstart = {
				format: 'YYYY-MM-DD',
				festival:true,
				ishmsVal:false,
				choosefun: function(elem,datas){
					qzend.minDate = datas;
				}
			};
		var qzend = {
			format: 'YYYY-MM-DD',
			festival:true,
			choosefun: function(elem,datas){
				qzstart.maxDate = datas;
			}
		};
		$.jeDate('#qzStartTime',qzstart);
		$.jeDate('#qzEndTime',qzend);
	}
	
/**
 * 判断查询项
 * @param searchItems
 */
function setSearchVal(searchItems){
	 switch(searchItems)
	 {
	 case 10:
		 if($("#cbx10").is(':checked')){
			 $("#goodsExpirationDate").val("1");
		 }else{
			 $("#goodsExpirationDate").val("");
		 }
		 break;
	 case 11:
		 if($("#cbx11").is(':checked')){
			 $("#gcEndTime").val("1");
		 }else{
			 $("#gcEndTime").val("");
		 }
		 break;
	 } 
 }

//表单验证
function checkForm(){
	var flag = true;
	var clientName = $("#clientNameEdit").val();
	if(clientName == ""){
		layer.tips('客户名称不能为空!', $("#clientNameEdit") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var clientPhone = $("#clientPhoneEdit").val();
	if(clientPhone == ""){
		layer.tips('客户电话不能为空!', $("#clientPhoneEdit") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var goodsName = $("#goodsNameEdit").val();
	if(goodsName == ""){
		layer.tips('物品名称不能为空!', $("#goodsNameEdit") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	var goodsNumber = $("#goodsNumberEdit").val();
	if(goodsNumber == ""){
		layer.tips('数量不能为空!', $("#goodsNumberEdit") , {guide: 1, tipsMore:true, time: 2000}); 
		flag = false;
	}
	return flag;
}
