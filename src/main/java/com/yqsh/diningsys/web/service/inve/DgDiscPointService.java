package com.yqsh.diningsys.web.service.inve;

import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgDiscPoint;

/**
 * 盘点业务接口
 * @author jianglei
 * 日期 2016年10月26日 上午11:35:42
 *
 */
public interface DgDiscPointService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgDiscPoint> getPageList(DgDiscPoint discPoint);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgDiscPoint discPoint);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgDiscPoint get(String id);
	/**
	 * 调拨
	 * @author jianglei
	 * 日期 2016年11月3日 下午1:58:56
	 * @param tran
	 * @param jsonArr
	 * @return
	 * @throws DgCustomExceptionClass 
	 */
    Map<String,String> saveDiscPoint(DgDiscPoint discPoint, String jsonArr);
}
