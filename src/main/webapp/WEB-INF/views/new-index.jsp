<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>${systemName.settingValue}</title>
        <link href="${ctx }/assets/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
        <link href="${ctx }/assets/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">
        <link href="${ctx }/assets/css/animate.min.css" rel="stylesheet">
        <link href="${ctx }/assets/css/ls_main.css" rel="stylesheet">
        <script type="text/javascript">
            var sysCtx = "${ctx}";
        </script>
    </head>
    <body>
        <div class="left-menus">
            <div class="userinfos">
                <div class="sys-logo"></div>
                <span data-toggle="dropdown" class="dropdown-toggle lefthide">${userInfo.empName}<i class="fa fa-sort-desc"></i></span>
                <ul class="dropdown-menu animated fadeInRight m-t-xs">
                    <li class="divider"></li>
                    <li><a href="${ctx }/DININGSYS/logout">安全退出</a>
                    </li>
                </ul>
                <%--<span class="lefthide">${userInfo.job}</span>--%>
            </div>
            <div class="nav">
                <ul class="nav-list">
                    <c:forEach items="${userMenus}" var="menu" begin="0" varStatus="status">
                        <li <c:if test="${not empty menu.child }">shref="${menu.menuUrl}"</c:if>>
                                <label class="lefthide">
                                    <i class="fa ${menu.menuIcon}"></i>
                                ${menu.menuName}
                                <c:if test="${not empty menu.child}"><span class="fa fa-angle-left lefthide"></span>
                                </c:if></label>
                                <c:if test="${not empty menu.child}">
                                <ul>
                                    <c:forEach items="${menu.child}" var="childMenu">
                                        <li shref="${childMenu.menuUrl}">
                                            ${childMenu.menuName}
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </c:forEach>
                </ul>
            </div>
        </div>
        <div class="scrolbar"><i class="fa fa-angle-left"></i></div>
        <div class="right-container">
            <!-- 
                <div class="toolbars">
                        <div class="sys-logo"></div>
                        <div class="tools">
                                <a href="${ctx }/DININGSYS/logout"><span><i class="fa fa-sign-out"></i>退出</span></a>
                        </div>
                </div>
            -->
            <div class="container-tab">
                <span class="left-tab"><i class="fa fa-backward"></i></span>
                <div class="tab-contents">
                    <div>
                        <label>首页</label>
                    </div>
                </div>
                <span class="right-tab"><i class="fa fa-forward"></i></span>
                <span class="close-tabs dropdown" data-toggle="dropdown">
                    操作<i class="fa fa-sort-desc"></i>
                </span>
                <ul role="menu" class="dropdown-menu animated fadeInLeft dropdown-menu-right">
                    <li class="tabShowActive"><a>定位选项卡</a></li>
                    <li class="divider"></li>
                    <li class="tabCloseAll"><a>关闭全部</a></li>
                    <li class="tabCloseOther"><a>关闭其他</a></li>
                </ul>
            </div>
            <div class="content-pages">
                <iframe class="animated fadeInUp"></iframe>
            </div>
        </div>
        <div class="bottom-container">
            2013-2017 &copy; DININGSYS by 一起生活网络成都有限公司.
        </div>
        <script type="text/javascript" src="${ctx }/assets/scripts/jquery-1.9.1.min.js" ></script>
        <script type="text/javascript" src="${ctx }/assets/scripts/slimscroll/jquery.slimscroll.min.js" ></script>
        <script type="text/javascript" src="${ctx }/assets/scripts/bootstrap.min.js" ></script>
        <script type="text/javascript" src="${ctx }/assets/scripts/new-index.js" ></script>
    </body>
</html>

