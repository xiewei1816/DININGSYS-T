<%--
  Created by mrren.
  Date: 2016-07-27
  Time: 15:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
    jQuery(document).ready(function() {
        dbs_set.itemCheckboxInit();
        dbs_set.saveInfoBtnInit();
    });
</script>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="loungeSettingForm">
                <div class="form-body">
                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>
                                <input name="isEnableLoungeInterface" type="checkbox"  <c:if test="${dbsLoungeSettingDTO.isEnableLoungeInterface == 1}">checked</c:if> > 启用雅座接口
                            </label>
                        </div>
                    </div>

                    <div class="row">
                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">操作员：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入广告语" class="form-control" name="operator" type="text" value="${dbsLoungeSettingDTO.operator}">
                            </div>
                        </div>

                        <div class="checkbox col-md-6">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">操作员密码：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入广告语" class="form-control" name="operatorPassword" type="text" value="${dbsLoungeSettingDTO.operatorPassword}">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="checkbox col-md-12">
                            <label class=" col-md-3" style="text-align: left; padding-top:5px;">雅座主管密码：</label>
                            <div class="col-md-9">
                                <input placeholder="请输入广告语" class="form-control" name="loungeDirectorPassword" type="text" value="${dbsLoungeSettingDTO.loungeDirectorPassword}">
                            </div>
                        </div>

                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<c:if test="${single == '1' }">
	<div class="modal-footer">
	    <button type="button" class="btn default" data-dismiss="modal">取消</button>
	    <button type="button" class="btn blue saveInfo" id="loungeSettingBtn">保存</button>
	</div>
</c:if>