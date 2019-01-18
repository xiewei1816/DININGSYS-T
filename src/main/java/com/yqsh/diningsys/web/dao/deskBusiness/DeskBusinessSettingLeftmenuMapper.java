package com.yqsh.diningsys.web.dao.deskBusiness;


import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSettingLeftmenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeskBusinessSettingLeftmenuMapper extends GenericDao<DeskBusinessSettingLeftmenu,Integer> {
    int deleteByPrimaryKey(Integer id);

    int insert(DeskBusinessSettingLeftmenu record);

    int insertSelective(DeskBusinessSettingLeftmenu record);

    DeskBusinessSettingLeftmenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeskBusinessSettingLeftmenu record);

    int updateByPrimaryKey(DeskBusinessSettingLeftmenu record);

    List<DeskBusinessSettingLeftmenu> selectByParentPrimaryKey(Integer pid);
}