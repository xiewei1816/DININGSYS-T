package com.yqsh.diningsys.web.service.inve;

import java.util.List;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.inve.DgItemType;

/**
 * 物品类型接口
 * @author jianglei
 * 日期 2016年10月19日 上午9:00:08
 *
 */
public interface DgItemTypeService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgItemType> getPageList(DgItemType itemType);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:20:57
	 * @param supplier
	 * @return
	 */
    int insert(DgItemType itemType);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgItemType itemType);
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
    DgItemType get(String id);
	/**
	 * 带参不分页获取物品类型
	 * @author jianglei
	 * 日期 2016年10月19日 上午11:23:24
	 * @param itemType
	 * @return
	 */
    List<DgItemType> listItemType(DgItemType itemType);
	/**
	 * 数据同步相关操作
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:05:11
	 * @param listItemType
	 */
    void synData(List<DgItemType> listItemType);
}
