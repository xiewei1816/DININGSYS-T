$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	
	var startTime = {
			format: 'YYYY-MM-DD',
			festival:true,
			isinitVal:true,
			choosefun: function(elem,datas){
				startTime.minDate = '2017-08-01'
			}
	};
	$('#time').jeDate(startTime); 
	
	$("#grid-table").jqGrid({
		url:path+"/DININGSYS/yqshapi/log/getPageList",
		postData:{oper:$("#oper").val(),time:$("#time").val(),type:$("#type").val(),seatName:$("#seatName").val()},
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
   		height:pageUtil.tabHei,
    	autowidth:true,//自动宽
        rowNum:100,//每页显示记录数
        rowList:[100,200],//用于改变显示行数的下拉列表框的元素数组。
        colNames:["id","营业流水号","操作员","客位","操作类型","操作明细","操作时间"],
        colModel:[
            {name:'uuid',width:'0%', align:'center' ,hidden:true},
            {name:'owNum', width:'15%', align:'center'},
            {name:'oper',width:'5%',align:'center'},
            {name:'seatName',width:'5%',align:'center'},
            {name:'type',width:'15%',align:'center',formatter:typeFormat},
            {name:'content',width:'55%',align:'center'},
            {name:'time',width:'10%',align:'center'}
        ],
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        gridview: true,
        autoencode: true,
        multiselect:true,
    	styleUI : 'Bootstrap',
        pager:$('#grid-pager'),
        loadComplete : function() {
        }
    });

	
	/**
	 * 隐藏grid中id项
	 */
    $("#grid-table").hideCol("id");
    
    $("#search").click(function(e){		
		 var postData = {oper:$("#oper").val(),time:$("#time").val(),type:$("#type").val(),seatName:$("#seatName").val()};
		 jQuery("#grid-table").jqGrid('setGridParam',{postData:postData,page:1}).trigger("reloadGrid");	
	});
    
	 $(window).resize(function(){
		 	var needh = $(".search-wrapper").outerHeight()  + 80;
			var height = ($(window).height() - needh);
 		 $("#grid-table").setGridHeight(height);
 	});
	 
	function typeFormat(v,x,r){
		if(v == '1'){
			return "更换客位";
		} else if(v == '2'){
			return "加入团队"
		}else if(v == '3'){
			return "更改客位人数"
		}else if(v == '4'){
			return "修改品项数量"
		}else if(v == '5'){
			return "修改品项价格"
		}else if(v == '6'){
			return "品项赠送"
		}else if(v == '7'){
			return "撤销品项赠送"
		}else if(v == '8'){
			return "单品转台"
		}else if(v == '9'){
			return "转账"
		}else if(v == '10'){
			return "拆帐"
		}else if(v == '11'){
			return "关账"
		}else if(v == '12'){
			return "挂S帐"
		}else if(v == '13'){
			return "撤销S帐"
		}else if(v == '14'){
			return "结班"
		}
	}
	
//	<option value="1">更换客位</option>
//	<option value="2">加入团队</option>
//	<option value="3">更改客位人数</option>
//	<option value="4">修改品项数量</option>
//	<option value="5">修改品项价格</option>
//	<option value="6">品项赠送</option>
//	<option value="7">撤销品项赠送</option>
//	<option value="8">单品转台</option>
//	<option value="9">转账</option>
//	<option value="10">拆帐</option>
//	<option value="11">关账</option>
//	<option value="12">挂S帐</option>
//	<option value="13">撤销S帐</option>
//	<option value="14">结班</option>
});