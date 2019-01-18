var item_file_zccf = function () {
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
		var zccf_content = "";
  	    trs = $("#zccfTbody tr").each(function()
  	    {
  	    	var td = $(this).find("td:eq(1)");
  	    	zccf_content += td.text().trim()+",";	
  	    });
  	    if(zccf_content.length >0)
  	    {
  	  	    zccf_content = zccf_content.substring(0,zccf_content.length-1);
  	    }
		$("#grid-table-nothava").jqGrid({
	        url:"DININGSYS/archive/itemFile/getZccfItem",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:150,//高度，表格高度。可为数值、百分比或'auto'
	        width:630,//这个宽度不能为百分比
	        rowNum:-1,
	        colNames:["id","编号","名称","单位","类型"],
	        colModel:[
	            {name:'id',index:'id', width:'10%', align:'center' },
	            {name:'itemNo',index:'itemNo', width:'20%',align:'center'},
	            {name:'itemName',index:'itemName', width:'20%',align:'center'},
	            {name:'unit',index:'unit', width:'20%',align:'center'},
	            {name:'itemTypeName',index:'itemTypeName', width:'20%',align:'center'}
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为blackId
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        postData:{"ids":zccf_content}, //发送数据 
	        loadComplete : function() {
		  		grid = $("#grid-table-nothava");
		  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
		  	    len = ids.length;
		  		$(".len-1").html(len);
			},
			ondblClickRow: function(id){ 
				 var grid = $("#grid-table-nothava");
		  		 getRow = grid.getRowData(id);//获取当前的数据行  
				//双击行
		  		$("#grid-table-hava").addRowData(id, getRow, "first");
		  		$("#grid-table-nothava").delRowData(id);
		  		
		  		var len = $(".len-1").html();
		  		$(".len-1").html((Number(len)-1));
		  		var len2 = $(".len-2").html();
		  		$(".len-2").html((Number(len2)+1));
			} 
	    });
  		
		$("#grid-table-hava").jqGrid({
	        url:"DININGSYS/archive/itemFile/getHaveDish",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:150,//高度，表格高度。可为数值、百分比或'auto'
	        width:630,//这个宽度不能为百分比
	        rowNum:-1,
	        colNames:["id","编号","名称","单位","类型"],
	        colModel:[
	            {name:'id',index:'id', width:'10%', align:'center' },
	            {name:'itemNo',index:'itemNo', width:'20%',align:'center'},
	            {name:'itemName',index:'itemName', width:'20%',align:'center'},
	            {name:'unit',index:'unit', width:'20%',align:'center'},
	            {name:'itemTypeName',index:'itemTypeName', width:'20%',align:'center'}
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为blackId
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        postData:{"ids":zccf_content}, //发送数据 
	        loadComplete : function() {
		  		grid = $("#grid-table-hava");
		  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
		  	    len = ids.length;
		  		$(".len-2").html(len);
			},
			ondblClickRow: function(id){ 
				 var grid = $("#grid-table-hava");
		  		 getRow = grid.getRowData(id);//获取当前的数据行  
				//双击行
		  		$("#grid-table-nothava").addRowData(id, getRow, "first");
		  		$("#grid-table-hava").delRowData(id);
		  		
		  		var len = $(".len-2").html();
		  		$(".len-2").html((Number(len)-1));
		  		var len2 = $(".len-1").html();
		  		$(".len-1").html((Number(len2)+1));
			} 
	    });
		
	    $("#grid-table-nothava").hideCol("id");
	    $("#grid-table-hava").hideCol("id");
	    
	    $(".sele-all-1").click(function(){
	  	    grid = $("#grid-table-nothava");
	  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len = ids.length;
	  		for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        $("#grid-table-hava").addRowData(colVal, getRow, "first");
		  		$("#grid-table-nothava").delRowData(colVal);
	  		}
	  		
	  		$(".len-1").html('0');
	  		grid2 = $("#grid-table-hava");
	  		ids2 = grid2.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len2 = ids2.length;
	  	    
	  		$(".len-2").html(len2);
	    });
	    
	    $(".sele-all-2").click(function(){
	  	    grid = $("#grid-table-hava");
	  		ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len = ids.length;
	  		for(var i=0; i<len; i++){ 
		        var getRow = grid.getRowData(ids[i]);//获取当前的数据行  
		        var colVal = getRow.id;
		        $("#grid-table-nothava").addRowData(colVal, getRow, "first");
		  		$("#grid-table-hava").delRowData(colVal);
	  		}
	  		$(".len-2").html('0');
	  		
	  		grid2 = $("#grid-table-nothava");
	  		ids2 = grid2.getDataIDs();//返回数据表的ID数组["66","39"..]  
	  	    len2 = ids2.length;
	  	    
	  		$(".len-1").html(len2);
	    });
	    
	    $("#search-button").click(function(){
			var dish_content = "";
	  	    grid = $("#grid-table-hava");
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
	    	$("#grid-table-nothava").jqGrid('setGridParam',{
			    datatype:'json', 
		        postData:{"ids":dish_content,"search":$("#search").val()}, //发送数据 
		        page:1 }).trigger("reloadGrid");
	    	$("#search").val(""); //清空
	    	
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
	        url : 'DININGSYS/archive/itemFile/getTreeNodes',//加载数据的url  /DININGSYS/ProDiscount/getTreeNodes
	        autoParam : [ "id"],//异步发送请求时,表示自动传指定属性的参数值
			type: "get",
	        dataFilter : function(treeId, parentNode, childNodes) {//这里由于本人设置的节点属性跟zTree不一致所以进行了过滤     
	            for (var i = 0, l = childNodes.length; i < l; i++) {
	            		if(childNodes[i].id == '-1')
	            		{
	 		               childNodes[i].isParent = true;
	            		}
	            		else
	            		{
	            			 childNodes[i].isParent = false;
	            		}
		                childNodes[i].name = childNodes[i].itemTypeName;
		            }
		            return childNodes;
		        }
		    },
		    callback : {
				onAsyncSuccess: initTree,
				onClick: onClick
		    }
	};
		
	function onClick(event, treeId, treeNode) {
		
		var dish_content = "";
  	    grid = $("#grid-table-hava");
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
			$("#grid-table-nothava").jqGrid('setGridParam',{
				    datatype:'json', 
			        postData:{'treeId':treeNode.id,"ids":dish_content,"search":$("#search").val()}, //发送数据 
			        page:1 }).trigger("reloadGrid");
	}

	function initTree() {
		var treeObj = $.fn.zTree.getZTreeObj("stree");
		expandNodes(treeObj.getNodes());
	}

	
	function expandNodes(nodes) {
		if (!nodes) return;
		var zTree = $.fn.zTree.getZTreeObj("stree");
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes(nodes[i].children);
			}
		}
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