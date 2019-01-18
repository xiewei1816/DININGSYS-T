/**
 * Created by mrren on 2016-08-01.
 */
var dic = {
    dicTable : null,
    initCheckBox: function () {
        jQuery('#dic_table .group-checkable').live('change',function () {
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

        jQuery('#dic_table tbody tr td .checkboxes').live('change',function(){
            $(this).parents('tr').toggleClass("active");
        });
    },
    init: function () {
        if (!jQuery().dataTable) {
            return;
        }

        dic.dicTable = $('#dic_table').dataTable({
            "sDom" : "<'row'<'col-md-6 col-sm-12'l><'col-md-12 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
            "bPaginate":true,
            "bProcessing": true,
            "bServerSide": true,
            "sAjaxSource": "DININGSYS/dic/getData",
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
                    { "mData": "dicName"},
                    { "mData": "dicCode"},
                    { "mData": "dicType"},
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

        dic.initCheckBox();

        jQuery('#dic_table .dataTables_filter input').addClass("form-control input-medium"); // modify table search input
        jQuery('#dic_table .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown

        $('body').on('click', '#role_table .btn-editable', function() {
            alert('Edit record with id:' + $(this).attr("data-id"));
        });

        $('body').on('click', '#role_table .btn-removable', function() {
            alert('Remove record with id:' + $(this).attr("data-id"));
        });

        dic.iniDicAjaxModal();

    },
    iniDicAjaxModal : function (){
        $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
            '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
            '<div class="progress progress-striped active">' +
            '<div class="progress-bar" style="width: 100%;"></div>' +
            '</div>' +
            '</div>';

        $.fn.modalmanager.defaults.resize = true;

        var $modal = $('#dic_ajax_modal');

        //绑定按钮事件
        //修改
        $('.dicEdit').on('click', function(){
            var checkedTr = $('#dic_table tbody tr td').find("input:checked");
            if(checkedTr.length!=1){
                showAjaxMsg("请选择一条数据");
                return;
            }
            var dicEditId = $(checkedTr).val();

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/dic/showDicEditPage/'+dicEditId, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });
        //新增
        $(".dicAdd").on('click',function () {
            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/dic/showDicAddPage', '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $(".dicDelBtn").on('click',function () {
            var checkedTr = $('#dic_table tbody tr td').find("input:checked");
            if(checkedTr.length<1){
                showAjaxMsg("请至少选择一条数据");
                return;
            }
            var ids = [];
            $(checkedTr).each(function () {
                ids.push($(this).val());
            });
            var dicIds = ids.toString();
            showAjaxMsg("你确定要删除这些数据？",true,function(){
                $.ajax({
                    type:"POST",
                    url:"DININGSYS/dic/delDics",
                    data:{dicIds:dicIds},
                    dataType:"JSON",
                    success:function (data) {
                        if(data.success){
                            closeAjaxShowMsg();
                            dic.dicTable._fnAjaxUpdate();
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
    editDic:function (type) {
        if(type=='add') dic.addDic();else dic.updateDic();
    },
    addDic : function(){
        if($("#dicEditForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/dic/addDic",
                data:$("#dicEditForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("dic_ajax_modal");
                        dic.reloadTable();
                    }
                }
            })
        }
    },
    updateDic : function(){
        if($("#dicEditForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/dic/editDic",
                data:$("#dicEditForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("dic_ajax_modal");
                        dic.reloadTable();
                    }else
                        showAjaxMsg("操作失败")
                }
            })
        }
    },

    /*table reload*/
    reloadTable : function () {
        dic.dicTable._fnAjaxUpdate();
    }
};