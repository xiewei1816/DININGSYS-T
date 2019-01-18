package com.yqsh.diningsys.web.dao.deskBusiness;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.enums.DeskbusinessEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

/**
 *
 *
 * Created by mrren on 2016/11/14.
 */
@Repository
public interface DeskBusinessSettingMapper extends GenericDao<DeskBusinessSetting,Integer> {

    //获取客座设置
    DeskBusinessSetting getDeskBusinessSetting();

    //更新客座设置
    void updateDeskBusinessSetting(DeskBusinessSetting deskBusinessSetting);

    //初始化客座设置，如果数据库为空，需要先初始化一个客座设置信息
    void addDeskBusinessSetting(DeskBusinessSetting deskBusinessSetting);

    //获取流水的序号
    int getSerialRulNumber(@Param(value="flowType") String flowType, @Param(value="isStartWithOne") String isStartWithOne);

    //获取流水号个数
    int getSerialRulNumberCount();

    //初始化流水号编号
    void addFlowNumberOfYY();
    void addFlowNumberOfJS();
    void addFlowNumberOfJB();
    void addFlowNumberOfTDHM();
    void addFlowNumberOfFW();
    void addFlowNumberOfYD();

    void deleteFlowNumber();

    //更新流水号记录
    void updateSerialRul(@Param(value="maxNum") int maxNum, @Param(value="flowType") String flowType);
    
    
    //获取流水号
    String getAutoOwNum(@Param(value="owNumber") int owNumber, @Param(value="owType") String owType,@Param(value="isBeginWithOne") String isBeginWithOne, @Param(value="headStr") String headStr);

}
