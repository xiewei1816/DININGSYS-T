/**
 * Created by zhshuo on 2016-11-04.
 */
var busi_per = function () {

    var busi_per_tree,sysPerBackgroudZtree,sysPerDeskZtree,pageLoadLayerIndex;

    function tabFlush() {
        var currentLi = $("#myTab").find("li").filter(".active");
        currentLi.trigger("click");
    }

    function backAndDeskTreeFlush(){
        sysPerBackgroudZtree.reAsyncChildNodes(null, "refresh");
    }

    function pageInit() {
        //职务树的添加
        var busiTree = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "cParent",
                    rootPId: 0
                },
                key: {
                    name : "cName"
                }
            },
            view: {
                dblClickExpand: false,
                selectedMulti: false
            },
            callback: {
                beforeAsync: function () {
                    pageLoadLayerIndex = layer.load(1, {
                        shade: [0.6,'#fff']
                    });
                },
                beforeClick: busiPerBeforeClick,
                onClick: nodeClick,
                onAsyncSuccess : loadComplete,
            },
            async: {
                enable: true,
                url:"DININGSYS/permission/businessPermisson/getAllPost"
            }
        };

        busi_per_tree = $.fn.zTree.init($("#businessPostTree"), busiTree);

        function busiPerBeforeClick(treeId, treeNode, clickFlag) {
            return (treeNode.id != 16);
        }
        
        function nodeClick(event, treeId, treeNode) {
            if(busi_per_tree){
                pageLoadLayerIndex = layer.load(1, {
                    shade: [0.6,'#fff']
                });
            }

            $("#hideDutiesCode").val(treeNode.cCode);
            $("#postName").html(treeNode.cName);
            tabFlush();
            backAndDeskTreeFlush();
        }

        function loadComplete(event, treeId, treeNode, msg) {
            busi_per_tree.expandAll(true);
            var allPostNode = busi_per_tree.getNodeByParam("id",16,null).children;
            if(allPostNode.length > 0){
                busi_per_tree.selectNode(allPostNode[0]);
                $("#hideDutiesCode").val(allPostNode[0]['cCode']);
                var firstNode = busi_per_tree.getSelectedNodes()[0],
                    zwCode = firstNode['cCode'];
                //加载第一项
                $.get('DININGSYS/permission/businessPermisson/busPage/1',{'zwCode':zwCode},function (pageStr) {
                    $("#normal").html(pageStr);
                });

                $("#postName").html(firstNode.cName);
            }else{
                $("#noPostHideDiv").hide();
            }
            backgroudAndDeskMenuInit();
        }

        //tab点击事件
        //直接点击tab或者点击树的职务，tab刷新函数
        $("#myTab li").click(function () {
            var liObjId = $(this).attr("id"),
                ajaxUrl = "DININGSYS/permission/businessPermisson/busPage/",
                selectNode = busi_per_tree.getSelectedNodes()[0],
                zwCode = selectNode['cCode'],
                contenObj = "";
            if(liObjId == "generalBus"){
                ajaxUrl += "1";contenObj = "#normal";
            }else if(liObjId == "discountBus"){
                ajaxUrl += "2";contenObj = "#discount";
            }else if(liObjId == "freeBus"){
                ajaxUrl += "3";contenObj = "#free";
            }else if(liObjId == "chargebackBus"){
                ajaxUrl += "4";contenObj = "#chargeback";
            }else{
                ajaxUrl += "5";contenObj = "#variablePrice";
            }

            $.get(ajaxUrl,{'zwCode':zwCode},function (pageStr) {
                $(contenObj).empty();
                $(contenObj).html(pageStr);
            })
        });
        
        $("#ywczPermissonBtn").click(function(){
        	$.get("DININGSYS/permission/businessPermisson/toFrontDeskQxIndex",function(str){
	    		addIndex= layer.open({
	                type: 1,
	                title:'业务操作权限控制',
	                skin: 'layui-layer-rim',
	                area: ['95%', '93%'],
	                content: str,
	                btn:['确定','关闭'],
	                yes:function () {
	                	 $.ajax({
	                         type:'POST',
	                         url:'DININGSYS/permission/businessPermisson/saveFrontDeskQx',
	                         data:$("#frontForm").serialize(),
	                         dataType:'JSON',
	                         async:false,
	                         success:function(data){
	                             if(data.success){
	                            	 layer.close(addIndex);
	                                 layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	                             }else{
	                                 layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	                             }
	                         }
	                     })
	                }
	    		})
        	})
        });

        $("#empAreaStaBtn").click(function () {
            $.get("DININGSYS/permission/empAreaSta/index",function(str){
                var empAreaStaIndex= layer.open({
                    type:1,
                    title:'员工查看区域统计信息权限',
                    skin: 'layui-layer-rim',
                    area: ['95%', '93%'],
                    content: str,
                    btn:['关闭'],
                    yes:function (index, layero) {
                        layer.close(empAreaStaIndex);
                    }
                })
            })
        });

        $("#addNewDuties").click(function () {
            $.get("DININGSYS/permission/businessPermisson/toDutiesAddPage",function(str){
               var addIndex= layer.open({
                    type: 1,
                    title:'新增职务',
                    skin: 'layui-layer-rim',
                    area: ['400px', '300px'],
                    content: str,
                    btn:['确定','关闭'],
                    yes:function () {
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/permission/businessPermisson/addNewDuties',
                            data:{dutiesCode:$("#dutiesCode").val(),dutiesName:$("#dutiesName").val()},
                            dataType:'JSON',
                            success:function(data){
                                if(!data.success){
                                    layer.msg("职务保存出错！",{time:1000})
                                }else{
                                   busi_per_tree.reAsyncChildNodes(null, "refresh");
                                    layer.close(addIndex);
                                }
                            }
                        })
                    }
                })
            })
        })
    }

    function backgroudAndDeskMenuInit(){
        //系统使用权限后台菜单树
        var sysPerBackgroudZtreeSetting = {
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
            check:{
                chkboxType:  { "Y" : "ps", "N" : "ps" },
                enable:true,
                chkStyle: "checkbox",
                autoCheckTrigger: true
            },
            view: {
                dblClickExpand: false,
                selectedMulti: false,
                fontCss: getFont,
                showIcon: showIconForTree
            },
            callback: {
                onAsyncSuccess : function menuDataLoadSuccess(){
                    deskMenuInit();
                    var nodes = sysPerBackgroudZtree.getNodes();
                    if (nodes.length>0) {
                        sysPerBackgroudZtree.expandNode(nodes[0], true, false);
                    }
                },
                onCheck: backAndDeskMenuCheck,
                beforeAsync: beforeBackFlushTree
            },
            async: {
                enable: true,
                url:"DININGSYS/permission/businessPermisson/getAllBakcgroudMenu/"+$("#hideDutiesCode").val()
            }
        };

        sysPerBackgroudZtree = $.fn.zTree.init($("#sysPerBackgroudZtree"), sysPerBackgroudZtreeSetting);
    }

    function deskMenuInit(){
        //系统使用权限前台菜单树
        var sysPerDeskZtreeSetting = {
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
            check:{
                enable:true,
                chkStyle: "checkbox",
                chkboxType :{ "Y" : "ps", "N" : "ps" },
                autoCheckTrigger: true
            },
            view: {
                dblClickExpand: false,
                selectedMulti: false,
                fontCss: getFont,
                showIcon: showIconForTree
            },
            callback: {
                onAsyncSuccess : function menuDataLoadSuccess(){
                    var nodes = sysPerDeskZtree.getNodes();
                    if (nodes.length>0) {
                        sysPerDeskZtree.expandNode(nodes[0], true, false);
                    }
                    layer.close(pageLoadLayerIndex);
                },
                onCheck: backAndDeskMenuCheck,
                beforeAsync: beforeDeskFlushTree
            },
            async: {
                enable: true,
                url:"DININGSYS/permission/businessPermisson/getAllDeskMenu/"+$("#hideDutiesCode").val()
            }
        };

        sysPerDeskZtree = $.fn.zTree.init($("#sysPerDeskZtree"), sysPerDeskZtreeSetting);
    }

    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }

    function showIconForTree(treeId, treeNode) {
        return treeNode.menuType===1; //代表功能权限的数据为blue,并且不显示图标
    }
    
    function backAndDeskMenuCheck(event, treeId, treeNode) {
        var dutiesNode = busi_per_tree.getSelectedNodes()[0],
            dutiesCode = dutiesNode['cCode'];
        if(single == '1'){
	 		$.ajax({
	            type:'POST',
	            url:'DININGSYS/permission/businessPermisson/editDutiesMenu',
	            data:{dutiesCode:dutiesCode,menuId:treeNode.id,check:treeNode.checked},
	            dataType:'JSON',
	            success:function(data){
	                if(!data.success){
	                    layer.msg("保存出错",{
	                        time:1000
	                    })
	                }
	            }
	        })
        }
    }

    function beforeBackFlushTree(treeId, treeNode){
        var tempTree = $.fn.zTree.getZTreeObj("sysPerBackgroudZtree");
        tempTree.setting.async.url = "DININGSYS/permission/businessPermisson/getAllBakcgroudMenu/"+$("#hideDutiesCode").val();
    }

    function beforeDeskFlushTree(treeId, treeNode){
        var tempTree = $.fn.zTree.getZTreeObj("sysPerDeskZtree");
        tempTree.setting.async.url = "DININGSYS/permission/businessPermisson/getAllDeskMenu/"+$("#hideDutiesCode").val();
    }

    function tableInit(type) {
        var zwCode = busi_per_tree.getSelectedNodes()[0]['cCode'],itemFileTypeTableId,itemFileTableId,itemFileTableUrl,itemFileTypeTableUrl,widthObj;
        itemFileTableUrl = "DININGSYS/permission/businessPermisson/getSelectItemFile";
        itemFileTypeTableUrl = "DININGSYS/permission/businessPermisson/getSelectItemFileType";
        if(type == "free"){
            itemFileTableId = "#freeItemFileTable";
            itemFileTypeTableId = "#freeItemFileTypeTable";
            widthObj = "#free";
            itemFileTableUrl += "/"+zwCode+"/1";
            itemFileTypeTableUrl += "/"+zwCode+"/1";
        }else if(type == "cb"){
            itemFileTableId = "#cbItemFileTable";
            itemFileTypeTableId = "#cbItemFileTypeTable";
            widthObj = "#chargeback";
            itemFileTableUrl += "/"+zwCode+"/2";
            itemFileTypeTableUrl += "/"+zwCode+"/2";
        }else if(type == "vp"){
            itemFileTableId = "#vpItemFileTable";
            itemFileTypeTableId = "#vpItemFileTypeTable";
            widthObj = "#variablePrice";
            itemFileTableUrl += "/"+zwCode+"/3";
            itemFileTypeTableUrl += "/"+zwCode+"/3";
        }

        $(itemFileTableId).jqGrid({
            // url: itemFileTableUrl,
            datatype: "local",
            mtype: "GET",
            colNames: ["itemFileId","品项代码","品项名称","标准价格"],
            colModel: [
                { name: "itemFileId",hidden:true},
                { name: "num",width:80},
                { name: "name",width:100},
                { name: "standardPrice",width:80}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            height:220
        });

        $(itemFileTypeTableId).jqGrid({
            // url: itemFileTypeTableUrl,
            datatype: "local",
            colNames: ["itemFileTypeId","编号","名称","说明"],
            colModel: [
                { name: "itemFileTypeId",hidden:true},
                { name: "num" },
                { name: "name"},
                { name: "description"}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            height:220
        });

        $(itemFileTableId).jqGrid("setGridWidth",$(widthObj).width(),true);
        $(itemFileTypeTableId).jqGrid("setGridWidth",$(widthObj).width(),true);

        getItemFileTableData(itemFileTableUrl,itemFileTableId);
        getItemFileTypeTableData(itemFileTypeTableUrl,itemFileTypeTableId);
    }

    function getItemFileTableData(url,itemFileTableId){
        $.get(url,function (data) {
            for(var i in data){
                $(itemFileTableId).jqGrid('addRowData',data[i]['itemFileId'],data[i]);
            }
        })
    }

    function getItemFileTypeTableData(url,itemFileTypeTableId){
        $.get(url,function (data) {
            for(var i in data){
                $(itemFileTypeTableId).jqGrid('addRowData',data[i]['itemFileTypeId'],data[i]);
            }
        })
    }

    function chexBoxBtnInit() {
        $("#deyhCheck").change(function () {
            if($(this).is(":checked")){
                $("input[name='deyhType']").prop("disabled",false);
                $(".deyhInput").prop("disabled",false);
                $("#mddeyhTwo").prop("readonly",true);

                $("#pcceqxCheck").prop("checked",false);
                $("input[name='pcceType']").prop("disabled",true);
                $(".pcceqxInput").prop("disabled",true);
                $(".pcceqxInput").prop("readonly",false);
            }else{
                $("input[name='deyhType']").prop("disabled",true);
                $(".deyhInput").prop("disabled",true);
                $(".deyhInput").prop("readonly",false);
            }
        });

        $("#pcceqxCheck").change(function () {
            if($(this).is(":checked")){
                $("input[name='pcceType']").prop("disabled",false);
                $(".pcceqxInput").prop("disabled",false);
                $("#mdpccexzTwo").prop("readonly",true);

                $("#deyhCheck").prop("checked",false);
                $("input[name='deyhType']").prop("disabled",true);
                $(".deyhInput").prop("disabled",true);
                $(".deyhInput").prop("readonly",false);
            }else{
                $("input[name='pcceType']").prop("disabled",true);
                $(".pcceqxInput").prop("disabled",true);
                $(".pcceqxInput").prop("readonly",false);
            }
        });

        $("#yqzgedCheck").change(function () {
            if($(this).is(":checked")){
                $(".yqzgedInput").prop("disabled",false);
            }else{
                $(".yqzgedInput").prop("disabled",true);
                $(".yqzgedInput").val("0");
            }
        });


        $(".checkBoxAll").click(function () {
            var objId = $(this).attr("id");
            if(objId == "discountAll"){
                $("#discountForm").find("input[type='checkbox']").prop("checked",true);
                $("input[name='deyhType']").prop("disabled",false);
                $("input[name='pcceType']").prop("disabled",false);
                $(".deyhInput").prop("disabled",false);
                $(".pcceqxInput").prop("disabled",false);
                $(".yqzgedInput").prop("disabled",false);
                $("#mddeyhTwo").attr("readonly","readonly");
                $("#mdpccexzTwo").attr("readonly","readonly");
            }else if(objId == "generalAll"){
                $("#normalForm").find("input[type='checkbox']").filter("[name!='state']").prop("checked",true);
            }
        });

        $(".checkBoxCancel").click(function () {
            var objId = $(this).attr("id");
            if(objId == "discountCancel"){
                $("#discountForm").find("input[type='checkbox']").prop("checked",false);
                $("input[name='deyhType']").prop("disabled",true);
                $("input[name='pcceType']").prop("disabled",true);
                $(".deyhInput").prop("disabled",true);
                $(".pcceqxInput").prop("disabled",true);
                $(".yqzgedInput").prop("disabled",true);
                $(".deyhInput").removeAttr("readonly");
                $(".pcceqxInput").removeAttr("readonly");
            }else if(objId == "generalCancel"){
                $("#normalForm").find("input[type='checkbox']").filter("[name!='state']").prop("checked",false);
            }
        })

    }

    function discountRadioInit() {
        $(".deyhRadioClass").click(function () {
           var radioVal = $(this).val();
            if(radioVal == "1"){
                $("#mddeyhTwo").val("5").attr("readonly","readonly");
                $("#mddeyhOne").removeAttr("readonly");
            }else{
                $("#mddeyhOne").val("20").attr("readonly","readonly");
                $("#mddeyhTwo").removeAttr("readonly");
            }
        });

        $(".pcceRadioClass").click(function () {
           var radioVal = $(this).val();
            if(radioVal == "1"){
                $("#mdpccexzTwo").val("50").attr("readonly","readonly");
                $("#mdpccexzOne").removeAttr("readonly");
            }else{
                $("#mdpccexzOne").val("500").attr("readonly","readonly");
                $("#mdpccexzTwo").removeAttr("readonly");
            }
        })
    }
    
    function saveInfoBtnInit() {
        $(".saveInfo").click(function () {
            var objId = $(this).attr("id");
            //常规业务权限保存
            if(objId == "generalSave"){
                var generalCheckData = $("#normalForm").find("input:checked"),
                    dataId = $("#generalOverViewId").val();
                if(generalCheckData.length < 1 && dataId == ""){
                    layer.alert('没有数据可以保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    return;
                }

                $.ajax({
                    type:'POST',
                    url:'DININGSYS/permission/businessPermisson/editGeneralData',
                    data:$("#normalForm").serialize(),
                    dataType:'JSON',
                    async:false,
                    success:function(data){
                        if(data.success){
                            updateNodeName();
                            tabFlush();
                            layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }else{
                            layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                })
            }else if(objId == "discountSave"){//优惠业务权限保存
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/permission/businessPermisson/editDiscountData',
                    data:$("#discountForm").serialize(),
                    async:false,
                    dataType:'JSON',
                    success:function(data){
                        if(data.success){
                            updateNodeName();
                            tabFlush();
                            layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }else{
                            layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    }
                })
            }else if(objId == "freeSaveData"){ //赠送信息保存
                var typeVal =  Number($("input[name='znzszdpx']:checked").val());
                if(typeVal == 1){ //为品项小类时
                    var freeTable = $("#freeItemFileTypeTable"),
                        freeData = freeTable.jqGrid("getRowData"),
                        freeDataIds = [];
                    if(freeData.length > 0){
                        for(var i in freeData){
                            freeDataIds.push(freeData[i]['itemFileTypeId']);
                        }
                        $("#freeDataIds").val(freeDataIds.toString());
                        $("#freeDataType").val("1");
                    }
                }else{ //为品项时，需要先判断赠送价格的最大值，大于该值的品项无效
                    var freeTable = $("#freeItemFileTable"),
                        freeData = freeTable.jqGrid("getRowData"),
                        freeDataIds = [],
                        freeMaxPrice = Number($("#freeMaxPrice").val());
                    if(freeData.length > 0){
                        for(var i in freeData){
                            var itemFilePirce = Number(freeData[i].standardPrice);
                            if(itemFilePirce < freeMaxPrice){
                                freeDataIds.push(freeData[i].itemFileId);
                            }
                        }
                        $("#freeDataIds").val(freeDataIds.toString());
                        $("#freeDataType").val("2");
                    }
                }
                tableSaveAjax('DININGSYS/permission/businessPermisson/editFreeData',$("#freePerForm").serialize());
            }else if(objId == "chargeBackSaveData"){ //退单信息保存
                var typeVal =  Number($("input[name='zntdzdpx']:checked").val());
                if(typeVal == 1){ //为品项小类时
                    var cbTable = $("#cbItemFileTypeTable"),
                        cbData = cbTable.jqGrid("getRowData"),
                        cbDataIds = [];
                    if(cbData.length > 0){
                        for(var i in cbData){
                            cbDataIds.push(cbData[i]['itemFileTypeId']);
                        }
                        $("#chargeBackDataIds").val(cbDataIds.toString());
                        $("#chargeBackDataType").val("1");
                    }
                }else{ //为品项时
                    var cbTable = $("#cbItemFileTable"),
                        cbData = cbTable.jqGrid("getRowData"),
                        cbDataIds = [];
                    if(cbData.length > 0){
                        for(var i in cbData){
                            cbDataIds.push(cbData[i]['itemFileId']);
                        }
                        $("#chargeBackDataIds").val(cbDataIds.toString());
                        $("#chargeBackDataType").val("2");
                    }
                }
                tableSaveAjax('DININGSYS/permission/businessPermisson/editChageBackData',$("#chargeBackForm").serialize());
            }else if(objId == "variablePriceSaveData"){//变价信息保存
                var typeVal =  Number($("input[name='znbjzdpx']:checked").val());
                if(typeVal == 1){ //为品项小类时
                    var vpTable = $("#vpItemFileTypeTable"),
                        vpData = vpTable.jqGrid("getRowData"),
                        vpDataIds = [];
                    if(vpData.length > 0){
                        for(var i in vpData){
                            vpDataIds.push(vpData[i]['itemFileTypeId']);
                        }
                        $("#variablePriceDataIds").val(vpDataIds.toString());
                        $("#variablePriceDataType").val("1");
                    }
                }else{ //为品项时
                    var vpTable = $("#vpItemFileTable"),
                        vpData = vpTable.jqGrid("getRowData"),
                        vpDataIds = [];
                    if(vpData.length > 0){
                        for(var i in vpData){
                            vpDataIds.push(vpData[i]['itemFileId']);
                        }
                        $("#variablePriceDataIds").val(vpDataIds.toString());
                        $("#variablePriceDataType").val("2");
                    }
                }
                tableSaveAjax('DININGSYS/permission/businessPermisson/editVariablePriceData',$("#variablePriceForm").serialize());
            }
        })
    }

    function tableSaveAjax(url,data) {
        $.ajax({
            type:'POST',
            url:url,
            data:data,
            dataType:'JSON',
            async:false,
            success:function(data){
                if(data.success){
                    updateNodeName();
                    layer.alert('已保存',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    tabFlush();
                }else{
                    layer.alert('编辑失败',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                }
            }
        })
    }

    function updateNodeName() {
        var selectNode = busi_per_tree.getSelectedNodes()[0],
            nodeName = selectNode.cName;
        if(nodeName.indexOf("-未设置业务权限") != -1){
            nodeName = nodeName.substring(0,nodeName.indexOf("-未设置业务权限"));
            selectNode.cName = nodeName;
            busi_per_tree.updateNode(selectNode);
            $("#postName").html(nodeName);
        }
    }

    function itemRadioBtnInit(name) {
        var radioObj = "input[name='"+name+"']";
        $(radioObj).click(function () {
            if($(this).hasClass('radioOne')){
                $(".btnTwo").prop("disabled",true);
                $(".btnOne").prop("disabled",false);
            }else if($(this).hasClass('radioTwo')){
                $(".btnTwo").prop("disabled",false);
                $(".btnOne").prop("disabled",true);
            }
        })
    }

    function itemChooseBtnInit(type) {
        var itemFileTypeTableId,itemFileTableId,itemFileTableObj,itemFileTypeTableObj;
        if(type == "free"){
            itemFileTableId = "#freeItemFileTable";
            itemFileTypeTableId = "#freeItemFileTypeTable";
        }else if(type == "cb"){
            itemFileTableId = "#cbItemFileTable";
            itemFileTypeTableId = "#cbItemFileTypeTable";
        }else if(type == "vp"){
            itemFileTableId = "#vpItemFileTable";
            itemFileTypeTableId = "#vpItemFileTypeTable";
        }

        itemFileTableObj = $(itemFileTableId);
        itemFileTypeTableObj = $(itemFileTypeTableId);

        $(".btnItemChoose").click(function () {
            var itemFileYxTableRows = itemFileTableObj.jqGrid('getRowData'),
                itemFileNotInIds = [];

            if(itemFileYxTableRows.length > 0){
                for(var i in itemFileYxTableRows){
                    itemFileNotInIds.push(itemFileYxTableRows[i].itemFileId);
                }
            }

            $.post('DININGSYS/permission/businessPermisson/itemFileChoosePage',{itemFileYxIds:itemFileNotInIds.toString()},function (data) {
                var itemFileIndex = layer.open({
                    type: 1,
                    title:'品项选择',
                    skin: 'layui-layer-rim',
                    area: ['750px', '600px'],
                    content: data,
                    btn:['保存','关闭'],
                    yes:function () {
                        var rowDatas = $("#itemFileYxTable").jqGrid('getRowData');

                        itemFileTableObj.jqGrid("clearGridData");
                        for(var i=0;i < rowDatas.length;i++){
                            itemFileTableObj.jqGrid('addRowData',i+1,rowDatas[i]);
                        }

                        layer.close(itemFileIndex);
                    }
                });
            })
        });

        $(".btnItemTypeChoose").click(function () {
            var itemFileTypeYxTableRows = itemFileTypeTableObj.jqGrid('getRowData'),
                itemFileTypeNotInIds = [];

            if(itemFileTypeYxTableRows.length > 0){
                for(var i in itemFileTypeYxTableRows){
                    itemFileTypeNotInIds.push(itemFileTypeYxTableRows[i]['itemFileTypeId']);
                }
            }

            $.post('DININGSYS/permission/businessPermisson/itemFileTypeChoosePage',{itemFileTypeYxIds:itemFileTypeNotInIds.toString()},function (data) {
                var itemFileTypeIndex = layer.open({
                    type: 1,
                    title:'品项小类选择',
                    skin: 'layui-layer-rim',
                    area: ['750px', '600px'],
                    content: data,
                    btn:['保存','关闭'],
                    yes:function () {
                        var rowDatas = $("#itemFileTypeYxTable").jqGrid('getRowData');

                        itemFileTypeTableObj.jqGrid("clearGridData");
                        for(var i=0;i < rowDatas.length;i++){
                            itemFileTypeTableObj.jqGrid('addRowData',i+1,rowDatas[i]);
                        }

                        layer.close(itemFileTypeIndex);
                    }
                });
            })
        });

        $(".btnItemTypeDel").click(function () {
            var selectId = itemFileTypeTableObj.jqGrid('getGridParam',"selrow");
            if(selectId){
                itemFileTypeTableObj.delRowData(selectId);
            }else{
                layer.alert('请选择一条数据进行删除！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});

            }
        });

        $(".btnItemDel").click(function () {
            var selectId = itemFileTableObj.jqGrid('getGridParam',"selrow");
            if(selectId){
                itemFileTableObj.delRowData(selectId);
            }else{
                layer.alert('请选择一条数据进行删除！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});

            }
        });

        $(".btnItemTypeClear").click(function () {
            var rows = itemFileTypeTableObj.jqGrid('getRowData');
            if(rows.length > 0){
                itemFileTypeTableObj.jqGrid("clearGridData");
            }else{
                layer.alert('数据已经被清空！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});

            }
        });

        $(".btnItemClear").click(function () {
            var rows = itemFileTableObj.jqGrid('getRowData');
            if(rows.length > 0){
                itemFileTableObj.jqGrid("clearGridData");
            }else{
                layer.alert('数据已经被清空！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});

            }
        })

    }

    function itemChoosePageInit(name) {
        //赠送、退单以及变价指定品项表格
        $("#itemFileTable").jqGrid({
            url: '',
            datatype: "local",
            colNames: ["itemFileId","编号","名称","单位","标准价格","说明"],
            colModel: [
                { name: "itemFileId",hidden:true},
                { name: "num",width:80},
                { name: "name",width:100},
                { name: "unit",width:50},
                { name: "standardPrice",width:80},
                { name: "sm"}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            height:220,
            ondblClickRow:selectRowToYx
        });

        //赠送、退单以及变价指定品项已选表格
        $("#itemFileYxTable").jqGrid({
            url: 'DININGSYS/archive/itemFile/getItemFileYxInIds?inIds='+$("#itemFileYxIds").val(),
            datatype: "JSON",
            colNames: ["itemFileId","编号","名称","单位","标准价格","说明"],
            colModel: [
                { name: "itemFileId",hidden:true},
                { name: "num",width:80},
                { name: "name",width:100},
                { name: "unit",width:50},
                { name: "standardPrice",width:80},
                { name: "sm"}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            height:200,
            ondblClickRow:selectRowToKx,
            loadComplete:function () {
                var yxLength = $("#itemFileYxTable").jqGrid('getRowData');
                $("#itemFileYx").html(yxLength.length);
            }
        });

        //右侧树
        var itemFileChooseSetting = {
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
                dblClickExpand: false
            },
            callback: {
                onAsyncSuccess : tcPageInitTree,
                onClick:tcPxTreeClick
            },
            async: {
                enable: true,
                url:"DININGSYS/permission/businessPermisson/getAllItemFileType"
            }
        };

        $.fn.zTree.init($("#itemFileTree"), itemFileChooseSetting);

        function tcPageInitTree() {
            var treeObj = $.fn.zTree.getZTreeObj("itemFileTree");

            var node = treeObj.getNodeByParam("id",0,null);

            treeObj.selectNode(node);

            treeObj.expandNode(node,true);

            tcPxChooseChangeTable(node);

        }

        function selectRowToYx(rowid,iRow,iCol,e) {
            var grid = $('#itemFileTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#itemFileYxTable").addRowData(rowid,myCellData);

            changeTcPxTxTabCon();
        }

        function selectRowToKx(rowid,iRow,iCol,e) {
            var grid = $('#itemFileYxTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#itemFileTable").addRowData(myCellData.itemFileId,myCellData);

            changeTcPxTxTabCon();
        }

        function tcPxTreeClick(event, treeId, treeNode) {
            tcPxChooseChangeTable(treeNode);
        }

        function tcPxChooseChangeTable(node) {
            var id = node.id,
                itemFileYxTable = $("#itemFileYxTable"),
                itemFileYxTableRows = itemFileYxTable.jqGrid('getRowData');

            $.get('DININGSYS/permission/businessPermisson/getKxItemFile',{itemFileType:id,yxItemFileIds:$("#itemFileYxIds").val()},function (data) {
                jQuery("#itemFileTable").jqGrid("clearGridData");
                for(var i=0;i < data.length;i++){
                    $("#itemFileTable").jqGrid('addRowData',i+1,data[i]);
                }
                $("#itemFileKx").html(data.length);
            })
        }

        $("#itemFileChooseAll").click(function () {
            var rowObj = $("#itemFileTable").jqGrid('getRowData');
            jQuery("#itemFileTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileYxTable").addRowData(rowObj[i].itemFileId,rowObj[i]);
            }
            changeTcPxTxTabCon();
        });

        $("#itemFileCancelAll").click(function () {
            var rowObj = $("#itemFileYxTable").jqGrid('getRowData');
            jQuery("#itemFileYxTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileTable").addRowData(rowObj[i].itemFileId,rowObj[i]);
            }
            changeTcPxTxTabCon();
        });


        function changeTcPxTxTabCon() {
            var kxLenth = $("#itemFileTable").jqGrid('getRowData');
            $("#itemFileKx").html(kxLenth.length);
            var yxLength = $("#itemFileYxTable").jqGrid('getRowData');
            $("#itemFileYx").html(yxLength.length);
        }
    }

    function itemTypeChoosePage(name) {
        //赠送、退单以及变价指定品项小类表格
        $("#itemFileTypeTable").jqGrid({
            url: '',
            datatype: "local",
            colNames: ["itemFileTypeId","编号","名称","说明"],
            colModel: [
                { name: "itemFileTypeId",hidden:true},
                { name: "num"},
                { name: "name"},
                { name: "description"}
            ],
            viewrecords: true,
            autoencode: true,
            rowNum:-1,
            styleUI : 'Bootstrap',
            height:220,
            ondblClickRow:selectRowToYx
        });

        //赠送、退单以及变价指定品项小类表格
        $("#itemFileTypeYxTable").jqGrid({
            url: 'DININGSYS/permission/businessPermisson/getFileItemTypeInIds?inIds='+$("#itemFileTypeYxIds").val(),
            datatype: "JSON",
            colNames: ["itemFileTypeId","编号","名称","说明"],
            colModel: [
                { name: "itemFileTypeId",hidden:true},
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

        //右侧树
        var itemFileTypeChooseSetting = {
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
                dblClickExpand: false
            },
            callback: {
                onAsyncSuccess : itemFileTypePageInitTree,
                onClick:itemFileTypeTreeClick
            },
            async: {
                enable: true,
                url:"DININGSYS/permission/businessPermisson/getFirstLeveType"
            }
        };

        $.fn.zTree.init($("#itemFileTypeTree"), itemFileTypeChooseSetting);

        function itemFileTypePageInitTree() {
            var treeObj = $.fn.zTree.getZTreeObj("itemFileTypeTree");

            var node = treeObj.getNodeByParam("id",0,null);

            treeObj.selectNode(node);

            treeObj.expandNode(node,true);

            tcPxChooseChangeTable(node);

        }

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

            $("#itemFileTypeTable").addRowData(myCellData.itemFileTypeId,myCellData);

            changeTcPxTxTabCon();
        }

        function itemFileTypeTreeClick(event, treeId, treeNode) {
            tcPxChooseChangeTable(treeNode);
        }

        function tcPxChooseChangeTable(node) {
            var id = node.id,
                itemFileTypeYxTable = $("#itemFileTypeYxTable"),
                itemFileTypeYxTableRows = itemFileTypeYxTable.jqGrid('getRowData');

            $.get('DININGSYS/permission/businessPermisson/getKxItemFileType',{parentId:id,yxItemFileTypeIds:$("#itemFileTypeYxIds").val()},function (data) {
                jQuery("#itemFileTypeTable").jqGrid("clearGridData");
                for(var i=0;i < data.length;i++){
                    $("#itemFileTypeTable").jqGrid('addRowData',i+1,data[i]);
                }
                $("#itemFileTypeKx").html(data.length);
            })
        }

        $("#itemFileTypeChooseAll").click(function () {
            var rowObj = $("#itemFileTypeTable").jqGrid('getRowData');
            jQuery("#itemFileTypeTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileTypeYxTable").addRowData(rowObj[i].itemFileTypeId,rowObj[i]);
            }
            changeTcPxTxTabCon();
        });

        $("#itemFileTypeCancelAll").click(function () {
            var rowObj = $("#itemFileTypeYxTable").jqGrid('getRowData');
            jQuery("#itemFileTypeYxTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#itemFileTypeTable").addRowData(rowObj[i]['itemFileTypeId'],rowObj[i]);
            }
            changeTcPxTxTabCon();
        });


        function changeTcPxTxTabCon() {
            var kxLenth = $("#itemFileTypeTable").jqGrid('getRowData');
            $("#itemFileTypeKx").html(kxLenth.length);
            var yxLength = $("#itemFileTypeYxTable").jqGrid('getRowData');
            $("#itemFileTypeYx").html(yxLength.length);
        }
    }

    function frontInit()
    {
        $("#frontAll").click(function(){
        	$("#frontForm input[type='checkbox']").prop("checked",true);
        });
        
        $("#frontCancel").click(function(){
        	$("#frontForm input[type='checkbox']").prop("checked",false);
        });
    }

    return {
        //首页页面初始化
        pageInit:function() {
            pageInit()
        },
        //赠送、退单、变价表格相关
        tableInit:function (type) {
            tableInit(type);
        },
        //chexBox事件
        chexBoxBtnInit:function () {
            chexBoxBtnInit();
        },
        discountRadioInit:function () {
            discountRadioInit();
        },
        //保存按钮事件
        saveInfoBtnInit:function () {
            saveInfoBtnInit();
        },
        //radio改变事件
        itemRadioBtnInit:function (name) {
            itemRadioBtnInit(name);
        },
        //赠送、退单、变价 3个按钮
        itemChooseBtnInit:function (type) {
            itemChooseBtnInit(type);
        },
        //品项选择页面初始化
        itemChoosePage:function () {
            itemChoosePageInit();
        },
        //品项小类选择页面初始化
        itemTypeChoosePage:function () {
            itemTypeChoosePage();
        },
        frontInit:function(){
        	frontInit();
        }
    }

}();