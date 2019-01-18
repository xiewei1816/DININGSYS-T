package com.yqsh.diningsys.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;

/**
 * 包房服务相关接口
 * 
 * @author yqsh-hs
 */
@RequestMapping("/yqshapi/member")
@Controller
public class APIMemberManagerContrller extends ApiBaseController {

	/**
	 * 获取会员列表<br>
	 *
	 * @param orgs
	 *            客户查询条件(可以是客户名称/手机号码/客户编码)
	 * @return 返回当前营业流水包房费明细
	 */
	@RequestMapping("/getMemberList")
	@ResponseBody
	@ApiOperation(value = "获取会员列表信息", httpMethod = "POST", notes = "获取会员列表")
	public Object getMemberList(
			HttpServletRequest request,
			@ApiParam(required = false, name = "orgs", value = "客户查询条件(可以是客户名称/手机号码/客户编码) ..") @RequestParam(value = "orgs", required = false) String orgs) {
		String memberList = null;
		try {
			Gson gson = new Gson();
			Map ornums = gson.fromJson(orgs,new TypeToken<Map>() {}.getType());
			ornums.put("comId",CacheUtil.getURLInCache("encDog"));
			memberList = OkHttpUtils.getMemberList(gson.toJson(ornums));
			if (!StringUtils.isEmpty(memberList)) {
				JSONObject obj = JSONObject.fromObject(memberList);
				if (obj.get("msgCode").equals("ok")) {
					return getSuccessResult(memberList);
				} else {
					return getFailHyResult(obj.get("resMsg"));
				}
			} else {
				return getResult(APIEnumDefine.M0600001);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getResult(APIEnumDefine.M0600001);
		}
	}

	/**
	 * 获取会员等级明细<br>
	 *
	 * @return 返回当前获取会员等级明细
	 */
	@RequestMapping("/getMemberLevel")
	@ResponseBody
	@ApiOperation(value = "获取会员等级明细", httpMethod = "POST", notes = "获取会员等级明细")
	public Object getMemberLevel() {
		String memberList = null;
		try {
			String  encDog = CacheUtil.getURLInCache("encDog");
			memberList = OkHttpUtils.getMemberLevel(encDog);
			if (!StringUtils.isEmpty(memberList)) {
				JSONObject obj = JSONObject.fromObject(memberList);
				if (obj.get("msgCode").equals("ok")) {
					return getSuccessResult(memberList);
				} else {
					return getFailHyResult(obj.get("resMsg"));
				}
			} else {
				return getResult(APIEnumDefine.M0600001);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getResult(APIEnumDefine.M0600001);
		}
	}
	
	/**
	 * 
	 * 新增/编辑会员信息<br>
	 * @param request
	 * @param userCode 操作员代码 <br>
	 * @param orgs json格式 {"custName":"客户名称","mobile":"电话号码","comId":"门店标识","email":"邮箱(可空)","性别":"0女/1男","custType":
	 * 				"1、单位客户与0、个人客户","creditMoney":"信用额度","onceMoney":"单次挂账额度","operateUserId":"操作人","saveType":
	 * 				"新增传值 add,其他可不传","gradId":"级别id uuid字符串","consPwd":"卡密码  6位数字"}
	 * 			{"custName":"heshuai","mobile":"15281074077","comId":"001","email":"","性别":"1","custType":"0","creditMoney":"500","onceMoney":"500",
	 * "operateUserId":"4","saveType":"add",
	 * "gradId":"f1046120-5d54-49c6-a337-c063a4296c50","consPwd":""}
	 * @return
	 */
	@RequestMapping("/saveMember")
	@ResponseBody
	@ApiOperation(value = "新增/编辑会员信息", httpMethod = "POST", notes = "新增/编辑会员信息")
	public Object saveMember(HttpServletRequest request,
			@ApiParam(required = true, name = "userCode", value = "操作员代码") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "orgs", value = "json参数") @RequestParam(value = "orgs", required = true) String orgs) {
		String saveBack = null;
		try {
			saveBack = OkHttpUtils.saveMember(orgs);
			if (!StringUtils.isEmpty(saveBack)) {
				JSONObject obj = JSONObject.fromObject(saveBack);
				if (obj.get("msgCode").equals("ok")) {
					return getSuccessResult(saveBack);
				} else {
					return getFailHyResult(obj.get("resMsg"));
				}
			} else {
				return getResult(APIEnumDefine.M0600001);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getResult(APIEnumDefine.M0600001);
		}
	}
	
	/**
	 * 
	 * 预充值,获取充值优惠活动
	 * {"topUpMoney":"80","vId":"5506ef59-5733-4b68-8287-863b1f771a4d"}
	 * @param request
	 * @param userCode
	 * @param orgs
	 * @return
	 */
	@RequestMapping("/memberRechargeBefore")
	@ResponseBody
	@ApiOperation(value = "预充值,获取充值优惠活动", httpMethod = "POST", notes = "预充值,获取充值优惠活动")
	public Object memberRechargeBefore(HttpServletRequest request,
			@ApiParam(required = true, name = "userCode", value = "操作员代码") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "orgs", value = "json") @RequestParam(value = "orgs", required = true) String orgs
			) {
		String saveBack = null;
		try {
			saveBack = OkHttpUtils.memberRechargeBefore(orgs);
			if (!StringUtils.isEmpty(saveBack)) {
				JSONObject obj = JSONObject.fromObject(saveBack);
				if (obj.get("msgCode").equals("ok")) {
					return getSuccessResult(saveBack);
				} else {
					return getFailHyResult(obj.get("resMsg"));
				}
			} else {
				return getResult(APIEnumDefine.M0600001);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getResult(APIEnumDefine.M0600001);
		}
	}
	
	
	/**
	 * 
	 * 充值
	 * @param request
	 * @param userCode
	 * @param orgs
	 * @return
	 */
	@RequestMapping("/memberRecharge")
	@ResponseBody
	@ApiOperation(value = "预充值,获取充值优惠活动", httpMethod = "POST", notes = "预充值,获取充值优惠活动")
	public Object memberRecharge(HttpServletRequest request,
			@ApiParam(required = true, name = "userCode", value = "操作员代码") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "orgs", value = "参数json") @RequestParam(value = "orgs", required = true) String orgs) {
		String saveBack = null;
		try {
			saveBack = OkHttpUtils.memberRecharge(orgs);
			if (!StringUtils.isEmpty(saveBack)) {
				JSONObject obj = JSONObject.fromObject(saveBack);
				if (obj.get("msgCode").equals("ok")) {
					return getSuccessResult(saveBack);
				} else {
					return getFailHyResult(obj.get("resMsg"));
				}
			} else {
				return getResult(APIEnumDefine.M0600001);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getResult(APIEnumDefine.M0600001);
		}
	}
}