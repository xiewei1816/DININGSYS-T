var ydSeats = function () {
	function pagerInit()
    {		
		var seleVals = [];
		$(".seats li input").each(function(){
			seleVals.push($(this).val());
		});

		$("#grid-table-seat").jqGrid({
            url: path+"/DININGSYS/yqshapi/yd/getSeatList?time="+$("#ydTime").val()+"&id="+$("#pId").val()+
            "&search="+$("#searchSeat").val()+"&posId="+$("#posId").val()+"&crId="+$("#crId").val()+"&bsd="+$('input:radio[name="bsd"]:checked').val(),
            datatype: "JSON",
            mtype: "GET",
            rowNum:-1,
            colNames: ["id","编码","名称","容纳人数","所在区域"],
            colModel: [
                { name: "id",width:30,hidden:true},
                { name: "number" },
                { name: "name"},
                { name: "allowNumber" },
                { name: "area.name"}
            ],
            rowList: [],        // disable page size dropdown
            pgbuttons: false,     // disable page control like next, back button
            pgtext: null,         // disable pager text like 'Page 0 of 10'
            viewrecords: false,
            width:$("#seatDialog").width()-5,
            height:$(window).height()/100*40-60,
            multiselect:true,
            styleUI : 'Bootstrap',
            caption:"客位选择 ",
            loadComplete : function() {
            	for(var i in seleVals){
                	$("#grid-table-seat").setSelection(seleVals[i]); 	
            	}
            }
        });
		
		$('#query-pan-seat :text').keypress(function(e){
            if(e.keyCode==13){
            	$("#query-pan-seat .btn").trigger("click");
            }
		});
		
		$('#query-pan-seat .btn').click(function(e){
			 e.preventDefault(); //阻止回车提交表单
	         url = path+"/DININGSYS/yqshapi/yd/getSeatList?time="+$("#ydTime").val()+"&id="+$("#pId").val()+"&search="+$("#searchSeat").val()+
	         "&posId="+$("#posId").val()+"&crId="+$("#crId").val()+"&bsd="+$('input:radio[name="bsd"]:checked').val();
	         jQuery("#grid-table-seat").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
		});
		
		$(window).resize(function(){
		    $("#grid-table-seat").setGridWidth($("#seatDialog").width()-5);
		    $("#grid-table-seat").setGridHeight($(window).height()/100*40-60);
		});
    }
	
	return {
 		pagerInit:function (){
 			pagerInit();
 		}
	}
}();