<%@ page contentType="text/html;charset=UTF-8" language="java" %>

  <!--  <script src="app/js/DININGSYS/deskBusiness/product_item/allow_discount_pan_dish_add.js"></script> -->
<script>
    $(function ($) {
    	disable_time_add.pagerInit();
    	disable_time_add.ztreeInit();
    })
</script>
<div class="left-menu">
	<div class="left-content">
		<div id="disTree" class="extra-t" >
          		<ul id="stree" class="ztree"></ul>
    	</div>
	</div>
	<div class="control" onclick="show_bar()">
          <span class="mid-bar">
          </span>
    </div>
</div>
<div class="right-content">
      <div class="sele">
            <form class="form-horizontal" role="form" id="search-form">
		      	 <div class="form-body">
		      			<div class="form-group">
		                        <label  class="col-md-4 control-label">查询:</label>
		                        <div class="col-md-3" style="display: inline-block;">
		                            <input type="text"  class="form-control" id="search"  value="">
		                        </div>
		                        <div class="col-md-3" style="display: inline-block;">
		  				            <input  type="button"  id="search-button" value="查询" class="input-button" />
		                        </div>
		                 </div>
		           </div>
             </form>
      </div>
   	  <div class="sele">
   			<span>可选内容:</span><span class="len-1">0</span>
   			<a class="sele-all-1">全部选择</a>
   	  </div>
  		  <div class="jqGrid_wrapper jq-margin1">
           <table id="grid-table-4"></table>
        </div>
        <div class="sele">
   			<span>已选内容:</span><span class="len-2">0</span>
   			<a class="sele-all-2">全部取消</a>
   	    </div>
 		 <div class="jqGrid_wrapper jq-margin1">
           <table id="grid-table-5"></table>
        </div>
</div>