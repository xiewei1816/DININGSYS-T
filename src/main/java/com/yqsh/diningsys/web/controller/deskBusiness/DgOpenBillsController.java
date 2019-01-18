package com.yqsh.diningsys.web.controller.deskBusiness;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.enums.SysVariableDefine;
import com.yqsh.diningsys.web.model.DgCommonsVariable;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgConsumptionAreaService;
import com.yqsh.diningsys.web.service.base.CommonService;
import com.yqsh.diningsys.web.service.base.DgCommonsVariableService;
import com.yqsh.diningsys.web.service.deskBusiness.DgCurrentOpenWaterService;
import com.yqsh.diningsys.web.service.deskBusiness.DgOpenWaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-30 下午3:04
 */
@RequestMapping("/deskBusiness/openBills")
@Controller
public class DgOpenBillsController  extends BaseController{

    @Autowired
    private DgCommonsVariableService dgCommonsVariableService;

    @Autowired
    private DgOpenWaterService dgOpenWaterService;

    @Autowired
    private DgCurrentOpenWaterService dgCurrentOpenWaterService;

    @Autowired
    private DgConsumptionAreaService dgConsumptionAreaService;
    
    @Autowired
	private DgConsumerSeatService consumerSeatService;
    
	@Autowired
	private DgConsumptionAreaService consumptionAreaService;

    @RequestMapping("/index")
    public String index(Model model){
        DgCommonsVariable dgCommonsVariable = dgCommonsVariableService.selectByCode(new DgCommonsVariable(SysVariableDefine.CURRENT_OPEN_WATER_FLUSH, null));
        model.addAttribute("flushTime",dgCommonsVariable.getCvValue());

        //客位
        model.addAttribute("seatList",consumerSeatService.getAllList(null));
        //消费区域
        model.addAttribute("areaList",consumptionAreaService.getAllList(null));
        List<DgConsumptionArea> dgConsumptionAreas = dgConsumptionAreaService.selectAllArea();
        model.addAttribute("dgConsumptionAreas",dgConsumptionAreas);
        return "deskBusiness/openBills/open_bills_index";
    }

    @SuppressWarnings("unchecked")
	@RequestMapping("/getCurrentOpenWater")
    @ResponseBody
    public List<DgOpenWater> getCurrentOpenWater(Integer place,String seatName,String owNum){
        Map param = new HashMap();
        param.put("place",place);
        param.put("seatName",seatName);
        param.put("owNum",owNum);
        return dgOpenWaterService.selectCurrentOpenWater(param);
    }

    @RequestMapping("/selectOpenWaterInfo/{owId}")
    @ResponseBody
    public Map selectOpenWaterInfo(@PathVariable Integer owId){
        Map res = new HashMap();
        res.put("owInfo",dgCurrentOpenWaterService.selectOpenWaterInfoById(owId));
        res.put("itemFileInfo",dgCurrentOpenWaterService.getOpenWaterConDeInfo(owId));
        return res;
    }

}
