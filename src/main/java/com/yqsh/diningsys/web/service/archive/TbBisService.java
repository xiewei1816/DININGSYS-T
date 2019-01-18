package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbBis;

/**
 * 营业市别管理服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbBisService {

	/**
	 * 分页查询营业市别信息
	 * @param tbBis
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbBis> getPageList(TbBis tbBis);
	
	/**
	 * 新增、修改营业市别信息
	 * @param tbBis
	 * @return
	 */
	int insertOrUpdBis(TbBis tbBis);
	
	/**
	 * 通过营业市别ID查询营业市别信息
	 * @param tbBis
	 * @return
	 */
	TbBis getBisByID(TbBis tbBis);
	
	/**
	 * 删除营业市别信息
	 * @param tbBis
	 * @return
	 */
	int deleteByIds(TbBis tbBis);
	
	/**
	 * 营业市别信息回收站
	 * @param tbBis
	 * @return
	 */
	int deleteTrash(TbBis tbBis);
	
	/**
	 * 还原回收站营业市别信息
	 * @param tbBis
	 * @return
	 */
	int replyBis(TbBis tbBis);
	
	List<TbBis> getAllList(TbBis tbBis);
	TbBis authentication(TbBis tbBis);
	TbBis selectByBisName(String BisName);
    List<TbBis> selectByConAndPage(Page<TbBis> page, Map params);
    TbBis selectByPrimaryKey(Integer id);
    int insertBis(TbBis tbBis);
    int modifyUserByPrimaryKey(TbBis TbBis);
    int delUserByPrimaryKey(List ids);

	List<TbBis> selectAllBis();
	
	
	/**
	 * 
	 * 根据时间断获取id
	 * @param src
	 * @return
	 */
	
	int calculateSJD(Map src);

    TbBis selectHasSameTimeAndOrg(TbBis tbBis);

	TbBis selectHasSameNameAndOrg(TbBis tbBis);
}
