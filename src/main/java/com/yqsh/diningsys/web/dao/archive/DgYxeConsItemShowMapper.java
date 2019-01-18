package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.web.model.archive.DgYxeConsItemShow;

public interface DgYxeConsItemShowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DgYxeConsItemShow record);

    int insertSelective(DgYxeConsItemShow record);

    DgYxeConsItemShow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgYxeConsItemShow record);

    int updateByPrimaryKey(DgYxeConsItemShow record);
}