/**
 * Created by mrren on 2016-07-25.
 */
var  sysUser = {
    userTable : null,
    userRoleTable : null,
    //checkbox init
    initCheckBox: function () {
        jQuery('#user_table .group-checkable').live('change',function () {
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

        jQuery('#user_table tbody tr td .checkboxes').live('change',function(){
            $(this).parents('tr').toggleClass("active");
        });
    },
    init: function () {
        //解决当JS重新加载，事件注册多次的情况
        $(".userInfoBtn").die("click");
        $(".userEditBtn").die("click");
        $(".userLockBtn").die("click");
        $(".userDelBtn").die("click");
        $(".userRoleEditBtn").die("click");

        if (!jQuery().dataTable) {
            return;
        }

        sysUser.userTable = $('#user_table').dataTable({
           "sDom" : "<'row'<'col-md-6 col-sm-12'l><'col-md-12 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",
            "bPaginate":true,
            "bProcessing": true,
            "bServerSide": true,
            "sAjaxSource": "DININGSYS/user/getData",
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
                    {"mData":null},
                    { "mData": "id"},
                    { "mData": "username"},
                    { "mData": "nickname"},
                    { "mData": "state"},
                    { "mData": "createTime"},
                    { "mData": "email"}
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
                    return "<input type='checkbox' class='checkboxes' value='"+operationID+"' userstate='"+state+"' />";
                }},
                {'aTargets': [3],"bVisible":false},
                {'aTargets': [6],"bVisible":false},
                {'aTargets': [4],mRender:function (data, type, full) {
                    if(full['state']=='normal'){
                        return "正常";
                    }else {
                        return "锁定";
                    }
                }},
                {'aTargets': [7],"mData":null,"bSortable":false,"bSearchable":false,mRender:function (data, type, full) {
                    var operationID = full['id'];
                    return "<a href='javascript:void(0)' operaID='"+operationID+"' class='btn btn-xs default userRoleEditBtn'>角色编辑 <i class='fa fa-user'></i></a>";
                }}
            ]
        });

        sysUser.initCheckBox();

        jQuery('#user_table .dataTables_filter input').addClass("form-control input-medium"); // modify table search input
        jQuery('#user_table .dataTables_length select').addClass("form-control input-small"); // modify table per page dropdown


        sysUser.initUserAjaxModal();
        sysUser.initUserLockAndDel();

    },
    initUserAjaxModal : function (){
        $.fn.modal.defaults.spinner = $.fn.modalmanager.defaults.spinner =
            '<div class="loading-spinner" style="width: 200px; margin-left: -100px;">' +
            '<div class="progress progress-striped active">' +
            '<div class="progress-bar" style="width: 100%;"></div>' +
            '</div>' +
            '</div>';

        $.fn.modalmanager.defaults.resize = true;

        var $modal = $('#user_ajax_modal');

        //绑定按钮事件
        //info modal
        $('.userInfoBtn').on('click', function(){

            var userId = "";

            var checkedTr = $('#user_table tbody tr td').find("input:checked");
            if(checkedTr.length!=1){
                showAjaxMsg("请选择一条数据");
                return;
            }
            userId = $(checkedTr).val();

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/user/showUserInfo/'+userId, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });
        //修改modal
        $('.userEditBtn').on('click', function(){
            var userId = "";

            var checkedTr = $('#user_table tbody tr td').find("input:checked");
            if(checkedTr.length!=1){
                showAjaxMsg("请选择一条数据");
                return;
            }
            userId = $(checkedTr).val();

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/user/showUserEdit/'+userId, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $('.userTopAdd').on('click', function(){
            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/user/addPage', '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $('.userRoleEditBtn').live('click', function(){

            var userId = $(this).attr('operaID');

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/user/showUserRoleEdit/'+userId, '', function(){
                    $modal.modal();
                });
            }, 1000);
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
    //修改
    updateUser : function () {
        if($("#userEditForm").valid()){
            if($("#checkUserNameClassId").hasClass("is-error")){
                showAjaxMsg("该用户名已存在，请重新输入。");
                return;
            }
            $.ajax({
                type:"POST",
                url:"DININGSYS/user/editUser",
                data:$("#userEditForm").serialize(),
                dataType:"json",
                success:function (data) {
                    if(data.success){
                        console.info("updateUser-success");
                        sysUser.reloadTable();
                    }else{
                        showAjaxMsg("修改失败，请重新操作。");
                    }
                },
                error:function () {
                    showAjaxMsg("error:修改失败，请重新操作。");
                }
            })
        }
    },
    //新增
    insertUser : function () {
        if($("#userAddForm").valid()){
            if($("#checkedRoleLenthID").html() == "0"){
                showAjaxMsg("还未对需要添加的用户分配角色！");
                return;
            }
            if($("#showUserDeptP").html() == "当前未选择部门"){
                showAjaxMsg("还未对需要添加的用户选择部门！");
                return;
            }
            if($("#checkUserNameClassId").hasClass("is-error")){
                showAjaxMsg("该用户名已存在，请重新输入。");
                return;
            }
            $.ajax({
                type:"POST",
                url:"DININGSYS/user/insertUser",
                data:$("#userAddForm").serialize(),
                dataType:"json",
                success:function (data) {
                    if(data.success){
                        console.info("insertUser-success");
                        sysUser.reloadTable();
                    }else
                        showAjaxMsg("error:添加失败，请重新操作。");
                }
            })
        }
    },
    //table reload
    reloadTable:function () {
        $("#user_ajax_modal").modal("hide");
        sysUser.userTable._fnAjaxUpdate();
    },
    initUserLockAndDel: function(){
        //解锁、锁定用户
        $('.userLockBtn').on('click',function(){
            var state,userID;
            var checkedTr = $('#user_table tbody tr td').find("input:checked");
            if(checkedTr.length!=1){
                showAjaxMsg("请选择一条数据");
                return;
            }
            userID = $(checkedTr).val();
            state = ($(checkedTr).attr("userstate")=='normal')?'locked':'normal'; //用户状态，正常->锁定，锁定->正常

            var msg = (state=='normal'?'解锁':'锁定');
            showAjaxMsg("请确认是否要"+msg+"该用户",true,function(){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/user/lockUser',
                    data:{userIDs:userID,state:state},
                    dataType:'json',
                    success:function (data) {
                        if(data.success){
                            closeAjaxShowMsg();
                            sysUser.reloadTable();
                        }else {
                            alert("操作失败");
                        }
                    }
                })
            });
        });

        //删除
        $(".userDelBtn").on('click',function () {
            var userID;
            var checkedTr = $('#user_table tbody tr td').find("input:checked");
            if(checkedTr.length<1){
                showAjaxMsg("请至少选择一条数据");
                return;
            }
            var ids = [];
            $(checkedTr).each(function () {
                ids.push($(this).val());
            });
            userID = ids.toString();

            showAjaxMsg("请确认是否要删除该用户",true,function(){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/user/delUsers',
                    data:{userIDs:userID},
                    dataType:'json',
                    success:function (data) {
                        if(data.success){
                            closeAjaxShowMsg();
                            sysUser.reloadTable();
                        }else {
                            alert("操作失败");
                        }
                    }
                })
            });
        });

        //重置密码
        $(".userResetBtn").on('click',function () {
            var userID;
            var checkedTr = $('#user_table tbody tr td').find("input:checked");
            if(checkedTr.length<1){
                showAjaxMsg("请至少选择一条数据");
                return;
            }
            var ids = [];
            $(checkedTr).each(function () {
                ids.push($(this).val());
            });
            userID = ids.toString();

            showAjaxMsg("请确认是否要重置用户密码",true,function(){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/user/resetPassword',
                    data:{userIDs:userID},
                    dataType:'json',
                    success:function (data) {
                        if(data.success){
                            closeAjaxShowMsg();
                            sysUser.reloadTable();
                        }else {
                            alert("操作失败");
                        }
                    }
                })
            });
        })
    },
    //用户选择角色的表格
    initUserRoleTable : function () {
        if (!jQuery().dataTable) {
            return;
        }

        sysUser.userRoleTable = $('#user_role_table').dataTable({
            "aoColumns": [
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false }
            ],
            "aLengthMenu": [
                [5, 15, 20, -1],
                [5, 15, 20, "All"]
            ],
            "iDisplayLength": 5,
            "sPaginationType": "bootstrap",
            "oLanguage" : {
                "sUrl":          "app/js/DININGSYS/system/pageI18N/zh_CN.json"
            },
            "aoColumnDefs": [{
                'bSortable': false,
                'aTargets': [0]
            }
            ]
        });

        jQuery('#user_role_table .group-checkable').change(function () {
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

        jQuery('#user_role_table tbody tr .checkboxes').change(function(){
            $(this).parents('tr').toggleClass("active");
        });

        jQuery('#user_role_table_wrapper .dataTables_filter input').addClass("form-control input-medium"); // modify table search input
        jQuery('#user_role_table_wrapper .dataTables_length select').addClass("form-control input-xsmall"); // modify table per page dropdown
    },
    saveUserRoles : function (id) {
        var checkedRoleIDs = [];
        var checkedRole = $("#user_role_table tbody tr input:checked");
        $(checkedRole).each(function () {
            checkedRoleIDs.push($(this).val());
        });
        if(!id){
            $("#checkedRoleLenthID").text(checkedRole.length);
            $("#userRoleIDs").val(checkedRoleIDs.toString());
            closeModal("userRoleChoose");
            return;
        }

        $.ajax({
            type:'POST',
            url:'DININGSYS/user/userRoleEdit',
            data:{userId:id,roleIds:checkedRoleIDs.toString()},
            dataType:'json',
            success:function (data) {
                if(data.success) {
                    closeModal("user_ajax_modal");
                }else
                    showAjaxMsg("error:操作失败，请重试");
            }
        })



    },

    /*2016年8月4日09:59:07  用户管理新增部门*/
    initUserDeptTree: function(zNodes){
        var deptTree = $.fn.zTree.init($("#userDeptTree"), setting, zNodes['allBdOrgan']);
        if($("#userDeptID").val() != null){
            var selectNode = deptTree.getNodeByParam("id",$("#userDeptID").val(),null);
            deptTree.selectNode(selectNode);
        }
    },
    saveUserDept:function () {
        var treeObj = $.fn.zTree.getZTreeObj("userDeptTree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length > 0){
            $("#showUserDeptP").text(nodes[0].organName);
            $("#userDeptID").val(nodes[0].id);
            closeModal("userDeptChoose");
        }
    },
    checkUserNameExistAdd:function (obj,type) {
        var newName = $("#newName").val();
        var oldName = $("#oldName").val();

        var dataParam = null;

        var divObj = $(obj).parents("div").filter(".form-group");
        var helpI = $(obj).parent().find("i");
        if(helpI.length > 0){
            helpI.remove();
        }

        if(type == 'edit' && (newName == oldName)){
            return;
        }else if(type == 'edit' && (newName != oldName)){
            dataParam = {name:$(obj).val().trim(),type:'edit',loginId:$("#userId").val()};
        }else if(type == 'add'){
            dataParam = {name:$(obj).val().trim()};
        }
            
        if($(obj).val().trim() != null && $(obj).val().trim() != ""){
            $(obj).toggleClass("spinner");
            $.ajax({
                type:"POST",
                url:"DININGSYS/checkUserNameExist",
                data:dataParam,
                dataType:"JSON",
                success:function (data) {
                    if(data.success){
                        if(data.successMsg == 'exist'){
                            $(obj).toggleClass("spinner");
                            if($(divObj).hasClass("is-success")){
                                $(divObj).removeClass("is-success")
                            }
                            if(!$(divObj).hasClass("is-error")){
                                $(divObj).addClass("is-error");
                            }
                            $(obj).before("<i class='fa fa-exclamation tooltips' data-container='body'></i>");
                        }else{
                            $(obj).toggleClass("spinner");
                            if($(divObj).hasClass("is-error")){
                                $(divObj).removeClass("is-error");
                            }
                            if(!$(divObj).hasClass("is-success")){
                                $(divObj).addClass("is-success")
                            }
                            $(obj).before("<i class='fa fa-check tooltips' data-container='body'></i>");
                        }
                    }
                }
            })
        }else{
            var helpI = $(obj).parent().find("i");
            if(helpI.length > 0){
                helpI.remove();
            }
            $(divObj).removeClass("is-success");
            $(divObj).removeClass("is-error");
            $(obj).removeClass("spinner");
        }
    }
};

var setting = {
    data: {
        key: {
            name:"organName"
        },
        simpleData: {
            enable: true,
            idKey:"id",
            pIdKey:"parentId",
            rootPId:"-1"
        }
    },
    view: {
        selectedMulti: false
    }
};