package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.archive.DgSeatDoorinfo;
@Repository
public interface DgSeatDoorinfoMapper extends GenericDao<DgSeatDoorinfo,Integer>{
    int insert(DgSeatDoorinfo record);

    int insertSelective(DgSeatDoorinfo record);
    
    int deleteByMac(List orgs);
    
    int updateByMac(DgSeatDoorinfo record);
   
    Integer getCount(DgSeatDoorinfo record);
    
    DgSeatDoorinfo selectById(Integer id);
    
    List<DgSeatDoorinfo> getListByPage(DgSeatDoorinfo record);
    
    List<DgConsumerSeat> getSeatNotInDoorInfo();
    
    List<DgConsumerSeat> getAllSeat();

    List<Map<String, Object>> getConsumerSeatInfoByMac(String mac);
}