package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbBis;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TbBisMapper {

    int insert(TbBis record);

    int insertSelective(TbBis record);

    TbBis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbBis record);

    int updateByPrimaryKey(TbBis record);

    TbBis selectByBisName(String BisName);

    TbBis authentication(TbBis tbBis);

    List<TbBis> selectByConAndPage(Page<TbBis> page, Map params);

    int deleteByPrimaryKeys(List ids);

    List<TbBis> getListByPage(TbBis tbBis);

    Integer countListByPage(TbBis tbBis);

    TbBis getBisByID(TbBis tbBis);

    int newInsert(TbBis tbBis);

    int update(TbBis tbBis);

    int deleteTrash(TbBis tbBis);

    int replyBis(TbBis tbBis);

    int delete(TbBis tbBis);

    List<TbBis> getAllList(TbBis tbBis);

    List<TbBis> selectAllBis();

    int calculateSJD(Map src);

    TbBis selectHasSameTimeAndOrg(TbBis tbBis);

    TbBis selectHasSameNameAndOrg(TbBis tbBis);
    
    String getNextBisTime(String startTime);
    
    String getMinBisTime();
}