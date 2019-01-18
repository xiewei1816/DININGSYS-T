package com.yqsh.diningsys.web.dao.sysSettings;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.sysSettings.SysLog;
import com.yqsh.diningsys.web.model.sysSettings.SysLogQuery;

@Repository
public interface SysLogMapper extends GenericDao<SysLog,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKeyWithBLOBs(SysLog record);

    int updateByPrimaryKey(SysLog record);
    
    
    /**
     * 
     * 根据type获取存在的年份
     * @param type
     * @return
     */
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
    
    /**
     * 分页查询
     * 
     */
    List<SysLogQuery> getListByPage(SysLogQuery page);
    
    int countListByPage(SysLogQuery page);
}