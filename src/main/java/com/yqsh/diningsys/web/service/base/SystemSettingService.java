package com.yqsh.diningsys.web.service.base;

import com.yqsh.diningsys.web.model.SysSetting;

import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-08-19 14:25
 */
public interface SystemSettingService{
    void insertMultiSetting(Map<String,String[]> param);

    List<SysSetting> selectSettingData();

    SysSetting selectSystemName();
    
    SysSetting selectSystemByCode(SysSetting code);
}
