var cookers = [];//对象数组 ;
$(function () {
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	 });
	$("#sele-dep option").each(function(){
		var cooker = {
				id:$(this).val(),
				empName:$(this).html()
		};
		cookers.push(cooker);
	});
	
	
	 var grid = $("#grid-table");
     var sele_dep = $("#sele-dep");
     var isALL = $("#isALL");
     var placeIdArray = []; //初始化区域id数组，方便后面调用
     var colModelData = [
                         {name:'id',index:'id', width:'0%', align:'center' },
                         {name:'id',index:'itemId', width:'0%',align:'center',hidden:true},
                         {name:'num',index:'itemCode', width:'15%',align:'center'},
                         {name:'name',index:'csName', width:'15%',align:'center'},
                         {name:'cs',index:'cs', width:'15%',align:'center',formatter:format1},
                     ];
    var colNames = ["id","品项id","品项编号","品项名称","厨师名称"];
	
    //动态增加列
    $("#grid-table").jqGrid({
        url:"DININGSYS/DgItemFromCook/getAllData",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        rowNum:-1,
        colNames:colNames,
        colModel:colModelData,
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        multiselect:true
    });
    $("#grid-table").hideCol("id");
    
    
   $("#refresh").click(function(){
    	 $("#grid-table").trigger("reloadGrid");
    	
    });
    
    $("#save").click(function(){
        layer.confirm('确认保存当前市别品项价格?', {icon: 3, title:'提示'}, function(index){
        	var send='[';
        	var hava_rows = $("#grid-table").jqGrid('getRowData');
        	
        	if(hava_rows.length > 0){
                for (var i in hava_rows) {
                	var cs = $("#sele-"+hava_rows[i].id).val();
                	var id = hava_rows[i].id; //品项id
                	var itemId = hava_rows[i].itemId; //品项id
                	var itemCode = hava_rows[i].itemCode;//品项编码
                	send +="{\"cookId\":\""+cs+"\",\"id\":\""+id+"\"},";
                }
            }	 
        	if(hava_rows.length >0)
        	{
            	send = send.substring(0,send.length-1);	
        	}
        	send += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/DgItemFromCook/updateData',
                    data:{data:send},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                        	 $("#grid-table").trigger("reloadGrid");
                        }
                    }
                });
                layer.close(index);
            });
	});
    
    $("#set-s").click(function(){
  	  var rows = grid.jqGrid('getRowData');
        if(rows.length > 0){
              for (var i in rows) {
              	    var getRow = rows[i];//获取当前的数据行  
     		        var id = getRow.id;
     		       $("#sele-"+getRow.id).find("option[value='"+sele_dep.val()+"']").attr("selected",true);
              }
         }
	});
    
	
    $.fn.zTree.init($("#dtree"), discount_setting); 
    $("#disTree").outerHeight($(window).innerHeight()-160);
    resize();
	
});

function format1(c,o,r){
	var ret="<select id='sele-"+r.id+"'>";
	if(c == '0')
	{
		ret +="<option value='0' selected></option>";
	}
	else
	{
		ret +="<option value='0'></option>";
	}
	for(var i in cookers)
	{
		if(c == cookers[i].id)
		{
			ret +="<option value='"+cookers[i].id+"' selected>"+cookers[i].empName+"</option>";	
		}
		else
		{
			ret +="<option value='"+cookers[i].id+"'>"+cookers[i].empName+"</option>";
		}
	}
    ret +="</select>";
    return ret;
}

var discount_setting = {
	    async : {
    	view: {
			dblClickExpand: false,
			showLine: false
		},
	    enable : true,//是否异步加载
        url : path+'/DININGSYS/ProDiscount/getTreeNodes',//加载数据的url  /DININGSYS/ProDiscount/getTreeNodes
        autoParam : [ "id","childCount"],//异步发送请求时,表示自动传指定属性的参数值
		type: "get",
        dataFilter : function(treeId, parentNode, childNodes) {//这里由于本人设置的节点属性跟zTree不一致所以进行了过滤     
            for (var i = 0, l = childNodes.length; i < l; i++) {
	               childNodes[i].isParent = true;
	            }
	            return childNodes;
	        }
	    },
	    callback : {
			onAsyncSuccess: initChildTree,
			onClick: onClick
	    }
};
	

function onClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("dtree");
		$("#grid-table").jqGrid('setGridParam',{
			    datatype:'json', 
		        postData:{'id':treeNode.id} //发送数据  
			}).trigger("reloadGrid");
}

function initChildTree() {
	var treeObj = $.fn.zTree.getZTreeObj("dtree");
	expandNodes(treeObj.getNodes());
}

	
function expandNodes(nodes) {
	if (!nodes) return;
	var zTree = $.fn.zTree.getZTreeObj("dtree");
	for (var i=0, l=nodes.length; i<l; i++) {
		zTree.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync) {
			expandNodes(nodes[i].children);
		}
	}
}

function resize(){
    $(window).resize(function() { 
    	 $("#disTree").outerHeight($(window).innerHeight()-160);
   }) 
}

function show_bar()
{
	if($(".left-content").css("display")=="none")
	{
		var imgPath = path+"/app/img/center0.gif";
		$(".left-content").css("display","block");
		$(".right-content").css("margin-left","10px");
		$(".control").css("margin-left","210px");
		$(".jq-margin").css("margin-left","220px");
		$(".sele").css("margin-left","220px");
		$(".control").css("background-image","url("+imgPath+")");
	}
	else
	{
		var imgPath = path+"/app/img/center.gif";
		$(".left-content").css("display","none");
		$(".right-content").css("margin-left","10px");
		$(".control").css("margin-left","10px");
		$(".jq-margin").css("margin-left","20px");
		$(".sele").css("margin-left","20px");
		$(".control").css("background-image","url("+imgPath+")");
	}
}