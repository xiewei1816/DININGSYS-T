/**
 * Created by zhshuo on 16-11-25.
 */
var empAreaSta = function () {
    function resize() {
        $("#contentDiv").height($(window).height()-30);
        $("#contentDiv").width($(window).width()-150);

        $("#leftDiv").height($(window).height()-150);
        $("#rightDiv").height($(window).height()-150);

        $("#empAreaStaEmpTable").jqGrid("setGridWidth",$("#leftDiv").width()-20,true);
        $("#empAreaStaEmpTable").jqGrid("setGridHeight",$("#leftDiv").height(),true);

        $("#empAreaStaAreaTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#empAreaStaAreaTable").jqGrid("setGridHeight",$("#rightDiv").height(),true);
    }

    function pageInit(){
        $("#empAreaStaEmpTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","员工编号","员工姓名","职务"],
            colModel: [
                { name: "id",hidden:true},
                { name: "empCode",width:100},
                { name: "empName",width:100},
                { name: "job" ,width:100}
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            onSelectRow:empTableSelect
        });

        $("#empAreaStaEmpTable").jqGrid("setGridWidth",$("#leftDiv").width()-20,true);
        $("#empAreaStaEmpTable").jqGrid("setGridHeight",$("#leftDiv").height(),true);

        $("#empAreaStaAreaTable").jqGrid({
            url: "",
            datatype: "local",
            mtype: "GET",
            colNames: ["id","消费区域编号","消费区域名称","启用"],
            colModel: [
                { name: "id",hidden:true},
                { name: "areaCode"},
                { name: "areaName"},
                { name: "isOpen",formatter:checkBoxFormate }
            ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true
        });

        $("#empAreaStaAreaTable").jqGrid("setGridWidth",$("#rightDiv").width()-20,true);
        $("#empAreaStaAreaTable").jqGrid("setGridHeight",$("#rightDiv").height(),true);

        $(window).resize(function () {
            resize();
        });

        $("#allOpen").click(function () {
            $("#empAreaStaAreaTable").find("input[type='checkbox']").each(function () {
                $(this).prop("checked","checked");
            });
            var rowId =  $("#empAreaStaEmpTable").getGridParam("selrow"),
                empCode = $("#empAreaStaEmpTable").jqGrid('getCell',rowId,'empCode');
            editEmpAreaStaWithAll(empCode,1);
        });

        $("#allClose").click(function () {
            $("#empAreaStaAreaTable").find("input[type='checkbox']").each(function () {
                $(this).prop("checked","");
            });
            var rowId =  $("#empAreaStaEmpTable").getGridParam("selrow"),
                empCode = $("#empAreaStaEmpTable").jqGrid('getCell',rowId,'empCode');
            editEmpAreaStaWithAll(empCode,0);
        });

        $("#masterCheck").change(function () {
            var isOpen;
            if($(this).is(":checked"))isOpen = 1;else isOpen = 0;

            $.ajax({
                type:'POST',
                url:'DININGSYS/permission/empAreaSta/editMasterEmpAreaSta/'+isOpen,
                dataType:'JSON',
                success:function(data){
                    if(!data.success){
                        layer.msg("error",{
                            time:1000
                        })
                    }
                }
            })
        });

        pageDataInit();

    }

    function pageDataInit(){
        $.ajax({
            type:'POST',
            url:'DININGSYS/permission/empAreaSta/selectAllEmp',
            dataType:'JSON',
            success:function(data){
                for(var i in data){
                    $("#empAreaStaEmpTable").jqGrid('addRowData',data[i]['id'],data[i]);
                }
            }
        }).done(function () {
            var allRowData = $("#empAreaStaEmpTable").jqGrid("getRowData");
            if(allRowData.length > 0){
                var firstRowData = allRowData[0];
                $("#empAreaStaEmpTable").jqGrid('setSelection',firstRowData['id']);
            }
        })
    }

    function empTableSelect(rowid, status, e) {
        var empCode = $("#empAreaStaEmpTable").jqGrid('getCell',rowid,'empCode');

        $.ajax({
            type:'POST',
            url:'DININGSYS/permission/empAreaSta/selectAllEmpAreaSta',
            data:{empCode:empCode},
            dataType:'JSON',
            success:function(data){
                $("#empAreaStaAreaTable").jqGrid('clearGridData');
                for(var i in data){
                    $("#empAreaStaAreaTable").jqGrid('addRowData',data[i]['id'],data[i]);
                }
                tableCheckBoxChange();
            }
        })

    }

    function checkBoxFormate(cellValue, options, rowObject) {
        var areaCode = rowObject['areaCode'];
        if(cellValue === 1){
            return "<input type='checkbox' value='"+areaCode+"' checked>"
        }else{
            return "<input type='checkbox' value='"+areaCode+"'>"
        }
    }

    function tableCheckBoxChange() {
        $("#empAreaStaAreaTable").find("input[type='checkbox']").change(function () {
           var rowId =  $("#empAreaStaEmpTable").getGridParam("selrow"),
               empCode = $("#empAreaStaEmpTable").jqGrid('getCell',rowId,'empCode'),
               areaCode = $(this).val(),isOpen;
           if($(this).is(":checked")) {
               isOpen = 1;
           }else{
               isOpen = 0;
           }
            $.ajax({
                type:'POST',
                url:'DININGSYS/permission/empAreaSta/editEmpAreaStaPermission',
                data:{empCode:empCode,areaCode:areaCode,isOpen:isOpen,},
                dataType:'JSON',
                success:function(data){
                    if(!data.success){
                        layer.msg("error",{
                            time:1000
                        })
                    }
                }
            })
        })
    }

    function editEmpAreaStaWithAll(empCode, isOpen) {
        $.ajax({
            type:'POST',
            url:'DININGSYS/permission/empAreaSta/editEmpAreaStaWithAll',
            data:{empCode:empCode,isOpen:isOpen},
            dataType:'JSON',
            success:function(data){
                if(!data.success){
                    layer.msg("error",{
                        time:1000
                    })
                }
            }
        })
    }

    return{
        resize:function () {
            resize();
        },
        pageInit:function () {
            pageInit();
        }
    }

}();
