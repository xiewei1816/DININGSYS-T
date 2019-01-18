package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgAllowNumber;
import com.yqsh.diningsys.web.model.archive.DgPublicCode0;

public interface DgAllowNumberService extends GenericService<DgAllowNumber,Integer>{
	
    Page<DgAllowNumber> getListByPage(DgAllowNumber page);
    
    Map insertOrUpdate(DgAllowNumber org);
    
    int deleteById(String ids);
    
    int updateById(DgAllowNumber record);
    
    DgAllowNumber selectById(Integer id);

    List<DgAllowNumber> getAllList();

}
