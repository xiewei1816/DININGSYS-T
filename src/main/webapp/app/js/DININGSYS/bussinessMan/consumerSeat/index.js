
$(function () {
    var setting = {
		    async : {
		    enable : true,//是否异步加载
	        url : path+'/DININGSYS/consumerSeatManager/getTreeNodes',//加载数据的url
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
				onAsyncSuccess: initTree,
				onClick: onClick
		    }
	};
    $.fn.zTree.init($("#tree"), setting);
    $(".extra").outerHeight($(window).innerHeight());
    resize();
});
function initTree() {
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	expandNodes(treeObj.getNodes());
}

function onClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("tree");
        $.get(path+treeNode.murl+"?id="+treeNode.id,function (data) {
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
function initCheckVals()
{
	$(".number").keyup(function(e){//整数
		onlyNumberKeyUp($(this));
	});
	$(".decimal").keyup(function(e){ //小数
		onlyDecimalKeyUp($(this));
	});
	$(".discount").keyup(function(e){//整数
		onlyDiscountKeyUp($(this));
	});

}
	
/**
 * 限制只能输入整数
 */
function onlyNumberKeyUp(obj){
	if(! /^[0-9]*[1-9][0-9]*$/.test($(obj).val()))
	{
		$(obj).val($(obj).val().substring(0,$(obj).val().length-1));
	}
	
	if($(obj).attr("name") == "typeMinVal")
	{
		if($(obj).val() != "")
		{
			$(".minVal").text("分钟,按"+$(obj).val()+"分钟补齐");
		}
		else
		{
			$(".minVal").text("分钟,按 0分钟补齐");
		}
	}
}
/**
 * 
 * @param obj
 */
function onlyDecimalKeyUp(obj){
	if( !/^\d+\.?\d{0,2}$/.test($(obj).val()))
	{
		$(obj).val($(obj).val().substring(0,$(obj).val().length-1));
	}
	else
	{
		if($(obj).val().length>1)
		{
			var sub1 = $(obj).val().substring(0,2); //判断第一位是不是0
			if(sub1 == '00')
			{
				$(obj).val("0");
			}	
		}
	}
}

function onlyDiscountKeyUp(obj){
	var discount = $(obj).val();
	var r = /^([1]?\d{1,2})$/;	//0-100的正整数  	
	if(!r.test(discount)){
		$(obj).val($(obj).val().substring(0,$(obj).val().length-1));
	}
	else if(Number(discount) < 1 || Number(discount) >100)
	{
		$(obj).val($(obj).val().substring(0,$(obj).val().length-1));
	}
}
	