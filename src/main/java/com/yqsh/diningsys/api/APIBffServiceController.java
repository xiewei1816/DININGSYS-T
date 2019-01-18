package com.yqsh.diningsys.api;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.readers.ApiModelReader;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.api.util.DateTimeComputing;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwModifySeatMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.service.api.APIBffService;
import com.yqsh.diningsys.web.service.api.APIModifyService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeService;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;

/**
 * 包房服务相关接口
 * 
 * @author yqsh-hs
 */
@RequestMapping("/yqshapi/bff")
@Controller
public class APIBffServiceController extends ApiBaseController {
	@Autowired
	private APIBffService apiBffService;

	/**
	 * 根据营业流水号,获取包房费明细<br>
	 *
	 * @param openNum
	 *            营业流水号
	 * @return 返回当前营业流水包房费明细
	 */
	@RequestMapping("/getBffInfo")
	@ResponseBody
	@ApiOperation(value = "获取包房费明细", httpMethod = "POST", notes = "获取包房费明细")
	public Object getBffInfo(
			HttpServletRequest request,
			@ApiParam(required = true, name = "openNum", value = "营业流水号") @RequestParam(value = "openNum", required = true) String openNum) {
		return getSuccessResult(apiBffService.getBffInfo(openNum));
	}

	/**
	 * 修改包房费方案<br>
	 *
	 * @param userCode
	 *            操作人员
	 * @param openNum
	 *            营业流水号
	 * @param bffId
	 *            修改的包房费id
	 * @return 修改后的当前营业单包房费明细
	 */
	@RequestMapping("/updateBff")
	@ResponseBody
	@ApiOperation(value = "修改包房费方案", httpMethod = "POST", notes = "修改包房费方案")
	public Object updateBff(
			HttpServletRequest request,
			@ApiParam(required = true, name = "userCode", value = "操作人员code") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "openNum", value = "营业流水号") @RequestParam(value = "openNum", required = true) String openNum,
			@ApiParam(required = true, name = "bffId", value = "修改的包房方案id") @RequestParam(value = "bffId", required = true) String bffId) {
		Map ret = apiBffService.updateBff(openNum, Integer.valueOf(bffId));
		if (ret.containsKey("error")) {
			return getResult((APIEnumDefine)ret.get("error"));
		}
		return getSuccessResult(ret);
	}
	
	
	/**
	 * 获取所有包房费方案<br>
	 *
	 */
	@RequestMapping("/getAllBff")
	@ResponseBody
	@ApiOperation(value = "获取所有包房费方案", httpMethod = "POST", notes = "获取所有包房费方案")
	public Object getAllBff() {
		return getSuccessResult(apiBffService.getAllBff());
	}

}
