package com.yqsh.diningsys.web.service.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.api.APIReMoMapper;
import com.yqsh.diningsys.web.dao.api.BillMapper;
import com.yqsh.diningsys.web.dao.api.SysBusinessLogMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFileMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFileTypeMapper;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.dao.archive.TbBisMapper;
import com.yqsh.diningsys.web.dao.archive.TbOrgMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatChargingSchemeMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatChargingSchemeSMapper;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatManagerMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemOutofStockMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwConsumerDetailsMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwModifySeatMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.api.SysBusinessLog;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwModifySeat;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.service.api.APIModifyService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatChargingSchemeService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemOutofStockService;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;
import com.yqsh.diningsys.web.sevlet.CacheInit;
import com.yqsh.diningsys.web.util.OnlineHttp;

@Service
public class APIModifyServiceImpl implements APIModifyService {
	private static SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
	private static SimpleDateFormat minFormat = new SimpleDateFormat("mm");
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat dateformat = new SimpleDateFormat(
			"yyyy-MM-dd");
	@Resource
	private BillMapper billMapper;
	@Resource
	private BillService billService;
	@Resource
	private DgOpenWaterMapper dgOpenWaterMapper;
	@Resource
	private DgOwModifySeatMapper dgOwModifySeatMapper;
	@Resource
	private DgSeatManagerMapper dgSeatManagerMapper;
	@Resource
	private DgSeatChargingSchemeMapper dgSeatChargingSchemeMapper;
	@Resource
	private DgSeatChargingSchemeSMapper dgSeatChargingSchemeSMapper;
	@Resource
	private DgOwConsumerDetailsMapper dgOwConsumerDetailsMapper;
	@Resource
	private APICheckServiceMapper aPICheckServiceMapper;
	@Resource
	private DgConsumerSeatMapper dgConsumerSeatMapper;
	@Resource
	private DgItemFileMapper dgItemFileMapper;
	@Autowired
	private DgItemOutofStockMapper dgItemOutofStockMapper;
	@Autowired
	private TbBisMapper tbBisMapper;
	@Autowired
	private DgItemFileTypeMapper dgItemFileTypeMapper;
	@Autowired
	private APIReMoMapper apiReMoMapper;
	@Autowired
	private DgItemSaleLimitMapper dgItemSaleLimitMapper;
	@Autowired
	private DgItemSaleLimitSMapper dgItemSaleLimitSMapper;
	@Autowired
	private DgPrintDataService dgPrintDataService;
	@Autowired
	private DgPosMapper dgPosMapper;
	@Autowired
	private TbOrgMapper tbOrgMapper;
	@Autowired
	private DeskBusinessSettingService deskBusinessSettingService;
	@Autowired
	private SysBusinessLogMapper sysBusinessLogMapper;
	@Autowired
	private SysUserMapper sysUserMapper;
	@Override
	public void modifySeat(String userCode,DgOpenWater ow, Integer waterId, Integer seatId,
			Integer isGgFa, Integer isJsBffPx) {
		// TODO Auto-generated method stub

		Map param = new HashMap();
		param.put("transferNum",ow.getOwNum());

		param.put("teamNum",ow.getTeamMembers());
		param.put("seatId",ow.getSeatId());

		
		
		List<DgOpenWater> dgOpenWaters = aPICheckServiceMapper.selectOpenWaterByTeamNum(param);

		//该流水为团队的流水，进行更换客位操作
		if(dgOpenWaters != null && dgOpenWaters.size() > 0){
			for(DgOpenWater dgOpenWater :dgOpenWaters){
				param.put("seatId",seatId);
				param.put("openWater",dgOpenWater.getOwNum());
				if(dgOpenWater.getSeatId().equals(ow.getSeatId())){ //本流水或者转账流水
					if(dgOpenWater.getTeamMainSeat().equals(dgOpenWater.getSeatId())){
						param.put("teamMainSeat",seatId);
					}else{
						param.put("teamMainSeat",dgOpenWater.getTeamMainSeat());
					}
					ow.setTeamMainSeat((Integer)param.get("teamMainSeat"));
					aPICheckServiceMapper.modifyOpenWaterSeat(param);
					
				} else { //团队流水
					if(!dgOpenWater.getTeamMainSeat().equals(dgOpenWater.getSeatId())){
						param.put("teamMainSeat",seatId);
					}else{
						param.put("teamMainSeat",dgOpenWater.getTeamMainSeat());
					}
					aPICheckServiceMapper.modifyOpenWaterTeamMainSeat(param);
				}
				
			}
			
		}
		
		
		//插入日志
		SysUser user= sysUserMapper.chekUserCode(userCode);
		DgConsumerSeat oldSeat = dgConsumerSeatMapper.getConsumerSeatById(ow.getSeatId()); //原来客位
		DgConsumerSeat nowSeat = dgConsumerSeatMapper.getConsumerSeatById(seatId); //现在客位
		sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 1, ow.getOwNum(), 
				oldSeat.getName(), "操作客位:"+oldSeat.getName()+",转台后客位:"+nowSeat.getName(),new SimpleDateFormat("yyyy_MM").format(new Date())));	
		
		

		String startTime = ow.getFirstTime();
		DgOwModifySeat oldModifys = dgOwModifySeatMapper.selectByOwNum(ow
				.getOwNum());
		if (oldModifys != null) {
			startTime = oldModifys.getTime();
		}
		int bffType = -1;
		int oldSeatId = ow.getSeatId();
		if (oldModifys == null) {
			DgOwModifySeat dgOwModifySeat = new DgOwModifySeat();
			dgOwModifySeat.setOrgSeatId(ow.getSeatId());
			dgOwModifySeat.setNowSeatId(seatId);
			dgOwModifySeat.setOwNum(ow.getOwNum());
			dgOwModifySeat.setIsGgbff(isGgFa);
			dgOwModifySeat.setIsJsbffpx(isJsBffPx);
			Map<String, Object> seatManager = selectSeatConsumeByOpenId(ow,
					ow.getSeatId(), startTime);
			dgOwModifySeat.setTime(new Date());
			dgOwModifySeatMapper.insertSelective(dgOwModifySeat);
			bffType = seatManager.containsKey("bffType")? (Integer)seatManager.get("bffType"):0;
			// 生成包房费品项
			if (isJsBffPx == 1 && (bffType == 4 || bffType == 5)
					&& (double)seatManager.get("bff") > 0) {
				buildBffPx(ow, seatId, waterId, (double)seatManager.get("bff"),isGgFa,(Integer)seatManager.get("bffpx"));
			} else {
				updateWaterSeat(ow, seatId, waterId,(double)seatManager.get("bff"), isGgFa);
			}
		} else {
			oldModifys.setOrgSeatId(ow.getSeatId());
			oldModifys.setNowSeatId(seatId);
			oldModifys.setOwNum(ow.getOwNum());
			oldModifys.setIsGgbff(isGgFa);
			oldModifys.setIsJsbffpx(isJsBffPx);
			Map<String, Object> seatManager = selectSeatConsumeByOpenId(ow,
					ow.getSeatId(), startTime);
			oldModifys.setTime(new Date());
			dgOwModifySeatMapper.updateByPrimaryKeySelective(oldModifys);
			bffType = seatManager.containsKey("bffType")? (Integer)seatManager.get("bffType"):0;
			// 生成包房费品项
			if (isJsBffPx == 1 && (bffType == 4 || bffType == 5)
					&& (double)seatManager.get("bff") > 0) {
				buildBffPx(ow, seatId, waterId,(double)seatManager.get("bff"),isGgFa,(Integer)seatManager.get("bffpx"));
			} else {
				updateWaterSeat(ow, seatId, waterId, (double)seatManager.get("bff"),isGgFa);
			}
		}
			
		//加入打印
		dgPrintDataService.insertChangeTable(waterId, ow.getOwNum(), seatId,oldSeatId, isGgFa, isJsBffPx);
	}

	private Map<String, Object> selectSeatConsumeByOpenId(
			DgOpenWater openWater, int seatId, String orgStartTime) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		if (openWater != null) {
			DgSeatManager seatMan = dgSeatManagerMapper.selectBySeatId(openWater.getSeatId());
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

				int bff = seatMan.getBff();
				double xfm = openWater.getItemCostsSum();
				// 消费满多少，不收取包房费
				if (bff != 1 && xfm >= seatMan.getBffConFree()) {
					ret.put("bff", 0.00);
				} else {
					ret.put("bffType", bff);
					if (bff == 1) {
						ret.put("bff", 0.0);
					} else if (bff == 2) {
						ret.put("bff", 0.0);
					} else if (bff == 3) // 按消费比例收取(开始设置为0)
					{
						ret.put("bff", 0.0);
					} else if (bff == 4) // 固定包房收费方案(按消费时间断来,暂时不能计算,结算时计算)
					{
						int gdBffPro = seatMan.getBffGdPro();
						try {
							ret.put("bff",
									calculationBff(gdBffPro, simpleDateFormat
											.parse(orgStartTime), new Date()));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
						ret.put("bffpx", seatMan.getBffItemId());

					} else if (bff == 5) // 一周内设置不同的收费方案
					{
						String dgBffWeek = seatMan.getBffWeekProD();
						if (dgBffWeek.length() < 10) {
							ret.put("bff", 0.00);
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
							try {
								ret.put("bff",
										calculationBff(dgBffWeekId,
												simpleDateFormat
														.parse(orgStartTime),
												new Date()));
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}
						ret.put("bffpx", seatMan.getBffItemId());
					}
				}

				double xfh = openWater.getItemCostsSum()
						+ (double) ret.get("bff");
				int fwf = seatMan.getFwf();
				// 消费满多少，不收取包房费
				if (fwf != 1 && xfh >= seatMan.getFwfConFree()) {
					ret.put("fwf", 0.00);
				} else {
					// 服务费
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
		return ret;
	}

	@Override
	public double calculationBff(int faId, Date startTime, Date endTime) {
		if (faId != 0) {
			DgSeatChargingScheme dscs = dgSeatChargingSchemeMapper
					.selectByPrimaryKey(faId);
			if (dscs != null) {
				// 时段收费(按分钟)
				if (dscs.getType() == 1) {
					if (DateUtil.dateIntervalMin(startTime, endTime) <= dscs
							.gettLongLowLim()) {
						return 0.0;
					} else {
						double total = 0;
						long day = DateUtil.dateIntervalDay(startTime, endTime);
						List<DgSeatChargingSchemeS> dcss = dgSeatChargingSchemeSMapper
								.seleByPid(dscs.getId());
						String startTimehour = hourFormat.format(startTime);
						String startTimeMin = minFormat.format(startTime);
						String endTimehour = hourFormat.format(endTime);
						String endTimeMin = minFormat.format(endTime);
						int startIndex = 0;
						int endIndex = 0;
						double tMoney = 0;
						for (int i = 0; i < dcss.size(); i++) {
							DgSeatChargingSchemeS s = dcss.get(i);
							if (startTimehour.equals(s.getSdName().substring(0,
									2))) {
								startIndex = i;
							}
							if (endTimehour.equals(s.getSdName()
									.substring(0, 2))) {
								endIndex = i;
							}
							if (s.getMoney() != null) {
								tMoney = MathExtend.add(tMoney,
										MathExtend.multiply(s.getMoney(), 60));
							}
						}
						total = MathExtend.add(total,
								MathExtend.multiply(day, tMoney));
						int startMin = 0;
						if (!startTimeMin.replaceFirst("^0*", "").equals("")) {
							startMin = Integer.valueOf(startTimeMin
									.replaceFirst("^0*", ""));
						}
						int endMin = 0;
						if (!endTimeMin.replaceFirst("^0*", "").equals("")) {
							endMin = Integer.valueOf(endTimeMin.replaceFirst(
									"^0*", ""));
						}
						if (startIndex == endIndex) {
							if (startMin > endMin) {
								if (dcss.get(startIndex).getMoney() != null) {
									total = MathExtend.add(total, MathExtend
											.multiply(dcss.get(startIndex)
													.getMoney(), startMin
													- endMin));
								}
								for (int i = 0; i < dcss.size(); i++) {
									if (startIndex != i) {
										DgSeatChargingSchemeS s = dcss.get(i);
										if (s.getMoney() != null) {
											total = MathExtend.add(
													total,
													MathExtend.multiply(
															s.getMoney(), 60));
										}
									}
								}
							} else if (startMin <= endMin
									&& dcss.get(startIndex).getMoney() != null) {

								total = MathExtend
										.add(total, MathExtend.multiply(dcss
												.get(startIndex).getMoney(),
												endMin - startMin));
							}
						} else if (startIndex > endIndex) {
							if (dcss.get(startIndex).getMoney() != null) {
								total = MathExtend.add(total, MathExtend
										.multiply(dcss.get(startIndex)
												.getMoney(), 60 - startMin));
							}
							if (dcss.get(endIndex).getMoney() != null) {
								total = MathExtend.add(total, MathExtend
										.multiply(dcss.get(endIndex)
												.getMoney(), endMin));
							}
							// 加上中间时段
							for (int i = startIndex + 1; i < dcss.size(); i++) {
								if (dcss.get(i).getMoney() != null) {
									total = MathExtend.add(total, MathExtend
											.multiply(dcss.get(i).getMoney(),
													60));
								}
							}
							// 加上中间时段
							for (int i = 0; i < endIndex; i++) {
								if (dcss.get(i).getMoney() != null) {
									total = MathExtend.add(total, MathExtend
											.multiply(dcss.get(i).getMoney(),
													60));
								}
							}
						} else if (startIndex < endIndex) {
							if (dcss.get(startIndex).getMoney() != null) {
								total = MathExtend.add(total, MathExtend
										.multiply(dcss.get(startIndex)
												.getMoney(), 60 - startMin));
							}
							if (dcss.get(endIndex).getMoney() != null) {
								total = MathExtend.add(total, MathExtend
										.multiply(dcss.get(endIndex)
												.getMoney(), endMin));
							}
							// 加上中间时段
							for (int i = startIndex + 1; i < endIndex; i++) {
								if (dcss.get(i).getMoney() != null) {
									total = MathExtend.add(total, MathExtend
											.multiply(dcss.get(i).getMoney(),
													60));
								}
							}
						}
						if (dscs.getAmountUpLim() != 0) {
							if (total > dscs.getAmountUpLim()) {
								total = dscs.getAmountUpLim();
							}
						}
						return total;
					}
				}
				// 时段阶梯收费
				else if (dscs.getType() == 2) {
					double total = 0;
					Long sc = DateUtil.dateIntervalMin(startTime, endTime);
					if (sc <= dscs.gettLongLowLim()) {
						return 0.0;
					} else {
						List<Map> resList = new ArrayList<Map>();
						List<DgSeatChargingSchemeS> dcss = dgSeatChargingSchemeSMapper
								.seleByPid(dscs.getId());
						for (DgSeatChargingSchemeS dsc : dcss) {
							if (dsc.gettLong() != null
									&& dsc.getMoney() != null) {
								if (sc >= dsc.gettLong()) {
									Map m = new HashMap();
									m.put("money", dsc.getMoney());
									m.put("inter", sc - dsc.gettLong());
									resList.add(m);
								}
							}
						}
						if (!resList.isEmpty()) {
							Long minInter = (Long) resList.get(0).get("inter");
							total = (double) resList.get(0).get("money");
							for (Map m : resList) {
								if (minInter > (Long) m.get("inter")) {
									minInter = (Long) m.get("inter");
									total = (double) m.get("money");
								}
							}
						}
						return total;
					}
				}
				// 时长收费
				else if (dscs.getType() == 3) {
					double total = 0;
					Long sc = DateUtil.dateIntervalMin(startTime, endTime);
					if (sc <= dscs.gettLongLowLim()) {
						return 0.0;
					} else {
						List<Map> resList = new ArrayList<Map>();
						List<DgSeatChargingSchemeS> dcss = dgSeatChargingSchemeSMapper
								.seleByPid(dscs.getId());
						for (DgSeatChargingSchemeS dsc : dcss) {
							if (dsc.gettLong() != null
									&& dsc.getMoney() != null) {
								if (sc > dsc.gettLong()) {
									Map m = new HashMap();
									m.put("money", dsc.getMoney());
									m.put("inter", sc - dsc.gettLong());
									resList.add(m);
								}
							}
						}
						if (!resList.isEmpty()) {
							Long minInter = (Long) resList.get(0).get("inter");
							total = (double) resList.get(0).get("money");
							for (Map m : resList) {
								if (minInter > (Long) m.get("inter")) {
									minInter = (Long) m.get("inter");
									total = (double) m.get("money");
								}
							}
						}
						return total;
					}
				}
				// 时段收费(按小时)
				else if (dscs.getType() == 4) {
					if (DateUtil.dateIntervalMin(startTime, endTime) <= dscs
							.gettLongLowLim()) {
						return 0.0;
					} else {
						double total = 0;
						long day = DateUtil.dateIntervalDay(startTime, endTime);
						List<DgSeatChargingSchemeS> dcss = dgSeatChargingSchemeSMapper
								.seleByPid(dscs.getId());
						String startTimehour = hourFormat.format(startTime);
						String startTimeMin = minFormat.format(startTime);
						String endTimehour = hourFormat.format(endTime);
						String endTimeMin = minFormat.format(endTime);
						int startIndex = 0;
						int endIndex = 0;
						double tMoney = 0;
						for (int i = 0; i < dcss.size(); i++) {
							DgSeatChargingSchemeS s = dcss.get(i);
							if (startTimehour.equals(s.getSdName().substring(0,
									2))) {
								startIndex = i;
							}
							if (endTimehour.equals(s.getSdName()
									.substring(0, 2))) {
								endIndex = i;
							}
							if (s.getMoney() != null) {
								tMoney = MathExtend.add(tMoney, s.getMoney());
							}
						}
						total = MathExtend.add(total,
								MathExtend.multiply(day, tMoney));
						if (startIndex == endIndex) {
							total = MathExtend.add(total, dcss.get(startIndex)
									.getMoney());
						} else if (startIndex > endIndex) {
							// 加上中间时段
							for (int i = startIndex; i < dcss.size(); i++) {
								total = MathExtend.add(total, dcss.get(i)
										.getMoney());
							}
							// 加上中间时段
							for (int i = 0; i <= endIndex; i++) {
								total = MathExtend.add(total, dcss.get(i)
										.getMoney());
							}
						} else if (startIndex < endIndex) {
							// 加上中间时段
							for (int i = startIndex; i <= endIndex; i++) {
								if (dcss.get(i).getMoney() != null) {
									total = MathExtend.add(total, dcss.get(i)
											.getMoney());
								}
							}
						}
						if (dscs.getAmountUpLim() != 0) {
							if (total > dscs.getAmountUpLim()) {
								total = dscs.getAmountUpLim();
							}
						}
						return total;
					}
				}
			} else {
				return 0.0;
			}
		} else {
			return 0.0;
		}
		return 0.0;
	}

	// 生成包房费品项
	private void buildBffPx(DgOpenWater ow, Integer seatId, Integer waiterId,
			double price, Integer isGgFa,Integer bffpx) {
		
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
		
		DgItemFile itemFile = dgItemFileMapper.selectByPrimaryKey(bffpx);
		int orgSeatId = ow.getSeatId();
		// 插入服务流水
		Map serviceMap = new HashMap();
		serviceMap.put("owId", ow.getId());
		serviceMap.put("waiterId", waiterId);
		serviceMap.put("serviceTime", new Date());
		serviceMap.put("serviceNum", serviceNums.get(0));
		serviceMap.put("serviceType", 7); // 品项
		billMapper.insertOwServiceWater(serviceMap); // 插入服务流水
		// 更新桌位状态
		DgConsumerSeat newSeat = new DgConsumerSeat();
		newSeat.setId(seatId);
		newSeat.setSeatState(2);
		dgConsumerSeatMapper.updateState(newSeat);
		// 插入包房费品项
		DgOwConsumerDetails cd = new DgOwConsumerDetails();
		cd.setItemFileId(itemFile.getId());
		cd.setItemFileNumber(1.0);
		cd.setNotes("7");// 标记为包房费品项
		cd.setIsTc(0);
		cd.setOwId(Integer.valueOf("" + serviceMap.get("id")));
		cd.setSubtotal(price);
		cd.setItemFinalPrice(price);

		dgOwConsumerDetailsMapper.insertSelective(cd);
		// 最后刷新营业流水品项和 小计
		Map<String, Object> seatMan = billService.selectSeatConsumeInfo(seatId,""+ow.getPeopleCount());
		double oldfwf = ow.getServiceCharge();
		double subtotal = MathExtend.subtract(ow.getSubtotal(),oldfwf);
		double oldbff = ow.getPrivateRoomCost();
		subtotal = MathExtend.subtract(subtotal,oldbff);
		subtotal = MathExtend.add(subtotal, (double) seatMan.get("bff"));
		subtotal = MathExtend.add(subtotal, (double) seatMan.get("fwf"));
		subtotal = MathExtend.add(subtotal, price);
	    
		ow.setServiceCharge((double) seatMan.get("fwf"));
		ow.setSeatId(seatId);
		ow.setItemCostsSum(MathExtend.add(ow.getItemCostsSum(), price));
		ow.setPrivateRoomCost((double) seatMan.get("bff"));
		ow.setSubtotal(subtotal);
		if (isGgFa == 1) {
			ow.setPrivateRoomType(getBffa(seatId));
		}
		billMapper.updateOpenWaterPrimaryKey(ow);

		Map param = new HashedMap();
		param.put("seatId", orgSeatId);
		List<Map> orgSeat = aPICheckServiceMapper
				.selectOpenWaterBySeatId(param);
		if (orgSeat.size() == 0) {
			DgConsumerSeat seat = new DgConsumerSeat();
			seat.setId(orgSeatId);
			seat.setSeatState(1);
			dgConsumerSeatMapper.updateState(seat);
			DgConsumerSeat oldSeat = dgConsumerSeatMapper
					.selectByPrimaryKey(orgSeatId);
			OnlineHttp.onlineSeatModify(oldSeat.getUuidKey(), "1");
		}
		DgConsumerSeat useSeat = dgConsumerSeatMapper
				.selectByPrimaryKey(seatId);
		OnlineHttp.onlineSeatModify(useSeat.getUuidKey(), "2");	
	}

	// 生成包房费品项
	private void updateWaterSeat(DgOpenWater ow, Integer seatId,
			Integer waiterId, double price, Integer isGgFa) {
		
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
		
		int orgSeatId = ow.getSeatId();
		// 插入服务流水
		Map serviceMap = new HashMap();
		serviceMap.put("owId", ow.getId());
		serviceMap.put("waiterId", waiterId);
		serviceMap.put("serviceTime", new Date());
		serviceMap.put("serviceNum", serviceNums.get(0));
		serviceMap.put("serviceType", 7);
		billMapper.insertOwServiceWater(serviceMap); // 插入服务流水
		// 更新桌位状态
		DgConsumerSeat newSeat = new DgConsumerSeat();
		newSeat.setId(seatId);
		newSeat.setSeatState(2);
		dgConsumerSeatMapper.updateState(newSeat);
		// 最后刷新营业流水品项和 小计
		Map<String, Object> seatMan = billService.selectSeatConsumeInfo(seatId,""+ow.getPeopleCount());
		double oldfwf = ow.getServiceCharge();
		double subtotal = MathExtend.subtract(ow.getSubtotal(),oldfwf);
		double oldbff = ow.getPrivateRoomCost();
		subtotal = MathExtend.subtract(subtotal,oldbff);
		subtotal = MathExtend.add(subtotal, (double) seatMan.get("bff"));
		subtotal = MathExtend.add(subtotal, (double) seatMan.get("fwf"));
		subtotal = MathExtend.add(subtotal, price);
	    
		ow.setServiceCharge((double) seatMan.get("fwf"));
		ow.setSeatId(seatId);
		ow.setItemCostsSum(MathExtend.add(ow.getItemCostsSum(), price));
		ow.setPrivateRoomCost((double) seatMan.get("bff"));
		ow.setSubtotal(subtotal);
		if (isGgFa == 1) {
			ow.setPrivateRoomType(getBffa(seatId));
		}
		billMapper.updateOpenWaterPrimaryKey(ow);

		Map param = new HashedMap();
		param.put("seatId", orgSeatId);
		List<Map> orgSeat = aPICheckServiceMapper
				.selectOpenWaterBySeatId(param);
		if (orgSeat.size() == 0) {
			DgConsumerSeat seat = new DgConsumerSeat();
			seat.setId(orgSeatId);
			seat.setSeatState(1);
			dgConsumerSeatMapper.updateState(seat);
			DgConsumerSeat oldSeat = dgConsumerSeatMapper
					.selectByPrimaryKey(orgSeatId);
			OnlineHttp.onlineSeatModify(oldSeat.getUuidKey(), "1");	
		}
		DgConsumerSeat useSeat = dgConsumerSeatMapper
				.selectByPrimaryKey(seatId);
		OnlineHttp.onlineSeatModify(useSeat.getUuidKey(), "2");	

	}

	@Override
	public void savaGqPx(String orgs, String type) {
		// TODO Auto-generated method stub
		JSONArray json = JSONArray.fromObject(orgs);
		if (json.size() > 0) {
			dgItemOutofStockMapper.deleteAll(Integer.valueOf(type)); // 先删除所有
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				DgItemOutofStock item = new DgItemOutofStock();
				item.setItemId((int)job.get("itemId"));
				item.setItemCode((String) job.get("itemCode"));
				item.setmType(Integer.valueOf(type));
				item.setDate(new Date());
				item.setUuidKey(UUID.randomUUID().toString());
				if (item.getmType() == 2) // 按市别 得插入市别
				{
					List<TbBis> tbBiss = tbBisMapper.selectAllBis();
					List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
					for (int j = 0; j < tbBiss.size(); j++) {
						if (j != tbBiss.size() - 1) {
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("startTime", tbBiss.get(j).getBisTime());
							m.put("endTime", tbBiss.get(j + 1).getBisTime());
							m.put("nowTime", format.format(new Date()));
							m.put("id", tbBiss.get(j).getId());
							timeD.add(m);
						} else {
							Map<String, Object> m = new HashMap<String, Object>();
							m.put("startTime", tbBiss.get(j).getBisTime());
							m.put("endTime", tbBiss.get(0).getBisTime());
							m.put("nowTime", format.format(new Date()));
							m.put("id", tbBiss.get(0).getId());
							timeD.add(m);
						}
					}
					int TbisID = 0; // 获取市别id
					for (int j = 0; j < timeD.size(); j++) {
						int count = tbBisMapper.calculateSJD(timeD.get(j));
						if (count > 0) {
							TbisID = (int) timeD.get(j).get("id");
							break;
						}
					}
					if (TbisID == 0 && timeD.size() >= 1) // 没有就是最后个时段
					{
						TbisID = (int) timeD.get(timeD.size() - 1).get("id");
					}
					item.setmBis(TbisID);// 没有市别信息
				}
				dgItemOutofStockMapper.insert(item);
			}
		}
	}

	@Override
	public Map getPxAndPxType(List ids) {
		// TODO Auto-generated method stub
		Map<String, Object> ret = new HashMap<String, Object>();
		Map tree = new HashMap();
		tree.put("name", "品项类别");
		List<DgItemFileType> bTypes = dgItemFileTypeMapper.selectAllBigType();
		for (DgItemFileType b : bTypes) {
			Map param = new HashMap();
			param.put("parentId", b.getId());
			List<DgItemFileType> sTypes = dgItemFileTypeMapper
					.getItemFileTypeByParent(param);
			b.setSmallItemTypes(sTypes);
		}
		tree.put("smallItemTypes", bTypes);
		ret.put("tree", tree);
		ret.put("items", apiReMoMapper.selecAllGqxzPx(ids));
		return ret;
	}

	@Override
	public void saveXlPx(String orgs, int type) {
		// TODO Auto-generated method stub
		int todayCount = dgItemSaleLimitMapper.getCountByData(dateformat
				.format(new Date()));
		int limitId;
		if (todayCount == 0) // 要是没有就创建个
		{
			DgItemSaleLimit saleLimit = new DgItemSaleLimit();
			saleLimit.setToday(new Date());
			saleLimit.setType(type); // 获取返回的type类型
			dgItemSaleLimitMapper.insertBackId(saleLimit); // 插入
			limitId = saleLimit.getId();
		} else // 存在就更新类型
		{
			DgItemSaleLimit s = dgItemSaleLimitMapper.selectByDate(dateformat
					.format(new Date())); // 根据事件获取主表id
			s.setType(type);
			dgItemSaleLimitMapper.updateByPrimaryKeySelective(s);
			dgItemSaleLimitSMapper.deleteAll(s.getId()); // 删除子表所有数据
			limitId = s.getId();
		}
		JSONArray jArray = JSONArray.fromObject(orgs);
		if (jArray.size() > 0) {
			for (int i = 0; i < jArray.size(); i++) {
				JSONObject job = jArray.getJSONObject(i);
				DgItemSaleLimitS item = new DgItemSaleLimitS();
				item.setLimitId(limitId);
				item.setItemId((int) job.get("itemId"));
				item.setItemCode((String) job.get("itemCode"));
				if (!StringUtils.isEmpty(Double.valueOf(""+job.get("saleCount")))) {
					item.setSaleCount(Double.valueOf(""+job.get("saleCount")));
					item.setReservationCount(0.0); // 预约数量先设置为0,还没有那个功能
					item.setFrontSaleCount(Double.valueOf(""+job.get("saleCount"))); // 前台限量设置为存在数量
				} else {
					item.setSaleCount(null);
					item.setReservationCount(0.0); // 预约数量先设置为0,还没有那个功能
					item.setFrontSaleCount(null); // 前台限量也设置为null
				}
				dgItemSaleLimitSMapper.insert(item);
			}
		}
	}

	@Override
	public Map modifyJoinTeam(String userCode,String openNum, String teamNumber) {
		// TODO Auto-generated method stub
		Map ret = new HashMap();
		DgOpenWater joinWater = dgOpenWaterMapper
				.selectByOpenWaterByNum(openNum);
		if (joinWater.getIsTeam() == 1) {
			ret.put("error", APIEnumDefine.M0430002);
		} else {
			 DgOpenWater water = apiReMoMapper
					.seleOpenWaterByTeamMember(teamNumber);
			// 为1表示都没有团队
			if (apiReMoMapper.seleTeamMemberCount(teamNumber) == 1) {
				water.setIsTeam(1);
				water.setTeamMainSeat(water.getTeamMainSeat());
				dgOpenWaterMapper.updateByPrimaryKeySelective(water);
				
				joinWater.setJoinTeamNotes("加入团队");
				joinWater.setJoinTeamTime(new Date());
				joinWater.setTeamMembers(teamNumber);
				joinWater.setIsTeam(1);
				joinWater.setTeamMainSeat(water.getTeamMainSeat());
				dgOpenWaterMapper.updateByPrimaryKeySelective(joinWater);
			} else {
				// 大于1表示已有团队,直接更新团队号和团队表示即可
				joinWater.setJoinTeamNotes("加入团队");
				joinWater.setJoinTeamTime(new Date());
				joinWater.setTeamMainSeat(water.getTeamMainSeat());
				joinWater.setTeamMembers(teamNumber);
				joinWater.setIsTeam(1);
				dgOpenWaterMapper.updateByPrimaryKeySelective(joinWater);
			}
		}
		if(!ret.containsKey("error")){
			//插入日志
			List<DgOpenWater> dgOpenwaters = apiReMoMapper.seleAllOpenWaterByTeamMember(teamNumber);
			String teamseats = "";
			for(DgOpenWater dow:dgOpenwaters){
				teamseats += dow.getSeatName()+",";
			}
			if(dgOpenwaters.size() > 0){
				teamseats = teamseats.substring(0, teamseats.length()-1);
			}
			SysUser user= sysUserMapper.chekUserCode(userCode);
			DgConsumerSeat seat = dgConsumerSeatMapper.getConsumerSeatById(joinWater.getSeatId());
			sysBusinessLogMapper.insert(new SysBusinessLog(UUID.randomUUID().toString(),user.getEmpCode()+"-"+user.getEmpName() , "", new Date(), 2, openNum, 
					seat.getName(), "操作客位:"+seat.getName()+",加入团队:"+teamNumber+",加入后现有团队成员:"+teamseats,new SimpleDateFormat("yyyy_MM").format(new Date())));	
		}
		//加入团队
		//dgPrintDataService.insertJoinTeam(userCode, openNum, teamNumber);
		return ret;
	}

	/**
	 * 根据桌位id,时间获取包房收费方案id,没有就返回0
	 * 
	 * @param seatId
	 * @return
	 */
	private int getBffa(int seatId) {
		DgSeatManager seatMan = dgSeatManagerMapper.selectBySeatId(seatId);
		if(seatMan == null)
		{
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
	public void modifyGgHy(String openNum, String gradeId) {
		// TODO Auto-generated method stub
		DgOpenWater openWater = dgOpenWaterMapper.selectByOpenWaterByNum(openNum);
		openWater.setCrId(gradeId);
		dgOpenWaterMapper.updateByPrimaryKeySelective(openWater);
	}
}