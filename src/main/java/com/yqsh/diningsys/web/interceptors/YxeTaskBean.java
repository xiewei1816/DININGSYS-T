package com.yqsh.diningsys.web.interceptors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yqsh.diningsys.core.util.SpringContextUtil;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.dao.api.APIMainMapper;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;

@Component
public class YxeTaskBean {
	
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss"); //年月日 时分秒
	
	private static APIMainMapper apiMainMapper;

	static{
		apiMainMapper = (APIMainMapper) SpringContextUtil.getBean("apiMainMapper");
	}

	/**
     * 轮询客位信息,到缓存
     *
     * @throws Exception
     */
    @Scheduled(cron = "0/8 * *  * * ? ")   //每8秒执行一次
    public void Loop() throws Exception {
    		//缓存营业流水信息 
    	    List<DgOpenWater> dows = apiMainMapper.selectOpenWaterTop200();
    		//缓存客位信息,预定信息
    	    List<DgConsumerSeat> dcss = apiMainMapper.selectAllTableConReserve(simpleDateFormat.format(new Date()));
    	    
    	    CacheUtil.setYxeCache(dows, dcss);
    }
}