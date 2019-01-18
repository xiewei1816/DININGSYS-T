package com.yqsh.diningsys.web.dao.api;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;

import java.util.List;
import java.util.Map;

/**
 * 对外接口
 *
 * @author zhshuo create on 2016-12-06 15:25
 */
@Repository("apiMainMapper")
public interface APIMainMapper {

    Map selectLastOpenClassInfo(Integer id);

    void updateOpenClass(Map param);
    
    Map selectItemFileTypeByItemId(Integer itemId);
    
    List<Map> selectItemOrderByService(Integer owId);
    
    List<Map> selectTeamByTeamMember(String teamMember);
    
    Map selectSeatInfoBySeatId(Integer seatId);
    
    
    
    /**
     * 根据流水获取套餐下明细
     */
    List<Map> selectTcSumByWaterTcId(Map orgs);
    
    /**
     * 根据服务单获取套餐下明细
     */
    List<Map> selectTcSumByServiceTcId(Map orgs);
    
    /**
     * 根据加单获取套餐下明细
     */
    List<Map> selectTcSumByIncreaServiceTcId(Map orgs);

    /**
     * 当前班次统计
     */
    Map selectCountCurrentFrequency(Map param);

    /**
     * 当前市别统计
     */
    Map selectCountBis(Map param);
    
    /**
     * 获取POS登录的最后信息
     * @param map
     */
    Map selectUserLastLoginPos(Map<String, Object> map);

    /**
     * 获取用户登录的最后信息
     * @param empCode
     * @return
     */
    Map selectLoginUserLast(@Param("userCode") String empCode);
     
    
    List<DgOpenWater> selectOpenWaterTop200();
    
    
    DgOpenWater selectOpenWaterByOwNumConCount(@Param("owNum") String owNum);
    
    List<DgConsumerSeat> selectAllTableConReserve(String time);
    
    DgConsumerSeat selectTableConReserveByseatId(Map org);

}
