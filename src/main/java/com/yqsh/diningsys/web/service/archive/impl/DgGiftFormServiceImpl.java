package com.yqsh.diningsys.web.service.archive.impl;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.core.generic.GenericServiceImpl;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.core.util.PageUtil;
import com.yqsh.diningsys.web.dao.archive.DgGiftFormMapper;
import com.yqsh.diningsys.web.dao.archive.DgGiftFormReasonMapper;
import com.yqsh.diningsys.web.model.archive.DgGiftForm;
import com.yqsh.diningsys.web.model.archive.DgGiftFormReason;
import com.yqsh.diningsys.web.service.archive.DgGiftFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-09 9:36
 */
@Service
public class DgGiftFormServiceImpl extends GenericServiceImpl<DgGiftForm,Integer> implements DgGiftFormService{

    @Resource
    private DgGiftFormMapper dgGiftFormMapper;

    @Resource
    private DgGiftFormReasonMapper dgGiftFormReasonMapper;

    @Override
    public GenericDao<DgGiftForm, Integer> getDao() {
        return dgGiftFormMapper;
    }

    @Override
    public Page<DgGiftForm> getAllGiftForm(DgGiftForm dgGiftForm) {
        Integer totle = dgGiftFormMapper.countAllData(dgGiftForm);
        List<DgGiftForm> list = dgGiftFormMapper.getAllData(dgGiftForm);
        return PageUtil.getPage(totle, dgGiftForm.getPage(),list, dgGiftForm.getRows());
    }

    @Override
    public Page<DgGiftFormReason> getAllGiftFormReason(DgGiftFormReason dgGiftFormReason) {
        Integer totle = dgGiftFormReasonMapper.countAllData();
        List<DgGiftFormReason> list = dgGiftFormReasonMapper.getAllData(dgGiftFormReason);
        return PageUtil.getPage(totle, dgGiftFormReason.getPage(),list, dgGiftFormReason.getRows());
    }

    @Override
    public List<DgGiftFormReason> getAllGiftFormReason() {
        List<DgGiftFormReason> allData = dgGiftFormReasonMapper.getAllData(null);
        for(DgGiftFormReason dgGiftFormReason:allData){
            List<DgGiftForm> dgGiftForms = dgGiftFormReasonMapper.selectGiftFormByReason(dgGiftFormReason.getId());
            dgGiftFormReason.setDgGiftForms(dgGiftForms);
        }
        return allData;
    }

    @Override
    public List<DgGiftForm> getAllList(DgGiftForm dgGiftForm){
		return dgGiftFormMapper.getAllList(dgGiftForm);
    }
    
    @Override
    public List<DgGiftFormReason> getAllReasonList(DgGiftFormReason dgGiftFormReason){
		return dgGiftFormReasonMapper.getAllReasonList(dgGiftFormReason);
    }
    
    @Override
    public void addDgGiftForm(DgGiftForm dgGiftForm) {
        dgGiftForm.setCreateTime(new Date());
        dgGiftFormMapper.insertSelective(dgGiftForm);
    }

    @Override
    public void addDgGiftFormReason(DgGiftFormReason dgGiftFormReason) {
        dgGiftFormReason.setCreateTime(new Date());
        dgGiftFormReasonMapper.insertSelective(dgGiftFormReason);
    }

    @Override
    public void updateDgGiftForm(DgGiftForm dgGiftForm) {
        dgGiftFormMapper.updateByPrimaryKeySelective(dgGiftForm);
    }

    @Override
    public void updateDgGiftFormReason(DgGiftFormReason dgGiftFormReason) {
        dgGiftFormReason.setUpdateTime(new Date());
        dgGiftFormReasonMapper.updateByPrimaryKeySelective(dgGiftFormReason);
    }

    @Override
    public void deleteDgGiftForm(String ids) {
        dgGiftFormMapper.deleteData(StringIdsTOList(ids));
    }

    @Override
    public void deleteDgGiftFormReason(String ids) {
        dgGiftFormReasonMapper.deleteData(StringIdsTOList(ids));
    }

    @Override
    public DgGiftFormReason selectGiftFormReasonById(Integer id) {
        return dgGiftFormReasonMapper.selectByPrimaryKey(id);
    }

    @Override
    public String selectNextCode() {
    	Integer maxCode = 0;
    	Integer code = dgGiftFormMapper.selectNextCode();
    	if(code != null){
    		maxCode = code + 1;
    	}else{
    		maxCode = 1;
    	}
        if(maxCode < 10){
            return "0"+maxCode;
        }
        return maxCode+"";
    }

    @Override
    public String selectReasonNextCode() {
    	Integer maxCode = 0;
    	Integer code = dgGiftFormReasonMapper.selectNextCode();
    	if(code != null){
    		maxCode = code + 1;
    	}else{
    		maxCode = 1;
    	}
        if(maxCode < 10){
            return "0"+maxCode;
        }
        return maxCode+"";
    }

}
