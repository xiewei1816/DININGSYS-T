/**
 * Created by zengchao 
 */

var zTree,rMenu;
var flavor = function () {
     function zTreeInit() {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentid",
                    rootPId: 0
                },
                key: {
                    name : "name"
                }
            },
            view: {
                dblClickExpand: false
            },
            callback: {
                onClick: proOnClick,
                onAsyncSuccess : initTree
            },
            async: {
                enable: true,
                url:"DININGSYS/archive/flavor/getTreeNodes"
            }
        };

         
         rMenu = $("#rMenu");
         zTree = $.fn.zTree.init($("#treeDemo"), setting);

    }
    
    function proOnClick(event, treeId, treeNode) {
            changeTable(treeNode);
    }
    
    function changeTable(node){
        if(node){
            var url;
            url = "DININGSYS/archive/flavor/getFlavorByParentId?id="+node.id+"&pid="+node.parentid;
            jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
        }   
            //setTableWidthAndHeight();
    }

    function initTree() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.expandAll(true);
        var node = treeObj.getNodeByParam("id",0,null);

        treeObj.selectNode(node);

        changeTable(node);
    }

    function setTableWidthAndHeight() {
        var needh = $("#tabelWidth").outerHeight() + $(".btn-toolbar").outerHeight() + 80;
        $("#grid-table").jqGrid("setGridWidth",$("#tabelWidth").width(),true);
        $("#grid-table").jqGrid("setGridHeight",$(window).height() - needh,true);
    }

    function formateColum(cellvalue, options, rowObject) {
        if(options.colModel.name === 'isonly'){
            if(cellvalue === '1'){
                return "<font color='red'>√</font>";
            }else{
                return "×";
            }
        }
    }

    function pagerInit() {
        $("#grid-table").jqGrid({
            url: "DININGSYS/archive/flavor/getFlavorByParentId",
            datatype: "JSON",
            mtype: "GET",
            colNames: ["id","编号","名称","助记符","创建时间","是否单选"],
            colModel: [
                { name: "id",width:30},
                { name: "number" },
                { name: "name"},
                { name: "zjf"},
                { name: "createtime"},
                { name: "isonly",formatter:formateColum}
            ],
            pager: "#grid-pager",
            rowNum: 10,
            rowList: [ 20, 50, 100 ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true,
            multiselect:true
        });
        setTableWidthAndHeight();
    }
    
    $("#add").click(function () {
            var nodes = zTree.getSelectedNodes();
            if(nodes.length === 0){
                layer.alert('请选择新建的树节点！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }
            var node = nodes[0];
            
            $.get("DININGSYS/archive/flavor/toAddFlavor",{pid:node.id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'添加',
                    skin: 'layui-layer-rim',
                    area: ['550px', '400px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#flavorEditForm").valid()){
                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/archive/flavor/saveFlavor',
                                data:$("#flavorEditForm").serialize(),
                                dataType:'JSON',
                                success:function (data) {
                                    if(data.success){
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                        treeObj.reAsyncChildNodes(null, "refresh");
                                    }else{
                                        layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });
    
    $("#update").click(function () {
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
            
            $.get("DININGSYS/archive/flavor/toAddFlavor",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'修改',
                    skin: 'layui-layer-rim',
                    area: ['550px', '400px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#flavorEditForm").valid()) {
                            $.ajax({
                                type: 'POST',
                                url: 'DININGSYS/archive/flavor/saveFlavor',
                                data: $("#flavorEditForm").serialize(),
                                dataType: 'JSON',
                                success: function (data) {
                                    if (data.success) {
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                        treeObj.reAsyncChildNodes(null, "refresh");
                                    } else {
                                        layer.alert(data.errorMsg, {title: '提示', icon: 0, skin: 'layer-ext-moon'});
                                    }
                                }
                            });
                        }
                    }
                });
            });
        });
        
        
        $("#delb").click(function () {
            var grid = $("#grid-table");
            var rowId = grid.getGridParam("selrow");
            var selectRow = grid.getGridParam("selarrrow");
            
            if(selectRow.length < 1){
                layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            if(selectRow.length > 1){
                layer.alert('请选择一条信息进行删除！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }
            
            var id = grid.jqGrid('getCell',rowId,'id');
            
            layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/archive/flavor/deleteFlavor',
                    data:{id:id},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                            $("#grid-table").trigger("reloadGrid");
                            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                            treeObj.reAsyncChildNodes(null, "refresh");
                        }else{
                            layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                });
                layer.close(index);
            });
        });
        
    return {
        zTreeInit:function () {
            zTreeInit();
        },
        pagerInit:function () {
            pagerInit();
        }
    }

}();