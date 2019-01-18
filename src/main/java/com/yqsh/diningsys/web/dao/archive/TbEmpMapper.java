package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbEmp;

@Repository
public interface TbEmpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbEmp record);

    int insertSelective(TbEmp record);

    TbEmp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbEmp record);

    int updateByPrimaryKey(TbEmp record);

    TbEmp selectByEmpName(String empName);

    TbEmp authentication(TbEmp TbEmp);

    List<TbEmp> selectByConAndPage(Page<TbEmp> page, Map params);

    int deleteByPrimaryKeys(List ids);
    
    List<TbEmp> getListByPage(TbEmp tbEmp);
    Integer countListByPage(TbEmp tbEmp);
    
    TbEmp getEmpById(TbEmp tbEmp);
	int newInsert(TbEmp tbEmp);
	int update(TbEmp tbEmp);
	int deleteTrash(TbEmp tbEmp);
	int replyEmp(TbEmp tbEmp);
	int delete(TbEmp tbEmp);
	
	List<TbEmp> getAllList(TbEmp tbEmp);
}