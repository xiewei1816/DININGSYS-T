package com.yqsh.diningsys.web.controller.franchise;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.ResultInfo;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.*;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.service.data_upload.DataUploadService;
import com.yqsh.diningsys.web.util.DATA_UPLOAD_UTIL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-21 10:12
 * 加盟店数据上传
 * @author zhshuo
 */
@RequestMapping("/system/dataUpload")
@Controller
public class DataUploadController extends BaseController{

    @Autowired
    private DataUploadService dataUploadService;

    @RequestMapping("/index")
    public String index(){
        return "system/franchise/index";
    }

    @ResponseBody
    @RequestMapping("/dataUpload")
    public ResultInfo dataUpload(String dataModel){
        try {
            Map result = null;
            switch (dataModel){
                case "DATA_YGGL":
                    List<SysUser> sysUsers = dataUploadService.selectSelfYGGL();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_YGGL(JSON.toJSONString(sysUsers));
                    return returnPackage("员工管理",resultParse(result));
                case "DATA_PXGL":
                	List<DgItemTypeDiscount> dgItemTypeDiscounts = dataUploadService.selectSelfPXGLPXDZXLMR();
                	List<DgItemDiscountProgramme> dgItemDiscountProgrammes = dataUploadService.selectSelfPXGLPXDZFAZB();
                	List<DgItemDiscountProgrammeS> dgItemDiscountProgrammeSs = dataUploadService.selectSelfPXGLPXDZFAFB();
                	List<DgWeekDiscount> dgWeekDiscounts = dataUploadService.selectSelfPXGLPXDZFAXQB();
                	List<DgItemMemberDiscount> dgItemMemberDiscounts = dataUploadService.selectSelfPXGLPXDZHYFAZB();
                	List<DgItemMemberDiscountS> dgItemMemberDiscountSs = dataUploadService.selectSelfPXGLPXDZHYFAFB();
                	List<DgForMealTimePrice> dgForMealTimePrices = dataUploadService.selectSelfPXGLPXDZYCDJASBZB();
                	List<DgForMealTimePriceS> dgForMealTimePriceSs = dataUploadService.selectSelfPXGLPXDZYCDJASBFB();
                	List<DgPlacePrice> dgPlacePrices = dataUploadService.selectSelfPXGLPXDZYCDJAXFQYZB();
                	List<DgPlacePriceS> dgPlacePriceSs = dataUploadService.selectSelfPXGLPXDZYCDJAXFQYFB();
                	List<DgItemForWeek> dgItemForWeeks = dataUploadService.selectSelfPXGLPXDZYCDJAXQ();
                	List<DgPromotionItem> dgPromotionItems = dataUploadService.selectSelfPXGLPXDZCXPX();
                	List<DgItemCurrentPrice> dgItemCurrentPrices = dataUploadService.selectSelfPXGLPXDZSJPX();
                	List<DgItemPriceLadder> dgItemPriceLadders = dataUploadService.selectSelfPXGLPXDZJTJG();
                	List<DgItemPricePriority> dgItemPricePrioritys = dataUploadService.selectSelfPXGLPXDZJGYXJ();
                	List<DgImportantActivityDiscount> dgImportantActivityDiscounts = dataUploadService.selectSelfPXGLPXDZZYHDZB();
                	List<DgImportantAcitivityDiscountS> dgImportantAcitivityDiscountSs = dataUploadService.selectSelfPXGLPXDZZYHDFB();
            	    Map<String,Object> map = new HashMap<>();
                    map.put("PXGLPXDZXLMR",dgItemTypeDiscounts);
                    map.put("PXGLPXDZFAFB",dgItemDiscountProgrammeSs);
                    map.put("PXGLPXDZFAZB",dgItemDiscountProgrammes);
                    map.put("PXGLPXDZFAXQB",dgWeekDiscounts);
                    map.put("PXGLPXDZHYFAFB",dgItemMemberDiscountSs);
                    map.put("PXDZHYFAZB",dgItemMemberDiscounts);
                    map.put("PXGLPXDZYCDJASBFB",dgForMealTimePriceSs);
                    map.put("PXGLPXDZYCDJASBZB",dgForMealTimePrices);
                    map.put("PXDZYCDJAXFQYFB",dgPlacePriceSs);
                    map.put("PXDZYCDJAXFQYZB",dgPlacePrices);
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXGL_ONE(JSON.toJSONString(map));
                    Integer intRes = resultParse(result);
                    if(intRes != -2){
                    	Map<String,Object> map2 = new HashMap<>();
                  	    map2.put("PXGLPXDZCXPX",dgPromotionItems);
                  	    map2.put("PXGLPXDZSJPX",dgItemCurrentPrices);
                  	    map2.put("PXGLPXDZJTJG",dgItemPriceLadders);
                  	    map2.put("PXGLPXDZJGYXJ",dgItemPricePrioritys);
                  	    map2.put("PXGLPXDZYCDJAXQ",dgItemForWeeks);
                  	    map2.put("PXGLPXDZZYHDFB",dgImportantAcitivityDiscountSs);
                  	    map2.put("PXGLPXDZZYHDZB",dgImportantActivityDiscounts);
                        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXGL_TWO(JSON.toJSONString(map2));
                        intRes += resultParse(result);
                             	
                    }
                    //品项管理3
                    intRes += GET_PXGL_COUNT();
                    return returnPackage("品项管理",intRes);
                case "DATA_SCZT":
                    List<DgServing> selfSCZT = dataUploadService.selectSelfSCZT();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_SCZT(JSON.toJSONString(selfSCZT));
                    return returnPackage("上菜状态",resultParse(result));
                case "DATA_ZDYY":
                    Map<String, Object> zdyy = dataUploadService.selectSelfZDYY();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_ZDYY(JSON.toJSONString(zdyy));
                    return returnPackage("赠单原因",resultParse(result));
                case "DATA_BMGL":
                    List<TbDep> tbDeps = dataUploadService.selectSelfBMGL();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_BMGL(JSON.toJSONString(tbDeps));
                    return returnPackage("部门管理",resultParse(result));
                case "DATA_YYSB":
                    List<TbBis> tbBis = dataUploadService.selectSelfYYSB();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_YYSB(JSON.toJSONString(tbBis));
                    return returnPackage("营业市别",resultParse(result));
                case "DATA_TCYY":
                    Map<String,Object> tcyy = dataUploadService.selectSelfTCYY();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_TCYY(JSON.toJSONString(tcyy));
                    return returnPackage("退菜原因",resultParse(result));
                case "DATA_ZDBZ":
                    List<TbZdbz> tbZdbzs = dataUploadService.selectSelfZDBZ();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_ZDBZ(JSON.toJSONString(tbZdbzs));
                    return returnPackage("整单备注",resultParse(result));
                case "DATA_GGDM":
                    List<DgPublicCode0> publicCode0s = dataUploadService.selectSelfGGDM();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_GGDM(JSON.toJSONString(publicCode0s));
                    return returnPackage("公共代码",resultParse(result));
                case "DATA_FYDJ":
                    List<TbFydj> tbFydjs = dataUploadService.selectSelfFYDJ();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_FYDJ(JSON.toJSONString(tbFydjs));
                    return returnPackage("费用登记管理",resultParse(result));
                case "DATA_XFPXXSSZ":
                    List<DgItemShowRank> dgItemShowRanks = dataUploadService.selectSelfXFPXXS();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_XFPXXS(JSON.toJSONString(dgItemShowRanks));
                    return returnPackage("消费品项显示管理",resultParse(result));
                case "DATA_FWYFWKW":
                    List<ServiceClass> serviceClasss = dataUploadService.selectSelfFWYFWKW();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_FWYFWKW(JSON.toJSONString(serviceClasss));
                    return returnPackage("服务员服务客位管理",resultParse(result));
                case "DATA_XFQYYKWGL":
                    List<DgAreaLimitItem> dgAreaLimitItems = dataUploadService.selectSelfXFQYXSPX();
                    List<DgAreaManager> dgAreaManagers = dataUploadService.selectSelfXFQUGL();
                    List<DgSeatManager> dgSeatManagers = dataUploadService.selectSelfKWGL();
                    List<DgSeatChargingScheme> dgSeatChargingScheme = dataUploadService.selectSelfBFFAZB();
                    List<DgSeatChargingSchemeS> dgSeatChargingSchemeS = dataUploadService.selectSelfBFFAFB();
                    Map<String,Object> org = new HashMap<>();
                    org.put("XFQYXSPX",dgAreaLimitItems);
                    org.put("XFQUGL",dgAreaManagers);
                    org.put("KWGL",dgSeatManagers);
                    org.put("BFFAZB",dgSeatChargingScheme);
                    org.put("BFFAFB",dgSeatChargingSchemeS);
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_XFQYHKWGL(JSON.toJSONString(org));
                    return returnPackage("服务员服务客位管理",resultParse(result));
                case "DATA_QTYYSZ":
                    List<DeskBusinessSetting> deskBusinessSettings = dataUploadService.selectSelfQTYYSZ();
                    result = DATA_UPLOAD_UTIL.DATA_UPLOAD_QTYYSZ(JSON.toJSONString(deskBusinessSettings));
                    return returnPackage("前台营业设置管理",resultParse(result));
                default:
                return returnFail("未知的上传数据类型");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnFail("服务器忙，请稍后");
    }

    private Integer resultParse(Map result){
        if(result == null || result.isEmpty()){
            return -2;
        }
        if(result.get("status").toString().equals("200"))
            return Integer.parseInt(result.get("effectRow").toString());
        return -2;
    }

    private ResultInfo returnPackage(String message,Integer judgeFlag){
        if(judgeFlag != -2)
            return returnSuccess(message+"数据上传成功，总计："+judgeFlag+"条数据！");
        return returnSuccess(message+"数据上传失败!");
    }

    private Integer GET_PXGL_COUNT(){
    	 /** ********************************************BY XW S********************************************** */
    	Integer effectRow = 0;
    	Map result = null;
        List<DgItemOutofStock> dgItemOutofStock = dataUploadService.selectSelfPXGQ();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXGQ(JSON.toJSONString(dgItemOutofStock));
        effectRow += resultParse(result);
        List<DgItemSaleLimit> dgItemSaleLimit = dataUploadService.selectSelfPXXLZB();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLZB(JSON.toJSONString(dgItemSaleLimit));
        effectRow += resultParse(result);
        List<DgItemSaleLimitS> dgItemSaleLimits = dataUploadService.selectSelfPXXLFB();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLFB(JSON.toJSONString(dgItemSaleLimits));
        effectRow += resultParse(result);
        List<DgItemDisable> dgItemDisable = dataUploadService.selectSelfPXTY();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLZB(JSON.toJSONString(dgItemDisable));
        effectRow += resultParse(result);
        List<DgRecommendItem> dgRecommendItem = dataUploadService.selectSelfTJCP();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLFB(JSON.toJSONString(dgRecommendItem));
        effectRow += resultParse(result);
        List<DgNewestItem> dgNewestItem = dataUploadService.selectSelfZXCP();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXGQ(JSON.toJSONString(dgNewestItem));
        effectRow += resultParse(result);
        List<DgItemProDep> dgItemProDep = dataUploadService.selectSelfPXCPBMZB();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLZB(JSON.toJSONString(dgItemProDep));
        effectRow += resultParse(result);
        List<DgItemProDepS> dgItemProDeps = dataUploadService.selectSelfPXCPBMFB();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLFB(JSON.toJSONString(dgItemProDeps));
        effectRow += resultParse(result);
        List<DgSpecialItem> dgSpecialItem = dataUploadService.selectSelfTSPX();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLZB(JSON.toJSONString(dgSpecialItem));
        effectRow += resultParse(result);
        List<DgItemFromCook> dgItemFromCook = dataUploadService.selectSelfPXCPCS();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLFB(JSON.toJSONString(dgItemFromCook));
        effectRow += resultParse(result);
        List<DgGateItemAllowCustom> dgGateItemAllowCustom = dataUploadService.selectSelfZDYJEPXXL();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXGQ(JSON.toJSONString(dgGateItemAllowCustom));
        effectRow += resultParse(result);
        List<DgItemCustomMoney> dgItemCustomMoney = dataUploadService.selectSelfZDYJEPX();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLZB(JSON.toJSONString(dgItemCustomMoney));
        effectRow += resultParse(result);
        List<BgItemCustomMoneyName> bgItemCustomMoneyName = dataUploadService.selectSelfZDYJEMC();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLFB(JSON.toJSONString(bgItemCustomMoneyName));
        effectRow += resultParse(result);
        List<DgItemGiftPlan> dgItemGiftPlan = dataUploadService.selectSelfPXCXFAZB();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLZB(JSON.toJSONString(dgItemGiftPlan));
        effectRow += resultParse(result);
        List<DgItemGiftPlanS> dgItemGiftPlans = dataUploadService.selectSelfPXCXFAFB();
        result = DATA_UPLOAD_UTIL.DATA_UPLOAD_PXXLFB(JSON.toJSONString(dgItemGiftPlans));
        effectRow += resultParse(result);
        return effectRow;
        /** ********************************************BY XW D********************************************** */
    }
}
