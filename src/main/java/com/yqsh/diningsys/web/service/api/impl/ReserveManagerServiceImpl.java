package com.yqsh.diningsys.web.service.api.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.catering.web.mq.DepositOrderMessage;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.dao.api.DgCallServiceMapper;
import com.yqsh.diningsys.web.dao.api.DgReserveManagerMapper;
import com.yqsh.diningsys.web.dao.api.DgReserveSeatidsMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumptionAreaMapper;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatids;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatidsList;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.service.api.ReserveManagerService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.sevlet.CacheInit;
import com.yqsh.diningsys.web.util.OnlineHttp;
import com.yqsh.diningsys.web.util.TableQueryUtil;

@Service("reserveManagerServiceImpl")
public class ReserveManagerServiceImpl implements ReserveManagerService {
	@Resource
	private DgReserveManagerMapper dgReserveManagerMapper;
	@Resource
	private DgReserveSeatidsMapper dgReserveSeatidsMapper;
	@Resource
	private DeskBusinessSettingService deskBusinessSettingService;
	@Resource
	private DgPosMapper dgPosMapper;
	@Resource
	private DgConsumerSeatMapper dgConsumerSeatMapper;
	@Resource
	private DgCallServiceMapper dgCallServiceMapper;
	@Override
	public Page<DgReserveManager> getListByPage(DgReserveManager page) {
		// TODO Auto-generated method stub
		String tableEnd = TableQueryUtil.tableNameUtilWithMonthSingle(page.getSearchTime());
		page.setTableEnd(tableEnd);
		Integer totle = dgReserveManagerMapper.getCount(page);
		List<DgReserveManager> list = dgReserveManagerMapper
				.getListByPage(page);
		return PageUtil.getPage(totle, page.getPage(), list, page.getRows());
	}

	
	@Override
	public Page<DepositOrderMessage> getOnlinePageList(DepositOrderMessage page) {
		// TODO Auto-generated method stub
		try {
			String dataString = OnlineHttp.onlineReserveInfo(page.getStartRow()/page.getRows() +1,page.getRows(),page.getSearch(),page.getState());
			JSONObject obj = JSONObject.parseObject(dataString);
			Map contentMap = JSONObject.parseObject(obj.get("content").toString(),Map.class);
			Integer totle = (Integer)contentMap.get("total");
			List<DepositOrderMessage> list = JSONArray.parseArray(contentMap.get("rows").toString(), DepositOrderMessage.class);
			return PageUtil.getPage(totle, page.getStartRow()/page.getRows(), list, page.getRows());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
	public int deleteByIds(String ids) {
		// TODO Auto-generated method stub
		List id = new ArrayList();
	    Collections.addAll(id,ids.split(","));
	    
	    
	    //发送给线上用户,删除信息
	    List<DgReserveManager> drms = dgReserveManagerMapper.selectByIds(id);
		String buffer = "";
		for(DgReserveManager drm:drms){
			if(StringUtil.isNotEmpty(drm.getYdResoure())){
				buffer += drm.getYdResoure()+",";	
			}
		}
		
		if(buffer.length() > 0){
			String onlineIds = buffer.substring(0, buffer.length()-1);
		    String s = OnlineHttp.onlineReserveInfo(onlineIds,false,"店内已取消本次预定!");		
			if(s == null){
				throw new RuntimeException();  //回滚	
			}	
		}
		
		
		//删除本地记录
	    dgReserveSeatidsMapper.deleteByParentIds(id);
		dgReserveManagerMapper.deleteByIds(id);
		return 0;
	}

	@Override
	public int update(DgReserveManager record) {
		// TODO Auto-generated method stub
		return dgReserveManagerMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<DgConsumerSeat> getAllSeat(DgReserveManager search) {
		// TODO Auto-generated method stub
		DgPos dgPos = dgPosMapper.selectPosByPosId(search.getPosId());
		List<Integer> integers = StringUtil.arrayToList(dgPos.getConsumerAreas());
		search.setPosConsumerIds(integers);
		String bjString = getProperty("reserve.bj");
		if(StringUtil.isNotEmpty(bjString)){
			List<Integer> bjIds = StringUtil.arrayToList(bjString);
			search.setBjIds(bjIds);	
		}
		String interTime = getProperty("reserve.interval.time");
		if(StringUtil.isEmpty(interTime)){
			interTime = "4";
		}
		search.setInterTime(Integer.valueOf(interTime));
		return dgReserveManagerMapper.getAllSeat(search);
	}

	@Override
	public DgReserveManager selectById(Integer id) {
		// TODO Auto-generated method stub
		return dgReserveManagerMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map insertOrUpdate(DgReserveManager dgReserveManager,
			DgReserveSeatidsList list) {
		Map result = new HashMap();
		String interTime = getProperty("reserve.interval.time");
		if(StringUtil.isEmpty(interTime)){
			interTime = "4";
		}
		//先检测客位在预定时间内是否已预定
		Map orgs = new HashMap();
		orgs.put("time",dgReserveManager.getYdTime());
		orgs.put("id",dgReserveManager.getId());
		orgs.put("seats",list.getSeatIds());
		orgs.put("interTime",Integer.valueOf(interTime));
		if(StringUtil.isEmpty(dgReserveManager.getTime())){
			dgReserveManager.setTime(new Date());	
		}
		List<String> exits = dgReserveManagerMapper.getYdSeat(orgs);
		if(exits != null && exits.size() > 0){
			result.put("success",false);
			String msg = "";
			for(int i=0;i<exits.size();i++){
				msg += exits.get(i)+";";
			}
			msg = msg.substring(0, msg.length()-1);
			msg += "客位已预定,请重新选择!";
			result.put("msg",msg);
			return result;
		} if(dgReserveManager.getId() == null) {
			Gson gson = new Gson();
			List<String> ornums = gson.fromJson(deskBusinessSettingService
					.createFlowNumber("", "", 1,
							SerialRulEnum.YD), new TypeToken<List<String>>() {
			}.getType());
			dgReserveManager.setYdNum(ornums.get(0));
			dgReserveManagerMapper.insertBackId(dgReserveManager);
		} else {
			dgReserveSeatidsMapper.deleteZsByParentId(dgReserveManager.getId());
			dgReserveManagerMapper.updateByPrimaryKey(dgReserveManager);
		}
		for(int i=0;i<list.getSeatIds().size();i++) {
			list.getSeatIds().get(i).setReserveId(dgReserveManager.getId());
			dgReserveSeatidsMapper.insertSelective(list.getSeatIds().get(i));
		}
		result.put("success",true);
		//yd_resource 为线上id
		if(StringUtil.isNotEmpty(dgReserveManager.getYdResoure())){
			//发送消息给线上
			List<DgConsumerSeat> dcss = dgReserveManagerMapper.selectSeatByIds(list.getSeatIds());
			StringBuffer buffer = new StringBuffer("预定客位为:");
			for(DgConsumerSeat dcs:dcss){
				buffer.append(dcs.getName()+",");
			}
			//发送线上
			String s = OnlineHttp.onlineReserveInfo(dgReserveManager.getYdResoure(),true,buffer.toString());
			if(s == null){
				result.put("success",false);
				result.put("msg","连接线上平台失败!");
				throw new RuntimeException();  //回滚
			}
		}
		return result;
	}

	@Override
	public List<DgReserveSeatids> selectYdSeatById(Integer id) {
		// TODO Auto-generated method stub
		return dgReserveSeatidsMapper.selectByParentId(id);
	}

	@Override
	public int updateSeatToYd(String time) {
		// TODO Auto-generated method stub
		List<DgConsumerSeat> ydSeats = dgReserveManagerMapper.getSeatToYd30(time);
		List<DgConsumerSeat> initSeats = dgReserveManagerMapper.getSeatToInit30(time);
		dgReserveManagerMapper.updateNotArriveDgReserve(time);
		String ydStr = "";
		for(DgConsumerSeat s:ydSeats){
			s.setSeatState(4);
			ydStr += s.getUuidKey()+",";
			dgConsumerSeatMapper.updateSeatState(s);	
		}
		
		String initStr = "";
		for(DgConsumerSeat s:initSeats){
			s.setSeatState(1);
			initStr += s.getUuidKey()+",";
			dgConsumerSeatMapper.updateSeatState(s);	
		}
		if(ydStr.length() >0){
			OnlineHttp.onlineSeatModify(ydStr.substring(ydStr.length()-1), "4");	
		}
		
		if(initStr.length() >0){
			OnlineHttp.onlineSeatModify(initStr.substring(initStr.length()-1), "1");	
		}
		return 0;
	}

	@Override
	public DgReserveManager selectYdFromSeatId(Map orgs) {
		// TODO Auto-generated method stub
		return dgReserveManagerMapper.selectYdFromSeatId(orgs);
	}

	@Override
	public int insertOnlineReserve(DepositOrderMessage dom) {
		// TODO Auto-generated method stub
		int count = dgCallServiceMapper.selectReserveCount();
		if(count == 0){
			DgCallService dcs = new DgCallService();
			dcs.setType(5); //线上预定消息
			dcs.setContent("收到预定消息");
			dcs.setSeatId(1); //设置为计数	
			dgCallServiceMapper.insert(dcs);
		}
		return 1;
	}
	
	
	public  String getProperty(String name){
		InputStream is=StringUtil.class.getResourceAsStream("/application.properties");  
		Properties p = new Properties();
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p.getProperty(name);
	}

	@Override
	public DgCallService selectCallInfo(Integer id) {
		// TODO Auto-generated method stub
		return dgCallServiceMapper.selectByPrimaryKey(id);
	}


	@Override
	public int refuseOnline(String ids, String msg) {
		// TODO Auto-generated method stub
		//发送线上
		String s = OnlineHttp.onlineReserveInfo(ids,false,msg);		
		if(s == null){
			throw new RuntimeException();  //回滚	
		}
		return 0;
	}

	@Override
	public int deleteCallInfo() {
		// TODO Auto-generated method stub
		if(dgCallServiceMapper.selectReserveCount() > 0){
			List<DgCallService> dcss = dgCallServiceMapper.selectReserve();
			for(DgCallService dcs:dcss){
				dgCallServiceMapper.deleteByPrimaryKey(dcs.getId());
			}
		}
		return 0;
	}


	@Override
	public int deleteOnlineCancel(String orderId) {
		// TODO Auto-generated method stub
		DgReserveManager drm = dgReserveManagerMapper.selectByOrderId(orderId);
		if(drm != null){
			dgReserveSeatidsMapper.deleteByParentId(drm.getId());
			dgReserveManagerMapper.deleteByPrimaryKey(drm.getId());
		}
		return 0;
	}

	@Override
	public int noticeNotArrive(String time) {
		// TODO Auto-generated method stub
		Map org = new HashMap();
		org.put("time",time);
		List<DgReserveManager> drms = dgReserveManagerMapper.selectReserverLatelyTenMintues(org);
		String onlineYds = "";
		for(DgReserveManager drm:drms){
			if(StringUtil.isNotEmpty(drm.getYdResoure())){
				onlineYds += drm.getYdResoure()+",";
			}
		}
		if(onlineYds.length() > 0){
			onlineYds = onlineYds.substring(0, onlineYds.length()-1);
			//发送到线上平台
			String s = OnlineHttp.onlineSendRemindersInfo(onlineYds);		
			if(s == null){
				throw new RuntimeException();  //回滚	
			}
		}

		if(!drms.isEmpty()){
			//更新为已通知态
			dgReserveManagerMapper.updateDgReserveManagerAlreadyNotice(drms);
		}

		return 0;
	}

	@Override
	public List<DgReserveManager> getDgReserveManagerList(DgReserveManager dgReserveManager) {
		return dgReserveManagerMapper.getDgReserveManagerList(dgReserveManager);
	}

}