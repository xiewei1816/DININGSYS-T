package com.yqsh.diningsys.web.dao.inve;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.inve.DgDiscPoint;

/**
 * 盘点mapper接口
 * @author jianglei
 * 日期 2016年10月26日 上午11:33:04
 *
 */
@Repository
public interface DgDiscPointMapper {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    List<DgDiscPoint> getPageList(DgDiscPoint discPoint);
	/**
	 * 分页相关数据查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:03:16
	 * @param supplier
	 * @return
	 */
    Integer countListByPage(DgDiscPoint discPoint);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:20:57
	 * @param supplier
	 * @return
	 */
    int insert(DgDiscPoint discPoint);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgDiscPoint discPoint);
	/**
	 * 根据id获信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgDiscPoint get(@Param("id") String id);
	/**
	 * 根据条件查询一条数据
	 * @author jianglei
	 * 日期 2016年11月2日 下午1:37:58
	 * @param supplier
	 * @return
	 */
    DgDiscPoint findLastOne(DgDiscPoint discPoint);
}
