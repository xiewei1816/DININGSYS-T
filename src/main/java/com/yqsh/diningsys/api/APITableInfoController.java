package com.yqsh.diningsys.api;

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
import com.yqsh.diningsys.core.util.pay.DgConstants;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.PLcswMerchantMapper;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.archive.DgCard;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.model.pay.DgPayWater;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIMainService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.api.TableInfoService;
import com.yqsh.diningsys.web.service.api.impl.TableInfoServiceImpl;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;
import com.yqsh.diningsys.web.service.pay.DgPayInterface;
import com.yqsh.diningsys.web.service.pay.DgPayWaterService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;
import com.yqsh.diningsys.web.sevlet.CacheInit;
import com.yqsh.diningsys.web.util.OnlineHttp;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yqsh-zc on 2017/2/7.
 */
@Controller
@RequestMapping("/yqshapi/tableInfo")
@SuppressWarnings("all")
public class APITableInfoController extends ApiBaseController {

	public static final Object lock = new Object();
	private static Logger LOGGER=Logger.getLogger(APITableInfoController.class);
	@Autowired
	private TableInfoService tableInfoService;
	@Autowired
	private BillService billService;
	@Autowired
	private APIMainService aPIMainService;
	@Autowired
	private DgOpenWaterService dgOpenWaterService;
	@Autowired
	private APICheckService apiCheckService;
	@Autowired
	private DgConsumerSeatService dgConsumerSeatService;
	@Autowired
	private BusinessPermissionService businessPermissionService;

	@Autowired
	private DeskBusinessSettingService deskBusinessSettingService;

	@Autowired
	private DgUrlSettingService dgUrlSettingService;
	@Autowired
	private DgPosService dgPosService;
	@Autowired
	private DgPayInterface dgPayInterface;
	@Autowired
	private DgPayWaterService dgPayWaterService;
	/**
	 * 桌面信息机获取桌位信息<br>
	 * 桌面信息机通过上传MAC地址 获取桌位对应信息
	 * 
	 * @param mac
	 *            必填
	 * @return 返回对应数据
	 */
	@RequestMapping("/getConsumerInfo")
	@ResponseBody
	@ApiOperation(value = "桌面信息机获取桌位信息", httpMethod = "POST", notes = "桌面信息机获取桌位信息")
	public Object getUserInfo(
			HttpServletRequest request,
			@ApiParam(required = true, name = "mac", value = "MAC地址") @RequestParam(value = "mac", required = true) String mac,
			@ApiParam(required = true, name = "code", value = "code") @RequestParam(value = "code", required = true) String code) {
		try {
			tableInfoService.insertOrUpdate(mac);
			Map<String, Object> result = tableInfoService
					.getConsumerSeatInfoByMac(mac,code);
			return getSuccessResult(result);
		} catch (Exception e) {
			return getExceptionResult();
		}
	}
	

	/**
	 * 返回平板所有品项信息
	 * 
	 */
	@RequestMapping("/getAllItemInfo")
	@ResponseBody
	@ApiOperation(value = "获取平板所有品项信息", httpMethod = "POST", notes = "获取平板所有品项信息")
	public Object getAllItemInfo(
			@ApiParam(required = true, name = "seatId", value = "客位id") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			List<Map<String, Object>> items = tableInfoService
					.selectAllItemCXsqg(Integer.valueOf(seatId));
			return getSuccessResult(items);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 根据客位状态返回当前客位状态信息
	 * 
	 */
	@RequestMapping("/getSeatInfo")
	@ResponseBody
	@ApiOperation(value = "根据客位状态返回当前客位状态信息", httpMethod = "POST", notes = "根据客位状态返回当前客位状态信息")
	public Object getSeatInfo(
			@ApiParam(required = true, name = "seatId", value = "客位id") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			DgConsumerSeat seatInfo = dgConsumerSeatService
					.selectByPrimaryKey(Integer.valueOf(seatId));
			return getSuccessResult(seatInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 平板开台接口
	 * 
	 * @param eatNumber
	 * @param waiterCode
	 * @param seatId
	 * @param openPosNumber
	 * @return
	 */
	@RequestMapping("/openBill")
	@ResponseBody
	@ApiOperation(value = "开单操作", httpMethod = "POST", notes = "开单操作")
	public Object openBill(
			@ApiParam(required = true, name = "eatNumber", value = "开单人数") @RequestParam(value = "eatNumber", required = true) String eatNumber,
			@ApiParam(required = false, name = "ydId", value = "预定Id") @RequestParam(value = "ydId", required = false) String ydId,
			@ApiParam(required = true, name = "waiterCode", value = "服务人员编码") @RequestParam(value = "waiterCode", required = true) String waiterCode,
			@ApiParam(required = false, name = "cardCode", value = "卡号") @RequestParam(value = "cardCode", required = false) String cardCode,
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = true) String seatId,
			@ApiParam(required = true, name = "openPosNumber", value = "开单pos号") @RequestParam(value = "openPosNumber", required = true) String openPosNumber) {
		try {
			synchronized(lock){
				Map<String, Object> map = tableInfoService.openBill(eatNumber,
						waiterCode, seatId, openPosNumber, cardCode,ydId,false);	
				if (map.containsKey("success")) {
					DgConsumerSeat useSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.valueOf(seatId));
					OnlineHttp.onlineSeatModify(useSeat.getUuidKey(), "2");	
					return getSuccessResult(map.get("water"));
				} else {
					if (map.containsKey("error") && (map.get("error") instanceof String)) {
		                return getResult(APIEnumDefine.S1000, (String) map.get("error"));
		            } else if (map.containsKey("error") && (map.get("error") instanceof APIEnumDefine)) {
		                return getResult((APIEnumDefine) map.get("error"));
		            } else {
		                return getSuccessResult();
		            }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 平板预定开台接口
	 * 
	 * @param seatId
	 * @return
	 */
	@RequestMapping("/openYdBill")
	@ResponseBody
	@ApiOperation(value = "开单操作", httpMethod = "POST", notes = "开单操作")
	public Object openBill(
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = true) String seatId,
			@ApiParam(required = true, name = "number", value = "人数") @RequestParam(value = "number", required = true) String number,
			@ApiParam(required = false, name = "cardCode", value = "卡号") @RequestParam(value = "cardCode", required = false) String cardCode) {
		try {
			synchronized(lock){
				Map<String, Object> map = tableInfoService.openYdBill(seatId,number,
						cardCode);
				if (map.containsKey("success")) {
					DgConsumerSeat useSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.valueOf(seatId));
					OnlineHttp.onlineSeatModify(useSeat.getUuidKey(), "2");	
					return getSuccessResult(map.get("water"));
				} else {
					if (map.containsKey("error") && (map.get("error") instanceof String)) {
		                return getResult(APIEnumDefine.S1000, (String) map.get("error"));
		            } else if (map.containsKey("error") && (map.get("error") instanceof APIEnumDefine)) {
		                return getResult((APIEnumDefine) map.get("error"));
		            } else {
		                return getSuccessResult();
		            }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 平板加单接口 [{"itemFileId":"品项id","itemFileNumber":"数量"}]
	 * 
	 * @param orgs
	 * @param openNumber
	 * @param type
	 * @return
	 */
	@RequestMapping("/addBill")
	@ResponseBody
	@ApiOperation(value = "加单操作", httpMethod = "POST", notes = "加单操作")
	public Object addBill(
			@ApiParam(required = true, name = "orgs", value = "加单json参数") @RequestParam(value = "orgs", required = true) String orgs,
			@ApiParam(required = true, name = "openNumber", value = "营业流水号") @RequestParam(value = "openNumber", required = true) String openNumber,
			@ApiParam(required = false, name = "cardCode", value = "卡号") @RequestParam(value = "cardCode", required = false) String cardCode,
			@ApiParam(required = true, name = "type", value = "加单类型 1/加单并厨打  2/加单不厨打") @RequestParam(value = "type", required = true) String type,
			@ApiParam(required = true, name = "pre", value = "是否预点单 1/是预点单  2/不是") @RequestParam(value = "pre", required = true) String pre) {
		try {
			DgOpenWater water = dgOpenWaterService
					.selectByOpenWaterByNum(openNumber);
			// 判断营业单状态
			if (water.getOwState() != null && (water.getOwState() == -1 || water.getOwState() == 2 || water.getOwState() == 7 || water.getOwState() == 6)) {
				return getResult(APIEnumDefine.M0101020);
			}

			if (water.getOwState() == 3 || water.getOwState() == 8
					|| water.getOwState() == 9 || water.getOwState() == 5) {
				DgOwLockinfo lockInfo = apiCheckService
						.selectOpenWaterLock(openNumber);
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

			if(StringUtil.isNotEmpty(cardCode)){
				DgCard dgCard = new DgCard();
				dgCard.setCardnum(cardCode);
				dgCard.setConsumerid("" + water.getSeatId());
				if (tableInfoService.countBySeatIdAndCardNum(dgCard) == 0) {
					return getResult(APIEnumDefine.M0101021);
				}	
			}
			Map ret = null;
			if(pre.equals("1")){
				//全部预点单
//				ret = billService.addYxePreBill(list,openNumber);
				//川西靶子版本 锅底直接上账 其它预点单
//				ret = tableInfoService.addBill(list,openNumber);
				ret = billService.addBill(null, list, openNumber, type, "",true);	
			} else {
				ret = billService.addBill(null, list, openNumber, type, "",true);	
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
	 * 
	 * 主界面轮询函数
	 * 
	 * @param request
	 * @param owNum
	 *            营业流水号
	 * @return
	 */
	@RequestMapping("/getOpenWaterOrderItems")
	@ResponseBody
	@ApiOperation(value = "主界面轮询函数", httpMethod = "POST", notes = "主界面轮询函数")
	public Object getOpenWaterOrderItems(
			@ApiParam(required = false, name = "seatId", value = "座位ID") @RequestParam(value = "seatId", required = false) String seatId,
			@ApiParam(required = true, name = "owNum", value = "营业流水号") @RequestParam(value = "owNum", required = true) String owNum) {
		try {
			Map ret = aPIMainService.getOpenWaterOrderItem(owNum,seatId == null ? null :Integer.valueOf(seatId));
			return getSuccessResult(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * 主界面轮询函数(版本2)
	 * 
	 * @param request
	 * @param owNum
	 *            营业流水号
	 * @return
	 */
	@RequestMapping("/yxeMainLoop")
	@ResponseBody
	@ApiOperation(value = "主界面轮询函数", httpMethod = "POST", notes = "主界面轮询函数")
	public Object yxeMainLoop(
			@ApiParam(required = false, name = "seatId", value = "座位ID") @RequestParam(value = "seatId", required = false) String seatId,
			@ApiParam(required = true, name = "owNum", value = "营业流水号") @RequestParam(value = "owNum", required = true) String owNum) {
		try {
			Map ret = aPIMainService.yxeMainLoop(owNum,Integer.valueOf(seatId));
			return getSuccessResult(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据客位号查询,是否有占用/埋单/手工锁定的营业流水
	 *
	 * @param seatId
	 *            客座号码
	 * @return owNum为空返回M0202001，客座存在品项返回S001，无品项返回M0202002
	 */
	@RequestMapping(value = "/getOpenWaterBySeatId", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据客位号查询,是否有占用/埋单/手工锁定的营业流水", httpMethod = "POST", notes = "根据客位号查询,是否有占用/埋单/手工锁定的营业流水")
	public Object getOpenWaterBySeatId(
			@ApiParam(required = true, name = "seatId", value = "客位ID") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			Map owMap = tableInfoService.selectOpenWaterBySeatIdLastOne(seatId);
			return getSuccessResult(owMap);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取所有呼叫服务类别
	 *
	 */
	@RequestMapping(value = "/getAllFw", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有呼叫服务类别", httpMethod = "GET", notes = "获取所有呼叫服务类别")
	public Object getAllFw() {
		try {
			List<DgPublicCode0> fws = tableInfoService.getAllFw();
			return getSuccessResult(fws);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取客户呼叫服务信息
	 *
	 */
	@RequestMapping(value = "/submiteHjFw", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取客户呼叫服务信息", httpMethod = "POST", notes = "获取客户呼叫服务信息")
	public Object submiteHjFw(
			@ApiParam(required = true, name = "hjFw", value = "呼叫服务内容") @RequestParam(value = "hjFw", required = true) String hjFw,
			@ApiParam(required = true, name = "owNum", value = "呼叫服务内容") @RequestParam(value = "owNum", required = true) String owNum) {
		try {
			tableInfoService.insertHjFw(owNum,hjFw);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	
	
	/**
	 * 前台获取易小二呼叫服务信息
	 * 
	 * @return
	 */
	@RequestMapping("/getSumbitHjFw")
	@ResponseBody
	@ApiOperation(value = "前台获取易小二呼叫服务信息(前5条)", httpMethod = "GET", notes = "前台获取易小二呼叫服务信息(前5条)")
	public Object getSumbitHjFw() {
		try {
			return getSuccessResult(tableInfoService.getCallServiceTop5("5"));
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	
	/**
	 * 删除易小二呼叫服务信息(id组合,d)
	 *
	 */
	@RequestMapping(value = "/deleteHjFw", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = " 删除易小二呼叫服务信息(id组合,d)", httpMethod = "POST", notes = " 删除易小二呼叫服务信息(id组合,d)")
	public Object deleteHjFw(@ApiParam(required = true, name = "ids", value = "删除呼叫服务id组合(1,2,3)") @RequestParam(value = "ids", required = true) String ids) {
		try {
			tableInfoService.deleteCallServiceByIds(ids);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	
	
	/**
	 * 获取所有广告图片地址
	 *
	 */
	@RequestMapping(value = "/getAllPic", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取所有广告图片地址", httpMethod = "GET", notes = "获取所有广告图片地址")
	public Object getAllPic() {
		try {
			List<DgItemTimeLimitPic> zPics = tableInfoService.selectAllZPic();
			List<DgItemTimeLimitPic> hPics = tableInfoService.selectAllHPic();
			Map ret = new HashMap();
			ret.put("zPics", zPics);
			ret.put("hPics", hPics);
			return getSuccessResult(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取本店介绍
	 *
	 */
	@RequestMapping(value = "/getOwnOrg", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取本店介绍", httpMethod = "GET", notes = "获取本店介绍")
	public Object getOwnOrg() {
		try {
			TbOrg org = tableInfoService.getOwnOrg();
			return getSuccessResult(org);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	/**
	 * 获取预定信息
	 * 
	 * @param seatId
	 * @return
	 */
	@RequestMapping("/getYdInfo")
	@ResponseBody
	@ApiOperation(value = "获取预定信息", httpMethod = "POST", notes = "获取预定信息")
	public Object getYdInfo(
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			return getSuccessResult(tableInfoService
					.getReserveManagerBySeatId(seatId));
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	
	/**
	 * 清扫状态改为空闲状态
	 * 
	 * @param seatId
	 * @return
	 */
	@RequestMapping("/setSeatFree")
	@ResponseBody
	@ApiOperation(value = "清扫状态改为空闲状态", httpMethod = "POST", notes = "清扫状态改为空闲状态")
	public Object setSeatFree(
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			tableInfoService.setSeatFree(seatId);
			return getSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	
	/**
	 * 预定界面/开台界面 循环获取接口
	 * 
	 * @param seatId
	 * @return
	 */
	@RequestMapping("/getYdKtLoopInfo")
	@ResponseBody
	@ApiOperation(value = "预定界面/开台界面 循环获取接口", httpMethod = "POST", notes = "预定界面/开台界面 循环获取接口")
	public Object getYdKtLoopInfo(
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = true) String seatId) {
		try {
			return getSuccessResult(tableInfoService.getYdKtLoopInfo(seatId));
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/yxePayMoneyCount",method = RequestMethod.POST)
	@ApiOperation(value = "易小二价钱计算", httpMethod = "POST", notes = "易小二支付接口")
	public Object yxePayMoneyCount(@ApiParam(required = true, name = "openWater", value = "流水号") @RequestParam(value = "openWater", required = true)String openWater,
			@ApiParam(required = true, name = "posId", value = "posId") @RequestParam(value = "posId", required = true)Integer posId){
        try {
            Map<String,Object> resMap = new HashMap(); //返回map
            Map map = apiCheckService.selectOpenWaterByOwNum(openWater);

            DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(map.get("seat_id").toString()));

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(openWater);

			//自动锁定营业流水为结算锁定
			apiCheckService.modifyOpenWaterLock(dgOpenWaters,"yxe_water",1,posId);

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

	@ResponseBody
	@RequestMapping(value = "/yxePay",method = RequestMethod.POST)
	@ApiOperation(value = "易小二支付接口", httpMethod = "POST", notes = "易小二支付接口")
	public Object yxePay( @ApiParam(required = true, name = "payMoney", value = "payMoney") @RequestParam(value = "payMoney", required = true)String payMoney, 
			@ApiParam(required = true, name = "orderWater", value = "orderWater") @RequestParam(value = "orderWater", required = true)String orderWater, 
			@ApiParam(required = true, name = "payType", value = "payType") @RequestParam(value = "payType", required = true)String payType) {
		if(StringUtil.isEmpty(payMoney)){
			return getFailResult("金额为空");
		}
		if(StringUtil.isEmpty(orderWater)){
			return getFailResult("营业流水为空");
		}
		if(StringUtil.isEmpty(payType)){
			return getFailResult("支付类型为空");
		}

        String returnData = null;
        Gson gson = new Gson();
        try {
            String s = orderWater.split("-")[1].substring(0,6);
			DgUrlSetting yxeSource=dgUrlSettingService.selectByCode("yxe.resource");
			if(yxeSource != null && yxeSource.getValue().equals("sbPay")){
				//先删除之前的流水
				dgPayWaterService.deleteByOpenWater(orderWater,payType.equals("wx")?"WX":"ZFB");
				int a=(int) (Double.valueOf(payMoney)*100);
				Map<String,Object> payCode = dgPayInterface.createPayQr(payType.equals("wx")?"WX":"ZFB",a+"","yxe",orderWater);
				if(payCode.get("msg").equals(DgConstants.RES_MEG)){
					return getSuccessResult(payCode);
				} else {
					return getFailResult(null);
				}
			}
            String format = new SimpleDateFormat("yyyy_MM").format(new SimpleDateFormat("yyyyMM").parse(s));
            Map<String,Object> map = new HashMap<>();
            map.put("yearMonth",format);
            map.put("payMoney",payMoney);
            map.put("orderWater",orderWater);
            map.put("payType",payType);
            map.put("companyId", dgUrlSettingService.selectByCode("encDog").getValue());
            String des = dgUrlSettingService.selectByCode("yxe.des").getValue();
            String enData = ThreeDESUtil.encryptGeneral(new Gson().toJson(map), des);
            String sign = YQSH_SHA1.getSHA1(enData, dgUrlSettingService.selectByCode("yxe.sha").getValue());
            returnData = OkHttpUtils.yxePayMent(enData, sign);

            Map resMap = gson.fromJson(returnData, Map.class);
            if(resMap != null && resMap.get("msgCode").toString().equalsIgnoreCase("ok")){
                Map map1 = gson.fromJson(resMap.get("body").toString(), Map.class);
                Map map2 = gson.fromJson(ThreeDESUtil.decryptGeneral(map1.get("cipInfo").toString(), des), Map.class);

                return getSuccessResult(map2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFailResult(returnData);
    }

	/**
	 * 易小二支付轮询查询支付状态
	 * @param openWaterData
	 * @param paidMoney
	 * @param zeroMoney
	 * @param payType
	 * @param priceType 1：品项打折 2：重要活动
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/yxeLoopQuery",method = RequestMethod.POST)
	@ApiOperation(value = "易小二支付轮询查询支付状态", httpMethod = "POST", notes = "易小二支付轮询查询支付状态")
	public Object yxeLoopQuery(@ApiParam(required = true, name = "openWaterData", value = "openWaterData") @RequestParam(value = "openWaterData", required = true)String openWaterData,
			@ApiParam(required = true, name = "paidMoney", value = "paidMoney") @RequestParam(value = "paidMoney", required = true)String paidMoney,
			@ApiParam(required = true, name = "zeroMoney", value = "zeroMoney") @RequestParam(value = "zeroMoney", required = true)String zeroMoney,
			@ApiParam(required = true, name = "payType", value = "payType") @RequestParam(value = "payType", required = true)String payType,
			@ApiParam(required = true, name = "priceType", value = "priceType") @RequestParam(value = "priceType", required = true)Integer priceType) {
		String returnData = null;
		try {        	if(StringUtil.isEmpty(openWaterData)){
        		return getFailResult("营业流水为空");
			}
        	if(StringUtil.isEmpty(paidMoney)){
        		return getFailResult("支付金额为空");
			}
        	if(StringUtil.isEmpty(zeroMoney)){
        		return getFailResult("找零金额为空");
			}
        	if(StringUtil.isEmpty(payType)){
        		return getFailResult("支付类型为空");
			}

        	Gson gson = new Gson();
			//判断结算的营业流水中是否有押金未处理
			List<DgOpenWater> dgOpenWaters = new Gson().fromJson(openWaterData, new TypeToken<List<DgOpenWater>>() {
			}.getType());
			DgOpenWater openWaterMap = dgOpenWaters.get(0);
			String orderWater = openWaterMap.getOwNum();

			List<String> owNums = new ArrayList<>();
            for(int i = 0;i < dgOpenWaters.size();i++){
                owNums.add(dgOpenWaters.get(i).getOwNum());
            }
//			if(apiCheckService.modifyCheckOwNumHasDeposit(new Gson().toJson(owNums)) != null){
//                return getFailResult("结算的营业流水中有押金未处理，请先处理押金"); 
//            }

			DgUrlSetting yxeSource=dgUrlSettingService.selectByCode("yxe.resource");
            if(yxeSource != null && yxeSource.getValue().equals("sbPay")){
				DgPayWater dgPayWater=new DgPayWater();
				dgPayWater.setPayType(payType.equals("wx")?"WX":"ZFB");
				dgPayWater.setOrderNo(orderWater);
            	List<DgPayWater> dgPayWaters=dgPayWaterService.findAllObj(dgPayWater);
            	if(dgPayWaters.isEmpty()){
					return getFailResult(returnData);
				} else {
					String queryBack=dgPayInterface.queryOrder(dgPayWaters.get(0).getOutTradeNo(),dgPayWaters.get(0).getThreeTradeNo(),payType.equals("wx")?"WX":"ZFB",null);
					if(queryBack.equals("SUCCESS")){
						String date= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
						synchronized (lock){
							tableInfoService.modifyYxePaySuccessState(dgOpenWaters,paidMoney,zeroMoney,payType,date,priceType);
						}
						return getSuccessResult(dgOpenWaters);
					} else {
						return getFailResult(dgOpenWaters);
					}
				}
			}
			String s = orderWater.split("-")[1].substring(0,6);
			String format = new SimpleDateFormat("yyyy_MM").format(new SimpleDateFormat("yyyyMM").parse(s));
            Map<String,Object> map = new HashMap<>();
            map.put("timestamp",System.currentTimeMillis());
            map.put("yearMonth",format);
            map.put("orderWater",orderWater);
            map.put("perType", payType);
            map.put("companyId", dgUrlSettingService.selectByCode("encDog").getValue());
            String des = dgUrlSettingService.selectByCode("yxe.des").getValue();
            String enData = ThreeDESUtil.encryptGeneral(new Gson().toJson(map), des);
            String sign = YQSH_SHA1.getSHA1(enData, dgUrlSettingService.selectByCode("yxe.sha").getValue());
			returnData = OkHttpUtils.yxeLoopPayStateQuery(enData, sign);
            LOGGER.error("oneYxeLoopQuery"+orderWater+":"+returnData);
			Map resMap = gson.fromJson(returnData, Map.class);

			if(resMap != null && resMap.get("msgCode").toString().equalsIgnoreCase("ok")){
				Map map1 = gson.fromJson(resMap.get("body").toString(), Map.class);
				Map map2 = gson.fromJson(ThreeDESUtil.decryptGeneral(map1.get("cipInfo").toString(), des), Map.class);

                LOGGER.error("msgCode map2"+orderWater+":"+ com.alibaba.fastjson.JSON.toJSONString(map2));
				String date = null;
				if(map2.containsKey("tradeDate") && map2.get("tradeDate") != null){
					date = map2.get("tradeDate").toString();
				}else{
					date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				}
                LOGGER.error("msgCode success"+orderWater+":"+date);
				synchronized (lock) {
					tableInfoService.modifyYxePaySuccessState(dgOpenWaters, paidMoney, zeroMoney, payType, date, priceType);
				}
				return getSuccessResult(map2);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return getFailResult(returnData);
	}

	/**
	 * 获取前5条呼叫服务信息(用于手表)
	 *
	 */
	@RequestMapping(value = "/getHjFwItems", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取前5条呼叫服务信息,用于手表", httpMethod = "POST", notes = "获取前5条呼叫服务信息,用于手表")
	public Object getHjFwItems(@ApiParam(required = true, name = "posId", value = "posId") @RequestParam(value = "posId", required = true)String posId) {
		try {
			DgPos pos = new DgPos();
			pos.setId(Integer.parseInt(posId));
			pos = dgPosService.getPosByID(pos);
			List<DgCallService> callInfo = tableInfoService.getCallServiceTop5ByPos(pos);
			return getSuccessResult(callInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	
	/**
	 * 获取所有预点单菜品明细
	 *
	 */
	@RequestMapping(value = "/getAllPreOrderItems", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = " 获取所有预点单菜品明细", httpMethod = "POST", notes = " 获取所有预点单菜品明细")
	public Object getAllPreOrderItems(@ApiParam(required = true, name = "waterId", value = "流水id") @RequestParam(value = "waterId", required = true)String waterId) {
		try {
			List<DgPreOrderbill> pre = tableInfoService.selectAllPreOrderBill(waterId);
			return getSuccessResult(pre);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	
	/**
	 * 易小二呼叫服务信息id,打印结账单
	 *
	 */
	@RequestMapping(value = "/jzdPrintData", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "易小二呼叫服务信息id,打印结账单", httpMethod = "POST", notes = "易小二呼叫服务信息id,打印结账单")
	public Object jzdPrintData(@ApiParam(required = true, name = "id", value = "易小二呼叫服务信息id,打印结账单") @RequestParam(value = "id", required = true) String id,
							   @ApiParam(required = false, name = "isCategory", value = "是否按类别打印") @RequestParam(value = "isCategory" , required = false) Integer isCategory) {
		try {
			DgCallService call = tableInfoService.getDgCallServiceById(Integer.valueOf(id));
			int clearingWaterId = dgOpenWaterService.selectByOpenWaterByNum(call.getOwNum()).getClearingWaterId();
			tableInfoService.deleteCallServiceByIds(id);
            Map<String,Object> resMap = new HashMap<>();
            //结账单基本数据
            DgReceptionClearingWater dgReceptionClearingWater = apiCheckService.selectClearingWaterById(clearingWaterId);

            //根据结算ID查询出优惠信息
			DgOwDiscount dgOwDiscounts = apiCheckService.selectYhxx(clearingWaterId);

            //结算方式
            List<DgOwClearingway> dgOwClearingways = apiCheckService.selectClearingWayByCwId(clearingWaterId);
            //结账单下的所有营业流水
            List<DgOpenWater> dgOpenWaters = apiCheckService.selectOpenWaterByCwId(clearingWaterId);
            //查看每个营业流水下的所有品项信息
            for(DgOpenWater dgOpenWater:dgOpenWaters){
                List<DgOwConsumerDetails> dgOwConsumerDetails = apiCheckService.selectClearingItemFileInfos(dgOpenWater.getOwNum(),isCategory);
                dgOpenWater.setItemFileInfos(dgOwConsumerDetails);
            }

            resMap.put("dgOwDiscounts",dgOwDiscounts);
            resMap.put("dgReceptionClearingWater",dgReceptionClearingWater);
            resMap.put("dgOwClearingways",dgOwClearingways);
            resMap.put("dgOpenWaters",dgOpenWaters);
            return getSuccessResult(resMap);
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	
	/**
	 * 清扫状态改为空闲状态
	 * 
	 * @param seatId
	 * @return
	 */
	@RequestMapping("/checkCard")
	@ResponseBody
	@ApiOperation(value = "检测卡号(台卡,服务员卡)", httpMethod = "POST", notes = "检测卡号(台卡,服务员卡)")
	public Object setSeatFree(
			@ApiParam(required = true, name = "seatId", value = "桌位id") @RequestParam(value = "seatId", required = true) String seatId,
			@ApiParam(required = true, name = "cardNum", value = "卡号") @RequestParam(value = "cardNum", required = true) String cardNum) {
		try {
			
			return getSuccessResult(tableInfoService.checkCard(seatId, cardNum));
		} catch (Exception e) {
			e.printStackTrace();
			return getExceptionResult();
		}
	}
	/**
	 * 一代易小二，支付状态查询
	 * @param openWater 营业流水号
	 * @param posId pos编号
	 * @return
	 */
	@RequestMapping(value = "/oneYxeLoopQuery",method = RequestMethod.POST)
	@ApiOperation(value = "易小二支付轮询查询支付状态", httpMethod = "POST", notes = "易小二支付轮询查询支付状态")
	@ResponseBody
	public Object oneYxeLoopQuery(@ApiParam(required = true, name = "openWater", value = "流水号") @RequestParam(value = "openWater", required = true)String openWater,
			@ApiParam(required = true, name = "posId", value = "posId") @RequestParam(value = "posId", required = true)Integer posId,
			@ApiParam(required = true, name = "payType", value = "payType") @RequestParam(value = "payType", required = true)String payType) {
		String returnData = null;
		try {
        	if(StringUtil.isEmpty(openWater)){
        		return getFailResult("营业流水号不能为空");
			}
        	if(null==posId){
        		return getFailResult("posId不能为空");
			}
        	if(org.apache.commons.lang.StringUtils.isBlank(payType)){
        		return getFailResult("支付类型不能为空");
			}
        	//判断结算的营业流水中是否有押金未处理
        	Map<String,Object> mapObj=oneYxePayMoneyCount(openWater, posId);
        	Double openWaterZyhdYs=null;
        	Double openWaterPxdzYs=null;
        	String openWaterData=null;
        	List<DgOpenWater> dgOpenWaters = new ArrayList<>();
        		if(mapObj.containsKey("openWaterZyhdYs"))openWaterZyhdYs=(Double) mapObj.get("openWaterZyhdYs");
            	if(mapObj.containsKey("openWaterPxdzYs"))openWaterPxdzYs=(Double) mapObj.get("openWaterPxdzYs");
            	if(mapObj.containsKey("openWaterData"))dgOpenWaters=(List<DgOpenWater>) mapObj.get("openWaterData");
        	Map<String,Object> retMap=calcPriceTypeAndMoney(openWaterZyhdYs, openWaterPxdzYs);
        	Gson gson = new Gson();
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
			String format = new SimpleDateFormat("yyyy_MM").format(new SimpleDateFormat("yyyyMM").parse(s));
            Map<String,Object> map = new HashMap<>();
            map.put("timestamp",System.currentTimeMillis());
            map.put("yearMonth",format);
            map.put("orderWater",orderWater);
            map.put("perType", payType);
            map.put("companyId", dgUrlSettingService.selectByCode("encDog").getValue());
            String des = dgUrlSettingService.selectByCode("yxe.des").getValue();
            String enData = ThreeDESUtil.encryptGeneral(new Gson().toJson(map), des);
            String sign = YQSH_SHA1.getSHA1(enData, dgUrlSettingService.selectByCode("yxe.sha").getValue());
			returnData = OkHttpUtils.yxeLoopPayStateQuery(enData, sign);
			LOGGER.error("oneYxeLoopQuery"+openWater+":"+returnData);
			Map resMap = gson.fromJson(returnData, Map.class);
			if(resMap != null && resMap.get("msgCode").toString().equalsIgnoreCase("ok")){
				Map map1 = gson.fromJson(resMap.get("body").toString(), Map.class);
				Map map2 = gson.fromJson(ThreeDESUtil.decryptGeneral(map1.get("cipInfo").toString(), des), Map.class);

				String date = null;
				if(map2.containsKey("tradeDate") && map2.get("tradeDate") != null){
					date = map2.get("tradeDate").toString();
				}else{
					date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				}
				tableInfoService.modifyYxePaySuccessState(dgOpenWaters,retMap.get("min").toString(),"0.0",payType,date,(Integer)retMap.get("type"));
				
				return getSuccessResult(map2);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return getFailResult(returnData);
	}
	/**
	 * 计算支付金额及类型
	 * @param openWaterZyhdYs
	 * @param openWaterPxdzYs
	 * @return
	 */
	private Map<String,Object> calcPriceTypeAndMoney(Double openWaterZyhdYs,Double openWaterPxdzYs ){
		Map<String,Object> ret=new HashMap<>();
		if(null==openWaterZyhdYs&&null==openWaterPxdzYs){
			ret.put("type",1);
			ret.put("min",0.0);
		}else if(null==openWaterPxdzYs){
			ret.put("type",2);
			ret.put("min",openWaterZyhdYs);
		}else if(null==openWaterZyhdYs){
			ret.put("type",1);
			ret.put("min",openWaterPxdzYs);
		}else{
			ret.put("type", openWaterPxdzYs>openWaterZyhdYs?1:2);
			ret.put("min", openWaterPxdzYs>openWaterZyhdYs?openWaterZyhdYs:openWaterPxdzYs);
		}
		return ret;
	}
	public Map<String,Object> oneYxePayMoneyCount(String openWater,Integer posId){
        try {
            Map<String,Object> resMap = new HashMap(); //返回map
            Map map = apiCheckService.selectOpenWaterByOwNum(openWater);

            DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(map.get("seat_id").toString()));

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(openWater);

			//自动锁定营业流水为结算锁定
			//apiCheckService.modifyOpenWaterLock(dgOpenWaters,"yxe_water",1,posId);

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

            resMap.put("openWaterData",dgOpenWaters);

            //3种价格合计
            resMap.put("pxdzZeroMonry",returnValue(pxdzZeroMonry));
            resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
            resMap.put("openWaterPxdzYs",returnValue(openWaterPxdzYs)+dgOpenWaters.get(0).getServiceCharge());

            if(maps.size() > 0){
                resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                resMap.put("zyhdZeroMonry",zyhdZeroMonry);
                resMap.put("openWaterZyhdYs",openWaterZyhdYs+dgOpenWaters.get(0).getServiceCharge());
            }
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
