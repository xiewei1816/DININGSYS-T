<%--
  Created by IntelliJ IDEA.
  User: 35079_000
  Date: 2016-07-21
  Time: 15:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="app/lib/treeTable/css/screen.css" media="screen" />
<link rel="stylesheet" href="app/lib/treeTable/css/jquery.treetable.css" />
<link rel="stylesheet" href="app/lib/treeTable/css/jquery.treetable.theme.default.css" />
<script src="app/lib/treeTable/js/jquery.treetable.js"></script>
<script src="app/js/DININGSYS/system/organ/sys_organ.js"></script>
<script src="app/js/DININGSYS/system/organ/organ_validation.js"></script>
<div class="portlet box">
    <div class="portlet-body">
        <div class="table-toolbar">
            <div class="clearfix">
                <a href="javascript:void(0)" class="btn green sysOrganAdd" data-toggle="modal">新增 <i class="fa fa-plus"></i></a>
                <a href="javascript:void(0)" clickType="a" class="btn purple sysOrganEdit">修改 <i class="fa fa-edit"></i></a>
                <a href="javascript:void(0)" clickType="a" class="btn red sysOrganDel"><i class="fa fa-times"></i> 删除</a>
            </div>
        </div>
        <div class="scroller"  data-always-visible="1" data-rail-visible="0">
            <div class="main" id="indexTableID">

            </div>
        </div>
        <div id="sysorgan-ajax-modal" class="modal fade" tabindex="-1"></div>
    </div>
</div>

<script>


    $(document).ready(function() {
        sysOrgan.getIndexTableData();
        sysOrgan.initAddOrEditModal();
    });
</script>
