package com.yqsh.diningsys.web.dao.archive;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.archive.DgYxeConsItemShow;

@Repository
public interface DgItemFileTypeMapper extends GenericDao<DgItemFileType, Integer> {

    int deleteByPrimaryKey(Integer id);

    int insert(DgItemFileType record);

    int insertSelective(DgItemFileType record);

    DgItemFileType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DgItemFileType record);

    int updateByPrimaryKey(DgItemFileType record);

    List<DgItemFileType> selectAllItemFileType();

    List<DgItemFileType> selectAllItemFileBigType(Map param);

    List<DgItemFileType> selectSmallItemTypeId(Integer id);

    List<DgItemFileType> selectAllItemFileSmallType();

    List<DgItemFileType> getItemFileTypeByParent(Map param);

    List<DgItemFileType> getFirstLeveType();

    List<DgItemFileType> getSecondLeveType(Integer id);

    List<DgItemFileType> selectSecondItemFileTypeInIds(Map param);

    List<DgItemFileType> selectItemFileTypeByTypeIdAndNotInIds(Map map);

    DgItemFileType getItemFileById(Integer id);
    
    DgItemFileType getDgItemFileTypeByName(Map map);
    
    List<DgItemFileType> selectAllBigType();
    /**
	 * 物理删除所有数据，此方法慎用
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:14:16
	 */
    void delPhy();
	/**
	 * 批量插入
	 * @author jianglei
	 * 日期 2017年1月18日 上午11:07:10
	 * @param listObj
	 */
    void insertBatch(@Param("listObj") List<DgItemFileType> listObj);

    /**
     * 获取所有的品项大类
     * @return
     */
    List<DgItemFileType> selectAllItemFileBigTypeNoParam();

    List<DgItemFileType> selectWayItemYxBigType(@Param("list") List ids);

    List<DgItemFileType> selectWayItemWxBigType(@Param("list") List strings);

    List<DgItemFileType> selectWayItemYxSmallType(@Param("list") List<Integer> integers);

    List<DgItemFileType> selectWayItemWxSmallType(Map<String,Object> param);

	List<DgItemFileType> getItemFileSmallTypeRankList();
    
	Map<String, Object> selItemFileSmallTypeRank(Map<String, Object> map);

	void addItemFileSmallTypeRank(Map<String, Object> map);

	int updateItemFileSmallTypeRankMoveUp(Integer id);

	int updateItemFileSmallTypeRankMoveDown(Integer id);

	List<String> selectItemFileRankSmallTypeMoveTopper(Integer id);

	void updateItemFileRankSmallTypeMoveTopper(List<String> ids);

	void updateItemFileSmallTypeRank(Map<String, Object> params);
	
	List<Map> selectYxeConsItemFileType(Integer id);
	
	List<Map> selectYxeConsItemFile(Integer id);
	
	void delYxeConsItems(Integer id);
	
	void insertYxeConsItems(List<DgYxeConsItemShow> items);

}
