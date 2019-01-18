/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yqsh.diningsys.web.service.archive;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgFlavor;
import java.util.List;

/**
 *
 * @author yqsh-zc
 */
public interface DgFlavorService extends GenericService<DgFlavor,Integer>{
    List<DgFlavor> getAllBeans(String hasRoot);
    
    List<DgFlavor> getTreeBean();
    
    List<DgFlavor> getFlavorByParentId(Integer id);
    
    DgFlavor getFlavorByNumber(String number);
    
    void insertOrUpdateBeans(DgFlavor flavor);
}
