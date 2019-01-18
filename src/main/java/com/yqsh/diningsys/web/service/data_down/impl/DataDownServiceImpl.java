package com.yqsh.diningsys.web.service.data_down.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yqsh.diningsys.web.model.ShopOnline;
import com.yqsh.diningsys.web.model.archive.*;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.yqsh.catering.web.mq.MqDataObj;
import com.yqsh.diningsys.web.dao.data_down.DataDownMapper;
import com.yqsh.diningsys.web.model.SysRoleMenu;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.businessMan.DgAreaLimitItem;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;
import com.yqsh.diningsys.web.model.businessMan.DgSeatItem;
import com.yqsh.diningsys.web.model.businessMan.DgSeatManager;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
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
import com.yqsh.diningsys.web.model.permission.SysPerBusiness;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.model.permission.SysPerItemfile;
import com.yqsh.diningsys.web.model.permission.SysPerItemfiletype;
import com.yqsh.diningsys.web.model.permission.SysPerOverview;
import com.yqsh.diningsys.web.service.data_down.DataDownService;
import com.yqsh.diningsys.web.util.DATA_DOWN_UTIL;

/**
 * Created on 2017-04-13 14:00
 *
 * @author zhshuo
 */
@Service
public class DataDownServiceImpl implements DataDownService {

    private static Logger logger = Logger.getLogger(DataDownServiceImpl.class);

    @Resource
    private DataDownMapper dataDownMapper;

    @Override
    public Integer insertOrEdit_SCZT(List<DgServing> maps) {
        if(maps.size() > 0){
            return dataDownMapper.insertOrUpdareSCZT(maps);
        }
        return 0;
    }

    @Override
    public Integer insertOrEdit_YGGL(List<SysUser> list) {
        if(list.size() > 0)
            return dataDownMapper.insertOrEdit_YGGL(list);
        return 0;
    }

    @Override
    public Integer insertOrEdit_ZDYY(Map<String, Object> resMap) {
        List<DgGiftForm> giftForms = JSON.parseObject(resMap.get("giftForms").toString(), new TypeToken<List<DgGiftForm>>(){}.getType());
        List<DgGiftFormReason> giftFormReasons = JSON.parseObject(resMap.get("giftFormReasons").toString(), new TypeToken<List<DgGiftFormReason>>(){}.getType());
        Integer integer = 0;
        if(giftForms.size() > 0)
            integer += dataDownMapper.insertOrEdit_ZDYY(giftForms);
        if(giftFormReasons.size() > 0)
            integer += dataDownMapper.insertOrEdit_ZDYYLX(giftFormReasons);
        return integer;
    }

    @Override
    public Integer insertOrEdit_BMGL(List<TbDep> list) {
        if(list.size() > 0)
            return dataDownMapper.insertOrEdit_BMGL(list);
        return 0;
    }

    @Override
    public Integer insertOrEdit_YYSB(List<TbBis> list) {
        if(list.size() > 0)
            return dataDownMapper.insertOrEdit_YYSB(list);
        return 0;
    }

    @Override
    public Integer insertOrEdit_TCYY(Map<String, Object> resMap) {
        List<TbRfc> rfc = JSON.parseObject(resMap.get("rfc").toString(), new TypeToken<List<TbRfc>>(){}.getType());
        List<TbRfct> rfct = JSON.parseObject(resMap.get("rfct").toString(), new TypeToken<List<TbRfct>>(){}.getType());
        Integer integer = 0;
        if(rfc!= null && rfc.size() > 0)
            integer += dataDownMapper.insertOrEdit_TCYY(rfc);
        if(rfct!= null && rfct.size() > 0){
        	for(TbRfct r: rfct){
        		try {
					r.setUpdateTime(r.getUpdateTime() == null ?null:new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").parse(r.getUpdateTime()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
        	}
            integer += dataDownMapper.insertOrEdit_TCYYT(rfct); 	
        }
        return 0;
    }

    @Override
    public Integer insertOrEdit_ZDBB(List<TbZdbz> tbZdbzs) {
        if(tbZdbzs.size() > 0)
            return dataDownMapper.insertOrEdit_ZDBB(tbZdbzs);
        return 0;
    }

    @Override
    public Integer insertOrEdit_FYXM(Map<String, Object> maps) {
        List<TbFyxm> tbFyxms = JSON.parseObject(maps.get("tbFyxms").toString(), new TypeToken<List<TbFyxm>>(){}.getType());
        List<TbFylx> tbFylxes = JSON.parseObject(maps.get("tbFylxes").toString(), new TypeToken<List<TbFylx>>(){}.getType());
        Integer integer = 0;
        if(tbFyxms.size() > 0)
            integer += dataDownMapper.insertOrEdit_FYXM(tbFyxms);
        if(tbFylxes.size() > 0)
            integer += dataDownMapper.insertOrEdit_FYXMLX(tbFylxes);
        return integer;
    }

    @Override
    public Integer insertOrEdit_POSDA(List<DgPos> dgPos) {
        if(dgPos.size() > 0)
            return dataDownMapper.insertOrEdit_POSDA(dgPos);
        return 0;
    }

    @Override
    public Integer insertOrEdit_JSFS(List<DgSettlementWay> dgSettlementWays) {
        if(dgSettlementWays.size() > 0)
            return dataDownMapper.insertOrEdit_JSFS(dgSettlementWays);
        return 0;
    }

    @Override
    public Integer insertOrEdit_ZZFF(Map<String, Object> map) {
        List<DgProMethods> proMethods = JSON.parseObject(map.get("dgProMethods").toString(), new TypeToken<List<DgProMethods>>(){}.getType());
        List<DgProMethodsType> proMethodsTypes = JSON.parseObject(map.get("dgProMethodsTypes").toString(), new TypeToken<List<DgProMethodsType>>(){}.getType());
        Integer integer = 0;
        if(proMethods.size() > 0)
            integer += dataDownMapper.insertOrEdit_ZZFF(proMethods);
        if(proMethodsTypes.size() > 0)
            integer += dataDownMapper.insertOrEdit_ZZFFLX(proMethodsTypes);
        return integer;
    }

    @Override
    public Integer insertOrEdit_KWYQ(List<DgFlavor> dgFlavors) {
        if(dgFlavors.size() > 0)
            return dataDownMapper.insertOrEdit_KWYQ(dgFlavors);
        return 0;
    }

    @Override
    public Integer insertOrEdit_FYDJ(List<TbFydj> tbFydjs) {
        if(tbFydjs.size() > 0)
            return dataDownMapper.insertOrEdit_FYDJ(tbFydjs);
        return 0;
    }

    @Override
    public Integer insertOrEdit_SFPXXSSZ(List<DgItemShowRank> dgItemShowRanks) {
        return dataDownMapper.insertOrEdit_SFPXXSSZ(dgItemShowRanks);
    }

    @Override
    public Integer insertOrEdit_FWYFWKW(List<ServiceClass> serviceClasses) {
        return dataDownMapper.insertOrEdit_FWYFWKW(serviceClasses);
    }

    @Override
    public Integer insertOrEdit_QTYYSZ(List<DeskBusinessSetting> deskBusinessSettings) {
        return dataDownMapper.insertOrEdit_QTYYSZ(deskBusinessSettings);
    }

    @Override
    public Integer insertOrEdit_XFQYYKWGL(Map<String, Object> map) {
        List<DgAreaLimitItem> dgAreaLimitItems = JSON.parseObject(map.get("dgAreaLimitItems").toString(), new TypeToken<List<DgAreaLimitItem>>(){}.getType());
        List<DgAreaManager> dgAreaManagers = JSON.parseObject(map.get("dgAreaManagers").toString(), new TypeToken<List<DgAreaManager>>(){}.getType());
        List<DgSeatManager> dgSeatManagers = JSON.parseObject(map.get("dgSeatManagers").toString(), new TypeToken<List<DgSeatManager>>(){}.getType());
        List<DgSeatChargingScheme> dgSeatChargingSchemes = JSON.parseObject(map.get("dgSeatChargingSchemes").toString(), new TypeToken<List<DgSeatChargingScheme>>(){}.getType());
        List<DgSeatChargingSchemeS> dgSeatChargingSchemeS = JSON.parseObject(map.get("dgSeatChargingSchemeS").toString(), new TypeToken<List<DgSeatChargingSchemeS>>(){}.getType());
		List<DgSeatItem> dgSeatItems = JSON.parseObject(map.get("dgSeatItems").toString(), new TypeToken<List<DgSeatItem>>(){}.getType());
        Integer integer = 0;
        integer += dataDownMapper.insertOrEdit_XFQYYKWGL_AREALIMITITEM(dgAreaLimitItems);
        if(dgAreaManagers.size() > 0)
            integer += dataDownMapper.insertOrEdit_XFQYYKWGL_AREAMANAGER(dgAreaManagers);
        if(dgSeatManagers.size() > 0)
            integer += dataDownMapper.insertOrEdit_XFQYYKWGL_SEATMANAGER(dgSeatManagers);
        if(dgSeatChargingSchemes.size() > 0)
            integer += dataDownMapper.insertOrEdit_XFQYYKWGL_SEATCHARGINSCHEMS(dgSeatChargingSchemes);
        if(dgSeatChargingSchemeS.size() > 0)
            integer += dataDownMapper.insertOrEdit_XFQYYKWGL_SEATCHARGINSCEMES2(dgSeatChargingSchemeS);
		integer += dataDownMapper.insertOrEdit_KWZZPX(dgSeatItems);
        return integer;
    }

    @Override
    public Integer insertOrEdit_QXGL(Map<String, Object> qxglMap) {
        List<SysPerOverview> SysPerOverview = JSON.parseObject(qxglMap.get("sysPerOverviews").toString(), new TypeToken<List<SysPerOverview>>(){}.getType());
        List<SysPerBusiness> perBusinesses = JSON.parseObject(qxglMap.get("sysPerBusinesses").toString(), new TypeToken<List<SysPerBusiness>>(){}.getType());
        List<SysPerDiscount> perDiscounts = JSON.parseObject(qxglMap.get("sysPerDiscounts").toString(), new TypeToken<List<SysPerDiscount>>(){}.getType());
        List<SysPerItemfile> perItemfiles = JSON.parseObject(qxglMap.get("sysPerItemfiles").toString(), new TypeToken<List<SysPerItemfile>>(){}.getType());
        List<SysPerItemfiletype> perItemfiletypes = JSON.parseObject(qxglMap.get("sysPerItemfiletypes").toString(), new TypeToken<List<SysPerItemfiletype>>(){}.getType());
        List<SysRoleMenu> roleMenus = JSON.parseObject(qxglMap.get("sysRoleMenus").toString(), new TypeToken<List<SysRoleMenu>>(){}.getType());
        Integer integer = 0;
        if(SysPerOverview.size() > 0)
            integer += dataDownMapper.insertOrEdit_QXGL_OVERVIEW(SysPerOverview);
        if(perBusinesses.size() > 0)
            integer += dataDownMapper.insertOrEdit_QXGL_BUSINESS(perBusinesses);
        if(perDiscounts.size() > 0)
            integer += dataDownMapper.insertOrEdit_QXGL_DISCOUNTS(perDiscounts);
        if(perItemfiles.size() > 0)
            integer += dataDownMapper.insertOrEdit_QXGL_ITEMFILES(perItemfiles);
        if(perItemfiletypes.size() > 0)
            integer += dataDownMapper.insertOrEdit_QXGL_ITEMFILETYPES(perItemfiletypes);
        if(roleMenus.size() > 0)
            integer += dataDownMapper.insertOrEdit_QXGL_ROLEMENU(roleMenus);
        return integer;
    }

    @Override
    public Integer insertOrEdit_GGDM(List<DgPublicCode0> dgPublicCode0s) {
        if(dgPublicCode0s.size() > 0)
            return dataDownMapper.insertOrEdit_GGDM(dgPublicCode0s);
        return 0;
    }

    @Override
    public Integer insertOrEdit_PXDA(Map<String, Object> map) {
        List<DgItemFile> dgItemFiles = JSON.parseObject(map.get("dgItemFiles").toString(), new TypeToken<List<DgItemFile>>(){}.getType());
        List<DgItemFilePackage> dgItemFilePackages = JSON.parseObject(map.get("dgItemFilePackages").toString(), new TypeToken<List<DgItemFilePackage>>(){}.getType());
        List<DgItemFilePackageBx> dgItemFilePackageBxes = JSON.parseObject(map.get("dgItemFilePackageBxes").toString(), new TypeToken<List<DgItemFilePackageBx>>(){}.getType());
        List<DgItemFilePackageKx> dgItemFilePackageKxes = JSON.parseObject(map.get("dgItemFilePackageKxes").toString(), new TypeToken<List<DgItemFilePackageKx>>(){}.getType());
        List<DgItemFilePackageSlxd> dgItemFilePackageSlxds = JSON.parseObject(map.get("dgItemFilePackageSlxds").toString(), new TypeToken<List<DgItemFilePackageSlxd>>(){}.getType());
        List<DgItemFilePackageTh> dgItemFilePackageThs = JSON.parseObject(map.get("dgItemFilePackageThs").toString(), new TypeToken<List<DgItemFilePackageTh>>(){}.getType());
        List<DgItemFileType> dgItemFileTypes = JSON.parseObject(map.get("dgItemFileTypes").toString(), new TypeToken<List<DgItemFileType>>(){}.getType());
        List<DgItemFileZccf> dgItemFileZccfs = JSON.parseObject(map.get("dgItemFileZccfs").toString(), new TypeToken<List<DgItemFileZccf>>(){}.getType());
        List<DgNutrition> dgNutritions = JSON.parseObject(map.get("dgNutritions").toString(), new TypeToken<List<DgNutrition>>(){}.getType());
        List<DgItemFileColor> dgItemFileColors = JSON.parseObject(map.get("dgItemFileColors").toString(), new TypeToken<List<DgItemFileColor>>(){}.getType());
        Integer integer = 0;
        if(dgItemFiles.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_BASE(dgItemFiles);
        if(dgItemFilePackages.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_TC(dgItemFilePackages);
        if(dgItemFilePackageBxes.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_TC_BX(dgItemFilePackageBxes);
        if(dgItemFilePackageKxes.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_TC_KX(dgItemFilePackageKxes);
        if(dgItemFilePackageSlxds.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_TC_SLXD(dgItemFilePackageSlxds);
        if(dgItemFilePackageThs.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_TC_TH(dgItemFilePackageThs);
        if(dgItemFileTypes.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_TYPE(dgItemFileTypes);
        if(dgItemFileZccfs.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_ZCCF(dgItemFileZccfs);
        if(dgNutritions.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_YY(dgNutritions);
        if(dgItemFileColors.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXDA_YS(dgItemFileColors);
        return integer;
    }

    @Override
    public void DATA_DOWN_FILTER(MqDataObj object) {
        List<String> sendModel = object.getSendModel();
        DATA_DOWN(sendModel);
    }

	@Override
	public Integer insertOrEdit_PXGL_ONE(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<DgItemTypeDiscount> dgItemTypeDiscounts = JSON.parseObject(map.get("dgItemTypeDiscounts").toString(), new TypeToken<List<DgItemTypeDiscount>>(){}.getType());
		List<DgItemDiscountProgramme> dgItemDiscountProgrammes = JSON.parseObject(map.get("dgItemDiscountProgrammes").toString(), new TypeToken<List<DgItemDiscountProgramme>>(){}.getType());
		List<DgItemDiscountProgrammeS> DgItemDiscountProgrammeSs = JSON.parseObject(map.get("DgItemDiscountProgrammeSs").toString(), new TypeToken<List<DgItemDiscountProgrammeS>>(){}.getType());
		List<DgWeekDiscount> dgWeekDiscounts = JSON.parseObject(map.get("dgWeekDiscounts").toString(), new TypeToken<List<DgWeekDiscount>>(){}.getType());
		List<DgItemMemberDiscount> dgItemMemberDiscounts = JSON.parseObject(map.get("dgItemMemberDiscounts").toString(), new TypeToken<List<DgItemMemberDiscount>>(){}.getType());
		List<DgItemMemberDiscountS> dgItemMemberDiscountSs = JSON.parseObject(map.get("dgItemMemberDiscountSs").toString(), new TypeToken<List<DgItemMemberDiscountS>>(){}.getType());
		List<DgForMealTimePrice> dgForMealTimePrices = JSON.parseObject(map.get("dgForMealTimePrices").toString(), new TypeToken<List<DgForMealTimePrice>>(){}.getType());
		List<DgForMealTimePriceS> dgForMealTimePriceSs = JSON.parseObject(map.get("dgForMealTimePriceSs").toString(), new TypeToken<List<DgForMealTimePriceS>>(){}.getType());
		List<DgPlacePrice> dgPlacePrices = JSON.parseObject(map.get("dgPlacePrices").toString(), new TypeToken<List<DgPlacePrice>>(){}.getType());
		List<DgPlacePriceS> dgPlacePriceSs = JSON.parseObject(map.get("dgPlacePriceSs").toString(), new TypeToken<List<DgPlacePriceS>>(){}.getType());
		Integer integer = 0;
//       if(dgItemTypeDiscounts.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZXLMR(dgItemTypeDiscounts);
         if(dgItemDiscountProgrammes.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZFAZB(dgItemDiscountProgrammes);
//        if(DgItemDiscountProgrammeSs.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZFAFB(DgItemDiscountProgrammeSs);
//        if(dgWeekDiscounts.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZFAXQB(dgWeekDiscounts);
         if(dgItemMemberDiscounts.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZHYFAZB(dgItemMemberDiscounts);
//        if(dgItemMemberDiscountSs.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZHYFAFB(dgItemMemberDiscountSs);
//        if(dgForMealTimePrices.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZYCDJASBZB(dgForMealTimePrices);
//        if(dgForMealTimePriceSs.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZYCDJASBFB(dgForMealTimePriceSs);
//        if(dgPlacePrices.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZYCDJAXFQYZB(dgPlacePrices);
//        if(dgPlacePriceSs.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZYCDJAXFQYFB(dgPlacePriceSs);
        return integer;
	}

	@Override
	public Integer insertOrEdit_PXGL_TWO(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<DgItemForWeek> dgItemForWeeks = JSON.parseObject(map.get("dgItemForWeeks").toString(), new TypeToken<List<DgItemForWeek>>(){}.getType());
		List<DgPromotionItem> dgPromotionItems = JSON.parseObject(map.get("dgPromotionItems").toString(), new TypeToken<List<DgPromotionItem>>(){}.getType());
		List<DgItemCurrentPrice> dgItemCurrentPrices = JSON.parseObject(map.get("dgItemCurrentPrices").toString(), new TypeToken<List<DgItemCurrentPrice>>(){}.getType());
		List<DgItemPriceLadder> dgItemPriceLadders = JSON.parseObject(map.get("dgItemPriceLadders").toString(), new TypeToken<List<DgItemPriceLadder>>(){}.getType());
		List<DgItemPricePriority> dgItemPricePrioritys = JSON.parseObject(map.get("dgItemPricePrioritys").toString(), new TypeToken<List<DgItemPricePriority>>(){}.getType());
		List<DgImportantActivityDiscount> dgImportantActivityDiscounts = JSON.parseObject(map.get("dgImportantActivityDiscounts").toString(), new TypeToken<List<DgImportantActivityDiscount>>(){}.getType());
		List<DgImportantAcitivityDiscountS> dgImportantAcitivityDiscountSs = JSON.parseObject(map.get("dgImportantAcitivityDiscountSs").toString(), new TypeToken<List<DgImportantAcitivityDiscountS>>(){}.getType());
		Integer integer = 0;
//       if(dgItemForWeeks.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZYCDJAXQ(dgItemForWeeks);
//        if(dgPromotionItems.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZCXPX(dgPromotionItems);
//        if(dgItemCurrentPrices.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZSJPX(dgItemCurrentPrices);
//        if(dgItemPriceLadders.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZJTJG(dgItemPriceLadders);
          if(dgItemPricePrioritys.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZJGYXJ(dgItemPricePrioritys);
          if(dgImportantActivityDiscounts.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZZYHDZB(dgImportantActivityDiscounts);
//        if(dgImportantAcitivityDiscountSs.size() > 0)
            integer += dataDownMapper.insertOrEdit_PXGL_PXDZZYHDFB(dgImportantAcitivityDiscountSs);
        return integer;
	}
	
	
	@Override
	public Integer insertOrEdit_XFQUHKW(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<DgConsumptionArea> dgConsumptionAreas = JSON.parseObject(map.get("dgConsumptionAreas").toString(), new TypeToken<List<DgConsumptionArea>>(){}.getType());
		List<DgConsumerSeat> dgConsumerSeats = JSON.parseObject(map.get("dgConsumerSeats").toString(), new TypeToken<List<DgConsumerSeat>>(){}.getType());
		List<DgAllowNumber> dgAllowNumbers = JSON.parseObject(map.get("dgAllowNumbers").toString(), new TypeToken<List<DgAllowNumber>>(){}.getType());
		Integer integer = 0;
		if(dgConsumptionAreas.size() > 0)
			integer += dataDownMapper.insertOrEdit_XFQY(dgConsumptionAreas);
		if(dgConsumerSeats.size() > 0)
			integer += dataDownMapper.insertOrEdit_KW(dgConsumerSeats);
		if(dgAllowNumbers.size() > 0)
			integer += dataDownMapper.insertOrEdit_KWLX(dgAllowNumbers);
		return integer;
	}

    @Override
    public Integer insertOrEdit_ZZJG(ShopOnline shopOnline) {
        if(shopOnline != null){
            dataDownMapper.deleteOrgData();
            return dataDownMapper.insertOrEdit_ZZJG(shopOnline);
        }
        return 0;
    }

    @Override
	public Integer insertOrEdit_PXGQ(List<DgItemOutofStock> dgItemOutofStock) {
        if(dgItemOutofStock.size() > 0)
        	return dataDownMapper.insertOrEdit_PXGQ(dgItemOutofStock);
		return 0;
	}

	@Override
	public Integer insertOrEdit_PXXLZB(List<DgItemSaleLimit> dgItemSaleLimit) {
		if(dgItemSaleLimit.size() > 0)
        	return dataDownMapper.insertOrEdit_PXXLZB(dgItemSaleLimit);
		return 0;
	}

	@Override
	public Integer insertOrEdit_PXXLFB(List<DgItemSaleLimitS> dgItemSaleLimits) {
		if(dgItemSaleLimits.size() > 0)
        	return dataDownMapper.insertOrEdit_PXXLFB(dgItemSaleLimits);
		return 0;
	}

	@Override
	public Integer insertOrEdit_PXTY(List<DgItemDisable> dgItemDisable) {
		if(dgItemDisable.size() > 0)
        	return dataDownMapper.insertOrEdit_PXTY(dgItemDisable);
		return 0;
	}

	@Override
	public Integer insertOrEdit_TJCP(List<DgRecommendItem> dgRecommendItem) {
        return dataDownMapper.insertOrEdit_TJCP(dgRecommendItem);
	}

	@Override
	public Integer insertOrEdit_ZXCP(List<DgNewestItem> dgNewestItem) {
        return dataDownMapper.insertOrEdit_ZXCP(dgNewestItem);
	}

	@Override
	public Integer insertOrEdit_PXCPBMZB(List<DgItemProDep> dgItemProDep) {
		if(dgItemProDep.size() > 0)
        	return dataDownMapper.insertOrEdit_PXCPBMZB(dgItemProDep);
		return 0;
	}

	@Override
	public Integer insertOrEdit_PXCPBMFB(List<DgItemProDepS> dgItemProDeps) {
		if(dgItemProDeps.size() > 0)
        	return dataDownMapper.insertOrEdit_PXCPBMFB(dgItemProDeps);
		return 0;
	}

	@Override
	public Integer insertOrEdit_TSPX(List<DgSpecialItem> dgSpecialItem) {
		if(dgSpecialItem.size() > 0)
        	return dataDownMapper.insertOrEdit_TSPX(dgSpecialItem);
		return 0;
	}

	@Override
	public Integer insertOrEdit_PXCPCS(List<DgItemFromCook> dgItemFromCook) {
		if(dgItemFromCook.size() > 0)
        	return dataDownMapper.insertOrEdit_PXCPCS(dgItemFromCook);
		return 0;
	}

	@Override
	public Integer insertOrEdit_ZDYJEPXXL(
			List<DgGateItemAllowCustom> dgGateItemAllowCustom) {
		if(dgGateItemAllowCustom.size() > 0)
        	return dataDownMapper.insertOrEdit_ZDYJEPXXL(dgGateItemAllowCustom);
		return 0;
	}

	@Override
	public Integer insertOrEdit_ZDYJEPX(
			List<DgItemCustomMoney> dgItemCustomMoney) {
		if(dgItemCustomMoney.size() > 0)
        	return dataDownMapper.insertOrEdit_ZDYJEPX(dgItemCustomMoney);
		return 0;
	}

	@Override
	public Integer insertOrEdit_ZDYJEMC(
			List<BgItemCustomMoneyName> bgItemCustomMoneyName) {
		if(bgItemCustomMoneyName.size() > 0)
        	return dataDownMapper.insertOrEdit_ZDYJEMC(bgItemCustomMoneyName);
		return 0;
	}

	@Override
	public Integer insertOrEdit_PXCXFAZB(List<DgItemGiftPlan> dgItemGiftPlan) {
		if(dgItemGiftPlan.size() > 0)
        	return dataDownMapper.insertOrEdit_PXCXFAZB(dgItemGiftPlan);
		return 0;
	}

	@Override
	public Integer insertOrEdit_PXCXFAFB(List<DgItemGiftPlanS> dgItemGiftPlans) {
		if(dgItemGiftPlans.size() > 0)
        	return dataDownMapper.insertOrEdit_PXCXFAFB(dgItemGiftPlans);
		return 0;
	}
	
    private synchronized void DATA_DOWN(List<String> sendDataModel) {
        if(sendDataModel.size() > 0){
            Integer editCount = -1;
            switch (sendDataModel.get(0)) {
                case "DATA_GGDM"://公共代码
                    editCount = DATA_DOWN_UTIL.DOWN_GGDM();
                    logger.info("公共代码数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_YGGL"://员工管理
                    editCount = DATA_DOWN_UTIL.DOWN_YGGL();
                    logger.info("员工管理数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_SCZT"://上菜状态
                    editCount = DATA_DOWN_UTIL.DOWN_SCZT();
                    logger.info("上菜状态数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_ZDYY"://赠单原因
                    editCount = DATA_DOWN_UTIL.DOWN_ZDYY();
                    logger.info("赠单原因数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_BMGL"://部门管理
                    editCount = DATA_DOWN_UTIL.DOWN_BMGL();
                    logger.info("部门管理数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_YYSB"://营业市别
                    editCount = DATA_DOWN_UTIL.DOWN_YYSB();
                    logger.info("营业市别数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_PXDA"://品项档案
                    editCount = DATA_DOWN_UTIL.DOWN_PXDA();
                    logger.info("品项档案数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_TCYY"://退菜原因
                    editCount = DATA_DOWN_UTIL.DOWN_TCYY();
                    logger.info("退菜原因数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_ZDBZ"://整单备注
                    editCount = DATA_DOWN_UTIL.DOWN_ZDBB();
                    logger.info("整单备注数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_FYXM"://费用项目
                    editCount = DATA_DOWN_UTIL.DOWN_FYXM();
                    logger.info("费用项目数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_XFQYHKE"://消费区域和客位
                    editCount = DATA_DOWN_UTIL.DOWN_XFQUHKE();
                    logger.info("消费区域和客位数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_POSDA"://POS档案
                    editCount = DATA_DOWN_UTIL.DOWN_POSDA();
                    logger.info("POS档案数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_JSFS"://结算方式
                    editCount = DATA_DOWN_UTIL.DOWN_JSFS();
                    logger.info("结算方式数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_ZZFF"://制作方法
                    editCount = DATA_DOWN_UTIL.DOWN_ZZFF();
                    logger.info("制作方法数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_KWYQ"://口味要求
                    editCount = DATA_DOWN_UTIL.DOWN_KWYQ();
                    logger.info("口味要求数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_PXGL"://品项管理
                    editCount = DATA_DOWN_UTIL.DOWN_PXGL_ONE();
                    editCount += DATA_DOWN_UTIL.DOWN_PXGL_TWO();
                    editCount += GET_PXGL_COUNT();
                    logger.info("品项管理数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_FYDJ"://费用登记
                    editCount = DATA_DOWN_UTIL.DOWN_FYDJ();
                    logger.info("费用登记数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_XFPXXSSZ"://消费品项显示设置
                    editCount = DATA_DOWN_UTIL.DOWN_SFPXXSSZ();
                    logger.info("消费品项显示设置下载成功，共计" + editCount + "条");
                    break;
                case "DATA_FWYFWKW"://服务员服务客位
                    editCount = DATA_DOWN_UTIL.DOWN_FWYFWKW();
                    logger.info("服务员服务客位数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_XFQYYKWGL"://消费区域与客位管理
                    editCount = DATA_DOWN_UTIL.DOWN_XFQYYKWGL();
                    logger.info("消费区域与客位管理下载成功，共计" + editCount + "条");
                    break;
                case "DATA_QTYYSZ"://前台营业设置
                    editCount = DATA_DOWN_UTIL.DOWN_QTYYSZ();
                    logger.info("前台营业设置数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_CZQX"://操作权限
                    editCount = DATA_DOWN_UTIL.DOWN_QXGL();
                    logger.info("权限管理数据下载成功，共计" + editCount + "条");
                    break;
                case "DATA_ZZJG"://组织机构
                    editCount = DATA_DOWN_UTIL.DOWN_ZZJG();
                    logger.info("组织机构数据下载成功，共计" + editCount + "条");
                    break;
                default:
                    throw new RuntimeException("未知的下发数据类型！");
            }
            sendDataModel.remove(0);
            DATA_DOWN(sendDataModel);
        }
    }
    
	public Integer GET_PXGL_COUNT() {
		Integer editCount = 0;
//		editCount += DATA_DOWN_UTIL.DOWN_PXGQ();
//		editCount += DATA_DOWN_UTIL.DOWN_PXXLZB();
//		editCount += DATA_DOWN_UTIL.DOWN_PXXLFB();
//		editCount += DATA_DOWN_UTIL.DOWN_PXTY();
		editCount += DATA_DOWN_UTIL.DOWN_TJCP();
		
		editCount += DATA_DOWN_UTIL.DOWN_ZXCP();
//		editCount += DATA_DOWN_UTIL.DOWN_PXCPBMZB();
//		editCount += DATA_DOWN_UTIL.DOWN_PXCPBMFB();
//		editCount += DATA_DOWN_UTIL.DOWN_TSPX();
//		editCount += DATA_DOWN_UTIL.DOWN_PXCPCS();
		
//		editCount += DATA_DOWN_UTIL.DOWN_ZDYJEPXXL();
//		editCount += DATA_DOWN_UTIL.DOWN_ZDYJEMC();
//		editCount += DATA_DOWN_UTIL.DOWN_ZDYJEPX();
//		editCount += DATA_DOWN_UTIL.DOWN_PXCXFAZB();
//		editCount += DATA_DOWN_UTIL.DOWN_PXCXFAFB();
		return editCount;
	}

}
