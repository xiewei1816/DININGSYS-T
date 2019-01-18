<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <div id="editPanel" style="display: none;" title="费用登记">
   	<form id="editForm" class="editForm">
   		<input type="hidden" name="id" value="" editype="val" class="edit_id">
   		<ul class="edit-contents animated fadeInUp">
   			<li>
   				<span class="title"><span class="required">*</span>费用名称</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_fydjName" id="fydjName" name="fydjName" >
						 <c:forEach items="${listFyxm}" var="f" varStatus="index">
						 	<option  value="${f.id}" selected="selected">${f.fyxmName}</option>
						 </c:forEach>
					</select>
				</div>
   			</li>
			<li>
				<span class="title">支出费用</span>
				<div class="form-group biger-wid">
					<input type="number" maxlength="11" class="form-control edit_fydjExpend" id="fydjExpend" name="fydjExpend" editype="val" 
					dete="em-money" placeholder="0.00" onfocus="cheakChooseOne(this.id)"/>
				</div>
			</li>
			<li>
				<span class="title">收入费用</span>
				<div class="form-group biger-wid">
					<input type="number" maxlength="11" class="form-control edit_fydjEarning" id="fydjEarning" name="fydjEarning" editype="val" 
					dete="em-money" placeholder="0.00" onfocus="cheakChooseOne(this.id)" />
				</div>
			</li>
			<li>
				<span class="title"><span class="required">*</span>发生时间</span>
				<div class="form-group biger-wid">
					<input type="text" class="form-control edit_fydjTime" id="fydjTime" name="fydjTime" pattern="yyyy-MM-dd HH:mm:ss" editype="val" placeholder="选择发生时间" readonly
    					onclick="$.jeDate('#fydjTime',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD hh:mm:ss'})" dete="em-datetime">
				</div>
			</li>
			<li>
   				<span class="title"><span class="required">*</span>所属机构</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_fydjOrganization" id="fydjOrganization" name="fydjOrganization" >
						 <c:forEach items="${listOrg}" var="o" varStatus="index">
						 	<option  value="${o.id}" selected="selected">${o.orgName}</option>
						 </c:forEach>
					</select>
				</div>
			</li>
			<li>
				<span class="title">摘要</span>
				<div class="form-group biger-wid">
					<textarea class="form-control edit_fydjAbstract" name="fydjAbstract" editype="val" ></textarea>
				</div>
			</li>
   		</ul>	
   	</form>
 </div>