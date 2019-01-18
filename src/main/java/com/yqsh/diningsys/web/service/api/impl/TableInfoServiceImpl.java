package com.yqsh.diningsys.web.service.api.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.api.*;
import com.yqsh.diningsys.web.dao.archive.DgCardMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumptionAreaMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFileMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFilePackageBxMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFilePackageMapper;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.dao.archive.DgPublicCode0Mapper;
import com.yqsh.diningsys.web.dao.archive.DgSeatDoorinfoMapper;
import com.yqsh.diningsys.web.dao.archive.TbBisMapper;
import com.yqsh.diningsys.web.dao.archive.TbOrgMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatItemMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatManagerMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDisableMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemOutofStockMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemTimeLimitPicMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgPromotionItemMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwConsumerDetailsMapper;
import com.yqsh.diningsys.web.dao.sysSettings.DgUrlSettingMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.archive.DgCard;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackage;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatids;
import com.yqsh.diningsys.web.model.archive.DgSeatDoorinfo;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitPic;
import com.yqsh.diningsys.web.model.deskBusiness.DgPromotionItem;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwClearingway;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.api.*;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.util.OnlineHttp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yqsh-zc on 2017/2/7.
 */

@Service
public class TableInfoServiceImpl implements TableInfoService {
	private static Logger LOGGER=Logger.getLogger(TableInfoServiceImpl.class);
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat formatDate = new SimpleDateFormat(
			"yyyy-MM-dd"); // 年月日
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Object lock = new Object();
	@Autowired
	private BillService billService;
	@Resource
	private DgSeatDoorinfoMapper dgSeatDoorinfoMapper;
	@Resource
	private DgOpenWaterMapper dgOpenWaterMapper;
	@Resource
	private BillMapper billMapper;
	@Resource
	private DgConsumerSeatMapper dgConsumerSeatMapper;
	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private TbOrgMapper tbOrgMapper;
	@Resource
	private DgPosMapper dgPosMapper;
	@Resource
	private DgOwConsumerDetailsMapper dgOwConsumerDetailsMapper;
	@Resource
	private DeskBusinessSettingService deskBusinessSettingService;
	@Resource
	private DgSeatItemMapper dgSeatItemMapper;
	@Resource
	private DgItemFileMapper dgItemFileMapper;
	@Resource
	private DgItemFilePackageMapper dgItemFilePackageMapper;
	@Resource
	private DgItemFilePackageBxMapper dgItemFilePackageBxMapper;
	@Resource
	private DgItemSaleLimitMapper dgItemSaleLimitMapper;
	@Resource
	private DgItemDisableMapper dgItemDisableMapper;
	@Resource
	private TbBisMapper tbBisMapper;
	@Resource
	private DgPromotionItemMapper dgPromotionItemMapper;
	@Resource
	private DgItemSaleLimitSMapper dgItemSaleLimitSMapper;
	@Resource
	private DgItemOutofStockMapper dgItemOutofStockMapper;
	@Resource
	private DgSeatManagerMapper dgSeatManagerMapper;
	@Resource
	private APICheckServiceMapper apiCheckServiceMapper;
	@Resource
	private DgPublicCode0Mapper dgPublicCode0Mapper;
	@Resource
	private DgItemTimeLimitPicMapper dgItemTimeLimitPicMapper;
	@Autowired
	private DgReserveManagerMapper dgReserveManagerMapper;
	@Autowired
	private DgReserveSeatidsMapper dgReserveSeatidsMapper;
	@Autowired
	private DgCardMapper dgCardMapper;
	@Autowired
	private DgCallServiceMapper dgCallServiceMapper;
	@Autowired
	private PaySettlementMapper paySettlementMapper;
	@Autowired
	private DgPreOrderbillService dgPreOrderbillService;

	@Autowired
	private PaySettlementService paySettlementService;

	@Autowired
	private APICheckService apiCheckService;

	@Autowired
	private DgUrlSettingMapper dgUrlSettingMapper;
	
	@Autowired
	private DgConsumptionAreaMapper dgConsumptionAreaMapper;
	@Override
	public Map<String, Object> getConsumerSeatInfoByMac(String mac,String code) {
		List<Map<String, Object>> list = dgSeatDoorinfoMapper
				.getConsumerSeatInfoByMac(mac);
		// 在这里判断下 只能唯一值，否则返回空
		if (list != null && list.size() == 1) {
			Map<String, Object> ret = new HashMap<String, Object>();
			DgPos pos = dgPosMapper.selectPosByPosNumber(code);
			ret.put("mac",list.get(0));
			ret.put("pos",pos);
			ret.put("itemFilePicPath",CacheUtil.getURLInCache("DOWN_DATA_URL"));
			return ret;
		} else {
			return null;
		}
		
	}

	@Override
	public void insertOrUpdate(String mac) {
		// TODO Auto-generated method stub
		DgSeatDoorinfo src = new DgSeatDoorinfo();
		src.setMac(mac);
		int count = dgSeatDoorinfoMapper.getCount(src);
		if (count == 0) {
			dgSeatDoorinfoMapper.insert(src);
		}
	}

	@Override
	public  Map<String, Object> openBill(String eatNumber,
			String waiterCode, String seatId, String openPosNumber,
			String cardCode, String ydId,boolean isOnline) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		if(!isOnline && StringUtil.isNotEmpty(cardCode)){
			// 卡片判断
			DgCard card = new DgCard();
			card.setCardnum(cardCode);
			card.setConsumerid(seatId);
			int count = dgCardMapper.countBySeatIdAndCardNum(card);
			if (count == 0) {
				ret.put("error", APIEnumDefine.M0101021);
				return ret;
			}	
		}

		SysUser user = sysUserMapper.selectByUsername(waiterCode);
		Gson gson = new Gson();
		DgPos pos = dgPosMapper.getposByCode(openPosNumber);
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
		DgConsumerSeat seat = new DgConsumerSeat();
		seat.setId(Integer.valueOf(seatId));
		seat = dgConsumerSeatMapper.getDgConsumerSeatByID(seat);
		seat.setSeatState(2);
		seat.setSeatLabel("yxe");
		seat.setLastOpenTime(new Date());
		dgConsumerSeatMapper.updateState(seat); // 更新桌位状态
		Map<String, Object> seatMan = billService.selectSeatConsumeInfo(
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
						+ errCheckItem.get("error"), ret);
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
		water.setWaiter(user.getId());
		water.setOpenPos(pos.getId());
		water.setPrivateRoomType(getBffa(Integer.valueOf(seatId))); // 设置包房费方案
		water.setOwState(1);
		water.setIsTeam(0);
		water.setSeatAmount(1);
		water.setOpenBis(getMealInt());
		if (!StringUtils.isEmpty(ydId)) {
			water.setYdId(Integer.valueOf(ydId));
			DgReserveManager reserveManager = new DgReserveManager();
			reserveManager.setId(Integer.valueOf(ydId));
			reserveManager.setState(1);
			reserveManager.setSeatId(Integer.valueOf(seatId));
			dgReserveManagerMapper.updateManagerState(reserveManager);
		} else {
			DgConsumerSeat seatInfo = dgConsumerSeatMapper
					.selectByPrimaryKey(Integer.valueOf(seatId));
			Map orgs = new HashMap();
			orgs.put("time", simpleDateFormat.format(new Date()));
			orgs.put("seatId", Integer.valueOf(seatId));
			DgReserveManager man = dgReserveManagerMapper
					.selectYdFromSeatId(orgs);
			if (man != null) {
				DgReserveManager reserveManager = new DgReserveManager();
				reserveManager.setId(man.getId());
				reserveManager.setState(-1);
				reserveManager.setSeatId(Integer.valueOf(seatId));
				dgReserveManagerMapper.updateManagerState(reserveManager);
			}
		}
		// 团队流水号
		List<String> teamOrnums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(org.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.TDHM), new TypeToken<List<String>>() {
		}.getType());
		water.setTeamMembers(teamOrnums.get(0));
		water.setTeamMainSeat(Integer.valueOf(seatId));
		billMapper.insertOpenWater(water);

		double subtotal = 0;
		if (!autoIncreaList.isEmpty()) {
			List<String> serviceNums = gson.fromJson(deskBusinessSettingService
					.createFlowNumber(org.getOrgCode(), pos.getNumber(),
							1, SerialRulEnum.FW),
					new TypeToken<List<String>>() {
					}.getType());
			Map serviceMap = new HashMap();
			serviceMap.put("owId", water.getId());
			serviceMap.put("waiterId", user.getId());
			serviceMap.put("serviceTime", new Date());
			serviceMap.put("serviceNum", serviceNums.get(0)); // 新增服务流水号
			serviceMap.put("serviceType", 1);
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
				Map<String, Object> price = billService.getDishPrice(
						item.getItemId(), org.getId(), seat.getConsArea(),
						itemCount, -1, true, false);
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
				// 插入明细表
				dgOwConsumerDetailsMapper.insertSelective(detail);

				// 判断插入是否是套餐
				if (fileItem.getIsTc() != null && fileItem.getIsTc() == 1) {
					insertTc(fileItem.getId(), itemCount,
							Integer.valueOf("" + serviceMap.get("id")), "1");
				}
			}
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

		DgOpenWater retWater = dgOpenWaterMapper.selectByPrimaryKey(water.getId());
		
		ret.put("water",retWater);
		if(StringUtil.isNotEmpty(retWater.getYdId())){
			ret.put("yd",dgReserveManagerMapper.selectByPrimaryKey(retWater.getYdId()));
		}
		
		ret.put("success", true);
		return ret;
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
						if (number > sli.get(0).getFrontSaleCount()) // 超出限制就设置为当前剩余量
						{
							addMapKeyValue("error", itemFile.getName()
									+ "是超过今日限量数量", errList);
							if (tcId == 0) {
								addMapKeyValueSplit("errItemIds",
										itemFile.getId(), errList);
							} else {
								addMapKeyValueListTc("errTc", tcId, errList);
							}
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
						}
					}
				}
			}
		} else {
			addMapKeyValue("error", itemFile.getName() + "是停用品项", errList);
			addMapKeyValueSplit("errItemIds", itemFile.getId(), errList);

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
        return !isErrItemId;
	}

	/*
	 * 获取当前时间下的市别id
	 */
	@Override
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

	Integer getMealId() {
		List<TbBis> tbBiss = tbBisMapper.selectAllBis();
		List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
		if (tbBiss.size() == 0) {
			return -1;
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
		Integer TbisId = null; // 获取市别id
		for (int i = 0; i < timeD.size(); i++) {
			int count = tbBisMapper.calculateSJD(timeD.get(i));
			if (count > 0) {
				TbisId = ((TbBis) timeD.get(i).get("TbBis")).getId();
				break;
			}
		}
		if (TbisId == null) // 没有就是最后个时段
		{
			TbisId = ((TbBis) timeD.get(timeD.size() - 1).get("TbBis"))
					.getId();
		}
		return TbisId;
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

	@Override
	public Map selectOpenWaterBySeatIdLastOne(String seatId) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		Map org = new HashMap();
		org.put("seatId", seatId);
		Map water = apiCheckServiceMapper.selectOpenWaterBySeatIdLastOne(org);
		ret.put("water",water);
		if(water != null && water.containsKey("yd_id") && StringUtil.isNotEmpty(water.get("yd_id"))){
			ret.put("yd",dgReserveManagerMapper.selectByPrimaryKey(Integer.valueOf(water.get("yd_id").toString())));	
		}
		DgConsumerSeat seat = dgConsumerSeatMapper.selectByPrimaryKey(Integer.valueOf(seatId));
//		String code = "http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/icatering/h5/wechat/index?shopids="+
//		CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY")+"&tableid="+seat.getUuidKey()+"&offonlineorderid="+water.get("ow_num")+"&redir_type=6";
		if(water != null) {
			String code = "http://"+CacheUtil.getURLInCache("ONLINE_DOMAIN")+"/icatering/h5/wechat/index?"+"shopids="+
					CacheUtil.getURLInCache("DOWN_DATA_SHOPKEY")+"&tableid="+seat.getUuidKey()+"&offonlineorderid="+water.get("ow_num")+"&redir_type=6";
					ret.put("code",code);	
		}
		return ret;
	}

	@Override
	public List<DgPublicCode0> getAllFw() {
		// TODO Auto-generated method stub
		Map orgs = new HashMap();
		orgs.put("cParent", 258);
		orgs.put("iDelFlg", 0);
		return dgPublicCode0Mapper.selectSmallDpc(orgs);
	}

	@Override
	public List<DgItemTimeLimitPic> selectAllHPic() {
		// TODO Auto-generated method stub
		return dgItemTimeLimitPicMapper.selectAllHPic();
	}

	@Override
	public List<DgItemTimeLimitPic> selectAllZPic() {
		// TODO Auto-generated method stub
		return dgItemTimeLimitPicMapper.selectAllZPic();
	}

	@Override
	public TbOrg getOwnOrg() {
		// TODO Auto-generated method stub
		return tbOrgMapper.getOwnOrg();
	}

	@Override
	public Map<String, Object> openYdBill(String seatId, String number,String cardCode) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		DgConsumerSeat seatInfo = dgConsumerSeatMapper
				.selectByPrimaryKey(Integer.valueOf(seatId));
		if (seatInfo.getSeatState() != 4) {
			ret.put("error", "该客位未处于预定状态");
		} else {
			Map orgs = new HashMap();
			orgs.put("time", simpleDateFormat.format(new Date()));
			orgs.put("seatId", Integer.valueOf(seatId));
			DgReserveManager man = dgReserveManagerMapper
					.selectYdFromSeatId(orgs);
			if (man == null) {
				ret.put("error", "该客位未处于预定状态");
			} else {
//				List<DgReserveSeatids> cIds = dgReserveSeatidsMapper
//						.selectByParentId(man.getId());
//				if (cIds.size() == 1) // 只预定一张客位,开台人数为预定人数
//				{
					ret = openBill(number, "yxe_water", seatId,
							"yxe_pos", cardCode, "" + man.getId(),false);
//				} else { // 预定多张客位,开台人数为客位最大容纳人数
//					ret = openBill(number, "yxe_water",
//							seatId, "yxe_pos", cardCode, "" + man.getId(),false);
//				}
			}
		}
		return ret;
	}

	@Override
	public DgReserveManager getReserveManagerBySeatId(String seatId) {
		// TODO Auto-generated method stub
		Map orgs = new HashMap();
		orgs.put("time", simpleDateFormat.format(new Date()));
		orgs.put("seatId", Integer.valueOf(seatId));
		return dgReserveManagerMapper.selectYdFromSeatId(orgs);
	}

	@Override
	public List<Map<String, Object>> selectAllItemCXsqg(Integer seatId) {
		// TODO Auto-generated method stub
//		DgUrlSetting dgUrlSetting = dgUrlSettingMapper.selectByCode("yxe_qy");
//		if(dgUrlSetting == null || dgUrlSetting.getValue().equals("0")){
//			return billMapper.selectAllItemCXsqg(seatId);
//		} else {
//			dgUrlSetting = dgUrlSettingMapper.selectByCode("yxe_qy_c");
//			if(dgUrlSetting != null && StringUtil.isNotEmpty(dgUrlSetting.getValue())){
//				String[] qyIds = dgUrlSetting.getValue().split(",");
//				//查询seatId 是否在qyIds 里面
//				DgConsumerSeat seat = dgConsumerSeatMapper.selectByPrimaryKey(seatId);
//				boolean find = findIdInStr(seat.getConsArea()+"",qyIds);
//				if(find){
//					//找到,就显示yxe品项
//					return billMapper.selectAllItemCXsqg(seatId);
//				} else {
//					//不在区域,就显示品项小类+yxe = 1的品项
//					dgUrlSetting = dgUrlSettingMapper.selectByCode("yxe_qy_o");
//					if(dgUrlSetting != null || StringUtil.isEmpty(dgUrlSetting.getValue())){
//						List ids = new ArrayList();
//					    Collections.addAll(ids,dgUrlSetting.getValue().split(","));
//					    
//					    Map orgs = new HashMap();
//					    orgs.put("seatId",seatId);
//					    orgs.put("ids",ids);
//					    return billMapper.selectAllItemCXsqgOutOfQy(orgs);
//					} else {
//						return new ArrayList<Map<String,Object>>();
//					}
//				}
//				
//				
//			} else {
//				//显示不受控区域品项
//			}
//		}
		
		return billMapper.selectAllItemCXsqg(seatId);
	}

	@Override
	public Map getYdKtLoopInfo(String seatId) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		Map water = selectOpenWaterBySeatIdLastOne(seatId);
		ret.put("water", water.get("water"));
		if(water.get("water") != null){
			ret.put("code", water.get("code"));
		} else {
			DgConsumerSeat seatInfo = dgConsumerSeatMapper
					.selectByPrimaryKey(Integer.valueOf(seatId));
			ret.put("seat", seatInfo);
			if (seatInfo.getSeatState() == 4) {
				DgReserveManager ydInfo = getReserveManagerBySeatId(seatId);
				ret.put("yd", ydInfo);
			}
		}
		return ret;
	}

	@Override
	public int countBySeatIdAndCardNum(DgCard card) {
		// TODO Auto-generated method stub
		return dgCardMapper.countBySeatIdAndCardNum(card);
	}

	@Override
	public int insertHjFw(String owNum, String content) {
		// TODO Auto-generated method stub
		DgOpenWater dgOpenWater = dgOpenWaterMapper
				.selectByOpenWaterByNum(owNum);
		DgCallService dgCallService = new DgCallService();
		dgCallService.setContent(content);
		dgCallService.setState(1); // 初始化状态
		dgCallService.setSeatId(dgOpenWater.getSeatId());
		dgCallService.setOwNum(owNum);
		dgCallService.setType(1);
		return dgCallServiceMapper.insert(dgCallService);
	}

    @Override
    public List<DgCallService> getCallServiceTop5(String  posID) {
        // TODO Auto-generated method stub
    	DgPos dgPos = dgPosMapper.selectPosByPosId(Integer.valueOf(posID));
		List<Integer> integers = StringUtil.arrayToList(dgPos
				.getConsumerAreas());
		Map org = new HashMap();
		org.put("areaIds",integers);
		org.put("type",1);
        return dgCallServiceMapper.selectTop5(org);
    }

	@Override
	public List<DgCallService> getCallServiceTop5ByPos(DgPos dgPos) {
		List<Integer> integers = StringUtil.arrayToList(dgPos
				.getConsumerAreas());
		Map org = new HashMap();
		org.put("areaIds",integers);
		org.put("type",1);
		return dgCallServiceMapper.selectTop5(org);
	}


	@Override
	public int deleteCallServiceByIds(String s) {
		// TODO Auto-generated method stub
		List ids = new ArrayList();
		Collections.addAll(ids, s.split(","));
		return dgCallServiceMapper.deleteIds(ids);
	}

    @Override
    public void modifyYxePaySuccessState(List<DgOpenWater> openWaterData, String paidMoney, String zeroMoney, String payType, String time, Integer priceType) {
		SysUser sysUser = sysUserMapper.selectByUsername("yxe_water");

		String openWaterNumber = openWaterData.get(0).getOwNum();

		DgOpenWater openWater = apiCheckServiceMapper.selectOpenWaterByowNum(openWaterNumber);

		if(openWater.getClearingWaterId() == null){
			DgPos dgPos = dgPosMapper.selectPosByPosNumber("yxe_pos");
			TbOrg org = new TbOrg();
			org.setId(Integer.parseInt(sysUser.getEmpOrganization()));
			org = tbOrgMapper.getOrgByID(org);
			//结算流水插入
			Gson gson = new Gson();
			List<String> jsList = gson.fromJson(deskBusinessSettingService
							.createFlowNumber(org.getOrgCode(), dgPos.getNumber(), 1,
									SerialRulEnum.JS),
					new TypeToken<List<String>>() {
					}.getType());
			String jsNum = jsList.get(0);
			//插入结算流水并获取插入流水的ID
			Map<String, Object> clearingParam = new HashMap<>();
			clearingParam.put("cwNum", jsNum);
			clearingParam.put("conAmount", paidMoney);
			clearingParam.put("zeroAmount", zeroMoney);
			clearingParam.put("fixedDiscount", null);
			clearingParam.put("amountsReceivable", paidMoney);
			clearingParam.put("paidAmount", paidMoney);
			clearingParam.put("changeAmount", null);
			clearingParam.put("clearingBis", getMealId());
			clearingParam.put("clearingOperator", "yxe_water");
			clearingParam.put("clearingPos", "yxe_pos");
			clearingParam.put("clearingState", 2);
			clearingParam.put("clearingTime", new Date());
			clearingParam.put("zeroSettlement", null);
			clearingParam.put("clearingNotes", null);
			clearingParam.put("statementLabel", null);
			clearingParam.put("generalProportions", null);
			clearingParam.put("singleProportions", null);
			clearingParam.put("hyzfId", null);
			BigDecimal beforeDiscountsAmount = new BigDecimal(0.0);
			for(DgOpenWater dow:openWaterData){
				beforeDiscountsAmount = beforeDiscountsAmount.add(new BigDecimal(dow.getItemCostsSum()));
			}
			clearingParam.put("beforeDiscountsAmount",beforeDiscountsAmount.doubleValue());
			clearingParam.put("totalDiscountAmount",beforeDiscountsAmount.subtract(new BigDecimal(paidMoney)).doubleValue());
			paySettlementMapper.insertClearingWater(clearingParam);
			Integer clearingId = Integer.parseInt(clearingParam.get("id").toString());
			//营业流水数据状态修改
			for(DgOpenWater dgOpenWater:openWaterData){
				Map<String,Object> openWaterMap = new HashMap<>();
				openWaterMap.put("owNum", dgOpenWater.getOwNum());
				openWaterMap.put("cwId", clearingId);
				openWaterMap.put("finalItemCostSum",dgOpenWater.getItemCostsSum());
				openWaterMap.put("openWater", dgOpenWater);
				openWaterMap.put("owState", 2);
				paySettlementMapper.modifyOpenWaterAdvancePayInfo(openWaterMap);

				//修改品项的信息
				List<DgOwConsumerDetails> dgOwConsumerDetailss = dgOpenWater.getItemFileInfos();
				for(DgOwConsumerDetails details:dgOwConsumerDetailss){
					paySettlementService.modifyItemFile(details,priceType,dgOpenWater.getId(),null,null);
				}

				List<Map> maps = paySettlementMapper.selectSeatOpenWater(dgOpenWater.getSeatId());
				if (maps.size() < 1) {
					if(dgOpenWater.getTransferTarget() == null){
						paySettlementService.modifySeatState(dgOpenWater.getSeatId(),1);
					} else {
						DgOpenWater transWater = dgOpenWaterMapper.selectByOpenWaterByNum(dgOpenWater.getTransferTarget()) ;
						if(transWater.getSeatId() == dgOpenWater.getSeatId()) {
							paySettlementService.modifySeatState(dgOpenWater.getSeatId(),1);
						}
					}
				}

				//客座状态修改
				Map<String,Object> seatMap = new HashMap<>();
				DgConsumerSeat dgConsumerSeat = dgConsumerSeatMapper.selectByPrimaryKey(dgOpenWater.getSeatId());
				if(dgConsumerSeat.getSeatState() != 1 && dgConsumerSeat.getSeatState() != 3) {
					seatMap.put("id", dgOpenWater.getSeatId());
					if (dgConsumerSeat.getDefaultState() == 1) {
						seatMap.put("state", 1);
						OnlineHttp.onlineSeatModify(dgConsumerSeat.getUuidKey(), "1");
					} else {
						seatMap.put("state", 3);
						OnlineHttp.onlineSeatModify(dgConsumerSeat.getUuidKey(), "3");
					}
					paySettlementMapper.modifySeatState(seatMap);
					
		            if(CacheUtil.getURLInCache("ONLINE_ORDER") == null || !"1".equals(CacheUtil.getURLInCache("ONLINE_ORDER"))){
		    			//插入清理缓存 数据
		    			DgCallService dgCallService = new DgCallService();
		    			dgCallService.setContent("清理缓存数据");
		    			dgCallService.setState(1); // 初始化状态
		    			dgCallService.setSeatId(dgConsumerSeat.getId());
		    			dgCallService.setOwNum(dgConsumerSeat.getUuidKey()); //这里存入客位uuid方便上传
		    			dgCallService.setType(4);
		    			dgCallServiceMapper.insert(dgCallService);
		            }
				}
			}

			//支付方式插入
			Map<String, Object> clearingWaymap = new HashMap();
			clearingWaymap.put("cwId", clearingId);
			clearingWaymap.put("settlementAmount", paidMoney);
			clearingWaymap.put("conversionAmount", paidMoney);
			clearingWaymap.put("cwCode", payType.equalsIgnoreCase("wx")?"MTCHAT":"ALIPAY");
			clearingWaymap.put("cwTime", time);
			paySettlementMapper.insertPayWay(clearingWaymap);

			//插入支付成功日志
			DgCallService dgCallService = new DgCallService();
			dgCallService.setContent("易小二支付成功,结算流水:"+jsNum+"，序号为:"+clearingId);
			dgCallService.setState(1); // 初始化状态
			dgCallService.setSeatId(openWater.getSeatId());
			dgCallService.setOwNum(openWaterNumber);
			dgCallService.setType(2);
			dgCallServiceMapper.insert(dgCallService);

			dgPreOrderbillService.deleteByWaterId(openWater.getId());
		}
    }

	@Override
	public void setSeatFree(String seatId) {
		// TODO Auto-generated method stub
		 dgConsumerSeatMapper.modifySeatSeat(Integer.valueOf(seatId));
	}

	@Override
	public List<DgCallService> selectTopYxeZf5(DgPos pso) {
		// TODO Auto-generated method stub
		List<Integer> integers = StringUtil.arrayToList(pso
				.getConsumerAreas());
		Map org = new HashMap();
		org.put("areaIds",integers);
		org.put("type",2);
		return dgCallServiceMapper.selectTop5(org);
	}

	@Override
	public List<DgCallService> selectTopZccf5() {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectTopZccf5();
	}

	@Override
	public List<DgPreOrderbill> selectAllPreOrderBill(String waterId) {
		// TODO Auto-generated method stub
		return dgPreOrderbillService.selectByWaterId(Integer.valueOf(waterId));
	}

	@Override
	public DgCallService getDgCallServiceById(Integer id) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map checkCard(String seatId, String cardNo) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		ret.put("user",billMapper.selectUserByCardNo(cardNo));
		ret.put("seat",billMapper.selectSeatByCard(cardNo,seatId));
		return ret;
	}

	@Override
	public Map addBill(List<Map<String,Object>> list, String openNumber) {
		// TODO Auto-generated method stub
		
		Map resMap = new HashMap();
		List<Integer> gList = billMapper.selectGdFromItemIds(list);
		List<Map<String,Object>> gmList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> qmList = new ArrayList<Map<String,Object>>();
		for(Map l:list){
			boolean isGItem = false;
			for(int i:gList){
				if((int)l.get("itemFileId") == i){
					gmList.add(l);
					isGItem = true;
					break;
				}
			}
			if(!isGItem){
				qmList.add(l);
			}
		}
		
		if(!gmList.isEmpty()){
			resMap = billService.addBill(null, gmList, openNumber, "1", "",true);	
			if(resMap.containsKey("error")){
				return resMap;
			}
		} 
		
		if(!qmList.isEmpty()){
			resMap = billService.addYxePreBill(qmList,openNumber);
		}
		return resMap;
	}
	
	
	boolean findIdInStr(String id,String[] ids){
		for(String i:ids){
			if(i.equals(id)){
				return true;
			}
		}
		return false;
	}

	@Override
	public int selectReserveCount() {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectReserveCount();
	}

	@Override
	public String modifyPosPaySuccessState(List<DgOpenWater> openWaterData,
			List<DgOwClearingway> docws, String paidMoney, String zeroMoney,
			String time, Integer priceType) {
		// TODO Auto-generated method stub
		SysUser sysUser = sysUserMapper.selectByUsername("yxe_water");

		String openWaterNumber = openWaterData.get(0).getOwNum();

		DgOpenWater openWater = apiCheckServiceMapper.selectOpenWaterByowNum(openWaterNumber);

		if(openWater.getClearingWaterId() == null){
			DgPos dgPos = dgPosMapper.selectPosByPosNumber("yxe_pos");
			TbOrg org = new TbOrg();
			org.setId(Integer.parseInt(sysUser.getEmpOrganization()));
			org = tbOrgMapper.getOrgByID(org);
			//结算流水插入
			Gson gson = new Gson();
			List<String> jsList = gson.fromJson(deskBusinessSettingService
							.createFlowNumber(org.getOrgCode(), dgPos.getNumber(), 1,
									SerialRulEnum.JS),
					new TypeToken<List<String>>() {
					}.getType());
			String jsNum = jsList.get(0);
			//插入结算流水并获取插入流水的ID
			Map<String, Object> clearingParam = new HashMap<>();
			clearingParam.put("cwNum", jsNum);
			clearingParam.put("conAmount", paidMoney);
			clearingParam.put("zeroAmount", zeroMoney);
			clearingParam.put("fixedDiscount", 0);
			clearingParam.put("amountsReceivable", paidMoney);
			clearingParam.put("paidAmount", paidMoney);
			clearingParam.put("changeAmount", 0);
			clearingParam.put("clearingBis", getMealId());
			clearingParam.put("clearingOperator", "yxe_water");
			clearingParam.put("clearingPos", "yxe_pos");
			clearingParam.put("clearingState", 2);
			clearingParam.put("clearingTime", new Date());
			clearingParam.put("zeroSettlement", 1);
			clearingParam.put("clearingNotes", null);
			clearingParam.put("statementLabel", null);
			clearingParam.put("generalProportions", null);
			clearingParam.put("singleProportions", null);
			clearingParam.put("hyzfId", null);
			paySettlementMapper.insertClearingWater(clearingParam);
			Integer clearingId = Integer.parseInt(clearingParam.get("id").toString());
			//营业流水数据状态修改
			for(DgOpenWater dgOpenWater:openWaterData){
				Map<String,Object> openWaterMap = new HashMap<>();
				openWaterMap.put("owNum", dgOpenWater.getOwNum());
				openWaterMap.put("cwId", clearingId);
				openWaterMap.put("finalItemCostSum",dgOpenWater.getItemCostsSum());
				openWaterMap.put("openWater", dgOpenWater);
				openWaterMap.put("owState", 2);
				paySettlementMapper.modifyOpenWaterAdvancePayInfo(openWaterMap);

				//修改品项的信息
				List<DgOwConsumerDetails> dgOwConsumerDetailss = dgOpenWater.getItemFileInfos();
				for(DgOwConsumerDetails details:dgOwConsumerDetailss){
					paySettlementService.modifyItemFile(details,priceType,dgOpenWater.getId(),null,null);
				}

				List<Map> maps = paySettlementMapper.selectSeatOpenWater(dgOpenWater.getSeatId());
				if (maps.size() < 1) {
					if(dgOpenWater.getTransferTarget() == null){
						paySettlementService.modifySeatState(dgOpenWater.getSeatId(),1);
					} else {
						DgOpenWater transWater = dgOpenWaterMapper.selectByOpenWaterByNum(dgOpenWater.getTransferTarget()) ;
						if(transWater.getSeatId() == dgOpenWater.getSeatId()) {
							paySettlementService.modifySeatState(dgOpenWater.getSeatId(),1);
						}
					}
				}

				//客座状态修改
				Map<String,Object> seatMap = new HashMap<>();
				DgConsumerSeat dgConsumerSeat = dgConsumerSeatMapper.selectByPrimaryKey(dgOpenWater.getSeatId());
				if(dgConsumerSeat.getSeatState() != 1 && dgConsumerSeat.getSeatState() != 3) {
					seatMap.put("id", dgOpenWater.getSeatId());
					if (dgConsumerSeat.getDefaultState() == 1) {
						seatMap.put("state", 1);
						OnlineHttp.onlineSeatModify(dgConsumerSeat.getUuidKey(), "1");
					} else {
						seatMap.put("state", 3);
						OnlineHttp.onlineSeatModify(dgConsumerSeat.getUuidKey(), "3");
					}
					paySettlementMapper.modifySeatState(seatMap);
					
		            if(CacheUtil.getURLInCache("ONLINE_ORDER") == null || !"1".equals(CacheUtil.getURLInCache("ONLINE_ORDER"))){
		    			//插入清理缓存 数据
		    			DgCallService dgCallService = new DgCallService();
		    			dgCallService.setContent("清理缓存数据");
		    			dgCallService.setState(1); // 初始化状态
		    			dgCallService.setSeatId(dgConsumerSeat.getId());
		    			dgCallService.setOwNum(dgConsumerSeat.getUuidKey()); //这里存入客位uuid方便上传
		    			dgCallService.setType(4);
		    			dgCallServiceMapper.insert(dgCallService);
		            }
				}
			}

			
			//支付方式插入
			for(DgOwClearingway doc:docws){
				Map<String, Object> clearingWaymap = new HashMap();
				clearingWaymap.put("cwId", clearingId);
				clearingWaymap.put("settlementAmount", doc.getSettlementAmount());
				clearingWaymap.put("conversionAmount", doc.getConversionAmount());
				clearingWaymap.put("cwCode", doc.getCwCode());
				clearingWaymap.put("cwTime", time);
				paySettlementMapper.insertPayWay(clearingWaymap);	
			}

//插入支付成功日志
//			DgCallService dgCallService = new DgCallService();
//			dgCallService.setContent("易小二支付成功,结算流水:"+jsNum+"，序号为:"+clearingId);
//			dgCallService.setState(1); // 初始化状态
//			dgCallService.setSeatId(openWater.getSeatId());
//			dgCallService.setOwNum(openWaterNumber);
//			dgCallService.setType(2);
//			dgCallServiceMapper.insert(dgCallService);

			//删除预点单
			dgPreOrderbillService.deleteByWaterId(openWater.getId());
			return jsNum;
		}
		return "";
	}

}