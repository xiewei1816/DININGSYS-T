package com.yqsh.diningsys.web.service.businessMan;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.businessMan.TbFydj;

/**
 * 费用登记服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbFydjService {

	/**
	 * 分页查询费用登记信息
	 * @param TbFydj
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbFydj> getPageList(TbFydj tbFydj);
	
	/**
	 * 新增、修改费用登记信息
	 * @param TbFydj
	 * @return
	 */
	int insertOrUpdFydj(TbFydj tbFydj);
	
	/**
	 * 通过费用登记ID查询费用登记信息
	 * @param TbFydj
	 * @return
	 */
	TbFydj getFydjById(TbFydj tbFydj);
	
	/**
	 * 删除费用登记信息
	 * @param TbFydj
	 * @return
	 */
	int deleteByIds(TbFydj tbFydj);
	
	/**
	 * 费用登记信息回收站
	 * @param TbFydj
	 * @return
	 */
	int deleteTrash(TbFydj tbFydj);
	
	/**
	 * 还原回收站费用登记信息
	 * @param TbFydj
	 * @return
	 */
	int replyFydj(TbFydj tbFydj);
	
	List<TbFydj> getAllList(TbFydj tbFydj);
	TbFydj authentication(TbFydj tbFydj);
	TbFydj selectByFydjName(String FydjName);
    List<TbFydj> selectByConAndPage(Page<TbFydj> page, Map params);
    TbFydj selectByPrimaryKey(Integer id);
    int insertFydj(TbFydj tbFydj);
    int modifyUserByPrimaryKey(TbFydj tbFydj);
    int delUserByPrimaryKey(List ids);


}
