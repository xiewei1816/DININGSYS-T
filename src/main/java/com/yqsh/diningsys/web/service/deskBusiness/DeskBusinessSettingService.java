package com.yqsh.diningsys.web.service.deskBusiness;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.enums.DeskbusinessEnum;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;

import java.util.Map;

/**
 *
 *
 * Created by mrren on 2016/11/14.
 */
public interface DeskBusinessSettingService  extends GenericService<DeskBusinessSetting,Integer> {

    //获取客座设置
    DeskBusinessSetting getDeskBusinessSetting();

    //更新客座设置
    void updateDeskBusinessSetting(DeskBusinessSetting deskBusinessSetting);

    //根据菜单id，和设置名称
    Map<String, Object> getDeskBusinessSettingDetail(DeskbusinessEnum dbEnum, String settingName);

    //初始化客座设置，如果数据库为空，需要先初始化一个客座设置信息
    void initDeskBusinessSetting();

    //初始化序列号记录
    void initDBSFlowNumber();

    String createFlowNumber(String organ, String posNum, int number, SerialRulEnum type);

    /**
     * 结班流水
     * @param organ
     * @param posNum
     * @return
     */
    String createOpenClassNumber(String organ, String posNum);

    /**
     * 修改结班的顺序号
     */
    void updateOpenClassNumber();
    
    Map getFlowRul(String organ, String posNum, SerialRulEnum type);

}
