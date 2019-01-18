<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
$(function () {
	ydSeats.pagerInit();
});
</script>
<div class="wrapper animated fadeInUp" id="seatDialog">
	<div class="search-wrapper input-groups">
		  <form class="query-pan" id="query-pan-seat">
             <ul>
               <li>
                   <span class="title">输入客位编码/名称:</span>
                   <div class="form-group big-wid">
                       <input type="text" class="form-control" name="search" id="searchSeat" placeholder="请输入客位编码/名称"/>
                   </div>
               </li>
        	 </ul>
          <div class="search-btns">
                <button class="btn btn-primary">查询</button>
          </div>
		</form> 
	</div>
    <div class="jqGrid_wrapper">
        <table id="grid-table-seat"></table>
        <div id="grid-pager-seat"></div>
    </div>
</div>
