package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscountQuery;

public interface DgItemTypeDiscountService extends GenericService<DgItemTypeDiscount,Integer>{
    
    /**
     * 
     * 更具小类id获取是否打折
     * @param id
     * @return
     */
    int getCountByItemType(Integer id);
    
    
    
    /*
     * 
     * 获取所有数据
     */
    
    List<DgItemTypeDiscountQuery> getAll();
    
    
    /**
     * 
     * 批量修改
     * @param record
     */
    void updateBySrcList(List<DgItemTypeDiscount> record);
    
    int getDiscountByDgId(Integer id);
    
    int deleteByGateItemId(int id);
    /**
	 * 数据同步相关操作
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:05:11
	 * @param listItemType
	 */
    void synData(List<DgItemTypeDiscount> listObj);
}
