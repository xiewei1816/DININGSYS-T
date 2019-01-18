<%--
  Created by zhshuo.
  Date: 2016-11-07
  Time: 9:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String single = (String)request.getSession().getAttribute("single");
	request.setAttribute("single", single);
%>
<script>
    $(document).ready(function () {
        busi_per.saveInfoBtnInit();
        busi_per.chexBoxBtnInit();
    })
</script>
<form class="form-horizontal"  id="normalForm" style="width: 95%">
    <input type="hidden" name="zwCode" value="${zwCode}"> <%--职务ID--%>
    <input type="hidden" id="generalOverViewId"  name="overViewId" value="${sysPerOverview.id}"> <%--业务权限overview表ID--%>
    <input type="hidden" name="id" value="${sysPerBusiness.id}"> <%--业务常规权限ID--%>
    <h4>结算方式权限</h4>
    <hr/>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input name="yqQx" type="checkbox" value="1" <c:if test="${sysPerBusiness.yqQx == 1}">checked</c:if> > 宴请
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input name="pdPx"  type="checkbox" value="1" <c:if test="${sysPerBusiness.pdPx == 1}">checked</c:if>> 跑单
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xykQx" <c:if test="${sysPerBusiness.xykQx == 1}">checked</c:if>> 信用卡
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="zpQx" <c:if test="${sysPerBusiness.zpQx == 1}">checked</c:if>> 支票
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="qQx" <c:if test="${sysPerBusiness.qQx == 1}">checked</c:if>> 券
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="hygzQx" <c:if test="${sysPerBusiness.hygzQx == 1}">checked</c:if>> 会员挂账
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <%--<div class="col-md-2"><!-- 暂时不需要-->
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="fhygzQx" <c:if test="${sysPerBusiness.fhygzQx == 1}">checked</c:if> > 非会员挂账
                </label>
            </div>
        </div>--%>
        <%--<div class="col-md-2"><!-- 暂时不需要-->
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="zwzQx" <c:if test="${sysPerBusiness.zwzQx == 1}">checked</c:if> > 转外账
                </label>
            </div>
        </div>--%>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="qtOneQx" <c:if test="${sysPerBusiness.qtOneQx == 1}">checked</c:if>> 微信支付
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="qtTwoQx" <c:if test="${sysPerBusiness.qtTwoQx == 1}">checked</c:if>> 支付宝支付
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="wbjsfs" <c:if test="${sysPerBusiness.wbjsfs == 1}">checked</c:if>> 外部结算方式
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="hykQx" <c:if test="${sysPerBusiness.hykQx == 1}">checked</c:if>> 会员卡
                </label>
            </div>
        </div>
    </div>
    <%--<div class="row"> <!-- 暂时不需要-->
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="scewmzfQx"  <c:if test="${sysPerBusiness.scewmzfQx == 1}">checked</c:if>> 删除二维码支付
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ewmzfhxQx" <c:if test="${sysPerBusiness.ewmzfhxQx == 1}">checked</c:if>> 二维码支付核销
                </label>
            </div>
        </div>
    </div>--%>
    <br>
    <h4>修改权限</h4>
    <hr/>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ghkwQx"  <c:if test="${sysPerBusiness.ghkwQx == 1}">checked</c:if>> 更换客位
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ggrsQx" <c:if test="${sysPerBusiness.ggrsQx == 1}">checked</c:if>> 更改人数
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="jsrsQx"  <c:if test="${sysPerBusiness.jsrsQx == 1}">checked</c:if>> 减少人数
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ghkzQx" <c:if test="${sysPerBusiness.ghkzQx == 1}">checked</c:if>> 更换客座
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xgthtcQx" <c:if test="${sysPerBusiness.xgthtcQx == 1}">checked</c:if>> 修改宴会套餐
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xgslQx" <c:if test="${sysPerBusiness.xgslQx == 1}">checked</c:if>> 修改数量
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="dpztQx" <c:if test="${sysPerBusiness.dpztQx == 1}">checked</c:if>> 单品转台
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="qyhydzQx" <c:if test="${sysPerBusiness.qyhydzQx == 1}">checked</c:if>> 启用会员卡打折
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="zzQx" <c:if test="${sysPerBusiness.zzQx == 1}">checked</c:if>> 转账
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="cxzzQx" <c:if test="${sysPerBusiness.cxzzQx == 1}">checked</c:if>> 撤销转账
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="czQx"  <c:if test="${sysPerBusiness.czQx == 1}">checked</c:if>> 拆账
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="jrtdQx" <c:if test="${sysPerBusiness.jrtdQx == 1}">checked</c:if>> 加入团队
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="tctdQx" <c:if test="${sysPerBusiness.tctdQx == 1}">checked</c:if>> 退出团队
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="cxmdQx" <c:if test="${sysPerBusiness.cxmdQx == 1}">checked</c:if> > 撤销埋单
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="fwjsQx" <c:if test="${sysPerBusiness.fwjsQx == 1}">checked</c:if> > 返位结算
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="fkxzQx" <c:if test="${sysPerBusiness.fkxzQx == 1}">checked</c:if>> 付款修正
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="bkfpQx" <c:if test="${sysPerBusiness.bkfpQx == 1}">checked</c:if> > 补开发票
                </label>
            </div>
        </div>
        <%--<div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xgyjzdQx" <c:if test="${sysPerBusiness.xgyjzdQx == 1}">checked</c:if> > 修改已结账单
                </label>
            </div>
        </div>--%>
    </div>
    <div class="row">
        <!-- <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="yxslwfQx" <c:if test="${sysPerBusiness.yxslwfQx == 1}">checked</c:if> > 加单时，允许数量为负
                </label>
            </div>
        </div> -->
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="qxhcQx" <c:if test="${sysPerBusiness.qxhcQx == 1}">checked</c:if> > 取消划菜
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="dfpkssbdsjQx" <c:if test="${sysPerBusiness.dfpkssbdsjQx == 1}">checked</c:if> > 导发票库时删本地数据
                </label>
            </div>
        </div>
        <!--  <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="jdxglspxQx" <c:if test="${sysPerBusiness.jdxglspxQx == 1}">checked</c:if> > 加单修改临时品项
                </label>
            </div>
        </div>-->
    </div>
    <br>
    <h4>其他权限</h4>
    <hr>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="btpdQx" <c:if test="${sysPerBusiness.btpdQx == 1}">checked</c:if> > 吧台盘点
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="jsQx" <c:if test="${sysPerBusiness.jsQx == 1}">checked</c:if> > 解锁
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ysqQx"  <c:if test="${sysPerBusiness.ysqQx == 1}">checked</c:if>  > 预授权
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ckdqyyqkQx"   <c:if test="${sysPerBusiness.ckdqyyqkQx == 1}">checked</c:if>  > 查看当前营业情况
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="yjdjQx" <c:if test="${sysPerBusiness.yjdjQx == 1}">checked</c:if> > 押金等级
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="pxxlQx"  <c:if test="${sysPerBusiness.pxxlQx == 1}">checked</c:if> > 品项沽清/限量
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="qhfpkQx"  <c:if test="${sysPerBusiness.qhfpkQx == 1}">checked</c:if> > 切换发票库
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ghcmpposQx"  <c:if test="${sysPerBusiness.ghcmpposQx == 1}">checked</c:if>  > 更换触摸屏POS
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="jdbcdQx" <c:if test="${sysPerBusiness.jdbcdQx == 1}">checked</c:if> > 加单不厨打
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="tdbcdQx" <c:if test="${sysPerBusiness.tdbcdQx == 1}">checked</c:if> > 退单不厨打
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="tdQx" <c:if test="${sysPerBusiness.tdQx == 1}">checked</c:if> > 退单并厨打
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="tcxtQx" <c:if test="${sysPerBusiness.tcxtQx == 1}">checked</c:if> > 退出系统
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="yxjdQx" <c:if test="${sysPerBusiness.yxjdQx == 1}">checked</c:if> > 加单并厨打
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="jbQx" <c:if test="${sysPerBusiness.jbQx == 1}">checked</c:if> > 结班
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="yhcpxdtdQx" <c:if test="${sysPerBusiness.yhcpxdtdQx == 1}">checked</c:if> > 已划菜品项的退单
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="xsqtzkdhhmQx" <c:if test="${sysPerBusiness.xsqtzkdhhmQx == 1}">checked</c:if> > 显示前台咨客电话号码
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="zxydQx" <c:if test="${sysPerBusiness.zxydQx == 1}">checked</c:if> > 执行预定
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="sdzsyddQx" <c:if test="${sysPerBusiness.sdzsyddQx == 1}">checked</c:if> > 开台时，锁定执行预订单
                </label>
            </div>
        </div>
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="lxyztgqQx" <c:if test="${sysPerBusiness.lxyztgqQx == 1}">checked</c:if> > 离线验证团购券
                </label>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="spQx" <c:if test="${sysPerBusiness.spQx == 1}">checked</c:if> > 锁屏
                </label>
            </div>
        </div>
    </div>
    <br>
    <hr>
    <div class="row">
        <div class="col-md-6">
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="state" <c:if test="${sysPerOverview.state == 1}">checked</c:if> > 停用当前职务的业务操作权限
                </label>
            </div>
        </div>
        <c:if test="${single == '1'}">
	        <div class="col-md-6" style="text-align: right;padding-right: 70px">
	            <button type="button" class="btn btn-primary checkBoxAll" id="generalAll">全部选择</button>
	            <button type="button" class="btn btn-warning checkBoxCancel" id="generalCancel">全部取消</button>
	            <button type="button" class="btn btn-success saveInfo" id="generalSave">保存信息</button>
	        </div>
        </c:if>
    </div>
</form>