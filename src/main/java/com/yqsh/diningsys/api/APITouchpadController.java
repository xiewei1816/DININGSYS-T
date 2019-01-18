package com.yqsh.diningsys.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wordnik.swagger.annotations.ApiParam;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPos;
import com.yqsh.diningsys.web.model.archive.DgSettlementWay;
import com.yqsh.diningsys.web.model.deskBusiness.DBSBillServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DBSSeetServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgReceptionClearingWater;
import com.yqsh.diningsys.web.model.permission.SysPerDiscount;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.api.APIMainService;
import com.yqsh.diningsys.web.service.api.PaySettlementService;
import com.yqsh.diningsys.web.service.archive.DgConsumerSeatService;
import com.yqsh.diningsys.web.service.archive.DgPosService;
import com.yqsh.diningsys.web.service.businessMan.DgSeatManagerService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.service.permission.BusinessPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2017-04-17 10:14
 * 触摸板设置所需的接口
 * @author zhshuo
 */
@RequestMapping("/yqshapi/touchpad")
@Controller
@SuppressWarnings("all")
public class APITouchpadController extends ApiBaseController {

    @Autowired
    private APICheckService apiCheckService;

    @Autowired
    private DgSeatManagerService dgSeatManagerService;

    @Autowired
    private APIMainService aPIMainService;

    @Autowired
    private PaySettlementService paySettlementService;

    @Autowired
    private DgPosService dgPosService;

    @Autowired
    private BusinessPermissionService businessPermissionService;

    @Autowired
    private DeskBusinessSettingService deskBusinessSettingService;

    @Autowired
    private DgConsumerSeatService dgConsumerSeatService;

    @ResponseBody
    @RequestMapping("/openWaterInfo")
    public Object openWaterInfo(String owNum,String userCode,Integer posId){
        {
            try {
                Map<String,Object> resMap = new HashMap(); //返回map

                //营业流水基本信息以及状态判断
                Map map = apiCheckService.selectOpenWaterByOwNum(owNum);
                if(map == null){
                    return getResult(APIEnumDefine.M0101001);
                }

                Integer owState = Integer.parseInt(map.get("ow_state").toString());

                //传入的营业流水状态为空
                if(owState == null){
                    return getResult(APIEnumDefine.M0201001);
                }

                //END

                //比例优惠、全单优惠
                Double generalProportions = null,singleProportions = null;

                //如果营业流水存在结算流水ID，得到埋单时可能存入的比例优惠以及全单优惠
                DgReceptionClearingWater clearingWater = null;
                if(map.containsKey("clearing_water_id")){
                    clearingWater = paySettlementService.selectClearingWaterById(Integer.parseInt(map.get("clearing_water_id").toString()));
                    if(clearingWater.getGeneralProportions() != null){
                        generalProportions = clearingWater.getGeneralProportions();
                    }
                    if(clearingWater.getSingleProportions() != null){
                        singleProportions = clearingWater.getSingleProportions();
                    }
                }

                //营业流水座位
                DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(map.get("seat_id").toString()));

                //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
                List<Map> maps = apiCheckService.selectIsZyhd();

                DgOpenWater dgOpenWater = apiCheckService.selectSingleOpenWaterByOwNum(owNum);

                //用户所属职务的优惠权限数据
                SysPerDiscount sysPerDiscount = businessPermissionService.selectDiscountByUserCode(userCode);

                //前台营业设置的账单权限
                DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
                //账单权限
                DBSBillServDTO dbsBillServDTO = new Gson().fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class);
                DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);

                Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
                        pxdzZeroMonry = 0.0,//品项打折小计抹零金额
                        hydzZeroMonry = 0.0,//会员打折小计抹零金额
                        openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
                        openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
                        openWaterHydzSubtotal = 0.0,//营业流水会员小计价格和
                        openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
                        openWaterPxdzYs = 0.0,//营业流水品项打折收费类型应收金额
                        openWaterHydzYs = 0.0;//营业流水会员小计收费类型应收金额

                Boolean isAllAdvancePay = false;
                Integer clearingId = null;
                List<Map> map1 = null;
                Map<String,Object> vipInfo = new HashMap<>();
                String hyLevel = null;

                DgPos pos = new DgPos();
                pos.setId(dgOpenWater.getOpenPos());
                pos = dgPosService.getPosByID(pos);

                //获取会员信息
                if(dgOpenWater.getCrId() != null){
                    String memberList = OkHttpUtils.memberGetVipAllCopu(dgOpenWater.getCrId());
                    if(memberList != null){
                        Gson gson = new Gson();
                        Map res = (Map)gson.fromJson(memberList, Map.class);
                        if(res.get("msgCode").toString().equalsIgnoreCase("ok")){
                            String bodys = res.get("body").toString();
                            if(!StringUtils.isEmpty(bodys)){
                                if(res.containsKey("vipCard")){
                                    Map<String,Object> vipMap = gson.fromJson(res.get("vipCard").toString(),Map.class);
                                    dgOpenWater.setMemberInfo(vipMap);

                                    if(owNum.equals(dgOpenWater.getOwNum())){
                                        hyLevel = vipMap.get("gradId").toString();
                                        map1 = (List<Map>)gson.fromJson(vipMap.get("listModel").toString(), new TypeToken<List<Map>>(){}.getType());
                                        vipInfo.putAll(vipMap);
                                    }
                                }
                            }
                        }
                    }
                }

                if (dgOpenWater.getClearingWaterId() != null) {
                    isAllAdvancePay = true;
                    clearingId = dgOpenWater.getClearingWaterId();
                } else {
                    isAllAdvancePay = false;
                }

                //循环获取所有营业流水下面的所有有效品项按照服务单分组
                List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterClearingWithService(dgOpenWater.getOwNum(),0);
//                List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterClearing(dgOpenWater.getOwNum());

                //重要活动价格，品项打折价格，会员打折价格
                Double standPriceZyhd = 0.0,standPricePxdz = 0.0,standPriceHydz = 0.0;

                //先计算出所有品项的小计
                for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                    //开单没有优惠的品项的三种折扣价格，计算制作费用
                    if(dgOwConsumerDetails.getIsPriceCal() == 0){//只有当品项为未结算状态才计算
                        apiCheckService.getOpenWaterTotalPrice(dgOwConsumerDetails,hyLevel,Integer.valueOf(pos.getOrganization()),generalProportions,singleProportions);

                        //设置优惠的比例
                        if(generalProportions != null){
                            dgOwConsumerDetails.setDiscount(generalProportions);
                        }

                        if (singleProportions != null) {
                            dgOwConsumerDetails.setDiscount(singleProportions);
                        }

                        //每一个营业流水下所有品项三种价格累加
                        standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount() != null?dgOwConsumerDetails.getZyhdItemCostsSumDiscount():dgOwConsumerDetails.getZyhdItemCostsSum());
                        standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount() != null?dgOwConsumerDetails.getPxdzItemCostsSumDiscount():dgOwConsumerDetails.getPxdzItemCostsSum());
                        standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getHydzItemCostsSum());
                    }else if (dgOwConsumerDetails.getIsPriceCal() == 1){ //开单有优惠的品项，直接累加
                        //如果品项开单已经有优惠，则将该品项的品项打折设置为该价格
                        dgOwConsumerDetails.setPxdzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setPxdzItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        dgOwConsumerDetails.setZyhdItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setZyhdItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                dgOwConsumerDetails.setPxdzItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setPxdzItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());

                                dgOwConsumerDetails.setZyhdItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setZyhdItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());
                            }
                        }

                        if(maps.size() > 0){
                            if(clearingWater != null){
                                if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount()) ;
                                }else{
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                                }
                            }else{
                                standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                            }
                        }
                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                            }else{
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                            }
                        }else{
                            standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                        }

                        if(hyLevel != null){
                            dgOwConsumerDetails.setHydzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                            dgOwConsumerDetails.setHydzItemCostsSum(dgOwConsumerDetails.getSubtotal());
                            standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getSubtotal());
                        }
                    }
                }

                //品项信息
                dgOpenWater.setItemFileInfos(dgOwConsumerDetailss);

                apiCheckService.updateOpenWaterSubtotal(dgOpenWater);

                dgOpenWater.setZyhdItemSubtotal(standPriceZyhd);
                dgOpenWater.setPxdzItemSubtotal(standPricePxdz);
                dgOpenWater.setHydzItemSubtotal(standPriceHydz);

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "all");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
                        dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
                        dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
                    }
                    if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
                        dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
                    }
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }

                openWaterZyhdSubtotal =  MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal());
                openWaterPxdzSubtotal =  MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal());
                openWaterHydzSubtotal =  MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal());

                DgReceptionClearingWater dgReceptionClearingWater = null;

                if (isAllAdvancePay) {
                    Map<String, Object> advancePayInfo = paySettlementService.selectAllAdvancePayInfo(clearingId);
                    dgReceptionClearingWater = (DgReceptionClearingWater)advancePayInfo.get("clearingData");
                    resMap.putAll(advancePayInfo);
                }

                //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
                if(!dbsBillServDTO.getNoSmallChangeWay().equals("0")){
                    if(dbsBillServDTO.getNoSmallChangeWay().equals("1")){
                        //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                        if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                            zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(swqdj(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(swqdj(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                            zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
                            pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);
                            hydzZeroMonry = setZeroValue(openWaterHydzSubtotal);

                            openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
                            openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
                            openWaterHydzYs = setYsje(openWaterHydzSubtotal);
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                            zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(swqdfive(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(swqdfive(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                            zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(swqdten(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(swqdten(openWaterHydzSubtotal));
                        }
                    }else if(dbsBillServDTO.getNoSmallChangeWay().equals("2")){//去尾法
                        //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                        if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                            zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(qwqdj(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(qwqdj(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                            zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(qwqdy(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(qwqdy(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                            zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(qwqd5y(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(qwqd5y(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                            zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(qwqd10y(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(qwqd10y(openWaterHydzSubtotal));
                        }
                    }else if(dbsBillServDTO.getNoSmallChangeWay().equals("3")){//四舍五入法
                        //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                        if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                            zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(roundingQdj(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(roundingQdj(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                            zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(roundingQdy(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(roundingQdy(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                            zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(roundingQd5y(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(roundingQd5y(openWaterHydzSubtotal));
                        }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                            zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
                            pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));
                            hydzZeroMonry = setZeroValue(roundingQd10y(openWaterHydzSubtotal));

                            openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
                            openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
                            openWaterHydzYs = setYsje(roundingQd10y(openWaterHydzSubtotal));
                        }
                    }
                }else{
                    openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
                    openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
                    openWaterHydzYs = MathExtend.add(openWaterHydzYs,openWaterHydzSubtotal);
                }

                //营业流水信息集合
                resMap.put("openWater",dgOpenWater);

                if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 1)){
                    //3种价格合计
                    resMap.put("pxdzZeroMonry",returnValue(pxdzZeroMonry));
                    resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
                    resMap.put("openWaterPxdzYs",returnValue(openWaterPxdzYs));
                }

                if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 2)){
                    if(maps.size() > 0){
                        resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                        resMap.put("zyhdZeroMonry",zyhdZeroMonry);
                        resMap.put("openWaterZyhdYs",openWaterZyhdYs);
                    }
                }

                if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 3)){
                    //会员打折价格
                    if(hyLevel != null){
                        Double subtract = MathExtend.subtract(openWaterHydzYs, returnValue(hydzZeroMonry));
                        resMap.put("hydzZeroMonry",hydzZeroMonry);
                        resMap.put("openWaterHydzYs",subtract);
                        resMap.put("openWaterHydzSubtotal",openWaterHydzSubtotal);
                        //优惠券金额计算
                        List<Map> maps1 = apiCheckService.modifyCouponMoney(map1, subtract, dgOpenWater);
                        resMap.put("memberCardList",maps1);
                        resMap.put("vipInfo",vipInfo);
                    }
                }

                //优惠权限相关
                resMap.put("sysPerDiscount",sysPerDiscount);
                resMap.put("otherSetting",dbsBillServDTO);

//                List<Map> 	itemVal = aPIMainService.getOpenWaterDetailsOrderByItem(dgOpenWater.getSeatId());
//
//                List<Map> 	subVal = aPIMainService.getOpenWaterDetailSubByService(dgOpenWater.getSeatId());
//
//                List<Map> 	bigVal = aPIMainService.getOpenWaterDetailsAccordingBig(dgOpenWater.getSeatId());
//
//                List<Map> 	serviceVal = aPIMainService.getOpenWaterDetailsOrderByService(dgOpenWater.getSeatId());

//                resMap.put("itemVal",itemVal);
//                resMap.put("subVal",subVal);
//                resMap.put("bigVal",bigVal);
//                resMap.put("serviceVal",serviceVal);

                //判断客座标签是否自动显示在结算备注里面
                String seatLabel = openWaterSeat.getSeatLabel();
                //自动显示
                if(seatLabel != null && dbsSeetServDTO.getIsAutoInsertDeskLabelToSettlementRemarks().equals("1")){
                    resMap.put("autoClearingNotes",seatLabel);
                }
                return getSuccessResult(resMap);
            } catch (Exception e) {
                e.printStackTrace();
                return getExceptionResult();
            }
        }
    }


    @ResponseBody
    @RequestMapping("/selectYjdData")
    public Object selectYjdData(String openWater,
                                @ApiParam(required = false, name = "isCategory", value = "是否按类别打印") @RequestParam(value = "isCategory" , required = false)Integer isCategory){
        try {
            Map<String,Object> resMap = new HashMap(); //返回map

            //营业流水基本信息以及状态判断
            Map map = apiCheckService.selectOpenWaterByOwNum(openWater);
            if(map == null){
                return getResult(APIEnumDefine.M0101001);
            }

            Integer owState = Integer.parseInt(map.get("ow_state").toString());

            //传入的营业流水状态为空
            if(owState == null){
                return getResult(APIEnumDefine.M0201001);
            }
            //END

            //比例优惠、全单优惠
            Double generalProportions = null,singleProportions = null;

            //如果营业流水存在结算流水ID，得到埋单时可能存入的比例优惠以及全单优惠
            DgReceptionClearingWater clearingWater = null;
            if(map.containsKey("clearing_water_id")){
                clearingWater = paySettlementService.selectClearingWaterById(Integer.parseInt(map.get("clearing_water_id").toString()));
                if(clearingWater.getGeneralProportions() != null){
                    generalProportions = clearingWater.getGeneralProportions();
                }
                if(clearingWater.getSingleProportions() != null){
                    singleProportions = clearingWater.getSingleProportions();
                }
            }

            //营业流水座位
            DgConsumerSeat openWaterSeat = dgConsumerSeatService.selectByPrimaryKey(Integer.parseInt(map.get("seat_id").toString()));

            //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
            List<Map> maps = apiCheckService.selectIsZyhd();

            //得到该营业流水所有相关团队成员以及向该营业流水转账的数据集合
            List<DgOpenWater> dgOpenWaters = apiCheckService.selectAllOpenWaterByOwNum(openWater);

            if(dgOpenWaters == null){
                return getResult(APIEnumDefine.S998);
            }

            //前台营业设置的账单权限
            DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
            //账单权限
            DBSBillServDTO dbsBillServDTO = new Gson().fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class);
            DBSSeetServDTO dbsSeetServDTO = new Gson().fromJson(deskBusinessSetting.getSeatServ(), DBSSeetServDTO.class);

            Double  zyhdZeroMonry = 0.0,//重要活动小计抹零金额
                    pxdzZeroMonry = 0.0,//品项打折小计抹零金额
                    hydzZeroMonry = 0.0,//会员打折小计抹零金额
                    openWaterZyhdSubtotal = 0.0,//营业流水重要活动价格和
                    openWaterPxdzSubtotal = 0.0,//营业流水品项打折价格和
                    openWaterHydzSubtotal = 0.0,//营业流水会员小计价格和
                    openWaterZyhdYs = 0.0,//营业流水重要活动收费类型应收金额
                    openWaterPxdzYs = 0.0,//营业流水品项打折收费类型应收金额
                    openWaterHydzYs = 0.0;//营业流水会员小计收费类型应收金额

            Boolean isAllAdvancePay = false;
            Integer clearingId = null;
            List<Map> map1 = null;
            Map<String,Object> vipInfo = new HashMap<>();
            String hyLevel = null;
            //循环可以进行买单结算的营业流水
            for(DgOpenWater dgOpenWater:dgOpenWaters) {

                DgPos pos = new DgPos();
                pos.setId(dgOpenWater.getOpenPos());
                pos = dgPosService.getPosByID(pos);

                //获取会员信息
                if(dgOpenWater.getCrId() != null ){
                    String memberList = OkHttpUtils.memberGetVipAllCopu(dgOpenWater.getCrId());
                    if(memberList != null){
                        Gson gson = new Gson();
                        Map res = (Map)gson.fromJson(memberList, Map.class);
                        if(res.get("msgCode").toString().equalsIgnoreCase("ok")){
                            String bodys = res.get("body").toString();
                            if(!StringUtils.isEmpty(bodys)){
                                Map  vipCardMap = (Map)res.get("body");
                                if(vipCardMap.containsKey("vipCard") && !vipCardMap.get("vipCard").toString().equals("null")){
                                    String vipCard = vipCardMap.get("vipCard").toString();
                                    Map<String,Object> vipMap = gson.fromJson(vipCard,Map.class);
                                    dgOpenWater.setMemberInfo(vipMap);

                                    if(openWater.equals(dgOpenWater.getOwNum())){
                                        hyLevel = vipMap.get("gradId").toString();
                                        map1 = (List<Map>)gson.fromJson(vipMap.get("listModel").toString(), new TypeToken<List<Map>>(){}.getType());
                                        vipInfo.putAll(vipMap);
                                    }
                                }
                            }
                        }
                    }
                }

                if (dgOpenWater.getClearingWaterId() != null) {
                    isAllAdvancePay = true;
                    clearingId = dgOpenWater.getClearingWaterId();
                } else {
                    isAllAdvancePay = false;
                }

                //循环获取所有营业流水下面的所有有效品项按照服务单分组
                List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterClearingWithService(dgOpenWater.getOwNum(),isCategory);

                //重要活动价格，品项打折价格，会员打折价格
                Double standPriceZyhd = 0.0,standPricePxdz = 0.0,standPriceHydz = 0.0;

                //先计算出所有品项的小计
                for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                    //开单没有优惠的品项的三种折扣价格，计算制作费用
                    if(dgOwConsumerDetails.getIsPriceCal() == 0){//只有当品项为未结算状态才计算
                        apiCheckService.getOpenWaterTotalPrice(dgOwConsumerDetails,hyLevel,Integer.valueOf(pos.getOrganization()),generalProportions,singleProportions);

                        //设置优惠的比例
                        if(generalProportions != null){
                            dgOwConsumerDetails.setDiscount(generalProportions);
                        }

                        if (singleProportions != null) {
                            dgOwConsumerDetails.setDiscount(singleProportions);
                        }

                        //每一个营业流水下所有品项三种价格累加
                        standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount() != null?dgOwConsumerDetails.getZyhdItemCostsSumDiscount():dgOwConsumerDetails.getZyhdItemCostsSum());
                        standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount() != null?dgOwConsumerDetails.getPxdzItemCostsSumDiscount():dgOwConsumerDetails.getPxdzItemCostsSum());
                        standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getHydzItemCostsSum());
                    }else if (dgOwConsumerDetails.getIsPriceCal() == 1){ //开单有优惠的品项，直接累加
                        //如果品项开单已经有优惠，则将该品项的品项打折设置为该价格
                        dgOwConsumerDetails.setPxdzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setPxdzItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        dgOwConsumerDetails.setZyhdItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                        dgOwConsumerDetails.setZyhdItemCostsSum(dgOwConsumerDetails.getSubtotal());

                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                dgOwConsumerDetails.setPxdzItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setPxdzItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());

                                dgOwConsumerDetails.setZyhdItemFilePriceDiscount(dgOwConsumerDetails.getItemFinalPrice());
                                dgOwConsumerDetails.setZyhdItemCostsSumDiscount(dgOwConsumerDetails.getSubtotal());
                            }
                        }

                        if(maps.size() > 0){
                            if(clearingWater != null){
                                if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getZyhdItemCostsSumDiscount()) ;
                                }else{
                                    standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                                }
                            }else{
                                standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                            }
                        }
                        if(clearingWater != null){
                            if(clearingWater.getSingleProportions() != null || clearingWater.getGeneralProportions() != null){
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getPxdzItemCostsSumDiscount());
                            }else{
                                standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                            }
                        }else{
                            standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
                        }

                        if(hyLevel != null){
                            dgOwConsumerDetails.setHydzItemFilePrice(dgOwConsumerDetails.getItemFinalPrice());
                            dgOwConsumerDetails.setHydzItemCostsSum(dgOwConsumerDetails.getSubtotal());
                            standPriceHydz = MathExtend.add(standPriceHydz,dgOwConsumerDetails.getSubtotal());
                        }
                    }
                }

                //品项信息
                dgOpenWater.setItemFileInfos(dgOwConsumerDetailss);

                dgOpenWater.setZyhdItemSubtotal(standPriceZyhd);
                dgOpenWater.setPxdzItemSubtotal(standPricePxdz);
                dgOpenWater.setHydzItemSubtotal(standPriceHydz);

                //算出三种价格，返回服务费
                apiCheckService.getOtherCost(dbsBillServDTO, dgOpenWater, "all");

                //计算出最低消费补齐的金额
                if(dgOpenWater.getMinimumConsumption() != null){
                    Double minimumConsumption = dgOpenWater.getMinimumConsumption();
                    if(dgOpenWater.getZyhdItemSubtotal() < minimumConsumption){
                        dgOpenWater.setZyhdZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()));
                        dgOpenWater.setZyhdSubtotal(dgOpenWater.getZyhdZdxfbq() + dgOpenWater.getZyhdItemSubtotal());
                    }
                    if(dgOpenWater.getPxdzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setPxdzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()));
                        dgOpenWater.setPxdzSubtotal(dgOpenWater.getPxdzZdxfbq() + dgOpenWater.getPxdzItemSubtotal());
                    }
                    if(dgOpenWater.getHydzItemSubtotal() < minimumConsumption){
                        dgOpenWater.setHydzZdxfbq(MathExtend.subtract(minimumConsumption,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal()));
                        dgOpenWater.setHydzSubtotal(dgOpenWater.getHydzZdxfbq() + dgOpenWater.getHydzItemSubtotal());
                    }
                }

                openWaterZyhdSubtotal =  MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal());
                openWaterPxdzSubtotal =  MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal());
                openWaterHydzSubtotal =  MathExtend.add(openWaterHydzSubtotal,dgOpenWater.getHydzSubtotal() == null ? 0 : dgOpenWater.getHydzSubtotal());
            }

            DgReceptionClearingWater dgReceptionClearingWater = null;

            if (isAllAdvancePay) {
                Map<String, Object> advancePayInfo = paySettlementService.selectAllAdvancePayInfo(clearingId);
                dgReceptionClearingWater = (DgReceptionClearingWater)advancePayInfo.get("clearingData");
                resMap.putAll(advancePayInfo);
            }

            //前台营业设置账单服务抹零方式0、不抹零，1、收尾法，2、去尾法，3、四舍五入法
            if(!dbsBillServDTO.getNoSmallChangeWay().equals("0")){
                if(dbsBillServDTO.getNoSmallChangeWay().equals("1")){
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                        zyhdZeroMonry = setZeroValue(swqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdj(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                        zyhdZeroMonry = setZeroValue(openWaterZyhdSubtotal);
                        pxdzZeroMonry = setZeroValue(openWaterPxdzSubtotal);
                        hydzZeroMonry = setZeroValue(openWaterHydzSubtotal);

                        openWaterZyhdYs = setYsje(openWaterZyhdSubtotal);
                        openWaterPxdzYs = setYsje(openWaterPxdzSubtotal);
                        openWaterHydzYs = setYsje(openWaterHydzSubtotal);
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                        zyhdZeroMonry = setZeroValue(swqdfive(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdfive(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdfive(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdfive(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdfive(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdfive(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                        zyhdZeroMonry = setZeroValue(swqdten(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(swqdten(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(swqdten(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(swqdten(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(swqdten(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(swqdten(openWaterHydzSubtotal));
                    }
                }else if(dbsBillServDTO.getNoSmallChangeWay().equals("2")){//去尾法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                        zyhdZeroMonry = setZeroValue(qwqdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqdj(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                        zyhdZeroMonry = setZeroValue(qwqdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqdy(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqdy(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqdy(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqdy(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                        zyhdZeroMonry = setZeroValue(qwqd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd5y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqd5y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd5y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqd5y(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                        zyhdZeroMonry = setZeroValue(qwqd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(qwqd10y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(qwqd10y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(qwqd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(qwqd10y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(qwqd10y(openWaterHydzSubtotal));
                    }
                }else if(dbsBillServDTO.getNoSmallChangeWay().equals("3")){//四舍五入法
                    //抹零位置0、取到角，1、取到元，2、取到五元，3、取到十元
                    if(dbsBillServDTO.getNoSmallChangePlace().equals("0")){
                        zyhdZeroMonry = setZeroValue(roundingQdj(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdj(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQdj(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdj(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdj(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQdj(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("1")){
                        zyhdZeroMonry = setZeroValue(roundingQdy(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQdy(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQdy(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQdy(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQdy(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQdy(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("2")){
                        zyhdZeroMonry = setZeroValue(roundingQd5y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd5y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQd5y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd5y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd5y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQd5y(openWaterHydzSubtotal));
                    }else if(dbsBillServDTO.getNoSmallChangePlace().equals("3")){
                        zyhdZeroMonry = setZeroValue(roundingQd10y(openWaterZyhdSubtotal));
                        pxdzZeroMonry = setZeroValue(roundingQd10y(openWaterPxdzSubtotal));
                        hydzZeroMonry = setZeroValue(roundingQd10y(openWaterHydzSubtotal));

                        openWaterZyhdYs = setYsje(roundingQd10y(openWaterZyhdSubtotal));
                        openWaterPxdzYs = setYsje(roundingQd10y(openWaterPxdzSubtotal));
                        openWaterHydzYs = setYsje(roundingQd10y(openWaterHydzSubtotal));
                    }
                }
            }else{
                openWaterZyhdYs = MathExtend.add(openWaterZyhdYs,openWaterZyhdSubtotal);
                openWaterPxdzYs = MathExtend.add(openWaterPxdzYs,openWaterPxdzSubtotal);
                openWaterHydzYs = MathExtend.add(openWaterHydzYs,openWaterHydzSubtotal);
            }

            //营业流水信息集合
            resMap.put("openWaters",dgOpenWaters);

            if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 1)){
                //3种价格合计
                resMap.put("pxdzZeroMonry",returnValue(pxdzZeroMonry));
                resMap.put("openWaterPxdzSubtotal",openWaterPxdzSubtotal);
                resMap.put("openWaterPxdzYs",returnValue(openWaterPxdzYs));
            }

            if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 2)){
                if(maps.size() > 0){
                    resMap.put("openWaterZyhdSubtotal",openWaterZyhdSubtotal);
                    resMap.put("zyhdZeroMonry",zyhdZeroMonry);
                    resMap.put("openWaterZyhdYs",openWaterZyhdYs);
                }
            }

            if(dgReceptionClearingWater == null || (dgReceptionClearingWater.getPayType() != null && dgReceptionClearingWater.getPayType() == 3)){
                //会员打折价格
                if(hyLevel != null){
                    Double subtract = MathExtend.subtract(openWaterHydzYs, returnValue(hydzZeroMonry));
                    resMap.put("hydzZeroMonry",hydzZeroMonry);
                    resMap.put("openWaterHydzYs",subtract);
                    resMap.put("openWaterHydzSubtotal",openWaterHydzSubtotal);
                    //优惠券金额计算
                    List<Map> maps1 = apiCheckService.modifyCouponMoney(map1, subtract, dgOpenWaters);
                    resMap.put("memberCardList",maps1);
                    resMap.put("vipInfo",vipInfo);
                }
            }

            return getSuccessResult(resMap);
        } catch (Exception e) {
            e.printStackTrace();
            return getExceptionResult();
        }
    }


}
