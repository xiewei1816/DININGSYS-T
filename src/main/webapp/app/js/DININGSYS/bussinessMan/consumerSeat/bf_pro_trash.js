var bf_pro_trash = function () {
	
	function pagerInit()
	{	
		$('#query-pan-trash :text').keypress(function(e){
            if(e.keyCode==13){
            	$("#query-pan-trash .btn").trigger("click");
            }
		});
		
		$('#query-pan-trash .btn').click(function(e){
			 e.preventDefault(); //阻止回车提交表单
             $("#grid-table-6").jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan-trash")});
 			 $("#grid-table-6").trigger("reloadGrid");
		});
		
		$("#grid-table-6").jqGrid({
	        url:"DININGSYS/consumerSeatManager/getPageBffPro?trash=1",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:320,
	        width:816,
	        colNames:["id","方案编号","方案名称","收费类型","总收费上限","最后修改日期"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'code',index:'code', width:'10%',align:'center'},
	            {name:'name',index:'name', width:'10%',align:'center',},
	            {name:'type',index:'type', width:'10%',align:'center',formatter:formatType},
	            {name:'amountUpLim',index:'amountUpLim', width:'10%',align:'center'},
	            {name:'time',index:'time', width:'10%',align:'center'},
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
	    });
	    $("#grid-table-6").hideCol("id");
		
	   
	    
	    $("#delete").click(function(){
			var grid = $("#grid-table-6");
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
	        layer.confirm('确认删除选中的数据?删除后将无法还原!', {icon: 3, title:'提示'}, function(index){

	        $.ajax({
	                type:'POST',
	                url:'DININGSYS/consumerSeatManager/delete',
	                data:{ids:ids.toString()},
	                dataType:'JSON',
	                success:function (data) {
	                    if(data.success){
	                        $("#grid-table-6").trigger("reloadGrid");
	                    }
	                }
	            });

	            layer.close(index);
	        });
		});
	    
	    
	    $("#reply").click(function(){
			var grid = $("#grid-table-6");
	        var selectRow = grid.getGridParam("selarrrow");

	        if(selectRow.length < 1){
	            layer.alert('请选择需要还原的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	            return;
	        }

	        var ids = [];
	        for(var i = 0;i < selectRow.length;i++){
	            var id = grid.jqGrid('getCell',selectRow[i],'id');
	            ids.push(id);
	        }
	        layer.confirm('确认还原选中的数据?', {icon: 3, title:'提示'}, function(index){

	        $.ajax({
	                type:'POST',
	                url:'DININGSYS/consumerSeatManager/restore',
	                data:{ids:ids.toString()},
	                dataType:'JSON',
	                success:function (data) {
	                    if(data.success){
	                        $("#grid-table-6").trigger("reloadGrid");
	                        $("#grid-table-5").trigger("reloadGrid");
	                    }
	                }
	            });

	            layer.close(index);
	        });
		});
	    
	    $("#trash-refresh").click(function(){
	    	 $("#grid-table-6").trigger("reloadGrid");
	    });
	}
	
    return {
 		pagerInit:function (){
 			pagerInit();
 		}
}
}();

function formatType(z,x,f){
	if(z == '1')
	{
		return '时段收费(按分钟)';
	}
	else if(z == '2')
	{
		return '时阶收费';
	}
	else if(z == '3')
	{
		return '时长收费';
	}
	else if(z == '4')
	{
		return '时段收费按小时';
	}
}