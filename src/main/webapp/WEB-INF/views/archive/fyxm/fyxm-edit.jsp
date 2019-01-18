<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="editPanel" style="display: none;" title="费用项目">
   	<form id="editForm" class="editForm">
   		<input type="hidden" name="id" value="" editype="val" class="edit_id">
   		<ul class="edit-contents animated fadeInUp">
   			<li style="display: none;">
   				<span class="title"><span class="required">*</span>编号</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_fyxmNum" name="fyxmNum" editype="val" placeholder="请输入编号"/>
				</div>
   			</li>
			<li>
				<span class="title"><span class="required">*</span>名称</span>
				<div class="form-group biger-wid">
					<input type="text" maxlength="12" class="form-control edit_fyxmName" name="fyxmName" editype="val" dete="val" placeholder="请输入名称" />
				</div>
			</li>
   			<li>
   				<span class="title"><span class="required">*</span>费用类型</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_fyxmType" id="fyxmType" name="fyxmType" editype="val" dete="val">
						 <c:if test="${empty listFylx}"><option selected></option></c:if>
						 <c:forEach items="${listFylx}" var="o" varStatus="index">
						 	<option value="${o.id}">${o.fylxName}</option>
						 </c:forEach>
					</select>
				</div>
   			</li>
			<li>
   				<span class="title">所属机构</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_fyxmOrganization" id="fyxmOrganization" name="fyxmOrganization" >
						 <c:forEach items="${listOrg}" var="o" varStatus="index">
						 	<option  value="${o.id}">${o.orgName}</option>
						 </c:forEach>
					</select>
				</div>
			</li>
   		</ul>	
   	</form>
</div>