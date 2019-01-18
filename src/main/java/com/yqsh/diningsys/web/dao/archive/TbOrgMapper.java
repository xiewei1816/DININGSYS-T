package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbOrg;

@Repository
public interface TbOrgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbOrg record);

    int insertSelective(TbOrg record);

    TbOrg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbOrg record);

    int updateByPrimaryKey(TbOrg record);

    TbOrg selectByOrgName(String DepName);

    TbOrg authentication(TbOrg tbOrg);

    List<TbOrg> selectByConAndPage(Page<TbOrg> page, Map params);

    int deleteByPrimaryKeys(List ids);

    List<TbOrg> getListByPage(TbOrg tbOrg);
    Integer countListByPage(TbOrg tbOrg);

    TbOrg getOrgByID(TbOrg tbOrg);
	int newInsert(TbOrg tbOrg);
	int update(TbOrg tbOrg);
	int deleteTrash(TbOrg tbOrg);
	int replyDep(TbOrg tbOrg);
	int delete(TbOrg tbOrg);
	
	List<TbOrg> getAllList(TbOrg tbOrg);

	List<TbOrg> selectAllTbOrg();
	List<TbOrg> ajaxTreeOrg();

    TbOrg getOwnOrg();

}