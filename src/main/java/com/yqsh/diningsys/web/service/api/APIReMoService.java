package com.yqsh.diningsys.web.service.api;

import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;

import java.util.List;
import java.util.Map;

/**
 * 前台更换修改接口service
 *
 * @author zhshuo create on 2016-12-15 13:07
 */
public interface APIReMoService {

    /**
     * 将清扫状态的客桌的状态初始化
     * @param id
     */
    Integer modifyResetSeatState(Integer id);

    /**
     * 修改客位标识
     * @param id
     * @return
     */
    Integer modifySeatIdentify(Integer id,String seatLable,Integer isVip,Integer isInternal);

    /**
     * 更换服务员/营销员
     * @param owNum 操作的营业流水
     * @param newWaiterId 新服务员ID
     * @param newMaketingStaff 新营销员ID
     * @param replaceService 是否替换
     */
    void modifyReplaceWaiter(String owNum,Integer newWaiterId,Integer newMaketingStaff,Integer replaceService);

    /**
     *
     * @return
     */
    List<DgOwConsumerDetails> selectAllItemFile();

    /**
     * 品项变价
     */
    void modifyItemFilePrice(String userCode,String itemFilePrice, SysUser sysUser, Integer variablePriceType,String owNum);

    /**
     * 修改数量
     * @param owNum
     * @param modifyData
     */
    void modifyItemFileNumber(String userCode,String owNum, String modifyData);

    /**
     * 品项赠送
     * @param dishFreeData
     * @param pos
     * @param openWater
     * @param modifyType
     */
    void modifyDishesFree(String userCode,String dishFreeData, Integer pos, SysUser sysUser, String openWater, Integer modifyType,Integer freeType,String reason);

    /**
     * 单品转台
     * @param targetOpenWater 单品转台的目标营业流水
     * @param operaOpenWater 单品转台的的营业流水
     * @param maps
     */
    Map modifyDishesTurntable(String userCode,String targetOpenWater, String operaOpenWater, List<Map> maps);

    /**
     * 撤销埋单
     * @param owNum
     */
    void modifyCancelPayState(String owNum);

    List<DgOpenWater> selectAllLockedData();

    /**
     * 手工锁定
     * @param dgOpenWater
     * @param userCode
     * @param state
     * @param pos
     */
    void modifyManualLocking(DgOpenWater dgOpenWater,String userCode,Integer state,Integer pos);

    /**
     * 解锁操作
     * @param dgOpenWater
     * @param state
     */
    void modifyUnlock(DgOpenWater dgOpenWater,Integer state);

    /**
     * 登记顾客信息
     * @param openWaterInfo
     * @param customerData
     */
    void updateOWCustomerInfo(Map openWaterInfo,String customerData);
}
