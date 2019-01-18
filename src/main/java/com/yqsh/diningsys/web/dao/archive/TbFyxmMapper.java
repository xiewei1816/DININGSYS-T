package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbFyxm;

@Repository
public interface TbFyxmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbFyxm record);

    int insertSelective(TbFyxm record);

    TbFyxm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbFyxm record);

    int updateByPrimaryKey(TbFyxm record);

    TbFyxm selectByFyxmName(String FyxmName);

    TbFyxm authentication(TbFyxm tbFyxm);

    List<TbFyxm> selectByConAndPage(Page<TbFyxm> page, Map params);

    int deleteByPrimaryKeys(List ids);
    
    List<TbFyxm> getListByPage(TbFyxm tbFyxm);
    Integer countListByPage(TbFyxm tbFyxm);
    
    TbFyxm getFyxmById(TbFyxm tbFyxm);
	int newInsert(TbFyxm tbFyxm);
	int update(TbFyxm tbFyxm);
	int deleteTrash(TbFyxm tbFyxm);
	int replyFyxm(TbFyxm tbFyxm);
	int delete(TbFyxm tbFyxm);
	
	List<TbFyxm> getAllList(TbFyxm tbFyxm);
        
        TbFyxm getFyxmByNum(String fyxmName);
}