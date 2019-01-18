package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.SysOrgan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysOrganMapper extends GenericDao<SysOrgan,Integer> {
    int deleteByPrimaryKey(Map params);

    List<Integer> selectDeleteIdByParentId(Integer id);

    int insert(SysOrgan record);

    int insertSelective(SysOrgan record);

    SysOrgan selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysOrgan record);

    int updateByPrimaryKey(SysOrgan record);

    List<SysOrgan> selectAllSysOrgan();

    Integer getNextMaxOrderNo(Integer id);
}