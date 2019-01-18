$(function () {
	console.log(123);
	var consumerId = 0;
	//树形结构设置
	var consumerSetting = {
		data: {
	        simpleData: {
	            enable: true,
	            idKey: "id",
	            pIdKey: "parentId",
	            rootPId: 0
	        },
	        key: {
	            name : "name" //设置显示字段
	        }
	    },
	    async : {
	    enable : true,//是否异步加载
	    url : 'DININGSYS/yxe/selectAllConsumption',//加载数据的url
	    autoParam : [ "id","parentId","name"],
	    dataFilter : function(treeId, parentNode, childNodes) {
	    	  for (var i = 0, l = childNodes.length; i < l; i++) {
	              if (childNodes[i].cParent == null ) {
	              		childNodes[i].open = true;
	                  }
	              }
	              return childNodes;
	          }
	    },
	    check: {
			enable: false
		},
		callback : {
	    	onClick: zTreeOnClick
		}
	};
	
	//树形结构设置
	var itemSetting = {
		data: {
	        simpleData: {
	            enable: true,
	            idKey: "id",
	            pIdKey: "parent_id",
	            rootPId: 0
	        },
	        key: {
	            name : "name" //设置显示字段
	        }
	    },
	    async : {
	    enable : true,//是否异步加载
	    url : 'DININGSYS/yxe/selectAllItem?consId=0',//加载数据的url
	    autoParam : [ "id","parent_id","name"],
	    dataFilter : function(treeId, parentNode, childNodes) {
	    	  for (var i = 0, l = childNodes.length; i < l; i++) {
	              if (childNodes[i].open == '1' ) {
	              		childNodes[i].open = true;
	                  } 
	              if (childNodes[i].parent_id != '0' ) {
	              		childNodes[i].open = false;
	                  }
	              if (childNodes[i].checked  == 'checked' ) {
	              		childNodes[i].checked = true;
	                  }
	              }
	              return childNodes;
	          }
	    },
	    check: {
			enable: true
		},
		callback: {
			onClick: function (e, treeId, treeNode, clickFlag) {
        		var treeObj = $.fn.zTree.getZTreeObj("itemTree");
        		treeObj.checkNode(treeNode, !treeNode.checked, true); 
        		console.log(123);
        		zItemTreeOnClick();
			},
			onCheck:function (e, treeId, treeNode) {
				zItemTreeOnClick()
			}
		} 
	};
	//树形结构加载
	$.fn.zTree.init($("#consumerTree"), consumerSetting);
	$.fn.zTree.init($("#itemTree"), itemSetting);
	
	/**
	 * 点击事件
	 * @param event
	 * @param treeId
	 * @param treeNode
	 */
	function zTreeOnClick(event, treeId, treeNode) {
	    /*alert(treeNode.id + ", " +treeNode.cParent + ", " + treeNode.cName);*/
	    var url;
	    consumerId = treeNode.id;
	    console.log(treeNode.id);
		var itemTree = $.fn.zTree.getZTreeObj("itemTree");
		itemTree.setting.async.url = "DININGSYS/yxe/selectAllItem?consId="+treeNode.id;
		itemTree.reAsyncChildNodes(null, "refresh");
	}
	
	function zItemTreeOnClick(){
		var treeObj = $.fn.zTree.getZTreeObj("itemTree");
		
		var ids = '';
		var nodes = treeObj.getCheckedNodes(true);
		for(var i in nodes){
			ids += nodes[i].id +',';
		}
		if(ids.length > 0){
			ids = ids.substring(0,ids.length-1);
		}
		 $.ajax({
             type:'POST',
             url:'DININGSYS/yxe/insertYxeConsumerItem.htm',
             data:{consumerId:consumerId,ids:ids},
             dataType:'JSON',
             success:function (data) {
             	if (data.success) {
             		console.log("保存成功");
       			}
             }
         })
	}
});