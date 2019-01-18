package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbDep;

/**
 * 部门管理服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbDepService {

	/**
	 * 分页查询部门信息
	 * @param tbDep
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbDep> getPageList(TbDep tbDep);
	
	/**
	 * 新增、修改部门信息
	 * @param tbDep
	 * @return
	 */
	int insertOrUpdDep(TbDep tbDep);
	
	/**
	 * 通过部门ID查询部门信息
	 * @param tbDep
	 * @return
	 */
	TbDep getDepById(TbDep tbDep);
	
	/**
	 * 删除部门信息
	 * @param tbDep
	 * @return
	 */
	int deleteByIds(TbDep tbDep);
	
	/**
	 * 部门信息回收站
	 * @param tbDep
	 * @return
	 */
	int deleteTrash(TbDep tbDep);
	
	/**
	 * 还原回收站部门信息
	 * @param tbDep
	 * @return
	 */
	int replyDep(TbDep tbDep);
	
	/**
	 * 获取所有部门信息
	 * @return
	 */
	List<TbDep> selectAllDep(Map<String,Object> params);
	
	/**
	 * 根据部门id获取部门信息
	 * @param depId
	 * @return
	 */
	List<TbDep> selectSmallDep(Map<String,Object> params);
	/**
	 * 通过上级代码查询最后一条记录获取代码进行叠加算法
	 * @param tbDep
	 * @return
	 */
	TbDep getLastDepCode(TbDep tbDep);
	
	/**
	 * 通过部门ID查询关联的部门
	 * @param dep
	 * @return
	 */
	TbDep getDepDepartmentNameById(TbDep dep);
	List<TbDep> getAllList(TbDep tbDep);
	TbDep authentication(TbDep tbDep);
	TbDep selectByDepName(String depName);
    List<TbDep> selectByConAndPage(Page<TbDep> page, Map params);
    TbDep selectByPrimaryKey(Integer id);
    int insertDep(TbDep tbDep);
    int modifyUserByPrimaryKey(TbDep tbDep);
    int delUserByPrimaryKey(List ids);
    void synDep(List<TbDep> listDep);






}
