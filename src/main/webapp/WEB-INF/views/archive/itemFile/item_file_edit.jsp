<%--
  Created by zhshuo.
  Date: 2016-10-14
  Time: 10:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="app/css/fileInput.css"/>
<style>
    #ppEditFormId .row {
        padding: 5px;
    }
</style>
<script src="assets/plugins/bootstrap-touchspin/jquery.bootstrap-touchspin.js" type="text/javascript" ></script>
<script src="assets/plugins/layui/lay/modules/element.js"></script>
<script src="app/js/DININGSYS/archive/itemFile/itemFileType_validation.js"></script>
<script>
    jQuery(document).ready(function () {

        itemFileTypeValidation.itemFileFormInit();

        itemFile.itemFileEditPageInit();

        itemFile.initPicPreview();

        $("#ppdlSelect").trigger("change");

    });
    
	//验证标准价格
    function chkPrice(value){
    	if(value < 0 ){
    		$("#standardPrice").val("");
    	}
    }
</script>
<input type="hidden" value="${nodeId}" id="pxxlId">
<div class="layui-tab">
    <ul class="layui-tab-title">
        <li class="layui-this">基本信息</li>
        <li>专用做法</li>
        <li>品项图片</li>
        <li>营养效果</li>
        <li>组成成分</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <form class="form-horizontal" id="ppEditFormId" style="width: 97%">
                <input type="hidden" id="hideId" name="id" value="${dgItemFile.id}">
                <input type="hidden" id="reUploadPxdt" name="reUploadPxdt">
                <input type="hidden" id="isDelPxdt" name="isDelPxdt">
                <input type="hidden" id="reUploadPxxt" name="reUploadPxxt">
                <input type="hidden" id="isDelPxxt" name="isDelPxxt">
                <input type="hidden" name="zyzfids" id="zyzfids"/>
                <input type="hidden" name="ggzyzfids" id="ggzyzfids"/>
                <input type="hidden" name="yyxgids" id="yyxgids"/>
                <input type="hidden" name="pxxtsm" id="yxxgsm"/>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4"><span class="required">*</span>编号：</span>
                        <div class="col-md-8">
                            <input readonly type="text" class="form-control" name="num" value="${dgItemFile.num}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">助记符：</span>
                        <div class="col-md-8">
                            <input placeholder="请输入助记符" type="text" class="form-control" name="zjf"
                                   value="${dgItemFile.zjf}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4"><span class="required">*</span>名称：</span>
                        <div class="col-md-8">
                            <input placeholder="请输入名称" type="text" class="form-control" name="name"
                                   value="${dgItemFile.name}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">单位：</span>
                        <div class="col-md-8">
                            <select class="form-control" name="unit">
                                <%-- <c:forEach items="${jbdw}" var="jbdw">
                                    <option value="${jbdw.cCode}"
                                            <c:if test="${dgItemFile.unit == jbdw.cCode}">selected</c:if>>${jbdw.cName}</option>
                                </c:forEach> --%>
                                <c:forEach items="${jbdw}" var="jbdw">
                                    <option value="${jbdw.cName}"
                                            <c:if test="${dgItemFile.unit == jbdw.cName}">selected</c:if>>${jbdw.cName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">品项大类：</span>
                        <div class="col-md-8">
                            <select class="form-control" name="ppdlId" id="ppdlSelect">
                                <c:forEach items="${dgItemFileTypes}" var="item">
                                    <option value="${item.id}"
                                            <c:if test="${nodeParentId == item.id}">selected</c:if>>${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">品项小类：</span>
                        <div class="col-md-8">
                            <select class="form-control" name="ppxlId" id="ppxlId">
                               <%-- <c:forEach items="${dgItemFileSmallTypes}" var="item">
                                    <option value="${item.id}"
                                            <c:if test="${nodeId == item.id}">selected</c:if>>${item.name}</option>
                                </c:forEach>--%>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">销售类型：</span>
                        <div class="col-md-8">
                            <select class="form-control" name="ppdlId">
                                <c:forEach items="${xslx}" var="xslx">
                                    <option value="${xslx.cCode}"
                                            <c:if test="${dgItemFile.xxlxId == xslx.cCode}">selected</c:if>>${xslx.cName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">品项辣度：</span>
                        <div class="col-md-8">
                            <select class="form-control" name="pxld">
                                <c:forEach items="${pxld}" var="pxld">
                                    <option value="${pxld.cCode}"
                                            <c:if test="${dgItemFile.pxld == pxld.cCode}">selected</c:if> >${pxld.cName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">角标：</span>
                        <div class="col-md-8">
                            <select class="form-control" name="jbiao">
                                <c:forEach items="${jb}" var="jb">
                                    <option value="${jb.cCode}"
                                            <c:if test="${dgItemFile.jbiao == jb.cCode}">selected</c:if> >${jb.cName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">打印分组：</span>
                        <div class="col-md-8">
                            <select class="form-control" name="dyfz">
                                <c:forEach items="${dyfz}" var="dyfz">
                                    <option value="${dyfz.cCode}"
                                            <c:if test="${dgItemFile.dyfz == dyfz.cCode}">selected</c:if> >${dyfz.cName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <%--<div class="row"> 允许半份暂时不需要 2017年3月1日14:06:47
                    <div class="col-md-6">
                        <span class="control-label col-md-4"></span>
                        <div class="checkbox col-md-8">
                            <label>
                                <input type="checkbox" value="1" name="yybf"
                                       <c:if test="${dgItemFile.yybf == 1}">checked="checked"</c:if> > 允许半份
                            </label>
                        </div>
                    </div>
                    <!--  <div class="col-md-6">
                        <span class="control-label col-md-4">最小点单数量：</span>
                        <div class="col-md-8">
                            <input type="text" class="form-control" name="zxddsl" value="${dgItemFile.zxddsl}"/>
                            最小点单数量为0时，不限制点单最小数量
                        </div>
                    </div>-->
                </div>--%>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4"><span class="required">*</span>标准价：</span>
                        <div class="col-md-8">
                            <input placeholder="请输入标准价"  type="number" class="form-control edit-control number" onblur="chkPrice(this.value)" name="standardPrice" id="standardPrice"
                                   value="${dgItemFile.standardPrice}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">成本价：</span>
                        <div class="col-md-8">
                            <input type="number" name="costPrice" class="form-control" value="${dgItemFile.costPrice}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">英文名称：</span>
                        <div class="col-md-8">
                            <input type="text" name="ywmc" class="form-control" value="${dgItemFile.ywmc}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">其他名称：</span>
                        <div class="col-md-8">
                            <input type="text" name="qtmc" class="form-control" value="${dgItemFile.qtmc}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">规格：</span>
                        <div class="col-md-8">
                            <input type="text" name="gg" class="form-control" value="${dgItemFile.gg}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">商品条码：</span>
                        <div class="col-md-8">
                            <input type="number" name="sptm" class="form-control" value="${dgItemFile.sptm}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">说明：</span>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="sm" name="sm" value="${dgItemFile.sm}"/>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">标准制作时长：</span>
                        <div class="col-md-8">
                            <input type="number" class="form-control" name="bzzzsc" value="${dgItemFile.bzzzsc}"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">厨师：</span>
                        <div class="col-md-8">
                            <select name="cs" class="form-control">
                                <c:forEach items="${allCook}" var="cook">
                                    <option value="${cook.empCode}"
                                            <c:if test="${dgItemFile.cs == cook.empCode}">selected</c:if> >${cook.empName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">特殊时间段1制作时长：</span>
                        <div class="col-md-8">
                            <input type="number" class="form-control" name="tssjdOne" value="${dgItemFile.tssjdOne}">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4">特殊时间段2制作时长：</span>
                        <div class="col-md-8">
                            <input type="number" class="form-control" name="tssjdTwo" value="${dgItemFile.tssjdTwo}">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4">RFID卡编号：</span>
                        <div class="col-md-8">
                            <input type="number" class="form-control" name="rfidCard" value="${dgItemFile.rfidCard}">
                        </div>
                    </div>
                </div>
                <%--<div class="row">暂时取消 2017年3月1日14:06:30
                    &lt;%&ndash;<div class="col-md-6">
                        <div class="col-md-8">
                            <input type="hidden" name="tcywhpxxl"/>
                            <a>套餐已维护可选品项或小类0个</a>
                        </div>
                    </div>&ndash;%&gt;
                    <div class="col-md-6">
                        <span class="control-label col-md-4"></span>
                        <div class="col-md-8">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="1" name="jsqtsxgsl"
                                           <c:if test="${dgItemFile.jsqtsxgsl == 1}">checked="checked"</c:if>> 结算前提示修改数量
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4"></span>
                        <div class="col-md-8">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="1" name="gjjefssl"
                                           <c:if test="${dgItemFile.gjjefssl == 1}">checked="checked"</c:if>> 根据金额反算数量
                                </label>
                            </div>
                        </div>
                    </div>
                </div>--%>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4"></span>
                        <div class="col-md-8">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"
                                           <c:if test="${dgItemFile.isTc == 1}">checked="checked"</c:if> value="1"
                                           name="isTc"> 套餐
                                </label>
                            </div>
                        </div>
                        <%--<div class="col-md-6">
                            <input type="hidden" name="tcywhpxxl"/>
                            <span class="control-label">套餐已维护可选品项或小类0个</span>
                        </div>--%>
                    </div>
                    <%--<div class="col-md-6"> 参与最低消费暂时取消  2017年3月1日16:15:01
                        <span class="control-label col-md-4"></span>
                        <div class="col-md-8">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="1" name="cyzdxf"
                                           <c:if test="${dgItemFile.cyzdxf == 1}">checked="checked"</c:if>> 参与最低消费
                                </label>
                            </div>
                        </div>
                    </div>--%>
                    <div class="col-md-6">
                        <span class="control-label col-md-4"></span>
                        <div class="col-md-8">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="1" name="lspx"
                                           <c:if test="${dgItemFile.lspx == 1}">checked="checked"</c:if>> 临时品项
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <span class="control-label col-md-4"></span>
                        <div class="col-md-8">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="1" name="yxdz"
                                           <c:if test="${dgItemFile.yxdz == 1}">checked="checked"</c:if>> 允许打折
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <span class="control-label col-md-4"></span>
                        <div class="col-md-8">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" value="1" name="yxe"
                                           <c:if test="${dgItemFile.yxe == 1}">checked="checked"</c:if> > 是否在平板上显示
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="layui-tab-item">
            <div style="height: 50px;text-align: right;">
                <a href="javascript:void(0)" id="zyzfAdd" class="btn btn-default btn-small active"
                   role="button">新增</a>
                <a href="javascript:void(0)" id="zyzfEdit" class="btn btn-default btn-small active"
                   role="button">修改</a>
                <a href="javascript:void(0)" id="zyzfDel" class="btn btn-default btn-small active"
                   role="button">删除</a>
            </div>
            <div class="table-responsive" style="height: 200px;">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>收取制作费用</th>
                        <th>制作费用</th>
                        <th>制作费用与数量有关</th>
                        <th>制作费用可修改标志</th>
                    </tr>
                    </thead>
                    <tbody id="zyzzffTbody">
                    <c:forEach items="${zyMethods}" var="zyMethods">
                        <tr>
                            <td style="display: none">
                                    ${zyMethods.id}
                            </td>
                            <td>${zyMethods.pmcode}</td>
                            <td>${zyMethods.pmname}</td>
                            <td>${zyMethods.collectProMoney == 1?'是':'否'}</td>
                            <td>${zyMethods.proMoney}</td>
                            <td>${zyMethods.collectProMoneybynum == 1?'是':'否'}</td>
                            <td>${zyMethods.canUpdate == 1?'是':'否'}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div style="height: 50px;text-align: right">
                也可以从公共制作方法中选择，作为当前品项的专用做法
                <a href="javascript:void(0)" id="ggzzffChoose" class="btn btn-default btn-small active"
                   role="button">制作方法选择</a>
            </div>
            <div class="table-responsive" style="height: 200px">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>收取制作费用</th>
                        <th>制作费用</th>
                        <th>制作费用与数量有关</th>
                        <th>制作费用可修改标志</th>
                    </tr>
                    </thead>
                    <tbody id="ggzzffTbody">
                    <c:forEach items="${ggMethods}" var="ggMethods">
                        <tr>
                            <td style="display: none">
                                    ${ggMethods.id}
                            </td>
                            <td>${ggMethods.pmcode}</td>
                            <td>${ggMethods.pmname}</td>
                            <td>${ggMethods.collectProMoney == 1?'是':'否'}</td>
                            <td>${ggMethods.proMoney}</td>
                            <td>${ggMethods.collectProMoneybynum == 1?'是':'否'}</td>
                            <td>${ggMethods.canUpdate == 1?'是':'否'}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="layui-tab-item">
            <form class="form-horizontal" id="pxtpFormId" style="width: 97%" enctype="multipart/form-data">
                <div style="width: 700px;height: 300px;">
                    <div style="width: 300px;height: 300px;margin: 50px;float: left">
                        <a href="javascript:;" class="file_a">
                            选择文件
                            <input type="hidden" name="pxdt" id="pxdtFileBd"/>
                            <input class="file_input pxpciUpload" id="pxdtFile" type="file" name="pxdtFile"/>
                        </a>
                        <a href="javascript:;" id="pxdtCancel" class="file_a fileUpCancel">
                            删除文件
                        </a>
                        <p>
                            <img alt="" name="pic" id="pxdtPreview" src="
                                <c:choose>
                                    <c:when test="${not empty dgItemFile.pxdt}">
                                        ${dgItemFile.pxdt}
                                    </c:when>
                                    <c:otherwise>
                                     app/img/no-image-big.png
                                    </c:otherwise>
                                </c:choose>
                            " width="280px" height="250px">
                        </p>
                    </div>
                    <div style="width: 250px;height: 250px;margin-top: 50px;float: right">
                        <a href="javascript:;" class="file_a">
                            选择文件
                            <input type="hidden" name="pxxt" id="pxxtFileBd"/>
                            <input class="file_input pxpciUpload" id="pxxtFile" type="file" name="pxxtFile"/>
                        </a>
                        <a href="javascript:;" id="pxxtCancel" class="file_a fileUpCancel">
                            删除文件
                        </a>
                        <p>
                            <img src="
                                    <c:choose>
                                        <c:when test="${not empty dgItemFile.pxxt}">
                                           ${dgItemFile.pxxt}
                                        </c:when>
                                        <c:otherwise>
                                         app/img/no-image-small.png
                                        </c:otherwise>
                                    </c:choose>
                                " alt="" name="pic" id="pxxtPreview" width="250px" height="200px">
                        </p>
                    </div>
                </div>
            </form>
        </div>
        <div class="layui-tab-item">
            <h4>营养成分</h4>
            <div class="table-responsive" style="height: 200px">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>名称</th>
                        <th>标准摄入量</th>
                        <th>单位</th>
                    </tr>
                    </thead>
                    <tbody id="nutritionChooseTab">
                    <c:forEach items="${nutritionInIds}" var="nutrition">
                        <tr>
                            <td style="display: none">${nutrition.id}</td>
                            <td>${nutrition.code}</td>
                            <td>${nutrition.name}</td>
                            <td>${nutrition.bzsrl}</td>
                            <td>${nutrition.unit == 1?'千焦':'克'}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div style="text-align: right">
                <a href="javascript:void(0)" class="editChooseNutrition btn btn-default btn-small"
                   role="button">营养选择</a>
                <a href="javascript:void(0)" id="showNutritionBtn" class="btn btn-default btn-small" role="button">基本营养成分</a>
            </div>
            <div>
                <h4>保健、食疗、美容效果</h4>
                <textarea id="pxxtsm" rows="2" cols="3"
                          style="width: 95%;height: 200px">${dgItemFile.pxxtsm}</textarea>
            </div>
        </div>
        <div class="layui-tab-item">
            <div style="height: 50px;text-align: right">
                <a href="javascript:void(0)" id="zccfAdd" class="btn btn-default btn-small active"
                   role="button">新增</a>
                <a href="javascript:void(0)" id="zccfDel" class="btn btn-default btn-small active"
                   role="button">删除</a>
                <a href="javascript:void(0)" id="zccfClear" class="btn btn-default btn-small active"
                   role="button">清空</a>
            </div>
            <div class="table-responsive" style="height: 400px">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>成分名称</th>
                        <th>成分类型名称</th>
                        <th>单位</th>
                        <th>数量</th>
                    </tr>
                    </thead>
                    <tbody id="zccfTbody">
                    <c:forEach items="${zccfMethods}" var="zccfMethods">
                        <tr>
                            <td style="display: none">
                                    ${zccfMethods.id}
                            </td>
                            <td style="display: none">
                                    ${zccfMethods.inveItemId}
                            </td>
                            <td>${zccfMethods.inveItemCode}</td>
                            <td>${zccfMethods.name}</td>
                            <td>${zccfMethods.bName}</td>
                            <td>${zccfMethods.unit}</td>
                            <td><input type="text" value="${zccfMethods.counter}"></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>