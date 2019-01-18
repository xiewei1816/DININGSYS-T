<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form id="editForm" class="editForm">
    <ul class="edit-contents animated fadeInUp">
   		<input  type="hidden"  name="id" value="${item.id}"/>
        <li>
            <span class="title"><span class="required">*</span>请输入url编码</span>
            <div class="form-group biger-wid">
                <input placeholder="请输入url编码" type="text" class="form-control" name="code" dete="val" value="${item.code}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>请输入名称</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="name"  placeholder="请输入名称" dete="val"  value="${item.name}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>请输入url地址</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="value"  placeholder="请输入url地址" dete="val"  value="${item.value}"/>
            </div>
        </li>
    </ul>
</form>