<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="panel panel-default" style="display: none;" id="cgrk-pan">
  <div class="panel-body">
	    	<form class="editForm" id="editForm">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<input type="hidden" name="itemName" id="itemName"/>
	    		<input type="hidden" name="jsonArr" id="jsonArr">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
						<span class="title"><span class="required">*</span><span id="deptMateTitleName">盘点仓库</span></span>
						<div class="form-group biger-wid">
							<select name="wareID" id="j-dicWareId" class="form-control edit_wareID chosens"
							          editype="val" style="overflow:visible;">
							    <c:forEach items="${listWare}" var="lw" varStatus="index">
								<option value="${lw.id}">${lw.wareName}</option>
							    </c:forEach>
							</select>
						</div>
					</li>
					 <li>
						<span class="title"><span class="required">*</span>盘点日期</span>
						<div class="form-group biger-wid">
							<input type="text" class="form-control edit_dateTime" id="dateTime" name="dateTime" editype="val" placeholder="请选择日期" readonly
		  						onclick="$.jeDate('#dateTime',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})">
						</div>
					</li> 
					<div class="col-md-10 col-md-push-2 panel panel-default">
						<div class="panel-body">
                			<table id="j-itemTable"></table>
						</div>
  					</div>
  					<div class="col-md-2 col-md-pull-10 panel panel-default">
  						<div class="panel-body">
						    <li>
							<select name="itemTypeId" class="form-control edit_itemTypeId chosens"
							          editype="val" id="itemTypeId">
							    <c:forEach items="${listItemType}" var="it" varStatus="index">
								<option value="${it.id}">${it.itemTypeName}</option>
							    </c:forEach>
							</select>
						</li>
						<div class="leftmenus"></div>
						</div>
  					</div>
	    		</ul>
	    	</form>
  </div>
</div>
