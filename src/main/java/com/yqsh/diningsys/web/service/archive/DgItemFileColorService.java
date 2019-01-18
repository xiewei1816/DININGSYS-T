package com.yqsh.diningsys.web.service.archive;

import java.util.List;

import com.yqsh.diningsys.web.model.archive.DgItemFileColor;

public interface DgItemFileColorService {
	
    int deleteByPrimaryKey(String id);

    int insert(DgItemFileColor record);

    int insertSelective(DgItemFileColor record);

    DgItemFileColor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DgItemFileColor record);

    int updateByPrimaryKey(DgItemFileColor record);
    
	List<DgItemFileColor> selectAllItemFileColor();
}
