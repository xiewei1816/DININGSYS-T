package com.yqsh.diningsys.web.service.archive;


import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbOrg;

/**
 * 机构管理服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbOrgService {

	/**
	 * 分页查询机构信息
	 * @param TbOrg
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbOrg> getPageList(TbOrg tbOrg);
	
	/**
	 * 新增、修改机构信息
	 * @param tbOrg
	 * @return
	 */
	int insertOrUpdOrg(TbOrg tbOrg);
	
	/**
	 * 通过机构ID查询机构信息
	 * @param tbOrg
	 * @return
	 */
	TbOrg getOrgByID(TbOrg tbOrg);
	
	/**
	 * 删除机构信息
	 * @param tbOrg
	 * @return
	 */
	int deleteByIds(TbOrg tbOrg);
	
	/**
	 * 机构信息回收站
	 * @param tbOrg
	 * @return
	 */
	int deleteTrash(TbOrg tbOrg);
	
	/**
	 * 还原回收站机构信息
	 * @param tbOrg
	 * @return
	 */
	int replyOrg(TbOrg tbOrg);
	
	List<TbOrg> getAllList(TbOrg tbOrg);
	
	List<TbOrg> ajaxTreeOrg();
	
	TbOrg authentication(TbOrg tbOrg);

	TbOrg selectByOrgName(String depName);

    List<TbOrg> selectByConAndPage(Page<TbOrg> page, Map params);

    TbOrg selectByPrimaryKey(Integer id);

    int insertOrg(TbOrg tbOrg);

    int modifyUserByPrimaryKey(TbOrg tbOrg);

    int delUserByPrimaryKey(List ids);

	TbOrg getOwnOrg();

	List<TbOrg> selectAllTbOrg();

    TbOrg selectSelfShop();
}
