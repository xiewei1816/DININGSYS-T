package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.SysDic;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysDicMapper extends GenericDao<SysDic,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(SysDic record);

    int insertSelective(SysDic record);

    SysDic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDic record);

    int updateByPrimaryKey(SysDic record);

    int deleteByPrimaryKeys(List ids);

    List<SysDic> selectByConAndPage(Page<SysDic> page, Map params);
    
    List<SysDic> selectByType(Map<String, Object> params);
}