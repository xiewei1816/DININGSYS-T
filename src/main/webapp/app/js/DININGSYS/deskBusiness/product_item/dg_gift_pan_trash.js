var dg_gift_pan_trash = function () {
	
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
	        url:"DININGSYS/DgItemGiftPlan/getAllData?recycle=1",
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:320,
	        width:816,
	        colNames:["id","方案名称","品项编码","品项名称","开始日期","开始时间","结束日期","结束时间","结束时间","星期一","星期二","星期三","星期四","星期五","星期六","星期日","赠送次数限定"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'name',index:'name', width:'10%',align:'center'},
	            {name:'itemId',index:'itemId', width:'10%',align:'center'},
	            {name:'itemCode',index:'itemCode', width:'10%',align:'center'},
	            {name:'itemName',index:'itemName', width:'10%',align:'center'},
	            {name:'startDate',index:'startDate', width:'10%',align:'center'},
	            {name:'startTime',index:'startTime', width:'10%',align:'center'},
	            {name:'endDate',index:'endDate', width:'10%',align:'center'},
	            {name:'endTime',index:'endTime', width:'10%',align:'center'},
	            {name:'week1',index:'week1', width:'10%',align:'center',formatter:format},
	            {name:'week2',index:'week2', width:'10%',align:'center',formatter:format},
	            {name:'week3',index:'week3', width:'10%',align:'center',formatter:format},
	            {name:'week4',index:'week4', width:'10%',align:'center',formatter:format},
	            {name:'week5',index:'week5', width:'10%',align:'center',formatter:format},
	            {name:'week6',index:'week6', width:'10%',align:'center',formatter:format},
	            {name:'week7',index:'week7', width:'10%',align:'center',formatter:format},
	            {name:'giftFrequencyLimit',index:'giftFrequencyLimit', width:'10%',align:'center'}
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
	                url:'DININGSYS/DgItemGiftPlan/delete',
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
	                url:'DININGSYS/DgItemGiftPlan/restore',
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