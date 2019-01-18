package com.yqsh.diningsys.web.service.api.impl;

import com.yqsh.diningsys.web.dao.api.DgCashPledgeMapper;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgCashPledge;
import com.yqsh.diningsys.web.service.api.DgCashPledgeService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Service
public class DgCashPledgeServiceImpl implements DgCashPledgeService {
	@Resource
	private DgCashPledgeMapper dgCashPledgeMapper;

	@Override
	public List<DgCashPledge> selectCashPledge(String owNum, String cpType) {
		// TODO Auto-generated method stub
		DgCashPledge dcp = new DgCashPledge();
		dcp.setOwNum(owNum);
		dcp.setCpType(cpType);
		return dgCashPledgeMapper.selectCashPledge(dcp);
	}

	@Override
	public int regMoney(String owNum, String cpType,String cpCurrency, Double cpMoney,String refInfo, String remark) {
		// TODO Auto-generated method stub
		DgCashPledge dcp = new DgCashPledge();
		dcp.setOwNum(owNum);
		dcp.setCpType(cpType);
		dcp.setCpCurrency(cpCurrency);
		dcp.setCpMoney(cpMoney);
		dcp.setConverMoney(cpMoney);
		dcp.setRegTime(new Date());
		dcp.setRefInfo(refInfo);
		dcp.setRemark(remark);
		return dgCashPledgeMapper.regMoney(dcp);
	}
	
	@Override
	public int updateCashPledge(String owNum, String cpType,String cpCurrency, Double cpMoney,String refInfo, String remark) {
		// TODO Auto-generated method stub
		DgCashPledge dcp = new DgCashPledge();
		dcp.setOwNum(owNum);
		dcp.setCpType(cpType);
		dcp.setCpCurrency(cpCurrency);
		dcp.setCpMoney(cpMoney);
		dcp.setConverMoney(cpMoney);
		dcp.setRegTime(new Date());
		dcp.setRefInfo(refInfo);
		dcp.setRemark(remark);
		return dgCashPledgeMapper.updateCashPledge(dcp);
	}

	@Override
	public int updetePrintNumber(String owNum) {
		// TODO Auto-generated method stub
		DgCashPledge dcp = new DgCashPledge();
		dcp.setOwNum(owNum);
		return dgCashPledgeMapper.updetePrintNumber(dcp);
	}
	
	/**
	 * 中文乱码转码
	 * @param value
	 * @return
	 */
	public String setTranscodingVal(String value){
		try {
			if(value != null && value != ""){
				value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}


}
