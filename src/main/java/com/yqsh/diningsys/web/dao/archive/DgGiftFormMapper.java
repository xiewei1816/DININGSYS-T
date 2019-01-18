package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DgGiftFormMapper extends GenericDao<DgGiftForm,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgGiftForm record);

    int insertSelective(DgGiftForm record);

    DgGiftForm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgGiftForm record);

    int updateByPrimaryKey(DgGiftForm record);

    List<DgGiftForm> getAllData(DgGiftForm dgGiftForm);

    Integer countAllData(DgGiftForm dgGiftForm);

    void deleteData(List<Integer> ids);

    Integer selectNextCode();

    List<DgGiftForm> getAllList(DgGiftForm dgGiftForm);

}