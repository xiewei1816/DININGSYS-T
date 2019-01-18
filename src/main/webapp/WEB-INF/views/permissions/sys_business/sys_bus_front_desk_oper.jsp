<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(document).ready(function () {
        busi_per.frontInit();
    })
</script>
<form class="form-horizontal"  id="frontForm" style="width: 95%">
    <h5 style="padding-top: 20px;padding-left: 20px;">你可以针对每一种权限进行设置,控制其是否在前台营业中进行权限校验(选中表示进行控制)</h5>
    <div class="col-md-6" style="text-align: left;margin-top: 15px;">
            <button type="button" class="btn btn-primary checkBoxAll" id="frontAll">全部选择</button>
            <button type="button" class="btn btn-warning checkBoxCancel" id="frontCancel">全部取消</button>
    </div>
    <div style="margin: 30px;clear:both;">
	    <div class="row" >
	    	<c:forEach items="${qxs}" var="item" varStatus="status">
	        <div class="col-md-3">
	            <div class="checkbox">
	                <input name="deskQx[${status.index}].code" id="code" type="hidden" value="${item.code}" > 
	                <label>
	                    <input name="deskQx[${status.index}].val" id="val" type="checkbox" value="${item.val}" <c:if test="${item.val == 1}">checked</c:if> > 
	                    ${item.name}
	                </label>
	            </div>
	        </div>
	        </c:forEach>
		</div>
	</div>
</form>