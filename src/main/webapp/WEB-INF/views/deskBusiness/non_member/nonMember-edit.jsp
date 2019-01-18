<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="editPanel" style="display: none;" title="非会员">
   	<form id="editForm" class="editForm">
   		<input type="hidden" name="id" value="" editype="val" class="edit_id">
   		<ul class="edit-contents animated fadeInUp">
   			<li>
				<span class="title" title="帐户编号"><span class="required">*</span>帐户编号</span>
				<div class="form-group biger-wid">
					<input placeholder="请输入帐户编号" type="text" class="form-control edit_number" name="number" editype="val" dete="val" />
				</div>
			</li>
   			<li>
   				<span class="title" title="帐户名称"><span class="required">*</span>帐户名称</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_name" id="edit_named" name="name" editype="val" dete="val" placeholder="请输入帐户名称"/>
				</div>
   			</li>
   			<li>
   				<span class="title" title="助记符">助记符</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_mnemonic" name="mnemonic" editype="val" placeholder="请输入助记符"/>
				</div>
   			</li>
   			<li>
   				<span class="title" title="帐户类型"><span class="required">*</span>帐户类型</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_accountType" name="accountType" editype="val">
						<option value="1">个人</option>
						<option value="2">公司</option>
					</select>
				</div>
   			</li>
   			<li>
   				<span class="title" title="客户经理"><span class="required">*</span>客户经理</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_empId" name="empId" editype="val">
						<c:forEach items="${emps }" var="user">
							<option value="${user.id }">${user.empName }</option>
						</c:forEach>
					</select>
				</div>
   			</li>
   			<li>
   				<span class="title" title="帐户余额">帐户余额</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_balance isnumber" name="balance" editype="val"  placeholder="请输入帐户余额"/>
				</div>
   			</li>
   			<li>
   				<span class="title" title="还款周期">还款周期</span>
				<div class="form-group input-groups">
					<input type="text" class="form-control small-wid edit_repaymentPeriod isnumber"
					 name="repaymentPeriod" editype="val" style="width: 260px;" placeholder="请输入还款周期"/>
					 <span class="symbol">天</span>
				</div>
   			</li>
   			<li>
   				<span class="title" title="信用额度">信用额度</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_creditLimit isnumber"
					 name="creditLimit" editype="val"  placeholder="请输入信用额度"/>
				</div>
   			</li>
   			<li>
   				<span class="title" title="联系人">联系人</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_contacts"
					 name="contacts" editype="val"  placeholder="请输入联系人"/>
				</div>
   			</li>
   			<li>
   				<span class="title" title="电话">电话</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_phone"
					 name="phone" editype="val"  placeholder="请输入电话"/>
				</div>
   			</li>
   			<li>
   				<span class="title" title="地址">地址</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_address"
					 name="address" editype="val"  placeholder="请输入地址"/>
				</div>
   			</li>
   			
   			
   			<li>
   				<span class="title" title="帐户停用">帐户停用</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn"><input type="radio" value="0" class="edit_isDisable"
					 name="isDisable" editype="radio" /><i></i><span></span>否</label>
					<label class="radiobtn"><input type="radio" value="1" class="edit_isDisable"
					 name="isDisable" editype="radio" /><i></i><span></span>是</label>
				</div>
   			</li>
   			<li class="bigger">
   				<span class="title" title="说明">说明</span>
				<div class="form-group bigest-wid">
					<textarea class="form-control edit_explains" name="explains" editype="val" id="explains"></textarea>
				</div>
   			</li>
   		</ul>
   	</form>
</div>