/**
 * Created by zhshuo on 2016-09-29.
 */
var arServing = function () {
    function pageInit(){
        pageUtil.pageInit({
            initFormVals : function(){
            }
        });

        $(".add").click(function(){
            $.get("DININGSYS/archive/serving/toEdit",function(str){
                var addIndex = layer.open({
                    type: 1,
                    title:'上菜状态',
                    skin: 'layui-layer-rim',
                    area: ['580px', '440px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if(checkForm()) {
                            $.ajax({
                                type: 'POST',
                                url: 'DININGSYS/archive/serving/addData',
                                data: $("#editForm").serialize(),
                                dataType: 'JSON',
                                success: function (data) {
                                    if (data.success) {
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                    }
                                }
                            })
                        }
                    }
                });
            })
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


            $.get("DININGSYS/archive/serving/toEdit",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'上菜状态',
                    skin: 'layui-layer-rim',
                    area: ['580px', '440px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if(checkForm()) {
                            $.ajax({
                                type: 'POST',
                                url: 'DININGSYS/archive/serving/editData',
                                data: $("#editForm").serialize(),
                                dataType: 'JSON',
                                success: function (data) {
                                    if (data.success) {
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });
        
    	//表单验证
    	function checkForm(){
    		var flag = true;
    		var scode = $("#scode").val();
    		if(scode == ""){
    			layer.tips('编码不能为空!', $("#scode") , {guide: 1, tipsMore:true, time: 2000}); 
    			flag = false;
    		}
    		var sname = $("#sname").val();
    		if(sname == ""){
    			layer.tips('名称不能为空!', $("#sname") , {guide: 1, tipsMore:true, time: 2000}); 
    			flag = false;
    		}
    		var mnemonic = $("#mnemonic").val();
    		if(mnemonic == ""){
    			layer.tips('助记符不能为空!', $("#mnemonic") , {guide: 1, tipsMore:true, time: 2000}); 
    			flag = false;
    		}
    		var sorg = $("#sorg").val();
    		if(sorg == ""){
    			layer.tips('所属机构不能为空!', $("#sorg") , {guide: 1, tipsMore:true, time: 2000}); 
    			flag = false;
    		}
    		return flag;
    	}
        

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
                    url:'DININGSYS/archive/serving/delData',
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
            url : "DININGSYS/archive/serving/getPageList",
            colM : "id,scode,sname,mnemonic,sorg",
            queryForm : "queryForm",
            colNames : "id,编码,名称,助记符,所属组织结构",
            colWid : {"id":40,"username":140,"create_time":140},
            formatter : {
            },
            loadComplete : function() {
            }
        });
    }

    return {
        init : function(){
            pageInit();
        }
    }

}();
