package com.yqsh.diningsys.web.dao.api;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.DgOpenWater;

@Repository
public interface DgPreOrderbillMapper extends GenericDao<DgPreOrderbill,String>{
    /**
     * 更新预点单数量
     * @param dgPreOrderbill
     * @return
     */
	Integer updateItemCount(DgPreOrderbill dgPreOrderbill);
	
	/**
	 * 查询是否有当前营业流水预点单
	 * @param id
	 * @return
	 */
	List<DgPreOrderbill> selectByWaterId(Integer id);
	
	/**
	 * 查询有相同流水和品项id的预点单
	 * @param dgPreOrderbill
	 * @return
	 */
	List<DgPreOrderbill> selectByWaterIdAndItemFileId(DgPreOrderbill dgPreOrderbill);
	
	/**
	 * 批量插入
	 * @param dgPreOrderbills
	 * @return
	 */
	Integer insertBatch(@Param("list")List<DgPreOrderbill> dgPreOrderbills);
	
	/**
	 * 按时间搓查询
	 */
	List<DgPreOrderbill>  selectByPreNum(String owNum);
	
	/**
	 * 结算时按流水删除数据
	 */
	Integer deleteByWaterId(Integer waterId);
	
	DgOpenWater selectWaterInfoByPreNum(String owNum);
	
	/**
	 * 查询预点单品项和
	 * @param dgPreOrderbill
	 * @return
	 */
	DgPreOrderbill selectAccountByItemFileId(DgPreOrderbill dgPreOrderbill);
	
	/**
	 * 根据waterid算出未上菜数量
	 * @param waterId
	 * @return
	 */
	Integer selectCountByWaterId(Integer waterId);
}