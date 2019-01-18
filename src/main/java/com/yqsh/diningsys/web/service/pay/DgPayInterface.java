package com.yqsh.diningsys.web.service.pay;

import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.PLcswMerchant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 支付接口
 * @author jianglei
 * 日期 2017年1月12日 下午2:26:46
 *
 */
public interface DgPayInterface {
	/**
	 * 店内支付接口
	 * @author jianglei
	 * 日期 2017年1月13日 上午10:44:23
	 * @param payMoney 支付金额
	 * @param orgId 组织机构id
	 * @param payType 支付类型
	 * @param orderNo 订单流水号
	 * @param operUser 操作人
	 * @param auth_code 授权码
	 * @param body 订单描述
	 * @return
	 */
	Map<String, Object> dgPayemnt(Double payMoney, String orgId, String payType, String orderNo, String operUser,
								  String auth_code, String body, HttpServletRequest request, HttpServletResponse response);
	/**
	 * 对账接口
	 * @author jianglei
	 * 日期 2017年1月18日 上午10:05:07
	 * @param orderNo
	 * @return
	 */
	Map<String,Object> payCheck(String orderNo,HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 店内支付接口
	 * @author hs
	 * @param payMoney 支付金额
	 * @param orgId 组织机构id
	 * @param payType 支付类型
	 * @param orderNo 订单流水号
	 * @param operUser 操作人
	 * @param auth_code 授权码
	 * @param body 订单描述
	 * @return
	 */
	Map<String,Object> payULine(Double payMoney, String orgId, String payType, String orderNo, String operUser,
			  String auth_code, String body, HttpServletRequest request, HttpServletResponse response);


	/**
	 * 扫呗刷卡支付
	 * @param payType
	 * @param auth_code
	 * @param total_fee
	 * @param body
	 * @param outTradeNo
	 * @param request
	 * @param response
	 * @return
	 */
	boolean slotCardPayNew(String payType,String auth_code,String total_fee,String body,String outTradeNo,String operUser,String jsNum,
								  HttpServletRequest request,HttpServletResponse response);


	/**
	 * 扫呗查询接口
	 * @param terminalTrace
	 * @param outTradeNo
	 * @param payType
	 * @param merchant
	 * @return
	 */
	String queryOrder(String terminalTrace,String outTradeNo, String payType, PLcswMerchant merchant);



	/**
	 * 扫呗生存动态二维码
	 * @param payType
	 * @param auth_code
	 * @param total_fee
	 * @param body
	 * @param outTradeNo
	 * @param request
	 * @param response
	 * @return
	 */
	Map<String,Object>  createPayQr(String payType, String total_fee, String operUser, String orderWater);
}
