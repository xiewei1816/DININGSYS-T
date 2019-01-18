package com.yqsh.diningsys.web.service.api.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.DoubleCompare;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.api.BillMapper;
import com.yqsh.diningsys.web.dao.api.DgCallServiceMapper;
import com.yqsh.diningsys.web.dao.api.DgPreOrderbillColorMapper;
import com.yqsh.diningsys.web.dao.api.DgPreOrderbillMapper;
import com.yqsh.diningsys.web.dao.api.DgReserveManagerMapper;
import com.yqsh.diningsys.web.dao.api.DgReserveSeatidsMapper;
import com.yqsh.diningsys.web.dao.api.SysBusinessLogMapper;
import com.yqsh.diningsys.web.dao.archive.*;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatItemMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatManagerMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.*;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.dao.permission.SysPerItemfileMapper;
import com.yqsh.diningsys.web.dao.print.DgPrintDataMapper;
import com.yqsh.diningsys.web.dao.sysSettings.DgUrlSettingMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.api.DgPreOrderbillColor;
import com.yqsh.diningsys.web.model.api.SysBusinessLog;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.api.DgCashPledgeService;
import com.yqsh.diningsys.web.service.base.UserService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;
import com.yqsh.diningsys.web.util.OnlineHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("all")
@Service
public class BillServiceImpl implements BillService {
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat formatDate = new SimpleDateFormat(
			"yyyy-MM-dd"); // 年月日
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Object lock = new Object();
	@Resource
	private BillMapper billMapper;
	@Resource
	private DgConsumerSeatMapper dgConsumerSeatMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private DgOpenWaterMapper dgOpenWaterMapper;
	@Resource
	private DgSeatItemMapper dgSeatItemMapper;
	@Resource
	private DgSeatManagerMapper dgSeatManagerMapper;
	@Resource
	private DeskBusinessSettingService deskBusinessSettingService;
	@Resource
	private TbOrgMapper tbOrgMapper;
	@Resource
	private DgPosMapper dgPosMapper;
	@Resource
	private DgItemFileMapper dgItemFileMapper;
	@Resource
	private DgItemDisableMapper dgItemDisableMapper;
	@Resource
	private TbBisMapper tbBisMapper;
	@Resource
	private DgForMealTimePriceSMapper dgForMealTimePriceSMapper;
	@Resource
	private DgPlacePriceSMapper dgPlacePriceSMapper;
	@Resource
	private DgItemForWeekMapper dgItemForWeekMapper;
	@Resource
	private DgItemPriceLadderMapper dgItemPriceLadderMapper;
	@Resource
	private DgPromotionItemMapper dgPromotionItemMapper;
	@Resource
	private DgItemCurrentPriceMapper dgItemCurrentPriceMapper;
	@Resource
	private DgItemPricePriorityMapper dgItemPricePriorityMapper;
	@Resource
	private DgOwConsumerDetailsMapper dgOwConsumerDetailsMapper;
	@Resource
	private DgItemSaleLimitMapper dgItemSaleLimitMapper;
	@Resource
	private DgItemSaleLimitSMapper dgItemSaleLimitSMapper;
	@Resource
	private DgOwDetailsOtherMapper dgOwDetailsOtherMapper;
	@Resource
	private DgItemOutofStockMapper dgItemOutofStockMapper;
	/**
	 * 套餐查询
	 */
	@Resource
	private DgItemFilePackageMapper dgItemFilePackageMapper;
	@Resource
	private DgItemFilePackageBxMapper dgItemFilePackageBxMapper;
	@Resource
	private TbRfctMapper tbRfctMapper;
	@Resource
	private TbRfcMapper tbRfcMapper;
	@Resource
	private DgOwModifySeatMapper dgOwModifySeatMapper;
	@Resource
	private SysPerItemfileMapper sysPerItemfileMapper;
	@Resource
	private DgCashPledgeService dgCashPledgeService;
	@Resource
	private DgPrintDataService dgPrintDataService;
	@Resource
	private DgPrintDataMapper dgPrintDataMapper;
	@Resource
	private DgItemTimeLimitMapper dgItemTimeLimitMapper;
	@Resource
	private DgReserveManagerMapper dgReserveManagerMapper;
	@Resource
	private DgReserveSeatidsMapper dgReserveSeatidsMapper;
	@Resource
	private DgPreOrderbillMapper dgPreOrderbillMapper;
	@Resource
	private DgPreOrderbillColorMapper dgPreOrderbillColorMapper;
	@Resource
	private DgCallServiceMapper dgCallServiceMapper;
	@Resource
	private APICheckServiceMapper apiCheckServiceMapper;
	@Resource
	private SysBusinessLogMapper sysBusinessLogMapper;
	@Resource
	private DgUrlSettingMapper dgUrlSettingMapper;
	@Resource
	private DgOffsetMapper dgOffsetMapper;
	@Override
	public List<Map<String, Object>> selectAllTeamMember(Integer pos) {
		List<Map<String, Object>> reMaps = new ArrayList<Map<String, Object>>();
		DgPos dgPos = dgPosMapper.selectPosByPosId(pos);
		List<Integer> integers = StringUtil.arrayToList(dgPos
				.getConsumerAreas());
		List<Map> maps;
		maps = billMapper.selectAllTeamMember(integers);
		for (Map map : maps) {
			List<Map> teamListMap = new ArrayList<>();
			String key = map.get("team_members").toString();
			if (map.get("first_time") != null) {
				map.put("first_time",
						simpleDateFormat.format(map.get("first_time")));
			}
			boolean havaBean = false;
			for (Map r : reMaps) {
				if (r.get("teamCode").equals(key)) {
					havaBean = true;
					List<Map> tMaps = (List<Map>) r.get("detail");
					tMaps.add(map);
					break;
				}
			}
			if (!havaBean) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("teamCode", key);
				List<Map> tMaps = new ArrayList<Map>();
				tMaps.add(map);
				m.put("detail", tMaps);
				reMaps.add(m);
			}
		}
		return reMaps;
	}

	/**
	 * @param eatNumber
	 * @param waiterId
	 * @param deposit
	 * @param marketingId
	 * @param seatId
	 * @param queueNumber
	 * @param teamMemberInfo
	 * @param openPos
	 * @param seatLable
	 * @return
	 */
	@Override
	public  Map<String, Object> openBill(String operCode,
										 String eatNumber, String waiterId, String deposit,
										 String marketingId, String seatId, String queueNumber,
										 String teamMemberInfo, String openPos, String seatLable,
										 String gradeId, String ydId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(Integer.valueOf(openPos));
		pos = dgPosMapper.getPosByID(pos);
		TbOrg org = new TbOrg();
		org.setId(Integer.valueOf(pos.getOrganization()));
		org = tbOrgMapper.getOrgByID(org);
		// 判断桌位是否空闲
		List<Integer> seatIds = new ArrayList<Integer>();
		seatIds.add(Integer.valueOf(seatId));
		Map<String, Object> seatErr = checkSeatFree(seatIds);
		if (seatErr.containsKey("error")) {
			ret.put("error", APIEnumDefine.M0101002);
			return ret;
		}
//			// 营业流水号组合
//			List<String> ornums = gson.fromJson(deskBusinessSettingService
//					.createFlowNumber(org.getOrgCode(), pos.getNumber(), 1,
//							SerialRulEnum.YY), new TypeToken<List<String>>() {
//			}.getType());

		Map owFlowRul = deskBusinessSettingService.getFlowRul(org.getOrgCode(),pos.getNumber(),SerialRulEnum.YY);

		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(org.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());
		DgConsumerSeat seat = new DgConsumerSeat();
		seat.setId(Integer.valueOf(seatId));
		seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);
		seat.setSeatState(2);
		seat.setLastOpenTime(new Date());
		seat.setSeatLabel(seatLable);
		dgConsumerSeatMapper.updateState(seat); // 更新桌位状态
		Map<String, Object> seatMan = selectSeatConsumeInfo(
				Integer.valueOf(seatId), eatNumber);
		// 获取开单自动增加品项
		List<DgSeatItem> autoIncreaList = dgSeatItemMapper.getBySeatId(Integer
				.valueOf(seatId));
		List<Integer> autoIncrErrItemIds = new ArrayList<Integer>();
		List<Integer> errTcItemIds = new ArrayList<Integer>();
		if (autoIncreaList.size() > 0) {
			List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
			for (DgSeatItem o : autoIncreaList) { // 验证每个品项的合法性
				Map<String, Object> checkMap = new HashMap<String, Object>();
				checkMap.put("itemId", o.getItemId());
				if (o.getUseOpenCounter() == 1) {
					checkMap.put(
							"number",
							MathExtend.multiply(o.getCount(),
									Integer.valueOf(eatNumber)));
				} else {
					checkMap.put("number", o.getCount());
				}
				checklist.add(checkMap);
			}
			Map<String, Object> errCheckItem = checkItemFile(checklist, null,
					true);
			if (!errCheckItem.isEmpty()) {
				addMapKeyValue("ItemFileError", "客位-" + seat.getName()
						+ (String) errCheckItem.get("error"), ret);
				if (errCheckItem.containsKey("errItemIds")) {
					String[] errItemIds = ((String) errCheckItem
							.get("errItemIds")).split("-");
					for (String er : errItemIds) {
						autoIncrErrItemIds.add(Integer.valueOf(er));
					}
				}
				if (errCheckItem.containsKey("errTc")) {
					errTcItemIds.addAll((List<Integer>) errCheckItem
							.get("errTc"));
				}
			}
		}
		DgOpenWater water = new DgOpenWater();
		water.setMinimumConsumption((double) seatMan.get("zdxf"));
		water.setServiceCharge((double) seatMan.get("fwf"));
		water.setPrivateRoomCost((double) seatMan.get("bff"));
//			water.setOwNum(ornums.get(0)); // 营业流水号
		water.setIsBeginWithOne(owFlowRul.get("isBeginWithOne").toString());
		water.setHeadStr(owFlowRul.get("headStr").toString());
		water.setSeatId(Integer.valueOf(seatId));
		water.setPeopleCount(Integer.valueOf(eatNumber));
		water.setFirstTime(new Date());
		water.setWaiter(Integer.valueOf(waiterId));
		water.setOpenPos(Integer.valueOf(openPos));
		water.setPrivateRoomType(getBffa(Integer.valueOf(seatId))); // 设置包房费方案
		water.setSeatLabel(seatLable);
		water.setCrId(gradeId);
		water.setOwState(1);
		water.setIsTeam(0);
		water.setSeatAmount(1);
		if (!StringUtils.isEmpty(marketingId)) {
			water.setMarketingStaff(Integer.valueOf(marketingId));
		}
		if (!StringUtils.isEmpty(ydId)) {
			Map orgs = new HashMap();
			orgs.put("time", simpleDateFormat.format(new Date()));
			orgs.put("seatId", Integer.valueOf(seatId));
			DgReserveManager man = dgReserveManagerMapper
					.selectYdFromSeatId(orgs);
			if (man != null) {
				if (ydId.equals("1")) {
					water.setYdId(man.getId());
					DgReserveManager reserveManager = new DgReserveManager();
					reserveManager.setId(man.getId());
					reserveManager.setState(1);
					reserveManager.setSeatId(Integer.valueOf(seatId));
					dgReserveManagerMapper.updateManagerState(reserveManager);
				} else {
					DgReserveManager reserveManager = new DgReserveManager();
					reserveManager.setId(man.getId());
					reserveManager.setState(-1);
					reserveManager.setSeatId(Integer.valueOf(seatId));
					dgReserveManagerMapper.updateManagerState(reserveManager);
				}
			}
		}
		water.setOpenBis(getMealInt());
		if (!StringUtils.isEmpty(teamMemberInfo)) {
			water.setTeamMembers(teamMemberInfo);
			water.setIsTeam(1);
			water.setTeamMainSeat(billMapper.getMemberMainSeat(teamMemberInfo));
		} else {
			// 团队流水号
			List<String> teamOrnums = gson.fromJson(deskBusinessSettingService
					.createFlowNumber(org.getOrgCode(), pos.getNumber(), 1,
							SerialRulEnum.TDHM), new TypeToken<List<String>>() {
			}.getType());
			water.setTeamMembers(teamOrnums.get(0));
			water.setTeamMainSeat(Integer.valueOf(seatId));
		}
		billMapper.insertOpenWater(water);

		double subtotal = 0;
		if (!autoIncreaList.isEmpty()) {
			Map serviceMap = new HashMap();
			serviceMap.put("owId", water.getId());
			serviceMap.put("waiterId", waiterId);
			serviceMap.put("serviceTime", new Date());
			serviceMap.put("serviceType", 1);
			serviceMap.put("serviceNum", serviceNums.get(0)); // 新增服务流水号
			billMapper.insertOwServiceWater(serviceMap); // 插入服务流水
			for (DgSeatItem item : autoIncreaList) // 录入开单自动增加品项
			{
				if (!isThough(item.getId(), autoIncrErrItemIds, errTcItemIds)) {
					continue;
				}
				DgItemFile fileItem = dgItemFileMapper.selectByPrimaryKey(item
						.getItemId());
				double itemCount = 0;
				if (item.getUseOpenCounter() == 1) // 按客位人数
				{
					itemCount = MathExtend.multiply(item.getCount(),
							Integer.valueOf(eatNumber));
				} else {
					itemCount = item.getCount();
				}
				double itemTotal = 0;
				DgOwConsumerDetails detail = new DgOwConsumerDetails();
				detail.setItemFileId(item.getItemId());
				detail.setItemFileNumber(itemCount);
				detail.setNotes("1");
				// 判断插入是否是套餐
				detail.setIsTc((fileItem.getIsTc() == null) ? 0 : fileItem
						.getIsTc());
				detail.setOwId(Integer.valueOf("" + serviceMap.get("id")));
				// 先设置会员为0
				Map<String, Object> price = getDishPrice(item.getItemId(),
						org.getId(), seat.getConsArea(), itemCount, -1, true,
						false);
				itemTotal = (double) price.get("price");
				if ((boolean) price.get("isPriceCal") == true) {
					detail.setIsPriceCal(1);
				} else {
					detail.setIsPriceCal(0);

				}
				detail.setDiscount(1.0);
				detail.setItemFinalPrice(itemTotal);
//					detail.setItemPayMoney(itemTotal);
				itemTotal = MathExtend.multiply(itemTotal, itemCount);
				detail.setSubtotal(itemTotal);
				subtotal = MathExtend.add(subtotal, itemTotal); // 累加

				DgItemSaleLimit li = dgItemSaleLimitMapper
						.selectByDate(formatDate.format(new Date()));
				if (li != null) {
					DgItemSaleLimitS lis = new DgItemSaleLimitS();
					lis.setUseCount((new Double(itemCount)).intValue());
					lis.setLimitId(li.getId());
					lis.setItemId(item.getItemId());
					dgItemSaleLimitSMapper.updateCount(lis);
				}
				//更新库存
				Map orgs = new HashMap();
				orgs.put("itemFileId",item.getItemId());
				orgs.put("ItemFileCount",(new Double(itemCount)).intValue());
				String inveRet = billMapper.updateInveDate(orgs);
				insertInveCall(inveRet);
				// 插入明细表
				dgOwConsumerDetailsMapper.insertSelective(detail);

				// 判断插入是否是套餐
				if (fileItem.getIsTc() != null && fileItem.getIsTc() == 1) {
					insertTc(fileItem.getId(), itemCount,
							Integer.valueOf("" + serviceMap.get("id")), "1");
				}
			}
			// 打印后厨
			dgPrintDataService.insertAddBillPrint(
					Integer.valueOf("" + serviceMap.get("id")), subtotal);
		}
		if (subtotal > 0) {
			water.setIsIncreasingItem(1);
		} else {
			water.setIsIncreasingItem(0);
		}
		// 最后刷新营业流水品项和 小计
		water.setItemCostsSum(subtotal);

		water.setSubtotal(MathExtend.add(
				MathExtend.add(subtotal, water.getServiceCharge()),
				water.getPrivateRoomCost()));
		billMapper.updateOpenWaterPrimaryKey(water);

		// 保存押金
		if (!StringUtils.isEmpty(deposit)) {
			dgCashPledgeService.regMoney(water.getOwNum(), "0", "252",
					Double.valueOf(deposit), "开单押金", "开单押金");
		}
		ret.put("water",
				dgOpenWaterMapper.selectByPrimaryKey(water.getId()));
		ret.put("success", true);
		return ret;
	}

	@Override
	public Map<String, Object> getDishPrice(Integer itemId, Integer orgId,
											Integer areaId, double number, Integer waterId, boolean isOpenTeam,
											boolean isYxe) {
		// TODO Auto-generated method stub
		Map<String, Object> cRet = new HashMap<String, Object>();
		DgItemFile disItem = dgItemFileMapper.selectByPrimaryKey(itemId); // 是否允许打折
		if (disItem.getYxdz() != null && disItem.getYxdz() == 1) {
			if (!isOpenTeam && isYxe) {
				Map xsqg = getXsqgPrice(itemId);
				if (xsqg.containsKey("success")) {
					cRet.put("price", xsqg.get("success"));
					cRet.put("isPriceCal", true);
					return cRet;
				}
			}
			List<DgItemPricePriority> pri = dgItemPricePriorityMapper.getAll();
			for (DgItemPricePriority p : pri) {

				if (p.getCode().equals("002")) {
					// 市别价
					Map<String, Object> sb = getSbPrice(itemId);
					if (sb.containsKey("success")) {
						cRet.put("price", sb.get("success"));
						break;
					}
				} else if (p.getCode().equals("003")) {
					// 消费区域
					Map<String, Object> qy = getQyPrice(itemId, areaId);
					if (qy.containsKey("success")) {
						cRet.put("price", qy.get("success"));
						break;
					}
				} else if (p.getCode().equals("004")) {
					// 星期价
					Map<String, Object> xq = getXqPrice(itemId);
					if (xq.containsKey("success")) {
						cRet.put("price", xq.get("success"));
						break;
					}
				} else if (p.getCode().equals("005")) {
					// 阶梯价
					Map<String, Object> jt = getJtPrice(itemId);
					if (jt.containsKey("success")) {
						cRet.put("price", jt.get("success"));
						break;
					}
				} else if (p.getCode().equals("006")) {
					// 促销价
					Map<String, Object> cx = getCxPrice(itemId, number,
							waterId, isOpenTeam);
					if (cx.containsKey("success")) {
						cRet.put("price", cx.get("success"));
						break;
					}
				} else if (p.getCode().equals("007")) {
					// 时价
					Map<String, Object> sj = getSjPrice(itemId);
					if (sj.containsKey("success")) {
						cRet.put("price", sj.get("success"));
						break;
					}
				}
			}
			if (cRet.isEmpty()) // 没有查询到价格-标准价格
			{
				Map<String, Object> bzMap = getBzPrice(itemId);
				cRet.put("price", bzMap.get("price"));
				cRet.put("isPriceCal", false);
			} else {
				cRet.put("isPriceCal", true);
			}
		} else {
			Map<String, Object> bzMap = getBzPrice(itemId);
			cRet.put("price", bzMap.get("price"));
			cRet.put("isPriceCal", false);
		}
		return cRet;
	}

	/*
	 * 直接查询品项id返回标准价格
	 */
	private Map<String, Object> getBzPrice(Integer id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		DgItemFile item = dgItemFileMapper.selectByPrimaryKey(id);
		if (item != null) {
			ret.put("success", true);
			ret.put("price",
					item.getStandardPrice() == null ? 0 : item
							.getStandardPrice());
		}
		return ret;
	}

	/*
	 * 获取标准价格 先判断是否是特殊品项/停用品项/沽清品项 不是 再判断是否允许打折 允许打折再 获取当前时间市别
	 * 根据当前市别查询是否市别下有此品项,有就返回市别价格,没有就返回失败
	 */
	private Map<String, Object> getSbPrice(Integer id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<TbBis> tbBiss = tbBisMapper.selectAllBis();
		List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
		if (tbBiss.size() == 0) {
			ret.put("fail", "没有市别数据!");
		}
		for (int i = 0; i < tbBiss.size(); i++) {
			if (i != tbBiss.size() - 1) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("startTime", tbBiss.get(i).getBisTime());
				m.put("endTime", tbBiss.get(i + 1).getBisTime());
				m.put("nowTime", format.format(new Date()));
				m.put("id", tbBiss.get(i).getId());
				timeD.add(m);
			} else {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("startTime", tbBiss.get(i).getBisTime());
				m.put("endTime", tbBiss.get(0).getBisTime());
				m.put("nowTime", format.format(new Date()));
				m.put("id", tbBiss.get(i).getId());
				timeD.add(m);
			}
		}
		int TbisID = 0; // 获取市别id
		for (int i = 0; i < timeD.size(); i++) {
			int count = tbBisMapper.calculateSJD(timeD.get(i));
			if (count > 0) {
				TbisID = (int) timeD.get(i).get("id");
				break;
			}
		}
		if (TbisID == 0) // 没有就是最后个时段
		{
			TbisID = (int) timeD.get(timeD.size() - 1).get("id");
		}

		Map<String, Object> mealMap = new HashMap<String, Object>();
		mealMap.put("itemId", id);
		mealMap.put("mealId", TbisID);
		DgForMealTimePriceS dgForMealTimePriceS = dgForMealTimePriceSMapper
				.selectByItemIdAndMealtime(mealMap);
		if (dgForMealTimePriceS != null) {
			if (dgForMealTimePriceS.getPrice() == null) {
				ret.put("success", getBzPrice(id).get("price"));
			} else {
				ret.put("success", dgForMealTimePriceS.getPrice());
			}
		} else {
			ret.put("fail", "当前市别下没有此品项打折!");
		}
		return ret;
	}

	/**
	 * 品项价格按消费区域
	 */
	private Map<String, Object> getQyPrice(Integer id, Integer placeId) {
		Map<String, Object> ret = new HashMap<String, Object>();

		Map<String, Object> placeMap = new HashMap<String, Object>();
		placeMap.put("itemId", id);
		placeMap.put("placeId", placeId);
		DgPlacePriceS dgForMealTimePriceS = dgPlacePriceSMapper
				.selectByItemIdAndPriceId(placeMap);
		if (dgForMealTimePriceS != null) {
			if (dgForMealTimePriceS.getPrice() == null) {
				ret.put("success", getBzPrice(id).get("price"));
			} else {
				ret.put("success", dgForMealTimePriceS.getPrice());
			}
		} else {
			ret.put("fail", "当前区域下没有此品项打折!");
		}

		return ret;
	}

	/**
	 * 按星期获取品项价格
	 */
	private Map<String, Object> getXqPrice(Integer id) {
		Map<String, Object> ret = new HashMap<String, Object>();

		Map<String, Object> placeMap = new HashMap<String, Object>();
		DgItemForWeek dgForMealTimePriceS = dgItemForWeekMapper
				.selectByItemId(id);
		if (dgForMealTimePriceS != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (weekIndex < 0) {
				weekIndex = 0;
			}
			if (weekIndex == 0) {
				if (dgForMealTimePriceS.getX7() != null) {
					ret.put("success", dgForMealTimePriceS.getX7());
				} else {
					ret.put("fail", "当前星期下没有次品项打折");
				}
			}
			if (weekIndex == 1) {
				if (dgForMealTimePriceS.getX1() != null) {
					ret.put("success", dgForMealTimePriceS.getX1());
				} else {
					ret.put("fail", "当前星期下没有次品项打折");
				}
			}
			if (weekIndex == 2) {
				if (dgForMealTimePriceS.getX2() != null) {
					ret.put("success", dgForMealTimePriceS.getX2());
				} else {
					ret.put("fail", "当前星期下没有次品项打折");
				}
			}
			if (weekIndex == 3) {
				if (dgForMealTimePriceS.getX3() != null) {
					ret.put("success", dgForMealTimePriceS.getX3());
				} else {
					ret.put("fail", "当前星期下没有次品项打折");
				}
			}
			if (weekIndex == 4) {
				if (dgForMealTimePriceS.getX4() != null) {
					ret.put("success", dgForMealTimePriceS.getX4());
				} else {
					ret.put("fail", "当前星期下没有次品项打折");
				}
			}
			if (weekIndex == 5) {
				if (dgForMealTimePriceS.getX5() != null) {
					ret.put("success", dgForMealTimePriceS.getX5());
				} else {
					ret.put("fail", "当前星期下没有次品项打折");
				}
			}
			if (weekIndex == 6) {
				if (dgForMealTimePriceS.getX6() != null) {
					ret.put("success", dgForMealTimePriceS.getX6());
				} else {
					ret.put("fail", "当前星期下没有次品项打折");
				}
			}
		} else {
			ret.put("fail", "当前星期下没有此品项打折!");
		}

		return ret;
	}

	/**
	 * 阶梯价 id 品项id
	 */
	private Map<String, Object> getJtPrice(Integer id) {
		Map<String, Object> ret = new HashMap<String, Object>();

		DgItemPriceLadder ladder = dgItemPriceLadderMapper.selectByItemId(id);
		if (ladder != null) {
			if (ladder.getLadder1() != null) {
				ret.put("success", ladder.getLadder1());
			} else {
				if (ladder.getLadder2() != null) {
					ret.put("success", ladder.getLadder2());
				} else {
					ret.put("fail", "当前品项阶梯价格为null!");
				}
			}
		} else {
			ret.put("fail", "阶梯下没有此品项!");
		}
		return ret;
	}

	private Map getXsqgPrice(Integer id) {
		Map ret = new HashMap();
		DgItemTimeLimit dgItemTimeLimit = dgItemTimeLimitMapper
				.selectByItemIdAndOnTime(id);
		if (dgItemTimeLimit != null) {
			if (dgItemTimeLimit.getSurplusLimit() > 0) {
				if (dgItemTimeLimit.getPrice() != null) {
					ret.put("success", dgItemTimeLimit.getPrice());
					dgItemTimeLimit.setSurplusLimit(MathExtend.subtract(
							dgItemTimeLimit.getSurplusLimit(), 1));
					dgItemTimeLimitMapper.updateByPrimaryKey(dgItemTimeLimit);
				} else {
					ret.put("fail", "当前品项限时抢购价格为null!");
				}
			} else {
				ret.put("fail", "当前品项限时抢购价格为null!");
			}
		} else {
			ret.put("fail", "当前品项限时抢购价格为null!");
		}
		return ret;
	}

	/**
	 *
	 * 促销价 id品项id number 点单数量(暂定)
	 */
	private Map<String, Object> getCxPrice(Integer id, double number,
										   Integer waterId, boolean isOpenTeam) {
		Map<String, Object> ret = new HashMap<String, Object>();
		DgPromotionItem ladder = dgPromotionItemMapper.selectByItemId(id);
		if (ladder != null) {
			if (ladder.getProPrice() != null) {
				// 开单情况,直接加入
				if (isOpenTeam) {
					ret.put("success", ladder.getProPrice());
				} else {
					if (ladder.getMaxCount() != null
							&& ladder.getMaxCount() != 0) {
						Map orgs = new HashMap();
						orgs.put("itemId", id);
						orgs.put("owId", waterId);
						Double ydNumber = billMapper
								.getWaterItemCountByItemId(orgs);
						if (ydNumber != null) {
							if (ydNumber != 0) {
								double sub = MathExtend.subtract(
										ladder.getMaxCount(), ydNumber);
								if (sub > 0) {
									ret.put("success", ladder.getProPrice());
								} else if (sub <= 0) {
									ret.put("fail", "促销数量已满!");
								}
							}
						} else {
							ret.put("success", ladder.getProPrice());
						}
					} else {
						ret.put("success", ladder.getProPrice());
					}
				}
			} else {
				ret.put("fail", "当前品项促销价格为null!");
			}
		}
		return ret;
	}

	/**
	 *
	 * 时价 id品项id
	 */
	private Map<String, Object> getSjPrice(Integer id) {
		Map<String, Object> ret = new HashMap<String, Object>();

		DgItemCurrentPrice sj = dgItemCurrentPriceMapper.selectByItemId(id);
		if (sj != null) {
			if (sj.getCurrentPrice() != null) {
				ret.put("success", sj.getCurrentPrice());
			} else {
				ret.put("fail", "当前品项时价为null!");
			}
		} else {
			ret.put("fail", "时价下没有此品项!");
		}
		return ret;
	}

	@Override
	public Map<String, Object> selectSeatConsumeInfo(Integer seatId,
													 String number) {
		Map<String, Object> ret = new HashMap<String, Object>();
		DgSeatManager seatMan = dgSeatManagerMapper.selectBySeatId(seatId);
		if (seatMan != null) {
			// 获取最低消费
			int zdxf = seatMan.getZdxf();
			if (zdxf == 1) {
				ret.put("zdxf", 0.00);
			} else {
				int zdxfType = seatMan.getZdxfType();
				if (zdxfType == 1) {
					ret.put("zdxf", seatMan.getZdxfMoney());
				} else {
					double zdxfValue = MathExtend.multiply(
							seatMan.getZdxfMoney(), Integer.valueOf(number));
					ret.put("zdxf", zdxfValue);
				}
			}
			// 服务费
			int fwf = seatMan.getFwf();
			if (fwf == 1) {
				ret.put("fwf", 0.00);
			} else if (fwf == 2) {
				ret.put("fwf", seatMan.getFwfGd());
			} else if (fwf == 3) // 按消费比例收取(开台设置为 0)
			{
				ret.put("fwf", 0.00);
			} else if (fwf == 4) // 按服务人数
			{
				ret.put("fwf",
						MathExtend.multiply(seatMan.getFwfPeople(),
								Integer.valueOf(number)));
			}

			// 包房费
			int bff = seatMan.getBff();
			if (bff == 1) {
				ret.put("bff", 0.00);
			} else if (bff == 2) {
				ret.put("bff", seatMan.getBffGd());
			} else if (bff == 3) // 按消费客位人数
			{
				ret.put("bff",
						MathExtend.multiply(seatMan.getBffPeople(),
								Integer.valueOf(number)));
			} else if (bff == 4) // 固定包房收费方案(按消费时间断来,暂时不能计算,结算时计算)
			{
				ret.put("bff", 0.00);
			} else if (bff == 5) // 一周内设置不同的收费方案
			{
				ret.put("bff", 0.00);
			}
		} else {
			ret.put("zdxf", 0.00);
			ret.put("fwf", 0.00);
			ret.put("bff", 0.00);
		}
		return ret;
	}

	@Override
	public Map<String, Object> selectSeatConsumeByOpenId(String openId) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		DgOpenWater openWater = dgOpenWaterMapper.selectByPrimaryKey(Integer
				.valueOf(openId));
		if (openWater != null) {
			DgSeatManager seatMan = dgSeatManagerMapper
					.selectBySeatId(openWater.getSeatId());
			if (seatMan != null) {
				// 获取最低消费
				int zdxf = seatMan.getZdxf();
				if (zdxf == 1) {
					ret.put("zdxf", 0.00);
				} else {
					int zdxfType = seatMan.getZdxfType();
					if (zdxfType == 1) {
						ret.put("zdxf", seatMan.getZdxfMoney());
					} else {
						double zdxfValue = MathExtend.multiply(
								seatMan.getZdxfMoney(),
								openWater.getPeopleCount());
						ret.put("zdxf", zdxfValue);
					}
				}

				// 包房费
				int bff = seatMan.getBff();
				if (bff == 1) {
					ret.put("bff", 0.00);
				} else if (bff == 2) {
					ret.put("bff", seatMan.getBffGd());
				} else if (bff == 3) // 按消费比例收取(开始设置为0)
				{
					ret.put("bff", MathExtend.multiply(seatMan.getBffPeople(),
							openWater.getPeopleCount()));
				} else if (bff == 4) // 固定包房收费方案(按消费时间断来,暂时不能计算,结算时计算)
				{
					ret.put("bff", 0.00);
				} else if (bff == 5) // 一周内设置不同的收费方案
				{
					ret.put("bff", 0.00);
				}

				double xfh = openWater.getItemCostsSum()
						+ (double) ret.get("bff");
				// 消费满多少，不收取包房费
				if (xfh >= seatMan.getFwfConFree()) {
					ret.put("fwf", 0.00);
				} else {
					// 服务费
					int fwf = seatMan.getFwf();
					if (fwf == 1) {
						ret.put("fwf", 0.00);
					} else if (fwf == 2) {
						ret.put("fwf", seatMan.getFwfGd());
					} else if (fwf == 3) // 按消费比例收取(开台设置为 0) 品项和 + 包房费
					{
						ret.put("fwf", MathExtend.multiply(
								MathExtend.divide(xfh, 100),
								seatMan.getFwfXfRatio()));
					} else if (fwf == 4) // 按服务人数
					{
						ret.put("fwf", MathExtend.multiply(
								seatMan.getFwfPeople(),
								openWater.getPeopleCount()));
					}
				}

			} else {
				ret.put("zdxf", 0.00);
				ret.put("fwf", 0.00);
				ret.put("bff", 0.00);
			}
		} else {
			ret.put("error", APIEnumDefine.S007);
		}
		return null;
	}

	@Override
	public  Map<String, Object> openBillTeam(String operCode,
											 String openPos, List<Map<String, Object>> orgs, String ydId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(Integer.valueOf(openPos));
		pos = dgPosMapper.getPosByID(pos);
		TbOrg org = new TbOrg();
		org.setId(Integer.valueOf(pos.getOrganization()));
		org = tbOrgMapper.getOrgByID(org);
		// 营业流水号组合
//			List<String> ornums = gson.fromJson(deskBusinessSettingService
//					.createFlowNumber(org.getOrgCode(), pos.getNumber(),
//							orgs.size(), SerialRulEnum.YY),
//					new TypeToken<List<String>>() {
//					}.getType());

		Map owFlowRul = deskBusinessSettingService.getFlowRul(org.getOrgCode(),pos.getNumber(),SerialRulEnum.YY);

		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
						.createFlowNumber(org.getOrgCode(), pos.getNumber(),
								orgs.size(), SerialRulEnum.FW),
				new TypeToken<List<String>>() {
				}.getType());
		// 团队流水号
		List<String> teamOrnums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(org.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.TDHM), new TypeToken<List<String>>() {
		}.getType());

		// 验证所有桌位是否被占用
		for (int i = 0; i < orgs.size(); i++) {
			Map<String, Object> o = orgs.get(i);
			// 判断桌位是否空闲
			List<Integer> seatIds = new ArrayList<Integer>();
			seatIds.add(Integer.valueOf("" + o.get("seatId")));
			Map<String, Object> seatErr = checkSeatFree(seatIds);
			if (seatErr.containsKey("error")) {
				ret.put("error", APIEnumDefine.M0101002);
				return ret;
			}
		}

		for (int i = 0; i < orgs.size(); i++) {
			Map<String, Object> o = orgs.get(i);
			Map<String, Object> mainObj = orgs.get(0);
			String mainSeatId = "" + mainObj.get("seatId");
			String seatId = "" + o.get("seatId");
			String eatNumber = "" + o.get("eatNumber");
			String waiterId = "" + o.get("waiterId");
			DgConsumerSeat seat = new DgConsumerSeat();
			seat.setId(Integer.valueOf(seatId));
			seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);
			seat.setSeatState(2);
			seat.setLastOpenTime(new Date());
			dgConsumerSeatMapper.updateState(seat); // 更新桌位状态
			Map<String, Object> seatMan = selectSeatConsumeInfo(
					Integer.valueOf(seatId), eatNumber);
			// 获取开单自动增加品项
			List<DgSeatItem> autoIncreaList = dgSeatItemMapper
					.getBySeatId(Integer.valueOf(seatId));
			List<Integer> autoIncrErrItemIds = new ArrayList<Integer>();
			List<Integer> errTcItemIds = new ArrayList<Integer>();
			if (autoIncreaList.size() > 0) {
				List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
				for (DgSeatItem che : autoIncreaList) { // 验证每个品项的合法性
					Map<String, Object> checkMap = new HashMap<String, Object>();
					checkMap.put("itemId", che.getItemId());
					if (che.getUseOpenCounter() == 1) {
						checkMap.put(
								"number",
								MathExtend.multiply(che.getCount(),
										Integer.valueOf(eatNumber)));
					} else {
						checkMap.put("number", che.getCount());
					}
					checklist.add(checkMap);
				}
				Map<String, Object> errCheckItem = checkItemFile(checklist,
						null, true);
				if (!errCheckItem.isEmpty()) {
					addMapKeyValue("ItemFileError", "客位-" + seat.getName()
							+ (String) errCheckItem.get("error"), ret);
					if (errCheckItem.containsKey("errItemIds")) {
						String[] errItemIds = ((String) errCheckItem
								.get("errItemIds")).split("-");
						for (String er : errItemIds) {
							autoIncrErrItemIds.add(Integer.valueOf(er));
						}
					}
					if (errCheckItem.containsKey("errTc")) {
						errTcItemIds.addAll((List<Integer>) errCheckItem
								.get("errTc"));
					}
				}
			}

			DgOpenWater water = new DgOpenWater();
			water.setMinimumConsumption((double) seatMan.get("zdxf"));
			water.setServiceCharge((double) seatMan.get("fwf"));
			water.setPrivateRoomCost((double) seatMan.get("bff"));

			water.setIsBeginWithOne(owFlowRul.get("isBeginWithOne").toString());
			water.setHeadStr(owFlowRul.get("headStr").toString());
			water.setSeatId(Integer.valueOf(seatId));
			water.setPeopleCount(Integer.valueOf(eatNumber));
			water.setFirstTime(new Date());
			water.setWaiter(Integer.valueOf(waiterId));
			water.setOpenPos(Integer.valueOf(openPos));
			water.setOwState(1);
			water.setSeatAmount(1);
			water.setPrivateRoomType(getBffa(Integer.valueOf(seatId))); // 设置包房费方案
			water.setOpenBis(getMealInt());
			water.setTeamMembers(teamOrnums.get(0));
			water.setTeamMainSeat(Integer.valueOf(mainSeatId));
			water.setIsTeam(1);
			if (!StringUtils.isEmpty(ydId)) {
				Map ydorgs = new HashMap();
				ydorgs.put("time", simpleDateFormat.format(new Date()));
				ydorgs.put("seatId", Integer.valueOf(seatId));
				DgReserveManager man = dgReserveManagerMapper
						.selectYdFromSeatId(ydorgs);
				if (man != null) {
					if (ydId.equals("1")) {
						water.setYdId(man.getId());
						DgReserveManager reserveManager = new DgReserveManager();
						reserveManager.setId(man.getId());
						reserveManager.setState(1);
						reserveManager.setSeatId(Integer.valueOf(seatId));
						dgReserveManagerMapper
								.updateManagerState(reserveManager);
					} else {
						DgReserveManager reserveManager = new DgReserveManager();
						reserveManager.setId(man.getId());
						reserveManager.setState(-1);
						reserveManager.setSeatId(Integer.valueOf(seatId));
						dgReserveManagerMapper
								.updateManagerState(reserveManager);
					}
				}
			}
			billMapper.insertOpenWater(water);

			double subtotal = 0;
			if (!autoIncreaList.isEmpty()) {
				Map serviceMap = new HashMap();
				serviceMap.put("owId", water.getId());
				serviceMap.put("waiterId", waiterId);
				serviceMap.put("serviceTime", new Date());
				serviceMap.put("serviceNum", serviceNums.get(i)); // 新增服务流水号
				serviceMap.put("serviceType", 1);
				billMapper.insertOwServiceWater(serviceMap); // 插入服务流水
				for (DgSeatItem item : autoIncreaList) // 录入开单自动增加品项
				{
					if (!isThough(item.getId(), autoIncrErrItemIds,
							errTcItemIds)) {
						continue;
					}
					DgItemFile fileItem = dgItemFileMapper
							.selectByPrimaryKey(item.getItemId());
					double itemCount = 0;
					if (item.getUseOpenCounter() == 1) // 按客位人数
					{
						itemCount = MathExtend.multiply(item.getCount(),
								Integer.valueOf(eatNumber));
					} else {
						itemCount = item.getCount();
					}
					double itemTotal = 0;
					DgOwConsumerDetails detail = new DgOwConsumerDetails();
					detail.setItemFileId(item.getItemId());
					detail.setItemFileNumber(itemCount);
					detail.setNotes("1");
					detail.setIsTc((fileItem.getIsTc() == null) ? 0 : fileItem
							.getIsTc());
					detail.setOwId(Integer.valueOf("" + serviceMap.get("id")));
					// 先设置会员为0
					Map<String, Object> price = getDishPrice(item.getItemId(),
							org.getId(), seat.getConsArea(), itemCount, -1,
							true, false);
					itemTotal = (double) price.get("price");
					if ((boolean) price.get("isPriceCal") == true) {
						detail.setIsPriceCal(1);
					} else {
						detail.setIsPriceCal(0);
					}
					detail.setDiscount(1.0);
					detail.setItemFinalPrice(itemTotal);
//						detail.setItemPayMoney(itemTotal);
					itemTotal = MathExtend.multiply(itemTotal, itemCount);
					detail.setSubtotal(itemTotal);
					subtotal = MathExtend.add(subtotal, itemTotal); // 累加

					DgItemSaleLimit li = dgItemSaleLimitMapper
							.selectByDate(formatDate.format(new Date()));
					if (li != null) {
						DgItemSaleLimitS lis = new DgItemSaleLimitS();
						lis.setUseCount((new Double(itemCount)).intValue());
						lis.setLimitId(li.getId());
						lis.setItemId(item.getItemId());
						dgItemSaleLimitSMapper.updateCount(lis);
					}
					Map zcorgs = new HashMap();
					zcorgs.put("itemFileId",item.getItemId());
					zcorgs.put("ItemFileCount",(new Double(itemCount)).intValue());
					insertInveCall(billMapper.updateInveDate(zcorgs));
					// 插入明细表
					dgOwConsumerDetailsMapper.insertSelective(detail);

					// 判断插入是否是套餐
					if (fileItem.getIsTc() != null && fileItem.getIsTc() == 1) {
						insertTc(fileItem.getId(), itemCount,
								Integer.valueOf("" + serviceMap.get("id")), "1");
					}
				}
				// 打印后厨
				dgPrintDataService.insertAddBillPrint(
						Integer.valueOf("" + serviceMap.get("id")), subtotal);
			}
			if (subtotal > 0) {
				water.setIsIncreasingItem(1);
			} else {
				water.setIsIncreasingItem(0);
			}
			// 最后刷新营业流水品项和 小计
			water.setItemCostsSum(subtotal);

			water.setSubtotal(MathExtend.add(
					MathExtend.add(subtotal, water.getServiceCharge()),
					water.getPrivateRoomCost()));
			billMapper.updateOpenWaterPrimaryKey(water);
			if (!ret.containsKey("success")) {
				ret.put("success", true);
			}
		}
		return ret;
	}

	@Override
	public List<Map<String, Object>> selectAllItem(Integer seatId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> ret = billMapper.selectAllItem(seatId);
		for (int i = 0; i < ret.size(); i++) {
			Map<String, Object> r = ret.get(i);
			if (r.containsKey("item_id")) {
				r.put("isGq", true);
			} else {
				r.put("isGq", false);
			}
			if (!r.containsKey("unit")) {
				r.put("unit", "");
			}
			if (!r.containsKey("lspx")) {
				r.put("lspx", 0);
			}
			if (!r.containsKey("is_tc")) {
				r.put("is_tc", 0);
			}
		}
		return ret;
	}

	@Override
	public List<Map<String, Object>> selectYxeAllItem(Integer seatId) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> ret = billMapper.selectYxeAllItem(seatId);
		for (int i = 0; i < ret.size(); i++) {
			Map<String, Object> r = ret.get(i);
			if (r.containsKey("item_id")) {
				r.put("isGq", true);
			} else {
				r.put("isGq", false);
			}
			if (!r.containsKey("unit")) {
				r.put("unit", "");
			}
			if (!r.containsKey("lspx")) {
				r.put("lspx", 0);
			}
			if (!r.containsKey("is_tc")) {
				r.put("is_tc", 0);
			}
		}
		return ret;
	}

	@Override
	public synchronized Map<String, Object> addBill(String operCode,
													List<Map<String, Object>> org, String openNumber, String type,
													String zdbz, boolean isYxe) {
		DgOpenWater water = dgOpenWaterMapper
				.selectByOpenWaterByNum(openNumber);
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> o : org) { // 验证每个品项的合法性
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("itemId", o.get("itemFileId"));
			checkMap.put("number", o.get("itemFileNumber"));
			checklist.add(checkMap);
		}
		List<DgOpenWater> waters = new ArrayList<DgOpenWater>();
		waters.add(water);
		Map<String, Object> errCheckItem = checkItemFile(checklist, waters,
				false);
		if (!errCheckItem.isEmpty()) {
			ret.put("error", errCheckItem.get("error"));
			return ret;
		}

		SysUser user = null;
		if(StringUtil.isNotEmpty(operCode)){
			user = sysUserMapper.selectUserByUserCode(operCode);
		}

		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(water.getOpenPos());
		pos = dgPosMapper.getPosByID(pos);
		DgUrlSetting offset = dgUrlSettingMapper.selectByCode("IS_OFFSET");
		TbOrg tbOrg = new TbOrg();
		tbOrg.setId(Integer.valueOf(pos.getOrganization()));
		tbOrg = tbOrgMapper.getOrgByID(tbOrg);
		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());

		DgConsumerSeat seat = new DgConsumerSeat();
		seat.setId(water.getSeatId());
		seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);
		// 插入服务流水
		Map serviceMap = new HashMap();
		serviceMap.put("owId", water.getId());
		serviceMap.put("waiterId", user == null ? water.getWaiter() :user.getId());
		serviceMap.put("serviceTime", new Date());
		serviceMap.put("zdbz", zdbz);
		serviceMap.put("serviceType", 2);
		serviceMap.put("serviceNum", serviceNums.get(0));
		billMapper.insertOwServiceWater(serviceMap);
		double openwaterSubtotal = 0;
		List<DgOwConsumerDetails> owConsDetails = new ArrayList<DgOwConsumerDetails>();
		for (Map<String, Object> o : org) {
			DgItemFile itemFile = dgItemFileMapper.selectByPrimaryKey(Integer
					.valueOf("" + o.get("itemFileId")));
			DgOwConsumerDetails cd = new DgOwConsumerDetails();
			cd.setItemFileId(Integer.valueOf("" + o.get("itemFileId")));
			cd.setItemFileNumber((double) o.get("itemFileNumber"));
			if (o.containsKey("itemFileZs")) {
				cd.setItemFileZs(Double.valueOf("" + o.get("itemFileZs")));
			}
			if (o.containsKey("servingType")) {
				cd.setServingType(Integer.valueOf("" + o.get("servingType")));
			}
			if (o.containsKey("servingTypeGlobal")) {
				cd.setServingTypeGlobal(Integer.valueOf(""
						+ o.get("servingTypeGlobal")));
			}
			if (o.containsKey("expectationsServingTime")) {
				try {
					cd.setExpectationsServingTime(simpleDateFormat.parse(""
							+ o.get("expectationsServingTime")));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			if (o.containsKey("isGift")) {
				if ((int) o.get("isGift") == 1) {
					cd.setNotes("3");
				} else {
					cd.setNotes("2");
				}
			} else {
				cd.setNotes("2");
			}
			cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile.getIsTc());
			cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
			// 先录入额外项(方便计算服务流水小计)
			double zzffTotal = 0;// 制作费用和
			if (o.get("extra") != null) {
				List<Map<String, Object>> ext = (List<Map<String, Object>>) o
						.get("extra");
				for (Map<String, Object> e : ext) {
					DgOwDetailsOther dts = new DgOwDetailsOther();
					dts.setItemId(cd.getItemFileId());
					dts.setSfId(cd.getOwId());
					dts.setOtype(Integer.valueOf("" + e.get("otype")));// 1、口味。2要求。3制作方法
					if (e.containsKey("ocode")) {
						dts.setOcode("" + e.get("ocode"));// 类型编码 临时数据为0
					}
					if (e.containsKey("oname")) {
						dts.setOname("" + e.get("oname"));
					}
					if (e.containsKey("ocosts")) {
						dts.setOcosts(Double.valueOf("" + e.get("ocosts")));
					}
					if (e.containsKey("zzffSf")) {
						dts.setZzffSf(Integer.valueOf("" + e.get("zzffSf")));
					}
					if (e.containsKey("zzffSfType")) {
						dts.setZzffSfType(Integer.valueOf(""
								+ e.get("zzffSfType")));
						// 多重判断是否是制作方法
						if (dts.getOtype() == 3 && dts.getOcosts() != null
								&& dts.getZzffSfType() == 0) {
							zzffTotal = MathExtend.add(zzffTotal,
									dts.getOcosts());
						} else if (dts.getOtype() == 3
								&& dts.getOcosts() != null
								&& dts.getZzffSfType() == 1) {
							double oc = MathExtend.multiply(dts.getOcosts(),
									cd.getItemFileNumber());
							zzffTotal = MathExtend.add(zzffTotal, oc);
						}
					}
					dgOwDetailsOtherMapper.insertSelective(dts);
				}
			}
			// 插入套餐子项
			if (cd.getIsTc() == 1) {
				insertTc(itemFile.getId(), cd.getItemFileNumber(),
						cd.getOwId(), "2");
			}

			DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
					.format(new Date()));
			if (li != null) {
				DgItemSaleLimitS lis = new DgItemSaleLimitS();
				lis.setUseCount((double) o.get("itemFileNumber"));
				lis.setLimitId(li.getId());
				lis.setItemId(Integer.valueOf("" + o.get("itemFileId")));
				dgItemSaleLimitSMapper.updateCount(lis);
			}

			// 获取品项当前价格
			Map<String, Object> price = getDishPrice(cd.getItemFileId(),
					Integer.valueOf(pos.getOrganization()), seat.getConsArea(),
					cd.getItemFileNumber(), water.getId(), false, isYxe);
			double itemTotal = (double) price.get("price");
			if ((boolean) price.get("isPriceCal") == true) {
				cd.setIsPriceCal(1);
			} else {
				cd.setIsPriceCal(0);

			}
			double subtotal = 0.0;
			if (cd.getNotes().equals("3")) {
				cd.setProductionCosts(0.0);
				cd.setItemFinalPrice(0.0);
//				cd.setItemPayMoney(0.0);
				cd.setSubtotal(0.0);
				cd.setZsProductionCosts(zzffTotal);
				cd.setZsItemFinalPrice(itemTotal);
				double moc = MathExtend.multiply(itemTotal,
						cd.getItemFileNumber());
				subtotal = 0.0;
				cd.setZsSubtotal(subtotal);
			} else {
				cd.setProductionCosts(zzffTotal);
				cd.setItemFinalPrice(itemTotal);
//				cd.setItemPayMoney(itemTotal);
				double moc = MathExtend.multiply(itemTotal,
						cd.getItemFileNumber());
				subtotal = MathExtend.add(zzffTotal, moc);
				cd.setSubtotal(subtotal);
			}
			cd.setDiscount(1.0);
			cd.setAddByYxe(isYxe?1:0);
			owConsDetails.add(cd);
			openwaterSubtotal = MathExtend.add(openwaterSubtotal, subtotal);
		}
		// 批量插入品项
		dgOwConsumerDetailsMapper.insertBatch(owConsDetails);
		// 最后更新营业流水号
		water.setItemCostsSum(MathExtend.add(water.getItemCostsSum(),
				openwaterSubtotal));
		water.setSubtotal(MathExtend.add(water.getSubtotal(), openwaterSubtotal));
		billMapper.updateOpenWaterPrimaryKey(water);

		// 发送库存冲减
		if(offset != null && offset.getValue().equals("1")){
			DgOffset dgOffset = new DgOffset(simpleDateFormat.format(new Date()), com.alibaba.fastjson.JSONArray.toJSONString(checklist),1);
			dgOffsetMapper.insert(dgOffset);
		}

		// 打印后厨
		if (type.equals("1")) {
			dgPrintDataService.insertAddBillPrint(
					Integer.valueOf("" + serviceMap.get("id")),
					openwaterSubtotal);
		} else if(type.equals("3")){
			dgPrintDataService.insertCtAddBillPrint(
					Integer.valueOf("" + serviceMap.get("id")),
					openwaterSubtotal);
		}
		ret.put("success", true);
		return ret;
	}

	@Override
	public Map<String, Object> selectTcDetail(Integer tcId) {
		// TODO Auto-generated method stub
		Map<String, Object> tcInfo = new HashMap<String, Object>();
		DgItemFilePackage pk = dgItemFilePackageMapper
				.selectByItemFileId(Integer.valueOf(tcId));
		if (pk != null) {
			List<Map> bx = dgItemFilePackageBxMapper.selectByPackageId(pk
					.getId());
			// List<DgItemFilePackageKx> kx = dgItemFilePackageKxMapper
			// .selectByPackageId(pk.getId());
			// List<DgItemFilePackageTh> th = dgItemFilePackageThMapper
			// .selectByPackageId(pk.getId());
			// List<DgItemFilePackageSlxd> slxd = dgItemFilePackageSlxdMapper
			// .selectByPackageId(pk.getId());
			pk.setDgItemFilePackageBx(bx);
			// pk.setDgItemFilePackageKx(kx);
			// pk.setDgItemFilePackageSlxd(slxd);
			// pk.setDgItemFilePackageTh(th);
			tcInfo.put("tc_detail", pk);
		} else {
			tcInfo.put("tc_detail", null);
		}
		return tcInfo;
	}

	/*
	 * 验证品项是否存在/沽清/停用/今日限量
	 */
	private Map<String, Object> checkItemFile(
			List<Map<String, Object>> itemIds, List<DgOpenWater> waterIds,
			boolean isOpenBill) {
		// TODO Auto-generated method stub
		Map<String, Object> errList = new HashMap<String, Object>();
		for (Map item : itemIds) {
			DgItemFile itemFile = dgItemFileMapper.selectByPrimaryKey(Integer
					.valueOf("" + item.get("itemId"))); // 品项是否存在
			if (itemFile != null) {
				if (itemFile.getIsTc() != null && itemFile.getIsTc() == 1) {
					DgItemFilePackage pk = dgItemFilePackageMapper
							.selectByItemFileId(itemFile.getId());
					if (pk != null) {
						List<Map> bx = dgItemFilePackageBxMapper
								.selectByPackageId(pk.getId());
						{
							for (Map b : bx) {
								if (b.get("item_file_id") != null) {
									DgItemFile tItemFile = dgItemFileMapper
											.selectByPrimaryKey((int) b
													.get("item_file_id")); // 品项是否存在
									if (b.get("item_amount") != null) {
										checkItemSingle(
												tItemFile,
												errList,
												MathExtend.multiply(
														(double) item
																.get("number"),
														(int) b.get("item_amount")),
												itemFile.getId(), isOpenBill,
												waterIds);
									}
								}
							}
						}
					}
					checkItemSingle(itemFile, errList,
							(double) item.get("number"), 0, isOpenBill,
							waterIds);
				} else {
					checkItemSingle(itemFile, errList,
							(double) item.get("number"), 0, isOpenBill,
							waterIds);
				}
			} else {
				addMapKeyValue("error", "id为" + item.get("itemId") + "的品项不存在",
						errList);
				addMapKeyValueSplit("errItemIds",
						Integer.valueOf("" + item.get("itemId")), errList);
			}
		}

		return errList;
	}

	/*
	 * 
	 * itemId套餐id number套餐数量 serviceId 服务号id
	 */
	private void insertTc(int itemId, double number, int serviceId, String notes) {
		DgItemFilePackage pk = dgItemFilePackageMapper
				.selectByItemFileId(itemId);
		if (pk != null) {
			List<Map> bx = dgItemFilePackageBxMapper.selectByPackageId(pk
					.getId());
			{
				for (Map b : bx) {
					if (b.get("item_file_id") == null
							|| b.get("item_amount") == null) {
						continue;
					}
					DgItemFile tItemFile = dgItemFileMapper
							.selectByPrimaryKey((int) b.get("item_file_id")); // 品项是否存在
					DgOwConsumerDetails detail = new DgOwConsumerDetails();
					detail.setItemFileId(tItemFile.getId());
					if (notes.equals("4") || notes.equals("5")) { // 退单
						detail.setItemFileNumber(-number
								* (int) b.get("item_amount"));
					} else { // 开单自增品项/加单/赠单
						detail.setItemFileNumber(number
								* (int) b.get("item_amount"));
					}
					detail.setNotes(notes);
					detail.setIsTc(-1);
					detail.setParentId(itemId);
					detail.setOwId(serviceId);
					detail.setItemFinalPrice(0.0); // 套餐内价格都设置为0
//					detail.setItemPayMoney(0.0);
					detail.setSubtotal(0.0);
					DgItemSaleLimit li = dgItemSaleLimitMapper
							.selectByDate(formatDate.format(new Date()));
					if (li != null) {
						DgItemSaleLimitS lis = new DgItemSaleLimitS();
						if (notes.equals("4") || notes.equals("5")) { // 退单
							lis.setUseCount(-number
									* (int) b.get("item_amount"));
						} else { // 开单自增品项/加单/赠单
							lis.setUseCount(number * (int) b.get("item_amount"));
						}
						lis.setLimitId(li.getId());
						lis.setItemId(tItemFile.getId());
						dgItemSaleLimitSMapper.updateCount(lis);
					}
					Map orgs = new HashMap();
					orgs.put("itemFileId",tItemFile.getId());
					if(notes.equals("4") || notes.equals("5")){
						orgs.put("ItemFileCount",-number * (int) b.get("item_amount"));
					} else {
						orgs.put("ItemFileCount",number * (int) b.get("item_amount"));
					}
					insertInveCall(billMapper.updateInveDate(orgs));
					// 插入明细表
					dgOwConsumerDetailsMapper.insertSelective(detail);

				}
			}
		}
	}

	/**
	 *
	 * @param itemFile
	 *            品项
	 * @param errList
	 *            回调数据
	 * @param number
	 *            点菜数量
	 * @param tcId
	 *            套餐id 不是套餐就传0
	 */
	private void checkItemSingle(DgItemFile itemFile,
								 Map<String, Object> errList, double number, Integer tcId,
								 boolean isOpenBill, List<DgOpenWater> waterIds) {
		DgItemDisable diItem = dgItemDisableMapper.seleByItemId(itemFile
				.getId()); // 停用品项
		if (diItem == null) {
			int stock = dgItemOutofStockMapper.selectByItemId(itemFile.getId()); // 沽清品项
			if (stock > 0) {
				addMapKeyValue("error", itemFile.getName() + "是沽清品项", errList);
				addMapKeyValueSplit("errItemIds", itemFile.getId(), errList);
				addMapKeyValueSplitString("errItemCodes",itemFile.getNum(), errList);
			} else {
				DgItemSaleLimit li = dgItemSaleLimitMapper
						.selectByDate(formatDate.format(new Date()));
				if (li != null) {
					Map<String, Object> sm = new HashMap<String, Object>();
					sm.put("limitId", li.getId());
					sm.put("itemId", itemFile.getId());
					List<DgItemSaleLimitS> sli = dgItemSaleLimitSMapper
							.getAllData(sm);
					if (!sli.isEmpty()) {
						if (number > sli.get(0).getFrontSaleCount()) { // 超出限制就设置为当前剩余量
							addMapKeyValue("error", itemFile.getName()
									+ "是超过今日限量数量", errList);
							addMapKeyValueSplit("errItemIds",itemFile.getId(), errList);
							addMapKeyValueSplitString("errItemCodes",itemFile.getNum(), errList);
						}
					}
				}
				if (!isOpenBill) {
					DgPromotionItem ladder = dgPromotionItemMapper
							.selectByItemId(itemFile.getId());
					if (ladder != null) {
						if (ladder.getMaxCount() != null
								&& ladder.getMaxCount() != 0) {
							for (DgOpenWater water : waterIds) {
								Map orgs = new HashMap();
								orgs.put("itemId", itemFile.getId());
								orgs.put("owId", water.getId());
								Double ydNumber = billMapper
										.getWaterItemCountByItemId(orgs);
								if (ydNumber != null) {
									if (ydNumber != 0) {
										double sub = MathExtend.subtract(
												ladder.getMaxCount(), ydNumber);
										if (sub > 0 && number > sub) {
											addMapKeyValue("error",
													itemFile.getName()
															+ "已点数量,大于促销数量",
													errList);
											addMapKeyValueSplit("errItemIds",
													itemFile.getId(), errList);
											addMapKeyValueSplitString("errItemCodes",itemFile.getNum(), errList);
										}
									}
								} else {
									if (number > ladder.getMaxCount()) {
										addMapKeyValue("error",
												itemFile.getName()
														+ "点菜数量,大于促销数量",
												errList);
										addMapKeyValueSplit("errItemIds",
												itemFile.getId(), errList);
										addMapKeyValueSplitString("errItemCodes",itemFile.getNum(), errList);
									}
								}
							}
						}
					}
				}
				if (waterIds != null) {
					for (DgOpenWater water : waterIds) {
						Map orgs = new HashMap();
						orgs.put("itemId", itemFile.getId());
						orgs.put("seatId", water.getSeatId());
						Integer count = billMapper
								.selectLimitItemCountBySeatId(orgs);
						if (count != null && count > 0) {
							addMapKeyValue("error", itemFile.getName() + "是"
									+ water.getAreaName() + "区域限售品项", errList);
							addMapKeyValueSplit("errItemIds", itemFile.getId(),
									errList);
							addMapKeyValueSplitString("errItemCodes",itemFile.getNum(), errList);
						}
					}
				}
			}
		} else {
			addMapKeyValue("error", itemFile.getName() + "是停用品项", errList);
			addMapKeyValueSplit("errItemIds", itemFile.getId(), errList);
			addMapKeyValueSplitString("errItemCodes",itemFile.getNum(), errList);

		}
	}

	/**
	 * 判断桌位是否是空闲状态 1空闲、2占用、3清扫、4预定、5埋单
	 */
	private Map<String, Object> checkSeatFree(List<Integer> seatIds) {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (Integer seatId : seatIds) {
			DgConsumerSeat seat = new DgConsumerSeat();
			seat.setId(Integer.valueOf(seatId));
			seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);
			if (seat.getSeatState() != 1 && seat.getSeatState() != 4) {
				if (seat.getSeatState() == 2) {
					addMapKeyValue("error", seat.getAllName() + "桌位处于占用状态", ret);
				} else if (seat.getSeatState() == 3) {
					addMapKeyValue("error", seat.getAllName() + "桌位处于清扫", ret);
				} else if (seat.getSeatState() == 5) {
					addMapKeyValue("error", seat.getAllName() + "桌位处于埋单", ret);
				}
			}
		}
		return ret;
	}

	private void addMapKeyValue(String key, String value, Map map) {
		if (map.get(key) != null) {
			map.put(key, map.get(key) + "\n" + value);
		} else {
			map.put(key, value);
		}
	}

	private void addMapKeyValueSplit(String key, Integer value, Map map) {
		if (map.get(key) != null) {
			map.put(key, map.get(key) + "-" + value);
		} else {
			map.put(key, "" + value);
		}
	}

	private void addMapKeyValueSplitString(String key, String value, Map map) {
		if (map.get(key) != null) {
			map.put(key, map.get(key) + "," + value);
		} else {
			map.put(key, "" + value);
		}
	}

	private void addMapKeyValueListTc(String key, Integer value, Map map) {
		if (map.get(key) != null) {
			boolean havaTc = false;
			List<Integer> tcList = (List<Integer>) map.get(key);
			for (Integer l : tcList) {
				if (l == value) {
					havaTc = true;
					break;
				}
			}
			if (!havaTc) {
				tcList.add(value);
			}
		} else {
			List<Integer> tcList = new ArrayList<Integer>();
			tcList.add(value);
			map.put(key, tcList);
		}
	}

	private boolean isThough(Integer itemId, List<Integer> errItemIds,
							 List<Integer> errTcIds) {
		errItemIds.addAll(errTcIds);
		boolean isErrItemId = false;
		for (Integer errId : errItemIds) {
			if (errId == itemId) {
				isErrItemId = true;
				break;
			}
		}
		if (isErrItemId) // 后台报错品项/不录入
		{
			return false;
		} else {
			return true;
		}
	}

	/*
	 * 获取当前时间下的市别id
	 */
	public String getMealInt() {
		List<TbBis> tbBiss = tbBisMapper.selectAllBis();
		List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
		if (tbBiss.size() == 0) {
			return "请到后台设置市别";
		}
		for (int i = 0; i < tbBiss.size(); i++) {
			if (i != tbBiss.size() - 1) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("startTime", tbBiss.get(i).getBisTime());
				m.put("endTime", tbBiss.get(i + 1).getBisTime());
				m.put("nowTime", format.format(new Date()));
				m.put("TbBis", tbBiss.get(i));
				timeD.add(m);
			} else {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("startTime", tbBiss.get(i).getBisTime());
				m.put("endTime", tbBiss.get(0).getBisTime());
				m.put("nowTime", format.format(new Date()));
				m.put("TbBis", tbBiss.get(0));
				timeD.add(m);
			}
		}
		String TbisName = null; // 获取市别id
		for (int i = 0; i < timeD.size(); i++) {
			int count = tbBisMapper.calculateSJD(timeD.get(i));
			if (count > 0) {
				TbisName = ((TbBis) timeD.get(i).get("TbBis")).getBisName();
				break;
			}
		}
		if (TbisName == null) // 没有就是最后个时段
		{
			TbisName = ((TbBis) timeD.get(timeD.size() - 1).get("TbBis"))
					.getBisName();
		}
		return TbisName;
	}

	@Override
	public synchronized Map<String, Object> insertGiveBill(String operCode,
														   List<Map<String, Object>> org, String openNumber,String zdbz) {
		DgOpenWater water = dgOpenWaterMapper
				.selectByOpenWaterByNum(openNumber);
		Map<String, Object> ret = new HashMap<String, Object>();
		if (!havaQcZsAndBack(operCode, org, 1)) {
			ret.put("error", APIEnumDefine.M0101018);
			return ret;
		}
		List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> o : org) { // 验证每个品项的合法性
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("itemId", o.get("itemFileId"));
			checkMap.put("number", o.get("itemFileNumber"));
			checklist.add(checkMap);
		}
		List<DgOpenWater> waters = new ArrayList<DgOpenWater>();
		waters.add(water);
		Map<String, Object> errCheckItem = checkItemFile(checklist, waters,
				false);
		if (!errCheckItem.isEmpty()) {
			ret.put("error", errCheckItem.get("error"));
			return ret;
		}
		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(water.getOpenPos());
		pos = dgPosMapper.getPosByID(pos);
		TbOrg tbOrg = new TbOrg();
		tbOrg.setId(Integer.valueOf(pos.getOrganization()));
		tbOrg = tbOrgMapper.getOrgByID(tbOrg);
		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());

		DgConsumerSeat seat = new DgConsumerSeat();
		seat.setId(water.getSeatId());
		seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);

		// 插入服务流水
		Map serviceMap = new HashMap();
		serviceMap.put("owId", water.getId());
		serviceMap.put("waiterId", water.getWaiter());
		serviceMap.put("serviceTime", new Date());
		serviceMap.put("serviceType", 3);
		serviceMap.put("zdbz", zdbz);
		serviceMap.put("serviceNum", serviceNums.get(0));
		billMapper.insertOwServiceWater(serviceMap);
		for (Map<String, Object> o : org) {
			DgItemFile itemFile = dgItemFileMapper.selectByPrimaryKey(Integer
					.valueOf("" + o.get("itemFileId")));
			DgOwConsumerDetails cd = new DgOwConsumerDetails();
			cd.setItemFileId(Integer.valueOf("" + o.get("itemFileId")));
			cd.setItemFileNumber((double) o.get("itemFileNumber"));
			if (o.containsKey("itemFileZs")) {
				cd.setItemFileZs(Double.valueOf("" + o.get("itemFileZs")));
			}
			if (o.containsKey("servingType")) {
				cd.setServingType(Integer.valueOf("" + o.get("servingType")));
			}
			if (o.containsKey("productionCosts")) {
				cd.setProductionCosts(Double.valueOf(""
						+ o.get("productionCosts")));
			}
			if (o.containsKey("servingTypeGlobal")) {
				cd.setServingTypeGlobal(Integer.valueOf(""
						+ o.get("servingTypeGlobal")));
			}
			if (o.containsKey("expectationsServingTime")) {
				try {
					cd.setExpectationsServingTime(simpleDateFormat.parse(""
							+ o.get("expectationsServingTime")));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			cd.setNotes("3");
			cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile.getIsTc());
			cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
			cd.setItemFinalPrice(0.0);
//			cd.setItemPayMoney(0.0);
			// 先录入额外项(方便计算服务流水小计)
			double zzffTotal = 0;// 制作费用和
			if (o.get("extra") != null) {
				List<Map<String, Object>> ext = (List<Map<String, Object>>) o
						.get("extra");
				for (Map<String, Object> e : ext) {
					DgOwDetailsOther dts = new DgOwDetailsOther();
					dts.setItemId(cd.getItemFileId());
					dts.setSfId(cd.getOwId());
					dts.setOtype(Integer.valueOf("" + e.get("otype")));// 1、口味。2要求。3制作方法
					if (e.containsKey("ocode")) {
						dts.setOcode("" + e.get("ocode"));// 类型编码 临时数据为0
					}
					if (e.containsKey("oname")) {
						dts.setOname("" + e.get("oname"));
					}
					if (e.containsKey("ocosts")) {
						dts.setOcosts(0.0);
					}
					if (e.containsKey("zzffSf")) {
						dts.setZzffSf(Integer.valueOf("" + e.get("zzffSf")));
					}
					if (e.containsKey("zzffSfType")) {
						dts.setZzffSfType(Integer.valueOf(""
								+ e.get("zzffSfType")));
						// 多重判断是否是制作方法
						if (dts.getOtype() == 3 && dts.getOcosts() != null
								&& dts.getZzffSfType() == 0) {
							zzffTotal = MathExtend.add(zzffTotal,
									dts.getOcosts());
						} else if (dts.getOtype() == 3
								&& dts.getOcosts() != null
								&& dts.getZzffSfType() == 1) {
							zzffTotal = MathExtend.add(
									zzffTotal,
									MathExtend.multiply(dts.getOcosts(),
											cd.getItemFileNumber()));
						}
					}
					dgOwDetailsOtherMapper.insertSelective(dts);
				}
			}
			// 插入套餐子项
			if (cd.getIsTc() == 1) {
				insertTc(itemFile.getId(), cd.getItemFileNumber(),
						cd.getOwId(), "3");
			}

			// 获取品项当前价格
			Map<String, Object> price = getDishPrice(cd.getItemFileId(),
					Integer.valueOf(pos.getOrganization()), seat.getConsArea(),
					cd.getItemFileNumber(), water.getId(), false, false);
			double itemTotal = (double) price.get("price");
			if ((boolean) price.get("isPriceCal") == true) {
				cd.setIsPriceCal(1);
			} else {
				cd.setIsPriceCal(0);

			}
			cd.setZsProductionCosts(zzffTotal);
			cd.setZsItemFinalPrice(itemTotal);
			double moc = MathExtend.multiply(itemTotal, cd.getItemFileNumber());
			double subtotal = MathExtend.add(zzffTotal, moc);
			cd.setZsSubtotal(subtotal);

			DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
					.format(new Date()));
			if (li != null) {
				DgItemSaleLimitS lis = new DgItemSaleLimitS();
				lis.setUseCount((double) o.get("itemFileNumber"));
				lis.setLimitId(li.getId());
				lis.setItemId(Integer.valueOf("" + o.get("itemFileId")));
				dgItemSaleLimitSMapper.updateCount(lis);
			}
			cd.setSubtotal(0.0);
			dgOwConsumerDetailsMapper.insertSelective(cd);
		}
		//赠单厨打
		dgPrintDataService.insertAddBillPrint(
				Integer.valueOf("" + serviceMap.get("id")),
				0.0);
		ret.put("success", true);
		return ret;
	}

	@Override
	public List<TbRfct> selectAllBackBillInfo() {
		// TODO Auto-generated method stub
		TbRfct rfct = new TbRfct();
		rfct.setIsDel("0");
		List<TbRfct> rfcts = tbRfctMapper.getAllList(rfct);
		for (TbRfct tbs : rfcts) {
			TbRfc rfc = new TbRfc();
			rfc.setIsDel("0");
			rfc.setRfcType("" + tbs.getId());
			tbs.setTbRfcs(tbRfcMapper.getAllList(rfc));
		}
		return rfcts;
	}

	@Override
	public List<Map<String, Object>> getWaterAllItemInfo(String openNum) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resList = billMapper.selectItemByWater(openNum);
		Map<String, String> param = new HashMap<>();
		param.put("owNum",openNum);
		List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param); //服务类型为退单的品项数据
		//将开单以及加单的数据与退单的数据整合g
		for(Map<String, Object> dgOwConsumerDetails:resList){
			Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
			itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
			dgOwConsumerDetails = (Map<String,Object>)res.get("obj");
		}

		List<Map<String, Object>> sl = new ArrayList<>();
		for(Map<String,Object> dgOwConsumerDetails:resList){
			if((double)dgOwConsumerDetails.get("item_file_number") > 0){
				sl.add(dgOwConsumerDetails);
			}
		}
		return sl;
//		Iterator<Map<String, Object>> re = ret.iterator();
//		while (re.hasNext()) {
//			Map sorg = new HashMap();
//			Map<String, Object> r = re.next();
//			sorg.put("owId", r.get("ow_id"));
//			sorg.put("itemFileId", r.get("item_file_id"));
//			sorg.put("yeId", openNum);
//			Double surplusItemNumber = billMapper
//					.selectConsumerDetailByOwId(sorg);
//			if (surplusItemNumber != null && surplusItemNumber > 0) {
//				r.put("item_file_number", surplusItemNumber);
//
//				Map<String, Object> src = new HashMap<String, Object>();
//				src.put("owId", r.get("ow_id"));
//				src.put("itemId", r.get("item_file_id"));
//				double zzffTotal = 0;// 制作费用和
//				List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
//						.selectByOwId(src);
//				for (DgOwDetailsOther other : rOther) {
//					if (other.getZzffSf() != null && other.getZzffSf() == 1) {
//						// 按品项
//						if (other.getZzffSfType() == 1) {
//							zzffTotal = MathExtend.add(zzffTotal,
//									other.getOcosts());
//						} else if (other.getZzffSfType() == 2) {
//							zzffTotal = MathExtend.add(zzffTotal, MathExtend
//									.multiply(other.getOcosts(),
//											surplusItemNumber));
//						}
//					}
//				}
//				double subtotal = MathExtend.multiply(
//						(double) r.get("item_final_price"), surplusItemNumber);
//				r.put("subtotal", MathExtend.add(subtotal, zzffTotal));
//			} else if (surplusItemNumber != null && surplusItemNumber <= 0) {
//				re.remove();
//			}
//			Map<String, Object> src = new HashMap<String, Object>();
//			src.put("owId", r.get("ow_id"));
//			src.put("itemId", r.get("item_file_id"));
//			List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
//					.selectByOwId(src);
//			r.put("extra", rOther);
//		}
//		return ret;
	}

	@Override
	public synchronized Map<String, Object> insertBackBill(String operCode,
														   String openNumber, List<Map<String, Object>> org, String type) {
		Map<String, Object> ret = new HashMap<String, Object>();
		// 验证数据是否正确
		Map checkBack = checkBackBill(openNumber, org);
		if (checkBack.containsKey("error")) {
			ret.put("error", APIEnumDefine.M0101012);
			return ret;
		}
		if (!havaQcZsAndBack(operCode, org, 2)) {
			ret.put("error", APIEnumDefine.M0101019);
			return ret;
		}

		List<Map> syNumber = (List<Map>) checkBack.get("syNumber");
		DgOpenWater water = dgOpenWaterMapper
				.selectByOpenWaterByNum(openNumber);

		SysUser sysUser = sysUserMapper.selectUserByUserCode(operCode);
		DgUrlSetting offset = dgUrlSettingMapper.selectByCode("IS_OFFSET");

		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(water.getOpenPos());
		pos = dgPosMapper.getPosByID(pos);
		TbOrg tbOrg = new TbOrg();
		tbOrg.setId(Integer.valueOf(pos.getOrganization()));
		tbOrg = tbOrgMapper.getOrgByID(tbOrg);
		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());

		// 插入服务流水
		Map serviceMap = new HashMap();
		serviceMap.put("owId", water.getId());
		if(sysUser != null){
			serviceMap.put("waiterId", sysUser.getId());
		}else{
			serviceMap.put("waiterId", water.getWaiter());
		}
		serviceMap.put("serviceTime", new Date());
		serviceMap.put("serviceType", 4);
		serviceMap.put("serviceNum", serviceNums.get(0));
		billMapper.insertOwServiceWater(serviceMap);
		double openwaterSubtotal = 0;
		for (Map<String, Object> o : org) {
			Map<String, Object> src = new HashMap<String, Object>();
			src.put("owId", o.get("serviceId"));
			src.put("itemId", o.get("itemFileId"));
			DgOwConsumerDetails hisDc = dgOwConsumerDetailsMapper
					.selectByOwId(src);
			DgItemFile itemFile = dgItemFileMapper.selectByPrimaryKey(hisDc
					.getItemFileId());
			DgOwConsumerDetails cd = new DgOwConsumerDetails();
			cd.setItemFileId(hisDc.getItemFileId());
			cd.setItemFileNumber(-(double) o.get("itemFileNumber"));
			if (o.containsKey("itemFileZs")) {
				cd.setItemFileZs(-Double.valueOf("" + o.get("itemFileZs")));
			}
			cd.setNotes("4");
			cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile.getIsTc());
			cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
			cd.setBackOwId((Integer) o.get("serviceId"));
			// 不计算制作费用(退单一律不减)
			double zzffTotal = 0;// 制作费用和
			List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
					.selectByOwId(src);
			for (DgOwDetailsOther other : rOther) {
				if (other.getZzffSf() != null && other.getZzffSf() == 1) {
					// 按品项
					if (other.getZzffSfType() == 1) {
						for (Map sy : syNumber) {
							if (((int) sy.get("item_file_id") == (int) o
									.get("itemFileId"))
									&& ((int) sy.get("ow_id") == (int) o
									.get("serviceId"))) {
								if (DoubleCompare.compare(
										(double) sy.get("item_file_number"),
										(double) o.get("itemFileNumber"))) {
									zzffTotal = MathExtend.add(zzffTotal,
											other.getOcosts());
								} else {
									zzffTotal = 0.0;
								}
								break;
							}
						}
					} else if (other.getZzffSfType() == 2) {
						zzffTotal = MathExtend.add(zzffTotal, MathExtend
								.multiply(other.getOcosts(),
										(double) o.get("itemFileNumber")));
					}
				}
			}
			// 插入套餐子项
			if (cd.getIsTc() == 1) {
				insertTc(itemFile.getId(), cd.getItemFileNumber(),
						cd.getOwId(), "4");
			}

			DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
					.format(new Date()));
			if (li != null) {
				DgItemSaleLimitS lis = new DgItemSaleLimitS();
				lis.setUseCount(-(double) o.get("itemFileNumber"));
				lis.setLimitId(li.getId());
				lis.setItemId(Integer.valueOf("" + o.get("itemFileId")));
				dgItemSaleLimitSMapper.updateCount(lis);
			}
			Map zcorgs = new HashMap();
			zcorgs.put("itemFileId",Integer.valueOf("" + o.get("itemFileId")));
			zcorgs.put("ItemFileCount",-(double) o.get("itemFileNumber"));
			insertInveCall(billMapper.updateInveDate(zcorgs));

			cd.setProductionCosts(-zzffTotal);
			cd.setItemFinalPrice(hisDc.getItemFinalPrice());
//			cd.setItemPayMoney(hisDc.getItemFinalPrice());
			double moc = MathExtend.multiply(cd.getItemFinalPrice(),
					cd.getItemFileNumber());
			double subtotal = MathExtend.add(-zzffTotal, moc);
			cd.setSubtotal(subtotal);
			dgOwConsumerDetailsMapper.insertSelective(cd);
			openwaterSubtotal = MathExtend.add(openwaterSubtotal, subtotal);
		}
		// 最后更新营业流水号
		water.setItemCostsSum(MathExtend.add(water.getItemCostsSum(),
				openwaterSubtotal));
		water.setSubtotal(MathExtend.add(water.getSubtotal(), openwaterSubtotal));
		billMapper.updateOpenWaterPrimaryKey(water);

		// 打印后厨
		if (type.equals("1")) {
			dgPrintDataService.insertBackBill(
					Integer.valueOf("" + serviceMap.get("id")),
					openwaterSubtotal);
		}
		// 发送库存冲减
		if(offset != null && offset.getValue().equals("1")){
			List<Map<String, Object>> checklist = new ArrayList<>();
			for (Map<String, Object> o : org) {
				Map<String, Object> src = new HashMap<String, Object>();
				src.put("number", (double)o.get("itemFileNumber"));
				src.put("itemId", o.get("itemFileId"));
				checklist.add(src);
			}
			DgOffset dgOffset = new DgOffset(simpleDateFormat.format(new Date()), com.alibaba.fastjson.JSONArray.toJSONString(checklist),2);
			dgOffsetMapper.insert(dgOffset);
		}
		ret.put("success", true);
		return ret;
	}

	@Override
	public List<Map<String, Object>> getTeamBackItemInfo(String teamMember,
														 List seatIds) {
		// TODO Auto-generated method stub
		Map orgs = new HashMap();
		orgs.put("teamMember", teamMember);
		if (seatIds != null) {
			orgs.put("ids", seatIds);
		} else {
			orgs.put("ids", null);
		}
		List<Map<String, Object>> ret = billMapper.selectTeamSeatInfo(orgs);
		List<List<Map<String, Object>>> allTeamItemList = new ArrayList<List<Map<String, Object>>>();
		for (Map<String, Object> r : ret) {
//			List<Map<String, Object>> cr = billMapper
//					.selectItemByWater((String) r.get("owNum"));// 选出加单流水
//			Iterator<Map<String, Object>> re = cr.iterator();
//			while (re.hasNext()) {
//				Map sorg = new HashMap();
//				Map<String, Object> rr = re.next();
//				sorg.put("owId", rr.get("ow_id"));
//				sorg.put("itemFileId", rr.get("item_file_id"));
//				sorg.put("yeId", (String) r.get("owNum"));
//				Double surplusItemNumber = billMapper
//						.selectConsumerDetailByOwId(sorg); // 计算本流水(退单后),最终数量
//				if (surplusItemNumber != null && surplusItemNumber > 0) {
//					rr.put("item_file_number", surplusItemNumber);
//				} else if (surplusItemNumber != null && surplusItemNumber <= 0) {
//					re.remove();
//				}
//			}
			List<Map<String, Object>> resList = billMapper
					.selectItemByWater((String) r.get("owNum"));
			Map<String, String> param = new HashMap<>();
			param.put("owNum",(String) r.get("owNum"));
			List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param); //服务类型为退单的品项数据
			//将开单以及加单的数据与退单的数据整合g
			for(Map<String, Object> dgOwConsumerDetails:resList){
				Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
				itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
				dgOwConsumerDetails = (Map<String,Object>)res.get("obj");
			}

			List<Map<String, Object>> sl = new ArrayList<>();
			for(Map<String,Object> dgOwConsumerDetails:resList){
				if((double)dgOwConsumerDetails.get("item_file_number") > 0){
					sl.add(dgOwConsumerDetails);
				}
			}
			allTeamItemList.add(sl);
		}

		List<Map<String, Object>> allHavaItems = new ArrayList<Map<String, Object>>();
		if (allTeamItemList.size() == 0 || allTeamItemList.size() < ret.size()) {
			return null;
		}
		List<Map<String, Object>> l = allTeamItemList.get(0);
		for (int j = 0; j < l.size(); j++) {
			Map<String, Object> li = l.get(j);
			Map<String, Object> minCount = li;
			int itemId = (int) li.get("item_file_id");
			boolean allHavaItemshava = false;
			if (!allHavaItems.isEmpty()) {
				for (Map<String, Object> hli : allHavaItems) {
					if ((int) hli.get("item_file_id") == itemId) // 存在就不继续查
					{
						allHavaItemshava = true;
						break;
					}
				}
			}
			if (allHavaItemshava) {
				continue;
			}
			boolean allHava = true;
			for (int k = 0; k < allTeamItemList.size(); k++) {
				boolean havaItem = false;
				List<Map<String, Object>> kl = allTeamItemList.get(k);
				for (int kj = 0; kj < kl.size(); kj++) {
					Map<String, Object> kli = kl.get(kj);
					if ((Integer) kli.get("item_file_id") == itemId) {
						if (((Double) minCount.get("item_file_number"))
								.intValue() >= ((Double) kli
								.get("item_file_number")).intValue()) {
							minCount = kli;
						}
						havaItem = true;
						break;
					}
				}
				if (!havaItem) {
					allHava = false;
					break;
				}
			}
			if (allHava) {
				allHavaItems.add(minCount);
			}
		}

		return allHavaItems;
	}

	@Override
	public synchronized Map<String, Object> insertTeamBackBill(String operCode,
															   String teamMember, List<String> seatIds,
															   List<Map<String, Object>> org, String type) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		if (checkTeamBack(teamMember, org, seatIds).containsKey("error")) {
			ret.put("error", APIEnumDefine.M0101012);
			return ret;
		}
		if (!havaQcZsAndBack(operCode, org, 2)) {
			ret.put("error", APIEnumDefine.M0101019);
			return ret;
		}
		DgUrlSetting offset = dgUrlSettingMapper.selectByCode("IS_OFFSET");
		for (Map<String, Object> o : org) {
			int itemFileId = (int) o.get("itemFileId");
			double itemFileNumber = (double) o.get("itemFileNumber");
			double itemFileZs = 0;
			if (o.containsKey("itemFileZs")) {
				itemFileZs = (double) o.get("itemFileZs");
			}
			if (o.containsKey("backTypeId")) {
				int backTypeId = (int) o.get("backTypeId");
			}
			if (o.containsKey("backId")) {
				int backId = (int) o.get("backId");
			}
			Map worg = new HashMap();
			worg.put("teamMember", teamMember);
			worg.put("ids", seatIds);
			List<Map<String, Object>> members = billMapper
					.selectWaterByTeamMember(worg);

			for (Map<String, Object> m : members) {

				Gson gson = new Gson();
				DgPos pos = new DgPos();
				pos.setId((int) m.get("open_pos"));
				pos = dgPosMapper.getPosByID(pos);
				TbOrg tbOrg = new TbOrg();
				tbOrg.setId(Integer.valueOf(pos.getOrganization()));
				tbOrg = tbOrgMapper.getOrgByID(tbOrg);
				List<String> serviceNums = gson.fromJson(
						deskBusinessSettingService.createFlowNumber(
								tbOrg.getOrgCode(), pos.getNumber(), 1,
								SerialRulEnum.FW),
						new TypeToken<List<String>>() {
						}.getType());

				// 插入服务流水
				Map serviceMap = new HashMap();
				serviceMap.put("owId", (int) m.get("id"));
				serviceMap.put("waiterId", (int) m.get("waiter"));
				serviceMap.put("serviceTime", new Date());
				serviceMap.put("serviceType", 4); // 退单类型
				serviceMap.put("serviceNum", serviceNums.get(0)); // 退单类型
				billMapper.insertOwServiceWater(serviceMap);
				Map dorg = new HashMap();
				dorg.put("owId", (int) m.get("id"));
				dorg.put("itemFileId", itemFileId);
				double openwaterSubtotal = 0;
				List<Map<String, Object>> detailItems = billMapper
						.selectConsumerDetailByWaterIdAndItemFileId(dorg);
				double thisWaterItemFileNumber = itemFileNumber;
				for (Map<String, Object> d : detailItems) {
					Map sorg = new HashMap();
					sorg.put("owId", d.get("ow_id"));
					sorg.put("itemFileId", d.get("item_file_id"));
					sorg.put("yeId", (String) m.get("ow_num"));
					Double surplusItemNumber = billMapper
							.selectConsumerDetailByOwId(sorg);
					if (surplusItemNumber != null && surplusItemNumber > 0) {
						DgItemFile itemFile = dgItemFileMapper
								.selectByPrimaryKey((int) d.get("item_file_id"));
						if (thisWaterItemFileNumber <= surplusItemNumber) // 扣除这支流水就行
						{
							openwaterSubtotal = backNext(d,
									thisWaterItemFileNumber, itemFileZs,
									itemFile, openwaterSubtotal, serviceMap,
									"4", (String) m.get("ow_num"));
							break;
						} else {
							openwaterSubtotal = backNext(d, surplusItemNumber,
									itemFileZs, itemFile, openwaterSubtotal,
									serviceMap, "4", (String) m.get("ow_num"));
							thisWaterItemFileNumber = MathExtend.subtract(
									thisWaterItemFileNumber, surplusItemNumber);
						}
					}
				}

				// 发送库存冲减
				if(offset != null && offset.getValue().equals("1")){
					List<Map<String, Object>> checklist = new ArrayList<>();
					for (Map<String, Object> item : org) {
						Map<String, Object> src = new HashMap<String, Object>();
						src.put("number", (double)item.get("itemFileNumber"));
						src.put("itemId", item.get("itemFileId"));
						checklist.add(src);
					}
					DgOffset dgOffset = new DgOffset(simpleDateFormat.format(new Date()), com.alibaba.fastjson.JSONArray.toJSONString(checklist),2);
					dgOffsetMapper.insert(dgOffset);
				}

				DgOpenWater water = dgOpenWaterMapper
						.selectByOpenWaterByNum((String) m.get("ow_num"));
				// 最后更新营业流水号
				water.setItemCostsSum(MathExtend.add(water.getItemCostsSum(),
						openwaterSubtotal));
				water.setSubtotal(MathExtend.add(water.getSubtotal(),
						openwaterSubtotal));
				billMapper.updateOpenWaterPrimaryKey(water);
			}
		}
		ret.put("success", true);
		return ret;
	}

	/**
	 * 退单执行函数
	 *
	 * @param d
	 * @param itemFileNumber
	 * @param itemFileZs
	 * @param itemFile
	 * @param openwaterSubtotal
	 * @param serviceMap
	 */
	private double backNext(Map d, double itemFileNumber, double itemFileZs,
							DgItemFile itemFile, double openwaterSubtotal, Map serviceMap,
							String type, String water) {
		Map<String, Object> src = new HashMap<String, Object>();
		src.put("owId", d.get("ow_id"));
		src.put("itemId", d.get("item_file_id"));
		DgOwConsumerDetails cd = new DgOwConsumerDetails();
		cd.setItemFileId((int) d.get("item_file_id"));
		cd.setItemFileNumber(-itemFileNumber);
		cd.setItemFileZs(-itemFileZs);
		cd.setNotes(type);
		cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile.getIsTc());
		cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
		cd.setBackOwId((Integer) d.get("ow_id"));
		// 不计算制作费用(退单一律不减)
		double zzffTotal = 0;// 制作费用和
		List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
				.selectByOwId(src);
		for (DgOwDetailsOther other : rOther) {
			if (other.getZzffSf() != null && other.getZzffSf() == 1) {
				// 按品项
				if (other.getZzffSfType() == 1) {
					Map sorg = new HashMap();
					sorg.put("owId", (Integer) d.get("ow_id"));
					sorg.put("itemFileId", cd.getItemFileId());
					sorg.put("yeId", water);
					Double surplusItemNumber = billMapper
							.selectConsumerDetailByOwId(sorg); // 计算本流水(退单后),最终数量
					if (DoubleCompare
							.compare(surplusItemNumber, itemFileNumber)) {
						zzffTotal = MathExtend
								.add(zzffTotal, other.getOcosts());
					} else {
						zzffTotal = 0.0;
					}

				} else if (other.getZzffSfType() == 2) {
					zzffTotal = MathExtend.add(zzffTotal, MathExtend.multiply(
							other.getOcosts(), itemFileNumber));
				}
			}
		}

		// 插入套餐子项
		if (cd.getIsTc() == 1) {
			insertTc(itemFile.getId(), cd.getItemFileNumber(), cd.getOwId(),
					type);
		}

		DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
				.format(new Date()));
		if (li != null) {
			DgItemSaleLimitS lis = new DgItemSaleLimitS();
			lis.setUseCount(-itemFileNumber);
			lis.setLimitId(li.getId());
			lis.setItemId((int) d.get("item_file_id"));
			dgItemSaleLimitSMapper.updateCount(lis);
		}
		Map zcorgs = new HashMap();
		zcorgs.put("itemFileId",(int) d.get("item_file_id"));
		zcorgs.put("ItemFileCount",-itemFileNumber);
		insertInveCall(billMapper.updateInveDate(zcorgs));

		cd.setItemFinalPrice((double) d.get("item_final_price"));
//		cd.setItemPayMoney((double) d.get("item_final_price"));
		double moc = MathExtend.multiply(cd.getItemFinalPrice(),
				cd.getItemFileNumber());
		cd.setProductionCosts(-zzffTotal);
		double subtotal = MathExtend.add(-zzffTotal, moc);
		cd.setSubtotal(subtotal);
		dgOwConsumerDetailsMapper.insertSelective(cd);
		openwaterSubtotal = MathExtend.add(openwaterSubtotal, subtotal);
		return openwaterSubtotal;
	}

	/**
	 * 验证单个退单数据正确性
	 *
	 * @return
	 */
	private Map<String, Object> checkBackBill(String openNumber,
											  List<Map<String, Object>> org) {
		// TODO Auto-generated method stub
		Map<String, Object> back = new HashMap<String, Object>();
		List<Map<String, Object>> resList = billMapper
				.selectItemByWater(openNumber);
//		Iterator<Map<String, Object>> re = ret.iterator();
//		while (re.hasNext()) {
//			Map sorg = new HashMap();
//			Map<String, Object> r = re.next();
//			sorg.put("owId", r.get("ow_id"));
//			sorg.put("itemFileId", r.get("item_file_id"));
//			sorg.put("yeId", openNumber);
//			Double surplusItemNumber = billMapper
//					.selectConsumerDetailByOwId(sorg);
//			if (surplusItemNumber != null && surplusItemNumber > 0) {
//				r.put("item_file_number", surplusItemNumber);
//			} else if (surplusItemNumber != null && surplusItemNumber <= 0) {
//				re.remove();
//			}
//		}
		Map<String, String> param = new HashMap<>();
		param.put("owNum",openNumber);
		List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param); //服务类型为退单的品项数据
		//将开单以及加单的数据与退单的数据整合g
		for(Map<String, Object> dgOwConsumerDetails:resList){
			Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
			itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
			dgOwConsumerDetails = (Map<String,Object>)res.get("obj");
		}

		List<Map<String, Object>> ret = new ArrayList<>();
		for(Map<String,Object> dgOwConsumerDetails:resList){
			if((double)dgOwConsumerDetails.get("item_file_number") > 0){
				ret.add(dgOwConsumerDetails);
			}
		}


		for (Map<String, Object> o : org) {
			int owId = (int) o.get("serviceId");
			double itemFileNumber = Double
					.valueOf("" + o.get("itemFileNumber"));
			int itemFileId = (int) o.get("itemFileId");
			for (Map<String, Object> m : ret) {
				int mOwId = (int) m.get("ow_id");
				double mItemFileNumber = Double.valueOf(""
						+ m.get("item_file_number"));
				int mItemFileId = (int) m.get("item_file_id");
				if (owId == mOwId && itemFileId == mItemFileId) {
					if (itemFileNumber > mItemFileNumber) {
						back.put("error", APIEnumDefine.M0101012);
					}
					break;
				}
			}
			if (back.containsKey("error")) {
				break;
			}
		}
		back.put("syNumber", ret);
		return back;
	}

	/**
	 *
	 * 团队退单数量超额验证
	 */
	private Map<String, Object> checkTeamBack(String teamMember,
											  List<Map<String, Object>> org, List<String> seatIds) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> jxMap = getTeamBackItemInfo(teamMember,
				seatIds);
		for (Map<String, Object> o : org) {
			int itemFileId = (int) o.get("itemFileId");
			double itemFileNumber = (double) o.get("itemFileNumber");
			for (Map<String, Object> jx : jxMap) {
				int jxItemFileId = (int) jx.get("item_file_id");
				double jxItemFileNumber = (double) jx.get("item_file_number");
				if (jxItemFileId == itemFileId) {
					if (itemFileNumber > jxItemFileNumber) {
						ret.put("error", APIEnumDefine.M0101012);
					}
					break;
				}
			}
		}
		return ret;
	}

	@Override
	public Map<String, Object> insertReminderBill(String operCode,
												  List<Map> list) {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (Map<String, Object> l : list) {
			int serviceId = (int) l.get("serviceId");
			int itemFileId = (int) l.get("itemFileId");
			int reminderItemNumber = (int) l.get("reminderItemNumber");
			Map re = new HashMap();
			re.put("owId", serviceId);
			re.put("itemFileId", itemFileId);
			billMapper.updateReminder(re);
		}
		// 打印后厨
		dgPrintDataService.insertReminderBill(list);
		return ret;
	}

	@Override
	public List<Map<String, Object>> getReminderItemInfo(String openNum) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resList = billMapper.selectItemByWater(openNum);
		Map<String, String> param = new HashMap<>();
		param.put("owNum",openNum);
		List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param); //服务类型为退单的品项数据
		//将开单以及加单的数据与退单的数据整合g
		for(Map<String, Object> dgOwConsumerDetails:resList){
			Map<String,Object> res = setItemFileNum(itemDetail2,dgOwConsumerDetails);
			itemDetail2 = (List<DgOwConsumerDetails>)res.get("list");
			dgOwConsumerDetails = (Map<String,Object>)res.get("obj");
		}

		List<Map<String, Object>> sl = new ArrayList<>();
		for(Map<String,Object> dgOwConsumerDetails:resList){
			if((double)dgOwConsumerDetails.get("item_file_number") > 0){
				sl.add(dgOwConsumerDetails);
			}
		}
		return sl;
//		Iterator<Map<String, Object>> re = ret.iterator();
//		while (re.hasNext()) {
//			Map sorg = new HashMap();
//			Map<String, Object> r = re.next();
//			sorg.put("owId", r.get("ow_id"));
//			sorg.put("itemFileId", r.get("item_file_id"));
//			sorg.put("yeId", openNum);
//			Double surplusItemNumber = billMapper
//					.selectConsumerDetailByOwId(sorg);
//			if (surplusItemNumber != null && surplusItemNumber > 0) {
//				r.put("item_file_number", surplusItemNumber);
//
//				double zzffTotal = 0;// 制作费用和
//				Map<String, Object> src = new HashMap<String, Object>();
//				src.put("owId", r.get("ow_id"));
//				src.put("itemId", r.get("item_file_id"));
//				List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
//						.selectByOwId(src);
//				for (DgOwDetailsOther other : rOther) {
//					if (other.getZzffSf() != null && other.getZzffSf() == 1) {
//						// 按品项
//						if (other.getZzffSfType() == 1) {
//							zzffTotal = MathExtend.add(zzffTotal,
//									other.getOcosts());
//						} else if (other.getZzffSfType() == 2) {
//							zzffTotal = MathExtend.add(zzffTotal, MathExtend
//									.multiply(other.getOcosts(),
//											surplusItemNumber));
//						}
//					}
//				}
//				r.put("production_costs", zzffTotal);
//				double itemTotal = MathExtend.multiply(
//						(double) r.get("item_final_price"), surplusItemNumber);
//				r.put("subtotal", MathExtend.add(itemTotal, zzffTotal));
//			} else if (surplusItemNumber != null && surplusItemNumber <= 0) {
//				re.remove();
//			}
//			Map<String, Object> src = new HashMap<String, Object>();
//			src.put("owId", r.get("ow_id"));
//			src.put("itemId", r.get("item_file_id"));
//			List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
//					.selectByOwId(src);
//			r.put("extra", rOther);
//		}
//		return ret;
	}

	@Override
	public List<Map<String, Object>> getQcItemInfo(String openNum) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> ret = billMapper.selectQcItemByWater(openNum);
		Iterator<Map<String, Object>> re = ret.iterator();
		while (re.hasNext()) {
			Map sorg = new HashMap();
			Map<String, Object> r = re.next();
			sorg.put("owId", r.get("ow_id"));
			sorg.put("itemFileId", r.get("item_file_id"));
			sorg.put("yeId", openNum);
			Double surplusItemNumber = billMapper
					.selectConsumerDetailByOwId(sorg);
			if (surplusItemNumber != null && surplusItemNumber > 0) {
				r.put("item_file_number", surplusItemNumber);
			} else if (surplusItemNumber != null && surplusItemNumber <= 0) {
				re.remove();
				continue;
			}
			if (!r.containsKey("qc_zhsj")) {
				r.put("qc_zhsj", null);
			}
			if (!r.containsKey("serving_type")) {
				r.put("serving_type", null);
			}
			if (!r.containsKey("qc_sl")) {
				r.put("qc_sl", 0);
			}
			Map<String, Object> src = new HashMap<String, Object>();
			src.put("owId", r.get("ow_id"));
			src.put("itemId", r.get("item_file_id"));
			List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
					.selectByOwId(src);
			r.put("extra", rOther);
		}
		return ret;
	}

	@Override
	public List<Map<String, Object>> getQcItemTypeInfo(
			List<Map<String, Object>> src) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> s : src) {
			int bId = (int) s.get("bId");
			int sId = (int) s.get("sId");
			String bigName = (String) s.get("bigName");
			String smallName = (String) s.get("smallName");
			if (ret.isEmpty()) {
				Map<String, Object> c = new HashMap<String, Object>();
				c.put("bId", bId);
				c.put("bigName", bigName);
				List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();
				Map<String, Object> child = new HashMap<String, Object>();
				child.put("sId", sId);
				child.put("smallName", smallName);
				clist.add(child);
				c.put("child", clist);
				ret.add(c);
			} else {
				boolean havaBig = false;
				for (Map<String, Object> r : ret) {
					int cBid = (int) r.get("bId");
					String cBigName = (String) r.get("bigName");
					if (cBid == bId) {
						boolean havaSmall = false;
						List<Map<String, Object>> clist = (List<Map<String, Object>>) r
								.get("child");
						for (Map<String, Object> sm : clist) {
							int cSmId = (int) sm.get("sId");
							String cSmName = (String) sm.get("smallName");
							if (cSmId == sId) {
								havaSmall = true;
								break;
							}
						}
						if (!havaSmall) {
							Map<String, Object> child = new HashMap<String, Object>();
							child.put("sId", sId);
							child.put("smallName", smallName);
							clist.add(child);
						}
						havaBig = true;
						break;
					}
				}
				if (!havaBig) {
					Map<String, Object> c = new HashMap<String, Object>();
					c.put("bId", bId);
					c.put("bigName", bigName);
					List<Map<String, Object>> clist = new ArrayList<Map<String, Object>>();
					Map<String, Object> child = new HashMap<String, Object>();
					child.put("sId", sId);
					child.put("smallName", smallName);
					clist.add(child);
					c.put("child", clist);
					ret.add(c);
				}
			}
		}

		return ret;
	}

	@Override
	public Map<String, Object> insertQcBill(Object obj, String type,
											String operCode) {
		// TODO Auto-generated method stub
		int qcType = Integer.valueOf(type);
		List<Map> printList = new ArrayList<Map>();
		int owId = -1;
		if (qcType == 1) {
			List<Map> orgs = (List<Map>) obj;
			owId = (int) orgs.get(0).get("serviceId");
			for (Map o : orgs) {
				int itemFileId = (int) o.get("itemFileId");
				double qcNumber = Double.valueOf("" + o.get("qcNumber"));
				int serviceId = (int) o.get("serviceId");
				int qcFs = (int) o.get("qcFs");
				Date qcZhsj = new Date();
				Map qct = new HashMap();
				qct.put("qcNumber", qcNumber);
				qct.put("qcFs", qcFs);
				qct.put("qcZhsj", qcZhsj);
				qct.put("owId", serviceId);
				qct.put("itemFileId", itemFileId);
				billMapper.updateQcType1(qct);
				printList.add(qct);
			}
			// 加入打印列表
//			dgPrintDataService.insertQcBill(operCode,
//					dgPrintDataMapper.selectOwNumByServiceId(owId), printList);
		} else if (qcType == 2) {
			List<Map> orgs = (List<Map>) obj;
			owId = (int) orgs.get(0).get("serviceId");
			for (Map o : orgs) {
				String waterNum = (String) o.get("waterNum");
				int xlId = (int) o.get("xlId");
				int qcFs = (int) o.get("qcFs");
				Map st = new HashMap();
				st.put("waterNum", waterNum);
				st.put("xlId", xlId);
				List<Map<String, Object>> consItems = billMapper
						.selectQcItemByWaterType2(st);
				for (Map<String, Object> consItem : consItems) {
					Map qct = new HashMap();
					qct.put("qcNumber", consItem.get("item_file_number"));
					qct.put("qcFs", qcFs);
					qct.put("qcZhsj", new Date());
					qct.put("owId", consItem.get("ow_id"));
					qct.put("itemFileId", consItem.get("item_file_id"));
					billMapper.updateQcType1(qct);
					printList.add(qct);
				}
			}
			// 加入打印列表
//			dgPrintDataService.insertQcBill(operCode,
//					dgPrintDataMapper.selectOwNumByServiceId(owId), printList);
		} else if (qcType == 3) {
			Map orgs = (Map) obj;
			String waterNum = (String) orgs.get("waterNum");
			int qcFs = (int) orgs.get("qcFs");
			List<Map<String, Object>> consItems = billMapper
					.selectQcItemByWater(waterNum);
			for (Map<String, Object> consItem : consItems) {
				Map qct = new HashMap();
				qct.put("qcNumber", consItem.get("item_file_number"));
				qct.put("qcFs", qcFs);
				qct.put("qcZhsj", new Date());
				qct.put("owId", consItem.get("ow_id"));
				qct.put("itemFileId", consItem.get("item_file_id"));
				billMapper.updateQcType1(qct);
				printList.add(qct);
			}
			// 加入打印列表
//			dgPrintDataService.insertQcBill(operCode, waterNum, printList);
		}
		return null;
	}

	@Override
	public synchronized Map modifyGgkw(String userCode,String openNum, int number,
									   String waiterId, String isJsPx) {
		Map ret = new HashMap();
		DgOpenWater ow = dgOpenWaterMapper.selectByOpenWaterByNum(openNum);
		Integer oldNumber = ow.getPeopleCount();
		List<DgSeatItem> autoIncreaList = dgSeatItemMapper.getBySeatId(Integer
				.valueOf(ow.getSeatId()));
		Integer owNumber = ow.getPeopleCount();

		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(ow.getOpenPos());
		pos = dgPosMapper.getPosByID(pos);
		TbOrg tbOrg = new TbOrg();
		tbOrg.setId(Integer.valueOf(pos.getOrganization()));
		tbOrg = tbOrgMapper.getOrgByID(tbOrg);
		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());

		if (!autoIncreaList.isEmpty()) {
			Map serviceMap = new HashMap();
			serviceMap.put("owId", ow.getId());
			serviceMap.put("waiterId", waiterId);
			serviceMap.put("serviceNum", serviceNums.get(0));
			serviceMap.put("serviceTime", new Date());
			double openwaterSubtotal = 0;
			DgOwModifySeat modifys = dgOwModifySeatMapper
					.selectByOwNum(openNum);
			if (modifys != null) {
				// 替换过的客位,不进行自增品项赠减操作
				ow.setPeopleCount(number);
				billMapper.updateOpenWaterPrimaryKey(ow);
				return ret;
			}
			if (ow.getPeopleCount() > number) {
				serviceMap.put("serviceType", 5); // 减少人数操作
				billMapper.insertOwServiceWater(serviceMap); // 插入服务流水
				List<Map<String, Object>> dbIncreaList = billMapper
						.getServiceIncreaItemByOwId(ow.getId());
				// 启用自动减少自增品项
				if (isJsPx.equals("1")) {
					for (Map<String, Object> db : dbIncreaList) {
						Map org = new HashMap();
						org.put("id", ow.getId());
						org.put("itemFileId", db.get("item_file_id"));
						org.put("yeId", openNum);
						List<DgOwConsumerDetails> owIds = billMapper
								.getTotalIncreaItemByOwId(org);
						org.put("owIds", owIds);
						Double nowNumber = billMapper.getTotalIncreCount(org);
						if (nowNumber != null && nowNumber > number) {
							// 减单操作
							Map dorg = new HashMap();
							dorg.put("owId", ow.getId());
							dorg.put("itemFileId", db.get("item_file_id"));
							List<Map<String, Object>> detailItems = billMapper
									.selectZdConsumerDetailByWaterIdAndItemFileId(dorg);
							DgItemFile itemFile = dgItemFileMapper
									.selectByPrimaryKey((int) db
											.get("item_file_id"));
							Double thisWaterItemFileNumber = MathExtend
									.subtract(nowNumber, number);
							int llNumber = owNumber - number;
							// 存在退
							if (llNumber > thisWaterItemFileNumber) {
								ret.put("msg", itemFile.getName()
										+ "数量不足,不退菜品!");
								break;
							}
							for (Map<String, Object> d : detailItems) {
								Map sorg = new HashMap();
								sorg.put("owId", d.get("ow_id"));
								sorg.put("itemFileId", d.get("item_file_id"));
								sorg.put("yeId", openNum);
								Double surplusItemNumber = billMapper
										.selectConsumerDetailByOwId(sorg);
								if (surplusItemNumber != null
										&& surplusItemNumber > 0) {
									if (thisWaterItemFileNumber <= surplusItemNumber) // 扣除这支流水就行
									{
										openwaterSubtotal = backNext(d,
												thisWaterItemFileNumber, 0,
												itemFile, openwaterSubtotal,
												serviceMap, "5", openNum);
										break;
									} else {
										openwaterSubtotal = backNext(d,
												surplusItemNumber, 0, itemFile,
												openwaterSubtotal, serviceMap,
												"5", openNum);
										thisWaterItemFileNumber = MathExtend
												.subtract(
														thisWaterItemFileNumber,
														surplusItemNumber);
									}
								}
							}
						}
					}
				}
			} else if (ow.getPeopleCount() < number) {
				serviceMap.put("serviceType", 6); // 增加人数操作
				billMapper.insertOwServiceWater(serviceMap); // 插入服务流水
				List<Map<String, Object>> dbIncreaList = billMapper
						.getServiceIncreaItemByOwId(ow.getId());
				for (Map<String, Object> db : dbIncreaList) {
					boolean isAdd = true;
					for (DgSeatItem dsi : autoIncreaList) {
						if (dsi.getItemId() == (int) db.get("item_file_id")) {
							if (dsi.getUseOpenCounter() == 0) {
								isAdd = false;
							}
							break;
						}
					}
					// 不增加
					if (!isAdd) {
						continue;
					}
					double itemFinalPrice = (double) db.get("item_final_price");
					// Map org = new HashMap();
					// org.put("id", ow.getId());
					// org.put("itemFileId", db.get("item_file_id"));
					// Integer nowNumber = billMapper
					// .getTotalIncreaItemByOwId(org);
					List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
					Map<String, Object> checkMap = new HashMap<String, Object>();
					checkMap.put("itemId", db.get("item_file_id"));
					checkMap.put("number", (double) (number - owNumber));
					checklist.add(checkMap);
					List<DgOpenWater> waters = new ArrayList<DgOpenWater>();
					waters.add(ow);
					Map<String, Object> errCheckItem = checkItemFile(checklist,
							waters, true);
					if (!errCheckItem.isEmpty()) {
						// 品项不能增加
					} else {
						Map og = new HashMap();
						og.put("seatId", ow.getSeatId());
						og.put("itemId", db.get("item_file_id"));
						// 获取开单自动增加品项
						DgSeatItem seatItem = dgSeatItemMapper
								.getBySeatIdAndItemId(og);
						DgItemFile fileItem = dgItemFileMapper
								.selectByPrimaryKey((int) db
										.get("item_file_id"));
						double itemCount = 0;
						if (seatItem.getUseOpenCounter() == 1) // 按客位人数
						{
							itemCount = MathExtend.multiply(
									seatItem.getCount(), number - owNumber);
						} else {
							itemCount = seatItem.getCount();
						}
						DgOwConsumerDetails detail = new DgOwConsumerDetails();
						detail.setItemFileId(seatItem.getItemId());
						detail.setItemFileNumber(itemCount);
						detail.setNotes("6");
						// 判断插入是否是套餐
						detail.setIsTc((fileItem.getIsTc() == null) ? 0
								: fileItem.getIsTc());
						detail.setOwId(Integer.valueOf(""
								+ serviceMap.get("id")));
						detail.setItemFinalPrice(itemFinalPrice);
//						detail.setItemPayMoney(itemFinalPrice);
						double itemTotal = MathExtend.multiply(itemFinalPrice,
								itemCount);
						detail.setSubtotal(itemTotal);
						openwaterSubtotal = MathExtend.add(openwaterSubtotal,
								itemTotal); // 累加

						DgItemSaleLimit li = dgItemSaleLimitMapper
								.selectByDate(formatDate.format(new Date()));
						if (li != null) {
							DgItemSaleLimitS lis = new DgItemSaleLimitS();
							lis.setUseCount((new Double(itemCount)).intValue());
							lis.setLimitId(li.getId());
							lis.setItemId(seatItem.getItemId());
							dgItemSaleLimitSMapper.updateCount(lis);
						}
						Map zcorgs = new HashMap();
						zcorgs.put("itemFileId",seatItem.getItemId());
						zcorgs.put("ItemFileCount",new Double(itemCount));
						insertInveCall(billMapper.updateInveDate(zcorgs));
						// 插入明细表
						dgOwConsumerDetailsMapper.insertSelective(detail);

						// 判断插入是否是套餐
						if (fileItem.getIsTc() != null
								&& fileItem.getIsTc() == 1) {
							insertTc(fileItem.getId(), itemCount,
									Integer.valueOf("" + serviceMap.get("id")),
									"6");
						}
					}
				}
			}
			// 最后更新营业流水号 openwaterSubtotal经过backNext计算后是负数
			ow.setPeopleCount(number);
			ow.setItemCostsSum(MathExtend.add(ow.getItemCostsSum(),
					openwaterSubtotal));
			ow.setSubtotal(MathExtend.add(ow.getSubtotal(), openwaterSubtotal));
			billMapper.updateOpenWaterPrimaryKey(ow);
		} else {
			// 最后更新营业流水号 openwaterSubtotal经过backNext计算后是负数
			ow.setPeopleCount(number);
			billMapper.updateOpenWaterPrimaryKey(ow);
		}
		
		
		//插入日志
		SysUser user= sysUserMapper.chekUserCode(userCode);
		DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(ow.getSeatId()); //原来客位
		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 3, ow.getOwNum(), 
				 seat.getName(), "操作客位:"+seat.getName()+",进行更改人数操作,更改前人数:"+oldNumber+",更改后人数:"+number,new SimpleDateFormat("yyyy_MM").format(new Date())));
		return ret;
	}

	/**
	 *
	 * 检测增减人数自增品项合法性
	 *
	 * @param openNum
	 * @param number
	 * @param waiterId
	 * @param isJsPx
	 * @return
	 */
	private boolean checkGgkw(String openNum, int number, String waiterId,
							  String isJsPx) {
		DgOpenWater ow = dgOpenWaterMapper.selectByOpenWaterByNum(openNum);
		List<Map<String, Object>> dbIncreaList = billMapper
				.getServiceIncreaItemByOwId(ow.getId());
		// 存在自增品项/切已点
		if (dbIncreaList.size() > 0) {
			if (number < ow.getPeopleCount()) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * 更新限量品项
	 *
	 * @param useCount
	 * @param itemFileId
	 */
	private void updateLimitPx(int useCount, int itemFileId) {
		DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
				.format(new Date()));
		if (li != null) {
			DgItemSaleLimitS lis = new DgItemSaleLimitS();
			lis.setUseCount(useCount);
			lis.setLimitId(li.getId());
			lis.setItemId(itemFileId);
			dgItemSaleLimitSMapper.updateCount(lis);
		}
	}

	/**
	 * 判断赠送品项是否有权限 type 1赠送 2退单
	 */
	private boolean havaQcZsAndBack(String operCode,
									List<Map<String, Object>> org, int type) {
		for (Map<String, Object> m : org) {
			if (type == 2
					|| (type == 1 && m.containsKey("isGift") && (int) m
					.get("isGift") == 1)) {
				int itemFileId = (int) m.get("itemFileId");
				DgItemFile itemFile = dgItemFileMapper
						.selectByPrimaryKey(itemFileId);
				SysUser sysUser = sysUserMapper.selectByUsername(operCode);
				Map param = new HashedMap();
				param.put("zwCode", sysUser.getEmpDuties());
				param.put("type", type);
				List<DgItemFile> zs = sysPerItemfileMapper
						.selectItemFileByZwCodeAndType(param);
				boolean havaItem = false;
				for (DgItemFile z : zs) {
					if (z != null && (z.getItemFileId()).equals(itemFileId)) {
						havaItem = true;
						break;
					}
				}
				if (!havaItem) {
					List<DgItemFileType> zsType = sysPerItemfileMapper
							.selectItemFileTypeByZwCodeAndType(param);
					for (DgItemFileType zt : zsType) {
						if ((zt.getItemFileTypeId()).equals(itemFile.getPpxlId())) {
							havaItem = true;
							break;
						}
					}
					if (!havaItem) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 根据桌位id,时间获取包房收费方案id,没有就返回0
	 *
	 * @param seatId
	 * @return
	 */
	private int getBffa(int seatId) {
		DgSeatManager seatMan = dgSeatManagerMapper.selectBySeatId(seatId);
		if (seatMan == null) {
			return 0;
		}
		int bff = seatMan.getBff();
		if (bff == 4 || bff == 5) {
			if (bff == 4) {
				int gdBffPro = seatMan.getBffGdPro();
				return gdBffPro;
			} else {
				String dgBffWeek = seatMan.getBffWeekProD();
				if (dgBffWeek.length() < 10) {
					return 0;
				} else {
					int dgBffWeekId = 0;
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
					if (weekIndex < 0) {
						weekIndex = 7;
					}
					JSONArray arr = JSONArray.fromObject(seatMan
							.getBffWeekProD());
					if (arr.size() > 0) {
						for (int i = 0; i < arr.size(); i++) {
							JSONObject wObject = arr.getJSONObject(i);
							if (weekIndex == Integer.valueOf(wObject
									.getString("id"))) {
								dgBffWeekId = Integer.valueOf(wObject
										.getString("faId"));
							}
						}
					}
					return dgBffWeekId;
				}
			}
		} else {
			return 0;
		}
	}

	/**
	 * 及时更新流水服务费,包房费
	 *
	 * @param water
	 */
	private Map updateWaterFwfBff(DgOpenWater water) {
		Map ret = new HashMap();
		int seatId = water.getSeatId();
		DgSeatManager seatMan = dgSeatManagerMapper.selectBySeatId(seatId);
		if (seatMan != null) {
			if (water.getItemCostsSum() != null) {
				double itemCostsSum = water.getItemCostsSum();
				if (seatMan.getFwf() != null && seatMan.getFwf() != 1) {
					double fwf = itemCostsSum > seatMan.getFwfConFree() ? 0
							: water.getServiceCharge();
				} else {

				}
				if (seatMan.getBff() != null && seatMan.getBff() != 1) {
					double bff = itemCostsSum > seatMan.getBffConFree() ? 0
							: water.getPrivateRoomCost();
				}
			} else {
				ret.put("fwf", 0.0);
				ret.put("bff", 0.0);
			}
		} else {
			ret.put("fwf", 0.0);
			ret.put("bff", 0.0);
		}
		return ret;
	}

	@Override
	public List<DgConsumerSeat> selectTeamSeatMember(String member) {
		// TODO Auto-generated method stub
		return billMapper.selectTeamSeatMember(member);
	}

	@Override
	public List<Map<String, Object>> getTeamMemberInfo(String member, List ids) {
		// TODO Auto-generated method stub
		Map orgs = new HashMap();
		orgs.put("teamMember", member);
		if (ids != null) {
			orgs.put("ids", ids);
		} else {
			orgs.put("ids", null);
		}
		List<Map<String, Object>> ret = billMapper
				.selectTeamSeatInfoNotInWaterIds(orgs);
		return ret;
	}

	@Override
	public synchronized Map<String, Object> addTeamBill(String operCode,
														List<Map<String, Object>> org, List ids, String zdbz,
														boolean isYxe, String type,String spm) {
		int idsSize = ids.size();
		List<DgOpenWater> waters = dgOpenWaterMapper.selectByIds(ids);
		Map<String, Object> ret = new HashMap<String, Object>();

//		if (!havaQcZsAndBack(StringUtils.isEmpty(spm) ? operCode:spm, org, 1)) {
		if (!havaQcZsAndBack(operCode, org, 1)) {
			ret.put("error", APIEnumDefine.M0101018);
			return ret;
		}
		List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> o : org) { // 验证每个品项的合法性
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("itemId", o.get("itemFileId"));
			checkMap.put("number", MathExtend.multiply(
					(double) o.get("itemFileNumber"), idsSize));
			checklist.add(checkMap);
		}
		Map<String, Object> errCheckItem = checkItemFile(checklist, waters,
				false);
		if (!errCheckItem.isEmpty()) {
			ret.put("error", errCheckItem.get("error"));
			return ret;
		}


		SysUser sysUser = sysUserMapper.selectByUsername(operCode);

		List<DgOwConsumerDetails> owConsumerList = new ArrayList<DgOwConsumerDetails>();
		List<DgOwDetailsOther> owOtherList = new ArrayList<DgOwDetailsOther>();
		List<DgItemSaleLimitS> limitUpdateList = new ArrayList<DgItemSaleLimitS>();
		List<owAndSubtoal> owAndSubtoals = new ArrayList<BillServiceImpl.owAndSubtoal>();
		List<Map<String, Object>> addOrgs = filterOrg(org, 1);
		List<Map<String, Object>> giftOrgs = filterOrg(org, 2);
		for (DgOpenWater water : waters) {
			if (addOrgs.size() > 0) {
				insertAddBill(addOrgs, owConsumerList, owOtherList,
						limitUpdateList, owAndSubtoals, zdbz, water, type,
						false, false,sysUser);
			}
			if (giftOrgs.size() > 0) {
				insertAddBill(giftOrgs, owConsumerList, owOtherList,
						limitUpdateList, owAndSubtoals, zdbz, water, type,
						false, true,sysUser);
			}

			// 发送库存冲减
			DgUrlSetting offset = dgUrlSettingMapper.selectByCode("IS_OFFSET");
			if(offset != null && offset.getValue().equals("1")){
				DgOffset dgOffset = new DgOffset(simpleDateFormat.format(new Date()), com.alibaba.fastjson.JSONArray.toJSONString(checklist),1);
				dgOffsetMapper.insert(dgOffset);
			}
		}
		/** 批量 插入,更新 **/
		dgOwConsumerDetailsMapper.insertBatch(owConsumerList);
		if (owOtherList.size() > 0) {
			dgOwDetailsOtherMapper.insertBatch(owOtherList);
		}
		if (limitUpdateList.size() > 0) {
			dgItemSaleLimitSMapper.updateCountList(limitUpdateList);
		}
		// 打印后厨
		if (type.equals("1")) {
			for (owAndSubtoal oas : owAndSubtoals) {
				dgPrintDataService.insertAddBillPrint(oas.owId, oas.total);
			}
		}

		dgOpenWaterMapper.updateWaterList(waters);
		ret.put("success", true);
		return ret;
	}

	private List<Map<String, Object>> filterOrg(List<Map<String, Object>> org,
												Integer addOrGift) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();

		for (Map o : org) {
			// 1为增加品项
			if (addOrGift == 1) {
				if (o.containsKey("isGift") && (int) o.get("isGift") == 1) {
				} else {
					ret.add(o);
				}
			} else {
				if (o.containsKey("isGift") && (int) o.get("isGift") == 1) {
					ret.add(o);
				}
			}
		}
		return ret;

	}

	private void insertAddBill(List<Map<String, Object>> org,
							   List<DgOwConsumerDetails> owConsumerList,
							   List<DgOwDetailsOther> owOtherList,
							   List<DgItemSaleLimitS> limitUpdateList,
							   List<owAndSubtoal> owAndSubtoals, String zdbz, DgOpenWater water,
							   String type, boolean isYxe, boolean isGift,SysUser sysUser) {

		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(water.getOpenPos());
		pos = dgPosMapper.getPosByID(pos);
		TbOrg tbOrg = new TbOrg();
		tbOrg.setId(Integer.valueOf(pos.getOrganization()));
		tbOrg = tbOrgMapper.getOrgByID(tbOrg);
		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());

		DgConsumerSeat seat = new DgConsumerSeat();
		seat.setId(water.getSeatId());
		seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);
		// 插入服务流水
		Map serviceMap = new HashMap();
		serviceMap.put("owId", water.getId());
		serviceMap.put("waiterId", sysUser.getId());
		serviceMap.put("serviceTime", new Date());
		if (isGift) {
			serviceMap.put("serviceType", 3);
		} else {
			serviceMap.put("serviceType", 2);
		}
		serviceMap.put("serviceNum", serviceNums.get(0));
		serviceMap.put("zdbz", zdbz);
		billMapper.insertOwServiceWater(serviceMap);
		double openwaterSubtotal = 0;
		for (Map<String, Object> o : org) {
			DgItemFile itemFile = dgItemFileMapper.selectByPrimaryKey(Integer
					.valueOf("" + o.get("itemFileId")));
			DgOwConsumerDetails cd = new DgOwConsumerDetails();
			// 先录入额外项(方便计算服务流水小计)
			double zzffTotal = 0;// 制作费用和
			cd.setItemFileId(Integer.valueOf("" + o.get("itemFileId")));
			cd.setItemFileNumber((double) o.get("itemFileNumber"));
			if (o.containsKey("itemFileZs")) {
				cd.setItemFileZs(Double.valueOf("" + o.get("itemFileZs")));
			}
			if (o.containsKey("servingType")) {
				cd.setServingType(Integer.valueOf("" + o.get("servingType")));
			}
			if (o.containsKey("servingTypeGlobal")) {
				cd.setServingTypeGlobal(Integer.valueOf(""
						+ o.get("servingTypeGlobal")));
			}
			if (o.containsKey("expectationsServingTime")) {
				try {
					cd.setExpectationsServingTime(simpleDateFormat.parse(""
							+ o.get("expectationsServingTime")));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			if (isGift) {
				cd.setNotes("3");
			} else {
				cd.setNotes("2");
			}
			cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile.getIsTc());
			cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
			if (o.get("extra") != null) {
				List<Map<String, Object>> ext = (List<Map<String, Object>>) o
						.get("extra");
				for (Map<String, Object> e : ext) {
					DgOwDetailsOther dts = new DgOwDetailsOther();
					dts.setItemId(cd.getItemFileId());
					dts.setSfId(cd.getOwId());
					dts.setOtype(Integer.valueOf("" + e.get("otype")));// 1、口味。2要求。3制作方法
					if (e.containsKey("ocode")) {
						dts.setOcode("" + e.get("ocode"));// 类型编码 临时数据为0
					}
					if (e.containsKey("oname")) {
						dts.setOname("" + e.get("oname"));
					}
					if (e.containsKey("ocosts")) {
						dts.setOcosts(Double.valueOf("" + e.get("ocosts")));
					}
					if (e.containsKey("zzffSf")) {
						dts.setZzffSf(Integer.valueOf("" + e.get("zzffSf")));
					}
					if (e.containsKey("zzffSfType") && dts.getZzffSf()!=null && dts.getZzffSf()==1) {
						dts.setZzffSfType(Integer.valueOf(""
								+ e.get("zzffSfType")));
						// 多重判断是否是制作方法
						if (dts.getOtype() == 3 && dts.getOcosts() != null
								&& dts.getZzffSfType() == 0) {
							zzffTotal = MathExtend.add(zzffTotal,
									dts.getOcosts());
						} else if (dts.getOtype() == 3
								&& dts.getOcosts() != null
								&& dts.getZzffSfType() == 1) {
							double oc = MathExtend.multiply(dts.getOcosts(),
									cd.getItemFileNumber());
							zzffTotal = MathExtend.add(zzffTotal, oc);
						}
					}
					owOtherList.add(dts);
				}
			}
			// 插入套餐子项
			if (cd.getIsTc() == 1) {
				insertTc(itemFile.getId(), cd.getItemFileNumber(),
						cd.getOwId(), "2");
			}

			DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
					.format(new Date()));
			if (li != null) {
				DgItemSaleLimitS lis = new DgItemSaleLimitS();
				lis.setUseCount((double) o.get("itemFileNumber"));
				lis.setLimitId(li.getId());
				lis.setItemId(Integer.valueOf("" + o.get("itemFileId")));
				limitUpdateList.add(lis);
			}
			Map orgs = new HashMap();
			orgs.put("itemFileId",Integer.valueOf("" + o.get("itemFileId")));
			orgs.put("ItemFileCount",(double) o.get("itemFileNumber"));
			insertInveCall(billMapper.updateInveDate(orgs));
			// 获取品项当前价格
			Map<String, Object> price = getDishPrice(cd.getItemFileId(),
					Integer.valueOf(pos.getOrganization()), seat.getConsArea(),
					cd.getItemFileNumber(), water.getId(), false, isYxe);
			double itemTotal = (double) price.get("price");
			if ((boolean) price.get("isPriceCal") == true) {
				cd.setIsPriceCal(1);
			} else {
				cd.setIsPriceCal(0);

			}
			double subtotal = 0.0;
			if (cd.getNotes().equals("3")) {
				cd.setProductionCosts(0.0);
				cd.setItemFinalPrice(0.0);
//				cd.setItemPayMoney(0.0);
				cd.setSubtotal(0.0);
				cd.setZsProductionCosts(zzffTotal);
				cd.setZsItemFinalPrice(itemTotal);
				double moc = MathExtend.multiply(itemTotal,
						cd.getItemFileNumber());
				cd.setZsSubtotal(MathExtend.add(zzffTotal, moc));
			} else {
				cd.setProductionCosts(zzffTotal);
				cd.setItemFinalPrice(itemTotal);
//				cd.setItemPayMoney(itemTotal);
				double moc = MathExtend.multiply(itemTotal,
						cd.getItemFileNumber());
				subtotal = MathExtend.add(zzffTotal, moc);
				cd.setSubtotal(subtotal);
			}
			cd.setDiscount(1.0);
			cd.setAddByYxe(0);
			owConsumerList.add(cd);
			openwaterSubtotal = MathExtend.add(openwaterSubtotal, subtotal);
		}
		// 最后更新营业流水号
		water.setItemCostsSum(MathExtend.add(water.getItemCostsSum(),
				openwaterSubtotal));
		water.setSubtotal(MathExtend.add(water.getSubtotal(), openwaterSubtotal));

		// 打印后厨
		if (type.equals("1")) {
			owAndSubtoals.add(new owAndSubtoal(Integer.valueOf(""
					+ serviceMap.get("id")), openwaterSubtotal));
		}
	}

	@Resource
	private DgWeekDiscountMapper dgWeekDiscountMapper;

	@Resource
	private DgItemDiscountProgrammeMapper dgItemDiscountProgrammeMapper;

	@Resource
	private DgItemDiscountProgrammeSMapper dgItemDiscountProgrammeSMapper;

	@Override
	public Map<Integer, Object> getPxDzFaPrice(Integer id) {
		Map<Integer, Object> ret = new HashMap();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (weekIndex < 0) {
			weekIndex = 0;
		}
		DgWeekDiscount dis;
		String name = null;
		if (weekIndex == 0) {
			name = "星期日";
		} else if (weekIndex == 1) {
			name = "星期一";
		} else if (weekIndex == 2) {
			name = "星期二";
		} else if (weekIndex == 3) {
			name = "星期三";
		} else if (weekIndex == 4) {
			name = "星期四";
		} else if (weekIndex == 5) {
			name = "星期五";
		} else if (weekIndex == 6) {
			name = "星期六";
		}
		dis = dgWeekDiscountMapper.selectByName(name);
		if (dis != null) {
			DgItemDiscountProgramme dp = dgItemDiscountProgrammeMapper
					.selectByPrimaryKey(dis.getpId());
			if (dp == null) {
				ret.put(-1, "今日没有打折方案!");
				return ret;
			}
			Map orgs = new HashedMap();
			orgs.put("itemId", id);
			orgs.put("pId", dp.getId());
			if (dp.getDisable() != 1) {
				if (dp.getType().equals("1")) {
					Map<String, Object> dps = dgItemDiscountProgrammeSMapper
							.selectByPIdAndItemIdType1(orgs);
					if (dps != null && dps.get("discount") != null) {
						ret.put(id, MathExtend.multiply(MathExtend.divide(
								(double) dps.get("standard_price"), 100),
								(int) dps.get("discount")));
					} else {
						ret.put(-1, "今日打折方案打折比例为空!");
					}
				} else {
					Map<String, Object> dps = dgItemDiscountProgrammeSMapper
							.selectByPIdAndItemIdType2(orgs);
					if (dps != null && dps.get("discount") != null) {
						ret.put(id, MathExtend.multiply(MathExtend.divide(
								(double) dps.get("standard_price"), 100),
								(int) dps.get("discount")));
					} else {
						ret.put(-1, "今日打折方案打折比例为空!");
					}
				}

			} else {
				ret.put(-1, "今日打折方案已停用!");
			}

		} else {
			ret.put(-1, "今日没有打折方案!");
		}

		return ret;
	}

	@Override
	public Integer getNowBisId() {
		List<TbBis> tbBiss = tbBisMapper.selectAllBis();
		List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
		if (tbBiss.size() == 0) {
			return null;
		}
		for (int i = 0; i < tbBiss.size(); i++) {
			if (i != tbBiss.size() - 1) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("startTime", tbBiss.get(i).getBisTime());
				m.put("endTime", tbBiss.get(i + 1).getBisTime());
				m.put("nowTime", format.format(new Date()));
				m.put("TbBis", tbBiss.get(i));
				timeD.add(m);
			} else {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("startTime", tbBiss.get(i).getBisTime());
				m.put("endTime", tbBiss.get(0).getBisTime());
				m.put("nowTime", format.format(new Date()));
				m.put("TbBis", tbBiss.get(0));
				timeD.add(m);
			}
		}
		Integer TbisName = null; // 获取市别id
		for (int i = 0; i < timeD.size(); i++) {
			int count = tbBisMapper.calculateSJD(timeD.get(i));
			if (count > 0) {
				TbisName = ((TbBis) timeD.get(i).get("TbBis")).getId();
				break;
			}
		}
		if (TbisName == null) // 没有就是最后个时段
		{
			TbisName = ((TbBis) timeD.get(timeD.size() - 1).get("TbBis"))
					.getId();
		}
		return TbisName;
	}

	@Override
	public List<Map> selectAllItemGroupByXl(Integer seatId) {
		// TODO Auto-generated method stub
		List<Map> xlItem = new ArrayList<Map>();
		List<Map<String, Object>> ret = billMapper.selectAllItem(seatId);
		for (int i = 0; i < ret.size(); i++) {
			Map<String, Object> r = ret.get(i);
			if (r.containsKey("item_id")) {
				r.put("isGq", true);
			} else {
				r.put("isGq", false);
			}
			if (!r.containsKey("unit")) {
				r.put("unit", "");
			}
			if (!r.containsKey("lspx")) {
				r.put("lspx", 0);
			}
			if (!r.containsKey("is_tc")) {
				r.put("is_tc", 0);
			}

			if (xlItem.isEmpty()) {
				Map item = new HashMap();
				item.put("xlId", r.get("ppxl_id"));
				item.put("xlName", r.get("xlName"));
				List items = new ArrayList();
				items.add(ret.get(i));
				item.put("xlItems", items);
				xlItem.add(item);
			} else {
				boolean find = false;
				for (Map xl : xlItem) {
					if (xl.get("xlId").equals(r.get("ppxl_id"))) {
						List items = (List) xl.get("xlItems");
						items.add(ret.get(i));
						find = true;
						break;
					}
				}
				if (!find) {
					Map item = new HashMap();
					item.put("xlId", r.get("ppxl_id"));
					item.put("xlName", r.get("xlName"));
					List items = new ArrayList();
					items.add(ret.get(i));
					item.put("xlItems", items);
					xlItem.add(item);
				}
			}
		}
		return xlItem;
	}

	@Override
	public DgReserveManager getReserverInfoBySeatId(Integer seatId) {
		// TODO Auto-generated method stub
		Map orgs = new HashMap();
		orgs.put("time", simpleDateFormat.format(new Date()));
		orgs.put("seatId", seatId);
		DgReserveManager ret = dgReserveManagerMapper.selectYdFromSeatId(orgs);
		ret.setChildIds(dgReserveSeatidsMapper.selectByParentIdExitUse(ret
				.getId()));
		return ret;
	}

	private class owAndSubtoal {
		public Integer owId; // 服务id
		public double total;

		public owAndSubtoal(Integer owId, double total) {
			this.owId = owId;
			this.total = total;
		}
	}

	@Override
	public String updateInveDate(Map orgs) {
		// TODO Auto-generated method stub
		return billMapper.updateInveDate(orgs);
	}

	@Override
	public Map<String, Object> addYxePreBill(List<Map<String, Object>> org,
											 String openNumber) {
		// TODO Auto-generated method stub
		DgOpenWater water = dgOpenWaterMapper
				.selectByOpenWaterByNum(openNumber);
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map<String, Object>> checklist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> o : org) { // 验证每个品项的合法性
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("itemId", o.get("itemFileId"));
			checkMap.put("number", o.get("itemFileNumber"));
			checklist.add(checkMap);
		}
		List<DgOpenWater> waters = new ArrayList<DgOpenWater>();
		waters.add(water);
		Map<String, Object> errCheckItem = checkItemFile(checklist, waters,
				false);
		if (!errCheckItem.isEmpty()) {
			ret.put("error", errCheckItem.get("error"));
			return ret;
		}
		List<DgPreOrderbill> dgPreOrderbills = new ArrayList<DgPreOrderbill>();
		String owNum = "88"+System.currentTimeMillis();
		String preNum = null;
		for (Map<String, Object> o : org) {
			DgItemFile itemFile = dgItemFileMapper.selectByPrimaryKey(Integer.valueOf("" + o.get("itemFileId")));
			List<DgPreOrderbill> queryDgPreOrderbill = dgPreOrderbillMapper.selectByWaterId(water.getId());
			DgPreOrderbill dgPreOrderbill = new DgPreOrderbill();
			dgPreOrderbill.setItemFileId(itemFile.getId());
			dgPreOrderbill.setItemFileCount((int)(double)o.get("itemFileNumber"));
			dgPreOrderbill.setId(UUID.randomUUID().toString());
			dgPreOrderbill.setOwNum(owNum);
			dgPreOrderbill.setWaterId(water.getId());

			if(queryDgPreOrderbill.size() == 0){
				preNum = owNum;
				dgPreOrderbill.setFirstNum(owNum);
				dgPreOrderbills.add(dgPreOrderbill);
			} else {
				preNum = queryDgPreOrderbill.get(0).getFirstNum();
				dgPreOrderbill.setFirstNum(queryDgPreOrderbill.get(0).getFirstNum());
				dgPreOrderbills.add(dgPreOrderbill);
			}

		}
		if(dgPreOrderbills.size() > 0){
			dgPreOrderbillMapper.insertBatch(dgPreOrderbills);
		}

		List<DgPreOrderbill> queryDgPreOrderbill = dgPreOrderbillMapper.selectByPreNum(owNum);
		dgPrintDataService.insertPreAddBill(queryDgPreOrderbill,dgConsumerSeatMapper.selectByPrimaryKey(water.getSeatId()).getConsArea(),water,preNum);
		return ret;
	}

	private void insertInveCall(String inveRet){
		if(!StringUtils.isEmpty(inveRet)){
			String[] splitStr = inveRet.split(",");
			for(String s:splitStr){
				if(!StringUtils.isEmpty(s)){
					String[] content = s.split(":");
					double number = Double.parseDouble(content[1]);
					if(number <= 0){
						DgCallService dgCallService = new DgCallService();
						dgCallService.setContent(content[0]+"物品已用完,请通知采购部采购!");
						dgCallService.setState(1);
						dgCallService.setType(3);
						dgCallServiceMapper.insert(dgCallService);
					}
				}
			}
		}
	}

	@Override
	public Map<String, Object> addBill(List<Map<String, Object>> org,String waterNum) {
		// TODO Auto-generated method stub
		DgOpenWater water = dgOpenWaterMapper
				.selectByOpenWaterByNum(waterNum);
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map> orderItemsRet = new ArrayList<Map>();
		List<DgOpenWater> waters = new ArrayList<DgOpenWater>();
		waters.add(water);

		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(water.getOpenPos());
		pos = dgPosMapper.getPosByID(pos);
		TbOrg tbOrg = new TbOrg();
		tbOrg.setId(Integer.valueOf(pos.getOrganization()));
		tbOrg = tbOrgMapper.getOrgByID(tbOrg);
		List<String> serviceNums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.FW), new TypeToken<List<String>>() {
		}.getType());

		DgConsumerSeat seat = new DgConsumerSeat();
		seat.setId(water.getSeatId());
		seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);
		// 插入服务流水
		Map serviceMap = new HashMap();
		serviceMap.put("owId", water.getId());
		serviceMap.put("waiterId", water.getWaiter());
		serviceMap.put("serviceTime", new Date());
		serviceMap.put("zdbz", "线上点菜");
		serviceMap.put("serviceType", 2);
		serviceMap.put("serviceNum", serviceNums.get(0));
		billMapper.insertOwServiceWater(serviceMap);
		double openwaterSubtotal = 0;
		List<DgOwConsumerDetails> owConsDetails = new ArrayList<DgOwConsumerDetails>();
		for (Map<String, Object> o : org) {
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("itemCode", o.get("itemCode"));
			checkMap.put("number", o.get("number"));
			Map<String, Object> errCheckItem = checkItemFileByCode(checkMap, waters,false);
			//判断是否是未通过品项
			if(!errCheckItem.isEmpty()) {
				setOnlineBodyMap(false,errCheckItem.get("error").toString(),o.get("itemCode").toString(),o.get("id").toString(),o.get("goodsname").toString(),orderItemsRet);
				continue;
			}
			setOnlineBodyMap(true,null,o.get("itemCode").toString(),o.get("id").toString(),o.get("goodsname").toString(),orderItemsRet);

			DgItemFile itemFile = dgItemFileMapper.getDgItemFileByNumber(o.get("itemCode").toString());
			DgOwConsumerDetails cd = new DgOwConsumerDetails();
			cd.setItemFileId(itemFile.getId());
			cd.setItemFileNumber((double) o.get("number"));
			cd.setNotes("2");
			cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile.getIsTc());
			cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
			// 先录入额外项(方便计算服务流水小计)
			double zzffTotal = 0;// 制作费用和
			// 插入套餐子项
			if (cd.getIsTc() == 1) {
				insertTc(itemFile.getId(), cd.getItemFileNumber(),
						cd.getOwId(), "2");
			}

			DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
					.format(new Date()));
			if (li != null) {
				DgItemSaleLimitS lis = new DgItemSaleLimitS();
				lis.setUseCount((double) o.get("number"));
				lis.setLimitId(li.getId());
				lis.setItemId(itemFile.getId());
				dgItemSaleLimitSMapper.updateCount(lis);
			}

			// 获取品项当前价格
			Map<String, Object> price = getDishPrice(cd.getItemFileId(),
					Integer.valueOf(pos.getOrganization()), seat.getConsArea(),
					cd.getItemFileNumber(), water.getId(), false, false);
			double itemTotal = (double) price.get("price");
			if ((boolean) price.get("isPriceCal") == true) {
				cd.setIsPriceCal(1);
			} else {
				cd.setIsPriceCal(0);

			}
			double subtotal = 0.0;
			cd.setProductionCosts(zzffTotal);
			cd.setItemFinalPrice(itemTotal);
//			cd.setItemPayMoney(itemTotal);
			double moc = MathExtend.multiply(itemTotal,
					cd.getItemFileNumber());
			subtotal = MathExtend.add(zzffTotal, moc);
			cd.setSubtotal(subtotal);
			cd.setDiscount(1.0);
			owConsDetails.add(cd);
			openwaterSubtotal = MathExtend.add(openwaterSubtotal, subtotal);
		}
		if(owConsDetails.size() > 0) {
			// 批量插入品项
			dgOwConsumerDetailsMapper.insertBatch(owConsDetails);
			// 最后更新营业流水号
			water.setItemCostsSum(MathExtend.add(water.getItemCostsSum(),
					openwaterSubtotal));
			water.setSubtotal(MathExtend.add(water.getSubtotal(), openwaterSubtotal));
			billMapper.updateOpenWaterPrimaryKey(water);

			// 打印后厨
			dgPrintDataService.insertAddBillPrint(
					Integer.valueOf("" + serviceMap.get("id")),
					openwaterSubtotal);
			ret.put("success", true);
		}
		ret.put("body",orderItemsRet);
		return ret;
	}


	/*
	 * 验证品项是否存在/沽清/停用/今日限量
	 */
	private Map<String, Object> checkItemFileByCode(
			Map<String, Object> item, List<DgOpenWater> waterIds,
			boolean isOpenBill) {
		// TODO Auto-generated method stub
		Map<String, Object> errList = new HashMap<String, Object>();
		DgItemFile itemFile = dgItemFileMapper.getDgItemFileByNumber(item.get("itemCode").toString()); // 品项是否存在
		if (itemFile != null) {
			if (itemFile.getIsTc() != null && itemFile.getIsTc() == 1) {
				DgItemFilePackage pk = dgItemFilePackageMapper
						.selectByItemFileId(itemFile.getId());
				if (pk != null) {
					List<Map> bx = dgItemFilePackageBxMapper
							.selectByPackageId(pk.getId());
					{
						for (Map b : bx) {
							if (b.get("item_file_id") != null) {
								DgItemFile tItemFile = dgItemFileMapper
										.selectByPrimaryKey((int) b
												.get("item_file_id")); // 品项是否存在
								if (b.get("item_amount") != null) {
									checkItemSingle(
											tItemFile,
											errList,
											MathExtend.multiply(
													(double) item
															.get("number"),
													(int) b.get("item_amount")),
											itemFile.getId(), isOpenBill,
											waterIds);
								}
							}
						}
					}
				}
				checkItemSingle(itemFile, errList,
						(double) item.get("number"), 0, isOpenBill,
						waterIds);
			} else {
				checkItemSingle(itemFile, errList,
						(double) item.get("number"), 0, isOpenBill,
						waterIds);
			}
		} else {
			addMapKeyValue("error", "编码为" + item.get("itemCode") + "的品项不存在",
					errList);
			addMapKeyValueSplitString("errItemCodes", item.get("itemCode")+"", errList);
		}
		return errList;
	}

	@Override
	public void insertOnlineOrderBill(String mq) {
		// TODO Auto-generated method stub
		try{
			JSONObject obj = JSONObject.fromObject(mq);
			JSONObject arr = JSONObject.fromObject(obj.getString("message"));
			String order = arr.getString("orderid");
			String orderBack = OnlineHttp.onlineOrderInfo(order);
			JSONObject orderBackObj = JSONObject.fromObject(orderBack);
			if(orderBackObj.getInt("status") == 200){
				JSONObject orderInfo = orderBackObj.getJSONObject("content").getJSONObject("order");
				String offonlineorderid = orderInfo.getString("offonlineorderid");
				String tableid = orderInfo.getString("tableid");
				String shopid = orderInfo.getString("shopid");
				JSONArray orderArr = JSONArray.fromObject(orderInfo.getString("orderDetailsInstore"));
				LinkedList<Map<String, Object>> orgs = new LinkedList<Map<String,Object>>();
				if(orderArr.size() > 0){
					for(int j=0;j<orderArr.size();j++){
						JSONObject org = orderArr.getJSONObject(j);
						Map<String, Object> item = new HashMap<String, Object>();
						item.put("itemCode", org.getString("goodscode"));
						item.put("number", (double)Integer.valueOf(org.get("num").toString()));
						item.put("id",org.get("id"));
						item.put("goodsname",org.get("goodsname"));
						orgs.add(item);
					}
				}

				Map ret = new HashMap();
				ret.put("orderid",order);
				ret.put("shopid",shopid);


				DgConsumerSeat seat = dgConsumerSeatMapper.selectSeatIdByUuidKey(tableid);
				if(seat == null){
					ret.put("result",false);
					ret.put("message","台位不存在");
					Gson gson = new Gson();
					OnlineHttp.onlineOrderBack(gson.toJson(ret));
					return;
				}


				Map org = new HashMap();
				org.put("seatId", seat.getId());
				Map water = apiCheckServiceMapper.selectOpenWaterBySeatIdLastOne(org);

				if(StringUtil.isEmpty(offonlineorderid) || offonlineorderid.equals("null")) {
					if(water == null) {
						ret.put("result",false);
						ret.put("message","未开台/营业流水埋单或锁定");
					} else {
						ret.putAll(insertOnlineAddBill(orgs,water.get("ow_num").toString()));
					}
				} else {
					if(water.get("ow_num").toString().equals(offonlineorderid)){
						ret.putAll(insertOnlineAddBill(orgs,offonlineorderid));
					} else {
						ret.put("result",false);
						ret.put("message","流水已转账不能进行加单!");
					}
				}

				Gson gson = new Gson();
				OnlineHttp.onlineOrderBack(gson.toJson(ret));
			}
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException();  //回滚
		}
	}

	private Map insertOnlineAddBill(List<Map<String, Object>> orgs,String offonlineorderid){
		Map ret = new HashMap();
		DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(offonlineorderid);
		if(water == null){
			ret.put("result",false);
			ret.put("message","流水不存在!");
			return ret;
		}

		if (water.getOwState() != 1) {
			ret.put("result",false);
			ret.put("message","流水不是已锁定或结账不能进行加单操作!");
		} else {
//			//加单模式
			Map addRet = addBill(orgs, offonlineorderid);
			//预加单模式
//			Map addRet = addOnlinePreBill(orgs, offonlineorderid);
			ret.put("body",addRet.get("body"));
//			if(addRet.containsKey("success")) {
			ret.put("result",true);
//			} else {
//				ret.put("result",false);
//			}
			ret.put("message","");
		}
		return ret;
	}

	private void setOnlineBodyMap(boolean success,String msg,String goodscode,String goodsid,String goodsname,List<Map> orderItemsRet){
		Map item = new HashedMap();
		item.put("msg",msg==null?"":msg);
		item.put("goodscode",goodscode);
		item.put("id",goodsid);
		item.put("goodsname",goodsname);
		item.put("success",success);
		orderItemsRet.add(item);
	}

	@Override
	public Map<String, Object> addOnlinePreBill(List<Map<String, Object>> org,
												String openNumber) {
		// TODO Auto-generated method stub
		DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(openNumber);
		Map<String, Object> ret = new HashMap<String, Object>();
		List<Map> orderItemsRet = new ArrayList<Map>();
		List<DgOpenWater> waters = new ArrayList<DgOpenWater>();
		waters.add(water);
		List<DgPreOrderbill> dgPreOrderbills = new ArrayList<DgPreOrderbill>();
		String owNum = "88"+System.currentTimeMillis();
		String preNum = null;
		for (Map<String, Object> o : org) {
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("itemCode", o.get("itemCode"));
			checkMap.put("number", o.get("number"));
			Map<String, Object> errCheckItem = checkItemFileByCode(checkMap, waters,false);
			//判断是否是未通过品项
			if(!errCheckItem.isEmpty()) {
				setOnlineBodyMap(false,errCheckItem.get("error").toString(),o.get("itemCode").toString(),o.get("id").toString(),o.get("goodsname").toString(),orderItemsRet);
				continue;
			}
			setOnlineBodyMap(true,null,o.get("itemCode").toString(),o.get("id").toString(),o.get("goodsname").toString(),orderItemsRet);

			DgItemFile itemFile = dgItemFileMapper.getDgItemFileByNumber(o.get("itemCode").toString());
			List<DgPreOrderbill> queryDgPreOrderbill = dgPreOrderbillMapper.selectByWaterId(water.getId());
			DgPreOrderbill dgPreOrderbill = new DgPreOrderbill();
			dgPreOrderbill.setItemFileId(itemFile.getId());
			dgPreOrderbill.setItemFileCount((int)(double)o.get("number"));
			dgPreOrderbill.setId(UUID.randomUUID().toString());
			dgPreOrderbill.setOwNum(owNum);
			dgPreOrderbill.setWaterId(water.getId());

			if(queryDgPreOrderbill.size() == 0){
				preNum = owNum;
				dgPreOrderbill.setFirstNum(owNum);
				dgPreOrderbills.add(dgPreOrderbill);
			} else {
				preNum = queryDgPreOrderbill.get(0).getFirstNum();
				dgPreOrderbill.setFirstNum(queryDgPreOrderbill.get(0).getFirstNum());
				dgPreOrderbills.add(dgPreOrderbill);
			}
		}
		if(dgPreOrderbills.size() > 0){
			dgPreOrderbillMapper.insertBatch(dgPreOrderbills);
			List<DgPreOrderbill> queryDgPreOrderbill = dgPreOrderbillMapper.selectByPreNum(owNum);
			dgPrintDataService.insertPreAddBill(queryDgPreOrderbill,dgConsumerSeatMapper.selectByPrimaryKey(water.getSeatId()).getConsArea(),water,preNum);
		}

		ret.put("body",orderItemsRet);
		return ret;
	}

	@Override
	public Map<String, Object> insertCxptOrderBill(List<Map> items, DgConsumerSeat seat) {
		Map<String,Object> resmap = new HashMap<>();
		resmap.put("result",false);
		Map org = new HashMap();
		org.put("seatId", seat.getId());
		Map waterInfo = apiCheckServiceMapper.selectOpenWaterBySeatIdLastOne(org);

		if(waterInfo == null) {
			resmap.put("message","未开台/营业流水埋单或锁定");
		} else {
			DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(waterInfo.get("ow_num").toString());
			if(water == null){
				resmap.put("message","流水不存在!");
				return resmap;
			}

			if (water.getOwState() != 1) {
				resmap.put("message","流水不是已锁定或结账不能进行加单操作!");
			} else {
				List<Map> orderItemsRet = new ArrayList<Map>();
				List<DgOpenWater> waters = new ArrayList<DgOpenWater>();
				waters.add(water);

				Gson gson = new Gson();
				DgPos pos = new DgPos();
				pos.setId(water.getOpenPos());
				pos = dgPosMapper.getPosByID(pos);
				TbOrg tbOrg = new TbOrg();
				tbOrg.setId(Integer.valueOf(pos.getOrganization()));
				tbOrg = tbOrgMapper.getOrgByID(tbOrg);
				List<String> serviceNums = gson.fromJson(deskBusinessSettingService
						.createFlowNumber(tbOrg.getOrgCode(), pos.getNumber(), 1,
								SerialRulEnum.FW), new TypeToken<List<String>>() {
				}.getType());

				List<DgOwConsumerDetails> owConsDetails = new ArrayList<DgOwConsumerDetails>();

				//每个品项验证一次
				for (Map<String, Object> o : items) {
					o.put("number",((BigDecimal)o.get("number")).doubleValue());
					Map<String, Object> checkMap = new HashMap<String, Object>();
					checkMap.put("itemCode", o.get("itemCode"));
					checkMap.put("number", o.get("number"));
					Map<String, Object> errCheckItem = checkItemFileByCode(checkMap, waters, false);
					//判断是否是未通过品项
					if (!errCheckItem.isEmpty()) {
						resmap.put("message",o.get("goodsname").toString()+":"+errCheckItem.get("error").toString());
						return resmap;
					}
				}

				// 插入服务流水
				Map serviceMap = new HashMap();
				serviceMap.put("owId", water.getId());
				serviceMap.put("waiterId", water.getWaiter());
				serviceMap.put("serviceTime", new Date());
				serviceMap.put("zdbz", "创享平台线上点菜");
				serviceMap.put("serviceType", 2);
				serviceMap.put("serviceNum", serviceNums.get(0));
				billMapper.insertOwServiceWater(serviceMap);
				double openwaterSubtotal = 0;
				for(Map<String,Object> o:items){
					DgItemFile itemFile = dgItemFileMapper.getDgItemFileByNumber(o.get("itemCode").toString());
					DgOwConsumerDetails cd = new DgOwConsumerDetails();
					cd.setItemFileId(itemFile.getId());
					cd.setItemFileNumber((double) o.get("number"));
					cd.setNotes("2");
					cd.setIsTc((itemFile.getIsTc() == null) ? 0 : itemFile.getIsTc());
					cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
					// 先录入额外项(方便计算服务流水小计)
					double zzffTotal = 0;// 制作费用和
					// 插入套餐子项
					if (cd.getIsTc() == 1) {
						insertTc(itemFile.getId(), cd.getItemFileNumber(),
								cd.getOwId(), "2");
					}

					DgItemSaleLimit li = dgItemSaleLimitMapper.selectByDate(formatDate
							.format(new Date()));
					if (li != null) {
						DgItemSaleLimitS lis = new DgItemSaleLimitS();
						lis.setUseCount((double) o.get("number"));
						lis.setLimitId(li.getId());
						lis.setItemId(itemFile.getId());
						dgItemSaleLimitSMapper.updateCount(lis);
					}

					// 获取品项当前价格
					Map<String, Object> price = getDishPrice(cd.getItemFileId(),
							Integer.valueOf(pos.getOrganization()), seat.getConsArea(),
							cd.getItemFileNumber(), water.getId(), false, false);
					double itemTotal = (double) price.get("price");
					if ((boolean) price.get("isPriceCal") == true) {
						cd.setIsPriceCal(1);
					} else {
						cd.setIsPriceCal(0);

					}
					double subtotal = 0.0;
					cd.setProductionCosts(zzffTotal);
					cd.setItemFinalPrice(itemTotal);
//			cd.setItemPayMoney(itemTotal);
					double moc = MathExtend.multiply(itemTotal,
							cd.getItemFileNumber());
					subtotal = MathExtend.add(zzffTotal, moc);
					cd.setSubtotal(subtotal);
					cd.setDiscount(1.0);
					owConsDetails.add(cd);
					openwaterSubtotal = MathExtend.add(openwaterSubtotal, subtotal);
				}
				if(owConsDetails.size() > 0) {
					// 批量插入品项
					dgOwConsumerDetailsMapper.insertBatch(owConsDetails);
					// 最后更新营业流水号
					water.setItemCostsSum(MathExtend.add(water.getItemCostsSum(),
							openwaterSubtotal));
					water.setSubtotal(MathExtend.add(water.getSubtotal(), openwaterSubtotal));
					billMapper.updateOpenWaterPrimaryKey(water);

					// 打印后厨
					dgPrintDataService.insertAddBillPrint(
							Integer.valueOf("" + serviceMap.get("id")),
							openwaterSubtotal);
				}
				resmap.put("result", true);
			}
		}
		return resmap;
	}

	private Map<String,Object> setItemFileNum(List<DgOwConsumerDetails> dgOwConsumerDetailss, Map<String,Object> checkObj) {
		Map<String, Object> map = new HashMap<>();
		for (Iterator<DgOwConsumerDetails> it = dgOwConsumerDetailss.iterator(); it.hasNext(); ) {
			DgOwConsumerDetails dgOwConsumerDetails = it.next();
			if(dgOwConsumerDetails.getBackOwId() != null){
				if (dgOwConsumerDetails.getBackOwId().equals(checkObj.get("ow_id"))) {
					if (dgOwConsumerDetails.getItemFileNumber() != null && checkObj.get("item_file_id") != null) {
						if (checkObj.get("item_file_id").equals(dgOwConsumerDetails.getItemFileId())) {
							checkObj.put("item_file_number",(double)checkObj.get("item_file_number") + dgOwConsumerDetails.getItemFileNumber());
							checkObj.put("subtotal",MathExtend.multiply((double)checkObj.get("item_file_number"),(double)checkObj.get("item_final_price")));
							it.remove();
						}
					}
				}
			}
		}
		map.put("list", dgOwConsumerDetailss);
		map.put("obj", checkObj);
		return map;
	}
}