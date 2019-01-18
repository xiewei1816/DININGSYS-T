package com.yqsh.diningsys.web.service.pay;

import java.util.List;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.pay.DgMerchants;

/**
 * 商户管理
 * @author jianglei
 * 日期 2017年1月10日 下午1:26:37
 *
 */
public interface DgMerchantsService{
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgMerchants> getPageList(DgMerchants merch);
	/**
	 * 分页相关数据查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:03:16
	 * @param supplier
	 * @return
	 */
    Integer countListByPage(DgMerchants merch);
	/**
	 * 保存
	 * @author jianglei
	 * 日期 2017年1月11日 上午9:10:29
	 * @param merch
	 * @return
	 */
    int insert(DgMerchants merch);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2017年1月11日 上午9:10:55
	 * @param merch
	 * @return
	 */
    int update(DgMerchants merch);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2017年1月11日 上午9:15:17
	 * @param id
	 * @return
	 */
    DgMerchants get(String id);
	/**
	 * 批量删除
	 * @author jianglei
	 * 日期 2017年1月12日 上午8:54:27
	 * @param listId
	 * @return
	 */
    int delBatch(List<String> listId);
	/**
	 * 根据组织机构Id查询一条数据
	 * @author jianglei
	 * 日期 2017年1月13日 上午9:11:28
	 * @param orgId
	 * @return
	 */
    DgMerchants findOneMerch(String orgId);
}
