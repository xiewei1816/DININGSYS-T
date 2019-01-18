/**
 * Created by zhshuo on 2016-07-29.
 */
var  bdOrgan = {
    getIndexTableData: function () {
        $.get("SCRMS/bdOrgan/getIndexTableData",function (data) {
            $("#indexTableID").html(data);
        })
    },
    initAddOrEditModal : function (){
        $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
            '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
            '<div class="progress progress-striped active">' +
            '<div class="progress-bar" style="width: 100%;"></div>' +
            '</div>' +
            '</div>';

        $.fn.modalmanager.defaults.resize = true;

        var $modal = $('#bdorgan-ajax-modal');

        $('.bdOrganAdd').on('click', function(){


            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('SCRMS/bdOrgan/toAdd', '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $('.bdOrganEdit').on('click', function(){

            var tableSelected = $("#organ-index-table tbody tr").filter(".selected");

            if(tableSelected.length < 1){
                showAjaxMsg("请先选择一条数据");
                return;
            }

            var editID = $(tableSelected).attr("organ-id");

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('SCRMS/bdOrgan/showEditPage/'+editID, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });
        
        $('.bdOrganDel').on('click', function(){

            var tableSelected = $("#organ-index-table tbody tr").filter(".selected");

            if(tableSelected.length < 1){
                showAjaxMsg("请先选择一条数据");
                return;
            }

            var editID = $(tableSelected).attr("organ-id");

            $.ajax({
                type:"POST",
                url:'SCRMS/bdOrgan/delOrgan',
                data:{id:editID},
                dataType:"json",
                success:function (data) {
                    if(data.success){
                        bdOrgan.getIndexTableData();
                    }else
                        showAjaxMsg("操作失败")
                }
            })
        });

        $modal.on('click', '.update', function(){
            $modal.modal('loading');
            setTimeout(function(){
                $modal
                    .modal('loading')
                    .find('.modal-body')
                    .prepend('<div class="alert alert-info fade in">' +
                        'Updated!<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '</div>');
            }, 1000);
        });
    },
    addBdOrgan:function (type) {
        if($("#bdOrganAddForm").valid()){
            if(type == 'add'){
                $.ajax({
                    type:"POST",
                    url:"SCRMS/bdOrgan/addBdOrgan",
                    data:$("#bdOrganAddForm").serialize(),
                    dataType:"json",
                    success:function (data) {
                        if(data.success){
                            bdOrgan.getIndexTableData();
                            closeModal("bdorgan-ajax-modal")
                        }else
                            showAjaxMsg("操作失败")
                    }
                })
            }else{
                $.ajax({
                    type:"POST",
                    url:"SCRMS/bdOrgan/editBdOrgan",
                    data:$("#bdOrganAddForm").serialize(),
                    dataType:"json",
                    success:function (data) {
                        if(data.success){
                            bdOrgan.getIndexTableData();
                            closeModal("bdorgan-ajax-modal")
                        }else
                            showAjaxMsg("操作失败")
                    }
                })
            }
        }
    },

    /*初始化和tree相关的function*/
    getbdOrganData:function(){
        $.ajax({
            type: "POST",
            url: "SCRMS/bdOrgan/getbdOrganData",
            dataType:"json",
            contentType: 'charset/utf-8',
            async:false,
            success: function (data) {
                data = eval('(' + data + ')');
                bdOrgan.initZtree(data.allBdOrgan);
            },
            error: function () {
                console.error("菜单数据加载异常。");
            }
        })
    },
    saveSelectedParentOrgan:function(){
        var organParentTree =  $.fn.zTree.getZTreeObj("bdOrganParentTree");
        var parentOrgan = organParentTree.getSelectedNodes();
        if(parentOrgan.length > 0){
            $("#bdOrganParentID").text(parentOrgan[0].organName);
            $("#parentId").val(parentOrgan[0].id);
        }
        closeModal("bdOrganParentChoose");
    },
    initZtree : function(zNodes){
        var treeObj = $.fn.zTree.init($("#bdOrganParentTree"), bdOrganSetting, zNodes);
        treeObj.expandAll(true);
        var initID = $("#parentId").val();
        if(initID){
            var selectedNode = treeObj.getNodeByTId(initID);
            treeObj.selectNode(selectedNode);
        }

    }
};

var bdOrganSetting = {
    view: {
        selectedMulti: false,
        showLine:false
    },
    data: {
        key: {
            name: "organName",
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: 0
        }
    }
};