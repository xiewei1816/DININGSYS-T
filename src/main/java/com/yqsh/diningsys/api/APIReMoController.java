package com.yqsh.diningsys.api;

import com.alibaba.druid.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitSMapper;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.model.deskBusiness.DgOwLockinfo;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIModifyService;
import com.yqsh.diningsys.web.service.api.APIReMoService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.base.SysAuthorizationLogService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemOutofStockService;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;
import com.yqsh.diningsys.web.util.OnlineHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户端更换修改对外接口
 *
 * @author zhshuo create on 2016-12-12 17:15
 */
@Controller
@RequestMapping("/yqshapi/replaceModify")
public class APIReMoController extends ApiBaseController {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateformat = new SimpleDateFormat(
			"yyyy-MM-dd");
	@Autowired
	private UserService userService;

	@Autowired
	private APIModifyService aPIModifyService;

	@Autowired
	private BusinessPermissionService businessPermissionService;

	@Autowired
	private DgOpenWaterService dgOpenWaterService;

	@Autowired
	private BillService billService;

	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;

	@Autowired
	private APIReMoService apiReMoService;

	@Autowired
	private APICheckService apiCheckService;

	@Autowired
	private DgItemOutofStockService dgItemOutofStockService;

	@Autowired
	private DgItemSaleLimitMapper dgItemSaleLimitMapper;

	@Autowired
	private DgItemSaleLimitSMapper dgItemSaleLimitSMapper;

	@Autowired
	private SysAuthorizationLogService sysAuthorizationLogService;
	

	/********************************** 更换客位人数操作 *****************************************/

	/**
	 * 进行客位人数修改之前的操作，先调用 /yqshapi/checkService/checkSeatManyOpenWater
	 * 接口，判断该客座是否存在多个营业流水<br>
	 * 在调用此接口，前台传入需要更改人数的营业流水后，判断该营业流水的状态，确定该营业流水是否能修改人数。
	 *
	 * @param owNum
	 * @return
	 */
	@RequestMapping("/updatePeopleNumBefore")
	@ResponseBody
	@ApiOperation(value = "进行客位人数修改之前的操作", httpMethod = "POST", notes = "进行客位人数修改之前的操作")
	public Object updatePeopleNumBefore(String owNum) {
		DgOpenWater ow = dgOpenWaterService.selectByOpenWaterByNum(owNum);
		if (ow == null) {
			return getResult(APIEnumDefine.S998);
		}

		// 判断营业单状态
		if (ow.getOwState() == 2) {
			return getResult(APIEnumDefine.M0404003);
		}
		if (ow.getOwState() == 3 || ow.getOwState() == 5) {
			return getResult(APIEnumDefine.M0404004);
		}
		if(ow.getOwState() == 9){
			return getResult(APIEnumDefine.S993);
		}
		if(ow.getOwState() == 8){
			return getResult(APIEnumDefine.S988);
		}
		return getSuccessResult(ow);
	}

	/**
	 * 更新客位人数<br>
	 * 根据营业单号更新客位人数<br>
	 * 前提条件1:营业员具有更改座位人数的权利 更改人数权利 -- 减少人数权利 前提条件改桌子已经开台或者埋单-客户端自行判断一次<br>
	 * 实际的人数是放在营业单下面。所以传入的是营业单号<br>
	 *
	 * @param userCode
	 *            营业员
	 * @param owNum
	 *            营业单号
	 * @param newNum
	 *            人数
	 * @return
	 */
	@RequestMapping("/updatePeopleNum")
	@ResponseBody
	@ApiOperation(value = "更新客桌人数", httpMethod = "POST", notes = "根据营业单号更新客桌人数")
	public Object updatePeopleNum(
			@ApiParam(required = true, name = "userCode", value = "营业员ID") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "owNum", value = "营业单号") @RequestParam(value = "owNum", required = true) String owNum,
			@ApiParam(required = true, name = "newNum", value = "更改的人数") @RequestParam(value = "newNum", required = true) Integer newNum,
			@ApiParam(required = true, name = "num", value = "更改之前人数") @RequestParam(value = "num", required = true) Integer num,
			@ApiParam(required = true, name = "waiterId", value = "服务人员id") @RequestParam(value = "waiterId", required = true) String waiterId,
			@ApiParam(required = false, name = "isJsPx", value = "当减少人数切桌位有自增品项时,是否同时减少自增品项 0/1") @RequestParam(value = "isJsPx", required = false) String isJsPx,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			SysPerBusiness sysPerBusiness = null;
			if(!StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"repalce_modify_rmPeopleCount");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"更改客位人数")
				);
				sysPerBusiness = businessPermissionService
						.selectBusinessByUserCode(sysUser.getEmpCode());
			}else{
				sysPerBusiness = businessPermissionService
						.selectBusinessByUserCode(userCode);
				if (sysPerBusiness != null && sysPerBusiness.getGgrsQx() == null) {
					return getResult(APIEnumDefine.M0404001);
				}
			}




			Map closedOpenWater = apiCheckService.selectOpenWaterByOwNum(owNum);
			String ow_state = closedOpenWater.get("ow_state").toString();
			if(ow_state.equals("2") || ow_state.equals("6")){
				return getResult(APIEnumDefine.M0201019);
			}


			DgOpenWater ow = dgOpenWaterService.selectByOpenWaterByNum(owNum);
			// 判断营业单状态
			if(ow == null){
				return getResult(APIEnumDefine.S998);
			}
			if (ow.getOwState() == 2 || ow.getOwState() == 6) {
				return getResult(APIEnumDefine.M0404003);
			}
			if (ow.getOwState() == 3 || ow.getOwState() == 5) {
				return getResult(APIEnumDefine.M0404004);
			}
			if (ow.getOwState() == 8) {
				return getResult(APIEnumDefine.S988);
			}
			if (ow.getOwState() == 9 ) {
				return getResult(APIEnumDefine.S993);
			}

			if (newNum < num) {
				if (sysPerBusiness != null
						&& sysPerBusiness.getJsrsQx() == null) {
					return getResult(APIEnumDefine.M0404002);
				}
			}

			// 此处加入计算岁人数添加品项的服务单并计算营业单 to 何帅 by 曾潮
			Map ret = billService.modifyGgkw(userCode,owNum, newNum, waiterId, isJsPx);
			if (ret.containsKey("error")) {
				return getResult((APIEnumDefine) ret.get("error"));
			} else {
				return getSuccessResult(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 更换客位人数操作 END *****************************************/

	/********************************** 更换客位状态操作 *****************************************/
	/**
	 * 修改客位状态操作，前台传入需要修改状态的客座的ID
	 *
	 * @param seatId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/resetSeatState")
	@ApiOperation(value = "更换客位状态操作", httpMethod = "POST", notes = "更换客位状态操作")
	public Object resetSeatState(
			@ApiParam(required = true, name = "seatId", value = "客座ID") @RequestParam(value = "seatId", required = true) Integer seatId) {
		try {
			DgConsumerSeat dgConsumerSeat = dgConsumerSeatService
					.selectByPrimaryKey(seatId);
			if (dgConsumerSeat == null) {
				return getResult(APIEnumDefine.S998);
			}
			if (dgConsumerSeat.getSeatState() != 3) {
				return getResult(APIEnumDefine.M0405001);
			}
			Integer res = apiReMoService.modifyResetSeatState(seatId);
			return res == 1 ? getSuccessResult() : getFailResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 更换客位状态操作 END *****************************************/

	/********************************** 更换客位标记操作 *****************************************/

	@ResponseBody
	@RequestMapping("/modifySeatIdentifyBefore")
	@ApiOperation(value = "更换客位标记操作先前操作", httpMethod = "POST", notes = "更换客位标记操作先前操作")
	public Object modifySeatIdentifyBefore(
			@ApiParam(required = true, name = "seatId", value = "客座ID") @RequestParam(value = "seatId", required = true) Integer seatId) {
		try {
			DgConsumerSeat dgConsumerSeat = dgConsumerSeatService
					.selectByPrimaryKey(seatId);
			return getSuccessResult(dgConsumerSeat);
		} catch (Exception e) {
			e.printStackTrace();
			return getSuccessResult();
		}
	}

	/**
	 * 修改客座标记，先调用modifySeatIdentifyBefore接口。<br>
	 * VIP和内部留房值为0|1，必传，客座标签选填
	 *
	 * @param seatId
	 *            更换客位标识的客座的ID
	 * @param seatLabel
	 *            客座标签
	 * @param isVip
	 *            VIP
	 * @param isInternal
	 *            内部留房
	 * @return
	 */
	@RequestMapping("/modifySeatIdentify")
	@ResponseBody
	@ApiOperation(value = "更换客位标记操作", httpMethod = "POST", notes = "更换客位标记操作")
	public Object modifySeatIdentify(
			@ApiParam(required = true, name = "seatId", value = "客座ID") @RequestParam(value = "seatId", required = true) Integer seatId,
			@ApiParam(required = false, name = "seatLabel", value = "客座标签") @RequestParam(value = "seatLabel", required = false) String seatLabel,
			@ApiParam(required = true, name = "isVip", value = "VIP标识") @RequestParam(value = "isVip", required = true) Integer isVip,
			@ApiParam(required = true, name = "isInternal", value = "内部留房") @RequestParam(value = "isInternal", required = true) Integer isInternal) {
		try {
			Integer res = apiReMoService.modifySeatIdentify(seatId, seatLabel,
					isVip, isInternal);
			return res == 1 ? getSuccessResult() : getFailResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 更换客位标识操作 END *****************************************/

	/********************************** 更换营业流水服务员/营销员 *****************************************/
	/**
	 * 先调用 /yqshapi/checkService/checkSeatManyOpenWater 接口，判断该客座是否存在多个营业流水<br>
	 * 前台传入具体需要修改服务员/营销员的营业流水之后，
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/replaceWaiterBefore")
	@ApiOperation(value = "更换营业流水服务员/营销员的先前操作", httpMethod = "POST", notes = "更换营业流水服务员/营销员的先前操作")
	public Object replaceWaiterBefore(
			@ApiParam(required = true, name = "owNum", value = "营业单号") @RequestParam(value = "owNum") String owNum) {
		try {

			DgOpenWater dgOpenWater = dgOpenWaterService
					.selectByOpenWaterByNum(owNum);
			if(dgOpenWater == null){
				return getResult(APIEnumDefine.S998);
			}
			if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
				return getResult(APIEnumDefine.S996);
			}
			if(dgOpenWater.getOwState() == 9){
				return getResult(APIEnumDefine.S993);
			}
			if(dgOpenWater.getOwState() == 8){
				return getResult(APIEnumDefine.S988);
			}
			Map resBody = new HashedMap();
			resBody.put("waiterName", dgOpenWater.getWaiterCode() + "—"
					+ dgOpenWater.getWaiterName());
			resBody.put(
					"marketingStaffName",
					dgOpenWater.getMarketingStaffCode() + "—"
							+ dgOpenWater.getMarketingStaffName());
			return getSuccessResult(resBody);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 更换营业流水服务员/营销员 操作 <br>
	 * 三个参数后台都能为空，但是前台点击确定的时候必须要有一项不为空。
	 *
	 * @param owNum
	 *            进行操作的营业流水号
	 * @param newWaiterId
	 *            替换过后的服务员的ID
	 * @param newMaketingStaff
	 *            替换过后的营销员的ID
	 * @param replaceService
	 *            是否替换对应服务员的点单数据，值0|1
	 * @return 三个参数全部为空，返回M0406003， 只有当是否替换不为空时，返回M0406002
	 */
	@ResponseBody
	@RequestMapping("/replaceWaiter")
	@ApiOperation(value = "更换营业流水服务员/营销员", httpMethod = "POST", notes = "更换营业流水服务员/营销员")
	public Object replaceWaiterOrMarketingStaff(
			@ApiParam(required = true, name = "owNum", value = "进行操作的营业流水号") @RequestParam(value = "owNum") String owNum,
			@ApiParam(required = false, name = "newWaiterId", value = "替换过后的服务员的ID") @RequestParam(value = "newWaiterId", required = false) Integer newWaiterId,
			@ApiParam(required = false, name = "newMaketingStaff", value = "替换过后的营销员的ID") @RequestParam(value = "newMaketingStaff", required = false) Integer newMaketingStaff,
			@ApiParam(required = false, name = "replaceService", value = "是否替换对应服务员的点单数据") @RequestParam(value = "replaceService", required = false) Integer replaceService,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode,
			@ApiParam(required = true, name = "userCode", value = "前台的登录用户") @RequestParam(value = "userCode") String userCode
			) {

		try {
			if(!StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"repalce_modify_rmWaiter");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"更换营业流水服务员/营销员")
				);
			}
			
			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);
			if(dgOpenWater == null){
				return getResult(APIEnumDefine.S998);
			}
			if(dgOpenWater.getOwState() == 2 || dgOpenWater.getOwState() == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
				return getResult(APIEnumDefine.S996);
			}
			if(dgOpenWater.getOwState() == 9){
				return getResult(APIEnumDefine.S993);
			}
			if(dgOpenWater.getOwState() == 8){
				return getResult(APIEnumDefine.S988);
			}
			if (newWaiterId == null && newMaketingStaff == null
					&& replaceService == null) {
				return getResult(APIEnumDefine.M0406003);
			}

			if (newWaiterId == null && newMaketingStaff == null
					&& replaceService != null) {
				return getResult(APIEnumDefine.M0406002);
			}

			apiReMoService.modifyReplaceWaiter(owNum, newWaiterId,
					newMaketingStaff, replaceService);

			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 更换营业流水服务员/营销员 END *****************************************/

	/********************************** 品项变价 *********************************************/
	/**
	 * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
	 * 2、调用/yqshapi/checkService/selectOpenWaterInfoBySeat接口，得到该营业流水下面的所有有效品项<br>
	 * 品项变价
	 *
	 * @param owNum
	 *            需要进行变价操作的营业流水
	 * @param itemPriceDate
	 *            品项变价的json数据 数据格式为{[
	 *            'itemFileId':品项ID,'serviceId':服务单号ID,'itemFileNum':品项数量,'itemFilePrice':变价的价格,'initalPrice':初始价格]
	 *            }
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/modifyItemPrice")
	@ApiOperation(value = "品项变价", httpMethod = "POST", notes = "品项变价")
	public Object modifyItemPrice(
			@ApiParam(required = true, name = "owNum", value = "进行变价操作的营业流水号") @RequestParam(value = "owNum") String owNum,
			@ApiParam(required = true, name = "itemPriceDate", value = "品项变价的json数据") @RequestParam(value = "itemPriceDate") String itemPriceDate,
			@ApiParam(required = true, name = "userCode", value = "前台登录的用户") @RequestParam(value = "userCode") String userCode,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {

			SysUser sysUser = null;
			Integer variablePriceType = null;

			if(!StringUtils.isEmpty(authCode)){
				sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"closing");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				SysPerOverview sysPerOverview = businessPermissionService.selecOverViewByZwCode(sysUser.getEmpDuties());
				variablePriceType = sysPerOverview.getVariablePriceType();
				if(variablePriceType == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"品项变价")
				);
			}else{
				sysUser = userService.selectByUsername(userCode);
				SysPerOverview sysPerOverview = businessPermissionService.selecOverViewByZwCode(sysUser.getEmpDuties());
				variablePriceType = sysPerOverview.getVariablePriceType();

				if(variablePriceType == null){
					return getResult(APIEnumDefine.M0201020);
				}
			}

			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);
			if(dgOpenWater == null){
				return getResult(APIEnumDefine.S998);
			}
			if(dgOpenWater.getOwState() == 2 || dgOpenWater.getOwState() == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
				return getResult(APIEnumDefine.S996);
			}
			if(dgOpenWater.getOwState() == 9){
				return getResult(APIEnumDefine.S993);
			}
			if(dgOpenWater.getOwState() == 8){
				return getResult(APIEnumDefine.S988);
			}
			apiReMoService.modifyItemFilePrice(userCode,itemPriceDate,sysUser,variablePriceType,owNum);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 品项变价 END *****************************************/

	/********************************** 修改数量 *****************************************/

    /**
     * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
     * 2、调用/yqshapi/checkService/selectOpenWaterInfoBySeat接口，得到该营业流水下面的所有有效品项<br>
     * 修改数量
     *
     * @param owNum 进行修改数量操作的营业流水号
     * @param modifyData 修改数量的json数据
     *                   数据格式：[{"serviceId":服务ID,"itemFileId":品项ID,"itemFileNum":品项数量,"productionCosts":新制作费用,"itemFilePrice":品项价格}]
     * @return
     */
    @ResponseBody
    @RequestMapping("/modifyItemFileNumber")
    @ApiOperation(value = "修改数量", httpMethod = "POST", notes = "修改数量")
    public Object modifyItemFileNumber( @ApiParam(required = true, name = "owNum", value = "进行修改数量操作的营业流水号") @RequestParam(value = "owNum", required = true) String owNum,
                                        @ApiParam(required = true, name = "modifyData", value = "修改数量的json数据") @RequestParam(value = "modifyData", required = true) String modifyData,
										@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode,
										@ApiParam(required = true, name = "userCode", value = "前台的登录用户") @RequestParam(value = "userCode") String userCode
										) {
        try {
			if(!StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"repalce_modify_rmEditNumber");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"修改数量")
				);
			}

			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);
			if(dgOpenWater == null){
				return getResult(APIEnumDefine.S998);
			}
			if(dgOpenWater.getOwState() == 2 || dgOpenWater.getOwState() == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
				return getResult(APIEnumDefine.S996);
			}
			if(dgOpenWater.getOwState() == 9){
				return getResult(APIEnumDefine.S993);
			}
			if(dgOpenWater.getOwState() == 8){
				return getResult(APIEnumDefine.S988);
			}
        	
            apiReMoService.modifyItemFileNumber(userCode,owNum, modifyData);
            return getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }

	/********************************** 修改数量 END *****************************************/

	/********************************** 品项赠送AND品项撤销赠送 *****************************************/
	/**
	 * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
	 * 2、调用/yqshapi/checkService/selectOpenWaterInfoBySeat接口，得到该营业流水下面的所有有效品项<br>
	 * 品项赠送操作
	 *
	 * @param dishFreeData
	 *            品项赠送的json数据
	 *            数据格式：[{"serviceId":服务ID,"itemFileId":品项ID,"itemFileNumber":品项数量}]
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/modifyDishesFree")
	@ApiOperation(value = "品项赠送", httpMethod = "POST", notes = "品项赠送")
	public Object modifyDishesFree(
			@ApiParam(required = true, name = "dishFreeData", value = "品项赠送的json数据") @RequestParam(value = "dishFreeData") String dishFreeData,
			@ApiParam(required = true, name = "pos", value = "操作POS的ID") @RequestParam(value = "pos") Integer pos,
			@ApiParam(required = true, name = "userCode", value = "前台操作用户") @RequestParam(value = "userCode") String userCode,
			@ApiParam(required = true, name = "openWater", value = "营业流水") @RequestParam(value = "openWater") String openWater,
			@ApiParam(required = true, name = "modifyType", value = "1：赠送，2：取消赠送") @RequestParam(value = "modifyType") Integer modifyType,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode,
			@ApiParam(required = false, name = "reason", value = "赠送原因") @RequestParam(value = "reason", required = false) String reason) {
		try {
			SysUser sysUser = null;
			if(!StringUtils.isEmpty(authCode)){
				sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,modifyType==1?"repalce_modify_rmFree":"repalce_modify_cancelFree");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"品项赠送")
				);
			}

			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(openWater);
			if(dgOpenWater == null){
				return getResult(APIEnumDefine.S998);
			}
			if(dgOpenWater.getOwState() == 2 || dgOpenWater.getOwState() == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
				return getResult(APIEnumDefine.S996);
			}
			if(dgOpenWater.getOwState() == 9){
				return getResult(APIEnumDefine.S993);
			}
			if(dgOpenWater.getOwState() == 8){
				return getResult(APIEnumDefine.S988);
			}
			if(sysUser == null){
				sysUser = userService.selectByUsername(userCode);
			}

			SysPerOverview sysPerOverview = businessPermissionService.selecOverViewByZwCode(sysUser.getEmpDuties());
			Integer freeType = sysPerOverview.getFreeType();

			if(freeType == null)return getResult(APIEnumDefine.M02010201);

			apiReMoService.modifyDishesFree(userCode,dishFreeData,pos,sysUser,openWater,modifyType,freeType,reason);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取所有品项为赠送的数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancelDishesFreeBefore")
	@ApiOperation(value = "撤销品项赠送之前的操作，获取是赠送的品项", httpMethod = "POST", notes = "撤销品项赠送之前的操作，获取是赠送的品项")
	public Object cancelDishesFreeBefore(@ApiParam(required = true, name = "owNum", value = "进行修改数量操作的营业流水号") @RequestParam(value = "owNum", required = true) String owNum) {
		try {
			List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterFreeWithService(owNum);
			return getSuccessResult(dgOwConsumerDetailss);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 品项赠送AND品项撤销赠送 END *****************************************/

	/********************************** 单品转台 *****************************************/
	/**
	 * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
	 * 2、调用/yqshapi/checkService/selectTransferTeam，得到当前的团队数据<br>
	 * 单品转台操作
	 *
	 * @param turntableData
	 *            单品转台的json数据
	 *            数据格式：[{"itemFileNum":"品项数量","itemFileZs":"品项只数","itemFileId"
	 *            :"品项ID","serviceId":"服务ID"}]
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/modifyDishesTurntable")
	@ApiOperation(value = "单品转台", httpMethod = "POST", notes = "单品转台")
	public Object modifyDishesTurntable(
			@ApiParam(required = true, name = "userCode", value = "前台登录的用户code") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "targetOwNum", value = "单品转台的目标流水") @RequestParam(value = "targetOwNum", required = true) String targetOwNum,
			@ApiParam(required = true, name = "operaOwNum", value = "需要进行单品转台的营业流水") @RequestParam(value = "operaOwNum", required = true) String operaOwNum,
			@ApiParam(required = true, name = "turntableData", value = "单品转台的json数据") @RequestParam(value = "turntableData", required = true) String turntableData,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			DgOpenWater water = dgOpenWaterService
					.selectByOpenWaterByNum(operaOwNum);
			if(water == null){
				return getResult(APIEnumDefine.S998);
			}
			if(water.getOwState() == 2 || water.getOwState() == 6){
				return getResult(APIEnumDefine.M0201019);
			}

			DgOpenWater targetWater = dgOpenWaterService
					.selectByOpenWaterByNum(targetOwNum);
			if (water.getOwState() == 3 || water.getOwState() == 8 || water.getOwState() == 9 || water.getOwState() == 5) {
				DgOwLockinfo lockInfo = apiCheckService
						.selectOpenWaterLock(operaOwNum);
				if (lockInfo != null) {
					return getResult(APIEnumDefine.S992, lockInfo);
				}
			}
			
			if (targetWater.getOwState() == 3 || targetWater.getOwState() == 8 || targetWater.getOwState() == 9 || water.getOwState() == 5) {
				DgOwLockinfo lockInfo = apiCheckService
						.selectOpenWaterLock(targetOwNum);
				if (lockInfo != null) {
					return getResult(APIEnumDefine.S992, lockInfo);
				}
			}
			
			// 判断营业单状态
			if (water.getOwState() != null && (water.getOwState() == -1 || water.getOwState() == 2 || water.getOwState() == 7 || water.getOwState() == 6)) {
				return getResult(APIEnumDefine.M0101020);
			}
			
			SysPerBusiness sysPerBusiness = businessPermissionService
					.selectBusinessByUserCode(userCode);

			if (sysPerBusiness == null) {
				return getResult(APIEnumDefine.S995);
			}

			if(!StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"repalce_modify_rmTurntable");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"单品转台")
				);
			}else{
				if (sysPerBusiness.getDpztQx() == null) {
					return getResult(APIEnumDefine.M0411001);
				}
			}

			if (targetOwNum.equals(operaOwNum)) {
				return getResult(APIEnumDefine.M0411002);
			}

			List<Map> data = new ArrayList<Map>();
			JSONArray arr = JSONArray.fromObject(turntableData);
			if(arr.size()>0)
			{
				for(int i=0;i<arr.size();i++)
				{
					JSONObject object = arr.getJSONObject(i);
					Map m = new HashMap();
					m.put("itemFileNum",object.get("itemFileNum"));
					m.put("itemFileZs",object.get("itemFileZs"));
					m.put("itemFileId",object.get("itemFileId"));
					m.put("serviceId",object.get("serviceId"));
					data.add(m);
				}
			}
			else{
				return getResult(APIEnumDefine.M0101023);
			}
			
			Map ret = apiReMoService.modifyDishesTurntable(userCode,targetOwNum,
					operaOwNum, data);
			if (ret.containsKey("error")) {
				return getResult((APIEnumDefine) ret.get("error"));
			} else {
				return getSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 单品转台 END *****************************************/

	/********************************** 手工锁定AND解锁 *****************************************/
	/**
	 * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
	 * 手工锁定，修改为团队单全部锁定
	 *
	 * @param owNum
	 *            手工锁定的营业流水
	 * @param userCode
	 *            操作人员
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ManualLocking")
	@ApiOperation(value = "手工锁定", httpMethod = "POST", notes = "手工锁定")
	public Object ManualLocking(
			@ApiParam(required = true, name = "owNum", value = "手工锁定的营业流水") @RequestParam(value = "owNum", required = true) String owNum,
			@ApiParam(required = true, name = "userCode", value = "手工锁定的操作人员") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "pos", value = "操作的POS") @RequestParam(value = "pos", required = true) Integer pos) {
		try {
			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);

			if (dgOpenWater == null)
				return getFailResult(APIEnumDefine.S998);

			Integer owState = dgOpenWater.getOwState();
			if(owState == 2 || owState == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			
			if (owState == 3) {
				return getFailResult(APIEnumDefine.S996);
			}

			if (owState == 9) {
				return getFailResult(APIEnumDefine.S993);
			}

			apiReMoService.modifyManualLocking(dgOpenWater, userCode, 8,pos);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}

	}

	@ResponseBody
	@RequestMapping("unlockBefore")
	@ApiOperation(value = "解锁之前的操作，获取可以进行解锁的营业流水数据", httpMethod = "POST", notes = "解锁之前的操作，获取可以进行解锁的营业流水数据")
	public Object unlockBefore(
			@ApiParam(required = true, name = "userCode", value = "前台登录的用户的code") @RequestParam(value = "userCode", required = true) String userCode) {
		try {
			SysPerBusiness sysPerBusiness = businessPermissionService
					.selectBusinessByUserCode(userCode);
			if (sysPerBusiness == null)
				return getFailResult(APIEnumDefine.S995);
			if (sysPerBusiness.getJsQx() == null)
				return getFailResult(APIEnumDefine.M0418001);

			List<DgOpenWater> dgOpenWaters = apiReMoService
					.selectAllLockedData();
			return getSuccessResult(dgOpenWaters);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
	 * 手工锁定，当撤销埋单，调用cancelPayState接口
	 * 8：解除手工锁定  9：解除结算锁定  3：撤销埋单
	 * @param owNum
	 *            解锁的营业流水号码
	 * @param type
	 *            解锁的类型，1（解除手工锁定）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/unlock")
	@ApiOperation(value = "解锁", httpMethod = "POST", notes = "解锁")
	public Object unlock(
			@ApiParam(required = true, name = "type", value = "解锁的类型，：解除手工锁定  9：解除结算锁定  3：撤销埋单") @RequestParam(value = "type", required = true) Integer type,
			@ApiParam(required = true, name = "owNum", value = "手工锁定的营业流水") @RequestParam(value = "owNum", required = true) String owNum) {
		try {
			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(owNum);
			if (dgOpenWater == null)
				return getFailResult(APIEnumDefine.S998);

			Integer ow_state = dgOpenWater.getOwState();
			if(ow_state == 2 || ow_state == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			apiReMoService.modifyUnlock(dgOpenWater, type);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}

	}

	/********************************** 手工锁定AND解锁 END *****************************************/

	/********************************** 顾客信息登记 *****************************************/
	/**
	 * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
	 * 登记顾客信息
	 *
	 * @param owNum
	 *            登记顾客信息的营业流水号码
	 * @param customerInfoData
	 *            顾客信息json数据。数据格式为
	 *            [{"ageRange":"年龄范围,"manCount":"男人数","femaleCount
	 *            ":"女人数","forgenCount":"外宾人数"}]
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateOWCustomerInfo")
	@ApiOperation(value = "顾客信息登记", httpMethod = "POST", notes = "顾客信息登记")
	public Object updateOWCustomerInfo(
			@ApiParam(required = true, name = "owNum", value = "顾客信息登记的营业流水") @RequestParam(value = "owNum", required = true) String owNum,
			@ApiParam(required = true, name = "customerInfoData", value = "顾客的JSON数据") @RequestParam(value = "customerInfoData", required = true) String customerInfoData) {
		try {
			
			Map closedOpenWater = apiCheckService.selectOpenWaterByOwNum(owNum);
			String ow_state = closedOpenWater.get("ow_state").toString();
			if(ow_state.equals("2") || ow_state.equals("6")){
				return getResult(APIEnumDefine.M0201019);
			}
			
			Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
			if (map == null)
				return getFailResult(APIEnumDefine.S998);

			apiReMoService.updateOWCustomerInfo(map, customerInfoData);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 顾客信息登记 END *****************************************/

	/********************************** 撤销埋单 *****************************************/
	/**
	 * 1、调用/yqshapi/checkService/checkSeatManyOpenWater得到当前客位是否存在多个营业流水<br>
	 *
	 * @param owNum
	 * @param userCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cancelPayState")
	@ApiOperation(value = "撤销埋单", httpMethod = "POST", notes = "撤销埋单")
	public Object cancelPayState(
			@ApiParam(required = true, name = "owNum", value = "撤销埋单的营业流水") @RequestParam(value = "owNum", required = true) String owNum,
			@ApiParam(required = true, name = "userCode", value = "前台用户登录的编号") @RequestParam(value = "userCode", required = true) String userCode) {
		try {

			Map closedOpenWater = apiCheckService.selectOpenWaterByOwNum(owNum);

			String ow_state = closedOpenWater.get("ow_state").toString();
			if(ow_state.equals("2") || ow_state.equals("6")){
				return getResult(APIEnumDefine.M0201019);
			}

			SysPerBusiness sysPerBusiness = businessPermissionService
					.selectBusinessByUserCode(userCode);
			if (sysPerBusiness == null)
				return getFailResult(APIEnumDefine.S995);

			if (sysPerBusiness.getCxmdQx() == null)
				return getFailResult(APIEnumDefine.M0420001);

			Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
			if (map == null)
				return getFailResult(APIEnumDefine.S998);

			apiReMoService.modifyCancelPayState(owNum);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}

	}

	/********************************** 撤销埋单 END *****************************************/

	/**
	 * 更改桌位
	 */
	@RequestMapping("/changeTable")
	@ResponseBody
	@ApiOperation(value = "更换客位", httpMethod = "POST", notes = "更换客位")
	public Object changeTable(
			@ApiParam(required = true, name = "userCode", value = "营业员code") @RequestParam(value = "userCode", required = false) String userCode,
			@ApiParam(required = true, name = "waterid", value = "服务员ID") @RequestParam(value = "waterid", required = false) String waterid,
			@ApiParam(required = true, name = "ow_num", value = "营业单号") @RequestParam(value = "ow_num", required = false) String ow_num,
			@ApiParam(required = true, name = "tableid", value = "目标客桌ID") @RequestParam(value = "tableid", required = false) String tableid,
			@ApiParam(required = true, name = "isGgFa", value = "是否更改包房收费方案") @RequestParam(value = "isGgFa", required = false) String isGgFa,
			@ApiParam(required = true, name = "isJsBffPx", value = "是否将之前的包房费生存一个品项") @RequestParam(value = "isJsBffPx", required = false) String isJsBffPx,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			
			//判断营业流水是否已经结算
			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(ow_num);
			if(dgOpenWater == null){
				return getResult(APIEnumDefine.S998);
			}
			if(dgOpenWater.getOwState() == 2 || dgOpenWater.getOwState() == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
				return getResult(APIEnumDefine.S996);
			}
			if(dgOpenWater.getOwState() == 9){
				return getResult(APIEnumDefine.S993);
			}
			if(dgOpenWater.getOwState() == 8){
				return getResult(APIEnumDefine.S988);
			}
			if(!StringUtils.isEmpty(dgOpenWater.getTransferTarget())){
				return getResult(APIEnumDefine.S1001);
			}


			SysPerBusiness pre_business = null;
			if(!StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"repalce_modify_rmRoundtrip");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				pre_business = businessPermissionService
						.selectBusinessByUserCode(sysUser.getEmpDuties());
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"更换客位")
				);
			}else{
				pre_business = businessPermissionService
						.selectBusinessByUserCode(userCode);
			}

//			pre_business = businessPermissionService
//					.selectBusinessByUserCode(userCode);
			// 如果为空，ggrs_qx != 1 说明该人员没有权限
			if (pre_business != null && pre_business.getGhkwQx() != null
					&& pre_business.getGhkwQx() == 1) {
				DgOpenWater ow = dgOpenWaterService
						.selectByOpenWaterByNum(ow_num);
				if (ow != null) {

					// 判断营业单状态
					if (ow.getOwState() != null && ow.getOwState() == 2) {
						return getResult(APIEnumDefine.M0101014);
					} else if (ow.getOwState() != null && ow.getOwState() == 3) {
						return getResult(APIEnumDefine.M0101015);
					} else if (ow.getOwState() != null && ow.getOwState() == -1) {
						return getResult(APIEnumDefine.M0101016);
					}
					DgConsumerSeat seat = dgConsumerSeatService
							.getConsumerSeatById(Integer.valueOf(tableid));
					if (seat != null) {
						if (seat.getSeatState() == 1) {
							// 更换客位处理
							aPIModifyService.modifySeat(userCode,ow,
									Integer.valueOf(waterid),
									Integer.valueOf(tableid),
									Integer.valueOf(isGgFa),
									Integer.valueOf(isJsBffPx));
						} else {
							return getResult(APIEnumDefine.M0101002);
						}
					} else {
						return getResult(APIEnumDefine.M0101017);
					}
					return getSuccessResult(ow);
				} else {
					return getResult(APIEnumDefine.M0101001);
				}
			} else {

				return getResult(APIEnumDefine.M0101003);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 沽清品项处理开始 *****************************************/

	/**
	 * * 获取沽清品项<br>
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGqPx")
	@ApiOperation(value = "获取沽清品项", httpMethod = "POST", notes = "获取沽清品项")
	public Object getGqPx(
			@ApiParam(required = true, name = "type", value = "获取类型 1不指定 2按营业市别 3按营业日") @RequestParam(value = "type", required = false) String type) {
		try {
			DgItemOutofStock stock = new DgItemOutofStock();
			stock.setmType(Integer.valueOf(type));
			List<DgItemOutofStock> ret = dgItemOutofStockService
					.getAllData(stock);
			return getSuccessResult(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * * 获取沽清增加品项<br>
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getGqZjPx")
	@ApiOperation(value = "获取沽清增加品项", httpMethod = "POST", notes = "获取沽清增加品项")
	public Object getGqZjPx(
			@ApiParam(required = false, name = "removeIds", value = "不包含的品项id组合   1,2,3..") @RequestParam(value = "removeIds", required = false) String removeIds) {
		try {
			if (!StringUtils.isEmpty(removeIds)) {
				List ids = new ArrayList();
				Collections.addAll(ids, removeIds.split(","));
				return getSuccessResult(aPIModifyService.getPxAndPxType(ids));
			} else {
				return getSuccessResult(aPIModifyService.getPxAndPxType(null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 *
	 * 保存沽清品项<br>
	 *
	 * @param orgs
	 *            格式[{"itemId":1,"itemCode":"001"}]
	 * @param type
	 *            1/2/3
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveGqPx")
	@ApiOperation(value = "获取沽清品项", httpMethod = "POST", notes = "获取沽清品项")
	public Object saveGqPx(
			@ApiParam(required = true, name = "userCode", value = "操作人员编码") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "orgs", value = "保存品项数据json") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "type", value = "获取类型 1不指定 2按营业市别 3按营业日") @RequestParam(value = "type", required = true) String type) {
		try {
			SysPerBusiness sysPerBusiness = businessPermissionService
					.selectBusinessByUserCode(userCode);
			if (sysPerBusiness != null && sysPerBusiness.getPxxlQx() == null) {
				return getResult(APIEnumDefine.M0440001);
			}
			
			aPIModifyService.savaGqPx(orgs, type);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 沽清品项处理 END *****************************************/

	/********************************** 销售限量品项处理开始 *****************************************/

	/**
	 * * 获取今日销售限量品项<br>
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getXlPx")
	@ApiOperation(value = "获取今日销售限量品项", httpMethod = "POST", notes = "获取今日销售限量品项")
	public Object getXlPx() {
		try {
			DgItemSaleLimit s = dgItemSaleLimitMapper.selectByDate(dateformat
					.format(new Date())); // 根据事件获取主表id
			if (s == null) {
				return getSuccessResult();
			} else {
				Map<String, Object> record = new HashMap<String, Object>();
				record.put("limitId", s.getId());
				List<DgItemSaleLimitS> ret = dgItemSaleLimitSMapper
						.getAllData(record);
				return getSuccessResult(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 *
	 * 保存沽清品项<br>
	 *
	 * @param orgs
	 *            格式[{"itemId":1,"itemCode":"001","saleCount":500.0}]
	 * @param type
	 *            1/2/3
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveXlPx")
	@ApiOperation(value = "获取沽清品项", httpMethod = "POST", notes = "获取沽清品项")
	public Object saveXlPx(
			@ApiParam(required = true, name = "userCode", value = "操作人员编码") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "orgs", value = "保存品项数据json") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "type", value = "1  减去今日预定数量  2不减") @RequestParam(value = "type", required = true) String type) {
		try {
			SysPerBusiness sysPerBusiness = businessPermissionService
					.selectBusinessByUserCode(userCode);
			if (sysPerBusiness != null && sysPerBusiness.getPxxlQx() == null) {
				return getResult(APIEnumDefine.M0440001);
			}
			aPIModifyService.saveXlPx(orgs, Integer.valueOf(type));
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/********************************** 销售限量品项处理 END *****************************************/

	/********************************** 加入团队开始 *****************************************/
	/**
	 *
	 * 加入团队<br>
	 *
	 * @param openNum
	 *            要加入的营业流水号
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/joinTeam")
	@ApiOperation(value = "加入团队", httpMethod = "POST", notes = "加入团队")
	public Object joinTeam(
			@ApiParam(required = true, name = "userCode", value = "操作人员编码") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "openNum", value = "要加入的营业流水号") @RequestParam(value = "openNum", required = true) String openNum,
			@ApiParam(required = true, name = "teamNumber", value = "要加入的团队号") @RequestParam(value = "teamNumber", required = true) String teamNumber) {
		try {
			
			Map closedOpenWater = apiCheckService.selectOpenWaterByOwNum(openNum);
			String ow_state = closedOpenWater.get("ow_state").toString();
			if(ow_state.equals("2") || ow_state.equals("6")){
				return getResult(APIEnumDefine.M0201019);
			}
			
			DgOpenWater water = dgOpenWaterService
					.selectByOpenWaterByNum(openNum);
			
			if (water.getOwState() == 3 || water.getOwState() == 8
					|| water.getOwState() == 9 || water.getOwState() == 5) {
				DgOwLockinfo lockInfo = apiCheckService
						.selectOpenWaterLock(openNum);
				if (lockInfo != null) {
					return getResult(APIEnumDefine.S992, lockInfo);
				}
			}
			
			// 判断营业单状态
			if (water.getOwState() != null && water.getOwState() != 1) {
				return getResult(APIEnumDefine.M0101020);
			}
			
			SysPerBusiness sysPerBusiness = businessPermissionService
					.selectBusinessByUserCode(userCode);
			if (sysPerBusiness != null && sysPerBusiness.getJrtdQx() == null) {
				return getResult(APIEnumDefine.M0430001);
			}
			Map ret = aPIModifyService.modifyJoinTeam(userCode,openNum, teamNumber);
			if (ret.containsKey("error")) {
				return getResult((APIEnumDefine)ret.get("error"));
			} else {
				return getSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}


	/********************************** 加入团队END *****************************************/

	/********************************** 更改会员开始 *****************************************/
	/**
	 *
	 * 更改会员<br>
	 *
	 * @param openNum
	 *            更改会员营业流水号
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/GgHy")
	@ApiOperation(value = "更改会员", httpMethod = "POST", notes = "更改会员")
	public Object GgHy(
			@ApiParam(required = true, name = "userCode", value = "操作人员编码") @RequestParam(value = "userCode", required = true) String userCode,
			@ApiParam(required = true, name = "openNum", value = "更改会员的营业流水号") @RequestParam(value = "openNum", required = true) String openNum,
			@ApiParam(required = true, name = "gradeId", value = "会员id") @RequestParam(value = "gradeId", required = true) String gradeId,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			if(!StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"repalce_modify_rmMembers");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,userCode,sysUser.getEmpCode(),"更换会员")
				);
			}

			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(openNum);
			if(dgOpenWater == null){
				return getResult(APIEnumDefine.S998);
			}
			if(dgOpenWater.getOwState() == 2 || dgOpenWater.getOwState() == 6){
				return getResult(APIEnumDefine.M0201019);
			}
			if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
				return getResult(APIEnumDefine.S996);
			}
			if(dgOpenWater.getOwState() == 9){
				return getResult(APIEnumDefine.S993);
			}
			if(dgOpenWater.getOwState() == 8){
				return getResult(APIEnumDefine.S988);
			}
			
			DgOpenWater ow = dgOpenWaterService.selectByOpenWaterByNum(openNum);
			// 判断营业单状态
			if (ow.getOwState() != null && ow.getOwState() == 2) {
				return getResult(APIEnumDefine.M0101014);
			} else if (ow.getOwState() != null && ow.getOwState() == 3) {
				return getResult(APIEnumDefine.M0101015);
			} else if (ow.getOwState() != null && ow.getOwState() == -1) {
				return getResult(APIEnumDefine.M0101016);
			}
			aPIModifyService.modifyGgHy(openNum, gradeId);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	/********************************** 更改会员END *****************************************/
	
	/********************************** 上传所有客位信息START *****************************************/
	@ResponseBody
	@RequestMapping("/sendAllSeatInfoOnline")
	@ApiOperation(value = "上传所有客位信息", httpMethod = "POST", notes = "上传所有客位信息")
	public Object sendAllSeatInfoOnline() {
		try {
			List<Map> dgConsumerSeats = dgConsumerSeatService.selectAllSeat();
			String sendInfo = JSON.toJSONString(dgConsumerSeats);
			OnlineHttp.onlineAllSeatModify(sendInfo);	
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	/********************************** 上传所有客位信息END *****************************************/
}