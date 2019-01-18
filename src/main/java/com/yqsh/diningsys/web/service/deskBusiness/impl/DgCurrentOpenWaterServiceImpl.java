package com.yqsh.diningsys.web.service.deskBusiness.impl;

import java.util.*;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.deskBusiness.DBSBillServDTO;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.service.api.APICheckService;
import com.yqsh.diningsys.web.service.deskBusiness.DeskBusinessSettingService;
import com.yqsh.diningsys.web.util.TableQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yqsh.diningsys.api.model.SeatCountByState;
import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.service.deskBusiness.DgCurrentOpenWaterService;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-14 下午1:53
 */
@Service
public class DgCurrentOpenWaterServiceImpl extends GenericServiceImpl<DgOpenWater, Integer>
        implements DgCurrentOpenWaterService {
    @Resource
    private DgOpenWaterMapper dgOpenWaterMapper;

    @Autowired
    private APICheckService apiCheckService;

    @Autowired
    private DeskBusinessSettingService deskBusinessSettingService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        Map param = new HashMap();

        param.put("id", id);

        return dgOpenWaterMapper.deleteByPrimaryKey(param);
    }

    @Override
    public void editClearingNotes(String clearingNotes, Integer statementLabel, Integer cwId) {
        Map param = new HashMap();

        param.put("clearingNotes", clearingNotes);
        param.put("statementLabel", statementLabel);
        param.put("cwId", cwId);
        dgOpenWaterMapper.editClearingNotes(param);
    }

    @Override
    public Map selectAllSeatPeople(Integer place, Integer bis, Integer seatState) {
        Map param = new HashMap();

        param.put("place", place);
        param.put("bis", bis);
        param.put("seatState", seatState);

        return dgOpenWaterMapper.selectAllSeatPeople(param);
    }

    @Override
    public DgOpenWater selectByPrimaryKey(Integer id) {
        Map param = new HashMap();

        param.put("id", id);

        return dgOpenWaterMapper.selectByPrimaryKey(param);
    }

    @Override
    public Map selectCurrentBisClassInfo(Integer org, Integer bis) {
        Map param = new HashMap();
        param.put("bis", bis);

        Double openWaterZyhdSubtotal = 0.0,
                openWaterPxdzSubtotal = 0.0;

        //前台营业设置的账单权限
        DeskBusinessSetting deskBusinessSetting = deskBusinessSettingService.getDeskBusinessSetting();
        //账单权限
        DBSBillServDTO dbsBillServDTO = new Gson().fromJson(deskBusinessSetting.getBillServ(), DBSBillServDTO.class);

        //判断今天是否存在重要活动数据，如果有重要活动，无论是否包含营业流水品项，都会计算重要活动价格
        List<Map> maps = apiCheckService.selectIsZyhd();

        List<DgOpenWater> dgOpenWaters = dgOpenWaterMapper.selectCurrentOpeningWater(param);
        for(DgOpenWater dgOpenWater:dgOpenWaters) {
            //循环获取所有营业流水下面的所有有效品项按照服务单分组
            List<DgOwConsumerDetails> dgOwConsumerDetailss = apiCheckService.selectOpenWaterClearingWithService(dgOpenWater.getOwNum(),0);

            //重要活动价格，品项打折价格，会员打折价格
            Double standPriceZyhd = 0.0,standPricePxdz = 0.0,standPriceHydz = 0.0;

            //先计算出所有品项的小计
            for(DgOwConsumerDetails dgOwConsumerDetails:dgOwConsumerDetailss){
                //开单没有优惠的品项的三种折扣价格，计算制作费用
                if(dgOwConsumerDetails.getIsPriceCal() == 0){
                    apiCheckService.getOpenWaterTotalPrice(dgOwConsumerDetails,null,org,null,null);

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

                    if(maps.size() > 0){
                        standPriceZyhd = MathExtend.add(standPriceZyhd,dgOwConsumerDetails.getSubtotal()) ;
                    }

                    standPricePxdz = MathExtend.add(standPricePxdz,dgOwConsumerDetails.getSubtotal());
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

            openWaterZyhdSubtotal =  MathExtend.add(MathExtend.add(openWaterZyhdSubtotal,dgOpenWater.getZyhdSubtotal() == null ? 0 : dgOpenWater.getZyhdSubtotal()),dgOpenWater.getPrivateRoomCost());
            openWaterPxdzSubtotal =  MathExtend.add(MathExtend.add(openWaterPxdzSubtotal,dgOpenWater.getPxdzSubtotal() == null ? 0 : dgOpenWater.getPxdzSubtotal()),dgOpenWater.getPrivateRoomCost());

            openWaterZyhdSubtotal =  MathExtend.add(dgOpenWater.getServiceCharge(),openWaterZyhdSubtotal);
            openWaterPxdzSubtotal =  MathExtend.add(dgOpenWater.getServiceCharge(),openWaterPxdzSubtotal);
        }

        Map map = dgOpenWaterMapper.selectCurrentBisClassInfo(param);
        Double aDouble = openWaterZyhdSubtotal.compareTo(openWaterPxdzSubtotal) < 0 ? openWaterZyhdSubtotal : openWaterPxdzSubtotal;
        map.put("bisOpenClassAmount",aDouble);
        Object allAmount = map.get("allAmount");
        if(allAmount != null){
        	map.put("allAmount",MathExtend.add(allAmount.toString(),aDouble.toString()));
        }
        return map;
    }

    @Override
    public List<Map> selectCurrentPayInfo(Integer org, Integer place, Integer bis) {
        Map param = new HashMap();

        param.put("org", org);
        param.put("place", place);
        param.put("bis", bis);
        return dgOpenWaterMapper.selectCurrentPayInfo(param);
    }

    @Override
    public Map selectCurrentSeatCount(Integer org, Integer place, Integer bis) {
        Map param = new HashMap();

        param.put("org", org);
        param.put("place", place);
        param.put("bis", bis);

        List<Map> maps = dgOpenWaterMapper.selectCurrentSeatCount(param);

        param.clear();

        Integer totalCount = 0;

        for (Map map : maps) {
            totalCount += Integer.parseInt(map.get("countNumber").toString());
            param.put(Integer.parseInt(map.get("seatState").toString()), map.get("countNumber"));
        }

        param.put("totalCount", totalCount);

        return param;
    }

    @Override
    public Map selectCurrentSeatCountByPosArea(String areas) {
        Map                    param             = new HashMap(),
                               resMap            = new HashMap();
        List<SeatCountByState> seatCountByStates = new ArrayList<>();
        List<Map>              seatByArea        = new ArrayList<>();
        Integer                totalCount        = 0;

        param.put("areas", arraysTOList(areas));

        List<Map> mapsState = dgOpenWaterMapper.selectCurrentSeatCountByPostAreaState(param);
        List<Map> mapsArea  = dgOpenWaterMapper.selectCurrentSeatCountByPostArea(param);

        // 前台返回数据拼装
        for (Map map : mapsState) {
            totalCount += Integer.parseInt(map.get("countNumber").toString());
            seatCountByStates.add(new SeatCountByState(Integer.parseInt(map.get("seatState").toString()),
                                                       map.get("countNumber").toString()));
        }

        resMap.put("totalCount", totalCount);
        resMap.put("seatCountByState", seatCountByStates);

        for (Map map : mapsArea) {
            Map areaMap = new HashMap();
            areaMap.put("areaName", map.get("areaName"));
            areaMap.put("areaCount", map.get("countNumberArea"));
            areaMap.put("areaId", map.get("consArea"));
            seatByArea.add(areaMap);
        }

        resMap.put("seatCountByArea", seatByArea);

        return resMap;
    }

    @Override
    public List<DgOpenWater> selectOpenWaterBySeatId(Integer seatId) {
        return dgOpenWaterMapper.selectOpenWaterBySeatId(seatId);
    }

    @Override
    public DgOpenWater selectOpenWaterByOwNum(String owNum) {
        return dgOpenWaterMapper.selectByOpenWaterByNum(owNum);
    }

    @Override
    public List<DgOwConsumerDetails> getOpenWaterConDeInfo_new(Integer owId, String time) {

        Map param = new HashMap();
        param.put("id",owId);

        Boolean check = TableQueryUtil.closedBillTimeCheck(time);
        param.put("flag",check);
        if(!check){
            String tableSuffix = TableQueryUtil.tableNameUtilWithMonthSingle(time);
            param.put("suffix",tableSuffix);
        }

        List<DgOwConsumerDetails> dgOwConsumerDetails = dgOpenWaterMapper.selectConsumerDetailsByOwId_new(param);
        for(DgOwConsumerDetails dgOwConsumerDetail:dgOwConsumerDetails){
            if(dgOwConsumerDetail.getNotes().equals("1")){
                dgOwConsumerDetail.setNotes("自增品项");
            }else if(dgOwConsumerDetail.getNotes().equals("2")){
                dgOwConsumerDetail.setNotes("加单");
            }else if(dgOwConsumerDetail.getNotes().equals("3")){
                dgOwConsumerDetail.setNotes("赠单");
            }else if(dgOwConsumerDetail.getNotes().equals("4")){
                dgOwConsumerDetail.setNotes("退单");
            }else if(dgOwConsumerDetail.getNotes().equals("5")){
                dgOwConsumerDetail.setNotes("减少人数减单");
            }else if(dgOwConsumerDetail.getNotes().equals("6")){
                dgOwConsumerDetail.setNotes("增加人数自增");
            }else if(dgOwConsumerDetail.getNotes().equals("7")){
                dgOwConsumerDetail.setNotes("包房费品项");
            }
        }
        return dgOwConsumerDetails;
    }

    @Override
    public List<DgConsumerSeat> selectCurrentSeatInfo(Integer org, Integer place, Integer bis, Integer seatState) {
        Map param = new HashMap();


        param.put("org", org);
        param.put("place", place);
        param.put("bis", bis);
        param.put("seatState", seatState);

        List<DgConsumerSeat> dgConsumerSeats = dgOpenWaterMapper.selectAllSeat(param);

        List<DgOpenWater> dgOpenWaters = dgOpenWaterMapper.selectSeatCurrentOpenWater(param);

        for(DgConsumerSeat seat:dgConsumerSeats){
            for(DgOpenWater ow:dgOpenWaters){
                if(ow.getSeatId().equals(seat.getId())){
                    Integer realTimePeopleCount = seat.getRealTimePeopleCount()==null?0:seat.getRealTimePeopleCount();
                    seat.setRealTimePeopleCount(realTimePeopleCount+ow.getPeopleCount());
                }
            }
        }

        return dgConsumerSeats;
    }

    @Override
    public DgOpenWater selectOpenWaterInfoById(Integer id) {
        Map param = new HashMap();
        param.put("id", id);
        return dgOpenWaterMapper.selectOpenWaterInfoById(param);
    }

    @Override
    public List<Map> selectOpenWaterItemFileByOwId(Integer id) {
        Map param = new HashMap();
        param.put("id", id);
        return dgOpenWaterMapper.selectOpenWaterItemFileByOwId(param);
    }

    @Override
    public Map selectPlaceBisRes(Integer place, Integer bis) {
        Map param = new HashMap();
        param.put("place", place);
        param.put("bis", bis);
        return dgOpenWaterMapper.selectPlaceBisRes(param);
    }

    @Override
    public DgReceptionClearingWater selectSeatHasClosedCheck(Integer id) {
        Map param = new HashMap();
        param.put("id", id);
        return dgOpenWaterMapper.selectSeatHasClosedCheck(param);
    }

    @Override
    public Map selecttodayReservationInfo(Integer place) {
        Map param = new HashMap();
        param.put("id", place);
        return dgOpenWaterMapper.selecttodayReservationInfo(param);
    }

    @Override
    public List<DgOwClearingway> getClearingWayByCwId(Integer id) {
        Map param = new HashMap();
        param.put("id", id);
        return dgOpenWaterMapper.selectSettlementInfoByCwId(param);
    }

    @Override
    public GenericDao<DgOpenWater, Integer> getDao() {
        return dgOpenWaterMapper;
    }

    @Override
    public Map getNotesAndLabel(Integer id) {
        Map param = new HashMap();

        param.put("id", id);

        return dgOpenWaterMapper.getNotesAndLabel(param);
    }

    @Override
    public List<DgOwConsumerDetails> getOpenWaterConDeInfo(Integer id) {
        Map param = new HashMap();
        param.put("id",id);
        List<DgOwConsumerDetails> dgOwConsumerDetails = dgOpenWaterMapper.selectConsumerDetailsByOwId(param);
        for(DgOwConsumerDetails dgOwConsumerDetail:dgOwConsumerDetails){
            if(dgOwConsumerDetail.getNotes().equals("1")){
                dgOwConsumerDetail.setNotes("自增品项");
            }else if(dgOwConsumerDetail.getNotes().equals("2")){
                dgOwConsumerDetail.setNotes("加单");
            }else if(dgOwConsumerDetail.getNotes().equals("3")){
                dgOwConsumerDetail.setNotes("赠单");
            }else if(dgOwConsumerDetail.getNotes().equals("4")){
                dgOwConsumerDetail.setNotes("退单");
            }else if(dgOwConsumerDetail.getNotes().equals("5")){
                dgOwConsumerDetail.setNotes("减少人数减单");
            }else if(dgOwConsumerDetail.getNotes().equals("6")){
                dgOwConsumerDetail.setNotes("增加人数自增");
            }else if(dgOwConsumerDetail.getNotes().equals("7")){
                dgOwConsumerDetail.setNotes("包房费品项");
            }
        }
        return dgOwConsumerDetails;
    }

    @Override
    public Map getOpenWaterOtherInfo(Integer cwId) {

        Map param = new HashMap();

        param.put("id", cwId);
        Map resMap = new HashMap();

        resMap.put("settlements", dgOpenWaterMapper.selectSettlementInfoByCwId(param));
        resMap.put("discounts", dgOpenWaterMapper.selectDiscountInfoByCwId(param));
        resMap.put("receipts", dgOpenWaterMapper.selectReceiptInfoByCwId(param));

        return resMap;
    }

    @Override
    public Map getOpenWaterOtherInfo_new(Integer cwId,String time) {
        Map param = new HashMap();

        param.put("id", cwId);
        Map resMap = new HashMap();

        Boolean check = TableQueryUtil.closedBillTimeCheck(time);
        param.put("flag",check);
        if(!check){
            String tableSuffix = TableQueryUtil.tableNameUtilWithMonthSingle(time);
            param.put("suffix",tableSuffix);
        }

        resMap.put("settlements", dgOpenWaterMapper.selectSettlementInfoByCwId_new(param));
        resMap.put("discounts", dgOpenWaterMapper.selectDiscountInfoByCwId(param));
        resMap.put("receipts", dgOpenWaterMapper.selectReceiptInfoByCwId_new(param));

        return resMap;
    }

    @Override
    public List<DgOwReceipt> getReceiptInfoByCwId(Integer id) {
        Map param = new HashMap();

        param.put("id", id);
        return dgOpenWaterMapper.selectReceiptInfoByCwId(param);
    }

    @Override
    public List<DgOwReceipt> getReceiptInfoByCwId_new(Integer id,String time) {
        Map param = new HashMap();

        param.put("id", id);

        Boolean check = TableQueryUtil.closedBillTimeCheck(time);
        param.put("flag",check);
        if(!check){
            String tableSuffix = TableQueryUtil.tableNameUtilWithMonthSingle(time);
            param.put("suffix",tableSuffix);
        }

        return dgOpenWaterMapper.selectReceiptInfoByCwId_new(param);
    }

    @Override
    public List<DgOpenWater> getSeatOpenWater(Integer id) {
        Map param = new HashMap();
        param.put("id", id);
        List<DgOpenWater> ow = dgOpenWaterMapper.getSeatOpenWater(param);
        if(ow != null && ow.size() > 0){
        	for (int i = 0; i < ow.size(); i++) {
        		DgOpenWater dgOpenWater = ow.get(0);
        		if(dgOpenWater != null){
        			//修改之后的服务费
        			if(dgOpenWater.getModifyServiceCharge() != null){
        				dgOpenWater.setServiceCharge(dgOpenWater.getModifyServiceCharge());
        			}
        			//是否免服务费
        			if(dgOpenWater.getFreeServceCharge() != null){
        				dgOpenWater.setServiceCharge(0.0);
        			}
        			
        			//是否免最低消费
        			if(dgOpenWater.getFreeMinSpend() != null){
        				dgOpenWater.setMinimumConsumption(0.0);
        			}
        			
        			//修改之后的包房费
        			if(dgOpenWater.getModifyPrivateRoom() != null){
        				dgOpenWater.setPrivateRoomCost(dgOpenWater.getModifyPrivateRoom());
        			}
        			//免包房费
        			if(dgOpenWater.getFreePrivateRoom() != null){
        				dgOpenWater.setPrivateRoomCost(0.0);
        			}
        			
        			//买单时最终的品项消费
        			if(dgOpenWater.getFinalItemCostSum() != null){
        				dgOpenWater.setSubtotal(dgOpenWater.getFinalItemCostSum().doubleValue());
        			}
        		}
        	}
        }
        return ow;
    }
}