package com.yqsh.diningsys.web.sevlet;

import com.yqsh.diningsys.core.util.SpringContextUtil;

import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.model.SysSetting;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.base.SysMenuService;
import com.yqsh.diningsys.web.service.base.SystemSettingService;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2017-03-28 13:18
 * 缓存初始化类
 * @author zhshuo
 */
@Component
public class CacheInit implements ApplicationListener<ContextRefreshedEvent> {

    static DgUrlSettingService dgUrlSettingService;

    static SysMenuService sysMenuService;

    static SystemSettingService systemSettingService;
	
    public static boolean isNet=false;
    
    
    private static void initSysSet(){
    	SysSetting set = new SysSetting();
    	set.setSettingCode("usenet");
    	set = systemSettingService.selectSystemByCode(set);
    	if(set.getSettingValue() != null && set.getSettingValue().equals("1")){
    		isNet = true;
    	}
    }

    static {
        dgUrlSettingService = SpringContextUtil.getApplicationContext().getBean(DgUrlSettingService.class);
        sysMenuService = SpringContextUtil.getApplicationContext().getBean(SysMenuService.class);
        systemSettingService = SpringContextUtil.getApplicationContext().getBean(SystemSettingService.class);
        initSysSet();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            List<DgUrlSetting> dgUrlSetting = dgUrlSettingService.selectAllData();
            CacheUtil.setSysUrl(dgUrlSetting);
        }
    }

}
