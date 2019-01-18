/**
 * Created by zhshuo on 2016-10-10.
 */
var proMethods = function () {

    var zTree,rMenu;

     function zTreeInit() {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: 0
                },
                key: {
                    name : "pmtname"
                }
            },
            view: {
                dblClickExpand: false
            },
            callback: {
                onRightClick: OnRightClick,
                onClick: proOnClick,
                onAsyncSuccess : initTree
            },
            async: {
                enable: true,
                url:"DININGSYS/archive/proMethods/getAllTypeData"
            }
        };

        function OnRightClick(event, treeId, treeNode) {
            if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
                return false;
            } else if (treeNode && treeNode.id == 0) {
                zTree.cancelSelectedNode();
                zTree.selectNode(treeNode);
                showRMenu("root", event.clientX, event.clientY);
            } else if (treeNode && treeNode.parentId == 0){
                zTree.selectNode(treeNode);
                showRMenu("type", event.clientX, event.clientY);
            }
        }

        function proOnClick(event, treeId, treeNode) {
            changeTable(treeNode);
        }

        function showRMenu(type, x, y) {
            $("#rMenu ul").show();
            if(type == 'root'){
                $("#m_addTypes").show();
                $("#m_addMethods").hide();
                $("#m_del").hide();
                $("#m_editTypes").hide();
            }else if(type == 'type'){
                $("#m_addTypes").hide();
                $("#m_addMethods").show();
                $("#m_del").show();
                $("#m_editTypes").show();
            }
            rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
            $("body").bind("mousedown", onBodyMouseDown);
        }
        function hideRMenu() {
            if (rMenu) rMenu.css({"visibility": "hidden"});
            $("body").unbind("mousedown", onBodyMouseDown);
        }
        function onBodyMouseDown(event){
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
                rMenu.css({"visibility" : "hidden"});
            }
        }
        var addCount = 1;
        $("#m_addTypes").click(function () {
            hideRMenu();
            $.get('DININGSYS/archive/proMethods/toProMehtodsTypeEdit',function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'制作方法类别新增',
                    skin: 'layui-layer-rim',
                    area: ['500px', '400px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#proMethTypeEditForm").valid()) {
                            $.ajax({
                                type: 'POST',
                                url: 'DININGSYS/archive/proMethods/addProMethodsType',
                                data: $("#proMethTypeEditForm").serialize(),
                                dataType: 'JSON',
                                async: false,
                                success: function (data) {
                                    if (data.success) {
                                        layer.close(addIndex);
                                        tableRload(true);
                                        layer.msg("数据已保存",{time:1000});
                                        zTree.reAsyncChildNodes(null, "refresh");
                                    } else {
                                        layer.alert(data.errorMsg, {title: '提示', icon: 0, skin: 'layer-ext-moon'});
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });


        $("#m_editTypes").click(function () {
            hideRMenu();
            var node = zTree.getSelectedNodes()[0];
            var id = node.id;
            $.get('DININGSYS/archive/proMethods/toProMehtodsTypeEdit',{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'制作方法类别修改',
                    skin: 'layui-layer-rim',
                    area: ['500px', '400px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/archive/proMethods/editProMethodsType',
                            data:$("#proMethTypeEditForm").serialize(),
                            dataType:'JSON',
                            async:false,
                            success:function (data) {
                                if(data.success){
                                    layer.close(addIndex);
                                    layer.msg("数据已修改",{time:1000});
                                    zTree.reAsyncChildNodes(null, "refresh");
                                    tableRload(true);
                                }else{
                                    layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                }
                            }
                        })
                    }
                });
            })
        });


        $("#m_addMethods").click(function () {
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            var id = nodes[0].id;
            var h = ($(window).height() - 100)*0.9;
            $.get('DININGSYS/archive/proMethods/toProMehtodsEdit',{pmtId:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'制作方法新增',
                    skin: 'layui-layer-rim',
                    area: ['550px', h+'px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#proMethTypeEditForm").valid()){
                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/archive/proMethods/addProMethods',
                                data:$("#proMethTypeEditForm").serialize(),
                                dataType:'JSON',
                                async:false,
                                success:function (data) {
                                    if(data.success){
                                        layer.msg("数据已保存",{time:1000});
                                        layer.close(addIndex);
                                        tableRload(false);
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

        $("#m_editMethods").click(function () {
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            var id = nodes[0].id;
            var h = ($(window).height() - 100)*0.9;
            $.get('DININGSYS/archive/proMethods/toProMehtodsEdit',{pmtId:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'制作方法',
                    skin: 'layui-layer-rim',
                    area: ['550px', h+'px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/archive/proMethods/addProMethods',
                            data:$("#proMethTypeEditForm").serialize(),
                            dataType:'JSON',
                            async:false,
                            success:function (data) {
                                if(data.success){
                                    layer.close(addIndex);
                                    layer.msg("数据已保存",{time:1000});
                                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                    treeObj.reAsyncChildNodes(null, "refresh");
                                    tableRload(false);
                                }else{
                                    layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                }
                            }
                        })
                    }
                });
            })
        });

        $("#m_del").click(function () {
            hideRMenu();
            var nodes = zTree.getSelectedNodes();
            if (nodes && nodes.length>0) {
                if (nodes[0].children && nodes[0].children.length > 0) {
                    var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
                    if (confirm(msg)==true){
                        zTree.removeNode(nodes[0]);
                    }
                } else {
                	var id = nodes[0].id;
                	layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/archive/proMethods/deleteByPrimaryKey',
                            data:{id:id},
                            dataType:'JSON',
                            success:function (data) {
                                if(data.success){
                                	layer.msg("数据已删除",{time:1000});
//                                	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                                    zTree.reAsyncChildNodes(null, "refresh");
                                }
                            }
                        });
                        layer.close(index);
                    });
                }
            }
        });

         rMenu = $("#rMenu");
         zTree = $.fn.zTree.init($("#treeDemo"), setting);
    }

    function initTree() {
//        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandAll(true);
        var node = zTree.getNodeByParam("id",0,null);

        zTree.selectNode(node);

        changeTable(node);
    }

    function setTableWidthAndHeight() {
        $("#rightBigPanel").height($(window).height()-60);

        $("#proMethodTypeTree").height($(window).height()-100);

        var needh = $("#tabelWidth").outerHeight() + $(".btn-toolbar").outerHeight() + 80;
        $("#grid-table").jqGrid("setGridWidth",$("#tabelWidth").width(),true);
        $("#grid-table").jqGrid("setGridHeight",$(window).height() - needh,true);

        $("#grid-table1").jqGrid("setGridWidth",$("#tabelWidth").width(),true);
        $("#grid-table1").jqGrid("setGridHeight",$(window).height() - needh,true);
    }

    function changeTable(node){
        var url;
        if(node.id == "0"){
            url = "DININGSYS/archive/proMethods/DgProMethodsType";
            jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
            $("#methodsEditDiv").hide();
            $("#grid-table").jqGrid("setGridState",'visible');
            $("#grid-table1").jqGrid("setGridState",'hidden');
        }else if(node.parentId == 0){
            var id =  node.id;
            url = "DININGSYS/archive/proMethods/getAllProMethods?pmtid="+id;
            jQuery("#grid-table1").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
            $("#methodsEditDiv").show();
            $("#grid-table").jqGrid("setGridState",'hidden');
            $("#grid-table1").jqGrid("setGridState",'visible');
        }
    }

    function formateColum(cellvalue, options, rowObject) {
        if(options.colModel.name == 'collectProMoney'){
            if(cellvalue == 1){
                return "√";
            }else{
                return "×";
            }
        }else if(options.colModel.name == 'collectProMoneybynum'){
            if(cellvalue == 1){
                return "√";
            }else{
                return "×";
            }
        }else if(options.colModel.name == 'canUpdate'){
            if(cellvalue == 1){
                return "√";
            }else{
                return "×";
            }
        }
    }

    function pagerInit() {
        $("#grid-table").jqGrid({
            url: "DININGSYS/archive/proMethods/DgProMethodsType",
            datatype: "json",
            colNames: ["id","类别名称","创建时间"],
            colModel: [
                { name: "id"},
                { name: "pmtname" },
                { name: "createTime"}
            ],
            pager: "#grid-pager",
            rowNum: 20,
            rowList: [ 20, 50, 100 ],
            viewrecords: true,
            gridview: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });
        setTableWidthAndHeight();

        $("#grid-table1").jqGrid({
            url: "DININGSYS/archive/proMethods/getAllProMethods",
            datatype: "json",
            colNames: ["id","编号","名称","顺序号","收取制作费用","制作费用","按品项数量收取制作费用","制作费用可修改标志","说明","创建时间"],
            colModel: [
                { name: "id"},
                { name: "pmcode"},
                { name: "pmname"},
                { name: "orderno"},
                { name: "collectProMoney",formatter:formateColum},
                { name: "proMoney"},
                { name: "collectProMoneybynum",formatter:formateColum},
                { name: "canUpdate",formatter:formateColum},
                { name: "description",},
                { name: "createTime"}
            ],
            pager: "#grid-pager1",
            rowNum: 20,
            rowList: [ 20, 50, 100 ],
            viewrecords: true,
            gridview: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });
        setTableWidthAndHeight();
        $("#grid-table1").jqGrid("setGridState",'hidden');

        $(window).resize(function () {
            setTableWidthAndHeight();
        });

        $("#methodsDelb").click(function () {
        	var ids = [];
            var grid = $("#grid-table1");
            var rowId = grid.getGridParam("selrow");
            var selectRow = grid.getGridParam("selarrrow");
            var id = grid.jqGrid('getCell',rowId,'id');
            if(id == undefined){
                layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }
            ids.push(id);
            layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/archive/proMethods/deleteProMethods',
                    data:{ids:ids.toString()},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                        	layer.msg("数据已删除",{time:1000});
                            tableRload(false);
                        }
                    }
                });
                layer.close(index);
            });

        });

        
        $("#methodsEdit").click(function () {
            var grid = $("#grid-table1");
            var rowId = grid.getGridParam("selrow");
            var selectRow = grid.getGridParam("selarrrow");
            var h = ($(window).height() - 100)*0.9;
            var id = grid.jqGrid('getCell',rowId,'id');
            if(id == undefined){
                layer.alert('请选择一项需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }
            $.get("DININGSYS/archive/proMethods/toProMehtodsEdit",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'制作方法修改',
                    skin: 'layui-layer-rim',
                    area: ['550px', h+'px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/archive/proMethods/updateProMethods',
                            data:$("#proMethTypeEditForm").serialize(),
                            dataType:'JSON',
                            success:function (data) {
                                if(data.success){
                                	layer.msg("数据已修改",{time:1000});
                                    layer.close(addIndex);
                                    tableRload(false);
                                }
                            }
                        })
                    }
                });
            })
        })
    }
    

    function tableRload(flag) {
        if(flag){
            jQuery("#grid-table").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
        }else{
            jQuery("#grid-table1").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
        }
    }

    return {
        zTreeInit:function () {
            zTreeInit();
        },
        pagerInit:function () {
            pagerInit();
        }
    }

}();