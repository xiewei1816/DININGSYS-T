package com.yqsh.diningsys.web.dao.api;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.api.DgPreOrderbillColor;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;
@Repository
public interface DgPreOrderbillColorMapper extends GenericDao<DgPreOrderbillColor,String>{
	
    /**
     * 更新预点单数量
     * @param dgPreOrderbill
     * @return
     */
	Integer updateItemCount(DgPreOrderbillColor dgPreOrderbill);
	
	/**
	 * 批量插入
	 * @param DgPreOrderbillColors
	 * @return
	 */
	Integer insertBatch(@Param("list")List<DgPreOrderbillColor> dgPreOrderbillColors);
	
	/**
	 * 查询是否有当前营业流水预点单
	 * @param id
	 * @return
	 */
	List<DgPreOrderbillColor> selectByWaterId(Integer id);
	
	/**
	 * 查询有相同流水和品项id的预点单
	 * @param dgPreOrderbill
	 * @return
	 */
	DgPreOrderbillColor selectByWaterIdAndItemFileId(DgPreOrderbillColor dgPreOrderbillColor);
	
	/**
	 * 计算大于预定单的数量
	 * @param dgPreOrderbillColor
	 * @return
	 */
	Integer selectCountByPreAndItemFileId(DgPreOrderbillColor dgPreOrderbillColor);
	
	/**
	 * 按时间搓查询
	 */
	List<DgPreOrderbillColor>  selectByPreNum(String owNum);
	
	
	DgOpenWater selectWaterInfoByPreNum(String owNum);
	
	/**
	 * 根据waterid算出未上菜数量
	 * @param waterId
	 * @return
	 */
	Integer selectCountByWaterId(Integer waterId);
}