<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>品项排序管理</title>
		<jsp:include page="../../head.jsp"/>
	    <script type="text/javascript" src="app/js/DININGSYS/archive/itemFile/itemFile_rank.js" ></script>
	</head>
	<body>
		<div class="wrapper animated fadeInUp">
		
			<div class="search-wrapper input-groups">
				<form class="query-pan" id="query-pan">
					<ul>
						 <li>
							<div class="form-group bigest-wid">
								<label class="radiobtn"><input type="radio" value="0" class="edit_isItemFlag" name="isItemFlag" 
								editype="radio" onclick="doSelected(this.value)" checked/><i></i><span></span>按品项</label>
								<label class="radiobtn"><input type="radio" value="1" class="edit_isItemFlag" name="isItemFlag" 
								editype="radio" onclick="doSelected(this.value)" /><i></i><span></span>按品项小类</label>
							</div>
		    			</li>
					</ul>
				</form> 
			</div>
			<div id="tab1">
				<div class="wrapper animated fadeInUp">
					<div class="search-wrapper input-groups">
						<form class="query-pan" id="query-pan">
							<ul>
								<li>
									<span class="title">品项小类</span>
									<div class="form-group big-wid">
										<select class="form-control" name="ppxlId" id="ppxlId">
				                            <option selected value="">全部</option>
				                            <c:forEach items="${itemFileSmallTypeList}" var="itemFileSmallType">
				                                <option value="${itemFileSmallType.id}">${itemFileSmallType.name}</option>
				                            </c:forEach>
				                        </select>
									</div>
								</li>
							</ul>
							<div class="search-btns">
								<button id="searchItemFileByPxxlId" class="btn btn-primary">查询</button>
							</div>
						</form> 
					</div>
					<div class="btn-toolbar">
						<span id="rank-item-up" class="graybtn" title="上移"><i class="fa fa-arrow-up"></i>上移</span>
						<span id="rank-item-down" class="royalbtn" title="下移"><i class="fa fa-arrow-down"></i>下移</span>
						<span id="rank-item-topper" class="graybtn" title="置顶"><i class="fa fa-arrow-up"></i>置顶</span>
					</div>
			        <div id="beItem" class="jqGrid_wrapper">
		                <table id="rank-item-table"></table>
		            </div>
		        </div>
	        </div>
	        
	        <div id="tab2" style="display: none;">
				<div class="btn-toolbar">
					<span id="rank-smallitem-up" class="graybtn" title="上移"><i class="fa fa-arrow-up"></i>上移</span>
					<span id="rank-smallitem-down" class="royalbtn" title="下移"><i class="fa fa-arrow-down"></i>下移</span>
					<span id="rank-smallitem-topper" class="graybtn" title="置顶"><i class="fa fa-arrow-up"></i>置顶</span>
				</div>
		        <div id="beItem" class="jqGrid_wrapper">
	                <table id="rank-smallitem-table"></table>
	            </div>
	        </div>
	        
	    </div>
	</body>
</html>
