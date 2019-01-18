package com.yqsh.diningsys.web.dao.businessMan;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;

import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public interface DgSeatManagerMapper extends GenericDao<DgSeatManager,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(DgSeatManager record);

    int insertSelective(DgSeatManager record);

    DgSeatManager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgSeatManager record);

    int updateByPrimaryKey(DgSeatManager record);
    
    
    DgSeatManager selectBySeatId(Integer id);
    
    List<DgSeatManager> selectDetailBySeatId(Integer id);
    
    List<DgSeatManager> selectAllDetailBySeatId();
    
    int getAllCount();//获取所有数量
    int getCountByAreaId(Integer id);
       
    int deleteBySeatIds(DgConsumerSeat seat);
}