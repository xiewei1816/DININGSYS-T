package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbRfct;

@Repository
public interface TbRfctMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbRfct record);

    int insertSelective(TbRfct record);

    TbRfct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbRfct record);

    int updateByPrimaryKey(TbRfct record);

    TbRfct selectByRfctName(String RfctName);

    TbRfct authentication(TbRfct tbRfct);

    List<TbRfct> selectByConAndPage(Page<TbRfct> page, Map params);

    int deleteByPrimaryKeys(List ids);
    
    List<TbRfct> getListByPage(TbRfct tbRfct);
    Integer countListByPage(TbRfct tbRfct);
    
    TbRfct getRfctById(TbRfct tbRfct);
	int newInsert(TbRfct tbRfct);
	int update(TbRfct tbRfct);
	int deleteTrash(TbRfct tbRfct);
	int replyRfct(TbRfct tbRfct);
	int delete(TbRfct tbRfct);
	
	List<TbRfct> getAllList(TbRfct tbRfct);

	TbRfct getLastRfctCode();
}