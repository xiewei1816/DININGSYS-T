package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.util.StringUtils;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-12-01 下午1:09
 */
@Service
public class DgOpenWaterServiceImpl implements DgOpenWaterService {

    @Resource
    private DgOpenWaterMapper dgOpenWaterMapper;

    @Override
    public List<DgOpenWater> selectCurrentOpenWater(Map param) {
        return dgOpenWaterMapper.selectCurrentOpenWater(param);
    }

    @Override
    public int updateOpenWater(DgOpenWater openWater) {
        return dgOpenWaterMapper.updateByPrimaryOpenWaterNum(openWater);
    }

    @Override
    public DgOpenWater selectByOpenWaterByNum(String num) {
        return dgOpenWaterMapper.selectByOpenWaterByNum(num);
    }

	@Override
	public int isExists() {
		return dgOpenWaterMapper.isExists();
	}

    @Override
    public List<Map<String,Object>> getOWByDate(String startTime, String endTime) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("starttime",startTime);
        map.put("endtime",endTime);

        String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(startTime);

        if(StringUtils.isEmpty(tableDate)){
            return null;
        }
        map.put("tableDate", tableDate);
        return dgOpenWaterMapper.getOWByDate(map);
    }

    @Override
    public List<Map<String, Object>> getPCountAndCNum(String startTime, String endTime,Integer cbis) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("starttime",startTime);
        map.put("endtime",endTime);
        map.put("cbis",cbis);
        return dgOpenWaterMapper.getPCountAndCNum(map);
    }

    @Override
	public DgOpenWater selectByOpenWaterById(String id) {
		// TODO Auto-generated method stub
		return dgOpenWaterMapper.selectByPrimaryKey(Integer.valueOf(id));
	}


}
