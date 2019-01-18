package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePrice;
import com.yqsh.diningsys.web.model.deskBusiness.DgForMealTimePriceS;

public interface DgForMealTimePriceService extends GenericService<DgForMealTimePrice,Integer>{
    List<DgForMealTimePrice> getAllData(DgForMealTimePrice record);
    void deleteIds(String s);
    int deleteAll();
    int insertBackId(DgForMealTimePrice record);
    
    int deleteByItemId(int id);
    /**
     * 重要活动打折数据同步
     * @author jianglei
     * 日期 2017年2月14日 下午2:49:32
     * @param listDiad
     * @param listDiads
     */
    void syngetForMealTime(List<DgForMealTimePrice> listDfmtp,
    		List<DgForMealTimePriceS> listDfmtps);
    int saveForMealTimePrice(String data);
}