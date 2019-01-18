package com.yqsh.diningsys.web.dao.archive;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.archive.DgGiftFormReason;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DgGiftFormReasonMapper extends GenericDao<DgGiftFormReason,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgGiftFormReason record);

    int insertSelective(DgGiftFormReason record);

    DgGiftFormReason selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgGiftFormReason record);

    int updateByPrimaryKey(DgGiftFormReason record);

    List<DgGiftFormReason> getAllData(DgGiftFormReason dgGiftForm);

    Integer countAllData();

    void deleteData(List<Integer> ids);

    Integer selectNextCode();

    List<DgGiftForm> selectGiftFormByReason(Integer id);

    List<DgGiftFormReason> getAllReasonList(DgGiftFormReason dgGiftFormReason);
}