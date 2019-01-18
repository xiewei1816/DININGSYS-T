package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbZdbz;

@Repository
public interface TbZdbzMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TbZdbz record);

    int insertSelective(TbZdbz record);

    TbZdbz selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbZdbz record);

    int updateByPrimaryKey(TbZdbz record);

    TbZdbz selectByZdbzName(String ZdbzName);

    TbZdbz authentication(TbZdbz tbZdbz);

    List<TbZdbz> selectByConAndPage(Page<TbZdbz> page, Map params);

    int deleteByPrimaryKeys(List ids);

    List<TbZdbz> getListByPage(TbZdbz tbZdbz);

    Integer countListByPage(TbZdbz tbZdbz);

    TbZdbz getZdbzById(TbZdbz tbZdbz);

    int newInsert(TbZdbz tbZdbz);

    int update(TbZdbz tbZdbz);

    int deleteTrash(TbZdbz tbZdbz);

    int replyZdbz(TbZdbz tbZdbz);

    int delete(TbZdbz tbZdbz);

    List<TbZdbz> getAllList(TbZdbz tbZdbz);

    TbZdbz getZdbzByNum(String numbser);
    
}
