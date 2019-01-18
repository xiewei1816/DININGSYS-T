package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgSeatDoorinfo;

public interface DgSeatDoorinfoService extends GenericService<DgSeatDoorinfo,Integer>{
	
    Page<DgSeatDoorinfo> getListByPage(DgSeatDoorinfo page);
    
    int deleteByMac(String ids);
    
    int updateByMac(DgSeatDoorinfo record);
    
    List<DgConsumerSeat> getSeatNotInDoorInfo();
    
    DgSeatDoorinfo selectById(Integer id);
    
    Map insertOrUpdate(DgSeatDoorinfo org);
    
    List<DgConsumerSeat> getAllSeat();
}
