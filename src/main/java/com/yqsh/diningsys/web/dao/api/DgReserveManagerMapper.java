package com.yqsh.diningsys.web.dao.api;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatids;
@Repository
public interface DgReserveManagerMapper extends GenericDao<DgReserveManager,Integer>{    
    List<DgReserveManager> getListByPage(DgReserveManager record);
    
    Integer getCount(DgReserveManager record);
    
    Integer deleteByIds(List ids);
    
    List<DgConsumerSeat> getAllSeat(DgReserveManager search);
    
    Integer insertBackId(DgReserveManager record);
    
    
    //检测客位是否已预定,前后4小时内检测
    List<String> getYdSeat(Map orgs);

    /**
     * 根据时间/客位id 获取预定信息
     * @param orgs
     * @return
     */
    DgReserveManager selectYdFromSeatId(Map orgs);
    
    int updateSeatToYd(String time);
    
    int updateSeatToInit(String time);
    
    int updateManagerState(DgReserveManager record);
    
    List<DgConsumerSeat> getSeatToYd30(String time);
    
    List<DgConsumerSeat> getSeatToInit30(String time);
    
    List<DgConsumerSeat> selectSeatByIds(@Param("ids")List<DgReserveSeatids> ids);
    
    
    List<DgReserveManager> selectByIds(List ids);
    
    DgReserveManager selectByOrderId(String orderId);
    
    /**
     * 未到预定信息,修改状态
     * @param time
     * @return
     */
    int updateNotArriveDgReserve(String time);
    
    /**
     * 根据客位id找最近的预定信息
     * @param orgs
     * @return
     */
    DgReserveManager selectBySeatId(Map orgs);
        
    /**
     * 查询10分钟后预定到时，未到客户
     */
    List<DgReserveManager> selectReserverLatelyTenMintues(Map orgs);
    
    /**
     * 更新10分钟后的预定，已通知状态
     */
    Integer updateDgReserveManagerAlreadyNotice(List<DgReserveManager> drms);

    List<DgReserveManager> getDgReserveManagerList(DgReserveManager dgReserveManager);
}