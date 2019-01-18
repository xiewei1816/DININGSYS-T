package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgFlavor;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface DgFlavorMapper extends GenericDao<DgFlavor,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgFlavor record);

    int insertSelective(DgFlavor record);

    DgFlavor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgFlavor record);

    int updateByPrimaryKey(DgFlavor record);
    
    List<DgFlavor> getAllBeansWithOutRoot();
    
    List<DgFlavor> getAllBeans();
    
    List<DgFlavor> getFlavorByParentId(Integer id);
    
    DgFlavor getFlavorByNumber(String number);
    
    List<DgFlavor> getTreeBean();
}