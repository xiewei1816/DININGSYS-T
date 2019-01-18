<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8" />
    <title>${systemName.settingValue}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport" />
    <meta content="" name="description" />
    <meta content="" name="author" />
    <meta name="MobileOptimized" content="320">
    <%--ztree style 放在最前面，防止和系统样式冲突--%>
    <link rel="stylesheet" href="app/lib/ztree/css/demo.css" type="text/css">
    <link rel="stylesheet" href="app/lib/ztree/css/metroStyle/metroStyle.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-datepicker/css/datepicker.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" />
    <link href="app/lib/summernote/summernote.css" rel="stylesheet">
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <!-- END GLOBAL MANDATORY STYLES -->
    <!-- BEGIN PAGE LEVEL STYLES -->
    <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.css" />
    <link href="assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css"/>
    <link href="assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css"/>
    <link href="assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="assets/plugins/select2/select2_metro.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/jquery-tags-input/jquery.tagsinput.css" />
    <%--table style--%>
    <link rel="stylesheet" href="assets/plugins/data-tables/DT_bootstrap.css" />
    <%--end--%>
    <%--modal style--%>
    <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.css" />
    <link rel="stylesheet" type="text/css" href="assets/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
    <%--end--%>
    <!-- END PAGE LEVEL STYLES -->
    <!-- BEGIN THEME STYLES -->
    <link href="assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
    <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link href="assets/css/plugins.css" rel="stylesheet" type="text/css"/>
    <link href="assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="assets/css/custom.css" rel="stylesheet" type="text/css"/>
    <!-- END THEME STYLES -->
    <link rel="shortcut icon" href="favicon.ico" />
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="page-header-fixed page-footer-fixed">
<!-- BEGIN HEADER -->
<div class="header navbar navbar-inverse navbar-fixed-top">
    <!-- BEGIN TOP NAVIGATION BAR -->
    <div class="header-inner">
        <!-- BEGIN LOGO -->
        <a class="navbar-brand" href="index.html">
            <img src="assets/img/logo.png" alt="logo" class="img-responsive" />
        </a>
        <!-- END LOGO -->
        <!-- BEGIN RESPONSIVE MENU TOGGLER -->
        <a href="javascript:;" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <img src="assets/img/menu-toggler.png" alt="" />
        </a>
        <!-- END RESPONSIVE MENU TOGGLER -->
        <!-- BEGIN TOP NAVIGATION MENU -->
        <ul class="nav navbar-nav pull-right">
            <!-- BEGIN USER LOGIN DROPDOWN -->
            <li class="dropdown user">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                   <%-- <img alt="" src="assets/img/avatar1_small.jpg"/>--%>
                    <span class="username">${userInfo.username}</span>
                    <i class="fa fa-angle-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="javascript:void(0)" onclick="toPersonCenterPage()"><i class="fa fa-user"></i> 个人中心</a></li>
                    <li class="divider"></li>
                    <li><a href="javascript:;" id="trigger_fullscreen"><i class="fa fa-move"></i> 全屏</a></li>
                    <%--<li><a href="extra_lock.html"><i class="fa fa-lock"></i> 锁屏</a></li>--%>
                    <li><a href="DININGSYS/logout"><i class="fa fa-key"></i> 退出</a></li>
                </ul>
            </li>
            <!-- END USER LOGIN DROPDOWN -->
        </ul>
        <!-- END TOP NAVIGATION MENU -->
    </div>
    <!-- END TOP NAVIGATION BAR -->
</div>
<!-- END HEADER -->
<div class="clearfix"></div>
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
    <div class="modal fade" id="portlet-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">Modal title</h4>
                </div>
                <div class="modal-body">
                    Widget settings form goes here
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn blue">Save changes</button>
                    <button type="button" class="btn default" data-dismiss="modal">Close</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>
    <!-- /.modal -->
    <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
    <!-- BEGIN SIDEBAR1 -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU1 -->
        <ul class="page-sidebar-menu">
            <li>
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler hidden-xs"></div>
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
            </li>
           <c:forEach items="${userMenus}" var="menu" begin="0" varStatus="status">
                <li <c:if test="${status.index == 0}">class="start"</c:if> <c:if test="${status.index+1 == fn:length(userMenus)}">class="last"</c:if>>
                    <a href="${menu.menuUrl}" class="ajaxify <c:if test="${status.index == 0}">start</c:if>">
                        <i class="fa ${menu.menuIcon}"></i>
                        <span class="title">${menu.menuName}</span>
                        <span class="selected"></span>
                        <c:if test="${not empty menu.child}"><span class="arrow open"></span></c:if>
                    </a>
                    <c:if test="${not empty menu.child}">
                        <ul class="sub-menu">
                            <c:forEach items="${menu.child}" var="childMenu">
                                <li>
                                    <a class="ajaxify" href="${childMenu.menuUrl}">
                                        ${childMenu.menuName}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                </li>
            </c:forEach>
                    </ul>
                    <!-- END SIDEBAR MENU1 -->
                </div>
                <!-- END SIDEBAR1 -->
                <!-- BEGIN PAGE -->
                <div class="page-content">
                    <!-- BEGIN STYLE CUSTOMIZER -->
                    <%--<div class="theme-panel hidden-xs hidden-sm">
                        <div class="toggler"></div>
                        <div class="toggler-close"></div>
                        <div class="theme-options">
                            <div class="theme-option theme-colors clearfix">
                                <span>主题颜色</span>
                                <ul>
                        <li class="color-black current color-default" data-style="default"></li>
                        <li class="color-blue" data-style="blue"></li>
                        <li class="color-brown" data-style="brown"></li>
                        <li class="color-purple" data-style="purple"></li>
                        <li class="color-grey" data-style="grey"></li>
                        <li class="color-white color-light" data-style="light"></li>
                    </ul>
                </div>
                <div class="theme-option">
                    <span>布局</span>
                    <select class="layout-option form-control input-small">
                        <option value="fluid" selected="selected">流状</option>
                        <option value="boxed">盒状</option>
                    </select>
                </div>
                <div class="theme-option">
                    <span>头部</span>
                    <select class="header-option form-control input-small">
                        <option value="fixed" selected="selected">流状</option>
                        <option value="default">默认</option>
                    </select>
                </div>
                <div class="theme-option">
                    <span>菜单栏</span>
                    <select class="sidebar-option form-control input-small">
                        <option value="fixed">固定</option>
                        <option value="default" selected="selected">默认</option>
                    </select>
                </div>
                <div class="theme-option">
                    <span>底部</span>
                    <select class="footer-option form-control input-small">
                        <option value="fixed" selected="selected">默认</option>
                        <option value="default">填充</option>
                    </select>
                </div>
            </div>
        </div>--%>
        <!-- END BEGIN STYLE CUSTOMIZER -->
        <!-- BEGIN PAGE HEADER-->
        <div class="page-content-body">
            <!-- HERE WILL BE LOADED AN AJAX CONTENT -->
        </div>
    </div>
    <!-- BEGIN PAGE -->
</div>
<!-- END CONTAINER -->
<!-- BEGIN FOOTER -->
<div class="footer">
    <div class="footer-inner">
        2013 &copy; DININGSYS by 成都易科士信息产业有限公司.
    </div>
    <div class="footer-tools">
			<span class="go-top">
			<i class="fa fa-angle-up"></i>
			</span>
    </div>
</div>

<%--用来显示ajax返回信息提示，比如错误提示信息--%>
<div id="ajaxMessageShow" class="modal fade" tabindex="-1">
    <div class="modal-header">
        <button type="button" class="close ajaxShowClose" aria-hidden="true"></button>
        <h4 class="modal-title">系统提示</h4>
    </div>
    <div class="modal-body">
        <p id="ajaxMessageShowPID"></p>
    </div>
    <div class="modal-footer">
        <button type="button" id="ajaxMessageShowCloseBtn" class="btn btn-default ajaxShowClose">关闭</button>
        <button id="ajaxMessageShowSureBtn" type="button" class="btn btn-default">确认</button>
    </div>
</div>
<a style="display: none" class="btn default" data-target="#ajaxMessageShow" data-toggle="modal" id="ajaxShowBtnID"/>
<!-- END FOOTER -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="assets/plugins/respond.min.js"></script>
<script src="assets/plugins/excanvas.min.js"></script>
<![endif]-->
<script src="assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript" ></script>
<script src="assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
<script src="assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript" ></script>
<!-- END CORE PLUGINS -->
<script type="text/javascript" src="assets/plugins/select2/select2.min.js"></script>
<%--ztree--%>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="app/lib/ztree/js/jquery.ztree.exedit.js"></script>
<%--end--%>
<script type="text/javascript" src="assets/plugins/gritter/js/jquery.gritter.js"></script>
<script type="text/javascript" src="assets/plugins/jquery.pulsate.min.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-bootpag/jquery.bootpag.min.js"></script>
<script type="text/javascript" src="assets/plugins/holder.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-fileupload/bootstrap-fileupload.js"></script>
<%--jquery-validation--%>
<script type="text/javascript" src="assets/plugins/jquery-validation/dist/jquery.validate.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-validation/dist/additional-methods.min.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-wysihtml5/wysihtml5-0.3.0.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-wysihtml5/bootstrap-wysihtml5.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-daterangepicker/moment.min.js"></script>
<script type="text/javascript" src="assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script type="text/javascript" src="assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script>
<script src="assets/plugins/jquery-tags-input/jquery.tagsinput.min.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript" ></script>
<script src="app/lib/summernote/summernote.js"></script>
<script src="app/lib/summernote/lang/summernote-zh-CN.js"></script>
<%--end--%>
<%--table--%>
<script type="text/javascript" src="assets/plugins/data-tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="assets/plugins/data-tables/DT_bootstrap.js"></script>
<%--end--%>
<%--bootstrap-modal--%>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript" ></script>
<script src="assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript" ></script>
<%--end--%>
<%--bootbox--%>
<%--end--%>

<%--index JS--%>
<script src="assets/scripts/app.js"></script>
<script src="app/js/index.js" type="text/javascript" ></script>
<%--end--%>
<script>
    jQuery(document).ready(function() {
        $('.page-sidebar .ajaxify.start').click() // load the content for the dashboard page.

        if(${not empty topicNames}){
            setTimeout(function () {
                var unique_id = $.gritter.add({
                    title: '课题中间检查报告提醒!',
                    text: '您有课题${topicNames}需要填写中间检查报告，请及时填写！',
                    sticky: true,
                    time: '',
                    class_name: 'my-sticky-class'
                });

                setTimeout(function () {
                    $.gritter.remove(unique_id, {
                        fade: true,
                        speed: 'slow'
                    });
                }, 12000);
            }, 2000);
        }

    });

    function toPersonCenterPage(){

        $.get("DININGSYS/personalCenter/index",function (data) {
            $(".page-content-body").html(data);
        })
    }
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>