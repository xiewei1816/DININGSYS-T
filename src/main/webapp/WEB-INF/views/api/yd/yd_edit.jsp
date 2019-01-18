<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>
$(function () {
	ydEdit.pagerInit();
    // $(".edit-contents input[dete='val']").bind("focus", function () {
    //     document.body.onselectstart = function () { return true; };
    //     document.body.ondragstart = function () { return true; };
    //     x = $(this).offset();
    //     var topstr = x.top + 30;
    //     var leftstr = x.left -20;
    //     //可以自己设置位置
    //     $("#txtinput").css("position", "absolute").css("z-index", "19901211");
    //     $("#txtinput").offset({ top: topstr, left: leftstr });
    //
    //     var id = $(this).attr("id");
    //     VirtualKeyboard.toggle('' + id + '', 'txtinput');
    //
    // }).bind('blur', function () {
    //     document.body.onselectstart = function () { return false; };
    //     document.body.ondragstart = function () { return false; };
    //     var id = $(this).attr("id");
    //     VirtualKeyboard.toggle("'" + id + "'", 'txtinput');
    //     $("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
    //
    // });
    // onfocus="VirtualKeyboard.attachInput(this)"
});
</script>
<style>
.radiobtnM{
	position:relative;
	padding-left: 26px;
	margin: 0 10px;
	/* line-height: 24px; */
	cursor: pointer;
}
.radiobtnM span{
	display: block;
	position: absolute;
	left: 0px;
	top: 5px;
	width: 18px;
	height: 18px;
	border: 1px solid #ababab;
	border-radius: 9px;
	background: #eeeeee;
	z-index: 1;
}
.radiobtnM i{
	display: block;
	position: absolute;
	left: 2px;
	top: 7px;
	width: 14px;
	height: 14px;
	border: 1px solid #fff;
	border-radius: 7px;
	background: #eeeeee;
	z-index: 2;
	transition: all 0.5s;
}
.radiobtnM input[type='radio']:checked + i{
	background: #36bca1;
	width: 12px;
	height: 12px;
	top: 8px;
	left: 3px;
}
.radiobtnM input[type='radio']{
	display: none;
}
#VirtualKeyboardIME{
	z-index: 19901212 !important;
}
</style>
<form id="editForm">
	<div class="editForm" onselectstart ='return false' ondragstart="return false;">
		<ul class="edit-contents">
	    	<input type="hidden" name="id" value="${item.id}" id="pId"/>
	    	<input type="hidden" name="state" value="${item.state}" id="state"/>
	    	<input type="hidden" name="ydNum" value="${item.ydNum}" id="ydNum"/>
	    	<input type="hidden" name="ydResoure" value="${item.ydResoure}" id="ydResoure"/>
	        <li>
	            <span class="title"><span class="required">*</span>姓名</span>
	            <div class="form-group biger-wid">
	                <input placeholder="姓名" type="text" class="form-control" name="name" dete="val" value="${item.name}" />
	            </div>
	        </li>
	        <li>
	            <span class="title"><span class="required">*</span>人数</span>
	            <div class="form-group biger-wid">
	                <input placeholder="人数" type="text" class="form-control" name="number" dete="val" value="${item.number}" />
	            </div>
	        </li>
	        <li>
	            <span class="title"><span class="required">*</span>电话</span>
	            <div class="form-group biger-wid">
	                <input placeholder="电话" type="text" class="form-control" name="phone" dete="val" value="${item.phone}" />
	            </div>
	        </li>
	        <li>
	            <span class="title"><span class="required">*</span>午餐或晚餐</span>
	            <div class="form-group biger-wid">
		            <select class="form-control" name="wOw" id="wOw">
						<option  value="1" <c:if test="${item.wOw == 1}">selected</c:if>>午餐</option>
						<option  value="2" <c:if test="${item.wOw == 2}">selected</c:if>>晚餐</option>
				    </select>
			    </div>
	        </li>
	        <li>
	            <span class="title"><span class="required">*</span>就餐时间</span>
	            <div class="form-group biger-wid">
	                <input id="ydTime" placeholder="请选择时间" type="text" class="form-control" name="ydTime" dete="valll" value="${item.ydTime}" readonly="readonly"/>
	            </div>
	        </li>
	        <li>
	            <span class="title"><span class="required">*</span>性别</span>
	            <div class="form-group biger-wid">
		            <select class="form-control" name="sex" >
						<option  value="1" <c:if test="${item.sex == 1}">selected</c:if>>男</option>
						<option  value="2" <c:if test="${item.sex == 2}">selected</c:if>>女</option>
				    </select>
			    </div>
	        </li>
	        <li>
	            <span class="title"><span class="required">*</span>客位类型</span>
	            <div class="form-group biger-wid">
		            <select class="form-control" name="crId" id="crId">
						<option  value="-1" <c:if test="${item.crId == -1}">selected</c:if>>不限</option>
						<option  value="0" <c:if test="${item.crId == 0}">selected</c:if>>大厅</option>
						<option  value="1" <c:if test="${item.crId == 1}">selected</c:if>>包间</option>
				    </select>
			    </div>
			</li>
			<li>
				<span class="title"><span class="required">*</span>没包间选择大厅</span>
                <label class="radiobtnM">
					  <input type="radio" name="bsd"  value="0" <c:if test="${item.bsd == 0}">checked="checked"</c:if> ><i></i><span></span>否
				</label>
				<label class="radiobtnM">
					  <input type="radio" name="bsd"  value="1" <c:if test="${item == null or item.bsd == 1}">checked="checked"</c:if> ><i></i><span></span>是
				</label>
			</li>
	    </ul>
	</div>
    <div style="clear:both;margin-left: 93px;">
        <span class="title" style="display:inline-block;float:left;font-size: 14px;font-weight: 400;padding:7px 10px 0px 0px;">选择客位:</span>
        <ul class="seats">
       		 <c:forEach items="${seats}" var="o" varStatus="stat">
					 <li> ${o.seatName}
					 	<input type='hidden' name='seatIds[${stat.index}].seatId' value='${o.seatId}'/>
					 </li>
			  </c:forEach>
        </ul>
        <input class="btn btn-primary seatAdd" type="button" value="增加" id="seatAdd">
     </div>
	<div id="txtinput"></div>
</form>