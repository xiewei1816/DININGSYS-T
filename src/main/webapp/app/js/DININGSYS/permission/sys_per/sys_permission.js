/**
 * Created by zhshuo on 2016-10-28.
 */
var sysPer = function () {
    var sys_permisson_tree,sys_menu_tree;

    function treeInit() {
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
            callback: {
                onClick: nodeClick,
                onAsyncSuccess : loadComplete
            },
            async: {
                enable: true,
                url:"DININGSYS/permission/sysPermissions/getSysTree"
            }
        };
        
        function nodeClick(event, treeId, treeNode) {
            var userUrl = "getSysPerUserData",
                roleUrl = "getSysPerRoleData",
                table = $("#sr_per_table");

            if(treeNode.id != 4 && treeNode.id != 5){
                $(".userBtn").removeAttr("disabled");
                $(".roleBtn").attr("disabled","disabled");
                table.showCol(['empCode','empName']);
                table.hideCol(['roleName','description']);
                table.jqGrid("setGridParam",{url:"DININGSYS/permission/sysPermissions/"+userUrl}).trigger("reloadGrid");
            }else{
                $(".roleBtn").removeAttr("disabled");
                $(".userBtn").attr("disabled","disabled");
                table.hideCol (['empCode','empName']);
                table.showCol(['roleName','description']);
                table.jqGrid("setGridParam",{url:"DININGSYS/permission/sysPermissions/"+roleUrl}).trigger("reloadGrid");
            }

        }

        function loadComplete() {
            sys_permisson_tree.expandAll(true);
            var nodes = sys_permisson_tree.getNodes();
            if (nodes.length>0) {
                sys_permisson_tree.selectNode(nodes[0]);
            }
            $(".userBtn").removeAttr("disabled");
            $(".roleBtn").attr("disabled","disabled");
        }

        sys_permisson_tree = $.fn.zTree.init($("#sysPermisson"), setting);
    }


    function setTableWidthAndHeight() {
        var needh = $("#tabelWidth").outerHeight() + $(".btn-toolbar").outerHeight() + 80;
        $("#sr_per_table").jqGrid("setGridWidth",$("#tabelWidth").width(),true);
        $("#sr_per_table").jqGrid("setGridHeight",$(window).height() - needh,true);
    }

    function pageInit() {
        $("#sr_per_table").jqGrid({
            url: "DININGSYS/permission/sysPermissions/getSysPerUserData",
            datatype: "JSON",
            mtype: "GET",
            colNames: ["id","编号","名称","权限组名称","描述","所属分店","是否以分组","集团用户"],
            colModel: [
                { name: "id",hidden:true},
                { name: "empCode" },
                { name: "empName"},
                { name: "roleName",hidden:true},
                { name: "description",hidden:true},
                { name: "empOrganizationName" },
                { name: "isOrguserFlag"},
                { name: "isOrguserFlag"}
            ],
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap'
        });

        setTableWidthAndHeight();
    }

    function BtnInit(){
        $("#editBtn").click(function () {
            var nodes = sys_permisson_tree.getSelectedNodes();
            if(nodes.length > 0){
                var selectNode = nodes[0],
                    perTable = $("#sr_per_table"),
                    rowId = perTable.getGridParam("selrow");

                if(!rowId){
                    layer.alert('请在右侧表格中选择一条！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    return;
                }

                var tableDataId = perTable.jqGrid('getCell',rowId,'id');

                if(selectNode.id == 1 || selectNode.id == 2 || selectNode.id == 3){
                    $.get('DININGSYS/permission/sysPermissions/userRoleIndex/'+tableDataId,function (str) {
                        sysPerLayerOpen('用户权限维护-所属组',str,'user_choose_role',tableDataId);
                    })
                }else{
                    $.get('DININGSYS/permission/sysPermissions/roleUserIndex',{id:tableDataId},function (str) {
                        sysPerLayerOpen('修改权限组',str,'role_choose_user',tableDataId);
                    })
                }
            }

        });

        $("#roleAdd").click(function () {
            $.get('DININGSYS/permission/sysPermissions/roleUserIndex',function (str) {
                sysPerLayerOpen('新增权限组',str,'role_choose_user',null);
            })
        });

        $("#delBtn").click(function () {
            var nodes = sys_permisson_tree.getSelectedNodes();
            if(nodes.length > 0){
                var selectNode = nodes[0],
                    perTable = $("#sr_per_table"),
                    rowId = perTable.getGridParam("selrow");

                if(!rowId){
                    layer.alert('请在右侧表格中选择一条！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    return;
                }

                var tableDataId = perTable.jqGrid('getCell',rowId,'id');

                if(selectNode.id == 1 || selectNode.id == 2 || selectNode.id == 3){
                    sysPerDelLayer(tableDataId,1);
                }else{
                    sysPerDelLayer(tableDataId,2);
                }
            }
        });

        $("#flushBtn").click(function () {
            $("#sr_per_table").trigger("reloadGrid");
            //sys_permisson_tree.reAsyncChildNodes(null, "refresh");
        });

        $("#permissonBtn").click(function () {
            var perTable = $("#sr_per_table"),
                rowId = perTable.getGridParam("selrow");

            if(!rowId){
                layer.alert('请在右侧表格中选择一条！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var tableDataId = perTable.jqGrid('getCell',rowId,'id');

            $.get('DININGSYS/permission/sysPermissions/rolePermisson/'+tableDataId,function (dataStr) {
                var userRoleIndex = layer.open({
                    type: 1,
                    title:"权限维护",
                    skin: 'layui-layer-rim',
                    area: ['800px', '700px'],
                    content: dataStr,
                    btn:['保存','关闭'],
                    yes:function () {
                        var roleID = $("#roleId").val(),
                            selectNodes= sys_menu_tree.getCheckedNodes(true),
                            QXCheckNodesID = [];
                        $(selectNodes).each(function () {
                            QXCheckNodesID.push(this.id);
                        });

                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/permission/sysPermissions/editRoleMenu',
                            dataType:'JSON',
                            data:{roleId:roleID,QXCheckNodesID:QXCheckNodesID.toString()},
                            success:function(data){
                                if(data.success){
                                    layer.close(userRoleIndex);id
                                }
                            }
                        })
                    }
                });
            });
        });

        $("#userPwd").click(function () {
            var perTable = $("#sr_per_table"),
                rowId = perTable.getGridParam("selrow");

            if(!rowId){
                layer.alert('请在右侧表格中选择一条！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var tableDataId = perTable.jqGrid('getCell',rowId,'id');

            $.get('DININGSYS/permission/sysPermissions/userPwdPage/'+tableDataId,function (str) {
                var userRoleIndex = layer.open({
                    type: 1,
                    title:'密码修改',
                    skin: 'layui-layer-rim',
                    area: ['450px', '350px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        var newPwd = sha256_digest($("#newPwd").val());
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/permission/sysPermissions/updateUserPwd',
                            data:{id:$("#id").val(),newPwd:newPwd},
                            dataType:'JSON',
                            success:function(data){
                                if(data.success){
                                    layer.close(userRoleIndex);
                                }
                            }
                        })
                    }
                });
            })
        })

    }

    function userRoleInit() {
        multiSelectInit('user_choose_role');
    }

    function roleUserInit() {
        multiSelectInit('role_choose_user');
    }

    function initRolePermissionPage() {
        var sysMenuSetting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: 0
                },
                key: {
                    name : "menuName"
                }
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType:  { "Y" : "ps", "N" : "ps" }
            },
            view: {
                dblClickExpand: false,
                selectedMulti: false,
                fontCss: getFont,
            },
            callback: {
                onAsyncSuccess : loadComplete
            },
            async: {
                enable: true,
                url:"DININGSYS/permission/sysPermissions/getRoleMenus/"+$("#roleId").val()
            }
        };
        
        function getFont(treeId, node){
            return node.font ? node.font : {};
        }

        function loadComplete() {
            sys_menu_tree.expandAll(true);
        }
        
        sys_menu_tree = $.fn.zTree.init($("#sysMenuTree"), sysMenuSetting);
    }

    function multiSelectInit(selectId){
        var selectObj = "#"+selectId;

        $(selectObj).multiSelect({
            selectableHeader: "<input type='text' class='form-control search-input' autocomplete='off' placeholder='搜索可选...'>",
            selectionHeader: "<input type='text' class='form-control search-input' autocomplete='off' placeholder='搜索已选...'>",
            afterInit: function (ms) {
                var that = this,
                    $selectableSearch = that.$selectableUl.prev(),
                    $selectionSearch = that.$selectionUl.prev(),
                    selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable:not(.ms-selected)',
                    selectionSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selection.ms-selected';

                that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
                    .on('keydown', function (e) {
                        if (e.which === 40) {
                            that.$selectableUl.focus();
                            return false;
                        }
                    });

                that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
                    .on('keydown', function (e) {
                        if (e.which == 40) {
                            that.$selectionUl.focus();
                            return false;
                        }
                    });
            },
            afterSelect: function () {
                this.qs1.cache();
                this.qs2.cache();
            },
            afterDeselect: function () {
                this.qs1.cache();
                this.qs2.cache();
            }
        });
    }

    function sysPerLayerOpen(title,dataStr,multiSelectId){
        var userRoleIndex = layer.open({
            type: 1,
            title:title,
            skin: 'layui-layer-rim',
            area: ['650px', '450px'],
            content: dataStr,
            btn:['保存','关闭'],
            yes:function () {
                var ajaxUrl,param;
                multiSelcetSure(multiSelectId);
                if(multiSelectId == 'user_choose_role'){
                    /*用户权限维护*/
                    ajaxUrl = 'DININGSYS/permission/sysPermissions/userRoleEdit';
                    param = $("#userRoleForm").serialize();
                }else{
                    /*修改权限组*/
                    ajaxUrl = 'DININGSYS/permission/sysPermissions/roleUserEdit';
                    param = $("#roleUserForm").serialize();
                }

                $.ajax({
                    type:'POST',
                    url:ajaxUrl,
                    data:param,
                    dataType:'JSON',
                    success:function(data){
                        if(data.success){
                            $("#sr_per_table").trigger("reloadGrid");
                            //sys_permisson_tree.reAsyncChildNodes(null, "refresh");
                            layer.close(userRoleIndex);
                        }
                    }
                })
            }
        });
    }

    /*用户和权限组的删除*/
    function sysPerDelLayer(selectId,type){
        layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
            var url;
            if(type == 1){
                url = "DININGSYS/permission/sysPermissions/deleteUser/"+selectId;
            }else{
                url = "DININGSYS/permission/sysPermissions/deleteRole/"+selectId;
            }
            $.ajax({
                type:'POST',
                url:url,
                dataType:'JSON',
                success:function(data){
                    if(data.success){
                        $("#sr_per_table").trigger("reloadGrid");
                    }
                }
            });
            layer.close(index);
        });
    }

    function multiSelcetSure(selectId){
        var selectObj = "#"+selectId;
        var chooseData = $(selectObj).next().find("div").filter(".ms-selection").find("li");
        var chooseDataArray = [];
        $(chooseData).each(function () {
            if ($(this).hasClass("ms-selected")) {
                var liIndex = $(this).index();
                var val = $(selectObj).find("option").eq(liIndex).val();
                chooseDataArray.push(val);
            }
        });
        if(selectId == "role_choose_user"){
            $("#userIds").val(chooseDataArray.toString());
        }else{
            $("#roleIds").val(chooseDataArray.toString());
        }
    }

    return {
        treeInit:function () {
            treeInit();
        },
        pageInit:function () {
            pageInit();
            BtnInit();
        },
        userRoleInit:function () {
            userRoleInit();
        },
        roleUserInit:function () {
            roleUserInit();
        },
        initRolePermissionPage:function () {
            initRolePermissionPage();
        }
    }

}();
