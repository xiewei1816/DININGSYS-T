package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.web.dao.deskBusiness.DgForMealTimePriceMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgForMealTimePriceSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgGateItemAllowCustomMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgImportantAcitivityDiscountSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemCurrentPriceMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemCustomMoneyMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDiscountMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemDiscountProgrammeSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemForWeekMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemGiftPlanMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemGiftPlanSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemMemberDiscountSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemOutofStockMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemPriceLadderMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemProDepMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemProDepSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemSaleLimitSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgItemTypeDiscountMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgNewestItemMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgPlacePriceMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgPlacePriceSMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgPromotionItemMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgRecommendItemMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.DgSpecialItemMapper;
import com.yqsh.diningsys.web.service.deskBusiness.DgJointDeleteService;
@Service
public class DgJointDeleteServiceImpl implements DgJointDeleteService{
	@Autowired
	private DgForMealTimePriceMapper dgForMealTimePriceMapper;
	@Autowired
	private DgForMealTimePriceSMapper  dgForMealTimePriceSMapper;
	@Autowired
	private DgGateItemAllowCustomMapper dgGateItemAllowCustomMapper;
	@Autowired
	private DgImportantAcitivityDiscountSMapper dgImportantAcitivityDiscountSMapper;
	@Autowired
	private DgItemCurrentPriceMapper dgItemCurrentPriceMapper;
	@Autowired
	private DgItemCustomMoneyMapper dgItemCustomMoneyMapper;
	@Autowired
	private DgItemDiscountProgrammeSMapper dgItemDiscountProgrammeSMapper;
	@Autowired
	private DgItemDiscountMapper dgItemDiscountMapper;
	@Autowired 
	private DgItemForWeekMapper dgItemForWeekMapper;
	@Autowired 
	private DgItemGiftPlanMapper dgItemGiftPlanMapper;
	@Autowired 
	private DgItemGiftPlanSMapper dgItemGiftPlanSMapper;
	@Autowired 
	private DgItemMemberDiscountSMapper dgItemMemberDiscountSMapper;
	@Autowired 
	private DgItemOutofStockMapper dgItemOutofStockMapper;
	@Autowired 
	private DgItemPriceLadderMapper dgItemPriceLadderMapper;
	@Autowired 
	private DgItemProDepMapper dgItemProDepMapper;
	@Autowired 
	private DgItemProDepSMapper dgItemProDepSMapper;
	@Autowired 
	private DgItemSaleLimitSMapper dgItemSaleLimitSMapper;
	@Autowired 
	private DgItemTypeDiscountMapper dgItemTypeDiscountMapper;
	@Autowired 
	private DgNewestItemMapper dgNewestItemMapper;
	@Autowired 
	private DgPlacePriceMapper dgPlacePriceMapper;
	@Autowired 
	private DgPlacePriceSMapper dgPlacePriceSMapper;
	@Autowired 
	private DgPromotionItemMapper dgPromotionItemMapper;
	@Autowired 
	private DgRecommendItemMapper dgRecommendItemMapper;
	@Autowired 
	private DgSpecialItemMapper dgSpecialItemMapper;
	@Override
	public void deleteJointItem(Integer id) {
		dgForMealTimePriceMapper.deleteByItemId(id);
		dgForMealTimePriceSMapper.deleteByItemId(id);
		dgItemCurrentPriceMapper.deleteByItemId(id);
		dgItemCustomMoneyMapper.deleteByItemId(id);
		dgItemForWeekMapper.deleteByItemId(id);
		dgItemGiftPlanMapper.deleteByItemId(id);
		dgItemGiftPlanSMapper.deleteByItemId(id);
		dgItemMemberDiscountSMapper.deleteByItemId(id);
		dgItemOutofStockMapper.deleteByItemId(id);
		dgItemPriceLadderMapper.deleteByItemId(id);
		dgItemProDepMapper.deleteByItemId(id);
		dgItemSaleLimitSMapper.deleteByItemId(id);
		dgNewestItemMapper.deleteByItemId(id);
		dgPlacePriceMapper.deleteByItemId(id);
		dgPromotionItemMapper.deleteByItemId(id);
		dgRecommendItemMapper.deleteByItemId(id);
		dgSpecialItemMapper.deleteByItemId(id);
		Map<String,Object> o = new HashMap<String, Object>();
		o.put("id",id);
		o.put("type",1);
		dgItemDiscountProgrammeSMapper.deleteByItemId(o);
	}

	@Override
	public void deleteJointGate(Integer id) {
		dgGateItemAllowCustomMapper.deleteByGateId(id);
		dgImportantAcitivityDiscountSMapper.deleteByGateId(id);
		dgItemTypeDiscountMapper.deleteByGateItemId(id);
		Map<String,Object> o = new HashMap<String, Object>();
		o.put("id",id);
		o.put("type",2);
		dgItemDiscountProgrammeSMapper.deleteByItemId(o);
	}

}