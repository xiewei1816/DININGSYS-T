package com.yqsh.diningsys.web.cache;

import com.yqsh.diningsys.core.util.SpringContextUtil;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.sysSettings.DgUrlSetting;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-03-28 13:35
 * 缓冲工具类
 * @author zhshuo
 */
public class CacheUtil {

    private static CacheManager CACHEMANAGER;

    private static CacheManager yxeCacheManager ;

    static DgUrlSettingService dgUrlSettingService;

    
    static {
        
    	CACHEMANAGER
                = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("systemUrlCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build(true);

    	
    	yxeCacheManager
        = CacheManagerBuilder.newCacheManagerBuilder()
        .withCache("yxeCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, List.class, ResourcePoolsBuilder.heap(10)))
        .build(true);

        dgUrlSettingService = SpringContextUtil.getApplicationContext().getBean(DgUrlSettingService.class);
    }

    /**
     * 存放系统常用链接
     * @param dgUrlSettingList
     */
    public static void setSysUrl(List<DgUrlSetting> dgUrlSettingList){
        Cache<String, String> systemUrlCache =
                CACHEMANAGER.getCache("systemUrlCache", String.class, String.class);
        systemUrlCache.clear();
        for(DgUrlSetting dgUrlSetting:dgUrlSettingList){
            systemUrlCache.put(dgUrlSetting.getCode(), dgUrlSetting.getValue());
        }
    }

    /**
     * 根据key获取链接的值
     * @param key
     * @return
     */
    public static String getURLInCache(String key){
        if(StringUtil.isEmpty(key)) return null;
        Cache<String, String> systemUrlCache =
                CACHEMANAGER.getCache("systemUrlCache", String.class, String.class);

        if(!systemUrlCache.containsKey(key)){
            DgUrlSetting dgUrlSetting = dgUrlSettingService.selectByCode(key);
            if(dgUrlSetting == null){
                return null;
            }else{
                return dgUrlSetting.getValue();
            }
        }

        String s = systemUrlCache.get(key);
        if(StringUtils.isEmpty(s) || s.equalsIgnoreCase("null")){
            DgUrlSetting dgUrlSetting = dgUrlSettingService.selectByCode(key);
            return dgUrlSetting.getValue();
        }
        return systemUrlCache.get(key);

    }
    
    /**
     * 根据key修改链接的值
     * @param dgUrlSettingList
     */
    public static void updateURLInCache(List<DgUrlSetting> dgUrlSettingList){
        if(dgUrlSettingList != null && dgUrlSettingList.size() > 0){
	        Cache<String, String> systemUrlCache =
	                CACHEMANAGER.getCache("systemUrlCache", String.class, String.class);
	        systemUrlCache.clear();
            for(DgUrlSetting dgUrlSetting:dgUrlSettingList){
                systemUrlCache.put(dgUrlSetting.getCode(), dgUrlSetting.getValue());
            }
        }
    }
    
    /**
     * 获取外卖缓存
     * @return
     */
    public static Boolean getWmCache(){
    	Cache<String,String> wm = CACHEMANAGER.getCache("systemUrlCache", String.class, String.class);
    	if(!wm.containsKey("newWm")){
    		return false;
        } else {
        	return wm.get("newWm").equals("1")?true:false;
        }
    }
    /**
     * 设置外卖缓存
     */
    public static void setWmCache(String newWm){
    	 Cache<String, String> wm =
	                CACHEMANAGER.getCache("systemUrlCache", String.class, String.class);
    	 wm.remove("newWm");
    	 wm.put("newWm",newWm);
    }
    
    
    /**
     * 设置易小二缓存信息
     */
    public static void setYxeCache(List<DgOpenWater> dows,List<DgConsumerSeat> dcss){
    	 Cache<String, List> yxeCache =
    			 yxeCacheManager.getCache("yxeCache", String.class, List.class);
    	 if(yxeCache != null){
        	 yxeCache.clear(); 
    	 }
    	 yxeCache.put("dgOpenwaters",dows);
    	 yxeCache.put("dcss",dcss);
    }
    
    public static Map getYxeCache(String owNum,Integer seatId){
    	 Map resmap = new HashMap();
    	 Cache<String, List> yxeCache =
    			 yxeCacheManager.getCache("yxeCache", String.class, List.class);
    	 List<DgOpenWater> dows = yxeCache.get("dgOpenwaters");
    	 List<DgConsumerSeat> dcss = yxeCache.get("dcss");
    	 if(dows == null){
        	 return null;
    	 }
    	 for(DgOpenWater dow:dows){
    		 if(dow.getOwNum().equals(owNum)){
    			 resmap.put("water",dow);
    			 break;
    		 }
    	 }
    	 
    	 for(DgConsumerSeat dcs:dcss){
    		 if(dcs.getId().equals(seatId)){
    			 resmap.put("seat",dcs);
    			 break;
    		 }
    	 }
    	 return resmap;
    }
}
