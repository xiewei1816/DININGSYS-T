<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="editForm" class="editForm">
    <ul class="edit-contents animated fadeInUp">
    	<input type="hidden" name="id" value="${allowNumber.id}"/>
        <li>
            <span class="title"><span class="required">*</span>分类名称</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入名称" maxlength="11"  type="text" class="form-control" name="name" dete="val" value="${allowNumber.name}" >
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>最小容纳人数</span>
            <div class="form-group biger-wid">
                <input onblur="checkAllowNumber()" min="1" placeholder="请输入最小容纳人数" type="number" class="form-control" id="minAllowNumber" name="minAllowNumber" value="${allowNumber.minAllowNumber}" dete="val"/>
            </div>
        </li>
		<li>
            <span class="title"><span class="required">*</span>最大容纳人数</span>
            <div class="form-group biger-wid">
                <input onblur="checkAllowNumber()" min="1" placeholder="请输入最大容纳人数" type="number" class="form-control" id="maxAllowNumber" name="maxAllowNumber" value="${allowNumber.maxAllowNumber}" dete="val"/>
            </div>
        </li>
    </ul>
</form>