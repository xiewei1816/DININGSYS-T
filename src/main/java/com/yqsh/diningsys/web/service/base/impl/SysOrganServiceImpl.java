package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.SysOrganMapper;
import com.yqsh.diningsys.web.model.SysOrgan;
import com.yqsh.diningsys.web.service.base.SysOrganService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明该类的作用
 *
 * @author zhshuo create on 2016-07-29 11:05
 */
@Service
public class SysOrganServiceImpl extends GenericServiceImpl<SysOrgan, Integer> implements SysOrganService {

    @Resource
    private SysOrganMapper sysOrganMapper;

    @Override
    public GenericDao<SysOrgan, Integer> getDao() {
        return sysOrganMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        List<Integer> ids = selectDeleteIdByParentId(id);
        Map params = new HashMap();
        params.put("id", id);
        params.put("ids", ids.size() == 0 ? null : ids);
        return sysOrganMapper.deleteByPrimaryKey(params);
    }

    @Override
    public List<SysOrgan> selectAllSysOrgan() {
        return sysOrganMapper.selectAllSysOrgan();
    }

    @Override
    public Integer getNextMaxOrderNo(Integer id) {
        return sysOrganMapper.getNextMaxOrderNo(id);
    }

    @Override
    public List<Integer> selectDeleteIdByParentId(Integer id) {
        return sysOrganMapper.selectDeleteIdByParentId(id);
    }
}
