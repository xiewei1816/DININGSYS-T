package com.yqsh.diningsys.api;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wordnik.swagger.annotations.ApiOperation;
import com.yqsh.diningsys.api.util.ApiBaseController;
import com.yqsh.diningsys.web.service.api.APIDiningTabService;

/**
 * Created by yqsh-hs on 2017/3/22.
 */
@Controller
@RequestMapping("/yqshapi")
@SuppressWarnings("all")
public class APIDiningTableController extends ApiBaseController {
	@Autowired
	private APIDiningTabService apiDiningTabService;

	/**
	 * 餐台接口
	 * 
	 * @return
	 */
	@RequestMapping("/SmartTable")
	@ResponseBody
	@ApiOperation(value = "餐台接口对接", httpMethod = "POST", notes = "餐台接口对接")
	public Object SmartTable(HttpServletRequest request) {
		Map<String, String[]> dataMap = request.getParameterMap();
        Iterator it= dataMap.keySet().iterator();
        String data = "";
        while(it.hasNext()){  
        	 String key = it.next().toString();
        	 if(!StringUtils.isEmpty(key)) {
        		 data += key;
        	 }
        	 String[] value = dataMap.get(key);
        	 for(int i=0;i<value.length;i++) {
            	 if(!StringUtils.isEmpty(value[i])) {
            		 data += value[i];
            	 } 
        	 }
        }  
		String dataString = Base64.decodeToString(data);
		String orderCode = dataString.substring(0, 4);
		Map content = null;
		switch (orderCode) {
		case "0001":
			content = apiDiningTabService.queryTable(dataString);
			break;
		case "0002":
			content = apiDiningTabService.queryItemFile(dataString);
			break;
		case "0080":
			content = apiDiningTabService.insertOrderItemFile(dataString,1);
			break;
		case "0081": //预定单数据检测
			content = apiDiningTabService.insertPreOrderItemFileBefor(dataString);
			break;
		case "0082": //预定单数据下单
			content = apiDiningTabService.insertOrderItemFile(dataString,2);
			break;
		default:
		}
		Gson gson = new Gson();
		return getMac(orderCode + "001", gson.toJson(content));
	}
}