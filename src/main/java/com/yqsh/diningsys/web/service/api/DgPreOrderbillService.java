package com.yqsh.diningsys.web.service.api;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.api.DgPreOrderbill;

public interface DgPreOrderbillService extends GenericService<DgPreOrderbill,String>{
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
}
