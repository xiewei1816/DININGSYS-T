<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <div id="editPanel" style="display: none;" title="退菜原因类型维护信息">
  	<form id="editForm" class="editForm">
  		<input type="hidden" name="id" value="" editype="val" class="edit_id">
  		<ul class="edit-contents animated fadeInUp">
  			<li style="display: none;">
  				<span class="title"><span class="required">*</span>编号</span>
			<div class="form-group bigest-wid">
				<input type="text" class="form-control edit_rfctCode" name="rfctCode" editype="val" placeholder="请输入编号"/>
			</div>
  			</li>
		<li>
			<span class="title"><span class="required">*</span>名称</span>
			<div class="form-group biger-wid">
				<input type="text" maxlength="12" class="form-control edit_rfctName" name="rfctName" editype="val" dete="val" placeholder="请输入名称" />
			</div>
		</li>
		<li>
  				<span class="title">所属机构</span>
			<div class="form-group bigest-wid">
				<select class="form-control edit_rfctOrganization" id="rfctOrganization" name="rfctOrganization" >
					 <c:forEach items="${listOrg}" var="o" varStatus="index">
					 	<option  value="${o.id}" selected="selected">${o.orgName}</option>
					 </c:forEach>
				</select>
			</div>
		</li>
  			<li>
  				<span class="title">默认设置</span>
			<div class="form-group bigest-wid">
				<label class="radiobtn" ><input type="radio" value="0" class="edit_isDefaultFlag"
				 name="isDefaultFlag" editype="radio" /><i></i><span></span>否</label>
				<label class="radiobtn"><input type="radio" value="1" class="edit_isDefaultFlag"
				 name="isDefaultFlag" editype="radio" /><i></i><span></span>是</label>
			</div>
  			</li>
  		</ul>	
  	</form>
  </div>