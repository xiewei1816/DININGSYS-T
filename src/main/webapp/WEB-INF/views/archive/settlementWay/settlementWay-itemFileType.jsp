<%--
  Date: 2017-10-17 11:13
  @Author zhshuo.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    jQuery(document).ready(function () {
        var type = ${type} ;
        var wayId = ${id} ;
        var itemIds = '${itemIds}';

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
            async: {
                enable: true,
                url:"DININGSYS/settlementWay/selectAllItemFileType?flag=0&type="+type
            }
        };
        $.fn.zTree.init($("#itemFileTypeTree"), setting);


        $("#itemFileTypeTable").jqGrid({
            url: 'DININGSYS/settlementWay/selectAllItemFileType?type='+type+'&flag=2&ids='+itemIds,
            datatype: "JSON",
            colNames: ["id","编号","名称","说明"],
            colModel: [
                { name: "id",hidden:true},
                { name: "num"},
                { name: "name"},
                { name: "description"}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            height:220,
            ondblClickRow:selectRowToYx,
            loadComplete:function () {
                var kxLenth = $("#itemFileTypeTable").jqGrid('getRowData');
                $("#itemFileTypeKx").html(kxLenth.length);
            }
        });

        $("#itemFileTypeYxTable").jqGrid({
            url: 'DININGSYS/settlementWay/selectAllItemFileType?type='+type+'&flag=1&ids='+itemIds,
            datatype: "JSON",
            colNames: ["id","编号","名称","说明"],
            colModel: [
                { name: "id",hidden:true},
                { name: "num"},
                { name: "name"},
                { name: "description"}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            height:200,
            rowNum:-1,
            ondblClickRow:selectRowToKx,
            loadComplete:function () {
                var kxLenth = $("#itemFileTypeYxTable").jqGrid('getRowData');
                $("#itemFileTypeYx").html(kxLenth.length);
            }
        });

        function selectRowToYx(rowid,iRow,iCol,e) {
            var grid = $('#itemFileTypeTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#itemFileTypeYxTable").addRowData(rowid,myCellData);

            changeTcPxTxTabCon();
        }

        function selectRowToKx(rowid,iRow,iCol,e) {
            var grid = $('#itemFileTypeYxTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#itemFileTypeTable").addRowData(myCellData.id,myCellData);

            changeTcPxTxTabCon();
        }

        function changeTcPxTxTabCon() {
            var kxLenth = $("#itemFileTypeTable").jqGrid('getRowData');
            $("#itemFileTypeKx").html(kxLenth.length);
            var yxLength = $("#itemFileTypeYxTable").jqGrid('getRowData');
            $("#itemFileTypeYx").html(yxLength.length);
        }

        $("#itemFileTypeChooseAll").click(function () {
            var rowObj = $("#itemFileTypeTable").jqGrid('getRowData');
            jQuery("#itemFileTypeTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileTypeYxTable").addRowData(rowObj[i].id,rowObj[i]);
            }
            changeTcPxTxTabCon();
        });

        $("#itemFileTypeCancelAll").click(function () {
            var rowObj = $("#itemFileTypeYxTable").jqGrid('getRowData');
            jQuery("#itemFileTypeYxTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileTypeTable").addRowData(rowObj[i]['id'],rowObj[i]);
            }
            changeTcPxTxTabCon();
        });

    })
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
                        <span class="col-md-6">可选内容：<span id="itemFileTypeKx">0</span></span>
                        <span class="col-md-6" style="text-align: right;cursor: pointer" id="itemFileTypeChooseAll">全部选择</span>
                        <table id="itemFileTypeTable"></table>
                    </div>
                    <br>
                    <div  style="overflow:auto;">
                        <span class="col-md-6">已选内容：<span id="itemFileTypeYx">0</span></span>
                        <span class="col-md-6" style="text-align: right;cursor: pointer" id="itemFileTypeCancelAll">全部取消</span>
                        <table id="itemFileTypeYxTable"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>