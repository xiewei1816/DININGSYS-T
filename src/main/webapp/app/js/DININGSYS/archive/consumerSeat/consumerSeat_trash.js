var consumerSeat_trash = function () {
	
	function pagerInit()
	{
		$('#query-pan-trash :text').keypress(function(e){
            if(e.keyCode==13){
            	$("#query-pan-trash .btn").trigger("click");
            }
		});
		
		$('#query-pan-trash .btn').click(function(e){
			 e.preventDefault(); //阻止回车提交表单
             $("#grid-table-2").jqGrid('setGridParam',{page:1,postData:changeJOSNr("query-pan-trash")});
 			 $("#grid-table-2").trigger("reloadGrid");
		});
		
		$("#grid-table-2").jqGrid({
	        url:"DININGSYS/consumerSeat/getPageList?delFlag=1",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:320,
	        width:816,
	        colNames:["id","编号","客位名称","客位类型","容纳人数","所属消费区域","说明"],
	        colModel:[
	            {name:'id', align:'center' ,hidden:true},
	            {name:'number',align:'center'},
	            {name:'name',align:'center'},
	            {name:'seatType',align:'center'},
	            {name:'allowNumber',align:'center'},
	            {name:'area.name',align:'center'},
	            {name:'explains',align:'center'},
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
	        pager:$('#grid-pager-2')
	    });
	    $("#grid-table-2").hideCol("id");
		
	   
	    
	    $("#delete").click(function(){
			var grid = $("#grid-table-2");
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
	                url:'DININGSYS/consumerSeat/delete',
	                data:{ids:ids.toString()},
	                dataType:'JSON',
	                success:function (data) {
	                    if(data.success){
	                        $("#grid-table-2").trigger("reloadGrid");
	                    }
	                }
	            });

	            layer.close(index);
	        });
		});
	    
	    
	    $("#reply").click(function(){
			var grid = $("#grid-table-2");
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
	                url:'DININGSYS/consumerSeat/restore',
	                data:{ids:ids.toString()},
	                dataType:'JSON',
	                success:function (data) {
	                    if(data.success){
	                        $("#grid-table-2").trigger("reloadGrid");
	                        $("#grid-table").trigger("reloadGrid");
	                    }
	                }
	            });

	            layer.close(index);
	        });
		});
	    
	    $("#trash-refresh").click(function(){
	    	 $("#grid-table-2").trigger("reloadGrid");
	    });
	}
	
    return {
 		pagerInit:function (){
 			pagerInit();
 		}
}
}();

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