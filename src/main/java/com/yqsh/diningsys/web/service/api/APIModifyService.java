package com.yqsh.diningsys.web.service.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;

public interface APIModifyService {
	/*
	 * 
	 * 更换客位
	 * openNum 营业流水号
	 * seatId 客位id
	 */
	void modifySeat(String userCode,DgOpenWater ow,Integer waterId,Integer seatId,Integer isGgFa,Integer isJsBffPx);
	
	double calculationBff(int faId, Date startTime, Date endTime);
	 
	void savaGqPx(String orgs,String type); 
	
	void saveXlPx(String orgs,int type);
	
	Map getPxAndPxType(List ids);
	
	Map modifyJoinTeam(String userCode,String openNum,String teamNumber);
	
	void modifyGgHy(String openNum,String gradeId);
}