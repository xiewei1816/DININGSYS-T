$(function(){
	    		pageUtil.pageInit({
	    			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
	    			}
	    		});
	    		$("#" + pageUtil.tabId).getJqGrid({
	    			url : "getPageList",
	    			colM : "itemNo,joinItemName,number,unit,wareNo,wareName",
	    			queryForm : "queryForm",
	    			colNames : "物品编码,物品名称,数量,物品单位,仓库编码,仓库",
	    			colWid : {"id":40},
	    			rowList:"10,20,50,100",
	    			rowNum:10,
	    			loadComplete : function() {
	    			}
	    		});
	    		//导出
	    		$('#export').click(function(){
	    			window.location.href="exportXls?wareID="+$('#j-wareID').val()+"&itemName="+$('#j-itemName').val();
	    		});
	    		//打印
	    		$('#j-print').click(function(){
	    			window.location.href="printDocu?wareID="+$('#j-wareID').val()+"&itemName="+$('#j-itemName').val();
	    		});
});