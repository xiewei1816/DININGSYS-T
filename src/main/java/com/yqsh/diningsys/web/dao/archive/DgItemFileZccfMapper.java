package com.yqsh.diningsys.web.dao.archive;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFileZccf;
@Repository
public interface DgItemFileZccfMapper extends GenericDao<DgItemFileZccf, Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgItemFileZccf record);

    int insertSelective(DgItemFileZccf record);

    DgItemFileZccf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemFileZccf record);

    int updateByPrimaryKey(DgItemFileZccf record);
    
    
    /**
     * 根据品项id获取组成成分
     * @param id
     * @return
     */
    List<DgItemFileZccf> getZccfByItemId(Integer id);
    
	List<DgItemFileZccf> selectHaveItem(List<String> ids);
    /**
     * 删除itemId所有数据
     */
    int deleteByItemId(Integer id);
}