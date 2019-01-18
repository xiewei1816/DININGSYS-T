package com.yqsh.diningsys.web.dao.inve;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.inve.DgSupplier;

/**
 * 供应商mapper接口
 * @author jianglei
 * 日期 2016年10月17日 上午9:54:58
 *
 */
@Repository
public interface DgSupplierMapper {
	/**
	 * 带参分页查询供应商
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    List<DgSupplier> getPageList(DgSupplier supplier);
	/**
	 * 分页相关数据查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:03:16
	 * @param supplier
	 * @return
	 */
    Integer countListByPage(DgSupplier supplier);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:20:57
	 * @param supplier
	 * @return
	 */
    int insert(DgSupplier supplier);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:21:36
	 * @param supplier
	 * @return
	 */
    int update(DgSupplier supplier);
	/**
	 * 删除
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:22:21
	 * @param id
	 * @return
	 */
    int delete(@Param("id") List<String> id, @Param("state") String state);
	/**
	 * 根据id获取供应商信息
	 * @author jianglei
	 * 日期 2016年10月17日 下午2:06:11
	 * @param id
	 * @return
	 */
    DgSupplier get(@Param("id") String id);
	/**
	 * 根据条件获取数据,不分页
	 * @author jianglei
	 * 日期 2016年10月21日 下午4:24:56
	 * @param ware
	 * @return
	 */
    List<DgSupplier> findListData(DgSupplier supplier);
	/**
	 * 根据条件查询一条数据
	 * @author jianglei
	 * 日期 2016年11月2日 下午1:37:58
	 * @param supplier
	 * @return
	 */
    DgSupplier findLastOne(DgSupplier supplier);
}
