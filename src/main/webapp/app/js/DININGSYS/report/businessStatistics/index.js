$(function () {
	var treeObj;
	var ctx = path;
    var setting = {
		    async : {
		    enable : true,//是否异步加载
	        url : path+'/DININGSYS/report/businessStatistics/getTreeNodes',//加载数据的url
	        autoParam : [ "id"],//异步发送请求时,表示自动传指定属性的参数值
			type: "get",
	        dataFilter : function(treeId, parentNode, childNodes) {//这里由于本人设置的节点属性跟zTree不一致所以进行了过滤     
	            for (var i = 0, l = childNodes.length; i < l; i++) {
	                if (childNodes[i].childCount > 0 ) {
	                    //当主节点  下面还有父节点时自动将isParent=true
	                    //这样点击父节点zTree会自动再加载其子节点的数据
		                    childNodes[i].isParent = true;
		                } else {
		                	childNodes[i].isParent = false;
		                }
		            }
		            return childNodes;
		        }
		    },
		    callback : {
				onAsyncSuccess: initTree,
				onClick: onClick
		    }
	};
	function initTree() {
		treeObj = $.fn.zTree.getZTreeObj("tree");
		expandNodes(treeObj.getNodes());
	}
	
	function onClick(event, treeId, treeNode) {
        $.get(ctx+treeNode.murl,function (data) {
            $(".childIframe").html(data);
        });
		return true;
	}
		
	
	function expandNodes(nodes) {
		if (!nodes) return;
		var zTree = $.fn.zTree.getZTreeObj("tree");
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.expandNode(nodes[i], true, false, false);
			if (nodes[i].isParent && nodes[i].zAsync) {
				expandNodes(nodes[i].children);
			}
		}
	}
	
	function resize(){
	    $(window).resize(function() { 
	        $(".extra").outerHeight($(window).innerHeight());
	   }) 
	}
	
    $.fn.zTree.init($("#tree"), setting);
    $(".extra").outerHeight($(window).innerHeight());
    resize();
});
	