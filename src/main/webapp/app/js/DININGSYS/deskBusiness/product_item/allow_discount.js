$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	/**
	 * 
	 * 几率选择没有,只能另外使用个列,checkbox不能改变
	 */
	var discount_count = $("#discount_count");
	$("#grid-table").jqGrid({
        url:path+"/DININGSYS/ProDiscount/getAllItemDiscount",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        rowNum:-1,
        colNames:["id","代码","品项名称","允许打折","允许打折","标准价格","套餐","品项大类","品项小类"],
        colModel:[
            {name:'id',index:'id', width:'10%', align:'center' },
            {name:'num',index:'num', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'yxdz',index:'yxdz', width:'0%',align:'center',hidden:true},
            {name:'yxdz_checkbox',index:'discount_checkbox', width:'20%',align:'center',formatter:format1},
            {name:'standardPrice',index:'standardPrice', width:'20%',align:'center'},
            {name:'isTc',index:'tc', width:'20%',align:'center',formatter:format},
            {name:'bName',index:'bName', width:'20%',align:'center'},
            {name:'cName',index:'cName', width:'20%',align:'center'}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为blackId
            repeatitems : false
        },
        gridview: true,
        autoencode: true,
        loadComplete : function() {
        	grid = $("#grid-table");
       		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
       	    len = ids.length;
        	discount_count.html("品项档案:"+len);
        	/**
        	 * 隐藏grid中id项
        	 */
        	$('.dish-checkbox').click(function () {
        		   this.blur();  
        		   this.focus();
        	});  
        		 
        	$(".dish-checkbox").change(function() {
                var id = $(this).attr("id");
        	    var grid = $("#grid-table");
        		var ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
        	    var len = ids.length; 
        		if ($(this).prop("checked") == true) {
        		    for(var i=0; i<len; i++){ 
        		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
        		        var colVal = getRow.id;
        		        if(colVal == id)
        	        	{
        		        	grid.jqGrid('setCell',id, 'yxdz', "1");
        	        	}
        		   }
        	    }
        	   //点击之后变为不是打钩时触发
        	    else
        	    {
        		    for(var i=0; i<len; i++){ 
        		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
        		        var colVal = getRow.id;
        		        if(colVal == id)
        	        	{
        		        	grid.jqGrid('setCell',id, 'yxdz', "0");
        	        	}
        		    }
        	    }
        	});
		}
    });
	
	$("#grid-table-gate").jqGrid({
        url:path+"/DININGSYS/ProDiscount/getAllItemTypeDiscount",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:200,//高度，表格高度。可为数值、百分比或'auto'
        width:200,//这个宽度不能为百分比
        rowNum:-1,
        colNames:["id","品项小类","打折默认值","打折默认值"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'dgName',index:'dgName', width:'130px',align:'center'},
            {name:'discount',index:'discount', width:'0%',align:'center',hidden:true},
            {name:'discount_checkbox',index:'discount_checkbox', width:'130px',align:'center',editable:true,formatter:format2},
        ],
        rownumbers:false,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        gridview: true,
        autoencode: true,
        loadComplete : function() {
        	/**
        	 * 隐藏grid中id项
        	 */
        	$('.gate-checkbox').click(function () {
        		   this.blur();  
        		   this.focus();
        	});  
        		 
        	$(".gate-checkbox").change(function() {
                var id = $(this).attr("id");
        	    var grid = $("#grid-table-gate");
        		var ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
        	    var len = ids.length; 
        		if ($(this).prop("checked") == true) {
        		    for(var i=0; i<len; i++){ 
        		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
        		        var colVal = getRow.id;
        		        if(colVal == id)
        	        	{
        		        	grid.jqGrid('setCell',id, 'discount', "1");
        	        	}
        		   }
        	    }
        	   //点击之后变为不是打钩时触发
        	    else
        	    {
        		    for(var i=0; i<len; i++){ 
        		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
        		        var colVal = getRow.id;
        		        if(colVal == id)
        	        	{
        		        	grid.jqGrid('setCell',id, 'discount', "0");
        	        	}
        		    }
        	    }
        	});
		}
    });
	
	
	$(".sele-all").click(function() {
	    var grid = $("#grid-table");
		var ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
	    var len = ids.length; 
	   
		if($(".dish-checkbox").prop("checked") == true)
		{
			$(".dish-checkbox").prop("checked",false);
			for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        grid.jqGrid('setCell',colVal, 'yxdz', "0");
		    }
		    
		}
		else
		{
			$(".dish-checkbox").prop("checked",true);
			for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        grid.jqGrid('setCell',colVal, 'yxdz', "1");
		    }
		}
	});
	
    $("#grid-table").hideCol("id");
    $("#grid-table-gate").hideCol("id");
    $.fn.zTree.init($("#dtree"), discount_setting); 
    
    
    $("#refresh").click(function(){
        $("#grid-table-gate").trigger("reloadGrid");
        $("#grid-table").trigger("reloadGrid");
    });
    
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/ProDiscount/exportXls";
	});
    
    $("#add").click(function(){
        layer.confirm('是否保存当前数据?', {icon: 3, title:'提示'}, function(index){
        	var gate_content = "[";
      	    var grid = $("#grid-table-gate");
      		var ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
      	    var len = ids.length;
      	    var isHava = false;
      		for(var i=0; i<len; i++){ 
      			isHava = true;
  		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
  		        var colVal = getRow.id;
  		        var colDiscount = getRow.discount;
  		        gate_content +="{\"id\":\""+colVal+"\",\"discount\":\""+colDiscount+"\"},";
      		}
      		if(isHava)
      		{
      			gate_content = gate_content.substring(0,gate_content.length-1);
      		}
      		gate_content += "]";
      		
      		
      		var dish_content = "[";
      	    grid = $("#grid-table");
      		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
      	    len = ids.length;
      	    var isdishHava = false;
      		for(var i=0; i<len; i++){ 
      			isdishHava = true;
  		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
  		        var colVal = getRow.id;
  		        var colDiscount = getRow.yxdz;
  		        dish_content +="{\"id\":\""+colVal+"\",\"discount\":\""+colDiscount+"\"},";
      		}
      		if(isdishHava)
      		{
      			dish_content = dish_content.substring(0,dish_content.length-1);
      		}
      		dish_content += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/ProDiscount/updateDiscount',
                    data:{gate:gate_content,dish:dish_content},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                            layer.close(index);
                            $("#grid-table-gate").trigger("reloadGrid");
                            $("#grid-table").trigger("reloadGrid");
                        }
                    }
             })
            });
    	
    });
});

function format(v,x,r){
	if(v == 1)
	{
		return "是";
	}
	else if(v == 0)
	{
		return "否";
	}
	else
	{
		return "否";
	}
}

/**
 * 
 * r为对象可以获取到id
 * @param v
 * @param x
 * @param r
 * @returns {String}
 */
function format1(v,x,r){
	if(r.yxdz == '1')
	{
		return "<input class='dish-checkbox' type='checkbox' checked='true'  id='"+r.id+"'/>";
	}
	else if(r.yxdz == '0')
	{
		return "<input class='dish-checkbox' type='checkbox' id='"+r.id+"'/>"
	}
}


function format2(v,x,r){
	if(r.discount == '1')
	{
		return "<input class='gate-checkbox' type='checkbox' checked='true'  id='"+r.id+"'/>";
	}
	else if(r.discount == '0')
	{
		return "<input class='gate-checkbox' type='checkbox'  id='"+r.id+"'/>";
	}
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
			onClick: onChildClick
	    }
};
	

function onChildClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("dtree");
		$("#grid-table").jqGrid('setGridParam',{
			    datatype:'json', 
		        postData:{'id':treeNode.id}, //发送数据 
		        page:1 }).trigger("reloadGrid");
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