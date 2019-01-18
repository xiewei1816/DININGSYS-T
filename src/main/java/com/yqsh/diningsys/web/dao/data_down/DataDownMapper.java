package com.yqsh.diningsys.web.dao.data_down;

import com.yqsh.diningsys.web.controller.inve.DgItemTypeController;
import com.yqsh.diningsys.web.model.ShopOnline;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.*;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.DgGateItemAllowCustom;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCustomMoney;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDisable;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemFromCook;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlan;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemGiftPlanS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemOutofStock;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDep;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;
import com.yqsh.diningsys.web.model.deskBusiness.DgNewestItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgRecommendItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgSpecialItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantAcitivityDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgImportantActivityDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCurrentPrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgrammeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemForWeek;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPriceLadder;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemPricePriority;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
import com.yqsh.diningsys.web.model.deskBusiness.DgPromotionItem;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.ServiceClass;
import com.yqsh.diningsys.web.model.permission.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-13 14:00
 *
 * @author zhshuo
 */
@Repository
public interface DataDownMapper {

    Integer insertOrUpdareSCZT(@Param("list") List<DgServing> list);

    Integer insertOrEdit_YGGL(@Param("list") List<SysUser> list);

    Integer insertOrEdit_ZDYY(@Param("list") List<DgGiftForm> giftForms);

    Integer insertOrEdit_ZDYYLX(@Param("list") List<DgGiftFormReason> giftFormReasons);

    Integer insertOrEdit_BMGL(@Param("list") List<TbDep> list);

    Integer insertOrEdit_YYSB(@Param("list") List<TbBis> list);

    Integer insertOrEdit_TCYY(@Param("list") List<TbRfc> list);

    Integer insertOrEdit_TCYYT(@Param("list") List<TbRfct> list);

    Integer insertOrEdit_ZDBB(@Param("list") List<TbZdbz> tbZdbzs);

    Integer insertOrEdit_FYXM(@Param("list") List<TbFyxm> tbFyxms);

    Integer insertOrEdit_FYXMLX(@Param("list") List<TbFylx> tbFylxes);

    Integer insertOrEdit_POSDA(@Param("list") List<DgPos> dgPos);

    Integer insertOrEdit_JSFS(@Param("list") List<DgSettlementWay> dgSettlementWays);

    Integer insertOrEdit_ZZFF(@Param("list") List<DgProMethods> proMethods);

    Integer insertOrEdit_ZZFFLX(@Param("list") List<DgProMethodsType> proMethodsTypes);

    Integer insertOrEdit_KWYQ(@Param("list") List<DgFlavor> dgFlavors);

    Integer insertOrEdit_FYDJ(@Param("list") List<TbFydj> tbFydjs);

    Integer insertOrEdit_SFPXXSSZ(@Param("list") List<DgItemShowRank> dgItemShowRanks);

    Integer insertOrEdit_FWYFWKW(@Param("list") List<ServiceClass> serviceClasses);

    Integer insertOrEdit_QTYYSZ(@Param("list") List<DeskBusinessSetting> deskBusinessSettings);

    Integer insertOrEdit_XFQYYKWGL_AREALIMITITEM(@Param("list") List<DgAreaLimitItem> dgAreaLimitItems);

    Integer insertOrEdit_XFQYYKWGL_AREAMANAGER(@Param("list") List<DgAreaManager> dgAreaManagers);

    Integer insertOrEdit_XFQYYKWGL_SEATMANAGER(@Param("list") List<DgSeatManager> dgSeatManagers);

    Integer insertOrEdit_XFQYYKWGL_SEATCHARGINSCHEMS(@Param("list") List<DgSeatChargingScheme> dgSeatChargingSchemes);

    Integer insertOrEdit_XFQYYKWGL_SEATCHARGINSCEMES2(@Param("list") List<DgSeatChargingSchemeS> dgSeatChargingSchemeS);

    Integer insertOrEdit_QXGL_OVERVIEW(@Param("list") List<SysPerOverview> sysPerOverview);

    Integer insertOrEdit_QXGL_BUSINESS(@Param("list") List<SysPerBusiness> perBusinesses);

    Integer insertOrEdit_QXGL_DISCOUNTS(@Param("list") List<SysPerDiscount> perDiscounts);

    Integer insertOrEdit_QXGL_ITEMFILES(@Param("list") List<SysPerItemfile> perItemfiles);

    Integer insertOrEdit_QXGL_ITEMFILETYPES(@Param("list") List<SysPerItemfiletype> perItemfiletypes);

    Integer insertOrEdit_QXGL_ROLEMENU(@Param("list") List<SysRoleMenu> roleMenus);

    Integer insertOrEdit_GGDM(@Param("list") List<DgPublicCode0> dgPublicCode0s);

    Integer insertOrEdit_PXDA_BASE(@Param("list") List<DgItemFile> dgItemFiles);

    Integer insertOrEdit_PXDA_TC(@Param("list") List<DgItemFilePackage> dgItemFilePackages);

    Integer insertOrEdit_PXDA_TC_BX(@Param("list") List<DgItemFilePackageBx> dgItemFilePackageBxes);

    Integer insertOrEdit_PXDA_TC_KX(@Param("list") List<DgItemFilePackageKx> dgItemFilePackageKxes);

    Integer insertOrEdit_PXDA_TC_SLXD(@Param("list") List<DgItemFilePackageSlxd> dgItemFilePackageSlxds);

    Integer insertOrEdit_PXDA_TC_TH(@Param("list") List<DgItemFilePackageTh> dgItemFilePackageThs);

    Integer insertOrEdit_PXDA_TYPE(@Param("list") List<DgItemFileType> dgItemFileTypes);

    Integer insertOrEdit_PXDA_ZCCF(@Param("list") List<DgItemFileZccf> dgItemFileZccfs);

    Integer insertOrEdit_PXDA_YY(@Param("list") List<DgNutrition> dgNutritions);

/************************************ by xiewei ****************************************************/
	Integer insertOrEdit_PXGQ(@Param("list") List<DgItemOutofStock> dgItemOutofStock);

	Integer insertOrEdit_PXXLZB(@Param("list") List<DgItemSaleLimit> dgItemOutofStock);

	Integer insertOrEdit_PXXLFB(@Param("list") List<DgItemSaleLimitS> dgItemSaleLimits);

	Integer insertOrEdit_PXTY(@Param("list") List<DgItemDisable> dgItemDisable);

	Integer insertOrEdit_TJCP(@Param("list") List<DgRecommendItem> dgRecommendItem);

	Integer insertOrEdit_ZXCP(@Param("list") List<DgNewestItem> dgNewestItem);

	Integer insertOrEdit_PXCPBMZB(@Param("list") List<DgItemProDep> dgItemProDep);

	Integer insertOrEdit_PXCPBMFB(@Param("list") List<DgItemProDepS> dgItemProDeps);

	Integer insertOrEdit_TSPX(@Param("list") List<DgSpecialItem> dgSpecialItem);

	Integer insertOrEdit_PXCPCS(@Param("list") List<DgItemFromCook> dgItemFromCook);

	Integer insertOrEdit_ZDYJEPXXL(
			@Param("list") List<DgGateItemAllowCustom> dgGateItemAllowCustom);

	Integer insertOrEdit_ZDYJEPX(@Param("list") List<DgItemCustomMoney> dgItemCustomMoney);

	Integer insertOrEdit_ZDYJEMC(
			@Param("list") List<BgItemCustomMoneyName> bgItemCustomMoneyName);

	Integer insertOrEdit_PXCXFAZB(@Param("list") List<DgItemGiftPlan> dgItemGiftPlan);

	Integer insertOrEdit_PXCXFAFB(@Param("list") List<DgItemGiftPlanS> dgItemGiftPlans);

    Integer insertOrEdit_PXGL_PXDZXLMR(@Param("list") List<DgItemTypeDiscount> roleMenus);

    Integer insertOrEdit_PXGL_PXDZFAZB(@Param("list") List<DgItemDiscountProgramme> roleMenus);

    Integer insertOrEdit_PXGL_PXDZFAFB(@Param("list") List<DgItemDiscountProgrammeS> roleMenus);

    Integer insertOrEdit_PXGL_PXDZFAXQB(@Param("list") List<DgWeekDiscount> roleMenus);

    Integer insertOrEdit_PXGL_PXDZHYFAZB(@Param("list") List<DgItemMemberDiscount> roleMenus);

    Integer insertOrEdit_PXGL_PXDZHYFAFB(@Param("list") List<DgItemMemberDiscountS> roleMenus);

    Integer insertOrEdit_PXGL_PXDZYCDJASBZB(@Param("list") List<DgForMealTimePrice> roleMenus);

    Integer insertOrEdit_PXGL_PXDZYCDJASBFB(@Param("list") List<DgForMealTimePriceS> roleMenus);

    Integer insertOrEdit_PXGL_PXDZYCDJAXFQYZB(@Param("list") List<DgPlacePrice> roleMenus);

    Integer insertOrEdit_PXGL_PXDZYCDJAXFQYFB(@Param("list") List<DgPlacePriceS> roleMenus);

    Integer insertOrEdit_PXGL_PXDZYCDJAXQ(@Param("list") List<DgItemForWeek> roleMenus);

    Integer insertOrEdit_PXGL_PXDZCXPX(@Param("list") List<DgPromotionItem> roleMenus);

    Integer insertOrEdit_PXGL_PXDZSJPX(@Param("list") List<DgItemCurrentPrice> roleMenus);

    Integer insertOrEdit_PXGL_PXDZJTJG(@Param("list") List<DgItemPriceLadder> roleMenus);

    Integer insertOrEdit_PXGL_PXDZJGYXJ(@Param("list") List<DgItemPricePriority> roleMenus);

    Integer insertOrEdit_PXGL_PXDZZYHDZB(@Param("list") List<DgImportantActivityDiscount> roleMenus);

    Integer insertOrEdit_PXGL_PXDZZYHDFB(@Param("list") List<DgImportantAcitivityDiscountS> roleMenus);
    
    Integer insertOrEdit_XFQY(@Param("list") List<DgConsumptionArea> dca);
    
    Integer insertOrEdit_KW(@Param("list") List<DgConsumerSeat> dca);
    
    Integer insertOrEdit_KWLX(@Param("list") List<DgAllowNumber> dca);
    
    Integer insertOrEdit_KWZZPX(@Param("list") List<DgSeatItem> dca);

    Integer insertOrEdit_ZZJG(ShopOnline shopOnline);

    Integer deleteOrgData();
    
    Integer insertOrEdit_PXDA_YS(@Param("list") List<DgItemFileColor> dca);
}
