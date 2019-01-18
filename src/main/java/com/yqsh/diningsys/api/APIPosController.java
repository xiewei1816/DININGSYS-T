package com.yqsh.diningsys.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.des.ThreeDESUtil;
import com.yqsh.diningsys.core.util.des.YQSH_SHA1;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgCard;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.deskBusiness.DBSBillServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DBSSeetServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.DgOwLockinfo;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwClearingway;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIModifyService;
import com.yqsh.diningsys.web.service.api.APIReMoService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.api.TableInfoService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgSettlementWayService;
import com.yqsh.diningsys.web.service.base.SysAuthorizationLogService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;

/**
 * Created by yqsh-hs on 2017/10/31.
 * 旺pos对接接口
 *
 * 开台用tableInfo
 * 加单,结账提出
 */
@Controller
@RequestMapping("/yqshapi/posInfo")
@SuppressWarnings("all")
public class APIPosController extends ApiBaseController{

	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;
	@Autowired
	private DgOpenWaterService dgOpenWaterService;
	@Autowired
	private TableInfoService tableInfoService;
	@Autowired
	private APICheckService apiCheckService;
	@Autowired
	private BillService billService;
	@Autowired
	private DgSettlementWayService dgSettlementWayService;
	@Autowired
	private BusinessPermissionService businessPermissionService;
	@Autowired
	private DeskBusinessSettingService deskBusinessSettingService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysAuthorizationLogService sysAuthorizationLogService;
	@Autowired
	private APIModifyService apiModifyService;
	@Autowired
	private APIReMoService apiReMoService;
	/**
	 * 登录
	 */
	@RequestMapping("/login")
	@ResponseBody
	@ApiOperation(value = "登录验证 ", httpMethod = "POST", notes = "登录验证")
	public Object login(
			@ApiParam(required = true, name = "userCode", value = "userCode") @RequestParam(value = "userCode") String userCode,
			@ApiParam(required = true, name = "password", value = "password") @RequestParam(value = "password") String password) {
		try {
			SysUser sysUser = userService.chekUserCode(userCode);
			if(sysUser == null){
				return getFailResult("用户不存在","用户不存在");
			} else {
				if(sysUser.getPassword().equals(getSHA256Str(password).toLowerCase())){
					return getSuccessResult(JSON.toJSONString(sysUser));
				} else {
					return getFailResult("密码不正确","密码不正确");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	/**
	 * 返回所有未开台客位信息
	 */
	@RequestMapping("/getAllSeatInfo")
	@ResponseBody
	@ApiOperation(value = "获取所有客位信息 ", httpMethod = "POST", notes = "获取所有客位信息 ")
	public Object getAllSeatInfo(
			@ApiParam(required = true, name = "pos", value = "pos ID") @RequestParam(value = "pos") Integer pos) {
		try {
			List<DgConsumerSeat> seats = dgConsumerSeatService.getAllSeat(pos);
			return getSuccessResult(seats);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}


	/**
	 * 获取所有结算方式
	 */
	@RequestMapping("/getAllSettlementWay")
	@ResponseBody
	@ApiOperation(value = "获取所有结算方式信息 ", httpMethod = "GET", notes = "获取所有结算信息 ")
	public Object getAllSettlementWay() {
		try {
			List<DgSettlementWay> dsws = dgSettlementWayService.getAllList(new DgSettlementWay());
			return getSuccessResult(dsws);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	@ResponseBody
	@RequestMapping("/unlock")
	@ApiOperation(value = "解锁", httpMethod = "POST", notes = "解锁")
	public Object unlock(
			@ApiParam(required = true, name = "type", value = "解锁的类型，：解除手工锁定  9：解除结算锁定  3：撤销埋单") @RequestParam(value = "type", required = true) Integer type,
			@ApiParam(required = true, name = "seatId", value = "手工锁定的营业流水") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			Map watermap = tableInfoService.selectOpenWaterBySeatIdLastOne(seatId);
			if(watermap.isEmpty()){
				return getResult(APIEnumDefine.S998);
			}
			String openNumber = (String)((Map)watermap.get("water")).get("ow_num");
			DgOpenWater dgOpenWater = apiCheckService.selectOpenWaterObjByOwNum(openNumber);
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


	/**
	 * 返回当前营业单下所有品项明细
	 *
	 * 返回格式：[{itemNum=0501, qc_zt=0, itemName=雪花勇闯, item_file_number=1.0, itemFileName=雪花勇闯, reminder_number=0, is_tc=0, production_costs=0.0, ow_id=34, item_file_id=768, subtotal=10.0, is_price_cal=0, discount=1.0, unit=份, zjf=xhyc, item_final_price=10.0, pxxt=, pxdt=, seatName=002, notes=2, qc_sl=0.0}, {itemNum=0502, qc_zt=0, itemName=雪花脸谱(花旦), item_file_number=1.0, itemFileName=雪花脸谱(花旦), reminder_number=0, is_tc=0, production_costs=0.0, ow_id=34, item_file_id=769, subtotal=20.0, is_price_cal=0, discount=1.0, unit=份, zjf=xhlp(hd), item_final_price=20.0, pxxt=, pxdt=, seatName=002, notes=2, qc_sl=0.0}, {itemNum=0504, zs_production_costs=0.0, zs_subtotal=15.0, qc_zt=0, itemName=雪花晶樽, item_file_number=1.0, itemFileName=雪花晶樽, reminder_number=0, is_tc=0, production_costs=0.0, settlement_status=0, ow_id=35, zs_item_final_price=15.0, item_file_id=771, subtotal=0.0, is_price_cal=0, unit=份, zjf=xhjz, item_final_price=0.0, pxxt=, pxdt=, seatName=002, notes=3, qc_sl=0.0}]
	 */
	@RequestMapping("/getWaterAllItemInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前营业单下所有品项明细", httpMethod = "POST", notes = "返回当前营业单下所有品项明细")
	public Object getWaterAllItemInfo(
			@ApiParam(required = true, name = "seatId", value = "客位号") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			//获取最后一条流水
			Map water = tableInfoService.selectOpenWaterBySeatIdLastOne(seatId);
			if(water.isEmpty()){
				return getResult(APIEnumDefine.S998);
			}
			String owNum = (String)((Map)water.get("water")).get("ow_num");
			List<Map<String, Object>> items = billService
					.getWaterAllItemInfo(owNum);
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	/**
	 * pos加单接口 [{"itemFileId":"品项id","itemFileNumber":"数量"}]
	 *
	 * @param orgs
	 * @param openNumber
	 * @param type
	 * @return
	 */
	@RequestMapping("/addBill")
	@ResponseBody
	@ApiOperation(value = "加单/赠单操作", httpMethod = "POST", notes = "加单/赠单操作")
	public Object addBill(
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "seatId", value = "客位号") @RequestParam(value = "seatId", required = true) String seatId,
			@ApiParam(required = false, name = "operCode", value = "点单员") @RequestParam(value = "operCode", required = false) String operCode,
			@ApiParam(required = true, name = "isGift", value = "是否赠送 0加单  1赠送") @RequestParam(value = "isGift", required = true) String isGift,
			@ApiParam(required = false, name = "remark", value = "备注") @RequestParam(value = "remark", required = false) String remark,
			@ApiParam(required = true, name = "type", value = "加单类型 1/加单并厨打  2/加单不厨打") @RequestParam(value = "type", required = true) String type) {
		try {
			//获取最后一条流水
			Map water = tableInfoService.selectOpenWaterBySeatIdLastOne(seatId);
			if(water.isEmpty()){
				return getResult(APIEnumDefine.S998);
			}
			Integer owState = (Integer)((Map)water.get("water")).get("ow_state");
			String owNum = (String)((Map)water.get("water")).get("ow_num");
			// 判断营业单状态
			if (owState != null && (owState == -1 || owState == 2 || owState == 7 || owState == 6)) {
				return getResult(APIEnumDefine.M0101020);
			}

			if (owState == 3 || owState == 8
					|| owState == 9 || owState == 5) {
				DgOwLockinfo lockInfo = apiCheckService
						.selectOpenWaterLock(owNum);
				if (lockInfo != null) {
					return getResult(APIEnumDefine.S992, lockInfo);
				}
			}

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray pAr = JSONArray.fromObject(orgs);
			for (int i = 0; i < pAr.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = pAr.getJSONObject(i);
				map.put("itemFileId", obj.getInt("itemFileId"));
				map.put("itemFileNumber", obj.getDouble("itemFileNumber"));
				map.put("extra", null);
				list.add(map);
			}
			if(list.isEmpty()){
				return getResult(APIEnumDefine.M0101024);
			}

			Map ret = null;
			if(isGift.equals("1")){
				ret = billService.insertGiveBill(operCode == null ?"yxe_water":operCode, list, owNum,remark);
			} else {
				ret = billService.addBill(operCode, list, owNum, type, remark,true);
			}

			if (ret.containsKey("error")) {
				if (ret.get("error") instanceof String) {
					return getResult(APIEnumDefine.S1000, (String) ret.get("error"));
				} else if (ret.get("error") instanceof APIEnumDefine) {
					return getResult((APIEnumDefine) ret.get("error"));
				} else {
					return getSuccessResult();
				}
			} else {
				return getSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}


	/**
	 * 退单接口 格式[{"itemFileId":"品项id","itemFileNumber":"数量","itemFileZs":"只数",
	 * "serviceId":"服务号id", "backTypeId":"退单原因类型id","backId":"退单原因id"]
	 *
	 * 默认退单原因类型传0
	 *
	 */
	@RequestMapping("/backBill")
	@ResponseBody
	@ApiOperation(value = "退单操作", httpMethod = "POST", notes = "退单操作")
	public Object backBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode") String operCode,
			@ApiParam(required = true, name = "seatId", value = "退单客位id") @RequestParam(value = "seatId") String seatId,
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs") String orgs,
			@ApiParam(required = true, name = "type", value = "加单类型 1/退单并厨打  2/退单不厨打") @RequestParam(value = "type") String type,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			if(!com.alibaba.druid.util.StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"chargeback");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,operCode,sysUser.getEmpCode(),"退单操作")
				);
				operCode = sysUser.getEmpCode();
			}

			Map watermap = tableInfoService.selectOpenWaterBySeatIdLastOne(seatId);
			if(watermap.isEmpty()){
				return getResult(APIEnumDefine.S998);
			}
			String openNumber = (String)((Map)watermap.get("water")).get("ow_num");

			DgOpenWater water = dgOpenWaterService
					.selectByOpenWaterByNum(openNumber);

			if (water.getOwState() == 3 || water.getOwState() == 8
					|| water.getOwState() == 9 || water.getOwState() == 5) {
				DgOwLockinfo lockInfo = apiCheckService
						.selectOpenWaterLock(openNumber);
				if (lockInfo != null) {
					return getResult(APIEnumDefine.S992, lockInfo);
				}
			}

			// 判断营业单状态
			if (water.getOwState() != null && (water.getOwState() == -1 || water.getOwState() == 2 || water.getOwState() == 7 || water.getOwState() == 6)) {
				return getResult(APIEnumDefine.M0101020);
			}

			SysPerBusiness sysPer = businessPermissionService
					.selectBusinessByUserCode(operCode);
			if (sysPer != null) {
				if (type.equals("1")) {
					if (sysPer.getTdQx() == null || sysPer.getTdQx() != 1) {
						return getResult(APIEnumDefine.M0101010);
					}
				} else if (type.equals("2")) {
					if (sysPer.getTdbcdQx() == null || sysPer.getTdbcdQx() != 1) {
						return getResult(APIEnumDefine.M0101011);
					}
				}
			} else {
				return getResult(APIEnumDefine.M0101003);
			}

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray pAr = JSONArray.fromObject(orgs);
			for (int i = 0; i < pAr.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = pAr.getJSONObject(i);
				map.put("itemFileId", obj.getInt("itemFileId"));
				map.put("itemFileNumber", obj.getDouble("itemFileNumber"));
				map.put("serviceId", obj.getInt("serviceId"));
				list.add(map);
			}
			Map ret = billService.insertBackBill(operCode, openNumber, list,
					type);
			if (ret.containsKey("error")
					&& (ret.get("error") instanceof String)) {
				return getResult(APIEnumDefine.S1000, ret.get("error"));
			} else if (ret.containsKey("error")
					&& (ret.get("error") instanceof APIEnumDefine)) {
				return getResult((APIEnumDefine) ret.get("error"));
			} else {
				return getSuccessResult();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}


	/**
	 * 更改桌位
	 */
	@RequestMapping("/changeTable")
	@ResponseBody
	@ApiOperation(value = "更换客位", httpMethod = "POST", notes = "更换客位")
	public Object changeTable(
			@ApiParam(required = true, name = "userCode", value = "营业员code") @RequestParam(value = "userCode", required = false) String userCode,
			@ApiParam(required = true, name = "owTableid", value = "原目标客位") @RequestParam(value = "owTableid", required = false) String owTableid,
			@ApiParam(required = true, name = "tableid", value = "目标客桌ID") @RequestParam(value = "tableid", required = false) String tableid,
			@ApiParam(required = true, name = "isGgFa", value = "是否更改包房收费方案") @RequestParam(value = "isGgFa", required = false) String isGgFa,
			@ApiParam(required = true, name = "isJsBffPx", value = "是否将之前的包房费生存一个品项") @RequestParam(value = "isJsBffPx", required = false) String isJsBffPx,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			//获取最后一条流水
			Map water = tableInfoService.selectOpenWaterBySeatIdLastOne(owTableid);
			if(water.isEmpty()){
				return getResult(APIEnumDefine.S998);
			}
			String ow_num = (String)((Map)water.get("water")).get("ow_num");

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

			SysUser user= userService.chekUserCode(userCode);
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
							apiModifyService.modifySeat(userCode,ow,
									user.getId(),
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

	@ResponseBody
	@RequestMapping(value = "/posPayMoneyCount",method = RequestMethod.POST)
	@ApiOperation(value = "pos结算价钱计算", httpMethod = "POST", notes = "pos结算支付接口")
	public Object posPayMoneyCount(@ApiParam(required = true, name = "seatId", value = "结算客位") @RequestParam(value = "seatId", required = true)String seatId,
								   @ApiParam(required = true, name = "posId", value = "posId") @RequestParam(value = "posId", required = true)Integer posId,
								   @ApiParam(required = true, name = "isPreform", value = "isPreform") @RequestParam(value = "isPreform", required = true)Integer isPreform){
		try {
			Map<String,Object> resMap = new HashMap(); //返回map
			//获取最后一条流水
			Map map = tableInfoService.selectOpenWaterBySeatIdLastOne(seatId);
			if(map.get("water") == null){
				return getResult(APIEnumDefine.S998);
			} else {
				map = (Map) map.get("water");
			}


			DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.valueOf(seatId));

			//判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
			List<Map> maps = apiCheckService.selectIsZyhd();

			List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(map.get("ow_num").toString());

			if(isPreform.equals(0)){
				//自动锁定营业流水为结算锁定
				apiCheckService.modifyOpenWaterLock(dgOpenWaters,"yxe_water",1,posId);
			}

			//用户所属职务的优惠权限数据
			SysPerDiscount sysPerDiscount = businessPermissionService.selectDiscountByUserCode("yxe_water");

			//前台营业设置的账单权限
			DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
			//账单权限
			DBSBillServDTO dbsBillServDTO = new Gson().fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class);
			DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);

			Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
					pxdzZeroMonry = 0.0,//品项打折小计抹零金额
					openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
					openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
					openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
					openWaterPxdzYs = 0.0;//营业流水品项打折收费类型应收金额

			Boolean isAllAdvancePay = false;
			Integer clearingId = null;
			List<Map> map1 = null;
			Map<String,Object> vipInfo = new HashMap<>();

			//循环可以进行买单结算的营业流水
			for(DgOpenWater dgOpenWater:dgOpenWaters) {

				//循环获取所有营业流水下面的所有有效品项按照服务单分组
				List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterWithService(dgOpenWater.getOwNum());

				//重要活动价格，品项打折价格，会员打折价格
				Double standPriceZyhd = 0.0,standPricePxdz = 0.0;

				//先计算出所有品项的小计
				for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
					//开单没有优惠的品项的三种折扣价格，计算制作费用
					if(dgOwConsumerDetails.getIsPriceCal() == 0){
						apiCheckService.getOpenWaterTotalPrice(dgOwConsumerDetails,null,openWaterSeat.getConsArea(),null,null);

						//每一个营业流水下所有品项三种价格累加
						standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount() != null?dgOwConsumerDetails.getZyhdItemCostsSumDiscount():dgOwConsumerDetails.getZyhdItemCostsSum());
						standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount() != null?dgOwConsumerDetails.getPxdzItemCostsSumDiscount():dgOwConsumerDetails.getPxdzItemCostsSum());
					}else if (dgOwConsumerDetails.getIsPriceCal() == 1){ //开单有优惠的品项，直接累加
						//如果品项开单已经有优惠，则将该品项的品项打折设置为该价格
						dgOwConsumerDetails.setPxdzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
						dgOwConsumerDetails.setPxdzItemCostsSum(dgOwConsumerDetails.getSubtotal());

						dgOwConsumerDetails.setZyhdItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
						dgOwConsumerDetails.setZyhdItemCostsSum(dgOwConsumerDetails.getSubtotal());


						if(maps.size() > 0){
							standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
						}
						standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
					}
				}

				//品项信息
				dgOpenWater.setItemFileInfos(dgOwConsumerDetailss);

				dgOpenWater.setZyhdItemSubtotal(standPriceZyhd);
				dgOpenWater.setPxdzItemSubtotal(standPricePxdz);
				dgOpenWater.setHydzItemSubtotal(0.0);

				//算出三种价格，返回服务费
				apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "yxe");

				//计算出最低消费补齐的金额
				if(dgOpenWater.getMinimumConsumption() != null){
					Double minimumConsumption = dgOpenWater.getMinimumConsumption();
					if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
						dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
						dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
					}
					if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
						dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
						dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
					}
				}

				openWaterZyhdSubtotal =  MathExtend.add(MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()),dgOpenWater.getPrivateRoomCost());
				openWaterPxdzSubtotal =  MathExtend.add(MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()),dgOpenWater.getPrivateRoomCost());
			}


			//前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
			if(!dbsBillServDTO.getNoSmallChangeWay().equals("0")){
				if(dbsBillServDTO.getNoSmallChangeWay().equals("1")){
					//抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
					if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
						zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
						zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
						pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);

						openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
						openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
						zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
						zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
					}
				}else if(dbsBillServDTO.getNoSmallChangeWay().equals("2")){//去尾法
					//抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
					if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
						zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
						zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
						zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
						zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
					}
				}else if(dbsBillServDTO.getNoSmallChangeWay().equals("3")){//四舍五入法
//四舍五入法
					//抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
					if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
						zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
						zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
						zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
					}else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
						zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
						pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));

						openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
						openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
					}
				}
			}else{
				openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
				openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
			}

            //2018-12-24 更新品项小计
            for(DgOpenWater dow:dgOpenWaters){
                apiCheckService.updateOpenWaterSubtotal(dow);
            }

			resMap.put("openWaterData",dgOpenWaters);
			//优惠权限相关
			resMap.put("sysPerDiscount",sysPerDiscount);
			resMap.put("otherSetting",dbsBillServDTO);
			//3种价格合计
			resMap.put("pxdzZeroMonry",returnValue(pxdzZeroMonry));
			resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
			resMap.put("openWaterPxdzYs",returnValue(openWaterPxdzYs)+dgOpenWaters.get(0).getServiceCharge());

			if(maps.size() > 0){
				resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
				resMap.put("zyhdZeroMonry",zyhdZeroMonry);
				resMap.put("openWaterZyhdYs",openWaterZyhdYs+dgOpenWaters.get(0).getServiceCharge());
			}
			return getSuccessResult(resMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * posPayMoney pos三方支付
	 * @param openWaterData
	 * @param docws 支付方式列表
	 * @param paidMoney
	 * @param zeroMoney
	 * @param payType
	 * @param priceType 1：品项打折 2：重要活动
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/posPayMoney",method = RequestMethod.POST)
	@ApiOperation(value = "pos", httpMethod = "POST", notes = "易小二支付轮询查询支付状态")
	public synchronized Object posPayMoney(@ApiParam(required = true, name = "openWaterData", value = "openWaterData") @RequestParam(value = "openWaterData", required = true)String openWaterData,
										   @ApiParam(required = true, name = "dgOwClearingways", value = "dgOwClearingways") @RequestParam(value = "dgOwClearingways", required = true)String dgOwClearingways,
										   @ApiParam(required = true, name = "paidMoney", value = "paidMoney") @RequestParam(value = "paidMoney", required = true)String paidMoney,
										   @ApiParam(required = true, name = "zeroMoney", value = "zeroMoney") @RequestParam(value = "zeroMoney", required = true)String zeroMoney,
										   @ApiParam(required = true, name = "priceType", value = "priceType") @RequestParam(value = "priceType", required = true)Integer priceType) {
		String returnData = null;
		try {
			if(StringUtil.isEmpty(openWaterData)){
				return getFailResult("营业流水为空");
			}
			if(StringUtil.isEmpty(paidMoney)){
				return getFailResult("支付金额为空");
			}
			if(StringUtil.isEmpty(zeroMoney)){
				return getFailResult("找零金额为空");
			}

			Gson gson = new Gson();
			//判断结算的营业流水中是否有押金未处理
			List<DgOpenWater> dgOpenWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
			}.getType());

			//获取结算方式列表
			List<DgOwClearingway> docws = new Gson().fromJson(dgOwClearingways, new TypeToken<List<DgOwClearingway>>() {
			}.getType());


			DgOpenWater openWaterMap = dgOpenWaters.get(0);
			String orderWater = openWaterMap.getOwNum();

			List<String> owNums = new ArrayList<>();
			for(int i = 0;i < dgOpenWaters.size();i++){
				owNums.add(dgOpenWaters.get(i).getOwNum());
			}
			if(apiCheckService.modifyCheckOwNumHasDeposit(new Gson().toJson(owNums)) != null){
				return getFailResult("结算的营业流水中有押金未处理，请先处理押金");
			}

			String s = orderWater.split("-")[1].substring(0,6);

			String date  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			String jsNum = tableInfoService.modifyPosPaySuccessState(dgOpenWaters,docws,paidMoney,zeroMoney,date,priceType);

			return getSuccessResult(jsNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getFailResult(returnData);
	}

}
