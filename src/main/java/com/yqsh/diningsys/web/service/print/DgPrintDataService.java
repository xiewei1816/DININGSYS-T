package com.yqsh.diningsys.web.service.print;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.api.DgPreOrderbillColor;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;
import com.yqsh.diningsys.web.model.print.DgPrintData;

public interface DgPrintDataService extends GenericService<DgPrintData,Integer>{
	
    /**
     * 获取数量
     */
    int getCount();
    
    /**
     * 获取第一条数据
     * @return
     */
    DgPrintData getFirstItem();
    
    
    
    List<Map> getPrintUrl(String body) throws Exception;
    
    
    /**
     * 加单打印数据插入
     * @return
     */
    void insertAddBillPrint(Integer owId,Double money);
    
    
    /**
     * 退单打印数据插入
     */
    
    void insertBackBill(Integer owId,Double money);
    
    
    /**
     * 退单打印数据插入
     */
    void insertReminderBill(List<Map> list);
    

    /**
     * 单品转台插入打印
     * @param oWaterId 需要单品转台的流水id
     * @param tWaterId 目标流水id
     * @param maps 转台品项组合
     */
    void insertModifyDishesTurntable(Integer oWaterId,Integer tWaterId,List<Map> maps);
    
    /**
     * 更改桌位插入打印
     * @param waterid 服务员id
     * @param owNum 营业流水号
     * @param tableid 桌位id
     * @param isGgFa  是否更改包房收费方案
     * @param isJsBffPx 是否将之前的包房费生存一个品项
     */
    void insertChangeTable(Integer waterid,String owNum,Integer tableid,Integer oldSeatId,Integer isGgFa,Integer isJsBffPx);
    
    
    /**
     * 并台插入打印
     * @param operCode 操作人
     * @param openNum  需要并台的营业流水号
     * @param teamNumber 团队号
     */
    void insertJoinTeam(String operCode,String openNum,String teamNumber);
    
    
    /**
     * 起菜插入打印
     * @param operCode 操作元code
     * @param openNum 营业流水号
     * @param orgs 起菜菜品
     */
    void insertQcBill(String operCode,String openNum,List<Map> orgs);
    
    
    
    /**
     * 插入外卖数据
     */
   void insertWmBill(List<Map<String, Object>> dates);
   
   
   /**
    * 插入预点单数据
    */
   void insertPreAddBill(List<DgPreOrderbill> dgPreOrderbills,Integer ConsumerId,DgOpenWater water,String colorPreNum);
   
   /**
    * 餐台加单打印数据插入
    * @return
    */
   void insertCtAddBillPrint(Integer owId,Double money);
}