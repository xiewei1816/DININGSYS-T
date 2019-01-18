package com.yqsh.diningsys.web.dao.print;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgConsumerSeat;
import com.yqsh.diningsys.web.model.archive.DgConsumptionArea;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemDiscountProgramme;
import com.yqsh.diningsys.web.model.print.DgPrintManager;
@Repository
public interface DgPrintManagerMapper extends GenericDao<DgPrintManager,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(DgPrintManager record);

    int insertSelective(DgPrintManager record);

    DgPrintManager selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgPrintManager record);

    int updateByPrimaryKey(DgPrintManager record);
    
    
    
    /**
     * 树
     */
    List<DgItemFile>  selectAllItemFile(List<Integer> ids);
    List<DgItemFile>  selectSmallItemFile(HashMap<String, Object> src);
    List<DgItemFile>  selectBigItemFile(HashMap<String, Object> src);
    List<DgItemFile>  selectHaveItem(List ids);
    
    
    List<DgItemFileType> selectAllItemFileType(List ids);
    List<DgItemFileType> selectHaveItemType(List ids);
    List<DgItemFileType> selectSmallItemFileType(Map src);
    
    
    void deleteIds(List<Integer> ids);
    void trash(List<Integer> ids);
    void restore(List<Integer> ids);
    
    int countAllData(DgPrintManager record); //获取总数量
    List<DgPrintManager> getAllData(DgPrintManager record);
    
    int insertBackId(DgPrintManager record);
    
    List<DgItemFile>  searchVague(HashMap<String, Object> src);
    
    List<DgConsumptionArea> getAreaByIds(List ids);
    
    
    /**
     * 获取所以打印机数据
     * @return
     */
    List<DgPrintManager> selectAll();
    
    /**
     * 查询转台打印机列表
     * @return
     */
    List<DgPrintManager> selectZtItem();
    
    /**
     * 获取外卖列表
     * @return
     */
    List<DgPrintManager> selectWmItem();
}