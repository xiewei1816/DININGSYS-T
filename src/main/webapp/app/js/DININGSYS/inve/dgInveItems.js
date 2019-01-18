$(function(){
	    		pageUtil.pageInit({
	    			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
	    			}
	    		});
	    		$(".add").click(function(){
	    			pageUtil.addOper({
	    				addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
	    				},
	    				saveUrl : "saveInveItems", 						//添加保存的地址(必要)。
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
	    				updUrl : "getInveItemsById",									//获取修改数据的后台地址(必要)。
	    				updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
	    					pageUtil.getUpdingData(data.supplier);                            	//自动给编辑界面里填值。
	    				},
	    				saveUrl : "saveInveItems", 									//添加保存的地址(必要)。
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
	    			var rowids = $("#" + pageUtil.tabId).jqGrid('getGridParam','selarrrow');
	    			var jsonStr='';
	    			$.each(rowids,function(k,v){
	    				//获取itemName
	    				var arrData=$("#" + pageUtil.tabId).getRowData(v);
	    				var val='{"id":"'+v+'","itemName":"'+arrData['itemName']+'"}';
	    				jsonStr+=val+",";
	    			});
	    			jsonStr=jsonStr.substring(0,jsonStr.length-1);
	    			jsonStr='['+jsonStr+']';
	    			if(jsonStr=='[]'){
	    				layer.alert( '请选择需要删除的数据',{title :'错误',icon: 2, skin: 'layer-ext-moon'});
	    				return false;
	    			}
	    			layer.confirm('请注意,该功能删除的数据不可逆.你确定吗?', {
	    				  icon : 0,
	    				  btn : ['删除','取消'] //按钮
	    				  }, function(index){
	    					  $.ajax({ 
	    		    				url: "deleteInveItems",
	    		    				traditional: true,
	    		    				data:{objJson:jsonStr},
	    		    				success: function(rlstData){
	    		    					if (rlstData.success == 'OK') {
	    		    						$("#" + pageUtil.tabId).trigger("reloadGrid");
	    		    						layer.close(index);
	    								} else {
	    									layer.alert( rlstData.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
	    									return false;
	    								}
	    		    				}
	    		    		    });
	    				  });
	    		});
	    		$("#" + pageUtil.tabId).getJqGrid({
	    			url : "getPageList",
	    			colM : "itemNo,itemName,itemTypeName,price,unit,minStorage,maxStorage,inCode,spellCode,remark",
	    			queryForm : "queryForm",
	    			colNames : "编码,名称,类型,单价,单位,最小存储,最大存储,领料减少库存,拼音简码,备注",
	    			colWid : {"id":40},
	    			rowList:"10,20,50,100",
	    			rowNum:10,
                    formatter : {
                        "inCode" : function(v){
                            if (v == '1') {
                                return '<font color="green">是</font>';
                            } else {
                                return '<font color="red">否</font>';
                            }
                        }
                    },
	    			loadComplete : function() {
	    			}
	    		});
	    	});