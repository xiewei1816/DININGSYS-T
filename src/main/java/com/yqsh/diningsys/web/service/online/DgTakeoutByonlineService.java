package com.yqsh.diningsys.web.service.online;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yqsh.diningsys.web.model.online.DgTakeoutByonline;

public interface DgTakeoutByonlineService {
    int deleteByPrimaryKey(String id);

    int insert(DgTakeoutByonline record);

    int insertSelective(DgTakeoutByonline record);

    DgTakeoutByonline selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DgTakeoutByonline record);

    int updateByPrimaryKeyWithBLOBs(DgTakeoutByonline record);

    int updateByPrimaryKey(DgTakeoutByonline record);
    
    /**
     * 批量下载更新
     * @param date
     * @return
     */
    int insertOrEdit_takeout_online(List<Map<String,Object>> date);
    /**
     * 取出前20条数据
     * @return
     */
    List<DgTakeoutByonline> selectTop20();
    
    /**
     * 更新已处理数据
     * @param date
     * @return
     */
    int updateIds(String date);
}