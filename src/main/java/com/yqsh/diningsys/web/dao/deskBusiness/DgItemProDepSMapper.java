package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemProDepS;
import com.yqsh.diningsys.web.model.deskBusiness.DgPlacePriceS;
@Repository
public interface DgItemProDepSMapper extends GenericDao<DgItemProDepS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemProDepS record);

    int insertSelective(DgItemProDepS record);

    DgItemProDepS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemProDepS record);

    int updateByPrimaryKey(DgItemProDepS record);
    
    
    List<DgItemProDepS>  selectByPlaceParentId(Integer id);
    void deleteIds(List<Integer> ids);
    int deleteAll();
    int selectByEarchId(Map<String, Object> obj);
    
    int deleteByItemId(int id);
    
    int insertChilds(List<DgItemProDepS> record);
}