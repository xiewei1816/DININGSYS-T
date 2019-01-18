package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;

/**
 * 未结账单 service
 *
 * @author zhshuo create on 2016-12-01 下午1:08
 */
public interface DgOpenWaterService {

    List<DgOpenWater> selectCurrentOpenWater(Map param);

    int updateOpenWater(DgOpenWater openWater);

    DgOpenWater selectByOpenWaterByNum(String num);
    
    DgOpenWater selectByOpenWaterById(String id);
    /**
     * 根据状态监测是否有开台
     * @author jianglei
     * 日期 2016年12月22日 下午2:59:22
     * @return
     */
    int isExists();
    //获取时间段之内的数据
    public List<Map<String,Object>> getOWByDate(String startTime,String endTime);

    public List<Map<String,Object>> getPCountAndCNum(String startTime,String endTime,Integer cbis);
}
