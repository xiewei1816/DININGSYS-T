/**
 * Created by zhshuo on 2017-03-22.
 */
$(document).ready(function () {

    /**
     * 时间初始化
     */
    var start = {
        format: 'YYYY-MM',
        festival:true,
        ishmsVal:false,
        choosefun: function(elem,datas){
            var loadIndex =  layer.load(1, {
                shade: [0.5,'gray']
            });
            $.post('DININGSYS/report/monthlyPassenger/body',
                {startTime:$("#startTime").val()},
                function (data) {
                    $("#containerDiv").html(data);
                    layer.close(loadIndex);
                });
        }
    };
    var date = new Date();
    $.jeDate('#startTime',start);


    $("#flushBtn").click(function () {
        var loadIndex =  layer.load(1, {
            shade: [0.5,'gray']
        });
        $.post('DININGSYS/report/monthlyPassenger/body',
            {startTime:$("#startTime").val()},
            function (data) {
                $("#containerDiv").html(data);
                layer.close(loadIndex);
            })
    }).trigger("click");

	$("#exportBtn").click(function(){
		var startTime = $("#startTime").val();
		var url = "DININGSYS/report/monthlyPassenger/exportXls?startTime="+startTime;
		window.location.href = url;
	});
    
    resize();

    $(window).resize(function () {
        resize();
    })
});

function resize() {
    $("#openDayReportContainer").height($(window).height()-50);
    $("#openDayReportContainer").width($(window).width());
}