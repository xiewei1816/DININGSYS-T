package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbDep;

@Repository
public interface TbDepMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbDep record);

    int insertSelective(TbDep record);

    TbDep selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDep record);

    int updateByPrimaryKey(TbDep record);

    TbDep selectByDepName(String DepName);

    TbDep authentication(TbDep TbDep);

    List<TbDep> selectByConAndPage(Page<TbDep> page, Map params);

    int deleteByPrimaryKeys(List ids);
    
    List<TbDep> getListByPage(TbDep tbDep);
    Integer countListByPage(TbDep tbDep);
    
    TbDep getDepById(TbDep tbDep);
    
	int newInsert(TbDep tbDep);
	
	int update(TbDep tbDep);
	
	int deleteTrash(TbDep tbDep);
	
	int replyDep(TbDep tbDep);
	
	int delete(TbDep tbDep);
	
	List<TbDep> getAllList(TbDep tbDep);
	
	List<TbDep> selectAllDep(Map<String,Object> params);
	
	List<TbDep> selectSmallDep(Map<String,Object> params);

	TbDep getLastDepCode(TbDep tbDep);

	TbDep getDepDepartmentNameById(TbDep dep);
	/**
	 * 物理删除所有数据，此方法慎用
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:14:16
	 */
    void delPhy();
	/**
	 * 批量插入
	 * @author jianglei
	 * 日期 2017年1月18日 上午11:07:10
	 * @param listObj
	 */
    void insertBatch(@Param("listObj") List<TbDep> listObj);
}