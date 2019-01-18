package com.yqsh.diningsys.web.service.report;

import java.util.List;
import java.util.Map;

public interface BusinessAnalysisService {
	
	Map<String,Object> selectBusinessAnalysisBy(String searchDate);
	
	Map<String,Object> selectBusinessAnalysisBr(String searchDate);
	
	Map<String,Object> selectBusinessAnalysisZt(String searchDate);
	
	List<Map<String,Object>> selectBusinessAnalysisSb(String searchDate);
}
