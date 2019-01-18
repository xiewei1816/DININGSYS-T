$(function(){
	    		pageUtil.pageInit({
	    			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
	    			}
	    		});
	    		$(".add").click(function(){
	    			pageUtil.addOper({
	    				addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
	    				},
	    				saveUrl : "save", 						//添加保存的地址(必要)。
	    				saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
	    					if (resultData.success == 'OK') {
	    						//layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
							} else {
								layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
								return false;
							}
	    				}
	    			});
		    	});
	    		
	    		$("#update").click(function(){
	    			pageUtil.updOper({
	    				updBefore : function(){												//修改数据界面之前执行的方法(非必要)。
	    					
	    				},
	    				updUrl : "getModel",									//获取修改数据的后台地址(必要)。
	    				updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
	    					pageUtil.getUpdingData(data.obj);                            	//自动给编辑界面里填值。
	    				},
	    				saveUrl : "save", 									//添加保存的地址(必要)。
	    				saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
	    					if (resultData.success == 'OK') {
	    						//layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
							} else {
								layer.alert( resultData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
								return false;
							}
	    				}
	    			});
	    		});
	    		
	    		$("#delb").click(function(){
	    			pageUtil.delOper({
	    				before : function(){												//删除数据界面之前执行的方法(非必要)。
	    				},
	    				url : "delete",										//删除数据提交的后台地址(必要)。
	    				success : function(){												//删除数据成功之后执行的方法(非必要)。
	    				}
	    			});
	    		});
	    		$("#" + pageUtil.tabId).getJqGrid({
	    			url : "getPageList",
	    			colM : "nickName,remark,createDate",
	    			queryForm : "queryForm",
	    			colNames : "名称,备注,创建时间",
	    			colWid : {"id":40,"create_time":140},
	    			loadComplete : function() {
	    			}
	    		});
	    	});