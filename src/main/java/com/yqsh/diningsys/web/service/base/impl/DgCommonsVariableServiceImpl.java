package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.web.dao.DgCommonsVariableMapper;
import com.yqsh.diningsys.web.model.DgCommonsVariable;
import com.yqsh.diningsys.web.service.base.DgCommonsVariableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-16 上午9:44
 */
@Service
public class DgCommonsVariableServiceImpl implements DgCommonsVariableService {

    @Resource
    private DgCommonsVariableMapper dgCommonsVariableMapper;

    @Override
    public DgCommonsVariable selectByCode(DgCommonsVariable dgCommonsVariable) {
        return dgCommonsVariableMapper.selectByCode(dgCommonsVariable);
    }

    @Override
    public void updateValueByCode(DgCommonsVariable dgCommonsVariable) {
        dgCommonsVariableMapper.updateValueByCode(dgCommonsVariable);
    }
}
