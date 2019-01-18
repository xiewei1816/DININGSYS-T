$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : path+"/DININGSYS/sysconfig/getPageList",
		colM : "operName,time,wind,content,openWater,settlementWater",
		queryForm : "queryForm",
		colNames : "操作员,时间,模块窗口,操作内容,营业流水,结算流水",
		colWid : {"id":40},
		loadComplete : function() {
			//加载完成设空
			$('#query_time_type').val('');
			$('#query_time').val('');
			$('#type').val('');
		}
	});

    $.fn.zTree.init($("#tree"), setting,{
   		id:'0',
   		pId:'-1',
   		isParent:true,
   		open:true,
   		name:'系统日志'
   		});
     expandAll("tree"); 
	    
});
	var setting = {
		    async : {
		    enable : true,//是否异步加载
	        url : path+'/DININGSYS/sysconfig/getTreeNodes',//加载数据的url
	        autoParam : [ "id","ctype","year","month","day"],//异步发送请求时,表示自动传指定属性的参数值
	        dataFilter : function(treeId, parentNode, childNodes) {//这里由于本人设置的节点属性跟zTree不一致所以进行了过滤     
	            for (var i = 0, l = childNodes.length; i < l; i++) {
	                if (childNodes[i].counter > 0 ) {
	                    //当主节点  下面还有父节点时自动将isParent=true
	                    //这样点击父节点zTree会自动再加载其子节点的数据
		                    childNodes[i].isParent = true;
		                }
		                else
		                {
		                	childNodes[i].isParent = false;
		                }
		            }
		            return childNodes;
		        }
		    },
		    callback : {
		    	onAsyncSuccess:onAsyncSuccess,
				beforeClick: function(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("tree");
					zTree.expandNode(treeNode);
					onClick(treeId,treeNode);
					return true;
				}
		    }
	};
		
	function beforeAsync() {  
	    curAsyncCount++;  
	}

	function onClick(treeId, treeNode) {
		if(treeNode.day != null) //跟节点才查询
		{
			var time = treeNode.year;
			if(treeNode.month < 10)
			{
				 time = time +'-0'+treeNode.month;
			}
			else
			{
				 time = time +'-'+treeNode.month;
			}
			if(treeNode.day < 10)
			{
				 time = time +'-0'+treeNode.day;
			}
			else
			{
				 time = time +'-'+treeNode.day;
			}
			$('#query_time_type').val('1');
			$('#query_time').val(time);
			$('#type').val(treeNode.ctype);
			$("#" + pageUtil.tabId).jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan")});
			$("#" + pageUtil.tabId).trigger("reloadGrid");
		}
		else if(treeNode.month != null && treeNode.day == null) //跟节点才查询
		{
			var time = treeNode.year;
			if(treeNode.month < 10)
			{
				 time = time +'-0'+treeNode.month;
			}
			else
			{
				 time = time +'-'+treeNode.month;
			}
			$('#query_time_type').val('2');
			$('#query_time').val(time);
			$('#type').val(treeNode.ctype);
			$("#" + pageUtil.tabId).jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan")});
			$("#" + pageUtil.tabId).trigger("reloadGrid");
		}
		else if(treeNode.year != null && treeNode.month == null) //跟节点才查询
		{
			var time = treeNode.year;
			$('#query_time_type').val('3');
			$('#query_time').val(time);
			$('#type').val(treeNode.ctype);
			$("#" + pageUtil.tabId).jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan")});
			$("#" + pageUtil.tabId).trigger("reloadGrid");
		}
		else if(treeNode.year == null ) //按类型查询
		{
			$('#type').val(treeNode.ctype);
			$("#" + pageUtil.tabId).jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan")});
			$("#" + pageUtil.tabId).trigger("reloadGrid");
		}
	}
	function onAsyncSuccess(event, treeId, treeNode, msg) {  
	    curAsyncCount--;  
	    if (curStatus == "expand") {  
	    expandNodes(treeNode.children);  
	} else if (curStatus == "async") {  
	                asyncNodes(treeNode.children);  
	            }  
	  
	            if (curAsyncCount <= 0) {  
	                curStatus = "";  
	            }  
	}  
	  
	var curStatus = "init", curAsyncCount = 0, goAsync = false, isFrist=true;  
	function expandAll() {  
	    if (!check()) {  
	        return;  
	    }  
		var zTree = $.fn.zTree.getZTreeObj("tree");  
		expandNodes(zTree.getNodes());  
		if (!goAsync) {  
		    curStatus = "";  
		}  
	}  
	function expandNodes(nodes) {  
	    if (!nodes) return;  
	    curStatus = "expand";
		if(isFrist)
		{
		isFrist = false;
	       var zTree = $.fn.zTree.getZTreeObj("tree");  
	       for (var i=0, l=nodes.length; i<l; i++) {  
	           zTree.expandNode(nodes[i], true, false, false);//展开节点就会调用后台查询子节点  
	           if (nodes[i].isParent && nodes[i].zAsync) {  
	               expandNodes(nodes[i].children);//递归  
	                   } else {  
	                       goAsync = true;  
	                   }  
	            } 
	      } 
	}  
	  
	function check() {  
	    if (curAsyncCount > 0) {  
	        return false;  
	    }  
	    return true;  
	}
	