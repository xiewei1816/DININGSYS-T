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
<div class="wrapper animated fadeInUp">
    <div class="btn-toolbar">
        <span class="add"><i class="fa fa-file-o"></i>新增</span>
        <span id="update" class="bluebtn"><i class="fa fa-edit"></i>修改</span>
        <span id="delb" class="greenbtn"><i class="fa fa-trash"></i>删除</span>
    </div>
    <div class="jqGrid_wrapper">
        <table id="grid-table"></table>
        <div id="grid-pager"></div>
    </div>
</div>
<div id="editPanel" style="display: none;" title="用户信息">
    <form id="editForm" class="editForm">
        <input type="hidden" name="id" value="" editype="val" class="edit_id">
        <ul class="edit-contents animated fadeInUp">
            <li>
                <span class="title"><span class="required">*</span>用户名</span>
                <div class="form-group biger-wid">
                    <input placeholder="请输入用户名" type="text" class="form-control edit_username" name="username" editype="val" dete="val" />
                </div>
            </li>
            <li>
                <span class="title"><span class="required">*</span>姓名</span>
                <div class="form-group bigest-wid">
                    <input type="text" class="form-control edit_nickname" name="nickname" editype="val" dete="val"  placeholder="请输入姓名"/>
                </div>
            </li>
            <li>
                <span class="title"><span class="required">*</span>性别</span>
                <div class="form-group bigest-wid">
                    <label class="radiobtn"><input type="radio" value="男" class="edit_gender"
                                                   name="gender" editype="radio" /><i></i><span></span>男</label>
                    <label class="radiobtn"><input type="radio" value="女" class="edit_gender"
                                                   name="gender" editype="radio" /><i></i><span></span>女</label>
                </div>
            </li>
            <li>
                <span class="title"><span class="required">*</span>联系方式</span>
                <div class="form-group bigest-wid">
                    <input type="text" class="form-control edit_phone" name="phone" editype="val" dete="val"  placeholder="请输入联系方式"/>
                </div>
            </li>
            <li>
                <span class="title">email</span>
                <div class="form-group bigest-wid">
                    <input type="text" class="form-control edit_email" name="email" editype="val" placeholder="请输入email"/>
                </div>
            </li>
            <li>
                <span class="title">职位</span>
                <div class="form-group bigest-wid">
                    <input type="text" class="form-control edit_job" name="job" editype="val" placeholder="请输入职位"/>
                </div>
            </li>
            <li>
                <span class="title">是否为领导</span>
                <div class="form-group bigest-wid">
                    <label class="radiobtn"><input type="radio" value="0" class="edit_isLeader"
                                                   name="is_leader" editype="radio" /><i></i><span></span>否</label>
                    <label class="radiobtn"><input type="radio" value="1" class="edit_isLeader"
                                                   name="is_leader" editype="radio" /><i></i><span></span>是</label>
                </div>
            </li>
            <li>
                <span class="title">状态</span>
                <div class="form-group bigest-wid">
                    <label class="radiobtn"><input type="radio" value="normal" class="edit_state"
                                                   name="state" editype="radio" /><i></i><span></span>正常</label>
                    <label class="radiobtn"><input type="radio" value="locked" class="edit_state"
                                                   name="state" editype="radio" /><i></i><span></span>锁定</label>
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
<script>
    $(function(){
        pageUtil.pageInit({
            initFormVals : function(){  											//编辑界面的初始化界面里的数据方法。
            }
        });

        $(".edit_username").blur(function(){
            checkName();
        });

        $(".add").click(function(){
            pageUtil.addOper({
                addBefore : function(){ 											//添加数据界面之前执行的方法(非必要)。
                },
                saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
                    return checkName();
                },
                saveUrl : "${ctx}/DININGSYS/user/saveUser", 						//添加保存的地址(必要)。
                saveSuccess : function(resultData){  								//保存数据成功之后执行的方法(非必要)。
                    if (resultData.success == 'OK') {
                        layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                    } else {
                        layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
                    }
                }
            });
        });

        $("#update").click(function(){
            pageUtil.updOper({
                updBefore : function(){												//修改数据界面之前执行的方法(非必要)。

                },
                updUrl : "${ctx}/DININGSYS/user/getUserById",									//获取修改数据的后台地址(必要)。
                updSuccess : function(data){										//成功获取修改数据之后执行的方法，默认传入后台返回的数据(必要)。
                    pageUtil.getUpdingData(data.user);                            	//自动给编辑界面里填值。
                },
                saveBefore : function(){ 											//保存数据之前执行的方法(非必要)。
                    return checkName();
                },
                saveUrl : "${ctx}/DININGSYS/user/saveUser", 									//添加保存的地址(必要)。
                saveSuccess : function(resultData){  							    //保存数据成功之后执行的方法(非必要)。
                    if (resultData.success == 'OK') {
                        layer.alert('数据处理成功！',{title :'提示',icon: 1, skin: 'layer-ext-moon'});
                    } else {
                        layer.alert( data.error,{title :'错误',icon: 2, skin: 'layer-ext-moon'});
                    }
                }
            });
        });

        $("#delb").click(function(){
            pageUtil.delOper({
                before : function(){												//删除数据界面之前执行的方法(非必要)。
                },
                url : "${ctx}/DININGSYS/user/deleteUser",										//删除数据提交的后台地址(必要)。
                success : function(){												//删除数据成功之后执行的方法(非必要)。

                }
            });
        });

        $("#" + pageUtil.tabId).getJqGrid({
            url : "${ctx}/DININGSYS/user/getPageList",
            colM : "username,nickname,state,gender,createTime,email,userRoleIDs",
            queryForm : "queryForm",
            colNames : "用户名,姓名,状态,性别,创建时间,Email,角色",
            colWid : {"id":40,"username":140,"create_time":140},
            formatter : {
                "state" : function(v){
                    if (v == 'normal') {
                        return '<font color="green">正常</font>';
                    } else {
                        return '<font color="red">锁定</font>';
                    }
                },
                "gender" : function(v){
                    if (v == '男') {
                        return '<font color="green">男</font>';
                    } else {
                        return '<font color="red">女</font>';
                    }
                }
            },
            loadComplete : function() {
            }
        });
    });
    function checkName(){
        var istrue = true;
        var id = $(".edit_id").val();
        $.ajax({
            url : "${ctx}/DININGSYS/user/checkUser",
            data : {"id": id == "" ? 0 : id,username:$(".edit_username").val()},
            dataType : "json",
            type : "post",
            async : false,
            success : function(d){
                if(d.state == 2){
                    layer.alert('你提交的用户名已经存在，请重新填写！',{title :'提示',icon: 0, skin: 'layer-ext-moon'});
                    istrue = false;
                }
            }
        });
        return istrue;
    }
</script>
</body>
</html>
