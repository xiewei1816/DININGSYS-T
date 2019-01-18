<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- <jsp:include page="../head.jsp"/> --%>
<style type="text/css">
	body{font-family:"微软雅黑";font-size:16px;}
	input{width:120px;height: 24px;vertical-align:middle;}
	select{width:120px;}
	p{color: #6ab46a; float: left;}
	#main{margin: 0 auto; width: 660px;height: 580px;}
	.required{color: red;}
</style>
<body>
<div id="main" align="center">
<form id="formid" class="editForm">
	<input type="hidden" name="id" value="${dgc.id }" />
	<input type="hidden" id="clFlag" value="${clFlag }">
	<table style="border-collapse:separate; border-spacing:1px;">
		<tr>
			<td colspan="6"><p>客户信息</p></td>
		</tr>
		<tr>
			<td>客户名称:<span class="required">*</span></td>	
			<td><input type="text" maxlength="12" id="clientNameEdit" name="clientName" value="${dgc.clientName }"/></td>
			<td>客户电话:<span class="required">*</span></td>
			<td><input type="text" maxlength="13" id="clientPhoneEdit" name="clientPhone" value="${dgc.clientPhone }"/></td>
			<td>客位:</td>
			<td>
				<input type="hidden" id="clientSeatVal" value="${dgc.clientSeat }"/>
				<select id="clientSeatEdit" name="clientSeat">
					<option selected="selected"></option>
					<c:forEach items="${seatList}" var="seat" varStatus="status">
						<option  value="${seat.id}" >${seat.name}</option>
					</c:forEach>
				</select>
			</td>
 		</tr>
 		<tr>
 			<td colspan="6"><hr/><p>物品信息</p></td>
 		</tr>
 		<tr>
			<td>物品寄存种类:</td>
			<td>
				<input type="hidden" id="goodsTypeVal" value="${dgc.goodsType }"/>
				<select id="goodsTypeEdit" name="goodsType">
					<option selected="selected"></option>
					<c:forEach items="${dgctList}" var="dgct" varStatus="status">
						<option  value="${dgct.id}" >${dgct.gt_name}</option>
					</c:forEach>
				</select>
			</td>
			<td>物品编号:</td>
			<td><input type="text" maxlength="12" id="goodsCode" name="goodsCode" value="${dgc.goodsCode }"/></td>
			<td>物品名称:<span class="required">*</span></td>
			<td><input type="text" maxlength="16" id="goodsNameEdit" name="goodsName" value="${dgc.goodsName }"/></td>
		</tr>
		<tr>
			<td>数量:<span class="required">*</span></td>
			<td><input type="number" maxlength="3" id="goodsNumberEdit" name="goodsNumber" value="${dgc.goodsNumber }"/></td>
			<td>规格:</td>
			<td><input type="text" maxlength="24" id="goodsSpecification" name="goodsSpecification" value="${dgc.goodsSpecification }"/></td>
			<td>颜色:</td>
			<td><input type="text" maxlength="8" id="goodsColor" name="goodsColor" value="${dgc.goodsColor }"/></td>
		</tr>
		<tr>
			<td>保质截止日期:</td>
			<td>
				<input readonly type="text" id="goodsExpirationDateEdit" name="goodsExpirationDate" value="${dgc.goodsExpirationDate }"
						onclick="$.jeDate('#goodsExpirationDateEdit',{zIndex:19891116,insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})" >
			</td>
			<td>说明:</td>
			<td colspan="3"><input style="width: 326px;" type="text" maxlength="64" id="goodsExplain" name="goodsExplain" value="${dgc.goodsExplain }"/></td>
 		</tr>
 		<tr>
 			<td colspan="6"><hr/><p>寄存信息</p></td>
 		</tr>
 		<tr>
 			<td>寄存状态:</td>
 			<td>
 				<input type="hidden" id="gcFlagVal" value="${dgc.gcFlag }"/>
 				<select id="gcFlag" name="gcFlag">
					<option value="1" selected="selected" >寄存</option>
					<option value="2" >取出</option>
					<option value="3" >处理</option>
				</select>
 			</td>
			<td>寄存操作POS:</td>
			<td><input type="text" maxlength="16" id="gcPos" name="gcPos" value="${dgc.gcPos }"/></td>
			<td>寄存操作员:</td>
			<td><input type="text" maxlength="12" id="gcOperator" name="gcOperator" value="${dgc.gcOperator }"/></td>
		</tr>
		<tr>
			<td>寄存时间:</td>
			<td colspan="2">
				<input readonly style="width: 100%;" type="text" id="gcStartTimeEdit" name="gcStartTime" value="${dgc.gcStartTime }" >
			</td>
			<td>寄存截止时间:</td>
			<td colspan="2">
				<input readonly style="width: 100%;" type="text" id="gcEndTimeEdit" name="gcEndTime" value="${dgc.gcEndTime }" >
			</td>
 		</tr>
 		<tr>
 			<td>寄存位置:</td>
			<td colspan="5"><input style="width: 548px;" type="text" maxlength="36" id="gcAddress" name="gcAddress" value="${dgc.gcAddress }"/></td>
 		</tr>
 		<tr id="cl_tr1">
 			<td colspan="6"><hr/><p>处理信息</p></td>
 		</tr>
 		<tr id="cl_tr2">
 			<td>处理方式:</td>
 			<td><input type="text" maxlength="12" id="clWay" name="clWay" value="${dgc.clWay }"/></td>
			<td>处理操作POS:</td>
			<td><input type="text" maxlength="16" id="clPos" name="clPos" value="${dgc.clPos }"/></td>
			<td>处理操作员:</td>
			<td><input type="text" maxlength="12" id="clOperator" name="clOperator" value="${dgc.clOperator }"/></td>
 		</tr>
 		<tr id="cl_tr3">
 			<td>处理说明:</td>
			<td colspan="5"><input style="width: 548px;" type="text" maxlength="64" id="clExplain" name="clExplain" value="${dgc.clExplain }"/></td>
 		</tr>
 	</table>
 </form>
 </div>
 </body>
<script type="text/javascript">
	$(document).ready(function(){
		//初始化寄存时间
		if($("#gcStartTimeEdit").val() == ""){
			$("#gcStartTimeEdit").jeDate({
				zIndex:19891116,
			    isinitVal:true,
			    initAddVal:[0],
			    festival: true,
			    format: 'YYYY-MM-DD hh:mm:ss'
			});
		}
		$("#gcEndTimeEdit").jeDate({
		    format:"YYYY-MM-DD hh:mm:ss",
		    isTime:true, 
		    festival: true,
		    minDate:$("#gcStartTimeEdit").val()
		});
		
		//初始化下拉列表数据
		//客位
		var clientSeatVal = $("#clientSeatVal").val();
		var clientSeatOption = $("#clientSeatEdit option");
		clientSeatOption.each(function(){
    		if($(this).text() == clientSeatVal){
   				$(this).attr("selected",true);
    		}
		}); 
		//寄存物品类型
		var goodsTypeVal = $("#goodsTypeVal").val();
		var goodsTypeOption = $("#goodsTypeEdit option");
		goodsTypeOption.each(function(){
    		if($(this).text() == goodsTypeVal){
   				$(this).attr("selected",true);
    		}
		});
		//寄存状态
		var gcFlagVal = $("#gcFlagVal").val();
		if(gcFlagVal != ""){
			$("#gcFlag").val(gcFlagVal);
		}
		
		//处理状态判断显示
		var clFlag = $("#clFlag").val();
		if(clFlag != ""){
			$("#cl_tr1").show();
			$("#cl_tr2").show();
			$("#cl_tr3").show();
			$("#gcFlag").val(clFlag);
		}else{
			$("#cl_tr1").hide();
			$("#cl_tr2").hide();
			$("#cl_tr3").hide();
		}
		/* alert(goodsTypeVal +"-"+ clientSeatVal +"-"+ gcFlagVal); */
	});
</script>
