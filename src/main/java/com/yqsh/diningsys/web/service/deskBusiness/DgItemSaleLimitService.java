package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.Date;
import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemSaleLimitS;

public interface DgItemSaleLimitService extends GenericService<DgItemSaleLimit,Integer>{
    /**
     *  根据事件获取id
     */
    DgItemSaleLimit selectByDate(Date date);
    /**
     * 查询是否当日数据存在
     */
    int getCountByData(Date date);
    int insertBackId(DgItemSaleLimit record);
    int saveItemSaleLimit(String data);
    void synItemSaleLimit(List<DgItemSaleLimit> listItemSaleLimit,List<DgItemSaleLimitS> listItemSaleLimits);
}