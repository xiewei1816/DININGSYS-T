/**
 * Created by mrren on 2016-08-01.
 */
var dicType = {
    dicTypeTable : null,
    initCheckBox: function () {
        jQuery('#dicType_table .group-checkable').live('change',function () {
            var set = jQuery(this).attr("data-set");
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                if (checked) {
                    $(this).attr("checked", true);
                } else {
                    $(this).attr("checked", false);
                }
                $(this).parents('tr').toggleClass("active");
            });
            jQuery.uniform.update(set);

        });

        jQuery('#dicType_table tbody tr td .checkboxes').live('change',function(){
            $(this).parents('tr').toggleClass("active");
        });
    },
    init: function () {
        if (!jQuery().dataTable) {
            return;
        }

        dicType.dicTypeTable = $('#dicType_table').dataTable({
            "sDom" : "<'row'<'col-md-6 col-sm-12'l><'col-md-12 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
            "bPaginate":true,
            "bProcessing": true,
            "bServerSide": true,
            "sAjaxSource": "DININGSYS/dicType/getData",
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                $.ajax( {
                    "dataType": 'json',
                    "type": "POST",
                    "url": sSource,
                    "data": aoData,
                    "success": fnCallback
                } );
            },
            "aoColumns":
                [
                    { "mData": null},
                    { "mData": "dicTypeName"},
                    { "mData": "dicTypeCode"},
                    { "mData": "createTime"}
                ],
            "iDisplayLength": 10,
            "sPaginationType": "bootstrap",
            "oLanguage" : {
                "sUrl":          "app/js/DININGSYS/system/pageI18N/zh_CN.json"
            },
            "aoColumnDefs": [
                {'aTargets': [0],"mData":null,"bSortable":false,"bSearchable":false,mRender:function (data, type, full) {
                    var operationID = full['id'];
                    return "<input type='checkbox' class='checkboxes' value='"+operationID+"' />";
                }}
            ]
        });

        dicType.initCheckBox();

        jQuery('#dicType_table .dataTables_filter input').addClass("form-control input-medium"); // modify table search input
        jQuery('#dicType_table .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown

        $('body').on('click', '#role_table .btn-editable', function() {
            alert('Edit record with id:' + $(this).attr("data-id"));
        });

        $('body').on('click', '#role_table .btn-removable', function() {
            alert('Remove record with id:' + $(this).attr("data-id"));
        });

        dicType.iniDicTypeAjaxModal();

    },
    iniDicTypeAjaxModal : function (){
        $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
            '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
            '<div class="progress progress-striped active">' +
            '<div class="progress-bar" style="width: 100%;"></div>' +
            '</div>' +
            '</div>';

        $.fn.modalmanager.defaults.resize = true;

        var $modal = $('#dicType_ajax_modal');

        //绑定按钮事件
        //修改
        $('.dicTypeEdit').on('click', function(){
            var checkedTr = $('#dicType_table tbody tr td').find("input:checked");
            if(checkedTr.length!=1){
                showAjaxMsg("请选择一条数据");
                return;
            }
            var dicTypeEditId = $(checkedTr).val();

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/dicType/showDicTypeEditPage/'+dicTypeEditId, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });
        //新增
        $(".dicTypeAdd").on('click',function () {
            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/dicType/showDicTypeAddPage', '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $(".dicTypeDelBtn").on('click',function () {
            var checkedTr = $('#dicType_table tbody tr td').find("input:checked");
            if(checkedTr.length<1){
                showAjaxMsg("请至少选择一条数据");
                return;
            }
            var ids = [];
            $(checkedTr).each(function () {
                ids.push($(this).val());
            });
            var dicTypeIds = ids.toString();
            showAjaxMsg("你确定要删除这些数据？",true,function(){
                $.ajax({
                    type:"POST",
                    url:"DININGSYS/dicType/delDicTypes",
                    data:{dicTypeIds:dicTypeIds},
                    dataType:"JSON",
                    success:function (data) {
                        if(data.success){
                            closeAjaxShowMsg();
                            dicType.dicTypeTable._fnAjaxUpdate();
                        }
                    }
                })
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
    editDicType:function (type) {
        if(type=='add') dicType.addDicType();else dicType.updateDicType();
    },
    addDicType : function(){
        if($("#dicTypeEditForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/dicType/addDicType",
                data:$("#dicTypeEditForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("dicType_ajax_modal");
                        dicType.reloadTable();
                    }
                }
            })
        }
    },
    updateDicType : function(){
        if($("#dicTypeEditForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/dicType/editDicType",
                data:$("#dicTypeEditForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("dicType_ajax_modal");
                        dicType.reloadTable();
                    }else
                        showAjaxMsg("操作失败")
                }
            })
        }
    },

    /*table reload*/
    reloadTable : function () {
        dicType.dicTypeTable._fnAjaxUpdate();
    }
};