package com.yqsh.diningsys.web.service.archive;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgProMethods;

import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-10 11:32
 */
public interface DgProMethodsSerivce extends GenericService<DgProMethods,Integer>{

    Page<DgProMethods> selelctAllData(DgProMethods dgProMethods);

    void updateData(DgProMethods dgProMethods);

    void addData(DgProMethods dgProMethods);

    void deleteData(String ids);

    List<DgProMethods> getProMethodsByType(String ids,Integer id);

    List<DgProMethods> getPubProMeInIds(String ids);

    List<DgProMethods> getPubProMeNotInIds(String ids);
    
    DgProMethods getProMethodByNumber(String number);

}