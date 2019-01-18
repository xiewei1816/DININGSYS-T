<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
$(document).ready(function(){
	var useTypeVal = $("#useTypeVal").val()
	if(useTypeVal != ""){
   		$("#useType").val($("#useTypeVal").val());
	}
});
</script>
<form id="myform" class="editForm" style="text-align: center;">
    <input type="hidden" id="hideId" name="id" value="${dep.id}">
   		<ul class="edit-contents animated fadeInUp">
   			<li>
   				<span class="title"><span class="required">*</span>部门代码</span>
				<div class="form-group bigest-wid">
					<input readonly="readonly" maxlength="12" class="form-control" type="text" id="depCode" name="depCode" value="${dep.depCode}" />
				</div>
   			</li>
   			<li>
				<span class="title"><span class="required">*</span>部门名称</span>
				<div class="form-group biger-wid">
					<input type="text" maxlength="24" class="form-control" id="depName" name="depName" placeholder="请输入部门名称" value="${dep.depName}" />
				</div>
			</li>
   			<li>
   				<span class="title"><span class="required">*</span>速记码</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="18" class="form-control" id="depSjm" name="depSjm" placeholder="请输入速记码" value="${dep.depSjm}"/>
				</div>
   			</li>
   			<li>
   				<span class="title"><span class="required">*</span>上级部门</span>
				<div class="form-group bigest-wid">
	                <input type="text" class="form-control" name="depDepartment" value="${dep.depDepartment}" style="display: none;"/>
	            	<input type="text" class="form-control" id="depDepartmentName" value="${depDepartmentName}" readonly="readonly"/>
				</div>
   			</li>
   			<li>
   				<span class="title"><span class="required">*</span>使用类型</span>
				<div class="form-group bigest-wid">
					<input type="text" id="useTypeVal" value="${dep.useType}" style="display: none;"/>
					<select class="form-control" id="useType" name="useType" >
						<c:forEach items="${list}" var="map" varStatus="status"> 
							<c:forEach items="${map.SYLX}" var="o" varStatus="index">
							 	<option  value="${o.id}" >${o.cName}</option>
							 </c:forEach>
						</c:forEach>
					</select>
				</div>
   			</li>
   			 <c:choose>  
			    <c:when test="${listOrg != null }">
			    	<li>
		   				<span class="title"><span class="required">*</span>所属机构</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_empOrganization" id="depOrganizationSel" name="depOrganization" >
								 <c:forEach items="${listOrg}" var="o" varStatus="index">
								 	<option  value="${o.id}" selected="selected">${o.orgName}</option>
								 </c:forEach>
							</select>
						</div>
	   				</li>
			    </c:when>  
			    <c:otherwise>
			    	<li>
	   					<span class="title"><span class="required">*</span>所属机构</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control" name="depOrganization" value="${dep.depOrganization}" style="display: none;"/>
							<input type="text" class="form-control" id="depOrganizationName" value="${depOrganizationName}" readonly="readonly"/>
						</div>
	   				</li>
			    </c:otherwise>
			 </c:choose>
   		</ul>	
   	</form>

