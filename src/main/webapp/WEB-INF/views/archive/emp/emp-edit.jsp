<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="app/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script src="app/js/DININGSYS/archive/emp/emp-edit.js"></script>
<div id="editPanel" style="display:none;" title="员工信息">
	<div class="menuTree" style="width:300px;height:300px;border:1px solid #C0C0C0;background-color:#fff;margin-top:160px;margin-left:590px;
	 	position:absolute; z-index:10522; overflow:auto; display:none;">
		<ul id="myTree" class="ztree"></ul>
	</div>
   	<form id="editForm" class="editForm">
   		<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
			<li><a href="#extend" data-toggle="tab" onclick="hideDep()">扩展信息</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="basic">
   		<input type="hidden" name="id" value="" editype="val" class="edit_id">
   		<ul class="edit-contents animated fadeInUp">
   			<li>
   				<span class="title"><span class="required">*</span>员工编号：</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="18" class="form-control edit_empCode" name="empCode" editype="val" dete="val" placeholder="请输入员工编号"/>
				</div>
   			</li>
   			<li>
				<span class="title"><span class="required">*</span>员工姓名：</span>	
				<div class="form-group biger-wid">
					<input type="text" maxlength="8" class="form-control edit_empName" name="empName" editype="val" dete="val" placeholder="请输入员工姓名" />
				</div>
			</li>
   			<li>
   				<span class="title"><span class="required">*</span>身份证号：</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="18" class="form-control edit_empCardno" name="empCardno" editype="val" placeholder="请输入身份证号"/>
				</div>
   			</li>
   			<li>
   				<span class="title"><span class="required">*</span>所属机构：</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_empOrganization" id="empOrganization" name="empOrganization" editype="val" dete="val">
						 <c:forEach items="${listOrg}" var="o" varStatus="index">
						 	<option  value="${o.id}">${o.orgName}</option>
						 </c:forEach>
					</select>
				</div>
   			</li>
   			<li>
   				<span class="title"><span class="required">*</span>所属部门：</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_empDepartment" id="empDepartment" name="empDepartment" onclick="showOrHideDep()" editype="val" dete="val">
						 	<option value="${d.id}" >${d.depName}</option>
					</select>
				</div>
   			</li>
   			<li>
   				<span class="title"><span class="required">*</span>职位：</span>
				<div class="form-group bigest-wid">
					<select class="form-control edit_empDuties" id="empDuties" name="empDuties" editype="val" dete="val">
						<c:forEach items="${postData}" var="post" varStatus="status">
							<option  value="${post.cCode}" >${post.cName}</option>
						</c:forEach>
					</select>
				</div>
   			</li>
   			<li>
   				<span class="title">出生日期：</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_empDob" id="empDob" name="empDob" editype="val" placeholder="选择出生日期" readonly
    					onclick="$.jeDate('#empDob',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})" dete="em-datetime">
				</div>
   			</li> 
   			<li>
   				<span class="title">员工卡ID：</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="18" class="form-control edit_empCardid" name="empCardid" editype="val" placeholder="请输入员工卡ID"/>
				</div>
   			</li>
   			<li>
   				<span class="title">授权号1：</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="50" class="form-control edit_sqhNo1" name="sqhNo1" editype="val" placeholder="请输入授权号1"/>
				</div>
   			</li>
   			<li>
   				<span class="title">授权号2：</span>
				<div class="form-group bigest-wid">
					<input type="text" maxlength="50" class="form-control edit_sqhNo2" name="sqhNo2" editype="val" placeholder="请输入授权号2"/>
				</div>
   			</li>
			<li>
   				<span class="title">集团用户标志：</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn"><input type="radio" value="0" class="edit_isOrguserFlag"
					 name="isOrguserFlag" editype="radio" /><i></i><span></span>否</label>
					<label class="radiobtn"><input type="radio" value="1" class="edit_isOrguserFlag"
					 name="isOrguserFlag" editype="radio" /><i></i><span></span>是</label>
				</div>
   			</li>
   			<li>
   				<span class="title">管理员标志：</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn"><input type="radio" value="0" class="edit_isManagerFlag"
					 name="isManagerFlag" editype="radio" /><i></i><span></span>否</label>
					<label class="radiobtn"><input type="radio" value="1" class="edit_isManagerFlag"
					 name="isManagerFlag" editype="radio" /><i></i><span></span>是</label>
				</div>
   			</li>
   			<li>
   				<span class="title">启用标志：</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn" ><input type="radio" value="0" class="edit_isStartFlag"
					 name="isStartFlag" editype="radio" /><i></i><span></span>否</label>
					<label class="radiobtn"><input type="radio" value="1" class="edit_isStartFlag"
					 name="isStartFlag" editype="radio" /><i></i><span></span>是</label>
				</div>
   			</li>
   			<li>
   				<span class="title"><span class="required">*</span>性别：</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn"><input type="radio" value="男" class="edit_empSex"
					 name="empSex" editype="radio" /><i></i><span></span>男</label>
					<label class="radiobtn"><input type="radio" value="女" class="edit_empSex"
					 name="empSex" editype="radio" /><i></i><span></span>女</label>
				</div>
   			</li>
   			<li>
   				<span class="title">婚姻状况：</span>
				<div class="form-group bigest-wid">
					<label class="radiobtn"><input type="radio" value="已婚" class="edit_isMarry"
					 name="isMarry" editype="radio" /><i></i><span></span>已婚</label>
					<label class="radiobtn"><input type="radio" value="未婚" class="edit_isMarry"
					 name="isMarry" editype="radio" /><i></i><span></span>未婚</label>
				</div>
   			</li>
   		</ul>	
   		</div>
   		<div class="tab-pane fade" id="extend">
			<!-- tab2 扩展信息 -->
				<ul class="edit-contents animated fadeInUp">
					<li>
	    				<span class="title">籍贯：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="8" class="form-control edit_natives" name="natives" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">户籍地：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="200" class="form-control edit_place" name="place" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">固定电话：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="16" class="form-control edit_phone" name="phone" editype="val" dete="em-phoneormobile"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">邮箱地址：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="18" class="form-control edit_email" name="email" editype="val" dete="em-email"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">身高：</span>
						<div class="form-group input-groups">
							<input type="text" maxlength="5" style="width: 260px;" class="form-control edit_height small-wid isnumber" name="height" editype="val" dete="em-positive"/>
							<span class="symbol">cm</span>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">民族：</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_nation" id="nation" name="nation">
								<option selected="selected"></option>
								<c:forEach items="${list}" var="map" varStatus="status"> 
									<c:forEach items="${map.MZ}" var="o" varStatus="index">
									 	<option  value="${o.id}" >${o.cName}</option>
									 </c:forEach>
								</c:forEach>
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">移动电话：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="14" class="form-control edit_mobile" name="mobile" editype="val" dete="em-mobile">
						</div>
	    			</li>
	    			<li>
	    				<span class="title">家庭住址：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="200" class="form-control edit_address" name="address" editype="val"/>
						</div>
	    			</li>
				</ul>
			</div>
			<!-- tab2 扩展信息 end -->
		</div>
   	</form>
   </div>