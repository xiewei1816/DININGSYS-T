<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="editPanel" style="display: none;" title="机构信息">
	    <form id="editForm" class="editForm">
	    	<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#basic" data-toggle="tab">基本信息</a></li>
				<li><a href="#extend" data-toggle="tab">扩展信息</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="basic">
	    		<input type="hidden" name="id" value="" editype="val" class="edit_id">
	    		<ul class="edit-contents animated fadeInUp">
	    			<li>
	    				<span class="title"><span class="required">*</span>机构代码</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_orgCode" name="orgCode" editype="val" dete="val"  placeholder="请输入机构代码"/>
						</div>
	    			</li>
	    			<li>
						<span class="title"><span class="required">*</span>机构名称</span>
						<div class="form-group biger-wid">
							<input placeholder="请输入机构姓名" maxlength="24" type="text" class="form-control edit_orgName" name="orgName" editype="val" dete="val" />
						</div>
					</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>速记码</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="18" class="form-control edit_orgSjm" editype="val" dete="val" name="orgSjm" placeholder="请输入速记码"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>开店时间</span>
						<div class="form-group bigest-wid">
							<input type="text" id="orgKdsj" name="orgKdsj" class="form-control edit_orgKdsj" editype="val" dete="val" placeholder="选择开店日期" readonly
	    						onclick="$.jeDate('#orgKdsj',{insTrigger:false,isTime:true,festival:true,format:'YYYY-MM-DD'})" dete="em-datetime">
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>加密狗号</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_orgJmgh" name="orgJmgh" editype="val" dete="val" placeholder="请输入加密狗号"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>电话</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="13" class="form-control edit_orgPhone" name="orgPhone" editype="val" dete="val" placeholder="请输入电话" dete="em-phoneormobile"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>管理模式</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_orgGlms" id="orgGlms" name="orgGlms" editype="val" dete="val">
								<c:forEach items="${list}" var="map" varStatus="status"> 
									<c:forEach items="${map.GLMS}" var="o" varStatus="index">
									 	<option  value="${o.id}" >${o.cName}</option>
									 </c:forEach>
								</c:forEach>
							</select>
						</div>
	    			</li>
	    			
	    			<li>
	    				<span class="title"><span class="required">*</span>区域</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_orgArea" id="orgArea" name="orgArea" editype="val" dete="val">
								<c:forEach items="${list}" var="map" varStatus="status"> 
									<c:forEach items="${map.DLQY}" var="o" varStatus="index">
									 	<option  value="${o.id}" >${o.cName}</option>
									 </c:forEach>
								</c:forEach>
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>品牌</span>
						<div class="form-group bigest-wid">
							<select class="form-control edit_orgBrand" id="orgBrand" name="orgBrand" editype="val" dete="val">
								<c:forEach items="${list}" var="map" varStatus="status"> 
									<c:forEach items="${map.PP}" var="o" varStatus="index">
									 	<option  value="${o.id}" >${o.cName}</option>
									 </c:forEach>
								</c:forEach>
							</select>
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>加盟商</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="24" class="form-control edit_franchisees" name="franchisees" editype="val" dete="val" placeholder="请输入加盟商"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title"><span class="required">*</span>最大容纳客数</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="11" class="form-control edit_maxCustomer" name="maxCustomer" editype="val" dete="val" placeholder="请输入最大容纳客数" dete="em-positive"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">地址</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="32" class="form-control edit_address" name="address" editype="val" placeholder="请输入地址"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">备注</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="128" class="form-control edit_remark" name="remark" editype="val" placeholder="请输入备注"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">本店标志</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isOwnFlag"
							 name="isOwnFlag" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isOwnFlag"
							 name="isOwnFlag" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">启用标志</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isStartFlag"
							 name="isStartFlag" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isStartFlag"
							 name="isStartFlag" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">新店标志</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isNewstoreFlag"
							 name="isNewstoreFlag" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isNewstoreFlag"
							 name="isNewstoreFlag" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">排队标志</span>
						<div class="form-group bigest-wid">
							<label class="radiobtn"><input type="radio" value="0" class="edit_isLineFlag"
							 name="isLineFlag" editype="radio" /><i></i><span></span>否</label>
							<label class="radiobtn"><input type="radio" value="1" class="edit_isLineFlag"
							 name="isLineFlag" editype="radio" /><i></i><span></span>是</label>
						</div>
	    			</li>
	    		</ul>
	    	</div>
			<!-- tab2 扩展信息 -->
	    	<div class="tab-pane fade" id="extend">
				<ul class="edit-contents animated fadeInUp">
					<li>
	    				<span class="title">所属RDC：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="2" class="form-control edit_rdc" name="rdc" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">省：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_province" name="province" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">市：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_city" name="city" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">区：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_region" name="region" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">路：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_road" name="road" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">面积：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_area" name="area" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">距RDC距离：</span>
						<div class="form-group bigest-wid">
							<input type="text" maxlength="12" class="form-control edit_rdcDistance" name="rdcDistance" editype="val">
						</div>
	    			</li>
	    			<li>
	    				<span class="title">自定义字段1：</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_diyFields1" name="diyFields1" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">自定义字段2：</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_diyFields2" name="diyFields2" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">自定义字段3：</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_diyFields3" name="diyFields3" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">自定义字段4：</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_diyFields4" name="diyFields4" editype="val"/>
						</div>
	    			</li>
	    			<li>
	    				<span class="title">自定义字段5：</span>
						<div class="form-group bigest-wid">
							<input type="text" class="form-control edit_diyFields5" name="diyFields5" editype="val"/>
						</div>
	    			</li>
				</ul>
			</div>
			<!-- tab2 扩展信息 end -->
   		</div>	
   	</form>
</div>
