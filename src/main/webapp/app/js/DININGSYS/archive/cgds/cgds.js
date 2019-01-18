$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	/**************************************** 不参与消费排行的品项 - 按品项 ****************************************/
	$("#rank-item-table").getJqGrid({
		url : "DININGSYS/businessMan/itemShowRank/selectDgItemFileList?pxlx=0&isRank=1",
		colM : "id,num,name",
		queryForm : "queryForm",
		colNames : "ID,品项编号,品项名称",
		colWid : {id:"0"},
		rowNum : -1,
		rownumbers : true,
		formatter : {},
		loadComplete : function() {
			$("#rank-item-table").hideCol("id");
		}
	});
	
	$("#rank-item-add").click(function(){
		layer.open({
			  type: 2,
			  title: '品项选择窗口',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['1000px', '520px'],
			  content: 'DININGSYS/businessMan/itemShowRank/toAddCgdsRankItem',
			  end:function(){
				  $("#rank-item-table").trigger("reloadGrid");
			  }
		});
	});
	
	$("#rank-item-reduce").click(function(){
		var grid = $("#rank-item-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length < 1){
            layer.alert('请选择需要移除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var ids = [];
        for(var i=0;i<selectRow.length;i++){
            var tid = grid.jqGrid('getCell',selectRow[i],'id');
            ids.push(tid);
        }
        layer.confirm('确认移除数据吗？', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type:'POST',
                url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
                data:{isRank:0,pxlx:0,ids:ids.toString()},
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.success == 'OK'){
                    	$("#rank-item-table").trigger("reloadGrid");
                    }
                }
            });
           layer.close(index);
        });
	});
	
	$("#rank-item-remove").click(function(){
        var ids = [];
        var rowData = $("#rank-item-table").jqGrid("getRowData");
        jQuery(rowData).each(function(){
        	jQuery('#rank-item-table').jqGrid('setSelection',this.id); //全选
        	ids.push(this.id);
        });
        layer.confirm('确认全部移除数据吗？', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type:'POST',
                url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
                data:{isRank:0,pxlx:0,ids:ids.toString()},
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.success == 'OK'){
                    	$("#rank-item-table").trigger("reloadGrid");
                    }
                }
            });
           layer.close(index);
        });
	});	
	
	/**************************************** 不参与消费排行的品项 - 按品项小类 ****************************************/
	$("#rank-smallitem-table").getJqGrid({
		url : "DININGSYS/businessMan/itemShowRank/selectDgItemFileSmallList?pxlx=1&isRank=1",
		colM : "id,num,name",
		queryForm : "queryForm",
		colNames : "id,品项小类编号,品项小类名称",
		colWid : {id:'0',num:($(window).width() -70) * 0.5,name:($(window).width() - 70) * 0.5},
		rowNum : -1,
		rownumbers : true,
		formatter : {},
		loadComplete : function() {
			$("#rank-smallitem-table").hideCol("id");
		}
	});
	
	$("#rank-smallitem-add").click(function(){
		layer.open({
			  type: 2,
			  title: '品项小类选择窗口',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['1000px', '520px'],
			  content: 'DININGSYS/businessMan/itemShowRank/toAddCgdRankSmallItem',
			  end:function(){
				  $("#rank-smallitem-table").trigger("reloadGrid");
			  }
		});
	});
	
	$("#rank-smallitem-reduce").click(function(){
		var grid = $("#rank-smallitem-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length < 1){
            layer.alert('请选择需要移除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var ids = [];
        for(var i=0;i<selectRow.length;i++){
            var tid = grid.jqGrid('getCell',selectRow[i],'id');
            ids.push(tid);
        }
        layer.confirm('确认移除数据吗？', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type:'POST',
                url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
                data:{isRank:0,pxlx:1,ids:ids.toString()},
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.success == 'OK'){
                    	$("#rank-smallitem-table").trigger("reloadGrid");
                    }
                }
            });
           layer.close(index);
        });
	});
	
	$("#rank-smallitem-remove").click(function(){
		var ids = [];
        var rowData = $("#rank-smallitem-table").jqGrid("getRowData");
        jQuery(rowData).each(function(){
        	jQuery('#rank-smallitem-table').jqGrid('setSelection',this.id); //全选
        	ids.push(this.id);
        });
        layer.confirm('确认全部移除数据吗？', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type:'POST',
                url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
                data:{isRank:0,pxlx:1,ids:ids.toString()},
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.success == 'OK'){
                    	$("#rank-smallitem-table").trigger("reloadGrid");
                    }
                }
            });
           layer.close(index);
        });
	});	
	
	/**************************************** 不显示消费品项设置 - 按品项 ****************************************/
	$("#show-item-table").getJqGrid({
		url : "DININGSYS/businessMan/itemShowRank/selectDgItemFileList?pxlx=0&isShow=1",
		colM : "id,num,name",
		queryForm : "queryForm",
		colNames : "id,品项编号,品项名称",
		colWid : {id:'0',num:($(window).width() -70) * 0.5,name:($(window).width() - 70) * 0.5},
		rowNum : -1,
		rownumbers : true,
		formatter : {},
		loadComplete : function() {
			$("#show-item-table").hideCol("id");
		}
	});
	
	$("#show-item-add").click(function(){
		layer.open({
			  type: 2,
			  title: '品项选择窗口',
			  shadeClose: true,
			  shade: 0.3,
			  area: ['1000px', '520px'],
			  content: 'DININGSYS/businessMan/itemShowRank/toAddCgdShowItem',
			  end:function(){
				  $("#show-item-table").trigger("reloadGrid");
			  }
		});
	});
	
	$("#show-item-reduce").click(function(){
		var grid = $("#show-item-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length < 1){
            layer.alert('请选择需要移除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var ids = [];
        for(var i=0;i<selectRow.length;i++){
            var tid = grid.jqGrid('getCell',selectRow[i],'id');
            ids.push(tid);
        }
        layer.confirm('确认移除数据吗？', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type:'POST',
                url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
                data:{isShow:0,pxlx:0,ids:ids.toString()},
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.success == 'OK'){
                    	$("#show-item-table").trigger("reloadGrid");
                    }
                }
            });
           layer.close(index);
        });
	});
	
	$("#show-item-remove").click(function(){
		var ids = [];
        var rowData = $("#show-item-table").jqGrid("getRowData");
        jQuery(rowData).each(function(){
        	jQuery('#show-item-table').jqGrid('setSelection',this.id); //全选
        	ids.push(this.id);
        });
        layer.confirm('确认全部移除数据吗？', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type:'POST',
                url:"DININGSYS/businessMan/itemShowRank/saveDgItemShowRank",
                data:{isShow:0,pxlx:0,ids:ids.toString()},
                dataType:'JSON',
                async:false,
                success:function (data) {
                    if(data.success == 'OK'){
                    	$("#show-item-table").trigger("reloadGrid");
                    }
                }
            });
           layer.close(index);
        });
	});	
	
	$("#show-item-up").click(function(){
		var grid = $("#show-item-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行向上移动！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var id = grid.jqGrid('getCell',selectRow[0],'id');
       
        $.ajax({
            type:'POST',
            url:"DININGSYS/businessMan/itemShowRank/dgItemShowSetRank",
            data:{isShow:1,pxlx:0,id:id.toString(),moveType:1}, //moveType:1 = 上移； moveType:0 = 下移 
            dataType:'JSON',
            async:false,
            success:function (data) {
                if(data.success == 'OK'){
                    $("#show-item-table").trigger("reloadGrid");
                } else if(data.no == 'NO'){
					layer.alert( '该项已在最上层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				}else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        })
	});	
	
	$("#show-item-down").click(function(){
		var grid = $("#show-item-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行向下移动！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        var id = grid.jqGrid('getCell',selectRow[0],'id');
       
        $.ajax({
            type:'POST',
            url:"DININGSYS/businessMan/itemShowRank/dgItemShowSetRank",
            data:{isShow:1,pxlx:0,id:id.toString(),moveType:0}, //moveType:1 = 上移； moveType:0 = 下移 
            dataType:'JSON',
            async:false,
            success:function (data) {
                if(data.success == 'OK'){
                    $("#show-item-table").trigger("reloadGrid");
                } else if(data.no == 'NO'){
					layer.alert( '该项已在最下层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
				}else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        })
	});	
	
	/**
	 * 保存
	 */
	$("#save-btn").click(function(){
		var id = $("#id").val();
	   	var refreshHz = $("#refreshHz").val();
		var startDate = $("#startDate").val();
	   	var reg = /^[0-9]*$/; //正整数验证规则
		if(!reg.test(refreshHz)){
			layer.tips('刷新频率(秒)格式不正确！', '#refreshHz');
			return;
		}
	   	$.ajax({
	        type:'POST',
	        url:"DININGSYS/businessMan/itemTimeSet/saveDgItemTimeSet",
	        data:{id:id,refreshHz:refreshHz,startDate:startDate},
	        dataType:'JSON',
	        async:false,
	        success:function (data) {
	            if(data.success == 'OK'){
	            	layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
				} else {
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
	        }
	    })
	});

});

/**
 * 单选切换按钮点击事件函数
 * @param value
 */
function doSelected(value){
	switch (value){
	case '0':
		$("#tab1").show();
		$("#tab2").hide();
	  break;
	case '1':
		$("#tab2").show();
		$("#tab1").hide();
	  break;
	}
}
