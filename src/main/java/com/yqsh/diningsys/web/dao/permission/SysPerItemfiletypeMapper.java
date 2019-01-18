package com.yqsh.diningsys.web.dao.permission;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.permission.SysPerItemfiletype;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface SysPerItemfiletypeMapper extends GenericDao<SysPerItemfiletype,Integer>{
    int insert(SysPerItemfiletype record);

    int insertSelective(SysPerItemfiletype record);

    void inserMultiData(Map param);

    void deleteMutliDataByZwIdAndType(Map param);
}