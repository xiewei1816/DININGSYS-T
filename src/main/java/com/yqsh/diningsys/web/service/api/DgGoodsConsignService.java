package com.yqsh.diningsys.web.service.api;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgGoodsConsign;

public interface DgGoodsConsignService {
	
	/**
	 * 查询所有寄存物品信息
	 * @param id
	 * @param isDel 删除状态
	 * @param jcStartTime 寄存开始时间
	 * @param jcEndTime 寄存结束时间
	 * @param goodsName 物品名称
	 * @param goodsColor 颜色
	 * @param gcPos 寄存POS
	 * @param qzStartTime 取走开始时间
	 * @param qzEndTime 取走结束时间
	 * @param clientName 客户名称
	 * @param clientSeat 客位
	 * @param expArea 消费区域
	 * @param aboveDays 超出截止日期X天
	 * @param goodsExpirationDate 超出保存期
	 * @param gcEndTime 超出寄存截止日期
	 * @param gcFlag 寄存状态
	 * @return
	 */
	List<DgGoodsConsign> selectGoodsConsign(Integer id, String isDel, String jcStartTime, String jcEndTime,
			String goodsName, String goodsColor, String gcPos,
			String qzStartTime, String qzEndTime, String clientName,
			String clientSeat, String expArea, Integer aboveDays,
			String goodsExpirationDate, String gcEndTime,String gcFlag);
	
	/**
	 * 寄存物品
	 * @param clientName 客户名称
	 * @param clientPhone 客户电话
	 * @param clientSeat 客位
	 * @param goodsType 物品寄存种类
	 * @param goodsCode 物品编号
	 * @param goodsName 物品名称
	 * @param goodsNumber 数量
	 * @param goodsSpecification 规格
	 * @param goodsColor 颜色
	 * @param goodsExpirationDate 保质截止日期
	 * @param goodsExplain 说明
	 * @param gcFlag 寄存状态
	 * @param gcPos 寄存操作POS
	 * @param gcOperator 寄存操作员
	 * @param gcStartTime 寄存时间
	 * @param gcEndTime 寄存截止时间
	 * @param gcAddress 寄存位置
	 * @param clWay 处理方式
	 * @param clPos 处理操作POS
	 * @param clOperator 处理操作员
	 * @param clExplain 处理说明
	 * @return
	 */
	int insertGoodsConsign(String clientName, String clientPhone,
			String clientSeat, String goodsType, String goodsCode,
			String goodsName, Integer goodsNumber, String goodsSpecification,
			String goodsColor, String goodsExpirationDate, String goodsExplain,
			String gcFlag, String gcPos, String gcOperator, String gcStartTime,
			String gcEndTime, String gcAddress,String clWay, String clPos,
			String clOperator, String clExplain);
	
	/**
	 * 寄存物品修改
	 * @param id 
	 * @param clientName 客户名称
	 * @param clientPhone 客户电话
	 * @param clientSeat 客位
	 * @param goodsType 物品寄存种类
	 * @param goodsCode 物品编号
	 * @param goodsName 物品名称
	 * @param goodsNumber 数量
	 * @param goodsSpecification 规格
	 * @param goodsColor 颜色
	 * @param goodsExpirationDate 保质截止日期
	 * @param goodsExplain 说明
	 * @param gcFlag 寄存状态
	 * @param gcPos 寄存操作POS
	 * @param gcOperator 寄存操作员
	 * @param gcStartTime 寄存时间
	 * @param gcEndTime 寄存截止时间
	 * @param gcAddress 寄存位置
	 * @param clWay 处理方式
	 * @param clPos 处理操作POS
	 * @param clOperator 处理操作员
	 * @param clExplain 处理说明
	 * @return
	 */
	int updateGoodsConsign(Integer id,String clientName, String clientPhone,
			String clientSeat, String goodsType, String goodsCode,
			String goodsName, Integer goodsNumber, String goodsSpecification,
			String goodsColor, String goodsExpirationDate, String goodsExplain,
			String gcFlag, String gcPos, String gcOperator, String gcStartTime,
			String gcEndTime, String gcAddress,String clWay, String clPos,
			String clOperator,String clExplain);
	
	/**
	 * 添加寄存物品取走信息
	 * @param id 
	 * @param gcFlag 寄存状态
	 * @param qzTime 取走时间
	 * @param qzPos 取走操作POS
	 * @param qzOperator 取走操作员
	 * @return
	 */
	int addGoodsConsignByQz(Integer id,String gcFlag, String qzTime, String qzPos,String qzOperator);
	
	/**
	 * 删除寄存物品信息
	 * @param editIds 以逗号“,”隔开
	 * @return
	 */
	int deleteGoodsConsign(String editIds);
	
	/**
	 * 寄存物品信息回收站
	 * @param editIds 以逗号“,”隔开
	 * @return
	 */
	int deleteGoodsConsignTrash(String editIds);
	
	/**
	 * 还原回收站寄存物品信息
	 * @param editIds 以逗号“,”隔开
	 * @return
	 */
	int replyGoodsConsign(String editIds);
	
	/** 物品寄存种类 增、删、改、查 **/
	int insertGoodsType(String gtName, String isRemind);
	int deleteGoodsType(Integer id);
	int updateGoodsType(Integer id, String gtName, String isRemind);
	List<Map<String,Object>> selectGoodsType();
	
}
