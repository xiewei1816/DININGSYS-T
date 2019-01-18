package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zengc on 2017/4/27.
 */

@Controller
@RequestMapping("/report/monthlyPassenger")
public class MonthlyPassengerController {

    @Autowired
    private DgOpenWaterService owService;

    @RequestMapping("/index")
    public String index(Model model){
        return "report/monthlypassenger/monthlyPassenger_index";
    }


    @RequestMapping("/body")
    public String content(HttpServletRequest request,String startTime) throws  Exception{
        if(StringUtil.isBlank(startTime)){
            request.setAttribute("list",new ArrayList());
            request.setAttribute("ctime","");
            request.setAttribute("ptime","");
            return "report/monthlypassenger/monthlyPassenger_content";
        }
        //选择时间
        String sTime = startTime + "-01 00:00:00";
        String eTime = startTime + "-31 23:59:59";
        //前一个月时间
        String preTime = getPreMonthTime(startTime);
        String pre_sTime = preTime + "-01 00:00:00";
        String pre_eTime = preTime + "-31 23:59:59";

        int mNum = getDaysOfMonth(startTime);
        int pre_num = getDaysOfMonth(preTime);
        List<Map<String, Object>> list = owService.getOWByDate(sTime, eTime);
        List<Map<String, Object>> pre_list = owService.getOWByDate(pre_sTime, pre_eTime);


        Map<String, Object> map = listToMap(list);
        Map<String, Object> pre_map = listToMap(pre_list);

        List<Object> mlist = new ArrayList<Object>();
        for(int i = 1;i<mNum+1;i++){
                Map<String,Object> day = new HashMap<String,Object>();
                if(map.containsKey(i+"")){
                    day.put("c",map.get(i+""));
                }else{
                    day.put("c","0");
                }

                if(pre_map.containsKey(i+"")){
                    day.put("pre",pre_map.get(i+""));
                }else{
                    day.put("pre","0");
                }
                mlist.add(day);
        }

        request.setAttribute("list",mlist);
        request.setAttribute("ctime",startTime);
        request.setAttribute("ptime",preTime);
        return "report/monthlypassenger/monthlyPassenger_content";
    }


    public int getDaysOfMonth(String time) throws  Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public String getPreMonthTime(String time) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date=sdf.parse(time);
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.MONTH,-1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();   //这个时间就是日期往后推一天的结果
        return sdf.format(date).toString();
    }

    public Map<String,Object> listToMap(List<Map<String, Object>> list) {
        Map<String,Object> result = new HashMap<String,Object>();
        if(list != null && list.size() > 0){
            for(int i = 0;i<list.size();i++){
                Map<String,Object> m = list.get(i);
                int s = (int) m.get("mday");
                BigDecimal pcount = (BigDecimal)m.get("pcount");
                result.put(s+"",pcount.intValue()+"");
            }
        }
        return result;
    }
    
	@RequestMapping("/exportXls")
	public Object selectPayWayReportList(HttpServletRequest request,String startTime) throws Exception{
			if(StringUtil.isBlank(startTime)){
	            request.setAttribute("list",new ArrayList());
	            request.setAttribute("ctime","");
	            request.setAttribute("ptime","");
	            return "report/monthlypassenger/exportXls";
	        }
	        //选择时间
	        String sTime = startTime + "-01 00:00:00";
	        String eTime = startTime + "-31 23:59:59";
	        //前一个月时间
	        String preTime = getPreMonthTime(startTime);
	        String pre_sTime = preTime + "-01 00:00:00";
	        String pre_eTime = preTime + "-31 23:59:59";

	        int mNum = getDaysOfMonth(startTime);
	        int pre_num = getDaysOfMonth(preTime);
	        List<Map<String, Object>> list = owService.getOWByDate(sTime, eTime);
	        List<Map<String, Object>> pre_list = owService.getOWByDate(pre_sTime, pre_eTime);


	        Map<String, Object> map = listToMap(list);
	        Map<String, Object> pre_map = listToMap(pre_list);

	        List<Object> mlist = new ArrayList<Object>();
	        for(int i = 1;i<mNum+1;i++){
	                Map<String,Object> day = new HashMap<String,Object>();
	                if(map.containsKey(i+"")){
	                    day.put("c",map.get(i+""));
	                }else{
	                    day.put("c","0");
	                }

	                if(pre_map.containsKey(i+"")){
	                    day.put("pre",pre_map.get(i+""));
	                }else{
	                    day.put("pre","0");
	                }
	                mlist.add(day);
	        }

	        request.setAttribute("list",mlist);
	        request.setAttribute("ctime",startTime);
	        request.setAttribute("ptime",preTime);
	        return "report/monthlypassenger/exportXls";
	}
}
