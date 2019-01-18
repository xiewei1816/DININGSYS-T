package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.web.model.SysSetting;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysSettingMapper {

    void insertMultiSetting(Map param);

    void deleteData();

    List<SysSetting> selectSettingData();

    SysSetting selectSystemName();

    SysSetting selectSystemByCode(SysSetting set);
}