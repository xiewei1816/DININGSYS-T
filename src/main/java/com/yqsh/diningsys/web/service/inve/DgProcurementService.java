package com.yqsh.diningsys.web.service.inve;

import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgProcurement;

/**
 *  采购管理业务接口
 * @author jianglei
 * 日期 2016年10月21日 下午1:21:01
 *
 */
public interface DgProcurementService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgProcurement> getPageList(DgProcurement proc);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgProcurement proc);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgProcurement get(String id);
	/**
	 * 采购入库与采购退货
	 * @author jianglei
	 * 日期 2016年11月2日 上午9:47:34
	 * @param proc
	 * @param jsonArr
	 * @return
	 */
    Map<String,String> insertProc(DgProcurement proc, String jsonArr);

    void insertFastStorage(DgProcurement proc);
}
