package com.yqsh.diningsys.web.service.data_down;

import com.yqsh.catering.web.mq.MqDataObj;
import com.yqsh.diningsys.web.model.ShopOnline;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;
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

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Created on 2017-04-13 13:39
 *
 * @author zhshuo
 */
public interface DataDownService {

    void DATA_DOWN_FILTER(MqDataObj object);

    Integer insertOrEdit_SCZT(List<DgServing> maps);

    Integer insertOrEdit_YGGL(List<SysUser> list);

    Integer insertOrEdit_ZDYY(Map<String, Object> resMap);

    Integer insertOrEdit_BMGL(List<TbDep> list);

    Integer insertOrEdit_YYSB(List<TbBis> list);

    Integer insertOrEdit_TCYY(Map<String, Object> resMap);

    Integer insertOrEdit_ZDBB(List<TbZdbz> tbZdbzs);

    Integer insertOrEdit_FYXM(Map<String,Object> maps);

    Integer insertOrEdit_POSDA(List<DgPos> dgPos);

    Integer insertOrEdit_JSFS(List<DgSettlementWay> dgSettlementWays);

    Integer insertOrEdit_ZZFF(Map<String, Object> map);

    Integer insertOrEdit_KWYQ(List<DgFlavor> dgFlavors);

    Integer insertOrEdit_FYDJ(List<TbFydj> tbFydjs);

    Integer insertOrEdit_SFPXXSSZ(List<DgItemShowRank> dgItemShowRanks);

    Integer insertOrEdit_FWYFWKW(List<ServiceClass> serviceClasses);

    Integer insertOrEdit_QTYYSZ(List<DeskBusinessSetting> deskBusinessSettings);

    Integer insertOrEdit_XFQYYKWGL(Map<String, Object> map);

    Integer insertOrEdit_QXGL(Map<String, Object> qxglMap);

    Integer insertOrEdit_GGDM(List<DgPublicCode0> dgPublicCode0s);

    Integer insertOrEdit_PXDA(Map<String, Object> map);

    /************************************ by xiewei ****************************************************/

	Integer insertOrEdit_PXGQ(List<DgItemOutofStock> dgItemOutofStock);

	Integer insertOrEdit_PXXLZB(List<DgItemSaleLimit> dgItemOutofStock);

	Integer insertOrEdit_PXXLFB(List<DgItemSaleLimitS> dgItemSaleLimits);

	Integer insertOrEdit_PXTY(List<DgItemDisable> dgItemDisable);

	Integer insertOrEdit_TJCP(List<DgRecommendItem> dgRecommendItem);

	Integer insertOrEdit_ZXCP(List<DgNewestItem> dgNewestItem);

	Integer insertOrEdit_PXCPBMZB(List<DgItemProDep> dgItemProDep);

	Integer insertOrEdit_PXCPBMFB(List<DgItemProDepS> dgItemProDeps);

	Integer insertOrEdit_TSPX(List<DgSpecialItem> dgSpecialItem);

	Integer insertOrEdit_PXCPCS(List<DgItemFromCook> dgItemFromCook);

	Integer insertOrEdit_ZDYJEPXXL(
			List<DgGateItemAllowCustom> dgGateItemAllowCustom);

	Integer insertOrEdit_ZDYJEPX(List<DgItemCustomMoney> dgItemCustomMoney);

	Integer insertOrEdit_ZDYJEMC(
			List<BgItemCustomMoneyName> bgItemCustomMoneyName);

	Integer insertOrEdit_PXCXFAZB(List<DgItemGiftPlan> dgItemGiftPlan);

	Integer insertOrEdit_PXCXFAFB(List<DgItemGiftPlanS> dgItemGiftPlans);

    Integer insertOrEdit_PXGL_ONE(Map<String, Object> map);

    Integer insertOrEdit_PXGL_TWO(Map<String, Object> map);
    
    Integer insertOrEdit_XFQUHKW(Map<String, Object> map);

    Integer insertOrEdit_ZZJG(ShopOnline shopOnline);
}
