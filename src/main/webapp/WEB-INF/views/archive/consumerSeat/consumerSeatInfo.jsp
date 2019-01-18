<%-- 
    Document   : consumerSeatInfo
    Created on : 2016-11-11, 12:55:22
    Author     : yqsh-zc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<script>
    $("select.chosens").chosen({
   		width : "200px"
   	});
    
    $(function () {
		//初始化客位类型
		var seatTypeText = $("#seatTypeValue").val();
		var option = $("#seatType option");
		option.each(function(){
			if($(this).text() == seatTypeText){
				$(this).attr("selected",true);
			}
		});
	});
    
    function chkAllowNumber(){
    	var allowNumber = $("#allowNumber").val();
    	if(allowNumber < 1 ){
    		$("#allowNumber").val(1);
    	}
    }
    
    //判断容纳人数是否合法
    function isLegalAllowNumber(){
    	var flag = true;
    	var id = $("#id").val();
    	var seatState = $("#seatState").val();
    	var disabledSeat = $('input[id="disabledSeat"]').filter(':checked').val();
    	if(id != ""){
    		if(seatState != 1 && disabledSeat == 1){
    			layer.tips("该客位正在使用,不能进行停用！",$("#chkDisabledSeat"), {guide: 1, tipsMore:true, time: 2000}); 
    			flag = false;
    		}
    	}

    	var allowNumberId = $("#allowNumberId").val();
    	var allowNumber = parseInt($("#allowNumber").val());
    	if(typeof(allowNumberId) != "undefined" ){
    		$.ajax({
    	        type:'POST',
    	        url:"DININGSYS/dgAllowNumber/selectById",
    	        data:{id:allowNumberId},
    	        dataType:'JSON',
    	        async:false,
    	        success:function (data) {
    	            if(data.success){
    	            	var min = data.dan.minAllowNumber;
    	            	var max = data.dan.maxAllowNumber;
    	            	var msg = "容纳人数在"+min+"-"+max+"之间!";
				    	if(allowNumber > 0){
				    		if(allowNumber < min || allowNumber > max){
					    		layer.tips(msg,$("#allowNumber"), {guide: 1, tipsMore:true, time: 2000}); 
								flag = false;
				    		}
				    	}else{
				    		layer.tips("容纳人数必须大于0",$("#allowNumber"), {guide: 1, tipsMore:true, time: 2000}); 
							flag = false;
				    	}
    	            }
    	        }
    	    });
    	}
    	
    	return flag;
    }
</script>
<style>
    .required{
            display:block;
            position:absolute;
            color: red;
            right:5px;
            top:6px;
            font-size: 25px;
    }
</style>
</head>
<body style="overflow-x:hidden;overflow-y: auto;">
<form class="form-horizontal" role="form" id="myfrom" method="post">
        <input type="hidden" id="id" name="id" value="${bean.id}" >
        <div class="form-group">
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>客位编号</label>
                    <div class="col-sm-7">
                        <input placeholder="请输入客位编号" type="text" class="form-control" name="number" value="${bean.number}" dete="val"/>
                    </div>
            </div>
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>客位名称</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="edit_named" name="name" value="${bean.name}" placeholder="请输入客位名称" dete="val"/>
                    </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label">助记符</label>
                    <div class="col-sm-7">
                            <input type="text" class="form-control" name="mnemonic" value="${bean.mnemonic}" placeholder="请输入助记符"/>
                    </div>
            </div>
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>消费区域</label>
                    <div class="col-sm-7">
                   	 	<select class="form-control edit_consArea" id="consArea" name="consArea" editype="val" dete="val">
                            <c:forEach var="area" items="${areas}">
                                <option value="${area.id}">${area.name}</option>
                            </c:forEach>
                        </select>
                    </div> 
                    <script type="text/javascript">
                    if('${bean.consArea}' != ""){
						$("#consArea").val('${bean.consArea}');
                    }
                    </script>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label">全名称</label>
                    <div class="col-sm-7">
                            <input type="text" class="form-control edit_allName" name="allName" editype="val" value="${bean.allName}"  placeholder="请输入全名称"/>
                    </div>
            </div>
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>客位类型</label>
                    <div class="col-sm-7">
                   	 	<input type="text" id="seatTypeValue" value="${bean.seatType}" style="display: none;"/>
                   	 	<select class="form-control edit_seatType" id="seatType" name="seatType" editype="val" dete="val">
							<c:forEach items="${list}" var="map" varStatus="status"> 
								<c:forEach items="${map.KWLX}" var="o" varStatus="index">
								 	<option  value="${o.id}" <c:if test="${fn:indexOf(o.cName,bean.seatType) > 0}">selected</c:if>>${o.cName}</option>
								 </c:forEach>
							</c:forEach>
                           </select>
                    </div>
            </div>
        </div>
        <div class="form-group">
        	<div class="col-sm-5">
                  <label class="col-sm-4 control-label"><span class="required">*</span>客位分类</label>
                  <div class="col-sm-7">
                 	 	<input type="hidden" id="allowNumberIdValue" value="${bean.allowNumberId}" />
                 	 	<select class="form-control edit_allowNumberId" id="allowNumberId" name="allowNumberId" editype="val" dete="val">
                            <c:forEach var="o" items="${allowNumberList}">
                                <option value="${o.id}">${o.name}</option>
                            </c:forEach>
                      	</select>
                  </div>
                   <script type="text/javascript">
                   		if('${bean.allowNumberId}' != ""){
							$("#allowNumberId").val('${bean.allowNumberId}');
                   		}
                    </script>
            </div>
            <div class="col-sm-5">
                   <label class="col-sm-4 control-label"><span class="required">*</span>容纳人数</label>
                    <div class="col-sm-7">
                          <input type="number" onchange="chkAllowNumber()" class="form-control" id="allowNumber" name="allowNumber" value="${bean.allowNumber}" placeholder="请输入容纳人数" dete="val"/>
                    </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>结算后默认状态</label>
                    <div class="col-sm-7">
                    	<select class="form-control edit_defaultState" id="defaultState" name="defaultState" editype="val" dete="val">
                            <option value="1">空闲</option>
                            <option value="2">清扫</option>
                         </select>
                    </div>
                    <script type="text/javascript">
                    if('${bean.defaultState}' != ""){
						$("#defaultState").val('${bean.defaultState}');
                    }
                    </script>
            </div>
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label">说明</label>
                    <div class="col-sm-7">
                        <textarea class="form-control edit_explains" name="explains" id="explains">${bean.explains}</textarea>
                    </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span id="chkDisabledSeat">客位停用</span></label>
                    <div class="col-sm-7">
                    		<input type="hidden" id="seatState" value="${bean.seatState}"/>
                            <label class="radiobtn"><input type="radio" value="0" class="edit_disabledSeat"
                                                           name="disabledSeat" id="disabledSeat" editype="radio" checked="checked"/><i></i><span></span>否</label>
                           <label class="radiobtn"><input type="radio" value="1" class="edit_disabledSeat"
                                                          name="disabledSeat" id="disabledSeat" editype="radio" /><i></i><span></span>是</label>
                    </div>
                    <script type="text/javascript">
			$("input[name='disabledSeat']:eq(${bean.disabledSeat})").attr("checked",'checked'); 
                    </script>
            </div>
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>内部留房</label>
                    <div class="col-sm-7">
                           <label class="radiobtn"><input type="radio" value="0" class="edit_insetRetentionRoom"
                            name="insetRetentionRoom" checked="checked"/><i></i><span></span>否</label>
                           <label class="radiobtn"><input type="radio" value="1" class="edit_insetRetentionRoom"
                            name="insetRetentionRoom"/><i></i><span></span>是</label>
                    </div>
                    <script type="text/javascript">
			$("input[name='insetRetentionRoom']:eq(${bean.insetRetentionRoom})").attr("checked",'checked'); 
                    </script>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>不统计翻台率</label>
                    <div class="col-sm-7">
                          <label class="radiobtn"><input type="radio" value="0" class="edit_notStatisticsOvertaiwan"
                                         name="notStatisticsOvertaiwan" editype="radio" checked="checked"/><i></i><span></span>否</label>
                                        <label class="radiobtn"><input type="radio" value="1" class="edit_notStatisticsOvertaiwan"
                                         name="notStatisticsOvertaiwan" editype="radio" /><i></i><span></span>是</label>
                    </div>
                    <script type="text/javascript">
			$("input[name='notStatisticsOvertaiwan']:eq(${bean.notStatisticsOvertaiwan})").attr("checked",'checked'); 
                    </script>
            </div>
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label"><span class="required">*</span>是否允许排队</label>
                    <div class="col-sm-7">
                          <label class="radiobtn"><input type="radio" value="0" class="edit_customerRetention"
                                         name="customerRetention" editype="radio" checked="checked"/><i></i><span></span>否</label>
                                        <label class="radiobtn"><input type="radio" value="1" class="edit_customerRetention"
                                         name="customerRetention" editype="radio" /><i></i><span></span>是</label>
                    </div>
                    <script type="text/javascript">
			$("input[name='customerRetention']:eq(${bean.customerRetention})").attr("checked",'checked'); 
                    </script>
            </div>
        </div>
        <!-- <div class="form-group">
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label">虚客位</label>
                    <div class="col-sm-7">
                            <label class="radiobtn"><input type="radio" value="0" class="edit_emptyGuest"
                                         name="emptyGuest" editype="radio" checked="checked"/><i></i><span></span>否</label>
                                        <label class="radiobtn"><input type="radio" value="1" class="edit_emptyGuest"
                                         name="emptyGuest" editype="radio" /><i></i><span></span>是</label>
                    </div>
                    <script type="text/javascript">
                $("input[name='emptyGuest']:eq(${bean.emptyGuest})").attr("checked",'checked');
            </script>
            </div>
            <div class="col-sm-5">
                    <label class="col-sm-4 control-label">加单后立即结算</label>
                    <div class="col-sm-7">
                            <label class="radiobtn"><input type="radio" value="0" class="edit_immediateSettlement"
                                         name="immediateSettlement" editype="radio" checked="checked"/><i></i><span></span>否</label>
                                        <label class="radiobtn"><input type="radio" value="1" class="edit_immediateSettlement"
                                         name="immediateSettlement" editype="radio" /><i></i><span></span>是</label>
                    </div>
                    <script type="text/javascript">
			$("input[name='immediateSettlement']:eq(${bean.immediateSettlement})").attr("checked",'checked'); 
                    </script>
            </div>
        </div> -->
	</form>
</body>