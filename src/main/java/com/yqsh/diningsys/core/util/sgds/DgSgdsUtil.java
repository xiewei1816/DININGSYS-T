package com.yqsh.diningsys.core.util.sgds;
import java.util.SortedMap;

import org.springframework.stereotype.Component;

import com.yqsh.diningsys.core.util.DateUtil;

import net.sf.json.JSONObject;

/**
 * 蜀国大师新全程通接入积分管理类
 * @author xiewei
 * 日期 2017年9月27日 下午13:12:54
 *
 */
@Component
public class DgSgdsUtil {
	
	/**
	 * 
	 * 调用蜀国大师会员积分管理接口
	 * @author xiewei
	 * 日期 2017年9月27日 下午13:12:54
	 * @return
	 */
	public static JSONObject requestAddIntegralByMobile(String mobile,String serialNumber,String amount){
		DgSgdsIntegral dgSgdsIntegral = new DgSgdsIntegral();
		dgSgdsIntegral.setMobile(mobile);
		dgSgdsIntegral.setSerialNumber(serialNumber);
		dgSgdsIntegral.setAmount(amount);
		dgSgdsIntegral.setAccessToken(parseJsonObject(requestAccessToken()));
		dgSgdsIntegral.setTimestamp(DateUtil.getCurrentTimeMillis()+"");
		dgSgdsIntegral.setMethod(DgSgdsConstants.SGDS_User_AddIntegralByMobile);
		dgSgdsIntegral.setVersion(DgSgdsConstants.VERSION);
		RequestSgdsHandler reqSgdsHandler = new RequestSgdsHandler();
		SortedMap<String,String> sortSign = reqSgdsHandler.returnCreateSgdsSignByUser(dgSgdsIntegral);
		String sign = reqSgdsHandler.createSgdsSign(sortSign);
		dgSgdsIntegral.setSign(sign);
		//组装参数
		SortedMap<String,String> sortParams = reqSgdsHandler.createSgdsParams(dgSgdsIntegral);
		String params = reqSgdsHandler.createSgdsParams(sortParams) + "sign=" + sign;
		JSONObject jsonObject = DgSgdsClassUtil.httpRequestArray(DgSgdsConstants.SGDS_FORMAL_URL + params, DgSgdsConstants.REQUST_METHOD_GET, sign);
		return jsonObject;
	}
	
	/**
	 * 
	 * 调用蜀国大师获取Token接口
	 * @author xiewei
	 * 日期 2017年9月27日 下午13:12:54
	 * @return
	 */
	public static JSONObject requestAccessToken(){
		DgSgdsIntegral dgSgdsIntegral = new DgSgdsIntegral();
		dgSgdsIntegral.setAppid(DgSgdsConstants.appId);
		dgSgdsIntegral.setTimestamp(DateUtil.getCurrentTimeMillis()+"");
		dgSgdsIntegral.setMethod(DgSgdsConstants.SGDS_Token_GetAccessToken);
		dgSgdsIntegral.setVersion(DgSgdsConstants.VERSION);
		RequestSgdsHandler reqSgdsHandler = new RequestSgdsHandler();
		SortedMap<String,String> sortSign = reqSgdsHandler.returnCreateSgdsSignByToken(dgSgdsIntegral);
		String sign = reqSgdsHandler.createSgdsSign(sortSign);
		dgSgdsIntegral.setSign(sign);
		//组装参数
		SortedMap<String,String> sortParams = reqSgdsHandler.createSgdsParams(dgSgdsIntegral);
		String params = reqSgdsHandler.createSgdsParams(sortParams) + "sign=" + sign;
		JSONObject jsonObject = DgSgdsClassUtil.httpRequestArray(DgSgdsConstants.SGDS_FORMAL_URL + params, DgSgdsConstants.REQUST_METHOD_GET, sign);
		return jsonObject;
	}
	
	/**
	 * 解析返回值JSON
	 */
	public static String parseJsonObject(JSONObject jsonObject){
		String code = jsonObject.get("code").toString();
		String message = null;
		String result = null;
		if(code.equals("0")){
			result = jsonObject.get("result").toString();
			try {
				JSONObject jsonTokenObject = JSONObject.fromObject(result);
				result = jsonTokenObject.get("newAccessToken").toString();
			} catch (Exception e) {
				return result;
			}
			return result;
		}else{
			message = jsonObject.get("message").toString();
			return message;
		}
	}
	
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
//		JSONObject ja = requestAccessToken();
//		System.out.println(parseJsonObject(ja));
		
		JSONObject ja = requestAddIntegralByMobile("13980404664", "Xf201708799", "500");
		System.out.println(parseJsonObject(ja));
	}
	
}
