<%--
  Created by mrren.
  Date: 2016-08-17
  Time: 16:00
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    $(function () {
        $("#systemSettingSave").on('click',function(){
            $.ajax({
                type:'POST',
                url:'DININGSYS/systemSetting/updateData',
                data:$("#systemSettingInfo").serialize(),
                dataType:'JSON',
                success:function (data) {
                    if(data.success){
                        showAjaxMsg("修改成功，请刷新登录系统查看！")
                    }else{
                        showAjaxMsg("操作失败！")
                    }
                }
            })
        })
    })
</script>
<div class="row profile-account">
    <div class="col-md-3">
        <ul class="ver-inline-menu tabbable margin-bottom-10">
            <li class="active">
                <a data-toggle="tab" href="#tab_1-1">
                    <i class="fa fa-cog"></i>
                    系统基本设置
                </a>
                <span class="after"></span>
            </li>
        </ul>
    </div>
    <div class="col-md-9">
        <div class="tab-content">
            <div id="tab_1-1" class="tab-pane active">
                <form role="form" id="systemSettingInfo">
                    <c:forEach items="${data}" var="map">
                        <c:choose>
                            <c:when test="${map.settingCode == 'name'}">
                                <div class="form-group">
                                    <label class="control-label">系统名称</label>
                                    <input type="text" name="name" placeholder="${map.settingValue}"
                                           class="form-control"/>
                                </div>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    <div class="margiv-top-10">
                        <a href="javascript:void(0)" class="btn green" id="systemSettingSave">保存</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>