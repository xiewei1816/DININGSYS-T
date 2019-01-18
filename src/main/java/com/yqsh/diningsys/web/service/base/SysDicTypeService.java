package com.yqsh.diningsys.web.service.base;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.SysDicType;

import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明该类的作用
 *
 * @author zhshuo create on 2016-08-01 10:31
 */
public interface SysDicTypeService extends GenericService<SysDicType,Integer> {

    void deleteByPrimaryKeys(List id);

    List<SysDicType> selectByConAndPage(Page<SysDicType> page, Map params);

    List<SysDicType> getAllDicType();
}
