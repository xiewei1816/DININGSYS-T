package com.yqsh.diningsys.web.service.pay;

import java.util.List;

import com.yqsh.diningsys.core.util.Page;
import com.yqsh.diningsys.web.model.pay.DgPayWater;
import org.apache.ibatis.annotations.Param;

/**
 * 支付流水接口
 * @author jianglei
 * 日期 2017年1月12日 上午10:06:55
 *
 */
public interface DgPayWaterService {
	/**
	 * 带参分页查询
	 * @author jianglei
	 * 日期 2016年10月17日 下午12:52:17
	 * @param supplier
	 * @return
	 */
    Page<DgPayWater> getPageList(DgPayWater payWater);
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
	 * 修改流水相关信息
	 * @author jianglei
	 * 日期 2017年1月13日 上午11:09:22
	 * @param outTradeNo 商户订单号
	 * @param tradeTime  交易时间
	 * @param threeTradeNo 第三方订单号
	 * @param payPeopleInfo 支付人信息
	 * @return
	 */
    int update(String outTradeNo, String tradeTime, String threeTradeNo, String payPeopleInfo);
	/**
	 * 根据id获取信息
	 * @author jianglei
	 * 日期 2017年1月11日 上午9:15:17
	 * @param id
	 * @return
	 */
    DgPayWater get(String id);
	/**
	 * 带参查询数据
	 * @author jianglei
	 * 日期 2017年1月16日 上午10:47:11
	 * @param water
	 * @return
	 */
    List<DgPayWater> findAllObj(DgPayWater water);


	void deleteByOpenWater(String orderNo,String payType);
}
