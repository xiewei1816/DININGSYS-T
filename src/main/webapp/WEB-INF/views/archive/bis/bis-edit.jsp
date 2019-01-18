<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- <jsp:include page="../../head.jsp"/> --%>
<script type="text/javascript" src="app/js/DININGSYS/archive/bis/bis_validation.js"></script>
<script>
    jQuery(document).ready(function () {
        bisValidation.editFormInit();
        
      	//初始化下拉列表数据
      	$("#bisOrganization option:first").prop("selected", 'selected');
		//所属机构
		var bisOrganizationVal = $("#bisOrganizationVal").val();
      	if(bisOrganizationVal != ""){
			$("#bisOrganization").val(bisOrganizationVal);
      	}
    })
</script>
 <body>
 <form id="myfrom" class="editForm" style="text-align: center;">
     <input type="hidden" id="hideId" name="id" value="${bis.id}">
     <ul class="edit-contents animated fadeInUp">
         <li>
             <span class="title"><span class="required">*</span>名称</span>
             <div class="form-group biger-wid">
                 <input placeholder="请输入名称" maxlength="18" type="text" class="form-control" id="bisName" name="bisName" value="${bis.bisName}"
                        >
             </div>
         </li>
         <li>
             <span class="title"><span class="required">*</span>开始时间</span>
             <div class="form-group bigest-wid">
                 <input type="text" class="form-control" id="bisTime" name="bisTime" value="${bis.bisTime}"
                        placeholder="输入开始时间" readonly
                        onclick="$.jeDate('#bisTime',{insTrigger:false,isTime:true,festival:true,format:'hh:mm'})"
                        >
             </div>
         </li>
         <li>
             <span class="title"><span class="required">*</span>所属机构</span>
             <div class="form-group bigest-wid">
             	<input type="hidden" id="bisOrganizationVal" value="${bis.bisOrganization }"/>
                 <select class="form-control" id="bisOrganization" name="bisOrganization" >
                     <c:forEach items="${listOrg}" var="o" varStatus="index">
                         <option value="${o.id}">${o.orgName}</option>
                     </c:forEach>
                 </select>
             </div>
         </li>
     </ul>
 </form>
 </body>