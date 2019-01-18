var inputSele;
var ssd;
var addIndex = 1;
var bf_pro_add = function () {
	
	function pagerInit()
	{
		var id = $("#ProId").val();
		$("#grid-table-6").jqGrid({
	        url:"DININGSYS/consumerSeatManager/getSdItem?id="+id,
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:180,//高度，表格高度。可为数值、百分比或'auto'
	        width:500,//自动宽
	        rowNum:-1,
	        colNames:["id","时段id","时段","收费金额","折扣"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'sd',index:'sd', width:'0%', align:'center',hidden:true},
	            {name:'sdName',index:'sdName', width:'20%',align:'center'},
	            {name:'money',index:'money', width:'20%',align:'center',formatter:formatPromMoneyText},
	            {name:'discount',index:'discount', width:'20%',align:'center',formatter:formatProDiscountText},
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为id
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        pgbuttons: false,
	        loadComplete : function() {
	        	initGrid();
	        	/**
	        	 * 隐藏grid中id项
	        	 */
	        	initCheckVals();
			}
	    });
	    $("#grid-table-6").hideCol("id");
	    
	    
		$("#grid-table-7").jqGrid({
	        url:"DININGSYS/consumerSeatManager/getNoSdItem?id="+id,
	        datatype:"json", //数据来源，本地数据
	        mtype:"POST",//提交方式
	        height:180,//高度，表格高度。可为数值、百分比或'auto'
	        width:500,//自动宽
	        rowNum:-1,
	        colNames:["id","时长","收费金额"],
	        colModel:[
	            {name:'id',index:'id', width:'0%', align:'center' },
	            {name:'tLong',index:'tLong', width:'20%', align:'center',formatter:formatTLongText},
	            {name:'money',index:'money', width:'20%',align:'center',formatter:formatPromMoneyText},
	        ],
	        rownumbers:true,//添加左侧行号
	        viewrecords: true,//是否在浏览导航栏显示记录总数
	        jsonReader:{
	            id: "id",//设置返回参数中，表格ID的名字为id
	            repeatitems : false
	        },
	        gridview: true,
	        autoencode: true,
	        multiselect:true,
	        loadComplete : function() {
	        	
	        	initGrid();
	        	/**
	        	 * 隐藏grid中id项
	        	 */
	        	initCheckVals();
	        }
	    });
	    $("#grid-table-7").hideCol("id");

	    
	    $("#btn-set").click(function()
	    {
	    	  var setVal = $("#set-val").val();
	    	  var setMonType = $("#set-type").val();
	    	  var rows = $("#grid-table-6").jqGrid('getRowData');
	          if(rows.length > 0){
	                for (var i in rows) {
	                	    var getRow = rows[i];//获取当前的数据行  
	       		            var id = getRow.id;
		       		        if(setMonType == '1')
		  	    		    {
		       		        	$("#mon-"+id).val(setVal);
			    		    }
		       		        else
		       		        {
		       		        	$("#dis-"+id).val(setVal);
		       		        }
	                }
	           }
	    });
	    
	   
	    
	    
	    $("#btn-add-pro").click(function(){
	    	 var data = {id:"s"+addIndex,tLong:"",money:""};
	    	 $("#grid-table-7").addRowData("s"+addIndex, data,"first");
	    	 addIndex++;
	    	 initGrid();
	         initCheckVals();
	    });
	    
	    
	    $("#btn-sub-pro").click(function(){
			var grid = $("#grid-table-7");
		    var selectRow = grid.getGridParam("selarrrow");
		    if(selectRow.length < 1){
		        layer.alert('请选择需要删除的数据！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
		        return;
		    }
		    layer.confirm('确认删除选中的数据？', {icon: 3, title:'提示'}, function(index){
		    	 var len = selectRow.length;
		         for (var i = 0; i < len; i ++) {
		         	grid.jqGrid("delRowData", selectRow[0]);
		         }
		         layer.close(index);
		    });
	    	
	    });
	    
		/**
		 * 清空
		 */
		$("#btn-clear-pro").click(function(){
			var grid = $("#grid-table-7");
		    layer.confirm('确认删除所有数据?', {icon: 3, title:'提示'}, function(index){
		    	 grid.jqGrid("clearGridData");
		         layer.close(index);
		    });
		});
	    
		
		
	    var setType = $("#set-type").val();
	    initSetType(setType);
	    
	    
	    $("#set-type").change(function()
	    {
	    	$("#set-val").val("");
	    	initSetType($("#set-val").val());
	    });
	    
	    ssd = $("#qd-ssd-check-2");
	    
	    //初始化设置
		inputSele = $("#pro-type");
		initSeleType();
		inputSele.change(function()
		{
			initSeleType();
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
			
			initSsd();
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
			initSsd();
		});
		
	}
    return {
 		pagerInit:function (){
 			pagerInit();
 		}
}

}();
function initSsd()
{
	if(ssd.val() == '1')
	{
		$("#ssd-min").removeAttr("disabled");
		$("#ssd-money").removeAttr("disabled");
	}
	else
	{
		$("#ssd-min").attr("disabled","disabled");
		$("#ssd-money").attr("disabled","disabled");
	}
}
function initSeleType()
{
	if(inputSele.val()=='1')
	{
		$("#qd-ssd-check-1").attr("name","qySsdsf");
		$("#qd-ssd-check-2").removeAttr("name");
		$("#grid-table-6").closest(".ui-jqgrid").show();
		$("#grid-table-7").closest(".ui-jqgrid").hide();
		$(".grid-content-2").hide();
		$(".grid-content-1").show();
		$(".type-min").show();
		$(".type-hour").hide();
		$(".type-ladder-tlong").hide();
		$("#set-content").show();
		$("#grid-table-6").setGridParam({colNames:["id","时段id","时段(按分钟)","收费金额","折扣"]});
	}
	else if(inputSele.val()=='4')
	{
		$("#qd-ssd-check-1").removeAttr("name");
		$("#qd-ssd-check-2").removeAttr("name");
		$("#grid-table-6").closest(".ui-jqgrid").show();
		$("#grid-table-7").closest(".ui-jqgrid").hide();
		$(".grid-content-2").hide();
		$(".grid-content-1").show();
		$(".type-min").hide();
		$(".type-hour").show();
		$(".type-ladder-tlong").hide();
		$("#set-content").show();
		$("#grid-table-6").setGridParam({colNames:["id","时段id","时段(按小时)","收费金额","折扣"]});
	}
	else
	{
		$("#qd-ssd-check-1").removeAttr("name");
		$("#qd-ssd-check-2").attr("name","qySsdsf");
		$("#grid-table-6").closest(".ui-jqgrid").hide();
		$("#grid-table-7").closest(".ui-jqgrid").show();
		$(".grid-content-1").hide();
		$(".grid-content-2").show();
		$(".type-min").hide();
		$(".type-hour").hide();
		$(".type-ladder-tlong").show();
		$("#set-content").hide();
	}	
}

function initGrid()
{
	$("#grid-table-7 tr.jqgrow td").css("height","15px").css("padding","1px");
	$("#gbox_grid-table-7 tr.ui-jqgrid-labels th").css("height","15px").css("padding","3px");	
	$("#grid-table-6 tr.jqgrow td").css("height","15px").css("padding","1px");
	$("#gbox_grid-table-6 tr.ui-jqgrid-labels th").css("height","15px").css("padding","3px");	
}


function formatPromMoneyText(z,x,f){
	if(z == null)
	{
		z='';
	}
	return "<input  type='text' checked='true' value='"+z+"' id='mon-"+f.id+"'  class='decimal'/>";
}

function formatProDiscountText(z,x,f){
	if(z == null)
	{
		z='';
	}
	return "<input  type='text' checked='true' value='"+z+"' id='dis-"+f.id+"'  class='discount'/>";
}

function formatTLongText(z,x,f)
{
	if(z == null)
	{
		z='';
	}
	return "<input  type='text' checked='true' value='"+z+"' id='tLong-"+f.id+"' class='number'/>";	
}

function initSetType(n)
{
	if(n == '1')
	{
		$("#set-val").removeClass("discount");
		$("#set-val").addClass("decimal");		
	}
	else
	{
		$("#set-val").addClass("discount");
		$("#set-val").removeClass("decimal");
	}
	initCheckVals();
}