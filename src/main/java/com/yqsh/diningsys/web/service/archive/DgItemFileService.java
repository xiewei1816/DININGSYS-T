package com.yqsh.diningsys.web.service.archive;

import com.yqsh.diningsys.core.generic.GenericService;
import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.archive.*;
import com.yqsh.diningsys.web.model.report.ItemFileSell;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-13 16:53
 */
public interface DgItemFileService extends GenericService<DgItemFile,Integer>{

    int insertSelectiveCus(HttpServletRequest request);

    List<DgItemFileType> selectSmallItemType(Integer id);

    List<DgItemFile> selectDataBySmallType(Integer id);

    Page<DgNutrition> selectAllNutrition(DgNutrition dgNutrition);

    List<DgNutrition> getNutritionNotInIds(String ids);

    List<DgNutrition> getNutritionInIds(String ids);

    DgNutrition selectNutritionById(Integer id);

    void editNutrition(DgNutrition dgNutrition);

    void delNutrition(String ids);

    List<DgProMethods> selectGgzyz(String ids);

    /**
     * 套餐页面，品项数据选择，
     * @param id 查询的数据不包含当前套餐
     * @return
     */
    List<DgItemFile> selectItemFileInTc(Integer id,Integer selectedTcId, String tcPxNotInIds);

    /**
     * 查询指定IDS的品项数据
     * @param ids
     * @return
     */
    List<DgItemFile> selectItemFileInIds(String ids);

    /**
     * 根据已选表格获取未选的不是套餐的品项
     * @param ids
     * @return
     */
    List<DgItemFile> getItemFileYxInIds(String ids);


    /*套餐新增*/
    void editTc(String bxpxData,String countData,String kxpxData,String thpxData,String slxdData,
                String tcName,Double tcPrice,String tcType,String tcWithNum,String canUpdate,
                String minNum,String maxNum,String tcId,String itemFileId);

    /*套餐固定基本信息*/
    DgItemFilePackage selectTcBaseInfo(Integer id);

    List<Map> selectTcBaseInfoMap(Integer id);

    /*套餐必选品项基本信息*/
    List<Map> selectTcBxInfo(Integer id);

    /*套餐可选品项信息*/
    List<Map> selectTcKxInfo(Integer id);

    /*套餐大类数量限定信息*/
    List<Map> selectTcSlxdInfo(Integer id);

    /*套餐必选品项的替换品项信息*/
    List<Map> selectTcThpxInfo(Integer id);

    void deleteItemFile(Integer id);

    //查询品项的可选列表
    List<DgItemFile> selectItemFileByTypeIdAndNotInIds(Integer id,String notInIds);
    
    List<DgItemFile> getAllItemCooker(Map<String,Object> map);
    
    DgItemFile getDgItemFileByNumber(String number);
    
    DgItemFile getDgItemFileByName(String name);
    
    
    int updateCsList(List<DgItemFile> records);


	/*报表相关*/
    ////品项销售明细-明细
    com.yqsh.diningsys.core.util.Page<ItemFileSell> getItemFileSellDetailsPageList(ItemFileSell itemFileSell);
    //品项销售明细-汇总
    com.yqsh.diningsys.core.util.Page<ItemFileSell> getItemFileSellSummaryPageList(ItemFileSell itemFileSell);
    //品项销售明细-导出
	List<Map> selectItemFileSellDetails(String startTime, String endTime, Integer itemFileType, Integer searchDataType, Integer bis, String itemFileName,String serviceType);

    List<SysUser> selectAllCook();
    void synItemFileTc(List<DgItemFilePackage> listItemFilePackage,List<DgItemFilePackageBx> listItemFilePackageBx,
    		List<DgItemFilePackageKx> listItemFilePackageKx,List<DgItemFilePackageSlxd> listItemFilePackageSlxd,
    		List<DgItemFilePackageTh> listItemFilePackageTh);

    List<DgItemFile> searchItemFile(String searchText, Integer searchFlag, Integer typeId);

    /**
     * 2017年10月23日15:57:54
     * 结算方式和券相关的功能新增方法
     * @param parentId
     * @param type
     * @param ids
     * @return
     */
    List<DgItemFile> selectItemFileNotInIds(Integer parentId, Integer type, String ids);

    /**
     * 2017年10月23日16:55:10
     * 结算方式和券相关的功能新增方法
     * @param ids
     * @return
     */
    List<DgItemFile> selectItemFileInIdsTicket(String ids);

    List<DgItemFile> getItemFileRankList(Integer ppxlId);

    List<DgItemFile> getItemFileNotRankList();
    
    Integer getItemFileLargeRankByPpxlId(Integer xlId);
    
    Integer getItemFileSmallRankByPpxlId(Integer xlId);
    
    void updateItemFileRankIncreaseByRank(Integer largeRank);
    
    void updateItemFileRankIncreaseByRankAndPpxlId(Map<String, Object> map);
    
    Map<String, Object> selItemFileRank(Map<String, Object> map);

    void addItemFileRank(Map<String, Object> map);

    int updateItemFileRankMove(Integer id, Integer rank);
    
    int updateItemFileRankMoveUp(Integer id);

    int updateItemFileRankMoveDown(Integer id);

    List<String> selectItemFileRankMoveTopper(Integer id);

    void updateItemFileRankMoveTopper(List<String> ids);

    void updateItemFileRank(Map<String, Object> params);

	Integer getItemFileRankByItemFileId(Integer itemFileId);

	Integer getItemFileRankCount();

}
