package com.yqsh.diningsys.web.service.deskBusiness.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yqsh.diningsys.api.util.OkHttpUtils;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.web.cache.CacheUtil;
import com.yqsh.diningsys.web.dao.SysAuthorizationLogMapper;
import com.yqsh.diningsys.web.dao.api.APICheckServiceMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgOpenWaterMapper;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.DgWaterCouponMapper;
import com.yqsh.diningsys.web.model.SysAuthorizationLog;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgPublicCode;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.*;
import com.yqsh.diningsys.web.service.deskBusiness.DgClosedOpenWaterService;
import com.yqsh.diningsys.web.util.TableQueryUtil;

import okhttp3.OkHttpClient;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-11-23 下午2:52
 */
@Service
public class DgClosedOpenWaterServiceImpl implements DgClosedOpenWaterService {

    @Resource
    private DgOpenWaterMapper dgOpenWaterMapper;

    @Resource
    private SysAuthorizationLogMapper sysAuthorizationLogMapper;

    @Resource
    private APICheckServiceMapper apiCheckServiceMapper;

    @Resource
    private DgWaterCouponMapper dgWaterCouponMapper;
    @Override
    public List<Map> selectClosedOpenWater(String date,String clientSeat,String bis,String expArea,String pos,String choiceCode,String code) {
        List<Map> maps = new ArrayList<>();
        Map param = new HashMap();
        param.put("date",date);
        param.put("clientSeat",clientSeat);
        param.put("bis",bis);
        param.put("expArea",expArea);
        param.put("pos",pos);
        param.put("choiceCode",choiceCode);
        param.put("code",code);

        Boolean check = TableQueryUtil.closedBillTimeCheck(date);
        param.put("flag",check);

        if(!check){
            String tableSuffix = TableQueryUtil.tableNameUtilWithMonthSingle(date);
            param.put("suffix",tableSuffix);

            if(StringUtils.isEmpty(tableSuffix)){
                return maps;
            }
        }
        maps = dgOpenWaterMapper.selectClosedOpenWaterByDate(param);
        return maps;
    }

    @Override
    public DgReceptionClearingWater selectCwInfoById(Integer id, String time) {
        Map param = new HashMap();
        param.put("id",id);

        Boolean check = TableQueryUtil.closedBillTimeCheck(time);
        param.put("flag",check);
        if(!check){
            String tableSuffix = TableQueryUtil.tableNameUtilWithMonthSingle(time);
            param.put("suffix",tableSuffix);
        }

        return dgOpenWaterMapper.selectCwInfoById(param);
    }

    @Override
    public List<DgOpenWater> selectOwInfoByCwId(Integer id, String time) {
        Map param = new HashMap();
        param.put("id",id);

        Boolean check = TableQueryUtil.closedBillTimeCheck(time);
        param.put("flag",check);
        if(!check){
            String tableSuffix = TableQueryUtil.tableNameUtilWithMonthSingle(time);
            param.put("suffix",tableSuffix);
        }

        List<DgOpenWater> ow = dgOpenWaterMapper.selectOwInfoByCwId(param);
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

    @Override
    public List<DgReceipt> selectAllReceipt() {
        return dgOpenWaterMapper.selectAllReceipt();
    }

    @Override
    public void editCwInvoice(String tableJsonData, Integer cwId, String time) {
        Map param = new HashMap();
        param.put("cwId",cwId);

        Boolean check = TableQueryUtil.closedBillTimeCheck(time);
        param.put("flag",check);
        if(!check){
            String tableSuffix = TableQueryUtil.tableNameUtilWithMonthSingle(time);
            param.put("suffix",tableSuffix);
        }

        //先删除表
        dgOpenWaterMapper.deleteInvoiceByCwId(param);

        Gson gson = new Gson();
        if(!StringUtils.isEmpty(tableJsonData)){
            List list = gson.fromJson(tableJsonData,List.class);
            param.put("list",list);
        }
        //批量插入
        dgOpenWaterMapper.insertInvoice(param);
    }

    @Override
    public List<DgPublicCode> selectAllCommonWay() {
        return dgOpenWaterMapper.selectAllCommonWay();
    }

    @Override
    public List<DgPublicCode> selectAllOtherWay() {
        return null;
    }

    @Override
    public void updateClearingWay(String tableJsonData,Integer cwId,Double ss,Double zl) {
        Map param = new HashMap();
        param.put("cwId",cwId);
        param.put("ss",ss);
        param.put("zl",zl);

        dgOpenWaterMapper.updateCwMoney(param);

        dgOpenWaterMapper.deleteClearingWayByCwId(param);

        Gson gson = new Gson();
        List<Map> list = gson.fromJson(tableJsonData,List.class);
        for(Map map:list){
            if(StringUtils.isEmpty(map.get("notes").toString())){
                map.put("notes",null);
            }
            if(StringUtils.isEmpty(map.get("nonZeroAmount").toString())){
                map.put("nonZeroAmount",null);
            }
            if(StringUtils.isEmpty(map.get("foreignPay").toString())){
                map.put("foreignPay",null);
            }
        }
        param.put("list",list);
        dgOpenWaterMapper.insertClearingWay(param);
    }

    @Override
    public String modifySettlement(SysUser authUser, String deskUserCode, String authCode, List<DgOpenWater> dgOpenWaters, DgReceptionClearingWater dgReceptionClearingWater) {
    	//20180130 新增会员返位
    	List<DgOwClearingway> dgOwClearingways = apiCheckServiceMapper.selectClearingWayByCwId(dgReceptionClearingWater.getId());

        //获取优惠券
        List<DgWaterCoupon> dgWaterCoupons = dgWaterCouponMapper.getCouponCountByWaters(dgOpenWaters);
        //核销微信会员卡券
        if(!dgWaterCoupons.isEmpty()){
            String consComId = CacheUtil.getURLInCache("member.comId");//消费店铺id
            String orderWater = dgReceptionClearingWater.getCwNum();
            List<Map> couponInfos = new ArrayList<>();
            for(DgWaterCoupon dgWaterCoupon:dgWaterCoupons){
                Map couponInfo = new HashMap();
                couponInfo.put("couponVal",dgWaterCoupon.getCouponval());
                couponInfos.add(couponInfo);
            }
            OkHttpUtils.writeOffWxFwCouponInfo(JSON.toJSONString(couponInfos),orderWater,consComId);
        }

    	StringBuffer memberIds = new StringBuffer();
    	for(DgOwClearingway dgOwClearingway:dgOwClearingways){
    		if(dgOwClearingway.getCwCode().equals("HYZF")||
    				dgOwClearingway.getCwCode().equals("WXHY")||
    				dgOwClearingway.getCwCode().equals("HYMBZF")){
    			if(!StringUtils.isEmpty(dgOwClearingway.getConsId())){
    				memberIds.append(dgOwClearingway.getConsId()).append(",");
    			}
    		}
    	}
    	if(memberIds.length() >0){
    		 String res = OkHttpUtils.memberRConsWater(memberIds.substring(0, memberIds.length()-1));
    		 if(res == null){
    		        return "连接会员平台失败";
    		 }
    		 Map resMap = new Gson().fromJson(res, Map.class);
             if(resMap.containsKey("msgCode")){
                 if(!resMap.get("msgCode").equals("200")){
                     return resMap.get("msgCode").toString();
                 }
             }
    	}

    	
    	//修改所有营业流水状态为初始化，并清空结算关联字段
        dgOpenWaterMapper.resetOpenWaterState(dgOpenWaters);

        List<DgConsumerSeat> dgConsumerSeats = dgOpenWaterMapper.selectOpenWaterFreeSeat(dgOpenWaters);
        if(dgConsumerSeats.size() > 0){
            dgOpenWaterMapper.updateSeatBusy(dgConsumerSeats);
        }

        //清空所有的品项的结算金额为空，结算状态为未结算
        List<DgOwConsumerDetails> dgOwConsumerDetailss = dgOpenWaterMapper.selectAllItemByOpenWaters(dgOpenWaters);
        for(DgOwConsumerDetails details:dgOwConsumerDetailss){
            if(StringUtil.isNotEmpty(details.getCouponVal())){
                dgOpenWaterMapper.deleteOwConsumerDetail(details);
            } else {
                if(details.getCouponNum() != null){
                    details.setItemFileNumber(MathExtend.add(details.getItemFileNumber(),details.getCouponNum()));
                }
                details.setSubtotal(MathExtend.multiply(details.getItemFileNumber(),details.getItemFinalPrice()));
                dgOpenWaterMapper.resetItemFinalPayMoney(details);
            }
        }
        //修改原本的结算流水状态为已经返位结算
        dgOpenWaterMapper.updateClearingWaterState(dgReceptionClearingWater.getId());
        //2017年5月15日17:25:32 新增返位结算日志表
        dgOpenWaterMapper.insertFwjsLog(dgReceptionClearingWater.getId());
        //插入授权码操作日志记录
        if(!StringUtils.isEmpty(authCode)){
            sysAuthorizationLogMapper.insertAuthLog(
                    new SysAuthorizationLog(authCode,deskUserCode,authUser.getEmpCode(),"返位结算")
            );
        }
        return "success";
    }

    @Override
    public List<DgOpenWater> selectOpenWaterbyCwId(Integer clearingWaterId) {
        List<DgOpenWater> dgOpenWaters = dgOpenWaterMapper.selectOpenWaterbyCwId(clearingWaterId);
        return dgOpenWaters;
    }

    @Override
    public List<DgConsumerSeat> selectSeatInfoBySeatIds(List<DgOpenWater> dgOpenWaters) {
        return dgOpenWaterMapper.selectSeatInfoBySeatIds(dgOpenWaters);
    }
}
