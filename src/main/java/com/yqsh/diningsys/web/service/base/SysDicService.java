package com.yqsh.diningsys.web.service.base;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.SysDic;

/**
 * SysDicService
 *
 * @author zhshuo create on 2016-08-01 10:31
 */
public interface SysDicService extends GenericService<SysDic,Integer> {

    void deleteByPrimaryKeys(List id);

    List<SysDic> selectByConAndPage(Page<SysDic> page, Map params);
    
    List<SysDic> selectByType(Map<String, Object> params);
 
}
