package com.yqsh.diningsys.core.util.pay;

import java.util.SortedMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.SerialNumberUtil;
import com.yqsh.diningsys.web.model.pay.DgMerchants;
import com.yqsh.diningsys.web.model.pay.DgPayWater;
import com.yqsh.diningsys.web.model.pay.Dg_WxUnifiedOrderInfo;
import com.yqsh.diningsys.web.service.pay.DgMerchantsService;
import com.yqsh.diningsys.web.service.pay.DgPayWaterService;

/**
 * 支付相关公用类
 * @author jianglei
 * 日期 2017年1月16日 下午12:12:54
 *
 */
@Component
public class DgPayUtil {
	@Autowired
	private  DgMerchantsService dgMerchantsService;
	@Autowired
	private  DgPayWaterService dgPayWaterService;
	
	
	private static DgPayUtil dgPayUtil;

    @PostConstruct
    public void init() {  
		dgPayUtil = this;  
		dgPayUtil.dgMerchantsService = this.dgMerchantsService;  
		dgPayUtil.dgPayWaterService=this.dgPayWaterService;
  
    }  
	/**
	 * 
	 * 查询微信订单状态
	 * @author jianglei
	 * 2016年7月28日 下午5:45:06
	 * @return
	 */
	public static Element selectWxOrder(DgMerchants merchant,String out_trade_no,String threeTradeNo,HttpServletRequest request,HttpServletResponse response){
		Dg_WxUnifiedOrderInfo orderInfo=new Dg_WxUnifiedOrderInfo();
		orderInfo.setAppid(merchant.getWxAppId());
		orderInfo.setMch_id(merchant.getWxMchId());
		orderInfo.setOut_trade_no(out_trade_no);
		orderInfo.setNonce_str(SerialNumberUtil.create_nonce_str());
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String,String> sortSignMap=reqHandler.returnCreateSign(orderInfo);
		reqHandler.init(null,null,merchant.getWxApiSecretKey());
		String sign = reqHandler.createSign(sortSignMap);
		   orderInfo.setSign(sign);
		   String xmlData=DgPayClassUtil.entityToXML(orderInfo);
		Element rootMap=DgPayClassUtil.httpReqPraseXML(DgConstants.ORDERQUERY_URL, "POST", xmlData);
		return rootMap;
	}
	
	/**
	 * 微信单条订单对账
	 * @author jianglei
	 * 日期 2016年8月29日 下午1:25:43
	 * @return
	 */
	public static String wxCheck(DgPayWater water, HttpServletRequest request, HttpServletResponse response) {
		String msg = DgConstants.RES_MEG;
		try {
			if (DgPayWater.PAYSTATE_FAIL.equals(water.getPayState())) {
				// 获取商户信息
				DgMerchants merch = dgPayUtil.dgMerchantsService.get(water.getMerchId());
				if (merch != null) {
					Element elm = DgPayUtil.selectWxOrder(merch, water.getOutTradeNo(), water.getThreeTradeNo(),
							request, response);
					if (null != elm) {
						String threeTradeNo = elm.elementText("transaction_id");
						String payPeopleInfo = elm.elementText("openid");
						String tradeTime = elm.elementText("time_end"); // 交易完成时间
						if ("SUCCESS".equals(elm.elementText("return_code"))
								&& "SUCCESS".equals(elm.elementText("result_code"))
								&& "SUCCESS".equals(elm.elementText("trade_state"))) {
							// 修改流水状态
							dgPayUtil.dgPayWaterService.update(water.getOutTradeNo(), tradeTime, threeTradeNo,
									payPeopleInfo);
							msg = DgConstants.RES_MEG;
						} else {
							msg = "订单未支付成功!";
						}
					} else {
						msg = "对账失败!";
					}
				} else {
					msg = "商户信息获取失败!";
				}
			} else {
				msg = "请选择失败的数据对账!";
			}
		} catch (Exception e) {
			msg = "对账失败!";
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * 支付宝对账
	 * @author jianglei
	 * 日期 2017年1月17日 上午9:07:29
	 * @param water
	 * @param request
	 * @param response
	 * @return
	 * @throws AlipayApiException 
	 */
	public static String aliPayCheck(DgPayWater water){
		String msg = DgConstants.RES_MEG;
		if (DgPayWater.PAYSTATE_FAIL.equals(water.getPayState())) {
			// 获取商户信息
			DgMerchants mch = dgPayUtil.dgMerchantsService.get(water.getMerchId());
			if (mch != null) {
				try {
					AlipayClient alipayClient = new DefaultAlipayClient(DgConstants.ZFB_ALIPAY_URL, mch.getZfbAppid(),
							mch.getZfbPrivateKey(), "json", "UTF-8", mch.getZfbAlipayPublicKey());
					AlipayTradeQueryRequest aliReq = new AlipayTradeQueryRequest();
					aliReq.setBizContent("{" + "\"out_trade_no\":\"" + water.getOutTradeNo() + "\" }");
					AlipayTradeQueryResponse aliResp = alipayClient.execute(aliReq);
					if ("TRADE_SUCCESS".equals(aliResp.getTradeStatus())) {
						String threeTradeNo = aliResp.getTradeNo();
						String payPeopleInfo = aliResp.getBuyerLogonId();
						String endTime = DateUtil.dateToStr(aliResp.getSendPayDate(), DateUtil.YYYY_MM_DD_HH_MM_SS);
						String time_end = DateUtil.fromatStrToStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS,
								DateUtil.YYYYMMDDHH24MISS);
						// 修改流水状态
						dgPayUtil.dgPayWaterService.update(water.getOutTradeNo(), time_end, threeTradeNo, payPeopleInfo);
						
					} else {
						msg = "订单未支付成功!";
					}
				} catch (Exception e) {
					msg = "对账失败!";
					e.printStackTrace();
				}
				
			} else {
				msg = "商户信息获取失败!";
			}
		} else {
			msg = "请选择失败的数据对账!";
		}

		return msg;
	}
}
