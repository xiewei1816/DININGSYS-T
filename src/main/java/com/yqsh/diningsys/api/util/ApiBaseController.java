/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yqsh.diningsys.api.util;


import com.alibaba.fastjson.JSONObject;
import com.yqsh.diningsys.api.enums.APIEnumDefine;
import com.yqsh.diningsys.core.util.MathExtend;
import com.yqsh.diningsys.web.model.deskBusiness.DgOwLockinfo;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.net.util.Base64;


/**
 *
 * @author yqsh-zc
 */
public class ApiBaseController {

	public Map<String, Object> getResult(APIEnumDefine enumDefine) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", enumDefine.toString());
		result.put("msg", APIEnumDefine.enumValue(enumDefine.toString()));
		return result;
	}

	public Map<String, Object> getResult(String msg) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", "error");
		result.put("msg", msg);
		return result;
	}

	public Map<String, Object> getResult(APIEnumDefine enumDefine, Object object) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", enumDefine.toString());
		result.put("msg", APIEnumDefine.enumValue(enumDefine.toString()));
		result.put("body", object);
		if (enumDefine == APIEnumDefine.S992) {
			DgOwLockinfo info = (DgOwLockinfo) object;
			String msg = (String) result.get("msg");
			msg = msg.replace("owNum", info.getOwNum());
			String state = "";
			if (info.getOwState() == 3) {
				state = "埋单";
				msg = msg.replace("state", state);
			} else if (info.getOwState() == 8) {
				state = "手工锁定";
				msg = msg.replace("state", state);
			} else if (info.getOwState() == 9) {
				state = "结算锁定";
				msg = msg.replace("state", state);
			}
			msg = msg.replace(
					"content",
					state + "人:" + info.getLockOperator() + " - "
							+ info.getLockOpeartorName() + "    " + state
							+ "时间:" + info.getLockTime() + "   Pos:"
							+ info.getLockPos() + " - "
							+ info.getLockPosName());
			result.put("msg", msg);
		}
		else if(enumDefine == APIEnumDefine.S1000)
		{
			String msg = (String) result.get("msg");
			msg = msg.replace(
					"Msg",(String)object);
			result.put("msg", msg);
		}
		return result;
	}

	public Map<String, Object> getSuccessResult() {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S001);
		result.put("msg", APIEnumDefine.enumValue("S001"));
		return result;
	}

	public Map<String, Object> getSuccessResult(Object obj) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S001);
		result.put("msg", APIEnumDefine.enumValue("S001"));
		result.put("body", obj);
		return result;
	}

	public Map<String, Object> getFailResult() {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S002);
		result.put("msg", APIEnumDefine.enumValue("S002"));
		return result;
	}

	public Map<String, Object> getFailResult(Object obj) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S002);
		result.put("msg", APIEnumDefine.enumValue("S002"));
		result.put("body", obj);
		return result;
	}

	public Map<String, Object> getFailResult(String msg,Object obj) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S002);
		result.put("msg", msg);
		result.put("body", obj);
		return result;
	}

	public Map<String, Object> getExceptionResult() {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S999);
		result.put("msg", APIEnumDefine.enumValue("S999"));
		return result;
	}

	/**
	 * 返回成功/内部返回信息
	 *
	 * @param msg
	 * @return
	 */
	public Map<String, Object> getSuccessResultExtra(String msg) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S001);
		result.put("msg", msg);
		return result;
	}

	public Map<String, Object> getFailHyResult(Object obj) {
		Map<String, Object> result = new HashMap<>();
		result.put("result", APIEnumDefine.S002);
		result.put("msg", obj);
		return result;
	}

	/************************************买单结算相关方法************************************************/
	/**
	 * 设置3种小计的抹零金额
	 */
	public Double setZeroValue(Map<String,Double> value){
		return value.get("zero");
	}

	/**
	 * 设置收尾法中的取到元抹零金额
	 */
	public Double setZeroValue(Double subtotalValue){
		Double newV = Math.ceil(subtotalValue);
		Double zeroValue = MathExtend.subtract(newV,subtotalValue);
		return zeroValue;
	}

	/**
	 * 设置3种小计的应收金额
	 */
	public Double setYsje(Map<String,Double> value){
		return value.get("new");
	}

	/**
	 * 设置收尾法中的取到元抹零金额
	 */
	public Double setYsje(Double subtotalValue){
		return Math.ceil(subtotalValue);
	}

	/**
	 * 收尾取到角
	 * @return
	 */
	public Map<String,Double> swqdj(Double value){
		Map<String,Double> map = new HashMap();

		BigDecimal bValue = new BigDecimal(value.toString()),
				zero = BigDecimal.ZERO;

		if(bValue.compareTo(zero) == 0){
			map.put("zero", zero.doubleValue());
			map.put("new",zero.doubleValue());
			return map;
		}

		Double newV = 0.;
		String bigDecimal = new BigDecimal(value.toString()).setScale(2).toString();
		map.put("zero", 0.);
		map.put("new",value);
		if(!bigDecimal.endsWith(".00")){
			String  ends = bigDecimal.substring(bigDecimal.indexOf(".")+2,bigDecimal.length());
			if(!ends.equals("0")){
				String newB = bigDecimal.substring(0,bigDecimal.length()-1)+"0";
				newV = new BigDecimal(newB).setScale(2).add(new BigDecimal("0.1")).doubleValue();
				map.put("zero", MathExtend.subtract(newV,value));
				map.put("new",newV);
			}
		}else{
			map.put("new",value);
		}
		return map;
	}

	/**
	 * 收尾取到5元
	 * @return
	 */
	public Map swqdfive(Double value){
		Map<String,Double> map = new HashMap();
		BigDecimal dValue = new BigDecimal(value.toString()),
				five = new BigDecimal("5"),
				zero = BigDecimal.ZERO;

		if(dValue.compareTo(zero) == 0){
			map.put("new",zero.doubleValue());
			map.put("zero", zero.doubleValue());
			return map;
		}

		if(dValue.compareTo(five) <= 0){
			map.put("new",five.doubleValue());
			map.put("zero", five.subtract(dValue).setScale(2).doubleValue());
		}else{
			BigDecimal remainder = dValue.remainder(five);
			Integer compareTo = remainder.compareTo(zero);

			if(compareTo == 0){
				map.put("new",value.doubleValue());
				map.put("zero", zero.doubleValue());
			}else{

				Integer i = dValue.divide(five).add(BigDecimal.ONE).intValue();

				BigDecimal newValue = five.multiply(new BigDecimal(i.toString()));

				map.put("new",newValue.doubleValue());
				map.put("zero", newValue.subtract(dValue).setScale(2).doubleValue());
			}
		}
		return map;
	}

	/**
	 * 收尾取到十元
	 * @return
	 */
	public Map swqdten(Double value){
		Map<String,Double> map = new HashMap();
		BigDecimal dValue = new BigDecimal(value.toString()),
				ten = BigDecimal.TEN,
				zero = BigDecimal.ZERO;

		if(dValue.compareTo(zero) == 0){
			map.put("new",zero.doubleValue());
			map.put("zero", zero.doubleValue());
			return map;
		}

		if(dValue.compareTo(ten) <= 0){
			map.put("new",ten.doubleValue());
			map.put("zero", ten.subtract(dValue).setScale(2).doubleValue());
		}else{
			BigDecimal remainder = dValue.remainder(ten);
			Integer compareTo = remainder.compareTo(zero);

			if(compareTo == 0){
				map.put("new",value.doubleValue());
				map.put("zero", zero.doubleValue());
			}else{

				Integer i = dValue.divide(ten).add(BigDecimal.ONE).intValue();

				BigDecimal newValue = ten.multiply(new BigDecimal(i.toString()));

				map.put("new",newValue.doubleValue());
				map.put("zero", newValue.subtract(dValue).setScale(2).doubleValue());
			}
		}
		return map;
	}

	public Double returnValue(Double value){
		if(value == null){
			return 0.;
		}
		return value;
	}

	/*去尾法*/

	/**
	 * 去尾法-取到角
	 * @param value
	 * @return
	 */
	public Map qwqdj(Double value){
		Map<String,Object> map = new HashMap<>();

		DecimalFormat df   = new DecimalFormat("######0.00");
		String format = df.format(value);

		if(format.endsWith("0")){ //以x.x0结尾，直接返回
			map.put("new",Double.parseDouble(format));
			map.put("zero", 0.);
			return map;
		}

		String substring = format.substring(0, format.length() - 1) + "0";
		map.put("new",Double.parseDouble(substring));
		map.put("zero", MathExtend.subtract(value,Double.parseDouble(substring)));
		return map;
	}

	/**
	 * 去尾法-取到元
	 * @param value
	 * @return
	 */
	public Map qwqdy(Double value){
		Map<String,Object> map = new HashMap<>();

		BigDecimal bValue = new BigDecimal(value.toString());
		Integer newValue = bValue.intValue();

		map.put("new",Double.parseDouble(newValue.toString()));
		map.put("zero", new BigDecimal(newValue.toString()).subtract(bValue).setScale(2).doubleValue());

		return map;
	}

	/**
	 * 去尾法-取到5元
	 * @param value
	 * @return
	 */
	public Map qwqd5y(Double value){
		Map<String,Object> map = new HashMap<>();
		BigDecimal bigDecimal = new BigDecimal(value.toString()),
				five = new BigDecimal("5"),
				zero = BigDecimal.ZERO;
		Integer i = bigDecimal.compareTo(five),
				one = 1,zeroI = 0;
		map.put("new",zero.doubleValue());
		map.put("zero", value.doubleValue());
		if(i == zeroI){ //等于5
			map.put("new",value.doubleValue());
			map.put("zero", zero.doubleValue());
		}else if(i == one){//大于5
			Integer divideValue = bigDecimal.divide(five).intValue();

			BigDecimal multiply = five.multiply(new BigDecimal(divideValue.toString()));

			map.put("new",multiply.doubleValue());
			map.put("zero",multiply.subtract(bigDecimal).setScale(2).doubleValue());
		}
		return map;
	}

	/**
	 * 去尾法-取到10元
	 * @param value
	 * @return
	 */
	public Map qwqd10y(Double value){
		Map<String,Object> map = new HashMap<>();
		BigDecimal bigDecimal = new BigDecimal(value.toString()),
				ten = BigDecimal.TEN,
				zero = BigDecimal.ZERO;
		Integer i = bigDecimal.compareTo(ten),
				one = 1;
		map.put("new",zero.doubleValue());
		map.put("zero", value.doubleValue());
		if(i == 0){ //等于10
			map.put("new",value.doubleValue());
			map.put("zero", zero.doubleValue());
		}else if(i == one){//大于10
			Integer divideValue = bigDecimal.divide(ten).intValue();

			BigDecimal multiply = ten.multiply(new BigDecimal(divideValue.toString()));

			map.put("new",multiply.doubleValue());
			map.put("zero",multiply.subtract(bigDecimal).setScale(2).doubleValue());
		}
		return map;
	}

	/*四舍五入法*/

	/**
	 * 四舍五入取到角
	 * @param value
	 * @return
	 */
	public Map roundingQdj(Double value){
		Map<String,Object> map = new HashMap<>();

		BigDecimal ovalue = new BigDecimal(value.toString());
		BigDecimal newValue = ovalue.setScale(1,BigDecimal.ROUND_HALF_UP).setScale(2);

		map.put("new",newValue.doubleValue());
		map.put("zero", ovalue.subtract(newValue).doubleValue());

		return map;
	}

	/**
	 * 四舍五入取到元
	 * @param value
	 * @return
	 */
	public Map roundingQdy(Double value){
		Map<String,Object> map = new HashMap<>();

		BigDecimal ovalue = new BigDecimal(value.toString());
		BigDecimal newValue = ovalue.setScale(0, BigDecimal.ROUND_HALF_UP).setScale(2);

		map.put("new",newValue.doubleValue());
		map.put("zero", ovalue.subtract(newValue).doubleValue());

		return map;
	}

	/**
	 * 四舍五入取到5元
	 * @param value
	 * @return
	 */
	public Map roundingQd5y(Double value){
		Map<String,Object> map = new HashMap<>();

		BigDecimal ovalue = new BigDecimal(value.toString()),
				five = new BigDecimal("5"),
				zero = BigDecimal.ZERO,
				three = new BigDecimal("3"),
				remainder = ovalue.remainder(five);

		Integer zeroI = 0;

		if(remainder.compareTo(zero) == zeroI){
			map.put("new",value.doubleValue());
			map.put("zero", zero.doubleValue());
		}else {
			if(remainder.compareTo(three) < zeroI){
				Integer divide = ovalue.divide(five).intValue();

				BigDecimal newValue = five.multiply(new BigDecimal(divide.toString())).setScale(2);

				map.put("new",newValue.doubleValue());
				map.put("zero", newValue.subtract(ovalue).setScale(2).doubleValue());
			}else {
				Integer divide = ovalue.divide(five).add(BigDecimal.ONE).intValue();

				BigDecimal newValue = five.multiply(new BigDecimal(divide.toString())).setScale(2);

				map.put("new",newValue.doubleValue());
				map.put("zero", newValue.subtract(ovalue).setScale(2).doubleValue());
			}
		}

		return map;
	}

	/**
	 * 四舍五入取到10元
	 * @param value
	 * @return
	 */
	public Map roundingQd10y(Double value){
		Map<String,Object> map = new HashMap<>();

		BigDecimal ovalue = new BigDecimal(value.toString()),
				ten = BigDecimal.TEN,
				zero = BigDecimal.ZERO,
				five = new BigDecimal("5"),
				remainder = ovalue.remainder(ten);

		Integer zeroI = 0;

		if(remainder.compareTo(zero) == zeroI){
			map.put("new",value.doubleValue());
			map.put("zero", zero.doubleValue());
		}else {
			if(remainder.compareTo(five) < zeroI){
				Integer divide = ovalue.divide(ten).intValue();

				BigDecimal newValue = ten.multiply(new BigDecimal(divide.toString())).setScale(2);

				map.put("new",newValue.doubleValue());
				map.put("zero", newValue.subtract(ovalue).setScale(2).doubleValue());
			}else {
				Integer divide = ovalue.divide(ten).add(BigDecimal.ONE).intValue();

				BigDecimal newValue = ten.multiply(new BigDecimal(divide.toString())).setScale(2);

				map.put("new",newValue.doubleValue());
				map.put("zero", newValue.subtract(ovalue).setScale(2).doubleValue());
			}
		}

		return map;
	}

	/************************************买单结算相关方法 END************************************************/


	/************************************获取输入流************************************************/
	public String getRequestData(HttpServletRequest request)
	{
		StringBuffer sb = new StringBuffer();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}

	/************************************计算数据mac************************************************/
	public String getMac(String order,String jsonContent)
	{
		String str = order+jsonContent;
		try {
			String keyStr = ByteUtils.bytesToHexString("12345678".getBytes("GBK"));
			String mac = AnsiiX99MacUtil.getMac(str.getBytes("GBK"),keyStr);
			String allStr = str+mac;
			String encodeBase64 = Base64.encodeBase64String(allStr.getBytes("UTF-8"));
			return encodeBase64;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	//时间戳精确到天
	public static int getSecondTimestamp(Date date){
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime());
		int length = timestamp.length();
		if (length > 3) {
			return Integer.valueOf(timestamp.substring(0,length-3));
		} else {
			return 0;
		}
	}


//	/************************************易小二推送************************************************/
//	public void yxeTs(Integer type,int seatId){
//		for (Map.Entry<Integer, Channel> entry : ChanneList.channels.entrySet()) {  
//		  	if( entry.getKey().equals(seatId)){
//		  		Message msg = new Message();
//		  		msg.setType(MessageType.MSG_PUSH.getValue());
//		  		msg.setMsg(new PushMsg(type, seatId));
//		  		entry.getValue().writeAndFlush(JSONObject.toJSONString(msg));
//		  	}
//		}  
//	}

	/***
	 *  利用Apache的工具类实现SHA-256加密
	 * @param str 加密后的报文
	 * @return
	 */
	public static String getSHA256Str(String str){
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}
}
