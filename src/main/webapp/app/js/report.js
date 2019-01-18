/**
 * 初始化报表日期查询项
 */
$(function () {
	
    /**
     * 时间初始化
     */
    var start = {
        format: 'YYYY-MM-DD hh:mm:ss',
        isinitVal:true,
        festival: true,
        ishmsVal: false,
        hmsSetVal:{hh:09,mm:00,ss:00},
        choosefun: function (elem, datas) {
            end.minDate = datas;
            end.maxDate = getCurrentMonthLast();
            $("#endTime").val(getNextday())
        }
    };
    var end = {
        format: 'YYYY-MM-DD hh:mm:ss',
        isinitVal:true,
        festival: true,
        ishmsVal: false,
        hmsSetVal:{hh:09,mm:00,ss:00},
        choosefun: function (elem, datas) {

        }
    };
    
    $.jeDate('#startTime', start);
    $.jeDate('#endTime', end);
    //初始化设置结束日期最小为开始日期
    end.minDate = $("#startTime").val();
    //初始化设置结束日期最大为开始日期月份的最大日期
    end.maxDate = getCurrentMonthLast();
    //初始化设置结束时间为开始时间的第二天
    $("#endTime").val(getNextday())
    
    //获取所选月份的第一天
    function getCurrentMonthFirst(){
         var startdate=new Date($("#startTime").val());
         startdate.setDate(1);
         return startdate;
    };
	
    //获取所选月份的最后一天
    function getCurrentMonthLast() {
    	var startTime = $("#startTime").val();
    	var endDate = new Date(startTime);
        var month = endDate.getMonth();
        var nextMonth = ++month;
        var nextMonthFirstDay = new Date(endDate.getFullYear(),nextMonth,1);
        var oneDay = 1000*60*60*24;
        return formatDate(new Date(nextMonthFirstDay/*-oneDay*/));        
   } 
    //获取所选日期的下一天
    function getNextday(){
    	var startTime = $("#startTime").val();
    	var t = new Date(startTime);
    	var tm = new Date(t.getFullYear(),t.getMonth(),t.getDate()+1);

    	var m = '0'+(tm.getMonth()+1);
    	var d = '0'+tm.getDate()
    	if(d == '01'){
    		return startTime;
    	}

    	return tm.getFullYear()+'-'+m.substr(m.length-2)+'-'+d.substr(d.length-2) + " 09:00:00";

    }
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
	

});
