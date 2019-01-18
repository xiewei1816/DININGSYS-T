package com.yqsh.diningsys.web.dao.permission;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.permission.SysPerItemfile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysPerItemfileMapper extends GenericDao<SysPerItemfile,Integer>{
    int insert(SysPerItemfile record);

    int insertSelective(SysPerItemfile record);

    void inserMultiData(Map param);

    void deleteMutliDataByZwIdAndType(Map param);

    List<DgItemFile> selectItemFileByZwCodeAndType(Map param);

    List<DgItemFileType> selectItemFileTypeByZwCodeAndType(Map param);
}