<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 退菜原因维护 -->
<div id="editPanel" style="display: none;" title="退菜原因信息">
   	<form id="editForm" class="editForm">
   		<input type="hidden" name="id" value="" editype="val" class="edit_id">
   		<ul class="edit-contents animated fadeInUp">
   			<li style="display: none;">
   				<span class="title"><span class="required">*</span>编号</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_rfcCode" name="rfcCode" editype="val" placeholder="请输入编号"/>
				</div>
   			</li>
   			<li>
				<span class="title"><span class="required">*</span>名称</span>
				<div class="form-group biger-wid">
					<input type="text" maxlength="12" class="form-control edit_rfcName" name="rfcName" editype="val" dete="val" placeholder="请输入名称" />
				</div>
			</li>
			<li>
				<span class="title"><span class="required">*</span>退菜原因类型</span>
				<div class="form-group biger-wid">
					<select class="form-control edit_rfcType" id="rfcType" name="rfcType" >
						 <c:forEach items="${listRfct}" var="r" varStatus="index">
						 	<option  value="${r.id}">${r.rfctName}</option>
						 </c:forEach>
					</select>
				</div>
			</li>
   			<li>
   				<span class="title">助记符</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="18" class="form-control edit_rfcZjf" name="rfcZjf" editype="val" placeholder="请输入助记符"/>
				</div>
   			</li>
   			<li>
   				<span class="title">说明</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="128" class="form-control edit_rfcExplain" name="rfcExplain" editype="val" />
				</div>
   			</li>
   			<li>
   				<span class="title">原料损失</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn"><input type="radio" value="0" class="edit_isMaterialLoss"
					 name="isMaterialLoss" editype="radio" /><i></i><span></span>否</label>
					<label class="radiobtn"><input type="radio" value="1" class="edit_isMaterialLoss"
					 name="isMaterialLoss" editype="radio" /><i></i><span></span>是</label>
				</div>
   			</li>
   			<li>
   				<span class="title">退单时沽清品项</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn"><input type="radio" value="0" class="edit_isTdsgqpx"
					 name="isTdsgqpx" editype="radio" /><i></i><span></span>否</label>
					<label class="radiobtn"><input type="radio" value="1" class="edit_isTdsgqpx"
					 name="isTdsgqpx" editype="radio" /><i></i><span></span>是</label>
				</div>
   			</li>
   		</ul>	
   	</form>
</div>