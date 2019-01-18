<%--
  Created by mrren.
  Date: 2016-07-27
  Time: 15:43
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
    var prefix1 = 'YY';
    var dataString1 = '';
    var dbsJournalBusiness = '';

    var prefix2 = 'JS';
    var dataString2 = '';
    var dbsJournalSettlement = '';

    var prefix3 = 'JB';
    var dataString3 = '';
    var dbsJournalOffwork = '';

    var organCode = '222222';
    var posNumber = '1111';

    jQuery(document).ready(function() {
        dbs_set.itemCheckboxInit();
        dbs_set.saveInfoBtnInit();

        initDbsJournalBusiness();
        initDbsJournalSettlement();
        initDbsJournalOffwork();

        /******************************************************************************/
        $(".dbsJournalBusiness.isNeedPrefix").on('change', function(){
            var example = '';
            if(!$(this).is(':checked')){
                prefix1 ='';
            } else {
                prefix1 = $(".dbsJournalBusiness.prefix").val();
            }
            example = prefix1+organCode+posNumber+dataString1+'0001';
            $("#dbsJournalBusinessExample").text(example);
        });
        $(".dbsJournalBusiness.prefix").on('keyup', function(){
            if (!$(".dbsJournalBusiness.isNeedPrefix").is(':checked')) {
                prefix1='';
            } else {
                prefix1 = $(".dbsJournalBusiness.prefix").val();
            }

            var example = prefix1+organCode+posNumber+dataString1+'0001';
            $("#dbsJournalBusinessExample").text(example);
        });
        $(".dbsJournalBusiness.isNeedOrganCode").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                organCode = '222222';
            } else {
                organCode = '';
            }
            example = prefix1+organCode+posNumber+dataString1+'0001';
            $("#dbsJournalBusinessExample").text(example);
        });
        $(".dbsJournalBusiness.isNeedDateString").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                dataString1 = $(".dbsJournalBusiness.dateFormat  option:selected").text();
            } else {
                dataString1 = '';
            }
            example = prefix1+organCode+posNumber+dataString1+'0001';
            $("#dbsJournalBusinessExample").text(example);
        });
        $(".dbsJournalBusiness.dateFormat").on('change', function(){
            var example = '';
            if($(".dbsJournalBusiness.isNeedDateString").is(':checked')){
                dataString1 = $(".dbsJournalBusiness.dateFormat  option:selected").text();
            } else {
                dataString1 = '';
            }
            example = prefix1+organCode+posNumber+dataString1+'0001';
            $("#dbsJournalBusinessExample").text(example);
        });
        $(".dbsJournalBusiness.isOrderBeginWithOne").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                example = prefix1+organCode+posNumber+dataString1+ '0001';
            } else {
                example = prefix1+organCode+posNumber+dataString1+ '1234';
            }

            $("#dbsJournalBusinessExample").text(example);
        });
        /******************************************************************************/

        /******************************************************************************/
        $(".dbsJournalSettlement.isNeedPrefix").on('change', function(){
            var example = '';
            if(!$(this).is(':checked')){
                prefix2 ='';
            } else {
                prefix2 = $(".dbsJournalSettlement.prefix").val();
            }
            example = prefix2+organCode+posNumber+dataString2+'0001';
            $("#dbsJournalSettlementExample").text(example);
        });
        $(".dbsJournalSettlement.prefix").on('keyup', function(){
            if (!$(".dbsJournalSettlement.isNeedPrefix").is(':checked')) {
                prefix2='';
            } else {
                prefix2 = $(".dbsJournalSettlement.prefix").val();
            }

            var example = prefix2+organCode+posNumber+dataString2+'0001';
            $("#dbsJournalSettlementExample").text(example);
        });
        $(".dbsJournalSettlement.isNeedOrganCode").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                organCode = '222222';
            } else {
                organCode = '';
            }
            example = prefix2+organCode+posNumber+dataString2+'0001';
            $("#dbsJournalSettlementExample").text(example);
        });
        $(".dbsJournalSettlement.isNeedDateString").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                dataString2 = $(".dbsJournalSettlement.dateFormat  option:selected").text();
            } else {
                dataString2 = '';
            }
            example = prefix2+organCode+posNumber+dataString2+'0001';
            $("#dbsJournalSettlementExample").text(example);
        });
        $(".dbsJournalSettlement.dateFormat").on('change', function(){
            var example = '';
            if($(".dbsJournalSettlement.isNeedDateString").is(':checked')){
                dataString2 = $(".dbsJournalSettlement.dateFormat  option:selected").text();
            } else {
                dataString2 = '';
            }
            example = prefix2+organCode+posNumber+dataString2+'0001';
            $("#dbsJournalSettlementExample").text(example);
        });
        $(".dbsJournalSettlement.isOrderBeginWithOne").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                example = prefix2+organCode+posNumber+dataString2+ '0001';
            } else {
                example = prefix2+organCode+posNumber+dataString2+ '1234';
            }

            $("#dbsJournalSettlementExample").text(example);
        });
        /******************************************************************************/

        /******************************************************************************/
        $(".dbsJournalOffwork.isNeedPrefix").on('change', function(){
            var example = '';
            if(!$(this).is(':checked')){
                prefix3 ='';
            } else {
                prefix3 = $(".dbsJournalOffwork.prefix").val();
            }
            example = prefix3+organCode+posNumber+dataString3+'0001';
            $("#dbsJournalOffworkExample").text(example);
        });
        $(".dbsJournalOffwork.prefix").on('keyup', function(){
            if (!$(".dbsJournalOffwork.isNeedPrefix").is(':checked')) {
                prefix3='';
            } else {
                prefix3 = $(".dbsJournalOffwork.prefix").val();
            }

            var example = prefix3+organCode+posNumber+dataString3+'0001';
            $("#dbsJournalOffworkExample").text(example);
        });
        $(".dbsJournalOffwork.isNeedOrganCode").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                organCode = '222222';
            } else {
                organCode = '';
            }
            example = prefix3+organCode+posNumber+dataString3+'0001';
            $("#dbsJournalOffworkExample").text(example);
        });
        $(".dbsJournalOffwork.isNeedDateString").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                dataString3 = $(".dbsJournalOffwork.dateFormat  option:selected").text();
            } else {
                dataString3 = '';
            }
            example = prefix3+organCode+posNumber+dataString3+'0001';
            $("#dbsJournalOffworkExample").text(example);
        });
        $(".dbsJournalOffwork.dateFormat").on('change', function(){
            var example = '';
            if($(".dbsJournalOffwork.isNeedDateString").is(':checked')){
                dataString3 = $(".dbsJournalOffwork.dateFormat  option:selected").text();
            } else {
                dataString3 = '';
            }
            example = prefix3+organCode+posNumber+dataString3+'0001';
            $("#dbsJournalOffworkExample").text(example);
        });
        $(".dbsJournalOffwork.isOrderBeginWithOne").on('change', function(){
            var example = '';
            if($(this).is(':checked')){
                example = prefix3+organCode+posNumber+dataString3+ '0001';
            } else {
                example = prefix3+organCode+posNumber+dataString3+ '1234';
            }

            $("#dbsJournalOffworkExample").text(example);
        });
        /******************************************************************************/
    });
    function initDbsJournalBusiness(){
        if ($(".dbsJournalBusiness.isNeedPrefix").is(':checked')) {
            prefix1 = $(".dbsJournalBusiness.prefix").val();
        } else {
            prefix1 = '';
        }

        if(!$(".dbsJournalBusiness.isNeedOrganCode").is(':checked')) {
            organCode = '';
        }else {
            organCode = '222222';
        }
        if ($(".dbsJournalBusiness.isNeedDateString").is(':checked')) {
            dataString1 = $(".dbsJournalBusiness.dateFormat  option:selected").text();
        } else {
            dataString1 = '';
        }
        $("#dbsJournalBusinessExample").text(prefix1+organCode+posNumber+dataString1+'0001');

    }

    function initDbsJournalSettlement(){
        if ($(".dbsJournalSettlement.isNeedPrefix").is(':checked')) {
            prefix2 = $(".dbsJournalSettlement.prefix").val();
        } else {
            prefix2 = '';
        }

        if(!$(".dbsJournalSettlement.isNeedOrganCode").is(':checked')) {
            organCode = '';
        }else {
            organCode = '222222';
        }
        if ($(".dbsJournalSettlement.isNeedDateString").is(':checked')) {
            dataString2 = $(".dbsJournalSettlement.dateFormat  option:selected").text();
        } else {
            dataString2 =  '';
        }
        $("#dbsJournalSettlementExample").text(prefix2+organCode+posNumber+dataString2+'0001');
    }

    function initDbsJournalOffwork(){
        if ($(".dbsJournalOffwork.isNeedPrefix").is(':checked')) {
            prefix3 = $(".dbsJournalOffwork.prefix").val();
        } else {
            prefix3 = '';
        }

        if(!$(".dbsJournalOffwork.isNeedOrganCode").is(':checked')) {
            organCode = '';
        } else {
            organCode = '222222';
        }
        if ($(".dbsJournalOffwork.isNeedDateString").is(':checked')) {
            dataString3 = $(".dbsJournalOffwork.dateFormat  option:selected").text();
        } else {
            dataString3 = '';
        }
        $("#dbsJournalOffworkExample").text(prefix3+organCode+posNumber+dataString3+'0001');
    }
</script>
<div class="modal-body">
    <div class="row">
        <div class="col-md-12 form">
            <form class="form-horizontal" role="form" id="serialRulForm">
                <div class="form-body">
                    <h4>营业流水</h4>
                    <hr>
                    <div class="row" id="dbsJournalBusiness">
                        <div class="col-md-12 checkbox">
                            <div class="col-md-1">
                                <label>
                                    <input class="dbsJournalBusiness isNeedPrefix" name="dbsJournalBusiness.isNeedPrefix" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalBusiness.isNeedPrefix == 1}">checked</c:if> > 前缀 &nbsp;&nbsp;&nbsp;
                                </label>
                            </div>
                            <div class="col-md-1">
                                <input placeholder="请输入" style="width:60px;"  class="dbsJournalBusiness prefix" name="dbsJournalBusiness.prefix" type="text" value="${dbsSerialRulDTO.dbsJournalBusiness.prefix}">&nbsp;&nbsp;
                            </div>
                            <div class="col-md-3">
                                <span>+</span>&nbsp;&nbsp;<input class="dbsJournalBusiness isNeedOrganCode" name="dbsJournalBusiness.isNeedOrganCode" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalBusiness.isNeedOrganCode == 1}">checked</c:if> >组织机构代码&nbsp;&nbsp;<span>+</span>&nbsp;&nbsp;POS号&nbsp;&nbsp;<span>+</span>&nbsp;&nbsp;
                            </div>
                            <div class="col-md-3">
                                <label>
                                    <input class="dbsJournalBusiness isNeedDateString" name="dbsJournalBusiness.isNeedDateString" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalBusiness.isNeedDateString == 1}">checked</c:if> > 日期&nbsp;&nbsp;&nbsp;
                                </label>
                                <label>
                                    <select class="dbsJournalBusiness dateFormat" name="dbsJournalBusiness.dateFormat" placeholder="日期格式" >
                                        <option value="0" <c:if test="${dbsSerialRulDTO.dbsJournalBusiness.dateFormat == '0'}">selected</c:if>>-YYYYMMDD-</option>
                                        <option value="1" <c:if test="${dbsSerialRulDTO.dbsJournalBusiness.dateFormat == '1'}">selected</c:if>>YYYYMMDD</option>
                                    </select>
                                </label>
                            </div>
                            <div class="col-md-4">
                                <label><span>+顺序号（</span></label>
                                <label>&nbsp;<input class="dbsJournalBusiness isOrderBeginWithOne" name="dbsJournalBusiness.isOrderBeginWithOne" type="checkbox" <c:if test="${dbsSerialRulDTO.dbsJournalBusiness.isOrderBeginWithOne == 1}">checked</c:if> >每天从0开始)</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>示例：<span id="dbsJournalBusinessExample"></span></label>
                        </div>
                    </div>
                    <hr>
                    <h4>结算流水</h4>
                    <hr>
                    <div class="row" id="dbsJournalSettlement">
                        <div class="col-md-12 checkbox">
                            <div class="col-md-1">
                                <label>
                                    <input class="dbsJournalSettlement isNeedPrefix" name="dbsJournalSettlement.isNeedPrefix" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalSettlement.isNeedPrefix == 1}">checked</c:if> > 前缀 &nbsp;&nbsp;&nbsp;
                                </label>
                            </div>
                            <div class="col-md-1">
                                <input placeholder="请输入" style="width:60px;" class="dbsJournalSettlement prefix" name="dbsJournalSettlement.prefix" type="text" value="${dbsSerialRulDTO.dbsJournalSettlement.prefix}">&nbsp;&nbsp;
                            </div>
                            <div class="col-md-3">
                                <span>+</span>&nbsp;&nbsp;<input class="dbsJournalSettlement isNeedOrganCode" name="dbsJournalSettlement.isNeedOrganCode" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalSettlement.isNeedOrganCode == 1}">checked</c:if> >组织机构代码&nbsp;&nbsp;<span>+</span>&nbsp;&nbsp;POS号&nbsp;&nbsp;<span>+</span>&nbsp;&nbsp;
                            </div>
                            <div class="col-md-3">
                                <label>
                                    <input class="dbsJournalSettlement isNeedDateString" name="dbsJournalSettlement.isNeedDateString" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalSettlement.isNeedDateString == 1}">checked</c:if> > 日期&nbsp;&nbsp;&nbsp;
                                </label>
                                <label>
                                    <select class="dbsJournalSettlement dateFormat" name="dbsJournalSettlement.dateFormat" placeholder="日期格式" >
                                        <option value="0" <c:if test="${dbsSerialRulDTO.dbsJournalSettlement.dateFormat == '0'}">selected</c:if>>-YYYYMMDD-</option>
                                        <option value="1" <c:if test="${dbsSerialRulDTO.dbsJournalSettlement.dateFormat == '1'}">selected</c:if>>YYYYMMDD</option>
                                    </select>
                                </label>
                            </div>
                            <div class="col-md-4">
                                <label><span>+顺序号（</span></label>
                                <label>&nbsp;<input class="dbsJournalSettlement isOrderBeginWithOne" name="dbsJournalSettlement.isOrderBeginWithOne" type="checkbox" <c:if test="${dbsSerialRulDTO.dbsJournalSettlement.isOrderBeginWithOne == 1}">checked</c:if> >每天从0开始)</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>示例：<span id="dbsJournalSettlementExample"></span></label>
                        </div>
                    </div>
                    <hr>
                    <h4>结班流水</h4>
                    <hr>
                    <div class="row" id="dbsJournalOffwork">
                        <div class="col-md-12 checkbox">
                            <div class="col-md-1">
                                <label>
                                    <input class="dbsJournalOffwork isNeedPrefix" name="dbsJournalOffwork.isNeedPrefix" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalOffwork.isNeedPrefix == 1}">checked</c:if> > 前缀 &nbsp;&nbsp;&nbsp;
                                </label>
                            </div>
                            <div class="col-md-1">
                                <input placeholder="请输入" style="width:60px;" class="dbsJournalOffwork prefix" name="dbsJournalOffwork.prefix" type="text" value="${dbsSerialRulDTO.dbsJournalOffwork.prefix}">&nbsp;&nbsp;
                            </div>
                            <div class="col-md-3">
                                <span>+</span>&nbsp;&nbsp;<input class="dbsJournalOffwork isNeedOrganCode" name="dbsJournalOffwork.isNeedOrganCode" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalOffwork.isNeedOrganCode == 1}">checked</c:if> >组织机构代码&nbsp;&nbsp;<span>+</span>&nbsp;&nbsp;POS号&nbsp;&nbsp;<span>+</span>&nbsp;&nbsp;
                            </div>
                            <div class="col-md-3">
                                <label>
                                    <input class="dbsJournalOffwork isNeedDateString" name="dbsJournalOffwork.isNeedDateString" type="checkbox"  <c:if test="${dbsSerialRulDTO.dbsJournalOffwork.isNeedDateString == 1}">checked</c:if> > 日期&nbsp;&nbsp;&nbsp;
                                </label>
                                <label>
                                    <select class="dbsJournalOffwork dateFormat" name="dbsJournalOffwork.dateFormat" placeholder="日期格式" >
                                        <option value="0" <c:if test="${dbsSerialRulDTO.dbsJournalOffwork.dateFormat == '0'}">selected</c:if>>-YYYYMMDD-</option>
                                        <option value="1" <c:if test="${dbsSerialRulDTO.dbsJournalOffwork.dateFormat == '1'}">selected</c:if>>YYYYMMDD</option>
                                    </select>
                                </label>
                            </div>
                            <div class="col-md-4">
                                <label><span>+顺序号（</span></label>
                                <label>&nbsp;<input class="dbsJournalOffwork isOrderBeginWithOne" name="dbsJournalOffwork.isOrderBeginWithOne" type="checkbox" <c:if test="${dbsSerialRulDTO.dbsJournalOffwork.isOrderBeginWithOne == 1}">checked</c:if> >每天从0开始)</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 checkbox">
                            <label>示例：<span id="dbsJournalOffworkExample"></span></label>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<c:if test="${single == '1' }">
	<div class="modal-footer">
	    <button type="button" class="btn default" data-dismiss="modal">取消</button>
	    <button type="button" class="btn blue saveInfo" id="serialRulBtn">保存</button>
	</div>
</c:if>