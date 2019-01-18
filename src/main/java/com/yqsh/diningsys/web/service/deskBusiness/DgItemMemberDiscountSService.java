package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;

public interface DgItemMemberDiscountSService extends GenericService<DgItemMemberDiscountS,Integer>{
    List<DgItemMemberDiscountS> seleByPid(Integer id);
    void deleteIds(String s);
    int deleteByItemId(int id);
    void insertChilds(List<DgItemMemberDiscountS> s);
}
