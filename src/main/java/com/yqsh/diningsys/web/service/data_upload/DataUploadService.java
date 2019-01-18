package com.yqsh.diningsys.web.service.data_upload;

import com.yqsh.diningsys.web.model.SysUser;

import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.*;
import com.yqsh.diningsys.web.model.deskBusiness.*;

import java.util.List;
import java.util.Map;



import org.apache.ibatis.annotations.Param;

import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgFlavor;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgProMethods;
import com.yqsh.diningsys.web.model.archive.DgProMethodsType;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.archive.DgSettlementWayType;
import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;
import com.yqsh.diningsys.web.model.deskBusiness.DgGateItemAllowCustom;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCurrentPrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCustomMoney;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemForWeek;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemFromCook;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPriceLadder;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPricePriority;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
import com.yqsh.diningsys.web.model.deskBusiness.DgPromotionItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgRecommendItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpecialItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;

/**
 * Created on 2017-04-21 11:131
 *
 * @author zhshuo
 */
public interface DataUploadService {

    List<SysUser> selectSelfYGGL();

    /**
     * 以下品项管理数据
     *
     * @return
     */
    List<DgItemTypeDiscount> selectSelfPXGLPXDZXLMR();

    List<DgItemDiscountProgramme> selectSelfPXGLPXDZFAZB();

    List<DgItemDiscountProgrammeS> selectSelfPXGLPXDZFAFB();

    List<DgWeekDiscount> selectSelfPXGLPXDZFAXQB();

    List<DgItemMemberDiscount> selectSelfPXGLPXDZHYFAZB();

    List<DgItemMemberDiscountS> selectSelfPXGLPXDZHYFAFB();

    List<DgForMealTimePrice> selectSelfPXGLPXDZYCDJASBZB();

    List<DgForMealTimePriceS> selectSelfPXGLPXDZYCDJASBFB();

    List<DgPlacePrice> selectSelfPXGLPXDZYCDJAXFQYZB();

    List<DgPlacePriceS> selectSelfPXGLPXDZYCDJAXFQYFB();

    List<DgItemForWeek> selectSelfPXGLPXDZYCDJAXQ();

    List<DgPromotionItem> selectSelfPXGLPXDZCXPX();

    List<DgItemCurrentPrice> selectSelfPXGLPXDZSJPX();

    List<DgItemPriceLadder> selectSelfPXGLPXDZJTJG();

    List<DgItemPricePriority> selectSelfPXGLPXDZJGYXJ();

    List<DgImportantActivityDiscount> selectSelfPXGLPXDZZYHDZB();

    List<DgImportantAcitivityDiscountS> selectSelfPXGLPXDZZYHDFB();

    /**
     * 营业管理其它数据
     */
    List<TbFydj> selectSelfFYDJ();

    List<DgItemShowRank> selectSelfXFPXXS();

    List<ServiceClass> selectSelfFWYFWKW();

    List<DgAreaLimitItem> selectSelfXFQYXSPX();

    List<DgAreaManager> selectSelfXFQUGL();

    List<DgSeatManager> selectSelfKWGL();

    List<DgSeatChargingScheme> selectSelfBFFAZB();

    List<DgSeatChargingSchemeS> selectSelfBFFAFB();

    List<DeskBusinessSetting> selectSelfQTYYSZ();

    /**
     * *******************************************BY XW S**********************************************
     */

    List<DgItemOutofStock> selectSelfPXGQ();

    List<DgItemSaleLimit> selectSelfPXXLZB();

    List<DgItemSaleLimitS> selectSelfPXXLFB();

    List<DgItemDisable> selectSelfPXTY();

    List<DgRecommendItem> selectSelfTJCP();

    List<DgNewestItem> selectSelfZXCP();

    List<DgItemProDep> selectSelfPXCPBMZB();

    List<DgItemProDepS> selectSelfPXCPBMFB();

    List<DgSpecialItem> selectSelfTSPX();

    List<DgItemFromCook> selectSelfPXCPCS();

    List<DgGateItemAllowCustom> selectSelfZDYJEPXXL();

    List<DgItemCustomMoney> selectSelfZDYJEPX();

    List<BgItemCustomMoneyName> selectSelfZDYJEMC();

    List<DgItemGiftPlan> selectSelfPXCXFAZB();

    List<DgItemGiftPlanS> selectSelfPXCXFAFB();

    /** ********************************************BY XW D********************************************** */

    List<DgConsumptionArea> selectSelfXFQU(String selfShopKey);  
    List<DgConsumerSeat> selectSelfKW(String selfShopKey);   
    List<DgPos> selectSelfPOS(String selfShopKey);  
    List<DgSettlementWayType> selectSelfJSFSLX(String selfShopKey); 
    List<DgSettlementWay> selectSelfJSFS(String selfShopKey); 
    List<DgProMethodsType> selectSelfZZFSLX(String selfShopKey); 
    List<DgProMethods> selectSelfZZFS(String selfShopKey);  
    List<DgFlavor> selectSelfKWYQ(String selfShopKey);
    
    /**
     * 上菜状态
     *
     * @return
     */
    List<DgServing> selectSelfSCZT();

    /**
     * 赠单原因
     *
     * @return
     */
    Map<String, Object> selectSelfZDYY();

    /**
     * 部门管理
     *
     * @return
     */
    List<TbDep> selectSelfBMGL();

    /**
     * 营业市别
     *
     * @return
     */
    List<TbBis> selectSelfYYSB();

    /**
     * 退菜原因
     *
     * @return
     */
    Map<String, Object> selectSelfTCYY();

    /**
     * 整单备注
     *
     * @return
     */
    List<TbZdbz> selectSelfZDBZ();

    /**
     * 公共代码
     *
     * @return
     */
    List<DgPublicCode0> selectSelfGGDM();


    /******************************营业流水数据上传*************************************/




}
