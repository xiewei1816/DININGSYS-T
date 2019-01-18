package com.yqsh.diningsys.web.dao.businessMan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.businessMan.DgSeatChargingScheme;
@Repository
public interface DgSeatChargingSchemeMapper extends GenericDao<DgSeatChargingScheme,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgSeatChargingScheme record);

    int insertSelective(DgSeatChargingScheme record);

    DgSeatChargingScheme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgSeatChargingScheme record);

    int updateByPrimaryKey(DgSeatChargingScheme record);
    
    
    int insertBackId(DgSeatChargingScheme record);
    /**
     * 
     * 分页
     */
    int countAllData(DgSeatChargingScheme record); //获取总数量
    List<DgSeatChargingScheme> getAllData(DgSeatChargingScheme record);
    List<DgSeatChargingScheme> seleAll();
    void deleteIds(List<Integer> ids);
    void update(List<Integer> ids);
    void trash(List<Integer> ids);
    void restore(List<Integer> ids);
    
    List<DgSeatChargingScheme> seleAllNotContainSelf(Integer id);
}