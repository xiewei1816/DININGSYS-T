package com.yqsh.diningsys.web.service.archive;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgProMethodsType;

import java.util.List;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-10 11:41
 */
public interface DgProMethodsTypeService extends GenericService<DgProMethodsType,Integer>{

    List<DgProMethodsType> selelctAllData();

    List<DgProMethodsType> selelctAllTypes();

    Page<DgProMethodsType> selectAllDataPage(DgProMethodsType dgProMethods);

    void updateData(DgProMethodsType dgProMethods);

    void addData(DgProMethodsType dgProMethods);

    void deleteData(String ids);

    int deleteByPrimaryKey(int id);
}
