<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
$(function () {
	gift_item_plan_add.pagerInit();
});

</script>
<ul class="nav nav-tabs" role="tablist" >
  <li role="presentation" class="active" ><a style="cursor:pointer;text-decoration: none;height: 30px;padding-top: 3px;" id="basis-a">基础信息</a></li>
  <li role="presentation" ><a style="cursor:pointer;text-decoration: none;height: 30px;padding-top: 3px;" id="detail-a">详细信息</a></li>
</ul>

<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="basis">
                <div class="form-body">
                	<input type="hidden" value="${gift.id}" name="id" id="Pid"/>

                	<div class="form-group">
                        
                         <div class="checkbox">
                                <label  class="col-md-3 control-label name_title">方案名称<span class="required">*</span></label>
		                        <div class="col-md-5">
		                            <input type="text" class="form-control"   name="name" placeholder="请输入名称" value="${gift.name }" editype="val" dete="val">
		                        </div>
							    <label style="margin-top: 5px;">
							        <input type="checkbox"  name="enable" id="enable" value="${gift.enable }" <c:if test="${'1' == gift.enable}">checked</c:if>> 启用方案
							    </label>
						  </div>
                    </div>
                    
                    <div class="form-group">
                        <label  class="col-md-3 control-label itemName_title">品项:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="hidden" name="itemId" id="itemId"  value="${gift.itemId }">
                            <input type="hidden" name="itemCode" id="itemCode"  value="${gift.itemCode }">
                            <input type="button" name="itemName" class="form-control" id="itemName"  value="${gift.itemName }" editype="val" dete="val">
                        </div>
                        <div class="col-md-3" style="display: inline-block;">
  				            <input  type="button" name="add-m" id="add-m" value="增加" class="input-button" />
					     	<input  type="button" name="sub-m" id="sub-m" value="删除" class="input-button" style="margin-left: 20px;"/>	
                        </div>
                    </div>
                    
                     <div class="form-group">
                        <label  class="col-md-3 control-label giftAmount_title">赠送数量:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="giftAmount" class="form-control" id="giftAmount"  value="${gift.giftAmount }" dete="isnumber">
                        </div>
                        
                        <label  class="col-md-2 control-label giftFrequencyLimit_title">赠送次数限定:</label>
                        <div>
  				            <input type="text" name="giftFrequencyLimit" class="form-control" id="giftFrequencyLimit" value="${gift.giftFrequencyLimit }" style="width: 80px;display: inline-block;" dete="isnumber">
					     	<span>次</span>
                        </div>
                    </div>
                    
                    
                    <div class="form-group">
                        <label  class="col-md-3 control-label">说明:</label>
                        <div class="col-md-7">
                            <textarea class="form-control" class="form-control" rows="2" name="explains" >${gift.explains}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label startDate_title">开始日期:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="startDate" class="form-control" id="start-date" placeholder="开始日期" value="${gift.startDate }" dete="val">
                        </div>
                        
                        <label  class="col-md-2 control-label endDate_title">结束日期:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="endDate" class="form-control" id="end-date" placeholder="结束日期" value="${gift.endDate }" dete="val">
                        </div>
                    </div>
                    
                   <div class="form-group">
                        <label  class="col-md-3 control-label startTime_title">每日开始时间:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="startTime" class="form-control" id="start-time" placeholder="每日开始时间" value="${gift.startTime }" dete="val">
                        </div>
                        
                        <label  class="col-md-2 control-label endTime_title">每日结束时间:</label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="endTime" class="form-control" id="end-time" placeholder="每日结束时间" value="${gift.endTime }" dete="val">
                        </div>
                    </div>
                     <div class="form-group">
				       			<legend class="col-md-3 control-label">周设置</legend>
				       			<div class="col-md-8">
					                <div class="checkbox" style="display: inline-block;">
									    <label>
									      <input type="checkbox" id="week1" name="week1" value="${gift.week1 }"> 周一
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week2" name="week2" value="${gift.week2 }"> 周二
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week3" name="week3" value="${gift.week3 }"> 周三
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week4" name="week4" value="${gift.week4 }"> 周四
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week5" name="week5" value="${gift.week5 }"> 周五
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week6" name="week6" value="${gift.week6 }"> 周六
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week7" name="week7" value="${gift.week7 }"> 周日
									    </label>									    
							        </div>
						        </div>
                       </div>
                </div>
            </form>
            <form class="form-horizontal" role="form" id="detail">
                     <div class="form-group">
                     	  <div class="col-md-10">
                     	  <div class="checkbox-inline" >	
				                    <input  type="button" name="add-s" id="add-s" value="增加" class="input-button" style="margin-left: 300px;"/>
					     			<input  type="button" name="sub-s" id="sub-s" value="删除" class="input-button" style="margin-left: 20px;"/>								    
						  </div>
					      </div>
					</div>
			        <div class="jqGrid_wrapper" style="margin-top: 5px;">
			                <table id="grid-table-2"></table>
			                <div id="grid-pager-2"></div>
			        </div>
            </form>
        </div>
    </div>
</div>