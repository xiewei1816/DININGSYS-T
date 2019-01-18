package com.yqsh.diningsys.web.service.data_upload.impl;

import com.yqsh.diningsys.core.util.pay.DgConstants;

import com.yqsh.diningsys.web.dao.data_upload.DataUploadMapper;
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
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.businessMan.*;
import com.yqsh.diningsys.web.model.deskBusiness.*;
import com.yqsh.diningsys.web.service.data_upload.DataUploadService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-21 11:13
 *
 * @author zhshuo
 */
@Service
public class DataUploadServiceImpl implements DataUploadService {

    @Resource
    private DataUploadMapper dataUploadMapper;

    @Override
    public List<SysUser> selectSelfYGGL() {
        return dataUploadMapper.selectSelfYGGL(getSelfShopKey());
    }

    String getSelfShopKey(){
        return DgConstants.getApplicationPropertiesValue("mq.selfShopKey");
    }

	@Override
	public List<DgItemTypeDiscount> selectSelfPXGLPXDZXLMR() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZXLMR();
	}

	@Override
	public List<DgItemDiscountProgramme> selectSelfPXGLPXDZFAZB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZFAZB(getSelfShopKey());
	}

	@Override
	public List<DgItemDiscountProgrammeS> selectSelfPXGLPXDZFAFB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZFAFB();
	}

	@Override
	public List<DgWeekDiscount> selectSelfPXGLPXDZFAXQB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZFAXQB(getSelfShopKey());
	}

	@Override
	public List<DgItemMemberDiscount> selectSelfPXGLPXDZHYFAZB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZHYFAZB(getSelfShopKey());
	}

	@Override
	public List<DgItemMemberDiscountS> selectSelfPXGLPXDZHYFAFB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZHYFAFB();
	}

	@Override
	public List<DgForMealTimePrice> selectSelfPXGLPXDZYCDJASBZB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZYCDJASBZB(getSelfShopKey());
	}

	@Override
	public List<DgForMealTimePriceS> selectSelfPXGLPXDZYCDJASBFB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZYCDJASBFB();
	}

	@Override
	public List<DgPlacePrice> selectSelfPXGLPXDZYCDJAXFQYZB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZYCDJAXFQYZB(getSelfShopKey());
	}

	@Override
	public List<DgPlacePriceS> selectSelfPXGLPXDZYCDJAXFQYFB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZYCDJAXFQYFB();
	}

	@Override
	public List<DgItemForWeek> selectSelfPXGLPXDZYCDJAXQ() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZYCDJAXQ(getSelfShopKey());
	}

	@Override
	public List<DgPromotionItem> selectSelfPXGLPXDZCXPX() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZCXPX(getSelfShopKey());
	}

	@Override
	public List<DgItemCurrentPrice> selectSelfPXGLPXDZSJPX() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZSJPX(getSelfShopKey());
	}

	@Override
	public List<DgItemPriceLadder> selectSelfPXGLPXDZJTJG() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZJTJG(getSelfShopKey());
	}

	@Override
	public List<DgItemPricePriority> selectSelfPXGLPXDZJGYXJ() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZJGYXJ(getSelfShopKey());
	}

	@Override
	public List<DgImportantActivityDiscount> selectSelfPXGLPXDZZYHDZB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZZYHDZB(getSelfShopKey());
	}

	@Override
	public List<DgImportantAcitivityDiscountS> selectSelfPXGLPXDZZYHDFB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPXGLPXDZZYHDFB();
	}

	 /** ********************************************BY XW S********************************************** */
	@Override
	public List<DgItemOutofStock> selectSelfPXGQ() {
		return dataUploadMapper.selectSelfPXGQ(getSelfShopKey());
	}

	@Override
	public List<DgItemSaleLimit> selectSelfPXXLZB() {
		return dataUploadMapper.selectSelfPXXLZB(getSelfShopKey());
	}

	@Override
	public List<DgItemSaleLimitS> selectSelfPXXLFB() {
		return dataUploadMapper.selectSelfPXXLFB(getSelfShopKey());
	}

	@Override
	public List<DgItemDisable> selectSelfPXTY() {
		return dataUploadMapper.selectSelfPXTY(getSelfShopKey());
	}

	@Override
	public List<DgRecommendItem> selectSelfTJCP() {
		return dataUploadMapper.selectSelfTJCP(getSelfShopKey());
	}

	@Override
	public List<TbFydj> selectSelfFYDJ() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfFYDJ(getSelfShopKey());
	}

	@Override
	public List<DgItemShowRank> selectSelfXFPXXS() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfXFPXXS(getSelfShopKey());
	}

	@Override
	public List<ServiceClass> selectSelfFWYFWKW() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfFWYFWKW(getSelfShopKey());
	}

	@Override
	public List<DgAreaLimitItem> selectSelfXFQYXSPX() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfXFQYXSPX();
	}

	@Override
	public List<DgAreaManager> selectSelfXFQUGL() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfXFQUGL();
	}

	@Override
	public List<DgSeatManager> selectSelfKWGL() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfKWGL();
	}

	@Override
	public List<DgSeatChargingScheme> selectSelfBFFAZB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfBFFAZB(getSelfShopKey());
	}

	@Override
	public List<DgSeatChargingSchemeS> selectSelfBFFAFB() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfBFFAFB();
	}

	@Override
	public List<DeskBusinessSetting> selectSelfQTYYSZ() {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfQTYYSZ(getSelfShopKey());
	}

	@Override
	public List<DgNewestItem> selectSelfZXCP() {
		return dataUploadMapper.selectSelfZXCP(getSelfShopKey());
	}

	@Override
	public List<DgItemProDep> selectSelfPXCPBMZB() {
		return dataUploadMapper.selectSelfPXCPBMZB(getSelfShopKey());
	}

	@Override
	public List<DgItemProDepS> selectSelfPXCPBMFB() {
		return dataUploadMapper.selectSelfPXCPBMFB(getSelfShopKey());
	}

	@Override
	public List<DgSpecialItem> selectSelfTSPX() {
		return dataUploadMapper.selectSelfTSPX(getSelfShopKey());
	}

	@Override
	public List<DgItemFromCook> selectSelfPXCPCS() {
		return dataUploadMapper.selectSelfPXCPCS(getSelfShopKey());
	}

	@Override
	public List<DgGateItemAllowCustom> selectSelfZDYJEPXXL() {
		return dataUploadMapper.selectSelfZDYJEPXXL(getSelfShopKey());
	}

	@Override
	public List<DgItemCustomMoney> selectSelfZDYJEPX() {
		return dataUploadMapper.selectSelfZDYJEPX(getSelfShopKey());
	}

	@Override
	public List<BgItemCustomMoneyName> selectSelfZDYJEMC() {
		return dataUploadMapper.selectSelfZDYJEMC(getSelfShopKey());
	}

	@Override
	public List<DgItemGiftPlan> selectSelfPXCXFAZB() {
		return dataUploadMapper.selectSelfPXCXFAZB(getSelfShopKey());
	}

	@Override
	public List<DgItemGiftPlanS> selectSelfPXCXFAFB() {
		return dataUploadMapper.selectSelfPXCXFAFB(getSelfShopKey());
	}
    /** ********************************************BY XW S********************************************** */

	@Override
	public List<DgConsumptionArea> selectSelfXFQU(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfXFQU(getSelfShopKey());
	}

	@Override
	public List<DgConsumerSeat> selectSelfKW(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfKW(getSelfShopKey());
	}

	@Override
	public List<DgPos> selectSelfPOS(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfPOS(getSelfShopKey());
	}

	@Override
	public List<DgSettlementWayType> selectSelfJSFSLX(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfJSFSLX(selfShopKey);
	}

	@Override
	public List<DgSettlementWay> selectSelfJSFS(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfJSFS(selfShopKey);
	}

	@Override
	public List<DgProMethodsType> selectSelfZZFSLX(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfZZFSLX(selfShopKey);
	}

	@Override
	public List<DgProMethods> selectSelfZZFS(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfZZFS(selfShopKey);
	}

	@Override
	public List<DgFlavor> selectSelfKWYQ(String selfShopKey) {
		// TODO Auto-generated method stub
		return dataUploadMapper.selectSelfKWYQ(selfShopKey);
	}

    @Override
    public List<DgServing> selectSelfSCZT() {
        return dataUploadMapper.selectSelfSCZT(getSelfShopKey());
    }

	@Override
	public Map<String, Object> selectSelfZDYY() {
    	Map<String,Object> map = new HashMap<>();
	 	List<DgGiftForm> dgGiftForms = dataUploadMapper.selectSelfZDYY(getSelfShopKey());
		List<DgGiftFormReason> dgGiftFormslx = dataUploadMapper.selectSelfZDYYLX(getSelfShopKey());
		map.put("dgGiftForms",dgGiftForms);
		map.put("dgGiftFormslx",dgGiftFormslx);
		return map;
	}

	@Override
	public List<TbDep> selectSelfBMGL() {
		return dataUploadMapper.selectSelfBMGL(getSelfShopKey());
	}

	@Override
	public List<TbBis> selectSelfYYSB() {
		return dataUploadMapper.selectSelfYYSB(getSelfShopKey());
	}

	@Override
	public Map<String, Object> selectSelfTCYY() {
		Map<String,Object> map = new HashMap<>();
		List<TbRfc> rfcs = dataUploadMapper.selectSelfTCYY(getSelfShopKey());
		List<TbRfct> rfcts = dataUploadMapper.selectSelfTCYYLX(getSelfShopKey());
		map.put("rfcs",rfcs);
		map.put("rfcts",rfcts);
		return map;
	}

	@Override
	public List<TbZdbz> selectSelfZDBZ() {
		return dataUploadMapper.selectSelfZDBZ(getSelfShopKey());
	}

	@Override
	public List<DgPublicCode0> selectSelfGGDM() {
		return dataUploadMapper.selectSelfGGDM(getSelfShopKey());
	}

}
