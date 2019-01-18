package com.yqsh.diningsys.web.dao.inve;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.inve.DgInveItems;

/**
 * 物品管理
 * @author jianglei
 * 日期 2016年10月19日 上午10:20:21
 *
 */
@Repository
public interface DgInveItemsMapper {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    List<DgInveItems> getPageList(DgInveItems items);
	/**
	 * 分页相关数据查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:03:16
	 * @param supplier
	 * @return
	 */
    Integer countListByPage(DgInveItems items);
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
    int delete(@Param("listMap") List<Map<String, String>> listMap);
	/**
	 * 根据id获信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgInveItems get(@Param("id") String id);
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
    List<Map<String, Object>> selectHaveItem(List<String> list);
	/**
	 * 物理删除所有数据，此方法慎用
	 * @author jianglei
	 * 日期 2017年1月18日 上午9:14:16
	 */
    void delPhy();
	/**
	 * 批量插入
	 * @author jianglei
	 * 日期 2017年1月18日 上午11:07:10
	 * @param listObj
	 */
    void insertBatch(@Param("listObj") List<DgInveItems> listObj);

    DgInveItems selectInveItemByCode(@Param("code") String code);

	DgInveItems selectInveItemById(@Param("id") String id);
}
