<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<form id="addPanForm" class="addPanForm">
	<input type="hidden" value="${disP.id}" name="id" id="Pid"/>
	<div class="content">
		 <div class="form-inline">
		      <span class="text-1  code_title">方案编号:<font style="color: red;font-size: 25px">*</font></span>
	          <input placeholder="请输入方案编号" type="text" name="code" class="form-control" editype="val" dete="val" value="${disP.code}" style="width: 150px"/>
		      <span class="text-1 m-left  name_title" >方案名称:<font style="color: red;font-size: 25px">*</font></span>
	          <input type="text"  name="name"  placeholder="请输入方案" class="form-control" editype="val" dete="val"  value="${disP.name}" style="width: 150px"/>
		</div>
		<div class="form-inline">
		      <span class="text-1 type_title">方案类型:</span>
	          <select class="input-sele form-control" name="type" id="sele-type" onchange="seleChange(this)" dete="clstype" style="width: 150px">
	          	<option value="1" <c:if test="${'1' == disP.type}">selected</c:if>>按品项设置</option>
	          	<option value="2" <c:if test="${'2' == disP.type}">selected</c:if>>按小类设置</option>
	          </select>
	          <!--  <div class="checkbox m-left">
			    <label>
			      <input type="checkbox"  name="allowFDis" <c:if test="${'1' == disP.allowFDis}">checked</c:if>  class="inputStyle"/> 允许前台修改折扣比例
			    </label>
			  </div>-->
			  <div class="checkbox m-left">
			    <label>
			      <input type="checkbox"   name="disable" <c:if test="${'1' == disP.disable}">checked</c:if> class="inputStyle"/> 停用
			    </label>
			  </div>
		</div>
		<div class="form-inline">
			  <div class="checkbox">
			    <label>
			      <input type="checkbox" class="inputStyle use_time" name="dateLimit" <c:if test="${'1' == disP.dateLimit}">checked</c:if> /> 启用日期限制
			    </label>
			  </div>
		      <span class="m-left" >开始日期:</span>
	          <input  type="text" name="startTime" class="form-control  startTime" id="start-time" <c:if test="${'0' == disP.dateLimit}">disabled</c:if>  value="${disP.startTime}" style="width: 150px"/>
		      <span class="m-left" >结束日期:</span>
	          <input  type="text" name="endTime"   class="form-control  endTime" id="end-time" <c:if test="${'0' == disP.dateLimit}">disabled</c:if> value="${disP.endTime}" style="width: 150px"/>
		</div>
		<div class="form-inline">
		      <span class="item-count">共计:0个打折品项</span>
		      <span class="m-left discount_title">统一设置折扣:</span>
	          <input  class="form-control  discount" type="number" name="discount" min="1" max="100" value="${disP.discount}" onchange="yanz(this)" dete="discount" style="width: 100px">%
	          <input  type="button" name="set-s"  id="set-s" value="设置" class="input-button"/>
		      <input  type="button" name="add-s" id="add-s" value="增加" class="input-button"/>
		      <input  type="button" name="sub-s"  id="sub-s" value="删除" class="input-button"/>
		</div>
	</div>
	<div class="jqGrid_wrapper" style="margin: 0 auto;">
	         <table id="grid-table-3"></table>
	         <table id="grid-table-3-gate"></table>
	</div>
</form>



<script>
$(function () {
	pageUtil.pageInit({
		initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
		}
	});
	
	seleType = $("sele-type").val();
	var Pid = $("#Pid");
	var discount = $(".discount");
	
	//新增情况,设置为不可编辑
	if(Pid.val().length == 0)
	{
		$("#start-time").attr("disabled",true);
		$("#end-time").attr("disabled",true);
	}
	
	
	if(discount.val().length == 0)
	{
		discount.val('100');
	}
    $('.use_time').click(function () {
		   this.blur();  
		   this.focus();
	});  
		 
	$(".use_time").change(function() {
		if ($(this).prop("checked") == true) {
			$("#start-time").removeAttr("disabled");
			$("#end-time").removeAttr("disabled");
	    }
	    else
	    {
			$("#start-time").attr("disabled",true);
			$("#end-time").attr("disabled",true);
	    }
	});
	
	
	$("#grid-table-3").jqGrid({
        url:path+"/DININGSYS/ProDiscountPan/getPanItem",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:260,//高度，表格高度。可为数值、百分比或'auto'
        width:816,//自动宽
        colNames:["id","品项id","代码","品项名称","套餐","折扣比例","停用"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'itemId',index:'itemId', width:'0%', align:'center',hidden:true},
            {name:'code',index:'code', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'tc',index:'tc', width:'20%',align:'center',formatter:format,editable:true},
            {name:'discount',index:'discount', width:'20%',align:'center',formatter:format3},
            {name:'disable',index:'disable', width:'20%',align:'center',formatter:format1}
        ],
        postData:{"id":Pid.val(),"type":"1"},
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:-1,//每页显示记录数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为blackId
            repeatitems : false
        },
        multiselect:true,
        loadComplete : function() {
        	setClick();
        	var ids = $('#grid-table-3').getDataIDs();
            var len = ids.length;
        	$(".item-count").html("共计:"+len+"个打折品项");
		}
    });
	
	
	$("#grid-table-3-gate").jqGrid({
        url:path+"/DININGSYS/ProDiscountPan/getPanItem",
        datatype:"json", //数据来源，本地数据
        mtype:"POST",//提交方式
        height:260,//高度，表格高度。可为数值、百分比或'auto'
        width:816,//自动宽
        colNames:["id","品项id","代码","小类名称","折扣比例","停用"],
        colModel:[
            {name:'id',index:'id', width:'0%', align:'center' },
            {name:'itemId',index:'itemId', width:'0%', align:'center',hidden:true},
            {name:'code',index:'code', width:'20%',align:'center'},
            {name:'name',index:'name', width:'20%',align:'center'},
            {name:'discount',index:'discount', width:'20%',align:'center',formatter:format4},
            {name:'disable',index:'disable', width:'20%',align:'center',formatter:format2}
        ],
        postData:{"id":Pid.val(),"type":"2"},
        rownumbers:true,//添加左侧行号
        viewrecords: true,//是否在浏览导航栏显示记录总数
        rowNum:-1,//每页显示记录数
        jsonReader:{
            id: "id",//设置返回参数中，表格ID的名字为blackId
            repeatitems : false
        },
        multiselect:true,
        loadComplete : function() {
        	setClick();
        	var ids = $('#grid-table-3-gate').getDataIDs();
            var len = ids.length;
        	$(".item-count").html("共计:"+len+"个打折品项");
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
	
	
    $("#grid-table-3").hideCol("id");
    $("#grid-table-3").hideCol("pId");
    
    
    $("#grid-table-3-gate").hideCol("id");
	var inputSele = $(".input-sele");
	if(inputSele.val()=='1')
	{
		$("#grid-table-3").closest(".ui-jqgrid").show();
		$("#grid-table-3-gate").closest(".ui-jqgrid").hide();	
	}
	else
	{
		$("#grid-table-3").closest(".ui-jqgrid").hide();
		$("#grid-table-3-gate").closest(".ui-jqgrid").show();
	}
    
   
    
    $("#add-s").click(function(){
    	$.get(path+"/DININGSYS/ProDiscountPan/toAddPlanDish?type="+inputSele.val(),function(str){
            var addsIndex = layer.open({
                type: 1,
                title:'打折方案-品项选择窗口',
                skin: 'layui-layer-rim',
                area: ['850px', '600px'],
                content: str,
                btn:['确定','关闭'],
                yes:function () {
	                  var rows = $("#grid-table-5").jqGrid('getRowData'),
	                  chooseTrIds = new Array();
		              if(rows.length > 0){
		                    for (var i in rows) {
		                        chooseTrIds.push(rows[i].id);
		                  }
		              }	                
	                
	                  $.ajax({
	                  type:'POST',
	                  url:path+'/DININGSYS/ProDiscountPan/getPanItemByAdd',
	                  data:{chooseTrIds:chooseTrIds.toString(),type:inputSele.val()},
	                  dataType:'JSON',
	                  success:function (data) {
	                      if(data.success == true)
	                      {
	                    	/**  if(inputSele.val() == '1')
	                    	  {
	                    		  
		                    	  $("#grid-table-3").jqGrid("clearGridData"); //先清理
		                    	  for (var i in data.list) {
				                        $("#grid-table-3").addRowData(data.list[i].id, data.list[i],"first");
				                  }  
	                    	  }
	                    	  else
	                    	  {
	                    		  $("#grid-table-3-gate").jqGrid("clearGridData"); //先清理
		                    	  for (var i in data.list) {
				                        $("#grid-table-3-gate").addRowData(data.list[i].id, data.list[i],"first");
				                  }  
	                    	  }**/
	                    	  var hava_rows;
	                    	  if(inputSele.val() == '1')
	                    	  {
	                    		  hava_rows = $("#grid-table-3").jqGrid('getRowData');
	                    	  }
	                    	  else
	                    	  {
	                    		  hava_rows = $("#grid-table-3-gate").jqGrid('getRowData');
	                    	  }
	                    	  havaIds = new Array();
	                    	  gridTableIds = new Array();
	    		              if(hava_rows.length > 0){
	    		                    for (var i in hava_rows) {
	    		                    	havaIds.push(hava_rows[i].itemId);
	    		                    	gridTableIds.push(hava_rows[i].id);
	    		                  }
	    		              }	 
	    		              
	                    	  //先删除没有的项
	                    	  for (var i in havaIds) { 
	                    		    var ishava = false;
	                    		    for(var j in data.list)
	                    		    {
	                  		    		if(havaIds[i] == data.list[j].itemId)
	                  		    		{
	                  		    			ishava = true;
	                  		    			break;
	                  		    		}		
	                    		    }
	                    		    if(!ishava) //找到当前表格中没有,就添加数据
	                    		    {
	                    		    	if(inputSele.val() == '1')
	      	                    	    {
	                    		  			$("#grid-table-3").delRowData(gridTableIds[i]);
	      	                    	    }
	                    		    	else
	                    		    	{
	                    		    		$("#grid-table-3-gate").delRowData(gridTableIds[i]);
	                    		    	}
	                    		    }
			                  }
	                    	  
	                    	 //再增加没有的项
	                    	  for (var i in data.list) { 
	                    		    var ishava = false;
	                    		    for(var j in havaIds)
	                    		    {
                    		    		if(havaIds[j] == data.list[i].itemId)
                    		    		{
                    		    			ishava = true; //存在就不增加
                    		    		}		
	                    		    }
	                    		    if(!ishava) //找到当前表格中没有,就添加数据
	                    		    {
				                        if(inputSele.val() == '1')
	      	                    	    {
					                        $("#grid-table-3").addRowData(data.list[i].id, data.list[i]);
	      	                    	    }
	                    		    	else
	                    		    	{
	    			                        $("#grid-table-3-gate").addRowData(data.list[i].id, data.list[i]);
	                    		    	}
	                    		    }
			                  }
	                    	  setClick(); //设置监听
	                          layer.close(addsIndex);
	                          var ids;
	                          if(inputSele.val() == '1')
	                          {
	                        	  ids = $('#grid-table-3').getDataIDs();  
	                          }
	                          else
	                          {
	                        	  ids = $('#grid-table-3-gate').getDataIDs();  
	                          }
	                          var len = ids.length;
	                    	  $(".item-count").html("共计:"+len+"个打折品项");
	                      }
	                   }
                   })
                }
            });
        })
    });
    
    $("#sub-s").click(function(){
    	var grid;
    	if(inputSele.val() == '1')
  	    {
        	grid = $("#grid-table-3");
  	    }
    	else
    	{
    		grid = $("#grid-table-3-gate");
    	}
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
            
          	var ids = grid.getDataIDs();  
            var len = ids.length;
      	    $(".item-count").html("共计:"+len+"个打折品项");
    	});
    });
    
    
    $("#set-s").click(function(){
    	  var discount = $(".discount").val();
    	  if(discount.length<0)
    	  {
    		  layer.alert('请设置折扣比例',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
    		  return;
    	  }
    	  
    	  if(inputSele.val() == '1')
  	      {
			  $(".item-discount").val(discount);
  	      }
    	  else
    	  {
			  $(".gate-discount").val(discount);
    	  }
	});
    function format(v,x,r){
    	if(v == '1')
    	{
    		return "是";
    	}
    	else
    	{
    		return "否"
    	}
    }
    
    function format1(v,x,r){
    	if(r.disable == '1')
    	{
    		return "<input class='item-disable-checkbox' type='checkbox' checked='true'  id='item-check-"+r.id+"'/>";
    	}
    	else if(r.disable == '0')
    	{
    		return "<input class='item-disable-checkbox' type='checkbox'  id='item-check-"+r.id+"'/>";
    	}
    }
    function format2(v,x,r){
    	if(r.disable == '1')
    	{
    		return "<input class='gate-disable-checkbox' type='checkbox' checked='true'  id='gate-check-"+r.id+"'/>";
    	}
    	else if(r.disable == '0')
    	{
    		return "<input class='gate-disable-checkbox' type='checkbox'  id='gate-check-"+r.id+"'/>";
    	}
    }
    
    function format3(v,x,r){
    	return "<input class='item-discount' type='text'  id='item-dis-"+r.id+"' style='width:70px;' value='"+v+"' dete='discount'/>";
    }
    
    function format4(v,x,r){
    	return "<input class='gate-discount' type='text'  id='gate-dis-"+r.id+"' style='width:70px;' value='"+v+"' dete='discount'/>";
    }
    
    function setClick(){
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
    }
    /**
    		初始化所有checkbox
  	**/  
    setClick();
	
	
	
});
var seleType;
function yanz(obj)
{
	var y = '/(^0$)|(^100$)|(^\d{1,2}$)/';
	if(!y.test($(obj).val()))
	{
		$(obj).val('');
		alert('输入格式不正确!');
	}
}

function seleChange(obj){
	if($(obj).val() == '1')
	{
		$("#grid-table-3-gate").closest(".ui-jqgrid").hide();
		$("#grid-table-3").closest(".ui-jqgrid").show();
	}
	else{	
		$("#grid-table-3-gate").closest(".ui-jqgrid").show();
		$("#grid-table-3").closest(".ui-jqgrid").hide();
	}
}
</script>