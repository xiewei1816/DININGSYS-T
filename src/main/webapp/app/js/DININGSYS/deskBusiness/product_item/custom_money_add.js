var custom_money_add = function () {
	var treeNodeId='';
	function pagerInit()
    {
		$('#search-form :text').keypress(function(e){
            if(e.keyCode==13){
    			e.preventDefault(); //阻止回车提交表单
            	$("#search-button").trigger("click");
            }
		});
		
		/**
		 * 
		 * 初始化自动刷新次,加载上个框中数据
		 */
		var dish_content = "";
  	    grid = $("#grid-table-1");
  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
  	    len = ids.length;
  	    var isdishHava = false;
  		for(var i=0; i<len; i++){ 
  			isdishHava = true;
	        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
	        var colVal = getRow.itemId;
	        dish_content +=colVal+",";
  		}
  		if(isdishHava)
  		{
  			dish_content = dish_content.substring(0,dish_content.length-1);
  		}
  		
		$("#grid-table-4").jqGrid({
	        url:path+"/DININGSYS/ProDiscountPan/getAllDish?yxdz=0",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:150,//高度，表格高度。可为数值、百分比或'auto'
	        width:630,//这个宽度不能为百分比
	        rowNum:-1,
	        colNames:["id","编号","名称","单位","标准价格","连锁方案价格","说明"],
	        colModel:[
	            {name:'id',index:'id', width:'10%', align:'center' },
	            {name:'num',index:'num', width:'20%',align:'center'},
	            {name:'name',index:'name', width:'20%',align:'center'},
	            {name:'unit',index:'unit', width:'20%',align:'center'},
	            {name:'standardPrice',index:'standardPrice', width:'20%',align:'center'},
	            {name:'costPrice',index:'costPrice', width:'20%',align:'center'},
	            {name:'pxxtsm',index:'pxxtsm', width:'20%',align:'center'}
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为blackId
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        postData:{"ids":dish_content}, //发送数据 
	        loadComplete : function() {
		  		grid = $("#grid-table-4");
		  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
		  	    len = ids.length;
		  		$(".len-1").html(len);
			},
			ondblClickRow: function(id){ 
				 var grid = $("#grid-table-4");
		  		 getRow = grid.getRowData(id);//获取当前的数据行  
				//双击行
		  		$("#grid-table-5").addRowData(id, getRow, "first");
		  		$("#grid-table-4").delRowData(id);
		  		
		  		var len = $(".len-1").html();
		  		$(".len-1").html((Number(len)-1));
		  		var len2 = $(".len-2").html();
		  		$(".len-2").html((Number(len2)+1));
			} 
	    });
  		
		$("#grid-table-5").jqGrid({
	        url:path+"/DININGSYS/ProDiscountPan/getHaveDish?yxdz=0",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:150,//高度，表格高度。可为数值、百分比或'auto'
	        width:630,//这个宽度不能为百分比
	        rowNum:-1,
	        colNames:["id","编号","名称","单位","标准价格","连锁方案价格","说明"],
	        colModel:[
	            {name:'id',index:'id', width:'10%', align:'center' },
	            {name:'num',index:'num', width:'20%',align:'center'},
	            {name:'name',index:'name', width:'20%',align:'center'},
	            {name:'unit',index:'unit', width:'20%',align:'center'},
	            {name:'standardPrice',index:'standardPrice', width:'20%',align:'center'},
	            {name:'costPrice',index:'costPrice', width:'20%',align:'center'},
	            {name:'pxxtsm',index:'pxxtsm', width:'20%',align:'center'}
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为blackId
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        postData:{"ids":dish_content}, //发送数据 
	        loadComplete : function() {
		  		grid = $("#grid-table-5");
		  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
		  	    len = ids.length;
		  		$(".len-2").html(len);
			},
			ondblClickRow: function(id){ 
				 var grid = $("#grid-table-5");
		  		 getRow = grid.getRowData(id);//获取当前的数据行  
				//双击行
		  		$("#grid-table-4").addRowData(id, getRow, "first");
		  		$("#grid-table-5").delRowData(id);
		  		
		  		var len = $(".len-2").html();
		  		$(".len-2").html((Number(len)-1));
		  		var len2 = $(".len-1").html();
		  		$(".len-1").html((Number(len2)+1));
			} 
	    });
		
	    $("#grid-table-4").hideCol("id");
	    $("#grid-table-5").hideCol("id");
	    
	    $(".sele-all-1").click(function(){
	  	    grid = $("#grid-table-4");
	  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len = ids.length;
	  		for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        $("#grid-table-5").addRowData(colVal, getRow, "first");
		  		$("#grid-table-4").delRowData(colVal);
	  		}
	  		
	  		$(".len-1").html('0');
	  		grid2 = $("#grid-table-5");
	  		ids2 = grid2.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len2 = ids2.length;
	  	    
	  		$(".len-2").html(len2);
	    });
	    
	    $(".sele-all-2").click(function(){
	  	    grid = $("#grid-table-5");
	  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len = ids.length;
	  		for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        $("#grid-table-4").addRowData(colVal, getRow, "first");
		  		$("#grid-table-5").delRowData(colVal);
	  		}
	  		$(".len-2").html('0');
	  		
	  		grid2 = $("#grid-table-4");
	  		ids2 = grid2.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len2 = ids2.length;
	  	    
	  		$(".len-1").html(len2);
	    });
	    
	    $("#search-button").click(function(){
			var dish_content = "";
	  	    grid = $("#grid-table-5");
	  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len = ids.length;
	  	    var isdishHava = false;
	  		for(var i=0; i<len; i++){ 
	  			isdishHava = true;
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        var colDiscount = getRow.discount;
		        dish_content +=colVal+",";
	  		}
	  		if(isdishHava)
	  		{
	  			dish_content = dish_content.substring(0,dish_content.length-1);
	  		}
	    	$("#grid-table-4").jqGrid('setGridParam',{
			    datatype:'json', 
		        postData:{'id':treeNodeId,"ids":dish_content,"search":$("#search").val()}, //发送数据 
		        page:1 }).trigger("reloadGrid");
	    	
	    });
    }
    
    function zTreeInit()
    {
        $.fn.zTree.init($("#stree"), discounts_setting); 	
    }


	var discounts_setting = {
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
				beforeAsync: beforeAsyncChilds,
				onAsyncSuccess: onAsyncSuccessChilds,
				onAsyncError: onAsyncErrorChilds,
				onClick: onClick
		    }
	};
		
	function onClick(event, treeId, treeNode) {
		
		var dish_content = "";
  	    grid = $("#grid-table-5");
  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
  	    len = ids.length;
  	    var isdishHava = false;
  		for(var i=0; i<len; i++){ 
  			isdishHava = true;
	        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
	        var colVal = getRow.id;
	        var colDiscount = getRow.discount;
	        dish_content +=colVal+",";
  		}
  		if(isdishHava)
  		{
  			dish_content = dish_content.substring(0,dish_content.length-1);
  		}
  		
		var zTree = $.fn.zTree.getZTreeObj("stree");
			$("#grid-table-4").jqGrid('setGridParam',{
				    datatype:'json', 
			        postData:{'id':treeNode.id,"ids":dish_content,"search":$("#search").val()}, //发送数据 
			        page:1 }).trigger("reloadGrid");
			treeNodeId = treeNode.id;
	}
	
	function beforeAsyncChilds() {
		curAsyncCountChilds++;
	}
	
	function onAsyncSuccessChilds(event, treeId, treeNode, msg) {
		curAsyncCountChilds--;
	    expandAllChilds();
	}
	
	function onAsyncErrorChilds(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		curAsyncCountChilds--;
	} 
	  
	
	function expandAllChilds() {
		if (!check()) {
			return;
		}
		var zTree = $.fn.zTree.getZTreeObj("stree");
		if (asyncForAllChilds) {
			zTree.expandAll(true);
		} else {
			expandNodesChilds(zTree.getNodes());
			if (!goAsyncChilds) {
				curStatusChilds = "";
			}
		}
	}
	function expandNodesChilds(nodes) {
		if (!nodes) return;
		curStatus = "expand";
		var zTree = $.fn.zTree.getZTreeObj("stree");
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodesChilds(nodes[i].children);
			} else {
				goAsyncChilds = true;
			}
		}
	}
	
	var curStatusChilds = "init", curAsyncCountChilds = 0, asyncForAllChilds = false;
	goAsyncChilds = false;  
	function check() {  
	    if (curAsyncCountChilds > 0) {  
	        return false;  
	    }  
	    return true;  
	}
	
	
	return {
	        ztreeInit:function () {
	            zTreeInit();
	        },
	 		pagerInit:function (){
	 			pagerInit();
	 		}
	}
}();

function show_bar()
{
	if($(".left-content").css("display")=="none")
	{
		var imgPath = path+"/app/img/center.gif";
		$(".left-content").css("display","block");
		$(".right-content").css("margin-left","10px");
		$(".control").css("margin-left","180px");
		$(".jq-margin1").css("margin-left","190px");
		$(".sele").css("margin-left","190px");
		$(".control").css("background-image","url("+imgPath+")");
	}
	else
	{
		var imgPath = path+"/app/img/center0.gif";
		$(".left-content").css("display","none");
		$(".right-content").css("margin-left","10px");
		$(".control").css("margin-left","10px");
		$(".jq-margin1").css("margin-left","20px");
		$(".sele").css("margin-left","20px");
		$(".control").css("background-image","url("+imgPath+")");
	}
}