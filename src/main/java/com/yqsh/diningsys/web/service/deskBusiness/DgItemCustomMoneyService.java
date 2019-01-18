package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.BgItemCustomMoneyName;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemCustomMoney;


public interface DgItemCustomMoneyService extends GenericService<DgItemCustomMoney,Integer>{
    List<DgItemCustomMoney> getAllByCustomNameId(int id);
    int deleteIds(String ids);
    int deleteByItemIds(String ids);
    List<DgItemCustomMoney> selectAllByItemIds(List<Integer> ids);
    int deleteByCustomId(Integer id);
    int deleteByItemId(int id);
    void insertChilds(List<DgItemCustomMoney> src);
}