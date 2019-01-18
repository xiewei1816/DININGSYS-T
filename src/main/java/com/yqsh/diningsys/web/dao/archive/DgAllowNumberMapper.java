package com.yqsh.diningsys.web.dao.archive;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgAllowNumber;
@Repository
public interface DgAllowNumberMapper extends GenericDao<DgAllowNumber,Integer>{
    int insert(DgAllowNumber record);
    
    int deleteById(List orgs);
    
    int updateById(DgAllowNumber record);
   
    Integer getCount(DgAllowNumber record);
    
    DgAllowNumber selectById(Integer id);
    
    List<DgAllowNumber> getListByPage(DgAllowNumber record);

    List<DgAllowNumber> getAllList();


}