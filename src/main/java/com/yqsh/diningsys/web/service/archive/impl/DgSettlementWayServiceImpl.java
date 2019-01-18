package com.yqsh.diningsys.web.service.archive.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.core.util.DateUtil;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgSettlementWayMapper;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.service.archive.DgSettlementWayService;
import org.springframework.util.StringUtils;

@Service
public class DgSettlementWayServiceImpl implements DgSettlementWayService {
	
	@Resource
	private DgSettlementWayMapper settlementWayMapper;
	
	@Override
	public Page<DgSettlementWay> getPageList(DgSettlementWay dgSettlementWay) {
		if(!StringUtils.isEmpty(dgSettlementWay.getCrEndTime())){
			Date dateByFormat = DateUtil.getDateByFormat(dgSettlementWay.getCrEndTime(), "yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateByFormat);
			calendar.add(Calendar.DATE,1);
			String nextDay = DateUtil.dateToStr(calendar.getTime(),"yyyy-MM-dd");
			dgSettlementWay.setCrEndTime(nextDay);
		}
		Integer totle = settlementWayMapper.countListByPage(dgSettlementWay);
		List<DgSettlementWay> list = settlementWayMapper.getListByPage(dgSettlementWay);
		return PageUtil.getPage(totle, dgSettlementWay.getPage(),list, dgSettlementWay.getRows());
	}

	@Override
	public int insertOrUpd(DgSettlementWay dgSettlementWay) {
		int result = 0;
		if(dgSettlementWay.getId() != null && dgSettlementWay.getId() > 0){
			result = settlementWayMapper.update(dgSettlementWay);
		}else{
			dgSettlementWay.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			result = settlementWayMapper.newInsert(dgSettlementWay);
		}
		return result;
	}

	@Override
	public DgSettlementWay getDgSettlementWayByID(
			DgSettlementWay dgSettlementWay) {
		return settlementWayMapper.getSettlementWayByID(dgSettlementWay);
	}

	@Override
	public int deleteByIds(DgSettlementWay dgSettlementWay) {
		return settlementWayMapper.delete(dgSettlementWay);
	}

	@Override
	public List<DgSettlementWay> getAllList(DgSettlementWay dgSettlementWay) {
		return settlementWayMapper.getAllList(dgSettlementWay);
	}

    @Override
    public List<DgSettlementWay> getSettleMentWayRankList() {
        return settlementWayMapper.getSettleMentWayRankList();
    }

    @Override
    public void addSettlementWayRank(Map<String,Object> map) {
        settlementWayMapper.addSettlementWayRank(map);
    }

	@Override
	public void updateSettlementWayRank(Map<String, Object> params) {
		settlementWayMapper.updateSettlementWayRank(params);
	}

    @Override
    public Map<String, Object> selSettlementWayRank(Map<String, Object> map) {
        return settlementWayMapper.selSettlementWayRank(map);
    }

    @Override
    public DgSettlementWay selectDataById(Integer id) {
		return settlementWayMapper.selectDataById(id);
    }

    @Override
    public void upateWayItemSet(Integer wayId, String ids) {
        Map<String,Object> map = new HashMap<>();
        map.put("wayId",wayId);
        map.put("ids", JSON.toJSONString(ids.split(",")));
        settlementWayMapper.upateWayItemSet(map);
    }

	@Override
	public int updateSettlementWayRankMoveUp(Integer id) {
		return settlementWayMapper.updateSettlementWayRankMoveUp(id);
	}

	@Override
	public int updateSettlementWayRankMoveDown(Integer id) {
		return settlementWayMapper.updateSettlementWayRankMoveDown(id);
	}

	@Override
	public List<String> selectSettlementWayRankMoveTopper(Integer id) {
		return settlementWayMapper.selectSettlementWayRankMoveTopper(id);
	}

	@Override
	public void updateSettlementWayRankMoveTopper(List<String> ids) {
		settlementWayMapper.updateSettlementWayRankMoveTopper(ids);
	}

}
