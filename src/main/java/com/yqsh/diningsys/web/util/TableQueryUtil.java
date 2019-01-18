package com.yqsh.diningsys.web.util;

import com.yqsh.diningsys.core.util.SpringContextUtil;

import com.yqsh.diningsys.core.util.pay.DgConstants;
import com.yqsh.diningsys.web.service.sysSettings.SysHelpService;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.yqsh.diningsys.core.entity.SystemConstants.*;

/**
 * 创建时间 2017-08-04 17:23
 * 分月表之后跨月，跨时间查询，指定时间查询
 * @author zhshuo
 */
public final class TableQueryUtil {

    static SysHelpService sysHelpService;

    static {
        sysHelpService = SpringContextUtil.getApplicationContext().getBean(SysHelpService.class);
    }

    private static Integer timeMatch(String time){
        if(time.matches(DATE_FORMATE_TIME_REGEXP)){
            return  1;
        }else if(time.matches(DATE_FORMATE_DATE_REGEXP)){
            return 2;
        }else{
            return 3;
        }
    }

    /**
     * 根据传入的时间来返回时间跨度的几个月后缀
     * @param beginTime
     * @param endTime
     * @return
     * @throws ParseException
     */
    public static List<String> tableNameUtilWithMonthRange(String beginTime, String endTime) {
        try {
            if(StringUtils.isEmpty(beginTime) && StringUtils.isEmpty(endTime)){
                return tabelSuffixGet(null,1,timeMatch(beginTime));
            }
            if(!StringUtils.isEmpty(beginTime) && StringUtils.isEmpty(endTime)) {//只有开始时间
                return tabelSuffixGet(beginTime,1,timeMatch(beginTime));
            }else if(StringUtils.isEmpty(beginTime) && !StringUtils.isEmpty(endTime)) {//只有结束时间
                return tabelSuffixGet(endTime,2,timeMatch(endTime));
            }else{//包含开始时间以及结束时间
                return tabelSuffixRangeGet(beginTime,endTime,timeMatch(beginTime));
            }
        } catch (ParseException e) {
            throw new RuntimeException("tableNameUtilWithMonthRange Exception");
        }
    }

    /**
     * 根据传入的时间来获取是否存在指定月份的表
     * @param time
     * @return
     * @throws ParseException
     */
    public static String tableNameUtilWithMonthSingle(String time) {
        try {
            if(StringUtils.isEmpty(time)){
                return null;
            }
            Integer formateType = timeMatch(time);
            return sysHelpService.selectWithSingleTime(getDefaultDataBaseName(),stringTimeParse(time,formateType));
        } catch (Exception e) {
            throw new RuntimeException("tableNameUtilWithMonthSingle Exception");
        }
    }

    private static List<String> tabelSuffixRangeGet(String beginTime, String endTime, Integer formateType) throws ParseException {
        try {
            return sysHelpService.selectWithBegtinTimeAndEndTime(getDefaultDataBaseName(),beginTime,endTime,getRangeMonth(beginTime, endTime,formateType));
        } catch (ParseException e) {
            throw new RuntimeException("tabelSuffixRangeGet Exception");
        }
    }


    private static LinkedList<String> tabelSuffixGet(String beginOrEndTime,Integer queryType,Integer fomateType) throws ParseException{
        return sysHelpService.selectWithBegtinTime(getDefaultDataBaseName(),stringTimeParse(beginOrEndTime,fomateType),queryType);
    }

    private static LinkedList<String> getRangeMonth(String beginDateStr, String endDateStr, Integer formateType) throws ParseException{

        LinkedList<String> monthRangeList = new LinkedList();

        Date beginDate = new SimpleDateFormat(formateType==1?DATE_FORMATE_TIME:(formateType==2?DATE_FORMATE_DATE:DATE_FORMATE_YM)).parse(beginDateStr);
        Date endDate = new SimpleDateFormat(formateType==1?DATE_FORMATE_TIME:(formateType==2?DATE_FORMATE_DATE:DATE_FORMATE_YM)).parse(endDateStr);

        String beginMonth = new SimpleDateFormat(DATE_FORMATE_YM).format(beginDate);
        String endMonth = new SimpleDateFormat(DATE_FORMATE_YM).format(endDate);

        Date newBeginDate = new SimpleDateFormat(DATE_FORMATE_YM).parse(beginMonth);
        Date newEndDate = new SimpleDateFormat(DATE_FORMATE_YM).parse(endMonth);

        Calendar beginCal = Calendar.getInstance();
        beginCal.setTime(newBeginDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(newEndDate);

        if(beginCal.compareTo(endCal) == 0){//如果两个时间相等，则取该时间本月
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_YM);

            String month = sdf.format(beginCal.getTime());

            monthRangeList.add(month);
        }else{
            while(beginCal.compareTo(endCal) < 1){

                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATE_YM);

                String month = sdf.format(beginCal.getTime());

                monthRangeList.add(month);

                beginCal.add(Calendar.MONTH, 1);
            }
        }

        return monthRangeList;
    }

    public static Integer yearMothToInteger(String yearMonth){
        return Integer.parseInt(yearMonth.replace("-",""));
    }

    public static Integer yearMothToInteger_(String yearMonth){
        return Integer.parseInt(yearMonth.replace("_",""));
    }

    public static String stringTimeParse(String time,Integer formateType) throws ParseException{
        if(StringUtils.isEmpty(time))return null;
        String year = "",month = "",conditionBeginDate = "";
        Date beginDate = new SimpleDateFormat(formateType==1?DATE_FORMATE_TIME:(formateType==2?DATE_FORMATE_DATE:DATE_FORMATE_YM)).parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        year = calendar.get(Calendar.YEAR)+"";
        int monthTemp = calendar.get(Calendar.MONTH)+1;
        if(monthTemp < 10){
            month = "0" + monthTemp;
        }else{
            month = monthTemp+"";
        }
        conditionBeginDate = year+"_"+month;
        return conditionBeginDate;
    }

    public static Boolean openDayReportCheck(String beginTime,String endTime){
        try {
            Date beginDate = new SimpleDateFormat(DATE_FORMATE_TIME).parse(beginTime);

            Date endDate = new SimpleDateFormat(DATE_FORMATE_TIME).parse(endTime);

            String newBeginTime = new SimpleDateFormat(DATE_FORMATE_TIME).format(beginDate);
            String newEndTime = new SimpleDateFormat(DATE_FORMATE_TIME).format(endDate);

            Date newBeginDate = new SimpleDateFormat(DATE_FORMATE_DATE).parse(newBeginTime);

            Date newEndDate = new SimpleDateFormat(DATE_FORMATE_DATE).parse(newEndTime);

            Date now = new SimpleDateFormat(DATE_FORMATE_DATE).parse(new SimpleDateFormat(DATE_FORMATE_TIME).format(new Date()));

            Calendar beginCal = Calendar.getInstance(),
                    endCal = Calendar.getInstance(),
                    today_2 = Calendar.getInstance();

            beginCal.setTime(newBeginDate);
            endCal.setTime(newEndDate);

            today_2.setTime(now);
            today_2.add(Calendar.DATE,-getDefaultSwitchLimit());

            return beginCal.compareTo(today_2) > -1 && endCal.compareTo(today_2) > -1;
        } catch (Exception e) {
            throw new RuntimeException("openDayReportCheck RuntimeException");
        }

    }

    public static Boolean closedBillTimeCheck(String time){
        try {
            Integer integer = timeMatch(time);
            Date judgeDate = new SimpleDateFormat(integer==1?DATE_FORMATE_TIME:(integer==2?DATE_FORMATE_DATE:DATE_FORMATE_YM)).parse(time);
            Date now = new SimpleDateFormat(DATE_FORMATE_DATE).parse(new SimpleDateFormat(DATE_FORMATE_TIME).format(new Date()));

            Calendar judgeCal = Calendar.getInstance(),
                    nowCal = Calendar.getInstance(),
                    nowCal2 = Calendar.getInstance();

            judgeCal.setTime(judgeDate);

            nowCal2.setTime(new Date());

            nowCal.setTime(now);
            nowCal.add(Calendar.DATE,-getDefaultSwitchLimit());

            return judgeCal.compareTo(nowCal) > -1 && judgeCal.compareTo(nowCal2) < 1;
        } catch (ParseException e) {
            throw new RuntimeException("closedBillTimeCheck throw RuntimeException");
        }
    }

    private static String getDefaultDataBaseName(){
        return DgConstants.getApplicationPropertiesValue("defaultDataBaseName");
    }

    private static Integer getDefaultSwitchLimit(){
        return Integer.parseInt(DgConstants.getApplicationPropertiesValue("defaultSwitchLimit"));
    }

    public static Integer getDefaultReturnLimit(){
        return Integer.parseInt(DgConstants.getApplicationPropertiesValue("defaultReturn"));
    }

    public static int daysBetween(String startStr, String endStr) {
        int daysBetween = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date date1 = sdf.parse(startStr);
            Calendar startDate = Calendar.getInstance();
            startDate.setTime(date1);

            Date date2 = sdf.parse(endStr);
            Calendar endDate = Calendar.getInstance();
            endDate.setTime(date2);

            Calendar date = (Calendar) startDate.clone();

            while (date.before(endDate)) {
                date.add(Calendar.DAY_OF_MONTH, 1);
                daysBetween++;
            }
            return daysBetween;
        } catch (ParseException e) {
            throw new RuntimeException("daysBetween throw Exception");
        }
    }

}
