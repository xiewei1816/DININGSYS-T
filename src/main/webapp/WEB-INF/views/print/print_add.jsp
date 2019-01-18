<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>      
<script>
    $(function ($) {
    	print_add.pagerInit();
    })
</script>  
<form id="addPanForm" class="addPanForm">
	<input type="hidden" value="${disP.id}" name="id" id="Pid"/>
	<div class="content">
		 <div class="form-inline">
		      <span class="text-1  code_title" >打印机名称:</span>
		       <select class="form-control" name="name"  dete="clstype" style="width: 300px" id="printName" <c:if test="${disP != null}"> disabled = "disabled"</c:if>>
		       		<c:if test="${print != null}">
			       		<c:forEach items="${print}" var="item">
				          	<option value="${item.name}" num="${item.no}" <c:if test="${item.name == disP.name}">selected</c:if>>${item.name}</option>
			          	</c:forEach>
		       		</c:if>
		       		<c:if test="${print == null}">
		       			<option value="${disP.name}" num="${disP.num}" selected>${ disP.name}</option>
		       		</c:if>
	          </select>
	          <input  type="hidden" name="num" class="form-control" editype="val" dete="val" value="${disP.num}" id="printNum"/>
		</div>
		<div class="form-inline">
		      <span class="text-1 type_title">打印类型: </span>
	          <select class="input-sele form-control" name="type" id="sele-type" onchange="seleChange(this)" dete="clstype" style="width: 170px">
	          	<option value="1" <c:if test="${'1' == disP.type}">selected</c:if>>按品项设置</option>
	          	<option value="2" <c:if test="${'2' == disP.type}">selected</c:if>>按小类设置</option>
	          </select>
		</div>
		<div class="form-inline">
			  <span class="text-1 type_title">打印份数: </span>
			  <input class="input-sele form-control" type="number"
					 oninput="if(value.length>2)value=value.slice(0,2)"
					 onKeyUp="this.value=this.value.replace(/^(0+)|[^\d]+/g,'1');this.value=this.value.replace('.','1');"
					 name="copies" value="${disP.copies==null?1:disP.copies}" />
		</div>
		<div class="form-inline">
			  <div class="checkbox m-left">
			    <label>
			      <input type="checkbox"   name="disable" <c:if test="${'1' == disP.disable}">checked</c:if> /> 停用
			    </label>
			  </div>
			   <div class="checkbox m-left">
			    <label>
			      <input type="checkbox"   name="zt" <c:if test="${'1' == disP.zt}">checked</c:if> /> 打印客位转台
			    </label>
			  </div>
			  <div class="checkbox m-left">
			    <label>
			      <input type="checkbox"   name="wm" <c:if test="${'1' == disP.wm}">checked</c:if> /> 打印外卖单
			    </label>
			  </div>
		</div>  
		<div class="form-inline">
			  <div class="checkbox m-left">
			    <label>
			      <input type="checkbox"   name="ct" <c:if test="${'1' == disP.ct}">checked</c:if> /> 打印餐台点单
			    </label>
			  </div>
			  <div class="checkbox m-left">
				<label>
					<input type="checkbox"   name="dp" <c:if test="${'1' == disP.dp}">checked</c:if> /> 打印单品转台
				</label>
			 </div>
		</div>  
		<div class="form-inline" >
			  <span class="text-1  code_title" >打印管理区域:</span>
			  <input type="hidden" name="areaIds" value="${disP.areaIds}" id="areaIds" escapeXml="true"/> 
               <c:forEach items="${areas}" var="item">
                	<div class="checkbox" style="display: inline-block;">
					    <label>
					      <input type="checkbox" id="${item.id}" class="area-checkbox">${item.name}&nbsp;&nbsp;
					    </label>									    
					</div>
			  </c:forEach>
		</div>
		<div class="form-inline">
		      <span class="text-1 name_title" >切打或整打:</span>
	          <select class="input-sele form-control" name="qOZ" id="sele-type"  dete="clstype" style="width: 170px">
	          	<option value="1" <c:if test="${'1' == disP.qOZ}">selected</c:if>>切打</option>
	          	<option value="2" <c:if test="${'2' == disP.qOZ}">selected</c:if>>整打</option>
	          </select>
	          &nbsp;&nbsp;
	          <input  type="button" name="add-s" id="add-s" value="增加" class="input-button"/>
		      &nbsp;&nbsp;
		      <input  type="button" name="sub-s"  id="sub-s" value="删除" class="input-button"/>
		</div>
	</div>
	<div class="jqGrid_wrapper" style="margin-top: 10px;">
	         <table id="grid-table-3"></table>
	         <table id="grid-table-3-gate"></table>
	</div>
</form> 