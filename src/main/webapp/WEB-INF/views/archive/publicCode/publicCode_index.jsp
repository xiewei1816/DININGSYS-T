<%--
  Created by zhshuo.
  Date: 2016-09-28
  Time: 17:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="${ctx }/assets/css/font-awesome.min93e3.css" />
    <link rel="stylesheet" href="${ctx }/assets/css/animate.min.css" />
    <link rel="stylesheet" href="${ctx }/assets/scripts/jqgrid/ui.jqgridffe4.css" />
    <link rel="stylesheet" href="${ctx }/assets/css/bootstrap.min14ed.css?v=3.3.6" />
    <link rel="stylesheet" href="${ctx }/assets/css/chosen.css" />
    <link rel="stylesheet" href="${ctx }/assets/css/ls_form.css" />
    <link rel="stylesheet" href="${ctx }/assets/scripts/layer/skin/layer.css" />
    <link rel="stylesheet" href="${ctx }/assets/scripts/jedate/skin/jedate.css" />
</head>
<body>
<div class="leftmenus"></div>
<div  class="wrapper animated fadeInUp menuright">
	<div class="btn-toolbar">
	    <form id="queryForm">
	        <input type="hidden" name="ilocalsno" id="ilocalsno">
	    </form>
	</div>
    <div class="jqGrid_wrapper">
        <table id="grid-table"></table>
        <div id="grid-pager"></div>
    </div>
</div>
<div class="menus-form">
	<form class="editForm" id="classForm" style="display: none;">
		<ul>
			<li>
   				<span class="title">编号</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_number" editype="val" name="number" dete="val" id="men_number"/>
				</div>
   			</li>
   			<li>
   				<span class="title">名称</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_name" editype="val" name="name" dete="val" id="men_name"/>
				</div>
   			</li>
   			<li>
   				<span class="title">助记符</span>
				<div class="form-group bigest-wid">
					<input type="text" class="form-control edit_mnemonic" editype="val" name="mnemonic" id="men_mnemonic"/>
				</div>
   			</li>
   			<li>
   				<span class="title">目录描述 </span>
				<div class="form-group bigest-wid">
					<textarea class="form-control edit_explains" name="explains" editype="val" id="men_explains"></textarea>
				</div>
   			</li>
		</ul>
	</form>
</div>

<script type="text/javascript" src="${ctx }/assets/scripts/jquery-2.1.1.js" ></script>
<script type="text/javascript" src="${ctx }/assets/scripts/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/i18n/grid.locale-cnffe4.js"></script>
<script type="text/javascript" src="${ctx }/assets/scripts/jqgrid/jquery.jqGrid.minffe4.js"></script>
<script type="text/javascript" src="${ctx }/assets/scripts/chosen.proto.js" ></script>
<script type="text/javascript" src="${ctx }/assets/scripts/chosen.jquery.js" ></script>
<script type="text/javascript" src="${ctx }/assets/scripts/layer/layer.js" ></script>
<script type="text/javascript" src="${ctx }/assets/scripts/jedate/jedate.min.js" ></script>
<script type="text/javascript" src="${ctx }/assets/scripts/yqsh-ls.js" ></script>
<script type="text/javascript" src="${ctx }/assets/scripts/ls-tree.js" ></script>
<script>
    $(function(){
    	pageUtil.pageInit();
        $(".leftmenus").getLSTree({
			url : "${ctx}/DININGSYS/archive/publicCode/getAllDataTree",  		 				//通过url获取获取
			nodeIdName : "cgpcid",
            nodePidName : "cparent",
            nodeTitleName : "cname",
            dynamicHei : 40,											 				//动态高度（dynamicHei是被减数为：$(window).height() - dynamicHei）
			allowOperSeries : [1],									 	 				//限制可以编辑的层级（数组结构）
			rootNodeName : "公共代码",									 				//树形结构的标题值
			allowClickSeries : [1],									     				//限制树形结构中可以点击的节点的层级（数组结构，与nodeclick事件相关）
			formId : "classForm",										 				//数据编辑的form表单id值
			delIdName : "editIds",										 				//数据删除操作传入删除结构的字段名称（默认为nodeIdName的值）
			allowClickSeries : [1],									     				//限制树形结构中可以点击的节点的层级（数组结构，与nodeclick事件相关）
			saveUrl : "${ctx}/DININGSYS/consumptionArea/saveConsumptionArea",   		//编辑功能后台保存数据url地址（新增，修改同一地址，修改保存时会传入修改的id值）
			getNodeUrl : "${ctx}/DININGSYS/consumptionArea/getConsumptionAreaByID",		//编辑功能中获取节点完整数据的url地址（点击修改按钮时执行）
			delNodeUrl : "${ctx}/DININGSYS/consumptionArea/deleteConsumptionArea",		//编辑功能中删除节点执行的url地址
			refresh : function(){										 				//点击刷新按钮刷新数据之后执行的方法
				$("#qur_consArea").val("");
				$("#qur_consArea").siblings(":hidden").val(0);
				$(".query-pan .search-btns").trigger("click");
			},
			nodeclick : function(dom,nodeData){							 				//点击树形结构中所有节点执行方法（传入nodeData,该节点的数据）
				$("#ilocalsno").val(nodeData.ilocalsno);
                $("#grid-table").jqGrid('setGridParam',{page:1,postData:changeJOSNr("queryForm")});
                $("#grid-table").trigger("reloadGrid");
			},	
			leftHide : function(){										 				//当点击隐藏按钮执行的方法
				$(".menuright").animate({marginLeft:70},300);
			},
			leftShow : function(){
				$(".menuright").animate({marginLeft:230},300);
			}
		});
        
        $("#" + pageUtil.tabId).getJqGrid({
            url : "${ctx}/DININGSYS/archive/publicCode/getPageList",
            colM : "ilocalsno,cname,parentName,gender",
            queryForm : "queryForm",
            colNames : "代码,名称,上级代码类型,组织机构名称",
            colWid : {"id":40,"username":140,"create_time":140},
            checkbox : false,
            footerrow : true,
            rownumbers : true,
            formatter : {
                "parentName" : function(v){
                    if (v == '' || v == null ) {
                        return '顶级公共代码';
                    } else {
                        return v;
                    }
                },
            },
            loadComplete : function() {
            }
        });
        
        $("#" + pageUtil.tabId).footerData('set',{ilocalsno:123,cname:"测试",parentName:"",gender:"测试"} ,true);
    });
</script>
</body>
</html>
