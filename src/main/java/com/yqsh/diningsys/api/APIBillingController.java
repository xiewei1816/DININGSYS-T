package com.yqsh.diningsys.api;

import com.google.gson.Gson;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysSetting;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.model.deskBusiness.DBSSeetServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.DgOwLockinfo;
import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.archive.*;
import com.yqsh.diningsys.web.service.base.SysAuthorizationLogService;
import com.yqsh.diningsys.web.service.base.SystemSettingService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatItemService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;
import com.yqsh.diningsys.web.service.deskBusiness.ServiceClassService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.sevlet.CacheInit;
import com.yqsh.diningsys.web.util.OnlineHttp;

import lombok.Synchronized;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * 开单相关接口
 *
 * @author yqsh-hs
 */
@RequestMapping("/yqshapi/bill")
@Controller
public class APIBillingController extends ApiBaseController {
	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;
	@Autowired
	private DgConsumptionAreaService dgConsumptionAreaService;
	@Autowired
	private UserService userService;
	@Autowired
	private BillService billService;
	@Autowired
	private DgOpenWaterService dgOpenWaterService;
	@Autowired
	private DgPublicCode0Service dgPublicCode0Service;
	@Autowired
	private DgFlavorService dgFlavorService;
	@Autowired
	private DgSeatItemService dgSeatItemService;
	@Autowired
	private DgServingService dgServingService;
	@Autowired
	private DgProMethodsSerivce dgProMethodsSerivce;
	@Autowired
	private DgProMethodsTypeService dgProMethodsTypeService;
	@Autowired
	private DgItemFileTypeService dgItemFileTypeService;
	@Autowired
	private BusinessPermissionService businessPermissionService;
	@Autowired
	private APICheckService apiCheckService;

	@Autowired
	private DeskBusinessSettingService deskBusinessSettingService;

	@Autowired
	private ServiceClassService serviceClassService;

	@Autowired
	private DgGiftFormService dgGiftFormService;

	@Autowired
	private SysAuthorizationLogService sysAuthorizationLogService;

	/**
	 * 获取所有服务人员信息<br>
	 *
	 * @return 返回所有服务人员信息
	 */
	@RequestMapping("/getAllUserInfo")
	@ResponseBody
	@ApiOperation(value = "获取所有服务人员信息", httpMethod = "GET", notes = "获取所有服务人员信息")
	public Object getUserInfo(
			@ApiParam(required = false, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = false) Integer seatId) {
		SysUser user = new SysUser();
		user.setIsDel("0");
		try {
			List<SysUser> emps = userService.getAllList(user);
			Integer nowBisId = billService.getNowBisId();
			if (nowBisId != null && seatId != null) {
				ServiceClass serviceClass = serviceClassService
						.selectDataByBisIdAndSeatId(nowBisId, seatId);
				if (serviceClass != null) {
					for (SysUser sysUser : emps) {
						if (sysUser.getId() == serviceClass.getWaiterId()) {
							sysUser.setOpenBillDefault(true);
						}
					}
				}
			}
			return getSuccessResult(emps);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 根据桌位id获取自增品项
	 *
	 * @return 根据桌位id获取自增品项
	 */
	@RequestMapping("/getSeatItem")
	@ResponseBody
	@ApiOperation(value = "根据桌位id获取自增品项", httpMethod = "GET", notes = "根据桌位id获取自增品项")
	public Object getSeatItem(
			@ApiParam(required = true, name = "id", value = "桌位id") @RequestParam(value = "id", required = true) String seatId) {
		try {
			List<DgSeatItem> items = dgSeatItemService.getBySeatId(Integer
					.valueOf(seatId));
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取所有加入团队信息<br>
	 * 根据营业单当前营业状态1/3获取<br>
	 * 营业流水状态1:初始化状态、2已经结算、3埋单<br>
	 *
	 * @return 获取所有加入团队信息
	 */
	@RequestMapping("/getAllTeamMemberInfo")
	@ResponseBody
	@ApiOperation(value = "获取所有加入团队信息", httpMethod = "GET", notes = "获取所有加入团队信息")
	public Object getAllTeamMemberInfo(
			@ApiParam(required = true, name = "pos", value = "pos ID") @RequestParam(value = "pos") Integer pos) {
		try {
			List<Map<String, Object>> emps = billService
					.selectAllTeamMember(pos);
			return getSuccessResult(emps);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 开单操作 流程------ 1.根据指定规则生成营业单号 2.初步计算客位服务费/包房费 (按消费比例/时段
	 * 开单先忽略(获取不到最终消费时间/消费小计)) 3。插入营业流水 4.判断座位是否有自增品项 4.1 有就插入服务单,没有就结束 4.2
	 * 计算出每个自增品项的当前消费金额,插入4.1服务单下品项明细单 4.3 更新营业单品项小计/小计
	 *
	 * @param eatNumber
	 *            开单人数
	 *
	 * @param waiterId
	 *            服务人员id
	 *
	 * @param deposit
	 *            押金
	 *
	 * @param marketingId
	 *            营销人员id
	 *
	 * @param seatId
	 *            桌位id
	 *
	 * @param queueNumber
	 *            排队号
	 *
	 * @param teamMemberInfo
	 *            团队标识
	 *
	 * @param openPos
	 *            开单pos
	 *
	 * @param seatLable
	 *            客座标签
	 */
	@RequestMapping("/openBill")
	@ResponseBody
	@ApiOperation(value = "开单操作", httpMethod = "POST", notes = "开单操作")
	public  synchronized Object openBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode", required = true) String operCode,
			@ApiParam(required = false, name = "ydId", value = "预定Id") @RequestParam(value = "ydId", required = false) String ydId,
			@ApiParam(required = true, name = "eatNumber", value = "开单人数") @RequestParam(value = "eatNumber", required = true) String eatNumber,
			@ApiParam(required = true, name = "waiterId", value = "服务人员id") @RequestParam(value = "waiterId", required = true) String waiterId,
			@ApiParam(required = false, name = "deposit", value = "押金") @RequestParam(value = "deposit", required = false) String deposit,
			@ApiParam(required = false, name = "marketingId", value = "营销人员id") @RequestParam(value = "marketingId", required = false) String marketingId,
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = true) String seatId,
			@ApiParam(required = false, name = "queueNumber", value = "排队号") @RequestParam(value = "queueNumber", required = false) String queueNumber,
			@ApiParam(required = false, name = "teamMemberInfo", value = "团队标识") @RequestParam(value = "teamMemberInfo", required = false) String teamMemberInfo,
			@ApiParam(required = true, name = "openPos", value = "开单pos") @RequestParam(value = "openPos", required = true) String openPos,
			@ApiParam(required = false, name = "seatLable", value = "客座标签") @RequestParam(value = "seatLable", required = false) String seatLable,
			@ApiParam(required = false, name = "gradeId", value = "会员id") @RequestParam(value = "gradeId", required = false) String gradeId,
			@ApiParam(required = false, name = "seatNumber", value = "客座号(A/B/c..)") @RequestParam(value = "seatNumber", required = false) String seatNumber) {
		try {
			if (!StringUtils.isEmpty(teamMemberInfo)) {
				SysPerBusiness sysPerBusiness = businessPermissionService
						.selectBusinessByUserCode(operCode);
				if (sysPerBusiness != null
						&& sysPerBusiness.getJrtdQx() == null) {
					return getResult(APIEnumDefine.M0430001);
				}
			}

			Map<String, Object> map = billService.openBill(operCode, eatNumber,
					waiterId, deposit, marketingId, seatId, queueNumber,
					teamMemberInfo, openPos, seatLable, gradeId, ydId);
			if (map.containsKey("success")) {
				// if (map.containsKey("ItemFileError")) {
				// return getSuccessResultExtra((String) map
				// .get("ItemFileError"));
				// } else {
				DgConsumerSeat seat = dgConsumerSeatService
						.selectByPrimaryKey(Integer.valueOf(seatId));
				OnlineHttp.onlineSeatModify(seat.getUuidKey(), "2");
				return getSuccessResult(map.get("water"));
				// }
			} else {
				return getResult((APIEnumDefine) map.get("error"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 开单之前的操作，根据客座ID获取当前客座是否是内部留房
	 *
	 * @return
	 */
	@RequestMapping("/openBillBefore")
	@ResponseBody
	@ApiOperation(value = "开单之前的操作 ", httpMethod = "GET", notes = "获取所有未开台客位信息 ")
	public Object openBillBefore(
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId") Integer seatId) {
		DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService
				.getDeskBusinessSetting();
		DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(
				deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);
		String isRetainRoomReserve = dbsSeetServDTO.getIsRetainRoomReserve();
		DgConsumerSeat dgConsumerSeat = dgConsumerSeatService
				.selectByPrimaryKey(seatId);
		// 不允许内部留房开台，且客座为内部留房，拒绝开台
		if (!isRetainRoomReserve.equals("1")
				&& dgConsumerSeat.getInsetRetentionRoom() == 1) {
			return getResult(APIEnumDefine.M0101022);
		}

		return getSuccessResult(dbsSeetServDTO);
	}

	/**
	 * 返回所有未开台客位信息
	 */
	@RequestMapping("/getAllFreeSeatInfo")
	@ResponseBody
	@ApiOperation(value = "获取所有未开台客位信息 ", httpMethod = "GET", notes = "获取所有未开台客位信息 ")
	public Object getAllFreeSeatInfo(
			@ApiParam(required = true, name = "pos", value = "pos ID") @RequestParam(value = "pos") Integer pos) {
		try {
			List<DgConsumerSeat> seats = dgConsumerSeatService
					.getAllFreeSeat(pos);
			return getSuccessResult(seats);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回所有消费区域
	 */
	@RequestMapping("/getAllAreaInfo")
	@ResponseBody
	@ApiOperation(value = "获取所以消费区域", httpMethod = "GET", notes = "获取所以消费区域 ")
	public Object getAllAreaInfo() {
		try {
			List<DgConsumptionArea> areas = dgConsumptionAreaService
					.getAllList(null);
			return getSuccessResult(areas);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取所有客座号<br>
	 * 取值字典表
	 *
	 * @return 获取所有客座号
	 */
	@RequestMapping("/getAllSeatNumber")
	@ResponseBody
	@ApiOperation(value = "获取所有客座号", httpMethod = "GET", notes = "获取所有客座号")
	public Object getAllSeatNumber() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cParent", 239);
			map.put("iDelFlg", 0);
			List<DgPublicCode0> emps = dgPublicCode0Service.selectSmallDpc(map);
			return getSuccessResult(emps);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	// /**
	// * 开单操作 流程------ 1.根据指定规则生成营业单号 2.初步计算客位服务费/包房费 (按消费比例/时段
	// * 开单先忽略(获取不到最终消费时间/消费小计)) 3。插入营业流水 4.判断座位是否有自增品项 4.1 有就插入服务单,没有就结束 4.2
	// * 计算出每个自增品项的当前消费金额,插入4.1服务单下品项明细单 4.3 更新营业单品项小计/小计
	// *
	// * @param eatNumber 开单人数
	// * @param waiterId 服务人员id
	// * @param deposit 押金
	// * @param marketingId 营销人员id
	// * @param seatId 桌位id
	// * @param queueNumber 排队号
	// * @param teamMemberInfo 团队标识
	// * @param openPos 开单pos
	// * @param seatLable 客座标签
	// */
	// @RequestMapping("/openBill")
	// @ResponseBody
	// @ApiOperation(value = "开单操作", httpMethod = "POST", notes = "开单操作")
	// public Object openBill(
	// @ApiParam(required = true, name = "operCode", value = "操作人员代码")
	// @RequestParam(value = "operCode", required = true) String operCode,
	// @ApiParam(required = true, name = "eatNumber", value = "开单人数")
	// @RequestParam(value = "eatNumber", required = true) String eatNumber,
	// @ApiParam(required = true, name = "waiterId", value = "服务人员id")
	// @RequestParam(value = "waiterId", required = true) String waiterId,
	// @ApiParam(required = false, name = "deposit", value = "押金")
	// @RequestParam(value = "deposit", required = false) String deposit,
	// @ApiParam(required = false, name = "marketingId", value = "营销人员id")
	// @RequestParam(value = "marketingId", required = false) String
	// marketingId,
	// @ApiParam(required = true, name = "seatId", value = "桌位id")
	// @RequestParam(value = "seatId", required = true) String seatId,
	// @ApiParam(required = false, name = "queueNumber", value = "排队号")
	// @RequestParam(value = "queueNumber", required = false) String
	// queueNumber,
	// @ApiParam(required = false, name = "teamMemberInfo", value = "团队标识")
	// @RequestParam(value = "teamMemberInfo", required = false) String
	// teamMemberInfo,
	// @ApiParam(required = true, name = "openPos", value = "开单pos")
	// @RequestParam(value = "openPos", required = true) String openPos,
	// @ApiParam(required = false, name = "seatLable", value = "客座标签")
	// @RequestParam(value = "seatLable", required = false) String seatLable,
	// @ApiParam(required = false, name = "gradeId", value = "会员id")
	// @RequestParam(value = "gradeId", required = false) String gradeId,
	// @ApiParam(required = false, name = "seatNumber", value = "客座号(A/B/c..)")
	// @RequestParam(value = "seatNumber", required = false) String seatNumber)
	// {
	//
	// try {
	// Map<String, Object> map = billService.openBill(operCode, eatNumber,
	// waiterId, deposit, marketingId, seatId, queueNumber,
	// teamMemberInfo, openPos, seatLable, gradeId);
	// if (map.containsKey("success")) {
	// // if (map.containsKey("ItemFileError")) {
	// // return getSuccessResultExtra((String) map
	// // .get("ItemFileError"));
	// // } else {
	// return getSuccessResult(map.get("water"));
	// // }
	// } else {
	// return getResult((APIEnumDefine) map.get("error"));
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// return getExceptionResult();
	// }
	// }

	/*
	 * 
	 * orgs 参数为json数组
	 * 格式{"openPos":"7","body":[{"seatId":"28","eatNumber":"8","waiterId":"4"},
	 * {"seatId":"29","eatNumber":"8","waiterId":"4"}]} 流程
	 * 1.解析出json对象,用开单pos获取组织id,生存营业流水号和团队流水号 2.对每隔对象做如下处理 ( * 2.1.初步计算客位服务费/包房费
	 * (按消费比例/时段 开单先忽略(获取不到最终消费时间/消费小计)) 2.2。插入营业流水 2.3.判断座位是否有自增品项 2.4
	 * 有就插入服务单,没有就结束 2.4.1 计算出每个自增品项的当前消费金额,插入2.4.1服务单下品项明细单 2.4.2 更新营业单品项小计/小计
	 * )
	 */

	@RequestMapping("/openBillTeam")
	@ResponseBody
	@ApiOperation(value = "团队开单操作", httpMethod = "POST", notes = "团队开单操作")
	public synchronized Object openBillTeam(
			@ApiParam(required = true, name = "openPos", value = "开单pos") @RequestParam(value = "openPos", required = true) String openPos,
			@ApiParam(required = false, name = "ydId", value = "预定Id") @RequestParam(value = "ydId", required = false) String ydId,
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode", required = true) String operCode,
			@ApiParam(required = true, name = "orgs", value = "团队开单json参数") @RequestParam(value = "orgs", required = true) String orgs) {
		try {
			List<Map<String, Object>> listOrgs = new ArrayList<Map<String, Object>>();
			JSONArray ar = JSONArray.fromObject(orgs);
			for (int i = 0; i < ar.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = ar.getJSONObject(i);
				map.put("eatNumber", obj.get("eatNumber"));
				if ((obj.get("waiterId") instanceof JSONNull)) {
					map.put("waiterId", "0");
				} else {
					map.put("waiterId", obj.get("waiterId"));
				}
				map.put("seatId", obj.get("seatId"));
				listOrgs.add(map);
			}
			Map<String, Object> map = billService.openBillTeam(operCode,
					openPos, listOrgs, ydId);
			if (map.containsKey("success")) {
				// if (map.containsKey("ItemFileError")) {
				// return getSuccessResultExtra((String) map
				// .get("ItemFileError"));
				// } else {
				for(int i=0;i<listOrgs.size();i++){
					DgConsumerSeat seat = dgConsumerSeatService
							.selectByPrimaryKey((int)listOrgs.get(i).get("seatId"));
					OnlineHttp.onlineSeatModify(seat.getUuidKey(), "2");
				}
				return getSuccessResult();
				// }
			} else {
				return getResult((APIEnumDefine) map.get("error"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	@RequestMapping("/changeTable")
	@ResponseBody
	@ApiOperation(value = "更换客位", httpMethod = "POST", notes = "根据营业单号更换客桌")
	public Object changeTable(
			HttpServletRequest request,
			@ApiParam(required = false, name = "userid", value = "营业员ID") @RequestParam(value = "userid", required = false) String userid,
			@ApiParam(required = false, name = "ow_num", value = "营业单号") @RequestParam(value = "ow_num", required = false) String ow_num,
			@ApiParam(required = false, name = "tableid", value = "目标客桌ID") @RequestParam(value = "tableid", required = false) String tableid,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {

		try {
			if(!StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"repalce_modify_rmRoundtrip");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
			}
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 测试单个品项当前价格
	 *
	 * @return 测试单个品项当前价格
	 */
	@RequestMapping("/getItemNowPrice")
	@ResponseBody
	@ApiOperation(value = "测试打折方案价格", httpMethod = "GET", notes = "测试打折方案价格")
	public Object getItemNowPrice(
			@ApiParam(required = false, name = "itemId", value = "品项id") @RequestParam(value = "itemId", required = false) String itemId) {
		try {
			Map<Integer, Object> emps = billService.getPxDzFaPrice(Integer
					.valueOf(itemId));
			return getSuccessResult(emps);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回所有品项信息
	 */
	@RequestMapping("/getAllItemInfo")
	@ResponseBody
	@ApiOperation(value = "获取所有品项信息", httpMethod = "GET", notes = "获取所有品项信息")
	public Object getAllItemInfo(
			@ApiParam(required = true, name = "seatId", value = "客位id") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			List<Map<String, Object>> items = billService.selectAllItem(Integer
					.valueOf(seatId));
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回所有品项信息 (按品项小类分组)
	 */
	@RequestMapping("/selectAllItemGroupByXl")
	@ResponseBody
	@ApiOperation(value = "获取所有品项信息(按品项小类分组)", httpMethod = "GET", notes = "获取所有品项信息(按品项小类分组)")
	public Object selectAllItemGroupByXl(
			@ApiParam(required = true, name = "seatId", value = "客位id") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			List<Map> items = billService.selectAllItemGroupByXl(Integer
					.valueOf(seatId));
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 根据套餐id获取套餐明细
	 */
	@RequestMapping("/getAllTcInfo")
	@ResponseBody
	@ApiOperation(value = "根据套餐id获取套餐明细", httpMethod = "GET", notes = "根据套餐id获取套餐明细")
	public Object getAllTcInfo(
			@ApiParam(required = true, name = "id", value = "套餐id") @RequestParam(value = "id", required = true) String id) {
		try {
			Map<String, Object> items = billService.selectTcDetail(Integer
					.valueOf(id));
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回所有口味信息 或要求
	 */
	@RequestMapping("/getAllKgInfo")
	@ResponseBody
	@ApiOperation(value = "返回所有口味信息或要求", httpMethod = "GET", notes = "返回所有口味信息或要求")
	public Object getAllKgYqInfo(
			@ApiParam(required = true, name = "id", value = "查询口味或要求1/口味 2/要求") @RequestParam(value = "id", required = true) String id) {
		try {
			List<DgFlavor> items = dgFlavorService.getFlavorByParentId(Integer
					.valueOf(id));
			for (DgFlavor item : items) {
				List<DgFlavor> childItems = dgFlavorService
						.getFlavorByParentId(item.getId());
				item.setChild(childItems);
			}
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取赠单原因相关权限
	 */
	@ResponseBody
	@RequestMapping("/getFreeReason")
	@ApiOperation(value = "获取赠单原因相关权限", httpMethod = "GET", notes = "获取赠单原因相关权限")
	public Object getFreeReason() {
		try {
			Map<String, Object> map = new HashMap<>();
			DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService
					.getDeskBusinessSetting();
			DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(
					deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);
			List<DgGiftFormReason> allGiftFormReason = dgGiftFormService
					.getAllGiftFormReason();
			map.put("useReason", dbsSeetServDTO.getIsUseGiftOrderReason());
			map.put("mustUseReason",
					dbsSeetServDTO.getIsUseGiftOrderReasonNeed());
			map.put("allGiftFormReason", allGiftFormReason);
			return getSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getFailResult();
	}

	/**
	 * 获取所有上菜状态
	 */
	@RequestMapping("/getAllServingInfo")
	@ResponseBody
	@ApiOperation(value = "获取所有上菜状态", httpMethod = "GET", notes = "获取所有上菜状态")
	public Object getAllServingInfo() {
		try {
			List<DgServing> items = dgServingService.getAll();
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取所有制作方法
	 */
	@RequestMapping("/getAllProMethodsInfo")
	@ResponseBody
	@ApiOperation(value = "获取所有制作方法", httpMethod = "GET", notes = "获取所有制作方法")
	public Object getAllProMethodsInfo() {
		try {
			List<DgProMethodsType> items = dgProMethodsTypeService
					.selelctAllTypes();
			for (DgProMethodsType t : items) {
				List<DgProMethods> methods = dgProMethodsSerivce
						.getProMethodsByType(null, t.getId());
				t.setMethods(methods);
			}
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 增加套餐(宴会类别时)品项界面左侧树
	 */
	@RequestMapping("/getItemTypeTree")
	@ResponseBody
	@ApiOperation(value = "套餐(宴会类别时)品项界面左侧树", httpMethod = "GET", notes = "套餐(宴会类别时)品项界面左侧树")
	public Object getItemTypeTree() {
		try {
			Map<String, Object> ret = new HashMap<String, Object>();
			ret.put("name", "品项类别");
			List<DgItemFileType> bTypes = dgItemFileTypeService
					.selectAllBigType();
			for (DgItemFileType b : bTypes) {
				List<DgItemFileType> sTypes = dgItemFileTypeService
						.getItemFileTypeByParent(b.getId());
				b.setSmallItemTypes(sTypes);
			}
			ret.put("smallItemTypes", bTypes);
			return getSuccessResult(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	// /**
	// * 加单接口 格式[{"itemFileId":"品项id","itemFileNumber":"数量","itemFileZs":"只数",
	// * "productionCosts":"制作费用",
	// * "servingType":"上菜状态","servingTypeGlobal":"上菜状态是否为全局"
	// * ,"expectationsServingTime":"期望上菜时间","isGift":"1","extra":[{
	// * "otype":"1、口味。2要求。3制作方法", "ocode":"当为制作方法时，前台修改的制作费用", "oname":"制作",
	// * "ocosts":"零时制作费用", "zzffSf":"制作方法是否收费", "zzffSfType":"1按品项  2按品项数量"
	// }]}]
	// */
	// @RequestMapping("/addBill")
	// @ResponseBody
	// @ApiOperation(value = "加单操作", httpMethod = "GET", notes = "加单操作")
	// public Object addBill(
	// @ApiParam(required = true, name = "operCode", value = "操作人员代码")
	// @RequestParam(value = "operCode", required = true) String operCode,
	// @ApiParam(required = true, name = "orgs", value = "加单json参数")
	// @RequestParam(value = "orgs", required = true) String orgs,
	// @ApiParam(required = true, name = "openNumber", value = "营业流水号")
	// @RequestParam(value = "openNumber", required = true) String openNumber,
	// @ApiParam(required = false, name = "zdbz", value = "整单备注")
	// @RequestParam(value = "zdbz", required = false) String zdbz,
	// @ApiParam(required = true, name = "type", value =
	// "加单类型 1/加单并厨打  2/加单不厨打") @RequestParam(value = "type", required = true)
	// String type) {
	// try {
	// DgOpenWater water = dgOpenWaterService
	// .selectByOpenWaterByNum(openNumber);
	//
	// if (water.getOwState() == 3 || water.getOwState() == 8
	// || water.getOwState() == 9) {
	// DgOwLockinfo lockInfo = apiCheckService
	// .selectOpenWaterLock(openNumber);
	// if (lockInfo != null) {
	// return getResult(APIEnumDefine.S992, lockInfo);
	// }
	// }
	//
	//
	// // 判断营业单状态
	// if (water.getOwState() != null && water.getOwState() != 1) {
	// return getResult(APIEnumDefine.M0101020);
	// }
	//
	//
	// // 判断权限
	// SysPerBusiness sysPer = businessPermissionService
	// .selectBusinessByUserCode(operCode);
	// if (sysPer != null) {
	// if (type.equals("2")) {
	// if (sysPer.getJdbcdQx() == null || sysPer.getJdbcdQx() != 1) {
	// return getResult(APIEnumDefine.M0101009);
	// }
	// }
	// } else {
	// return getResult(APIEnumDefine.M0101003);
	// }
	//
	//
	// List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	// JSONArray pAr = JSONArray.fromObject(orgs);
	// for (int i = 0; i < pAr.size(); i++) {
	// Map<String, Object> map = new HashMap<String, Object>();
	// JSONObject obj = pAr.getJSONObject(i);
	// map.put("itemFileId", obj.getInt("itemFileId"));
	// map.put("itemFileNumber", obj.getDouble("itemFileNumber"));
	// if (obj.containsKey("itemFileZs")
	// &&!(obj.get("itemFileZs") instanceof JSONNull)) {
	// map.put("itemFileZs", obj.getDouble("itemFileZs"));
	// }
	// if (obj.containsKey("productionCosts")
	// &&!(obj.get("productionCosts") instanceof JSONNull)) {
	// map.put("productionCosts", obj.getDouble("productionCosts"));
	// }
	// if (obj.containsKey("servingType")
	// &&!(obj.get("servingType") instanceof JSONNull)) {
	// map.put("servingType", obj.getInt("servingType"));
	// }
	// if (obj.containsKey("isGift")
	// && !(obj.get("isGift") instanceof JSONNull)) {
	// // 是否赠送
	// map.put("isGift", obj.getInt("isGift"));
	// }
	// if (obj.containsKey("servingTypeGlobal")
	// &&!(obj.get("servingTypeGlobal") instanceof JSONNull)) {
	// map.put("servingTypeGlobal",
	// obj.getInt("servingTypeGlobal"));
	// }
	// if (obj.containsKey("expectationsServingTime")
	// &&!(obj.get("expectationsServingTime") instanceof JSONNull)) {
	// map.put("expectationsServingTime",
	// obj.getString("expectationsServingTime"));
	// }
	// JSONArray cAr = JSONArray.fromObject(obj.get("extra"));
	// if (cAr.size() > 0) {
	// List<Map<String, Object>> cms = new ArrayList<Map<String, Object>>();
	// for (int j = 0; j < cAr.size(); j++) {
	// JSONObject o = cAr.getJSONObject(j);
	// Map<String, Object> cmap = new HashMap<String, Object>();
	// cmap.put("otype", o.getInt("otype"));
	// if (!(o.get("ocode") instanceof JSONNull)) {
	// cmap.put("ocode", o.getString("ocode"));
	// }
	// if (!(o.get("oname") instanceof JSONNull)) {
	// cmap.put("oname", o.getString("oname"));
	// }
	// if (o.containsKey("ocosts")
	// && !(o.get("ocosts") instanceof JSONNull)) {
	// cmap.put("ocosts", o.getDouble("ocosts"));
	// }
	// if (o.containsKey("zzffSfType")
	// && !(o.get("zzffSfType") instanceof JSONNull)) {
	// cmap.put("zzffSf", o.getInt("zzffSf"));
	// }
	// if (o.containsKey("zzffSfType")
	// && !(o.get("zzffSfType") instanceof JSONNull)) {
	// cmap.put("zzffSfType", o.getInt("zzffSfType"));
	// }
	// cms.add(cmap);
	// }
	// map.put("extra", cms);
	// } else {
	// map.put("extra", null);
	// }
	// list.add(map);
	// }
	// Map ret = billService.addBill(operCode, list, openNumber,
	// type,zdbz,false);
	// if (ret.containsKey("error")) {
	// return getResult(APIEnumDefine.S1000,(String) ret.get("error"));
	// } else {
	// return getSuccessResult();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// return getExceptionResult();
	// }
	// }

	/**
	 * 团队加单接口 格式[{"itemFileId":"品项id","itemFileNumber":"数量","itemFileZs":"只数",
	 * "productionCosts":"制作费用",
	 * "servingType":"上菜状态","servingTypeGlobal":"上菜状态是否为全局"
	 * ,"expectationsServingTime":"期望上菜时间","isGift":"1","extra":[{
	 * "otype":"1、口味。2要求。3制作方法", "ocode":"当为制作方法时，前台修改的制作费用", "oname":"制作",
	 * "ocosts":"零时制作费用", "zzffSf":"制作方法是否收费", "zzffSfType":"0按品项  1按品项数量" }]}]
	 */
	@RequestMapping("/addBill")
	@ResponseBody
	@ApiOperation(value = "加单操作", httpMethod = "GET", notes = "加单操作")
	public Object addBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode", required = true) String operCode,
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "openNumberIds", value = "营业流水号(可以是组合逗号分割)") @RequestParam(value = "openNumberIds", required = true) String openNumberIds,
			@ApiParam(required = false, name = "zdbz", value = "整单备注") @RequestParam(value = "zdbz", required = false) String zdbz,
			@ApiParam(required = true, name = "type", value = "加单类型 1/加单并厨打  2/加单不厨打") @RequestParam(value = "type", required = true) String type,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			List<String> idList = new ArrayList<String>();
			Collections.addAll(idList, openNumberIds.split(","));
			for (String id : idList) {
				DgOpenWater water = dgOpenWaterService
						.selectByOpenWaterById(id);
				if (water.getOwState() == 3 || water.getOwState() == 8
						|| water.getOwState() == 9 || water.getOwState() == 5) {
					DgOwLockinfo lockInfo = apiCheckService
							.selectOpenWaterLock(water.getOwNum());
					if (lockInfo != null) {
						return getResult(APIEnumDefine.S992, lockInfo);
					}
				}
				// 判断营业单状态
				if (water.getOwState() != null && (water.getOwState() == -1 || water.getOwState() == 2 || water.getOwState() == 7 || water.getOwState() == 6)) {
					return getResult(APIEnumDefine.M0101020);
				}
			}

			if(StringUtils.isEmpty(authCode)){//授权码为空
				// 判断权限
				SysPerBusiness sysPer = businessPermissionService
						.selectBusinessByUserCode(operCode);
				if (sysPer != null) {
					if (type.equals("2")) {
						if (sysPer.getJdbcdQx() == null || sysPer.getJdbcdQx() != 1) {
							return getResult(APIEnumDefine.M0101009);
						}
					} else if (type.equals("1")) {
						if (sysPer.getYxjdQx() == null || sysPer.getYxjdQx() != 1) {
							return getResult(APIEnumDefine.M0101008);
						}
					}
				} else {
					return getResult(APIEnumDefine.M0101003);
				}
			} else {
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,operCode,sysUser.getEmpCode(),"赠单操作")
				);
				operCode = sysUser.getEmpCode();
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray pAr = JSONArray.fromObject(orgs);
			for (int i = 0; i < pAr.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = pAr.getJSONObject(i);
				map.put("itemFileId", obj.getInt("itemFileId"));
				map.put("itemFileNumber", obj.getDouble("itemFileNumber"));
				if (!(obj.get("itemFileZs") instanceof JSONNull)) {
					map.put("itemFileZs", obj.getDouble("itemFileZs"));
				}
				if (!(obj.get("productionCosts") instanceof JSONNull)) {
					map.put("productionCosts", obj.getDouble("productionCosts"));
				}
				if (!(obj.get("servingType") instanceof JSONNull)) {
					map.put("servingType", obj.getInt("servingType"));
				}
				if (obj.containsKey("isGift")
						&& !(obj.get("isGift") instanceof JSONNull)) {
					// 是否赠送
					map.put("isGift", obj.getInt("isGift"));
				}
				if (!(obj.get("servingTypeGlobal") instanceof JSONNull)) {
					map.put("servingTypeGlobal",
							obj.getInt("servingTypeGlobal"));
				}
				if (!(obj.get("expectationsServingTime") instanceof JSONNull)) {
					map.put("expectationsServingTime",
							obj.getString("expectationsServingTime"));
				}
				JSONArray cAr = JSONArray.fromObject(obj.get("extra"));
				if (cAr.size() > 0) {
					List<Map<String, Object>> cms = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < cAr.size(); j++) {
						JSONObject o = cAr.getJSONObject(j);
						Map<String, Object> cmap = new HashMap<String, Object>();
						cmap.put("otype", o.getInt("otype"));
						if (!(o.get("ocode") instanceof JSONNull)) {
							cmap.put("ocode", o.getString("ocode"));
						}
						if (!(o.get("oname") instanceof JSONNull)) {
							cmap.put("oname", o.getString("oname"));
						}
						if (o.containsKey("ocosts")
								&& !(o.get("ocosts") instanceof JSONNull)) {
							cmap.put("ocosts", o.getDouble("ocosts"));
						}
						if (o.containsKey("zzffSfType")
								&& !(o.get("zzffSfType") instanceof JSONNull)) {
							cmap.put("zzffSf", o.getInt("zzffSf"));
						}
						if (o.containsKey("zzffSfType")
								&& !(o.get("zzffSfType") instanceof JSONNull)) {
							cmap.put("zzffSfType", o.getInt("zzffSfType"));
						}
						cms.add(cmap);
					}
					map.put("extra", cms);
				} else {
					map.put("extra", null);
				}
				list.add(map);
			}

			Map ret = billService.addTeamBill(operCode, list, idList, zdbz,
					false, type,authCode);
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
	 * 赠单接口 格式[{"itemFileId":"品项id","itemFileNumber":"数量","itemFileZs":"只数",
	 * "productionCosts":"制作费用",
	 * "servingType":"上菜状态","servingTypeGlobal":"上菜状态是否为全局"
	 * ,"expectationsServingTime":"期望上菜时间", "extra":[{ "otype":"1、口味。2要求。3制作方法",
	 * "ocode":"当为制作方法时，前台修改的制作费用", "oname":"制作", "ocosts":"零时制作费用",
	 * "zzffSf":"制作方法是否收费", "zzffSfType":"1按品项  2按品项数量" }]}]
	 *
	 */
	@RequestMapping("/giveBill")
	@ResponseBody
	@ApiOperation(value = "赠单操作", httpMethod = "GET", notes = "赠单操作")
	public Object giveBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode", required = true) String operCode,
			@ApiParam(required = true, name = "orgs", value = "赠单json参数") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "openNumber", value = "营业流水号") @RequestParam(value = "openNumber", required = true) String openNumber,
			@ApiParam(required = false, name = "zdbz", value = "整单备注") @RequestParam(value = "zdbz", required = false) String zdbz) {
		try {
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

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			JSONArray pAr = JSONArray.fromObject(orgs);
			for (int i = 0; i < pAr.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = pAr.getJSONObject(i);
				map.put("itemFileId", obj.getInt("itemFileId"));
				map.put("itemFileNumber", obj.getDouble("itemFileNumber"));
				if (!(obj.get("itemFileZs") instanceof JSONNull)) {
					map.put("itemFileZs", obj.getDouble("itemFileZs"));
				}
				if (!(obj.get("productionCosts") instanceof JSONNull)) {
					map.put("productionCosts", obj.getDouble("productionCosts"));
				}
				if (!(obj.get("servingType") instanceof JSONNull)) {
					map.put("servingType", obj.getInt("servingType"));
				}
				if (!(obj.get("servingTypeGlobal") instanceof JSONNull)) {
					map.put("servingTypeGlobal",
							obj.getInt("servingTypeGlobal"));
				}
				if (!(obj.get("expectationsServingTime") instanceof JSONNull)) {
					map.put("expectationsServingTime",
							obj.getString("expectationsServingTime"));
				}
				JSONArray cAr = JSONArray.fromObject(obj.get("extra"));
				if (cAr.size() > 0) {
					List<Map<String, Object>> cms = new ArrayList<Map<String, Object>>();
					for (int j = 0; j < cAr.size(); j++) {
						JSONObject o = cAr.getJSONObject(j);
						Map<String, Object> cmap = new HashMap<String, Object>();
						cmap.put("otype", o.getInt("otype"));
						if (!(o.get("ocode") instanceof JSONNull)) {
							cmap.put("ocode", o.getString("ocode"));
						}
						if (!(o.get("oname") instanceof JSONNull)) {
							cmap.put("oname", o.getString("oname"));
						}
						if (o.containsKey("ocosts")
								&& !(o.get("ocosts") instanceof JSONNull)) {
							cmap.put("ocosts", o.getDouble("ocosts"));
						}
						if (o.containsKey("zzffSf")
								&& !(o.get("zzffSf") instanceof JSONNull)) {
							cmap.put("zzffSf", o.getInt("zzffSf"));
						}
						if (o.containsKey("zzffSfType")
								&& !(o.get("zzffSfType") instanceof JSONNull)) {
							cmap.put("zzffSfType", o.getInt("zzffSfType"));
						}
						cms.add(cmap);
					}
					map.put("extra", cms);
				} else {
					map.put("extra", null);
				}
				list.add(map);
			}
			Map map = billService.insertGiveBill(operCode, list, openNumber,zdbz);
			if (map.containsKey("error")) {
				return getResult((APIEnumDefine) map.get("error"));
			} else {
				return getSuccessResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回所有退单原因类型/原因
	 * <p>
	 * 2017年2月21日10:19:55 添加前台营业设置退单相关权限
	 */
	@RequestMapping("/getAllBackBillInfo")
	@ResponseBody
	@ApiOperation(value = "返回所有退单原因类型/原因 ", httpMethod = "GET", notes = "返回所有退单原因类型/原因")
	public Map<String, Object> getAllBackBillInfo() {
		try {
			Map<String, Object> map = new HashMap<>();
			List<TbRfct> tbRfcts = billService.selectAllBackBillInfo();
			DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService
					.getDeskBusinessSetting();
			map.put("tbRfcts", tbRfcts);
			map.put("seatServ", new Gson().fromJson(
					deskBusinessSetting.getSeatServ(), Map.class));
			return getSuccessResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回当前营业单下所有品项明细
	 *
	 */
	@RequestMapping("/getWaterAllItemInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前营业单下所有品项明细", httpMethod = "GET", notes = "返回当前营业单下所有品项明细")
	public Object getWaterAllItemInfo(
			@ApiParam(required = true, name = "waterNum", value = "营业流水号") @RequestParam(value = "waterNum", required = true) String waterNum) {
		try {
			List<Map<String, Object>> items = billService
					.getWaterAllItemInfo(waterNum);
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回当前营业单下团队共有菜品的明细列表
	 *
	 */
	@RequestMapping("/getTeamBackItemInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前营业单下团队共有菜品的明细列表", httpMethod = "GET", notes = "返回当前营业单下团队共有菜品的明细列表")
	public Object getTeamBackItemInfo(
			@ApiParam(required = true, name = "teamMember", value = "团队号") @RequestParam(value = "teamMember", required = true) String teamMember,
			@ApiParam(required = false, name = "seatIds", value = "筛选桌位id组合   1,2,3") @RequestParam(value = "seatIds", required = false) String seatIds) {
		try {
			if (seatIds == null) {
				List<Map<String, Object>> items = billService
						.getTeamBackItemInfo(teamMember, null);
				return getSuccessResult(items);
			} else {
				List<String> seatIdList = new ArrayList<String>();
				Collections.addAll(seatIdList, seatIds.split(","));
				List<Map<String, Object>> items = billService
						.getTeamBackItemInfo(teamMember, seatIdList);
				return getSuccessResult(items);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回当前营业单下团队所有桌位信息
	 *
	 */
	@RequestMapping("/getTeamSeatItemInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前营业单下团队所有桌位信息", httpMethod = "GET", notes = "返回当前营业单下团队所有桌位信息")
	public Object getTeamSeatItemInfo(
			@ApiParam(required = true, name = "teamMember", value = "团队号") @RequestParam(value = "teamMember", required = true) String teamMember) {
		try {
			List<DgConsumerSeat> items = billService
					.selectTeamSeatMember(teamMember);
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回当前营业单下团队(初始化状态-未埋单/结账)所有流水信息
	 *
	 */
	@RequestMapping("/getTeamMemberInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前营业单下团队(初始化状态-未埋单/结账)所有流水信息", httpMethod = "GET", notes = "返回当前营业单下团队(初始化状态-未埋单/结账)所有流水信息")
	public Object getTeamMemberInfo(
			@ApiParam(required = true, name = "teamMember", value = "团队号") @RequestParam(value = "teamMember", required = true) String teamMember,
			@ApiParam(required = false, name = "ids", value = "要加入的营业流水号id组合   1,2,3") @RequestParam(value = "ids", required = false) String ids) {
		try {
			if (ids == null) {
				List<Map<String, Object>> items = billService
						.getTeamMemberInfo(teamMember, null);
				return getSuccessResult(items);
			} else {
				List owIds = new ArrayList();
				Collections.addAll(owIds, ids.split(","));
				List<Map<String, Object>> items = billService
						.getTeamMemberInfo(teamMember, owIds);
				return getSuccessResult(items);
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
	@ApiOperation(value = "退单操作", httpMethod = "GET", notes = "退单操作")
	public Object backBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode") String operCode,
			@ApiParam(required = true, name = "openNumber", value = "营业流水号") @RequestParam(value = "openNumber") String openNumber,
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs") String orgs,
			@ApiParam(required = true, name = "type", value = "加单类型 1/退单并厨打  2/退单不厨打") @RequestParam(value = "type") String type,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(required = false,value = "authCode") String authCode) {
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
				if (!(obj.get("itemFileZs") instanceof JSONNull)) {
					map.put("itemFileZs", obj.getDouble("itemFileZs"));
				}
				if (!(obj.get("serviceId") instanceof JSONNull)) {
					map.put("serviceId", obj.getInt("serviceId"));
				}
				if (!(obj.get("backTypeId") instanceof JSONNull)) {
					map.put("backTypeId", obj.getInt("backTypeId"));
				}
				if (!(obj.get("backId") instanceof JSONNull)) {
					map.put("backId", obj.getInt("backId"));
				}
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
	 * 团队退单接口 格式[{"itemFileId":"品项id","itemFileNumber":"数量","itemFileZs":"只数",
	 * "backTypeId":"退单原因类型id","backId":"退单原因id"] 默认退单原因类型传0
	 *
	 *
	 * seatIds 1,2,3,4
	 */
	@RequestMapping("/backTeamBill")
	@ResponseBody
	@ApiOperation(value = "团队退单接口 ", httpMethod = "GET", notes = "团队退单接口 ")
	public Object backTeamBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode", required = true) String operCode,
			@ApiParam(required = true, name = "teamMember", value = "团队号") @RequestParam(value = "teamMember", required = true) String teamMember,
			@ApiParam(required = true, name = "seatIds", value = "退单桌位id组合") @RequestParam(value = "seatIds", required = true) String seatIds,
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "type", value = "加单类型 1/退单并厨打  2/退单不厨打") @RequestParam(value = "type", required = true) String type,
			@ApiParam(required = false, name = "authCode", value = "授权码") @RequestParam(value = "authCode", required = false) String authCode) {
		try {
			if(!com.alibaba.druid.util.StringUtils.isEmpty(authCode)){
				SysUser sysUser = userService.selectUserByAuthCode(authCode);
				if(sysUser == null){
					return getResult(APIEnumDefine.S990);
				}
				SysRoleMenu sysRoleMenu = userService.selectRoleMenuByAuthCodeAndMenuCode(sysUser,"team_chargeback");
				if(sysRoleMenu == null){
					return getResult(APIEnumDefine.S989);
				}
				sysAuthorizationLogService.insertAuthLog(
						new SysAuthorizationLog(authCode,operCode,sysUser.getEmpCode(),"团队退单")
				);
				operCode = sysUser.getEmpCode();
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
				if (!(obj.get("itemFileZs") instanceof JSONNull)) {
					map.put("itemFileZs", obj.getDouble("itemFileZs"));
				}
				if (!(obj.get("backTypeId") instanceof JSONNull)) {
					map.put("backTypeId", obj.getInt("backTypeId"));
				}
				if (!(obj.get("backId") instanceof JSONNull)) {
					map.put("backId", obj.getInt("backId"));
				}
				list.add(map);
			}
			List<String> seatIdList = new ArrayList<String>();
			Collections.addAll(seatIdList, seatIds.split(","));
			Map ret = billService.insertTeamBackBill(operCode, teamMember,
					seatIdList, list, type);
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
	 * 返回当前桌位下催单显示信息
	 *
	 */
	@RequestMapping("/getReminderItemInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前桌位下催单显示信息", httpMethod = "GET", notes = "返回当前桌位下催单显示信息")
	public Object getReminderItemInfo(
			@ApiParam(required = true, name = "openNum", value = "营业流水号") @RequestParam(value = "openNum", required = true) String openNum) {
		try {
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
			if (water.getOwState() != null && (water.getOwState() == -1 || water.getOwState() == 2 || water.getOwState() == 7 || water.getOwState() == 6)) {
				return getResult(APIEnumDefine.M0101020);
			}

			List<Map<String, Object>> items = billService
					.getReminderItemInfo(openNum);
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 催单接口
	 * 格式[{"serviceId":"服务号id","itemFileId":"品项id","reminderItemNumber":"数量"}]
	 *
	 */
	@RequestMapping("/reminderBill")
	@ResponseBody
	@ApiOperation(value = "催单接口", httpMethod = "GET", notes = "催单接口")
	public Object reminderBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode", required = true) String operCode,
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs", required = true) String orgs) {
		try {
			List<Map> list = new ArrayList<Map>();
			JSONArray pAr = JSONArray.fromObject(orgs);
			for (int i = 0; i < pAr.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject obj = pAr.getJSONObject(i);
				map.put("itemFileId", obj.getInt("itemFileId"));
				map.put("reminderItemNumber", obj.getInt("reminderItemNumber"));
				map.put("serviceId", obj.getInt("serviceId"));
				list.add(map);
			}
			return getSuccessResult(billService.insertReminderBill(operCode,
					list));
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回当前桌位下起菜显示
	 *
	 */
	@RequestMapping("/getQcItemInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前桌位下起菜显示", httpMethod = "GET", notes = "返回当前桌位下起菜显示")
	public Object getQcItemInfo(
			@ApiParam(required = true, name = "openNum", value = "营业流水号") @RequestParam(value = "openNum", required = true) String openNum) {
		try {
			List<Map<String, Object>> items = billService
					.getQcItemInfo(openNum);
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回当前桌位下起菜品项存在大类小类明细
	 *
	 */
	@RequestMapping("/getQcTypeInfo")
	@ResponseBody
	@ApiOperation(value = "返回当前桌位下起菜品项存在大类小类明细", httpMethod = "GET", notes = "返回当前桌位下起菜品项存在大类小类明细")
	public Object getQcTypeInfo(
			@ApiParam(required = true, name = "openNum", value = "营业流水号") @RequestParam(value = "openNum", required = true) String openNum) {
		try {
			DgOpenWater ow = dgOpenWaterService.selectByOpenWaterByNum(openNum);
			// 判断营业单状态
			if (ow.getOwState() != null && (ow.getOwState() == -1 || ow.getOwState() == 2 || ow.getOwState() == 7 || ow.getOwState() == 6)) {
				return getResult(APIEnumDefine.M0101020);
			}
			List<Map<String, Object>> items = billService
					.getQcItemTypeInfo(billService.getQcItemInfo(openNum));
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 起菜接口 type = 1格式
	 * [{"serviceId":"服务号id","itemFileId":"起菜品项id","qcNumber":"品项id"
	 * ,"qcFs":"起菜方式"}] type = 2格式
	 * [{"waterNum":"流水号","xlId":"小类id","qcFs":"起菜方式"}] type = 3格式
	 * {"waterNum":"流水号","qcFs":"起菜方式"}
	 *
	 * type 1/按品项 2/按品项类别 3/按客位
	 */
	@RequestMapping("/qcBill")
	@ResponseBody
	@ApiOperation(value = "起菜接口 ", httpMethod = "GET", notes = "起菜接口 ")
	public Object qcBill(
			@ApiParam(required = true, name = "operCode", value = "操作人员代码") @RequestParam(value = "operCode", required = true) String operCode,
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "type", value = "起菜执行类型") @RequestParam(value = "type", required = true) String type) {
		try {
			if (Integer.valueOf(type) == 1) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				JSONArray pAr = JSONArray.fromObject(orgs);
				for (int i = 0; i < pAr.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject obj = pAr.getJSONObject(i);
					map.put("itemFileId", obj.getInt("itemFileId"));
					map.put("qcNumber", obj.getDouble("qcNumber"));
					map.put("serviceId", obj.getInt("serviceId"));
					if (obj.containsKey("qcFs")
							&& !(obj.get("qcFs") instanceof JSONNull)) {
						map.put("qcFs", obj.getInt("qcFs"));
					} else {
						map.put("qcFs", 1);// 默认为起菜
					}
					list.add(map);
				}
				return getSuccessResult(billService.insertQcBill(list, type,
						operCode));
			} else if (Integer.valueOf(type) == 2) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				JSONArray pAr = JSONArray.fromObject(orgs);
				for (int i = 0; i < pAr.size(); i++) {
					Map<String, Object> map = new HashMap<String, Object>();
					JSONObject obj = pAr.getJSONObject(i);
					map.put("waterNum", obj.getString("waterNum"));
					map.put("xlId", obj.getInt("xlId"));
					if (obj.containsKey("qcFs")
							&& !(obj.get("qcFs") instanceof JSONNull)) {
						map.put("qcFs", obj.getInt("qcFs"));
					} else {
						map.put("qcFs", 1);// 默认为起菜
					}
					list.add(map);
				}
				return getSuccessResult(billService.insertQcBill(list, type,
						operCode));
			} else if (Integer.valueOf(type) == 3) {
				JSONObject obj = JSONObject.fromObject(orgs);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("waterNum", obj.getString("waterNum"));
				map.put("qcFs", obj.getInt("qcFs"));
				return getSuccessResult(billService.insertQcBill(map, type,
						operCode));
			} else {
				return getResult(APIEnumDefine.M0101013);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 返回本客位预定信息
	 */
	@RequestMapping("/getSeatYdInfo")
	@ResponseBody
	@ApiOperation(value = "返回本客位预定信息", httpMethod = "GET", notes = "返回本客位预定信息 ")
	public Object getSeatYdInfo(
			@ApiParam(required = true, name = "seatId", value = "操作人员代码") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			return getSuccessResult(billService.getReserverInfoBySeatId(Integer
					.valueOf(seatId)));
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

}