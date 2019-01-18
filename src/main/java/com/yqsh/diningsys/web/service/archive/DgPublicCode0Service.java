package com.yqsh.diningsys.web.service.archive;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.web.model.archive.DgPublicCode0;

/**
 * 公共代码服务层
 * @author xiewei
 *
 */
public interface DgPublicCode0Service {

	/**
	 * 分页查询公共代码
	 * @param DgPublicCode0
	 * @return
	 */
	com.yqsh.diningsys.core.util.Page<DgPublicCode0> getPageList(DgPublicCode0 dpc);
	
	/**
	 * 新增、修改公共代码
	 * @param dpc
	 * @return
	 */
	int insertOrUpdDpc(DgPublicCode0 dpc);
	
	/**
	 * 通过ID查询公共代码
	 * @param dpc
	 * @return
	 */
	DgPublicCode0 getDpcById(DgPublicCode0 dpc);
	
	/**
	 * 通过公共代码ID查询关联的公共代码
	 * @param dpc
	 * @return
	 */
	DgPublicCode0 getDpcParentNameById(DgPublicCode0 dpc);

	/**
	 * 新增公共代码
	 * @param dpc
	 * @return
	 */
    int insertDpc(DgPublicCode0 dpc);
    
	/**
	 * 删除公共代码
	 * @param dpc
	 * @return
	 */
	int deleteByIds(DgPublicCode0 dpc);
	
	/**
	 * 公共代码回收站
	 * @param dpc
	 * @return
	 */
	int deleteTrash(DgPublicCode0 dpc);
	
	/**
	 * 还原回收站公共代码
	 * @param dpc
	 * @return
	 */
	int replyDpc(DgPublicCode0 dpc);
	
	/**
	 * 获取所有公共代码
	 * @return
	 */
	List<DgPublicCode0> selectAllDpc(Map<String,Object> params);
	
	/**
	 * 导出公共代码
	 * @param dpc
	 * @return
	 */
	List<DgPublicCode0> getAllList(DgPublicCode0 dpc);
	
	/**
	 * 通过上级代码查询最后一条记录获取代码进行叠加算法 
	 * @param dpc
	 * @return
	 */
	DgPublicCode0 getLastcCode(DgPublicCode0 dpc);

	/**
	 * 根据部门cParent获取公共代码信息
	 * @param params
	 * @return
	 */
	List<DgPublicCode0> selectSmallDpc(Map<String, Object> params);

	/**
	 * 获取并封装所有字典数据提供至前台
	 * @return
	 */
	List<Map<String, List<DgPublicCode0>>> getAllDpcToPage();
	

	/**
	 * 根据父名称获取子数据
	 * @param params
	 * @return
	 */
	List<DgPublicCode0> selectSmallByParentName(Map<String, Object> params);
	/**
	 * 根据条件修改速记码
	 * @author jianglei
	 * 日期 2016年12月26日 下午6:54:53
	 * @param pubCode
	 * @return
	 */
	int updatePubCode(DgPublicCode0 pubCode);
	/**
	 * 根据条件检测数据是否正在同步
	 * @author jianglei
	 * 日期 2016年12月26日 下午6:55:42
	 * @param pubCode
	 * @return
	 */
	int isExists(DgPublicCode0 pubCode);

	List<DgPublicCode0> selectPublicCodeByKey(String key);

}
