package com.yqsh.diningsys.web.service.api;

import java.util.List;
import java.util.Map;

/**
 * 查询打印
 * @author xiewei
 *
 */
public interface QueryPrintService {
	
	/**
	 * 根据团队编号获取团队成员
	 * @param teamMembers 团队编号
	 * @return
	 */
	Map<String, Object> selectTeamSeatMembersList(String teamMembers);
	
	/**
	 * 查询当前客单信息
	 * @param owNum 营业流水号
	 * @return
	 */
	Map<String,Object> selectGuestList(String owNum);
	
	/**
	 * 查询当前客单信息下品项信息
	 * @param id 营业流水id
	 * @return
	 */
	List<Map<String,Object>> selectGuestItemFileList(String id);

	/**
	 * 查询核对单据客位信息
	 * @param owNum 营业流水号
	 * @return
	 */
	Map<String, Object> selectCheckDocSeatList(String owNum);
	
	/**
	 * 查询单据信息
	 * @param owNum 营业流水号
	 * @return
	 */
	List<Map<String, Object>> selectCheckDocList(String owNum);

	/**
	 * 查询单据信息下品项信息
	 * @param sfId 服务单id
	 * @return
	 */
	List<Map<String, Object>> selectCheckItemFileList(String sfId);
	
	/**
	 * 查询团队信息
	 * @return
	 */
	List<Map<String, Object>> selectTeamMembersList();
	
	/**
	 * 根据团队编号查询团队信息树形数据
	 * @return 
	 */
	List<Map<String, Object>> selectTeamBySeatNameList(String teamMembers);

	/**
	 * 根据客位编号查询团队信息
	 * @param seatId 客位编号
	 * @param teamMembers 团队编号
	 * @param owNum 营业流水号
	 * @return
	 */
	List<Map<String, Object>> selectTeamList(String seatId,String teamMembers,String owNum);

	/**
	 * 根据营业流水号退出团队、撤销转账
	 * @param owNum 营业流水号
	 * @return
	 */
	int updWaterJoinTeamNotes(String owNum);

	/**
	 * 查询结算流水信息
	 * @param seatId 客位
	 * @param openBis 市别
	 * @param consArea 区域
	 * @param firstTime 营业日期
	 * @return
	 */
	List<Map<String, Object>> selectClearingList(String seatId, String openBis,
			String consArea,String firstTime);

	/**
	 * 根据结算流水查询结算流水基本信息
	 * @param cwId 结算流水号
	 * @return
	 */
	Map<String, Object> selectClearingBaseList(String cwId);

	/**
	 * 根据结算流水查询营业流水基本信息
	 * @param cwId 结算流水号
	 * @return
	 */
	List<Map<String, Object>> selectOpenWaterBaseList(String cwId);

	/**
	 * 根据营业流水ID查询结算方式信息
	 * @param id 营业流水ID
	 * @return
	 */
	List<Map<String, Object>> selectClearingWayBaseList(String id);
	
	/**
	 * 根据营业流水ID查询发票信息
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> selectReceiptFileList(String id);

	/**
	 * 根据座位查询核对信息营业流水信息
	 * @param seatId 客位ID
	 * @return
	 */
	List<Map<String, Object>> selectDocListOpenWater(Integer seatId);

	/**
	 * 查询当前客单座位信息
	 * @param seatId 客位ID
	 * @return
	 */
	Map<String, Object> selectSeatAreaList(Integer seatId);

	/**
	 * 撤销转账
	 * @param owNum 营业流水
	 * @return
	 */
	 Integer updRepealWater(String owNum);

	/**
	 * 根据转账备注查询转入、转出转账流水信息
	 * @param joinTeamNotes 转账备注
	 * @param expArea 消费区域
	 * @param clientSeat 客位
	 * @param owNum 营业流水
	 * @return
	 */
	List<Map<String, Object>> selectIntoOrOutOpenWaterList(String joinTeamNotes,String expArea,String clientSeat,String owNum);
	
	/**
	 * 根据转账流水查询品项信息
	 * @param owNum 营业流水
	 * @return
	 */
	List<Map<String, Object>> selectIntoOrOutItemFileList(String owNum);
	
	List<Map<String,Object>> getNoBalenceDataList();



}
