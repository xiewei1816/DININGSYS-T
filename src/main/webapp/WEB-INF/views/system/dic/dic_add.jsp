<%--
  Created by mrren.
  Date: 2016-07-27
  Time: 15:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/system/dic/dic_validation.js"></script>
<script>
    jQuery(document).ready(function() {
        dicFormValidation.editFormInit();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">字典数据编辑</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="dicEditForm">
                <div class="form-body">
                    <div class="form-group">
                        <label  class="col-md-3 control-label">名称<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" value="${dic.dicName}" name="dicName" class="form-control"  placeholder="请输入名称">
                            <input type="hidden" name="id" value="${dic.id}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">代码<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" name="dicCode" value="${dic.dicCode}" class="form-control" placeholder="请输入代码" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">分类<span class="required">*</span></label>
                        <div class="col-md-9">
                            <select name="dicType"  class="form-control">
                                <c:forEach items="${sysDicTypeList}" var="dicType">
                                    <option <c:if test="${dic.dicType==dicType.id}">selected</c:if> value="${dicType.id}">${dicType.dicTypeName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">取消</button>
    <button type="button" class="btn blue" onclick="dic.editDic('${type}')">保存</button>
</div>