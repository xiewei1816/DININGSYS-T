<%--
  Created by zhshuo.
  Date: 2016-10-11
  Time: 13:02
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="app/js/DININGSYS/archive/proMethods/pro_methods_edit.js"></script>
<script type="text/javascript" src="app/js/DININGSYS/archive/proMethods/pro_methods_validation.js"></script>
<script>
$(function(){
  	
    proMethodsValidation.editFormInit();

	//树形结构加载
	$.fn.zTree.init($("#myTree"), setting);
	
	$("#collectProMoney").change(function() {
        if(this.checked) {
            $("#proMoney").removeAttr('disabled');
            $("#proMoney").removeAttr('value');
            $("#proMoney").val('');
            $("#proMoney").attr('placeholder','请输入制作费用');
            $("#collectProMoneybynum").removeAttr('disabled');
            $("#canUpdate").removeAttr('disabled');
        }else{
            $("#proMoney").attr('disabled','disabled');
            $("#proMoney").attr('placeholder','请先勾选制作收取制作费用');
            $("#collectProMoneybynum").attr('disabled','disabled');
            $("#canUpdate").attr('disabled','disabled');
            $("#collectProMoneybynum").prop('checked',false);
            $("#canUpdate").prop('checked',false);
            $("#proMoney").removeAttr('value');
            $("#proMoney").val('');
        }
    });
    
  	//初始化下拉列表默认选中第一条数据
	$("#organid option:first").prop("selected", 'selected');
  	//初始化客位类型
	var organidVal = $("#organidVal").val();
  	if(organidVal != ""){
		$("#organid").val(organidVal);
  	}
	
	//初始化部门
	/* var id = data.dep.id;
	var depName = data.dep.depName;
	$("#dept").find("option").val(id).text(depName).prop("selected",true); */
});
</script>
<div class="menuTree" style="width:300px;height:280px;border:1px solid #C0C0C0;background-color:#fff;margin-top: 314.5px;margin-left: 140.5px;
 	position:absolute; z-index:10522; overflow:auto; display:none;">
	<ul id="myTree" class="ztree"></ul>	
</div>
<form id="proMethTypeEditForm" class="editForm">
    <input type="hidden" id="hideId" name="id" value="${dgProMethods.id}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>编号</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入编号" type="text" class="form-control" name="pmcode" value="${dgProMethods.pmcode}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>名称</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入名称" type="text" class="form-control" id="pmname" name="pmname" value="${dgProMethods.pmname}"/>
            </div>
        </li>
        <li>
            <span class="title">顺序号</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="orderno" value="${dgProMethods.orderno}"/>
            </div>
        </li>
        <li>
            <span class="title">助记符</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="pmmnemonic" value="${dgProMethods.pmmnemonic}"/>
            </div>
        </li>
        <li>
            <span class="title">制作方法类别</span>
            <div class="form-group">
                <select class="form-control" name="pmtid">
                    <c:forEach items="${dgProMethodsTypes}" var="type">
                        <option value="${type.id}" <c:if test="${dgProMethods.pmtid == type.id}">selected</c:if> >${type.pmtname}</option>
                    </c:forEach>
                </select>
            </div>
        </li>
        <li>
            <span class="title">所属部门</span>
            <div class="form-group bigest-wid">
                <input type="text" id="deptId" name="dept" value="${dgProMethods.dept}" style="display: none;"/>
                <input type="text" readonly value="${dep.depName}" class="form-control" id="dept" onclick="showOrHideDep()">
                <%--<select class="form-control edit_dept" id="dept" name="dept" onclick="showOrHideDep()" editype="val" dete="val">
					 <option value="${dep.id}" >${dep.depName}</option>
				</select>--%>
            </div>
        </li>
        <li>
            <span class="title">收取制作费用</span>
            <div>
                <div class="col-md-2">
                    <input type="checkbox" value="1" name="collectProMoney" id="collectProMoney" <c:if test="${dgProMethods.collectProMoney == 1}">checked</c:if>/>
                </div>
                <div class="col-md-6">
                    <input type="text" id="proMoney" name="proMoney" value="${dgProMethods.proMoney}" <c:if test="${dgProMethods.collectProMoney == 0}">disabled="disabled"</c:if> class="form-control" placeholder="请先勾选制作收取制作费用"/>
                </div>
            </div>
        </li>
        <li>
            <span class="title">说明</span>
            <div class="form-group bigest-wid">
                <input type="text" name="description" class="form-control" placeholder="请输入说明" value="${dgProMethods.description}"/>
            </div>
        </li>
        <li>
            <span class="title">所属组织机构</span>
            <div class="form-group bigest-wid">
                <input type="text" id="organidVal" class="form-control" value="${dgProMethods.organid}" style="display: none;"/>
                <select class="form-control edit_organid" id="organid" name="organid" editype="val" dete="val">
					 <c:forEach items="${listOrg}" var="o" varStatus="index">
					 	<option  value="${o.id}" >${o.orgName}</option>
					 </c:forEach>
				</select>
            </div>
        </li>
        <li>
            <span class="title">其他</span>
            <div class="form-group checkbox">
                <c:choose>
                    <c:when test="${dgProMethods.collectProMoney == 1}">
                        <label>
                            <input type="checkbox" value="1" id="collectProMoneybynum" <c:if test="${dgProMethods.collectProMoneybynum == 1}">checked="checked"</c:if> name="collectProMoneybynum"/>按品项数量收取制作费用<br/>
                        </label>
                        <label>
                            <input type="checkbox" value="1" id="canUpdate" <c:if test="${dgProMethods.canUpdate == 1}">checked="checked"</c:if> name="canUpdate"/>前台销售时可以修改制作费用
                        </label>
                    </c:when>
                    <c:otherwise>
                        <label>
                            <input type="checkbox" value="1" id="collectProMoneybynum" disabled="disabled" name="collectProMoneybynum"/>按品项数量收取制作费用<br/>
                        </label>
                        <label>
                            <input type="checkbox" value="1" id="canUpdate" disabled="disabled" name="canUpdate"/>前台销售时可以修改制作费用
                        </label>
                    </c:otherwise>
                </c:choose>
            </div>
        </li>
    </ul>
</form>