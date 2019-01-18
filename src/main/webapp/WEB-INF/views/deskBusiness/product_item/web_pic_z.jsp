<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="assets/scripts/webuploader/aceCss/font-awesome.min.css" />
<link rel="stylesheet" href="assets/scripts/webuploader/aceCss/colorbox.css" />
<link rel="stylesheet" href="assets/scripts/webuploader/aceCss/ace-fonts.css" />
<link rel="stylesheet" href="assets/scripts/webuploader/aceCss/ace.min.css" />
<link rel="stylesheet" href="assets/scripts/webuploader/aceCss/ace-rtl.min.css" />
<link rel="stylesheet" href="assets/scripts/webuploader/aceCss/ace-skins.min.css" />
<link rel="stylesheet" href="assets/scripts/webuploader/aceCss/ace-ie.min.css" />
<link href="assets/scripts/webuploader/css/webuploader.css" rel="stylesheet" type="text/css">
<link href="assets/scripts/webuploader/css/demo.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    // 添加全局站点信息
    var BASE_URL = '/webuploader';
    var ctx = "${ctx}";
    var count = "${count}";
    var ctype = "${type}";
</script>
<script type="text/javascript">
$(function () {
	if(count == 0)
	{
		window.setTimeout(function(){
			$("#filePicker div:not(.webuploader-pick)").css({height:"44px",width:"183px",left:"312px"});
		}, 500);
		createUpload(ctx,1,ctype);	
	}
	else
	{
		initGarrly(ctype);
	}
});
</script>
<div class="btn-toolbar">
</div>
<c:if test="${count != 0}">
<div class="row-fluid">
	<ul class="ace-thumbnails" id="ace-thumbnails-z">
		<c:forEach items="${pic}" var="item">
			<li data="${item.picNames}"><a href="${ctx}/advertpic/${item.picNames}" title='Photo Title' data-rel='colorbox'><img style='max-height: 100px;max-width: 100px;' src="${ctx}/advertpic/${item.picNames}" /></a><div class='tools'><a href='#' class='gallery-li'><i class='icon-remove red'></i></a></div></li>
		</c:forEach>
	</ul>
</div>
</c:if>

<c:if test="${count == 0}">
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
</c:if>