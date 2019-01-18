<%--
  Created by zhshuo.
  Date: 2017-03-13
  Time: 14:47
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var inArray = new Array(),
        length = 0;
    jQuery(document).ready(function () {
        $("#checkBillItemTable").jqGrid({
            url: "",
            datatype: "local",
            colNames: ['品项', '价格','数量','注','制作费用','小计'],
            colModel: [
                { name: 'itemFileName'},
                { name: 'itemFinalPrice',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'itemFileNumber',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'notes',formatter:function (cellValue,option,rowObj) {
                    if(cellValue == 1){
                        return "开单客位自增";
                    }else if(cellValue == 2){
                        return "加单";
                    }else if(cellValue == 3){
                        return "赠单";
                    }else if(cellValue == 4){
                        return "退单";
                    }else if(cellValue == 5){
                        return "减少人数";
                    }else if(cellValue == 6){
                        return "增加人数";
                    }else if(cellValue == 7){
                        return "更换客位";
                    }else if(cellValue == 8){
                        return "单品转台";
                    }
                }},
                { name: 'productionCosts',formatter:'number',formatoptions:{decimalPlaces: 2 }},
                { name: 'subtotal',formatter:'number',formatoptions:{decimalPlaces: 2 }}
            ],
            styleUI : 'Bootstrap',
            footerrow:true,
        })

        $("#checkBillItemTable").jqGrid("setGridWidth",$("#serviceDiv").width(),true);
        $("#checkBillItemTable").jqGrid("setGridHeight",$(window).height()-400,true);

        $(window).resize(function () {
            $("#checkBillItemTable").jqGrid("setGridWidth",$("#serviceDiv").width(),true);
            $("#checkBillItemTable").jqGrid("setGridHeight",$(window).height()-400,true);
        })

        inArray = ${inList};
        length = inArray.length;

        $("#firstPage").click(function () {
            $("#nowPage").val(1);
            var nowPage = Number($("#nowPage").val());

            if(nowPage == 1){
                $("#firstPage").attr("disabled","disabled");
                $("#previousPage").attr("disabled","disabled");
            }

            if(length == 1){
                $("#nextPage").attr("disabled","disabled");
                $("#lastPage").attr("disabled","disabled");
                $("#firstPage").attr("disabled","disabled");
                $("#previousPage").attr("disabled","disabled");
            }

            tableDataFill(1);
        })

        $("#lastPage").click(function () {
            $("#nextPage").attr("disabled","disabled");
            $("#lastPage").attr("disabled","disabled");
            $("#firstPage").removeAttr("disabled");
            $("#previousPage").removeAttr("disabled");

            $("#nowPage").val(length);

            tableDataFill(length);
        })

        $("#nextPage").click(function () {
            var nextPage = Number($("#nowPage").val()) + 1;

            $("#nowPage").val(nextPage);

            if(nextPage == length){
                $("#nextPage").attr("disabled","disabled");
                $("#lastPage").attr("disabled","disabled");
            }
            $("#firstPage").removeAttr("disabled");
            $("#previousPage").removeAttr("disabled");

            tableDataFill(nextPage);
        })

        $("#nowPage").bind('keyup input', function () {
            nowPageChange();
        });

        $("#nowPage").change(function () {
            nowPageChange();
        })

        $("#previousPage").click(function () {
            var previousPage = Number($("#nowPage").val()) - 1;

            $("#nowPage").val(previousPage);

            if(previousPage == 1){
                $("#firstPage").attr("disabled","disabled");
                $("#previousPage").attr("disabled","disabled");
            }

            $("#lastPage").removeAttr("disabled");
            $("#nextPage").removeAttr("disabled");

            tableDataFill(previousPage);
        })

        if(length > 0){
            $("#nowPage").attr("max",length);
            $("#nowPage").val(1);
            $("#firstPage").trigger("click");
        }

    })

    function nowPageChange() {
        var nowPage = Number($("#nowPage").val());
        if(nowPage > $("#pageCount").val()){
            layer.msg("请输入合法的页码",{time:1500});
            $("#nowPage").val(length);
        }else{
            if(nowPage == 1){
                $("#firstPage").attr("disabled","disabled");
                $("#previousPage").attr("disabled","disabled");
                $("#nextPage").removeAttr("disabled");
                $("#lastPage").removeAttr("disabled");
            }

            if(nowPage < length && nowPage > 1){
                $("#firstPage").removeAttr("disabled");
                $("#previousPage").removeAttr("disabled");
                $("#nextPage").removeAttr("disabled");
                $("#lastPage").removeAttr("disabled");
            }

            if(nowPage == length){
                $("#nextPage").attr("disabled","disabled");
                $("#lastPage").attr("disabled","disabled");
                $("#firstPage").removeAttr("disabled");
                $("#previousPage").removeAttr("disabled");
            }
            tableDataFill(nowPage);
        }
    }

    function tableDataFill(page) {
        $.get('DININGSYS/yqshapi/queryPrint/dataSearch_details_next/'+ inArray[page-1],{startTime:$("#startTime").val(),endTime:$("#endTime").val()},function (data) {
            if(data != null){
                var $grid = $("#checkBillItemTable");

                $grid.jqGrid("clearGridData");

                for(var i in data['tableData']){
                    $("#checkBillItemTable").addRowData(data['tableData'][i].id, data['tableData'][i],"first");
                }

                var itemFileNumber = $grid.jqGrid("getCol", "itemFileNumber", false, "sum"),
                    subtotal = $grid.jqGrid("getCol", "subtotal", false, "sum");
                $grid.jqGrid("footerData", "set", {itemFileNumber:itemFileNumber,subtotal:subtotal});


                var serviceType = data['headData']['service_type'],
                    serviceTypeName = data['headData']['serviceTypeName'],
                    titleHtml = "";

                if(serviceType === 1 || serviceType === 2 || serviceType === 6 ){
                    titleHtml = "<span style='color: green'>"+serviceTypeName+"</span>"
                }else if(serviceType === 3){
                    titleHtml = "<span>"+serviceTypeName+"</span>"
                }else if(serviceType === 4 || serviceType === 5){
                    titleHtml = "<span style='color: red'>"+serviceTypeName+"</span>"
                }else if(serviceType === 5){
                    titleHtml = "<span style='color: green'>"+serviceTypeName+"</span>"
                }else if(serviceType === 7){
                    titleHtml = "<span style='color: yellow'>"+serviceTypeName+"</span>"
                }else if(serviceType === 8){
                    titleHtml = "<span style='color: #1c98e1'>"+serviceTypeName+"</span>"
                }

                $("#typeTitle").html(titleHtml);
                $("#serviceNum").text(data['headData']['service_num']);
                $("#bisName").text(data['headData']['open_bis']);
                $("#waiterName").text(data['headData']['empName']);
                $("#servicePos").text(data['headData']['posName']);
                $("#serviceTime").text(data['headData']['serviceTime']);
            }
        })
    }
</script>
<style>
    .detailA a{
        cursor: pointer;
    }
    .detailA a:link {
        color:#FF0000;
        text-decoration:underline;
    }
    .detailA a:visited {
        color:#00FF00;
        text-decoration:none;
    }
    .detailA a:hover {
        color:#000000;
        text-decoration:none;
    }
    .detailA a:active {
        text-decoration:none;
    }
</style>
<form class="form-horizontal"  id="normalForm" style="width: 95%;padding: 15px">
    <input type="hidden" id="pageCount" value="${fn:length(inList)}">
    <div class="row" style="text-align: center">
        <div class="col-md-12">
            <span style="font-size: 20px" id="typeTitle">${title}</span>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <span class="col-md-6"><label>服务流水：</label></span>
            <div class="col-md-6">
                    <span class="form-control-static" id="serviceNum">${serviceNum}</span>
            </div>
        </div>
        <div class="col-md-6">
            <span class="col-md-6"><label>市别：</label></span>
            <div class="col-md-6">
                    <span class="form-control-static" id="bisName">${bisName}</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <span class="col-md-6"><label>点单员：</label></span>
            <div class="col-md-6">
                <div class="input-group">
                    <span class="form-control-static" id="waiterName">${waiter}</span>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <span class="col-md-6"><label>服务POS：</label></span>
            <div class="col-md-6">
                    <span class="form-control-static" id="servicePos">${posName}</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <span class="col-md-6"><label>时间：</label></span>
            <div class="col-md-6">
                    <span class="form-control-static" id="serviceTime">${serviceTime}</span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="checkBillItemTable"></table>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-12 detailA" style="text-align: right;">
            <button type="button" id="firstPage"  class="btn btn-link btn-sm">首页</button>
            <button type="button" id="previousPage"  class="btn btn-link btn-sm">上一页</button>
            <span>第<input id="nowPage" type="number" min="1" style="width: 45px;text-align: center">页</span>
            <span>共<span>${fn:length(inList)}</span>页</span>
            <button type="button" id="nextPage"  class="btn btn-link btn-sm">下一页</button>
            <button type="button" id="lastPage"  class="btn btn-link btn-sm">末页</button>
        </div>
    </div>
</form>

