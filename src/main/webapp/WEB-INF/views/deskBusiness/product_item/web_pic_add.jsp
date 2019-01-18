<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>广告图片</title>
</head>
<body>
<script type="text/javascript">
    // 添加全局站点信息
    var BASE_URL = '/webuploader';
    var ctx = "${ctx}";
    var count = "${count}";
    var ctype = "${type}";
</script>
<script type="text/javascript">
$(function () {
	window.setTimeout(function(){
		$("#filePicker div:not(.webuploader-pick)").css({height:"44px",width:"183px",left:"312px"});
	}, 500);
	createUpload(ctx,2,ctype);	
});
</script>
<div class="btn-toolbar">
</div>
<div class='wu-example2' id='uploader'>
  <div class='queueList'>
    <div class='placeholder' id='dndArea'>
      <div id='filePicker' ></div>
          <p>或将照片拖到这里，单次最多可选10张</p></div>
          </div><div class='statusBar' style='display: none;'>
            <div class='progress'><span class='text'>0%</span>
            <span class='percentage'></span></div>
            <div class='info'></div><div class='btns' > 
         <div id='filePicker2'></div>
         <div class='uploadBtn'>开始上传</div>
    </div>
  </div>
</div>
</body>