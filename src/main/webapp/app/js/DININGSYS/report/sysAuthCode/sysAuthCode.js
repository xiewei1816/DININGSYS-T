/**
 * Created by zhshuo on 2017-06-06.
 */
var sysAuthCode = function () {
    function pageInit(){
        var start = {
            format: 'YYYY-MM-DD hh:mm:ss',
            festival:true,
            ishmsVal:false,
            choosefun: function(elem,datas){
                end.minDate = datas;
            }
        };
        var end = {
            format: 'YYYY-MM-DD hh:mm:ss',
            festival:true,
            choosefun: function(elem,datas){
                start.maxDate = datas;
            }
        };

        $.jeDate('#startTime',start);
        $.jeDate('#endTime',end);

        $("#grid-table").jqGrid({
            url: "DININGSYS/report/sysAuthCode/getPageList",
            datatype: "JSON",
            mtype: "GET",
            colNames: ["授权人","被授权人","授权时间","备注"],
            colModel: [
                { name: "authUser" },
                { name: "authOpUser"},
                { name: "authTime"},
                { name: "authRemarks"}
            ],
            pager: "#grid-pager",
            rowNum: 20,
            rowList: [ 20, 50, 100 ],
            viewrecords: true,
            gridview: true,
            autoencode: true,
            styleUI : 'Bootstrap',
            autowidth : true,
        });
        resize();

        $("#refresh").click(function(){
            $("#" + pageUtil.tabId).trigger("reloadGrid");
        });

        $("#doSearch").click(function(){
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            var authOpUser = $("#authOpUser").val();
            var authUser = $("#authUser").val();
            var url = "DININGSYS/report/sysAuthCode/getPageList?startTime="+startTime+"&endTime="+endTime+"&authOpUser="+authOpUser+"&authUser="+authUser;
            jQuery("#grid-table").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
        });

        $(window).resize(function(){
            resize();
        });

    }

    function resize(){
        $("#grid-table").jqGrid("setGridWidth",$("#grid-table").closest(".jqGrid_wrapper").width(),true);
        $("#grid-table").jqGrid("setGridHeight",$(window).height()-210,true);
    }

    return{
        pageInit:function () {
            pageInit();
        }
    }

}()