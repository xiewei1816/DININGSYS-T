$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	
	 var time ={
				format:"YYYY-MM-DD",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				zIndex:29891015,
				choosefun: function(elem,datas){
			         end.minDate = datas; //开始日选好后，重置结束日的最小日期
			    }
	};
	$('#time').jeDate(time);
			
			
	$("#" + pageUtil.tabId).getJqGrid({
		url : path+"/DININGSYS/yqshapi/yd/getPageList",
		colM : "id,ydNum,seatName,name,sex,number,phone,time,ydTime,wOw,state",
		queryForm : "query-pan",
		colNames : "id,预订单号,客位号,姓名,性别,人数,电话号码,下单时间,就餐时间,晚餐或晚餐,状态",
		colWid : {"id":40},
		formatter: {
			"name": function (v, o, r) {
				if(r.sex == '2'){
					if(v.indexOf("(")!=-1){
						return v.trim();
					} else {
						return v.trim()+'(先生)';	
					}
				     return v.trim()+'(女士)';	
				} else {
					if(v.indexOf("(")!=-1){
						 return v.trim();
					} else {
						return v.trim()+'(先生)';	
					}
				}
				
            },
            "wOw": function (v, o, r) {
				if(r.sex == '1'){
				     return '午餐';	
				} else {
					 return '晚餐';
				}
            },
            "state": function (v, o, r) {
            	return '<font color="red">已删除</font>';	
            }
		}
	});	  
	
	/**
	 * 隐藏grid中id项
	 */
    $("#grid-table").hideCol("id");
    $("#grid-table").hideCol("sex");
});