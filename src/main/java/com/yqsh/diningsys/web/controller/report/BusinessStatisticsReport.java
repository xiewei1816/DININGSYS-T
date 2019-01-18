package com.yqsh.diningsys.web.controller.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Authorization;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.deskBusiness.DgProductPhaseLeftmenu;
import com.yqsh.diningsys.web.service.report.ReportPayWayService;
/**
 * Created on 2017-04-27 
 * 营业统计报表
 * @author hs
 */
@Controller
@RequestMapping("/report/businessStatistics")
public class BusinessStatisticsReport extends BaseController{
	@Autowired
	private ReportPayWayService reportPayWayService;
	@RequestMapping("/index")
	public String index(Model model){
	    return "report/businessStatistics/businessStatistics_index";
	}
	 
	@RequestMapping("/date")
	public String date(Model model){
	    return "report/businessStatistics/businessStatistics_date";
	}
	
	@RequestMapping("/week")
	public String week(Model model){
	    return "report/businessStatistics/businessStatistics_week";
	}
	
	@RequestMapping("/month")
	public String month(Model model){
	    return "report/businessStatistics/businessStatistics_month";
	}
	 /**
	 * 
	 * 左侧树菜单
	 * 
	 * @return
	 */
	@RequestMapping("/getTreeNodes")
	@ResponseBody
	public List<DgProductPhaseLeftmenu> getTreeNodes() {
		String id = getRequest().getParameter("id");
		List<DgProductPhaseLeftmenu> menu = new ArrayList<DgProductPhaseLeftmenu>();
	
		// 获取根节点
		if (StringUtils.isEmpty(id)) {
			DgProductPhaseLeftmenu m = new DgProductPhaseLeftmenu();
			m.setId(1);
			m.setName("营业统计报表");
			m.setChildCount(1);
			m.setParentId(0);
			menu.add(m);
		} else {
			DgProductPhaseLeftmenu date = new DgProductPhaseLeftmenu();
			date.setId(2);
			date.setName("营业统计日报");
			date.setChildCount(0);
			date.setParentId(1);
			date.setMurl("/DININGSYS/report/businessStatistics/date");
			menu.add(date);
			
			DgProductPhaseLeftmenu week = new DgProductPhaseLeftmenu();
			week.setId(3);
			week.setName("营业统计周报");
			week.setChildCount(0);
			week.setParentId(1);
			week.setMurl("/DININGSYS/report/businessStatistics/week");
			menu.add(week);
			
			DgProductPhaseLeftmenu month = new DgProductPhaseLeftmenu();
			month.setId(4);
			month.setName("营业统计月报");
			month.setChildCount(0);
			month.setParentId(1);
			month.setMurl("/DININGSYS/report/businessStatistics/month");
			menu.add(month);
		}
		return menu;
	}
	
    @ResponseBody
    @RequestMapping("/dataSearch_date")
    public Object dataSearch_date(String searchDate){
        try {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtils.isEmpty(searchDate)){
            	searchDate = format.format(new Date());
            }
            Map alret = new HashMap();
            List<Map> ret = new ArrayList<Map>();
            Map<String,Object> srfl =  reportPayWayService.selectBusinessStatisticsDateSufl(searchDate);
            if(StringUtils.isEmpty(srfl)){
                return alret;
            }
            Map xsxjMap = new HashMap();
            xsxjMap.put("name","销售小计");
            xsxjMap.put("zr",srfl.get("zrxsxj"));
            xsxjMap.put("jr",srfl.get("jrxsxj"));
            xsxjMap.put("by",srfl.get("byxsxj"));
            xsxjMap.put("bn",srfl.get("bnxsxj"));
            ret.add(xsxjMap);
            Map deyhMap = new HashMap();
            deyhMap.put("name","定额优惠");
            deyhMap.put("zr",srfl.get("zrdeyh"));
            deyhMap.put("jr",srfl.get("jrdeyh"));
            deyhMap.put("by",srfl.get("bydeyh"));
            deyhMap.put("bn",srfl.get("bndeyh"));
            ret.add(deyhMap);
            Map mlMap = new HashMap();
            mlMap.put("name","抹零");
            mlMap.put("zr",srfl.get("zrml"));
            mlMap.put("jr",srfl.get("jrml"));
            mlMap.put("by",srfl.get("byml"));
            mlMap.put("bn",srfl.get("bnml"));
            ret.add(mlMap);
            
            Map ysMap = new HashMap();
            ysMap.put("name","销售收入合计");
            ysMap.put("zr",srfl.get("zrys"));
            ysMap.put("jr",srfl.get("jrys"));
            ysMap.put("by",srfl.get("byys"));
            ysMap.put("bn",srfl.get("bnys"));
            ret.add(ysMap);
            
            List<Map> sk = new ArrayList<Map>();
            List<Map<String,Object>> skl =  reportPayWayService.selectBusinessStatisticsDateSk(searchDate); 
            Map skall = new HashMap();
            skall.put("name","收款合计");
            double zrsk = 0.0;
            double jrsk = 0.0;
            double bysk = 0.0;
            double bnsk = 0.0;
            for(int i=0;i<skl.size();i++){
//            	if(skl.get(i).get("cw_code").toString().equals("ALIPAY")){
//            		skl.get(i).put("name","支付宝");
//            	} else if(skl.get(i).get("cw_code").toString().equals("CREDIT_CARD")){
//            		skl.get(i).put("name","信用卡");
//            	} else if(skl.get(i).get("cw_code").toString().equals("RMB")){
//            		skl.get(i).put("name","人民币");
//            	} else if(skl.get(i).get("cw_code").toString().equals("WECHAT")){
//            		skl.get(i).put("name","微信");
//            	} else if(skl.get(i).get("cw_code").toString().equals("HYGZ")){
//            		skl.get(i).put("name","会员挂账");
//            	} else if(skl.get(i).get("cw_code").toString().equals("HYZF")){
//            		skl.get(i).put("name","会员支付");
//            	}
            	skl.get(i).put("name",skl.get(i).get("zfname").toString());
            	skl.get(i).put("znsum",skl.get(i).get("znsum") == null ? 0 :Double.valueOf(skl.get(i).get("znsum").toString()));
            	skl.get(i).put("jsum",skl.get(i).get("jsum") == null ? 0 :Double.valueOf(skl.get(i).get("jsum").toString()));
            	skl.get(i).put("bysum",skl.get(i).get("bysum") == null ? 0 :Double.valueOf(skl.get(i).get("bysum").toString()));
            	skl.get(i).put("bnsum",skl.get(i).get("bnsum") == null ? 0 :Double.valueOf(skl.get(i).get("bnsum").toString()));
            	zrsk = MathExtend.add(zrsk,skl.get(i).get("znsum") == null ? 0 :Double.valueOf(skl.get(i).get("znsum").toString()));
        		jrsk = MathExtend.add(jrsk,skl.get(i).get("jsum") == null ? 0 :Double.valueOf(skl.get(i).get("jsum").toString()));
        		bysk = MathExtend.add(bysk,skl.get(i).get("bysum") == null ? 0 :Double.valueOf(skl.get(i).get("bysum").toString()));
        		bnsk = MathExtend.add(bnsk,skl.get(i).get("bnsum") == null ? 0 :Double.valueOf(skl.get(i).get("bnsum").toString()));
            	sk.add(skl.get(i));
            }
            skall.put("znsum",zrsk);
            skall.put("jsum",jrsk);
            skall.put("bysum",bysk);
            skall.put("bnsum",bnsk);
            sk.add(skall);
            List<Map<String,Object>> rj =  reportPayWayService.selectBusinessStatisticsDateRj(searchDate);
            Map rjmoney = new HashMap();
            rjmoney.put("name","人均消费");
            rjmoney.put("bnp",rj.get(0).get("bnp").toString().equals("0")  ? 0.0 : MathExtend.divide(bnsk, Integer.valueOf(rj.get(0).get("bnp").toString()),2));
            rjmoney.put("zrp",rj.get(0).get("zrp").toString().equals("0")  ? 0.0 : MathExtend.divide(zrsk, Integer.valueOf(rj.get(0).get("zrp").toString()),2));
            rjmoney.put("jrp",rj.get(0).get("jrp").toString().equals("0")  ? 0.0 : MathExtend.divide(jrsk, Integer.valueOf(rj.get(0).get("jrp").toString()),2));
            rjmoney.put("byp",rj.get(0).get("byp").toString().equals("0")  ? 0.0 : MathExtend.divide(bysk, Integer.valueOf(rj.get(0).get("byp").toString()),2));
            rj.get(0).put("name","消费人数");
            rj.get(0).put("bnp",rj.get(0).get("bnp"));
            rj.get(0).put("zrp",rj.get(0).get("zrp"));
            rj.get(0).put("jrp",rj.get(0).get("jrp"));
            rj.get(0).put("byp",rj.get(0).get("byp"));
            rj.add(rjmoney);
            alret.put("srfl",ret);
            alret.put("skfl",sk);
            alret.put("rjfl",rj);
            return alret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @ResponseBody
    @RequestMapping("/dataSearch_month")
    public Object dataSearch_month(String searchDate){
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            if(StringUtils.isEmpty(searchDate)){
            	searchDate = format.format(new Date())+"-01";
            } else {
            	searchDate = searchDate + "-01";
            }
            
            String month = searchDate.substring(5, 7);
            String year = searchDate.substring(0,4);
            Integer im = Integer.valueOf(month);
            Integer iy = Integer.valueOf(year);
            String sDate;
            String xDate;
            String snDate;
            String snxDate;
            snDate = (iy-1)+"-"+month+"-01";
            if(im == 1){
            	sDate = (iy-1)+"-12-01";
            	xDate = iy+"-02-01";
            	snxDate = (iy-1)+"-02-01";
            } else if(im == 12){
            	sDate = iy+"-11-01";
            	xDate = (iy+1)+"-01-01";
            	snxDate = iy+"-01-01";
            } else {
            	String sm = (im-1)+"";
            	if(sm.length() == 1){
            		sm = ""+sm;
            	}
            	String xm = (im+1)+"";
            	if(xm.length() == 1){
            		xm = ""+xm;
            	}
            	sDate = iy+"-"+sm+"-01";
            	xDate = iy+"-"+xm+"-01";
            	snxDate = (iy-1)+"-"+xm+"-01";
            }
            List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
    		Map<String,Object> skl =  reportPayWayService.selectBusinessStatisticsMonth(searchDate);
    		if(StringUtils.isEmpty(skl)){
                return ret;
            }
    		Map<String,Object> head = new HashMap<String, Object>();
    		head.put("name","统计周期");
    		head.put("by",searchDate + "至"+xDate);
    		head.put("sy",sDate + "至"+searchDate);
    		head.put("snby",snDate + "至" + snxDate);
    		head.put("sbypercent","上月本年增长率");
    		head.put("snbypercent","上月去年同年增减率");
    		ret.add(head);
    		
    		Map<String,Object> head2 = new HashMap<String, Object>();
    		head2.put("name","统计明细");
    		head2.put("by","本月累计");
    		head2.put("sy","上月累计");
    		head2.put("sbypercent","(本月-上月)/上月");
    		head2.put("snby","去年同月");
    		head2.put("snbypercent","上月/去年同月赠减率");
    		ret.add(head2);
    		
    		
    		Map<String,Object> yysr = new HashMap<String, Object>();
    		yysr.put("name","营业总收入");
    		yysr.put("by",skl.get("byys"));
    		yysr.put("sy",skl.get("syys"));
    		yysr.put("snby",skl.get("sntyys"));
    		yysr.put("sbypercent",skl.get("sbypercent"));
    		yysr.put("snbypercent",skl.get("snbypercent"));
    		ret.add(yysr);
    		
    		Map<String,Object> yyzrs = new HashMap<String, Object>();
    		yyzrs.put("name","消费总人数");
    		yyzrs.put("by",skl.get("byp"));
    		yyzrs.put("sy",skl.get("syp"));
    		yyzrs.put("snby",skl.get("sntyp"));
    		yyzrs.put("sbypercent",skl.get("sbyrpercent"));
    		yyzrs.put("snbypercent",skl.get("snbyrpercent"));
    		ret.add(yyzrs);
    		
    		Map<String,Object> rjxf = new HashMap<String, Object>();
    		rjxf.put("name","人均消费");
    		rjxf.put("by",skl.get("byrjxf"));
    		rjxf.put("sy",skl.get("syrjxf"));
    		rjxf.put("snby",skl.get("snbyrjxf"));
    		double rjxfbzypercent = 0.0;
    		double rjxfbznpercent = 0.0;
    		if(Double.valueOf(skl.get("byrjxf").toString()) > 0 && Double.valueOf(skl.get("syrjxf").toString()) > 0){
    			double byrjxf = Double.valueOf(skl.get("byrjxf").toString());
    			double syrjxf = Double.valueOf(skl.get("syrjxf").toString());
    			rjxfbzypercent = MathExtend.divide(MathExtend.subtract(byrjxf, syrjxf),syrjxf,4)*100;
    		}
    		if(Double.valueOf(skl.get("byrjxf").toString()) > 0 && Double.valueOf(skl.get("snbyrjxf").toString()) > 0){
    			double byrjxf = Double.valueOf(skl.get("byrjxf").toString());
    			double snbyrjxf = Double.valueOf(skl.get("snbyrjxf").toString());
    			rjxfbznpercent = MathExtend.divide(MathExtend.subtract(byrjxf, snbyrjxf),snbyrjxf,4)*100;
    		}
    		rjxf.put("sbypercent",rjxfbzypercent);
    		rjxf.put("snbypercent",rjxfbznpercent);
    		ret.add(rjxf);
    		
    		Map<String,Object> rijxf = new HashMap<String, Object>();
    		rijxf.put("name","日均消费");
    		rijxf.put("by",skl.get("byrj"));
    		rijxf.put("sy",skl.get("sbyrj"));
    		rijxf.put("snby",skl.get("snbyrj"));
    		double rijxfbzypercent = 0.0;
    		double rijxfbznpercent = 0.0;
    		if(Double.valueOf(skl.get("byrj").toString()) > 0 && Double.valueOf(skl.get("sbyrj").toString()) > 0){
    			double byrj = Double.valueOf(skl.get("byrjxf").toString());
    			double sbyrj = Double.valueOf(skl.get("sbyrj").toString());
    			rijxfbzypercent = MathExtend.divide(MathExtend.subtract(byrj, sbyrj),sbyrj,4)*100;
    		}
    		if(Double.valueOf(skl.get("byrj").toString()) > 0 && Double.valueOf(skl.get("snbyrj").toString()) > 0){
    			double byrj = Double.valueOf(skl.get("byrj").toString());
    			double snbyrj = Double.valueOf(skl.get("snbyrj").toString());
    			rijxfbznpercent = MathExtend.divide(MathExtend.subtract(byrj, snbyrj),snbyrj,4)*100;
    		}
    		rijxf.put("sbypercent",rijxfbzypercent);
    		rijxf.put("snbypercent",rijxfbznpercent);
    		ret.add(rijxf);
    		return ret;
    	} catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    @ResponseBody
    @RequestMapping("/dataSearch_week")
    public Object dataSearch_week(String sStartDate,String sEndDate,String bStartDate,String bEndDate){
    	try {
    		
            List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
    		Map<String,Object> skl =  reportPayWayService.selectBusinessStatisticsWeek(sStartDate,sEndDate,bStartDate,bEndDate);
    		if(StringUtils.isEmpty(skl)){
    			return ret;
    		}
    		Map<String,Object> head = new HashMap<String, Object>();
    		head.put("name","统计周期");
    		head.put("by",bStartDate + "至"+bEndDate);
    		head.put("sy",sStartDate + "至"+sEndDate);
    		head.put("snby","增减率");
    		ret.add(head);
    		
    		Map<String,Object> head2 = new HashMap<String, Object>();
    		head2.put("name","统计明细");
    		head2.put("by","本周累计");
    		head2.put("sy","上周累计");
    		head2.put("snby","(本周-上周)/上周");
    		ret.add(head2);
    		
    		
    		Map<String,Object> yysr = new HashMap<String, Object>();
    		yysr.put("name","营业总收入");
    		yysr.put("by",skl.get("byz"));
    		yysr.put("sy",skl.get("syz"));
    		yysr.put("snby",skl.get("yypercent"));
    		ret.add(yysr);
    		
    		Map<String,Object> yyzrs = new HashMap<String, Object>();
    		yyzrs.put("name","消费总人数");
    		yyzrs.put("by",skl.get("byp"));
    		yyzrs.put("sy",skl.get("syp"));
    		yyzrs.put("snby",skl.get("rrpercent"));
    		ret.add(yyzrs);
    		
    		Map<String,Object> rjxf = new HashMap<String, Object>();
    		rjxf.put("name","人均消费");
    		rjxf.put("by",skl.get("bwjxf"));
    		rjxf.put("sy",skl.get("swjxf"));
    		double rjxfbzypercent = 0.0;
    		if(Double.valueOf(skl.get("bwjxf").toString()) > 0 && Double.valueOf(skl.get("swjxf").toString()) > 0){
    			double byrjxf = Double.valueOf(skl.get("bwjxf").toString());
    			double syrjxf = Double.valueOf(skl.get("swjxf").toString());
    			rjxfbzypercent = MathExtend.multiply(MathExtend.divide(MathExtend.subtract(byrjxf, syrjxf),syrjxf,4),100);
    		}
    		rjxf.put("snby",rjxfbzypercent);
    		ret.add(rjxf);
    		
    		Map<String,Object> rijxf = new HashMap<String, Object>();
    		rijxf.put("name","日均消费");
    		rijxf.put("by",skl.get("byrj"));
    		rijxf.put("sy",skl.get("sbyrj"));
    		double rijxfbzypercent = 0.0;
    		if(Double.valueOf(skl.get("byrj").toString()) > 0 && Double.valueOf(skl.get("sbyrj").toString()) > 0){
    			double byrj = Double.valueOf(skl.get("byrj").toString());
    			double sbyrj = Double.valueOf(skl.get("sbyrj").toString());
    			rijxfbzypercent = MathExtend.multiply(MathExtend.divide(MathExtend.subtract(byrj, sbyrj),sbyrj,4),100);
    		}
    		rijxf.put("snby",rijxfbzypercent);
    		ret.add(rijxf);
    		return ret;
    	} catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }
    
    
    @RequestMapping("/dataSearch_date_export")
    public Object dataSearch_date_export(String searchDate){
        try {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtils.isEmpty(searchDate)){
            	searchDate = format.format(new Date());
            }
            Map alret = new HashMap();
            List<Map> ret = new ArrayList<Map>();
            Map<String,Object> srfl =  reportPayWayService.selectBusinessStatisticsDateSufl(searchDate);
            Map xsxjMap = new HashMap();
            xsxjMap.put("name","销售小计");
            xsxjMap.put("zr",srfl.get("zrxsxj"));
            xsxjMap.put("jr",srfl.get("jrxsxj"));
            xsxjMap.put("by",srfl.get("byxsxj"));
            xsxjMap.put("bn",srfl.get("bnxsxj"));
            ret.add(xsxjMap);
            Map deyhMap = new HashMap();
            deyhMap.put("name","定额优惠");
            deyhMap.put("zr",srfl.get("zrdeyh"));
            deyhMap.put("jr",srfl.get("jrdeyh"));
            deyhMap.put("by",srfl.get("bydeyh"));
            deyhMap.put("bn",srfl.get("bndeyh"));
            ret.add(deyhMap);
            Map mlMap = new HashMap();
            mlMap.put("name","抹零");
            mlMap.put("zr",srfl.get("zrml"));
            mlMap.put("jr",srfl.get("jrml"));
            mlMap.put("by",srfl.get("byml"));
            mlMap.put("bn",srfl.get("bnml"));
            ret.add(mlMap);
            
            Map ysMap = new HashMap();
            ysMap.put("name","销售收入合计");
            ysMap.put("zr",srfl.get("zrys"));
            ysMap.put("jr",srfl.get("jrys"));
            ysMap.put("by",srfl.get("byys"));
            ysMap.put("bn",srfl.get("bnys"));
            ret.add(ysMap);
            
            List<Map> sk = new ArrayList<Map>();
            List<Map<String,Object>> skl =  reportPayWayService.selectBusinessStatisticsDateSk(searchDate); 
            Map skall = new HashMap();
            skall.put("name","收款合计");
            double zrsk = 0.0;
            double jrsk = 0.0;
            double bysk = 0.0;
            double bnsk = 0.0;
            for(int i=0;i<skl.size();i++){
            	skl.get(i).put("name",skl.get(i).get("zfname").toString());
            	skl.get(i).put("znsum",skl.get(i).get("znsum") == null ? 0 :Double.valueOf(skl.get(i).get("znsum").toString()));
            	skl.get(i).put("jsum",skl.get(i).get("jsum") == null ? 0 :Double.valueOf(skl.get(i).get("jsum").toString()));
            	skl.get(i).put("bysum",skl.get(i).get("bysum") == null ? 0 :Double.valueOf(skl.get(i).get("bysum").toString()));
            	skl.get(i).put("bnsum",skl.get(i).get("bnsum") == null ? 0 :Double.valueOf(skl.get(i).get("bnsum").toString()));
            	zrsk = MathExtend.add(zrsk,skl.get(i).get("znsum") == null ? 0 :Double.valueOf(skl.get(i).get("znsum").toString()));
        		jrsk = MathExtend.add(jrsk,skl.get(i).get("jsum") == null ? 0 :Double.valueOf(skl.get(i).get("jsum").toString()));
        		bysk = MathExtend.add(bysk,skl.get(i).get("bysum") == null ? 0 :Double.valueOf(skl.get(i).get("bysum").toString()));
        		bnsk = MathExtend.add(bnsk,skl.get(i).get("bnsum") == null ? 0 :Double.valueOf(skl.get(i).get("bnsum").toString()));
            	sk.add(skl.get(i));
            }
            skall.put("znsum",zrsk);
            skall.put("jsum",jrsk);
            skall.put("bysum",bysk);
            skall.put("bnsum",bnsk);
            sk.add(skall);
            List<Map<String,Object>> rj =  reportPayWayService.selectBusinessStatisticsDateRj(searchDate);
            Map rjmoney = new HashMap();
            rjmoney.put("name","人均消费");
            rjmoney.put("bnp",rj.get(0).get("bnp").toString().equals("0")  ? 0.0 : MathExtend.divide(bnsk, Integer.valueOf(rj.get(0).get("bnp").toString()),2));
            rjmoney.put("zrp",rj.get(0).get("zrp").toString().equals("0")  ? 0.0 : MathExtend.divide(zrsk, Integer.valueOf(rj.get(0).get("zrp").toString()),2));
            rjmoney.put("jrp",rj.get(0).get("jrp").toString().equals("0")  ? 0.0 : MathExtend.divide(jrsk, Integer.valueOf(rj.get(0).get("jrp").toString()),2));
            rjmoney.put("byp",rj.get(0).get("byp").toString().equals("0")  ? 0.0 : MathExtend.divide(bysk, Integer.valueOf(rj.get(0).get("byp").toString()),2));
            rj.get(0).put("name","消费人数");
            rj.get(0).put("bnp",rj.get(0).get("bnp"));
            rj.get(0).put("zrp",rj.get(0).get("zrp"));
            rj.get(0).put("jrp",rj.get(0).get("jrp"));
            rj.get(0).put("byp",rj.get(0).get("byp"));
            rj.add(rjmoney);
            alret.put("srfl",ret);
            alret.put("skfl",sk);
            alret.put("rjfl",rj);
            getRequest().setAttribute("data",alret);
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return "report/businessStatistics/businessStatistics_date_Xls";
    }
    
    @RequestMapping("/dataSearch_month_export")
    public Object dataSearch_month_export(String searchDate){
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
            if(StringUtils.isEmpty(searchDate)){
            	searchDate = format.format(new Date())+"-01";
            } else {
            	searchDate = searchDate + "-01";
            }
            
            String month = searchDate.substring(5, 7);
            String year = searchDate.substring(0,4);
            Integer im = Integer.valueOf(month);
            Integer iy = Integer.valueOf(year);
            String sDate;
            String xDate;
            String snDate;
            String snxDate;
            snDate = (iy-1)+"-"+month+"-01";
            if(im == 1){
            	sDate = (iy-1)+"-12-01";
            	xDate = iy+"-02-01";
            	snxDate = (iy-1)+"-02-01";
            } else if(im == 12){
            	sDate = iy+"-11-01";
            	xDate = (iy+1)+"-01-01";
            	snxDate = iy+"-01-01";
            } else {
            	String sm = (im-1)+"";
            	if(sm.length() == 1){
            		sm = ""+sm;
            	}
            	String xm = (im+1)+"";
            	if(xm.length() == 1){
            		xm = ""+xm;
            	}
            	sDate = iy+"-"+sm+"-01";
            	xDate = iy+"-"+xm+"-01";
            	snxDate = (iy-1)+"-"+xm+"-01";
            }
            List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
    		Map<String,Object> skl =  reportPayWayService.selectBusinessStatisticsMonth(searchDate);
    		Map<String,Object> head = new HashMap<String, Object>();
    		head.put("name","统计周期");
    		head.put("by",searchDate + "至"+xDate);
    		head.put("sy",sDate + "至"+searchDate);
    		head.put("snby",snDate + "至" + snxDate);
    		head.put("sbypercent","上月本年增长率");
    		head.put("snbypercent","上月去年同年增减率");
    		ret.add(head);
    		
    		Map<String,Object> head2 = new HashMap<String, Object>();
    		head2.put("name","统计明细");
    		head2.put("by","本月累计");
    		head2.put("sy","上月累计");
    		head2.put("sbypercent","(本月-上月)/上月");
    		head2.put("snby","去年同月");
    		head2.put("snbypercent","上月/去年同月赠减率");
    		ret.add(head2);
    		
    		
    		Map<String,Object> yysr = new HashMap<String, Object>();
    		yysr.put("name","营业总收入");
    		yysr.put("by",skl.get("byys"));
    		yysr.put("sy",skl.get("syys"));
    		yysr.put("snby",skl.get("sntyys"));
    		yysr.put("sbypercent",skl.get("sbypercent"));
    		yysr.put("snbypercent",skl.get("snbypercent"));
    		ret.add(yysr);
    		
    		Map<String,Object> yyzrs = new HashMap<String, Object>();
    		yyzrs.put("name","消费总人数");
    		yyzrs.put("by",skl.get("byp"));
    		yyzrs.put("sy",skl.get("syp"));
    		yyzrs.put("snby",skl.get("sntyp"));
    		yyzrs.put("sbypercent",skl.get("sbyrpercent"));
    		yyzrs.put("snbypercent",skl.get("snbyrpercent"));
    		ret.add(yyzrs);
    		
    		Map<String,Object> rjxf = new HashMap<String, Object>();
    		rjxf.put("name","人均消费");
    		rjxf.put("by",skl.get("byrjxf"));
    		rjxf.put("sy",skl.get("syrjxf"));
    		rjxf.put("snby",skl.get("snbyrjxf"));
    		double rjxfbzypercent = 0.0;
    		double rjxfbznpercent = 0.0;
    		if(Double.valueOf(skl.get("byrjxf").toString()) > 0 && Double.valueOf(skl.get("syrjxf").toString()) > 0){
    			double byrjxf = Double.valueOf(skl.get("byrjxf").toString());
    			double syrjxf = Double.valueOf(skl.get("syrjxf").toString());
    			rjxfbzypercent = MathExtend.divide(MathExtend.subtract(byrjxf, syrjxf),syrjxf,4)*100;
    		}
    		if(Double.valueOf(skl.get("byrjxf").toString()) > 0 && Double.valueOf(skl.get("snbyrjxf").toString()) > 0){
    			double byrjxf = Double.valueOf(skl.get("byrjxf").toString());
    			double snbyrjxf = Double.valueOf(skl.get("snbyrjxf").toString());
    			rjxfbznpercent = MathExtend.divide(MathExtend.subtract(byrjxf, snbyrjxf),snbyrjxf,4)*100;
    		}
    		rjxf.put("sbypercent",rjxfbzypercent);
    		rjxf.put("snbypercent",rjxfbznpercent);
    		ret.add(rjxf);
    		
    		Map<String,Object> rijxf = new HashMap<String, Object>();
    		rijxf.put("name","日均消费");
    		rijxf.put("by",skl.get("byrj"));
    		rijxf.put("sy",skl.get("sbyrj"));
    		rijxf.put("snby",skl.get("snbyrj"));
    		double rijxfbzypercent = 0.0;
    		double rijxfbznpercent = 0.0;
    		if(Double.valueOf(skl.get("byrj").toString()) > 0 && Double.valueOf(skl.get("sbyrj").toString()) > 0){
    			double byrj = Double.valueOf(skl.get("byrjxf").toString());
    			double sbyrj = Double.valueOf(skl.get("sbyrj").toString());
    			rijxfbzypercent = MathExtend.divide(MathExtend.subtract(byrj, sbyrj),sbyrj,4)*100;
    		}
    		if(Double.valueOf(skl.get("byrj").toString()) > 0 && Double.valueOf(skl.get("snbyrj").toString()) > 0){
    			double byrj = Double.valueOf(skl.get("byrj").toString());
    			double snbyrj = Double.valueOf(skl.get("snbyrj").toString());
    			rijxfbznpercent = MathExtend.divide(MathExtend.subtract(byrj, snbyrj),snbyrj,4)*100;
    		}
    		rijxf.put("sbypercent",rijxfbzypercent);
    		rijxf.put("snbypercent",rijxfbznpercent);
    		ret.add(rijxf);
    		getRequest().setAttribute("data",ret);
    	} catch (Exception e) {
             e.printStackTrace();
        }
        return "report/businessStatistics/businessStatistics_month_Xls";
    }
    
    @RequestMapping("/dataSearch_week_export")
    public Object dataSearch_week_export(String sStartDate,String sEndDate,String bStartDate,String bEndDate){
    	try {
    		
            List<Map<String,Object>> ret = new ArrayList<Map<String,Object>>();
    		Map<String,Object> skl =  reportPayWayService.selectBusinessStatisticsWeek(sStartDate,sEndDate,bStartDate,bEndDate);
    		Map<String,Object> head = new HashMap<String, Object>();
    		head.put("name","统计周期");
    		head.put("by",bStartDate + "至"+bEndDate);
    		head.put("sy",sStartDate + "至"+sEndDate);
    		head.put("snby","增减率");
    		ret.add(head);
    		
    		Map<String,Object> head2 = new HashMap<String, Object>();
    		head2.put("name","统计明细");
    		head2.put("by","本周累计");
    		head2.put("sy","上周累计");
    		head2.put("snby","(本周-上周)/上周");
    		ret.add(head2);
    		
    		
    		Map<String,Object> yysr = new HashMap<String, Object>();
    		yysr.put("name","营业总收入");
    		yysr.put("by",skl.get("byz"));
    		yysr.put("sy",skl.get("syz"));
    		yysr.put("snby",skl.get("yypercent"));
    		ret.add(yysr);
    		
    		Map<String,Object> yyzrs = new HashMap<String, Object>();
    		yyzrs.put("name","消费总人数");
    		yyzrs.put("by",skl.get("byp"));
    		yyzrs.put("sy",skl.get("syp"));
    		yyzrs.put("snby",skl.get("rrpercent"));
    		ret.add(yyzrs);
    		
    		Map<String,Object> rjxf = new HashMap<String, Object>();
    		rjxf.put("name","人均消费");
    		rjxf.put("by",skl.get("bwjxf"));
    		rjxf.put("sy",skl.get("swjxf"));
    		double rjxfbzypercent = 0.0;
    		if(Double.valueOf(skl.get("bwjxf").toString()) > 0 && Double.valueOf(skl.get("swjxf").toString()) > 0){
    			double byrjxf = Double.valueOf(skl.get("bwjxf").toString());
    			double syrjxf = Double.valueOf(skl.get("swjxf").toString());
    			rjxfbzypercent = MathExtend.multiply(MathExtend.divide(MathExtend.subtract(byrjxf, syrjxf),syrjxf,4),100);
    		}
    		rjxf.put("snby",rjxfbzypercent);
    		ret.add(rjxf);
    		
    		Map<String,Object> rijxf = new HashMap<String, Object>();
    		rijxf.put("name","日均消费");
    		rijxf.put("by",skl.get("byrj"));
    		rijxf.put("sy",skl.get("sbyrj"));
    		double rijxfbzypercent = 0.0;
    		if(Double.valueOf(skl.get("byrj").toString()) > 0 && Double.valueOf(skl.get("sbyrj").toString()) > 0){
    			double byrj = Double.valueOf(skl.get("byrj").toString());
    			double sbyrj = Double.valueOf(skl.get("sbyrj").toString());
    			rijxfbzypercent = MathExtend.multiply(MathExtend.divide(MathExtend.subtract(byrj, sbyrj),sbyrj,4),100);
    		}
    		rijxf.put("snby",rijxfbzypercent);
    		ret.add(rijxf);
    		getRequest().setAttribute("data",ret);
    	} catch (Exception e) {
             e.printStackTrace();
        }
        return "report/businessStatistics/businessStatistics_week_Xls";
    }
}
