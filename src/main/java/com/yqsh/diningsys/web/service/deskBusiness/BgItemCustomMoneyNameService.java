package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;


public interface BgItemCustomMoneyNameService extends GenericService<BgItemCustomMoneyName,Integer>{
    /**
     * 
     * 获取所有
     */
    List<BgItemCustomMoneyName> getAll();
    int getCount(); //所有数目
    int getCountByItemCode(Integer itemId);
    int updateByItemCode(BgItemCustomMoneyName record);
}