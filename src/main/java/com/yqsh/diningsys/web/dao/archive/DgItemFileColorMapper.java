package com.yqsh.diningsys.web.dao.archive;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.archive.DgItemFileColor;
@Repository
public interface DgItemFileColorMapper {
    int deleteByPrimaryKey(String id);

    int insert(DgItemFileColor record);

    int insertSelective(DgItemFileColor record);

    DgItemFileColor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DgItemFileColor record);

    int updateByPrimaryKey(DgItemFileColor record);
    
	List<DgItemFileColor> selectAllItemFileColor();
}