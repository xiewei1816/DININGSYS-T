package com.yqsh.diningsys.web.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BusinessAnalysisMapper {
	
	Map<String,Object> selectBusinessAnalysisBy(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
	
	Map<String,Object> selectBusinessAnalysisBr(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
	
	Map<String,Object> selectBusinessAnalysisZt(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
	
	List<Map<String,Object>> selectBusinessAnalysisSb(@Param("searchDate")String searchDate, @Param("tableDate")String tableDate);
}
