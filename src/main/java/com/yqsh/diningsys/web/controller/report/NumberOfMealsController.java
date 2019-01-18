package com.yqsh.diningsys.web.controller.report;

import com.yqsh.diningsys.api.util.DoubleCompare;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;
import com.yqsh.diningsys.web.service.report.BackReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-02-08 13:31
 * 就餐人数
 * @author zhshuo
 */
@Controller
@RequestMapping("/report/NumberOfMeals")
public class NumberOfMealsController extends BaseController{

    @Autowired
    private BackReportService backReportService;
    @Autowired
    private TbBisService tbBisService;
    @Autowired
    private DgOpenWaterService owService;

    @RequestMapping("/index")
    public String index()
    {
        return "report/numberOfMeals/numberOfMeals_index";
    }

    @ResponseBody
    @RequestMapping("/dataSearch")
    public List<Map> dataSearch(String startTime, String endTime){
        try {
            return backReportService.queryNumberOfMealsBy24Hour(startTime,endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value = "exportXls")
    public ModelAndView exportXls(Model model,String startTime, String endTime) {
    	List<Map> mealsList = backReportService.queryNumberOfMealsBy24Hour(startTime,endTime);
        model.addAttribute("mealsList", mealsList);
        ModelAndView modelAndView = new ModelAndView("report/numberOfMeals/exportXls");
        return modelAndView;
    }

    /**
     * zengchao
     * @return
     */
    @RequestMapping("/index_pnum")
    public String index_pnum()
    {
        return "report/numberOfMeals/numberOfMeals_pnum_index";
    }

    @RequestMapping(value = "numberOfMeals_pnum_content")
    public String numberOfMeals_pnum_content(HttpServletRequest request, String startTime, String endTime)
    {
        List<Object> result = new ArrayList();
        List<TbBis> bislist = tbBisService.selectAllBis();
        int psum = 0; //总人数
        double jesum = 0.0; //总金额
        int cssum = 0;  //总次数
        if(bislist != null){
            for(int i = 0;i<bislist.size();i++){
                Map<String,Object> map = new HashMap<String,Object>();
                TbBis bis = bislist.get(i);
                int cbis = bis.getId();
                List<Map<String, Object>> list = owService.getPCountAndCNum(startTime, endTime, cbis);
                double s_cnum = 0.0;
                for(int j = 0;j<list.size();j++){
                    Map<String, Object> m = list.get(j);
                    int pcount = (int) m.get("pcount");
                    long pnumcount = (long)m.get("psumcount");
                    long hj = pcount*pnumcount;
                    Double str_cnum = (Double)m.get("cnum");
                    BigDecimal b   =   new   BigDecimal(str_cnum);
                    double   cnum   =   b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    s_cnum += cnum;
                    m.put("cnum",cnum);
                    m.put("hj",hj);
                    double s = hj == 0?0:cnum/hj;
                    BigDecimal ss   =   new   BigDecimal(s);
                    m.put("rjxf",ss.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    psum += hj;
                }
                map.put("bis",bis);
                map.put("datas",list);
                map.put("s_cnum",s_cnum);
                result.add(map);
                //计算总金额
                jesum +=s_cnum;
            }
        }

        for(int i = 0;i<result.size();i++){
            Map<String,Object> m = (Map<String,Object>) result.get(i);
            List<Map<String, Object>> datas = (List<Map<String, Object>>) m.get("datas");
            int s_jccs = 0;  //就餐次数
            int s_hj = 0; //合计
            if(datas != null){
                for(int j=0;j<datas.size();j++){
                    Map<String, Object> data = datas.get(j);
                    long hj = (long) data.get("hj");
                    double pj = psum==0?0:(double)(hj*100)/(double)psum;
                    BigDecimal ss   =   new   BigDecimal(pj);
                    long psumcount = (long) data.get("psumcount");
                    data.put("pj",ss.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    s_jccs += psumcount;
                    s_hj += hj;
                }
            }
            double ss_pj = psum==0?0:(double)(s_hj*100)/(double)psum;
            BigDecimal ss   =   new   BigDecimal(ss_pj);
            m.put("s_jccs",s_jccs);
            m.put("s_hj",s_hj);
            m.put("s_pj",ss.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
            cssum += s_jccs;
        }

        request.setAttribute("psum",psum);
        request.setAttribute("jesum",jesum);
        request.setAttribute("cssum",cssum);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        request.setAttribute("list",result);
        return "report/numberOfMeals/numberOfMeals_pnum_content";
    }
}
