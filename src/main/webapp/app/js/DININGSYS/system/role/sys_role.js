/**
 * Created by mrren on 2016-07-26.
 */
var sysRole = {
    roleTable : null,
    initCheckBox: function () {
        jQuery('#role_table .group-checkable').live('change',function () {
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

        jQuery('#role_table tbody tr td .checkboxes').live('change',function(){
            $(this).parents('tr').toggleClass("active");
        });
    },
    init: function () {
        //解决当JS重新加载，事件注册多次的情况
        $(".roleMenuEdit").die("click");
        $(".roleLockBtn").die("click");


        if (!jQuery().dataTable) {
            return;
        }

        sysRole.roleTable = $('#role_table').dataTable({
            "sDom" : "<'row'<'col-md-6 col-sm-12'l><'col-md-12 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
            "bPaginate":true,
            "bProcessing": true,
            "bServerSide": true,
            "sAjaxSource": "DININGSYS/role/getData",
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
                    { "mData": "roleName"},
                    { "mData": "roleSign"},
                    { "mData": "description"},
                    { "mData": "state"},
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
                    var state = full['state'];
                    return "<input type='checkbox' class='checkboxes' value='"+operationID+"' rolestate='"+state+"' />";
                }},
                {'aTargets': [4],mRender:function (data, type, full) {
                    if(full['state']=='1'){
                        return "正常";
                    }else {
                        return "锁定";
                    }
                }},
                {'mData':null,"bSortable":false,"bSearchable":false,'aTargets': [6],mRender:function (data, type, full) {
                    var operationID = full['id'];    
                    return "<a href='javascript:void(0)' roleid='"+operationID+"' class='btn btn-xs default roleMenuEdit'>权限编辑 <i class='fa fa-edit'></i></a>";
                }},
                {'mData':null,"bSortable":false,"bSearchable":false,'aTargets': [7],mRender:function (data, type, full) {
                    var operationID = full['id'];  
                    var roleState = full['state'];
                    return "<a href='javascript:void(0)' operaID='"+operationID+"' rolestate='"+roleState+"' class='btn btn-sm dark roleLockBtn' title='冻结'><i class='fa fa-lock'></i></a>";
                }}
            ]
        });

        sysRole.initCheckBox();

        jQuery('#role_table .dataTables_filter input').addClass("form-control input-medium"); // modify table search input
        jQuery('#role_table .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown

        $('body').on('click', '#role_table .btn-editable', function() {
            alert('Edit record with id:' + $(this).attr("data-id"));
        });

        $('body').on('click', '#role_table .btn-removable', function() {
            alert('Remove record with id:' + $(this).attr("data-id"));
        });

        sysRole.iniRoleAjaxModal();

    },
    iniRoleAjaxModal : function (){
        $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
            '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
            '<div class="progress progress-striped active">' +
            '<div class="progress-bar" style="width: 100%;"></div>' +
            '</div>' +
            '</div>';

        $.fn.modalmanager.defaults.resize = true;

        var $modal = $('#role_ajax_modal');

        //绑定按钮事件
        //角色菜单编辑
        $('.roleMenuEdit').live('click', function(){
            var roleId = "";

            if($(this).attr('clickType')){
                var checkedTr = $('#role_table tbody tr td').find("input:checked");
                if(checkedTr.length!=1){
                    showAjaxMsg("请选择一条数据");
                    return;
                }
                roleId = $(checkedTr).val();
            }else
                roleId = $(this).attr('roleid');

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/role/showMenuEdit/'+roleId, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });
        //角色功能编辑
        $('.roleFunEdit').live('click', function(){
            var userId = "";

            if($(this).attr('clickType')){
                var checkedTr = $('#role_table tbody tr td').find("input:checked");
                if(checkedTr.length!=1){
                    showAjaxMsg("请选择一条数据");
                    return;
                }
                userId = $(checkedTr).val();
            }else
                userId = $(this).attr('operaID');

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/role/showUserEdit/'+userId, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $(".roleAdd").on('click',function () {
            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/role/sysRoleAddPage', '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $(".roleBaseEdit").on('click',function () {

            var checkedTr = $('#role_table tbody tr td').find("input:checked");
            if(checkedTr.length!=1){
                showAjaxMsg("请选择一条数据");
                return;
            }
            var roleID = $(checkedTr).val();

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/role/showEditRileBaseInfo/'+roleID, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $(".roleDelBtn").on('click',function () {
            var checkedTr = $('#role_table tbody tr td').find("input:checked");
            if(checkedTr.length<1){
                showAjaxMsg("请至少选择一条数据");
                return;
            }
            var ids = [];
            $(checkedTr).each(function () {
                ids.push($(this).val());
            });
            var roleids = ids.toString();
            showAjaxMsg("你确定要删除这些数据？",true,function(){
                $.ajax({
                    type:"POST",
                    url:"DININGSYS/role/delRole",
                    data:{roleIds:roleids},
                    dataType:"JSON",
                    success:function (data) {
                        if(data.success){
                            closeAjaxShowMsg();
                            sysRole.roleTable._fnAjaxUpdate();
                        }
                    }
                })
            })

        });
        $(".roleLockBtn").live('click',function () {

            var roleID = $(this).attr("operaID");
            var state = ($(this).attr("rolestate")=='1')?'0':'1'; //用户状态，正常->锁定，锁定->正常

            $('body').modalmanager('loading');
            
            var msg = (state=='0'?'锁定':'解锁');
            showAjaxMsg("请确认是否要"+msg+"该角色",true,function(){
                $.ajax({
                    type:"POST",
                    url:"DININGSYS/role/lockRole",
                    data:{id:roleID,state:state},
                    dataType:"JSON",
                    success:function (data) {
                        if(data.success){
                            closeAjaxShowMsg();
                            sysRole.roleTable._fnAjaxUpdate();
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
    editRoleQX : function(){
        var roleID = $("#roleID").val();
        var QXTree =  $.fn.zTree.getZTreeObj("roleTreeDemo");
        var selectNodes= QXTree.getCheckedNodes(true);
        var QXCheckNodesID = [];
        $(selectNodes).each(function () {
            QXCheckNodesID.push(this.id);
        });
        
        /*role menu edit ajax*/
        $.ajax({
            type:'POST',
            url:'DININGSYS/role/editRole',
            data:{roleID:roleID,QXCheckNodesID:QXCheckNodesID.toString()},
            dataType:'json',
            success:function (data) {
                if(data.success)
                    closeModal("role_ajax_modal");
                else
                    showAjaxMsg("操作失败")
            }
        })
    },
    addRole : function(){
        if($("#roleAddForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/role/addRoleBaseInfo",
                data:$("#roleAddForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("role_ajax_modal");
                        sysRole.reloadTable();
                    }
                }
            })
        }
    },
    editRole : function(){
        if($("#roleEditForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/role/editRoleBaseInfo",
                data:$("#roleEditForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("role_ajax_modal");
                        sysRole.reloadTable();
                    }else
                        showAjaxMsg("操作失败")
                }
            })
        }
    },
    delRole : function(){
        if($("#roleEditForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/role/editRoleBaseInfo",
                data:$("#roleEditForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("role_ajax_modal");
                        sysRole.reloadTable();
                    }else
                        showAjaxMsg("操作失败")
                }
            })
        }
    },
    lockRole : function(){
        if($("#roleEditForm").valid()){
            $.ajax({
                type:"POST",
                url:"DININGSYS/role/editRoleBaseInfo",
                data:$("#roleEditForm").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        closeModal("role_ajax_modal");
                        sysRole.reloadTable();
                    }else
                        showAjaxMsg("操作失败")
                }
            })
        }
    },
    /*ztree*/
    initZtree : function(zNodes){
        var treeObj = $.fn.zTree.init($("#roleTreeDemo"), setting, zNodes);
        treeObj.expandAll(true);
        var nodes = treeObj.getNodes();
        treeObj.selectNode(nodes[0]);
    },
    getFont : function(treeId, node) {
        return node.font ? node.font : {};
    },
    showIconForTree : function (treeId, treeNode) {
        return treeNode.menuType=='default'; //角色选择具体权限中，代表功能权限的数据为blue,并且不显示图标
    },
    /*END*/
    
    /*table reload*/
    reloadTable : function () {
        sysRole.roleTable._fnAjaxUpdate();
    }
};

var setting = {
    view: {
        fontCss: sysRole.getFont,
        showIcon: sysRole.showIconForTree
    },
    check: {
        enable: true,
        chkboxType :   { "Y" : "ps", "N" : "s" }
    },
    data: {
        key: {
            name: "menuName",
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: 0
        }
    }
};