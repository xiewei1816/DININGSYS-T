$(function(){
	
	//初始化下拉列表默认选中第一条数据
	$("#j-dicWareId option:first").prop("selected", 'selected');
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
			
		}
	});
	$("#" + pageUtil.tabId).getJqGrid({
		url : "DININGSYS/inve/discPoint/getPageList",
		colM : "serialNumber,wareName,joinItemName,number,sumAmount,diffNum,diffAmount,unit,dateTime,sinceNumber,remark,createDate",
		queryForm : "queryForm",
		colNames : "流水号,仓库,物品名称,数量,金额,差异数量,差异金额,物品单位,日期,自编号,备注,创建时间",
		colWid : {"id":40},
		rowList:"10,20,50,100",
		rowNum:10,
		loadComplete : function(){
		}
	});
	$('#itemTypeId').on('change', function(e, params) {
		$(".leftmenus").lSTreeOption("postData",{wareID:$('#j-dicWareId').val(),itemTypeId:params.selected});
		$(".leftmenus").lSTreeRefresh();
	});
	$('#j-dicWareId').on('change', function(e, params) {
		$("#j-itemTable").jqGrid("clearGridData");
		$(".leftmenus").lSTreeOption("postData",{wareID:params.selected,itemTypeId:$('#itemTypeId').val()});
		$(".leftmenus").lSTreeRefresh();
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
	$('.j-disc').click(function(){
		$(".leftmenus").lSTreeRefresh();
		var idx=layer.open({
			  type: 1,
			  title:'盘点',
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
				 
				 var jdicWareId=$('#j-dicWareId').val();
				 if(jdicWareId==''||jdicWareId==null){
					 layer.tips('请选择盘点仓库!', '#deptMateTitleName');
					 return false;
				 }
				 var dateTime=$('#dateTime').val();
				 if(dateTime==''||dateTime==null){
					 layer.tips('请选择日期!', '#dateTime');
					 return false;
				 }
				 $.post('DININGSYS/inve/discPoint/saveDisc',$('#editForm').serialize(),function(data){
					 //var json=$.parseJSON(data);
					 var json = data;
					 if(json.success=='OK'){
						 layer.close(index);
						 $('#editForm')[0].reset();  //成功后重置表单
						 $("#j-itemTable").jqGrid("clearGridData");//重置table
						 layer.alert(json.msg,{title :'提示',icon: 1, skin: 'layer-ext-moon'});
						 $("#" + pageUtil.tabId).trigger("reloadGrid");
					 }else{
						 layer.alert(json.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
						 return false;
					 }
				 });
			  },
			  cancel: function(inx){ 
				  $('#editForm')[0].reset();  //成功后重置表单
				  $("#j-itemTable").jqGrid("clearGridData");//重置table
			  } 
		 });
	});
	$("#j-itemTable").jqGrid({
        datatype : "json",
        colNames : ['编号','物品编码','物品名称','原价','现价','库存数量','盘点数量','单位','自编号','金额','备注','操作'],
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
	initTree($('#j-dicWareId').val(),$('#itemTypeId').val());//初始化
});
function initTree(wareID,itemTypeId){
	 $(".leftmenus").getLSTree({
		url :"DININGSYS/inve/inventory/ajaxInveItems",
		postData:{wareID:wareID,itemTypeId:itemTypeId},
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
        unit:nodeData.unit,  
        sinceNumber:'',  
        sumAmount:'',  
        remark:''   
    };   
    //将新添加的行插入到第一列  
    $("#j-itemTable").jqGrid("addRowData", nodeData.id, dataRow, "first");  
    $('#j-itemTable').editRow(nodeData.id);
}