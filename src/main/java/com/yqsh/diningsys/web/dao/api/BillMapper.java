package com.yqsh.diningsys.web.dao.api;

import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOwConsumerDetails;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BillMapper {
	/*
	 * 
	 * 获取所有营业单团队信息(占用/埋单状态)
	 */
	List<Map> selectAllTeamMember(@Param("list") List<Integer> list);
	
	/**
	 * 转台获取流水selectAllTeamMemberToZt
	 */
	List<Map> selectAllTeamMemberToZt(@Param("list") List<Integer> list);
	
	/**
	 * 插入营业流水
	 */
	int insertOpenWater(DgOpenWater water);
	/**
	 * 
	 * 插入服务流水
	 */
	int insertOwServiceWater(Map param);
	
	/**
	 * 更新营业流水
	 */
	int updateOpenWaterPrimaryKey(DgOpenWater water);
	
	List<Map<String,Object>> selectAllItem(Integer seatId);
	
	/**
	 * 
	 * @param seatId
	 * @return
	 */
	
	
	List<Map<String,Object>> selectAllItemCXsqg(Integer seatId);
	
	List<Map<String,Object>> selectYxeAllItem(Integer seatId);
	
	
	List<DgItemFile> selectTcAddItemFile(Map param);
	
	List<Map<String,Object>> selectItemByWater(String waterNum);
	
	
	List<Map<String,Object>> selectQcItemByWater(String waterNum);
	
	
	List<Map<String,Object>> selectTeamSeatInfo(Map orgs);
	
	
	List<Map<String,Object>> selectTeamSeatInfoNotInWaterIds(Map orgs);
	
	/**
	 * 根据团队号(和指定的座位id)找出存在的流水
	 * @param orgs
	 * @return
	 */
	List<Map<String,Object>> selectWaterByTeamMember(Map orgs);
	
	
	/**
	 * 获取当前流水小类品项明细
	 * @param orgs
	 * @return
	 */
	List<Map<String,Object>> selectQcItemByWaterType2(Map orgs);
	/*
	 * 
	 * 
	 * 根据营业流水和品项id查找出消费明细
	 */
	List<Map<String,Object>> selectConsumerDetailByWaterIdAndItemFileId(Map orgs);
	
	
	/**
	 * 获取开单自增品项明细
	 * 只真对增加人数
	 * 方便减少
	 * @param orgs
	 * @return
	 */
	List<Map<String,Object>> selectZdConsumerDetailByWaterIdAndItemFileId(Map orgs);
	
	/*
	 * 根据服务号算出最终数量(可能退单后数量,可能没退过单)
	 * 
	 */
	Double selectConsumerDetailByOwId(Map orgs);
	
	
	/**
	 * 催菜调用
	 */
	void updateReminder(Map orgs);
	
	
	/**
	 * 起菜方式1
	 */
	void updateQcType1(Map orgs);
	/**
	 * 起菜方式2
	 */
	void updateQcType2(Map orgs);
	
	
	List<Map<String,Object>> getServiceIncreaItemByOwId(Integer owId);
	
	
	List<DgOwConsumerDetails> getTotalIncreaItemByOwId(Map orgs);
	
	Double getTotalIncreCount(Map orgs);
	
	/**
	 * 根据团队号获取主桌位
	 * @param member
	 * @return
	 */
	Integer getMemberMainSeat(String member);
	
	
	List<DgConsumerSeat> selectTeamSeatMember(String member);
	
	
	
	/**
	 * 根据流水，获取已点具体品项数量，方便计算促销价格
	 */
	Double getWaterItemCountByItemId(Map orgs);
	
	
	
	/**
	 * 根据客位id和品项id,获取区域限销售数量
	 */
	Integer selectLimitItemCountBySeatId(Map orgs);
	
	
	Integer selectServerTypeByServerId(@Param("id") Integer id);
	
	String updateInveDate(Map orgs);
	
	
	Integer selectUserByCardNo(@Param("cardNo") String cardNo);
	
	Integer selectSeatByCard(@Param("cardNo") String cardNo,@Param("seatId") String seatId);
	
	/**
	 * 	查询易小二点餐，锅底p
	 */
	List<Integer> selectGdFromItemIds(List<Map<String,Object>> list);

    List<Map<String,Object>> selectItemByWater_new(@Param("owNum") String owNum);
    
    
    List<Map<String,Object>> selectAllItemCXsqgOutOfQy(Map orgs);
}
