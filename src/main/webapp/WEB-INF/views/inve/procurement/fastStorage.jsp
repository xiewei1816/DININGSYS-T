<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Date: 2017-11-06 13:20
  @Author zhshuo.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    jQuery(document).ready(function () {
        $("#itemNo").focus();

        $("#addTime").jeDate({
            format:"YYYY-MM-DD",
            isinitVal:true,
            isTime:false
        })

        $("#itemNo").keyup(function (event) {
            if(event.keyCode == 13){
                $.ajax({
                    type:'POST',
                    url:'DININGSYS/inve/proc/selectInveItemByCode/'+$(this).val(),
                    dataType:'JSON',
                    success:function(data){
                        $("#name").val(data.itemName);
                        $("#price").val(data.price);
                        $("#unit").val(data.unit);
                        $("#nowPrice").val(data.price);
                        $("#itemId").val(data.id);
                        $("#amount").val("1");
                        $("#subtotal").val(new Decimal($("#nowPrice").val()));
                    }
                })
            }
        })

        $("#amount").on('input',function(e){
            var val = new Decimal($(this).val());
            if(val <= 0){
                $(this).val(1);
                val = 1;
            }
            $("#subtotal").val(val.mul(new Decimal($("#nowPrice").val())));
        });

    })
</script>
<div class="col-md-12" style="padding-top: 10px">
    <form class="form-horizontal" id="fastStorageForm" role="form">
        <input type="hidden" name="procType" id="procType" value="0">
        <input type="hidden" name="itemId" id="itemId">
        <div class="form-group">
            <label for="itemNo" class="col-sm-2 control-label">编码</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="itemNo">
            </div>
        </div>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">名称</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="name" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="price" class="col-sm-2 control-label">原价</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="origPrice" id="price" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="nowPrice" class="col-sm-2 control-label">现价</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="presPrice" id="nowPrice">
            </div>
        </div>
        <div class="form-group">
            <label for="amount" class="col-sm-2 control-label">数量</label>
            <div class="col-sm-10">
                <input type="number" data-min="1" class="form-control" name="number" id="amount">
            </div>
        </div>
        <div class="form-group">
            <label for="unit" class="col-sm-2 control-label">单位</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" name="unit" id="unit" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="subtotal" class="col-sm-2 control-label">金额</label>
            <div class="col-sm-10">
                <input type="number" class="form-control" name="sumAmount" id="subtotal" readonly>
            </div>
        </div>
        <div class="form-group">
            <label for="wareID" class="col-sm-2 control-label">入货仓库</label>
            <div class="col-sm-10">
                <select class="form-control" name="wareID" id="wareID">
                    <c:forEach var="warehouseIn" items="${listWare}">
                        <option value="${warehouseIn.id}">${warehouseIn.wareName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="supplierId" class="col-sm-2 control-label">供应商</label>
            <div class="col-sm-10">
                <select class="form-control" name="supplierId" id="supplierId">
                    <c:forEach var="supplier" items="${listSupplier}">
                        <option value="${supplier.id}">${supplier.supName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="addTime" class="col-sm-2 control-label">时间</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="addTime" name="dateTime" placeholder="请选择日期">
            </div>
        </div>
    </form>
</div>