package com.yqsh.diningsys.web.service.sysSettings;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;

public interface SysLogService extends GenericService<SysLog,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKeyWithBLOBs(SysLog record);

    int updateByPrimaryKey(SysLog record);
    
    List<Map<String,Object>> selectYearByType(int type);
    
    /**
     * 
     * 根据type和年份获取所以存在的月份
     * @param src
     * @return
     */
    List<Map<String,Object>> selectMonthByTypeAndYear(Map<String,Object> src );
    /**
     * 
     * 根据type和年份和月份获取所以存在的day
     * @param src
     * @return
     */
    List<Map<String,Object>> selectDayByTypeAndYearAndMonth(Map<String,Object> src );
    
    Page<SysLogQuery> getListByPage(SysLogQuery page);
    
    int countListByPage(SysLogQuery page);
   
}