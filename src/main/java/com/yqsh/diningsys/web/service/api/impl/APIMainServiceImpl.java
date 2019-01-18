package com.yqsh.diningsys.web.service.api.impl;

import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.api.APIMainMapper;
import com.yqsh.diningsys.web.dao.api.BillMapper;
import com.yqsh.diningsys.web.dao.api.DgPreOrderbillColorMapper;
import com.yqsh.diningsys.web.dao.api.DgPreOrderbillMapper;
import com.yqsh.diningsys.web.dao.api.DgReserveManagerMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.dao.archive.DgItemFileMapper;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.dao.archive.TbBisMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwDetailsOtherMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwModifySeatMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwDetailsOther;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIMainService;
import com.yqsh.diningsys.web.service.api.BillService;
import com.yqsh.diningsys.web.service.archive.DgPosService;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 类说明
 *
 * @author zhshuo create on 2016-12-06 15:07
 */
@Service
public class APIMainServiceImpl implements APIMainService {
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	@Resource
	private APIMainMapper apiMainMapper;
	@Resource
	private APICheckServiceMapper aPICheckServiceMapper;
	@Resource
	private BillService billService;
	@Resource
	private BillMapper billMapper;
	@Resource
	private DgOwDetailsOtherMapper dgOwDetailsOtherMapper;
	@Resource
	private DgOwModifySeatMapper dgOwModifySeatMapper;
	@Resource
	private DgItemFileMapper dgItemFileMapper;
	@Resource
	private TbBisMapper tbBisMapper;
	@Resource
	private APICheckServiceMapper apiCheckServiceMapper;
	@Resource
	private DgPosMapper dgPosMapper;
	@Resource
	private DgOpenWaterMapper dgOpenWaterMapper;
	@Resource
	private DgReserveManagerMapper dgReserveManagerMapper;
	@Resource
	private DgConsumerSeatMapper dgConsumerSeatMapper;

	@Autowired
	private APICheckService apiCheckService;
	@Autowired
	private DgPreOrderbillMapper dgPreOrderbillMapper;

	@Override
	public Map selectLastOpenClassInfo(Integer posid) {
		return apiMainMapper.selectLastOpenClassInfo(posid);
	}

	@Override
	public void updateOpenClass(String userCode, String userName,
			Integer posId, Double pettyCash, Date loginTime) {
		Map param = new HashMap();
		param.put("userCode", userCode);
		param.put("userName", userName);
		param.put("posId", posId);
		param.put("pettyCash", pettyCash);
		param.put("loginTime", loginTime);
		apiMainMapper.updateOpenClass(param);
	}

	@Override
	public List<Map> getOpenWaterDetailsAccordingBig(int seatId) {
		// TODO Auto-generated method stub
		Map param = new HashedMap();
		param.put("seatId", seatId);
		List<Map> owMap = aPICheckServiceMapper.selectOpenWaterBySeatId(param);
		for (Map o : owMap) {
			List<Map> bMap = new ArrayList<Map>();
			List<Map<String, Object>> items = billService
					.getWaterAllItemInfo((String) o.get("ow_num"));
			for (Map<String, Object> i : items) {
				i.put("notes",getNoteBackString((String)i.get("notes")));
				if((int)i.get("is_tc") == 1)
				{
					i.put("itemName",i.get("itemName")+"(套)");
					Map tc = new HashMap();
					tc.put("owId",i.get("ow_id"));
					tc.put("itemFileId",i.get("item_file_id"));
					List<Map> tcDetail = apiMainMapper.selectTcSumByIncreaServiceTcId(tc);
					for(Map t:tcDetail)
					{
						t.put("notes",i.get("notes"));
					}
					i.put("tcDetail",tcDetail);
				}
				Map s = new HashMap();
				s.put("owId", i.get("ow_id"));
				s.put("itemId", i.get("item_file_id"));
				i.put("extra", dgOwDetailsOtherMapper.selectByOwId(s));
				Map itemBinfo = apiMainMapper
						.selectItemFileTypeByItemId((int) i.get("item_file_id"));
				if (bMap.isEmpty()) {
					List<Map> cMap = new ArrayList<Map>();
					cMap.add(i);
					Map map = new HashMap();
					map.put("bId", itemBinfo.get("id"));
					map.put("bNum", itemBinfo.get("num"));
					map.put("bName", itemBinfo.get("name"));
					map.put("subtotal", i.get("subtotal"));
					map.put("child", cMap);
					bMap.add(map);
				} else {
					boolean havaBig = false;
					for (Map b : bMap) {
						if ((int) b.get("bId") == (int) itemBinfo.get("id")) {
							havaBig = true;
							List<Map> cMap = (List<Map>) b.get("child");
							cMap.add(i);
							b.put("subtotal", MathExtend.add(
									(double) b.get("subtotal"),
									(double) i.get("subtotal")));
							break;
						}
					}
					if (!havaBig) {
						List<Map> cMap = new ArrayList<Map>();
						cMap.add(i);
						Map map = new HashMap();
						map.put("bId", itemBinfo.get("id"));
						map.put("bNum", itemBinfo.get("num"));
						map.put("bName", itemBinfo.get("name"));
						map.put("subtotal", i.get("subtotal"));
						map.put("child", cMap);
						bMap.add(map);
					}
				}
			}
			o.put("first_time", simpleDateFormat.format(o.get("first_time")));
			o.put("teamMembers", apiMainMapper
					.selectTeamByTeamMember((String) o.get("team_members")));
			o.put("details", bMap);
		}
		return owMap;
	}

	@Override
	public List<Map> getOpenWaterDetailsOrderByService(int seatId) {
		// TODO Auto-generated method stub
		Map param = new HashedMap();
		param.put("seatId", seatId);
		List<Map> owMap = aPICheckServiceMapper.selectOpenWaterBySeatId(param);
		for (Map o : owMap) {
			List<Map> sMap = apiMainMapper.selectItemOrderByService((int) o
					.get("id"));
			for(Map i:sMap)
			{
				i.put("notes",getNoteBackString((String)i.get("notes")));
				if((int)i.get("is_tc") == 1)
				{
					i.put("itemName",i.get("itemName")+"(套)");
					Map tc = new HashMap();
					tc.put("owId",i.get("ow_id"));
					tc.put("itemFileId",i.get("item_file_id"));
					List<Map> tcDetail = apiMainMapper.selectTcSumByServiceTcId(tc);
					for(Map t:tcDetail)
					{
						t.put("notes",i.get("notes"));
					}
					i.put("tcDetail",tcDetail);
				}
			}
			o.put("first_time", simpleDateFormat.format(o.get("first_time")));
			o.put("teamMembers", apiMainMapper
					.selectTeamByTeamMember((String) o.get("team_members")));
			o.put("list", sMap);
		}
		return owMap;
	}

	@Override
	public List<Map> getOpenWaterDetailsOrderByItem(int seatId) {
		// TODO Auto-generated method stub
		Map param = new HashedMap();
		param.put("seatId", seatId);
		List<Map> owMap = aPICheckServiceMapper.selectOpenWaterBySeatId(param);
		for (Map o : owMap) {
			Double tempSubtotal = 0.0;
			if(o.containsKey("ow_num")){
				if(!StringUtils.isEmpty(o.get("ow_num").toString())){
					List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterClearingWithService(o.get("ow_num").toString(),0);
					for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
						tempSubtotal = MathExtend.add(tempSubtotal,MathExtend.add(MathExtend.multiply(dgOwConsumerDetails.getItemFinalPrice(),dgOwConsumerDetails.getItemFileNumber()),dgOwConsumerDetails.getProductionCosts()));
					}
				}
			}
			o.put("subtotal",tempSubtotal);


			List<Map<String, Object>> resList = billMapper
					.selectItemByWater((String) o.get("ow_num"));// 选出加单流水
			Map<String, String> param_sub = new HashMap<>();
			param_sub.put("owNum",(String) o.get("ow_num"));
			List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param_sub); //服务类型为退单的品项数据
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
//			Iterator<Map<String, Object>> re = cr.iterator();
//			while (re.hasNext()) {
//				Map sorg = new HashMap();
//				Map<String, Object> rr = re.next();
//				sorg.put("owId", rr.get("ow_id"));
//				sorg.put("itemFileId", rr.get("item_file_id"));
//				sorg.put("yeId", o.get("ow_num"));
//				Double surplusItemNumber = billMapper
//						.selectConsumerDetailByOwId(sorg); // 计算本流水(退单后),最终数量
//				if (surplusItemNumber != null && surplusItemNumber > 0) {
//					rr.put("item_file_number", surplusItemNumber);
//					if((int)rr.get("is_tc") == 1)
//					{
//						rr.put("itemName",rr.get("itemName")+"(套)");
//					}
//					Map<String, Object> src = new HashMap<String, Object>();
//					src.put("owId", rr.get("ow_id"));
//					src.put("itemId", rr.get("item_file_id"));
//					double zzffTotal = 0;// 制作费用和
//					List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
//							.selectByOwId(src);
//					for (DgOwDetailsOther other : rOther) {
//						if (other.getOtype() == 3 )
//						{
//							if (other.getZzffSf() !=null && other.getZzffSf() == 1) {
//								// 按品项
//								if (other.getZzffSfType() == 0) {
//									zzffTotal = MathExtend.add(zzffTotal,
//											other.getOcosts());
//								} else if (other.getZzffSfType() == 1) {
//									zzffTotal = MathExtend.add(zzffTotal,
//											MathExtend.multiply(other.getOcosts(),
//													surplusItemNumber));
//								}
//							}
//						}
//					}
//
//					double subtotal = MathExtend.multiply(
//							(double) rr.get("item_final_price"),
//							surplusItemNumber);
//					rr.put("subtotal", MathExtend.add(subtotal, zzffTotal));
//				} else if (surplusItemNumber != null && surplusItemNumber <= 0) {
//					re.remove();
//				}
//			}
			List<Map> retList = new ArrayList<Map>();
			for (Map c : sl) {
				if (retList.isEmpty()) {
					retList.add(c);
				} else {
					boolean havaItem = false;
					for (Map r : retList) {
						if ((int)r.get("item_file_id") == (int)c.get("item_file_id")) {
							havaItem = true;
							r.put("item_file_number",
									(double) r.get("item_file_number")
											+ (double) c
											.get("item_file_number"));
							r.put("subtotal", MathExtend.add(
									(double) r.get("subtotal"),
									(double) c.get("subtotal")));
							break;
						}
					}
					if (!havaItem) {
						retList.add(c);
					}
				}
			}

			//获取团队成员

			o.put("first_time", simpleDateFormat.format(o.get("first_time")));
			List<Map> teamMembers = apiMainMapper
					.selectTeamByTeamMember((String) o.get("team_members"));
			o.put("teamMembers", teamMembers);
			o.put("list", retList);
		}
		return owMap;
	}

	@Override
	public List<Map> getOpenWaterDetailSubByService(int seatId) {
		// TODO Auto-generated method stub
		Map param = new HashedMap();
		param.put("seatId", seatId);
		List<Map> owMap = aPICheckServiceMapper.selectOpenWaterBySeatId(param);
		for (Map o : owMap) {
//			List<Map<String, Object>> cr = billMapper
//					.selectItemByWater((String) o.get("ow_num"));// 选出加单流水
			List<Map<String, Object>> resList = billMapper
					.selectItemByWater((String) o.get("ow_num"));// 选出加单流水
			Map<String, String> param_sub = new HashMap<>();
			param_sub.put("owNum",(String) o.get("ow_num"));
			List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param_sub); //服务类型为退单的品项数据
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
//			Iterator<Map<String, Object>> re = cr.iterator();
//			while (re.hasNext()) {
//				Map sorg = new HashMap();
//				Map<String, Object> rr = re.next();
//				sorg.put("owId", rr.get("ow_id"));
//				sorg.put("itemFileId", rr.get("item_file_id"));
//				sorg.put("yeId", o.get("ow_num"));
//				Double surplusItemNumber = billMapper
//						.selectConsumerDetailByOwId(sorg); // 计算本流水(退单后),最终数量
//				if (surplusItemNumber != null && surplusItemNumber > 0) {
//					rr.put("item_file_number", surplusItemNumber);
//
//					rr.put("notes",getNoteBackString((String)rr.get("notes")));
//					if((int)rr.get("is_tc") == 1)
//					{
//						rr.put("itemName",rr.get("itemName")+"(套)");
//						Map tc = new HashMap();
//						tc.put("owId",rr.get("ow_id"));
//						tc.put("itemFileId",rr.get("item_file_id"));
//						List<Map> tcDetail = apiMainMapper.selectTcSumByIncreaServiceTcId(tc);
//						for(Map t:tcDetail)
//						{
//							t.put("notes",rr.get("notes"));
//						}
//						rr.put("tcDetail",tcDetail);
//					}
//
//					Map map = new HashMap();
//					map.put("owId", rr.get("ow_id"));
//					map.put("itemId", rr.get("item_file_id"));
//					rr.put("extra", dgOwDetailsOtherMapper.selectByOwId(map));
//
//					Map<String, Object> src = new HashMap<String, Object>();
//					src.put("owId", rr.get("ow_id"));
//					src.put("itemId", rr.get("item_file_id"));
//					double zzffTotal = 0;// 制作费用和
//					List<DgOwDetailsOther> rOther = dgOwDetailsOtherMapper
//							.selectByOwId(src);
//					for (DgOwDetailsOther other : rOther) {
//						if(other.getOtype() == 3)
//						{
//							if (other.getZzffSf() !=null && other.getZzffSf() == 1) {
//								// 按品项
//								if (other.getZzffSfType() == 0) {
//									zzffTotal = MathExtend.add(zzffTotal,
//											other.getOcosts());
//								} else if (other.getZzffSfType() == 1) {
//									zzffTotal = MathExtend.add(zzffTotal,
//											MathExtend.multiply(other.getOcosts(),
//													surplusItemNumber));
//								}
//							}
//						}
//					}
//					double subtotal = MathExtend.multiply(
//							(double) rr.get("item_final_price"),
//							surplusItemNumber);
//					rr.put("subtotal", MathExtend.add(subtotal, zzffTotal));
//				} else if (surplusItemNumber != null && surplusItemNumber <= 0) {
//					re.remove();
//				}
//			}
			o.put("first_time", simpleDateFormat.format(o.get("first_time")));
			o.put("teamMembers", apiMainMapper
					.selectTeamByTeamMember((String) o.get("team_members")));
			o.put("list", sl);
		}
		return owMap;
	}

	private String getNoteBackString(String note) {
		if (note.equals("1")) {
			return "加单";
		} else if (note.equals("2")) {
			return "加单";
		} else if (note.equals("3")) {
			return "赠单";
		} else if (note.equals("4")) {
			return "退单";
		} else if (note.equals("5")) {
			return "人数减单";
		} else if (note.equals("6")) {
			return "人数加单";
		} else if (note.equals("7")) {
			return "包房费";
		} else {
			return "未知";
		}
	}

	@Override
	public Map selectSeatInfoBySeatId(Integer seatId) {
		// TODO Auto-generated method stub
		return apiMainMapper.selectSeatInfoBySeatId(seatId);
	}

	@Override
	public Map selectCountCurrentFrequency(Integer posId, Date date,List<String> ids) {
		Map<String,Object> map = new HashMap();
		map.put("posId",posId);
		map.put("time",date);
		map.put("list",ids);
		return apiMainMapper.selectCountCurrentFrequency(map);
	}
	
	@Override
	public Map selectCountBis(Integer posId, Date date,List<String> ids) {
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String startTime = null;
		String endTime = null;
		
		//获取当前时间下的市别id
		Integer id = getMealInt_one();
		TbBis setParams = new TbBis();
		setParams.setId(id);
		setParams.setIsDel("0");
		List<TbBis> bis = tbBisMapper.getAllList(setParams);
		if(bis.size() > 0){
			if(bis.get(0) != null){
				startTime = bis.get(0).getBisTime();
				//获取下一个营业节点时间
				endTime = tbBisMapper.getNextBisTime(startTime);
				
				calendar.add(Calendar.DAY_OF_YEAR, 0);
				Date d = calendar.getTime();
				String nowDate = sdf.format(d);
				startTime = nowDate + " " + startTime;
				endTime = nowDate + " " + endTime;
				if(endTime == null){
					//获取下一天最小营业节点时间
					endTime = tbBisMapper.getMinBisTime();
					
					calendar.add(Calendar.DAY_OF_YEAR, 1);
					Date d2 = calendar.getTime();
					String nowDate2 = sdf.format(d2);
					startTime = nowDate2 + " " + startTime;
					endTime = nowDate2 + " " + endTime;
				}
			}
		}

		Map<String,Object> map = new HashMap();
		map.put("posId",posId);
		map.put("startTime",startTime);
		map.put("endTime",endTime);
		map.put("list",ids);
		return apiMainMapper.selectCountBis(map);
	}

	@Override
	public Map selectUserLastLoginPos(String userCode, Integer id) {
		Map<String,Object> map = new HashMap();
		map.put("userCode",userCode);
		map.put("id",id);
		return apiMainMapper.selectUserLastLoginPos(map);
	}

	@Override
	public Map selectCountCurrentBis() {
		Map<String,Object> map = new HashMap<>();
		TbBis mealInt = getMealInt();
		map.put("start",mealInt.getBisTime());
		map.put("start",mealInt.getBisTime());
		return map;
	}

	@Override
	public Map selectLoginUserLast(String empCode) {
		return apiMainMapper.selectLoginUserLast(empCode);
	}

	Integer getMealInt_one() {
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

	/*
     * 获取当前时间下的市别id
     */
	private TbBis getMealInt() {
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
		TbBis tbBis = null;
		for (int i = 0; i < timeD.size(); i++) {
			int count = tbBisMapper.calculateSJD(timeD.get(i));
			if (count > 0) {
				tbBis = (TbBis)timeD.get(i);
				break;
			}
		}
		if (tbBis == null) // 没有就是最后个时段
		{
			tbBis =  (TbBis) timeD.get(timeD.size() - 1);
		}
		return tbBis;
	}

	@Override
	public Map getOpenWaterOrderItem(String owNum,Integer seatId) {
		// TODO Auto-generated method stub
		Map o = new HashMap();
//		DgOpenWater water = (DgOpenWater) CacheUtil.getYxeCache(owNum,seatId).get("water");
		DgOpenWater water = (DgOpenWater) apiMainMapper.selectOpenWaterByOwNumConCount(owNum);
		DgConsumerSeat seat = (DgConsumerSeat) CacheUtil.getYxeCache(owNum,seatId).get("seat");
		List<Map<String, Object>> cr = billMapper
				.selectItemByWater_new(owNum);// 选出加单流水
		o.put("water", water);
		o.put("list", cr);
		Map param = new HashMap();
		param.put("seatId", seatId);
        o.put("waterCount",water.getWaterCount());
		Integer wlist = dgPreOrderbillMapper.selectCountByWaterId(water.getId());
		o.put("wlist",wlist == null?0 :wlist);
		if(seatId != null){
			o.put("seatState", seat.getSeatState());
			if(seat.getSeatState() == 4){
				o.put("Drm",seat.getDrm());
			}
		}
		return o;
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

	@Override
	public List<Map> getOpenWaterDetailsAccordingBig(String owNum) {
		// TODO Auto-generated method stub

		List<Map> bMap = new ArrayList<Map>();
		List<Map<String, Object>> items = billService
				.getWaterAllItemInfo(owNum);
		for (Map<String, Object> i : items) {
			i.put("notes",getNoteBackString((String)i.get("notes")));
			if((int)i.get("is_tc") == 1)
			{
				i.put("itemName",i.get("itemName")+"(套)");
				Map tc = new HashMap();
				tc.put("owId",i.get("ow_id"));
				tc.put("itemFileId",i.get("item_file_id"));
				List<Map> tcDetail = apiMainMapper.selectTcSumByIncreaServiceTcId(tc);
				for(Map t:tcDetail)
				{
					t.put("notes",i.get("notes"));
				}
				i.put("tcDetail",tcDetail);
			}
			Map s = new HashMap();
			s.put("owId", i.get("ow_id"));
			s.put("itemId", i.get("item_file_id"));
			i.put("extra", dgOwDetailsOtherMapper.selectByOwId(s));
			Map itemBinfo = apiMainMapper
					.selectItemFileTypeByItemId((int) i.get("item_file_id"));
			if (bMap.isEmpty()) {
				List<Map> cMap = new ArrayList<Map>();
				cMap.add(i);
				Map map = new HashMap();
				map.put("bId", itemBinfo.get("id"));
				map.put("bNum", itemBinfo.get("num"));
				map.put("bName", itemBinfo.get("name"));
				map.put("subtotal", i.get("subtotal"));
				map.put("child", cMap);
				bMap.add(map);
			} else {
				boolean havaBig = false;
				for (Map b : bMap) {
					if ((int) b.get("bId") == (int) itemBinfo.get("id")) {
						havaBig = true;
						List<Map> cMap = (List<Map>) b.get("child");
						cMap.add(i);
						b.put("subtotal", MathExtend.add(
								(double) b.get("subtotal"),
								(double) i.get("subtotal")));
						break;
					}
				}
				if (!havaBig) {
					List<Map> cMap = new ArrayList<Map>();
					cMap.add(i);
					Map map = new HashMap();
					map.put("bId", itemBinfo.get("id"));
					map.put("bNum", itemBinfo.get("num"));
					map.put("bName", itemBinfo.get("name"));
					map.put("subtotal", i.get("subtotal"));
					map.put("child", cMap);
					bMap.add(map);
				}
			}
		}
		return bMap;
	}

	@Override
	public List<Map> getOpenWaterDetailsOrderByService(String owNum) {
		// TODO Auto-generated method stub
		DgOpenWater dow = dgOpenWaterMapper.selectByOpenWaterByNum(owNum);
		List<Map> sMap = apiMainMapper.selectItemOrderByService(dow.getId());
		for(Map i:sMap)
		{
			i.put("notes",getNoteBackString((String)i.get("notes")));
			if((int)i.get("is_tc") == 1)
			{
				i.put("itemName",i.get("itemName")+"(套)");
				Map tc = new HashMap();
				tc.put("owId",i.get("ow_id"));
				tc.put("itemFileId",i.get("item_file_id"));
				List<Map> tcDetail = apiMainMapper.selectTcSumByServiceTcId(tc);
				for(Map t:tcDetail)
				{
					t.put("notes",i.get("notes"));
				}
				i.put("tcDetail",tcDetail);
			}
		}
		return sMap;
	}

	@Override
	public List<Map> getOpenWaterDetailsOrderByItem(String owNum) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resList = billMapper
				.selectItemByWater(owNum);// 选出加单流水
		Map<String, String> param_sub = new HashMap<>();
		param_sub.put("owNum",owNum);
		List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param_sub); //服务类型为退单的品项数据
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

		List<Map> retList = new ArrayList<Map>();
		for (Map c : sl) {
			if (retList.isEmpty()) {
				retList.add(c);
			} else {
				boolean havaItem = false;
				for (Map r : retList) {
					if ((int)r.get("item_file_id") == (int)c.get("item_file_id")) {
						havaItem = true;
						r.put("item_file_number",
								(double) r.get("item_file_number")
										+ (double) c
										.get("item_file_number"));
						r.put("subtotal", MathExtend.add(
								(double) r.get("subtotal"),
								(double) c.get("subtotal")));
						break;
					}
				}
				if (!havaItem) {
					retList.add(c);
				}
			}
		}
		//获取团队成员
		return retList;
	}

	@Override
	public List<Map<String, Object>> getOpenWaterDetailSubByService(String owNum) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> resList = billMapper
				.selectItemByWater(owNum);// 选出加单流水
		Map<String, String> param_sub = new HashMap<>();
		param_sub.put("owNum",owNum);
		List<DgOwConsumerDetails> itemDetail2 = apiCheckServiceMapper.selectAllItemDataReducedItem(param_sub); //服务类型为退单的品项数据
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
	}

	@Override
	public Map yxeMainLoop(String owNum, Integer seatId) {
		// TODO Auto-generated method stub
		Map ret = CacheUtil.getYxeCache(owNum, seatId);
		if(ret == null){
			ret = new HashMap();
			DgOpenWater water = apiMainMapper.selectOpenWaterByOwNumConCount(owNum);
			Map org = new HashMap();
			org.put("time",simpleDateFormat.format(new Date()));
			org.put("seatId", seatId);
			DgConsumerSeat seat = apiMainMapper.selectTableConReserveByseatId(org);
			ret.put("water",water);
			ret.put("seat",seat);
		}
		return ret;
	}

}
