$(function(){
	//树形结构加载
	$.fn.zTree.init($("#myTree"), setting);

});

//树形结构设置
var setting = {
	data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "depDepartment",
            rootPId: 0
        },
        key: {
            name : "depName" //设置显示字段
        }
    },
    async : {
    enable : true,//是否异步加载
    url : 'DININGSYS/archive/dep/selectAllDep?isDel=0',//加载数据的url
    autoParam : [ "id","depDepartment","depName"],
    dataFilter : function(treeId, parentNode, childNodes) {
        for (var i = 0, l = childNodes.length; i < l; i++) {
            if (childNodes[i].counter > 0 ) {
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
    	onClick: zTreeOnClick
    }
};

/**
 * 点击事件
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    /*alert(treeNode.id + ", " +treeNode.depDepartment + ", " + treeNode.depName);*/
    var id = treeNode.id;
    var depName = treeNode.depName;
    $("#empDepartment").val(id);
    $("#empDepartment").find("option").val(id).text(depName).prop("selected",true);
    $(".menuTree").hide();// 隐藏层
}
/**
 * 显示，隐藏部门树形选择DIV
 */
function showOrHideDep(){
	var temp = $(".menuTree").is(":hidden");//是否隐藏
	if(temp){
		//设置显示位置
		var X = $('#empDepartment').offset().top; 
		var Y = $('#empDepartment').offset().left; 
		$(".menuTree").css("marginTop",X - 62);
		$(".menuTree").css("marginLeft",Y - 50);
		
		$(".menuTree").show();
	}else{
		$(".menuTree").hide();
	}
}

/**
 * 隐藏部门树形选择DIV
 */
function hideDep(){
	$(".menuTree").hide();
}