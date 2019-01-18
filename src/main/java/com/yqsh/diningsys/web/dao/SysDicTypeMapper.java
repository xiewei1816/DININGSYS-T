package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.SysDicType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysDicTypeMapper extends GenericDao<SysDicType,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDicType record);

    int insertSelective(SysDicType record);

    SysDicType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDicType record);

    int updateByPrimaryKey(SysDicType record);

    int deleteByPrimaryKeys(List ids);

    List<SysDicType> getAllDicType();

    List<SysDicType> selectByConAndPage(Page<SysDicType> page, Map params);
}