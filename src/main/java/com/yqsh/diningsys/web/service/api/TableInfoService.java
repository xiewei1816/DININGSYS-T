package com.yqsh.diningsys.web.service.api;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.api.DgCallService;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitPic;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwClearingway;

/**
 * Created by yqsh-zc on 2017/2/7.
 */
public interface TableInfoService {
	/**
	 * 插入mac
	 * @param mac
	 */
    void insertOrUpdate(String mac);

    Map<String,Object> getConsumerSeatInfoByMac(String mac, String code);

    /**
     * 平板开单操作
     * @param eatNumber
     * @param waiterCode
     * @param seatId
     * @param openPosNumber
     * @return
     */
    Map<String, Object> openBill(String eatNumber, String waiterCode, String seatId, String openPosNumber,
    		String cardCode, String ydId,boolean isOnline);
    
    /**
     * 平板预定开台
     * @param seatId
     * @return
     */
    Map<String, Object> openYdBill(String seatId,String number, String cardCode);


    /*
     * 获取当前时间下的市别id
     */
    String getMealInt();

    /**
     * 用于平板查询最后一条流水(初始化/埋单/手工锁定 状态)
     * @param param
     * @return
     */
    Map selectOpenWaterBySeatIdLastOne(String seatId);
    
    
    /**
     * 获取所有服务
     * @return
     */
    List<DgPublicCode0> getAllFw();
    
    
    /**
     * 遍历所有横向广告图片
     */
    List<DgItemTimeLimitPic> selectAllHPic();
    
    
    /**
     * 遍历所有纵向广告图片
     */
    List<DgItemTimeLimitPic> selectAllZPic();
    
    /**
     * 获取本店介绍
     * @return
     */
    TbOrg getOwnOrg();
    
    /**
     * 根据客位id获取预定信息
     * @param seatId
     * @return
     */
    DgReserveManager getReserveManagerBySeatId(String seatId);
    
    
	/**
	 * 获取所有菜品,根据桌位id(包含限时抢购)
	 * @param seatId
	 * @return
	 */
	List<Map<String,Object>> selectAllItemCXsqg(Integer seatId);
	
	
	/**
	 * 预定/开台界面循环获取接口
	 */
	
	Map getYdKtLoopInfo(String seatId);
	
	
	/**
	 * 判断卡权限
	 */
	int countBySeatIdAndCardNum(DgCard card);

	/**
	 * 易小二支付成功修改状态
     * @param openWaterData
     * @param paidMoney
     * @param zeroMoney
     * @param payType
     * @param priceType
     */
	void modifyYxePaySuccessState(List<DgOpenWater> openWaterData, String paidMoney, String zeroMoney, String payType, String time, Integer priceType);
	
	/**
	 * 插入呼叫内容
	 */
	int insertHjFw(String owNum,String content);
	
	/**
	 * 获取前5条呼叫数据
	 */
	List<DgCallService> getCallServiceTop5(String posId);

	/**
	 * 获取前5条呼叫数据
	 */
	List<DgCallService> getCallServiceTop5ByPos(DgPos pso);
	
	/**
	 * 删除呼叫数据
	 */
	int deleteCallServiceByIds(String ids);
	
	
	/**
	 * 设置客为从清扫到空闲
	 */
	void setSeatFree(String seatId);
	
	
    List<DgCallService> selectTopYxeZf5(DgPos pso);
    
	
	/**
	 * 获取前5品项组成成分超量
	 */
    List<DgCallService> selectTopZccf5();
    
    
    DgCallService getDgCallServiceById(Integer id);
    
    /**
     * 
     */
    List<DgPreOrderbill> selectAllPreOrderBill(String water);
    
    
    /**
     * 获取未处理线上预定条数
     */
    int selectReserveCount();
    
    
    /**
     * 检测是否是当前台位台卡，服务员卡
     * 
     */
    Map checkCard(String seatId,String cardNo);
    
    
    Map addBill(List<Map<String,Object>> list,String openNumber);
    
    
    
    /**
     * pos支付成功修改状态(做记录)
     * @param openWaterData
     * @param docws
     * @param paidMoney
     * @param zeroMoney
     * @param time
     * @param priceType
     */
	String modifyPosPaySuccessState(List<DgOpenWater> openWaterData,List<DgOwClearingway> docws, String paidMoney, String zeroMoney, String time, Integer priceType);
}
