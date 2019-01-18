$(function(){
	pageUtil.pageInit({
		initFormVals : function(){
		}
	});

	$("#grid-table-rank").getJqGrid({
		url : "DININGSYS/settlementWay/getSettleMentWayRankList",
		colM : "id,number,name,rank",
		queryForm : "queryForm",
		colNames : "ID,结算方式编号,结算方式名称,排序值",
		colWid : {id:"0"},
		rowNum : -1,
		rownumbers : true,
		formatter : {},
		loadComplete : function() {
			$("#grid-table-rank").hideCol("id");
		}
	});
	
	$("#up").click(function(){
		var grid = $("#grid-table-rank");
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
        var lastObject = $("#grid-table-rank #" + clickId).prev()[0];
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
            url:"DININGSYS/settlementWay/doRank",
            data:{id1:clickId,id2:lastId,moveType:0},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#grid-table-rank").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#grid-table-rank").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});	
	
	$("#down").click(function(){
		var grid = $("#grid-table-rank");
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
        var nextObject = $("#grid-table-rank #" + clickId).next()[0];
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
            url:"DININGSYS/settlementWay/doRank",
            data:{id1:clickId,id2:nextId,moveType:1},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#grid-table-rank").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#grid-table-rank").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});	
	
	$("#topper").click(function(){
		var grid = $("#grid-table-rank");
        var rowId = grid.getGridParam("selrow");
        var selectRow = grid.getGridParam("selarrrow");
        if(selectRow.length != 1){
            layer.alert('请选择一条数据进行置顶！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        //获取选择数据的id
        var clickId = grid.jqGrid('getCell',selectRow[0],'id');
        
        //获取选择数据上一行的id
        var lastObject = $("#grid-table-rank #" + clickId).prev()[0];
        if(typeof(lastObject) == 'undefined' || lastObject.id == ''){
        	layer.alert( '该项已在最上层显示！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
        	return;
        }
        $.ajax({
            type:'POST',
            url:"DININGSYS/settlementWay/doRank",
            data:{id1:clickId,moveType:2},
            dataType:'JSON',
            async:true,
            success:function (data) {
                if(data.success){
                    $("#grid-table-rank").trigger("reloadGrid");
                }else{
					layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
				}
            }
        }).done(function () {
        	setTimeout(function () {
        		$("#grid-table-rank").jqGrid('setSelection',clickId);
        	  }, 500);
        })
	});
	
});

