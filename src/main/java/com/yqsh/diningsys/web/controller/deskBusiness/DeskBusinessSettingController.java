package com.yqsh.diningsys.web.controller.deskBusiness;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yqsh.diningsys.core.feature.gson.NullStringToEmptyAdapterFactory;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingLeftmenuService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrren on 2016/11/14.
 */
@Controller
@RequestMapping("/deskbusinesssetting")
public class DeskBusinessSettingController extends BaseController {

    private static final  Gson gson  = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();

    @Resource
    private DeskBusinessSettingService deskBusinessSettingService;

    @InitBinder
    public void initDBS(){
        //初始化一条设置数据
        DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
        if (deskBusinessSetting == null) {
            deskBusinessSettingService.initDeskBusinessSetting();
        }
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "deskBusiness/deskbusinesssetting/desk_business_index";
    }

    /**
     * 业务权限具体页面
     * @param type
     * @return
     */
    @RequestMapping("/busPage/{type}")
    public String generalBus(@PathVariable String type, Model model) throws Exception{
        if(type == null || "".equals(type)){
            return "permissions/sys_business/dbs_seatserv";
        }else{
            DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
            if("seatserv".equals(type)){
                model.addAttribute("dbsSeetServDTO", gson.fromJson(deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class));
                return "deskBusiness/deskbusinesssetting/dbs_seatserv";
            }else if("billserv".equals(type)){
                model.addAttribute("dbsBillServDTO", gson.fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class));
                return "deskBusiness/deskbusinesssetting/dbs_billserv";
            }else if("printersetting".equals(type)){
                model.addAttribute("dbsPrinterSettingDTO", gson.fromJson(deskBusinessSetting.getPrinterSetting(), DBSPrinterSettingDTO.class));
                return "deskBusiness/deskbusinesssetting/dbs_printerSetting";
            }else if("serialrul".equals(type)){
                model.addAttribute("dbsSerialRulDTO", gson.fromJson(deskBusinessSetting.getSerialRul(), DBSSerialRulDTO.class));
                return "deskBusiness/deskbusinesssetting/dbs_serialRul";
            }else if("loungesetting".equals(type)){
                model.addAttribute("dbsLoungeSettingDTO", gson.fromJson(deskBusinessSetting.getLoungeSetting(), DBSLoungeSettingDTO.class));
                return "deskBusiness/deskbusinesssetting/dbs_loungeSetting";
            }
        }
        return "404";
    }

    //客座设置
    @RequestMapping(value="/editSeatservData", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSeatservData(Model model, DBSSeetServDTO dbsSeetServDTO){
        ResultInfo result = new ResultInfo();
        DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
        String temp = gson.toJson(dbsSeetServDTO);
        deskBusinessSetting.setSeatServ(temp);
        deskBusinessSettingService.updateDeskBusinessSetting(deskBusinessSetting);
        result.setSuccess(true);
        return result;
    }

    //账单设置
    @RequestMapping(value="/editBillservData", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editBillservData(Model model, DBSBillServDTO dbsBillServDTO){
        ResultInfo result = new ResultInfo();
        DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
        deskBusinessSetting.setBillServ(gson.toJson(dbsBillServDTO));
        deskBusinessSettingService.updateDeskBusinessSetting(deskBusinessSetting);
        result.setSuccess(true);
        return result;
    }

    //打印设置
    @RequestMapping(value="/editPringterData", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editPringterData(Model model, DBSPrinterSettingDTO dbsPrinterSettingDTO){
        ResultInfo result = new ResultInfo();
        DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
        deskBusinessSetting.setPrinterSetting(gson.toJson(dbsPrinterSettingDTO));
        deskBusinessSettingService.updateDeskBusinessSetting(deskBusinessSetting);
        result.setSuccess(true);
        return result;
    }

    //序列号生成规则
    @RequestMapping(value="/editSerialRulData", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editSerialRulData(Model model, DBSSerialRulDTO dbsSerialRulDTO){
        ResultInfo result = new ResultInfo();
        DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
        deskBusinessSetting.setSerialRul(gson.toJson(dbsSerialRulDTO));
        deskBusinessSettingService.updateDeskBusinessSetting(deskBusinessSetting);
        result.setSuccess(true);
        return result;
    }

    //雅座设置
    @RequestMapping(value="/editLoungeSettingData", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo editLoungeSettingData(Model model, DBSLoungeSettingDTO dbsLoungeSettingDTO){
        ResultInfo result = new ResultInfo();
        DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
        deskBusinessSetting.setLoungeSetting(gson.toJson(dbsLoungeSettingDTO));
        deskBusinessSettingService.updateDeskBusinessSetting(deskBusinessSetting);
        result.setSuccess(true);
        return result;
    }

    //雅座设置
    @RequestMapping(value="/test", method = RequestMethod.POST)
    @ResponseBody
    public String test(){
        //return deskBusinessSettingService.createFlowNumber("222222","1111","JB");
        return "";
    }
}
