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
        for (var i = 0, l = childNodes.length; i < l; i++) {}
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
    $("#deptId").val(id);
    $("#dept").val(depName);
    $(".menuTree").hide();// 隐藏层
}
/**
 * 显示，隐藏部门树形选择DIV
 */
function showOrHideDep(){
	var temp = $(".menuTree").is(":hidden");//是否隐藏
	if(temp){
//		//设置显示位置
//		var X = $('#dept').offset().top; 
//		var Y = $('#dept').offset().left; 
//		alert(X + '+' + Y)
//		$(".menuTree").css("marginTop",X - 62);
//		$(".menuTree").css("marginLeft",Y - 50);
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