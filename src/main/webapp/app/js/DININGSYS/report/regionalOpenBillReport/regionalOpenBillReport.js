var regionalOpenBillReport = function () {
	
	function initPage() {
		var search = {
			format: 'YYYY-MM-DD',
			festival:true,
			isinitVal:true,
			choosefun: function(elem,datas){
				start.maxDate = datas;
			}
		};
		$.jeDate('#searchDate',search);
	
		loadDate();
	    
	    $("#doSearch").click(function(e){
			 e.preventDefault(); //阻止回车提交表单
          	 loadDate();
 		});
	}
	
	
	function initAnalysisPage() {
		var search = {
			format: 'YYYY-MM-DD',
			festival:true,
			isinitVal:true,
			choosefun: function(elem,datas){
				start.maxDate = datas;
			}
		};
		$.jeDate('#searchDate',search);
	
		loadAnalysisDate();
	    
	    $("#doSearch").click(function(e){
			 e.preventDefault(); //阻止回车提交表单
          	 loadAnalysisDate();
 		});
	}
	
	function loadDate(){
		$.ajax({
	    	url:'DININGSYS/report/regionalOpenBillReport/getData',
	    	type:'POST',
	    	data:$("#query-pan").serialize(),
	    	dataType:'JSON',
	   		success:function (data) {
				$("#tableAreaOpen").empty();
				var tableAreaOpen = '<tr><th>消费区域名称</th><th>客位数</th><th>项目</th><th>桌次</th><th>人数</th><th>金额</th><th>人均</th><th>菜品数量</th><th>开台率</th></tr>';
				for(var i in data){
					if(i%3 == 0){
										tableAreaOpen += "<tr><td rowspan='3'>"+data[i].areaName+"</td><td rowspan='3'>"+data[i].seatCount+"</td><td>"+data[i].type+"</td>" +
						"<td>"+data[i].waterCount+"</td><td>"+data[i].peopleCount+"</td><td>"+data[i].moneyCount+"</td><td>"+data[i].sj+"</td>"+
						"<td>"+data[i].itemCount+"</td><td>"+data[i].ktl+"</td></tr>";	
					} else {
						tableAreaOpen += "<tr><td>"+data[i].type+"</td>" +
						"<td>"+data[i].waterCount+"</td><td>"+data[i].peopleCount+"</td><td>"+data[i].moneyCount+"</td><td>"+data[i].sj+"</td>"+
						"<td>"+data[i].itemCount+"</td><td>"+data[i].ktl+"</td></tr>";	
					}
				}
				$("#tableAreaOpen").html(tableAreaOpen);
	        }
	    });
	}
	
	
	function loadAnalysisDate(){
		$.ajax({
	    	url:'DININGSYS/report/regionalOpenBillReport/getAnalysisData',
	    	type:'POST',
	    	data:$("#query-pan").serialize(),
	    	dataType:'JSON',
	   		success:function (data) {
				$("#tableAreaOpen tbody").empty();
				var tableAreaOpen = '';
				for(var i in data){
					tableAreaOpen += "<tr><td>"+data[i].areaName+"</td><td>"+data[i].bisName+"</td><td>"+data[i].jrmoneyCount+"</td>" +
					"<td>"+data[i].bymoneyCount+"</td><td>"+data[i].jrwaterCount+"</td><td>"+data[i].bywaterCount+"</td><td>"+data[i].jrpeopleCount+"</td>"+
					"<td>"+data[i].bypeopleCount+"</td><td>"+data[i].jrzjxf+"</td><td>"+data[i].byzjxf+"</td><td>"+data[i].jrsj+
							"</td><td>"+data[i].bysj+"</td><td>"+data[i].jritemCount+"</td><td>"+data[i].byitemCount+"</td></tr>";	
				}
				$("#tableAreaOpen tbody").append(tableAreaOpen);
	        }
	    });
	}
	
    return{
	    initPage:function () {
	        initPage();
	    },
	    initAnalysisPage:function() {
	    	initAnalysisPage();
	    }
	}	
}(); 