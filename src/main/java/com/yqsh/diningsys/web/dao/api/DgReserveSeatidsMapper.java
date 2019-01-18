package com.yqsh.diningsys.web.dao.api;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.archive.DgReserveSeatids;
@Repository
public interface DgReserveSeatidsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgReserveSeatids record);

    int insertSelective(DgReserveSeatids record);

    DgReserveSeatids selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgReserveSeatids record);

    int updateByPrimaryKey(DgReserveSeatids record);
    
    int deleteByParentId(Integer id);
    
    List<DgReserveSeatids> selectByParentId(Integer id);
    
    
    List<DgReserveSeatids> selectByParentIdExitUse(Integer id);
    
    int deleteByParentIds(List ids);  
    
    int deleteZsByParentId(Integer id);
    
    int deleteZsByParentIds(List ids);  
}