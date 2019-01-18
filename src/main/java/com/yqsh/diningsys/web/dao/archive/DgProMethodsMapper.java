package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgProMethods;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DgProMethodsMapper extends GenericDao<DgProMethods,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgProMethods record);

    int insertSelective(DgProMethods record);

    DgProMethods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgProMethods record);

    int updateByPrimaryKey(DgProMethods record);

    Integer countAllData(DgProMethods dgProMethods);

    List<DgProMethods> selectAllData(DgProMethods dgProMethods);

    void deleteData(List<Integer> ids);

    List<DgProMethods> getPublicProMethodsWithOutIds(List<Integer> ids);

    List<DgProMethods> getProMethodsByType(Map param);

    List<DgProMethods> getPubProMeInIds(List<Integer> ids);
    
    DgProMethods getProMethodByNumber(String number);

}