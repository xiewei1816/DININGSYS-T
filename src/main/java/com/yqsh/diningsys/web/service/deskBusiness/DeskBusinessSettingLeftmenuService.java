package com.yqsh.diningsys.web.service.deskBusiness;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSettingLeftmenu;
import com.yqsh.diningsys.web.model.deskBusiness.enums.DeskbusinessEnum;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * Created by mrren on 2016/11/14.
 */
public interface DeskBusinessSettingLeftmenuService extends GenericService<DeskBusinessSettingLeftmenu,Integer> {

    //更新菜单
    void addLeftmenu(DeskBusinessSettingLeftmenu deskBusinessSettingLeftmenu);

    //更新菜单
    void updateLeftmenu(DeskBusinessSettingLeftmenu deskBusinessSettingLeftmenu);

    //根据菜单id，获取对象
    DeskBusinessSettingLeftmenu getLeftmenu(Integer id);

    //删除菜单
    void deleteLeftmenu(Integer id);

    List<DeskBusinessSettingLeftmenu> selectByParentPrimaryKey(Integer pid);

}
