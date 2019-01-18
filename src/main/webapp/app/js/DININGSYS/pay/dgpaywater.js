$(function(){
	    		pageUtil.pageInit({
	    			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
	    			}
	    		});
	    		$("#" + pageUtil.tabId).getJqGrid({
	    			url : "DININGSYS/pay/water/getPageList",
	    			colM : "payType,payState,payMoney,outTradeNo,threeTradeNo,orderNo,tradeDate,createDate,remark",
	    			queryForm : "queryForm",
	    			colNames : "支付类型,状态,金额,商户订单号,第三方订单号,店内流水号,支付时间,创建时间,备注",
	    			colWid : {"id":40,"create_time":140},
	    			multiselect: true,  
	    			multiboxonly:true, 
	    			loadComplete : function() {
	    			},
	    			formatter:{
	    				"payState" : function(v){
	    					if(v==0){
	    						return '成功';
	    					}else if(v==1){
	    						return '<span style="color:red;">失败</span>';
	    					}
	    				}
	    			}
	    		});
	    		$('#btn-export').click(function(){
	    			var crStartTime=$('#crStartTime').val();
	    			var crEndTime=$('#crEndTime').val();
	    			if((crStartTime!=''&&crEndTime=='')||(crStartTime==''&&crEndTime!='')){
	    				layer.alert('创建开始时间与结束时间需同时选择!',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
	    				return false;
	    			}
	    				window.location.href="DININGSYS/pay/water/exportXls?payType="+$('#j-payType').val()+
	    				"&outTradeNo="+$('#j-outTradeNo').val()+"&threeTradeNo="+$('#j-threeTradeNo').val()+"&orderNo="+$('#j-orderNo').val()+
	    				"&crStartTime="+crStartTime+"&crEndTime="+crEndTime;
	    		});
	    		//单条订单对账
	    		$('.btn-check').click(function(){
	    			//获取选中行id
	    			var id=$('#'+ pageUtil.tabId).jqGrid('getGridParam','selrow');
	    					var params={
	    							id:id
	    					};
	    					$.post('DININGSYS/pay/water/payCheck',params,function(data){
	    						if(isNaN(data)){
	    							layer.msg(data, {icon: 2});
	    						}else{
	    							layer.msg('对账成功!', {icon: 1});
	    							$("#"+pageUtil.tabId).trigger("reloadGrid");
	    						}
	    					});
	    		});

});