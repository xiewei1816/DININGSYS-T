$(function () {
	//初始化客位类型
	var seatTypeText = $("#seatTypeValue").val();
	var option = $("#seatType option");
	option.each(function(){
		if($(this).text() == seatTypeText){
			$(this).attr("selected",true);
		}
	});
	
	var start = {
			format: 'YYYY-MM-DD',
			festival:true,
			ishmsVal:false,
			choosefun: function(elem,datas){
				end.minDate = datas;
			}
		};
	var end = {
		format: 'YYYY-MM-DD',
		festival:true,
		choosefun: function(elem,datas){
			start.maxDate = datas;
		}
	};
	$.jeDate('#crStartTime',start);
	$.jeDate('#crEndTime',end);
});