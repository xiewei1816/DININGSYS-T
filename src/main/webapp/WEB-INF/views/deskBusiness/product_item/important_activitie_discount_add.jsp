<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
$(function () {
	important_activities_add.pagerInit();
});

</script>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal editForm" role="form" id="userImpDisForm">
                 <div class="edit-contents animated fadeInUp">
                	<input type="hidden" value="${disP.id}" name="id" id="Pid"/>
	                <div class="form-group" id="checkUserNameClassId">
	                        <span  class="col-md-3 control-label organId_title"><span class="required">*</span>组织机构</span>
	                        <div class="col-md-8">
	                            	<select class="form-control" name="organId" dete="clstype">
										<c:forEach items="${orgs}" var="item">
											<option value="${item.id}" <c:if test="${item.id == disP.organId}">selected</c:if>>${item.orgName}</option>
										</c:forEach>
									</select>
	                        </div>
	                </div>
                    <div class="form-group">
                        <span  class="col-md-3 control-label name_title">活动名称<span class="required">*</span></span>
                        <div class="col-md-8">
                            <input type="text" name="name" class="form-control"  placeholder="请输入名称" value="${disP.name}" editype="val" dete="val">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label startTime_title">开始时间<span class="required">*</span></label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="startTime" class="form-control" id="start-time" placeholder="开始时间" value="${disP.startTime }" dete="em-date">
                        </div>
                        
                        <label  class="col-md-2 control-label endTime_title">结束时间<span class="required">*</span></label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="endTime" class="form-control" id="end-time" placeholder="结束时间" value="${disP.endTime }" dete="em-date">
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">启用重要活动打折<span class="required">*</span></label>
                        <div class="col-md-8">
                            <select name="enable"  class="form-control">
                                <option value="1" <c:if test="${'1' == disP.enable}">selected</c:if>>是</option>
                                <option value="0" <c:if test="${'0' == disP.enable}">selected</c:if>>否</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label">共计:<span class="gate-counter">0个小类</span></label>
                        <label  class="col-md-3 control-label discount_title">统一设置折扣比例</label>
                        <div class="col-md-1" style="display: inline-block;">
                            <input type="number" name="discount" class="form-control discount"  value="${ disP.discount}" min="1" max="100" style="width: 70px;" dete="discount">
                        </div>
                        <span style="text-align: left;">%</span>
                        <input  type="button" name="set-s"  id="set-s" value="设置" class="input-button" style="margin-left: 15px;"/>
                        <div class="jqGrid_wrapper" style="margin-top: 5px;">
			                <table id="grid-table-1"></table>
			                <div id="grid-pager-1"></div>
			            </div>
			        </div>
                  </div>
            </form>
        </div>
    </div>
</div>