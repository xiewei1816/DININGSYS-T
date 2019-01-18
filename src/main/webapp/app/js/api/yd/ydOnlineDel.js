$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	$("#" + pageUtil.tabId).getJqGrid({
		url : path+"/DININGSYS/yqshapi/yd/getOnlinePageList",
		colM : "orderId,contactuser,persons,gender,contacttel,createTime,reserveTime,seatType,sectionID,allowShiftSeatType",
		queryForm : "query-pan",
		colNames : "id,姓名,人数,性别,电话号码,下单时间,预定时间,客位类型,午餐or晚餐,没包间是否选择大厅",
		colWid : {"id":40},
		formatter: {
			"contactuser": function (v, o, r) {
				     return v.trim();	
            },
        	"sectionID": function (v, o, r) {
                if (v == '0') {
                    return '<font color="green">午餐</font>';
                } else {
                    return '<font color="green">晚餐</font>';
                }
            },
            "allowShiftSeatType": function (v, o, r) {
            	console.log(v);
                if (v == '1') {
                    return '<font color="green">是</font>';
                } else {
                    return '<font color="red">否</font>';
                }
            },
            "gender": function (v, o, r) {
                if (v == '2') {
                    return '<font color="red">女</font>';
                } else {
                    return '<font color="green">男</font>';
                }
            },
            "seatType": function (v) {
                if (v == '0') {
                    return '<font color="green">大厅</font>';
                } else if(v == '1'){
                    return '<font color="green">包间</font>';
                } else {
                	return '<font color="green">不限</font>';
                }
            }
        }
	});	  
	
	/**
	 * 隐藏grid中id项
	 */
    $("#grid-table").hideCol("orderId");
});