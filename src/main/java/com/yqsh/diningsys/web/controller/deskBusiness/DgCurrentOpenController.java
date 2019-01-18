package com.yqsh.diningsys.web.controller.deskBusiness;

import com.google.gson.Gson;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.enums.SysVariableDefine;
import com.yqsh.diningsys.web.model.DgCommonsVariable;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.TbBis;
import com.yqsh.diningsys.web.model.archive.TbOrg;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.archive.TbBisService;
import com.yqsh.diningsys.web.service.archive.TbOrgService;
import com.yqsh.diningsys.web.service.base.DgCommonsVariableService;
import com.yqsh.diningsys.web.service.deskBusiness.DgCurrentOpenWaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台营业-当前营业情况
 *
 * @author zhshuo create on 2016-11-10 16:57
 */
@RequestMapping("/deskBusiness/currentOpen")
@Controller
public class DgCurrentOpenController extends BaseController {

    @Autowired
    private DgCurrentOpenWaterService dgCurrentOpenWaterService;

    @Autowired
    private DgConsumptionAreaService dgConsumptionAreaService;

    @Autowired
    private TbBisService tbBisService;

    @Autowired
    private TbOrgService tbOrgService;

    @Autowired
    private DgCommonsVariableService dgCommonsVariableService;

    @RequestMapping("/index")
    public String index(Model model){
        try {
            //自定义的刷新时间
            DgCommonsVariable dgCommonsVariable = dgCommonsVariableService.selectByCode(new DgCommonsVariable(SysVariableDefine.CURRENT_OPEN_FLUSH, null));
            model.addAttribute("fushTime",dgCommonsVariable.getCvValue());
            //全部区域
            List<DgConsumptionArea> dgConsumptionAreas = dgConsumptionAreaService.selectAllArea();
            model.addAttribute("dgConsumptionAreas",dgConsumptionAreas);
            //全部市别
            List<TbBis> tbBises = tbBisService.selectAllBis();
            model.addAttribute("tbBises",tbBises);
            //客位状态1空闲、2占用、3清扫、4预定
            //组织机构 查询出启用本店标志的组织
            List<TbOrg> tbOrgs = tbOrgService.selectAllTbOrg();
            model.addAttribute("tbOrgs",tbOrgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "deskBusiness/currentOpen/current_open_index";
    }

    @RequestMapping("/selectCurrentSeatInfo")
    @ResponseBody
    public Map selectCurrentSeatInfo(Integer org,Integer place,Integer bis,Integer seatState){
        try {
            Map resMap = new HashMap();
            //当前条件下客位信息
            resMap.put("currentSeatInfo",dgCurrentOpenWaterService.selectCurrentSeatInfo(org,place,bis,seatState));
            //获取当前条件下所有客座能容纳的所有人数以及上座人数
            Map map = dgCurrentOpenWaterService.selectAllSeatPeople(place, bis, seatState);
            Double allNum = 0.0,acNum = 0.0,attendance = 0.0;
            if(map != null && map.containsKey("allNum")){
                allNum = Double.parseDouble(map.get("allNum").toString());
            }
            if(map != null && map.containsKey("acNum")){
                acNum = Double.parseDouble(map.get("acNum").toString());
            }
            if(acNum != 0.00){
                attendance = MathExtend.divide(allNum,acNum,2);
            }
            resMap.put("attendance",attendance);
            //获取当前条件下的客位统计信息
            resMap.put("currentSeatCount",dgCurrentOpenWaterService.selectCurrentSeatCount(org,place,bis));
            //获取当前条件下的结算方式信息
            resMap.put("currentPayInfo",dgCurrentOpenWaterService.selectCurrentPayInfo(org,place,bis));
            //获取当前的客位账单信息
            resMap.put("classStatement",dgCurrentOpenWaterService.selectCurrentBisClassInfo(org,bis));
            //获取今日预定总数、今日执行总数，条件只有客座区域
            resMap.put("todayReservationInfo",dgCurrentOpenWaterService.selecttodayReservationInfo(place));
            //获取所选市别预定，所选市别执行，条件包含客座区域以及市别
            resMap.put("todayPBReInfo",dgCurrentOpenWaterService.selectPlaceBisRes(place,bis));
            return resMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/getYyInfoBySeatId",method = RequestMethod.POST)
    @ResponseBody
    public Object getYyInfoBySeatId(Integer seatId){
        try {
            return dgCurrentOpenWaterService.selectOpenWaterBySeatId(seatId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/selectOpenWaterInfo")
    @ResponseBody
    public Object selectOpenWaterInfo(String openWaterNum){
        try {
            Map<String,Object> res = new HashMap<>();
            DgOpenWater dgOpenWater = dgCurrentOpenWaterService.selectOpenWaterByOwNum(openWaterNum);
            List<Map> maps = dgCurrentOpenWaterService.selectOpenWaterItemFileByOwId(dgOpenWater.getId());
            if(!StringUtils.isEmpty(dgOpenWater.getCrId())){
                String memberList = OkHttpUtils.getMemberList("{'vId':'" + dgOpenWater.getCrId() + "'}");
                if(memberList != null){
                    List crInfo = (List)new Gson().fromJson(memberList, Map.class).get("body");
                    dgOpenWater.setCrName(((Map)crInfo.get(0)).get("custName").toString());
                }
            }

            Double total = 0.00;
            if(maps.size() > 0){
                for(Map map :maps){
                    total += Double.parseDouble(map.get("subtotal").toString());
                }
            }
            res.put("openWaterInfo",dgOpenWater);
            res.put("openWaterItemFiles",maps);
            res.put("total",total);
            return res;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/nonLastClosedCheck")
    @ResponseBody
    public void checkSeatHasClosedCheck(HttpServletResponse response){
        try {
            Writer writer = response.getWriter();
            writer.write("layer.msg('该客位暂无已结账单！')");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/toSeatLastClosedCheck/{seatId}")
    public String toSeatLastClosedCheck(@PathVariable Integer seatId,Model modle){
        DgReceptionClearingWater dgReceptionClearingWater = dgCurrentOpenWaterService.selectSeatHasClosedCheck(seatId);
        modle.addAttribute("dgReceptionClearingWater",dgReceptionClearingWater);
        modle.addAttribute("seatId",seatId);
        if (dgReceptionClearingWater != null) {
            return "deskBusiness/currentOpen/last_closed_check";
        }
        return "redirect:/DININGSYS/deskBusiness/currentOpen/nonLastClosedCheck";
    }

    @RequestMapping("/getSeatOpenWater")
    @ResponseBody
    public List<DgOpenWater> getSeatOpenWater(Integer cwId){
       return dgCurrentOpenWaterService.getSeatOpenWater(cwId);
    }

    @RequestMapping("/getOpenWaterConDeInfo/{owId}")
    @ResponseBody
    public List<DgOwConsumerDetails> getOpenWaterConDeInfo(@PathVariable Integer owId){
        return dgCurrentOpenWaterService.getOpenWaterConDeInfo(owId);
    }

    @RequestMapping("/getOpenWaterOtherInfo/{cwId}")
    @ResponseBody
    public Map getOpenWaterOtherInfo(@PathVariable Integer cwId){
        return dgCurrentOpenWaterService.getOpenWaterOtherInfo(cwId);
    }

    @RequestMapping("/toClearingNotes/{cwId}")
    public String toClearingNotes(@PathVariable Integer cwId,Model model){
        model.addAttribute("notes",dgCurrentOpenWaterService.getNotesAndLabel(cwId).get("clearingNotes"));
        model.addAttribute("label",dgCurrentOpenWaterService.getNotesAndLabel(cwId).get("statementLabel"));
        model.addAttribute("cwId",cwId);
        return "deskBusiness/currentOpen/clearing_notes";
    }

    @RequestMapping("/editClearingNotes")
    @ResponseBody
    public ResultInfo editClearingNotes(String clearingNotes,Integer statementLabel,Integer cwId){
        try {
            dgCurrentOpenWaterService.editClearingNotes(clearingNotes,statementLabel,cwId);
            return returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail();
    }

}
