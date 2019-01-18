$(function(){
	
	//初始化下拉列表默认选中第一条数据
	$("#wareID option:first").prop("selected", 'selected');
	$("#depaId option:first").prop("selected", 'selected');
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/inve/depaMate/getPageList",
		colM : "serialNumber,mateType,dep_name,wareName,joinItemName,number,unit,dateTime,sinceNumber,remark,createDate",
		queryForm : "queryForm",
		colNames : "流水号,类型,所属部门,仓库,物品名称,数量,物品单位,日期,自编号,备注,创建时间",
		colWid : {"id":40},
		rowList:"10,20,50,100",
		rowNum:10,
		loadComplete : function(){
		},
		formatter:{
			"mateType" : function(v){
				if(v==0){
					return '部门领料';
				}else if(v==1){
					return '<span style="color:blue;">部门退料</span>';
				}
			}
		}
	});
	$('#editForm').on('change','.j-item-type-re', function(e, params) {
		$("#j-tree").lSTreeOption({
			url : "DININGSYS/inve/inventory/ajaxInveItems",
			postData : {wareID:$('#j-ware-id').val(),itemTypeId:params.selected}
		});
		$("#j-tree").lSTreeRefresh();
	});
	$('#editForm').on('change','.j-ware-re', function(e, params) {
		$("#j-itemTable").jqGrid("clearGridData");
		$("#j-tree").lSTreeOption({
			url : "DININGSYS/inve/inventory/ajaxInveItems",
			postData : {wareID:params.selected,itemTypeId:$('#itemTypeId').val()}
		});
		$("#j-tree").lSTreeRefresh();
	});
	$('#editForm').on('change','.j-item-type-in', function(e, params) {
		$("#j-tree").lSTreeOption({
			url :"DININGSYS/inve/proc/getListItems",
			postData : {wareID:'',itemTypeId:params.selected}
		});
		$("#j-tree").lSTreeRefresh();
	});   
	
	$("#j-itemTable").on("click",".fa-remove",function(){
		var id = $(this).closest("tr").find("td[aria-describedby$='_id']").attr("title");
		$(".leftmenus .content li[sid='"+id+"']").show();
		$(this).closest("tr").remove();
	}).on("click",".fa-check",function(){
		var id = $(this).closest("tr").find("td[aria-describedby$='_id']").attr("title");
		var num = $(this).closest("tr").find("input[name='number']").val();
		var presPrice=$(this).closest("tr").find("input[name='presPrice']").val();
		if(isNaN(num)||isNaN(presPrice)||num<=0||presPrice<=0){
			layer.alert('现价与数量请输入正确的数字',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
			return false;
		}
		var sumAmount=parseFloat(num*presPrice);
		$(this).closest("tr").find("td[aria-describedby$='_sumAmount']").attr("title",sumAmount).html(sumAmount);
		$("#j-itemTable").saveRow(id);
	}).on("keyup",":text",function(e){
		var tr = $(this).closest("tr");
		if(e.keyCode == 13){
			tr.find(".fa-check").trigger("click");
			if(tr.index() != $("#j-itemTable tr").size() - 1){
				$("#j-itemTable tr:eq("+(tr.index() + 1)+") :text:first").focus();
			}
		}
	});
		
	$('.j-depaMete').click(function(){
		var proctype=$(this).data('proctype');
		var title='部门领用';
		$('#deptMateTitleName').html('发料仓库');
		$('#j-deptSpan').html('领料部门');
		$('#j-ware-id').removeClass('j-ware-in').addClass('j-ware-re');
		$('#itemTypeId').removeClass('j-item-type-in').addClass('j-item-type-re');
		$('#mateType').val(0);
	if('re'==proctype){
		title='部门退料';
		$('#deptMateTitleName').html('收料仓库');
		$('#j-deptSpan').html('退料部门');
		$('#j-ware-id').removeClass('j-ware-re').addClass('j-ware-in');
		$('#itemTypeId').removeClass('j-item-type-re').addClass('j-item-type-in');
		$('#mateType').val(1);
		
	}else if('le'==proctype){
		$("#j-itemTable").jqGrid("clearGridData");
		$("#j-tree").lSTreeOption({
			url : ctx+"/DININGSYS/inve/inventory/ajaxInveItems",
			postData : {wareID:$('#j-ware-id').val(),itemTypeId:$('#itemTypeId').val()}
		});
		$("#j-tree").lSTreeRefresh();
	}
	layer.open({
		  type: 1,
		  title:title,
		  skin: 'layui-layer-rim', //加上边框
		  area: ['95%', '93%'], //宽高
		  content:$("#cgrk-pan"),
		  btn: ['确认'],
		  zIndex : 1050,
		  yes: function(index, layero){
			    //获取是否有值处于编辑状态
			 var editSize= $("#j-itemTable :text").size(); 
			 if(editSize>0){
				 layer.alert('还有物品处于编辑状态,请保存后再提交',{title :'提示信息',icon: 0, skin: 'layer-ext-moon'});
				 return false;
			 }
			 //获取table的值
			 var tableData=$("#j-itemTable").jqGrid("getRowData");
			 if(tableData.length<1){
				 layer.alert('请添加物品',{title :'提示信息',icon: 0, skin: 'layer-ext-moon'});
				 return false;
			 }
			 var jsonStr=JSON.stringify(tableData);
			 $('#jsonArr').val(jsonStr);
			 
			 var wareID=$('#wareID').val();
			 if(wareID==''||wareID==null){
				 layer.tips('请选择发料仓库!', '#deptMateTitleName');
				 return false;
			 }
			 
			 var depaId=$('#depaId').val();
			 if(depaId==''||depaId==null){
				 layer.tips('请选择领料部门!', '#j-deptSpan');
				 return false;
			 }
			 
			 var dateTime=$('#dateTime').val();
			 if(dateTime==''||dateTime==null){
				 layer.tips('请选择日期!', '#dateTime');
				 return false;
			 }
			 $.post('DININGSYS/inve/depaMate/depaMateSave',$('#editForm').serialize(),function(data){
				 //var json=$.parseJSON(data);
				 var json = data;
				 if(json.success=='OK'){
					 layer.close(index);
					 $('#editForm')[0].reset();  //成功后重置表单
					 $("#j-itemTable").jqGrid("clearGridData");//重置table
					 layer.alert(json.msg,{title :'提示',icon: 1, skin: 'layer-ext-moon'});
					 $("#" + pageUtil.tabId).trigger("reloadGrid");
				 }else{
					 layer.alert( json.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
					 return false;
				 }
			 });
		  },
		  cancel: function(index){ 
			  $('#editForm')[0].reset();  //成功后重置表单
			  $("#j-itemTable").jqGrid("clearGridData");//重置table
		  }
		});
	});
	$("#j-itemTable").jqGrid({
        datatype : "json",
        colNames : ['编号','编码','物品名称','原价','现价','库存数量','数量','单位','自编号','金额','备注','操作'],
        colModel : [ 
                 {name : 'id',index : 'id',width : 120,hidden:true}, 
                 {name : 'itemNo',index : 'itemNo',width : 120}, 
                 {name : 'itemName',index : 'itemName',width : 120}, 
                 {name : 'origPrice',index : 'origPrice',width : 50}, 
                 {name : 'presPrice',index : 'presPrice',width : 50,editable : true}, 
                 {name : 'inveNum',index : 'inveNum',width : 80}, 
                 {name : 'number',index : 'number',width : 80,editable : true}, 
                 {name : 'unit',index : 'unit',width : 80}, 
                 {name : 'sinceNumber',index : 'sinceNumber',width : 150,editable : true}, 
                 {name : 'sumAmount',index : 'sumAmount',width : 50}, 
                 {name : 'remark',index : 'remark',width : 250,editable : true},
                 {name : 'opery',index : 'opery',width : 70,formatter:function(d,opt,data){
                	 return "<i class='fa fa-check'></i>&nbsp;&nbsp;<i class='fa fa-remove'></i>";
                 }}
               ],
        viewrecords : true,
        height:'mixed',
        ondblClickRow:function(rowid){
           $('#j-itemTable').editRow(rowid);
        }
	});
	initTree($('#itemTypeId').val());
});

function initTree(itemTypeId){
	 $("#j-tree").getLSTree({
		url :"DININGSYS/inve/proc/getListItems",
		postData:{itemTypeId:itemTypeId},
		dynamicHei : 10,											 				//动态高度（dynamicHei是被减数为：$(window).height() - dynamicHei）
		isEdit : false,
		nodeIdName:'id',
		fixedWid:188,
		nodeTitleName:'itemName',
		isCheck : false,
		dynamicHei:360,
		rootNodeName : "物品",									 				//树形结构的标题值
		refresh : function(){										 				//点击刷新按钮刷新数据之后执行的方法
			$("#qur_consArea").val("").siblings(":hidden").val("");
			$(".query-pan .search-btns").trigger("click");
		},
		nodeclick : function(dom,nodeData){							 				//点击树形结构中所有节点执行方法（传入nodeData,该节点的数据）
			$(dom).closest("li").hide();
			$("#qur_consArea").val(nodeData.itemName).siblings(":hidden").val(nodeData.id);
			$(".query-pan .search-btns").trigger("click");
			addRow(nodeData);
		},	
		leftHide : function(){										 				//当点击隐藏按钮执行的方法
			$(".menuright").animate({marginLeft:70},300);
		},
		leftShow : function(){
			$(".menuright").animate({marginLeft:230},300);
		}
	});
}
function addRow(nodeData){
    var ids = jQuery("#j-itemTable").jqGrid('getDataIDs');  
    if(ids.indexOf(nodeData.id)>-1){  //如果包含就编辑
    	layer.alert('['+nodeData.itemName+']  已添加',{title :'错误',icon: 0, skin: 'layer-ext-moon'});
    	return false;
    }
    var dataRow = {    
        id:nodeData.id,
        itemNo:nodeData.itemNo,
        itemName:nodeData.itemName,  
        origPrice:nodeData.price,  
        presPrice:nodeData.price, 
        inveNum:nodeData.number,
        number:'',  
        unit:nodeData.unit,  
        sinceNumber:'',  
        sumAmount:'',  
        remark:''   
    };   
    //将新添加的行插入到第一列  
    $("#j-itemTable").jqGrid("addRowData", nodeData.id, dataRow, "first");  
    $('#j-itemTable').editRow(nodeData.id);
}