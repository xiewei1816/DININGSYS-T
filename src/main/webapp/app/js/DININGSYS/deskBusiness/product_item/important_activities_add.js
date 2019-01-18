var important_activities_add = function () {
	
	function pagerInit()
	{
		pageUtil.pageInit({
			initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
			}
		});
		var Pid = $("#Pid");
		var gateCounter = $(".gate-counter");
		$("#grid-table-1").jqGrid({
	        url:"DININGSYS/ProImportant/getAllImportChild?id="+Pid.val(),
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:230,//高度，表格高度。可为数值、百分比或'auto'
	        width:816,//自动宽
	        rowNum:-1,
	        colNames:["id","小类名称","小类代码","优惠比例","品项id"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'name',index:'name', width:'20%',align:'center'},
	            {name:'code',index:'code', width:'20%',align:'center'},
	            {name:'discount',index:'discount', width:'20%',align:'center',formatter:format1},
	            {name:'gateId',index:'gateId', width:'0%',align:'center',hidden:true}
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为id
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        loadComplete : function() {
	        	var grid = $("#grid-table-1");
        		var ids = grid.getDataIDs();//返回数据表的ID数组["66","39"..]  
        	    var len = ids.length; 
        	    gateCounter.html(len+"个小类");
	        }
	    });
	    $("#grid-table-1").hideCol("id");
	    
	    var discount = $(".discount");
	    if(discount.val().length == 0)
	    {
	    	discount.val("100");
	    }
	    
	    $("#set-s").click(function(){
	    	  var discount = $(".discount").val();
	    	  if(discount.length<0)
	    	  {
	    		  layer.alert('请设置折扣比例',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
	    		  return;
	    	  }
	      	  var grid = $("#grid-table-1");
	    	  
	    	  var rows = grid.jqGrid('getRowData');
	          if(rows.length > 0){
	                for (var i in rows) {
	                	var getRow = rows[i];//获取当前的数据行  
	       		        var id = getRow.id;
	       		        $(".item-discount").val(discount);
	       		     	//grid.jqGrid('setCell',id, 'discount', discount);
	              }
	          }
		});
	    
	    
	    
	    var start ={
				format:"YYYY-MM-DD",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				minDate:$.nowDate(0), //设定最小日期为当前日期
				zIndex:29891015,
				choosefun: function(elem,datas){
			         end.minDate = datas; //开始日选好后，重置结束日的最小日期
			    }
		};

		var end ={
				format:"YYYY-MM-DD",
				isinitVal:true, //显示时间
				isTime:true, 
			    festival: true, //显示节日
				minDate:$.nowDate(0), //设定最小日期为当前日期
				zIndex:29891015,
				choosefun: function(elem,datas){
			         start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
			    }
		};
		
		$('#start-time').jeDate(start);
		$('#end-time').jeDate(end);
		
		
	}
    return {
 		pagerInit:function (){
 			pagerInit();
 		}
}

}();

function format(z,x,f){
	if(z == '1')
	{
		return '是';
	}
	else
	{
		return '否';
	}
}
function format1(c,o,r){
	return "<input class='item-discount' type='text'  id='dis-"+r.id+"' style='width:70px;' value='"+c+"' dete='discount'/>";
}