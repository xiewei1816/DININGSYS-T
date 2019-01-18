package com.yqsh.diningsys.web.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.pay.DgMerchants;

/**
 * 商户管理mapper接口
 * @author jianglei
 * 日期 2017年1月10日 下午1:24:03
 *
 */
@Repository
public interface DgMerchantsMapper{
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    List<DgMerchants> getPageList(DgMerchants merch);
	/**
	 * 分页相关数据查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:03:16
	 * @param supplier
	 * @return
	 */
    Integer countListByPage(DgMerchants merch);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2017年1月10日 下午4:25:18
	 * @param merch
	 * @return
	 */
    int insert(DgMerchants merch);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2017年1月10日 下午4:25:52
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
    DgMerchants get(@Param("id") String id);
	/**
	 * 批量删除
	 * @author jianglei
	 * 日期 2017年1月12日 上午8:54:27
	 * @param listId
	 * @return
	 */
    int delBatch(@Param("listId") List<String> listId);
	/**
	 * 根据组织机构Id查询一条数据
	 * @author jianglei
	 * 日期 2017年1月13日 上午9:11:28
	 * @param orgId
	 * @return
	 */
    DgMerchants findOneMerch(@Param("orgId") String orgId);
}
