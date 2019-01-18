<%--
  Created by mrren.
  Date: 2016-07-27
  Time: 15:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="app/js/DININGSYS/system/dicType/dicType_validation.js"></script>
<script>
    jQuery(document).ready(function() {
        dicTypeFormValidation.editFormInit();
    });
</script>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4 class="modal-title">字典信息分类编辑</h4>
</div>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="dicTypeEditForm">
                <div class="form-body">
                    <div class="form-group">
                        <label  class="col-md-3 control-label">分类名称<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" value="${dicType.dicTypeName}" name="dicTypeName" class="form-control"  placeholder="请输入字典信息分类名称">
                            <input type="hidden" name="id" value="${dicType.id}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">分类代码<span class="required">*</span></label>
                        <div class="col-md-9">
                            <input type="text" name="dicTypeCode" value="${dicType.dicTypeCode}" class="form-control" placeholder="请输入字典信息分类代码" >
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn default" data-dismiss="modal">取消</button>
    <button type="button" class="btn blue" onclick="dicType.editDicType('${type}')">保存</button>
</div>