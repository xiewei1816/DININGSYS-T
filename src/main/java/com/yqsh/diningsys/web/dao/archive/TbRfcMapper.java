package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbRfc;

@Repository
public interface TbRfcMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TbRfc record);

    int insertSelective(TbRfc record);

    TbRfc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbRfc record);

    int updateByPrimaryKey(TbRfc record);

    TbRfc selectByRfcName(String RfcName);

    TbRfc authentication(TbRfc TbRfc);

    List<TbRfc> selectByConAndPage(Page<TbRfc> page, Map params);

    int deleteByPrimaryKeys(List ids);

    List<TbRfc> getListByPage(TbRfc tbRfc);

    Integer countListByPage(TbRfc tbRfc);

    TbRfc getRfcById(TbRfc tbRfc);

    int newInsert(TbRfc tbRfc);

    int update(TbRfc tbRfc);

    int deleteTrash(TbRfc tbRfc);

    int replyRfc(TbRfc tbRfc);

    int delete(TbRfc tbRfc);

    List<TbRfc> getAllList(TbRfc tbRfc);
    
    TbRfc getTbRfcByNumber(String number);
}
