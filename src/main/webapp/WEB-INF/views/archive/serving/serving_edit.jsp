<%--
  Created by zhshuo.
  Date: 2016-10-08
  Time: 10:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="app/js/DININGSYS/archive/serving/as_serving_validation.js"></script>
<script type="text/javascript">
$(function(){
    asServingValidation.editFormInit();
	//初始化下拉列表默认选中第一条数据
	$("#sorg option:first").prop("selected", 'selected');
    //初始化组织机构
	var sorgVal = $("#sorgVal").val();
    if(sorgVal != ""){
	    $("#sorg").val(sorgVal);
    }
});
</script>
<form id="editForm" class="editForm">
    <input type="hidden" id="hideId" name="id" value="${dgServing.id}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>编码</span>
            <div class="form-group biger-wid">
                <c:choose>
                    <c:when test="${not empty maxCode}">
                        <input readonly placeholder="请输入编码"  maxlength="10" type="text" class="form-control" id="scode" name="scode" value="${maxCode}"/>
                    </c:when>
                    <c:otherwise>
                        <input placeholder="请输入编码" maxlength="10" type="text" class="form-control" id="scode" name="scode" value="${dgServing.scode}"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>名称</span>
            <div class="form-group bigest-wid">
                <input type="text" maxlength="20" class="form-control" id="sname" name="sname"  placeholder="请输入名称" value="${dgServing.sname}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>助记符</span>
            <div class="form-group bigest-wid">
                <input type="text" maxlength="20" class="form-control" id="mnemonic" name="mnemonic"  placeholder="请输入助记符" value="${dgServing.mnemonic}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>所属组织结构</span>
            <div class="form-group bigest-wid">
                <input type="text" id="sorgVal" value="${dgServing.sorg}" style="display: none;"/>
            	<select class="form-control edit_sorg" id="sorg" name="sorg" editype="val" dete="val">
					 <c:forEach items="${listOrg}" var="o" varStatus="index">
					 	<option  value="${o.id}">${o.orgName}</option>
					 </c:forEach>
				</select>
            </div>
        </li>
    </ul>
</form>