/**
 * Created by zhshuo on 2017-02-08.
 */
var numberOfMeals = function () {

    function resize() {
        $("#numberOfMeals").jqGrid("setGridHeight",$(window).height()-160,true);
        $("#numberOfMeals").jqGrid("setGridWidth",$("#tableDiv").width(),true);
    }

    function pageInit() {

    	var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
        $("#numberOfMeals").jqGrid({
            url: "DININGSYS/report/NumberOfMeals/dataSearch?startTime="+startTime+"&endTime="+endTime,
            datatype: "json",
            colNames: ['时间段', '就餐人数', '占总人数比例'],
            colModel: [
                { name: 'hours'},
                { name: 'peopleCount'},
                { name: 'peoplePercent'}
            ],
            styleUI : 'Bootstrap'
        });
        
        $("#doSearch").click(function(){
    		var startTime = $("#startTime").val();
    		var endTime = $("#endTime").val();
    		var url = "DININGSYS/report/NumberOfMeals/dataSearch?startTime="+startTime+"&endTime="+endTime;
    		jQuery("#numberOfMeals").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    	});
        
        $("#export").click(function(){
        	var startTime = $("#startTime").val();
    		var endTime = $("#endTime").val();
    		var url = "DININGSYS/report/NumberOfMeals/exportXls?startTime="+startTime+"&endTime="+endTime;
    		window.location.href=url;
    	});
        
    	$("#refresh").click(function(){
    		$("#numberOfMeals").trigger("reloadGrid");
        });
    	
    	$("#print").click(function(){
    		$("#gbox_numberOfMeals").printArea();
    	});

        resize();

        $(window).resize(function () {
            resize();
        })

    }

    function eventInit() {
        $("#numberOfMealsQuery").click(function () {
            var startTime = jQuery("#startTime").val();
            var endTime = jQuery("#endTime").val();
            jQuery("#numberOfMeals").jqGrid('setGridParam',{url:"DININGSYS/report/NumberOfMeals/dataSearch?beginTime="+startTime+"&endTime="+endTime}).trigger("reloadGrid");
        })
    }

    return{
        pageInit:function () {
            pageInit();
            eventInit();
        }
    }

}();