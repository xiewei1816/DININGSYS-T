<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
$(function () {
	bf_pro_add.pagerInit();
});

</script>

<form class="form-horizontal" role="form" id="basics">
      <div class="form-body">
          <input type="hidden" value="${pro.id}" name="id" id="ProId"/>
          <div class="form-inline">
           	<span class="text-1 wid-120 code_title" >编号: <span class="required">*</span></span>
			<input  type="text"  class="form-control1 wid-300" name="code" value="${pro.code}" style="width: 300px;" dete="val"/>
          </div> 
          <div class="form-inline">
           	<span class="text-1 wid-120 name_title" >名称: <span class="required">*</span></span>
			<input  type="text"  class="form-control1 wid-300" name="name" value="${pro.name}" style="width: 300px;" dete="val"/>
          </div> 
          <div class="form-inline">
			      <span class="text-1 wid-120" >收费类型:<span class="required">*</span></span>
				   <select  class="form-control1 wid-300" name="type" id="pro-type" style="width: 150px;">
							<option value="1" <c:if test="${pro.type == '1'}">selected</c:if>>时段收费(按分钟)</option>
							<option value="2" <c:if test="${pro.type == '2'}">selected</c:if>>时阶收费</option>
							<option value="3" <c:if test="${pro.type == '3'}">selected</c:if>>时长收费</option>
							<option value="4" <c:if test="${pro.type == '4'}">selected</c:if>>时段收费按小时</option>
	              </select>
	              <span  class="type-min">
					<span class="text-1">不足: </span>
					<input  type="text"  class="form-control1 wid-300 number" name="typeMinVal" value="${pro.typeMinVal}" style="width: 50px;" />
					<span class="text-1 minVal" >分钟,按 0分钟补齐</span>
				 </span>
				 <span class="type-hour">
					<span class="text-1" >  尾时段不足: </span>
					<input  type="text"  class="form-control1 wid-300 number" name="typeHourVal"  value="${pro.typeHourVal}" style="width: 50px;" />
					<span class="text-1" >分钟,不计费</span>
				 </span>
		  </div> 
		  <div class="form-inline">
           	<span class="text-1 wid-120 amountUpLim_title" >收费金额上限: <span class="required">*</span></span>
			<input  type="text"  class="form-control1 wid-300 decimal" name="amountUpLim" value="${pro.amountUpLim}" style="width: 300px;" dete="em-money"/>
			<span class="text-1" > 0为不限制</span>
			
          </div>    
          <div class="form-inline">
           	<span class="text-1 wid-120 tLongLowLim_title" >收费时长下限: <span class="required">*</span></span>
			<input  type="text"  class="form-control1 wid-300 number" name="tLongLowLim" value="${pro.tLongLowLim}" style="width: 300px;" dete="isnumber"/>
			<span class="text-1" > 分钟,小于该时长,则不收费</span>
          </div>    
          <div class="form-inline">
	           	<span class="text-1 wid-120" >计时单位: <span class="required">*</span></span>
				<input  type="text"  class="form-control1 wid-300"  value="分钟" style="width: 300px;" readonly="readonly"/>
          </div> 
          <div class="checkbox type-min"  style="margin-left: 150px">
				  <label>
				    <input type="checkbox" name="qySsdsf" id="qd-ssd-check-1" value="${pro.qySsdsf}" <c:if test="${pro.qySsdsf == '1'}">checked</c:if>>
				 		 启动首时段收费:首时段不足1小时按一小时补齐
				  </label>
		  </div>
          
          
          <div class="checkbox type-ladder-tlong"   style="margin-left: 150px" >
				  <label>
				    <input type="checkbox" name="qySsdsf" id="qd-ssd-check-2" value="${pro.qySsdsf}" <c:if test="${pro.qySsdsf == '1'}">checked</c:if>>
				 		 启动首时段收费:
				  </label>
				  <input  type="text"  class="form-control1 wid-300 number" name="ssdsfMin" id="ssd-min" value="${pro.ssdsfMin}" style="width: 50px;"/>
				  <span class="text-1" >分钟内,收费</span>
				  <input  type="text"  class="form-control1 wid-300 decimal" name="ssdsfMoney" id="ssd-money" value="${pro.ssdsfMoney}" style="width: 50px;"/>
				  <span class="text-1" >元</span>
		  </div>
		  
          <div class="grid-content-1">
               <div class="grid-left">
          		    <table id="grid-table-6"></table>
          		</div>
          </div>
          
          <div class="grid-content-2">
          		<div class="grid-left">
          		    <table id="grid-table-7"></table>
          		</div>
                <div class="grid-right">
       				<input type="button" value="增加" class="grid-btn" id="btn-add-pro">
					<input type="button" value="减少" class="grid-btn" id="btn-sub-pro">
					<input type="button" value="清空" class="grid-btn" id="btn-clear-pro">
			    </div>
          </div>
          
          <div class="form-inline" id="set-content" style="clear: both;margin-left: 150px;margin-top: 30px;">
				   <select  class="form-control1 wid-300"  id="set-type" style="width: 150px;">
							<option value="1" selected>收费金额</option>
							<option value="2" >折扣</option>
	              </select>
					<span class="text-1" >  全部设置为: </span>
					<input  type="text"  class="form-control1 wid-300" id="set-val" style="width: 50px;"/>
					<input type="button" value="全部设置" class="btn-inline" id="btn-set">
		  </div> 
       </div>
</form>