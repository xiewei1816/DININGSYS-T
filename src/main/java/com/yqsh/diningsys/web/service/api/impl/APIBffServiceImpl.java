package com.yqsh.diningsys.web.service.api.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.DateTimeComputing;
import com.yqsh.diningsys.web.dao.businessMan.DgSeatChargingSchemeMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwModifySeatMapper;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwModifySeat;
import com.yqsh.diningsys.web.service.api.APIBffService;
import com.yqsh.diningsys.web.service.api.APIModifyService;

@Service
public class APIBffServiceImpl implements APIBffService {
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private DgOpenWaterMapper dgOpenWaterMapper;
	@Autowired
	private DgOwModifySeatMapper dgOwModifySeatMapper;
	@Autowired
	private DgSeatChargingSchemeMapper dgSeatChargingSchemeMapper;
	@Autowired
	private APIModifyService apiModifyService;
	@Override
	public Map getBffInfo(String openNum) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(openNum);
		String opentime = water.getFirstTime();
		DgOwModifySeat ms = dgOwModifySeatMapper.selectByOwNum(openNum);
		if (ms != null) {
			opentime = ms.getTime();
		}
		map.put("openTime", opentime);
		map.put("openNum", water.getOwNum());
		map.put("seatName", water.getSeatName());
		map.put("weaterName", water.getWaiterName());
		if (water.getPrivateRoomType() == null
				|| water.getPrivateRoomType() == 0) {
			// 没有包房方案
			map.put("BffName", "");
			map.put("bff", 0.0);
			if (water.getClosedTime() == null) {
				map.put("endTime", "");
				map.put("Xfsc", DateTimeComputing.compution(opentime));
			} else {
				map.put("endTime", water.getClosedTime());
				map.put("Xfsc", DateTimeComputing.compution(
						water.getClosedTime(), opentime));
			}

		} else {
			DgSeatChargingScheme dscs = dgSeatChargingSchemeMapper
					.selectByPrimaryKey(water.getPrivateRoomType());
			map.put("BffName", dscs.getName());
			if (water.getClosedTime() == null) {
				map.put("Xfsc",
						DateTimeComputing.compution(water.getFirstTime()));
				try {
					map.put("bff",
							apiModifyService.calculationBff(
									water.getPrivateRoomType(),
									df.parse(opentime), new Date()));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			} else {
				map.put("endTime", water.getClosedTime());
				map.put("Xfsc", DateTimeComputing.compution(
						water.getClosedTime(), water.getFirstTime()));
				try {
					map.put("bff",
							apiModifyService.calculationBff(
									water.getPrivateRoomType(),
									df.parse(opentime),
									df.parse(water.getClosedTime())));
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
		}
		map.put("Chargings", dgSeatChargingSchemeMapper
				.seleAllNotContainSelf(water.getPrivateRoomType()));
		return map;
	}

	@Override
	public Map updateBff(String openNum, Integer id) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		DgOpenWater water = dgOpenWaterMapper.selectByOpenWaterByNum(openNum);
		if (water.getPrivateRoomType() == null || water.getPrivateRoomType() == 0) {
			map.put("error", APIEnumDefine.M0500001);
		} else {
			water.setPrivateRoomType(id);
			dgOpenWaterMapper.updateByPrimaryKeySelective(water);
			map = getBffInfo(openNum);
		}
		return map;
	}

	@Override
	public List<DgSeatChargingScheme> getAllBff() {
		// TODO Auto-generated method stub
		return dgSeatChargingSchemeMapper.seleAll();
	}

}