<%--
  Created by zhshuo.
  Date: 2016-10-14
  Time: 13:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="app/js/DININGSYS/archive/itemFile/itemFileType_validation.js"></script>
<script type="text/javascript">
    $(function(){
        $("input:checkbox[name='qyzzfflbxd']").click(function () {
            if($(this).is(":checked")){
                $("#pxxlTrCheckBox").find("input:checkbox").each(function () {
                    $(this).removeAttr("disabled");
                })
            }else{
                $("#pxxlTrCheckBox").find("input:checkbox").each(function () {
                    if($(this).is(":checked")){
                        $(this).attr("checked",false);
                    }
                    $(this).attr("disabled","disabled");
                })
            }
        })

        var flag = $("input:checkbox[name='qyzzfflbxd']").is(":checked");

        if(!flag){
            $("#pxxlTrCheckBox").find("input:checkbox").each(function () {
                $(this).attr("disabled","disabled");
            })
        }

        itemFileTypeValidation.bigFormInit();
        itemFileTypeValidation.smallFormInit();
    });
</script>
<c:if test="${flag eq 1}">
    <div>
    <form id="itemFileBigTypeForm" class="editForm">
        <input type="hidden" name="id" value="${dgItemFileType.id}">
        <ul class="edit-contents animated fadeInUp">
            <li>
                <span class="title"><span class="required">*</span>编号</span>
                <div class="form-group biger-wid">
                    <input readonly type="text" class="form-control" name="num" value="${dgItemFileType.num}"/>
                </div>
            </li>
            <li>
                <span class="title"><span class="required">*</span>名称</span>
                <div class="form-group biger-wid">
                    <input placeholder="请输入名称" type="text" class="form-control" name="name" value="${dgItemFileType.name}"/>
                </div>
            </li>
            <li>
                <span class="title">助记符</span>
                <div class="form-group bigest-wid">
                    <input type="text" class="form-control" name="zjf" value="${dgItemFileType.zjf}"/>
                </div>
            </li>
            <li>
                <span class="title">品牌</span>
                <div class="form-group bigest-wid">
                    <select class="form-control" name="pp">
                        <c:forEach items="${pp}" var="pp">
                            <option value="${pp.cCode}" <c:if test="${dgItemFileType.pp == pp.cCode}">selected</c:if> >${pp.cName}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li>
                <span class="title">品项类型</span>
                <div class="form-group bigest-wid">
                    <select class="form-control" name="couponCode">
                        <c:forEach items="${pxlxgl}" var="pxlxgl">
                            <option value="${pxlxgl.cKeyWD}" <c:if test="${dgItemFileType.couponCode == pxlxgl.cKeyWD}">selected</c:if> >${pxlxgl.cName}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li>
                <span class="title">说明</span>
                <div class="form-group bigest-wid">
                    <textarea name="description" cols="2" rows="2" class="form-control" placeholder="请输入说明" value="${dgItemFileType.description}"/>
                </div>
            </li>
        </ul>
    </form>
</div>
</c:if>
<c:if test="${flag eq 2}">
<div>
    <form id="itemFileSmallTypeForm" class="editForm">
        <input type="hidden" name="id" value="${dgItemFileType.id}">
        <ul class="edit-contents animated fadeInUp">
            <li>
                <span class="title"><span class="required">*</span>编号</span>
                <div class="form-group biger-wid">
                    <input readonly type="text" class="form-control" name="num" value="${dgItemFileType.num}"/>
                </div>
            </li>
            <li>
                <span class="title"><span class="required">*</span>名称</span>
                <div class="form-group biger-wid">
                    <input placeholder="请输入名称" type="text" class="form-control" name="name" value="${dgItemFileType.name}"/>
                </div>
            </li>
            <li>
                <span class="title"><span class="required">*</span>所属品项大类</span>
                <div class="form-group biger-wid">
                    <select name="pId" class="form-control">
                        <c:forEach items="${dgItemFileBigTypes}" var="pxdl">
                            <option value="${pxdl.id}" <c:if test="${nodeParentId == pxdl.id}">selected</c:if>>${pxdl.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li>
                <span class="title">默认上菜状态</span>
                <div class="form-group biger-wid">
                    <select name="defaultSczt" class="form-control">
                        <c:forEach items="${sczt}" var="sczt">
                            <option value="${sczt.cCode}" <c:if test="${dgItemFileType.defaultSczt == pxlxgl.cCode}">selected</c:if> >${sczt.cName}</option>
                        </c:forEach>
                    </select>
                </div>
            </li>
            <li>
                <span class="title">助记符</span>
                <div class="form-group bigest-wid">
                    <input type="text" class="form-control" name="zjf" value="${dgItemFileType.zjf}"/>
                </div>
            </li>
            <li>
                <div class="form-group">
					 <div class="checkbox">
	                      <label>
	                           <input type="checkbox" value="1" name="yxe" <c:if test="${dgItemFileType.yxe == 1}">checked="checked"</c:if> > 是否在平板上显示
	                      </label>
	                 </div>
	            </div>
            </li>
            <li>
                <span class="title">说明</span>
                <div class="form-group">
                    <textarea name="description" cols="2" rows="2" class="form-control" placeholder="请输入说明">${dgItemFileType.description}</textarea>
                </div>
            </li>
            <li>
                <div class="form-group" style="height: 200px">
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="qyzzfflbxd" value="1"  <c:if test="${qyz == 1}">checked="checked"</c:if>  id="qyzzfflbxd"/>启用制作方法类别限定
                        </label>
                    </div>
                    <input type="hidden" name="qyzzfflbids" id="qyzzfflbids"/>
                    <div style="height: 150px;overflow-y: auto">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th></th>
                                <th>制作方法类别</th>
                            </tr>
                            </thead>
                            <tbody id="pxxlTrCheckBox">
                                <c:forEach items="${dgProMethodsTypes}" var="methods">
                                    <tr>
                                        <td>
                                            <input type="checkbox" id="dg${methods.id}" value="${methods.id}"/>
                                        </td>
                                        <td>
                                            ${methods.pmtname}
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <script type="text/javascript">
                                var qids = '${qids}'; //防止没有数据而JS报错
                                if(qids && qids != null){
                                    var ids = qids.split(",");
                                    for(var i = 0;i<ids.length;i++){
                                        var qid = ids[i];
                                        $("#dg"+qid).attr("checked",'checked');
                                    }
                                }
                            </script>
                        </table>
                    </div>
                </div>
            </li>
        </ul>
    </form>
</div>
</c:if>