package com.yqsh.diningsys.web.service.deskBusiness;

import java.util.List;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;

public interface DgItemDiscountService{
    /**
     * 
     * 获取是否存在当前品项(根据品项id)
     * @param code
     * @return
     */
    int getCountByItem(Integer id);
    
    /*
     * 
     * 获取所有数据
     */
    
    List<DgItemFile> getAll();
    
    /**
     * 获取所以品项
     */
    List<DgItemFile> selectAllItemFile();
    
    List<DgItemFile> selectSmallItemFile(Integer id);
    
    
    List<DgItemFile> selectBigItemFile(Integer id);
    
    /**
     * 
     * 批量修改
     * @param record
     */
    void updateBySrcList(List<DgItemFile> record);
    
    List<DgItemFileType> selectAllItemFileBigType();
    List<DgItemFileType> selectItemSmallByParentId(Integer id );
    
    List<DgItemFile> getSmallByID(Integer id);
    
    List<DgItemFile> getBigByID(Integer id);
}