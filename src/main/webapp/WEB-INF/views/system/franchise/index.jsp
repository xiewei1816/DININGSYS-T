<%--
  Created by zhshuo.
  Date: 2017-04-21
  Time: 10:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="app/js/DININGSYS/system/franchise/data_upload.js"></script>
<script>
    jQuery(document).ready(function () {
        $("#dataUploadModelChoose").height($(window).height()-250);
        $("#dataUploadLog").height($(window).height() -250);

        dataUpload.pageInit();
    })
</script>

<form class="form-horizontal" role="form" style="padding: 0px 5px 0px 5px">
    <div class="row">
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">上传模块选择</h3>
                    </span>
                </div>
                <div id="dataUploadModelChoose" class="panel-body" style="overflow-y:auto;">
                    <h5 class="">品项档案</h5>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_GGDM" msg="公共代码">公共代码
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_YGGL" msg="员工管理">员工管理
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_SCZT" msg="上菜状态">上菜状态
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_ZDYY" msg="赠单原因">赠单原因
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_BMGL" msg="部门管理">部门管理
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_YYSB" msg="营业市别">营业市别
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_PXDA" msg="品项档案">品项档案
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_TCYY" msg="退菜原因">退菜原因
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_ZDBZ" msg="整单备注">整单备注
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_FYXM" msg="费用项目">费用项目
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_XFQYHKE" msg="消费区域和客位">消费区域和客位
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_POSDA" msg="POS档案">POS档案
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_JSFS" msg="结算方式">结算方式
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_ZZFF" msg="制作方法">制作方法
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_KWYQ" msg="口味要求">口味要求
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <h5>营业管理</h5>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_PXGL" msg="品项管理">品项管理
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_FYDJ" msg="费用登记">费用登记
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_XFPXXSSZ" msg="消费品项显示设置">消费品项显示设置
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_FWYFWKW" msg="服务员服务客位">服务员服务客位
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_XFQYYKWGL" msg="消费区域与客位管理">消费区域与客位管理
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_QTYYSZ" msg="前台营业设置">前台营业设置
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <h5>权限管理</h5>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="DATA_CZQX" msg="操作权限">操作权限
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer" style="text-align: right">
                    <button type="button" id="chooseAll" class="btn btn-primary">全选</button>
                    <button type="button" id="cancelChooseAll" class="btn btn-info">取消全选</button>
                    <button type="button" id="upload" class="btn btn-danger">上传</button>
                    <button type="button" id="cancelUpload" class="btn btn-warning">取消上传</button>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">上传信息</h3>
                </div>
                <div id="dataUploadLog" class="panel-body" style="overflow-y:auto;">
                    <div id="messageAppendDiv" style="height: 100%;overflow-y: auto;">
                    </div>
                </div>
                <div class="panel-footer" style="text-align: right">
                    <button type="button" id="clearScreen" class="btn btn-info">清空信息</button>
                </div>
            </div>
        </div>
    </div>
</form>