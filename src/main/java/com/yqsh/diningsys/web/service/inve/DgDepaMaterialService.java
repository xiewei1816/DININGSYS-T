package com.yqsh.diningsys.web.service.inve;

import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgDepaMaterial;

/**
 * 部门用料业务接口
 * @author jianglei
 * 日期 2016年10月25日 上午10:03:47
 *
 */
public interface DgDepaMaterialService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgDepaMaterial> getPageList(DgDepaMaterial depaMate);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgDepaMaterial depaMate);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgDepaMaterial get(String id);
	/**
	 * 处理部门领用与退料管理
	 * @author jianglei
	 * 日期 2016年11月3日 上午11:36:42
	 * @param depaMate
	 * @param jsonArr
	 * @return
	 */
    Map<String,String> depaMeteInsert(DgDepaMaterial depaMate, String jsonArr);
}
