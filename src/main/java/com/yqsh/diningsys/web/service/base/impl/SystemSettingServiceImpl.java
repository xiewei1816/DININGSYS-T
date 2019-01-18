package com.yqsh.diningsys.web.service.base.impl;

import com.yqsh.diningsys.web.dao.SysSettingMapper;
import com.yqsh.diningsys.web.model.SysSetting;
import com.yqsh.diningsys.web.service.base.SystemSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-08-19 14:27
 */
@Service
public class SystemSettingServiceImpl implements SystemSettingService {

    @Resource
    private SysSettingMapper sysSettingMapper;

    @Override
    public void insertMultiSetting(Map<String,String[]> param) {
        sysSettingMapper.deleteData();

        String params = "";
        for(String key:param.keySet()){
            if(!param.get(key)[0].equals("") && param.get(key)[0] != null)
                params += "('"+key+"','"+param.get(key)[0]+"'),";
        }

        params = params.substring(0,params.lastIndexOf(","));

        Map map = new HashMap();
        map.put("param",params);
        sysSettingMapper.insertMultiSetting(map);
    }

    @Override
    public List<SysSetting> selectSettingData() {
        return sysSettingMapper.selectSettingData();
    }

    @Override
    public SysSetting selectSystemName() {
        return sysSettingMapper.selectSystemName();
    }

	@Override
	public SysSetting selectSystemByCode(SysSetting code) {
		// TODO Auto-generated method stub
		return sysSettingMapper.selectSystemByCode(code);
	}
}
