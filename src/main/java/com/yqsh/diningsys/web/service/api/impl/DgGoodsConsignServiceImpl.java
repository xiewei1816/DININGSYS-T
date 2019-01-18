package com.yqsh.diningsys.web.service.api.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.api.DgGoodsConsignMapper;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgGoodsConsign;
import com.yqsh.diningsys.web.service.api.DgGoodsConsignService;

@Service
public class DgGoodsConsignServiceImpl implements DgGoodsConsignService {
	@Resource
	private DgGoodsConsignMapper dgGoodsConsignMapper;

	@Override
	public List<DgGoodsConsign> selectGoodsConsign(Integer id, String isDel, String jcStartTime,
			String jcEndTime, String goodsName, String goodsColor,
			String gcPos, String qzStartTime, String qzEndTime,
			String clientName, String clientSeat, String expArea,
			Integer aboveDays, String goodsExpirationDate, 
			String gcEndTime,String gcFlag) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("isDel", isDel);
		params.put("jcStartTime", jcStartTime);
		params.put("jcEndTime", jcEndTime);
		params.put("goodsName", goodsName);
		params.put("goodsColor", goodsColor);
		params.put("gcPos", gcPos);
		params.put("qzStartTime", qzStartTime);
		params.put("qzEndTime", qzEndTime);
		params.put("clientName", clientName);
		params.put("clientSeat", clientSeat);
		params.put("expArea", expArea);
		params.put("aboveDays", aboveDays);
		params.put("goodsExpirationDate", goodsExpirationDate);
		params.put("gcEndTime", gcEndTime);
		params.put("gcFlag", gcFlag);
		return dgGoodsConsignMapper.selectGoodsConsign(params);
	}

	@Override
	public int insertGoodsConsign(String clientName, String clientPhone,
			String clientSeat, String goodsType, String goodsCode,
			String goodsName, Integer goodsNumber, String goodsSpecification,
			String goodsColor, String goodsExpirationDate, String goodsExplain,
			String gcFlag, String gcPos, String gcOperator, String gcStartTime,
			String gcEndTime, String gcAddress, String clWay, String clPos, 
			String clOperator,String clExplain) {
		// TODO Auto-generated method stub
		DgGoodsConsign dgc = new DgGoodsConsign();
		dgc.setClientName(setNUllVal(clientName));
		dgc.setClientPhone(setNUllVal(clientPhone));
		dgc.setClientSeat(setNUllVal(clientSeat));
		dgc.setGoodsType(setNUllVal(goodsType));
		dgc.setGoodsCode(setNUllVal(goodsCode));
		dgc.setGoodsName(setNUllVal(goodsName));
		dgc.setGoodsNumber(goodsNumber);
		dgc.setGoodsSpecification(setNUllVal(goodsSpecification));
		dgc.setGoodsColor(setNUllVal(goodsColor));
		dgc.setGoodsExpirationDate(setNUllVal(goodsExpirationDate));
		dgc.setGoodsExplain(setNUllVal(goodsExplain));
		dgc.setGcFlag(setNUllVal(gcFlag));
		dgc.setGcPos(setNUllVal(gcPos));
		dgc.setGcOperator(setNUllVal(gcOperator));
		dgc.setGcStartTime(setNUllVal(gcStartTime));
		dgc.setGcEndTime(setNUllVal(gcEndTime));
		dgc.setGcAddress(setNUllVal(gcAddress));
		dgc.setClWay(setNUllVal(clWay));
		dgc.setClPos(setNUllVal(clPos));
		dgc.setClOperator(setNUllVal(clOperator));
		dgc.setClExplain(setNUllVal(clExplain));
		return dgGoodsConsignMapper.insertGoodsConsign(dgc);
	}

	@Override
	public int updateGoodsConsign(Integer id, String clientName,
			String clientPhone, String clientSeat, String goodsType,
			String goodsCode, String goodsName, Integer goodsNumber,
			String goodsSpecification, String goodsColor,
			String goodsExpirationDate, String goodsExplain, String gcFlag,
			String gcPos, String gcOperator, String gcStartTime,
			String gcEndTime, String gcAddress, String clWay, String clPos,
			String clOperator,String clExplain) {
		// TODO Auto-generated method stub
		DgGoodsConsign dgc = new DgGoodsConsign();
		dgc.setId(id);
		dgc.setClientName(setNUllVal(clientName));
		dgc.setClientPhone(setNUllVal(clientPhone));
		dgc.setClientSeat(setNUllVal(clientSeat));
		dgc.setGoodsType(setNUllVal(goodsType));
		dgc.setGoodsCode(setNUllVal(goodsCode));
		dgc.setGoodsName(setNUllVal(goodsName));
		dgc.setGoodsNumber(goodsNumber);
		dgc.setGoodsSpecification(setNUllVal(goodsSpecification));
		dgc.setGoodsColor(setNUllVal(goodsColor));
		dgc.setGoodsExpirationDate(setNUllVal(goodsExpirationDate));
		dgc.setGoodsExplain(setNUllVal(goodsExplain));
		dgc.setGcFlag(setNUllVal(gcFlag));
		dgc.setGcPos(setNUllVal(gcPos));
		dgc.setGcOperator(setNUllVal(gcOperator));
		dgc.setGcStartTime(setNUllVal(gcStartTime));
		dgc.setGcEndTime(setNUllVal(gcEndTime));
		dgc.setGcAddress(setNUllVal(gcAddress));
		dgc.setClWay(setNUllVal(clWay));
		dgc.setClPos(setNUllVal(clPos));
		dgc.setClOperator(setNUllVal(clOperator));
		dgc.setClExplain(setNUllVal(clExplain));
		return dgGoodsConsignMapper.updateGoodsConsign(dgc);
	}
	
	@Override
	public int addGoodsConsignByQz(Integer id, String gcFlag, String qzTime, String qzPos, String qzOperator) {
		// TODO Auto-generated method stub
		DgGoodsConsign dgc = new DgGoodsConsign();
		dgc.setId(id);
		dgc.setGcFlag(gcFlag);
		dgc.setQzTime(new Date());
		dgc.setQzPos(setTranscodingVal(qzPos));
		dgc.setQzOperator(setTranscodingVal(qzOperator));
		return dgGoodsConsignMapper.addGoodsConsignByQz(dgc);
	}

	@Override
	public int deleteGoodsConsign(String editIds) {
		// TODO Auto-generated method stub
		DgGoodsConsign dgc = new DgGoodsConsign();
		List<String> list = new ArrayList<String>();
			for(String str : editIds.split(",")){
				list.add(str);
			}
			dgc.setIds(list);
		return dgGoodsConsignMapper.delete(dgc);
	}

	@Override
	public int deleteGoodsConsignTrash(String editIds) {
		// TODO Auto-generated method stub
		DgGoodsConsign dgc = new DgGoodsConsign();
		List<String> list = new ArrayList<String>();
		for(String str : editIds.split(",")){
			list.add(str);
		}
		dgc.setIds(list);
		return dgGoodsConsignMapper.deleteTrash(dgc);
	}

	@Override
	public int replyGoodsConsign(String editIds) {
		// TODO Auto-generated method stub
		DgGoodsConsign dgc = new DgGoodsConsign();
		List<String> list = new ArrayList<String>();
		for(String str : editIds.split(",")){
			list.add(str);
		}
		dgc.setIds(list);
		return dgGoodsConsignMapper.replyDgc(dgc);
	}
	
	/** 物品寄存种类 增、删、改、查 **/
	@Override
	public int insertGoodsType(String gtName, String isRemind) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gtName", setTranscodingVal(gtName));
		params.put("isRemind", setTranscodingVal(isRemind));
		return dgGoodsConsignMapper.insertGoodsType(params);
	}

	@Override
	public int deleteGoodsType(Integer id) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		return dgGoodsConsignMapper.deleteGoodsType(params);
	}

	@Override
	public int updateGoodsType(Integer id, String gtName, String isRemind) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("gtName", setTranscodingVal(gtName));
		params.put("isRemind", setTranscodingVal(isRemind));
		return dgGoodsConsignMapper.updateGoodsType(params);
	}

	@Override
	public List<Map<String,Object>> selectGoodsType() {
		// TODO Auto-generated method stub
		return dgGoodsConsignMapper.selectGoodsType();
	}


	/**
	 * 中文乱码转码
	 * @param value
	 * @return
	 */
	public String setTranscodingVal(String value){
		try {
			if(value != null && value != ""){
				value = new String(value.getBytes("IOS8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 判断字符串是否为"",返回null
	 * @param value
	 * @return
	 */
	public String setNUllVal(String value){
		if(value == ""){
			value = null;
		}
		return value;
	}
}
