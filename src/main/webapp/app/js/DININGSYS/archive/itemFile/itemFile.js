/**
 * Created by zhshuo on 2016-10-13.
 */
var itemFile = function () {
    /*必选品项的替换品项map对象*/
    var bxpxThpxMap = {};

    /*选择必选品项的替换品项时，必选品项的ID*/
    var chooseThpxBxPxId = null,treeDemoObJ,rMenuObj;

    /*品项档案首页树相关 start*/
    function zTreeInit() {
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
                dblClickExpand: false
            },
            callback: {
                onRightClick: OnRightClick,
                onClick: proOnClick,
                onAsyncSuccess : initTree
            },
            async: {
                enable: true,
                url:"DININGSYS/archive/itemFile/getAllItemFileType?flag=1"
            }
        };

        function OnRightClick(event, treeId, treeNode) {
             if (treeNode && treeNode.id == 0) {
                 treeDemoObJ.cancelSelectedNode();
                 treeDemoObJ.selectNode(treeNode);
                showRMenu("root", event.clientX, event.clientY);
            } else if (treeNode && treeNode.pId == 0){
            	treeDemoObJ.selectNode(treeNode);
                showRMenu("bType", event.clientX, event.clientY);
            } else if (treeNode && treeNode.pId > 0){
                 treeDemoObJ.selectNode(treeNode);
                 showRMenu("sType", event.clientX, event.clientY);
            }
        }

        function proOnClick(event, treeId, treeNode) {
            $("#searchItemFileText").val("");
            changeTable(treeNode);
        }

        function showRMenu(type, x, y) {
            $("#rMenu ul").show();
            if(type == 'root'){
                $("#m_add").show();
                $("#m_edit").hide();
                $("#m_del").hide();
            }else if(type == 'bType'){
                $("#m_add").show();
                $("#m_edit").show();
                $("#m_del").show();
            }else{
                 $("#m_add").show();
                $("#m_edit").show();
                $("#m_del").hide();
            }
            rMenuObj.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
            $("body").bind("mousedown", onBodyMouseDown);
        }
        function hideRMenu() {
            if (rMenuObj) rMenuObj.css({"visibility": "hidden"});
            $("body").unbind("mousedown", onBodyMouseDown);
        }
        function onBodyMouseDown(event){
            if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
                rMenuObj.css({"visibility" : "hidden"});
            }
        }

        /*右键菜单  添加*/
        $("#m_add").click(function () {
            hideRMenu();
            var node = treeDemoObJ.getSelectedNodes()[0];
            var url,param,title,ajaxUrl,ajaxForm,area,flag;
            var h = ($(window).height() - 100)*0.9;
            if(node.id == 0){
                flag = 1;
                url = "DININGSYS/archive/itemFile/toAddFileTypeEdit";
                param = {flag:1,id:node.id};
                title = "新增品项大类";
                ajaxUrl = "DININGSYS/archive/itemFile/addItemFileType";
                area = ['500px', h+'px'];
            }else if(node.id != 0 && node.pId == 0){
                flag = 2;
                url = "DININGSYS/archive/itemFile/toAddFileTypeEdit";
                param = {flag:2,id:node.id};
                title = "新增品项小类";
                ajaxUrl = "DININGSYS/archive/itemFile/addItemFileType";
                area = ['500px', h+'px'];
            }else if(node.id != 0 && node.pId > 0){
                flag = 3;
                url = "DININGSYS/archive/itemFile/toItemFileEdit";
                param = {flag:1,nodeParentId:node.pId,nodeId:node.id};
                title = "新增品项";
                ajaxUrl = "DININGSYS/archive/itemFile/addItemFile";
                area = ['95%', h+'px'];
            }

            var formData,formObj;
            if(flag == 1){
                formObj = "#itemFileBigTypeForm";
            }else if(flag == 2){
                formObj = "#itemFileSmallTypeForm";
            }else if(flag ==3){
                formObj = "#ppEditFormId";
            }
            $.get(url,param,function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:title,
                    skin: 'layui-layer-rim',
                    area: area,
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if(flag == 2){
                            /*品项小类-->启用制作方法类别限定*/
                            var ids  = [];
                            if($("#qyzzfflbxd").is(":checked")){
                                var trs = $("#pxxlTrCheckBox").find(":checkbox");
                                $(trs).each(function () {
                                    if($(this).is(":checked")){
                                        ids.push($(this).val())
                                    }
                                })
                            }
                            $("#qyzzfflbids").val(ids.toString());
                            /*品项小类-->启用制作方法类别限定  END*/
                        }else if(flag == 3){
                            formData = itemFileAddParam()
                        }
                        if(flag == 1 || flag == 2){
                            if($(formObj).valid()){
                                $.ajax({
                                    type:'POST',
                                    url:ajaxUrl,
                                    data:flag == 1?$("#itemFileBigTypeForm").serialize():$("#itemFileSmallTypeForm").serialize(),
                                    dataType:'JSON',
                                    async:false,
                                    success:function (data) {
                                        if(data.success){
                                            layer.msg("数据已保存",{time:1000});
                                            layer.close(addIndex);
                                            $("#grid-table").trigger("reloadGrid");
                                            treeDemoObJ.reAsyncChildNodes(null, "refresh");
                                        }else{
                                            layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                        }
                                    }
                                })
                            }
                        }else{
                            if($(formObj).valid()){
                                $.ajax({
                                    type:'POST',
                                    url:ajaxUrl,
                                    data:formData,
                                    dataType:'JSON',
                                    async:false,
                                    contentType: false,
                                    processData: false,
                                    success:function (data) {
                                        if(flag == 3)
                                        {
                                            if(data.success){

                                                var destoryTree = $.fn.zTree.getZTreeObj("pubProMeType");

                                                if(destoryTree != null){
                                                    destoryTree.destroy();
                                                }

                                                var json = "[";
                                                $("#zccfTbody tr").each(function(){
                                                    var inveItemId = $(this).find("td:eq(1)").text();
                                                    inveItemId = inveItemId.replace(/[\r\n]/g,"");
                                                    var count = $(this).find("input").val();
                                                    json += "{\"inveItemId\":\""+inveItemId+"\",\"count\":\""+count+"\"},";
                                                });
                                                if(json.length > 1)
                                                {
                                                    json = json.substring(0,json.length-1);
                                                }
                                                json +="]";
                                                $.ajax({
                                                    type:'POST',
                                                    url:'DININGSYS/archive/itemFile/addItemFileNext',
                                                    data:{zccfObj:json,id:data.id},
                                                    dataType:'JSON',
                                                    async:false,
                                                    success:function (data) {
                                                        if(data.success){
                                                            layer.msg("数据已保存",{time:1000});
                                                            layer.close(addIndex);
                                                            // $("#grid-table").trigger("reloadGrid");
                                                        }
                                                    }
                                                })
                                            }else{
                                                layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                            }
                                        }
                                        else
                                        {
                                            if(data.success){
                                                layer.msg("数据已保存",{time:1000});
                                                layer.close(addIndex);
                                                $("#grid-table").trigger("reloadGrid");
                                            }else{
                                                layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                            }
                                        }
                                    }
                                })
                            }
                        }
                    }
                });
            });
        });


        $("#m_edit").click(function () {
            hideRMenu();
            var node = treeDemoObJ.getSelectedNodes()[0];
            var url,param,title,ajaxUrl,area,flag;
            if(node.pId === 0){
                flag = 1;
                url = "DININGSYS/archive/itemFile/toFileTypeEdit";
                param = {flag:1,id:node.id};
                title = "编辑品项大类";
                ajaxUrl = "DININGSYS/archive/itemFile/addItemFileType";
                area = ['500px', "600px"];
            }else{
                flag = 2;
                url = "DININGSYS/archive/itemFile/toFileTypeEdit";
                param = {flag:2,id:node.id};
                title = "编辑品项小类";
                ajaxUrl = "DININGSYS/archive/itemFile/addItemFileType";
                area = ['500px', "600px"];
            }

            var formObj;
            if(flag == 1){
                formObj = "#itemFileBigTypeForm";
            }else{
                formObj = "#itemFileSmallTypeForm";
            }
            $.get(url,param,function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:title,
                    skin: 'layui-layer-rim',
                    area: area,
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if(flag === 2){
                            /*品项小类-->启用制作方法类别限定*/
                            var ids  = [];
                            if($("#qyzzfflbxd").is(":checked")){
                                var trs = $("#pxxlTrCheckBox").find(":checkbox");
                                $(trs).each(function () {
                                    if($(this).is(":checked")){
                                        ids.push($(this).val())
                                    }
                                })
                            }
                            $("#qyzzfflbids").val(ids.toString());
                            /*品项小类-->启用制作方法类别限定  END*/
                        }

                        if($(formObj).valid()){
                            $.ajax({
                                type:'POST',
                                url:ajaxUrl,
                                data:flag == 1?$("#itemFileBigTypeForm").serialize():$("#itemFileSmallTypeForm").serialize(),
                                dataType:'JSON',
                                async:false,
                                success:function (data) {
                                    if(data.success){
                                        layer.msg("数据已保存",{time:1000});
                                        layer.close(addIndex);
                                        $("#grid-table").trigger("reloadGrid");
                                        treeDemoObJ.reAsyncChildNodes(null, "refresh");
                                    }else{
                                        layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                    }
                                }
                            });
                        }
                    }
                });
            });
            
            
        });

        $("#m_del").click(function () {
            hideRMenu();
            var nodes = treeDemoObJ.getSelectedNodes();
            if (nodes && nodes.length>0) {
                if (nodes[0].children && nodes[0].children.length > 0) {
                    var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
                    if (confirm(msg) === true){
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/archive/itemFile/delItemType',
                            data:{id:nodes[0].id},
                            dataType:'JSON',
                            async:false,
                            success:function (data) {
                                if(data.success){
                                    $("#grid-table-item-type").trigger("reloadGrid");
                                    treeDemoObJ.removeNode(nodes[0]);
                                }
                            }
                        });
                    }
                } else {
                    $.ajax({
                            type:'POST',
                            url:'DININGSYS/archive/itemFile/delItemType',
                            data:{id:nodes[0].id},
                            dataType:'JSON',
                            async:false,
                            success:function (data) {
                                if(data.success){
                                    $("#grid-table-item-type").trigger("reloadGrid");
                                    treeDemoObJ.removeNode(nodes[0]);
                                }
                            }
                        });
                }
            }
        });

        //2017年6月19日13:28:59 菜品模糊搜索
        $("#searchItemFile").click(function () {
            var selectedNode = treeDemoObJ.getSelectedNodes()[0],searchFlag,typeId,shopKey;
            if(selectedNode.getParentNode() == null){//代表选择的顶级节点
                searchFlag = 1;
                shopKey = selectedNode.id;
            }else if(selectedNode.children != null && selectedNode.getParentNode != null){//代表选择的是大类
                searchFlag = 2;
                typeId = selectedNode.id;
            }else{//小类
                searchFlag = 3;
                typeId = selectedNode.id;
            }

            var $val =$.trim($("#searchItemFileText").val());

            $.post("DININGSYS/archive/itemFile/searchItemFile",
                {shopKey:shopKey,searchText:$val,
                    searchFlag:searchFlag,typeId:typeId},function (data) {
                    $("#grid-table").jqGrid("clearGridData");
                    for(var i in data)
                        $("#grid-table").addRowData(data[i].id,data[i]);
                })
        })

        $("#clearSearchText").click(function () {
            $("#searchItemFileText").val("");
        })

        rMenuObj = $("#rMenu");
        treeDemoObJ = $.fn.zTree.init($("#treeDemo"), setting);


        $("#exportItemFiles").click(function(){
            var nodes = treeDemoObJ.getSelectedNodes();
            console.log(node);
            if(nodes.length > 0){
                var node = nodes[0];
                window.location.href="DININGSYS/archive/itemFile/exportXls?sItemTypeId="+node.id;
            } else {
                window.location.href="DININGSYS/archive/itemFile/exportXls";
            }
        })
    }

    function initTree() {
        treeDemoObJ.expandAll(true);
        var node = treeDemoObJ.getNodeByParam("id",0,null);

        treeDemoObJ.selectNode(node);

        changeTable(node);
    }

    /*点击树，表格切换*/
    function changeTable(node){
        var nodeId = node.id;
        var itemFileTypeUrl = "DININGSYS/archive/itemFile/getItemFileTypeByParent?parentId="+nodeId;
        jQuery("#grid-table-item-type").jqGrid('setGridParam',{url:itemFileTypeUrl,page:1}).trigger("reloadGrid");


        var itemFileUrl = "DININGSYS/archive/itemFile/getItemFileByTypeId?sItemTypeId="+nodeId;
        jQuery("#grid-table").jqGrid('setGridParam',{url:itemFileUrl,page:1}).trigger("reloadGrid");

        setTableWidthAndHeight()
    }
    /*品项档案首页树相关 END*/

    /*设置jqgrid表格高宽*/
    function setTableWidthAndHeight() {
        $("#itemFileRightDiv").height($(window).height() - 20);

        $("#itemFileTypeTree").height($(window).height() - 60);

        $("#itemFileTable").height($("#itemFileRightDiv").height() * 0.6 - $("#itemFileEditDiv").height() - 100);
        $("#itemFileTypeTable").height($("#itemFileRightDiv").height() - $("#itemFileTable").height() - $("#itemFileEditDiv").height() - 100);

        $("#" + pageUtil.tabId).jqGrid("setGridWidth",$("#itemFileRightDiv").width(),true);
        $("#" + pageUtil.tabId).jqGrid("setGridHeight",$("#itemFileTable").height() - 50,true);

        $("#grid-table-item-type").jqGrid("setGridWidth",$("#itemFileRightDiv").width(),true);
        $("#grid-table-item-type").jqGrid("setGridHeight",$("#itemFileTypeTable").height() - 10,true);
    }

    /*品项档案页面数据初始化*/
    function pagerInit() {
        /*品项数据表格*/
        $("#grid-table").jqGrid({
            url: "DININGSYS/archive/itemFile/getItemFileByTypeId",
            datatype: "JSON",
            mtype: "GET",
            rowNum:-1,
            colNames: ["id","序号","名称","标准价格","成本价格","套餐","创建时间","修改时间"],
            colModel: [
                { name: "id",hidden:true},
                { name: "num" },
                { name: "name"},
                { name: "standardPrice",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "costPrice",formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: "isTc",formatter:function (cellvalue, options, rowObject) {
                    if(cellvalue == 1){
                        return "是";
                    }
                    return "否";
                }},
                { name: "createTime"},
                { name: "updateTime"}
            ],
            rownumbers:true,
            autoencode: true,
            styleUI : 'Bootstrap'
        });

        /*品项类别表格*/
        $("#grid-table-item-type").jqGrid({
            url: "DININGSYS/archive/itemFile/getItemFileTypeByParent",
            datatype: "JSON",
            mtype: "GET",
            rowNum:-1,
            colNames: ["id","序号","名称","说明","创建时间","修改时间","操作"],
            colModel: [
                { name: "id",hidden:true},
                { name: "num" },
                { name: "name"},
                { name: "description" },
                { name: "createTime"},
                { name: "updateTime"},
                { name: "delete",search:false,sortable:false,editable:false,formatter:colBtnInit}
            ],
            rownumbers:true,
            viewrecords: false,
            autoencode: true,
            styleUI : 'Bootstrap'
        });

        function colBtnInit(cellValue, options, rowdata, action) {
        	if(single == '1'){
        	    return "<a href='javascript:void(0)' itemtypeId='"+rowdata.id+"' class='btn btn-default btn-small itemTypeDel' role='button'>删除</a>";	
        	} else {
        		return '';
        	}
        }

        $("#grid-table-item-type").delegate("a.itemTypeDel","click",function(){
            var id = $(this).attr('itemtypeId');
            layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/archive/itemFile/delItemType',
                    data:{id:id},
                    dataType:'JSON',
                    async:false,
                    success:function (data) {
                        if(data.success){
                            $("#grid-table-item-type").trigger("reloadGrid");
                            treeDemoObJ.reAsyncChildNodes(null, "refresh");
                        }
                    }
                });
                layer.close(index);
            });
        });

        setTableWidthAndHeight();

        $(window).resize(function () {
            setTableWidthAndHeight();
        });

        /*套餐按钮事件*/
        $("#itemFileTc").click(function () {

            /*每次从新打开套餐页面，清空该对象*/
            bxpxThpxMap = {};

            var grid = $("#grid-table");
            var rowId = grid.getGridParam("selrow");

            if(!rowId){
                layer.alert('请选择数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var myGrid = $('#grid-table'),
                selRowId = myGrid.jqGrid ('getGridParam', 'selrow'),
                celValue = myGrid.jqGrid ('getCell', selRowId, 'isTc'),
                rowId = grid.jqGrid('getCell',rowId,'id');
            if(celValue == "是"){
            	if(single == '1'){
            		 $.get('DININGSYS/archive/itemFile/tcIndex/'+rowId,function (str) {
		                   var tcIndex = layer.open({
		                       type: 1,
		                       title:'修改套餐',
		                       skin: 'layui-layer-rim',
		                       area: ['1250px', '700px'],
		                       content: str,
		                       btn:['保存','关闭'],
		                       yes:function () {
		                           var bxpxTable = $("#tcBxPx"),kxpxTable = $("#kxpxTable"),thpxTable = $("#thpxTable"),slxdTable = $("#kxpxdlslxd"),
		                               countData = JSON.stringify(bxpxTable.jqGrid("footerData","get")),//必选品项统计信息
		                               bxpxData = JSON.stringify(bxpxTable.getGridParam('data')),//必选品项table数据
		                               kxpxData = JSON.stringify(kxpxTable.getGridParam('data')),//可选品项table数据
		                               thpxData = JSON.stringify(bxpxThpxMap),//替换品项table数据
		                               slxdData = JSON.stringify(slxdTable.jqGrid("getRowData"));//数量限定table数据
		
		
		                           var tcWithNum = 0,canUpdate = 0;
		                           if($("#tcWinNum").is(":checked")){
		                               tcWithNum = 1;
		                           }
		                           if($("#canUpdate").is(":checked")){
		                               canUpdate = 1;
		                           }
		
		                           var ajaxParam = {bxpxData:bxpxData,countData:countData,kxpxData:kxpxData,thpxData:thpxData,slxdData:slxdData,
		                               tcName:$("#tcName").val(),tcPrice:$("#tcPrice").val(),tcType:$("input:radio[name='tcType']:checked").val(),tcWithNum:tcWithNum,
		                               canUpdate:canUpdate,minNum:$("#minNum").val(),maxNum:$("#maxNum").val(),itemFileId:$("#selectedItemId").val(),tcId:$("#tcId").val()};
		                           $.ajax({
		                               type:'POST',
		                               url:'DININGSYS/archive/itemFile/editTc',
		                               data:ajaxParam,
		                               dataType:'JSON',
		                               success:function(data){
		                                    if(data.success){
		                                        layer.msg("数据已保存",{time:1000});
		                                        layer.close(tcIndex);
		                                        $("#grid-table").trigger("reloadGrid");
		                                    }
		                               }
		                           })
		
		                       }
		                   });
            		 });
            	} else {
            		$.get('DININGSYS/archive/itemFile/tcIndex/'+rowId,function (str) {
		                   var tcIndex = layer.open({
		                       type: 1,
		                       title:'查看套餐',
		                       skin: 'layui-layer-rim',
		                       area: ['1250px', '700px'],
		                       content: str,
		                       btn:['关闭']
		                   });
            		});
            	}
            }else{
                layer.alert('该品项档案不是套餐！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            }

        });

        
        $("#itemFileColor").click(function(){
        	   $.get("DININGSYS/archive/itemFile/colorManager",function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'品项颜色管理',
                    skin: 'layui-layer-rim',
                    area: ['800px', '600px'],
                    content: str,
                    btn:['关闭']
                });
            })
        });
        
    	/* ******************************  排序  **************************** */	
    	$("#rank").click(function(){
    		layer.open({
    			  type: 2,
    			  title: '品项(品项小类)排序',
    			  shadeClose: true,
    			  shade: 0.3,
    			  area: ['80%', '90%'],
    			  content: 'DININGSYS/archive/itemFile/goRank',
    			  end:function(){
    				  $("#" + pageUtil.tabId).trigger("reloadGrid");
    			  }
    		});
    	});
        
        /*品项修改*/
        $("#itemFileZccf").click(function () {
            var grid = $("#grid-table");
            var rowId = grid.getGridParam("selrow");

            if(!rowId){
                layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var id = grid.jqGrid('getCell',rowId,'id');

            $.get("DININGSYS/archive/itemFile/zccfManager.jspx",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'品项组成成分',
                    skin: 'layui-layer-rim',
                    area: ['800px', '600px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        var json = "[";
                        $("#zccfTbody tr").each(function(){
                            var inveItemId = $(this).find("td:eq(1)").text();
                            inveItemId = inveItemId.replace(/[\r\n]/g,"");
                            var count = $(this).find("input").val();
                            json += "{\"inveItemId\":\""+inveItemId+"\",\"count\":\""+count+"\"},";
                        });
                        if(json.length > 1)
                        {
                            json = json.substring(0,json.length-1);
                        }
                        json +="]";
                        $.ajax({
                            type:'POST',
                            url:'DININGSYS/archive/itemFile/addItemFileNext',
                            data:{zccfObj:json,id:id},
                            dataType:'JSON',
                            async:false,
                            success:function (data) {
                                if(data.success){
                                    layer.msg("数据已保存",{time:1000});
                                    layer.close(addIndex);
                                    // $("#grid-table").trigger("reloadGrid");
                                }
                            }
                        })
                    }
                })
            })
        });
        
        
        /*品项修改*/
        $("#itemFileEdit").click(function () {
            var grid = $("#grid-table");
            var rowId = grid.getGridParam("selrow");

            if(!rowId){
                layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var id = grid.jqGrid('getCell',rowId,'id');

            $.get("DININGSYS/archive/itemFile/toItemFileEdit",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'品项数据修改',
                    skin: 'layui-layer-rim',
                    area: ['95%', '90%'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        var formData = itemFileAddParam();
                        if($("#ppEditFormId").valid()){
                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/archive/itemFile/addItemFile',
                                data:formData,
                                dataType:'JSON',
                                async:false,
                                contentType: false,
                                processData: false,
                                success:function (data) {
                                    if(data.success){
                                        var json = "[";
                                        $("#zccfTbody tr").each(function(){
                                            var inveItemId = $(this).find("td:eq(1)").text();
                                            inveItemId = inveItemId.replace(/[\r\n]/g,"");
                                            var count = $(this).find("input").val();
                                            json += "{\"inveItemId\":\""+inveItemId+"\",\"count\":\""+count+"\"},";
                                        });
                                        if(json.length > 1)
                                        {
                                            json = json.substring(0,json.length-1);
                                        }
                                        json +="]";
                                        $.ajax({
                                            type:'POST',
                                            url:'DININGSYS/archive/itemFile/addItemFileNext',
                                            data:{zccfObj:json,id:data.id},
                                            dataType:'JSON',
                                            async:false,
                                            success:function (data) {
                                                if(data.success){
                                                    layer.msg("数据已保存",{time:1000});
                                                    layer.close(addIndex);
                                                    // $("#grid-table").trigger("reloadGrid");
                                                }
                                            }
                                        })
                                    }else{
                                        layer.alert(data.errorMsg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });
        

        $("#itemFileDelb").click(function () {
            var grid = $("#grid-table");
            var selectRow = grid.jqGrid('getGridParam','selrow');

            if(!selectRow){
                layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                return;
            }

            var id = grid.jqGrid('getCell',selectRow,'id');
            layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){

                $.ajax({
                    type:'POST',
                    url:'DININGSYS/archive/itemFile/deleteItemFile',
                    data:{id:id},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                            $("#grid-table").trigger("reloadGrid");
                        }
                    }
                });

                layer.close(index);
            });
        })
    }

    /*品项档案数据新增，ajax formdata参数拼装*/
    function itemFileAddParam() {
        /*新增品项，数据组合*/
        /*专用制作方法ID集合*/
        var zyzzffTrs = $("#zyzzffTbody").find("tr");
        if(zyzzffTrs.length > 0){
            var zyzzffIds = [];
            zyzzffTrs.each(function () {
                var tid = $(this).find("td:eq(0)").html();
                zyzzffIds.push($.trim(tid));
            });
            $("#zyzfids").val(zyzzffIds.toString());
        }
        /*END*/

        /*公共制作方法专用方法ID集合*/
        var ggzyzfidsTrs = $("#ggzzffTbody").find("tr");
        if(ggzyzfidsTrs.length > 0){
            var ggzyzfidsIds = [];
            ggzyzfidsTrs.each(function () {
                var tid = $(this).find("td:eq(0)").html();
                ggzyzfidsIds.push($.trim(tid));
            });

            $("#ggzyzfids").val(ggzyzfidsIds.toString());
        }
        /*END*/

        /*营养选择ID集合*/
        var yyxgidsTrs = $("#nutritionChooseTab").find("tr");
        if(yyxgidsTrs.length > 0){
            var yyxgidsIds = [];
            yyxgidsTrs.each(function () {
                var tid = $(this).find("td:eq(0)").html();
                yyxgidsIds.push($.trim(tid));
            });

            $("#yyxgids").val(yyxgidsIds.toString());
        }

        $("#yxxgsm").val($("#pxxtsm").val());

        var form = $("#ppEditFormId")[0];
        var formData = new FormData(form);


        formData.append("pxdt",$("#pxdtFileBd").val());
        formData.append("pxxt",$("#pxxtFileBd").val());

        return formData;
    }

    function initZyzfTr() {
        $("#zyzzffTbody tr").click(function () {
            $(this).toggleClass("success");
            $.each($(this).siblings("tr"),function () {
                $(this).removeClass("success");
            });
        })
    }
    
    
    function initZccfTr() {
        $("#zccfTbody tr").click(function () {
        	if($(this).hasClass("success"))
        	{
        		$(this).removeClass("success");
        	}
        	else
        	{
        		$(this).addClass("success");
        	}
            $.each($(this).siblings("tr"),function () {
                $(this).removeClass("success");
            });
        })
    }

    /*品项档案编辑页面初始化*/
    function itemFileEditPageInit() {
        /*专用制作方法新增*/
        $("#zyzfAdd").click(function () {
            $.get("DININGSYS/archive/proMethods/toProMehtodsEdit",function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'专用制作方法新增',
                    skin: 'layui-layer-rim',
                    area: ['550px', '700px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#proMethTypeEditForm").valid()){
                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/archive/proMethods/addProMethodsWithSpecial',
                                data:$("#proMethTypeEditForm").serialize(),
                                dataType:'JSON',
                                success:function (data) {
                                    if(data.success){
                                        var returnObj = eval("("+data.successMsg+")");

                                        var tr = "<tr><td style='display: none'>"+returnObj[0].id+"</td><td>"+returnObj[0].pmcode+"</td><td>"+returnObj[0].pmname+"" +
                                            "</td><td>"+booleanJudge(returnObj[0].collectProMoney)+"</td><td>"+returnObj[0].proMoney+"</td>" +
                                            "<td>"+booleanJudge(returnObj[0].collectProMoneybynum)+"</td><td>"+booleanJudge(returnObj[0].canUpdate)+"</td>";

                                        $("#zyzzffTbody").append(tr);

                                        initZyzfTr();
                                        layer.close(addIndex);
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });

        /*专用制作方法修改*/
        $("#zyzfEdit").click(function () {

            var tr = $("#zyzzffTbody tr").filter(".success");

            if(tr.length < 1){
                layer.alert('请先选择数据', {icon: 5});
                return;
            }

            var id = $(tr).find("td").eq(0).text();
            $.get("DININGSYS/archive/proMethods/toProMehtodsEdit",{id:id},function (str) {
                var addIndex = layer.open({
                    type: 1,
                    title:'专用制作方法修改',
                    skin: 'layui-layer-rim',
                    area: ['550px', '700px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        if($("#proMethTypeEditForm").valid()){
                            $.ajax({
                                type:'POST',
                                url:'DININGSYS/archive/proMethods/editProMethods',
                                data:$("#proMethTypeEditForm").serialize(),
                                dataType:'JSON',
                                success:function (data) {
                                    if(data.success){
                                        var returnObj = eval("("+data.successMsg+")");

                                        var retr = "<tr><td style='display: none'>"+returnObj[0].id+"</td><td>"+returnObj[0].pmcode+"</td><td>"+returnObj[0].pmname+"" +
                                            "</td><td>"+ booleanJudge(returnObj[0].collectProMoney)+"</td><td>"+returnObj[0].proMoney+"</td>" +
                                            "<td>"+booleanJudge(returnObj[0].collectProMoneybynum)+"</td><td>"+booleanJudge(returnObj[0].canUpdate)+"</td>";

                                        $(tr).replaceWith(retr);

                                        initZyzfTr();

                                        layer.close(addIndex);
                                    }
                                }
                            })
                        }
                    }
                });
            })
        });

        /*专用制作方法数据列表删除*/
        $("#zyzfDel").click(function () {
            var tr = $("#zyzzffTbody tr").filter(".success");
            if(tr.length < 1){
                layer.alert('请先选择数据', {icon: 5});
                return;
            }
            $(tr).remove();
        });

        /*公共专用做法选择*/
        $("#ggzzffChoose").click(function () {

            var chooseTrs = $("#ggzzffTbody").find("tr");
            var InIds = [];
            chooseTrs.each(function () {
                var tid = $(this).find("td:eq(0)").html();
                InIds.push( $.trim(tid));
            });

            var param = {InIds:InIds.toString()};

            $.post("DININGSYS/archive/itemFile/toPublicProMethodsChoose",param,function (str) {
                var ggMeTypeIndex = layer.open({
                    type: 1,
                    title:'公共制作方法选择',
                    skin: 'layui-layer-rim',
                    area: ['700px', '750px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        var chooseTr = $("#pubProMeT2").find("tr");
                        if(chooseTr.length > 0){
                            var ids = [];
                            chooseTr.each(function () {
                                var tid = $(this).find("td:eq(0)").html();
                                ids.push($.trim(tid));
                            });

                            $.post("DININGSYS/archive/itemFile/getPubProMeByIds",{ids:ids.toString()},function (data) {
                                $("#ggzzffTbody").empty();
                                // data = eval("("+data+")");
                                var trs;
                                for(var i in data){
                                    trs += "<tr><td style='display: none'>"+data[i].id+"</td><td>"+data[i].pmcode+"</td><td>"+data[i].pmname+"</td>" +
                                        "<td>"+booleanJudge(nullJudge(data[i].collectProMoney))+"</td><td>"+nullJudge(data[i].proMoney)+"</td>" +
                                        "<td>"+booleanJudge(nullJudge(data[i].collectProMoneybynum))+"</td><td>"+booleanJudge(nullJudge(data[i].canUpdate))+"</td></tr>"
                                }
                                $("#ggzzffTbody").html(trs);
                                layer.close(ggMeTypeIndex);

                                var destoryTree = $.fn.zTree.getZTreeObj("pubProMeType");
                                if(destoryTree != null){
                                    destoryTree.destroy();
                                }

                            })
                        }

                    }
                });
            })
            
        });

        function nullJudge(data) {
            if(data == null){
                return "";
            }
            return data;
        }

        function booleanJudge(data) {
            if(data == 1){
                return "是";
            }else{
                return "否"
            }
        }

        /*营养数据操作界面*/
        $("#showNutritionBtn").click(function () {
            $.get("DININGSYS/archive/itemFile/showNuritionIndex",function (str) {
                var nuritionIndex = layer.open({
                    type: 1,
                    title:'营养数据操作',
                    skin: 'layui-layer-rim',
                    area: ['1000px', '750px'],
                    content: str,
                    btn:['关闭'],
                    yes:function () {
                        layer.close(nuritionIndex);
                    }
                });
            })
        });

        /*营养数据选择界面*/
        $(".editChooseNutrition").on('click',function () {
            var param = {};

            var chooseTrs = $("#nutritionChooseTab").find("tr");

            if(chooseTrs.length > 0){
                var ids = [];
                chooseTrs.each(function () {
                    var tid = $(this).find("td:eq(0)").html();
                    ids.push($.trim(tid))
                });
                param = {ids:ids.toString()}
            }

            $.post("DININGSYS/archive/itemFile/nutritionChoose",param,function (str) {
                var nuritionChooseIndex = layer.open({
                    type: 1,
                    title:'营养数据选择',
                    skin: 'layui-layer-rim',
                    area: ['520px', '750px'],
                    content: str,
                    btn:['保存','关闭'],
                    yes:function () {
                        var chooseTrs = $("#nutritionTab2").find("tr");
                        if(chooseTrs.length > 0){
                            $("#nutritionChooseTab").html(chooseTrs.clone());
                        }else{
                            $("#nutritionChooseTab").empty();
                        }
                        layer.close(nuritionChooseIndex);
                    }
                });
            })
        });
        
        $("#zccfAdd").on('click',function(){
            	$.get("DININGSYS/archive/itemFile/toZccfChoose",function(str){
                    var addsIndex = layer.open({
                        type: 1,
                        title:'品项组成成分窗口',
                        skin: 'layui-layer-rim',
                        area: ['850px', '90%'],
                        content: str,
                        btn:['确定','关闭'],
                        yes:function () {
                        	  var zccfHtml;
        	                  var rows = $("#grid-table-hava").jqGrid('getRowData'),
        	                  chooseTrIds = [];
        		              if(rows.length > 0){
        		                    for (var i in rows) {
        		                    	var item = {
        		                				id:-rows[i].id,
        		                				inveItemId:rows[i].id,
        		                				inveItemCode:rows[i].itemNo,
        		                				name:rows[i].itemName,
        		                				bName:rows[i].itemTypeName,
        		                				unit:rows[i].unit,
        		                				counter:'1.00'
        		                				
        		                		};
        		                        chooseTrIds.push(item);
        		                  }
        		              }	                
        	                
		            	    $("#zccfTbody tr").each(function() //删除没有项
		            	    {
		            	    		var hava_item = false;
	            	      	    	var td = $(this).find("td:eq(1)");
	              		            for (var i  in chooseTrIds) //删除没有项
	            		            {
	        	      	    	    	if(chooseTrIds[i].inveItemId == (td.text()).trim())
	        	      	    	    	{
	        	      	    	    		hava_item = true;
	        	      	    	    	}
	            		            }
	            	      	    	if(!hava_item)
	            	      	    	{
	            	      	    		 $(this).remove();
	            	      	    	}
        		              });
        		              
		            	      zccfHtml = $("#zccfTbody").html();
        		              for (var i  in chooseTrIds) //增加没有项
        		              {
		            	    		var hava_item = false;
        		            	    $("#zccfTbody tr").each(function()
        		            	    {
        		            	    		var td = $(this).find("td:eq(1)");
	            	      	    	    	if(chooseTrIds[i].inveItemId == (td.text()).trim())
	            	      	    	    	{
	            	      	    	    		hava_item = true;
	            	      	    	    	}
        		            	    });
	            	      	    	if(!hava_item)
	            	      	    	{
	            	      	    		zccfHtml += "<tr> <td style='display: none'>"+
    			                    		   chooseTrIds[i].id+"</td>"+
	    			                    "<td style='display: none'>"+chooseTrIds[i].inveItemId+"</td>"+
	                                    "<td>"+chooseTrIds[i].inveItemCode+"</td>"+
	                                    "<td>"+chooseTrIds[i].name+"</td>"+
	                                    "<td>"+chooseTrIds[i].bName+"</td>"+
	                                    "<td>"+chooseTrIds[i].unit+"</td>"+
	                                    "<td><input type='text' value='"+chooseTrIds[i].counter+"'></td></tr>"; 
	            	      	    	}
        		              }
        		              $("#zccfTbody").empty();
        		              $("#zccfTbody").html(zccfHtml);
        		              initZccfTr();
	                          layer.close(addsIndex);
                           }
                        })
                    });

        	
        });

        $("#ppdlSelect").change(function () {
            $.ajax({
                type:'POST',
                url:'DININGSYS/archive/itemFile/getItemFileSmallType/'+$("#ppdlSelect").val(),
                dataType:'JSON',
                success:function(data){
                    if(data != null){
                        var optionsHtml = "",pxxlId = $("#pxxlId").val();
                        for(var i in data){
                            if(data[i].id == pxxlId){
                                optionsHtml += "<option selected value='"+data[i].id+"'>"+data[i].name+"</option>"
                            }else{
                                optionsHtml += "<option value='"+data[i].id+"'>"+data[i].name+"</option>"
                            }
                        }
                        $("#ppxlId").html(optionsHtml);
                    }

                }
            })
        });

        /*专用制作方法数据列表删除*/
        $("#zccfDel").click(function () {
            var tr = $("#zccfTbody tr").filter(".success");
            if(tr.length < 1){
                layer.alert('请先选择数据', {icon: 5});
                return;
            }
            $(tr).remove();
        });
        $("#zccfClear").click(function(){
        	$("#zccfTbody").empty();
        	
        });
        initZyzfTr();
        initZccfTr();

    }

    /*公共制作方法选择界面初始化*/
    function publicProMethodsInit() {

        initTableTrEvent("pubProMeT2");

        var publicProMethodsSetting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                },
                key: {
                    name : "pmtname"
                }
            },
            view: {
                dblClickExpand: false
            },
            callback: {
                onClick: PublicProMethodsTypeTreeOnClick,
                onAsyncSuccess : initPublicProMethodsTypeTree
            },
            async: {
                enable: true,
                url:"DININGSYS/archive/itemFile/getAllProMethodsType"
            }
        };

        var pubProMeTypeTreeObj = $.fn.zTree.init($("#pubProMeType"), publicProMethodsSetting);

        function initPublicProMethodsTypeTree() {
            var nodes = pubProMeTypeTreeObj.getNodes();
            if(nodes.length > 0){
                pubProMeTypeTreeObj.selectNode(nodes[0]);
            }
            tabelDataChangeByTree(nodes[0]);
        }
        
        function PublicProMethodsTypeTreeOnClick(event, treeId, treeNode) {
            tabelDataChangeByTree(treeNode);
        }

        function tabelDataChangeByTree(node) {

            var ids = [];
            $("#pubProMeT2 tr").each(function () {
                var id = $(this).find("td").eq(0).html();
                ids.push(id);
            });


            $.post("DININGSYS/archive/itemFile/getProMethodsByType",{pmtid:node.id,withOutIds:ids.toString()},function (data) {

                // var data = eval("("+str+")");

                $("#kxids").html(data.length);

                if($("#yxids").html() == ""){
                    $("#yxids").html("0");
                }

                $("#pubProMeT1").empty();

                var trs;
                for(var i in data){
                    trs += "<tr><td style='display: none'>"+data[i].id+"</td><td>"+data[i].pmcode+"</td><td>"+data[i].pmname+"</td><td>"+data[i].description+"</td></tr>"
                }
                $("#pubProMeT1").html(trs);

                initTableTrEvent("pubProMeT1");

            })
        }
        
        $("#chooseAll").click(function () {
           var chooseAllTrs = $("#pubProMeT1").find("tr");
            if(chooseAllTrs.length < 1)return;

            chooseAllTrs.remove();

            $("#pubProMeT2").append(chooseAllTrs);

            changeYxTabCon();

            initTableTrEvent("pubProMeT2");

        });
        
        $("#cancelAll").click(function () {
            var cancelAllTrs = $("#pubProMeT2").find("tr");
            if(cancelAllTrs.length < 1)return;

            cancelAllTrs.remove();

            $("#pubProMeT1").append(cancelAllTrs);

            changeYxTabCon();

            initTableTrEvent("pubProMeT1");
        })
    }

    function initTableTrEvent(tbodyId){
        var tbody = "#"+tbodyId;
        $(tbody).find("tr").dblclick(function () {
            var trObj = $(this);
            trObj.remove();

            if(tbodyId == "pubProMeT1"){
                $("#pubProMeT2").append(trObj);
                initTableTrEvent("pubProMeT2");
            }else{
                $("#pubProMeT1").append(trObj);
                initTableTrEvent("pubProMeT1");
            }
            changeYxTabCon()
        })
    }

    function changeYxTabCon() {
        var xyNum = $("#pubProMeT2").find("tr").length;
        $("#yxids").html(xyNum);
        var kyNum = $("#pubProMeT1").find("tr").length;
        $("#kxids").html(kyNum);
    }


    /*基本营养代码*/
    function initNutritionIndex() {
            $("#grid-table-nutrition").jqGrid({
                url: "DININGSYS/archive/itemFile/selectAllNutrition",
                datatype: "JSON",
                mtype: "GET",
                colNames: ["id","代码","营养名称","标准摄入量","单位","创建时间"],
                colModel: [
                    { name: "id",hidden:true},
                    { name: "code"},
                    { name: "name" },
                    { name: "bzsrl"},
                    { name: "unit",formatter:function (cellvalue, options, rowObject) {
                        if(cellvalue == '1'){
                            return "千焦";
                        }else{
                            return "克";
                        }
                        
                    } },
                    { name: "createTime"}
                ],
                viewrecords: true,
                autoencode: true,
                styleUI : 'Bootstrap',
                autowidth : true
            });
            $("#grid-table-nutrition").jqGrid("setGridWidth","600px",true);
            $("#grid-table-nutrition").jqGrid("setGridHeight","500px",true);


            $(".nuEdit").click(function () {
                var param,title;
                var id = $(this).attr("id");
                if(id == "nuAdd"){
                    param = {};
                    title = "营养数据新增";
                }else{

                    var grid = $("#grid-table-nutrition");
                    var rowId = grid.getGridParam("selrow");

                    if(!rowId){
                        layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        return;
                    }

                    var id = grid.jqGrid('getCell',rowId,'id');

                    param = {id:id};
                    title = "营养数据修改";
                }

                $.get("DININGSYS/archive/itemFile/nutritionEdit",param,function (str) {
                    var nutritionEditIndex = layer.open({
                        type: 1,
                        title:title,
                        skin: 'layui-layer-rim',
                        area: ['500px', '450px'],
                        content: str,
                        btn:['保存','关闭'],
                        yes:function () {
                            if($("#editForm").valid()){
                                $.ajax({
                                    url:'DININGSYS/archive/itemFile/editNurition',
                                    type:'POST',
                                    data:$("#editForm").serialize(),
                                    dataType:'json',
                                    success:function (data) {
                                        if(data.success)
                                        {
                                            layer.close(nutritionEditIndex);
                                            $("#grid-table-nutrition").trigger('reloadGrid');
                                        }
                                    }
                                })
                            }
                        }
                    });
                })
            });

            $("#nuDel").click(function () {
                var grid = $("#grid-table-nutrition");
                var rowId = grid.getGridParam("selrow");

                if(!rowId){
                    layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    return;
                }

                var id = grid.jqGrid('getCell',rowId,'id');

                layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/archive/itemFile/delNurition',
                        data:{ids:id},
                        dataType:'JSON',
                        success:function (data) {
                            if(data.success){
                                $("#grid-table-nutrition").trigger("reloadGrid");
                            }
                        }
                    });

                    layer.close(index);
                });
            })
    }

    /*营养选择界面初始化*/
    function initNutritionChoose() {

        initNutritionTableTrEvent('nutritionTab1');
        initNutritionTableTrEvent('nutritionTab2');

        $("#nutritionChooseAll").click(function () {
            var chooseAllTrs = $("#nutritionTab1").find("tr");
            if(chooseAllTrs.length < 1)return;

            chooseAllTrs.remove();

            $("#nutritionTab2").append(chooseAllTrs);

            changeNutritionYxTabCon();

            initNutritionTableTrEvent("nutritionTab2");

        });

        $("#nutritionCancelAll").click(function () {
            var cancelAllTrs = $("#nutritionTab2").find("tr");
            if(cancelAllTrs.length < 1)return;

            cancelAllTrs.remove();

            $("#nutritionTab1").append(cancelAllTrs);

            changeNutritionYxTabCon();

            initNutritionTableTrEvent("nutritionTab1");
        })

    }

    function initCatBttp()
    {
        var jcrop_api,
            boundx,
            boundy,

            // Grab some information about the preview pane
            $preview = $('#preview-pane'),
            $pcnt = $('#preview-pane .preview-container'),
            $pimg = $('#preview-pane .preview-container img'),

            xsize = $pcnt.width(),
            ysize = $pcnt.height();
        
        $('#target').Jcrop({
          onChange: updatePreview,
          onSelect: updatePreview,
          aspectRatio: xsize / ysize
        },function(){
          var bounds = this.getBounds();
          boundx = bounds[0];
          boundy = bounds[1];
          jcrop_api = this;

          $preview.appendTo(jcrop_api.ui.holder);
        });

        function updatePreview(c)
        {
          if (parseInt(c.w) > 0)
          {
            var rx = xsize / c.w;
            var ry = ysize / c.h;

            $pimg.css({
              width: Math.round(rx * boundx) + 'px',
              height: Math.round(ry * boundy) + 'px',
              marginLeft: '-' + Math.round(rx * c.x) + 'px',
              marginTop: '-' + Math.round(ry * c.y) + 'px'
            });
          }
          $("#width").val(Math.round(c.w));
          $("#height").val(Math.round(c.h));
          $("#x").val(Math.round(c.x));
          $("#y").val(Math.round(c.y));
          $("#boundx").val(boundx);
          $("#boundy").val(boundy);
        }
        //jcrop_api.animateTo([0,0,xsize,ysize]);
    }
    
    /*营养选择 tr事件*/
    function initNutritionTableTrEvent(tbodyId){

        var tbody = "#"+tbodyId;
        $(tbody).find("tr").dblclick(function () {
            var trObj = $(this);
            trObj.remove();

            if(tbodyId == "nutritionTab1"){
                $("#nutritionTab2").append(trObj);
                initNutritionTableTrEvent("nutritionTab2");
            }else{
                $("#nutritionTab1").append(trObj);
                initNutritionTableTrEvent("nutritionTab1");
            }
            changeNutritionYxTabCon()
        })
    }

    /*可选 已选数量 改变*/
    function changeNutritionYxTabCon() {
        var xyNum = $("#nutritionTab2").find("tr").length;
        $("#yxids").html(xyNum);
        var kyNum = $("#nutritionTab1").find("tr").length;
        $("#kxids").html(kyNum);
    }


    /*品项图片 即时预览*/
    function initPicPreview() {
        var pxdt = $("#pxdtFile");
        var pxxt = $("#pxxtFile");
        $(".fileUpCancel").click(function () {
            var itemFileId = $("#hideId").val(),
                eventId = $(this).attr("id");
            if(itemFileId != null && itemFileId != ""){
                if(eventId == "pxdtCancel"){
                    $("#reUploadPxdt").val("0");
                    $("#isDelPxdt").val("1");
                    $("#pxdtFileBd").val("");
                }else{
                    $("#reUploadPxxt").val("0");
                    $("#isDelPxxt").val("1");
                    $("#pxxtFileBd").val("");
                }
            }

            if(eventId == "pxdtCancel"){
                pxdt.replaceWith( pxdt = pxdt.clone( true ) );
                $("#pxdtPreview").attr("src","app/img/no-image-big.png");
            }else{
                pxxt.replaceWith( pxxt = pxxt.clone( true ) );
                $("#pxxtPreview").attr("src","app/img/no-image-small.png");
            }
        });

        $("#pxdtFile").change(function(){
            var itemFileId = $("#hideId").val();
            if(itemFileId != null && itemFileId != ""){
                $("#reUploadPxdt").val("1");
                $("#isDelPxdt").val("0");
                file_change("pxdtFile","pxdtPreview");
            }
        });
        
        $("#pxxtFile").change(function(){
            var itemFileId = $("#hideId").val();
            if(itemFileId != null && itemFileId != ""){
                $("#reUploadPxxt").val("1");
                $("#isDelPxxt").val("0");
                file_change("pxxtFile","pxxtPreview");
            }
        });
        
        function file_change(fileId,preId){
    		var value = $("#"+fileId).val();
    		if(value == '')
    		{
    			alert(value);
    			return;
    		}
    		if("" != value){
    			var inx  = value.lastIndexOf('.');
    			var lastName = value.substring(inx);
    			if(lastName!='.jpg' && lastName != '.png'){
    				alert("图标只能是jpg或者png");
    				return;
    			}

                var form = $("#pxtpFormId")[0];

                var formData = new FormData(form);

                $.ajax({
                    type:'POST',
                    url:'DININGSYS/archive/itemFile/saveItemIcon',
                    data:formData,
                    dataType:'JSON',
                    contentType: false,
                    processData: false,
                    success:function(data){
                        if(data.success){
                            $.post("DININGSYS/archive/itemFile/toCatLafbttp",{imageName:data.successMsg},function(str){
                                var catIndex = layer.open({
                                    type: 1,
                                    title:'编辑图片',
                                    skin: 'layui-layer-rim',
                                    area: ['800px', '500px'],
                                    content: str,
                                    btn:['确定','关闭'],
                                    yes:function () {
                                        catBttp(preId,fileId,catIndex);
                                    }
                                });
                            });
                        }else{
                            layer.alert("图片上传错误",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                        }
                    },
                    error:function (data, status, e){
                        layer.alert("图片上传错误",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    }
                })
    		}
    	}
        
        function catBttp(preId,fileId,catIndex)
        {
        	jQuery.ajax({
    			url : "DININGSYS/archive/itemFile/catNewsBttp",
    			data : $('#myform').serialize(),
    			type : "POST",
    			error : function() {
    				layer.alert("图片编辑失败!",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
    			},
    			success : function(data) {
    				if (data.success) {
    					var imagename =data.successMsg;
    					$("#"+preId).attr('src','itemconcat/'+imagename);
    					$("#"+fileId+"Bd").val('itemconcat/'+imagename);
    				} else {
    					layer.alert("图片编辑错误",{title :'提示',icon: 0, skin: 'layer-ext-moon'});
    				}
    				layer.close(catIndex);



    				if(fileId == 'pxdtFile'){
                        var pxdtFileHtml = "<input class='file_input pxpciUpload' id='pxdtFile' type='file' name='pxdtFile'/>";
                        $("#pxdtFile").replaceWith(pxdtFileHtml);

                        $("#pxdtFile").change(function(){
                            var itemFileId = $("#hideId").val();
                            if(itemFileId != null && itemFileId != ""){
                                $("#reUploadPxdt").val("1");
                                $("#isDelPxdt").val("0");
                                file_change("pxdtFile","pxdtPreview");
                            }
                        });

                    }else if(fileId == 'pxxtFile'){
                        var pxxtFileHtml = "<input class='file_input pxpciUpload' id='pxxtFile' type='file' name='pxxtFile'/>";
                        $("#pxxtFile").replaceWith(pxxtFileHtml);
                        $("#pxxtFile").change(function(){
                            var itemFileId = $("#hideId").val();
                            if(itemFileId != null && itemFileId != ""){
                                $("#reUploadPxxt").val("1");
                                $("#isDelPxxt").val("0");
                                file_change("pxxtFile","pxxtPreview");
                            }
                        });
                    }

    			}
    		});
        }

    }
    
    function initTcPage() {
        /*必选品项 table*/
        $("#tcBxPx").jqGrid({
            url: '',
            datatype: "local",
            colNames: ["itemFileId","编号","品项","数量","标准价格","数量x标准价格","品项加价","按数量加价"],
            colModel: [
                { name: "itemFileId",hidden:true,align:'center'},
                { name: "num",align:'center'},
                { name: "name",align:'center'},
                { name: "count",editable: true,align:'center',editoptions: {type:"number"}},
                { name: "standardPrice",align:'center'},
                { name: "countXPrice",align:'center'},
                { name: "pxjj",editable: true,align:'center',editoptions: {type:"number"}},
                { name: "sljj",align:'center',editable: true,formatter:function (cellValue, options, rowObject) {
                    if(typeof cellValue == 'undefined' || cellValue == "0"){
                        return '否';
                    }else if(cellValue == "1"){
                        return '是';
                    }
                    return cellValue;
                },edittype: "custom",editoptions: {
                        custom_value: getFreightElementValue,
                        custom_element:createFreightEditElement,
                }},
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            height:140,
            footerrow: true,
            onSelectRow:changeBxThpxData,
            ondblClickRow: tableInlineEdit,
            loadComplete: function () {
                var $self = $(this),
                    sum = $self.jqGrid("getCol", "standardPrice", false, "sum"),
                    countXPriceSum = $self.jqGrid("getCol", "countXPrice", false, "sum"),
                    pxjjSum = $self.jqGrid("getCol", "pxjj", false, "sum"),
                    countSum = $self.jqGrid("getCol", "count", false, "sum"),
                    rowLength = $self.jqGrid('getRowData');
                $self.jqGrid("footerData", "set", {num:rowLength.length,count:countSum,standardPrice: sum,countXPrice:countXPriceSum,pxjj:pxjjSum});
            }

        });

        /*数量加价行内编辑*/
        function createFreightEditElement(value, editOptions) {
            var div =$("<div style='margin-top:5px'></div>");
            var label = $("<label class='radio-inline'></label>");
            var radio = $("<input>", { type: "radio", cvalue:"否", value: "0", name: "sljj", checked: value == '否' });
            label.append(radio).append("否");
            var label1 = $("<label class='radio-inline'></label>");
            var radio1 = $("<input>", { type: "radio",cvalue:"是", value: "1", name: "sljj", checked: value == '是' });
            label1.append(radio1).append("是");
            div.append(label).append(label1);
            return div;
        }

        function getFreightElementValue(elem, oper, value) {
            if (oper === "set") {
                var radioButton = $(elem).find("input:radio[cvalue='" + value + "']");
                if (radioButton.length > 0) {
                    radioButton.prop("checked", true);
                }
            }
            if (oper === "get") {
                return $(elem).find("input:radio:checked").attr("cvalue");
            }
        }

        /*可选品项 table*/
        $("#kxpxTable").jqGrid({
            url: '',
            datatype: "local",
            colNames: ["itemFileId","品项","数量","品项加价","按数量加价"],
            colModel: [
                { name: "itemFileId",hidden:true,align:'center'},
                { name: "name",align:'center'},
                { name: "count",editable: true,align:'center',editoptions: {type:"number"}},
                { name: "pxjj",editable: true,align:'center',editoptions: {type:"number"}},
                { name: "sljj",align:'center',editable: true,formatter:function (cellValue, options, rowObject) {
                    if(typeof cellValue == 'undefined' || cellValue == "0"){
                        return '否';
                    }else if(cellValue == "1"){
                        return '是';
                    }
                    return cellValue;
                },edittype: "custom",editoptions: {
                    custom_value: getFreightElementValue,
                    custom_element:createFreightEditElement,
                }}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            ondblClickRow: tableInlineEdit,
            height:140
        });

        /*必选品项的替换品项 table*/
        $("#thpxTable").jqGrid({
            url: '',
            datatype: "local",
            colNames: ["itemFileId","品项","数量","替换加价","按数量加价"],
            colModel: [
                { name: "itemFileId",hidden:true,align:'center'},
                { name: "name",align:'center'},
                { name: "count",editable: true,align:'center',editoptions: {type:"number"}},
                { name: "thjj",align:'center',editable: true,editoptions: {type:"number"}},
                { name: "sljj",align:'center',editable: true,formatter:function (cellValue, options, rowObject) {
                    if(typeof cellValue == 'undefined' || cellValue =="0"){
                        return '否';
                    }else if(cellValue == "1"){
                        return '是';
                    }
                    return cellValue;
                },edittype: "custom",editoptions: {
                    custom_value: getFreightElementValue,
                    custom_element:createFreightEditElement,
                }}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            ondblClickRow: tableInlineEdit,
            height:140
        });

        /*可选品项大类数量限定 table*/
        $("#kxpxdlslxd").jqGrid({
            url: '',
            datatype: "local",
            colNames: ["id","品项大类代码","品项大类名称","最大限定数量"],
            colModel: [
                { name: "id",hidden:true,align:'center'},
                { name: "num",align:'center'},
                { name: "name",align:'center'},
                { name: "zdxdsl",align:'center',editable: true,editoptions: {type:"number"}}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            ondblClickRow: tableInlineEdit,
            height:450
        });

        function changeBxThpxData(rowid,status,e) {
            var data = bxpxThpxMap[rowid];
            $("#thpxTable").jqGrid("clearGridData");
            if(data){
                $("#thpxTable").addRowData(rowid.itemFileId,data);
            }
        }

        /*必选品项5个按钮事件*/
        $(".bxpxBtn").click(function () {

            var objId = $(this).attr("id"),
                bodyTrs = $("#tcBxpxTbody").find("tr"),
                selectTr = bodyTrs.filter(".success");
             if(objId == "bxpxAdd"){
                var yxRows = $("#tcBxPx").jqGrid('getRowData'),
                    tcPxYxIds = [];
                if(yxRows.length > 0){
                    for (var i in yxRows) {
                        tcPxYxIds.push(yxRows[i].itemFileId)
                    }
                }
                $.post('DININGSYS/archive/itemFile/toTcPxChoose',{id:$("#selectedItemId").val(),tcYxPxIds:tcPxYxIds.toString()},function (str) {
                    pxchooseLayer('必选品项选择',str,'tcBxPx');
                })
            }else if(objId == "bxpxRemove"){
                tcTableDataRemove('tcBxPx');
                $("#tcBxPx").trigger("reloadGrid");
                /*删除必选品项替换品项的值*/
                var rowKey = $("#tcBxPx").jqGrid('getGridParam',"selrow");
                 delete bxpxThpxMap[rowKey];
                $("#thpxTable").jqGrid("clearGridData");
            }else if(objId == "bxpxCancel"){
                tcTableDataClear('tcBxPx');
                /*清空必选品项替换品项的值*/
                bxpxThpxMap = {};
                $("#thpxTable").jqGrid("clearGridData");
            }
        });


        /*可选品项事件注册*/
        $(".kxpxBtn").click(function () {
            var objId = $(this).attr("id");
            if(objId == "kxpxAdd"){
                var kxRows = $("#kxpxTable").jqGrid('getRowData'),
                    tcKxYxIds = [];
                if(kxRows.length > 0){
                    for (var i in kxRows) {
                        tcKxYxIds.push(kxRows[i].itemFileId)
                    }
                }
                $.post('DININGSYS/archive/itemFile/toTcPxChoose',{id:$("#selectedItemId").val(),tcYxPxIds:tcKxYxIds.toString()},function (str) {
                    pxchooseLayer('可选品项选择',str,'kxpxTable');
                })
            }else if(objId == "kxpxRemove"){
                tcTableDataRemove('kxpxTable');

            }else if(objId == "kxpxCancel"){
                tcTableDataClear('kxpxTable');
            }
        });

        /*必选品项的替换品项*/
        $(".thpxBtn").click(function () {
            var objId = $(this).attr("id");
            if(objId == "thpxAdd"){
                var tcbxids = $("#tcBxPx").jqGrid('getGridParam',"selrow");
                if(!tcbxids){
                    layer.alert('请先选择一条必选品数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    return;
                }
                var kxRows = $("#thpxTable").jqGrid('getRowData'),
                    tcKxYxIds = [];
                if(kxRows.length > 0){
                    for (var i in kxRows) {
                        tcKxYxIds.push(kxRows[i].itemFileId)
                    }
                }
                $.post('DININGSYS/archive/itemFile/toTcPxChoose',{id:$("#selectedItemId").val(),tcYxPxIds:tcKxYxIds.toString()},function (str) {
                    pxchooseLayer('必选品项替换品项选择',str,'thpxTable');
                })
            }else if(objId == "thpxRemove"){
                tcTableDataRemove('thpxTable');
            }else if(objId == "thpxCancel"){
                tcTableDataClear('thpxTable');
            }
        });

        /*套餐类型改变事件*/
        $("input:radio[name='tcType']").change(function () {
            var typeVal = $(this).val();
            if(typeVal == "1"){
                $(".thpxBtn").attr("disabled","disabled");
                $(".kxpxBtn").attr("disabled","disabled");
                $("#hideLi").hide();
            }else{
                $(".thpxBtn").removeAttr("disabled");
                $(".kxpxBtn").removeAttr("disabled");
                $("#hideLi").show();
            }
        });

        var tcId = $("#tcId").val();

        /*大类数量限定 表格数据加载*/
        $.ajax({
            type:'POST',
            url:'DININGSYS/archive/itemFile/getAllBigItemFileType',
            data:{packageId:tcId},
            dataType:'JSON',
            async:false,
            success:function(data){
                for(var i in data){
                    $("#kxpxdlslxd").addRowData(data[i].id,data[i]);
                }
            }
        });


        if(tcId != null && tcId != ""){
            /*套餐数据加载*/
            $.ajax({
                type:'POST',
                url:'DININGSYS/archive/itemFile/getPackageTableInfo/'+tcId,
                dataType:'JSON',
                async:false,
                success:function(data){
                    /*必选项表格数据*/
                    for(var i in data['bxInfo']){
                        $("#tcBxPx").addRowData(data['bxInfo'][i].itemFileId,data['bxInfo'][i]);
                    }
                    var footData = data['bmap'][0];
                    if(data['bmap'].length > 0){
                        $("#tcBxPx").jqGrid("footerData", "set", {num:footData['package_sum'],count:footData['package_amount_sum'],standardPrice: footData['package_standardPrice_sum'],countXPrice:footData['package_standardPrice_sum_num'],pxjj:footData['item_file_addPrice']});
                        $("#tcBxPx").trigger("reloadGrid");
                    }
                    /*可选项表格数据*/
                    for(var i in data['kxInfo']){
                        $("#kxpxTable").addRowData(data['kxInfo'][i].itemFileId,data['kxInfo'][i]);
                    }
                    /*大类数量限定表格数据*/
                    for(var i in data['slxdInfo']){
                        $("#kxpxdlslxd").addRowData(data['slxdInfo'][i].itemFileId,data['slxdInfo'][i]);
                    }

                    /*必选品项的替换品项集合*/
                    for(var key in data['thpxInfo']){
                        bxpxThpxMap[key]=data['thpxInfo'][key];
                    }
                }
            })
        }

    }

    function tcpxChoosePageInit() {
        /*必选品项可选内容表格*/
        $("#bxpxChooseTable").jqGrid({
            url: '',
            datatype: "local",
            colNames: ["itemFileId","编号","名称","单位","标准价格","说明"],
            colModel: [
                { name: "itemFileId",hidden:true},
                { name: "num"},
                { name: "name"},
                { name: "unit"},
                { name: "standardPrice"},
                { name: "sm"}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            height:220,
            ondblClickRow:selectRowToYx
        });

        /*必选品项已选表格*/
        $("#bxpxYxChooseTable").jqGrid({
            url: 'DININGSYS/archive/itemFile/getItemFileYxInIds?inIds='+$("#tcYxPxIds").val(),
            datatype: "JSON",
            colNames: ["itemFileId","编号","名称","单位","标准价格","说明"],
            colModel: [
                { name: "itemFileId",hidden:true},
                { name: "num"},
                { name: "name"},
                { name: "unit"},
                { name: "standardPrice"},
                { name: "sm"}
            ],
            viewrecords: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            height:200,
            ondblClickRow:selectRowToKx
        });


        /*品项分类树*/
        var tcpxChooseSetting = {
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
                url:"DININGSYS/archive/itemFile/getAllItemFileType?flag=1"
            }
        };

        $.fn.zTree.init($("#tcPxChooseTree"), tcpxChooseSetting);

        function tcPageInitTree() {
            var treeObj = $.fn.zTree.getZTreeObj("tcPxChooseTree");

            var node = treeObj.getNodeByParam("id",0,null);

            treeObj.selectNode(node);

            treeObj.expandNode(node,true);

            tcPxChooseChangeTable(node);

        }
        
        function selectRowToYx(rowid,iRow,iCol,e) {
            var grid = $('#bxpxChooseTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#bxpxYxChooseTable").addRowData(rowid,myCellData);

            changeTcPxTxTabCon();
        }

        function selectRowToKx(rowid,iRow,iCol,e) {
            var grid = $('#bxpxYxChooseTable');
            var myCellData = grid.jqGrid('getRowData', rowid);
            grid.delRowData(rowid);

            $("#bxpxChooseTable").addRowData(myCellData.itemFileId,myCellData);

            changeTcPxTxTabCon();
        }

        function tcPxTreeClick(event, treeId, treeNode) {
            tcPxChooseChangeTable(treeNode);
        }

        /*品项*/
        /*table修改为jqgrid*/
        function tcPxChooseChangeTable(node) {
            var id = node.id,
                selectedTcId = $("#selectedTcId").val(),
                tcYxPxTable = $("#bxpxYxChooseTable"),
                tcYxPxTableRows = tcYxPxTable.jqGrid('getRowData'),
                tcPxNotInIds = [];

            if(tcYxPxTableRows.length > 0){
                for(var i in tcYxPxTableRows){
                    tcPxNotInIds.push(tcYxPxTableRows[i].itemFileId);
                }
            }
            $.get('DININGSYS/archive/itemFile/tcPxChooseData',{id:id,selectedTcId:selectedTcId,tcPxNotInIds:tcPxNotInIds.toString()},function (data) {
                jQuery("#bxpxChooseTable").jqGrid("clearGridData");
                for(var i=0;i < data.length;i++){
                    $("#bxpxChooseTable").jqGrid('addRowData',i+1,data[i]);
                }
                $("#tcPxChooseKxids").html(data.length);
            })
        }

        $("#tcPxChooseAll").click(function () {
            var rowObj = $("#bxpxChooseTable").jqGrid('getRowData');
            jQuery("#bxpxChooseTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#bxpxYxChooseTable").addRowData(rowObj[i].itemFileId,rowObj[i]);
            }
            changeTcPxTxTabCon();
        });

        $("#tcPxCancelAll").click(function () {
            var rowObj = $("#bxpxYxChooseTable").jqGrid('getRowData');
            jQuery("#bxpxYxChooseTable").jqGrid("clearGridData");
            for(var i = 0;i < rowObj.length;i++){
                $("#bxpxChooseTable").addRowData(rowObj[i].itemFileId,rowObj[i]);
            }
            changeTcPxTxTabCon();
        });


        function changeTcPxTxTabCon() {
            var kxLenth = $("#bxpxChooseTable").jqGrid('getRowData');
            $("#tcPxChooseKxids").html(kxLenth.length);
            var yxLength = $("#bxpxYxChooseTable").jqGrid('getRowData');
            $("#tcpxChooseYxids").html(yxLength.length);
        }

        /*套餐必选品项弹出 end*/
    }

    /*套餐页面，品项选择弹出页面*/
    function pxchooseLayer(title,content,tableId){
        var table = "#"+tableId;
        var tcbxpxndex = layer.open({
            type: 1,
            title:title,
            skin: 'layui-layer-rim',
            area: ['700px', '750px'],
            content: content,
            btn:['保存','关闭'],
            yes:function () {
                var yxRows = $("#bxpxYxChooseTable").jqGrid('getRowData'),
                    chooseTrIds = [];
                if(yxRows.length > 0){
                    for (var i in yxRows) {
                        chooseTrIds.push(yxRows[i].itemFileId);
                    }
                }
                if(yxRows.length > 0){
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/archive/itemFile/getItemFileInIds',
                        data:{inIds:chooseTrIds.toString()},
                        dataType:'JSON',
                        async:false,
                        success:function(data){
                            /*品项选择之后，在插入表格之前，对原表格数据进行判断，如果有则使用原表格数据*/
                            var oldRowData = $(table).jqGrid("getRowData");
                            if(data.length != 0){
                                for(var i = 0;i < oldRowData.length;i++){
                                    for(var j = 0;j < data.length;j++){
                                        if(oldRowData[i].itemFileId == data[j].itemFileId){
                                            data[j] = oldRowData[i];
                                        }
                                    }
                                }
                            }

                            $(table).jqGrid('clearGridData');
                            for (var i in data) {
                                data[i].sljj = "否";
                                data[i].count = "1";
                                $(table).addRowData(data[i].itemFileId,data[i]);
                                //$(table).trigger('reloadGrid');
                            }

                            if(tableId == "thpxTable"){
                                var tcbxids = $("#tcBxPx").jqGrid('getGridParam',"selrow");
                                /*必选品项替换品项数据存储*/
                                bxpxThpxMap[tcbxids] = data;
                            }

                        }
                    })
                }else{
                    $(table).jqGrid('clearGridData');
                    if(tableId == "tcBxPx"){
                        $(table).trigger('reloadGrid');
                    }
                }
                layer.close(tcbxpxndex);
            }
        });
    }

    /*套餐页面表格删除事件*/
    function tcTableDataRemove(tableId){
        var table = "#"+tableId;
        var grid = $(table);
        var rowKey = grid.jqGrid('getGridParam',"selrow");
        if (!rowKey){
            layer.alert('请选择一条数据进行删除！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        grid.delRowData(rowKey);
    }

    /*套餐页面表格清空事件*/
    function tcTableDataClear(tanbleId) {
        var table = "#"+tanbleId,
            grid = $(table);
        grid.jqGrid("clearGridData");
        grid.trigger("reloadGrid");
    }

    /*行内编辑*/
    var lastSelection = false,
        kxlastSelection = false,
        thlastSelection = false,
        kxpxxdsllastSelection = false;

    function varTrueOrFalse(obj,bo){
        if(obj == "tcBxPx"){
            lastSelection = bo;
        }else if(obj == "kxpxTable"){
            kxlastSelection = bo;
        }else if(obj == "thpxTable"){
            thlastSelection = bo;
        }else{
            kxpxxdsllastSelection = bo;
        }
    }

    function checkVarBo(obj){
        if(obj == "tcBxPx"){
           return lastSelection;
        }else if(obj == "kxpxTable"){
            return kxlastSelection;
        }else if(obj == "thpxTable"){
            return thlastSelection;
        }else{
            return kxpxxdsllastSelection;
        }
    }

    function tableInlineEdit(id) {
        var tableId = $(this).attr("id");

        if(checkVarBo(tableId)){
            layer.alert('请先保存正在编辑的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        if (id) {
            varTrueOrFalse(tableId,true);
            var grid = $(this);
            grid.jqGrid('restoreRow',id);
            grid.jqGrid('editRow',id,  {keys: true,aftersavefunc:function () {
                varTrueOrFalse(tableId,false);
                if(tableId == "tcBxPx"){
                    grid.jqGrid('setSelection',id);
                    var rowData = grid.jqGrid('getRowData',id);
                    if(rowData.count == "" || rowData.count < 1){
                        rowData.count = 1;
                    }
                    if(rowData.sljj == '是'){
                        var sl = rowData.count,
                            sljg = 0;
                        if(sl && sl != 0){
                            sljg = sl * parseFloat(rowData.standardPrice);
                        }
                        rowData.countXPrice = sljg;
                        grid.jqGrid('setRowData', id, rowData);
                    }else{
                        rowData.countXPrice = null;
                        grid.jqGrid('setRowData', id, rowData);
                    }

                    var sum = grid.jqGrid("getCol", "standardPrice", false, "sum"),
                        countXPriceSum = grid.jqGrid("getCol", "countXPrice", false, "sum"),
                        pxjjSum = grid.jqGrid("getCol", "pxjj", false, "sum"),
                        countSum = grid.jqGrid("getCol", "count", false, "sum"),
                        rowLength = grid.jqGrid('getRowData');
                    grid.jqGrid("footerData", "set", {num:rowLength.length,count:countSum,standardPrice: sum,countXPrice:countXPriceSum,pxjj:pxjjSum});
                }else if(tableId == "thpxTable"){
                    grid.jqGrid('setSelection',id);
                    var rowData = grid.jqGrid('getRowData',id);
                    if(rowData.count == "" || rowData.count < 1){
                        rowData.count = 1;
                    }
                    grid.jqGrid('setRowData', id, rowData);
                    var selectBxpxRow = $("#tcBxPx").jqGrid('getGridParam',"selrow"),
                        chooseThpxBxPxId = $("#tcBxPx").jqGrid('getCell',selectBxpxRow,'itemFileId'),
                        thRow = grid.jqGrid('getGridParam',"selrow"),
                        thId = grid.getRowData(thRow)['itemFileId'];
                    var rowData = grid.jqGrid('getRowData');
                    bxpxThpxMap[chooseThpxBxPxId] = rowData;
                    chooseThpxBxPxId = null;
                }else if(tableId == "kxpxTable"){
                    grid.jqGrid('setSelection',id);
                    var rowData = grid.jqGrid('getRowData',id);
                    if(rowData.count == "" || rowData.count < 1){
                        rowData.count = 1;
                    }
                    var rowData = grid.jqGrid('getRowData',id);
                    if(rowData.count == "" || rowData.count < 1){
                        rowData.count = 1;
                    }
                    grid.jqGrid('setRowData', id, rowData);
                }
            }});
        }
    }
    /*行内编辑 END*/

    return {
        /*首页树 初始化*/
        ztreeInit:function () {
            zTreeInit();
        },
        /*首页初始化*/
        pageInit:function () {
            pagerInit();
        },
        /*品项档案编辑页面初始化*/
        itemFileEditPageInit:function () {
            itemFileEditPageInit();
        },
        /*专用方法初始化*/
        publicProMethodsInit:function () {
            publicProMethodsInit();
        },
        /*基本营养页面初始化*/
        initNutritionIndex:function () {
            initNutritionIndex();
        },
        /*营养选择页面初始化*/
        initNutritionChoose:function () {
            initNutritionChoose();
        },
        /*图片预览*/
        initPicPreview:function () {
            initPicPreview();
        },
        /*套餐首页*/
        initTcPage:function () {
            initTcPage();
        },
        /*套餐品项选择页面初始化*/
        tcpxChoosePageInit:function () {
            tcpxChoosePageInit();
        },
        initCatBttp:function (){
        	initCatBttp();
        }
    }
}();