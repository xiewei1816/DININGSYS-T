<%--
  Date: 2017-10-17 11:27
  @Author zhshuo.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var type = ${type} ;
    var wayId = ${id} ;
    var itemIds = '${itemIds}';

    var ztreeObj;
    jQuery(document).ready(function () {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: 0
                },
                key: {
                    name : "name"
                }
            },
            view: {
                dblClickExpand: false,
                selectedMulti: false
            },
            callback:{
                onAsyncSuccess:function () {
                    ztreeObj.expandAll(true);
                    var nodes = ztreeObj.getNodes();
                    if (nodes.length>0) {
                        ztreeObj.selectNode(nodes[0]);
                    }
                    initWxTable();
                    initYxTable();
                },
                onClick:function (event, treeId, treeNode) {
                    var nodes = ztreeObj.getSelectedNodes();
                    var nodesId = nodes[0]['id'],url;
                    if(nodesId == 0){
                        url = 'DININGSYS/settlementWay/selectAllItemFile?parentId='+nodesId+'&type=3&flag=2&ids='+itemIds;
                    }else{
                        var pId = nodes[0]['pId'];
                        if(pId == 0){
                            url = 'DININGSYS/settlementWay/selectAllItemFile?parentId='+nodesId+'&type=1&flag=2&ids='+itemIds;
                        }else{
                            url = 'DININGSYS/settlementWay/selectAllItemFile?parentId='+nodesId+'&type=2&flag=2&ids='+itemIds;
                        }
                    }
                    jQuery("#itemFileTable").jqGrid('setGridParam',{url:url}).trigger("reloadGrid");
                }
            },
            async: {
                enable: true,
                url:"DININGSYS/settlementWay/selectAllItemFileType?flag=0&type=3"
            }
        };
        ztreeObj = $.fn.zTree.init($("#itemFileTypeTree"), setting);

        $("#itemFileTypeChooseAll").click(function () {
            var rowObj = $("#itemFileTable").jqGrid('getRowData');
            jQuery("#itemFileTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileYxTable").addRowData(rowObj[i].itemFileId,rowObj[i]);
            }
            changeTcPxTxTabCon();
        });

        $("#itemFileTypeCancelAll").click(function () {
            var rowObj = $("#itemFileYxTable").jqGrid('getRowData');
            jQuery("#itemFileYxTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileTable").addRowData(rowObj[i]['itemFileId'],rowObj[i]);
            }
            changeTcPxTxTabCon();
        });

    })

    function initWxTable(){
        var nodes = ztreeObj.getSelectedNodes();
        var nodesId = nodes[0]['id'];

        $("#itemFileTable").jqGrid({
            url: 'DININGSYS/settlementWay/selectAllItemFile?parentId='+nodesId+'&type=3&flag=2&ids='+itemIds,
            datatype: "JSON",
            colNames: ["id","编号","名称","说明"],
            colModel: [
                { name: "id",hidden:true},
                { name: "num"},
                { name: "name"},
                { name: "sm"}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            height:220,
            ondblClickRow:selectRowToYx,
            loadComplete:function () {
                var kxLenth = $("#itemFileTable").jqGrid('getRowData');
                $("#itemFileKx").html(kxLenth.length);
            }
        });

        function selectRowToYx(rowid,iRow,iCol,e) {
            var grid = $('#itemFileTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#itemFileYxTable").addRowData(rowid,myCellData);

            changeTcPxTxTabCon();
        }

    }

    function initYxTable(){
        $("#itemFileYxTable").jqGrid({
            url: 'DININGSYS/settlementWay/selectAllItemFile?flag=1&ids='+itemIds,
            datatype: "JSON",
            colNames: ["itemFileId","编号","名称","说明"],
            colModel: [
                { name: "id",hidden:true},
                { name: "num"},
                { name: "name"},
                { name: "sm"}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            height:200,
            rowNum:-1,
            ondblClickRow:selectRowToKx,
            loadComplete:function () {
                var kxLenth = $("#itemFileYxTable").jqGrid('getRowData');
                $("#itemFileYx").html(kxLenth.length);
            }
        });

        function selectRowToKx(rowid,iRow,iCol,e) {
            var grid = $('#itemFileYxTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#itemFileTable").addRowData(myCellData.itemFileId,myCellData);

            changeTcPxTxTabCon();
        }
    }

    function changeTcPxTxTabCon() {
        var kxLenth = $("#itemFileTable").jqGrid('getRowData');
        $("#itemFileKx").html(kxLenth.length);
        var yxLength = $("#itemFileYxTable").jqGrid('getRowData');
        $("#itemFileYx").html(yxLength.length);
    }

</script>
<form class="form-horizontal" role="form" style="width: 95%">
    <div class="row">
        <div class="col-md-3">
            <div class="panel panel-default">
                <div class="panel-body" id="treeDiv" style="height:560px;overflow:auto;">
                    <ul id="itemFileTypeTree" class="ztree"></ul>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="panel panel-default">
                <div class="panel-body" id="contentDiv" style="height:560px;overflow-y: scroll">
                    <div style="overflow:auto;">
                        <span class="col-md-6">可选内容：<span id="itemFileKx">0</span></span>
                        <span class="col-md-6" style="text-align: right;cursor: pointer" id="itemFileTypeChooseAll">全部选择</span>
                        <table id="itemFileTable"></table>
                    </div>
                    <br>
                    <div  style="overflow:auto;">
                        <span class="col-md-6">已选内容：<span id="itemFileYx">0</span></span>
                        <span class="col-md-6" style="text-align: right;cursor: pointer" id="itemFileTypeCancelAll">全部取消</span>
                        <table id="itemFileYxTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>