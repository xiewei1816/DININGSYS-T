<%--
  Created by zhshuo.
  Date: 2017-01-22
  Time: 11:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<jsp:include page="../head.jsp"></jsp:include>
<script src="app/js/DININGSYS/api/open_water.js"></script>
<script>
    jQuery(document).ready(function () {
        apiOpenClass.pageTableInit();
    })
</script>
<style type="text/css">
	#openClassReport {
		float: left;
	}
    table.jbtable {
        font-family: verdana,arial,sans-serif;
        font-size:12px;
        color:#333333;
        border-width: 1px;
        border-color: #999999;
        border-collapse: collapse;
        margin: 2px;
        float: left;
    }
    table.jbtable th {
        background:#f5f5f5;
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #999999;
        width: 100px;
        text-align: center;
    }
    table.jbtable td {
        border-width: 1px;
        padding: 8px;
        border-style: solid;
        border-color: #999999;
    }
</style>
<input id="loginPos" type="hidden" value="${loginPos}"/>
<input id="userCode" type="hidden" value="${userCode}"/>
<div class="col-md-12" style="width: 800px;">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#openClassReport" aria-controls="home" role="tab" data-toggle="tab">结班报表</a></li>
        <li role="presentation"><a href="#bigTypeReport" aria-controls="profile" role="tab" data-toggle="tab">大类报告</a></li>
        <li role="presentation"><a href="#smallTypeReport" aria-controls="home" role="tab" data-toggle="tab">小类报告</a></li>
        <li role="presentation"><a href="#itemFileReport" aria-controls="profile" role="tab" data-toggle="tab">品项报告</a></li>
        <li role="presentation"><a href="#memberGZReport" aria-controls="profile" role="tab" data-toggle="tab">会员挂账</a></li>
        <li role="presentation"><a href="#clearigWayReport" aria-controls="profile" role="tab" data-toggle="tab">结算方式</a></li>
    </ul>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="openClassReport">
            <table class="jbtable" >
                <tr>
                    <th>接班流水号:</th><td colspan="3">${openClassReport['jbWater']}</td>
                </tr>
                <tr>
                    <th>会员充值金额:</th><td>${openClassReport['rechargeData']}</td>
                    <th>S账金额:</th><td>${openClassReport['sbillData']['sbillSubtotal']}</td>
                </tr>
                <tr>
                    <th>POS:</th><td>${openClassReport['loginInfo']['posName']}</td>
                    <th>操作员:</th><td>${openClassReport['loginInfo']['login_user']}</td>
                </tr>
                <tr>
                    <th>签到时间:</th><td>${openClassReport['loginInfo']['login_time']}</td>
                    <th>备用金:</th><td>${openClassReport['loginInfo']['petty_cash']}</td>
                </tr>
                <tr>
                    <th>前班未结台数:</th><td>${openClassReport['lastLoginInfo']['current_wj_amount']}</td>
                    <th>前班未结金额:</th><td>${openClassReport['lastLoginInfo']['bbwjje']}</td>
                </tr>
                <tr>
                    <th>本班开单人数:</th><td>${openClassReport['bilingData']['peopleCount']}</td>
                    <th>本班开单总数:</th><td>${openClassReport['bilingData']['count']}</td>
                </tr>
                <tr>
                    <th>本班未结台数:</th><td>${openClassReport['openData']['count']}</td>
                    <th>本班未结金额:</th><td>${openClassReport['openData']['subtotal']}</td>
                </tr>
                <tr>
                    <th>本班未退押金:</th><td>${openClassReport['depositData']['wtDepositData']}</td>
                    <th>本班已结台数:</th><td>${openClassReport['closeData']['count']}</td>
                </tr>
                <tr>
                    <th>本班已结人数:</th><td>${openClassReport['closeData']['subtotal']}</td>
                    <th>本班赠单金额:</th><td>${openClassReport['freeData']['subtotal']}</td>
                </tr>
                <tr>
                    <th>本班退菜金额:</th><td>${openClassReport['backData']['subtotal']}</td>
                    <th>消费金额:</th><td>${openClassReport['clearingMoney']['xfje']}</td>
                </tr>
                <tr>
                    <th>服务费:</th><td>${openClassReport['fwf']}</td>
                    <th>包房费:</th><td>${openClassReport['bff']}</td>
                </tr>
                <tr>
                    <th>最低消费补齐:</th><td>${openClassReport['zdxfbq']}</td>
                    <th>优惠金额:</th><td>${openClassReport['yhje']}</td>
                </tr>
                <tr>
                    <th>定额优惠金额:</th><td>${openClassReport['clearingMoney']['deyh']}</td>
                    <th>抹零金额:</th><td>${openClassReport['clearingMoney']['mlje']}</td>
                </tr>
                <tr>
                    <th>应收小计:</th><th>${openClassReport['clearingMoney']['ysje']}</th>
                    <th>应收合计(含备用金押金):</th><th>${openClassReport['clearingMoney']['shje']}</th>
                </tr>
            </table>
            <!-- 结算方式 -->
            <table class="jbtable" >
            	<tr><th colspan="2">结算方式</th></tr>
            	<c:forEach items="${frequency}" var="list" varStatus="status">
                	<tr><th>${list.seName}:</th><td>${list.settlementAmount}</td></tr>
                </c:forEach>
            </table>   
        </div>
        <div role="tabpanel" class="tab-pane" id="bigTypeReport">
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">流水号:${openClassReport['jbWater']}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">POS:${openClassReport['loginInfo']['posName']}</label>
                </div>
                <div class="col-md-6">
                    <label class="control-label ">操作员:${openClassReport['loginInfo']['login_user']}-${openClassReport['loginInfo']['login_user_name']}</label>
                </div>
            </div>
            <table id="bigType"></table>
        </div>
        <div role="tabpanel" class="tab-pane" id="smallTypeReport">
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">流水号:${openClassReport['jbWater']}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">POS:${openClassReport['loginInfo']['posName']}</label>
                </div>
                <div class="col-md-6">
                    <label class="control-label ">操作员:${openClassReport['loginInfo']['login_user']}-${openClassReport['loginInfo']['login_user_name']}</label>
                </div>
            </div>
            <table id="smallType"></table>
        </div>
        <div role="tabpanel" class="tab-pane" id="itemFileReport">
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">流水号:${openClassReport['jbWater']}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">POS:${openClassReport['loginInfo']['posName']}</label>
                </div>
                <div class="col-md-6">
                    <label class="control-label ">操作员:${openClassReport['loginInfo']['login_user']}-${openClassReport['loginInfo']['login_user_name']}</label>
                </div>
            </div>
            <table id="itemFile"></table>
        </div>
        <div role="tabpanel" class="tab-pane" id="memberGZReport">
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">流水号:${openClassReport['jbWater']}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">POS:${openClassReport['loginInfo']['posName']}</label>
                </div>
                <div class="col-md-6">
                    <label class="control-label ">操作员:${openClassReport['loginInfo']['login_user']}-${openClassReport['loginInfo']['login_user_name']}</label>
                </div>
            </div>
            <table id="memberGZ"></table>
        </div>
        <div role="tabpanel" class="tab-pane" id="clearigWayReport">
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">流水号:${openClassReport['jbWater']}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label class="control-label ">POS:${openClassReport['loginInfo']['posName']}</label>
                </div>
                <div class="col-md-6">
                    <label class="control-label ">操作员:${openClassReport['loginInfo']['login_user']}-${openClassReport['loginInfo']['login_user_name']}</label>
                </div>
            </div>
            <table id="clearingWay"></table>
        </div>
    </div>
</div>