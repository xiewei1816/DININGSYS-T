$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	
	$("#grid-table").jqGrid({
        url:"DININGSYS/DgItemGiftPlan/getAllData?recycle=0",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        colNames:["id","方案名称","品项id","品项编码","品项名称","开始日期","开始时间","结束日期","结束时间","星期一","星期二","星期三","星期四","星期五","星期六","星期日","赠送次数限定"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'name',index:'name', width:'10%',align:'center'},
            {name:'itemId',index:'itemId', width:'10%',align:'center',hidden:true},
            {name:'itemCode',index:'itemCode', width:'10%',align:'center'},
            {name:'itemName',index:'itemName', width:'10%',align:'center'},
            {name:'startDate',index:'startDate', width:'10%',align:'center'},
            {name:'startTime',index:'startTime', width:'10%',align:'center'},
            {name:'endDate',index:'endDate', width:'10%',align:'center'},
            {name:'endTime',index:'endTime', width:'10%',align:'center'},
            {name:'week1',index:'week1', width:'10%',align:'center',formatter:format},
            {name:'week2',index:'week2', width:'10%',align:'center',formatter:format},
            {name:'week3',index:'week3', width:'10%',align:'center',formatter:format},
            {name:'week4',index:'week4', width:'10%',align:'center',formatter:format},
            {name:'week5',index:'week5', width:'10%',align:'center',formatter:format},
            {name:'week6',index:'week6', width:'10%',align:'center',formatter:format},
            {name:'week7',index:'week7', width:'10%',align:'center',formatter:format},
            {name:'giftFrequencyLimit',index:'giftFrequencyLimit', width:'10%',align:'center'}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:100,//每页显示记录数
        rowList:[100,200],//用于改变显示行数的下拉列表框的元素数组。
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        },
        multiselect:true,
        pager:$('#grid-pager')
    });
    $("#grid-table").hideCol("id");
	
    $("#add").click(function(){
        $.get("DININGSYS/DgItemGiftPlan/add",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'赠送品项促销方案',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
               		/**
               		 *  加入会员打折品相
               		 */
            		if(!checkUpdVals("basis"))
            		{
            			if(!checkUpdVals("detail"))
                		{
                			return;
                		}
            			return;
            		}
            		
            		
               	
                	var grid2 = $("#grid-table-2");
                	var content2 = "[";
               	    var rows2 = grid2.jqGrid('getRowData');
               	    var isItemHava2 = false;
               	    
               	    if(rows2.length > 0){
             			isItemHava2 = true;
             	        for (var i in rows2) {
             	        		var id = rows2[i].id;
             	        		var itemId = rows2[i].itemId;
             			        var itemCode = rows2[i].itemCode; //品项id
             			        var giftAcount = $("#gift-account-"+id).val();//赠送数量
             			        var enable = $("#org-"+id).val(); //品项打折
             			        content2 +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",\"giftAcount\":\""+giftAcount+"\",\"enable\":\""+enable+"\"},";
          
             	        }
                    }	
               		if(isItemHava2)
               		{
               			content2 = content2.substring(0,content2.length-1);
               		}
               		content2 += "]";
               		
                    $.ajax({
                        type:'POST',
                        url:'DININGSYS/DgItemGiftPlan/save',
                        data:$("#basis").serialize(),
                        dataType:'JSON',
                        beforeSend : function(){
                        	
                        },
                        success:function (d) {
                            if(d.success){//插入主体数据成功后,插入附表数据
                            	 $.ajax({
                                     type:'POST',
                                     url:path+'/DININGSYS/DgItemGiftPlan/saveChild',
                                     data:{childContent:content2,id:d.id},
                                     dataType:'JSON',
                                     success:function (data) {
                                         if(data.success){//插入主体数据成功后,插入附表数据
                                             layer.close(addIndex);
                                             $("#grid-table").trigger("reloadGrid");
                                         }
                                     }
                                 })
                            }
                            else
                            {
                            	layer.alert(d.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});

                            }
                        }
                    })
                }
            });
        });
    });
    
    
    $("#delb").click(function(){
		var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }

        var ids = [];
        for(var i = 0;i < selectRow.length;i++){
            var id = grid.jqGrid('getCell',selectRow[i],'id');
            ids.push(id);
        }
        layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){

        $.ajax({
                type:'POST',
                url:'DININGSYS/DgItemGiftPlan/delData',
                data:{ids:ids.toString()},
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        $("#grid-table").trigger("reloadGrid");
                    }
                }
            });

            layer.close(index);
        });
	});
    
    $("#refresh").click(function(){
    	 $("#grid-table").trigger("reloadGrid");
    	
    });
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/DgItemGiftPlan/exportXls";
	});

    
    $("#trash").click(function(){
        $.get("DININGSYS/DgItemGiftPlan/trash",function(str){
            var addIndex = layer.open({
                type: 1,
                title:'品项打折(回收站)',
                skin: 'layui-layer-rim',
                area: ['850px', '90%'],
                content: str
            });
        });
    });
    
	$("#update").click(function(){
		var Pid = $("#Pid");
		var grid = $("#grid-table");
        var selectRow = grid.getGridParam("selarrrow");

        if(selectRow.length < 1){
            layer.alert('请选择需要修改的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
            return;
        }
        else if(selectRow.length >1)
        {
        	 layer.alert('只能同时修改一行数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
             return;
        }
        
        var id = grid.jqGrid('getCell',selectRow[0],'id'); //获取id
        
        $.get("DININGSYS/DgItemGiftPlan/add?id="+id,function(str){
        	  var addIndex = layer.open({
                  type: 1,
                  title:'赠送品项促销方案',
                  skin: 'layui-layer-rim',
                  area: ['850px', '90%'],
                  content: str,
                  btn:['确定','关闭'],
                  yes:function () {
                	  
                	  /**
                 		 *  加入会员打折品相
                 		 */
              		if(!checkUpdVals("basis"))
              		{
              			if(!checkUpdVals("detail"))
                  		{
                  			return;
                  		}
              			return;
              		}
                 		/**
                 		 *  加入会员打折品相
                 		 */
                  	var grid2 = $("#grid-table-2");
                  	var content2 = "[";
                 	    var rows2 = grid2.jqGrid('getRowData');
                 	    var isItemHava2 = false;
                 	    
                 	    if(rows2.length > 0){
               			isItemHava2 = true;
               	        for (var i in rows2) {
               	        		var id = rows2[i].id;
               	        		var itemId = rows2[i].itemId;
               			        var itemCode = rows2[i].itemCode; //品项id
             			        var giftAcount = $("#gift-account-"+id).val();//赠送数量
               			        var enable = $("#org-"+id).val(); //品项打折
               			        content2 +="{\"itemId\":\""+itemId+"\",\"itemCode\":\""+itemCode+"\",\"giftAcount\":\""+giftAcount+"\",\"enable\":\""+enable+"\"},";
            
               	        }
                      }	
                 	  if(isItemHava2)
                 	  {
                 			content2 = content2.substring(0,content2.length-1);
                 	  }
                 	  content2 += "]";
                      $.ajax({
                          type:'POST',
                          url:'DININGSYS/DgItemGiftPlan/save',
                          data:$("#basis").serialize(),
                          dataType:'JSON',
                          beforeSend : function(){
                          	
                          },
                          success:function (d) {
                              if(d.success){//插入主体数据成功后,插入附表数据
                              	 $.ajax({
                                       type:'POST',
                                       url:path+'/DININGSYS/DgItemGiftPlan/saveChild',
                                       data:{childContent:content2,id:d.id},
                                       dataType:'JSON',
                                       success:function (data) {
                                           if(data.success){//插入主体数据成功后,插入附表数据
                                               layer.close(addIndex);
                                               $("#grid-table").trigger("reloadGrid");
                                           }
                                       }
                                   })
                              }
                              else
                              {
                              	layer.alert(d.msg,{title :'提示',icon: 0, skin: 'layer-ext-moon'});

                              }
                          }
                      })
                  }
              });
        })
	});
    
	 /**
	初始化所有checkbox
     **/  
	$('input:checkbox').each(function() {
		if ($(this).prop("checked") == true) {
			$(this).val('1');
		}
		else
		{
			$(this).val('0');
		}
	});
    $('input:checkbox').click(function () {
		 this.blur();  
		 this.focus();
	});  
		 
	$("input:checkbox").change(function() {
		if ($(this).prop("checked") == true) {
			$(this).val('1');
		}
		else
		{
			$(this).val('0');
		}
	});	
});


function format(z,x,f){
	if(z == '1')
	{
		return '启用';
	}
	else
	{
		return '停用';
	}
}