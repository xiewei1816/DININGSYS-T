package com.yqsh.diningsys.web.service.inve;

import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgTransfers;

/**
 * 调拨业务接口
 * @author jianglei
 * 日期 2016年10月25日 下午2:16:22
 *
 */
public interface DgTransfersService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgTransfers> getPageList(DgTransfers tran);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgTransfers tran);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgTransfers get(String id);
	/**
	 * 调拨
	 * @author jianglei
	 * 日期 2016年11月3日 下午1:58:56
	 * @param tran
	 * @param jsonArr
	 * @return
	 * @throws DgCustomExceptionClass 
	 */
    Map<String,String> saveTran(DgTransfers tran, String jsonArr);
}
