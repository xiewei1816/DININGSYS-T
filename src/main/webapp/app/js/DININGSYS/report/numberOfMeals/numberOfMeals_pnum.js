/**
 * Created by zhshuo on 2017-03-22.
 */
$(document).ready(function () {

    $("#flushBtn").click(function () {
        var loadIndex =  layer.load(1, {
            shade: [0.5,'gray']
        });
        $.post('DININGSYS/report/NumberOfMeals/numberOfMeals_pnum_content',
            {startTime:$("#startTime").val(),endTime:$("#endTime").val()},
            function (data) {
                $("#containerDiv").html(data);
                layer.close(loadIndex);
            });
    }).trigger("click");
    resize();
    $(window).resize(function () {
        resize();
    });
});

function resize() {
    $("#openDayReportContainer").height($(window).height()-50);
    $("#openDayReportContainer").width($(window).width());
}