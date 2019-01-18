<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="editPanel" style="display: none;" title="整单备注">
   	<form id="editForm" class="editForm">
   		<input type="hidden" name="id" value="" editype="val" class="edit_id">
   		<ul class="edit-contents animated fadeInUp">
   			<li>
   				<span class="title"><span class="required">*</span>编号</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="12" class="form-control edit_zdbzNum" name="zdbzNum" editype="val" dete="val" placeholder="请输入编号"/>
				</div>
   			</li>
			<li>
   				<span class="title"><span class="required">*</span>所属机构</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_zdbzOrganization" id="zdbzOrganization" name="zdbzOrganization" editype="val" dete="val" >
						 <c:forEach items="${listOrg}" var="o" varStatus="index">
						 	<option  value="${o.id}" >${o.orgName}</option>
						 </c:forEach>
					</select>
				</div>
			</li>
			<li>
				<span class="title"><span class="required">*</span>整单备注</span>
				<div class="form-group biger-wid">
					<textarea maxlength="128" rows="" cols="" class="form-control edit_orderRemark" name="orderRemark" editype="val" dete="val" placeholder="请输入整单备注"></textarea>
				</div>
			</li>
   		</ul>	
   	</form>
   </div>