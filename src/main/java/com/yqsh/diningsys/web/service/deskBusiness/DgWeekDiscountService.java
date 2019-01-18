package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgWeekDiscount;

public interface DgWeekDiscountService extends GenericService<DgWeekDiscount,Integer>{
    List<DgWeekDiscount> selectAll();
    int updataByDelete(String ids);
    DgWeekDiscount selectByName(String name);
}