package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbFylx;

@Repository
public interface TbFylxMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbFylx record);

    int insertSelective(TbFylx record);

    TbFylx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbFylx record);

    int updateByPrimaryKey(TbFylx record);

    TbFylx selectByFylxName(String FylxName);

    TbFylx authentication(TbFylx tbFylx);

    List<TbFylx> selectByConAndPage(Page<TbFylx> page, Map params);

    int deleteByPrimaryKeys(List ids);
    
    List<TbFylx> getListByPage(TbFylx tbFylx);
    Integer countListByPage(TbFylx tbFylx);
    
    TbFylx getFylxById(TbFylx tbFylx);
	int newInsert(TbFylx tbFylx);
	int update(TbFylx tbFylx);
	int deleteTrash(TbFylx tbFylx);
	int replyFylx(TbFylx tbFylx);
	int delete(TbFylx tbFylx);
	
	List<TbFylx> getAllList(TbFylx tbFylx);

	TbFylx getLastFylxCode();
}