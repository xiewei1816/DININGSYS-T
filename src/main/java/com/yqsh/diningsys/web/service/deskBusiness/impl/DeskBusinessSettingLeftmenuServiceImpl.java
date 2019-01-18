package com.yqsh.diningsys.web.service.deskBusiness.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.DeskBusinessSettingLeftmenuMapper;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSettingLeftmenu;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingLeftmenuService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 *
 * Created by mrren on 2016/11/14.
 */
@Service
public class DeskBusinessSettingLeftmenuServiceImpl extends GenericServiceImpl<DeskBusinessSettingLeftmenu,Integer> implements DeskBusinessSettingLeftmenuService {

    @Resource
    private DeskBusinessSettingLeftmenuMapper deskBusinessSettingLeftmenuMapper;

    @Override
    public GenericDao<DeskBusinessSettingLeftmenu, Integer> getDao() {
        return deskBusinessSettingLeftmenuMapper;
    }

    @Override
    public void addLeftmenu(DeskBusinessSettingLeftmenu deskBusinessSettingLeftmenu) {
        deskBusinessSettingLeftmenuMapper.insert(deskBusinessSettingLeftmenu);
    }

    @Override
    public void updateLeftmenu(DeskBusinessSettingLeftmenu deskBusinessSettingLeftmenu) {
        deskBusinessSettingLeftmenuMapper.updateByPrimaryKey(deskBusinessSettingLeftmenu);
    }

    @Override
    public DeskBusinessSettingLeftmenu getLeftmenu(Integer id) {
        return deskBusinessSettingLeftmenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteLeftmenu(Integer id) {
        deskBusinessSettingLeftmenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<DeskBusinessSettingLeftmenu> selectByParentPrimaryKey(Integer pid) {
        return deskBusinessSettingLeftmenuMapper.selectByParentPrimaryKey(pid);
    }
}
