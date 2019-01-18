package com.yqsh.diningsys.web.service.api.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.archive.DgConsumerSeatMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.web.dao.api.QueryPrintMapper;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.dao.archive.TbOrgMapper;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.service.api.QueryPrintService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;

@Service
public class QueryPrintServiceImpl implements QueryPrintService {
	
	@Resource
	private QueryPrintMapper queryPrintMapper;
	@Resource
	private DeskBusinessSettingService deskBusinessSettingService;
	@Resource
	private DgPosMapper dgPosMapper;
	@Resource
	private TbOrgMapper tbOrgMapper;

	@Resource
	private APICheckServiceMapper apiCheckServiceMapper;

	@Resource
	private DgConsumerSeatMapper dgConsumerSeatMapper;
	
	@Override
	public Map<String, Object> selectTeamSeatMembersList(String teamMembers) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("teamMembers", teamMembers);
		return queryPrintMapper.selectTeamSeatMembersList(params);
	}
	
	@Override
	public Map<String, Object> selectGuestList(String owNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("owNum", owNum);
		return queryPrintMapper.selectGuestList(params);
	}

	@Override
	public List<Map<String, Object>> selectGuestItemFileList(String id) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return queryPrintMapper.selectGuestItemFileList(params);
	}
	
	@Override
	public Map<String, Object> selectCheckDocSeatList(String owNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("owNum", owNum);
		return queryPrintMapper.selectCheckDocSeatList(params);
	}

	@Override
	public List<Map<String, Object>> selectCheckDocList(String owNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("owNum", owNum);
		return queryPrintMapper.selectCheckDocList(params);
	}

	@Override
	public List<Map<String, Object>> selectCheckItemFileList(String sfId) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("sfId", sfId);
		return queryPrintMapper.selectCheckItemFileList(params);
	}

	@Override
	public List<Map<String, Object>> selectTeamMembersList() {
		// TODO Auto-generated method stub
		return queryPrintMapper.selectTeamMembersList();
	}

	@Override
	public List<Map<String, Object>> selectTeamBySeatNameList(String teamMembers) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("teamMembers", teamMembers);
		return queryPrintMapper.selectTeamBySeatNameList(params);
	}

	@Override
	public List<Map<String, Object>> selectTeamList(String seatId,String teamMembers,String owNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("seatId", seatId);
		params.put("teamMembers", teamMembers);
		params.put("owNum", owNum);
		return queryPrintMapper.selectTeamList(params);
	}

	@Override
	public int updWaterJoinTeamNotes(String owNum) {
		// TODO Auto-generated method stub

		DgOpenWater dgOpenWater = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);

		//如果退出团队的营业流水的是主团队，并且该团队的营业流水数量大于1，则修改该团队里剩下的其余流水的主客位
		if(dgOpenWater.getTeamMainSeat().equals(dgOpenWater.getSeatId())){
			List<DgOpenWater> dgOpenWaters = apiCheckServiceMapper.selectOpenwaterByTeamNum(dgOpenWater.getTeamMembers());
			if(dgOpenWaters.size() > 1){
				Iterator<DgOpenWater> iterator = dgOpenWaters.iterator();
				while(iterator.hasNext()){
					if(iterator.next().getOwNum().equals(dgOpenWater.getOwNum())){
						iterator.remove();
					}
				}
				Integer otherMainSeatId = dgOpenWaters.get(0).getSeatId();
				Map<String,Object> map = new HashMap<>();
				map.put("mainSeatId",otherMainSeatId);
				map.put("list",dgOpenWaters);
				apiCheckServiceMapper.updateOpenWaterMainSeat(map);
			}
		}

		Map<String,Object> params = new HashMap<String, Object>();
		params.put("owNum", owNum);
		
		//获取营业流水pos信息
		Map<String, Object> map = queryPrintMapper.getTeamMembersList(params);
		String isTeam = "0";
		String teamMainSeat = map.get("seatId")+"";
		
		String openPos = map.get("openPos")+"";
		Gson gson = new Gson();
		DgPos pos = new DgPos();
		pos.setId(Integer.valueOf(openPos));
		pos = dgPosMapper.getPosByID(pos);
		TbOrg org = new TbOrg();
		org.setId(Integer.valueOf(pos.getOrganization()));
		org = tbOrgMapper.getOrgByID(org);

		// 团队流水号
		List<String> teamOrnums = gson.fromJson(deskBusinessSettingService
				.createFlowNumber(org.getOrgCode(), pos.getNumber(), 1,
						SerialRulEnum.TDHM), new TypeToken<List<String>>() {
		}.getType());
		String teamMembers = teamOrnums.get(0);
		//is_team = 0  team_main_seat = 自身seat
		params.put("isTeam", isTeam);
		params.put("teamMainSeat", teamMainSeat);
		params.put("teamMembers", teamMembers);
		
		return queryPrintMapper.updWaterJoinTeamNotes(params);
	}

	@Override
	public List<Map<String, Object>> selectClearingList(String seatId,
			String openBis, String consArea, String firstTime) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("seatId", seatId);
		params.put("openBis", setTranscodingVal(openBis));
		params.put("consArea", consArea);
		params.put("firstTime", firstTime);
		return queryPrintMapper.selectClearingList(params);
	}
	
	@Override
	public Map<String, Object> selectClearingBaseList(String cwNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("cwNum", cwNum);
		return queryPrintMapper.selectClearingBaseList(params);
	}
	
	@Override
	public List<Map<String, Object>> selectOpenWaterBaseList(String cwNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("cwNum", cwNum);
		return queryPrintMapper.selectOpenWaterBaseList(params);
	}

	@Override
	public List<Map<String, Object>> selectClearingWayBaseList(String id) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return queryPrintMapper.selectClearingWayBaseList(params);
	}
	
	@Override
	public List<Map<String, Object>> selectReceiptFileList(String id) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return queryPrintMapper.selectReceiptFileList(params);
	}
	
	@Override
	public List<Map<String, Object>> selectDocListOpenWater(Integer seatId) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("seatId", seatId);
		return queryPrintMapper.selectDocListOpenWater(params);
	}
	
	@Override
	public Map<String, Object> selectSeatAreaList(Integer seatId) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("seatId", seatId);
		return queryPrintMapper.selectSeatAreaList(params);
	}

	@Override
	public Integer updRepealWater(String owNum) {
		boolean flag = true;
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("owNum", owNum);
		//获取营业流水【主营业流水】信息

		DgOpenWater dgOpenWater = apiCheckServiceMapper.selectOpenWaterByowNum(owNum);

        DgPos dgPos = dgPosMapper.selectPosByPosId(dgOpenWater.getOpenPos());
        TbOrg org = new TbOrg();
        org.setId(Integer.valueOf(dgPos.getOrganization()));
        org = tbOrgMapper.getOrgByID(org);

        String joinTeamNotes = dgOpenWater.getJoinTeamNotes();

		//转账流水的原客座ID
		Integer oldSeatId = Integer.parseInt(joinTeamNotes.split("，")[1].split("：")[1]);

		//查询原客座的现在状态
		DgConsumerSeat dgConsumerSeat = dgConsumerSeatMapper.selectByPrimaryKey(oldSeatId);

		if(dgConsumerSeat.getSeatState() == 3){
			return 3;
		}
		if(dgConsumerSeat.getSeatState() == 4){
			return 4;
		}

		Gson gson = new Gson();

        List<String> teamOrnums = gson.fromJson(deskBusinessSettingService
                .createFlowNumber(org.getOrgCode(), dgPos.getNumber(), 1,
                        SerialRulEnum.TDHM), new TypeToken<List<String>>() {
        }.getType());
        params.put("newTeamNum",teamOrnums.get(0));
        params.put("seatId",oldSeatId);

		if(dgConsumerSeat.getSeatState() == 1){//空闲
			queryPrintMapper.updateOldSeatState(oldSeatId);
		}/*else if(dgConsumerSeat.getSeatState() == 2 || dgConsumerSeat.getSeatState() == 5){//占用或埋单
		}*/
        queryPrintMapper.updRepealWaterManyTeam(params);
        params.put("owNum",dgOpenWater.getTransferTarget());
        queryPrintMapper.updRepealWaterSeatAmount(params);

        /*DgOpenWater dgOpenWater1 = apiCheckServiceMapper.selectOpenWaterByowNum(dgOpenWater.getTransferTarget());

        //如果为主客位，则修改该流水下的流水数量
		if(dgOpenWater1.getTeamMainSeat().equals(dgOpenWater1.getSeatId())){
			Integer seatAmount = dgOpenWater1.getSeatAmount();
			if(seatAmount < 2){
				queryPrintMapper.updateOpenWaterNonTeam(dgOpenWater1.getOwNum());
			}
		}*/
        return 1;
		/*//根据撤销转账营业流水号更改客位状态为占用
		int num1 = queryPrintMapper.updRepealWaterSeatState(params);
		//根据撤销转账营业流水号更改该营业流水的【转账的营业流水号 、转账备注、转账时间
		int num2 = queryPrintMapper.updRepealWaterManyTeam(params);
		//根据撤销转账营业流水号更改该营业流水主营业流水下客位数量-1
		Map<String,Object> zparams = new HashMap<String, Object>();
		String transferTarget = map.get("transferTarget")+"";
		zparams.put("owNum", transferTarget);
		int num3 = queryPrintMapper.updRepealWaterSeatAmount(zparams);
		if(num1 * num2 * num3 == 0){
			flag = false;
		}
		return flag;*/
	}
	
	@Override
	public List<Map<String, Object>> selectIntoOrOutOpenWaterList(
			String joinTeamNotes,String expArea,String clientSeat,String owNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("joinTeamNotes", joinTeamNotes);
		params.put("expArea", expArea);
		params.put("clientSeat", clientSeat);
		params.put("owNum", owNum);
		return queryPrintMapper.selectIntoOrOutOpenWaterList(params);
	}
	
	@Override
	public List<Map<String, Object>> selectIntoOrOutItemFileList(String owNum) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("owNum", owNum);
		return queryPrintMapper.selectIntoOrOutItemFileList(params);
	}

	@Override
	public List<Map<String, Object>> getNoBalenceDataList() {
		return queryPrintMapper.getNoBalenceDataList();
	}
	
	/**
	 * 中文乱码转码
	 * @param value
	 * @return
	 */
	public String setTranscodingVal(String value){
		try {
			if(value != null && value != ""){
				value = new String(value.getBytes("iso8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
}