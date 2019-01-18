$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	$("#grid-table").jqGrid({
        url:"DININGSYS/RecommedItem/getAllData",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        colNames:["id","品项代码","品项名称","套餐","标准价格","品项大类","品项小类","品项id"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'code',index:'code', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'tc',index:'tc', width:'20%',align:'center',formatter:format},
            {name:'price',index:'price', width:'20%',align:'center'},
            {name:'bigName',index:'bigName', width:'20%',align:'center'},
            {name:'smallName',index:'smallName', width:'20%',align:'center'},
            {name:'itemId',index:'itemId', width:'0%',align:'center',hidden:true}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:100,//每页显示记录数
        rowList:[100,200],//用于改变显示行数的下拉列表框的元素数组。
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        multiselect:true,
        pager:$('#grid-pager')
    });
    $("#grid-table").hideCol("id");
    
    $("#add").click(function(){
    	$.get("DININGSYS/RecommedItem/toAddItem",function(str){
            var addsIndex = layer.open({
                type: 1,
                title:'公共信息-推荐菜品',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
                      var rows = $("#grid-table-5").jqGrid('getRowData'),
                      chooseTrIds = [];
    	              if(rows.length > 0){
    	                    for (var i in rows) {
    	                        chooseTrIds.push(rows[i].id);
    	                  }
    	              }	                
                    
                      $.ajax({
                      type:'POST',
                      url:'DININGSYS/RecommedItem/getPanItemByAdd',
                      data:{chooseTrIds:chooseTrIds.toString()},
                      dataType:'JSON',
                      success:function (data) {
                          if(data.success == true)
                          {
                              $("#grid-table").jqGrid("clearGridData"); //先清理
    	                    	 for (var i in data.list) {
    			                        $("#grid-table").addRowData(data.list[i].id, data.list[i],"first");
    	                      }
                              layer.close(addsIndex);
                          }
                       }
                   })
                }
            });
        })
    });
    
    $("#save").click(function(){
    	
		var dish_content = "";
  	    grid = $("#grid-table");
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
  		
  		if(dish_content.length ==0)
  		{
  			 layer.alert('请先添加数据数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
             return;
  		}
  		
  		layer.confirm('确认保存当前选择的品项数据?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/RecommedItem/saveData',
                    data:{data:dish_content},
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
    
    $("#delb").click(function(){
		var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }

        var ids = [];
        for(var i = 0;i < selectRow.length;i++){
            var id = grid.jqGrid('getCell',selectRow[i],'id');
            ids.push(id);
        }
        layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){

        $.ajax({
                type:'POST',
                url:'DININGSYS/RecommedItem/delData',
                data:{ids:ids.toString()},
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
    
    $("#refresh").click(function(){
    	 $("#grid-table").trigger("reloadGrid");
    	
    });
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/RecommedItem/exportXls";
	});
    
});

function format(z,x,f){
	if(z == '1')
	{
		return '是';
	}
	else
	{
		return '否';
	}
}