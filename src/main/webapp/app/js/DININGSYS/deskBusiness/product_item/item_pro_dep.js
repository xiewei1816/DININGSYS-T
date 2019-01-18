var deps ;
$(function () {
	
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	 });
	 var grid = $("#grid-table");
     var sele_area = $("#sele-area");
     var sele_dep = $("#sele-dep");
     var isALL = $("#isALL");
     var placeIdArray = []; //初始化区域id数组，方便后面调用
     var colModelData = [
                         {name:'id',index:'id', width:'0%', align:'center' },
                         {name:'itemId',index:'itemId', width:'0%',align:'center',hidden:true},
                         {name:'itemCode',index:'itemCode', width:'15%',align:'center'},
                         {name:'name',index:'name', width:'15%',align:'center'},
                     ];
    var colNames = ["id","品项id","品项编号","品项名称"]; 
    $.ajax({
        type:'POST',
        url:path+'/DININGSYS/DgItemProDepartment/getTableItem',
        dataType:'JSON',
        success:function (data) {
            if(data.success){
            	 deps = data.deps;
            	 for (var i in data.items) { 
            		 var colData = {
                             name : 'd'+data.items[i].id,
                             index : 'd'+data.items[i].id,
                             width:'15%',
                             align : "center",
                             formatter:format1
                     };
            		 placeIdArray.push("d"+data.items[i].id);
            		 colNames.push(data.items[i].name); //赋值名称title
                     colModelData.push(colData);
            	 }
            	 
                 //动态增加列
                 $("#grid-table").jqGrid({
                     url:"DININGSYS/DgItemProDepartment/getAllData",
                     datatype:"json", //数据来源，本地数据
                     mtype:"POST",//提交方式
                     rowNum:-1,
                     height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
                     autowidth:true,//自动宽
                     colNames:colNames,
                     colModel:colModelData,
                     rownumbers:true,//添加左侧行号
                     viewrecords: true,//是否在浏览导航栏显示记录总数
                     jsonReader:{
                         id: "id",//设置返回参数中，表格ID的名字为id
                         repeatitems : false
                     },
                     multiselect:true
                 });
                 $("#grid-table").hideCol("id");
              }
           }
    	});

    
   $("#refresh").click(function(){
    	 $("#grid-table").trigger("reloadGrid");
    	
    });
    
    $("#save").click(function(){
    	
        layer.confirm('确认保存品项出品部门?', {icon: 3, title:'提示'}, function(index){
        	var send='[';
        	var hava_rows = $("#grid-table").jqGrid('getRowData');
        	
        	if(hava_rows.length > 0){
                for (var i in hava_rows) {
                	var id = hava_rows[i].id; //品项id
                	var itemId = hava_rows[i].itemId; //品项id
                	var itemCode = hava_rows[i].itemCode;//品项编码
                	send +="{\"id\":\""+id+"\",\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",";
                	for(var j in placeIdArray)
         		    {
   		    		    var value = $("#sele-"+hava_rows[i].id+"-"+placeIdArray[j]).val();	
   		    		    send +="\""+placeIdArray[j]+"\":\""+value+"\",";
         		    }
                	send = send.substring(0,send.length-1);	
                    send += "},";
                }
            }	 
        	if(hava_rows.length >0)
        	{
            	send = send.substring(0,send.length-1);	
        	}
        	send += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/DgItemProDepartment/saveData',
                    data:{data:send},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                        	 $("#grid-table").trigger("reloadGrid");
                        }
                    }
                });
                layer.close(index);
            });
	});
    
    $("#set-s").click(function(){
  	  var rows = grid.jqGrid('getRowData');
        if(rows.length > 0){
              for (var i in rows) {
              	    var getRow = rows[i];//获取当前的数据行  
     		        var id = getRow.id;
 		        	//grid.jqGrid('setCell',id, 'd'+sele.val(), ''+nowPrice);
     		       $(".d"+sele_area.val()).find("option[value='"+sele_dep.val()+"']").attr("selected",true);
              }
         }
	});
    
	
    $.fn.zTree.init($("#dtree"), discount_setting); 
    $("#disTree").outerHeight($(window).innerHeight()-160);
    resize();
});

function format1(c,o,r){
	var m = c.split(":");
	var ret="<select id='sele-"+r.id+"-"+m[0]+"' class='"+m[0]+"'>";
	if(m[1] == '0')
	{
		ret +="<option value='0' selected></option>";
	}
	else
	{
		ret +="<option value='0'></option>";
	}
	for(var i in deps)
	{
		if(m[1] == deps[i].id)
		{
			ret +="<option value='"+deps[i].id+"' selected>"+deps[i].depName+"</option>";	
		}
		else
		{
			ret +="<option value='"+deps[i].id+"'>"+deps[i].depName+"</option>";
		}
	}
    ret +="</select>";
    return ret;
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
			onClick: onClick
	    }
};
	

function onClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("dtree");
		$("#grid-table").jqGrid('setGridParam',{
			    datatype:'json', 
		        postData:{'id':treeNode.id} //发送数据  
			}).trigger("reloadGrid");
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

function resize(){
    $(window).resize(function() { 
    	 $("#disTree").outerHeight($(window).innerHeight()-160);
   }) 
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