package com.yqsh.diningsys.web.service.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 对外的主要接口
 *
 * @author zhshuo create on 2016-12-06 15:07
 */
public interface APIMainService {

    Map selectLastOpenClassInfo(Integer posid);

    void updateOpenClass(String userCode,String userName,Integer posId,Double pettyCash,Date loginTime);
    /**
     * 
     * 按大类获取消费明细
     * @param seatId
     * @return
     */
    List<Map> getOpenWaterDetailsAccordingBig(int seatId);
    
    /**
     * 
     * 按服务顺序显示服务明细
     */
    List<Map> getOpenWaterDetailsOrderByService(int seatId);

    
    
    /**
     * 
     * 按品项汇总
     */
    List<Map> getOpenWaterDetailsOrderByItem(int seatId);
    
    /**
     * 按服务单汇总,不包括退单
     */
    List<Map> getOpenWaterDetailSubByService(int seatId);
    
    /**
     * 获取客位信息
     * @param seatId
     * @return
     */
    Map selectSeatInfoBySeatId(Integer seatId);

    /**
     * 统计当前班次
     */
    Map selectCountCurrentFrequency(Integer posId, Date date,List<String> ids);
    
    /**
     * 统计当前市别
     */
    Map selectCountBis(Integer posId, Date date,List<String> ids);

    /**
     * 获取用户最后登录的信息
     * @param userCode
     * @param id
     */
    Map selectUserLastLoginPos(String userCode, Integer id);

    /**
     * 当前市别的营业流水统计
     */
    Map selectCountCurrentBis();

    /**
     * 查找用户的最后登录班次信息
     * @param empCode
     * @return
     */
    Map selectLoginUserLast(String empCode);
    
    
    /**
     * 只查当前流水
     * 按品项汇总
     * 用于平板
     */
    Map getOpenWaterOrderItem(String owNum,Integer seatId);


    /**
     * 易小二主界面轮询
     * @param owNum
     * @param seatId
     * @return
     */
    Map yxeMainLoop(String owNum,Integer seatId);

    /**
     *
     * 按大类获取消费明细
     * @param seatId
     * @return
     */
    List<Map> getOpenWaterDetailsAccordingBig(String owNum);

    /**
     *
     * 按服务顺序显示服务明细
     */
    List<Map> getOpenWaterDetailsOrderByService(String owNum);

    /**
     *
     * 按品项汇总
     */
    List<Map> getOpenWaterDetailsOrderByItem(String owNum);

    /**
     * 按服务单汇总,不包括退单
     */
    List<Map<String, Object>> getOpenWaterDetailSubByService(String owNum);

}
