package com.yqsh.diningsys.web.dao.businessMan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingSchemeS;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemMemberDiscountS;
@Repository
public interface DgSeatChargingSchemeSMapper extends GenericDao<DgSeatChargingSchemeS,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgSeatChargingSchemeS record);

    int insertSelective(DgSeatChargingSchemeS record);

    DgSeatChargingSchemeS selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgSeatChargingSchemeS record);

    int updateByPrimaryKey(DgSeatChargingSchemeS record);
    
    
    List<DgSeatChargingSchemeS> seleByPid(Integer id);
    
    List<DgSeatChargingSchemeS> seleByPidNoSd(Integer id);
    void deleteIds(List<Integer> s);
    void deleteAllByPid(Integer id);
}