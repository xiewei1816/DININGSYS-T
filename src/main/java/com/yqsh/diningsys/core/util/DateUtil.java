package com.yqsh.diningsys.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 日期工具
 *
 * @author zhshuo create on 2016-08-12 15:00
 */
public class DateUtil {
	/**
	 * 日期格式如:20150625161505
	 */
	public static final String YYYYMMDDHH24MISS="yyyyMMddhhmmss";
	/**
	 * 日期格式如:2015-06-25 16:15:05
	 */
	public static final String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式精确到毫秒:20160626161606123
	 */
	public static final String YYYYMMDDHHMMSSSSS="yyyyMMddHHmmssSSS";
	/**
	 * 日期格式精确到毫秒:2016-06-26 16:16:06.123
	 */
	public static final String YYYY_MM_DD_HH_MM_SS_SSS="yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 日期格式，如：20161102
	 */
	public static final String YYYYMMDD="yyyyMMdd";
	/**
	 * 日期格式，如：20161102
	 */
	public static final String YYYY_MM_DD="yyyy-MM-dd";
	/**
	 * 日期格式如:20150625161505
	 */
	public static final String yyyyMMddHHmmss="yyyyMMddHHmmss";
    /**
     * 计算出两个日期之间相差的天数
     * 建议date1 大于 date2 这样计算的值为正数
     * @param date1 日期1
     * @param date2 日期2
     * @return date1 - date2
     */
    public static int dateInterval(long date1, long date2) {
        if(date2 > date1){
            date2 = date2 + date1;
            date1 = date2 - date1;
            date2 = date2 - date1;
        }

        // Canlendar 该类是一个抽象类
        // 提供了丰富的日历字段
        // 本程序中使用到了
        // Calendar.YEAR    日期中的年份
        // Calendar.DAY_OF_YEAR     当前年中的天数
        // getActualMaximum(Calendar.DAY_OF_YEAR) 返回今年是 365 天还是366天
        Calendar calendar1 = Calendar.getInstance(); // 获得一个日历
        calendar1.setTimeInMillis(date1); // 用给定的 long 值设置此 Calendar 的当前时间值。

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        // 先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);

        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if(y1 - y2 > 0){
            day = numerical(maxDays, d1, d2, y1, y2, calendar2);
        }else{
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 日期间隔计算
     * 计算公式(示例):
     *      20121201- 20121212
     *      取出20121201这一年过了多少天 d1 = 天数  取出20121212这一年过了多少天 d2 = 天数
     *      如果2012年这一年有366天就要让间隔的天数+1，因为2月份有29日。
     * @param maxDays   用于记录一年中有365天还是366天
     * @param d1    表示在这年中过了多少天
     * @param d2    表示在这年中过了多少天
     * @param y1    当前为2012年
     * @param y2    当前为2012年
     * @param calendar  根据日历对象来获取一年中有多少天
     * @return  计算后日期间隔的天数
     */
    public static int numerical(int maxDays, int d1, int d2, int y1, int y2, Calendar calendar){
        int day = d1 - d2;
        int betweenYears = y1 - y2;
        List<Integer> d366 = new ArrayList<Integer>();

        if(calendar.getActualMaximum(Calendar.DAY_OF_YEAR) == 366){
            System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
            day += 1;
        }

        for (int i = 0; i < betweenYears; i++) {
            // 当年 + 1 设置下一年中有多少天
            calendar.set(Calendar.YEAR, (calendar.get(Calendar.YEAR)) + 1);
            maxDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
            // 第一个 366 天不用 + 1 将所有366记录，先不进行加入然后再少加一个
            if(maxDays != 366){
                day += maxDays;
            }else{
                d366.add(maxDays);
            }
            // 如果最后一个 maxDays 等于366 day - 1
            if(i == betweenYears-1 && betweenYears > 1 && maxDays == 366){
                day -= 1;
            }
        }

        for(int i = 0; i < d366.size(); i++){
            // 一个或一个以上的366天
            if(d366.size() >= 1){
                day += d366.get(i);
            }
        }
        return day;
    }

    /**
     * 将日期字符串装换成日期
     * @param strDate   日期支持年月日 示例:yyyy-MM-dd
     * @return  1970年1月1日器日期的毫秒数
     */
    public static long getDateTime(String strDate) {
        return getDateByFormat(strDate, "yyyy-MM-dd").getTime();
    }

    /**
     * @param strDate   日期字符串
     * @param format    日期格式
     * @return      Date
     */
    public static Date getDateByFormat(String strDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try{
            return (sdf.parse(strDate));
        }catch (Exception e){
            return null;
        }
    }
    /**
	 * 将date转换成String
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String dateToStr(Date date, String aMask) {
		String ret = null;
		String mask = aMask;
		if (mask == null || "".equals(mask))
			mask = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(mask);
		ret = sdf.format(date);
		return ret;
	}
    public static Integer getMiddleDate(String beginTime,String endTime) {
        long date1 = getDateTime(beginTime);
        long date2 = getDateTime(endTime);
        Integer day = dateInterval(date1, date2);
        return day;
    }
    /**
	 * 一种日期格式转换为另一种日期格式,并返回日期类型
	 *@create_date 2016-6-3
	 *@param 
	 *@param date 需要转换的日期
	 *@param pattern1 转换前日期格式
	 *@param pattern2 转换后日期格式
	 *@return
	 */
	public static Date formatStrToDate(String date,String pattern1,String pattern2) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1); 
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2); 
		Date dt=null;
		if(StringUtils.isNotBlank(date)){
			try {
				Date d = sdf1.parse(date);
				 
				dt=sdf2.parse(sdf2.format(d));
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		return dt;
	}
	/**
	 * 字符串格式转换为另一种字符串格式
	 * @author jianglei
	 * 2016年8月8日 上午9:05:55
	 * @param dateStr 日期
	 * @param pattern1  日期转换前格式样式
	 * @param pattern2  日期转换后格式样式
	 * @return
	 */
	public static String fromatStrToStr(String dateStr,String pattern1,String pattern2){
		SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1); 
		String st=null;
		if(StringUtils.isNotBlank(dateStr)){
			try {
				Date d = sdf1.parse(dateStr);
				st=dateToStr(d,pattern2);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return st;
	}
	
	/*
	 * 
	 * 计算两时间相隔分钟
	 */
	public static long dateIntervalMin(Date startTime,Date endTime)
	{
		Calendar dateOne=Calendar.getInstance(),dateTwo=Calendar.getInstance();
		dateOne.setTime(endTime);
		dateTwo.setTime(startTime);
		long timeOne=dateOne.getTimeInMillis();
		long timeTwo=dateTwo.getTimeInMillis();
		long minute=(timeOne-timeTwo)/(1000*60);//转化分钟
		return minute;
	}
	
	/*
	 * 
	 * 计算两时间相隔天
	 */
	public static long dateIntervalDay(Date startTime,Date endTime)
	{
		Calendar dateOne=Calendar.getInstance(),dateTwo=Calendar.getInstance();
		dateOne.setTime(endTime);	//设置为当前系统时间 
		dateTwo.setTime(startTime);			//设置为2015年1月15日
		long timeOne=dateOne.getTimeInMillis();
		long timeTwo=dateTwo.getTimeInMillis();
		long minute=(timeOne-timeTwo)/(1000*60*60*24);//转化天
		return minute;
	}
	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
}
