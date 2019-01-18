<%--
  Created by zhshuo.
  Date: 16-11-18
  Time: 下午3:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form id="clearingNotesForm">
    <input name="cwId" value="${cwId}" type="hidden">
    <div class="form-group">
        <label for="clearingNotes">请输入结算备注：（最多50个汉字）</label>
        <textarea id="clearingNotes" rows="5" class="form-control" name="clearingNotes" maxlength="50">${notes}</textarea>
    </div>
    <div class="form-group">
        <label for="statementLabel">结账单标注:</label>
        <select class="form-control" id="statementLabel" name="statementLabel">
            <option value=""></option>
            <option value="">无</option>
        </select>
    </div>
</form>