package com.yqsh.diningsys.web.service.api;

import java.util.List;
import java.util.Map;

import com.yqsh.catering.web.mq.DepositOrderMessage;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatids;
import com.yqsh.diningsys.web.model.archive.DgReserveSeatidsList;
import com.yqsh.diningsys.web.model.archive.DgSeatDoorinfo;

/**
 * 预定管理
 * @author heshuai
 *
 */
public interface ReserveManagerService {
	Page<DgReserveManager> getListByPage(DgReserveManager page);
	
	Page<DepositOrderMessage> getOnlinePageList(DepositOrderMessage page);
    
    int deleteByIds(String ids);
    
    int update(DgReserveManager record);
    
    List<DgConsumerSeat> getAllSeat(DgReserveManager search);
    
    List<DgReserveSeatids> selectYdSeatById(Integer id);
    
    DgReserveManager selectById(Integer id);

	Map insertOrUpdate(DgReserveManager dgReserveManager,
			DgReserveSeatidsList list);
	
    int updateSeatToYd(String time);
    
    
    int noticeNotArrive(String time);
    /**
     * 根据时间/客位id 获取预定信息
     * @param orgs
     * @return
     */
    DgReserveManager selectYdFromSeatId(Map orgs);
    
    
    int insertOnlineReserve(DepositOrderMessage dom);
    
    DgCallService selectCallInfo(Integer id);
    
    int refuseOnline(String ids,String msg);
   
    
    int deleteCallInfo();
    
    
    /**
     * 线上发送取消订单消息
     */
    
    int deleteOnlineCancel(String orderId);

    List<DgReserveManager> getDgReserveManagerList(DgReserveManager dgReserveManager);
}
