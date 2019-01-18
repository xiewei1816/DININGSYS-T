package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.web.model.SysAutoseq;
import org.springframework.stereotype.Repository;
@Repository
public interface SysAutoseqMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAutoseq record);

    int insertSelective(SysAutoseq record);

    SysAutoseq selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAutoseq record);

    int updateByPrimaryKey(SysAutoseq record);
    
    SysAutoseq getSeqByType(String type);
    
    boolean deleteSeqByType(String type);
}