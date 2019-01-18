var businessStatistics = function () {
	
	function date() {
		var search = {
			format: 'YYYY-MM-DD',
			festival:true,
			isinitVal:true,
			choosefun: function(elem,datas){
				start.maxDate = datas;
			}
		};
		$.jeDate('#searchDate',search);
	
		loadData();
	    
	    $("#doSearch").click(function(e){
			 e.preventDefault(); //阻止回车提交表单
          	 loadData();
 		});
 		
 	    $("#export").click(function(){
			window.location.href="DININGSYS/report/businessStatistics/dataSearch_date_export?searchDate="+$("#searchDate").val();
		});
		
		$("#print").click(function(){
        	var headstr = "<html><head><title>营业统计日报</title></head><body>";  
        	var footstr = "</body>";  
        	var printData = document.getElementById("print-data").innerHTML;
        	var oldstr = document.body.innerHTML;  
        	document.body.innerHTML = headstr+printData+footstr;  
        	window.print();  
        	document.body.innerHTML = oldstr;
        	location.reload(); 
    	});
    
	}
	
    function month() {
		var search = {
			format: 'YYYY-MM',
			festival:true,
			isinitVal:true,
			choosefun: function(elem,datas){
				start.maxDate = datas;
			}
		};
		$.jeDate('#searchDate',search);
		
		loadMonth();
		
		 $("#doSearch").click(function(e){
			 e.preventDefault(); //阻止回车提交表单
          	 loadMonth();
 		});
 		
 		 $("#export").click(function(){
			window.location.href="DININGSYS/report/businessStatistics/dataSearch_month_export?searchDate="+$("#searchDate").val();
		});
		
		$("#print").click(function(){
        	var headstr = "<html><head><title>营业统计月报</title></head><body>";  
        	var footstr = "</body>";  
        	var printData = document.getElementById("print-data").innerHTML;
        	var oldstr = document.body.innerHTML;  
        	document.body.innerHTML = headstr+printData+footstr;  
        	window.print();  
        	document.body.innerHTML = oldstr;
        	location.reload(); 
    	});
	}
	
	function week() {
		var sm = {
			format: 'YYYY-MM-DD',
			festival:true,
			choosefun: function(elem,datas){
				var lastMonthEndDate = new Date(Number(datas.substring(0,4)), Number(datas.substring(5,7))-1,Number(datas.substring(8,10))+7);
				$("#bEndDate").val(formatDate(lastMonthEndDate));
			}
		};
		var em = {
			format: 'YYYY-MM-DD',
			festival:true,
			choosefun: function(elem,datas){
				var lastMonthEndDate = new Date(Number(datas.substring(0,4)), Number(datas.substring(5,7))-1,Number(datas.substring(8,10))+7);
				$("#sEndDate").val(formatDate(lastMonthEndDate));
			}
		};
		$.jeDate('#bStartDate',sm);
		$.jeDate('#sStartDate',em);
		$("#bStartDate").val(getWeekStartDate());
		$("#bEndDate").val(getWeekEndDate());
		$("#sStartDate").val(getLastWeekStartDate());
		$("#sEndDate").val(getLastWeekEndDate());
		
		
		loadWeek();
		$("#doSearch").click(function(e){
			 e.preventDefault(); //阻止回车提交表单
          	 loadWeek();
 		});
 		
 		$("#export").click(function(){
			window.location.href="DININGSYS/report/businessStatistics/dataSearch_week_export?sStartDate="+$("#sStartDate").val()+"" +
					"&sEndDate="+$("#sEndDate").val()+"&bStartDate="+$("#bStartDate").val()+"&bEndDate="+$("#bEndDate").val();
		});
		
		$("#print").click(function(){
        	var headstr = "<html><head><title>营业统计周报</title></head><body>";  
        	var footstr = "</body>";  
        	var printData = document.getElementById("print-data").innerHTML;
        	var oldstr = document.body.innerHTML;  
        	document.body.innerHTML = headstr+printData+footstr;  
        	window.print();  
        	document.body.innerHTML = oldstr;
        	location.reload(); 
    	});
	}
	

	function loadData(){
		$.ajax({
	    	url:'DININGSYS/report/businessStatistics/dataSearch_date',
	    	type:'POST',
	    	data:{searchDate:$("#searchDate").val()},
	    	dataType:'JSON',
	   		success:function (data) {
				$("#tableOne").empty();
				var tablOneData = '<tr><th>事项</th><th>昨日累计</th><th>本日累计</th><th>本月累计</th><th>本年累计</th></tr>';
				for(var i in data.srfl){
					tablOneData += "<tr><td>"+data.srfl[i].name+"</td><td>"+data.srfl[i].zr+"</td><td>"+data.srfl[i].jr+"</td><td>"+data.srfl[i].by+"</td><td>"+data.srfl[i].bn+"</td></tr>";
				}
				$("#tableOne").html(tablOneData);
				
				$("#tableTwo").empty();
				var tablTwoData = '<tr><th>事项</th><th>昨日累计</th><th>本日累计</th><th>本月累计</th><th>本年累计</th></tr>';
				for(var i in data.skfl){
					tablTwoData += "<tr><td>"+data.skfl[i].name+"</td><td>"+data.skfl[i].znsum+"</td><td>"+data.skfl[i].jsum+"</td><td>"+data.skfl[i].bysum+"</td><td>"+data.skfl[i].bnsum+"</td></tr>";
				}
				$("#tableTwo").html(tablTwoData);
				
				$("#tableThree").empty();
				var tablThreeData = '<tr><th>事项</th><th>昨日累计</th><th>本日累计</th><th>本月累计</th><th>本年累计</th></tr>';
				for(var i in data.rjfl){
					tablThreeData += "<tr><td>"+data.rjfl[i].name+"</td><td>"+data.rjfl[i].zrp+"</td><td>"+data.rjfl[i].jrp+"</td><td>"+data.rjfl[i].byp+"</td><td>"+data.rjfl[i].bnp+"</td></tr>";
				}
				$("#tableThree").html(tablThreeData);
	        }
	    });
	}
	
	function loadMonth(){
		$.ajax({
	    	url:'DININGSYS/report/businessStatistics/dataSearch_month',
	    	type:'POST',
	    	data:{searchDate:$("#searchDate").val()},
	    	dataType:'JSON',
	   		success:function (data) {
				$("#tableOne").empty();
				var tablOneData = "";
				for(var i in data){
					tablOneData += "<tr><td>"+data[i].name+"</td><td>"+data[i].by+"</td><td>"+data[i].sy+"</td><td>"+data[i].sbypercent+"%</td><td>"+data[i].snby+"</td><td>"+data[i].snbypercent+"%</td></tr>";
				}
				$("#tableOne").html(tablOneData);
	        }
	    });
	}
	
	function loadWeek(){
		$.ajax({
	    	url:'DININGSYS/report/businessStatistics/dataSearch_week',
	    	type:'POST',
	    	data:$("#query-pan").serialize(),
	    	dataType:'JSON',
	   		success:function (data) {
				$("#tableOne").empty();
				var tablOneData = "";
				for(var i in data){
					tablOneData += "<tr><td>"+data[i].name+"</td><td>"+data[i].by+"</td><td>"+data[i].sy+"</td><td>"+data[i].snby+"%</td></tr>";
				}
				$("#tableOne").html(tablOneData);
	        }
	    });
	}
	
    return{
	    date:function () {
	        date();
	    },
	    month:function () {
	        month();
	    },
	    week:function () {
	    	week();
	    }
	}	
}(); 