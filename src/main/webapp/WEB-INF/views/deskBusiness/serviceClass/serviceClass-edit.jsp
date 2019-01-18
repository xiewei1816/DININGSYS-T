<%--
  Created by zhshuo.
  Date: 2017-02-17
  Time: 16:04
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    jQuery(document).ready(function () {
        editPageInit();
    })
</script>
<form class="form-horizontal" id="serviceClassForm" style="width: 97%">
    <input type="hidden" name="id" value="" editype="val" class="edit_id">
    <div class="row">
        <div class="col-md-6">
            <span class="control-label col-md-2">市别：</span>
            <div class="col-md-8">
                <select class="form-control edit_eatTimeId" id="bis" name="eatTimeId" editype="val">
                    <c:forEach items="${bises }" var="bis">
                        <option value="${bis.id }">${bis.bisName }</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="col-md-6">
            <span class="control-label col-md-4">统一设置服务员：</span>
            <div class="col-md-8">
                <select class="form-control" id="comEmp">
                    <option value="">服务员选择</option>
                    <c:forEach items="${emps }" var="emp">
                        <option value="${emp.id }" class="waiterChoose">${emp.empName }</option>
                    </c:forEach>
                </select>
                <input type="hidden" id="updSeats" name="seatIds">
                <input type="hidden" id="updWaiters" name="waiterIds">
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-1">

        </div>
        <div class="col-md-11">
            <table id="grid-table1"></table>
        </div>
    </div>
</form>