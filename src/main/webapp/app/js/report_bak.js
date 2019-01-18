/**
 * 初始化报表日期查询项
 */
$(function () {

    /**
     * 时间初始化
     */
    var start = {
        format: 'YYYY-MM-DD hh:mm:ss',
        festival: true,
        ishmsVal: false,
        choosefun: function (elem, datas) {
            end.minDate = datas;
            // end.maxDate = getOneMonth(datas, 1);
            /*if ($("#endTime").val() > getOneMonth(datas, 1)) {
                $("#endTime").val(getOneMonth(datas, 1));
            }*/
        }
    };
    var end = {
        format: 'YYYY-MM-DD hh:mm:ss',
        festival: true,
        choosefun: function (elem, datas) {
            start.maxDate = datas;
            // start.minDate = getOneMonth(datas, -1);
            /*if ($("#startTime").val() < getOneMonth(datas, -1)) {
                $("#startTime").val(getOneMonth(datas, -1));
            }*/
        }
    };
    $.jeDate('#startTime', start);
    $.jeDate('#endTime', end);

    chickSel(1);

    /**
     * 快速选择日期初始化
     */
    var fastSel = $("#dateFastSel");
    fastSel.append('<div class="form-group big-wid"><select class="form-control" onchange="chickSel(this.value)">'
        /*+'<option value="0">快速选择日期</option>'*/
        + '<option value="1" selected>今天</option>'
        + '<option value="2">本周</option>'
        + '<option value="3">本月</option>'
		+'<option value="4">本季</option>'
		+'<option value="5">本年</option>'
        + '<option value="6">昨天</option>'
        + '<option value="7">上周</option>'
        + '<option value="8">上月</option>'
		+'<option value="9">上季</option>'
		+'<option value="10">上年</option>'
        + '</select></div>');
});

/**
 * 选择事件
 * @param value
 */
function chickSel(value) {
    var i = parseInt(value);
    var stp1 = " 09:00:00";
    switch (i) {
        case 0:
            $("#startTime").val("");
            $("#endTime").val("");
            break;
        case 1://今天
            $("#startTime").val($.nowDate({DD:0},'YYYY-MM-DD') + stp1);
            $("#endTime").val($.nowDate({DD:1},'YYYY-MM-DD') + stp1);
            break;
        case 2://本周
            var startDate = getWeekStartDate();
            var endDate = getWeekEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        case 3://本月
            var startDate = getMonthStartDate();
            var endDate = getMonthEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        case 4://本季
            var startDate = getQuarterStartDate();
            var endDate = getQuarterEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        case 5://本年
            var startDate = getYearStartDate();
            var endDate = getYearEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        case 6://昨天
            $("#startTime").val($.nowDate({DD:-1},'YYYY-MM-DD') + stp1);
            $("#endTime").val($.nowDate({DD:0},'YYYY-MM-DD') + stp1);
            break;
        case 7://上周
            var startDate = getLastWeekStartDate();
            var endDate = getLastWeekEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        case 8://上月
            var startDate = getLastMonthStartDate();
            var endDate = getLastMonthEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        case 9://上季
            var startDate = getLastQuarterStartDate();
            var endDate = getLastQuarterEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        case 10://上年
            var startDate = getLastYearStartDate();
            var endDate = getLastYearEndDate();
            $("#startTime").val(startDate + stp1);
            $("#endTime").val(endDate + stp1);
            break;
        default:

    }
}

/**
 * 获取今天、昨天、本周、本月、本季度、本年、上周、上月、上季、上年的开端日期、停止日期
 */
var now = new Date(); //当前日期   
var nowDayOfWeek = now.getDay(); //今天本周的第几天   
var nowDay = now.getDate(); //当前日   
var nowMonth = now.getMonth(); //当前月   
var nowYear = now.getYear(); //当前年   
nowYear += (nowYear < 2000) ? 1900 : 0; //  

var lastMonthDate = new Date(); //上月日期   
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth() - 1);
var lastYear = lastMonthDate.getYear();
var lastMonth = lastMonthDate.getMonth();

//格局化日期：yyyy-MM-dd   
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth() + 1;
    var myweekday = date.getDate();

    if (mymonth < 10) {
        mymonth = "0" + mymonth;
    }
    if (myweekday < 10) {
        myweekday = "0" + myweekday;
    }
    return (myyear + "-" + mymonth + "-" + myweekday);
}

//获得某月的天数
function getMonthDays(myMonth) {
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
    return days;
}

//获得本季度的开端月份
function getQuarterStartMonth() {
    var quarterStartMonth = 0;
    if (nowMonth < 3) {
        quarterStartMonth = 0;
    }
    if (2 < nowMonth && nowMonth < 6) {
        quarterStartMonth = 3;
    }
    if (5 < nowMonth && nowMonth < 9) {
        quarterStartMonth = 6;
    }
    if (nowMonth > 8) {
        quarterStartMonth = 9;
    }
    return quarterStartMonth;
}

//获得今日的日期   
function getNowDate() {
    return formatDate(now);
}

//获得昨日的日期   
function getYesterdayDate() {
    var yesterdayDate = new Date(nowYear, nowMonth, nowDay - 1);
    return formatDate(yesterdayDate);
}

//获得本周的开端日期   
function getWeekStartDate() {
    var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1);
    return formatDate(weekStartDate);
}
//获得本周的停止日期
function getWeekEndDate() {
    var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek + 2));
    return formatDate(weekEndDate);
}

//获得本月的开端日期   
function getMonthStartDate() {
    var monthStartDate = new Date(nowYear, nowMonth, 1);
    return formatDate(monthStartDate);
}
//获得本月的停止日期   
function getMonthEndDate() {
    //2017年8月15日17:35:53  by zhshuo
    var arr = $.nowDate({MM:1},'YYYY-MM-DD').split('-');
    var monthEndDate = new Date(arr[0], arr[1]-1, 1);
    return formatDate(monthEndDate);
}

//获得上周的开端日期   
function getLastWeekStartDate() {
    var lastWeekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek + 1 - 7);
    return formatDate(lastWeekStartDate);
}
//获得上周的停止日期
function getLastWeekEndDate() {
    var lastWeekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek + 2 - 7));
    return formatDate(lastWeekEndDate);
}

//获得上月开端时候   
function getLastMonthStartDate() {
    var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
    return formatDate(lastMonthStartDate);
}
//获得上月停止时候   
function getLastMonthEndDate() {
    var arr = $.nowDate({MM:-1},'YYYY-MM-DD').split('-');
    /*var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(lastMonth));
    return formatDate(lastMonthEndDate);*/
    var lastMonthEndDate = new Date(arr[0], arr[1], 1);
    return formatDate(lastMonthEndDate);
}

//获得上季度的开端日期
function getLastQuarterStartDate() {
    var lastQuarterStartDate = new Date(nowYear, getQuarterStartMonth() - 3, 1);
    return formatDate(lastQuarterStartDate);
}
//获得上季度的停止日期   
function getLastQuarterEndDate() {
    var lastQuarterEndMonth = getQuarterStartMonth() - 1;
    var lastQuarterStartDate = new Date(nowYear, lastQuarterEndMonth, getMonthDays(lastQuarterEndMonth));
    return formatDate(lastQuarterStartDate);
}

//获得上年开端日期
function getLastYearStartDate() {
    var lastYearStartDate = new Date(nowYear - 1, 0, 1);
    return formatDate(lastYearStartDate);
}
//获得上年停止日期   
function getLastYearEndDate() {
    var lastYearEndDate = new Date(nowYear - 1, 11, 31);
    return formatDate(lastYearEndDate);
}

//获得本季度的开端日期
function getQuarterStartDate() {
    var quarterStartDate = new Date(nowYear, getQuarterStartMonth(), 1);
    return formatDate(quarterStartDate);
}
//获得本季度的停止日期   
function getQuarterEndDate() {
    var quarterEndMonth = getQuarterStartMonth() + 2;
    var quarterStartDate = new Date(nowYear, quarterEndMonth, getMonthDays(quarterEndMonth));
    return formatDate(quarterStartDate);
}

//获得本年开端日期
function getYearStartDate() {
    var yearStartDate = new Date(nowYear, 0, 1);
    return formatDate(yearStartDate);
}
//获得本年停止日期   
function getYearEndDate() {
    var yearEndDate = new Date(nowYear, 11, 31);
    return formatDate(yearEndDate);
}

//当前时间加一个月
function getOneMonth(date, number) {
    date = date.replace(/-/g, "/");
    var oldDate = new Date(date);
    var newDate = new Date(oldDate.getFullYear(), oldDate.getMonth() + number, oldDate.getDate(), oldDate.getHours(), oldDate.getMinutes(), oldDate.getSeconds());
    return dateFtt("yyyy-MM-dd hh:mm:ss", newDate);

}

//格式化日期,
/**************************************时间格式化处理************************************/
function dateFtt(fmt, date) {
    var o = {
        "M+": date.getMonth() + 1,                 //月份
        "d+": date.getDate(),                    //日
        "h+": date.getHours(),                   //小时
        "m+": date.getMinutes(),                 //分
        "s+": date.getSeconds(),                 //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
} 