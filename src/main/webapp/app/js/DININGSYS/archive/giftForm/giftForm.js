/**
 * Created by zhshuo on 2016-10-09.
 */
var arGiftForm = function () {
    function pageInit(){
        pageUtil.pageInit({
            initFormVals : function(){
            }
        });

        $("#add").click(function(){
            $.get("DININGSYS/archive/giftForm/toGiftFormEdit",function(str){
                var addIndex = layer.open({
                    type: 1,
                    title:'赠单原因新增',
                    skin: 'layui-layer-rim',
                    area: ['600px', '500px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#editForm").valid()) {
                            $.ajax({
                                type: 'POST',
                                url: 'DININGSYS/archive/giftForm/addGiftForm',
                                data: $("#editForm").serialize(),
                                dataType: 'JSON',
                                success: function (data) {
                                    if (data.success) {
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                    }else{
                                    	layer.tips(data.msg, '#gfname', {guide: 1, tipsMore:true, time: 2000});
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });
        
    	$("#gotype").click(function(){
    		layer.open({
    			  type: 2,
    			  title: '赠单原因类型维护',
    			  shadeClose: true,
    			  shade: 0.3,
    			  area: ['80%', '90%'],
    			  content: 'DININGSYS/archive/giftForm/reasonIndex',
    			  end:function(){
    				  $("#" + pageUtil.tabId).trigger("reloadGrid");
    			  }
    		}); 
    	});

        $("#update").click(function(){
            var grid = $("#grid-table");
            var rowId = grid.getGridParam("selrow");
            var selectRow = grid.getGridParam("selarrrow");

            if(selectRow.length < 1){
                layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            if(selectRow.length > 1){
                layer.alert('请选择一条信息进行修改！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var id = grid.jqGrid('getCell',rowId,'id');


            $.get("DININGSYS/archive/giftForm/toGiftFormEdit",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'赠单原因修改',
                    skin: 'layui-layer-rim',
                    area: ['600px', '500px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#editForm").valid()) {
                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/archive/giftForm/editGiftForm',
                                data:$("#editForm").serialize(),
                                dataType:'JSON',
                                success:function (data) {
                                    if(data.success){
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                    }else{
                                    	layer.tips(data.msg, '#gfname', {guide: 1, tipsMore:true, time: 2000});
                                    }
                                }
                            })
                        }
                    }
                });
            })
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
                    url:'DININGSYS/archive/giftForm/deleteGiftForm',
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

        $("#" + pageUtil.tabId).getJqGrid({
            url : "DININGSYS/archive/giftForm/getAllData",
            colM : "id,gfcode,gfname,gfreasonName,createTime,gfdescription",
            queryForm : "queryForm",
            colNames : "id,编码,名称,赠单原因类型,创建时间,说明",
            colWid : {"id":40,"username":140,"create_time":140},
            formatter : {
            },
            loadComplete : function() {
            }
        });
    }

    function giftReasonPageInit(){
        pageUtil.pageInit({
            initFormVals : function(){
            }
        });

        $("#giftReasonAdd").click(function(){
            $.get("DININGSYS/archive/giftForm/toGiftFormReasonEdit",function(str){
                var addIndex = layer.open({
                    type: 1,
                    title:'赠单原因类型新增',
                    skin: 'layui-layer-rim',
                    area: ['600px', '500px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#giftReasonEditForm").valid()) {
                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/archive/giftForm/addGiftFormReason',
                                data:$("#giftReasonEditForm").serialize(),
                                dataType:'JSON',
                                success:function (data) {
                                	if (data.success) {
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                    }else{
                                    	layer.tips(data.msg, '#gfrtype', {guide: 1, tipsMore:true, time: 2000});
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });

        $("#giftReasonUpdate").click(function(){
            var grid = $("#grid-table");
            var rowId = grid.getGridParam("selrow");
            var selectRow = grid.getGridParam("selarrrow");

            if(selectRow.length < 1){
                layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            if(selectRow.length > 1){
                layer.alert('请选择一条信息进行修改！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var id = grid.jqGrid('getCell',rowId,'id');


            $.get("DININGSYS/archive/giftForm/toGiftFormReasonEdit",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'赠单原因类型修改',
                    skin: 'layui-layer-rim',
                    area: ['600px', '500px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#giftReasonEditForm").valid()) {
                            $.ajax({
                                type: 'POST',
                                url: 'DININGSYS/archive/giftForm/editGiftFormReason',
                                data: $("#giftReasonEditForm").serialize(),
                                dataType: 'JSON',
                                success: function (data) {
                                    if (data.success) {
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                    }else{
                                    	layer.tips(data.msg, '#gfrtype', {guide: 1, tipsMore:true, time: 2000});
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });

        $("#giftReasonDelb").click(function(){
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
                    url:'DININGSYS/archive/giftForm/deleteGiftFormReason',
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

        $("#" + pageUtil.tabId).getJqGrid({
            url : "DININGSYS/archive/giftForm/getAllReasonData",
            colM : "id,gfcode,gfrtype,createTime,updateTime",
            queryForm : "queryForm",
            colNames : "id,编码,赠单原因类型,创建时间,修改时间",
            colWid : {"id":40,"username":140,"create_time":140},
            formatter : {
            },
            loadComplete : function() {
            }
        });
    }
    return{
        init:function () {
            pageInit();
        },
        giftReasonInit:function () {
            giftReasonPageInit();
        }
    }
}();
