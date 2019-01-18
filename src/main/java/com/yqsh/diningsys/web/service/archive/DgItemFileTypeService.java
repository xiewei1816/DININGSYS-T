package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.generic.GenericDao;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;

/**
 * 这里用一句话来说明类的作用
 *
 * @author zhshuo create on 2016-10-13 16:54
 */
public interface DgItemFileTypeService extends GenericDao<DgItemFileType,Integer> {

    /**
     * 获取
     * @return
     */
    List<DgItemFileType> selectAllItemFileType();

    /**
     * 获取第一级品项类型
     * @return
     */
    List<DgItemFileType> getFirstLeveType();

    /**
     * 获取第二级品项小类，根据第一级品项类型的ID
     * @param id
     * @return
     */
    List<DgItemFileType> getSecondLeveType(Integer id);

    List<DgItemFileType> selectAllItemFileBigType(Integer id);

    List<DgItemFileType> selectAllItemFileSmallType();

    List<DgItemFileType> getItemFileTypeByParent(Integer parentId);

    List<DgItemFileType> selectSecondItemFileTypeInIds(String inIds);

    /**
     * 查询可选的品项小类列表
     * @param id
     * @param notInIds
     * @return
     */
    List<DgItemFileType> selectItemFileTypeByTypeIdAndNotInIds(Integer id,String notInIds);
    
    DgItemFileType getItemFileById(Integer id);
    
    DgItemFileType getDgItemFileTypeByName(Integer pId,String name);
    
    List<DgItemFileType> selectAllBigType();
    /**
	 * 数据同步相关操作
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:05:11
	 * @param listItemType
	 */
    void synData(List<DgItemFileType> listObj);

    /**
     * 获取所有的品项大类
     * @return
     */
    List<DgItemFileType> selectAllItemFileBigType();

    List<DgItemFileType> selectWayItemYxBigType(String ids);

    List<DgItemFileType> selectWayItemWxBigType(String ids);

    List<DgItemFileType> selectWayItemYxSmallType(String ids);

    List<DgItemFileType> selectWayItemWxSmallType(Integer parentId, String ids);

	List<DgItemFileType> getItemFileSmallTypeRankList();

	Map<String, Object> selItemFileSmallTypeRank(Map<String, Object> map);

	void addItemFileSmallTypeRank(Map<String, Object> map);

	int updateItemFileSmallTypeRankMoveUp(Integer id1);

	int updateItemFileSmallTypeRankMoveDown(Integer id2);

	List<String> selectItemFileRankSmallTypeMoveTopper(Integer id1);

	void updateItemFileRankSmallTypeMoveTopper(List<String> ids);

	void updateItemFileSmallTypeRank(Map<String, Object> params);

	List<Map> selectYxeConsumerItems(int consumerId);

	void insertYxeConsumers(int consumerId,String itemIds);
}
