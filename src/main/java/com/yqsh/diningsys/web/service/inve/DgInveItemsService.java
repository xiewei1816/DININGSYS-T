package com.yqsh.diningsys.web.service.inve;

import java.util.List;
import java.util.Map;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgInveItems;

/**
 * 物品管理接口
 * @author jianglei
 * 日期 2016年10月19日 上午10:25:10
 *
 */
public interface DgInveItemsService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgInveItems> getPageList(DgInveItems items);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:20:57
	 * @param supplier
	 * @return
	 */
    int insert(DgInveItems items);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgInveItems items);
	/**
	 * 删除
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:22:21
	 * @param id
	 * @return
	 */
    int delete(List<Map<String, String>> listMap);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgInveItems get(String id);
	/**
	 * 根据条件获取数据,不分页
	 * @author jianglei
	 * 日期 2016年10月21日 下午4:24:56
	 * @param ware
	 * @return
	 */
    List<DgInveItems> findListData(DgInveItems items);
	/**
	 * 根据名称检测数据是否存在
	 * @author jianglei
	 * 日期 2016年10月26日 下午1:58:59
	 * @param items
	 * @return
	 */
    int existsName(DgInveItems items);
	
	/**
	 * 根据树节点获取数据
	 * @return
	 */
    List<Map<String, Object>> getByTreeId(Map<String, Object> map);
	/**
	 * 查询已有数据
	 * @return
	 */
    List<Map<String, Object>> selectHaveItem(String list);
	/**
	 * 数据同步相关操作
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:05:11
	 * @param listItemType
	 */
    void synData(List<DgInveItems> listInveItems);

    DgInveItems selectInveItemByCode(String code);
}
