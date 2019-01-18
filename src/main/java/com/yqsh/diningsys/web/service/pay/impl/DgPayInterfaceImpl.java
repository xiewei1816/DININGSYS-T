package com.yqsh.diningsys.web.service.pay.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.yqsh.diningsys.core.util.StringUtil;
import com.yqsh.diningsys.core.util.pay.*;
import com.yqsh.diningsys.web.dao.deskBusiness.currentOpenWater.PLcswMerchantMapper;
import com.yqsh.diningsys.web.dao.sysSettings.DgUrlSettingMapper;
import com.yqsh.diningsys.web.model.deskBusiness.currentOpenWater.PLcswMerchant;
import com.yqsh.diningsys.web.util.OnlineHttp;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.core.util.Constants;
import com.yqsh.diningsys.core.util.DateUtil;
import com.yqsh.diningsys.core.util.SerialNumberUtil;
import com.yqsh.diningsys.web.model.pay.DgMerchants;
import com.yqsh.diningsys.web.model.pay.DgPayWater;
import com.yqsh.diningsys.web.model.pay.Dg_WxUnifiedOrderInfo;
import com.yqsh.diningsys.web.model.pay.MicropayInfo;
import com.yqsh.diningsys.web.service.pay.DgMerchantsService;
import com.yqsh.diningsys.web.service.pay.DgPayInterface;
import com.yqsh.diningsys.web.service.pay.DgPayWaterService;

import net.sf.json.JSONObject;

/**
 * 支付接口
 * @author jianglei
 * 日期 2017年1月12日 下午2:27:10
 *
 */
@Service
public class DgPayInterfaceImpl extends ApiBaseController implements DgPayInterface{

	private static Logger logger = Logger.getLogger(DgPayInterfaceImpl.class);
	@Autowired
	private DgPayWaterService dgPayWaterService;
	@Autowired
	private DgMerchantsService merchantsService;
	@Autowired
	private PLcswMerchantMapper pLcswMerchantMapper;
	@Autowired
	private DgUrlSettingMapper dgUrlSettingMapper;
	/**
	 * 支付接口
	 */
	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Map<String, Object> dgPayemnt(Double payMoney, String orgId, String payType, String orderNo, String operUser,
			String auth_code,String body,HttpServletRequest request,HttpServletResponse response) {
		try {
			APIEnumDefine relust = vailParams(payMoney, orgId, payType);
			if(APIEnumDefine.S001.equals(relust)){
			 String outTradeNo=SerialNumberUtil.cretatOrderNo(8);
			 //获取商户信息
			 DgMerchants merch=merchantsService.findOneMerch(orgId);
			 if(StringUtils.isBlank(merch.getId())){
				 return getResult(APIEnumDefine.P01004);
			 }
			 DgPayWater water=assWater(payMoney, orgId, payType, orderNo, operUser, outTradeNo, merch.getId());
			 int relustWater=dgPayWaterService.insert(water);
			 if(relustWater<1){
				 return getResult(APIEnumDefine.P01005);
			 }
			 Map<String, String> mapStr=new HashMap<String, String>();
			 mapStr.put("outTradeNo", outTradeNo);
	          String returnMsg="fail";
			 if(Constants.PAY_TYPE_WX.equals(payType)){
				 returnMsg=wxPay(merch, auth_code, payMoney, body, outTradeNo, request, response);
			 }else if(Constants.PAY_TYPE_ZFB.equals(payType)){
				 returnMsg=aliPay(merch, auth_code, payMoney, body, outTradeNo);
			 }else{
				 return getResult(APIEnumDefine.P01006,mapStr);
			 }
			 if(!DgConstants.RES_MEG.equals(returnMsg)){
				 return getResult(APIEnumDefine.P01007,mapStr);
			 }
				return getSuccessResult(mapStr);
			}else{
				return getResult(relust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getResult(APIEnumDefine.P01007);
	}
	/**
	 * 验证参数是否合法
	 * @author jianglei
	 * 日期 2017年1月12日 下午2:55:29
	 * @return
	 */
	private APIEnumDefine vailParams(Double payMoney, String orgId, String payType){
		if(StringUtils.isBlank(payType)){
			return APIEnumDefine.P01001;
		}
		if(payMoney<=0){
			return APIEnumDefine.P01002;
		}
		if(StringUtils.isBlank(orgId)){
			return APIEnumDefine.P01003;
		}
		return APIEnumDefine.S001;
	}
	/**
	 * 组装订单流水
	 * @author jianglei
	 * 日期 2017年1月12日 下午3:20:27
	 * @param payMoney
	 * @param str
	 * @return
	 */
	private DgPayWater assWater(Double payMoney, String orgId, String payType, String orderNo, String operUser,String outTradeNo,String merchId){
		DgPayWater water=new DgPayWater();
		water.setId(UUID.randomUUID().toString());
		water.setOutTradeNo(outTradeNo);
		water.setPayMoney(BigDecimal.valueOf(payMoney));
		water.setPayType(payType);
		water.setPayState(DgPayWater.PAYSTATE_FAIL);
		water.setMerchId(merchId);
		water.setOrderNo(orderNo);
		water.setOrgId(orgId);
		water.setOperUser(operUser);
		water.setCreateDate(DateUtil.dateToStr(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		return water;
	}



	/**
	 * 组装订单流水
	 * @author jianglei
	 * 日期 2017年1月12日 下午3:20:27
	 * @param payMoney
	 * @param str
	 * @return
	 */
	private void assSbWater(String payMoney, String orgId, String payType, String orderNo, String operUser,String outTradeNo,String merchId,String threeTradeNo,String isSuccess){
		DgPayWater water=new DgPayWater();
		water.setId(UUID.randomUUID().toString());
		water.setOutTradeNo(outTradeNo);
		water.setPayMoney(new BigDecimal(payMoney));
		water.setPayType(payType);
		water.setPayState(isSuccess);
		water.setMerchId(merchId);
		water.setOrderNo(orderNo);
		water.setThreeTradeNo(threeTradeNo);
		water.setOrgId(orgId);
		water.setOperUser(operUser);
		water.setCreateDate(DateUtil.dateToStr(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS_SSS));
		dgPayWaterService.insert(water);
	}
	/**
	 * 微信刷卡支付
	 * @author jianglei
	 * 日期 2016年11月21日 上午9:25:21
	 * @param mch 
	 * @param yearMonth
	 * @param auth_code
	 * @param total_fee
	 * @param body
	 * @param outTradeNo
	 * @param request
	 * @param response
	 * @return
	 * @throws InterruptedException 
	 */
	public String wxPay(DgMerchants mch,String auth_code,Double total_fee,String body,String outTradeNo,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException{
		
		String msg="fail";
		Dg_WxUnifiedOrderInfo orderInfo=new Dg_WxUnifiedOrderInfo();
		orderInfo.setBody(body);
		Map<String,Object> mStr=new HashMap<String,Object>();
		String josnMapStr=JSONObject.fromObject(mStr).toString();
		orderInfo.setAttach(josnMapStr);
		String nonce_str=SerialNumberUtil.create_nonce_str(); //随机字符串
		if(StringUtils.isNotBlank(auth_code))orderInfo.setAuth_code(auth_code.trim()); //授权码
		int a=(int) (total_fee*100);
		orderInfo.setTotal_fee(a+"");
		if(mch!=null){
			orderInfo.setAppid(mch.getWxAppId());
			orderInfo.setMch_id(mch.getWxMchId());
			orderInfo.setNonce_str(nonce_str);
			orderInfo.setOut_trade_no(outTradeNo);
			orderInfo.setSpbill_create_ip(DgConstants.getIpAddr(request));
		}
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String,String> sortSignMap=reqHandler.returnCreateSign(orderInfo);
			reqHandler.init(null,null,mch.getWxApiSecretKey());
			String sign = reqHandler.createSign(sortSignMap);
			   orderInfo.setSign(sign);
			   String xmlData=DgPayClassUtil.entityToXML(orderInfo);
			Element root=DgPayClassUtil.httpReqPraseXML(DgConstants.MICROPAY_URL, "POST", xmlData);
			if (null != root) {
				String return_code = root.elementText("return_code");
				String tradeTime=null;
				if(StringUtils.isNotBlank(root.elementText("time_end")))tradeTime=root.elementText("time_end"); //交易完成时间
				if("SUCCESS".equals(return_code)) {
					String return_msg = root.elementText("return_msg");
					String result_code=root.elementText("result_code");
					String err_code=null;
					String threeTradeNo=root.elementText("transaction_id");
					String payPeopleInfo=root.elementText("openid");
					if(StringUtils.isNotBlank(root.elementText("err_code")))err_code=root.elementText("err_code");
					if ("OK".equals(return_msg)&&"SUCCESS".equals(result_code)) {
						msg=DgConstants.RES_MEG;
						//修改流水状态
						dgPayWaterService.update(outTradeNo, tradeTime, threeTradeNo, payPeopleInfo);
					}else if("FAIL".equals(result_code)&&("USERPAYING".equals(err_code)
							||"BANKERROR".equals(err_code)||"SYSTEMERROR".equals(err_code))){
							long beginTime=System.currentTimeMillis();//开始时间
							long endTime=60*1000;//运行时间
							while (true) {
								long nowTime=System.currentTimeMillis();//当前时间
								if((nowTime-beginTime)<endTime){
									Thread.sleep(5000);
									Element elm=DgPayClassUtil.selectWxOrder(root, mch, request, response,outTradeNo);
									if(null!=elm){
										if("SUCCESS".equals(elm.elementText("return_code"))&&"SUCCESS".equals(elm.elementText("result_code"))
												&&"SUCCESS".equals(elm.elementText("trade_state"))){
											msg=DgConstants.RES_MEG;
											//修改流水状态
											tradeTime=elm.elementText("time_end"); //交易完成时间
											dgPayWaterService.update(outTradeNo, tradeTime, threeTradeNo, payPeopleInfo);
											break;
										}
									}
								}else{
									break;
								}
							}
					}
				}
			}
		return msg;
	}
	/**
	 * 支付宝刷卡支付
	 * @author jianglei
	 * 日期 2017年1月17日 上午9:08:19
	 * @param mch 商户对象
	 * @param auth_code 支付授权码
	 * @param total_fee 支付金额
	 * @param body 订单描述
	 * @param outTradeNo 商户订单号
	 * @return
	 * @throws AlipayApiException
	 */
	private String aliPay(DgMerchants mch,String auth_code,Double total_fee,String body,String outTradeNo) throws AlipayApiException{
		String msg=DgConstants.RES_MEG;
		AlipayClient alipayClient = null;
		if ("RSA2".equalsIgnoreCase(mch.getRemark())) {
			alipayClient = new DefaultAlipayClient(DgConstants.ZFB_ALIPAY_URL, mch.getZfbAppid(),
					mch.getZfbPrivateKey(), "json", "UTF-8", mch.getZfbAlipayPublicKey(), "RSA2");
		} else {
			alipayClient = new DefaultAlipayClient(DgConstants.ZFB_ALIPAY_URL, mch.getZfbAppid(),
					mch.getZfbPrivateKey(), "json", "UTF-8", mch.getZfbAlipayPublicKey());
		}
		AlipayTradePayRequest zfbReq = new AlipayTradePayRequest();
		// 创建刷卡支付请求builder，设置请求参数
		Map<String,Object> paramsMapObj=new HashMap<String,Object>();
		paramsMapObj.put("subject", body);
		paramsMapObj.put("total_amount", String.valueOf(total_fee));
		paramsMapObj.put("out_trade_no", outTradeNo);
		paramsMapObj.put("undiscountable_amount", "0");
		paramsMapObj.put("seller_id", "");
		paramsMapObj.put("store_id", "123");
		paramsMapObj.put("auth_code", auth_code);
		paramsMapObj.put("scene", "bar_code");
		zfbReq.setBizContent(JSONObject.fromObject(paramsMapObj).toString());
			AlipayTradePayResponse zfbRep = alipayClient.execute(zfbReq);
			if (zfbRep.isSuccess()) {
				String threeTradeNo=zfbRep.getTradeNo();
				String payPeopleInfo=zfbRep.getBuyerLogonId();
					String endTime=DateUtil.dateToStr(zfbRep.getGmtPayment(),DateUtil.YYYY_MM_DD_HH_MM_SS);
					String time_end = DateUtil.fromatStrToStr(endTime,
					DateUtil.YYYY_MM_DD_HH_MM_SS, DateUtil.YYYYMMDDHH24MISS);
					try {
						//修改流水状态
						dgPayWaterService.update(outTradeNo, time_end, threeTradeNo, payPeopleInfo);
					} catch (Exception e) {
						e.printStackTrace();
					}
			} else {
				msg="fail";
			}
		return msg;
	}
	@Override
	public Map<String, Object> payCheck(String outTradeNo,HttpServletRequest request,HttpServletResponse response) {
		if(StringUtils.isBlank(outTradeNo)){
			getResult(APIEnumDefine.P01008);
		}
		String msg=DgConstants.RES_MEG;
		//获取流水信息
		DgPayWater dpw=new DgPayWater();
		dpw.setOutTradeNo(outTradeNo);
		List<DgPayWater> listWater=dgPayWaterService.findAllObj(dpw);
		DgPayWater water=null;
		if(listWater!=null&&listWater.size()>0){
			water=listWater.get(0);
		}else{
			return getResult(APIEnumDefine.P01009);
		}
		if(null!=water&&Constants.PAY_TYPE_WX.equals(water.getPayType())){
			msg=DgPayUtil.wxCheck(water, request, response);
		}else if(null!=water&&Constants.PAY_TYPE_ZFB.equals(water.getPayType())){
			msg=DgPayUtil.aliPayCheck(water);
		}
		if(DgConstants.RES_MEG.equals(msg)){
			return getResult(APIEnumDefine.S001);
		}
		return getResult(APIEnumDefine.P01010);
	}
	@Override
	public Map<String, Object> payULine(Double payMoney, String orgId, String payType, String orderNo, String operUser,
			  String auth_code, String body, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			APIEnumDefine relust = vailParams(payMoney, orgId, payType);
			if(APIEnumDefine.S001.equals(relust)){
			 String outTradeNo=SerialNumberUtil.cretatOrderNo(8);
			 //获取商户信息
			 DgMerchants merch=merchantsService.findOneMerch(orgId);
			 if(StringUtils.isBlank(merch.getId())){
				 return getResult(APIEnumDefine.P01004);
			 }
			 DgPayWater water=assWater(payMoney, orgId, payType, orderNo, operUser, outTradeNo, merch.getId());
			 int relustWater=dgPayWaterService.insert(water);
			 if(relustWater<1){
				 return getResult(APIEnumDefine.P01005);
			 }
			 Map<String, String> mapStr=new HashMap<String, String>();
			 mapStr.put("outTradeNo", outTradeNo);
	          String returnMsg="fail";
			 if(Constants.PAY_TYPE_WX.equals(payType)){
				 returnMsg=uLineWxPay(merch, auth_code, payMoney, body, outTradeNo, request, response);
			 }else if(Constants.PAY_TYPE_ZFB.equals(payType)){
				 returnMsg=aliPay(merch, auth_code, payMoney, body, outTradeNo);
			 }else{
				 return getResult(APIEnumDefine.P01006,mapStr);
			 }
			 if(!DgConstants.RES_MEG.equals(returnMsg)){
				 return getResult(APIEnumDefine.P01007,mapStr);
			 }
				return getSuccessResult(mapStr);
			}else{
				return getResult(relust);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getResult(APIEnumDefine.P01007);
	}
	
	
	/**
	 * 微信刷卡支付
	 * @author hs
	 * @param mch 
	 * @param yearMonth
	 * @param auth_code
	 * @param total_fee
	 * @param body
	 * @param outTradeNo
	 * @param request
	 * @param response
	 * @return
	 * @throws InterruptedException 
	 */
	public String uLineWxPay(DgMerchants mch,String auth_code,Double total_fee,String body,String outTradeNo,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException{
		
		String msg="fail";
		MicropayInfo orderInfo=new MicropayInfo();
		orderInfo.setBody(body);
		Map<String,Object> mStr=new HashMap<String,Object>();
		String josnMapStr=JSONObject.fromObject(mStr).toString();
		orderInfo.setAttach(josnMapStr);
		String nonce_str=SerialNumberUtil.create_nonce_str(); //随机字符串
		if(StringUtils.isNotBlank(auth_code))orderInfo.setAuth_code(auth_code.trim()); //授权码
		int a=(int) (total_fee*100);
		orderInfo.setTotal_fee(String.valueOf(a));
		if(mch!=null){
			orderInfo.setMch_id(mch.getWxMchId());
			orderInfo.setNonce_str(nonce_str);
			orderInfo.setOut_trade_no(outTradeNo);
			orderInfo.setSpbill_create_ip(DgConstants.getIpAddr(request));
		}
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String,String> sortSignMap=reqHandler.returnCreateSign(orderInfo);
			reqHandler.init(null,null,mch.getWxApiSecretKey());
			String sign = reqHandler.createSign(sortSignMap);
			   orderInfo.setSign(sign);
			   String xmlData=DgPayClassUtil.entityToXML(orderInfo);
			Element root=DgPayClassUtil.httpReqPraseXML(DgConstants.ULINE_WX_MICROPAY_URL, "POST", xmlData);
			if (null != root) {
				String return_code = root.elementText("return_code");
				String tradeTime=null;
				if(StringUtils.isNotBlank(root.elementText("time_end")))tradeTime=root.elementText("time_end"); //交易完成时间
				if("SUCCESS".equals(return_code)) {
					String return_msg = root.elementText("return_msg");
					String result_code=root.elementText("result_code");
					String err_code=null;
					String threeTradeNo=root.elementText("transaction_id");
					String payPeopleInfo=root.elementText("openid");
					if(StringUtils.isNotBlank(root.elementText("err_code")))err_code=root.elementText("err_code");
					if ("OK".equals(return_msg)&&"SUCCESS".equals(result_code)) {
						msg=DgConstants.RES_MEG;
						//修改流水状态
						dgPayWaterService.update(outTradeNo, tradeTime, threeTradeNo, payPeopleInfo);
					}else if("FAIL".equals(result_code)&&("USERPAYING".equals(err_code)
							||"BANKERROR".equals(err_code)||"SYSTEMERROR".equals(err_code))){
							long beginTime=System.currentTimeMillis();//开始时间
							long endTime=60*1000;//运行时间
							while (true) {
								long nowTime=System.currentTimeMillis();//当前时间
								if((nowTime-beginTime)<endTime){
									Thread.sleep(5000);
									Element elm=DgPayClassUtil.selectUlineWxOrder(root, mch, request, response,outTradeNo);
									if(null!=elm){
										if("SUCCESS".equals(elm.elementText("return_code"))&&"SUCCESS".equals(elm.elementText("result_code"))
												&&"SUCCESS".equals(elm.elementText("trade_state"))){
											msg=DgConstants.RES_MEG;
											//修改流水状态
											tradeTime=elm.elementText("time_end"); //交易完成时间
											dgPayWaterService.update(outTradeNo, tradeTime, threeTradeNo, payPeopleInfo);
											break;
										}
									}
								}else{
									break;
								}
							}
					}
				}
			}
		return msg;
	}
	
	
	/**
	 * Unline支付宝刷卡支付
	 * @author hs
	 * 日期 2017年1月17日 上午9:08:19
	 * @param mch 商户对象
	 * @param auth_code 支付授权码
	 * @param total_fee 支付金额
	 * @param body 订单描述
	 * @param outTradeNo 商户订单号
	 * @return
	 * @throws AlipayApiException
	 */
	private String uLineAliPay(DgMerchants mch,String auth_code,Double total_fee,String body,String outTradeNo,
			HttpServletRequest request,HttpServletResponse response) throws InterruptedException{
		String msg="fail";
		MicropayInfo orderInfo=new MicropayInfo();
		orderInfo.setBody(body);
		Map<String,Object> mStr=new HashMap<String,Object>();
		String josnMapStr=JSONObject.fromObject(mStr).toString();
		orderInfo.setAttach(josnMapStr);
		String nonce_str=SerialNumberUtil.create_nonce_str(); //随机字符串
		if(StringUtils.isNotBlank(auth_code))orderInfo.setAuth_code(auth_code.trim()); //授权码
		int a=(int) (total_fee*100);
		orderInfo.setTotal_fee(String.valueOf(a));
		if(mch!=null){
			orderInfo.setMch_id(mch.getWxMchId());
			orderInfo.setNonce_str(nonce_str);
			orderInfo.setScene("bar_code");
			orderInfo.setOut_trade_no(outTradeNo);
			orderInfo.setBody("蜀国大师支付宝");
			orderInfo.setSpbill_create_ip(DgConstants.getIpAddr(request));
			orderInfo.setNotify_url("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
		}
		RequestHandler reqHandler = new RequestHandler(request, response);
		SortedMap<String,String> sortSignMap=reqHandler.returnCreateSign(orderInfo);
			reqHandler.init(null,null,mch.getWxApiSecretKey());
			String sign = reqHandler.createSign(sortSignMap);
			   orderInfo.setSign(sign);
			   String xmlData=DgPayClassUtil.entityToXML(orderInfo);
			Element root=DgPayClassUtil.httpReqPraseXML(DgConstants.ULINE_ZFB_MICROPAY_URL, "POST", xmlData);
			if (null != root) {
				String return_code = root.elementText("return_code");
				String tradeTime=null;
				if(StringUtils.isNotBlank(root.elementText("time_end")))tradeTime=root.elementText("time_end"); //交易完成时间
				if("SUCCESS".equals(return_code)) {
					String result_code=root.elementText("result_code");
					String err_code=null;
					String threeTradeNo=root.elementText("transaction_id");
					String payPeopleInfo=root.elementText("openid");
					if(StringUtils.isNotBlank(root.elementText("err_code")))err_code=root.elementText("err_code");
					if ("SUCCESS".equals(return_code)&&"SUCCESS".equals(result_code)) {
						msg=DgConstants.RES_MEG;
						//修改流水状态
						dgPayWaterService.update(outTradeNo, tradeTime, threeTradeNo, payPeopleInfo);
					}else if("FAIL".equals(result_code)&&("USERPAYING".equals(err_code)
							||"BANKERROR".equals(err_code)||"SYSTEMERROR".equals(err_code))){
							long beginTime=System.currentTimeMillis();//开始时间
							long endTime=60*1000;//运行时间
							while (true) {
								long nowTime=System.currentTimeMillis();//当前时间
								if((nowTime-beginTime)<endTime){
									Thread.sleep(5000);
									Element elm=DgPayClassUtil.selectUlineWxOrder(root, mch, request, response,outTradeNo);
									if(null!=elm){
										if("SUCCESS".equals(elm.elementText("return_code"))&&"SUCCESS".equals(elm.elementText("result_code"))){
											msg=DgConstants.RES_MEG;
											//修改流水状态
											tradeTime=elm.elementText("time_end"); //交易完成时间
											dgPayWaterService.update(outTradeNo, tradeTime, threeTradeNo, payPeopleInfo);
											break;
										}
									}
								}else{
									break;
								}
							}
					}
				}
			}
		return msg;
	}


	@Override
	public boolean slotCardPayNew(String payType,String auth_code,String total_fee,String body,String terminalTrace,String operUser,String jsNum,
								  HttpServletRequest request,HttpServletResponse response) {
		PLcswMerchant merchant =  pLcswMerchantMapper.selectOne();
		SortedMap<String,String> params = new TreeMap<>();
		params.put("pay_ver",merchant.getPayver()); //版本号，当前版本100
		params.put("pay_type",payType.equals("WX")?merchant.getWxpaytype():merchant.getZfbpaytype()); //请求类型，010微信，020支付宝，060qq钱包，090口碑，100翼支付
		params.put("service_id",merchant.getServiceslotcard()); //接口类型，当前类型012
		params.put("merchant_no",merchant.getMerchantno()); //商户号
		params.put("terminal_id",merchant.getTerminalid()); //终端号
		params.put("terminal_trace",terminalTrace); //终端流水号，填写商户系统的订单号
		params.put("terminal_time", DateUtil.dateToStr(new Date(),DateUtil.yyyyMMddHHmmss)); //终端交易时间，yyyyMMddHHmmss，全局统一时间格式
		params.put("total_fee",total_fee); //金额，单位分
		params.put("auth_no",auth_code); //授权码，客户的付款码
		params.put("key_sign",createSign(params,merchant.getAccesstoken())); //签名字符串,拼装所有必传参数+令牌，UTF-8编码，32位md5加密转换

		String resStr = OnlineHttp.sbHttpPost(dgUrlSettingMapper.selectByCode("LCSW_BARCODEPAY_URL").getValue(), JSON.toJSONString(params));
		logger.error("slotCardPayNew:"+resStr);
		if(StringUtil.isNotEmpty(resStr)){
			Map resMap = JSON.parseObject(resStr,Map.class);
			if(resMap.containsKey("return_code") && resMap.get("return_code").equals("01")){
				//利楚唯一订单号
				String outTradeNo = resMap.get("out_trade_no").toString();
				if(resMap.containsKey("result_code") && resMap.get("result_code").equals("01")){
					assSbWater(total_fee, "1", payType, jsNum, operUser, terminalTrace,"1",outTradeNo,"0");
//					dgPayWaterService.update(terminalTrace, DateUtil.dateToStr(new Date(),DateUtil.YYYY_MM_DD_HH_MM_SS), outTradeNo, "无");
					return true;
				} else {
					long beginTime = System.currentTimeMillis();// 开始时间
					long endTime = 60 * 1000;// 运行时间
					while (true) {
						long nowTime = System.currentTimeMillis();// 当前时间
						if ((nowTime - beginTime) < endTime) {
							try {
								Thread.sleep(5000);
							} catch (InterruptedException e) {
								e.printStackTrace();
								return false;
							}
							String payStatus = queryOrder(terminalTrace,outTradeNo,payType, merchant);
							switch (payStatus) {
								case "SUCCESS":
									assSbWater(total_fee, "1", payType, jsNum, operUser, terminalTrace,"1",outTradeNo,"0");
									return true;
								case "REFUND":
									assSbWater(total_fee, "1", payType, jsNum, operUser, terminalTrace,"1",outTradeNo,"1");
									return false;
								case "NOTPAY":
									assSbWater(total_fee, "1", payType, jsNum, operUser, terminalTrace,"1",outTradeNo,"1");
									return false;
								case "CLOSED":
									assSbWater(total_fee, "1", payType, jsNum, operUser, terminalTrace,"1",outTradeNo,"1");
									return false;
								case "REVOKED":
									assSbWater(total_fee, "1", payType, jsNum, operUser, terminalTrace,"1",outTradeNo,"1");
									return false;
								case "USERPAYING":
									break;
								case "PAYERROR":
									assSbWater(total_fee, "1", payType, jsNum, operUser, terminalTrace,"1",outTradeNo,"1");
									return false;
							}
						} else {
							break;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public Map<String,Object>  createPayQr(String payType, String total_fee, String operUser, String orderWater) {
	    Map<String,Object> backmap=new HashMap<>();
        PLcswMerchant merchant =  pLcswMerchantMapper.selectOne();
        SortedMap<String,String> params = new TreeMap<>();
		params.put("pay_ver",merchant.getPayver()); //版本号，当前版本100
		params.put("pay_type",payType.equals("WX")?merchant.getWxpaytype():merchant.getZfbpaytype()); //请求类型，010微信，020支付宝，060qq钱包，090口碑，100翼支付
		params.put("service_id",merchant.getServiceprepay()); //接口类型，当前类型012
		params.put("merchant_no",merchant.getMerchantno()); //商户号
		params.put("terminal_id",merchant.getTerminalid()); //终端号
		String terminalTrace = SerialNumberUtil.create_nonce_str();
		params.put("terminal_trace",terminalTrace); //终端流水号，填写商户系统的订单号
		params.put("terminal_time", DateUtil.dateToStr(new Date(),DateUtil.yyyyMMddHHmmss)); //终端交易时间，yyyyMMddHHmmss，全局统一时间格式
		params.put("total_fee",total_fee); //金额，单位分
		params.put("attach",orderWater); //128字节原路返回
//		params.put("notify_url",sysConfigerService.getParamWithName("LCSW_NOTIFY_URL")); //回调通知
		params.put("key_sign",createSign(params,merchant.getAccesstoken())); //签名字符串,拼装所有必传参数+令牌，UTF-8编码，32位md5加密转换

        String resStr = OnlineHttp.sbHttpPost(dgUrlSettingMapper.selectByCode("LCSW_PREPAY_URL").getValue(), JSON.toJSONString(params));
        logger.error("createPayQr:"+resStr);
        if(StringUtil.isNotEmpty(resStr)) {
            Map resmap = JSON.parseObject(resStr, Map.class);
            if (resmap.containsKey("return_code") && resmap.get("return_code").equals("01")) {
                if (resmap.containsKey("result_code") && resmap.get("result_code").equals("01")) {
                    //利楚唯一订单号
                    String outTradeNo = resmap.get("out_trade_no").toString();
                    String qr_code = resmap.get("qr_code").toString();
                    backmap.put("msg", DgConstants.RES_MEG);
                    backmap.put("codeUrl", qr_code);
                    //插入三方订单
                    assSbWater(total_fee, "1", payType, orderWater, operUser, terminalTrace,"1",outTradeNo,"0");
                    return backmap;
                }
            }
        }
        backmap.put("msg", "二维码生存谁败");
        return backmap;
	}

	@Override
	public String queryOrder(String terminalTrace,String outTradeNo, String payType, PLcswMerchant merchant) {
		if(merchant == null){
			 merchant=pLcswMerchantMapper.selectOne();
		}
		SortedMap<String,String> params = new TreeMap<>();
		params.put("pay_ver",merchant.getPayver()); //版本号，当前版本100
		params.put("pay_type",payType.equals("WX")?merchant.getWxpaytype():merchant.getZfbpaytype()); //请求类型，010微信，020支付宝，060qq钱包，090口碑，100翼支付
		params.put("service_id",merchant.getServicequery()); //接口类型，当前类型012
		params.put("merchant_no",merchant.getMerchantno()); //商户号
		params.put("terminal_id",merchant.getTerminalid()); //终端号
		params.put("terminal_trace",terminalTrace); //终端号
		params.put("terminal_time", DateUtil.dateToStr(new Date(),DateUtil.yyyyMMddHHmmss)); //终端交易时间，yyyyMMddHHmmss，全局统一时间格式
		params.put("out_trade_no",outTradeNo); //金额，单位分
		params.put("key_sign",createSign(params,merchant.getAccesstoken())); //签名字符串,拼装所有必传参数+令牌，UTF-8编码，32位md5加密转换

		String resStr = OnlineHttp.sbHttpPost(dgUrlSettingMapper.selectByCode("LCSW_QUERY").getValue(),JSON.toJSONString(params));
		logger.error("resStr:"+resStr);
		if(StringUtil.isNotEmpty(resStr)){
			Map resmap = JSON.parseObject(resStr,Map.class);
			if(resmap.containsKey("return_code")&&resmap.get("return_code").equals("01")){
				if(resmap.containsKey("result_code")&&resmap.get("result_code").equals("01")){
					return "SUCCESS";
				} else {
					if(resmap.containsKey("trade_state")){
						return resmap.get("trade_state").toString();
					} else {
						return "USERPAYING";
					}
				}
			} else {
				return "USERPAYING";
			}
		}
		return "USERPAYING";
	}

	public String createSign(SortedMap<String, String> packageParams,String access_token) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("access_token=" + access_token);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8")
				.toUpperCase();
		return sign;
	}
}
