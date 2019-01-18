package com.yqsh.diningsys.web.dao.data_upload;

import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.*;
import com.yqsh.diningsys.web.model.deskBusiness.*;









import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
import java.util.List;


/**
 * Created on 2017-04-21 11:53
 *
 * @author zhshuo
 */
@Repository
public interface DataUploadMapper {

    List<SysUser> selectSelfYGGL(@Param("shopKey") String shopKey);

    /**
     * 以下营业管理中(品项管理)数据
     * @return
     */
    List<DgItemTypeDiscount> selectSelfPXGLPXDZXLMR();
    
    List<DgItemDiscountProgramme> selectSelfPXGLPXDZFAZB(@Param("shopKey") String shopKey);
    
    List<DgItemDiscountProgrammeS> selectSelfPXGLPXDZFAFB();
    
    List<DgWeekDiscount> selectSelfPXGLPXDZFAXQB(@Param("shopKey") String shopKey);
    
    List<DgItemMemberDiscount> selectSelfPXGLPXDZHYFAZB(@Param("shopKey") String shopKey);
    
    List<DgItemMemberDiscountS> selectSelfPXGLPXDZHYFAFB();
    
    List<DgForMealTimePrice> selectSelfPXGLPXDZYCDJASBZB(@Param("shopKey") String shopKey);
    
    List<DgForMealTimePriceS> selectSelfPXGLPXDZYCDJASBFB();
    
    List<DgPlacePrice> selectSelfPXGLPXDZYCDJAXFQYZB(@Param("shopKey") String shopKey);
    
    List<DgPlacePriceS> selectSelfPXGLPXDZYCDJAXFQYFB();
    
    List<DgItemForWeek> selectSelfPXGLPXDZYCDJAXQ(@Param("shopKey") String shopKey);
    
    List<DgPromotionItem> selectSelfPXGLPXDZCXPX(@Param("shopKey") String shopKey);
    
    List<DgItemCurrentPrice> selectSelfPXGLPXDZSJPX(@Param("shopKey") String shopKey);
    
    List<DgItemPriceLadder> selectSelfPXGLPXDZJTJG(@Param("shopKey") String shopKey);
    
    List<DgItemPricePriority> selectSelfPXGLPXDZJGYXJ(@Param("shopKey") String shopKey);
    
    List<DgImportantActivityDiscount> selectSelfPXGLPXDZZYHDZB(@Param("shopKey") String shopKey);
    
    List<DgImportantAcitivityDiscountS> selectSelfPXGLPXDZZYHDFB();

    List<DgServing> selectSelfSCZT(@Param("shopKey") String shopKey);

    List<DgGiftForm> selectSelfZDYY(@Param("shopKey") String selfShopKey);

    List<DgGiftFormReason> selectSelfZDYYLX(@Param("shopKey") String selfShopKey);

    List<TbDep> selectSelfBMGL(@Param("shopKey") String selfShopKey);

    List<TbBis> selectSelfYYSB(@Param("shopKey") String selfShopKey);

    List<TbRfc> selectSelfTCYY(@Param("shopKey") String selfShopKey);

    List<TbRfct> selectSelfTCYYLX(@Param("shopKey") String selfShopKey);

    List<TbZdbz> selectSelfZDBZ(@Param("shopKey") String selfShopKey);

    List<DgPublicCode0> selectSelfGGDM(@Param("shopKey") String selfShopKey);

    /**
     * 营业管理其它数据
     */
    List<TbFydj> selectSelfFYDJ(@Param("shopKey") String shopKey);

    List<DgItemShowRank> selectSelfXFPXXS(@Param("shopKey") String shopKey);

    List<ServiceClass> selectSelfFWYFWKW(@Param("shopKey") String shopKey);

    List<DgAreaLimitItem> selectSelfXFQYXSPX();

    List<DgAreaManager> selectSelfXFQUGL();

    List<DgSeatManager> selectSelfKWGL();

    List<DgSeatChargingScheme> selectSelfBFFAZB(@Param("shopKey") String shopKey);

    List<DgSeatChargingSchemeS> selectSelfBFFAFB();

    List<DeskBusinessSetting> selectSelfQTYYSZ(@Param("shopKey") String shopKey);

    /** ********************************************BY XW S********************************************** */

    List<DgItemOutofStock> selectSelfPXGQ(@Param("shopKey") String selfShopKey);

    List<DgItemSaleLimit> selectSelfPXXLZB(@Param("shopKey") String selfShopKey);

    List<DgItemSaleLimitS> selectSelfPXXLFB(@Param("shopKey") String selfShopKey);

    List<DgItemDisable> selectSelfPXTY(@Param("shopKey") String selfShopKey);

    List<DgRecommendItem> selectSelfTJCP(@Param("shopKey") String selfShopKey);

    List<DgNewestItem> selectSelfZXCP(@Param("shopKey") String selfShopKey);

    List<DgItemProDep> selectSelfPXCPBMZB(@Param("shopKey") String selfShopKey);

    List<DgItemProDepS> selectSelfPXCPBMFB(@Param("shopKey") String selfShopKey);

    List<DgSpecialItem> selectSelfTSPX(@Param("shopKey") String selfShopKey);

    List<DgItemFromCook> selectSelfPXCPCS(@Param("shopKey") String selfShopKey);

    List<DgGateItemAllowCustom> selectSelfZDYJEPXXL(@Param("shopKey") String selfShopKey);

    List<DgItemCustomMoney> selectSelfZDYJEPX(@Param("shopKey") String selfShopKey);

    List<BgItemCustomMoneyName> selectSelfZDYJEMC(@Param("shopKey") String selfShopKey);

    List<DgItemGiftPlan> selectSelfPXCXFAZB(@Param("shopKey") String selfShopKey);

    List<DgItemGiftPlanS> selectSelfPXCXFAFB(@Param("shopKey") String selfShopKey);

    /** ********************************************BY XW D********************************************** */

//    List<DgItemMemberDiscount> selectSelfPXGLPXDZHYFAZB(@Param("shopKey") String shopKey);
    List<DgConsumptionArea> selectSelfXFQU(@Param("shopKey") String selfShopKey);
    
    List<DgConsumerSeat> selectSelfKW(@Param("shopKey") String selfShopKey);
    
    List<DgPos> selectSelfPOS(@Param("shopKey") String selfShopKey);
    
    List<DgSettlementWayType> selectSelfJSFSLX(@Param("shopKey") String selfShopKey);
    
    List<DgSettlementWay> selectSelfJSFS(@Param("shopKey") String selfShopKey);
    
    List<DgProMethodsType> selectSelfZZFSLX(@Param("shopKey") String selfShopKey);
    
    List<DgProMethods> selectSelfZZFS(@Param("shopKey") String selfShopKey);
    
    List<DgFlavor> selectSelfKWYQ(@Param("shopKey") String selfShopKey);
}
