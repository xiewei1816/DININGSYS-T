package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;

public interface DgItemMemberDiscountService extends GenericService<DgItemMemberDiscount,Integer>{
    int insertBackId(DgItemMemberDiscount record);
    Page<DgItemMemberDiscount> getAllMemberDishcount(DgItemMemberDiscount dgImportantActivityDiscount);
    void deleteIds(String s);
    void publish(Integer id);
    List<DgItemMemberDiscount> seleAll();
    
    void trash(String s);
    void restore(String s);
    List<Map<String,Object>> reminder();
    DgItemMemberDiscount selectByItem(DgItemMemberDiscount src);
    int seleNameCode(DgItemMemberDiscount src);
    
    int insertP(HttpServletRequest request,
			HttpServletResponse response, DgItemMemberDiscount dim);
    
    void insertChild(HttpServletRequest request,
			HttpServletResponse response);
    void synItemMemberDiscount(List<DgItemMemberDiscount> listMemberDisc,List<DgItemMemberDiscountS> listMemberDiscs);
}
