package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.SysDicMapper;
import com.yqsh.diningsys.web.model.SysDic;
import com.yqsh.diningsys.web.service.base.SysDicService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

/**
 * SysDicServiceImpl
 *
 * @author zhshuo create on 2016-08-01 10:33
 */
@Service
public class SysDicServiceImpl extends GenericServiceImpl<SysDic,Integer> implements SysDicService{

    @Resource
    private SysDicMapper sysDicMapper;

    @Override
    public GenericDao<SysDic, Integer> getDao() {
        return sysDicMapper;
    }

    @Override
    public void deleteByPrimaryKeys(List id) {
        sysDicMapper.deleteByPrimaryKeys(id);
    }

    @Override
    public List<SysDic> selectByConAndPage(Page<SysDic> page, Map params) {
        return sysDicMapper.selectByConAndPage(page,params);
    }

	@Override
	public List<SysDic> selectByType(Map<String, Object> params) {
		return sysDicMapper.selectByType(params);
	}
}
