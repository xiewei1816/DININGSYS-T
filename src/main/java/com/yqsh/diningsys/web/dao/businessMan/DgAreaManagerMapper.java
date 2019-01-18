package com.yqsh.diningsys.web.dao.businessMan;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.businessMan.DgAreaManager;
@Repository
public interface DgAreaManagerMapper extends GenericDao<DgAreaManager,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgAreaManager record);

    int insertSelective(DgAreaManager record);

    DgAreaManager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgAreaManager record);

    int updateByPrimaryKey(DgAreaManager record);
    
    
    
    int insertBackId(DgAreaManager record);
    int selectCountByAreaId(Integer id);
    DgAreaManager selectByAreaId(Integer id);
    List<DgAreaManager> selectAll();
    int getAllCount();
    
    int deleteByAreaId(Integer areaId);
}