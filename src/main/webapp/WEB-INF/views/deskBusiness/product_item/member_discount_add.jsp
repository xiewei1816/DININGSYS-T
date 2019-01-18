<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
$(function () {
	member_discount_add.pagerInit();
});

</script>
<style>
.input-button{
	height: 30px;
	font-size:12px; 
	line-height:30px; 
	text-align:center; 
	border:1px; 
	border-color: #37b5f9;
	padding-left:15px;
	padding-right:15px;
	border-radius:3px; 
	
}
.input-button:HOVER{
	border:1px; 
	border-color: #57b5f9;
	background: #c0c0c0;
}
.input-button:ACTIVE{
	border:1px; 
	border-color: #77b5f9;
	background: #a0a0a0;
}
</style>
<ul class="nav nav-tabs" role="tablist" >
  <li role="presentation" class="active" ><a style="cursor:pointer;text-decoration: none;height: 30px;padding-top: 3px;" id="basis-a">基础信息</a></li>
  <li role="presentation" ><a style="cursor:pointer;text-decoration: none;height: 30px;padding-top: 3px;" id="detail-a">详细信息</a></li>
</ul>

<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="basis">
                <div class="form-body">
                	<input type="hidden" value="${member.id}" name="id" id="Pid"/>

                	<div class="form-group">
                        
                         <div class="checkbox">
                                <label  class="col-md-3 control-label name_title">方案名称<span class="required">*</span></label>
		                        <div class="col-md-5">
		                            <input type="text" class="form-control"   name="name" placeholder="请输入名称" value="${member.name }" editype="val" dete="val">
		                        </div>
							    <label style="margin-top: 5px;">
							        <input type="checkbox"  name="enable" id="enable"  <c:if test="${'1' == member.enable}">checked</c:if>> 启用方案
							    </label>
						  </div>
                    </div>
                    
                    <div class="form-group">
 							 <label  class="col-md-3 control-label levelId_title">会员等级:<span class="required">*</span></label>
 							 <div class="col-md-3">
							      <select name="levelId"  class="form-control" id="level" style="width: 130px;display: inline-block;" editype="val" dete="clstype">
	                                <c:forEach items="${level}" var="item">
										  <option value="${item.gradeId}" <c:if test='${member.levelId == item.gradeId}'>selected</c:if> >${item.gradeName}</option>
									</c:forEach>
	                              </select>
 							 </div>
                    </div>
                    
                    <div class="form-group">
                        <label  class="col-md-3 control-label"></label>
                        <label  class="col-md-5 control-label" style="text-align: left;color:red;">注:该方案价格即结算时的CRM一卡通结算价格</label>
                    </div>
                    
                    <div class="form-group">
                        <label  class="col-md-3 control-label">说明:</label>
                        <div class="col-md-7">
                            <textarea class="form-control" class="form-control" rows="2" name="explains" >${member.explains}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-md-3 control-label startDate_title">开始日期<span class="required">*</span></label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="startDate" class="form-control" id="start-date" placeholder="开始日期" value="${member.startDate }" dete="em-date">
                        </div>
                        
                        <label  class="col-md-2 control-label endDate_title">结束日期<span class="required">*</span></label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="endDate" class="form-control" id="end-date" placeholder="结束日期" value="${member.endDate }" dete="em-date">
                        </div>
                    </div>
                    
                   <div class="form-group">
                        <label  class="col-md-3 control-label dayStartTime_title">每日开始时间<span class="required">*</span></label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="dayStartTime" class="form-control" id="start-time" placeholder="每日开始时间" value="${member.dayStartTime }" dete="val">
                        </div>
                        
                        <label  class="col-md-2 control-label dayEndTime_title">每日结束时间<span class="required">*</span></label>
                        <div class="col-md-3" style="display: inline-block;">
                            <input type="text" name="dayEndTime" class="form-control" id="end-time" placeholder="每日结束时间" value="${member.dayEndTime }" dete="val">
                        </div>
                    </div>
                     <div class="form-group">
                     			<input type="hidden" name="week" id="week" value="${member.week }"/>
				       			<legend class="col-md-3 control-label">周设置</legend>
				       			<div class="col-md-8">
					                <div class="checkbox" style="display: inline-block;">
									    <label>
									      <input type="checkbox" id="week1"> 周一
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week2"> 周二
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week3"> 周三
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week4"> 周四
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week5"> 周五
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week6"> 周六
									    </label>									    
							        </div>
							        <div class="checkbox" style="display: inline-block;margin-left: 15px;">
									    <label>
									      <input type="checkbox" id="week7"> 周日
									    </label>									    
							        </div>
						        </div>
                    </div>
                    <%-- <div class="form-group">
                  		 <div class="col-md-6" style="text-align: right;">
                      		<label class="radio-inline">
							  <input type="radio" name="useAllShop" id="inlineRadio1" value="1"  <c:if test="${'1' == member.useAllShop}">checked</c:if>> 应用在所有店
							</label>
							<label class="radio-inline">
								  <input type="radio" name="useAllShop" id="inlineRadio2" value="0"  <c:if test="${'0' == member.useAllShop}">checked</c:if>> 应用在指定店
							</label>
						 </div>
						 <label  class="col-md-4 control-label" ><a style="cursor:pointer;text-decoration: none;" id="sele-all">选择全部</a></label>
			        </div>
			        <div class="jqGrid_wrapper" style="margin-top: 5px;">
			        		<input type="hidden" name="affiliation" id="affiliation"/>
			                <table id="grid-table-1"></table>
			                <div id="grid-pager-1"></div>
			        </div> --%>
                </div>
            </form>
            <form class="form-horizontal" role="form" id="detail">
                     <div class="form-group">
				       			<div class="col-md-8">
							         	<div class="radio-inline" >
							         		<span style="display:inline-block; margin-left: 30px;margin-right: 30px;">总部价格设置</span>
										    <label>
										        <input type="radio" name="price-type" value="1"   checked ><span class="discount_title">按比例:</span>
										    </label>
										    <input type="number" name="discount" class="form-control discount"   value="100" min="1" max="100" style="width: 70px; display: inline-block;" dete="discount">
				                            <span style="text-align: left;">%</span>									    
								        </div>
									    <div class="radio-inline" >
										    <label>
										        <input type="radio" name="price-type" value="2" > <span class="moeny_title">按价格:</span>
										    </label>
										    <input type="number" name="moeny" class="form-control moeny"   style="width: 70px; display: inline-block;" dete="em-money">
				                        <span style="text-align: left;">元</span>									    
							            </div>
						        </div>
                    </div>
                    
                     <div class="form-group">
                     	  <div class="col-md-10">
                     	  <div class="checkbox-inline" >
							        <input  type="button" name="set-s"  id="set-s" value="统一设置" class="input-button" style="display:inline-block; margin-left: 30px;margin-right: 30px;"/>
								    <label>
									      <input type="checkbox" checked> 中部价格必须大于0
									</label>	
				                    <input  type="button" name="add-s" id="add-s" value="增加" class="input-button" style="margin-left: 200px;"/>
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