$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	/**
	 * 保存星期
	 */
	
	$("#save").click(function(){
        layer.confirm('确认保存当前选择的品项打折方案数据?', {icon: 3, title:'提示'}, function(index){
        	var send='[';
        	$(".select-week option:selected").each(function(){
        		var id = $(this).attr("edl");
        		var val = $(this).val();
        		send +="{\"id\":\""+id+"\",\"val\":\""+val+"\"},";
        	});
        	send = send.substring(0,send.length-1);
        	send += "]";
            $.ajax({
                    type:'POST',
                    url:path+'/DININGSYS/ProDiscountPan/saveWeekData',
                    data:{data:send},
                    dataType:'JSON',
                    success:function (data) {
                        if(data.success){
                        }
                    }
                });
                layer.close(index);
            });
		
		
	});
	
    
    $("#export").click(function(){
		window.location.href=path+"/DININGSYS/consumerSeatManager/exportAreaXls";
	});
    
    
    $("#refresh").click(function(){
        $("#grid-table-1").trigger("reloadGrid");
    });
    
	$("#grid-table-1").jqGrid({
        url:path+"/DININGSYS/consumerSeatManager/getAllAreaItem",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei/2,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        rowNum:-1,
        colNames:["id","编号","消费区域名称","时价品项允许打折","促销品项允许打折","变价品项允许打折","包房费允许打折","服务费允许打折","最低消费允许打折","消费区域销售品项总数"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'code',index:'code', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'sjDiscount',index:'sjDiscount', width:'20%',align:'center',formatter:formatDiscount},
            {name:'cxDiscount',index:'cxDiscount', width:'20%',align:'center',formatter:formatDiscount},
            {name:'bjDiscount',index:'bjDiscount', width:'20%',align:'center',formatter:formatDiscount},
            {name:'bffDiscount',index:'bffDiscount', width:'20%',align:'center',formatter:formatDiscount},
            {name:'fwfDiscount',index:'fwfDiscount', width:'20%',align:'center',formatter:formatDiscount},
            {name:'zdxfDiscount',index:'zdxfDiscount', width:'20%',align:'center',formatter:formatDiscount},
            {name:'limCount',index:'limCount', width:'20%',align:'center'}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        }
    });
    $("#grid-table-1").hideCol("id");
    
    
	$("#grid-table-2").jqGrid({
        url:path+"/DININGSYS/consumerSeatManager/getAllAreaConsumerSeatItem",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:pageUtil.tabHei/2,//高度，表格高度。可为数值、百分比或'auto'
        autowidth:true,//自动宽
        rowNum:-1,
        colNames:["id","编号","客位名称","所属消费区域","开单自动增加品项数目","是否收取","收取方式"
                  ,"收取金额","是否收取","收取方式","上限","是否收取","收取方式","收取金额","包房费品项"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'seatCode',index:'seatCode', width:'20%',align:'center'},
            {name:'seatName',index:'seatName', width:'20%',align:'center'},
            {name:'areaString',index:'areaString', width:'20%',align:'center'},
            {name:'itemCount',index:'itemCount', width:'20%',align:'center'},
            {name:'zdxf',index:'zdxf', width:'20%',align:'center',formatter:formatIS},
            {name:'zdxfType',index:'zdxfType', width:'20%',align:'center',formatter:formatZdxf},
            {name:'zdxfMoney',index:'zdxfMoney', width:'20%',align:'center'},
            {name:'fwf',index:'fwf', width:'20%',align:'center',formatter:formatIS},
            {name:'fwfType',index:'fwfType', width:'20%',align:'center',formatter:formatFwf},
            {name:'fwfSx',index:'fwfSx', width:'20%',align:'center'},
            {name:'bff',index:'bff', width:'20%',align:'center',formatter:formatIS},
            {name:'bffType',index:'bffType', width:'20%',align:'center',formatter:formatBff},
            {name:'bffGd',index:'bffGd', width:'20%',align:'center'},
            {name:'bffItemName',index:'bffItemName', width:'20%',align:'center'}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为id
            repeatitems : false
        }
    });
	
	jQuery("#grid-table-2").jqGrid('setGroupHeaders', {
	    useColSpanStyle: true,
	    groupHeaders:[
	      	        {startColumnName:'zdxf', numberOfColumns:3, titleText: '最低消费'},
	    	        {startColumnName:'fwf', numberOfColumns: 3, titleText: '服务费'},
	    	        {startColumnName:'bff', numberOfColumns: 4, titleText: '包房费'}
	    ]
	});
	
    $("#grid-table-2").hideCol("id");
    
});
function formatDiscount(v,x,r){
	if(v == '1')
	{
		return "是";
	}
	else
	{
		return "否"
	}
}

function formatIS(v,x,r){
	if(v == '1' || v == null)
	{
		return "否";
	}
	else
	{
		return "是"
	}
}
function formatZdxf(v,x,r){
	if(v == '1')
	{
		return "按每客位";
	}
	else if(v == '2')
	{
		return "按每客位人数";
	}
	else
	{
		return "";
	}
}
function formatFwf(v,x,r){
	if(v == '1' || v == null)
	{
		return "不收取";
	}
	else if(v == '2')
	{
		return "按固定金额"
	}
	else if(v == '3')
	{
		return "按消费比例"
	}
	else if(v == '4')
	{
		return "按客位人数"
	}
}
function formatBff(v,x,r){
	if(v == '1' || v == null)
	{
		return "不收取";
	}
	else if(v == '2')
	{
		return "按固定金额"
	}
	else if(v == '3')
	{
		return "按消费比例"
	}
	else if(v == '4')
	{
		return "固定包房收费方案"
	}
	else if(v == '5')
	{
		return "一周内设置不同的包房收费方案"
	}
}