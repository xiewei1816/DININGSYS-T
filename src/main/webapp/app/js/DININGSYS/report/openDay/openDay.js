/**
 * Created by zhshuo on 2017-03-22.
 */
$(document).ready(function () {

    $("#flushBtn").click(function () {
        var loadIndex =  layer.load(1, {
            shade: [0.5,'gray']
        });
        $.post('DININGSYS/report/openDay/body',
            {startTime:$("#startTime").val(),endTime:$("#endTime").val(),orgCode:$("#org").val(),timeType:$("input[name='timeType']:checked").val(),bis:$("#bis").val(),pos:$("#pos").val(),area:$("#area").val()},
            function (data) {
                $("#containerDiv").html(data);
                headDatFill();
                layer.close(loadIndex);
            })
    }).trigger("click");

    $("#exportBtn").click(function(){
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var timeType = $("input[name='timeType']:checked").val();
        var bis = $("#bis").val();
        var pos = $("#pos").val();
        var area = $("#area").val();

        var url = "DININGSYS/report/openDay/exportXls?startTime="+startTime+"&endTime="+endTime+"&timeType="+timeType+"&bis="+bis+"&pos="+pos+"&area="+area;
        window.location.href = url;
    });

    $("#printBtn").click(function(){
//		$("#openDayReportContainer").printArea();
        printHtml();
    });

    resize();

    $(window).resize(function () {
        resize();
    })
});

/**
 * 打印数据封装
 */
function printHtml(){
    var headstr = "<html><head><title></title></head><body>";
    var footstr = "</body>";
    var printData = "";
    //打印店铺
    var printOrgName = "<table style='width:70mm;margin:0 auto;border-bottom:1px solid;'>" +
        "<tr><td style='text-align:center;font-size:24px;'>"+$("#orgName").html()+"</td></tr>" +
        "</table>";
    //打印统计项条件
    var printSearch = "<table style='width:70mm;margin:0 auto;font-size:14px;'>" +
        "<tr><td>开始时间：</td><td style='text-align:left;'>"+$("#headStartTime").html()+"</td></tr>" +
        "<tr><td>结束时间：</td><td style='text-align:left;'>"+$("#headEndTime").html()+"</td></tr>" +
        "</table>";
    //打印营业总额
    var printYYZE = getPrintTable("营业总额","printYYZE");
    //结算方式信息
    var printJSFSTJ = getPrintTable_JSFS("结算方式统计","printJSFSTJ");
    
    //会员充值
    var printRecharge = getPrintTable("会员充值统计","recharge_table");
    
    //小类统计
    var printPxxl = getPrintTable("品项小类统计","smallsell_table");
    
    //账单和客位信息
    var printZDHKWXX = getPrintTable("账单和客位信息","printZDHKWXX");
    //押金
    //var printYJ = getPrintTable("押金","printYJ");
    //特殊品项统计
    var printTSPXTJ = getPrintTable("特殊品项统计","printTSPXTJ");
    //就餐人数统计
    //var printJCRSTJ = getPrintTable("就餐人数统计","printJCRSTJ");

    //品项销售统计
    var printPXXSTJ = getPrintTable_PXXSTJ("品项销售统计","printPXXSTJ");

    //整合打印
    printData = printOrgName + printSearch + printYYZE +
        printJSFSTJ + printRecharge + printPxxl + printZDHKWXX /*+ printYJ*/ + printTSPXTJ /*+ printJCRSTJ*/ + printPXXSTJ;
    var oldstr = document.body.innerHTML;
    document.body.innerHTML = headstr+printData+footstr;
    window.print();
    document.body.innerHTML = oldstr;
    location.reload();
}

/**
 * 封装tab打印模板
 * @param tabName 表名称
 * @param tabId 表ID
 * @returns {String}
 */
function getPrintTable(tabName,tabId){
    var printTab = "<table style='width:70mm;margin:0 auto;font-size:14px;'>" +
        "<tr><td colspan='2' style='font-size:16px;font-weight:bold;border-right:1px dashed;'>"+tabName+"</td></tr>";
    var table = document.getElementById(tabId);
    var th = table.getElementsByTagName("th");
    var td = table.getElementsByTagName("td");
    var len = td.length;
    for(i=0;i<len;i++){
        printTab += "<tr><td>&#12288;&#12288;"+th[i+1].innerHTML+"</td><td style='text-align:right;border-right:1px dashed;padding-right:10px;'>"+td[i].innerHTML+"</td></tr>"
    }
    printTab += "</table>";
    return printTab;
}
/**
 * 封装tab打印模板-结算方式
 * @param tabName 表名称
 * @param tabId 表ID
 * @returns {String}
 */
function getPrintTable_JSFS(tabName,tabId){
    var printTab = "<table style='width:70mm;margin:0 auto;font-size:14px;'>" +
        "<tr><td colspan='2' style='font-size:16px;font-weight:bold;border-right:1px dashed;'>"+tabName+"</td></tr>";
    var table = document.getElementById(tabId);
    var th = table.getElementsByTagName("th");
    var td = table.getElementsByTagName("td");
    var len = td.length;
    for(i=0;i<len;i++){
        if(th[i+1].innerHTML == "计入实际收入合计" || th[i+1].innerHTML == "不计入实际收入合计"){
            printTab += "<tr><td style='font-weight:bold;'>&#12288;&#12288;"+th[i+1].innerHTML+"</td><td style='text-align:right;border-right:1px dashed;padding-right:10px;'>"+td[i].innerHTML+"</td></tr>"
        }else{
            printTab += "<tr><td>&#12288;&#12288;&#12288;&#12288;"+th[i+1].innerHTML+"</td><td style='text-align:right;border-right:1px dashed;padding-right:10px;'>"+td[i].innerHTML+"</td></tr>"
        }
    }
    printTab += "</table>";
    return printTab;
}

/**
 * 封装tab打印模板-品项销售统计
 * @param tabName 表名称
 * @param tabId 表ID
 * @returns {String}
 */
function getPrintTable_PXXSTJ(tabName,tabId){
    var printTab = "<table style='width:70mm;margin:0 auto;font-size:14px;'>" +
        "<tr><td colspan='2' style='font-size:16px;font-weight:bold;'>"+tabName+"</td></tr>";
    var tb = document.getElementById('ryybb_itemfile_table');    // table 的 id
    console.log(tb)
    var rows = tb.rows;                           // 获取表格所有行
    for(var i = 1; i<rows.length; i++ ){
        printTab += "<tr>";
        for(var j = 0; j<rows[i].cells.length; j++ ){    // 遍历该行的 td
            //console.log("第"+(i+1)+"行，第"+(j+1)+"个td的值："+rows[i].cells[j].innerHTML+"。");           // 输出每个td的内容
            printTab += "<td style='border-right:1px dashed;'>" + rows[i].cells[j].innerHTML + "</td>";
        }
        printTab += "</tr>";
    }
    printTab += "</table>";
    return printTab;
}

function headDatFill() {
    var date = new Date();
    $("#printData").html(date.getFullYear()+"-"+(date.getMonth() + 1)+"-"+date.getDate());
    $("#printTime").html(date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());

    $("#headStartTime").html($("#startTime").val());
    $("#headEndTime").html($("#endTime").val());

    $("#bisName").html($("#bis option:selected").text());
    $("#orgCode").html($("#org option:selected").val());
    $("#posName").html($("#pos option:selected").text());
    $("#orgName").html($("#org option:selected").text());
    $("#headTimType").html($("input[name='timeType']:checked").val()==1?"按结算时间":"按开台时间");

}

function resize() {
    $("#openDayReportContainer").height($(window).height()-110);
    $("#openDayReportContainer").width($(window).width());
}