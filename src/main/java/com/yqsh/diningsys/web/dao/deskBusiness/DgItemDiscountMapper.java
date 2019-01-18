package com.yqsh.diningsys.web.dao.deskBusiness;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscount;
@Repository
public interface DgItemDiscountMapper {
    
    /**
     * 
     * 批量修改
     * @param record
     */
    void updateBySrcList(List<DgItemFile> record);
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
    
    
    List<DgItemFile> getSmallByID(Integer id);
    
    List<DgItemFile> getBigByID(Integer id);
    /**
     * 获取所以品项
     */
    List<DgItemFile> selectAllItemFile();
    
    
    List<DgItemFile> selectSmallItemFile(Integer id);
    
    
    List<DgItemFile> selectBigItemFile(Integer id);
    
    
    List<DgItemFileType> selectAllItemFileBigType();
    List<DgItemFileType> selectItemSmallByParentId(Integer id );
    
}