package com.yqsh.diningsys.web.service.archive;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.archive.DgGiftFormReason;

import java.util.List;

/**
 * 赠单原因以及赠单原因类型
 *
 * @author zhshuo create on 2016-10-09 9:18
 */
public interface DgGiftFormService extends GenericService<DgGiftForm,Integer>{

    Page<DgGiftForm> getAllGiftForm(DgGiftForm dgGiftForm);

    Page<DgGiftFormReason> getAllGiftFormReason(DgGiftFormReason dgGiftFormReason);

    List<DgGiftFormReason> getAllGiftFormReason();
    
    List<DgGiftForm> getAllList(DgGiftForm dgGiftForm);
    
    List<DgGiftFormReason> getAllReasonList(DgGiftFormReason dgGiftFormReason);
    
    void addDgGiftForm(DgGiftForm dgGiftForm);

    void addDgGiftFormReason(DgGiftFormReason dgGiftFormReason);

    void updateDgGiftForm(DgGiftForm dgGiftForm);

    void updateDgGiftFormReason(DgGiftFormReason dgGiftFormReason);

    void deleteDgGiftForm(String ids);

    void deleteDgGiftFormReason(String ids);

    DgGiftFormReason selectGiftFormReasonById(Integer id);

    String selectNextCode();

    String selectReasonNextCode();
}