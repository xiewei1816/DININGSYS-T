package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.SysDicTypeMapper;
import com.yqsh.diningsys.web.model.SysDicType;
import com.yqsh.diningsys.web.service.base.SysDicTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * dic service impl
 *
 * @author zhshuo create on 2016-08-01 10:33
 */
@Service
public class SysDicTypeServiceImpl extends GenericServiceImpl<SysDicType,Integer> implements SysDicTypeService {

    @Resource
    private SysDicTypeMapper sysDicTypeMapper;

    @Override
    public GenericDao<SysDicType, Integer> getDao() {
        return sysDicTypeMapper;
    }

    @Override
    public void deleteByPrimaryKeys(List id) {
        sysDicTypeMapper.deleteByPrimaryKeys(id);
    }

    @Override
    public List<SysDicType> selectByConAndPage(Page<SysDicType> page, Map params) {
        return sysDicTypeMapper.selectByConAndPage(page,params);
    }

    @Override
    public List<SysDicType> getAllDicType() {
        return sysDicTypeMapper.getAllDicType();
    }
}

