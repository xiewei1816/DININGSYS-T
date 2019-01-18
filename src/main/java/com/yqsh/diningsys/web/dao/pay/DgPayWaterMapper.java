package com.yqsh.diningsys.web.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yqsh.diningsys.web.model.pay.DgPayWater;
/**
 * 客户使用第三方支付方式(微信、支付宝)支付流水接口
 * @author jianglei
 * 日期 2017年1月12日 上午9:57:06
 *
 */
@Repository
public interface DgPayWaterMapper {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    List<DgPayWater> getPageList(DgPayWater payWater);
	/**
	 * 分页相关数据查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午1:03:16
	 * @param supplier
	 * @return
	 */
    Integer countListByPage(DgPayWater payWater);
	/**
	 * 新增
	 * @author jianglei
	 * 日期 2017年1月10日 下午4:25:18
	 * @param merch
	 * @return
	 */
    int insert(DgPayWater payWater);
	/**
	 * 修改
	 * @author jianglei
	 * 日期 2017年1月10日 下午4:25:52
	 * @param merch
	 * @return
	 */
    int update(DgPayWater payWater);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2017年1月11日 上午9:15:17
	 * @param id
	 * @return
	 */
    DgPayWater get(@Param("id") String id);
	/**
	 * 带参查询数据
	 * @author jianglei
	 * 日期 2017年1月16日 上午10:47:11
	 * @param water
	 * @return
	 */
    List<DgPayWater> findAllObj(DgPayWater water);


    void deleteByOpenWater(@Param("orderNo")String orderNo,@Param("payType")String payType);
}
