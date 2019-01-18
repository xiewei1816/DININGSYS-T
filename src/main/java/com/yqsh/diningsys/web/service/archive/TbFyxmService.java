package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbFyxm;

/**
 * 费用项目服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbFyxmService {

	/**
	 * 分页查询费用项目信息
	 * @param TbFyxm
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbFyxm> getPageList(TbFyxm tbFyxm);
	
	/**
	 * 新增、修改费用项目信息
	 * @param TbFyxm
	 * @return
	 */
	int insertOrUpdFyxm(TbFyxm tbFyxm);
	
	/**
	 * 通过费用项目ID查询费用项目信息
	 * @param TbFyxm
	 * @return
	 */
	TbFyxm getFyxmById(TbFyxm tbFyxm);
	
	/**
	 * 删除费用项目信息
	 * @param TbFyxm
	 * @return
	 */
	int deleteByIds(TbFyxm tbFyxm);
	
	/**
	 * 费用项目信息回收站
	 * @param TbFyxm
	 * @return
	 */
	int deleteTrash(TbFyxm tbFyxm);
	
	/**
	 * 还原回收站费用项目信息
	 * @param TbFyxm
	 * @return
	 */
	int replyFyxm(TbFyxm tbFyxm);
	
	List<TbFyxm> getAllList(TbFyxm tbFyxm);
	TbFyxm authentication(TbFyxm tbFyxm);
	TbFyxm selectByFyxmName(String FyxmName);
        List<TbFyxm> selectByConAndPage(Page<TbFyxm> page, Map params);
        TbFyxm selectByPrimaryKey(Integer id);
        int insertFyxm(TbFyxm tbFyxm);
        int modifyUserByPrimaryKey(TbFyxm tbFyxm);
        int delUserByPrimaryKey(List ids);
        TbFyxm getFyxmByNum(String FyxmName);

}
