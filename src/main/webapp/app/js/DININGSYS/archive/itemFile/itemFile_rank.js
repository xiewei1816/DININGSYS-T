$(function(){
	pageUtil.pageInit({
		initFormVals : function(){
		}
	});
	
	/**************************************** 排序 - 按品项 START****************************************/
	$("#rank-item-table").getJqGrid({
		url : "DININGSYS/archive/itemFile/getItemFileRankList",
		colM : "id,num,name,rank",
		queryForm : "queryForm",
		colNames : "ID,品项编号,品项名称,排序值",
		colWid : {id:"0"},
		rowNum : -1,
		rownumbers : true,
		formatter : {},
		loadComplete : function() {
			$("#rank-item-table").hideCol("id");
		}
	});
	
	$("#searchItemFileByPxxlId").click(function(){
		var ppxlId = $("#ppxlId").val();
		var url = 'DININGSYS/archive/itemFile/getItemFileRankList?ppxlId='+ppxlId;
        jQuery("#rank-item-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
	});
	
	$("#ppxlId").change(function(){
		var ppxlId = $("#ppxlId").val();
		var url = 'DININGSYS/archive/itemFile/getItemFileRankList?ppxlId='+ppxlId;
        jQuery("#rank-item-table").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
	});
	
	$("#rank-item-up").click(function(){
		var grid = $("#rank-item-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行向上移动！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //获取选择数据的id,rank
        var clickId = grid.jqGrid('getCell',selectRow[0],'id');
        var clickRank = grid.jqGrid('getCell',selectRow[0],'rank');	
        
        //获取选择数据上一行的id,rank
        var lastObject = $("#rank-item-table #" + clickId).prev()[0];
        if(typeof(lastObject) == 'undefined' || lastObject.id == ''){
        	layer.alert( '该项已在最上层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        //获取上一行的rowId
        var lastRowId = lastObject.id;
        var lastId = grid.getCell(lastRowId,"id");
        var lastRank = grid.getCell(lastRowId,"rank");
        $.ajax({
            type:'POST',
            url:"DININGSYS/archive/itemFile/doItemRank",
            data:{id1:clickId,rank1:clickRank,id2:lastId,rank2:lastRank,moveType:0},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#rank-item-table").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#rank-item-table").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});	
	
	$("#rank-item-down").click(function(){
		var grid = $("#rank-item-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行向下移动！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //获取选择数据的id,rank
        var clickId = grid.jqGrid('getCell',selectRow[0],'id');
        var clickRank = grid.jqGrid('getCell',selectRow[0],'rank');	
        
        //获取选择数据下一行的id,rank
        var nextObject = $("#rank-item-table #" + clickId).next()[0];
        if(typeof(nextObject) == 'undefined' || nextObject.id == ''){
        	layer.alert( '该项已在最下层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        //获取下一行的rowId
        var nextRowId = nextObject.id;
        var nextId = grid.getCell(nextRowId,"id");
        var nextRank = grid.getCell(nextRowId,"rank");
        $.ajax({
            type:'POST',
            url:"DININGSYS/archive/itemFile/doItemRank",
            data:{id1:clickId,rank1:clickRank,id2:nextId,rank2:nextRank,moveType:1},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#rank-item-table").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#rank-item-table").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});	
	
	$("#rank-item-topper").click(function(){
		var grid = $("#rank-item-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行置顶！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //获取选择数据的id
        var clickId = grid.jqGrid('getCell',selectRow[0],'id');
        var clickRank = grid.jqGrid('getCell',selectRow[0],'rank');
        
        //获取选择数据上一行的id
        var lastObject = $("#rank-item-table #" + clickId).prev()[0];
        if(typeof(lastObject) == 'undefined' || lastObject.id == ''){
        	layer.alert( '该项已在最上层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        $.ajax({
            type:'POST',
            url:"DININGSYS/archive/itemFile/doItemRank",
            data:{id1:clickId,rank1:clickRank,moveType:2},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#rank-item-table").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#rank-item-table").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});
	
	/**************************************** 排序 - 按品项 END****************************************/
	
	/**************************************** 排序 - 按品项小类 START****************************************/
	$("#rank-smallitem-table").getJqGrid({
		url : "DININGSYS/archive/itemFile/getItemFileSmallTypeRankList",
		colM : "id,num,name,rank",
		queryForm : "queryForm",
		colNames : "ID,品项小类编号,品项小类名称,排序值",
		colWid : {id:'0',num:($(window).width() -70) * 0.33,name:($(window).width() - 70) * 0.33,rank:($(window).width() - 70) * 0.33},
		rowNum : -1,
		rownumbers : true,
		formatter : {},
		loadComplete : function() {
			$("#rank-smallitem-table").hideCol("id");
		}
	});
	
	$("#rank-smallitem-up").click(function(){
		var grid = $("#rank-smallitem-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行向上移动！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //获取选择数据的id,rank
        var clickId = grid.jqGrid('getCell',selectRow[0],'id');
        var clickRank = grid.jqGrid('getCell',selectRow[0],'rank');	
        
        //获取选择数据上一行的id,rank
        var lastObject = $("#rank-smallitem-table #" + clickId).prev()[0];
        if(typeof(lastObject) == 'undefined' || lastObject.id == ''){
        	layer.alert( '该项已在最上层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        //获取上一行的rowId
        var lastRowId = lastObject.id;
        var lastId = grid.getCell(lastRowId,"id");
        var lastRank = grid.getCell(lastRowId,"rank");
        $.ajax({
            type:'POST',
            url:"DININGSYS/archive/itemFile/doItemSmallTypeRank",
            data:{id1:clickId,id2:lastId,moveType:0},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#rank-smallitem-table").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#rank-smallitem-table").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});	
	
	$("#rank-smallitem-down").click(function(){
		var grid = $("#rank-smallitem-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行向下移动！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //获取选择数据的id,rank
        var clickId = grid.jqGrid('getCell',selectRow[0],'id');
        var clickRank = grid.jqGrid('getCell',selectRow[0],'rank');	
        
        //获取选择数据下一行的id,rank
        var nextObject = $("#rank-smallitem-table #" + clickId).next()[0];
        if(typeof(nextObject) == 'undefined' || nextObject.id == ''){
        	layer.alert( '该项已在最下层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        //获取下一行的rowId
        var nextRowId = nextObject.id;
        var nextId = grid.getCell(nextRowId,"id");
        var nextRank = grid.getCell(nextRowId,"rank");
        $.ajax({
            type:'POST',
            url:"DININGSYS/archive/itemFile/doItemSmallTypeRank",
            data:{id1:clickId,id2:nextId,moveType:1},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#rank-smallitem-table").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#rank-smallitem-table").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});	
	
	$("#rank-smallitem-topper").click(function(){
		var grid = $("#rank-smallitem-table");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行置顶！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //获取选择数据的id
        var clickId = grid.jqGrid('getCell',selectRow[0],'id');
        
        //获取选择数据上一行的id
        var lastObject = $("#rank-smallitem-table #" + clickId).prev()[0];
        if(typeof(lastObject) == 'undefined' || lastObject.id == ''){
        	layer.alert( '该项已在最上层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        $.ajax({
            type:'POST',
            url:"DININGSYS/archive/itemFile/doItemSmallTypeRank",
            data:{id1:clickId,moveType:2},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#rank-smallitem-table").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#rank-smallitem-table").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});
	
	/**************************************** 排序 - 按品项小类 END****************************************/
	
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

