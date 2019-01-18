package com.yqsh.diningsys.web.dao.permission;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.permission.DgFrontDeskOperQx;
@Repository
public interface DgFrontDeskOperQxMapper extends GenericDao<DgFrontDeskOperQx,Integer>{
    int deleteByPrimaryKey(String code);

    int insert(DgFrontDeskOperQx record);

    int insertSelective(DgFrontDeskOperQx record);

    DgFrontDeskOperQx selectByPrimaryKey(String code);

    int updateByPrimaryKeySelective(DgFrontDeskOperQx record);

    int updateByPrimaryKey(DgFrontDeskOperQx record);
    
    List<DgFrontDeskOperQx> seleAll();
    int updateByPrimaryKeyInIds(List<String> ids);
    int updateByPrimaryKeyNotInIds(List<String> ids);
    int updateAllNoQx();
    
}