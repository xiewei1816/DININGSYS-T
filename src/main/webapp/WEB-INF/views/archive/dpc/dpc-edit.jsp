<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="myform" class="editForm" style="text-align: center;">
    <input type="hidden" id="hideId" name="id" value="${dpc.id}">
    <ul class="edit-contents animated fadeInUp">
        <li>
            <span class="title"><span class="required">*</span>代码：</span>
            <div class="form-group biger-wid">
                <input readonly="readonly" maxlength="12" type="text" class="form-control" name="cCode" value="${dpc.cCode}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>名称：</span>
            <div class="form-group bigest-wid">
                <input type="text" maxlength="24" class="form-control" id="cName" name="cName" placeholder="请输入名称" value="${dpc.cName}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>速记码：</span>
            <div class="form-group bigest-wid">
                <input type="text" maxlength="12" class="form-control" id="cKeyWD" name="cKeyWD" placeholder="请输入速记码" value="${dpc.cKeyWD}"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>上级代码类型：</span>
            <div class="form-group bigest-wid">
                <input type="text" class="form-control" name="cParent" value="${dpc.cParent}" style="display: none;"/>
            	<input type="text" class="form-control" id="cParentName" value="${cParentName}" readonly="readonly"/>
            </div>
        </li>
        <li>
            <span class="title"><span class="required">*</span>排序值：</span>
            <div class="form-group bigest-wid">
                <input type="number" maxlength="11"  class="form-control" id="cOrder" name="cOrder" placeholder="请输入排序值" value="${dpc.cOrder}" />
            </div>
        </li>
        <li style="display: none;">
            <span class="title"><span class="required">*</span>扩展属性：</span>
            <div class="form-group bigest-wid">
                <select class="form-control edit_c" id="c" name="c" ></select>
            </div>
        </li>
    </ul>
</form>