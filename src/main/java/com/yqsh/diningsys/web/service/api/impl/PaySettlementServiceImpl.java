package com.yqsh.diningsys.web.service.api.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.model.DgBigItemTypeInfoList;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.SerialNumberUtil;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.des.ThreeDESUtil;
import com.yqsh.diningsys.core.util.des.YQSH_SHA1;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.dao.SysUserMapper;
import com.yqsh.diningsys.web.dao.api.*;
import com.yqsh.diningsys.web.dao.archive.DgPosMapper;
import com.yqsh.diningsys.web.dao.archive.TbBisMapper;
import com.yqsh.diningsys.web.dao.archive.TbOrgMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOwConsumerDetailsMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgWaterCouponMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.PLcswMerchantMapper;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.model.deskBusiness.enums.SerialRulEnum;
import com.yqsh.diningsys.web.model.pay.DgMerchants;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.PaySettlementService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.pay.DgMerchantsService;
import com.yqsh.diningsys.web.service.pay.DgPayInterface;
import com.yqsh.diningsys.web.service.sysSettings.DgUrlSettingService;
import com.yqsh.diningsys.web.util.OnlineHttp;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 前台结算
 *
 * @author zhshuo create on 2016-12-30 14:43
 */
@Service
@SuppressWarnings("all")
public class PaySettlementServiceImpl implements PaySettlementService {
	
    private static Logger logger = Logger.getLogger(PaySettlementServiceImpl.class);
    
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    @Autowired
    private APICheckService apiCheckService;

    @Resource
    private APICheckServiceMapper apiCheckServiceMapper;

    @Resource
    private PaySettlementMapper paySettlementMapper;

    @Autowired
    private DeskBusinessSettingService deskBusinessSettingService;

    @Resource
    private DgPosMapper dgPosMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private TbOrgMapper tbOrgMapper;

    @Resource
    private TbBisMapper tbBisMapper;

    @Autowired
    private DgPayInterface dgPayInterface;

    @Autowired
    private DgConsumerSeatService dgConsumerSeatService;

    @Autowired
    private DgUrlSettingService dgUrlSettingService;
    
    @Autowired
    private DgPreOrderbillMapper dgPreOrderbillMapper;
    
    @Autowired
    private DgOpenWaterMapper dgOpenWaterMapper;
    
    @Autowired
    private DgCallServiceMapper dgCallServiceMapper;

    @Autowired
    private DgOwConsumerDetailsMapper dgOwConsumerDetailsMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private DgWaterCouponMapper dgWaterCouponMapper;

    @Autowired
    private DgMerchantsService merchantsService;
    @Autowired
    private PLcswMerchantMapper pLcswMerchantMapper;
    @Override
    public Map modifyFastSettlement(String userCode, List<DgOpenWater> owNums, String clearingWayData,
                                    Integer payType, Double amountsReceivable, Integer pos,
                                    Double zeroMoney, Double fixedDiscount, String clearingNotes,
                                    String statementLabel, String invoicingData,
                                    Double generalProportions, Double singleProportions, Double paidMoney, Double changeAmount,
                                    Boolean isWechatPay, Boolean isAliPay, String payAuthCode, String payBody, String jsWater,
                                    Integer isGz, String couponData,String isNotice,HttpServletRequest request, HttpServletResponse response) {
        /*//2017年7月31日11:38:25  一进入结算方法，对正在买单的营业流水进行锁定
        List<DgOpenWater> dgOpenWaters = apiCheckServiceMapper.lockPayingWaters(owNums);
        //如果没有查询到数据，说明这些流水正在被结算，则返回
        if(dgOpenWaters.size() < 1){
            Map<String,Object> map = new HashMap<>();
            map.put("paying",true);
            return map;
        }*/

        try {
            Gson gson = new Gson();

            SysUser sysUser = sysUserMapper.selectByUsername(userCode);

            DgPos dgPos = new DgPos();
            dgPos.setId(pos);
            dgPos = dgPosMapper.getPosByID(dgPos);
            TbOrg org = new TbOrg();
            org.setId(Integer.parseInt(sysUser.getEmpOrganization()));
            org = tbOrgMapper.getOrgByID(org);

            //结算方式数据
            List<Map<String, Object>> clearingWayDataList = gson.fromJson(clearingWayData, new TypeToken<List<Map<String, Object>>>() {
            }.getType());

            List<String> jsList = null;
            String jsNum = null;
            if (StringUtils.isEmpty(jsWater)) {
                //结算流水号码
                jsList = gson.fromJson(deskBusinessSettingService
                                .createFlowNumber(org.getOrgCode(), dgPos.getNumber(), 1,
                                        SerialRulEnum.JS),
                        new TypeToken<List<String>>() {
                        }.getType());
                jsNum = jsList.get(0);
            }


            String res = null, hyzfId = null;

            for (Map<String, Object> clearingWayMap : clearingWayDataList) {
                if (clearingWayMap.get("payCode").toString().equals("HYZF") ||
                        clearingWayMap.get("payCode").toString().equals("WXHY") ||
                        clearingWayMap.get("payCode").toString().equals("HYMBZF")) {
                    int hyPayType = 1;
                    String des = dgUrlSettingService.selectByCode("member.des").getValue();
                    List<Map<String, Object>> list = new ArrayList<>();
                    String payCode = clearingWayMap.get("payCode").toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("payType", payCode.equals("HYZF") ? "0" : "1");
                    map.put("totolMoney", amountsReceivable.toString());
                    map.put("orderWater", jsNum);
                    map.put("payMoney", clearingWayMap.get("payMoney").toString());
                    map.put("consComId", dgUrlSettingService.selectByCode("member.comId").getValue());
                    map.put("operUser", userCode);
                    map.put("consPwd", clearingWayMap.get("consPwd"));
                    map.put("cid", clearingWayMap.get("cid") == null ? null : clearingWayMap.get("cid").toString());
                    hyzfId = clearingWayMap.get("vId") == null ? null : clearingWayMap.get("vId").toString();
                    map.put("vId", hyzfId);
                    if (clearingWayMap.containsKey("coupNo")) {
                        map.put("coupNo", clearingWayMap.get("coupNo") == null ? null : clearingWayMap.get("coupNo").toString());
                    }
                    if (clearingWayMap.containsKey("coupMoney")) {
                        map.put("coupMoney", clearingWayMap.get("coupMoney") == null ? null : clearingWayMap.get("coupMoney").toString());
                    }
                    if (payCode.equals("WXHY")) {
                        map.put("verificationCode", clearingWayMap.get("verificationCode"));
                        hyPayType = 2;
                    }
                    if (payCode.equals("HYMBZF")) {
                        map.put("mobile", clearingWayMap.get("mobile").toString()); //手机号码
                        map.put("verificationCode", clearingWayMap.get("verificationCode")); //验证码
                        hyPayType = 3;
                    }
                    list.add(map);

                    String applyId = dgUrlSettingService.selectByCode("member.applyId").getValue();
                    String sha = dgUrlSettingService.selectByCode("member.sha").getValue();
                    String encryptGeneral = ThreeDESUtil.encryptGeneral(new Gson().toJson(list), des);
                    String sha1 = YQSH_SHA1.getSHA1(encryptGeneral, sha);
                    if (hyPayType == 1) {
                        res = OkHttpUtils.memberPayMent(encryptGeneral, sha1, applyId);
                    } else if (hyPayType == 2) {
                        res = OkHttpUtils.memberWxPayMent(encryptGeneral, sha1, applyId);
                    } else {
                        res = OkHttpUtils.memberPayMentByMobile(encryptGeneral, sha1, applyId);
                    }

                    if (res == null) {
                        return null;
                    } else {
                        logger.error(res);
                        Map resMap = new Gson().fromJson(res, Map.class);
                        if (resMap.containsKey("msgCode")) {
                            if (!resMap.get("msgCode").equals("ok")) {
                                return resMap;
                            } else {
                                Map body = (Map) resMap.get("body");
                                clearingWayMap.put("memberConsId", body.get("consId"));
                            }
                        }
                    }
                }
            }

//            if(StringUtils.isEmpty(jsWater)){
                if (isWechatPay || isAliPay) {
                    //获取商户信息
                    DgMerchants merch = merchantsService.findOneMerch("1");
                    if (StringUtils.isEmpty(merch.getId())) {
                        return null;
                    }
                    for(Map way:clearingWayDataList) {
                        //微信支付以及支付宝支付
                        if(way.get("payCode").toString().equals("WECHAT") ||
                                way.get("payCode").toString().equals("ALIPAY")){
                            String codeType="";
                            if(way.get("payCode").toString().equals("WECHAT")){
                                codeType="WX";
                            } else {
                                codeType="ZFB";
                            }
                            Double codeMoney=Double.valueOf(way.get("payMoney").toString());
                            if (merch.getThreePartyPayment() == null) {
                                //支付宝/微信原生支付
                                Map<String, Object> stringObjectMap = dgPayInterface.dgPayemnt(codeMoney, "1", codeType, jsNum, userCode, payAuthCode, payBody, request, response);
                                if (!stringObjectMap.get("result").toString().equals("S001")) {
                                    stringObjectMap.put("jsNum", jsNum);
                                    return stringObjectMap;
                                }
                            } else if (merch.getThreePartyPayment().equals("sb")) {
                                //扫呗支付
                                int a = (int) (codeMoney * 100);
                                boolean paySuccess = dgPayInterface.slotCardPayNew(codeType, payAuthCode, a + "", payBody, SerialNumberUtil.cretatOrderNo(8), userCode, jsNum, request, response);
                                if (!paySuccess) {
                                    return null;//支付失败
                                }
                            }
                        }
                    }
                }
//            }

            //获取优惠券
            List<DgWaterCoupon> dgWaterCoupons = dgWaterCouponMapper.getCouponCountByWaters(owNums);
            //核销微信会员卡券
            if(!dgWaterCoupons.isEmpty()){
            	String consComId = CacheUtil.getURLInCache("member.comId");//消费店铺id
                String orderWater = StringUtils.isEmpty(jsWater) ? jsNum:jsWater;
                List<Map> couponInfos = new ArrayList<>();
                for(DgWaterCoupon dgWaterCoupon:dgWaterCoupons){
                    Map couponInfo = new HashMap();
                    String couponMoney = "0.0";
                    if(payType == 1){
                        couponMoney = dgWaterCoupon.getPxdzYhSutotal().toString();
                    } else if(payType == 2){
                        couponMoney = dgWaterCoupon.getZyhdYhSutotal().toString();
                    } else if(payType == 3){
                        couponMoney = dgWaterCoupon.getHyYhSutotal().toString();
                    }
                    couponInfo.put("couponVal",dgWaterCoupon.getCouponval());
                    couponInfo.put("couponMoney",couponMoney);
                    couponInfos.add(couponInfo);
                }
            	OkHttpUtils.writeOffWxCouponInfo(JSON.toJSONString(couponInfos),orderWater,consComId);
            }

            //插入结算流水并获取插入流水的ID
            Map<String, Object> clearingParam = new HashMap<>();
            if(StringUtils.isEmpty(jsWater)){
                clearingParam.put("cwNum", jsNum);
            }else{
                clearingParam.put("cwNum", jsWater);
            }
            clearingParam.put("conAmount", amountsReceivable);
            clearingParam.put("zeroAmount", zeroMoney);
            clearingParam.put("fixedDiscount", fixedDiscount);
            clearingParam.put("amountsReceivable", amountsReceivable);
            clearingParam.put("paidAmount", paidMoney);
            clearingParam.put("changeAmount", changeAmount);
            clearingParam.put("clearingTime", new Date());
            clearingParam.put("clearingBis", getMealInt());
            clearingParam.put("clearingOperator", userCode);
            clearingParam.put("clearingPos", pos);
            clearingParam.put("zeroSettlement", 1);
            clearingParam.put("clearingState", 2);
            clearingParam.put("clearingNotes", clearingNotes);
            clearingParam.put("statementLabel", statementLabel);
            clearingParam.put("generalProportions", generalProportions);
            clearingParam.put("singleProportions", singleProportions);
            clearingParam.put("hyzfId", hyzfId);
            clearingParam.put("payType", payType);
            
            Double beforeDiscountsAmount = 0.0;
            for(DgOpenWater dow:owNums){
            	beforeDiscountsAmount = MathExtend.add(beforeDiscountsAmount,calItemStardPriceTotal(dow.getItemFileInfos()));  //new BigDecimal(dow.getItemCostsSum())
            }
            clearingParam.put("beforeDiscountsAmount",beforeDiscountsAmount.doubleValue());
            clearingParam.put("totalDiscountAmount",MathExtend.subtract(beforeDiscountsAmount,amountsReceivable));

            Integer clearingId = insertClearingWaterData(clearingParam);

            //修改微信优惠券状态
            if(!dgWaterCoupons.isEmpty()){
                for(DgWaterCoupon dgWaterCoupon:dgWaterCoupons){
                    dgWaterCoupon.setState(1);
                    dgWaterCoupon.setCwid(clearingId);
                    if(payType == 1){
                        dgWaterCoupon.setYhmoney(dgWaterCoupon.getPxdzYhSutotal());
                    } else if(payType == 2){
                        dgWaterCoupon.setYhmoney(dgWaterCoupon.getZyhdYhSutotal());
                    } else if(payType == 3){
                        dgWaterCoupon.setYhmoney(dgWaterCoupon.getHyYhSutotal());
                    }
                    dgWaterCouponMapper.updateByPrimaryKey(dgWaterCoupon);
                }
            }

            if(payType != 3){
                //插入优惠数据
                if(!StringUtils.isEmpty(singleProportions) || !StringUtils.isEmpty(generalProportions)){
                    insertDiscountData(clearingId,userCode,singleProportions,generalProportions);
                }
            }

            //插入支付方式数据
            insertClearWayData(clearingId, clearingWayDataList);

            //插入发票数据
            insertInvoice(clearingId, invoicingData);

            //修改每一个结算营业流水下面的所有的品项的最终结算价格，修改每一个结算的营业流水的状态
            //修改客座状态，修改营业流水的结算ID
            modifyOpenWaterAndDetails(owNums, clearingId, payType,1,userCode,pos,generalProportions,singleProportions);

            String IS_UPDATE_CXPT = CacheUtil.getURLInCache("IS_UPDATE_CXPT");
            if(StringUtil.isNotEmpty(IS_UPDATE_CXPT) && IS_UPDATE_CXPT.equals("1")) {
                //插入数据到call表,提醒创享平台清理台位
                insertCxptCallServe(owNums);
            }

            if(StringUtil.isNotEmpty(isNotice) && isNotice.equals("1")){
                insertNoticeQt(clearingParam.get("cwNum").toString(),clearingId,owNums,sysUser.getEmpCode()+"-"+sysUser.getEmpName());
            }
            clearingParam.put("clearingWaterId",clearingId);

            return clearingParam;
        }  catch (Exception e) {
            throw new RuntimeException("modifyFastSettlement exception",e);
        }
    }

    void insertCxptCallServe(List<DgOpenWater> owNums){
        List<DgConsumerSeat> dgConsumerSeats = dgConsumerSeatService.selectByIds(owNums);
        if(dgConsumerSeats.size() > 0){
            StringBuffer seatIds = new StringBuffer();
            for(DgConsumerSeat dcs:dgConsumerSeats){
                seatIds.append(dcs.getUuidKey()).append(",");
            }
            seatIds.deleteCharAt(seatIds.length()-1);
            DgCallService dgCallService = new DgCallService();
            dgCallService.setType(11); //11为提醒平台清理客位
            dgCallService.setContent(seatIds.toString());
            dgCallService.setState(1);
            dgCallServiceMapper.insert(dgCallService);
        }
    }

    void insertNoticeQt(String jsNum,Integer clearingId,List<DgOpenWater> owNums,String operCode){
        //插入支付成功日志
        DgCallService dgCallService = new DgCallService();
        StringBuffer sb = new StringBuffer();
        for(DgOpenWater _symbol : owNums){
            sb.append(_symbol.getSeatName()).append("',");
        }
        dgCallService.setContent("结算pos支付成功,买单客位:"+sb.toString()+"结算流水:"+jsNum+",买单人:"+operCode+",序号为:"+clearingId);
        dgCallService.setState(1); // 初始化状态
        dgCallService.setSeatId(owNums.get(0).getSeatId());
        dgCallService.setOwNum(owNums.get(0).getOwNum());
        dgCallService.setType(2);
        dgCallServiceMapper.insert(dgCallService);
    }

    void insertDiscountData(Integer cwId,String userCode,Double singleProportions, Double generalProportions) {
        Map<String,Object> map = new HashMap<>();
        map.put("cwId",cwId);
        map.put("userCode",userCode);
        map.put("userName",apiCheckServiceMapper.selectUserName(userCode));
        String discountData = "";
        if(singleProportions != null){
            discountData += "全单优惠比例：" + singleProportions +"%，";
        }
        if(generalProportions != null){
            discountData += "常规优惠比例：" + generalProportions +"%";
        }

        map.put("discountData",discountData);

        if(discountData.endsWith("，")){
            map.put("discountData",discountData.substring(0,discountData.lastIndexOf("，")));
        }

        apiCheckServiceMapper.insertDiscountData(map);
    }

    Double calItemStardPriceTotal(List<DgOwConsumerDetails> dods){
        Double subTotal= 0.0;
        for(DgOwConsumerDetails docd:dods){
            subTotal=MathExtend.add(subTotal,MathExtend.multiply(docd.getStandardPrice(),docd.getItemFileNumber()));
        }
        return subTotal;
    }

    @Override
    public Map modifyAdvancePay(String userCode, List<DgOpenWater> dgOpenWaters, Integer payType, Integer pos,String clearingWayData,
                                Double fixedDiscount, String invoicingData,Integer generalProportions,Integer singleProportions,Integer cwId) {
        Map<String, Object> clearingParam = new HashMap<>();
        Integer clearingId = null;
        clearingParam.put("fixedDiscount", fixedDiscount);
        clearingParam.put("clearingState", 1);
        clearingParam.put("generalProportions", generalProportions);
        clearingParam.put("singleProportions", singleProportions);
        clearingParam.put("payType", payType);
        if(cwId != null){
            clearingParam.put("id", cwId);
            clearingId = cwId;
            apiCheckServiceMapper.updateAdvancePayClearingWaterDataById(clearingParam);
        }else{
            //插入埋单的基本数据，创建基本的结算流水ID，插入预定的优惠信息已经定额优惠数据
            clearingId = insertAdvancePayClearingWaterData(clearingParam);
        }

        //重新保存预定的支付方式数据
        List<Map> maps = paySettlementMapper.selectPayWayByCwId(clearingId);
        if(maps.size() > 0){
            paySettlementMapper.deletePayWayInfoByCwId(cwId);
        }

        if(clearingWayData != null){
            //结算方式数据
            List<Map<String, Object>> clearingWayDataList = new Gson().fromJson(clearingWayData, new TypeToken<List<Map<String, Object>>>() {
            }.getType());

            if(clearingWayDataList.size() > 0){
                //插入支付方式数据
                insertClearWayData(clearingId, clearingWayDataList);
            }
        }

        insertInvoice(clearingId, invoicingData);

        //修改每一个结算营业流水下面的所有的品项的最终结算价格，修改每一个结算的营业流水的状态
        //修改客座状态，修改营业流水的结算ID，修改每个品项埋单的价格以及折扣
        modifyOpenWaterAndDetails(dgOpenWaters, clearingId, payType,2,userCode,pos,
        		generalProportions==null?null:Double.valueOf(generalProportions),singleProportions==null?null:Double.valueOf(singleProportions));

        return clearingParam;
    }

    @Override
    public void insertInvoice(Integer clearingId, String invoiceData) {
        if(!StringUtils.isEmpty(invoiceData)){
            List<Map> maps = new Gson().fromJson(invoiceData, new TypeToken<List<Map>>() {
            }.getType());
            if (maps.size() > 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("list", maps);
                map.put("cwId", clearingId);
                paySettlementMapper.insertInvoicing(map);
            }
        }
    }

    @Override
    public void modifyOpenWaterAndDetails(List<DgOpenWater> dgOpenWaters, Integer clearingId, Integer payType,
    		Integer operaType,String userCode,Integer pos,Double generalProportions,Double singleProportions) {
        for (DgOpenWater dgOpenWater : dgOpenWaters) {
            Map<String, Object> map = new HashMap<>();
            map.put("owNum", dgOpenWater.getOwNum());
            map.put("cwId", clearingId);
            map.put("openWater", dgOpenWater);
            //map.put("finalItemCostSum",dgOpenWater.getItemCostsSum());
            map.put("finalItemCostSum",dgOpenWater.getHydzItemSubtotal()!=0.0?dgOpenWater.getHydzItemSubtotal():dgOpenWater.getItemCostsSum());
            map.put("privareRoomCosts",dgOpenWater.getPrivateRoomCost());
            map.put("serviceCharge",dgOpenWater.getServiceCharge());
            map.put("minimumConsumption",dgOpenWater.getMinimumConsumption());
            //map.put("discountCosts",dgOpenWater.getItemCostsSum() - dgOpenWater.getPxdzItemSubtotal());
            map.put("discountCosts",dgOpenWater.getItemCostsSum() - (dgOpenWater.getHydzItemSubtotal()!=0.0?dgOpenWater.getHydzItemSubtotal():dgOpenWater.getPxdzItemSubtotal()));
            map.put("minimumChargeComplete",dgOpenWater.getZyhdZdxfbq());
            if(operaType == 1){
                if (dgOpenWater.getTransferTarget() != null) {
                    map.put("owState", 6);
                } else {
                    map.put("owState", 2);
                }
                modifyOpenWaterStateAndCwId(map);
                List<DgOwConsumerDetails> itemFileInfos = dgOpenWater.getItemFileInfos();

                /*List<DgOwConsumerDetails> backBillDetail = apiCheckService.selectBackBillDetailInfoByAddBillInfo(itemFileInfos);

                if(backBillDetail.size() > 0){
                    itemFileInfos.addAll(backBillDetail);
                }*/

                for (DgOwConsumerDetails dgOwConsumerDetails : itemFileInfos) {
                    modifyItemFile(dgOwConsumerDetails, payType,dgOpenWater.getId(),generalProportions,singleProportions);
                }
            }else if(operaType == 2){
                if (dgOpenWater.getTransferTarget() != null) {
                    map.put("owState", 5);
                } else {
                    map.put("owState", 3);
                }
                modifyOpenWaterAdvancePayInfo(map);

                if(dgOpenWater.getTransferTarget() == null){
                    modifySeatState(dgOpenWater.getSeatId(),5);
            	} else {
            		DgOpenWater transWater = dgOpenWaterMapper.selectByOpenWaterByNum(dgOpenWater.getTransferTarget()) ;
            		if(transWater.getSeatId() == dgOpenWater.getSeatId()) {
                        modifySeatState(dgOpenWater.getSeatId(),5);
            		}
            	}
                //埋单马上修改客座的状态为埋单状态
//                modifySeatState(dgOpenWater.getSeatId(),5);

                //埋单最后插入埋单的日志记录
                Map<String,Object> lockParam = new HashMap<>();
                lockParam.put("owNum",dgOpenWater.getOwNum());
                lockParam.put("state",3);
                lockParam.put("userCode",userCode);
                lockParam.put("pos",pos);
                insertLockInfo(lockParam);
            }

            List<Map> maps = paySettlementMapper.selectSeatOpenWater(dgOpenWater.getSeatId());
            if (maps.size() < 1) {
                modifySeatState(dgOpenWater.getSeatId(),operaType);
            	/*if(dgOpenWater.getTransferTarget() == null){
                    modifySeatState(dgOpenWater.getSeatId(),operaType);
            	} else {
            		DgOpenWater transWater = dgOpenWaterMapper.selectByOpenWaterByNum(dgOpenWater.getTransferTarget()) ;
            		if(transWater.getSeatId().equals(dgOpenWater.getSeatId())) {
            			 modifySeatState(dgOpenWater.getSeatId(),operaType);
            		}
            	}*/
            }

            dgPreOrderbillMapper.deleteByWaterId(dgOpenWater.getId());
        }
    }

    @Override
    public void modifyOpenWaterAdvancePayInfo(Map<String, Object> map) {
        paySettlementMapper.modifyOpenWaterAdvancePayInfo(map);
    }

    @Override
    public void insertLockInfo(Map<String, Object> param) {
        apiCheckServiceMapper.insertLockLog(param);
    }

    @Override
    public void cancelAdvancePay(List<DgOpenWater> dgOpenWaterNum,DgOpenWater dgOpenWater) {//撤销埋单修改为撤销全部营业流水

        //团队单号
        String teamMembers = dgOpenWater.getTeamMembers();

        //埋单的结算流水号
        Integer cwId = dgOpenWater.getClearingWaterId();

        //检测每一个营业流水的桌子是否存在其他非团队营业流水
        List<Integer> integers = paySettlementMapper.selectDistinctSeatId(dgOpenWaterNum);

        for(Integer seatId:integers){
            Map<String,Object> seatMap = new HashMap<>();
            seatMap.put("id",seatId);
            seatMap.put("state",2);
            seatMap.put("teamNum",teamMembers);

            List<DgOpenWater> dgOpenWaters = apiCheckService.selectOpenWaterObjBySeatIdAndTeamNum(seatMap);

            if(!checkOpenWaterAdvancePay(dgOpenWaters)){
                paySettlementMapper.modifySeatStateForMD(seatMap);
                DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(dgOpenWater.getSeatId());
                OnlineHttp.onlineSeatModify(seat.getUuidKey(), 2+"");
            }
        }

        Map<String,Object> map = new HashMap<>();
        for(DgOpenWater openWater:dgOpenWaterNum){
            map.put("owNum",openWater.getOwNum());
            //重置营业流水埋单相关埋单数据以及营业流水状态
            if(openWater.getTransferTarget() != null){
                map.put("state",4);
            }else{
                map.put("state",1);
            }
            //修改埋单时可能修改的服务费、包房费信息
            paySettlementMapper.modifyOpenWAterCancelAdvancePay(map);
        }

        //当有一个营业流水撤销埋单，删除支付信息
        paySettlementMapper.deletePayWayInfoByCwId(cwId);
        //清除定额优惠、比例优惠等信息
        paySettlementMapper.updateProportionByCwId(cwId);
        //清除发票信息
        paySettlementMapper.deleteInvoicingByCwId(cwId);

        //当撤销的营业流水的所属结算流水下没有营业流水，将该结算流水作废
        map.put("cwId",cwId);
        List<DgOpenWater> dgOpenWaters = paySettlementMapper.selectOpenWaterByCwId(map);
        if(dgOpenWaters.size() < 1){
//            paySettlementMapper.modifyClearingWaterState(map);
            //2017年6月7日09:30:46  撤销埋单后,如果该结算流水下没有其他
            paySettlementMapper.delTrashClearingWater(map);
        }
    }

    @Override
    public List<DgOpenWater> printGuestBillingData(List<DgOpenWater> dgOpenWaters) {
        List<DgItemFileType> dgItemTypes = paySettlementMapper.selectAllItemBigType();

        for(DgOpenWater dgOpenWater:dgOpenWaters){
            List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterWithService(dgOpenWater.getOwNum());
            List<DgBigItemTypeInfoList> infoLists = new ArrayList<>();
            for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                Integer pxdlId = dgOwConsumerDetails.getPxdlId();
                String pxdlName = checkPxdl(dgItemTypes, pxdlId);
                DgBigItemTypeInfoList cheRes = checkListContains(infoLists, pxdlName);
                if(cheRes != null){
                    cheRes.setItemCostsSum(MathExtend.add(cheRes.getItemCostsSum(),dgOwConsumerDetails.getSubtotal()));
                    List<DgOwConsumerDetails> allItems = cheRes.getAllItems();
                    allItems.add(dgOwConsumerDetails);
                    cheRes.setAllItems(allItems);
                    setList(infoLists,cheRes);
                }else{
                    DgBigItemTypeInfoList dgBigItemTypeInfoList = new DgBigItemTypeInfoList();
                    dgBigItemTypeInfoList.setBigItemTypeName(pxdlName);
                    dgBigItemTypeInfoList.setItemCostsSum(dgOwConsumerDetails.getSubtotal());
                    List<DgOwConsumerDetails> list = new ArrayList<DgOwConsumerDetails>();
                    list.add(dgOwConsumerDetails);
                    dgBigItemTypeInfoList.setAllItems(list);
                    infoLists.add(dgBigItemTypeInfoList);
                }
            }
            dgOpenWater.setItemFileByBigTypes(infoLists);
        }

        return dgOpenWaters;
    }

    @Override
    public Map<String, Object> selectAllAdvancePayInfo(Integer clearingId) {
        Map<String,Object> map = new HashMap<>();
        DgReceptionClearingWater dgReceptionClearingWater = paySettlementMapper.selectClearingWaterById(clearingId);
        List<Map> maps = paySettlementMapper.selectPayWayByCwId(clearingId);
        List<DgOwReceipt> dgOwReceipts = paySettlementMapper.selectInvoicingByCwId(clearingId);
        map.put("clearingData",dgReceptionClearingWater);
        map.put("allAdvancePayWay",maps);
        map.put("allReceipt",dgOwReceipts);
        return map;
    }

    @Override
    public DgReceptionClearingWater selectClearingWaterById(int clearing_water_id) {
        return paySettlementMapper.selectClearingWaterById(clearing_water_id);
    }

    DgBigItemTypeInfoList checkListContains(List<DgBigItemTypeInfoList> infoLists,String key){
        for(DgBigItemTypeInfoList dgBigItemTypeInfoList:infoLists){
            if(dgBigItemTypeInfoList.getBigItemTypeName().equals(key)) return dgBigItemTypeInfoList;
        }
        return null;
    }

    void setList(List<DgBigItemTypeInfoList> infoLists,DgBigItemTypeInfoList dgBigItemTypeInfoList){
        for(DgBigItemTypeInfoList obj:infoLists){
            if(dgBigItemTypeInfoList.getBigItemTypeName().equals(dgBigItemTypeInfoList.getBigItemTypeName())){
                obj = dgBigItemTypeInfoList;
            }
        }
    }

    String checkPxdl(List<DgItemFileType> dgItemTypes, Integer pxdlId){

        //dgItemTypes.stream().filter(name -> name.getId().equals(pxdlId)).map(DgItemFileType::getName);

        for(DgItemFileType dgItemType:dgItemTypes){
            if(dgItemType.getId().equals(pxdlId)){
                return dgItemType.getName();
            }
        }
        return null;
    }

    @Override
    public Integer insertClearingWaterData(Map<String, Object> param) {
        paySettlementMapper.insertClearingWater(param);
        Integer clearingId = Integer.parseInt(param.get("id").toString());
        return clearingId;
    }

    @Override
    public void modifyItemFile(DgOwConsumerDetails dgOwConsumerDetails, Integer payType,Integer owId,Double generalProportions,Double singleProportions) {
        Map<String, Object> map = new HashMap();
        if (payType.equals(1)) { //最终价格为品项打折价格
            if(generalProportions == null && singleProportions == null){
                map.put("itemFinalPrice", dgOwConsumerDetails.getPxdzItemFilePrice());
            } else {
                map.put("itemFinalPrice", dgOwConsumerDetails.getPxdzItemFilePriceDiscount() == null?
                        dgOwConsumerDetails.getPxdzItemFilePrice():dgOwConsumerDetails.getPxdzItemFilePriceDiscount());
            }
        } else if (payType.equals(2)) {//最终价格为重要活动
            if(generalProportions == null && singleProportions == null){
                map.put("itemFinalPrice", dgOwConsumerDetails.getZyhdItemFilePrice());
            } else {
                map.put("itemFinalPrice", dgOwConsumerDetails.getZyhdItemFilePriceDiscount() == null?
                        dgOwConsumerDetails.getZyhdItemFilePrice():dgOwConsumerDetails.getZyhdItemFilePriceDiscount());
            }
        } else if (payType.equals(3)) {//最终价格
//            map.put("itemFinalPrice", dgOwConsumerDetails.getHydzItemFilePrice());
            if(generalProportions == null && singleProportions == null){
                //map.put("itemFinalPrice", dgOwConsumerDetails.getPxdzItemFilePrice());
                map.put("itemFinalPrice", dgOwConsumerDetails.getHydzItemFilePrice()!=0.0?dgOwConsumerDetails.getHydzItemFilePrice():dgOwConsumerDetails.getPxdzItemFilePrice());
            } else {
                map.put("itemFinalPrice", dgOwConsumerDetails.getPxdzItemFilePriceDiscount() == null?
                        dgOwConsumerDetails.getPxdzItemFilePrice():dgOwConsumerDetails.getPxdzItemFilePriceDiscount());
            }
        }


        //品项的优惠金额
        map.put("discountMoney",new BigDecimal(dgOwConsumerDetails.getItemFinalPrice()).subtract(new BigDecimal(map.get("itemFinalPrice")+"")));
        map.put("itemId", dgOwConsumerDetails.getItemFileId());
        map.put("serviceId", dgOwConsumerDetails.getOwId());
        if(dgOwConsumerDetails.getSubNumber() == null){
            dgOwConsumerDetails.setSubNumber(0.0);
        }
        map.put("itemFileNumber",new BigDecimal(-dgOwConsumerDetails.getSubNumber()).add(new BigDecimal(dgOwConsumerDetails.getItemFileNumber())).doubleValue());
        map.put("discount", dgOwConsumerDetails.getDiscount());

        //"subtotal" create by xiewei 20181120
        BigDecimal itemFinalPrice = new BigDecimal(map.get("itemFinalPrice")+"");
        BigDecimal itemFileNumber = new BigDecimal(dgOwConsumerDetails.getItemFileNumber());
        map.put("subtotal", itemFinalPrice.multiply(itemFileNumber));

        //优惠数量,方便反位
        map.put("couponNum",dgOwConsumerDetails.getCouponNum());

        if(StringUtil.isNotEmpty(dgOwConsumerDetails.getCouponVal())){
            //新增券打折品项
            Map serviceMap = new HashMap();
            serviceMap.put("owId", owId);
            serviceMap.put("waiterId", 1);
            serviceMap.put("serviceTime", new Date());
            serviceMap.put("serviceType", 2);
            serviceMap.put("serviceNum", System.currentTimeMillis()+"");
            serviceMap.put("zdbz", "优惠券打折品项");
            billMapper.insertOwServiceWater(serviceMap);
            dgOwConsumerDetails.setItemPayMoney((Double)map.get("itemFinalPrice"));
            dgOwConsumerDetails.setSettlementStatus(1);
            dgOwConsumerDetails.setOwId(Integer.valueOf(serviceMap.get("id").toString()));
            dgOwConsumerDetailsMapper.insert(dgOwConsumerDetails);
        } else {
            paySettlementMapper.modifyItemFileAdd(map);
            paySettlementMapper.modifyItemFileSub(map);
        }
    }

    @Override
    public Integer insertAdvancePayClearingWaterData(Map<String, Object> param) {
        paySettlementMapper.insertAdvacePayClearingWater(param);
        Integer clearingId = Integer.parseInt(param.get("id").toString());
        return clearingId;
    }

    @Override
    public void insertClearWayData(Integer clearingId, List<Map<String, Object>> clearingWayDataList) {
        Date clearingData = new Date();

//        clearingWayDataList.forEach((name)-> name.put("cwTime", clearingData));

        for (Map<String, Object> stringObjectMap : clearingWayDataList) {
            stringObjectMap.put("cwTime", clearingData);
        }

        Map<String, Object> map = new HashMap();
        map.put("clearingId", clearingId);
        map.put("list", clearingWayDataList);
        paySettlementMapper.insertClearingWayData(map);
    }

    @Override
    public void modifyOpenWaterStateAndCwId(Map<String, Object> param) {
        paySettlementMapper.modifyOpenWaterState(param);
    }

    @Override
    public void modifySeatState(Integer id,Integer operaType) {
        Map<String,Object> map = new HashMap();
        map.put("id",id);
        map.put("state",operaType==1?1:5);
        if(operaType == 1){
            DgConsumerSeat dgConsumerSeat = dgConsumerSeatService.selectByPrimaryKey(id);
            if(dgConsumerSeat.getDefaultState() == 1){
                map.put("state",1);
            }else{
                map.put("state",3);
            }
            paySettlementMapper.modifySeatState(map);
            
            if(CacheUtil.getURLInCache("ONLINE_ORDER") != null && "1".equals(CacheUtil.getURLInCache("ONLINE_ORDER"))){
    			//插入清理缓存 数据
    			DgCallService dgCallService = new DgCallService();
    			dgCallService.setContent("清理缓存数据");
    			dgCallService.setState(1); // 初始化状态
    			dgCallService.setSeatId(dgConsumerSeat.getId());
    			dgCallService.setOwNum(dgConsumerSeat.getUuidKey()); //这里存入客位uuid方便上传
    			dgCallService.setType(4);
    			dgCallServiceMapper.insert(dgCallService);
            }
        }else{
            paySettlementMapper.modifySeatStateForMD(map);
        }

        DgConsumerSeat seat =  dgConsumerSeatService.selectByPrimaryKey(id);
        
        OnlineHttp.onlineSeatModify(seat.getUuidKey(), map.get("state").toString());    
        
        
    }

    Boolean checkOpenWaterAdvancePay(List<DgOpenWater> dgOpenWaters){
        for(DgOpenWater dgOpenWater:dgOpenWaters){
            if(dgOpenWater.getOwState() == 3 || dgOpenWater.getOwState() == 5){
                return true;
            }
        }
        return false;
    }

    /**
     * 会员支付方法
     */
    void sendHyPayData(String payCode ){
        /*Map<String,Object> map = new HashMap<>();
        map.put("payType",);
        map.put("orderWater",);
        map.put("payMoney",);
        map.put("consComId",);
        map.put("operUser",);
        map.put("consPwd",);*/
    }

    Integer getMealInt() {
        List<TbBis> tbBiss = tbBisMapper.selectAllBis();
        List<Map<String, Object>> timeD = new ArrayList<Map<String, Object>>(); // 获取时间断
        if (tbBiss.size() == 0) {
            return -1;
        }
        for (int i = 0; i < tbBiss.size(); i++) {
            if (i != tbBiss.size() - 1) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("startTime", tbBiss.get(i).getBisTime());
                m.put("endTime", tbBiss.get(i + 1).getBisTime());
                m.put("nowTime", format.format(new Date()));
                m.put("TbBis", tbBiss.get(i));
                timeD.add(m);
            } else {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put("startTime", tbBiss.get(i).getBisTime());
                m.put("endTime", tbBiss.get(0).getBisTime());
                m.put("nowTime", format.format(new Date()));
                m.put("TbBis", tbBiss.get(0));
                timeD.add(m);
            }
        }
        Integer TbisId = null; // 获取市别id
        for (int i = 0; i < timeD.size(); i++) {
            int count = tbBisMapper.calculateSJD(timeD.get(i));
            if (count > 0) {
                TbisId = ((TbBis) timeD.get(i).get("TbBis")).getId();
                break;
            }
        }
        if (TbisId == null) // 没有就是最后个时段
        {
            TbisId = ((TbBis) timeD.get(timeD.size() - 1).get("TbBis"))
                    .getId();
        }
        return TbisId;
    }

}
