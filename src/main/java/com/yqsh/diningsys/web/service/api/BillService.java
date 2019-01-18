package com.yqsh.diningsys.web.service.api;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgReserveManager;
import com.yqsh.diningsys.web.model.archive.TbRfct;

public interface BillService {
	/*
	 * 
	 * 获取所有营业单团队信息(占用/埋单状态)
	 */
	List<Map<String,Object>> selectAllTeamMember(Integer pos);


	/**
	 * 创建营业流水时调用
	 * 客位消费信息
	 * seatId 桌位id
	 * number 开台人数
	 *
	 * 此时  不能 计算   服务费按照消费金额比例  包房费按照消费比例
	 *
	 */
	Map<String,Object> selectSeatConsumeInfo(Integer seatId,String number);

	/**
	 * 用于结账时重新计算
	 * openId 营业流水id
	 *
	 *
	 * 此时能 计算   服务费按照消费金额比例  包房费按照消费比例
	 */
	Map<String,Object> selectSeatConsumeByOpenId(String openId);

	/**
	 * 计算菜品价格
	 * itemIds 品项id list
	 */
	Map<String, Object> getDishPrice(Integer itemId,Integer orgId,Integer areaId,double number,Integer waterId,boolean isOpenTeam,boolean isYxe);

	/**
	 * 开单操作
	 */
	Map<String,Object>  openBill(String operCode,String eatNumber,String waiterId,String deposit,String marketingId,String seatId,
								 String queueNumber,String teamMemberInfo,String openPos,String seatLable,String gradeId,String ydId);

	/**
	 * 团队开单
	 */
	Map<String,Object> openBillTeam(String operCode,String openPos,List<Map<String,Object>> orgs,String ydId);

	/**
	 * 获取所有菜品,根据桌位id
	 * @param seatId
	 * @return
	 */
	List<Map<String,Object>> selectAllItem(Integer seatId);

	/**
	 * 获取所有菜品,根据桌位id,平板
	 * @param seatId
	 * @return
	 */
	List<Map<String,Object>> selectYxeAllItem(Integer seatId);


	/**
	 * 获取所有菜品,按品项小类分类
	 * @param seatId
	 * @return
	 */
	List<Map> selectAllItemGroupByXl(Integer seatId);

	/**
	 * 加单操作
	 * openId 营业流水id
	 *
	 *
	 * 此时能 计算   服务费按照消费金额比例  包房费按照消费比例
	 * @throws Exception
	 */
	Map<String,Object> addBill(String operCode,List<Map<String,Object>> org,String openId,String type,String zdbz,boolean isYxe);


	/**
	 * 赠单操作
	 * openId 营业流水id
	 *
	 *
	 * 此时能 计算   服务费按照消费金额比例  包房费按照消费比例
	 */
	Map<String,Object> insertGiveBill(String operCode,List<Map<String,Object>> org,String openId,String zdbz);




	/**
	 * 退操作
	 */
	Map<String,Object> insertBackBill(String operCode,String openNumber,List<Map<String,Object>> org,String type);


	/**
	 * 团队退菜
	 */
	Map<String,Object> insertTeamBackBill(String operCode,String teamMember,List<String> seatIds,List<Map<String,Object>> org,String type);


	Map<String, Object> selectTcDetail(Integer tcId);


	List<TbRfct> selectAllBackBillInfo();

	List<Map<String,Object>> getWaterAllItemInfo(String openId);


	List<Map<String,Object>> getTeamBackItemInfo(String teamMember,List seatIds);


	/**
	 * 催单
	 * @param operCode
	 * @param list
	 * @return
	 */
	Map<String, Object> insertReminderBill(String operCode,List<Map> list);


	List<Map<String,Object>> getReminderItemInfo(String openNum);

	/**
	 *
	 * 当前营业单起菜信息
	 * @param openNum
	 * @return
	 */
	List<Map<String,Object>> getQcItemInfo(String openNum);

	/**
	 *
	 * 获取当前营业单类别列表
	 * @param openNum
	 * @return
	 */
	List<Map<String,Object>> getQcItemTypeInfo(List<Map<String,Object>> src);

	/**
	 * 起菜方式中心执行函数
	 * @param obj
	 * @param type
	 * @return
	 */

	Map<String,Object> insertQcBill(Object obj,String type,String operCode);

	/**
	 *
	 * 更改客位处理
	 * @param openNum 营业流水号
	 * @param number 人数
	 * @return
	 */
	Map modifyGgkw(String operCode,String openNum,int number,String waiterId,String isJsPx);

	/**
	 *
	 * 根据团队信息获取团队所有桌位
	 */
	List<DgConsumerSeat> selectTeamSeatMember(String member);


	/**
	 * 返回当前团队初始化状态的所有流水信息
	 * @param member
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> getTeamMemberInfo(String member,List ids);

	/**
	 *
	 * 团队加单
	 * @param operCode 操作人员
	 * @param org 参数组合
	 * @param ids 加单流水组合
	 * @param type 1加单厨打/ 2加单不厨打
	 * @return
	 */
	Map<String, Object> addTeamBill(String operCode,
									List<Map<String, Object>> org, List openWaters, String zdbz,boolean isYxe,String type,String spm);


	Map<Integer, Object> getPxDzFaPrice(Integer id);

	/**
	 * 获取现在时间所属市别
	 * @return
	 */
	Integer getNowBisId();


	DgReserveManager getReserverInfoBySeatId(Integer seatId);


	String updateInveDate(Map orgs);


	/**
	 * 加单操作(预加单 易小二加单)
	 * openNumber 营业流水号
	 * @throws Exception
	 */
	Map<String,Object> addYxePreBill(List<Map<String,Object>> org,String openNumber);


	/**
	 * 加单操作(线上扫码点餐)
	 * waterNum 营业流水号
	 * 此时能 计算   服务费按照消费金额比例  包房费按照消费比例
	 * @throws Exception
	 */
	Map<String,Object> addBill(List<Map<String,Object>> org,String waterNum);

	/**
	 * 线上订单处理
	 */
	void insertOnlineOrderBill(String mq);


	/**
	 * 线上加单操作(预加单模式)
	 * openNumber 营业流水号
	 * @throws Exception
	 */
	Map<String,Object> addOnlinePreBill(List<Map<String,Object>> org,String openNumber);

	/**
	 * 创享平台线上点餐操作
	 */
	Map<String,Object> insertCxptOrderBill(List<Map> items,DgConsumerSeat seat);
}
