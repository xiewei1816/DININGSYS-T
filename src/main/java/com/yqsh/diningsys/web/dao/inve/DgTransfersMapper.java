package com.yqsh.diningsys.web.dao.inve;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.inve.DgTransfers;

/**
 * 调拨mapper接口
 * @author jianglei
 * 日期 2016年10月25日 下午2:14:51
 *
 */
@Repository
public interface DgTransfersMapper {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    List<DgTransfers> getPageList(DgTransfers tran);
	/**
	 * 分页相关数据查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:03:16
	 * @param supplier
	 * @return
	 */
    Integer countListByPage(DgTransfers tran);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:20:57
	 * @param supplier
	 * @return
	 */
    int insert(DgTransfers tran);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgTransfers tran);
	/**
	 * 根据id获信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgTransfers get(@Param("id") String id);
	/**
	 * 根据条件查询一条数据
	 * @author jianglei
	 * 日期 2016年11月2日 下午1:37:58
	 * @param supplier
	 * @return
	 */
    DgTransfers findLastOne(DgTransfers tran);
}
