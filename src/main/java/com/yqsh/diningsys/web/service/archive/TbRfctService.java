package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbRfct;

/**
 * 退菜原因类型类型服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbRfctService {

	/**
	 * 分页查询退菜原因类型信息
	 * @param TbTest
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbRfct> getPageList(TbRfct tbRfct);
	
	/**
	 * 新增、修改退菜原因类型信息
	 * @param TbTest
	 * @return
	 */
	int insertOrUpdRfct(TbRfct tbRfct);
	
	/**
	 * 通过退菜原因类型ID查询退菜原因类型信息
	 * @param TbTest
	 * @return
	 */
	TbRfct getRfctById(TbRfct tbRfct);
	
	/**
	 * 删除退菜原因类型信息
	 * @param TbTest
	 * @return
	 */
	int deleteByIds(TbRfct tbRfct);
	
	/**
	 * 退菜原因类型信息回收站
	 * @param TbTest
	 * @return
	 */
	int deleteTrash(TbRfct tbRfct);
	
	/**
	 * 还原回收站退菜原因类型信息
	 * @param TbTest
	 * @return
	 */
	int replyRfct(TbRfct tbRfct);
	
	
	/**
	 * 查询最后一条记录获取代码进行叠加算法
	 * @return
	 */
	TbRfct getLastRfctCode();
	List<TbRfct> getAllList(TbRfct tbRfct);
	TbRfct authentication(TbRfct tbRfct);
	TbRfct selectByRfctName(String rfctName);
    List<TbRfct> selectByConAndPage(Page<TbRfct> page, Map params);
    TbRfct selectByPrimaryKey(Integer id);
    int insertRfct(TbRfct tbRfct);
    int modifyUserByPrimaryKey(TbRfct tbRfct);
    int delUserByPrimaryKey(List ids);


}
