package com.yqsh.diningsys.web.dao.businessMan;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;

@Repository
public interface TbFydjMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbFydj record);

    int insertSelective(TbFydj record);

    TbFydj selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbFydj record);

    int updateByPrimaryKey(TbFydj record);

    TbFydj selectByFydjName(String FydjName);

    TbFydj authentication(TbFydj tbFydj);

    List<TbFydj> selectByConAndPage(Page<TbFydj> page, Map params);

    int deleteByPrimaryKeys(List ids);
    
    List<TbFydj> getListByPage(TbFydj tbFydj);
    Integer countListByPage(TbFydj tbFydj);
    
    TbFydj getFydjById(TbFydj tbFydj);
	int newInsert(TbFydj tbFydj);
	int update(TbFydj tbFydj);
	int deleteTrash(TbFydj tbFydj);
	int replyFydj(TbFydj tbFydj);
	int delete(TbFydj tbFydj);
	
	List<TbFydj> getAllList(TbFydj tbFydj);
}