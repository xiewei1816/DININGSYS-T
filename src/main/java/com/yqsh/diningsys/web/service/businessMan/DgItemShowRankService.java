package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.archive.DgItemFileType;
import com.yqsh.diningsys.web.model.businessMan.DgItemShowRank;

/**
 * 消费品项显示设置服务层
 * @author xiewei
 * 
 */
public interface DgItemShowRankService{

	/**
	 * 新增、修改消费品项显示设置信息
	 * @param dgItemShowRank
	 * @return
	 */
	int insertOrUpdDgItemShowRank(DgItemShowRank dgItemShowRank);
	
	/**
	 * 通过消费品项显示设置ID查询消费品项显示设置信息
	 * @param dgItemShowRank
	 * @return
	 */
	DgItemShowRank getDgItemShowRankById(DgItemShowRank dgItemShowRank);
	
	/**
	 * 删除消费品项显示设置信息
	 * @param dgItemShowRank
	 * @return
	 */
	int deleteByIds(DgItemShowRank dgItemShowRank);

	/**
	 * 查询品项 "显示设置"信息
	 * @param inIds
	 * @return
	 */
	List<DgItemFile> selectDgItemFileList(Map params);
	
	/**
	 * 通过品项ID查询消费品项显示设置信息数量 
	 * @param dgItemShowRank
	 * @return
	 */
	DgItemShowRank getDgItemShowRankByPxId(DgItemShowRank dgItemShowRank);

	/**
	 * 根据品项名称、编号、助记码条件查询品项信息
	 * @param params
	 * @return
	 */
	List<DgItemFile> selectDgItemBySearch(Map<String, Object> params);
	
	/**
	 * 消费品项显示设置信息-排序移动
	 * @param param
	 * @return
	 */
	int dgItemShowSetRank(Map<String, Object> param);
	
	/**
	 * 通过向上移动查询消费品项显示设置信息
	 * @return
	 */
	DgItemShowRank getDgItemShowRankByMoveUp(DgItemShowRank dgItemShowRank);
	
	/**
	 * 通过向下移动查询消费品项显示设置信息
	 * @return
	 */
	DgItemShowRank getDgItemShowRankByMoveDown(DgItemShowRank dgItemShowRank);
	
	/**
	 * 查询品项小类 "显示设置"信息
	 * @return
	 */
	List<DgItemFileType> selectDgItemFileSmallList(Map<String, Object> params);
	
	/**
	 * 查询品项 "显示设置"信息 不显示和不排行
	 * @param params
	 * @return
	 */
	List<DgItemFile> selectDgItemFileNoShowRankList(Map<String, Object> params);

	/**
	 * 查询品项小类 "显示设置"信息 不显示和不排行
	 * @param params
	 * @return
	 */
	List<DgItemFile> selectDgSmallItemFileNoShowRankList(Map<String, Object> params);
}
