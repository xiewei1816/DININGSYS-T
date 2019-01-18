package com.yqsh.diningsys.web.service.archive;


import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.feature.orm.mybatis.Page;
import com.yqsh.diningsys.web.model.archive.TbEmp;

/**
 * 员工管理服务层
* @author xiewei
* @version 创建时间：2016年9月29日 上午9:36:00
 */
public interface TbEmpService {

	/**
	 * 分页查询员工信息
	 * @param tbEmp
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<TbEmp> getPageList(TbEmp tbEmp);
	
	/**
	 * 新增、修改员工信息
	 * @param tbEmp
	 * @return
	 */
	int insertOrUpdEmp(TbEmp tbEmp);
	
	/**
	 * 通过员工ID查询员工信息
	 * @param tbEmp
	 * @return
	 */
	TbEmp getEmpById(TbEmp tbEmp);
	
	/**
	 * 删除员工信息
	 * @param tbEmp
	 * @return
	 */
	int deleteByIds(TbEmp tbEmp);
	
	/**
	 * 员工信息回收站
	 * @param tbEmp
	 * @return
	 */
	int deleteTrash(TbEmp tbEmp);
	
	/**
	 * 还原回收站员工信息
	 * @param tbEmp
	 * @return
	 */
	int replyEmp(TbEmp tbEmp);
	
	List<TbEmp> getAllList(TbEmp tbEmp);
    TbEmp authentication(TbEmp tbEmp);
    TbEmp selectByEmpName(String empName);
    List<TbEmp> selectByConAndPage(Page<TbEmp> page, Map params);
    TbEmp selectByPrimaryKey(Integer id);
    int insertEmp(TbEmp tbEmp);
    int modifyUserByPrimaryKey(TbEmp TbEmp);
    int delUserByPrimaryKey(List ids);


}
