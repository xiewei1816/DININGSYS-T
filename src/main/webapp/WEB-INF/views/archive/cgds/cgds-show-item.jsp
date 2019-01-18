<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript">
	var path = "${ctx}";
</script>
<link rel="stylesheet" href="assets/scripts/ztree/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.all.min.js" ></script>
<script type="text/javascript" src="assets/scripts/ztree/js/jquery.ztree.core.js"></script>
<script src="app/js/DININGSYS/archive/cgds/cgds-show-item.js"></script>
<!-- 品项-显示 -->
<div id="menuTree" style="width: 200px; border: 1px solid #DDD; float: left;">
       <ul id="showItemTree" class="ztree" style="width: 200px; height: 434px; overflow: auto;"></ul>
</div>
<div class="right-content" style="float: left;">
   	  <div style="text-align: center;">
   	  		<span>查找:<input type="text" id="show-item-search" placeholder="编号，名称或助记符" onblur="goSearch(this.id)"/></span><br>
   			<span>可选内容:</span><span id="len-1">0</span>
   			<a id="sele-all-1" style="cursor:pointer;">全部选择</a>
   	  </div>
  		  <div class="jqGrid_wrapper jq-margin1">
           <table id="show-item-table1"></table>
        </div>
        <div style="text-align: center;">
   			<span>已选内容:</span><span id="len-2">0</span>
   			<a id="sele-all-2" style="cursor:pointer;">全部取消</a>
   	    </div>
 		 <div class="jqGrid_wrapper jq-margin1">
           <table id="show-item-table2"></table>
        </div>
</div>
