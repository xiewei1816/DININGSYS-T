package com.yqsh.diningsys.web.dao.api;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.archive.DgItemFile;

import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-12-15 13:16
 */
@Repository
public interface APIReMoMapper {

    /**
     * 将清扫状态的客桌的状态初始化
     * @param id
     */
    Integer resetSeatState(Integer id);

    /**
     * 修改客位标识
     * @param param
     * @return
     */
    Integer modifySeatIdentify(Map param);

    /**
     * 替换服务员/营销员
     * @param param
     * @return
     */
    Integer ReplaceWaiter(Map param);

    /**
     * 替换服务员的点单数据
     * @param param
     * @return
     */
    Integer ReplaceWaiterService(Map param);

    /**
     * 品项变价
     */
    void modifyItemFilePrice(Map param);

    /**
     * 修改数量
     * @param param
     */
    void modifyItemFileNumber(Map param);

    /**
     * 品项赠送操作
     * @param param
     */
    void modifyDishesFree(Map param);

    /**
     * 修改营业流水状态
     * @param param
     */
    void modifyOpenWaterState(Map param);

    /**
     *
     * 沽清增加品项明细
     * @param list
     * @return
     */
    List<DgItemFile> selecAllGqxzPx(List list);

    /**
     * 根据团队号,获取团队人数
     * @param teamMember
     * @return
     */
    Integer seleTeamMemberCount(String teamMember);

    /**
     * 查询一个团队下的营业流水
     * @param teamMember
     * @return
     */
    DgOpenWater seleOpenWaterByTeamMember(String teamMember);

    /**
     * 查询所有的结算锁定以及埋单的营业流水数据
     */
    List<DgOpenWater> selectAllLockedData();

    void updateOWCustomerInfo(Map<String, Object> map);

    void modifyUnlock(Map<String, Object> map);


    void modifyOpenWaterSubtotal(Map<String, Object> map);

    void updateOpenWaterSubtotal(DgOpenWater param);
    
    
    List<DgOpenWater> seleAllOpenWaterByTeamMember(String teamMember);
    
    
    List<DgOwConsumerDetails> selectItemFileBackOws(Map map);

    DgOwConsumerDetails selectItemFileByItemId(Map map);
}
