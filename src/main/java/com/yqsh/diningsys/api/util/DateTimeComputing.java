package com.yqsh.diningsys.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 现在时间与指定时间差计算
 *
 * @author zhshuo create on 2016-12-02 上午11:05
 */
public class DateTimeComputing {

    /**
     * 现在时间与指定时间差计算
     * @param comTime 需要计算的时间
     * @return 返回的时间为负，代表传入的时间大于现在时间
     */
    public static String compution(Object comTime){
        try {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date now = new Date();

        Date date= df.parse(comTime.toString());
        long l=now.getTime()-date.getTime();

        long day=l/(24*60*60*1000);

        long hour=(l/(60*60*1000)-day*24);

        long min=((l/(60*1000))-day*24*60-hour*60);

        long s=(l/1000-day*24*60*60-hour*60*60-min*60);

        String res = "";

        if(day != 0) res+=day+"天";
        if(hour != 0) res+=hour+"小时";
        if(min != 0) res+=min+"分";
        if(s != 0) res+=s+"秒";

        return res;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 计算两时间差,精确到时分秒
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 返回的时间为负，代表传入的时间大于现在时间
     */
    public static String compution(String startTime,String endTime){
        try {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        Date endDate= df.parse(endTime.toString());
        Date startDate = df.parse(startTime.toString());
        long l=endDate.getTime()-startDate.getTime();

        long day=l/(24*60*60*1000);

        long hour=(l/(60*60*1000)-day*24);

        long min=((l/(60*1000))-day*24*60-hour*60);

        long s=(l/1000-day*24*60*60-hour*60*60-min*60);

        String res = "";

        if(day != 0) res+=day+"天";
        if(hour != 0) res+=hour+"小时";
        if(min != 0) res+=min+"分";
        if(s != 0) res+=s+"秒";

        return res;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 比较两个时间大小
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean computionBf(String startTime,String endTime){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
    	try{
    		Date bt = sdf.parse(startTime); 
    		Date et = sdf.parse(endTime); 
    		if (bt.before(et)||bt.equals(et)){ 
    			return true;
    		}else{ 
    			return false;
    		} 	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return false;
    }

}
