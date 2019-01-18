package com.yqsh.diningsys.web.service.report.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yqsh.diningsys.web.dao.report.BusinessAnalysisMapper;
import com.yqsh.diningsys.web.service.report.BusinessAnalysisService;
import com.yqsh.diningsys.web.util.TableQueryUtil;

/**
 * Created on 2017-05-02 14:08
 *
 * @author heshuai
 */
@Service
public class BusinessAnalysisServiceImpl implements BusinessAnalysisService{
    @Resource
    private BusinessAnalysisMapper businessAnalysisMapper;

	@Override
	public Map<String, Object> selectBusinessAnalysisBy(String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return businessAnalysisMapper.selectBusinessAnalysisBy(searchDate,tableDate);
	}

	@Override
	public Map<String, Object> selectBusinessAnalysisBr(String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return businessAnalysisMapper.selectBusinessAnalysisBr(searchDate,tableDate);
	}

	@Override
	public Map<String, Object> selectBusinessAnalysisZt(String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return businessAnalysisMapper.selectBusinessAnalysisZt(searchDate,tableDate);
	}

	@Override
	public List<Map<String, Object>> selectBusinessAnalysisSb(String searchDate) {
		// TODO Auto-generated method stub
		String tableDate = TableQueryUtil.tableNameUtilWithMonthSingle(searchDate);
		if(StringUtils.isEmpty(tableDate)){
            return null;
        }
		return businessAnalysisMapper.selectBusinessAnalysisSb(searchDate,tableDate);
	}
}
