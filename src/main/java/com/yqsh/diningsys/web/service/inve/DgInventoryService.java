package com.yqsh.diningsys.web.service.inve;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgInventory;

/**
 * 库存管业务接口
 * @author jianglei
 * 日期 2016年10月19日 下午5:05:20
 *
 */
public interface DgInventoryService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgInventory> getPageList(DgInventory inventory);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:20:57
	 * @param supplier
	 * @return
	 */
    int insert(DgInventory inventory);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgInventory inventory);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgInventory get(String id);
	/**
	 * 根据相关条件获取数据
	 * @author jianglei
	 * 日期 2016年10月24日 下午2:52:58
	 * @param inve
	 * @return
	 */
    List<DgInventory> findListData(DgInventory inve);
	/**
	 * 导出
	 * @author jianglei
	 * 日期 2016年10月31日 上午11:36:21
	 * @param inve
	 * @return
	 */
    List<DgInventory> exportXls(DgInventory inve);
	/**
	 * 根据仓库或物品类型获取仓库所属物品
	 * @author jianglei
	 * 日期 2016年11月4日 上午10:01:00
	 * @param inve
	 * @param itemTypeId
	 * @return
	 */
    List<Map<String,Object>> ajaxInveItems(DgInventory inve, String itemTypeId);
}
