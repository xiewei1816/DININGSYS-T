package com.yqsh.diningsys.web.service.print.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.api.util.CommonUtil;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.print.DgPrintDataMapper;
import com.yqsh.diningsys.web.dao.print.DgPrintManagerMapper;
import com.yqsh.diningsys.web.dao.print.DgPrintManagerSMapper;
import com.yqsh.diningsys.web.dao.sysSettings.DgUrlSettingMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.api.DgPreOrderbillColor;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.print.DgPrintData;
import com.yqsh.diningsys.web.model.print.DgPrintManager;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.print.DgPrintDataService;

@Service("dgPrintDataServiceImpl")
public class DgPrintDataServiceImpl extends
		GenericServiceImpl<DgPrintData, Integer> implements DgPrintDataService {
	public static final MediaType GBK = MediaType
			.parse("application/json; charset=GBK");
	private static final Integer owId = null;
	@Autowired
	private DgOpenWaterMapper dgOpenWaterMapper;

	@Autowired
	private DgPrintDataMapper dgPrintDataMapper;

	@Autowired
	private DgPrintManagerMapper dgPrintManagerMapper;

	@Autowired
	private DgPrintManagerSMapper dgPrintManagerSMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private DgConsumerSeatMapper dgConsumerSeatMapper;

	@Autowired
	private DgUrlSettingMapper dgUrlSettingMapper;

	@Autowired
	private DgUrlSettingService dgUrlSettingService;

	@Override
	public GenericDao<DgPrintData, Integer> getDao() {
		// TODO Auto-generated method stub
		return dgPrintDataMapper;
	}

	@Override
	public DgPrintData getFirstItem() {
		// TODO Auto-generated method stub
		return dgPrintDataMapper.getFirstItem();
	}

	@Override
	public List<Map> getPrintUrl(String body) throws Exception {
		Gson gson = new Gson();
		OkHttpClient client = new OkHttpClient();
		DgUrlSetting urls = dgUrlSettingMapper.selectByCode("print.url");
		// 表单数据
		RequestBody orgs = RequestBody.create(GBK, body);
		Request request = new Request.Builder().url(urls.getValue()).post(orgs)
				.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			String str = response.body().string();
			Map printList = gson.fromJson(str, new TypeToken<Map>() {
			}.getType());
			List<DgPrintManager> allPrint = dgPrintManagerMapper.selectAll();
			List<Map> printers = (List<Map>) printList.get("list");
			if (allPrint == null) {
				return printers;
			}
			Iterator<Map> re = printers.iterator();
			while (re.hasNext()) {
				Map r = re.next();
				for (DgPrintManager pm : allPrint) {
					if (pm.getName().equals(r.get("name"))) {
						re.remove();
						break;
					}
				}
			}
			return printers;
		} else {
			return null;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dgPrintDataMapper.getCount();
	}

	@Override
	public void insertAddBillPrint(Integer owId, Double money) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map serviceItem = dgPrintDataMapper.getPrintOwServiceInfo(owId);
		serviceItem.put("subtotal", money);
		List<Map> item = getPrintItemByOwId(owId, serviceItem,false,"51002");
		serviceItem.put("item", item);
		if (item.size() == 0) {
			return;
		}

		String timestamp = "" + System.currentTimeMillis();
		String explain = "加单";
        String code = "51002";
		String serviceType = serviceItem.get("serviceType")+"";
		switch(serviceType){
			case "2":
				explain = "加单";
                code = "51002";
				break;
			case "3":
				explain = "赠单";
                code = "51008";
				break;
		}

		Map sendContent = new HashMap();
		sendContent.put("code", code);
		sendContent.put("explain", explain);
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", serviceItem);

		DgPrintData data = new DgPrintData();
		data.setPrintType(code);
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);
		dgPrintDataMapper.insertSelective(data);
	}

	@Override
	public void insertBackBill(Integer owId, Double money) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map serviceItem = dgPrintDataMapper.getPrintOwServiceInfo(owId);
		serviceItem.put("subtotal", money);
		List<Map> item = getPrintItemByOwId(owId, serviceItem,false,"51003");
		if (item.size() == 0) {
			return;
		}
		serviceItem.put("item", item);
		String timestamp = "" + System.currentTimeMillis();
		Map sendContent = new HashMap();
		sendContent.put("code", 51003);
		sendContent.put("explain", "退单");
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", serviceItem);

		DgPrintData data = new DgPrintData();
		data.setPrintType("51003");
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);
		dgPrintDataMapper.insertSelective(data);
	}

	@Override
	public void insertReminderBill(List<Map> list) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map serviceItem = dgPrintDataMapper.getPrintOwServiceInfo((int) list
				.get(0).get("serviceId"));
		List<Map> items = dgPrintDataMapper
				.selePrintItemByReminder(list);
		List<Map> content = new ArrayList<Map>();
		for (Map m : items) {
			for (Map<String, Object> l : list) {
				if (l.get("serviceId").equals(m.get("ow_id"))
						&& l.get("itemFileId").equals(m.get("item_file_id"))) {
					m.put("reminderItemNumber", l.get("reminderItemNumber"));
					break;
				}
			}

			if (content.size() == 0) {
				Map orgs = new HashMap();
				orgs.put("itemId", m.get("item_file_id"));
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);
				boolean hava = false;
				hava = getPrintItemByOwIdNextNotHava(pm, serviceItem, content,
						hava, m,false,"51004");
				if (!hava) {
					orgs.put("itemId", m.get("ppxl_id"));
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrintItemByOwIdNextNotHava(pm, serviceItem, content,
							hava, m,false,"51004");
				}
			} else {
				Map orgs = new HashMap();
				orgs.put("itemId", m.get("item_file_id"));
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);
				boolean hava = false;
				hava = getPrintItemByOwIdNextHava(pm, serviceItem, content,
						hava, m,false,"51004");
				if (!hava) {
					orgs.put("itemId", m.get("ppxl_id"));
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrintItemByOwIdNextHava(pm, serviceItem, content, hava,
							m,false,"51004");
				}
			}
		}
		if (content.size() == 0) {
			return;
		}
		serviceItem.put("item", content);
		String timestamp = "" + System.currentTimeMillis();
		Map sendContent = new HashMap();
		sendContent.put("code", 51004);
		sendContent.put("explain", "催单");
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", serviceItem);

		DgPrintData data = new DgPrintData();
		data.setPrintType("51004");
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);
		dgPrintDataMapper.insertSelective(data);
	}

	@Override
	public void insertModifyDishesTurntable(Integer oWaterId, Integer tWaterId,
			List<Map> maps) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map bodyMap = new HashMap();
		Map oWaterInfo = dgPrintDataMapper.getPrintWaterInfo(oWaterId);
		Map tWaterInfo = dgPrintDataMapper.getPrintWaterInfo(tWaterId);
		Map serviceItem = dgPrintDataMapper
				.getPrintOwServiceInfoByWaterId(oWaterId);
		List<Map> items = dgPrintDataMapper.selePrintItemByReminder(maps);
		List<Map> content = new ArrayList<Map>();
		for (Map m : items) {
			for (Map l : maps) {
				if (l.get("serviceId").equals(m.get("ow_id"))
						&& l.get("itemFileId").equals(m.get("item_file_id"))) {
					m.put("itemFileNum", l.get("itemFileNum"));
					break;
				}
			}

			if (content.size() == 0) {
				Map orgs = new HashMap();
				orgs.put("itemId", m.get("item_file_id"));
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);

				for (DgPrintManager dgPrintManager : pm) {
					Integer dp = dgPrintManager.getDp();
					if(dp != null && dp==0){
						pm.remove(dgPrintManager);
						if(pm.size() == 0){
							break;
						};
					}
				}

				boolean hava = false;
				hava = getPrintItemByOwIdNextNotHava(pm, serviceItem, content,
						hava, m,false,"51005");
				if (!hava) {
					orgs.put("itemId", m.get("ppxl_id"));
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrintItemByOwIdNextNotHava(pm, serviceItem, content,
							hava, m,false,"51005");
				}
			} else {
				Map orgs = new HashMap();
				orgs.put("itemId", m.get("item_file_id"));
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);

				for (DgPrintManager dgPrintManager : pm) {
					Integer dp = dgPrintManager.getDp();
					if(dp != null && dp==0){
						pm.remove(dgPrintManager);
						if(pm.size() == 0){
							break;
						};
					}
				}

				boolean hava = false;
				hava = getPrintItemByOwIdNextHava(pm, serviceItem, content,
						hava, m,false,"51005");
				if (!hava) {
					orgs.put("itemId", m.get("ppxl_id"));
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrintItemByOwIdNextHava(pm, serviceItem, content, hava,
							m,false,"51005");
				}
			}
		}
		if (content.size() == 0) {
			return;
		}
		bodyMap.put("oWaterInfo", oWaterInfo);
		bodyMap.put("tWaterInfo", tWaterInfo);
		bodyMap.put("item", content);
		String timestamp = "" + System.currentTimeMillis();
		Map sendContent = new HashMap();
		sendContent.put("code", 51005);
		sendContent.put("explain", "单品转台");
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", bodyMap);

		DgPrintData data = new DgPrintData();
		data.setPrintType("51005");
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);

		dgPrintDataMapper.insertSelective(data);

		//判断是否打印单品转台（“url编辑”：全局控制不指定打印机类型） 0-不打印 1-打印
		/*DgUrlSetting dgUrlSetting = dgUrlSettingService.selectByCode("isPrintDp");
		if(dgUrlSetting != null){
			String isPrintDp = dgUrlSetting.getValue();
			if(isPrintDp.equals("1")){
				dgPrintDataMapper.insertSelective(data);
			}
		}else{
			dgPrintDataMapper.insertSelective(data);
		}*/


	}

	private List<Map> getPrintItemByOwId(Integer owId, Map serviceItem,boolean isCt,String code) {
		List<Map> sendContent = new ArrayList<Map>();
		List<Map> itemList = dgPrintDataMapper.selePrintItem(owId);
		for (Map m : itemList) {
			if ((int) m.get("isTc") == 1) {
				m.put("name", m.get("name") + "(套)");
			}
			if (sendContent.size() == 0) {
				Map orgs = new HashMap();
				orgs.put("itemId", m.get("item_file_id"));
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);
				boolean hava = false;
				hava = getPrintItemByOwIdNextNotHava(pm, serviceItem,
						sendContent, hava, m,isCt,code);
//				if (!hava) {
					orgs.put("itemId", m.get("ppxl_id"));
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrintItemByOwIdNextNotHava(pm, serviceItem, sendContent,
							hava, m,isCt,code);
//				}
			} else {
				Map orgs = new HashMap();
				orgs.put("itemId", m.get("item_file_id"));
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);
				boolean hava = false;
				hava = getPrintItemByOwIdNextHava(pm, serviceItem, sendContent,
						hava, m,isCt,code);
//				if (!hava) {
					orgs.put("itemId", m.get("ppxl_id"));
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrintItemByOwIdNextHava(pm, serviceItem, sendContent,
							hava, m,isCt,code);
//				}
			}
		}

		return sendContent;
	}

	@Override
	public void insertChangeTable(Integer waterid, String owNum,
			Integer tableid, Integer oldSeatId, Integer isGgFa,
			Integer isJsBffPx) {
		// TODO Auto-generated method stub
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		SysUser user = sysUserMapper.selectByPrimaryKey(waterid);
		DgConsumerSeat seat = dgConsumerSeatMapper.selectByPrimaryKey(tableid);
		DgConsumerSeat oldseat = dgConsumerSeatMapper
				.selectByPrimaryKey(oldSeatId);
		DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(owNum);
		water.setSeatId(oldseat.getId());
		water.setSeatName(oldseat.getName());
		List<DgPrintManager> ztPrints = dgPrintManagerMapper.selectZtItem();
		if (ztPrints != null && ztPrints.size() > 0) {
			String timestamp = "" + System.currentTimeMillis();
			Map content = new HashMap();
			content.put("waterName", user.getEmpName());
			content.put("seatName", seat.getName());
			content.put("isGgFa", isGgFa == 1 ? "是" : "否");
			content.put("isJsBffPx", isJsBffPx == 1 ? "是" : "否");
			List<Map> item = new ArrayList<Map>();
			Map print = new HashMap();
			print.put("Name", ztPrints.get(0).getName());
			print.put("qOz", ztPrints.get(0).getqOZ());
			print.put("Num", ztPrints.get(0).getNum());
			List<DgOpenWater> items = new ArrayList<DgOpenWater>();
			items.add(water);
			print.put("items", items);
			item.add(print);
			content.put("item", item);
			if (item.size() == 0) {
				return;
			}
			Map sendContent = new HashMap();
			sendContent.put("code", 51006);
			sendContent.put("explain", "客位转台");
			sendContent.put("username", "admin");
			sendContent.put("timestamp", timestamp);
			sendContent.put("body", content);

			DgPrintData data = new DgPrintData();
			data.setPrintType("51006");
			data.setUniqueIdentif(timestamp);
			data.setContent(gson.toJson(sendContent));
			data.setSuccess(0);

			dgPrintDataMapper.insertSelective(data);
		}
	}

	@Override
	public void insertJoinTeam(String operCode, String openNum,
			String teamNumber) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		SysUser user = sysUserMapper.selectByUsername(operCode);
		DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(openNum);
		Map orgs = new HashMap();
		orgs.put("member", teamNumber);
		orgs.put("owId", water.getId());
		List<Map> memberWaters = dgPrintDataMapper.selectAllTeamMember(orgs);
		String timestamp = "" + System.currentTimeMillis();
		Map content = new HashMap();
		content.put("waterName", user.getEmpName());
		content.put("water", water);
		content.put("memberWaters", memberWaters);
		Map sendContent = new HashMap();
		sendContent.put("code", 51007);
		sendContent.put("explain", "加入团队");
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", content);
		gson.toJson(sendContent);

		DgPrintData data = new DgPrintData();
		data.setPrintType("51007");
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);
		dgPrintDataMapper.insertSelective(data);
	}

	@Override
	public void insertQcBill(String operCode, String openNum, List<Map> orgs) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		SysUser user = sysUserMapper.selectByUsername(operCode);
		DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(openNum);
		Map serviceItem = dgPrintDataMapper
				.getPrintOwServiceInfoByWaterId(water.getId());
		for (Map o : orgs) {
			o.put("serviceId", o.get("owId"));
		}
		List<Map> items = dgPrintDataMapper.selePrintItemByReminder(orgs);
		List<Map> content = new ArrayList<Map>();
		for (Map m : items) {
			for (Map l : orgs) {
				if (l.get("serviceId").equals(m.get("ow_id"))
						&& l.get("itemFileId").equals(m.get("item_file_id"))) {
					m.put("qcNumber", l.get("qcNumber")); // 起菜数量
					m.put("qcZhsj", l.get("qcZhsj")); // 起菜最后时间
					m.put("qcFs",
							(int) l.get("qcFs") == 1 ? "起菜" : ((int) l
									.get("qcFs") == 2 ? "拖菜" : "停菜")); // 起菜最后时间
					break;
				}
			}

			if (content.size() == 0) {
				Map org = new HashMap();
				org.put("itemId", m.get("item_file_id"));
				org.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(org);
				boolean hava = false;
				hava = getPrintItemByOwIdNextNotHava(pm, serviceItem, content,
						hava, m,false,"51008");
				if (!hava) {
					org.put("itemId", m.get("ppxl_id"));
					org.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(org);
					getPrintItemByOwIdNextNotHava(pm, serviceItem, content,
							hava, m,false,"51008");
				}
			} else {
				Map org = new HashMap();
				org.put("itemId", m.get("item_file_id"));
				org.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(org);
				boolean hava = false;
				hava = getPrintItemByOwIdNextHava(pm, serviceItem, content,
						hava, m,false,"51008");
				if (!hava) {
					org.put("itemId", m.get("ppxl_id"));
					org.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(org);
					getPrintItemByOwIdNextHava(pm, serviceItem, content, hava,
							m,false,"51008");
				}
			}
		}

		String timestamp = "" + System.currentTimeMillis();
		Map body = new HashMap();
		body.put("waterName", user.getEmpName());
		body.put("water", water);
		body.put("item", content);
		Map sendContent = new HashMap();
		sendContent.put("code", 51008);
		sendContent.put("explain", "起菜");
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", body);
		gson.toJson(sendContent);

		DgPrintData data = new DgPrintData();
		data.setPrintType("51008");
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);
		dgPrintDataMapper.insertSelective(data);
	}

	private boolean getPrintItemByOwIdNextNotHava(List<DgPrintManager> pm,
			Map serviceItem, List<Map> sendContent, boolean hava, Map m,boolean isCt,String code) {
		if (pm != null && pm.size() > 0) {
			for (DgPrintManager p : pm) {
				if (p.getAreaIds().contains("" + serviceItem.get("consumerId"))) {
					if(isCt) {
						if(p.getCt() == 0) {
							continue;
						}
					}
					Integer copies = p.getCopies();
					//打印多份判断（加单、赠单）
					if(copies!=null && (code.equals("51002") || code.equals("51008"))){
						for (int i = 0; i < copies; i++) {
							Map con = new HashMap();
							con.put("Num", p.getNum());
							con.put("Name", p.getName());
							con.put("qOz", p.getqOZ());
							List<Map> items = new ArrayList<Map>();
							items.add(m);
							con.put("items", items);
							sendContent.add(con);
						}
					}else{
						Map con = new HashMap();
						con.put("Num", p.getNum());
						con.put("Name", p.getName());
						con.put("qOz", p.getqOZ());
						List<Map> items = new ArrayList<Map>();
						items.add(m);
						con.put("items", items);
						sendContent.add(con);
					}


					hava = true;
//					break;
				}
			}
		}
		return hava;
	}

	private boolean getPrintItemByOwIdNextHava(List<DgPrintManager> pm,
			Map serviceItem, List<Map> sendContent, boolean hava, Map m,boolean isCt,String code) {
		if (pm != null && pm.size() > 0) {
			for (DgPrintManager p : pm) {
				if (p.getAreaIds().contains("" + serviceItem.get("consumerId"))) {
					if(isCt) {
						if(p.getCt() == 0) {
							continue;
						}
					} 
					boolean havaContent = false;
					for (Map sc : sendContent) {
						if (sc.get("Name").equals(p.getName())) {
							if (p.getqOZ() == 2) {
								List<Map> items = (List<Map>) sc.get("items");
								items.add(m);
								havaContent = true;
								hava = true;
							}
							break;
						}
					}
					if (!havaContent) {
						Integer copies = p.getCopies();
						//打印多份判断（加单、赠单）
						if(copies!=null && (code.equals("51002") || code.equals("51008"))){
							for (int i = 0; i < copies; i++) {
								Map con = new HashMap();
								con.put("Num", p.getNum());
								con.put("Name", p.getName());
								con.put("qOz", p.getqOZ());
								List<Map> items = new ArrayList<Map>();
								items.add(m);
								con.put("items", items);
								sendContent.add(con);
							}
						}else{
							Map con = new HashMap();
							con.put("Num", p.getNum());
							con.put("Name", p.getName());
							con.put("qOz", p.getqOZ());
							List<Map> items = new ArrayList<Map>();
							items.add(m);
							con.put("items", items);
							sendContent.add(con);
						}

						hava = true;
//						break;
					}
				}
			}
		}
		return hava;
	}

	@Override
	public void insertWmBill(List<Map<String, Object>> dates) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		List<DgPrintManager> WmPrints = dgPrintManagerMapper.selectWmItem();
		if (WmPrints != null && WmPrints.size() > 0) {
			for(Map<String, Object> d:dates){
				String timestamp = "" + System.currentTimeMillis();
				Map content = new HashMap();
				List<Map> item = new ArrayList<Map>();
				content.put("id",d.get("id").toString());
				content.put("note",d.get("note") == null ? "":d.get("note").toString() );
				content.put("createTime", d.get("createTime").toString());
				content.put("amount", d.get("amount"));
				content.put("contactUser", d.get("contactUser") == null ? "":d.get("contactUser").toString());
				content.put("contactTel", d.get("contactTel") == null ? "":d.get("contactTel").toString());
				content.put("contactAddress", d.get("contactAddress") == null ? "":d.get("contactAddress").toString());
				Map print = new HashMap();
				print.put("Name", WmPrints.get(0).getName());
				print.put("qOz", WmPrints.get(0).getqOZ());
				print.put("Num", WmPrints.get(0).getNum());
				List<Map> items = new ArrayList<Map>();
				List<Map<String, Object>> orderDetails= JSON.parseObject(d.get("orderDetails").toString(), new TypeToken<List<Map<String, Object>>>(){}.getType());
				for(Map<String, Object> o:orderDetails){
					Map m = new HashMap();
					m.put("unit",o.get("unit").toString()); //规格
					m.put("typeid",o.get("typeid").toString()); //小类id
					m.put("typename",o.get("typename").toString()); //小类名称
					m.put("price",o.get("price")); //价格
					m.put("discountPrice",o.get("discountPrice")); //打折后价格
					m.put("goodsid",o.get("goodsid").toString()); //品项id
					m.put("goodsname",o.get("goodsname").toString()); //品项名称
					m.put("num",o.get("num")); //品项数量
					items.add(m);
				}
				print.put("items", items);	
				item.add(print);
				content.put("item", item);
				if (item.size() == 0) {
					return;
				}
				Map sendContent = new HashMap();
				sendContent.put("code", 51008);
				sendContent.put("explain", "外卖单");
				sendContent.put("username", "admin");
				sendContent.put("timestamp", timestamp);
				sendContent.put("body", content);

				DgPrintData data = new DgPrintData();
				data.setPrintType("51008");
				data.setUniqueIdentif(timestamp);
				data.setContent(gson.toJson(sendContent));
				data.setSuccess(0);
				dgPrintDataMapper.insertSelective(data);
			}
		}
	}

	@Override
	public void insertPreAddBill(List<DgPreOrderbill> dgPreOrderbills,Integer ConsumerId,DgOpenWater water,String colorPreNum) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map serviceItem = new HashMap();
		serviceItem.put("preNum", colorPreNum);
		List<Map> item = getPreAddPrintItemByOwId(dgPreOrderbills,""+ConsumerId);
		serviceItem.put("item", item);
		serviceItem.put("waterNum",water.getOwNum());
		serviceItem.put("seatName",water.getSeatName());
		serviceItem.put("seatId",water.getSeatId());
		serviceItem.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if (item.size() == 0) {
			return;
		}
		String timestamp = "" + System.currentTimeMillis();
		Map sendContent = new HashMap();
		sendContent.put("code", 51010);
		sendContent.put("explain", "预加单");
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", serviceItem);

		DgPrintData data = new DgPrintData();
		data.setPrintType("51010");
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);
		dgPrintDataMapper.insertSelective(data);
	}
	
	private List<Map> getPreAddPrintItemByOwId(List<DgPreOrderbill> dgPreOrderbills,String ConsumerId) {
		List<Map> sendContent = new ArrayList<Map>();
		for (DgPreOrderbill m : dgPreOrderbills) {
			if (sendContent.size() == 0) {
				Map orgs = new HashMap();
				orgs.put("itemId", m.getItemFileId());
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);
				boolean hava = false;
				hava = getPrePrintItemByOwIdNextNotHava(pm,
						sendContent, hava, m,ConsumerId);
				if (!hava) {
					orgs.put("itemId", m.getPxxlId());
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrePrintItemByOwIdNextNotHava(pm, sendContent,
							hava, m,ConsumerId);
				}
			} else {
				Map orgs = new HashMap();
				orgs.put("itemId", m.getItemFileId());
				orgs.put("type", 1);
				List<DgPrintManager> pm = dgPrintManagerSMapper
						.selectPrintManagerByItem(orgs);
				boolean hava = false;
				hava = getPrePrintItemByOwIdNextNotHava(pm, sendContent,
						hava, m,ConsumerId);
				if (!hava) {
					orgs.put("itemId", m.getPxxlId());
					orgs.put("type", 2);
					pm = dgPrintManagerSMapper.selectPrintManagerByItem(orgs);
					getPrePrintItemByOwIdNextNotHava(pm, sendContent,
							hava, m,ConsumerId);
				}
			}
		}

		return sendContent;
	}
	
	private boolean getPrePrintItemByOwIdNextNotHava(List<DgPrintManager> pm, List<Map> sendContent, boolean hava, DgPreOrderbill m,String ConsumerId) {
		if (pm != null && pm.size() > 0) {
			for (DgPrintManager p : pm) {
				boolean conArea = false;
				String[] areaIds = p.getAreaIds().split(",");
				for(String area:areaIds){
					if(area.equals(ConsumerId)){
						conArea = true;
						break;
					}
				}
				if (conArea) {
					boolean havaContent = false;
					for (Map sc : sendContent) {
						if (sc.get("Name").equals(p.getName())) {
							if (p.getqOZ() == 2) {
								List<Map> items = (List<Map>) sc.get("items");
								Map item = new HashMap();
								item.put("itemCode",m.getItemNum());
								item.put("itemName",m.getItemName());
								item.put("itemCount",m.getItemFileCount());
								item.put("unit",m.getUnit());
								item.put("price",m.getPrice());
								items.add(item);
								havaContent = true;
								hava = true;
							}
							break;
						}
					}
					if (!havaContent) {
						Map con = new HashMap();
						con.put("Num", p.getNum());
						con.put("Name", p.getName());
						con.put("qOz", p.getqOZ());
						List<Map> items = new ArrayList<Map>();
						Map item = new HashMap();
						item.put("itemCode",m.getItemNum());
						item.put("itemName",m.getItemName());
						item.put("itemCount",m.getItemFileCount());
						item.put("unit",m.getUnit());
						item.put("price",m.getPrice());
						items.add(item);
						con.put("items", items);
						sendContent.add(con);
						hava = true;
						break;
					}
				}
			}
		}
		return hava;
	}
	
	@Override
	public void insertCtAddBillPrint(Integer owId, Double money) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Map serviceItem = dgPrintDataMapper.getPrintOwServiceInfo(owId);
		serviceItem.put("subtotal", money);
		List<Map> item = getPrintItemByOwId(owId, serviceItem,true,"51002");
		serviceItem.put("item", item);
		if (item.size() == 0) {
			return;
		}
		String timestamp = "" + System.currentTimeMillis();
		Map sendContent = new HashMap();
		sendContent.put("code", 51002);
		sendContent.put("explain", "加单");
		sendContent.put("username", "admin");
		sendContent.put("timestamp", timestamp);
		sendContent.put("body", serviceItem);

		DgPrintData data = new DgPrintData();
		data.setPrintType("51002");
		data.setUniqueIdentif(timestamp);
		data.setContent(gson.toJson(sendContent));
		data.setSuccess(0);
		dgPrintDataMapper.insertSelective(data);
	}

}