package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgServing;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DgServingMapper extends GenericDao<DgServing,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgServing record);

    int insertSelective(DgServing record);

    DgServing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgServing record);

    int updateByPrimaryKey(DgServing record);

    Integer countAllData(DgServing dgServing);

    List<DgServing> getAllData(DgServing dgServing);

    int deletDataWithLogic(List<Integer> ids);

    int deletData(List<Integer> ids);
    
    List<DgServing> getAll();

    String selectMaxNum();
}