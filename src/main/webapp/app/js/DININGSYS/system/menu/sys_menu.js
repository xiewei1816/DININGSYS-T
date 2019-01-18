/**
 * Created by mrren on 2016-07-21.
 */
var sysMenu = {
    IDMark_A : "_a",
    newCount : 1,
    getMenuData : function(){
         $.ajax({
             type: "POST",
             url: "DININGSYS/menu/getMenuData",
             dataType:"json",
             contentType: 'charset/utf-8',
             async:false,
             success: function (data) {
                 data = eval('(' + data + ')');
                 sysMenu.initZtree(data.menuData);
             },
             error: function () {
                console.error("菜单数据加载异常。");
             }
         })
    },
    addHoverDom:function (treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_a");
        $(sObj).find("span").each(function () {
            if($(this).index()>1){
                $(this).show();
            }
        })
    },
    removeHoverDom : function (treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_a");
        $(sObj).find("span").each(function () {
            if($(this).index()>1){
                $(this).hide();
            }
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
    
        var $modal = $('#ajax-modal');

        $('.menuAddBtn').on('click', function(){

            var menuId = $(this).attr('menuid');

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/menu/showMenuAddPage?id='+menuId, '', function(){
                    $modal.modal();
                });
            }, 1000);
        });

        $('.menuEditBtn').on('click', function(){

            var menuId = $(this).attr('menuid');

            $('body').modalmanager('loading');

            setTimeout(function(){
                $modal.load('DININGSYS/menu/showMenuEditPage?id='+menuId, '', function(){
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
    addDiyDom:function (treeId, treeNode) {
        var aObj = $("#" + treeNode.tId + sysMenu.IDMark_A);
        if(treeNode.id == 1){
            var editStr = "<span style='display: none' menuid='"+treeNode.id+"'  data-toggle='modal' class='button add  menuAddBtn' id='diyBtn_add_"+treeNode.id+ "' title='添加菜单' onfocus='this.blur();'>";
            aObj.append(editStr);
        }
        if(treeNode.menuType == 'default' && treeNode.id != 1){
            var editStr = "<span style='display: none' menuid='"+treeNode.id+"'  data-toggle='modal' class='button add  menuAddBtn' id='diyBtn_add_"+treeNode.id+ "' title='添加菜单' onfocus='this.blur();'>" +
                "</span><span menuid='"+treeNode.id+"' data-toggle='modal' style='display: none' title='修改' id='diyBtn_edit_" +treeNode.id+ "' class='button edit menuEditBtn'></span>" +
                "<span style='display: none' onclick='sysMenu.delMenuId(\""+treeNode.id+"\")' data-id='"+treeNode.id+"' title='删除' href='#delMenu' data-toggle='modal' id='diyBtn_del_" +treeNode.id+ "' class='button remove delMenu'></span>";
            aObj.append(editStr);
        }
        if(treeNode.menuType == 'button'){
            var editStr = "</span><span menuid='"+treeNode.id+"' data-toggle='modal' style='display: none' title='修改' id='diyBtn_edit_" +treeNode.id+ "' class='button edit menuEditBtn'></span>" +
                "<span style='display: none' onclick='sysMenu.delMenuId(\""+treeNode.id+"\")' data-id='"+treeNode.id+"' title='删除' href='#delMenu' data-toggle='modal' id='diyBtn_del_" +treeNode.id+ "' class='button remove delMenu'></span>";
            aObj.append(editStr);
        }
    },
    addMenu : function(){
        if($("#menuTypeEditID").val()=='button'){
            $("#menuAddUrl").val("javascript:void(0)");
        }
        if($("#menuAddForm").valid()){
            $.ajax({
                type:'POST',
                url:'DININGSYS/menu/addMenu',
                data:$("#menuAddForm").serialize(),
                dataType:'json',
                success:function (data) {
                    if(data.success){
                        console.info("addMenu-success");
                        //bootstrap modal close
                        var $modal = $('#ajax-modal');
                        sysMenu.reloadZtree();
                        $modal.modal('hide');
                    }else {
                        console.info("出错:"+data.errorMsg)
                    }
                },
                error:function () {
                    console.info("出错:"+data.errorMsg)
                }
            })
        }
    },
    updateMenu : function () {
        if($("#menuEditForm").valid()) {
            $.ajax({
                type: 'POST',
                url: 'DININGSYS/menu/editMenu',
                data: $("#menuEditForm").serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        console.info("updateMenu-success");
                        var $modal = $('#ajax-modal');
                        sysMenu.reloadZtree();
                        $modal.modal('hide');
                    } else {
                        console.info("出错:"+data.errorMsg)
                    }
                },
                error: function () {
                    console.info("出错:"+data.errorMsg)
                }
            })
        }

    },
    //删除ID设置
    delMenuId : function (id) {
        $("#menuDelId").val(id);
    },
    delMenu : function(){
        var $modal = $('#delMenu');
        var delMenuId = $("#menuDelId").val();
        $.ajax({
            type:'POST',
            url:'DININGSYS/menu/delMenu',
            data:{id:delMenuId},
            dataType:'json',
            success:function (data) {
                if(data.success){
                    console.info("delMenu-success");
                    sysMenu.reloadZtree();
                    $modal.modal('hide');
                }else {
                    if(data.errorMsg){
                        $modal.modal('hide');
                        showAjaxMsg(data.errorMsg)
                    }
                }
            },
            error:function () {
                console.info("出错:"+data.errorMsg)
            }
        })
    },
    checkMenuClick : function(treeId, treeNode, clickFlag){
      return treeNode.id != 1;
    },
    menuClick : function (event, treeId, treeNode) {
        sysMenu.showMenuInfo(treeNode);
    },
    showMenuInfo : function (treeNode) {
        if(treeNode.menuState == 'enable'){
            $("#enableBtnId").hide();
            $("#disableBtnID").show();
        }else {
            $("#enableBtnId").show();
            $("#disableBtnID").hide();
        }
        $("#menuNameID").text(treeNode.menuName);
        $("#menuModifyStateID").val(treeNode.id);
        $("#menuID").text(treeNode.id);
        $("#menuParentNameID").text(treeNode.parentName);
        $("#menuURLID").text(treeNode.menuUrl);
        $("#menuCodeID").text(treeNode.menuCode);
        $("#menuTypeID").text(treeNode.menuType=='default'?'默认':'按钮');
        $("#menuStateID").text(treeNode.menuState=='enable'?'启用':'禁用');

        if(treeNode.menuType=='default'){
            $(".menuBtnClass").text("菜单");
            $("#menuUrl").show();
        }else {
            $(".menuBtnClass").text("按钮");
            $("#menuUrl").hide();
        }
    },
    modifyMenuState : function(state){
        var modifyID = $("#menuModifyStateID").val();
        $.ajax({
            type:'POST',
            url:'DININGSYS/menu/modifyMenuState',
            data:{id:modifyID,state:state},
            dataType:'json',
            success:function (data) {
                if(data.success){
                    console.info("modifyMenuState-success");
                    sysMenu.reloadZtree(modifyID);
                }else {
                    console.info("出错:"+data.errorMsg)
                }
            },
            error:function () {
                console.info("出错:"+data.errorMsg)
            }
        })

    },
    changeInput : function (obj) {
        var val = $(obj).val();
        if(val == 'default'){
            $(".menuBtnEditClass").text("菜单");
            $("#menuBtnEditUrl").show();
            $("#menuAddUrl").val("");
        }else{
            $(".menuBtnEditClass").text("按钮");
            $("#menuBtnEditUrl").hide();
            $("#menuAddUrl").val("");
        }
    },
    /**
     * 重新加载Ztree
     */
    reloadZtree : function(selectID){
        $.fn.zTree.destroy("treeDemo");
        sysMenu.getMenuData();
        sysMenu.initAddOrEditModal();
        if(selectID){
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var selectNode = treeObj.getNodeByParam("id",selectID,null);
            treeObj.selectNode(selectNode);
            sysMenu.showMenuInfo(selectNode);
        }
    },
    initZtree : function(zNodes){
        var treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        treeObj.expandAll(true);

        var selectNode = treeObj.getNodeByParam("id",2,null);
        treeObj.selectNode(selectNode);
        sysMenu.showMenuInfo(selectNode);
    },
    getFont : function(treeId, node) {
        return node.font ? node.font : {};
    },
    showIconForTree : function (treeId, treeNode) {
        return treeNode.menuType=='default'; //角色选择具体权限中，代表功能权限的数据为blue,并且不显示图标
    }
};

var setting = {
    view: {
        addHoverDom: sysMenu.addHoverDom,
        removeHoverDom: sysMenu.removeHoverDom,
        addDiyDom: sysMenu.addDiyDom,
        fontCss: sysMenu.getFont,
        showIcon: sysMenu.showIconForTree
    },
    callback: {
        beforeClick: sysMenu.checkMenuClick,
        onClick: sysMenu.menuClick
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