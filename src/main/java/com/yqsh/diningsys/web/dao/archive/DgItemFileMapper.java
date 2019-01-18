package com.yqsh.diningsys.web.dao.archive;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.SysUser;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTypeDiscount;
import com.yqsh.diningsys.web.model.report.ItemFileSell;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFilePackage;

@Repository
public interface DgItemFileMapper extends GenericDao<DgItemFile,Integer> {
    int deleteByPrimaryKey(Integer id);

    int deleteByIds(List<Integer> id);

    int insert(DgItemFile record);

    int insertSelective(DgItemFile record);

    DgItemFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemFile record);

    int updateByPrimaryKey(DgItemFile record);

    List<DgItemFile> selectDataByItemTypeId(Map param);

    List<DgItemFile> selectItemFileInTc(Map param);

    List<DgItemFile> selectItemFileInIds(List<Integer> list);

    List<DgItemFile> getItemFileYxInIds(List<Integer> list);

    DgItemFilePackage selectTcBaseInfo(Integer id);

    List<Map> selectTcBxInfo(Integer id);

    List<Map> selectTcKxInfo(Integer id);

    List<Map> selectTcSlxdInfo(Integer id);

    List<Map> selectTcThpxInfo(Integer id);

    List<Map> selectTcBaseInfoMap(Integer id);

    List<DgItemFile> selectItemFileByTypeIdAndNotInIds(Map param);
    
    List<DgItemFile> getAllItemCooker(Map<String,Object> map);
    
    DgItemFile getDgItemFileByNumber(String number);
    
    DgItemFile getDgItemFileByName(String name);
    
    
    int updateCsList(List<DgItemFile> records);

    /**
     * 品项销售明细
     * @param param
     */
    //品项销售明细-明细
    Integer countItemFileSellDetailsListByPage(ItemFileSell itemFileSell);
    List<ItemFileSell> selectItemFileSellDetailsListByPage(ItemFileSell itemFileSell);
    //品项销售明细-汇总
    Integer countItemFileSellSummaryListByPage(ItemFileSell itemFileSell);
    List<ItemFileSell> selectItemFileSellSummaryListByPage(ItemFileSell itemFileSell);
    //品项销售明细-导出
    List<Map> selectItemFileSellDetails(Map<String, Object> param);

    /**
     * 品项汇总明细
     * @param map
     * @return
     */
    List<Map> selectItemFileSellSummary(Map<String, Object> map);

    /**
     * 获取所有的厨师
     * @return
     */
    List<SysUser> selectAllCook();

    DgItemTypeDiscount selectDefaultDiscount(@Param("ppxlId") Integer ppxlId);
    
    
    /**
     * 餐台获取所有菜品信息
     */
    List<DgItemFile> getItemByTableOrg(@Param("num") String num);
    
    
    DgItemFile selectByPrimaryKeyContainColor(Integer id);

    List<DgItemFile> searchItemFile(Map<String, Object> map);

    List<DgItemFile> selectItemFileNotInIds(Map<String, Object> map);

    List<DgItemFile> selectItemFileInIdsTicket(@Param("list") List<Integer> integers);

    List<DgItemFile> getItemFileRankList(@Param("ppxlId") Integer pxxlId);
    
	List<DgItemFile> getItemFileNotRankList();
	
	Integer getItemFileLargeRankByPpxlId(@Param("xlId") Integer xlId);

	Integer getItemFileSmallRankByPpxlId(@Param("xlId") Integer xlId);

	void updateItemFileRankIncreaseByRank(@Param("largeRank") Integer largeRank);
	
	void updateItemFileRankIncreaseByRankAndPpxlId(Map<String, Object> map);

    Map<String, Object> selItemFileRank(Map<String, Object> map);

    void addItemFileRank(Map<String, Object> map);
    
    Integer getItemFileRankCount();
    
    int updateItemFileRankMove(Map<String, Object> map);

    int updateItemFileRankMoveUp(Integer id);

    int updateItemFileRankMoveDown(Integer id);

    List<String> selectItemFileRankMoveTopper(Integer id);

    void updateItemFileRankMoveTopper(List<String> ids);

    void updateItemFileRank(Map<String, Object> params);

	Integer getItemFileRankByItemFileId(@Param("itemFileId") Integer itemFileId);
	
	//用于日营业报表
	List<Map> selectItemFileSellOpenDaySummaryList(Map<String, Object> params);
	
	Map selectItemFileSellOpenDaybackSubotal(Map<String, Object> params);
}