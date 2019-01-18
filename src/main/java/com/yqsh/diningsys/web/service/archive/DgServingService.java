package com.yqsh.diningsys.web.service.archive;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.archive.DgServing;

import java.util.List;

/**
 * 上菜状态service
 *
 * @author zhshuo create on 2016-09-29 16:20
 */
public interface DgServingService extends GenericService<DgServing,Integer>{

    Page<DgServing> getAllData(DgServing record);

    int addData(DgServing dgServing);

    int updateData(DgServing dgServing);

    int deleteDataWithLogic(String ids);

    int deleteData(String ids);

    DgServing selectById(DgServing dgServing);
    
    List<DgServing> getAll();

    String selectMaxNum();
}
