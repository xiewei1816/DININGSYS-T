package com.yqsh.diningsys.web.dao;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgNutrition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DgNutritionMapper extends GenericDao<DgNutrition,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgNutrition record);

    int insertSelective(DgNutrition record);

    DgNutrition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgNutrition record);

    int updateByPrimaryKey(DgNutrition record);

    List<DgNutrition> selectAllDgNutrition(DgNutrition record);

    void deleteNutritionByIds(List<Integer> id);

    List<DgNutrition> getNutritionNotInIds(List<Integer> id);

    List<DgNutrition> getNutritionInIds(List<Integer> id);

}