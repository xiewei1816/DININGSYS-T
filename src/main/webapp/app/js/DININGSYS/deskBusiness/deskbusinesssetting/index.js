$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	

    $.fn.zTree.init($("#tree"), setting); 
});
	var setting = {
		    async : {
		    enable : true,//是否异步加载
	        url : path+'/DININGSYS/deskbusinesssetting/getTreeNodes',//加载数据的url
	        autoParam : [ "id","childCount"],//异步发送请求时,表示自动传指定属性的参数值
			type: "get",
	        dataFilter : function(treeId, parentNode, childNodes) {//这里由于本人设置的节点属性跟zTree不一致所以进行了过滤     
	            for (var i = 0, l = childNodes.length; i < l; i++) {
	                if (childNodes[i].childCount > 0 ) {
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
				beforeAsync: beforeAsync,
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError,
				onClick: onClick
		    }
	};
		

	function onClick(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		if (treeNode.isParent) {
			zTree.expandNode(treeNode);
			return false;
		} else {
//			 $(".childIframe").load(path+treeNode.murl);
	        $.get(path+treeNode.murl,function (data) {
	            $(".childIframe").html(data);
	        });
//			$(".childIframe").attr("src",path+treeNode.murl);
			return true;
		}
	}
	  
	function beforeAsync() {
		curAsyncCount++;
	}
	
	function onAsyncSuccess(event, treeId, treeNode, msg) {
		curAsyncCount--;
	    expandAll();
	}

	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
		curAsyncCount--;
	} 
	  
	
	function expandAll() {
		if (!check()) {
			return;
		}
		var zTree = $.fn.zTree.getZTreeObj("tree");
		if (asyncForAll) {
			zTree.expandAll(true);
		} else {
			expandNodes(zTree.getNodes());
			if (!goAsync) {
				curStatus = "";
			}
		}
	}
	function expandNodes(nodes) {
		if (!nodes) return;
		curStatus = "expand";
		var zTree = $.fn.zTree.getZTreeObj("tree");
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes(nodes[i].children);
			} else {
				goAsync = true;
			}
		}
	}
	
	var curStatus = "init", curAsyncCount = 0, asyncForAll = false,isFrist=true;
	goAsync = false;  
	function check() {  
	    if (curAsyncCount > 0) {  
	        return false;  
	    }  
	    return true;  
	}
	