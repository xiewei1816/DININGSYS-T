package com.yqsh.diningsys.web.service.inve;

import java.util.List;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgWarehouse;
/**
 * 仓库管理业务接口
 * @author jianglei
 * 日期 2016年10月18日 下午2:38:20
 *
 */
public interface DgWarehouseService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgWarehouse> getPageList(DgWarehouse warehouse);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:20:57
	 * @param supplier
	 * @return
	 */
    int insert(DgWarehouse warehouse);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgWarehouse warehouse);
	/**
	 * 删除
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:22:21
	 * @param id
	 * @return
	 */
    int delete(List<String> id, String state);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgWarehouse get(String id);
	/**
	 * 根据条件获取数据
	 * @author jianglei
	 * 日期 2016年10月21日 下午4:24:56
	 * @param ware
	 * @return
	 */
    List<DgWarehouse> findListData(DgWarehouse ware);
	/**
	 * 数据同步方法
	 * @author jianglei
	 * 日期 2017年3月2日 下午1:42:02
	 * @param listWare
	 */
    void synWare(List<DgWarehouse> listWare);
}
