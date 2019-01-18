$(function(){
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。

		}
	});

	$(".add").click(function(){
		$.get('DININGSYS/serviceClass/serviceClassEdit',function (data) {
            var index = layer.open({
            	type:1,
                title: '服务员服务客位'
                ,content: data,
                area:['950px','600px'],
                btn:['确认','取消'],
                yes:function () {
                	var vals = "",seats = "";
                    $("#grid-table1 select").each(function(i,e){
                        var val = $(e).val();
                        var seat = $(e).attr("saveid");
                        vals += val == 0 ? "" : vals == "" ? val : "," + val;
                        seats += val == 0 ? "" : seats == "" ? seat : "," + seat;

                    });
                    if(seats != null && seats != ''){
						$("#updSeats").val(seats);
						$("#updWaiters").val(vals);
					}
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/serviceClass/saveServiceClassIds',
                        data:$("#serviceClassForm").serialize(),
                        dataType:'JSON',
                        success:function(data){
                            if (data.success) {
                                $("#grid-table").trigger("reloadGrid");
                                layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                            } else {
                                $("#grid-table").trigger("reloadGrid");
                                layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
                            }
                        }
                    })
                }
            })
		})

	});
	
	$("#delb").click(function(){
		pageUtil.delOper({
			before : function(){												//删除数据界面之前执行的方法(非必要)。
			},
			url : "DININGSYS/serviceClass/deleteServiceClass",			//删除数据提交的后台地址(必要)。
			success : function(){												//删除数据成功之后执行的方法(非必要)。

			}
		});
	});
	
	$("#grid-table").getJqGrid({
		url : "DININGSYS/serviceClass/getPageList",
		colM : "eatTime.bisName,seat.number,seat.name,waiter.empName",
		queryForm : "queryForm",
		colNames : "市别,客位编号,客位名称,服务员",
		colWid : {"eatTime.bisName":140},
        rownumbers:true,
        loadComplete : function() {
		}
	});

});

function editPageInit() {
    $("#grid-table1").jqGrid({
        url: "DININGSYS/consumerSeat/getAllSeatWithServiceClass?bisId="+$("#bis").val(),
        datatype: "json",
        mtype: "GET",
        colNames: ["客位编号","客位名称","客位类型","服务员"],
        rownumbers:true,
        colModel: [
            { name: "number"},
            { name: "name"},
            { name: "seatType"},
            { name: "waiterId",formatter:function (cellvalue, options, rowObject) {
                var selct = "<select class='form-control' saveid='"+rowObject.id+"'><option value='0'>请选择</option>";
                $("#comEmp option").filter(".waiterChoose").each(function(i,e){
                    if(cellvalue == $(e).attr("value")){
                        selct += "<option value='"+$(e).attr("value")+"' selected='selected'>"+$(e).text()+"</option>";
					}else{
                        selct += "<option value='"+$(e).attr("value")+"'>"+$(e).text()+"</option>";
                    }
                });
                selct += "</selcct>";
                return selct;
            }}
        ],
        viewrecords: true,
        autoencode: true,
        styleUI : 'Bootstrap',
        autowidth : true,
		height:380,
        rowNum:-1
    });

    $(".edit_eatTimeId").change(function(){
        $("#grid-table1 select").val(0);
        jQuery("#grid-table1").jqGrid('setGridParam',{url:"DININGSYS/consumerSeat/getAllSeatWithServiceClass?bisId="+$(this).val()}).trigger("reloadGrid");
    });


    $("#comEmp").change(function(){
        var val = $(this).val();
        if(val != ""){
            $("#grid-table1 select").each(function(){
                $(this).val(val);
            });
		}
    });
}