package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgProMethodsType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DgProMethodsTypeMapper extends GenericDao<DgProMethodsType,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgProMethodsType record);

    int insertSelective(DgProMethodsType record);

    DgProMethodsType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgProMethodsType record);

    int updateByPrimaryKey(DgProMethodsType record);

    List<DgProMethodsType> selelctAllTypes();

    List<DgProMethodsType> selectAllDataPage(DgProMethodsType record);

    Integer countAllData(DgProMethodsType record);

    void deleteData(List<Integer> ids);
}